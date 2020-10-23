/*
 * Copyright 2020 Dmitry Romanyuta
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package su.void_.api;

import org.junit.Test;
import static org.junit.Assert.*;

public class LookupServiceTest {
    @Test public void testSomeLibraryMethod() {
        LookupService classUnderTest = new LookupService();
        ServerCertificate serverCertificate = classUnderTest.lookup("", 0, "");
        assertTrue("someLibraryMethod should return", serverCertificate.getValidity());
    }
}
