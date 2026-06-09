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

package com.intechcore.scomponents.fx.bridge.common.css;

/**
 * Container for constant arguments used by {@link PropCssMetaData} to
 * override default CSS metadata behavior.
 * <p>
 * Allows specifying a fixed {@code isSettable} flag and/or a fixed
 * {@code initialValue} for a CSS property, bypassing the dynamic
 * resolution that would normally occur.
 *
 * @param <TProperty> the type of the CSS property value
 */
public class ConstantArgs<TProperty> {
    /**
     * If non-{@code null}, overrides the {@code isSettable} check for the
     * associated CSS property.
     */
    public final Boolean isSettable;

    /**
     * If non-{@code null}, overrides the initial value for the associated
     * CSS property.
     */
    public final TProperty initialValue;

    /**
     * Creates a new container with the given constant arguments.
     *
     * @param isSettable   fixed settable flag, or {@code null} to use dynamic resolution
     * @param initialValue fixed initial value, or {@code null} to use dynamic resolution
     */
    public ConstantArgs(Boolean isSettable, TProperty initialValue) {
        this.isSettable = isSettable;
        this.initialValue = initialValue;
    }
}