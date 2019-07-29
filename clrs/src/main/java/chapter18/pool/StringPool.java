/*
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
package chapter18.pool;

import chapter18.structures.hash.WeakSet;

/**
 * This class is Thread-Safe
 *
 * @author Guillermo Grandes / guillermo.grandes[at]gmail.com
 * @see java.util.WeakHashMap
 * @see String#intern()
 */
public class StringPool {
    private static final WeakSet<String> ws = new WeakSet<String>();

    public static final synchronized String getCanonicalVersion(final String str) {
        final String ref = ws.get(str);
        if (ref != null) {
            return ref;
        }
        ws.put(str);
        return str;
    }

    public static final synchronized int size() {
        return ws.size();
    }

    public static final synchronized void clear() {
        ws.clear();
    }

    public static final synchronized String get(final String str) {
        return ws.get(str);
    }
}
