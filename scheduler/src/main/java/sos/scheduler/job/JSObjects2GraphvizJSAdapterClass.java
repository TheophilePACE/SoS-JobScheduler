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
package sos.scheduler.job;

import org.apache.log4j.Logger;

import com.sos.scheduler.converter.graphviz.JSObjects2Graphviz;
import com.sos.scheduler.converter.graphviz.JSObjects2GraphvizOptions;

// Super-Class for JobScheduler Java-API-Jobs
/**
 * \class 		JSObjects2GraphvizJSAdapterClass - JobScheduler Adapter for "JSObjects2Graphviz"
 *
 * \brief AdapterClass of JSObjects2Graphviz for the SOSJobScheduler
 *
 * This Class JSObjects2GraphvizJSAdapterClass works as an adapter-class between the SOS
 * JobScheduler and the worker-class JSObjects2Graphviz.
 *

 *
 * see \see C:\Users\KB\AppData\Local\Temp\scheduler_editor-2781494595910967227.html for more details.
 *
 * \verbatim ;
 * mechanicaly created by C:\Users\KB\sos-berlin.com\jobscheduler\scheduler\config\JOETemplates\java\xsl\JSJobDoc2JSAdapterClass.xsl from http://www.sos-berlin.com at 20121108150924
 * \endverbatim
 */
public class JSObjects2GraphvizJSAdapterClass extends JobSchedulerJobAdapter {
	private final String	conClassName	= "JSObjects2GraphvizJSAdapterClass";						//$NON-NLS-1$
	@SuppressWarnings("unused")
	private static Logger	logger			= Logger.getLogger(JSObjects2GraphvizJSAdapterClass.class);
	@SuppressWarnings("unused")
	private final String				conSVNVersion		= "$Id: SOSSSHJob2JSAdapter.java 18220 2012-10-18 07:46:10Z kb $";

	public void init() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::init"; //$NON-NLS-1$
		doInitialize();
	}

	private void doInitialize() {
	} // doInitialize

	@Override
	public boolean spooler_init() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::spooler_init"; //$NON-NLS-1$
		return super.spooler_init();
	}

	@Override
	public boolean spooler_process() throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::spooler_process"; //$NON-NLS-1$

		try {
			super.spooler_process();
			doProcessing();
		}
		catch (Exception e) {
			return false;
		}
		finally {
		} // finally
			// return value for classic and order driven processing
			// TODO create method in base-class for this functionality
		return spooler_task.job().order_queue() != null;

	} // spooler_process

	@Override
	public void spooler_exit() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::spooler_exit"; //$NON-NLS-1$
		super.spooler_exit();
	}

	private void doProcessing() throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::doProcessing"; //$NON-NLS-1$

		JSObjects2Graphviz objR = new JSObjects2Graphviz();
		JSObjects2GraphvizOptions objO = objR.Options();
		objO.setAllOptions(getSchedulerParameterAsProperties(getJobOrOrderParameters()));
		objO.CurrentNodeName(this.getCurrentNodeName());
		objO.CheckMandatory();
		objR.setJSJobUtilites(this);
		objR.Execute();
	} // doProcessing

}
