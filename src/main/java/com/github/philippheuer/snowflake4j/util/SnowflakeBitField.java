package com.github.philippheuer.snowflake4j.util;

import org.apache.commons.lang3.BitField;

public class SnowflakeBitField extends BitField {

    private final long mask;
    private final long shift_count;

    /**
     * <p>Creates a BitField instance.</p>
     *
     * @param mask the mask specifying which bits apply to this
     *  BitField. Bits that are set in this mask are the bits
     *  that this BitField operates on
     */
    public SnowflakeBitField(final Long mask) {
        super(mask.intValue());
        this.mask = mask;
        this.shift_count = mask == 0 ? 0 : Long.numberOfTrailingZeros(mask);
    }

    /**
     * <p>Obtains the long value for the specified BitField, appropriately shifted right.</p>
     *
     * @see #setLongValue(long,long)
     * @param holder the long data containing the bits we're interested in
     * @return the selected bits, shifted right appropriately
     */
    public long getLongValue(final long holder) {
        return (holder & mask) >> shift_count;
    }

    /**
     * <p>Obtains the long value for the specified BitField, appropriately shifted right.</p>
     *
     * @see #setIntValue(long,int)
     * @param holder the long data containing the bits we're interested in
     * @return the selected bits, shifted right appropriately
     */
    public int getIntValue(final long holder) {
        return (int) ((holder & mask) >> shift_count);
    }

    /**
     * <p>Replaces the bits with new values.</p>
     *
     * @see #getLongValue(long)
     * @param holder the int data containing the bits we're interested in
     * @param value the new value for the specified bits
     * @return the value of holder with the bits from the value parameter replacing the old bits
     */
    public long setLongValue(final long holder, final long value) {
        return (holder & ~mask) | ((value << shift_count) & mask);
    }

    /**
     * <p>Replaces the bits with new values.</p>
     *
     * @see #getLongValue(long)
     * @param holder the int data containing the bits we're interested in
     * @param value the new value for the specified bits
     * @return the value of holder with the bits from the value parameter replacing the old bits
     */
    public long setIntValue(final long holder, final int value) {
        return (holder & ~mask) | ((value << shift_count) & mask);
    }

    /**
     * <p>Retrieves the highest possible value for this field.</p>
     *
     * @return the highest possible value for this field
     */
    public int getMaxValue() {
        return ((1 << shift_count) - 1);
    }

}
