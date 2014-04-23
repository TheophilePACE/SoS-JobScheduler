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
package sos.scheduler.editor.app;

import java.io.InputStream;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import sos.scheduler.editor.classes.SOSSplashScreen;
import sos.util.SOSClassUtil;

import com.sos.JSHelper.Logging.Log4JHelper;
import com.sos.i18n.annotation.I18NResourceBundle;

@I18NResourceBundle(baseName = "JOEMessages", defaultLocale = "en")
public class Editor {
	private final static String	conSVNVersion			= "$Id: Editor.java 20654 2013-07-05 08:16:09Z kb $";
	private static Logger		logger					= Logger.getLogger(Editor.class);
	@SuppressWarnings("unused")
	private final String		conClassName			= "Editor";
	@SuppressWarnings("unused")
	private static Log4JHelper	objLogger				= null;
	public static MainWindow	objMainWindow			= null;

	public final static int		CONFIG					= 1;
	public final static int		BASEFILE					= 2;
	public final static int		SECURITY				= 3;
	public final static int		CLUSTER					= 4;
	public final static int		PROCESS_CLASSES			= 5;
	public final static int		LOCKS					= 6;
	public final static int		SCRIPT					= 7;
	public final static int		WEBSERVICES				= 8;
	public final static int		HTTPDIRECTORIES			= 9;
	public final static int		HOLIDAYS				= 10;
	public final static int		JOBS					= 11;
	public final static int		JOB_CHAINS				= 12;
	public final static int		COMMANDS				= 13;
	public final static int		ORDER					= 14;
	public final static int		ORDERS					= 15;
	public final static int		JOB						= 16;
	public final static int		EXECUTE					= 17;
	public final static int		MONITOR					= 18;
	public final static int		OPTIONS					= 19;
	public final static int		LOCKUSE					= 20;
	public final static int		RUNTIME					= 21;
	public final static int		JOB_COMMANDS			= 22;
	public final static int		JOB_COMMAND				= 23;
	public final static int		WEEKDAYS				= 24;
	public final static int		MONTHDAYS				= 25;
	public final static int		ULTIMOS					= 26;
	public final static int		SPECIFIC_WEEKDAYS		= 27;
	public final static int		DAYS					= 28;
	public final static int		PERIODS					= 29;
	public final static int		EVERYDAY				= 30;
	public final static int		SPECIFIC_MONTHS			= 31;
	public final static int		DOC_JOB					= 50;
	public final static int		DOC_PROCESS				= 51;
	public final static int		DOC_SCRIPT				= 52;
	public final static int		DOC_MONITOR				= 53;
	public final static int		DOC_RELEASES			= 54;
	public final static int		DOC_RESOURCES			= 56;
	public final static int		DOC_DATABASES			= 57;
	public final static int		DOC_FILES				= 58;
	public final static int		DOC_DOCUMENTATION		= 59;
	public final static int		DOC_CONFIGURATION		= 60;
	public final static int		DOC_PARAMS				= 61;
	public final static int		DOC_PAYLOAD				= 63;
	public final static int		DOC_SECTIONS			= 65;
	public final static int		DOC_SETTINGS			= 66;
	public final static int		DOC_SECTION_SETTINGS	= 67;
	public final static int		DOC_PROFILES			= 68;
	public final static int		DOC_CONNECTIONS			= 69;
	public final static int		DOC_APPLICATIONS		= 70;
	public final static int		JOB_WIZARD				= 71;
	public final static int		DETAILS					= 72;
	public final static int		JOB_CHAIN				= 73;
	public final static int		HTTP_AUTHENTICATION		= 74;
	public final static int		PARAMETER				= 75;
	public final static int		JOB_COMMAND_EXIT_CODES	= 76;
	public final static int		SCHEDULES				= 77;
	public final static int		SCHEDULE				= 78;
	public final static int		MONITORS				= 79;
	public final static int		WEBSERVICE				= 80;
	public final static int		HTTP_SERVER				= 81;
	public final static int		JOB_CHAIN_NODES			= 82;
	public final static int		JOB_CHAIN_NESTED_NODES	= 83;
	public final static int		DOC_RELEASE				= 84;
	public final static int		DOC_RELEASE_AUTHOR		= 85;
	public final static int		DOC_DATABASES_RESOURCE	= 86;
	public final static int		SETTINGS				= 87;
	public final static int		ACTIONS					= 88;
	public final static int		ACTION					= 89;
	public final static int		EVENTS					= 90;
	public final static int		EVENT_GROUP				= 91;
	public final static int		ACTION_COMMANDS			= 92;
	public final static int		ADD_EVENT_GROUP			= 93;
	public final static int		REMOVE_EVENT_GROUP		= 94;
	public final static int		JOB_OPTION				= 95;
	public final static int		JOB_DOCUMENTATION		= 96;
	public static String		SCHEDULER_ENCODING		= "";
	public static String		DOCUMENTATION_ENCODING	= "utf-8";

	private static MainWindow	window					= null;
	private static Display		display					= null;

	public static void main(final String[] args) {
		try {
			display = Display.getDefault();
			window = new MainWindow();
			window.createSShell();

			final Shell shell = MainWindow.getSShell();

			Method objApplicationMainMethod = Editor.class.getMethod("openApplicationMainWnd", new Class[] { Shell.class });

			Image objImage4Splash = null;
			if (Options.showSplashScreen() == true) {
				InputStream img = Editor.class.getResourceAsStream("/SplashScreenJOE.bmp");
				if (img == null) {
					System.out.println("'/SplashScreenJOE.bmp' not found in resources.");
				}
				else {
					objImage4Splash = new Image(display, img);

				}

			}
			SOSSplashScreen.startJOEExecuteLoop(shell, new Runnable() {
				@Override
				public void run() {
					doSomeTimeconsumingOperation();
				}
			}, objImage4Splash, objApplicationMainMethod, 2000);

		}
		catch (Exception e) {
			try {
				logger.fatal("sudden death", e);
				e.printStackTrace();
				new ErrorLog("error in " + SOSClassUtil.getMethodName() + "cause: " + e.toString(), e);
			}
			catch (Exception ee) {
			}
		}
	}

	private static void doSomeTimeconsumingOperation() {
		try {
			objLogger = new Log4JHelper("./JOE-log4j.properties");

			logger = Logger.getRootLogger();
			logger.debug(conSVNVersion);

			window.OpenLastFolder();

			objMainWindow = window;

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void openApplicationMainWnd(final Shell shell) {
		MainWindow.getSShell().open();
		MainWindow.getSShell().update();

		while (!MainWindow.getSShell().isDisposed()) {

			try {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
			catch (Exception e) {
				MainWindow.getSShell().redraw();
			}
		}
		if (display.isDisposed() == false) {
			display.dispose();
		}
	}

}
