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

package com.alesharik.megabridge.gui.net;

import lombok.experimental.UtilityClass;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@UtilityClass
public final class ServerManager {
    private static final AtomicReference<CloseableHttpClient> httpClientRef = new AtomicReference<>();

    static {
        httpClientRef.set(HttpClients.createDefault());
    }

    public static boolean isValid(String server) {
        HttpGet get = new HttpGet("http://" + server + "/ping");
        try {
            HttpResponse response = httpClientRef.get().execute(get);
            String text = IOUtils.toString(response.getEntity().getContent());
            response.getEntity().getContent().close();
            return "pong".equals(text);
        } catch (IOException e) {
            return false;
        }
    }
}
