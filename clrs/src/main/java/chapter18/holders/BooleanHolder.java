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
package chapter18.holders;

import java.nio.ByteBuffer;

/**
 * Holder for Boolean values
 *
 * @author Guillermo Grandes / guillermo.grandes[at]gmail.com
 */
public final class BooleanHolder extends DataHolder<BooleanHolder> {
    public static final BooleanHolder TRUE = new BooleanHolder(true);
    public static final BooleanHolder FALSE = new BooleanHolder(false);

    private final boolean value;

    /**
     * Constructor necesario para la deserializacion
     */
    public BooleanHolder() {
        this(false);
    }

    private BooleanHolder(final boolean value) {
        this.value = value;
    }

    public static BooleanHolder valueOf(final boolean value) {
        return (value ? TRUE : FALSE);
    }

    public boolean booleanValue() {
        return value;
    }

    // ========= Basic Object methods =========

    @Override
    public String toString() {
        return (value ? "true" : "false");
    }

    @Override
    public int hashCode() {
        return (value ? 1231 : 1237);
    }

    // ========= Comparable =========

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof BooleanHolder) {
            return value == ((BooleanHolder) obj).value;
        }
        return false;
    }

    @Override
    public int compareTo(final BooleanHolder another) {
        return (another.value == value ? 0 : (value ? 1 : -1));
    }

    // ========= Serialization =========

    @Override
    public final int byteLength() {
        return 1;
    }

    @Override
    public void serialize(final ByteBuffer buf) {
        buf.put((byte) (value ? 1 : 0));
    }

    @Override
    public BooleanHolder deserialize(final ByteBuffer buf) {
        return valueOf(buf.get() != 0);
    }

}
