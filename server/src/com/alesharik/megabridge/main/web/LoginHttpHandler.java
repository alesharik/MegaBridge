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

import com.alesharik.megabridge.main.Main;
import org.glassfish.grizzly.http.Cookie;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.HttpStatus;

public class LoginHttpHandler extends HttpHandler {
    @Override
    public void service(Request request, Response response) throws Exception {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if(Main.isLoginPasswordValid(login, password)) {
            response.setStatus(HttpStatus.OK_200);
            String token = TokenStorage.getToken();
            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(3600);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED_401);
        }
    }
}
