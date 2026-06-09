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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Opens access to internal JavaFX packages required by the bridge.
 * This is necessary for Java 9+ due to the module system.
 */
public class PackageAuthorizer {
    private static Class<?> moduleClassCache;
    private static Method addExportsToAllUnnamed0MethodCache;

    private final JavaIllegalAccessHelper illegalAccessHelper = new JavaIllegalAccessHelper();

    /**
     * Iterates through the {@link ExportsConfigs} and opens access to the specified packages.
     */
    public void openAccess() {
        for (final ExportsConfigs exportsConfig : ExportsConfigs.values()) {
            Module sourceModule = exportsConfig.sourceModule;

            for (final String targetPackage : exportsConfig.packagesToOpenAccess) {
                // Then try to export (for general visibility and reflective access)
                this.authorize(sourceModule, targetPackage, getAddExportsMethod());
            }
        }
    }

    private boolean authorize(Module sourceModule, String targetPackage, Method method) {
        if (method == null) {
            return false;
        }
        try {
            method.invoke(null, sourceModule, targetPackage);
            return true;
        } catch (IllegalAccessException | InvocationTargetException e) {
            try {
                this.illegalAccessHelper.invokeMethod(method, sourceModule, targetPackage);
                return true;
            } catch (Throwable ex) {
            }
        }
        return false;
    }

    private static Class<?> getModuleClass() {
        if (moduleClassCache == null) {
            String javaLangModuleName = Module.class.getName();
            moduleClassCache = Class.forName(Module.class.getModule(), javaLangModuleName);
        }
        return moduleClassCache;
    }

    private static Method getAddExportsMethod() {
        if (addExportsToAllUnnamed0MethodCache == null) {
            try {
                addExportsToAllUnnamed0MethodCache = new JavaIllegalAccessHelper().forceAccessible(
                        getModuleClass().getDeclaredMethod(
                                "addExportsToAllUnnamed0",
                                Module.class,
                                String.class)
                );
            } catch (NoSuchMethodException e) {
                return null;
            }
        }
        return addExportsToAllUnnamed0MethodCache;
    }

    /**
     * Defines the modules and packages that need to be opened for the bridge to work.
     */
    enum ExportsConfigs {
        JAVAFX_CONTROLS(javafx.scene.control.Control.class.getModule()
                ,"com.sun.javafx.scene.control.behavior"
                ,"com.sun.javafx.scene.control" // ContextMenuContent
                ,"com.sun.javafx.scene.control.inputmap"
        ),
        JAVAFX_BASE(javafx.beans.property.BooleanProperty.class.getModule()
                ,"com.sun.javafx.runtime"
                ,"com.sun.javafx"
//                ,"com.sun.javafx.reflect"
//                ,"com.sun.javafx.beans"
        ),
        JAVAFX_GRAPHICS(javafx.animation.Animation.class.getModule()
                ,"com.sun.javafx.util"
//                ,"com.sun.javafx.application"
                ,"com.sun.glass.ui" // FxScaleGetter
        ),
//        JAVA_XML(javax.xml.XMLConstants.class.getModule(),
//              "com.sun.org.apache.xerces.internal.dom"
//        ),
//        JAVA_DESKTOP(java.awt.Graphics2D.class.getModule(),
//              "sun.font"
//        )
        ;

        public final Module sourceModule;
        public final String[] packagesToOpenAccess;

        /**
         * @param sourceModule The module from which packages are exported.
         * @param packagesToOpenAccess The packages to be exported.
         */
        ExportsConfigs(Module sourceModule, String... packagesToOpenAccess) {
            this.sourceModule = sourceModule;
            this.packagesToOpenAccess = packagesToOpenAccess;
        }
    }
}
