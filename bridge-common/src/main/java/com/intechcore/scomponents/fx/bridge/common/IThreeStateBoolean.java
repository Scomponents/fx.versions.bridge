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

package com.intechcore.scomponents.fx.bridge.common;

/**
 * Represents a three-state boolean value: {@code TRUE}, {@code FALSE}, or {@code ANY}.
 * <p>
 * Used primarily for modifier key states in key bindings, where a modifier
 * (such as Shift, Ctrl, Alt, or Meta) may be required to be pressed,
 * required to be not pressed, or may be in either state.
 */
public interface IThreeStateBoolean {
    /**
     * Returns {@code true} if this state is explicitly {@code TRUE}.
     *
     * @return {@code true} if the state is {@code TRUE}
     */
    boolean itsTrue();

    /**
     * Returns {@code true} if this state is explicitly {@code FALSE}.
     *
     * @return {@code true} if the state is {@code FALSE}
     */
    boolean itsFalse();

    /**
     * Returns {@code true} if this state is {@code ANY} (neither explicitly
     * {@code TRUE} nor {@code FALSE}).
     *
     * @return {@code true} if the state is {@code ANY}
     */
    boolean itsAny();
}