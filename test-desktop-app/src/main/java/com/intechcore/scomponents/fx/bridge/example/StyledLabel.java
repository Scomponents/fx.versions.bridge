package com.intechcore.scomponents.fx.bridge.example;


import com.intechcore.scomponents.fx.bridge.common.css.CssMetaDataFactory;
import com.intechcore.scomponents.fx.bridge.css.CssConverterFactory;
import javafx.beans.property.ObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A custom {@link Label} with a stylable {@code -fx-highlight-color} property.
 */
public class StyledLabel extends Label {
    private static final Color DEFAULT_HIGHLIGHT_COLOR = Color.BLACK;
    private static final CssMetaData<StyledLabel, Color> HIGHLIGHT_COLOR =
            CssMetaDataFactory.create("-fx-highlight-color",
                            DEFAULT_HIGHLIGHT_COLOR,
                            StyledLabel::highlightColorProperty,
                            CssConverterFactory.COLOR_CONVERTER,
                            null);

    /**
     * Creates a new StyledLabel.
     *
     * @param text the text for the label
     */
    public StyledLabel(String text) {
        super(text);
    }

    /**
     * The highlight color property of the label.
     * This can be styled via CSS using {@code -fx-highlight-color}.
     *
     * @return the highlight color property
     */
    public final ObjectProperty<Color> highlightColorProperty() {
        return this.highlightColor;
    }

    private final StyleableObjectProperty<Color> highlightColor =
            new StyleableObjectProperty<Color>(DEFAULT_HIGHLIGHT_COLOR) {

                @Override
                public CssMetaData<StyledLabel, Color> getCssMetaData() {
                    return StyledLabel.HIGHLIGHT_COLOR;
                }

                @Override
                public Object getBean() {
                    return StyledLabel.this;
                }

                @Override
                public String getName() {
                    return "highlightColor";
                }

                @Override
                public void invalidated() {
                    // code here for reacting to changes
                }
            };

    private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

    static {
        final List<CssMetaData<? extends Styleable, ?>> styleables =
                new ArrayList<>(Parent.getClassCssMetaData());
        styleables.add(HIGHLIGHT_COLOR);
        STYLEABLES = Collections.unmodifiableList(styleables);
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return STYLEABLES;
    }
}
