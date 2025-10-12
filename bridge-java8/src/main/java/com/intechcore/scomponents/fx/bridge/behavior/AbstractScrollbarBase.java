/*
 * Copyright 2025 Intechcore GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intechcore.scomponents.fx.bridge.behavior;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import javafx.scene.control.Control;
import java.util.ArrayList;

/**
 * Abstract base class for scrollbar behavior
 *
 * @param <TControl> the type of the control
 */
public abstract class AbstractScrollbarBase<TControl extends Control> extends BehaviorBase<TControl> {
    /**
     * Creates a new AbstractScrollbarBase
     *
     * @param node the control
     */
    protected AbstractScrollbarBase(TControl node) {
        super(node, new ArrayList<>());
    }
}
