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

package com.alesharik.megabridge.gui.form;

import com.alesharik.megabridge.gui.Main;
import com.alesharik.megabridge.gui.net.ServerManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;

public class LoginFormController {
    @Setter
    private Stage stage;

    @FXML
    private TextField serverField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField loginField;

    @FXML
    public void onOkButtonClick() {
        if(ServerManager.isValid(serverField.getText()) && ServerManager.login(serverField.getText(), loginField.getText(), passwordField.getText())) {
            FXMLLoader loader = Main.getLoader(Main.class.getClassLoader().getResource("com/alesharik/megabridge/gui/MainForm.fxml"));
            Scene scene = new Scene(loader.getRoot(), 600, 400);
            stage.close();

            Stage stage = new Stage();
            stage.setTitle("Main");
            stage.setScene(scene);
            stage.show();

            MainFormController controller = loader.getController();
            controller.setStage(stage);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Couldn't connect to server!");
            alert.setContentText("Couldn't connect to server!");

            alert.showAndWait();
        }
    }

    @FXML
    public void onCancelButtonClick() {
        stage.close();
    }
}
