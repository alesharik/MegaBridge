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

package com.alesharik.megabridge.discord;

import com.alesharik.megabridge.api.Module;
import com.alesharik.megabridge.api.channel.ChannelManager;
import com.alesharik.megabridge.api.command.CommandRegistry;
import com.alesharik.megabridge.api.configuration.AvailableConfiguration;
import com.alesharik.megabridge.api.configuration.Configuration;
import com.alesharik.megabridge.api.configuration.UserType;
import com.alesharik.megabridge.api.configuration.login.TokenConfiguration;
import lombok.EqualsAndHashCode;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import java.util.concurrent.atomic.AtomicReference;

@EqualsAndHashCode
public class DiscordModule implements Module {
    private final AtomicReference<JDA> api;
    private final DiscordMessageListener listener;

    public DiscordModule() {
        api = new AtomicReference<>();
        listener = new DiscordMessageListener();
    }

    @Nonnull
    @Override
    public String getName() {
        return "discord";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public AvailableConfiguration getAvailableConfiguration() {
        return DiscordAvailableConfiguration.INSTANCE;
    }

    @Override
    public void configure(Configuration configuration) throws LoginException {
        TokenConfiguration config;
        if(!(configuration.getUserConfiguration() instanceof TokenConfiguration))
            throw new IllegalArgumentException();
        config = (TokenConfiguration) configuration.getUserConfiguration();
        try {
            JDA jda = new JDABuilder(configuration.getType() == UserType.USER ? AccountType.CLIENT : AccountType.BOT)
                    .setAudioEnabled(false)
                    .setAutoReconnect(true)
                    .setEnableShutdownHook(true)
                    .setToken(config.getToken())
                    .buildBlocking();

            jda.addEventListener(listener);

            api.set(jda);
        } catch (InterruptedException | RateLimitedException e) {
            e.getStackTrace();
        }
    }

    @Override
    public ChannelManager getChannelManager() {
        return new DiscordChannelManager(api.get(), listener);
    }

    @Override
    public void handleCommandRegistry(CommandRegistry registry) {

    }
}
