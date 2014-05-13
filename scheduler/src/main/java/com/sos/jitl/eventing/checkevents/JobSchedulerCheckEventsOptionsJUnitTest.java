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


package com.sos.jitl.eventing.checkevents;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
 * \class 		JobSchedulerCheckEventsOptionsJUnitTest - Check if events exist
 *
 * \brief 
 *
 *

 *
 * 
 * \verbatim ;
 * \endverbatim
 *
 * \section TestData Eine Hilfe zum Erzeugen einer HashMap mit Testdaten
 *
 * Die folgenden Methode kann verwendet werden, um f�r einen Test eine HashMap
 * mit sinnvollen Werten f�r die einzelnen Optionen zu erzeugen.
 *
 * \verbatim
 private HashMap <String, String> SetJobSchedulerSSHJobOptions (HashMap <String, String> pobjHM) {
	pobjHM.put ("		JobSchedulerCheckEventsOptionsJUnitTest.auth_file", "test");  // This parameter specifies the path and name of a user's pr
		return pobjHM;
  }  //  private void SetJobSchedulerSSHJobOptions (HashMap <String, String> pobjHM)
 * \endverbatim
 */
public class JobSchedulerCheckEventsOptionsJUnitTest extends  JSToolBox {
	private final String					conClassName						= "JobSchedulerCheckEventsOptionsJUnitTest"; //$NON-NLS-1$
		@SuppressWarnings("unused") //$NON-NLS-1$
	private static Logger		logger			= Logger.getLogger(JobSchedulerCheckEventsOptionsJUnitTest.class);
	@SuppressWarnings("unused")
	private static Log4JHelper	objLogger		= null;
	private JobSchedulerCheckEvents objE = null;

	protected JobSchedulerCheckEventsOptions	objOptions			= null;

	public JobSchedulerCheckEventsOptionsJUnitTest() {
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
		objE = new JobSchedulerCheckEvents();
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
 * \brief testEventClassName : 
 * 
 * \details
 * 
 *
 */
    @Test
    public void testEventClassName() {  // SOSOptionString
    	 objOptions.EventClassName.Value("++----++");
    	 assertEquals ("", objOptions.EventClassName.Value(),"++----++");
    	
    }

                

/**
 * \brief testEventNames : 
 * 
 * \details
 * 
 *
 */
    @Test
    public void testEventNames() {  // SOSOptionString
    	 objOptions.EventNames.Value("++----++");
    	 assertEquals ("", objOptions.EventNames.Value(),"++----++");
    	
    }

                

/**
 * \brief testscheduler_event_class : 
 * 
 * \details
 * 
 *
 */
    @Test
    public void testscheduler_event_class() {  // SOSOptionString
    	 objOptions.scheduler_event_class.Value("++----++");
    	 assertEquals ("", objOptions.scheduler_event_class.Value(),"++----++");
    	
    }

                

/**
 * \brief testscheduler_event_exit_code : 
 * 
 * \details
 * 
 *
 */
    @Test
    public void testscheduler_event_exit_code() {  // SOSOptionString
    	 objOptions.scheduler_event_exit_code.Value("++----++");
    	 assertEquals ("", objOptions.scheduler_event_exit_code.Value(),"++----++");
    	
    }

                

/**
 * \brief testscheduler_event_handler_host : 
 * 
 * \details
 * 
 *
 */
    @Test
    public void testscheduler_event_handler_host() {  // SOSOptionHostName
    	objOptions.scheduler_event_handler_host.Value("++----++");
    	assertEquals ("", objOptions.scheduler_event_handler_host.Value(),"++----++");
    	
    }

                

/**
 * \brief testscheduler_event_handler_port : 
 * 
 * \details
 * 
 *
 */
    @Test
    public void testscheduler_event_handler_port() {  // SOSOptionPortNumber
    	objOptions.scheduler_event_handler_port.Value("++----++");
    	assertEquals ("", objOptions.scheduler_event_handler_port.Value(),"++----++");
    	
    }

                

/**
 * \brief testscheduler_event_id : 
 * 
 * \details
 * 
 *
 */
    @Test
    public void testscheduler_event_id() {  // SOSOptionString
    	 objOptions.scheduler_event_id.Value("++----++");
    	 assertEquals ("", objOptions.scheduler_event_id.Value(),"++----++");
    	
    }

                

/**
 * \brief testscheduler_event_job : 
 * 
 * \details
 * 
 *
 */
    @Test
    public void testscheduler_event_job() {  // SOSOptionString
    	 objOptions.scheduler_event_job.Value("++----++");
    	 assertEquals ("", objOptions.scheduler_event_job.Value(),"++----++");
    	
    }

                

/**
 * \brief testscheduler_event_jobchain : 
 * 
 * \details
 * 
 *
 */
    @Test
    public void testscheduler_event_jobchain() {  // SOSOptionString
    	 objOptions.scheduler_event_jobchain.Value("++----++");
    	 assertEquals ("", objOptions.scheduler_event_jobchain.Value(),"++----++");
    	
    }

                

/**
 * \brief testscheduler_event_xpath : 
 * 
 * \details
 * 
 *
 */
    @Test
    public void testscheduler_event_xpath() {  // SOSOptionString
    	 objOptions.scheduler_event_xpath.Value("++----++");
    	 assertEquals ("", objOptions.scheduler_event_xpath.Value(),"++----++");
    	
    }

                
        
} // public class JobSchedulerCheckEventsOptionsJUnitTest
