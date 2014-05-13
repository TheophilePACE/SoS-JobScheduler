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


import java.io.BufferedWriter;
import java.io.Writer;
import java.util.Iterator;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: SOS GmbH</p>
 * @author <a href="mailto:ghassan.beydoun@sos-berlin.com">Ghassan Beydoun</a>
 * @version $Id: SOSCSVWriter.java 1533 2005-12-07 10:06:41Z al $
 */



public class SOSCSVWriter
    extends BufferedWriter {


  /** default Feldseparator */
  private static final String FIELD_SEPARATOR = ";";

  private static final String QUOTE = "\"";

  private  String fieldSeparator;

  /** quoting String */
  private String quote = "";

  /** */
  private boolean useQuotes = false;

  private int lineCount = 0;

  private StringBuffer line = new StringBuffer();

  private int fieldCount = 0;

  private String field = null;


  /**
   *
   * @param writer
   */

  public SOSCSVWriter( Writer writer ) {
    super( writer);
    this.fieldSeparator = SOSCSVWriter.FIELD_SEPARATOR;
    this.field = null;
    this.fieldCount = 0;
    this.line = new StringBuffer();
  }

  /**
   *
   * @param separator ist der default Feldtrenner.
   * @param useQuotes falls Quoting verwendet werden soll.
   * @param writer
   */
  public SOSCSVWriter( Writer writer, String separator, boolean useQuotes ) {
    super( writer );

    if ( useQuotes ) {
        this.setQuote( SOSCSVWriter.QUOTE );
    }
    this.fieldSeparator = separator;
    this.field = null;
    this.fieldCount = 0;
    this.line = new StringBuffer();
  }


  public void writeRecord(Iterator record) throws Exception {
    fieldCount = 0;
    line = new StringBuffer();
    while (record.hasNext()) {
      if (fieldCount++ > 0)
        line.append(fieldSeparator);
      field = null;
      try {
        field = record.next().toString();
      }
      catch (Exception e) {
        continue;
      }
      encodeField();
    }
    super.write(line.toString());
    this.lineCount++;
    super.newLine();
  }

  public int getLineCount() throws Exception {
    return this.lineCount;
  }

  private void encodeField() throws Exception {
    line.append(quote);
    if (field.indexOf('\r') >= 0 || field.indexOf('\n') >= 0)
      throw new Exception(line.toString() +
                            ": not allowed character (CR,LF) on line[" +
                            lineCount + "].");
    line.append(field);
    line.append(quote);
  }

  public  final String getQuote() {
    return quote;
  }

  public  final void setQuote(String quote) {
    this.quote = quote;
  }

public final boolean isUseQuotes() {
    return useQuotes;
}

public final void setUseQuotes(boolean useQuotes) {
    this.useQuotes = useQuotes;
}
}
