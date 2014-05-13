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
package sos.xml;


import java.io.FileInputStream;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: �berschreibt die Klasse FileInputStream. Hier werden konkatenierte
 * XML Dateien aus dem Datenstrom herausgeschnitten. </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author mo
 * @version 1.0
 */

public class SOSXMLStream
    extends FileInputStream {

  static  int minBytes = 512;

  /** Parameter: welche xml-Datei abgeholt werden soll  */
  private int which;
  private int fileSize;
  private int position = 0;
  private int posBegin = 0;
  private int posEnd = 0;
  private int state = 0;
  private int cntBytes = 0;

  private StringBuffer resultString = new StringBuffer();


  public SOSXMLStream(int which_, String path) throws FileNotFoundException {

    super(path);
    which = which_;
    byte[] last_buff = new byte[4];
    boolean isStarting = true;

    try {
      DataInputStream fileIn = new DataInputStream(new FileInputStream(path));
      fileSize = fileIn.available();
      
      try {
      
      	while ( ((cntBytes = fileIn.available()) > 0) && (state < 3) ) {
      		cntBytes = (cntBytes > minBytes) ? minBytes : cntBytes;
			byte[] buff = new byte[4+cntBytes];
      		if ( isStarting ) {
				cntBytes = fileIn.read(buff,0,cntBytes);
      		} else {
        		cntBytes = fileIn.read(buff,4,cntBytes);
	        	buff[0] = last_buff[0];
    	    	buff[1] = last_buff[1];
        		buff[2] = last_buff[2];
        		buff[3] = last_buff[3];
	      	}
			last_buff = new byte[4];
			if (cntBytes <= minBytes)
    			java.lang.System.arraycopy(buff,cntBytes,last_buff,0,4);
	        String buff_str = new String(buff);
	        if (isStarting) {
    	        resultString.append(buff_str.substring(0, cntBytes) );
	        } else {
	        	if (cntBytes < minBytes) {
					resultString.append(buff_str.substring(4, cntBytes+4) );
	        	} else {
					resultString.append(buff_str.substring(4, cntBytes+4) );
	        	}
	        }
			isStarting = false;
        	int xmlpos = buff_str.indexOf("<?xml");
	        if (xmlpos != -1 && (state == 0 || state == 2)) {
    	      state++;
        	}
	        else {
    	      if (state > 1)
        	    state = 2;
	          else
    	        state = 0;
        	}
	        if ( state == 1 ) {
    	      //posBegin = fileSize - ( (buff.length()+2 - xmlpos) + fileIn.available());
	          posBegin = fileSize - ( (buff.length - xmlpos) + fileIn.available());
    	      posEnd = fileSize;
        	  state++;
	        }
    	    if ( state == 3 ) {
	          if ( which_ == 1 )
    	        //posEnd   = fileSize - ( (buff.length()+2 - xmlpos) + fileIn.available());
        	    posEnd   = fileSize - ( (buff.length - xmlpos) + fileIn.available());
	          else {
    	        which_--;
        	    state = 2;
            	//posBegin = fileSize - ( (buff.length()+2 - xmlpos) + fileIn.available());
	            posBegin = fileSize - ( (buff.length - xmlpos) + fileIn.available());
    	        posEnd   = fileSize;
        	  }
        	}

      	}

      	fileIn.close();
      	if (which_ > 1)
        	throw new FileNotFoundException();
      	this.skip(posBegin);

      } 
      catch (IOException e) {
		   // throw new FileNotFoundException();
      } 
    }
      catch (IOException e) {
       	throw new FileNotFoundException();
    } catch (Exception e) {
       	throw new FileNotFoundException();
    }
  }


  // Constructors

  public SOSXMLStream(int which, File file) throws FileNotFoundException {
    super(file.getAbsolutePath());
  }

  public SOSXMLStream(int which, FileDescriptor fileDescriptor) {
    super(fileDescriptor);
  }

  // Methods
  //private native void open(String string) throws FileNotFoundException;

  public int read() throws IOException{
    if ( this.available() < 1 ) {
        return -1;
    } else {
      return super.read();
    }
  }

  public int read(byte[] byteArray) throws IOException {
      if (this.available() < 1)
        return -1;
      if (byteArray.length > this.available())
        return this.read(byteArray, 0, this.available());
      else
        return this.read(byteArray, 0, byteArray.length);
   }

  public int read(byte[] byteArray, int int1, int int2) throws IOException {
     if (this.available() < 1)
       return -1;
     if (this.available() >= int2)
       return super.read(byteArray, int1, int2);
     else {
       return super.read(byteArray, int1, this.available());
     }
   }

  public long skip(long long0) throws IOException {
    if (long0 == 0)
       return 0;
    if (long0 > this.available())
      return super.skip(this.available());
    else
      return super.skip(long0);
  }

  public int available() throws IOException{
    return ( posEnd - ( fileSize - super.available()) );
  }

  public StringBuffer getResultString() {
  	return this.resultString;
  }
  
}
