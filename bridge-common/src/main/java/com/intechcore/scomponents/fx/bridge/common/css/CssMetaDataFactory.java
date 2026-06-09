/*
 * Copyright (c) 2026-present, Intechcore GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

package com.intechcore.scomponents.fx.bridge.common.css;

import javafx.beans.property.Property;
import javafx.css.CssMetaData;
import javafx.css.Styleable;

import java.util.function.Function;

/**
 * Factory for creating {@link CssMetaData} instances for CSS-stylable controls.
 * <p>
 * Produces {@link PropCssMetaData} objects that bind CSS properties to
 * JavaFX property suppliers, with optional constant argument overrides.
 */
public final class CssMetaDataFactory {
    /**
     * Creates a new {@link CssMetaData} instance for the given CSS property.
     *
     * @param <TStyledControl>       the type of the stylable control
     * @param <TProperty>            the type of the property value
     * @param cssProperty            the CSS property name
     * @param property               the default property value
     * @param objectPropertySupplier function to retrieve the property from a styled control
     * @param converter              factory for the CSS style converter
     * @param consts                 constant arguments for overriding default behavior,
     *                               or {@code null} for dynamic resolution
     * @return a new {@link CssMetaData} instance
     */
    public static <TStyledControl extends Styleable, TProperty> CssMetaData<TStyledControl, TProperty> create(
            String cssProperty,
            TProperty property,
            Function<TStyledControl, Property<TProperty>> objectPropertySupplier,
            ICssConverterFactory converter,
            ConstantArgs<TProperty> consts) {

        return new PropCssMetaData<>(cssProperty, property, objectPropertySupplier, converter.getConverter(), consts);
    }
}