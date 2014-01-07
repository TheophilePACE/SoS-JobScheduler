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
package com.sos.dailyschedule;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import sos.scheduler.editor.app.ErrorLog;
import sos.util.SOSClassUtil;

import com.sos.JSHelper.Logging.Log4JHelper;
import com.sos.dailyschedule.dialog.DashboardShowDialog;
import com.sos.dashboard.globals.DashBoardConstants;
import com.sos.dashboard.globals.SOSDashboardOptions;
import com.sos.i18n.I18NBase;
import com.sos.i18n.annotation.I18NResourceBundle;


/**
 * \class 		SosSchedulerDashboardMain - Main-Class for "Transfer files by FTP/SFTP and execute commands by SSH"
 *
 * \brief MainClass to launch sosftp as an executable command-line program
 *
 * This Class SosSchedulerDashboardMain is the worker-class.
 *
 *
 */
@I18NResourceBundle(baseName = "SOSSchedulerDashboardMain", defaultLocale = "en")
public class SosSchedulerDashboardMain extends I18NBase {
	private final static String		conClassName			= "SosSchedulerDashboardMain";
	public static final String		conSVNVersion			= "$Id: SosSchedulerDashboardMain.java 16415 2012-02-01 17:21:40Z ur $";
	private static Logger			logger					= Logger.getLogger(SosSchedulerDashboardMain.class);
	@SuppressWarnings("unused")
	private static Log4JHelper		objLogger				= null;
	protected SOSDashboardOptions	objOptions				= null;
	public static final String		SOSDashBoard_Intro		= "SosSchedulerDashboardMain.SOSDB-Intro";
	public static final String		SOSDashboard_E_0001		= "SosSchedulerDashboardMain.SOSDX_E_0001";
	public static final String		SOS_EXIT_WO_ERRORS		= "SosSchedulerDashboardMain.SOS_EXIT_WO_ERRORS";
	public static final String		SOS_EXIT_CODE_RAISED	= "SosSchedulerDashboardMain.SOS_EXIT_CODE_RAISED";

	/**
	 * 
	 * \brief main
	 * 
	 * \details
	 *
	 * \return void
	 *
	 * @param pstrArgs
	 * @throws Exception
	 */
	public final static void main(final String[] pstrArgs) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Main"; //$NON-NLS-1$
		SosSchedulerDashboardMain objEngine = new SosSchedulerDashboardMain();
		objEngine.execute(pstrArgs);
	}

	protected SosSchedulerDashboardMain() {
		super(DashBoardConstants.conPropertiesFileName);
	/*	try {
		 	AddAnnotation.addPersonneNameAnnotationToMethod("com.sos.scheduler.history.db.SchedulerHistoryLogDBItem", "getLog");
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}

	private void execute(final String[] pstrArgs) {
		final String conMethodName = conClassName + "::Execute";
		try {
			objLogger = new Log4JHelper("./log4j.properties"); //$NON-NLS-1$
			logger = Logger.getRootLogger();
			logger.info(Messages.getMsg(SOSDashBoard_Intro)); // $NON-NLS-1$
			logger.info(conSVNVersion);
			Shell shell = new Shell();
			try {

				Composite composite = new Composite(shell, SWT.NONE);
				SOSDashboardOptions objOptions = new SOSDashboardOptions();
				if (pstrArgs.length > 0) {
					logger.debug("pstrArgs = " + pstrArgs[0].toString());
					logger.debug("user-dir = " + System.getProperty("user.dir"));
					objOptions.CommandLineArgs(pstrArgs);
				}
				else {
					objOptions.enableJOC.value(true);
					objOptions.enableJOE.value(true);
					objOptions.enableEvents.value(true);
					objOptions.enableJobnet.value(false);

					objOptions.enableJobStart.value(true);
				}
				objOptions.CheckMandatory();
				logger.debug(objOptions.toString());
				DailyScheduleDataProvider dataProvider = new DailyScheduleDataProvider(objOptions.hibernateConfigurationFile.JSFile());
				DashboardShowDialog window = new DashboardShowDialog(composite);

				try{
	                  window.setDataProvider(dataProvider);
				}catch (Exception e){
 					MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION);
				    messageBox.setMessage(Messages.getLabel(DashBoardConstants.conSOSDashB_CouldNotConnect));
				    int rc = messageBox.open();
					
					objOptions.enableJobnet.value(false);
					
				}
				window.setObjOptions(objOptions);
				window.open();
				logger.debug("...............quit");
			}
			catch (Exception e) {
			    e.printStackTrace();
				throw e;
			}
		}
		
		  catch (Exception e) {
	            try {
	                logger.fatal("sudden death", e);
	                e.printStackTrace();
	                new ErrorLog("JID", "error in " + SOSClassUtil.getMethodName() + "cause: " + e.toString(), e);
	                int intExitCode = 99;
	                logger.error(String.format(getMsg(SOS_EXIT_CODE_RAISED), conMethodName, intExitCode), e);
	                System.exit(intExitCode);
	            }
	            catch (Exception ee) {
	            }
	        }
		
	} // private void Execute
} // class SosSchedulerDashboardMain
