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

import sun.misc.Unsafe;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.Method;

public class JavaIllegalAccessHelper {

    private static Class<?> getIllegalAccessLoggerClass() {
        try {
            return Class.forName("jdk.internal.module.IllegalAccessLogger");
        } catch (ClassNotFoundException exception) {
            return null;
        }
    }

    /**
     * Executes a {@code Runnable} with illegal access warnings temporarily suppressed.
     * This is achieved by temporarily setting the {@code IllegalAccessLogger} to null.
     *
     * @param runnable The code to execute with illegal access.
     */
    public void callWithIllegalAccess(Runnable runnable) {
        try {
            Unsafe u = (Unsafe) UnsafeWrapper.getUnsafe();

            Class cls = getIllegalAccessLoggerClass();

            if (cls == null) { //Java 17
                runnable.run();
                return;
            }

            Field logger = cls.getDeclaredField("logger");

            Object illegalAccessLogger = u.getObjectVolatile(cls, u.staticFieldOffset(logger));

            synchronized (getIllegalAccessLoggerClass()) {
                u.putObjectVolatile(cls, u.staticFieldOffset(logger), null);
                runnable.run();
                u.putObjectVolatile(cls, u.staticFieldOffset(logger), illegalAccessLogger);
            }
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * Forces an {@code AccessibleObject} to be accessible, even if it is in a module that does not export its package.
     * It uses {@code sun.misc.Unsafe} to bypass the accessibility checks.
     *
     * @param o The object to make accessible.
     * @param <T> The type of the accessible object.
     * @return The accessible object.
     */
    public <T extends AccessibleObject> T forceAccessible(T o) {
        try {
            Unsafe unsafe = (Unsafe) UnsafeWrapper.getUnsafe();

            Method m = Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class);
            m.setAccessible(true);
            Field override = ((Field[]) m.invoke(AccessibleObject.class, false))[0];

            if (!o.isAccessible()) {
                try {
                    o.setAccessible(true);
                } catch (InaccessibleObjectException e) {
                    unsafe.putBoolean(o, unsafe.objectFieldOffset(override), true);
                }
            }
        } catch (Exception e) {
        }
        return o;
    }

    /**
     * Gets the value of a static field using {@code sun.misc.Unsafe}.
     *
     * @param clazz The class containing the static field.
     * @param fieldName The name of the static field.
     * @return The value of the static field.
     * @throws NoSuchFieldException if the field does not exist.
     */
    public Object getDeclaredField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Field f = clazz.getDeclaredField(fieldName);
        Unsafe u = (Unsafe) UnsafeWrapper.getUnsafe();

        return u.getObject(clazz, u.staticFieldOffset(f));
    }

    private static MethodHandles.Lookup lookup;

    /**
     * Invokes a method using {@code MethodHandles.Lookup}.
     * This allows invoking methods that would otherwise be inaccessible.
     *
     * @param method The method to invoke.
     * @param args The arguments to pass to the method.
     * @return {@code true} if the invocation was successful, {@code false} otherwise.
     */
    public boolean invokeMethod(Method method, Object... args) {
        if (lookup == null) {
            try {
                Class<?> lookupClass =
                        Class.forName("java.lang.invoke.MethodHandles$Lookup");
                Field IMPL_LOOKUP = lookupClass.getDeclaredField("IMPL_LOOKUP");

                Unsafe u = (Unsafe) UnsafeWrapper.getUnsafe();

                lookup =
                        ((MethodHandles.Lookup) u
                                .getObject(lookupClass, u.staticFieldOffset(IMPL_LOOKUP)));

                if (lookup == null) {
                    return false;
                }
            } catch (NoSuchFieldException | ClassNotFoundException e) {
                return false;
            }
        }

        try {
            if (args.length == 0) {
                lookup.unreflect(method).invoke();
            } else if (args.length == 1) {
                lookup.unreflect(method).invoke(args[0]);
            } else if (args.length == 2) {
                lookup.unreflect(method).invoke(args[0], args[1]);
            }
        } catch (IllegalAccessException exception) {
            return false;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}