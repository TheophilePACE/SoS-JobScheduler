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

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

import static com.google.common.base.Charsets.ISO_8859_1;
import static com.sos.scheduler.engine.common.xml.XmlUtils.loadXml;
import static com.sos.scheduler.engine.common.xml.XmlUtils.toXml;

public final class SchedulerXmlUtils {
    /** Bytes, die in einem String zwischen C++ und Java ausgetauscht werden. */
    public static final Charset byteEncoding = ISO_8859_1;

    /** Liefert ein byte-codiertes XML-Dokument als String. */
    public static String xmlFromCppByteString(String byteString) {
        return toXml(loadXml(new ByteArrayInputStream(byteArrayFromCppByteString(byteString))));
    }

    /** Liefert ein byte-codiertes XML-Dokument als byte[]. */
    public static byte[] byteArrayFromCppByteString(String byteString) {
        return byteString.getBytes(byteEncoding);
    }

    private SchedulerXmlUtils() {}
}
