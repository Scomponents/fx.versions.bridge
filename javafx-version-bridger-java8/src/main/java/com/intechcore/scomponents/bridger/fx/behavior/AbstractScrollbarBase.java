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
import javafx.scene.control.Control;
import java.util.ArrayList;

public abstract class AbstractScrollbarBase<TControl extends Control> extends BehaviorBase<TControl> {
    protected AbstractScrollbarBase(TControl node) {
        super(node, new ArrayList<>());
    }
}
