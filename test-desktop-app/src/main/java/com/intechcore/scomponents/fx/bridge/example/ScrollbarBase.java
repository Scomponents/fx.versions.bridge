package com.intechcore.scomponents.fx.bridge.example;

import com.intechcore.scomponents.fx.bridge.behavior.AbstractScrollbarBase;
import javafx.scene.control.Label;

public class ScrollbarBase extends AbstractScrollbarBase<Label> {
    /**
     * Creates a new AbstractScrollbarBase
     *
     * @param node the control
     */
    public ScrollbarBase(Label node) {
        super(node);
    }
}
