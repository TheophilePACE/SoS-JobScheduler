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


package sos.scheduler.managed.db;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sos.JSHelper.Basics.JSToolBox;
import com.sos.JSHelper.Listener.JSListenerClass;
import com.sos.JSHelper.Logging.Log4JHelper;

/**
 * \class 		JobSchedulerManagedDBReportJobJUnitTest - JUnit-Test for "Launch Database Report"
 *
 * \brief MainClass to launch JobSchedulerManagedDBReportJob as an executable command-line program
 *

 *
 * see \see R:\backup\sos\java\development\com.sos.scheduler\src\sos\scheduler\jobdoc\JobSchedulerManagedDBReportJob.xml for (more) details.
 *
 * \verbatim ;
 * mechanicaly created by C:\Users\KB\eclipse\xsl\JSJobDoc2JSJUnitClass.xsl from http://www.sos-berlin.com at 20120830214522 
 * \endverbatim
 */
public class JobSchedulerManagedDBReportJobJUnitTest extends JSToolBox {
	@SuppressWarnings("unused")	 //$NON-NLS-1$
	private final static String					conClassName						= "JobSchedulerManagedDBReportJobJUnitTest"; //$NON-NLS-1$
	@SuppressWarnings("unused")	 //$NON-NLS-1$
	private static Logger		logger			= Logger.getLogger(JobSchedulerManagedDBReportJobJUnitTest.class);
	@SuppressWarnings("unused")	 //$NON-NLS-1$
	private static Log4JHelper	objLogger		= null;

	protected JobSchedulerManagedDBReportJobOptions	objOptions			= null;
	private JobSchedulerManagedDBReportJob objE = null;
	
	
	public JobSchedulerManagedDBReportJobJUnitTest() {
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
		objE = new JobSchedulerManagedDBReportJob();
		objE.registerMessageListener(this);
		objOptions = objE.Options();
		objOptions.registerMessageListener(this);
		
		JSListenerClass.bolLogDebugInformation = true;
		JSListenerClass.intMaxDebugLevel = 9;
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecute() throws Exception {
		
		
		objE.Execute();
		
//		assertEquals ("auth_file", objOptions.auth_file.Value(),"test"); //$NON-NLS-1$
//		assertEquals ("user", objOptions.user.Value(),"test"); //$NON-NLS-1$


	}
}  // class JobSchedulerManagedDBReportJobJUnitTest
