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


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.util.zip.GZIPOutputStream;
import java.util.zip.GZIPInputStream;

/**
 * <p>Title: </p>
 * <p>Description: Java GZip client</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: SOS GmbH</p>
 * @author <a href="mailto:ghassan.beydoun@sos-berlin.com">Ghassan Beydoun</a>
 * @version $Id: SOSGZip.java 6076 2010-03-05 13:03:22Z mo $
 */


public class SOSGZip {
	
	/**
	 * @param inputFile
	 * @param outputFile
	 * @exception FileNotFoundException, IOException
	 */
	public static void compressFile(
			File inputFile, File outputFile ) throws
			Exception {
		
		
		BufferedInputStream in = null;
		GZIPOutputStream out = null;
		
		try {
			
			in = new BufferedInputStream( new FileInputStream(inputFile));
			
			out = new GZIPOutputStream( new FileOutputStream(outputFile));
			
			byte buffer[] = new byte[1024];
			int bytesRead;
			while ( (bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
			
		}  catch (Exception e) {
			throw e;
		} finally {
			try { in.close(); } catch( Exception e) {}
			try { out.close(); } catch( Exception e) {}
		}
	}
	
	
	/**
	 * @param fileInputStream
	 * @param outputFile
	 * @exception FileNotFoundException, IOException
	 */
	public static void compressFile(
			FileInputStream fileInputStream, File outputFile ) throws
			Exception {
		
		BufferedInputStream in = null;
		GZIPOutputStream out = null;
		try {
			
			in = new BufferedInputStream(fileInputStream);
			
			out = new GZIPOutputStream( new FileOutputStream(outputFile));
			
			byte buffer[] = new byte[1024];
			int bytesRead;
			while ( (bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
		}  catch (Exception e) {
			throw e;
		} finally {
			try { in.close(); } catch( Exception e) {}
			try { out.close(); } catch( Exception e) {}
		}
	}
	
	/**
	 * Decomprimiert die Archivdatei
	 * @param inFilename
	 * @throws Exception
	 */
	public static void decompress(String inFilename) throws	Exception{
		decompress(inFilename, null);
	}
	
	/**
	 * Decomprimiert die Archivdatei
	 * @param inFilename
	 * @param outFilename
	 * @throws Exception
	 */
	public static void decompress(String inFilename, String outFilename) throws	Exception{
		try {
			if(outFilename == null) {
				outFilename = inFilename.substring(0, inFilename.lastIndexOf("."));
			}
			
			GZIPInputStream in = new GZIPInputStream(new FileInputStream(inFilename));
			
			java.io.OutputStream out = new FileOutputStream(outFilename);
			
			
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			
			in.close();
			out.close();
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public static void main(String[] args) {
		try {
			compressFile(new File("C:/temp/a.txt"), new File("C:/temp/a.gz"));
			
			//decompress("C:/temp/a/RDN_20070511_000001.tar.gz", null);
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
