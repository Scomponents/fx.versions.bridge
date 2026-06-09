package com.intechcore.scomponents.fx.bridge.example;

import com.intechcore.scomponents.fx.bridge.BridgeInitializer;
import com.intechcore.scomponents.fx.bridge.control.CustomPopup;
import com.intechcore.scomponents.fx.bridge.focus.MapFilterMenuContentStreamer;
import com.intechcore.scomponents.fx.bridge.input.KeyCodeSupplier;
import com.intechcore.scomponents.fx.bridge.input.NamedKeyBinding;
import com.intechcore.scomponents.fx.bridge.control.ColorPickerSkinProxy;
import com.intechcore.scomponents.fx.bridge.screen.FxScaleGetter;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.stream.Stream;

public class TestDesktopApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        BridgeInitializer.initialize();

        StyledLabel javaVersionLabel = new StyledLabel("JavaFX Bridge Test — " + System.getProperty("java.version"));

        ScrollbarBase scrollbarBase = new ScrollbarBase(javaVersionLabel);

        Label highlightColorLabel = new Label(javaVersionLabel.highlightColorProperty().getValue().toString());

        javaVersionLabel.highlightColorProperty().addListener((observable, oldValue, newValue) -> {
            highlightColorLabel.setText(newValue.toString());
        });

        VBox root = new VBox(5, scrollbarBase.getNode(), highlightColorLabel);
        root.setStyle("-fx-padding: 16; -fx-background-color: #f8f8f8;");

        javafx.scene.control.Button popupBtn = new javafx.scene.control.Button("Show CustomPopup");
        CustomPopup[] customPopup = new CustomPopup[] {null};
        popupBtn.setOnAction(e -> {
            if (customPopup[0] != null) {
                customPopup[0].hide();
                customPopup[0] = null;
                javaVersionLabel.setStyle("-fx-highlight-color: #00FF00;");
            } else {
                javaVersionLabel.setStyle("-fx-highlight-color: #FF00FF;");
                customPopup[0] = new CustomPopup();
                customPopup[0].getChildren().add(new Label("Custom Popup. Press again to close"));
                customPopup[0].show(primaryStage);
            }
        });

        CheckMenuItem checkMenuItem = new CheckMenuItem("Fx Scale - " + FxScaleGetter.getFxScaleX());

        MyContextMenu contextMenu = new MyContextMenu(checkMenuItem);
        contextMenu.createDefaultSkinPublic();

        root.setOnContextMenuRequested(event -> {
            Stream<ReadOnlyBooleanProperty> parents = MapFilterMenuContentStreamer.getFocusedPropertiesForParentsOf(
                    contextMenu.getSkin().getNode(), checkMenuItem);

            System.out.println("parents count: " + parents.count());

            contextMenu.show(root, event.getScreenX(), event.getScreenY());
        });

        root.getChildren().add(popupBtn);

        NamedKeyBinding keyBinding = new NamedKeyBinding(KeyCode.A, KeyEvent.KEY_PRESSED, "A-Pressed");

        System.out.println("KeyCode - " + KeyCodeSupplier.getKeyCode(keyBinding.getCode()));

        ColorPicker colorPicker = new ColorPicker(Color.CORNFLOWERBLUE);
        colorPicker.setSkin(new ColorPickerSkinProxy(colorPicker));
        root.getChildren().add(colorPicker);

        Scene scene = new Scene(root, 520, 620);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static class MyContextMenu extends ContextMenu {
        public MyContextMenu(CheckMenuItem checkMenuItem) {
            super(checkMenuItem, new SeparatorMenuItem(), new MenuItem("Item"));
        }

        public void createDefaultSkinPublic() {
            this.setSkin(this.createDefaultSkin());
        }
    }
}
