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

package com.intechcore.scomponents.fx.bridge.css;

import com.intechcore.scomponents.fx.bridge.common.css.ICssConverterFactory;
import com.sun.javafx.css.converters.*;
import javafx.css.StyleConverter;

/**
 * Enum-based factory for standard JavaFX {@link StyleConverter} instances.
 * <p>
 * Each enum constant wraps a built-in JavaFX CSS converter (such as
 * {@code PaintConverter}, {@code FontConverter}, etc.) and exposes it
 * through the {@link ICssConverterFactory} interface.
 */
public enum CssConverterFactory implements ICssConverterFactory {
    PAINT_CONVERTER(PaintConverter.getInstance()),
    EFFECT_CONVERTER(StyleConverter.getEffectConverter()),
    BOOLEAN_CONVERTER(BooleanConverter.getInstance()),
    INSETS_CONVERTER(InsetsConverter.getInstance()),
    FONT_CONVERTER(FontConverter.getInstance()),
    COLOR_CONVERTER(ColorConverter.getInstance()),
    DURATION_CONVERTER(DurationConverter.getInstance())
    ;

    private final StyleConverter<?, ?> styleConverter;

    CssConverterFactory(StyleConverter<?, ?> styleConverter) {
        this.styleConverter = styleConverter;
    }

    /**
     * Returns the style converter for this factory enum constant.
     *
     * @param <F> the source type
     * @param <T> the target type
     * @return the style converter
     */
    @Override
    public <F, T> StyleConverter<F, T> getConverter() {
        return (StyleConverter<F, T>) this.styleConverter;
    }
}
