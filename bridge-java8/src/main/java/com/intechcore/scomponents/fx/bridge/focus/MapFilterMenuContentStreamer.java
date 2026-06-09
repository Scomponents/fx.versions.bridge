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

package com.intechcore.scomponents.fx.bridge.focus;

import com.sun.javafx.scene.control.skin.ContextMenuContent;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;

import java.util.stream.Stream;

/**
 * Streams focused properties of context menu item containers.
 * <p>
 * Used for keyboard navigation within context menus: walks the menu
 * item container children, filters to those matching a target item,
 * and maps them to their focused properties.
 */
public final class MapFilterMenuContentStreamer {
    /**
     * Returns a stream of focused properties for the menu item containers
     * that match the given target item.
     *
     * @param source the context menu content node
     * @param target the target menu item to match
     * @return a stream of focused properties for matching containers
     */
    public static Stream<ReadOnlyBooleanProperty> getFocusedPropertiesForParentsOf(Node source, MenuItem target) {
        return ((ContextMenuContent) source).getItemsContainer().getChildren().stream()
                .filter(ContextMenuContent.MenuItemContainer.class::isInstance)
                .map(ContextMenuContent.MenuItemContainer.class::cast)
                .filter(itemContainer -> itemContainer.getItem().equals(target))
                .map(Node::focusedProperty);
    }
}
