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
package org.spongepowered.common.data.manipulator.mutable.item;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.immutable.item.ImmutableStoredEnchantmentData;
import org.spongepowered.api.data.manipulator.mutable.item.StoredEnchantmentData;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.common.data.manipulator.immutable.item.ImmutableSpongeStoredEnchantmentData;
import org.spongepowered.common.data.manipulator.mutable.common.AbstractListData;

import java.util.ArrayList;
import java.util.List;

public class SpongeStoredEnchantmentData extends AbstractListData<Enchantment, StoredEnchantmentData, ImmutableStoredEnchantmentData>
        implements StoredEnchantmentData {

    public SpongeStoredEnchantmentData(List<Enchantment> value) {
        super(StoredEnchantmentData.class, value, Keys.STORED_ENCHANTMENTS, ImmutableSpongeStoredEnchantmentData.class);
    }

    public SpongeStoredEnchantmentData() {
        this(new ArrayList<>());
    }

    @Override
    public DataContainer toContainer() {
        return DataContainer.createNew()
                .set(Keys.STORED_ENCHANTMENTS, getValue());
    }
}
