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


package com.sos.test;

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
 * \class 		CreateGenerateOrderOptionsJUnitTest - Creating PDIR generate order
 *
 * \brief
 *
 *

 *
 *
 * \verbatim ;
 * mechanicaly created by C:\ProgramData\sos-berlin.com\jobscheduler\scheduler_ur\config\JOETemplates\java\xsl\JSJobDoc2JSJUnitOptionSuperClass.xsl from http://www.sos-berlin.com at 20130225111725
 * \endverbatim
 *
 * \section TestData Eine Hilfe zum Erzeugen einer HashMap mit Testdaten
 *
 * Die folgenden Methode kann verwendet werden, um für einen Test eine HashMap
 * mit sinnvollen Werten für die einzelnen Optionen zu erzeugen.
 *
 * \verbatim
 private HashMap <String, String> SetJobSchedulerSSHJobOptions (HashMap <String, String> pobjHM) {
	pobjHM.put ("		CreateGenerateOrderOptionsJUnitTest.auth_file", "test");  // This parameter specifies the path and name of a user's pr
		return pobjHM;
  }  //  private void SetJobSchedulerSSHJobOptions (HashMap <String, String> pobjHM)
 * \endverbatim
 */
public class CreateGenerateOrderOptionsJUnitTest extends  JSToolBox {
	private final String					conClassName						= "CreateGenerateOrderOptionsJUnitTest"; //$NON-NLS-1$
		@SuppressWarnings("unused") 
	private static Logger		logger			= Logger.getLogger(CreateGenerateOrderOptionsJUnitTest.class);
	@SuppressWarnings("unused")
	private static Log4JHelper	objLogger		= null;
	private CreateGenerateOrder objE = null;

	protected CreateGenerateOrderOptions	objOptions			= null;

	public CreateGenerateOrderOptionsJUnitTest() {
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
		objE = new CreateGenerateOrder();
		objE.registerMessageListener(this);
		objOptions = objE.Options();
		objOptions.registerMessageListener(this);

		JSListenerClass.bolLogDebugInformation = true;
		JSListenerClass.intMaxDebugLevel = 9;
	}

	@After
	public void tearDown() throws Exception {
	}




/**
 * \brief testhibernate_configuration_file :
 *
 * \details
 *
 *
 */
    @Test
    public void testhibernate_configuration_file() {  // SOSOptionString
    	 objOptions.hibernate_configuration_file.Value("++----++");
    	 assertEquals ("", objOptions.hibernate_configuration_file.Value(),"++----++");

    }



/**
 * \brief testnumber_of_documents :
 *
 * \details
 *
 *
 */
    @Test
    public void testnumber_of_documents() {  // SOSOptionString
    	 objOptions.number_of_documents.Value("++----++");
    	 assertEquals ("", objOptions.number_of_documents.Value(),"++----++");

    }



/**
 * \brief testnumber_of_documents_probe :
 *
 * \details
 *
 *
 */
    @Test
    public void testnumber_of_documents_probe() {  // SOSOptionString
    	 objOptions.number_of_documents_probe.Value("++----++");
    	 assertEquals ("", objOptions.number_of_documents_probe.Value(),"++----++");

    }



/**
 * \brief testnumber_of_sample_documents :
 *
 * \details
 *
 *
 */
    @Test
    public void testnumber_of_sample_documents() {  // SOSOptionString
    	 objOptions.number_of_sample_documents.Value("++----++");
    	 assertEquals ("", objOptions.number_of_sample_documents.Value(),"++----++");

    }



/**
 * \brief testoutput_file_name :
 *
 * \details
 *
 *
 */
    @Test
    public void testoutput_file_name() {  // SOSOptionString
    	 objOptions.output_file_name.Value("++----++");
    	 assertEquals ("", objOptions.output_file_name.Value(),"++----++");

    }



/**
 * \brief testsource_id :
 *
 * \details
 *
 *
 */
    @Test
    public void testsource_id() {  // SOSOptionString
    	 objOptions.source_id.Value("++----++");
    	 assertEquals ("", objOptions.source_id.Value(),"++----++");

    }



/**
 * \brief testworkflow_execution_id :
 *
 * \details
 *
 *
 */
    @Test
    public void testworkflow_execution_id() {  // SOSOptionString
    	 objOptions.workflow_execution_id.Value("++----++");
    	 assertEquals ("", objOptions.workflow_execution_id.Value(),"++----++");

    }



} // public class CreateGenerateOrderOptionsJUnitTest
