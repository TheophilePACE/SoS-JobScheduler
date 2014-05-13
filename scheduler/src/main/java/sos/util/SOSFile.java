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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import com.sos.JSHelper.Exceptions.JobSchedulerException;

/**
 * Title: SOS File Utilities
 * Description: Helfer-Klassen zur Dateiverarbeitung
 * Copyright: Copyright (c) 2003
 * Company: SOS GmbH
 * @author <a href="mailto:ghassan.beydoun@sos-berlin.com">Ghassan Beydoun</a>
 * @version $Id: SOSFile.java 17687 2012-07-26 14:45:07Z kb $
 */

public class SOSFile {

	public static SOSLogger	logger	= null;

	/**
	 * Ersetzt einen Platzhalter in einer Dateispezifikation.
	 * @param  filespec Dateispezifikation, Platzhalter sind via [...] enthalten
	 * @param  substitute Subsitut des Platzhalters
	 * @return String Dateiname
	 */
	public static String subFileMask(String filespec, String substitute) throws IOException {

		if (filespec == null) {
			throw new IOException("file specification is null.");
		}

		String retVal = new String();
		int ipos1 = filespec.indexOf("[");
		int ipos2 = filespec.lastIndexOf("]");
		if ((ipos1 == -1) || (ipos2 == -1)) {
			return filespec;
		}

		String midStr = new String();
		String startStr = new String();
		String endStr = new String();

		if (ipos1 != 0) {
			startStr = filespec.substring(0, ipos1);
		}

		midStr = substitute.concat(filespec.substring(ipos2 + 1, filespec.length()));
		retVal = startStr.concat(midStr).concat(endStr);
		return retVal;
	}

	/**
	 * liefert Dateiliste des eingegebenen Verzeichnis zurück.
	 * @return Vector Dateiliste
	 * @param regexp ein regul&auml;er Ausdruck
	 * @param flag ein Integer-Wert: CASE_INSENSITIVE, MULTILINE, DOTALL, UNICODE_CASE, and CANON_EQ
	 * @see <a href="http://java.sun.com/j2se/1.4.2/docs/api/constant-values.html#java.util.regex.Pattern.UNIX_LINES">Constant Field Values</a>
	 */
	public static Vector<File> getFilelist(String folder, String regexp, int flag) throws Exception {

		Vector<File> filelist = new Vector<File>();

		if (folder == null || folder.length() == 0)
			throw new FileNotFoundException("empty directory not allowed!!");

		File f = new File(folder);
		if (!f.exists()) {
			throw new FileNotFoundException("directory does not exist: " + folder);
		}
		
		filelist = new Vector<File>();
		File[] files = f.listFiles(new SOSFilelistFilter(regexp, flag));
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
			}
			else
				if (files[i].isFile()) {
					filelist.add(files[i]);
				}
				else {
					// unknown
				}
		}

		return filelist;
	}

	/**
	 * liefert Dateiliste des eingegebenen Verzeichnis zurück.
	 * @return Vector Dateiliste
	 * @param regexp ein regul&auml;er Ausdruck
	 * @param flag ein Integer-Wert: CASE_INSENSITIVE, MULTILINE, DOTALL, UNICODE_CASE, and CANON_EQ
	 * @see <a href="http://java.sun.com/j2se/1.4.2/docs/api/constant-values.html#java.util.regex.Pattern.UNIX_LINES">Constant Field Values</a>
	 */
	public static Vector<File> getFolderlist(String folder, String regexp, int flag) throws Exception {

		Vector<File> filelist = new Vector<File>();

		if (folder == null || folder.length() == 0)
			throw new FileNotFoundException("empty directory not allowed!!");

		File f = new File(folder);
		if (!f.exists())
			throw new FileNotFoundException("directory does not exist: " + folder);
		filelist = new Vector<File>();
		File[] files = f.listFiles(new SOSFilelistFilter(regexp, flag));
		for (int i = 0; i < files.length; i++) {
			if (!files[i].getName().equals(".") && !files[i].getName().equals(".."))
				filelist.add(files[i]);
		}

		return filelist;
	}

	/**
	 * 
	 * liefert Dateiliste des eingegebenen Verzeichnis zurück.
	 * @return Vector Dateiliste
	 * @param regexp ein regul&auml;er Ausdruck
	 * @param flag ein Integer-Wert: CASE_INSENSITIVE, MULTILINE, DOTALL, UNICODE_CASE, and CANON_EQ
	 * @param withSubFolder 
	 * @see <a href="http://java.sun.com/j2se/1.4.2/docs/api/constant-values.html#java.util.regex.Pattern.UNIX_LINES">Constant Field Values</a>
	 */
	public static Vector<File> getFilelist(String folder, String regexp, int flag, boolean withSubFolder) throws Exception {
		Vector<File> filelist = new Vector<File>();
		File file = null;
		File[] subDir = null;

		file = new File(folder);
		subDir = file.listFiles();
		filelist.addAll(getFilelist(folder, regexp, flag));
		if (withSubFolder) {
			for (int i = 0; i < subDir.length; i++) {
				if (subDir[i].isDirectory()) {
					filelist.addAll(getFilelist(subDir[i].getPath(), regexp, flag, true));
				}
			}
		}
		return filelist;
	}

	/**
	 * 
	 * liefert Dateiliste des eingegebenen Verzeichnis zurück.
	 * @return Vector Dateiliste
	 * @param regexp ein regul&auml;er Ausdruck
	 * @param flag ein Integer-Wert: CASE_INSENSITIVE, MULTILINE, DOTALL, UNICODE_CASE, and CANON_EQ
	 * @param withSubFolder 
	 * @see <a href="http://java.sun.com/j2se/1.4.2/docs/api/constant-values.html#java.util.regex.Pattern.UNIX_LINES">Constant Field Values</a>
	 */
	public static Vector<File> getFolderlist(String folder, String regexp, int flag, boolean withSubFolder) throws Exception {
		Vector<File> filelist = new Vector<File>();
		File file = null;
		File[] subDir = null;

		file = new File(folder);
		subDir = file.listFiles();
		filelist.addAll(getFolderlist(folder, regexp, flag));
		if (withSubFolder) {
			for (int i = 0; i < subDir.length; i++) {
				if (subDir[i].isDirectory()) {
					filelist.addAll(getFolderlist(subDir[i].getPath(), regexp, flag, true));
				}
			}
		}
		return filelist;
	}

	/**
		 * Kopiert eine Datei von Quelle zum Ziel.
		 * Die Parametern geben die absoluter Pfad und Dateiname an, 
		 * in dem die Datei kopiert werden sollen
		 * 
		 * @param dest  
		 * @param source
		 * @throws IOException
		 * @author mo
		 */
	public static boolean copyFile(String source, String dest) throws Exception {
		return copyFile(new File(source), new File(dest), false);
	}

	/**
	 * Kopiert eine Datei von Quelle zum Ziel
	 * 
	 * @param dest
	 * @param source
	 * @throws IOException
	 * @author mo
	 */
	public static boolean copyFile(File source, File dest) throws Exception {
		return copyFile(source, dest, false);
		/*int size = (int) source.length();
		int bytes_read = 0;
		byte[] data=null;
		FileInputStream in=null;
		FileOutputStream out=null;
		boolean retVal = true;
		try {
			in   = new FileInputStream(source);
			out  = new FileOutputStream(dest,true);
			data = new byte[size];
			while (bytes_read < size)
				bytes_read += in.read(data, bytes_read, size-bytes_read);
			out.write(data);
			return retVal;
			
		} catch (Exception e) {
			throw new Exception ("\n -> ..error in " + SOSClassUtil.getMethodName() + " " + e);
		} finally {														 
			if (in != null)  in.close();
			if (out != null) out.close();
		}*/
	}

	// improved buffered copy
	// from http://java.sun.com/docs/books/performance/1st_edition/html/JPIOPerformance.fm.html
	static final int	BUFF_SIZE	= 100000;
	static final byte[]	buffer		= new byte[BUFF_SIZE];

	/**
	 * Kopiert eine Datei von Quelle zum Ziel. 
	 * Hier kann zus�tzlich angegeben werden, ob die Datei angeh�ngt werden soll
	 * 
	 * @param dest
	 * @param source
	 * @throws IOException
	 * @author al
	 */
	public static boolean copyFile(File source, File dest, boolean append) throws Exception {
		InputStream in = null;
		OutputStream out = null;
		try {
			if (logger != null) {
				logger.debug5("Copying file " + source.getAbsolutePath() + " with buffer of " + BUFF_SIZE + " bytes");
			}
			in = new FileInputStream(source);
			out = new FileOutputStream(dest, append);
			while (true) {
				synchronized (buffer) {
					int amountRead = in.read(buffer);
					if (amountRead == -1) {
						break;
					}
					out.write(buffer, 0, amountRead);
				}
			}
			if (logger != null) {
				logger.debug5("File " + source.getAbsolutePath() + " with buffer of " + BUFF_SIZE + " bytes");
			}
			return true;
		}
		finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * Liest die Dateiinhalt aus und gibt diese als String
	 * 
	 * @param dest
	 * @param source
	 * @throws IOException
	 * @author mo
	 */
	public static String readFile(File source) throws Exception {
		int size = (int) source.length();
		int bytes_read = 0;
		byte[] data = null;
		FileInputStream in = null;
		String retsVal = "";
		try {

			in = new FileInputStream(source);
			data = new byte[size];
			while (bytes_read < size)
				bytes_read += in.read(data, bytes_read, size - bytes_read);
			retsVal = retsVal + new String(data);
			return retsVal;

		}
		catch (Exception e) {
			throw new Exception("\n -> ..error in " + SOSClassUtil.getMethodName() + " " + e);
		}
		finally {
			if (in != null)
				in.close();
		}
	}

	/**
	 * Liest die Dateiinhalt aus und gibt diese als String<br>
	 * Dazu wird der SOSUnicodeInputStream verwendet
	 * @param dest
	 * @param source
	 * @throws IOException
	 * @author mo
	 */
	public static String readFileUnicode(File source) throws Exception {
		FileInputStream in = null;
		SOSUnicodeInputStream uniIn = null;
		try {

			in = new FileInputStream(source);
			uniIn = new SOSUnicodeInputStream(in, null);
			uniIn.getEncoding();
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = uniIn.read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			return out.toString();

		}
		catch (Exception e) {
			throw new Exception("\n -> ..error in " + SOSClassUtil.getMethodName() + " " + e);
		}
		finally {
			if (uniIn != null)
				uniIn.close();
		}
	}

	/**
	 * Bewegt eine Datei von Quelle zum Zielverzeichnis.
	 * 
	 * @param source
	 * @param dest
	 * @param sosLogger
	 * @return boolean
	 * @throws Exception
	 * @author mo
	 */
	public static boolean renameTo(String source, String dest, SOSStandardLogger sosLogger) throws Exception {
		return renameTo(new File(source), new File(dest), sosLogger);
	}

	/**
	 * Bewegt eine Datei von der Quelle zum Zielverzeichnis.
	 * @param dest
	 * @param source
	 * @throws IOException
	 * @author mo
	 */
	public static boolean renameTo(File source, File dest, SOSStandardLogger sosLogger) throws Exception {
		boolean retVal = true;
		try {
			if (sosLogger != null) {
				sosLogger.debug1("..trying to move File " + source.getAbsolutePath() + " to " + dest.getAbsolutePath());
			}
			copyFile(source, dest);
			if (!source.delete()) {
				retVal = false;
			}
			return retVal;
		}
		catch (Exception e) {
			throw new Exception("\n ..error in " + SOSClassUtil.getMethodName() + " " + e);
		}
	}

	/**
	 * �berpr�ft, ob die Datei in von einem anderen Prozess verwendet wird.
	 * Wenn ja, dann versucht es nochmal nach einer Sekunde. Die Anzahl der 
	 * Versuche sind fnfmal.
	 * @author mo
	 */
	public static boolean canReadFile(File file, SOSStandardLogger sosLogger) throws Exception {
		boolean rep = true;
		int repeatCount = 5; // 5 mal versuchen

		while (rep && repeatCount != -1) {
			try {
				if (!file.exists()) {
					rep = false;
					throw new Exception("..file does not exist: " + file.getAbsolutePath());
				}
				java.io.FileOutputStream f = new java.io.FileOutputStream(file, true);
				f.close();
				rep = false;
			}
			catch (Exception e) {
				repeatCount--;
				if (repeatCount == -1) {
					throw new Exception("..file cannot be accessed: " + file.getAbsolutePath() + ": " + e);
				}
				if (sosLogger != null) {
					sosLogger.debug1("trial " + (5 - repeatCount) + " of 5 to access order file: " + file.getAbsolutePath());
				}
				Thread.sleep(1000);
			}
		}
		return !rep;
	}

	/**
	 * Deletes file and directory.
	 * Deletes all files and subdirectories under file. 
	 *   
	 * @param file
	 * @return number deletions
	 * 
	 */
	public static int deleteFile(File file) {
		int count = 0;
		if (file.isDirectory()) {
			for (File file2 : file.listFiles()) {
				count += deleteFile(file2);
			}
		}
		
		boolean flgFileDeleted = file.delete();
		if (flgFileDeleted) {
			count++;
		}
		else {
			throw new JobSchedulerException(String.format("File '%1$s' not deleted due to an error.", file.getAbsolutePath()));
		}

		return count;
	} // deleteFile

	/**
	 * @return the logger
	 */
	public static SOSLogger getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	public static void setLogger(SOSLogger logger) {
		SOSFile.logger = logger;
	}

}
