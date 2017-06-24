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
import com.alesharik.megabridge.api.configuration.AvailableConfiguration;
import com.alesharik.megabridge.api.configuration.Configuration;

import javax.security.auth.login.LoginException;
import java.util.List;

public interface ModuleManager {
    List<String> getModules();

    AvailableConfiguration getAvailableConfiguration(String name) throws ModuleNotFoundException;

    Module load(String name, Configuration configuration) throws ModuleNotFoundException, LoginException;

    void unload(Module module);

    void unload(String name);

    List<Module> getLoadedModules();
}
