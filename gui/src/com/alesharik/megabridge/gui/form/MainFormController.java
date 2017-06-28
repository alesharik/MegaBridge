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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;

public class MainFormController {
    private final FXMLLoader loader;
    @Setter
    private Stage stage;


    public MainFormController() {
        loader = Main.getLoader(Main.class.getClassLoader().getResource("com/alesharik/megabridge/gui/ModulesForm.fxml"));
    }

    @FXML
    public void openModulesForm() {
        Scene scene = new Scene(loader.getRoot(), 500, 500);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        ModulesFormController controller = loader.getController();
        controller.init();
    }
}
