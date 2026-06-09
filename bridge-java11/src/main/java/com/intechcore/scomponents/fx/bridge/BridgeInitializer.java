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

import com.intechcore.scomponents.fx.bridge.access.JavaIllegalAccessHelper;
import com.intechcore.scomponents.fx.bridge.access.PackageAuthorizer;

/**
 * Initializes the JavaFX Bridge for Java 11+, ensuring necessary module access is opened.
 * This class's static initializer block runs once when the class is loaded.
 * It is crucial to ensure this class is referenced early in the application lifecycle
 * to avoid IllegalAccessErrors.
 */
public final class BridgeInitializer {

    // Prevent instantiation
    private BridgeInitializer() {
        // This class is not meant to be instantiated
    }

    static {
        // Ensure that the PackageAuthorizer runs to open up necessary JavaFX internal modules.
        // This is crucial for JavaFX applications running on Java 11+ that use internal APIs.
        new JavaIllegalAccessHelper().callWithIllegalAccess(() -> {
            PackageAuthorizer authorizer = new PackageAuthorizer();
            authorizer.openAccess();
        });
    }

    /**
     * A no-op method to ensure the static initializer block is executed.
     * Call this method early in your application's startup.
     */
    public static void initialize() {
        // Static initializer will be run when this method is first called,
        // or when any other member of this class is accessed.
    }
}
