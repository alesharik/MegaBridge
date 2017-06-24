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

import com.alesharik.megabridge.api.channel.ChannelManager;
import com.alesharik.megabridge.api.command.CommandRegistry;
import com.alesharik.megabridge.api.configuration.AvailableConfiguration;
import com.alesharik.megabridge.api.configuration.Configuration;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;

public interface Module {
    @Nonnull
    String getName();

    String getVersion();

    AvailableConfiguration getAvailableConfiguration();

    void configure(Configuration configuration) throws LoginException;

    ChannelManager getChannelManager();

    void handleCommandRegistry(CommandRegistry registry);
}
