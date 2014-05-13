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

import java.io.*;
import java.util.*;
import java.util.zip.*;


/**
 * Komprimiert Dateien.
 * <p>Title: sos.util.SOSZipCompress.java </p>
 * <p>Description: Diese Klasse hat Drei Methoden. <br> 
 * 1. Methode compressFile: archiviert eine Liste von Dateien <br>
 * 2. Methode getCompressFileName: Liefert eine ArrayListe von Namen der Dateien, die archiviert wurde. <br>
 * 3. Methide getCompressFiles: Liefert eine ArrayListe. Ein Eintrag in dieser ArrayListe entspricht einen HashMap.
 * HashMap: key = filename und value = Dateiinhalt in String</p>
 * 
 * <p>Copyright: Copyright (c) 25.08.2004</p>
 * <p>Company: SOS GmbH</p>
 * @author M�r�vet �ks�z
 * @resource
 */
public class SOSZipCompress { 
	
	/** SOSString Object*/
	private SOSString sosString = null;
	
	
	/**
	 * Konstruktor 
	 */
	public SOSZipCompress() throws Exception {
		try {
			sosString = new SOSString();
		} catch (Exception e) {
			throw new Exception("error in " + SOSClassUtil.getMethodName() + " " + e);
		}
	}
	
	/**
	 * Hier wird eine achive Datei generiert. Der Parameter filenames beiinhaltet
	 * alle Dokumentenname, die archiviert werden sollen . 
	 * @param filenames
	 * @param archivename
	 * @throws Exception
	 */
	public void compressFile(ArrayList filenames, String archivename) throws Exception {
		try {
			FileOutputStream f = new FileOutputStream(archivename);
			CheckedOutputStream csum = new CheckedOutputStream(f, new Adler32());
			ZipOutputStream out =new ZipOutputStream(new BufferedOutputStream(csum));
			//out.setComment("A test of Java Zipping");
			// Can't read the above comment, though
			for(int i = 0; i < filenames.size(); i++) {
				
				//System.out.println("Writing file " + filenames[i]);
				//BufferedReader in =new BufferedReader(new FileReader(sosString.parseToString(filenames.get(i))));
				
				out.putNextEntry(new ZipEntry(new File(sosString.parseToString(filenames.get(i))).getName()));
				/*int c;
				 while((c = in.read()) != -1)
				 out.write(c);
				 in.close();
				 */
				
				//test 
				int size = (int)(new File(sosString.parseToString(filenames.get(i))).length());
				int bytes_read = 0;
				byte[] data=null;
				FileInputStream in=null;
				try {
					
					in   = new FileInputStream(sosString.parseToString(filenames.get(i)));		    
					data = new byte[size];
					while (bytes_read < size)
						bytes_read += in.read(data, bytes_read, size-bytes_read);
					out.write( data);
					
				} finally {														 
					if (in != null)  in.close();
					
				}
				//
				
			}
			out.close();
			// Checksum valid only after the file
			// has been closed!
			//System.out.println("Checksum: " + csum.getChecksum().getValue());
		} catch (Exception e) {
			throw new Exception("error in " + SOSClassUtil.getMethodName() + " " + e);
		}
	}
	
	
	/**
	 * Hier wird eine achive Datei generiert. Der Parameter filenames beiinhaltet
	 * alle Dokumentenname, die archiviert werden sollen . 
	 * @param filenames
	 * @param archivename
	 * @throws Exception
	 */
	public void compressFile(HashMap filenames, String archivename) throws Exception {
		try {
			FileOutputStream f = new FileOutputStream(archivename);
			CheckedOutputStream csum = new CheckedOutputStream(f, new Adler32());
			ZipOutputStream out =new ZipOutputStream(new BufferedOutputStream(csum));
			
			Iterator keys = filenames.keySet().iterator();
			Iterator values = filenames.values().iterator();
			String key = "";
			String val = "";
			//for(int i = 0; i < filenames.size(); i++) {
			while (keys.hasNext()) {
				key = sosString.parseToString(keys.next());
				val = sosString.parseToString(values.next());
				
				out.putNextEntry(new ZipEntry(new File(sosString.parseToString(val)).getName()));
				/*int c;
				 while((c = in.read()) != -1)
				 out.write(c);
				 in.close();
				 */
				
				//test 
				int size = (int)(new File(sosString.parseToString(key)).length());
				int bytes_read = 0;
				byte[] data=null;
				FileInputStream in=null;
				try {
					
					in   = new FileInputStream(sosString.parseToString(key));		    
					data = new byte[size];
					while (bytes_read < size)
						bytes_read += in.read(data, bytes_read, size-bytes_read);
					out.write( data);
					
				} finally {														 
					if (in != null)  in.close();
					
				}
				//
				
			}
			out.close();
			// Checksum valid only after the file
			// has been closed!
			//System.out.println("Checksum: " + csum.getChecksum().getValue());
		} catch (Exception e) {
			throw new Exception("error in " + SOSClassUtil.getMethodName() + " " + e);
		}
	}
	
	/**
	 * Liefert alle Dateiname, die im "archivename" gezippt sind. 
	 * @param archivname
	 * @return
	 * @throws Exception
	 */
	public ArrayList getCompressFileName(String archivname) throws Exception {
		ArrayList retVal = null;
		try  {
			retVal = new ArrayList();
			ZipFile zf = new ZipFile(archivname);
			Enumeration e = zf.entries();
			while(e.hasMoreElements()) {
				ZipEntry ze2 = (ZipEntry)e.nextElement();
				retVal.add(ze2);
				//System.out.println("\n\nFile: " + ze2);
				// ... and extract the data as before
			}
			return retVal;
		} catch(Exception e) {
			throw new Exception("error in " + SOSClassUtil.getMethodName() + " " + e);
		}                
	}
	/**
	 * Liefert eine HashTabelle, die im "archivename" gezippt sind.
	 * key = Name des Dokumentes
	 * value = Inhalt des Dokumentes 
	 * @param archivname
	 * @return
	 * @throws Exception
	 */
	public ArrayList getCompressFiles(String archivname) throws Exception {
		ArrayList retVal = null;
		String readingFile = ""; 
		try  {
			retVal = new ArrayList();
			//System.out.println("Reading file");
			FileInputStream fi = new FileInputStream(archivname);
			CheckedInputStream csumi = new CheckedInputStream(fi, new Adler32());
			ZipInputStream in2 =new ZipInputStream(new BufferedInputStream(csumi));
			ZipEntry ze;
			//System.out.println("Checksum: " +csumi.getChecksum().getValue());
			HashMap doc = null;
			while((ze = in2.getNextEntry()) != null) {
				//System.out.println("\nReading file " + ze);
				doc = new HashMap();
				doc.put("filename", ze);
				int x;
				while((x = in2.read()) != -1) {
					//System.out.write(x);
					readingFile = readingFile + (char)x;
				}
				doc.put("file", readingFile);
				retVal.add(doc);
			}
			
			in2.close();
			return retVal;
		} catch(Exception e) {
			throw new Exception("error in " + SOSClassUtil.getMethodName() + " " + e);
		}                
	}
	
	
	/**
	 * zum Testen
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		//String archname = "C:/factory/archive/docs/Antrag_2004-03-04_144431_27608.zip";
		String archname = "C:/temp/2.zip";
		int test = 1;
		
		if( test == 1) {
			
			try {
				HashMap testdaten = new HashMap();
				testdaten.put("C:/printout/spool/output/tiff/1.tif", "1.tif");
				testdaten.put("C:/printout/spool/output/tiff/2.tif", "2.tif");
				SOSZipCompress com = new SOSZipCompress();
				
				System.out.println("Archivierung der Dateien");
				com.compressFile(testdaten, archname);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else if (test==2) {
			
			try {
				ArrayList testdaten  = new ArrayList();            
				testdaten.add("C:/printout/spool/output/tiff/print00001.tif");
				testdaten.add("C:/printout/spool/output/tiff/print00002.tif");
				
				SOSZipCompress com = new SOSZipCompress();
				
				System.out.println("Archivierung der Dateien");
				com.compressFile(testdaten, archname);
				
				System.out.println("Ausgabe der Dateinamen, die archiviert wurden.");
				ArrayList list = com.getCompressFileName(archname);
				for (int i =0; i < list.size(); i++) {
					System.out.println(i +" 'te Datei: " + list.get(i));
				}
				
				System.out.println("Ausgabe der Dateinamen mit Dateiinhalten, die archiviert wurden.");
				ArrayList list2 = com.getCompressFiles(archname);
				HashMap hash = null;
				for (int i =0; i < list2.size(); i++) {
					hash = (HashMap)list2.get(i);
					System.out.println(i +" 'te Datei: " + hash.get("filename"));
					System.out.println("documentinhalt: "  + hash.get("file"));
				}    			    		
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		} else if(test == 3) {
			try {
				SOSZipCompress com = new SOSZipCompress();
				com.deCompressFile("C:/temp/a/RDN_20070511_000001.tar.gz");
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	public static final void copyInputStream(InputStream in, OutputStream out)
	throws IOException
	{
		byte[] buffer = new byte[1024];
		int len;
		
		while((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);
		
		in.close();
		out.close();
	}
	
	/**
	 * Decomprimiert die Archivdatei  
	 * @param archivname
	 * @return
	 * @throws Exception
	 */
	public ArrayList deCompressFile(String archivname) throws Exception {
		ArrayList retVal = null;
		try  {
			retVal = new ArrayList();
			ZipFile zipFile = new ZipFile(archivname);
			Enumeration e = zipFile.entries();
			while(e.hasMoreElements()) {
				ZipEntry entry = (ZipEntry)e.nextElement();
				
				System.out.println("\n\nFile: " + zipFile);                
				if(entry.isDirectory()) {                                   
					(new File(entry.getName())).mkdir();
					continue;
				}
				
				copyInputStream(zipFile.getInputStream(entry),
						new BufferedOutputStream(new FileOutputStream(entry.getName())));
			}
			
			zipFile.close();
			
			return retVal;
		} catch(Exception e) {
			throw new Exception("error in " + SOSClassUtil.getMethodName() + " " + e);
		}                
	}
	
	
} ///:~ 
