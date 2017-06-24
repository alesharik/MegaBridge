/*
 *     This file is part of Megabridge.
 *
 *     Megabridge is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Megabridge is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Megabridge.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.alesharik.megabridge.api;

import com.alesharik.megabridge.api.configuration.Configuration;
import com.alesharik.megabridge.api.configuration.UserType;
import com.alesharik.megabridge.api.configuration.login.LoginConfiguration;
import com.alesharik.megabridge.api.configuration.login.LoginType;
import com.alesharik.megabridge.api.configuration.login.TokenConfiguration;
import com.alesharik.megabridge.api.configuration.login.UserConfiguration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Type;

public final class GsonUtils {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeHierarchyAdapter(Module.class, new ModuleSerializer())
            .registerTypeHierarchyAdapter(Module.class, new ModuleDeserializer())
            .registerTypeHierarchyAdapter(Configuration.class, new ConfigurationSerializer())
            .registerTypeHierarchyAdapter(LoginConfiguration.class, new LoginConfigurationSerializer())
            .registerTypeHierarchyAdapter(Configuration.class, new ConfigurationDeserializer())
            .registerTypeHierarchyAdapter(LoginConfiguration.class, new LoginConfigurationDeserializer())
            .create();

    public static Gson getGson() {
        return GSON;
    }

    private static final class ModuleSerializer implements JsonSerializer<Module> {
        @Override
        public JsonElement serialize(Module src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.add("name", new JsonPrimitive(src.getName()));
            object.add("version", new JsonPrimitive(src.getVersion()));
            return object;
        }
    }

    private static final class ModuleDeserializer implements JsonDeserializer<Module> {

        @Override
        public Module deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            Module module = ModuleRegistry.getModuleByName(object.get("name").getAsString());
            if(module == null || !object.get("version").getAsString().equals(module.getVersion()))
                throw new JsonParseException("versions aren't same");
            return module;
        }
    }

    private static final class ConfigurationSerializer implements JsonSerializer<Configuration> {

        @Override
        public JsonElement serialize(Configuration src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.add("type", new JsonPrimitive(src.getType().toString()));
            object.add("loginConfig", context.serialize(src.getUserConfiguration()));
            return object;
        }
    }

    private static final class LoginConfigurationSerializer implements JsonSerializer<LoginConfiguration> {

        @Override
        public JsonElement serialize(LoginConfiguration src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.add("type", new JsonPrimitive(src.getType().toString()));
            if(src instanceof UserConfiguration) {
                object.add("login", new JsonPrimitive(((UserConfiguration) src).getLogin()));
                object.add("password", new JsonPrimitive(((UserConfiguration) src).getPassword()));
            } else {
                object.add("token", new JsonPrimitive(((TokenConfiguration) src).getToken()));
            }
            return object;
        }
    }

    private static final class ConfigurationDeserializer implements JsonDeserializer<Configuration> {

        @Override
        public Configuration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            return new ConfigurationImpl(context.deserialize(object.get("loginConfig"), LoginConfiguration.class), UserType.valueOf(object.get("type").getAsString()));
        }

        @AllArgsConstructor
        private static final class ConfigurationImpl implements Configuration {
            private final LoginConfiguration loginConfiguration;
            private final UserType userType;

            @Override
            public LoginConfiguration getUserConfiguration() {
                return loginConfiguration;
            }

            @Override
            public UserType getType() {
                return userType;
            }
        }
    }

    private static final class LoginConfigurationDeserializer implements JsonDeserializer<LoginConfiguration> {

        @Override
        public LoginConfiguration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            LoginType type = LoginType.valueOf(object.get("type").getAsString());
            if(type == LoginType.USER) {
                return new UserConfigurationImpl(object.get("login").getAsString(), object.get("password").getAsString());
            } else {
                return new TokenConfigurationImpl(object.get("token").getAsString());
            }
        }

        @AllArgsConstructor
        @Getter
        private static final class UserConfigurationImpl implements UserConfiguration {
            private final String login;
            private final String password;
        }

        @AllArgsConstructor
        @Getter
        private static final class TokenConfigurationImpl implements TokenConfiguration {
            private final String token;
        }
    }
}
