/*
 *  Copyright 2017 Whole Bean Software, LTD.
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
package happynewmoonwithreport;

import happynewmoonwithreport.type.DataTypeNumber;
import happynewmoonwithreport.type.WasmVector;

/**
 * Activation frames carry the return arity of the respective function, hold the values of its locals (including
 * arguments) in the order corresponding to their static local indices, and a reference to the function’s own module
 * instance:
 * <p>
 * Source:  <a href="https://webassembly.github.io/spec/exec/runtime.html#syntax-frame" target="_top">
 * https://webassembly.github.io/spec/exec/runtime.html#syntax-frame
 * </a>
 */
public class WasmFrame {
    private WasmInstanceInterface instance;

    /**
     * The arity,  The types of the return values
     */
    private WasmVector<DataTypeNumber> returnTypeAll;
    /**
     * Local Variables.
     */
    private WasmVector<DataTypeNumber> localAll;

    private WasmFrame() {
        super();
        localAll = new WasmVector<>(100);
        returnTypeAll = new WasmVector<>(100);
    }

    public WasmFrame(WasmInstanceInterface instance) {
        this();
        this.instance = instance;

    }

    public WasmInstanceInterface instance() {
        return instance;
    }

    public WasmVector<DataTypeNumber> returnTypeAll() {
        return returnTypeAll;
    }

    public WasmVector<DataTypeNumber> localAll() {
        return localAll;
    }

    public void setLocalAll(WasmVector<DataTypeNumber> localAll) {
        this.localAll = localAll;
    }
}