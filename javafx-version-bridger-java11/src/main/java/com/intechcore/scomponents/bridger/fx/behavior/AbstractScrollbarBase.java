/*******************************************************************************
 *  Copyright (C) 2008-2022 Intechcore GmbH - All Rights Reserved
 *
 *  This file is part of SComponents project.
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *
 *  Proprietary and confidential
 *
 *  Written by Intechcore GmbH <info@intechcore.com>
 ******************************************************************************/

package com.intechcore.scomponents.bridger.fx.behavior;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.inputmap.InputMap;
import javafx.scene.Node;

public abstract class AbstractScrollbarBase<TControl extends Node> extends BehaviorBase<TControl> {
    protected AbstractScrollbarBase(TControl node) {
        super(node);
    }

    @Override
    public InputMap<TControl> getInputMap() {
        return null;
    }

    protected TControl getControl() {
        return this.getNode();
    }
}
