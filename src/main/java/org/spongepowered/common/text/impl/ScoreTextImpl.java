package org.spongepowered.common.text.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import org.spongepowered.api.scoreboard.Score;
import org.spongepowered.api.text.ScoreText;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.ClickAction;
import org.spongepowered.api.text.action.HoverAction;
import org.spongepowered.api.text.action.ShiftClickAction;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextFormat;
import org.spongepowered.api.text.format.TextStyle;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import javax.annotation.Nullable;

public final class ScoreTextImpl extends TextImpl implements ScoreText {

    final Score score;
    final Optional<String> override;

    ScoreTextImpl(Score score) {
        this.score = checkNotNull(score, "score");
        this.override = Optional.empty();
    }

    /**
     * Constructs a new immutable {@link ScoreText} for the given score with the
     * specified formatting and text actions applied.
     *
     * @param format The format of the text
     * @param children The immutable list of children of the text
     * @param clickAction The click action of the text, or {@code null} for none
     * @param hoverAction The hover action of the text, or {@code null} for none
     * @param shiftClickAction The shift click action of the text, or
     *        {@code null} for none
     * @param score The score of the text
     * @param override The text to override the score with, or {@code null} for
     *        none
     */
    ScoreTextImpl(TextFormat format, ImmutableList<Text> children, @Nullable ClickAction<?> clickAction,
            @Nullable HoverAction<?> hoverAction, @Nullable ShiftClickAction<?> shiftClickAction,
            Score score, @Nullable String override) {
        super(format, children, clickAction, hoverAction, shiftClickAction);
        this.score = checkNotNull(score, "score");
        this.override = Optional.ofNullable(override);
    }

    @Override
    public Score getScore() {
        return this.score;
    }

    @Override
    public Optional<String> getOverride() {
        return this.override;
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
        if (!(o instanceof ScoreTextImpl) || !super.equals(o)) {
            return false;
        }

        ScoreTextImpl that = (ScoreTextImpl) o;
        return this.score.equals(that.score) && this.override.equals(that.override);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), this.score, this.override);
    }

    @Override
    MoreObjects.ToStringHelper toStringHelper() {
        return super.toStringHelper()
                .addValue(this.score)
                .add("override", this.override.orElse(null));
    }

    public static final class Builder extends TextImpl.AbstractBuilder implements ScoreText.Builder {

        private Score score;
        @Nullable private String override;

        public Builder() {
        }

        Builder(Text text) {
            super(text);
        }

        Builder(ScoreText text) {
            super(text);
            this.score = text.getScore();
            this.override = text.getOverride().orElse(null);
        }

        @Override
        public final Score getScore() {
            return this.score;
        }

        @Override
        public ScoreText.Builder score(Score score) {
            this.score = checkNotNull(score, "score");
            return this;
        }

        @Override
        public final Optional<String> getOverride() {
            return Optional.ofNullable(this.override);
        }

        @Override
        public ScoreText.Builder override(@Nullable String override) {
            this.override = override;
            return this;
        }

        @Override
        public ScoreText build() {
            return new ScoreTextImpl(
                    this.format,
                    ImmutableList.copyOf(this.children),
                    this.clickAction,
                    this.hoverAction,
                    this.shiftClickAction,
                    this.score,
                    this.override);
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
            return Objects.equal(this.score, that.score)
                    && Objects.equal(this.override, that.override);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(super.hashCode(), this.score, this.override);
        }

        @Override
        MoreObjects.ToStringHelper toStringHelper() {
            return super.toStringHelper()
                    .addValue(this.score)
                    .add("override", this.override);
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
        public Builder from(Text value) {
            return new Builder(value);
        }

        @Override
        public Builder reset() {
            return new Builder();
        }
    }
}
