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

package com.intechcore.scomponents.fx.bridge.font;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Bridge for loading fonts in JavaFX
 */
public class FxFontLoaderBridge {
    /**
     * Loads fonts from a file
     *
     * @param fontFile the font file
     * @param size the font size
     * @return an array of loaded fonts
     */
    public javafx.scene.text.Font[] loadFontsFromFile(Path fontFile, Double size) {
        try (final InputStream inputStream = Files.newInputStream(fontFile)) {
            javafx.scene.text.Font result = javafx.scene.text.Font.loadFont(inputStream, size);
            return convertToArray(result);
        } catch (IOException ignored) {
        }

        return new javafx.scene.text.Font[] {};
    }

    /**
     * Loads fonts from a stream
     *
     * @param stream the input stream
     * @param size the font size
     * @return an array of loaded fonts
     */
    public javafx.scene.text.Font[] loadFontsFromStream(final InputStream stream, final Double size) {
        javafx.scene.text.Font result = javafx.scene.text.Font.loadFont(stream, size);
        return convertToArray(result);
    }

    /**
     * Converts a font to an array of fonts
     *
     * @param result the font to convert
     * @return an array of fonts
     */
    private static javafx.scene.text.Font[] convertToArray(javafx.scene.text.Font result) {
        if (result != null) {
            return new javafx.scene.text.Font[] { result };
        }

        return new javafx.scene.text.Font[] {};
    }
}
