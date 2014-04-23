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
package sos.scheduler.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sos.JSHelper.Basics.JSToolBox;
import com.sos.JSHelper.Listener.JSListenerClass;
import com.sos.JSHelper.Logging.Log4JHelper;
import com.sos.JSHelper.io.Files.JSTextFile;

/**
 * \class 		JSExistsFileJUnitTest - JUnit-Test for "check wether a file exist"
 *
 * \brief MainClass to launch JSExistFile as an executable command-line program
 *

 *
 * see \see C:\Users\KB\Documents\xmltest\JSExistFile.xml for (more) details.
 *
 * \verbatim ;
 * mechanicaly created by C:\Users\KB\eclipse\xsl\JSJobDoc2JSJUnitClass.xsl from http://www.sos-berlin.com at 20110820121024 
 * \endverbatim
 */
public class JSExistsFileJUnitTest extends JSToolBox {
	private static final int		conNumberOfFiles2Skip		= 2;
	private static final int		conNumberOfTestFiles		= 10;
	private static final String		conTestRegularExpression1	= "^.*\\.kb$";
	private static final String		conTestBaseFolderName		= "j:/e/java/development-testdata/com.sos.scheduler/";
	@SuppressWarnings("unused")//$NON-NLS-1$
	private final static String		conClassName				= "JSExistsFileJUnitTest";								//$NON-NLS-1$
	@SuppressWarnings("unused")//$NON-NLS-1$
	private static Logger			logger						= Logger.getLogger(JSExistsFileJUnitTest.class);
	@SuppressWarnings("unused")//$NON-NLS-1$
	private static Log4JHelper		objLogger					= null;

	protected JSExistsFileOptions	objOptions					= null;
	private JSExistsFile			objE						= null;

	public JSExistsFileJUnitTest() {
		//
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		objLogger = new Log4JHelper("./log4j.properties"); //$NON-NLS-1$
		objE = new JSExistsFile();
		// objE.registerMessageListener(this);
		objOptions = objE.Options();
		// objO.registerMessageListener(this);

		JSListenerClass.bolLogDebugInformation = true;
		JSListenerClass.intMaxDebugLevel = 9;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecute() throws Exception {

		boolean flgResult = objE.Execute();
		assertTrue("Directory must exists", flgResult);

		objOptions.file.Value("abcdef");
		flgResult = objE.Execute();
		assertFalse("Directory must exists", flgResult);

		objOptions.file.Value(conTestBaseFolderName);
		flgResult = objE.Execute();
		assertTrue("Directory must exists", flgResult);

	}

	private void createTestFiles() throws Exception {
		String strT = "Testfile Testfile Testfile";
		long lngTimeLag = 3600000;

		for (int i = 0; i < conNumberOfTestFiles; i++) {
			JSTextFile objF = new JSTextFile(conTestBaseFolderName + "testfile" + i + ".kb");
			if (objF.exists()) {
				break;
			}
			objF.Write(strT);
			strT = strT + strT;
			objF.deleteOnExit();
			Date lastModified = new Date(objF.lastModified());
			objF.close();
			boolean blnSuccess = objF.setLastModified(lastModified.getTime() - lngTimeLag);
			lngTimeLag += 3600000;
		}
	}

	@Test
	public void testFileSpec() throws Exception {
		createTestFiles();
		objOptions.file.Value(conTestBaseFolderName);
		objOptions.file_spec.Value(conTestRegularExpression1);
		boolean flgResult = objE.Execute();

		assertTrue("Dateien wurden gefunden", flgResult);
		assertEquals("i expect exactly " + conNumberOfTestFiles + " files", conNumberOfTestFiles, objE.getResultList().size());
	}

	@Test
	public void testSkipFirstFile() throws Exception {
		createTestFiles();
		objOptions.file.Value(conTestBaseFolderName);
		objOptions.file_spec.Value(conTestRegularExpression1);
		objOptions.skip_first_files.value(conNumberOfFiles2Skip);
		objOptions.min_file_size.Value("5");
		boolean flgResult = objE.Execute();

		assertTrue("Dateien wurden gefunden", flgResult);
		assertEquals("i expect exactly " + conNumberOfTestFiles + " files", conNumberOfTestFiles - conNumberOfFiles2Skip, objE.getResultList().size());
	}

	@Test
	public void testSkipLastFile() throws Exception {
		createTestFiles();
		objOptions.file.Value(conTestBaseFolderName);
		objOptions.file_spec.Value(conTestRegularExpression1);
		objOptions.skip_last_files.value(conNumberOfFiles2Skip);
		objOptions.min_file_size.Value("5");
		boolean flgResult = objE.Execute();

		assertTrue("Dateien wurden gefunden", flgResult);
		assertEquals("i expect exactly " + conNumberOfTestFiles + " files", conNumberOfTestFiles - conNumberOfFiles2Skip, objE.getResultList().size());
	}

	@Test
	public void testMinFileSize() throws Exception {
		createTestFiles();
		objOptions.file.Value(conTestBaseFolderName);
		objOptions.file_spec.Value(conTestRegularExpression1);
		objOptions.min_file_size.Value("4KB");
		boolean flgResult = objE.Execute();

		assertTrue("Dateien wurden gefunden", flgResult);
		assertEquals("i expect exactly " + conNumberOfTestFiles + " files", 2, objE.getResultList().size());
	}

	@Test
	public void testMaxFileSize() throws Exception {
		createTestFiles();
		objOptions.file.Value(conTestBaseFolderName);
		objOptions.file_spec.Value(conTestRegularExpression1);
		objOptions.max_file_size.Value("4KB");
		boolean flgResult = objE.Execute();

		assertTrue("Dateien wurden gefunden", flgResult);
		assertEquals("i expect exactly " + conNumberOfTestFiles + " files", 8, objE.getResultList().size());
	}

	@Test
	public void testMinMaxFileSize() throws Exception {
		createTestFiles();
		objOptions.file.Value(conTestBaseFolderName);
		objOptions.file_spec.Value(conTestRegularExpression1);
		objOptions.min_file_size.Value("2KB");
		objOptions.max_file_size.Value("4KB");
		boolean flgResult = objE.Execute();

		assertTrue("Dateien wurden gefunden", flgResult);
		assertEquals("i expect exactly " + conNumberOfTestFiles + " files", 1, objE.getResultList().size());
	}

	@Test
	public void testMinFileAge() throws Exception {
		createTestFiles();
		objOptions.file.Value(conTestBaseFolderName);
		objOptions.file_spec.Value(conTestRegularExpression1);
		objOptions.min_file_age.Value("02:00:00");
		boolean flgResult = objE.Execute();

		assertTrue("Dateien wurden gefunden", flgResult);
		assertEquals("i expect exactly " + conNumberOfTestFiles + " files", 8, objE.getResultList().size());
	}

	@Test
	public void testMaxFileAge() throws Exception {
		createTestFiles();
		objOptions.file.Value(conTestBaseFolderName);
		objOptions.file_spec.Value(conTestRegularExpression1);
		objOptions.max_file_age.Value("03:00:00");
		boolean flgResult = objE.Execute();

		assertTrue("Dateien wurden gefunden", flgResult);
		assertEquals("i expect exactly " + conNumberOfTestFiles + " files", 3, objE.getResultList().size());
	}

	@Test
	public void testMaxFileAge2() throws Exception {
		createTestFiles();
		objOptions.file.Value(conTestBaseFolderName);
		objOptions.file_spec.Value(conTestRegularExpression1);
		objOptions.max_file_age.Value("60");
		long intF = objOptions.max_file_age.calculateFileAge();
		assertEquals("long milliseconds ", 60000L, intF);
		
		boolean flgResult = objE.Execute();

		assertTrue("Dateien wurden gefunden", flgResult);
		assertEquals("i expect exactly " + conNumberOfTestFiles + " files", 3, objE.getResultList().size());
	}

	@Test
	public void testMinMaxFileAge() throws Exception {
		createTestFiles();
		objOptions.file.Value(conTestBaseFolderName);
		objOptions.file_spec.Value(conTestRegularExpression1);
		objOptions.min_file_age.Value("02:00:00");
		objOptions.max_file_age.Value("05:00:00");
		boolean flgResult = objE.Execute();

		assertTrue("Dateien wurden gefunden", flgResult);
		assertEquals("i expect exactly " + conNumberOfTestFiles + " files", 3, objE.getResultList().size());
	}

} // class JSExistsFileJUnitTest
