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
import javafx.css.StyleConverter;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;

import java.util.function.Function;

/**
 * A property-based {@link CssMetaData} implementation that supports
 * {@link ConstantArgs} overrides for the {@code isSettable} check and
 * {@code initialValue}.
 * <p>
 * When constant arguments are not provided (or their fields are {@code null}),
 * the default behavior of the parent {@link CssMetaData} class is used.
 *
 * @param <TStyledControl> the type of the stylable control
 * @param <TProperty>      the type of the property value
 */
public class PropCssMetaData<TStyledControl extends Styleable, TProperty>
        extends CssMetaData<TStyledControl, TProperty> {
    private final ConstantArgs<TProperty> consts;
    private final Function<TStyledControl, Property<TProperty>> objectPropertySupplier;

    /**
     * Creates a new {@code PropCssMetaData} instance.
     *
     * @param cssProperty            the CSS property name
     * @param property               the default property value
     * @param objectPropertySupplier function to retrieve the property from a styled control
     * @param styleConverter         the CSS style converter
     * @param consts                 constant arguments, or {@code null} for dynamic resolution
     */
    public PropCssMetaData(String cssProperty, TProperty property,
                           Function<TStyledControl, Property<TProperty>> objectPropertySupplier,
                           StyleConverter<?, TProperty> styleConverter,
                           ConstantArgs<TProperty> consts) {
        super(cssProperty, styleConverter, property);
        this.objectPropertySupplier = objectPropertySupplier;
        this.consts = consts;
    }

    /**
     * Returns whether this CSS property is settable on the given node.
     * <p>
     * If {@link ConstantArgs#isSettable} is non-{@code null}, returns that value.
     * Otherwise the property must not be bound for the value to be settable.
     *
     * @param node the styled node
     * @return {@code true} if the property is settable
     */
    @Override
    public boolean isSettable(TStyledControl node) {
        if (this.consts == null || this.consts.isSettable == null) {
            Property<TProperty> propValue = this.objectPropertySupplier.apply(node);
//            return propValue == null || !propValue.isBound(); // for CssAccent
            return !propValue.isBound();
        }

        return this.consts.isSettable;
    }

    /**
     * Returns the styleable property for the given node.
     *
     * @param node the styled node
     * @return the styleable property
     */
    @Override
    public StyleableProperty<TProperty> getStyleableProperty(TStyledControl node) {
        return (StyleableObjectProperty<TProperty>) this.objectPropertySupplier.apply(node);
    }

    /**
     * Returns the initial value for the CSS property on the given styleable.
     * <p>
     * If {@link ConstantArgs#initialValue} is non-{@code null}, returns that value.
     * Otherwise delegates to the parent implementation.
     *
     * @param styleable the styled node
     * @return the initial property value
     */
    @Override
    public TProperty getInitialValue(TStyledControl styleable) {
        if (this.consts == null || this.consts.initialValue == null) {
            return super.getInitialValue(styleable);
        }

        return this.consts.initialValue;
    }
}