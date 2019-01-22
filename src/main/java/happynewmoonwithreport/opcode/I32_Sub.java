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
package happynewmoonwithreport.opcode;


import java.util.UUID;

import happynewmoonwithreport.WasmInstanceInterface;
import happynewmoonwithreport.WasmRuntimeException;
import happynewmoonwithreport.WasmStack;
import happynewmoonwithreport.type.I32;

/**
 * Sign-agnostic addition
 * <p>
 * Return the result of subtracting i2 from i1 modulo 2<sup>N</sup>.
 * <p>
 * <b>Note this is the same for all Binary Operations</b>
 * <p>
 * t.binop
 * <ol>
 * <li>
 * Assert: due to validation, two values of value type t are on the top of the stack.
 * </li>
 * <li>
 * Pop the value t.const c2 from the stack.
 * </li>
 * <li>
 * Pop the value t.const c1 from the stack.
 * </li>
 * <li>
 * If binopt(c1,c2) is defined, then: Let c be a possible result of computing binopt(c1,c2). Push
 * the value t.const
 * c to the stack.
 * </li>
 * <li>
 * Else:
 * Trap.
 * <p>
 * </li>
 * </ol>
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/core/exec/numerics.html#op-isub"
 * target="_top">
 * https://webassembly.github.io/spec/core/exec/numerics.html#op-isub
 * </a>
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/core/exec/instructions.html#numeric-instructions"
 * target="_top">
 * https://webassembly.github.io/spec/core/exec/instructions.html#numeric-instructions  t.binop
 * </a>
 */
public class I32_Sub<ParameterType, ReturnType> {
	private WasmInstanceInterface instance;

	private I32_Sub() {
		super();
	}

	public I32_Sub(WasmInstanceInterface instance) {
		this();
		this.instance = instance;
	}

	/**
	 * Execute the opcode.
	 */
	public void execute() {
		WasmStack<Object> stack = instance.stack();
		if ((stack.peek() instanceof I32) == false) {
			throw new WasmRuntimeException(UUID.fromString("ed5b6703-894c-4d1e-8ddc-4aab7ed1f4dd"),
										   "addI32: Value2 type is incorrect");
		}
		I32 value2 = (I32) stack.pop();

		if ((stack.peek() instanceof I32) == false) {
			throw new WasmRuntimeException(UUID.fromString("d259488c-e394-4c0c-9246-1882923fb352"),
										   "addI32: Value1 type is incorrect");
		}
		I32 value1 = (I32) stack.pop();

		// does not need modulo because java is already handling
		I32 result = new I32(value1.integerValue() - value2.integerValue());

		stack.push(result);
	}
}