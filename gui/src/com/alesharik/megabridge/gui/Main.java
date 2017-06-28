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

package com.alesharik.megabridge.gui;

import com.alesharik.megabridge.gui.form.LoginFormController;
import com.alesharik.webserver.api.agent.Agent;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        while(Agent.isScanning())
            Thread.sleep(1);

        new JFXPanel();
        Platform.runLater(() -> {
            new JFXPanel();

            FXMLLoader root = getLoader(Main.class.getResource("/com/alesharik/megabridge/gui/LoginForm.fxml"));
            Scene scene = new Scene(root.getRoot(), 300, 150);

            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();

            LoginFormController controller = root.getController();
            controller.setStage(stage);
        });

    }

    public static FXMLLoader getLoader(URL url) {
        FXMLLoader loader = new FXMLLoader(url);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader;
    }
}
