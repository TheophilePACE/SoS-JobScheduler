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
package com.sos.jobnet.jobs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.apache.log4j.Logger;

import sos.scheduler.job.JobSchedulerJobAdapter;
import sos.spooler.Order;
import sos.spooler.Variable_set;
import sos.spooler.Web_service_operation;
import sos.spooler.Web_service_response;

import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.jobnet.options.ListFilesWebServiceOptions;
// Super-Class for JobScheduler Java-API-Jobs
/**
 * \class 		ListFilesWebServiceJSAdapterClass - JobScheduler Adapter for "Returns folder content"
 *
 * \brief AdapterClass of ListFilesWebService for the SOSJobScheduler
 *
 * This Class ListFilesWebServiceJSAdapterClass works as an adapter-class between the SOS
 * JobScheduler and the worker-class ListFilesWebService.
 *

 *
 * see \see C:\Users\oh\AppData\Local\Temp\scheduler_editor-7579366387269665015.html for more details.
 *
 * \verbatim ;
 * mechanicaly created by C:\ProgramData\sos-berlin.com\jobscheduler\scheduler.oh\config\JOETemplates\java\xsl\JSJobDoc2JSAdapterClass.xsl from http://www.sos-berlin.com at 20130710012632
 * \endverbatim
 */
public class ListFilesWebServiceJSAdapterClass extends JobSchedulerJobAdapter  {
	private final String					conClassName						= "ListFilesWebServiceJSAdapterClass";
	private static Logger		logger			= Logger.getLogger(ListFilesWebServiceJSAdapterClass.class);
	protected ListFilesWebServiceOptions	objOptions			= null;

	public void init() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::init";
		doInitialize();
	}

	private void doInitialize() {
	} // doInitialize

	@Override
	public boolean spooler_init() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::spooler_init";
		return super.spooler_init();
	}

	@Override
	public boolean spooler_process() throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::spooler_process";

		try {
			super.spooler_process();
			doProcessing();
		}
		catch (Exception e) {
            throw new JobSchedulerException("Fatal Error:" + e.getMessage(), e);
   		}
		finally {
		} // finally
        return signalSuccess();

	} // spooler_process

	@Override
	public void spooler_exit() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::spooler_exit";
		super.spooler_exit();
	}

	private void doProcessing() throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::doProcessing";

		ListFilesWebServiceOptions objO = this.Options();

        objO.CurrentNodeName(this.getCurrentNodeName());
		objO.setAllOptions(getSchedulerParameterAsProperties(getJobOrOrderParameters()));
//		objO.CheckMandatory();
        this.setJSJobUtilites(this);
		this.Execute();
	} // doProcessing
	
	
	
	private void Execute() {
		Order order = spooler_task.order();
		Web_service_operation operation = null;
		String responseStr = "";
		String charset = "UTF-8";
		ByteArrayOutputStream byos = new ByteArrayOutputStream();
		Writer writer = null;
		
		try {
			operation = order.web_service_operation();
			Variable_set webServiceParams = operation.web_service().params();
			Options().source_dir.Value(webServiceParams.value("source_dir"));
			Options().base_url.Value(webServiceParams.value("base_url"));
		}
		catch (Exception e) {
			throw new JobSchedulerException("There is no Web Service operation attached to this order", e);
		}
		Options().CheckMandatory();
		String source_dir = Options().source_dir.Value();
		String base_url = Options().base_url.Value();
		
		File folder = new File(source_dir);
		if (folder.isDirectory() == false) {
			throw new JobSchedulerException(String.format("%1$s is not a directory", source_dir));
		}
		
		File[] files = folder.listFiles();
		
		responseStr = "<?xml version=\"1.0\" encoding=\""+charset+"\" ?>";
		responseStr += "<folder path=\""+source_dir+"\" url=\""+base_url+"\" length=\""+files.length+"\">";
		for(File file : files) {
			responseStr += "<file name=\""+file.getName()+"\" modifcation_date=\""+file.lastModified()+"\" size=\""+file.length()+"\"/>";
		}
		responseStr += "</folder>";
		
		try {
			writer = new OutputStreamWriter( byos, charset );
			writer.write( responseStr );
		}
		catch (UnsupportedEncodingException e) {
			throw new JobSchedulerException(e);
		}
		catch (IOException e) {
			throw new JobSchedulerException(e);
		}
		finally {
			if(writer != null) {
				try {
					writer.close();
				}
				catch (IOException e) {}
			}
		}
	    
		Web_service_response response = operation.response();
		
		response.set_content_type( "text/xml" );
	    response.set_charset_name( charset );
	    response.set_binary_content( byos.toByteArray() );
		
//		response.set_string_content(responseStr);
		response.send();
	}

	/**
	 * 
	 * \brief Options - returns the ListFilesWebServiceOptionClass
	 * 
	 * \details
	 * The ListFilesWebServiceOptionClass is used as a Container for all Options (Settings) which are
	 * needed.
	 *  
	 * \return ListFilesWebServiceOptions
	 *
	 */
	public ListFilesWebServiceOptions Options() {

		@SuppressWarnings("unused")  
		final String conMethodName = conClassName + "::Options";  //$NON-NLS-1$

		if (objOptions == null) {
			objOptions = new ListFilesWebServiceOptions();
		}
		return objOptions;
	}

}

