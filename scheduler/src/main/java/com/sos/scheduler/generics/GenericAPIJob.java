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
package com.sos.scheduler.generics;

import org.apache.log4j.Logger;

import com.sos.JSHelper.Basics.JSJobUtilitiesClass;

/**
 * \class 		GenericAPIJob - Workerclass for "A generic internal API job"
 *
 * \brief AdapterClass of GenericAPIJob for the SOSJobScheduler
 *
 * This Class GenericAPIJob is the worker-class.
 *

 *
 * see \see C:\Users\KB\AppData\Local\Temp\scheduler_editor-2864692299059909179.html for (more) details.
 *
 * \verbatim ;
 * mechanicaly created by C:\Users\KB\sos-berlin.com\jobscheduler\scheduler\config\JOETemplates\java\xsl\JSJobDoc2JSWorkerClass.xsl from http://www.sos-berlin.com at 20120611173607
 * \endverbatim
 */
public class GenericAPIJob extends JSJobUtilitiesClass <GenericAPIJobOptions> {
	private final String			conClassName		= "GenericAPIJob";							//$NON-NLS-1$
	private static Logger			logger				= Logger.getLogger(GenericAPIJob.class);

//	protected GenericAPIJobOptions	objOptions			= null;
//	private JSJobUtilities			objJSJobUtilities	= this;

	/**
	 *
	 * \brief GenericAPIJob
	 *
	 * \details
	 *
	 */
	public GenericAPIJob() {
		super(new GenericAPIJobOptions());
	}

	/**
	 *
	 * \brief Options - returns the GenericAPIJobOptionClass
	 *
	 * \details
	 * The GenericAPIJobOptionClass is used as a Container for all Options (Settings) which are
	 * needed.
	 *
	 * \return GenericAPIJobOptions
	 *
	 */
	@Override
	public GenericAPIJobOptions Options() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Options"; //$NON-NLS-1$

		if (objOptions == null) {
			objOptions = new GenericAPIJobOptions();
		}
		return objOptions;
	}

	/**
	 *
	 * \brief Execute - Start the Execution of GenericAPIJob
	 *
	 * \details
	 *
	 * For more details see
	 *
	 * \see JobSchedulerAdapterClass
	 * \see GenericAPIJobMain
	 *
	 * \return GenericAPIJob
	 *
	 * @return
	 */
	public GenericAPIJob Execute() throws Exception {
		final String conMethodName = conClassName + "::Execute"; //$NON-NLS-1$

		logger.debug(String.format(Messages.getMsg("JSJ-I-110"), conMethodName));

		try {
			Options().CheckMandatory();
			logger.debug(Options().toString());
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			logger.error(String.format(Messages.getMsg("JSJ-I-107"), conMethodName), e);
		}
		finally {
			logger.debug(String.format(Messages.getMsg("JSJ-I-111"), conMethodName));
		}

		return this;
	}

	public void init() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::init"; //$NON-NLS-1$
		doInitialize();
	}

	private void doInitialize() {
	} // doInitialize

	@Override
	public String myReplaceAll(final String pstrSourceString, final String pstrReplaceWhat, final String pstrReplaceWith) {

		String newReplacement = pstrReplaceWith.replaceAll("\\$", "\\\\\\$");
		return pstrSourceString.replaceAll("(?m)" + pstrReplaceWhat, newReplacement);
	}

} // class GenericAPIJob
