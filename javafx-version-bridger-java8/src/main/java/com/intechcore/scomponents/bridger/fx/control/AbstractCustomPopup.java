package com.intechcore.scomponents.bridger.fx.control;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.PopupControl;

public abstract class AbstractCustomPopup extends PopupControl {
    protected ObservableList<Node> getChildren() {
        return this.getContent();
    }
}
