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

import com.alesharik.megabridge.api.GsonUtils;
import com.alesharik.megabridge.main.module.ModuleManager;
import lombok.AllArgsConstructor;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.HttpStatus;

@AllArgsConstructor
public class GetLoadedModulesHttpHandler extends HttpHandler {
    private final ModuleManager moduleManager;

    @Override
    public void service(Request request, Response response) throws Exception {
        if(!TokenStorage.isTokenValid(request.getCookies()))
            response.setStatus(HttpStatus.FORBIDDEN_403);

        String text = GsonUtils.getGson().toJson(moduleManager.getLoadedModules());
        response.setStatus(HttpStatus.OK_200);
        response.setContentLength(text.length());
        response.getWriter().write(text);
    }
}
