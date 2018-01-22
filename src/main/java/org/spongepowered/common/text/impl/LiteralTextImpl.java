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

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import org.spongepowered.api.text.LiteralText;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.ClickAction;
import org.spongepowered.api.text.action.HoverAction;
import org.spongepowered.api.text.action.ShiftClickAction;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextFormat;
import org.spongepowered.api.text.format.TextStyle;

import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Nullable;

public class LiteralTextImpl extends TextImpl implements LiteralText {

    static final LiteralText EMPTY = new LiteralTextImpl("");

    final String content;

    LiteralTextImpl(String content) {
        this.content = checkNotNull(content, "content");
    }

    LiteralTextImpl(TextFormat format, ImmutableList<Text> children, @Nullable ClickAction<?> clickAction,
            @Nullable HoverAction<?> hoverAction, @Nullable ShiftClickAction<?> shiftClickAction, String content) {
        super(format, children, clickAction, hoverAction, shiftClickAction);
        this.content = checkNotNull(content, "content");
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Represents a {@link Text.Builder} creating immutable {@link LiteralText}
     * instances.
     *
     * @see LiteralText
     */
    public static class Builder extends AbstractBuilder implements LiteralText.Builder {

        private String content;

        Builder() {
            this("");
        }

        Builder(String content) {
            this.content(content);
        }

        Builder(Text text) {
            super(text);
        }

        Builder(LiteralText text) {
            super(text);
            this.content = text.getContent();
        }

        @Override
        public final String getContent() {
            return this.content;
        }

        @Override
        public LiteralText.Builder content(String content) {
            this.content = checkNotNull(content, "content");
            return this;
        }

        @Override
        public LiteralText build() {
            // Special case for empty builder
            if (this.format.isEmpty() && this.children.isEmpty() && this.clickAction == null && this.hoverAction == null
                    && this.shiftClickAction == null) {
                if (this.content.isEmpty()) {
                    return EMPTY;
                } else if (this.content.equals(NEW_LINE_STRING)) {
                    return NEW_LINE;
                }
            }

            return new LiteralTextImpl(
                    this.format,
                    ImmutableList.copyOf(this.children),
                    this.clickAction,
                    this.hoverAction,
                    this.shiftClickAction,
                    this.content);
        }

        @Override
        public boolean equals(@Nullable Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Builder) || !super.equals(o)) {
                return false;
            }

            Builder that = (Builder) o;
            return Objects.equal(this.content, that.content);

        }

        @Override
        public int hashCode() {
            return Objects.hashCode(super.hashCode(), this.content);
        }

        @Override
        MoreObjects.ToStringHelper toStringHelper() {
            return super.toStringHelper()
                    .addValue(this.content);
        }

        @Override
        public LiteralText.Builder format(TextFormat format) {
            return (LiteralText.Builder) super.format(format);
        }

        @Override
        public LiteralText.Builder color(TextColor color) {
            return (LiteralText.Builder) super.color(color);
        }

        @Override
        public LiteralText.Builder style(TextStyle... styles) {
            return (LiteralText.Builder) super.style(styles);
        }

        @Override
        public LiteralText.Builder onClick(@Nullable ClickAction<?> clickAction) {
            return (LiteralText.Builder) super.onClick(clickAction);
        }

        @Override
        public LiteralText.Builder onHover(@Nullable HoverAction<?> hoverAction) {
            return (LiteralText.Builder) super.onHover(hoverAction);
        }

        @Override
        public LiteralText.Builder onShiftClick(@Nullable ShiftClickAction<?> shiftClickAction) {
            return (LiteralText.Builder) super.onShiftClick(shiftClickAction);
        }

        @Override
        public LiteralText.Builder append(Text... children) {
            return (LiteralText.Builder) super.append(children);
        }

        @Override
        public LiteralText.Builder append(Collection<? extends Text> children) {
            return (LiteralText.Builder) super.append(children);
        }

        @Override
        public LiteralText.Builder append(Iterable<? extends Text> children) {
            return (LiteralText.Builder) super.append(children);
        }

        @Override
        public LiteralText.Builder append(Iterator<? extends Text> children) {
            return (LiteralText.Builder) super.append(children);
        }

        @Override
        public LiteralText.Builder insert(int pos, Text... children) {
            return (LiteralText.Builder) super.insert(pos, children);
        }

        @Override
        public LiteralText.Builder insert(int pos, Collection<? extends Text> children) {
            return (LiteralText.Builder) super.insert(pos, children);
        }

        @Override
        public LiteralText.Builder insert(int pos, Iterable<? extends Text> children) {
            return (LiteralText.Builder) super.insert(pos, children);
        }

        @Override
        public LiteralText.Builder insert(int pos, Iterator<? extends Text> children) {
            return (LiteralText.Builder) super.insert(pos, children);
        }

        @Override
        public LiteralText.Builder remove(Text... children) {
            return (LiteralText.Builder) super.remove(children);
        }

        @Override
        public LiteralText.Builder remove(Collection<? extends Text> children) {
            return (LiteralText.Builder) super.remove(children);
        }

        @Override
        public LiteralText.Builder remove(Iterable<? extends Text> children) {
            return (LiteralText.Builder) super.remove(children);
        }

        @Override
        public LiteralText.Builder remove(Iterator<? extends Text> children) {
            return (LiteralText.Builder) super.remove(children);
        }

        @Override
        public LiteralText.Builder removeAll() {
            return (LiteralText.Builder) super.removeAll();
        }

        @Override
        public LiteralText.Builder from(final Text value) {
            return new Builder(value);
        }

        @Override
        public Text.Builder reset() {
            return new Builder();
        }
    }
}
