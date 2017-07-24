package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;

public final class VarInt32 extends VarInt<Integer> {

    @SuppressWarnings("unused")
    private VarInt32() {
        super();
    }

    public VarInt32(BytesFile bytesFile) {
        assert (bytesFile.longEnough(minBytes()));
        value = convert(bytesFile);
    }

    public VarInt32(DataTypeNumber value) {
        this.value = value.integerValue();
    }

    /**
     * Create using a Long. Used mainly in testing.
     *
     * @param value
     */
    public VarInt32(Long value) {
        this.value = value.intValue();
        // set to default value.
    }

    /**
     * Create using a Integer. Size is hard coded to 1. Used mainly in testing.
     *
     * @param value
     */
    public VarInt32(Integer value) {
        this.value = value.intValue();
        // set to default value.
    }

    /**
     * Create using a Byte. Size is hard coded to 1. Used mainly in testing.
     *
     * @param value
     */
    public VarInt32(Byte value) {
        this.value = value.intValue();
    }

    public Integer convert(BytesFile bytesFile) {
        Integer cur;
        Integer count = 0;
        Integer result = 0;
        Integer signBits = -1;

        do {
            cur = bytesFile.readByte() & 0xff;
            result |= ((int) (cur & 0x7f)) << (count * 7);
            signBits <<= 7;
            count++;
        } while (((cur & 0x80) != 0) && count < maxBytes());

        // Sign extend if appropriate
        if (((signBits >> 1) & result) != 0) {
            result |= signBits;
        }

        return result;
    }

    /**
     * Writes {@code value} as a signed integer to {@code out}, starting at {@code offset}. Returns the number of bytes
     * written.
     */
    public ByteOutput convert() {
        ByteOutput out = new ByteArrayByteOutput(maxBytes());
        Integer remaining = value >> 7;
        boolean hasMore = true;
        int end = ((value & Long.MIN_VALUE) == 0) ? 0 : -1;

        while (hasMore) {
            hasMore = (remaining != end) || ((remaining & 1) != ((value >> 6) & 1));

            out.writeByte((byte) ((value & 0x7f) | (hasMore ? 0x80 : 0)));
            value = remaining;
            remaining >>= 7;
        }
        return out;
    }

    public Integer maxBits() {
        return 32;
    }

    public Integer minValue() {
        Integer minValue = -1 * (1 << (maxBits() - 1));
        return minValue;

    }

    public Integer maxValue() {
        Integer maxValue = (1 << (maxBits() - 1)) - 1;
        return maxValue;
    }


    @Override
    public String toString() {
        return "VarInt32{" +
                "value=" + value +
                "} ";
    }
}
