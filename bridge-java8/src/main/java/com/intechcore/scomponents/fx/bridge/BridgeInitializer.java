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

package com.intechcore.scomponents.fx.bridge;

/**
 * A no-operation initializer for the JavaFX Bridge on Java 8.
 * This class exists for API symmetry with the Java 11+ bridge,
 * but performs no module-related initialization as it's not applicable to Java 8.
 */
public final class BridgeInitializer {

    private BridgeInitializer() {
        // This class is not meant to be instantiated
    }

    static {
        // No module-related initialization is needed for Java 8.
        // This static block exists for symmetry and consistency.
    }

    /**
     * A no-op method to ensure the static initializer block is executed,
     * maintaining API compatibility with the Java 11+ bridge.
     */
    public static void initialize() {
        // No operation needed for Java 8.
    }
}
