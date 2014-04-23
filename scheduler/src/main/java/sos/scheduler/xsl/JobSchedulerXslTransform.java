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
package sos.scheduler.xsl;

import static com.sos.scheduler.messages.JSMessages.JSJ_F_107;
import static com.sos.scheduler.messages.JSMessages.JSJ_I_110;
import static com.sos.scheduler.messages.JSMessages.JSJ_I_111;

import java.io.File;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.sos.JSHelper.Basics.JSJobUtilitiesClass;
import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.JSHelper.io.Files.JSFile;
import com.sos.JSHelper.io.Files.JSXMLFile;

/**
 * \class 		JobSchedulerXslTransform - Workerclass for "JobSchedulerXslTransform"
 *
 * \brief AdapterClass of JobSchedulerXslTransform for the SOSJobScheduler
 *
 * This Class JobSchedulerXslTransform is the worker-class.
 *

 *
 * see \see J:\E\java\development\com.sos.scheduler\src\sos\scheduler\jobdoc\JobSchedulerXslTransform.xml for (more) details.
 *
 * \verbatim ;
 * mechanicaly created by C:\Users\KB\eclipse\sos.scheduler.xsl\JSJobDoc2JSWorkerClass.xsl from http://www.sos-berlin.com at 20110815114219
 * \endverbatim
 *
 * \Version $Id: JobSchedulerXslTransform.java 19231 2013-02-23 14:33:09Z kb $
 */
public class JobSchedulerXslTransform extends JSJobUtilitiesClass <JobSchedulerXslTransformOptions>  {
	private final String						conClassName		= "JobSchedulerXslTransform";
	private static Logger						logger				= Logger.getLogger(JobSchedulerXslTransform.class);
	private final String						conSVNVersion		= "$Id: JobSchedulerXslTransform.java 19231 2013-02-23 14:33:09Z kb $";

//	protected JobSchedulerXslTransformOptions	objOptions			= null;
//	private JSJobUtilities						objJSJobUtilities	= this;

	protected HashMap<String, String>			hsmParameters		= null;

	/**
	 *
	 * \brief JobSchedulerXslTransform
	 *
	 * \details
	 *
	 */
	public JobSchedulerXslTransform() {
		super(new JobSchedulerXslTransformOptions());
	}

	/**
	 *
	 * \brief Options - returns the JobSchedulerXslTransformationOptionClass
	 *
	 * \details
	 * The JobSchedulerXslTransformationOptionClass is used as a Container for all Options (Settings) which are
	 * needed.
	 *
	 * \return JobSchedulerXslTransformationOptions
	 *
	 */
	@Override
	public JobSchedulerXslTransformOptions Options() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Options"; //$NON-NLS-1$

		if (objOptions == null) {
			objOptions = new JobSchedulerXslTransformOptions();
		}
		return objOptions;
	}

	/**
	 *
	 * \brief Execute - Start the Execution of JobSchedulerXslTransform
	 *
	 * \details
	 *
	 * For more details see
	 *
	 * \see JobSchedulerAdapterClass
	 * \see JobSchedulerXslTransformationMain
	 *
	 * \return JobSchedulerXslTransform
	 *
	 * @return
	 */
	public JobSchedulerXslTransform Execute() throws Exception {
		final String conMethodName = conClassName + "::Execute"; //$NON-NLS-1$

		logger.debug(JSJ_I_110.get(conMethodName));
		logger.info(conSVNVersion);

		try {
			Options().CheckMandatory();
			logger.debug(Options().dirtyString());

			// TODO Parameter für das XSLT reinreichen (alles was mit xslt: beginnt, allerdings ohne das xslt:)
			// TODO EnvironmentCheck als Option einbauen
			JSXMLFile objXMLFile = new JSXMLFile(Options().FileName.Value());
			// requires ant.main ? This is a know bug. I dont't know on which version of xalan it is solved.
//			objXMLFile.EnvironmentCheck();

			// Copy only, with resolve of xincclude tags
			if (Options().XslFileName.IsEmpty() == true) {
				logger.info("no xslt-file specified. copy xml file only");
				String strXML = objXMLFile.getContent();
				JSFile outFile = new JSFile(Options().OutputFileName.Value());
				// TODO charset as option
				outFile.setCharSet4OutputFile("UTF-8");
				outFile.Write(strXML);
				outFile.close();
			}
			else {
				objXMLFile.setParameters(hsmParameters);
				objXMLFile.Transform(new File(Options().XslFileName.Value()), new File(Options().OutputFileName.Value()));
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			throw new JobSchedulerException(JSJ_F_107.get(conMethodName) + ": "+ e.getMessage(), e);
		}
		finally {
		}

		JSJ_I_111.toLog(conMethodName);
		return this;
	}

	public void init() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::init"; //$NON-NLS-1$
		doInitialize();
	}

	public void setParameters(final HashMap<String, String> pobjHshMap) {
		hsmParameters = pobjHshMap;
	}

	private void doInitialize() {
	} // doInitialize

} // class JobSchedulerXslTransform
