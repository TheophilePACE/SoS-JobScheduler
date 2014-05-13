/**
 * Copyright (C) 2014 BigLoupe http://bigloupe.github.io/SoS-JobScheduler/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
/********************************************************* begin of preamble
**
** Copyright (C) 2003-2012 Software- und Organisations-Service GmbH. 
** All rights reserved.
**
** This file may be used under the terms of either the 
**
**   GNU General Public License version 2.0 (GPL)
**
**   as published by the Free Software Foundation
**   http://www.gnu.org/licenses/gpl-2.0.txt and appearing in the file
**   LICENSE.GPL included in the packaging of this file. 
**
** or the
**  
**   Agreement for Purchase and Licensing
**
**   as offered by Software- und Organisations-Service GmbH
**   in the respective terms of supply that ship with this file.
**
** THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
** IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
** THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
** PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
** BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
** CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
** SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
** INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
** CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
** ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
** POSSIBILITY OF SUCH DAMAGE.
********************************************************** end of preamble*/
package com.sos.scheduler.engine.kernel.util;

import static com.google.common.base.Strings.isNullOrEmpty;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Util {
    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    private Util() {}

    public static String stringOrException(Object o) {
        try {
            return o == null? "null" : o.toString();
        } catch (Throwable x) { return x.toString(); }
    }

    public static boolean booleanOf(String s, boolean nullDefault, boolean emptyDefault) {
        return s == null? nullDefault : booleanOf(s, emptyDefault);
    }

    public static boolean booleanOf(String s, boolean deflt) {
        if (isNullOrEmpty(s))  return deflt;
        else
        if (s.equals("true")) return true;
        else
        if (s.equals("false")) return false;
        else
            throw new RuntimeException("Invalid boolean value: " + s);
    }


    public static void ignore(@SuppressWarnings("unused") Object unusedVariable) {}

    /** @return true, wenn {@link InterruptedException} abgefangen worden ist. */
    public static boolean sleepUntilInterrupted(long ms) {
        try {
            Thread.sleep(ms);
            return false;
        } catch (InterruptedException x) {
            logger.trace("sleep({})", ms, x);
            return true;
        }
    }

    public static <T> ImmutableList<T> optional(@Nullable T o) {
        if (o == null)
            return ImmutableList.of();
        else
            return ImmutableList.of(o);
    }
}
