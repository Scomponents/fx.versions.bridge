package com.intechcore.scomponents.bridger.fx.control;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.PopupControl;
import javafx.scene.layout.Pane;

public abstract class AbstractCustomPopup extends PopupControl {
    protected final ObservableList<Node> getChildren() {
        final Parent rootNode = getScene().getRoot();
        if (rootNode instanceof Group) {
            return ((Group)rootNode).getChildren();
        }

        if (rootNode instanceof Pane) {
            return ((Pane)rootNode).getChildren();
        }

        throw new IllegalStateException("The content of the Popup can't be accessed");
    }
}
