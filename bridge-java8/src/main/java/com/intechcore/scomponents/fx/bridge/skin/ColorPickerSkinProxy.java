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
package com.intechcore.scomponents.fx.bridge.skin;

import com.sun.javafx.scene.control.skin.ColorPickerSkin;
import javafx.scene.control.ColorPicker;

/**
 * A proxy for the color picker skin
 */
public class ColorPickerSkinProxy extends ColorPickerSkin {
    /**
     * Creates a new color picker skin proxy
     *
     * @param colorPicker the color picker
     */
    public ColorPickerSkinProxy(ColorPicker colorPicker) {
        super(colorPicker);
    }
}