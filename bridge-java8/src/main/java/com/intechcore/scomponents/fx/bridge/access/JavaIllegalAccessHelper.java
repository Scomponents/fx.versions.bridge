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

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * A helper class for illegal access operations in Java 8.
 * This class provides methods to bypass access restrictions.
 */
public class JavaIllegalAccessHelper {
    /**
     * Executes a {@code Runnable} with illegal access warnings temporarily suppressed.
     * In Java 8, this method simply executes the runnable.
     *
     * @param runnable The code to execute with illegal access.
     */
    public void callWithIllegalAccess(Runnable runnable) {
        runnable.run();
    }

    /**
     * Forces an {@code AccessibleObject} to be accessible.
     *
     * @param o The object to make accessible.
     * @param <T> The type of the accessible object.
     * @return The accessible object.
     */
    public <T extends AccessibleObject> T forceAccessible(T o) {
        o.setAccessible(true);
        return o;
    }

    /**
     * Invokes a method using reflection.
     *
     * @param method The method to invoke.
     * @param args The arguments to pass to the method.
     * @return {@code true} if the invocation was successful, {@code false} otherwise.
     */
    public boolean invokeMethod(Method method, Object... args) {
        try {
            method.invoke(null, args);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * Gets the value of a static field using reflection.
     *
     * @param clazz The class containing the static field.
     * @param fieldName The name of the static field.
     * @return The value of the static field.
     * @throws NoSuchFieldException if the field does not exist.
     */
    public Object getDeclaredField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Field f = this.forceAccessible(clazz.getDeclaredField(fieldName));

        try {
            return f.get(null);
        } catch (Exception e) {
            return null;
        }
    }
}
