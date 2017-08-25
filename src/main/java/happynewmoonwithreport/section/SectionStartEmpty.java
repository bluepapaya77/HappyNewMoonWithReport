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
package happynewmoonwithreport.section;

import happynewmoonwithreport.type.UInt32;

/**
 * The start section is optional, this is the class to use when it has not been specified.
 *
 * @see SectionStart
 */
public class SectionStartEmpty extends SectionStart {

    public SectionStartEmpty() {
        index = new UInt32(-1);
    }

}
