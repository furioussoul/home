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

import chapter18.io.StringSerializer;
import chapter18.pool.StringPool;

import java.nio.ByteBuffer;

/**
 * Holder for String values <br/>
 * <b>WARNING:</b> Dont use this with BplusTreeFile (file need fixed/constant length objects, like Long or
 * Int)
 *
 * @author Guillermo Grandes / guillermo.grandes[at]gmail.com
 */
public final class StringHolder extends DataHolder<StringHolder> {
    private final String value;

    /**
     * Constructor necesario para la deserializacion
     */
    public StringHolder() {
        this("");
    }

    private StringHolder(final String value) {
        this.value = value;
    }

    public static StringHolder valueOf(final String value) {
        return new StringHolder(StringPool.getCanonicalVersion(value));
    }

    public String getValue() {
        return value;
    }

    // ========= Basic Object methods =========

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    // ========= Comparable =========

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof StringHolder) {
            return value.equals(((StringHolder) obj).value);
        }
        return false;
    }

    @Override
    public int compareTo(final StringHolder anotherString) {
        final String thisVal = this.value;
        final String anotherVal = anotherString.value;
        return thisVal.compareTo(anotherVal);
    }

    // ========= Serialization =========

    @Override
    public final int byteLength() {
        throw new UnsupportedOperationException("StringHolder is variable length Object");
    }

    @Override
    public void serialize(final ByteBuffer buf) {
        StringSerializer.fromStringToBuffer(buf, value);
    }

    @Override
    public StringHolder deserialize(final ByteBuffer buf) {
        return valueOf(StringSerializer.fromBufferToString(buf));
    }

}
