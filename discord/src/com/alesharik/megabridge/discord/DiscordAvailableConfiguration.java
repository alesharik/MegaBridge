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

import com.alesharik.megabridge.api.configuration.AvailableConfiguration;
import com.alesharik.megabridge.api.configuration.UserType;
import com.alesharik.megabridge.api.configuration.login.LoginType;

final class DiscordAvailableConfiguration implements AvailableConfiguration {
    static final DiscordAvailableConfiguration INSTANCE = new DiscordAvailableConfiguration();

    @Override
    public UserType[] getAllowedTypes() {
        return new UserType[]{UserType.BOT, UserType.USER};
    }

    @Override
    public LoginType getLoginType() {
        return LoginType.TOKEN;
    }
}
