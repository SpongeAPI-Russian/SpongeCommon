package org.spongepowered.common.text.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import org.spongepowered.api.text.SelectorText;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.ClickAction;
import org.spongepowered.api.text.action.HoverAction;
import org.spongepowered.api.text.action.ShiftClickAction;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextFormat;
import org.spongepowered.api.text.format.TextStyle;
import org.spongepowered.api.text.selector.Selector;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

import javax.annotation.Nullable;

public class SelectorTextImpl extends TextImpl implements SelectorText {

    final Selector selector;

    SelectorTextImpl(Selector selector) {
        this.selector = checkNotNull(selector, "selector");
    }

    SelectorTextImpl(TextFormat format, ImmutableList<Text> children, @Nullable ClickAction<?> clickAction,
            @Nullable HoverAction<?> hoverAction, @Nullable ShiftClickAction<?> shiftClickAction,
            Selector selector) {
        super(format, children, clickAction, hoverAction, shiftClickAction);
        this.selector = checkNotNull(selector, "selector");
    }

    @Override
    public Selector getSelector() {
        return this.selector;
    }

    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SelectorTextImpl) || !super.equals(o)) {
            return false;
        }

        SelectorTextImpl that = (SelectorTextImpl) o;
        return this.selector.equals(that.selector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.selector);
    }

    @Override
    MoreObjects.ToStringHelper toStringHelper() {
        return super.toStringHelper()
                .addValue(this.selector);
    }

    public static class Builder extends TextImpl.AbstractBuilder implements SelectorText.Builder {

        private Selector selector;

        public Builder() {
            selector(selector);
        }

        Builder(Text text) {
            super(text);
        }

        Builder(SelectorText text) {
            super(text);
            this.selector = text.getSelector();
        }

        @Override
        public final Selector getSelector() {
            return this.selector;
        }

        @Override
        public Builder selector(Selector selector) {
            this.selector = checkNotNull(selector, "selector");
            return this;
        }

        @Override
        public SelectorText build() {
            return new SelectorTextImpl(
                    this.format,
                    ImmutableList.copyOf(this.children),
                    this.clickAction,
                    this.hoverAction,
                    this.shiftClickAction,
                    this.selector);
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
            return Objects.equals(this.selector, that.selector);

        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), this.selector);
        }

        @Override
        MoreObjects.ToStringHelper toStringHelper() {
            return super.toStringHelper()
                    .addValue(this.selector);
        }

        @Override
        public Builder format(TextFormat format) {
            return (Builder) super.format(format);
        }

        @Override
        public Builder color(TextColor color) {
            return (Builder) super.color(color);
        }

        @Override
        public Builder style(TextStyle... styles) {
            return (Builder) super.style(styles);
        }

        @Override
        public Builder onClick(@Nullable ClickAction<?> clickAction) {
            return (Builder) super.onClick(clickAction);
        }

        @Override
        public Builder onHover(@Nullable HoverAction<?> hoverAction) {
            return (Builder) super.onHover(hoverAction);
        }

        @Override
        public Builder onShiftClick(@Nullable ShiftClickAction<?> shiftClickAction) {
            return (Builder) super.onShiftClick(shiftClickAction);
        }

        @Override
        public Builder append(Text... children) {
            return (Builder) super.append(children);
        }

        @Override
        public Builder append(Collection<? extends Text> children) {
            return (Builder) super.append(children);
        }

        @Override
        public Builder append(Iterable<? extends Text> children) {
            return (Builder) super.append(children);
        }

        @Override
        public Builder append(Iterator<? extends Text> children) {
            return (Builder) super.append(children);
        }

        @Override
        public Builder insert(int pos, Text... children) {
            return (Builder) super.insert(pos, children);
        }

        @Override
        public Builder insert(int pos, Collection<? extends Text> children) {
            return (Builder) super.insert(pos, children);
        }

        @Override
        public Builder insert(int pos, Iterable<? extends Text> children) {
            return (Builder) super.insert(pos, children);
        }

        @Override
        public Builder insert(int pos, Iterator<? extends Text> children) {
            return (Builder) super.insert(pos, children);
        }

        @Override
        public Builder remove(Text... children) {
            return (Builder) super.remove(children);
        }

        @Override
        public Builder remove(Collection<? extends Text> children) {
            return (Builder) super.remove(children);
        }

        @Override
        public Builder remove(Iterable<? extends Text> children) {
            return (Builder) super.remove(children);
        }

        @Override
        public Builder remove(Iterator<? extends Text> children) {
            return (Builder) super.remove(children);
        }

        @Override
        public Builder removeAll() {
            return (Builder) super.removeAll();
        }

        @Override
        public Text.Builder from(Text value) {
            return new Builder(value);
        }

        @Override
        public Text.Builder reset() {
            return new Builder();
        }
    }
}
