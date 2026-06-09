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

package com.intechcore.scomponents.fx.bridge.access;

import java.lang.reflect.Field;

/**
 * A wrapper class to obtain the {@code sun.misc.Unsafe} instance.
 */
public class UnsafeWrapper {
    private static final String CLASS_NAME = "sun.misc.Unsafe";

    private static Object unsafeCache;

    /**
     * Retrieves the singleton {@code sun.misc.Unsafe} instance.
     *
     * @return The {@code Unsafe} instance, or {@code null} if it cannot be obtained.
     */
    public static Object getUnsafe() {
        if (unsafeCache == null) {
            try {
                Field theUnsafe = Class.forName(CLASS_NAME).getDeclaredField("theUnsafe");
                theUnsafe.setAccessible(true);

                unsafeCache = theUnsafe.get(null);
            } catch (Exception ex) {
                return null;
            }
        }

        return unsafeCache;
    }
}
