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

package com.intechcore.scomponents.fx.bridge.input;

import com.intechcore.scomponents.fx.bridge.common.IThreeStateBoolean;
import com.sun.javafx.scene.control.inputmap.KeyBinding;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * A named key binding that supports a disabled state and three-state
 * modifier keys (Shift, Ctrl, Alt, Meta).
 * <p>
 * When disabled (and not always-enabled), the binding returns a specificity
 * of {@code -1}, effectively ignoring the key event. Modifier key states
 * are set via {@link IThreeStateBoolean} values which map to
 * {@link com.sun.javafx.scene.control.inputmap.KeyBinding.OptionalBoolean}.
 */
public class NamedKeyBinding extends KeyBinding {

    private final String name;
    private final boolean alwaysEnabled;

    private boolean disabled = true;

    /**
     * Creates a named key binding with explicit always-enabled flag.
     *
     * @param code          the key code
     * @param type          the key event type
     * @param name          a human-readable name for this binding
     * @param alwaysEnabled if {@code true}, this binding is never disabled
     */
    public NamedKeyBinding(KeyCode code, EventType<KeyEvent> type, String name, boolean alwaysEnabled) {
        super(code, type);
        this.name = name;
        this.alwaysEnabled = alwaysEnabled;
    }

    /**
     * Creates a named key binding that is not always-enabled.
     *
     * @param code the key code
     * @param type the key event type
     * @param name a human-readable name for this binding
     */
    public NamedKeyBinding(KeyCode code, EventType<KeyEvent> type, String name) {
        this(code, type, name, false);
    }

    /**
     * Sets whether this binding is disabled.
     * <p>
     * A disabled binding that is not always-enabled returns a specificity
     * of {@code -1}, causing it to be ignored.
     *
     * @param disabled {@code true} to disable this binding
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    /**
     * Returns the human-readable name of this binding.
     *
     * @return the binding name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the Shift modifier requirement for this binding.
     *
     * @param source the three-state boolean for the Shift key
     * @return this binding, for method chaining
     */
    public NamedKeyBinding shift(IThreeStateBoolean source) {
        this.shift(convertBoolean(source));
        return this;
    }

    /**
     * Sets the Ctrl modifier requirement for this binding.
     *
     * @param source the three-state boolean for the Ctrl key
     * @return this binding, for method chaining
     */
    public NamedKeyBinding ctrl(IThreeStateBoolean source) {
        this.ctrl(convertBoolean(source));
        return this;
    }

    /**
     * Sets the Alt modifier requirement for this binding.
     *
     * @param source the three-state boolean for the Alt key
     * @return this binding, for method chaining
     */
    public NamedKeyBinding alt(IThreeStateBoolean source) {
        this.alt(convertBoolean(source));
        return this;
    }

    /**
     * Sets the Meta modifier requirement for this binding.
     *
     * @param source the three-state boolean for the Meta key
     * @return this binding, for method chaining
     */
    public NamedKeyBinding meta(IThreeStateBoolean source) {
        this.meta(convertBoolean(source));
        return this;
    }

    /**
     * Returns the specificity of this binding for the given key event.
     * <p>
     * Returns {@code -1} when the binding is disabled and not always-enabled,
     * effectively making it a no-op.
     *
     * @param keyEvent the key event to check
     * @return the specificity, or {@code -1} if disabled
     */
    @Override
    public int getSpecificity(KeyEvent keyEvent) {
        if (this.disabled && !this.alwaysEnabled) {
            return -1;
        }

        return super.getSpecificity(keyEvent);
    }

    /**
     * Converts an {@link IThreeStateBoolean} to a {@link KeyBinding.OptionalBoolean}.
     *
     * @param from the three-state boolean
     * @return the corresponding optional boolean
     */
    private static KeyBinding.OptionalBoolean convertBoolean(IThreeStateBoolean from) {
        if (from == null) {
            return KeyBinding.OptionalBoolean.ANY;
        }

        if (from.itsTrue()) {
            return KeyBinding.OptionalBoolean.TRUE;
        }

        if (from.itsFalse()) {
            return KeyBinding.OptionalBoolean.FALSE;
        }

        return KeyBinding.OptionalBoolean.ANY;
    }
}