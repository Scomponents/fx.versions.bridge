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

import javafx.css.StyleConverter;

/**
 * Factory interface for providing a {@link StyleConverter} instance.
 * <p>
 * Implementations supply a type-safe CSS style converter that can transform
 * parsed CSS values into target property types.
 */
public interface ICssConverterFactory {
    /**
     * Returns the {@link StyleConverter} managed by this factory.
     *
     * @param <F> the source type
     * @param <T> the target type
     * @return the style converter
     */
    <F, T> StyleConverter<F, T> getConverter();
}