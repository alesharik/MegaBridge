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

package com.alesharik.megabridge.main;

import com.alesharik.megabridge.main.module.ModuleManager;
import com.alesharik.megabridge.main.module.ModuleManagerImpl;
import com.alesharik.megabridge.main.web.GetLoadedModulesHttpHandler;
import com.alesharik.megabridge.main.web.GetModulesHttpHandler;
import com.alesharik.megabridge.main.web.LoadModuleHttpHandler;
import com.alesharik.megabridge.main.web.LoginHttpHandler;
import com.alesharik.megabridge.main.web.PingHttpHandler;
import com.alesharik.megabridge.main.web.UnloadModuleHttpHandler;
import com.alesharik.webserver.api.LoginPasswordCoder;
import com.alesharik.webserver.api.agent.Agent;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.utils.Charsets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicReference;

public final class Main {
    private static final AtomicReference<String> logPass = new AtomicReference<>();

    public static void main(String[] args) throws InterruptedException, IOException {
        while(Agent.isScanning())
            Thread.sleep(1);

        File configFile = new File("./config.txt");
        if(!configFile.exists()) {
            if(!configFile.createNewFile()) {
                throw new IOException();
            }
            Files.write(configFile.toPath(), "admin:admin".getBytes(Charsets.UTF8_CHARSET));
            logPass.set(LoginPasswordCoder.encode("admin", "admin"));
        } else {
            String[] parts = new String(Files.readAllBytes(configFile.toPath()), Charsets.UTF8_CHARSET).split(":");
            logPass.set(LoginPasswordCoder.encode(parts[0], parts[1]));
        }

        ModuleManager moduleManager = new ModuleManagerImpl();
        HttpServer server = new HttpServer();
        server.addListener(new NetworkListener("grizzly", "0.0.0.0", 3456));
        ServerConfiguration serverConfiguration = server.getServerConfiguration();
        serverConfiguration.addHttpHandler(new GetModulesHttpHandler(), "/api/module/list");
        serverConfiguration.addHttpHandler(new LoadModuleHttpHandler(moduleManager), "/api/module/load");
        serverConfiguration.addHttpHandler(new UnloadModuleHttpHandler(moduleManager), "/api/module/unload");
        serverConfiguration.addHttpHandler(new GetLoadedModulesHttpHandler(moduleManager), "/api/module/loaded");

        serverConfiguration.addHttpHandler(new LoginHttpHandler(), "/login");
        serverConfiguration.addHttpHandler(new PingHttpHandler(), "/ping");

        server.start();

        System.in.read();

        server.stop();
    }

    public static boolean isLoginPasswordValid(String login, String password) {
        return logPass.get().equals(LoginPasswordCoder.encode(login, password));
    }
}
