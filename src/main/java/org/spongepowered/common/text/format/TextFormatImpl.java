package org.spongepowered.common.text.format;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.MoreObjects;
import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextFormat;
import org.spongepowered.api.text.format.TextStyle;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.text.serializer.TextFormatConfigSerializer;

import java.util.Objects;

public final class TextFormatImpl implements TextFormat {

    static {
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(TextFormat.class), new TextFormatConfigSerializer());
    }

    /**
     * An empty {@link TextFormat} with no {@link TextColor} and no {@link TextStyle}.
     */
    public static final TextFormat NONE = new TextFormatImpl(TextColors.NONE, TextStyles.NONE);
    private final TextColor color;
    private final TextStyle style;

    public TextFormatImpl(final TextColor color, final TextStyle style) {
        this.color = checkNotNull(color, "color");
        this.style = checkNotNull(style, "style");
    }

    @Override
    public TextColor getColor() {
        return this.color;
    }

    @Override
    public TextStyle getStyle() {
        return this.style;
    }

    @Override
    public TextFormat color(final TextColor color) {
        return new TextFormatImpl(color, this.style);
    }

    @Override
    public TextFormat style(final TextStyle style) {
        return new TextFormatImpl(this.color, style);
    }

    @Override
    public TextFormat merge(final TextFormat format) {
        TextColor color = format.getColor();
        // If the given format's color is NONE use this ones
        if (color == TextColors.NONE) {
            color = this.color;
            // If the given format's color is RESET use NONE
        } else if (color == TextColors.RESET) {
            color = TextColors.NONE;
        }
        return new TextFormatImpl(color, this.style.and(format.getStyle()));
    }

    @Override
    public boolean isEmpty() {
        return this.color == TextColors.NONE && this.style.isEmpty();
    }

    @Override
    public void applyTo(final Text.Builder builder) {
        builder.format(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TextFormatImpl)) {
            return false;
        }

        final TextFormatImpl that = (TextFormatImpl) o;
        return this.color.equals(that.color) && this.style.equals(that.style);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.color, this.style);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("color", this.color)
                .add("style", this.style)
                .toString();
    }
}
