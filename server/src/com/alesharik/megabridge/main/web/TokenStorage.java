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

package com.alesharik.megabridge.main.web;

import com.alesharik.webserver.api.collections.CachedArrayList;
import lombok.experimental.UtilityClass;
import org.glassfish.grizzly.http.Cookie;

import java.util.UUID;

@UtilityClass
public final class TokenStorage {
    private static final CachedArrayList<UUID> tokens = new CachedArrayList<>();

    public static boolean isTokenValid(String token) {
        return tokens.contains(UUID.fromString(token));
    }

    public static boolean isTokenValid(Cookie[] cookies) {
        for(Cookie cookie : cookies) {
            if("token".equals(cookie.getName()) && isTokenValid(cookie.getValue())) {
                return true;
            }
        }
        return false;
    }

    public static String getToken() {
        UUID uuid = UUID.randomUUID();
        tokens.add(uuid, 3600 * 1000);
        return uuid.toString();
    }
}
