package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VarInt32Test {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testReadSigned() {
        // -624485 (0xFFF6789B) is encoded as 0x9B 0xF1 0x59.
        byte[] bytesAll = new byte[]{(byte) 0x9B, (byte) 0xF1, (byte) 0x59};
        BytesFile bytesFile = new BytesFile(bytesAll);
        NumberHelper.assertEqualHex(new VarInt32(-624485L).longValue(), new VarInt32(bytesFile).longValue());
    }

    @Test
    public void testWriteSigned() {
        // -624485 (0xFFF6789B) is encoded as 0x9B 0xF1 0x59.
        byte[] bytesAll = new byte[]{(byte) 0x9B, (byte) 0xF1, (byte) 0x59};
        VarInt32 varInt = new VarInt32(-624485L);
        ByteOutput bo = varInt.convert();
        assertArrayEqualsJDH(bytesAll, bo.bytes());

    }

    public void assertArrayEqualsJDH(byte[] expected, byte[] actual) {
        Integer length = Math.min(expected.length, actual.length);
        Boolean equal = true;
        for (int i = 0; i < length; i++) {
            if (expected[i] == 0 || actual[i] == 0) {
                break;
            }
            if (expected[i] != actual[i]) {
                equal = false;
                throw new AssertionError("Array not equals");
            }
        }
    }

    /**
     * source : https://webassembly.github.io/spec/binary/values.html#integers
     */
    @Test
    public void testReadSignedPadded() {
        // -624485 (0xFFF6789B) is encoded as 0x9B 0xF1 0x59.
        byte[] bytesAll = new byte[]{(byte) 0x9B, (byte) 0xF1, (byte) 0xD9, (byte) 0x7F};
        BytesFile bytesFile = new BytesFile(bytesAll);
        NumberHelper.assertEqualHex(new VarInt32(-624485L).longValue(), new VarInt32(bytesFile).longValue());
    }

    /**
     * Similarly, either of<br> 0x7e<br> 0xFE 0x7F <br> 0xFE 0xFF 0x7F <br> are well-formed encodings of the value -2.
     * <p>
     * source : https://webassembly.github.io/spec/binary/values.html#integers
     */
    @Test
    public void testReadSignedPaddedNeg2() {
        byte[] bytesAll = new byte[]{(byte) 0x7E};
        BytesFile bytesFile = new BytesFile(bytesAll);

        NumberHelper.assertEqualHex(new VarInt32(-2).longValue(), new VarInt32(bytesFile).longValue());

        bytesAll = new byte[]{(byte) 0xFE, (byte) 0x7F};
        bytesFile = new BytesFile(bytesAll);
        NumberHelper.assertEqualHex(new VarInt32(-2).longValue(), new VarInt32(bytesFile).longValue());

        bytesAll = new byte[]{(byte) 0xFE, (byte) 0xFF, (byte) 0x7F};
        bytesFile = new BytesFile(bytesAll);
        NumberHelper.assertEqualHex(new VarInt32(-2).longValue(), new VarInt32(bytesFile).longValue());

        bytesAll = new byte[]{(byte) 0xFE, (byte) 0xFF, (byte) 0x7F, (byte) 0x00};
        bytesFile = new BytesFile(bytesAll);
        NumberHelper.assertEqualHex(new VarInt32(-2).longValue(), new VarInt32(bytesFile).longValue());

        bytesAll = new byte[]{(byte) 0xFE, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x7F, (byte) 0x00};
        bytesFile = new BytesFile(bytesAll);
        NumberHelper.assertEqualHex(new VarInt32(-2).longValue(), new VarInt32(bytesFile).longValue());
    }

    @Test
    public void testReadSignedPaddedPositive2() {
        byte[] bytesAll = new byte[]{(byte) 0x02};
        BytesFile bytesFile = new BytesFile(bytesAll);
        NumberHelper.assertEqualHex(new VarInt32(2).longValue(), new VarInt32(bytesFile).longValue());

        bytesAll = new byte[]{(byte) 0x82, (byte) 0x00};
        bytesFile = new BytesFile(bytesAll);
        NumberHelper.assertEqualHex(new VarInt32(2).longValue(), new VarInt32(bytesFile).longValue());

        bytesAll = new byte[]{(byte) 0x82, (byte) 0x80, (byte) 0x00};
        bytesFile = new BytesFile(bytesAll);
        NumberHelper.assertEqualHex(new VarInt32(2).longValue(), new VarInt32(bytesFile).longValue());

        bytesAll = new byte[]{(byte) 0x82, (byte) 0x80, (byte) 0x00, (byte) 0x00};
        bytesFile = new BytesFile(bytesAll);
        NumberHelper.assertEqualHex(new VarInt32(2).longValue(), new VarInt32(bytesFile).longValue());

        bytesAll = new byte[]{(byte) 0x82, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x00};
        bytesFile = new BytesFile(bytesAll);
        NumberHelper.assertEqualHex(new VarInt32(2).longValue(), new VarInt32(bytesFile).longValue());

    }

    @Test
    public void testSize() {
        // -624485 (0xFFF6789B) is encoded as 0x9B 0xF1 0x59.
        byte[] bytesAll = new byte[]{(byte) 0x9B, (byte) 0xF1, (byte) 0x59};
        BytesFile bytesFile = new BytesFile(bytesAll);
    }

	/* @formatter:off	
                        -624485 Decimal
	     1001_10000111_01100101 624485 In raw binary
	     0110_01111000_10011010 Ones Complement (NOT)
	     0110_01111000_10011011 Twos Complement +1
	   011001__1110001__0011011 Split into 7 bit groups 
	  1011001__1110001__0011011 Sign extend to 21 bits.   
	 01011001_11110001_10011011 Add high 1 bits on all but most significant  group to form bytes
	     0x59     0xF1     0x9B Hexidecimal
	     0x9B     0xF1     0x59 Convert to little Endian.   
	 @formatter:on
	**/
}
