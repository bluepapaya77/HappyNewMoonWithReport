/*
 *  Copyright 2017 - 2019 Whole Bean Software, LTD.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package happynewmoonwithreport.type;

import java.util.UUID;

import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.type.JavaType.ByteUnsigned;

/**
 * Signed Integer 32 Bits
 *
 * <table>
 * <td>High Byte</td><td>Byte</td><td>Byte</td><td>Low Byte</td>
 * </tr>
 * <tr>
 * <td>0</td><td>			1</td><td>			2</td><td>			3</td>
 * </tr>
 * <tr>	<td>Msb000000</td><td>00000000</td><td>00000000</td><td>0000000Lsb</td>
 * </tr>
 * </table>
 */
public class I32 extends IntWasm {

	protected Integer value;

	public I32() {
		value = 0;
	}

	public I32(Integer value) {
		this.value = value;
	}

	public I32(Long value) {
		this();
		if (isBoundByInteger(value) == false) {
			throw new WasmRuntimeException(UUID.fromString("62298944-804a-430e-b645-7bda0ecab265"),
										   "Value not bound by integer. Value = " + value + " ("
											   + toHex(value) + ")");
		}
		this.value = value.intValue();
	}


	public I32(ByteUnsigned[] byteAll) {
		this();
		value = 0;
		value += byteAll[0].intValue() << 24;
		value += byteAll[1].intValue() << 16;
		value += byteAll[2].intValue() << 8;
		value += byteAll[3].intValue() << 0;
	}



	/**
	 * Create an I32 using a Byte array, length, and sign extension.
	 *
	 * @param byteAll       an array of unsigned bytes.  Little Endian.
	 * @param length        length
	 * @param signExtension is this a signed value?  True = signed.
	 */
	public I32(ByteUnsigned[] byteAll, Integer length, Boolean signExtension) {
		this();
		create(byteAll, length, signExtension);
	}

	private void create(ByteUnsigned[] byteAll, Integer length, Boolean signExtension) {
		value = 0;
		switch (length) {
			case 8: {
				value += byteAll[0].intValue();
				if (signExtension) {
					value = signExtend8To32(byteAll[0]);
				}
				break;
			}
			case 16: {
				value += ((byteAll[1].intValue()) << 0);	// Least Significant Byte
				value += ((byteAll[0].intValue()) << 8);	// Most  Significant Byte
				if (signExtension) {
					value = signExtend16To32(value);
				}
				break;
			}
			// I'm not sure 24 and 32 are necessary or required by the specification.
//			case 24: {
//				value += ((byteAll[2].intValue()) << 0);	// Least  Significant Byte
//				value += ((byteAll[1].intValue()) << 8);
//				value += ((byteAll[0].intValue()) << 16);	// Most  Significant Byte
//				if (signExtension) {
//					value = twoComplement(value);
//				}
//				break;
//			}
			case 32: {
				value += ((byteAll[3].intValue()) << 0);	// Least  Significant Byte
				value += ((byteAll[2].intValue()) << 8);
				value += ((byteAll[1].intValue()) << 16);
				value += ((byteAll[0].intValue()) << 24);	// Most  Significant Byte
				if (signExtension) {
					value = twoComplement(value);
				}
				break;
			}
			default: {
				throw new WasmRuntimeException(
					UUID.fromString("f8d78ad2-67ed-441f-a327-6df48f2afca7"),
					"I32 Constructor Illegal value in length.  Valid values are 8, 16, 24, 32.    "
						+ "Length =  "
						+ length);
			}
		}

	}

	/**
	 * Get an array of the bytes.  Big Endian.
	 *
	 * @return array of bytes.
	 */
	public ByteUnsigned[] getBytes() {
		ByteUnsigned[] byteAll = new ByteUnsigned[4];
		byteAll[3] = new ByteUnsigned((value >>> 0) & 0x0000_00FF);
		byteAll[2] = new ByteUnsigned((value >>> 8) & 0x0000_00FF);
		byteAll[1] = new ByteUnsigned((value >>> 16) & 0x0000_00FF);
		byteAll[0] = new ByteUnsigned((value >>> 24) & 0x0000_00FF);
		return byteAll;
	}

	@Override
	public Integer maxBits() {
		return 32;
	}

	@Override
	public Integer maxBytes() {
		Integer maxBytes = maxBits() / 8;
		return maxBytes;
	}

	@Override
	public Integer minBytes() {
		Integer maxBytes = maxBits() / 8;
		return maxBytes;
	}

	@Override
	public Long minValue() {
		Long minValue = -1L * (1L << (maxBits() - 1L));
		return minValue;

	}

	@Override
	public Long maxValue() {
		Long maxValue = (1L << (maxBits() - 1L)) - 1L;
		return maxValue;
	}

	/**
	 * use IntegerValue();
	 *
	 * @return Integer Value
	 */
	@Deprecated
	public Integer getValue() {
		return value;
	}

	@Override
	public Byte byteValue() {
		return value.byteValue();
	}

	public S32 signedValue() {
		return new S32(value);
	}

	public U32 unsignedValue() {
		// java 8 and above.
		return new U32(Integer.toUnsignedLong(value));
	}

	@Override
	public Boolean booleanValue() {
		return value != 0;
	}

	@Override
	public Integer integerValue() {
		return value;
	}

	@Override
	public Long longValue() {
		return value.longValue();
	}

	@Override
	public Boolean isBoundByInteger() {
		return isBoundByInteger(value.longValue());
	}

	protected Boolean isBoundByInteger(Long input) {
		return (Integer.MIN_VALUE <= input.longValue() && input.longValue() <= Integer.MAX_VALUE);
	}

	public Boolean equals(I32 other) {
		return value.equals(other.getValue());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof I32)) {
			return false;
		}

		I32 i32 = (I32) o;

		return value.equals(i32.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public String toString() {
		String result = "I32{ value = " + value + " (hex = " + toHex(value) + ") }";
		return result;
	}
}
