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

import com.alesharik.megabridge.api.configuration.Configuration;
import com.alesharik.megabridge.api.configuration.UserType;
import com.alesharik.megabridge.api.configuration.login.LoginConfiguration;
import com.alesharik.megabridge.gui.form.model.ModuleModel;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ModulesFormController {
    private final ObservableList<ModuleModel> data = FXCollections.observableArrayList(new ModuleModel(new SimpleStringProperty("test"), new SimpleBooleanProperty(false), new Configuration() {
        @Override
        public LoginConfiguration getUserConfiguration() {
            return null;
        }

        @Override
        public UserType getType() {
            return null;
        }
    }));

    @FXML
    private TableView<ModuleModel> table;

    @FXML
    private TableColumn name;

    @FXML
    private TableColumn enabled;

    @FXML
    private TableColumn config;

    void init() {
        name.setCellFactory(new PropertyValueFactory<ModuleModel, String>("name"));

        enabled.setCellFactory(new PropertyValueFactory<ModuleModel, Boolean>("enabled"));
        config.setCellFactory(tableColumn -> new TableCell<ModuleModel, Configuration>() {
            final Button button = new Button("Edit");

            @Override
            protected void updateItem(Configuration item, boolean empty) {
                super.updateItem(item, empty);
                if(empty) {
                    setGraphic(null);
                } else {
                    button.setOnAction(actionEvent -> {
                        System.out.println("dsafasd");
                    });
                    setGraphic(button);
                }

                setText(null);
            }
        });
        table.setItems(data);
    }
}
