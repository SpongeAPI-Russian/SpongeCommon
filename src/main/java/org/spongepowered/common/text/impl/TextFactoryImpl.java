/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.common.text.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.api.scoreboard.Score;
import org.spongepowered.api.text.LiteralText;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextFactory;
import org.spongepowered.api.text.TextRepresentable;
import org.spongepowered.api.text.action.ClickAction;
import org.spongepowered.api.text.action.HoverAction;
import org.spongepowered.api.text.action.ShiftClickAction;
import org.spongepowered.api.text.action.TextAction;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextFormat;
import org.spongepowered.api.text.format.TextStyle;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.text.selector.Selector;
import org.spongepowered.api.text.translation.Translatable;
import org.spongepowered.api.text.translation.Translation;

import java.util.Iterator;

public final class TextFactoryImpl implements TextFactory {

    @Override
    public Text empty() {
        return LiteralText.EMPTY;
    }

    @Override
    public LiteralText literal(char content) {
        if (content == TextImpl.NEW_LINE_CHAR) {
            return TextImpl.NEW_LINE;
        }
        return Text.builder(String.valueOf(content)).build();
    }

    @Override
    public LiteralText literal(String content) {
        if (checkNotNull(content, "content").isEmpty()) {
            return LiteralText.EMPTY;
        } else if (content.equals(TextImpl.NEW_LINE_STRING)) {
            return TextImpl.NEW_LINE;
        } else {
            return Text.builder(content).build();
        }
    }

    @Override
    public Text of(Object... objects) {
        // Shortcut for lonely TextRepresentables
        if (objects.length == 1 && objects[0] instanceof TextRepresentable) {
            return ((TextRepresentable) objects[0]).toText();
        }

        final Text.Builder builder = Text.builder();
        TextFormat format = TextFormat.NONE;
        HoverAction<?> hoverAction = null;
        ClickAction<?> clickAction = null;
        ShiftClickAction<?> shiftClickAction = null;
        boolean changedFormat = false;

        for (Object obj : objects) {
            // Text formatting + actions
            if (obj instanceof TextFormat) {
                changedFormat = true;
                format = (TextFormat) obj;
            } else if (obj instanceof TextColor) {
                changedFormat = true;
                format = format.color((TextColor) obj);
            } else if (obj instanceof TextStyle) {
                changedFormat = true;
                format = format.style(obj.equals(TextStyles.RESET) ? TextStyles.NONE : format.getStyle().and((TextStyle) obj));
            } else if (obj instanceof TextAction) {
                changedFormat = true;
                if (obj instanceof HoverAction) {
                    hoverAction = (HoverAction<?>) obj;
                } else if (obj instanceof ClickAction) {
                    clickAction = (ClickAction<?>) obj;
                } else if (obj instanceof ShiftClickAction) {
                    shiftClickAction = (ShiftClickAction<?>) obj;
                } else {
                    // Unsupported TextAction
                }

            } else if (obj instanceof TextRepresentable) {
                // Special content
                changedFormat = false;
                Text.Builder childBuilder = ((TextRepresentable) obj).toText().toBuilder();

                // Merge format (existing format has priority)
                childBuilder.format(format.merge(childBuilder.getFormat()));

                // Overwrite text actions if *NOT* present
                if (!childBuilder.getClickAction().isPresent()) {
                    childBuilder.onClick(clickAction);
                }
                if (!childBuilder.getHoverAction().isPresent()) {
                    childBuilder.onHover(hoverAction);
                }
                if (!childBuilder.getShiftClickAction().isPresent()) {
                    childBuilder.onShiftClick(shiftClickAction);
                }

                builder.append(childBuilder.build());

            } else {
                // Simple content
                changedFormat = false;
                Text.Builder childBuilder;

                if (obj instanceof String) {
                    childBuilder = Text.builder((String) obj);
                } else if (obj instanceof Translation) {
                    childBuilder = Text.builder((Translation) obj);
                } else if (obj instanceof Translatable) {
                    childBuilder = Text.builder(((Translatable) obj).getTranslation());
                } else if (obj instanceof Selector) {
                    childBuilder = Text.builder((Selector) obj);
                } else if (obj instanceof Score) {
                    childBuilder = Text.builder((Score) obj);
                } else {
                    childBuilder = Text.builder(String.valueOf(obj));
                }

                if (hoverAction != null) {
                    childBuilder.onHover(hoverAction);
                }
                if (clickAction != null) {
                    childBuilder.onClick(clickAction);
                }
                if (shiftClickAction != null) {
                    childBuilder.onShiftClick(shiftClickAction);
                }

                builder.append(childBuilder.format(format).build());
            }
        }

        if (changedFormat) {
            // Did the formatting change without being applied to something?
            // Then just append an empty text with that formatting
            final Text.Builder childBuilder = Text.builder();
            if (hoverAction != null) {
                childBuilder.onHover(hoverAction);
            }
            if (clickAction != null) {
                childBuilder.onClick(clickAction);
            }
            if (shiftClickAction != null) {
                childBuilder.onShiftClick(shiftClickAction);
            }
            builder.append(childBuilder.format(format).build());
        }

        if (builder.getChildren().size() == 1) {
            // Single content, reduce Text depth
            return builder.getChildren().get(0);
        }

        return builder.build();
    }

    @Override
    public Text joinWith(final Text separator, final Text... texts) {
        switch (texts.length) {
            case 0:
                return LiteralTextImpl.EMPTY;
            case 1:
                return texts[0];
            default:
                Text.Builder builder = Text.builder();
                boolean appendSeparator = false;
                for (Text text : texts) {
                    if (appendSeparator) {
                        builder.append(separator);
                    } else {
                        appendSeparator = true;
                    }

                    builder.append(text);
                }

                return builder.build();
        }
    }

    @Override
    public Text joinWith(final Text separator, final Iterator<? extends Text> texts) {
        if (!texts.hasNext()) {
            return LiteralTextImpl.EMPTY;
        }

        Text first = texts.next();
        if (!texts.hasNext()) {
            return first;
        }

        Text.Builder builder = Text.builder().append(first);
        do {
            builder.append(separator);
            builder.append(texts.next());
        }
        while (texts.hasNext());

        return builder.build();
    }
}
