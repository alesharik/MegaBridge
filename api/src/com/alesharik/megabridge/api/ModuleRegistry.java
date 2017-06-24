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

import com.alesharik.webserver.api.agent.classPath.ClassPathScanner;
import com.alesharik.webserver.api.agent.classPath.ListenInterface;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
@ClassPathScanner
public class ModuleRegistry {
    private static final Map<String, Module> modules = new ConcurrentHashMap<>();

    @ListenInterface(Module.class)
    public static void listenModule(Class<?> clazz) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            Module module = (Module) constructor.newInstance();
            modules.put(module.getName(), module);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static List<Module> getModules() {
        return new ArrayList<>(modules.values());
    }

    @Nullable
    public static Module getModuleByName(String name) {
        return modules.get(name);
    }
}
