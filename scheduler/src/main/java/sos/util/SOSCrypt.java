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
package sos.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import sun.misc.BASE64Encoder;

/**
 * <p>Title: crypt-Klasse</p>
 * <p>Description:  Verwendet java standard Algorithmen wie MD5, SHA-1</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: SOS GmbH</p>
 * @author <a href="mailto:ghassan.beydoun@sos-berlin.com">Ghassan Beydoun</a>
 * @version $Id: SOSCrypt.java 3213 2008-01-28 15:27:48Z al $
 */



  public class SOSCrypt {

    /**
     * Liefert verschlsselten Text nach der eingegebenen Algorithmos zurck
     * @param text der Text der verschlsselt werden soll
     * @param algName Name des verwendeten Algorithmus, ein
     * java standard Algorithmus name z.B. MD5, SHA-1
     * @return String der verschluesselte Text mit BASE64 Kodierung.
     * @throws java.lang.Exception
     */
    public static String encrypt( String text, String algName ) throws Exception {

      BASE64Encoder encoder = null;
      MessageDigest md      = null;
      encoder = new BASE64Encoder();
      md = MessageDigest.getInstance(algName);
      md.reset();
      md.update(text.getBytes());
      return encoder.encode(md.digest());

    }
    
    /**
     * Errechnet den MD5-Code eines Strings<br> 
     * Dieser Code ist eine hexadezimale Zahl mit 32 Zeichen L�nge.<br>
     * Analog zur php Funktion md5() 
     * 
     * @param str
     * @return
     * @throws Exception
     */
    public static String MD5encrypt(String str) throws Exception {

        MessageDigest md = null;
        md = MessageDigest.getInstance("MD5");
        md.reset();
        md.update(str.getBytes());

        return toHexString(md.digest());
    }
    
    /**
     * Errechnet den MD5-Code einer Datei<br> 
     * Dieser Code ist eine hexadezimale Zahl mit 32 Zeichen L�nge.<br> 
     * 
     * @param inputFile 
     * @return
     * @throws Exception
     */
    public static String MD5encrypt(File inputFile) throws Exception {
    	byte[] b = createChecksum(inputFile);
    	String result = toHexString(b);
    	return result;
    }

    public static byte[] createChecksum(File inputFile) throws
    Exception{
    	InputStream fis =  new FileInputStream(inputFile);

    	byte[] buffer = new byte[1024];
    	MessageDigest md = MessageDigest.getInstance("MD5");
    	int numRead;
    	do {
    		numRead = fis.read(buffer);
    		if (numRead > 0) {
    			md.update(buffer, 0, numRead);
    		}
    	} while (numRead != -1);
    	fis.close();
    	return md.digest();
    }
    
    /**
     * Wandelt ein Byte-Array in ein Hex-String um.
     * 
     * @param bin
     *            Byte-Array
     */
    private static String toHexString(byte[] b) {
        
        char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        
        int length = b.length * 2;

        StringBuffer sb = new StringBuffer(length);
        for (int i = 0; i < b.length; i++) {
            // oberer Byteanteil
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
            // unterer Byteanteil
            sb.append(hexChar[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    


}

