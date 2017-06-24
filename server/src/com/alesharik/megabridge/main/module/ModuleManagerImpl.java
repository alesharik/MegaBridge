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

package com.alesharik.megabridge.main.module;

import com.alesharik.megabridge.api.Module;
import com.alesharik.megabridge.api.ModuleRegistry;
import com.alesharik.megabridge.api.configuration.AvailableConfiguration;
import com.alesharik.megabridge.api.configuration.Configuration;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public final class ModuleManagerImpl implements ModuleManager {
    private final List<Module> loadedModules = new CopyOnWriteArrayList<>();

    @Override
    public List<String> getModules() {
        return ModuleRegistry.getModules().stream()
                .map(Module::getName)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public AvailableConfiguration getAvailableConfiguration(String name) {
        Module module = getModule(name);
        return module.getAvailableConfiguration();
    }

    @Override
    public Module load(String name, Configuration configuration) throws LoginException {
        Module module = getModule(name);
        module.configure(configuration);
        loadedModules.add(module);
        return module;
    }

    private Module getModule(String name) {
        Module module = ModuleRegistry.getModuleByName(name);
        if(module == null)
            throw new ModuleNotFoundException();
        return module;
    }

    @Override
    public void unload(Module module) {
        if(loadedModules.contains(module))
            loadedModules.remove(module);
    }

    @Override
    public void unload(String name) {
        for(Module loadedModule : loadedModules) {
            if(loadedModule.getName().equals(name)) {
                loadedModules.remove(loadedModule);
                return;
            }
        }
    }

    @Override
    public List<Module> getLoadedModules() {
        return Collections.unmodifiableList(loadedModules);
    }
}
