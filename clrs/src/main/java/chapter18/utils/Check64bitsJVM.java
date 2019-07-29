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
package chapter18.utils;

public class Check64bitsJVM {
    public static boolean JVMis64bits() {
        final String propOsArch = System.getProperty("os.arch");
        final String propSunDataModel = System.getProperty("sun.arch.data.model");
        // http://stackoverflow.com/questions/807263/how-do-i-detect-which-kind-of-jre-is-installed-32bit-vs-64bit
        if (propSunDataModel != null) {
            return propSunDataModel.equals("64");
        }
        if (propOsArch != null) {
            return propOsArch.contains("64");
        }
        return false;
    }

    public static void main(final String[] args) throws Throwable {
        System.out.println("JVM is 64bits?: " + JVMis64bits());
    }
}
