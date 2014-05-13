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
package com.sos.VirtualFileSystem.shell;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.log4j.Logger;

import com.sos.VirtualFileSystem.Interfaces.ISOSCmdShellOptions;
import com.sos.VirtualFileSystem.common.SOSVfsMessageCodes;

/**
* \class cmdShell
*
* \brief cmdShell -
*
* \details
*
*
* \code
*   .... code goes here ...
* \endcode
*
* <p style="text-align:center">
* <br />---------------------------------------------------------------------------
* <br /> APL/Software GmbH - Berlin
* <br />##### generated by ClaviusXPress (http://www.sos-berlin.com) #########
* <br />---------------------------------------------------------------------------
* </p>
* \author KB
* \version $Id: cmdShell.java 18975 2013-02-02 11:57:37Z kb $
* \see reference
*
* Created on 21.08.2011 14:56:56
 */

/**
 * @author KB
 *
 */
public class cmdShell extends SOSVfsMessageCodes implements Runnable {

	// / Betrachtungen zum Thema Encoding:
	// http://www.torsten-horn.de/techdocs/encoding.htm#Codepage-Konsolenausgabe
	// private static final String strCharacterEncoding = "ISO-8859-1";
	// private static final String strCharacterEncoding = "windows-1252";
	private static final String	strCharacterEncoding	= "Cp1252";
	private final String		conClassName			= "cmdShell";
	@SuppressWarnings("unused")
	private static final String	conSVNVersion			= "$Id: cmdShell.java 18975 2013-02-02 11:57:37Z kb $";
	private static final Logger	logger					= Logger.getLogger(cmdShell.class);

	private String				strStdOut				= "";
	private String				strStdErr				= "";
	private int					intCC					= 0;
	String						osn						= System.getProperty("os.name");
	String						fcp						= System.getProperty("file.encoding");
	String						ccp						= System.getProperty("console.encoding");

	private ISOSCmdShellOptions	objShellOptions			= null;

	public cmdShell() {
		//
	}

	public cmdShell(final ISOSCmdShellOptions pobjOptions) {
		objShellOptions = pobjOptions;
	}

	public boolean isWindows() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::isWindows";

		boolean flgOK = osn != null && osn.contains("Windows");

		return flgOK;
	} // private boolean isWindows

	public int getCC() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getCC";

		return intCC;
	} // private int getCC

	public String getStdOut() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getStdOut";

		return strStdOut;
	} // private String getStdOut

	public String getStdErr() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getStdErr";

		return strStdErr;
	} // private String getStdErr

	class OutputPipe implements Runnable {

		private final InputStream	in;
		private final PrintStream	out;

		/** Creates a new {@code OutputPipe}. */
		OutputPipe(final InputStream in1, final PrintStream out1) {
			in = in1;
			out = out1;
		}

		@Override
		public void run() {
			try {
				byte[] buffer = new byte[1024];
				for (int n = 0; n != -1; n = in.read(buffer)) {
					out.write(buffer, 0, n);
				}
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private int executeCommand_(final String[] pstrCommand, final boolean showCommand) throws Exception {

		ByteArrayOutputStream bytStdOut = new ByteArrayOutputStream();
		ByteArrayOutputStream bytStdErr = new ByteArrayOutputStream();
		PrintStream psStdOut = new PrintStream(bytStdOut, true, strCharacterEncoding);
		PrintStream psStdErr = new PrintStream(bytStdErr, true, strCharacterEncoding);
		strStdOut = "";
		strStdErr = "";
		ProcessBuilder objShell = null;

		if (showCommand) {
			logger.debug(SOSVfs_D_0151.params(pstrCommand));
		}
		objShell = new ProcessBuilder(pstrCommand);

		final Process objCommand = objShell.start();
		createOutputPipe(objCommand.getInputStream(), psStdOut);
		createOutputPipe(objCommand.getErrorStream(), psStdErr);
		pipein(System.in, objCommand.getOutputStream());

		// wait for process to terminate and exit with process' return code
		intCC = objCommand.waitFor();
		strStdOut = bytStdOut.toString(strCharacterEncoding);
		strStdErr = bytStdErr.toString(strCharacterEncoding); // e.g. ISO-8859-1

		logger.debug(strStdOut);
		logger.debug(strStdErr);
		return intCC;
	}

	private String strCommand = "";
	public void setCommand(final String pstrcommand) throws Exception {
		strCommand = pstrcommand;
	}


	public int executeCommand(final String pstrcommand) throws Exception {
		int intRet = executeCommand_(createCommand(pstrcommand), true);
		return intRet;
	}

	public int executeCommand(final ISOSCmdShellOptions pobjOptions) throws Exception {
		objShellOptions = pobjOptions;
		String strCommand[] = createCommandUsingOptions();
		int intRet = executeCommand_(strCommand, true);
		return intRet;
	}

	private String[] createCommands (final String pstrComSpec, final String pstrComSpecDefault, final String pstrStartShellCommandParameter) {
		final String[] command = {" ", " ", " "};
		String strComSpec = "";
		int intCmdIndex = 0;

		String strStartShellCommandParameter = pstrStartShellCommandParameter ; // "-c";
		if (objShellOptions.getStart_Shell_command().isDirty()) {
			strComSpec = objShellOptions.getStart_Shell_command().Value();
			if (strComSpec.equalsIgnoreCase("none") == false) {
				command[intCmdIndex++] = strComSpec;
				if (objShellOptions.getStart_Shell_command_Parameter().isDirty()) {
					strStartShellCommandParameter = objShellOptions.getStart_Shell_command_Parameter().Value();
					command[intCmdIndex++] = strStartShellCommandParameter;
				}
				command[intCmdIndex++] = objShellOptions.getshell_command().Value() + " " + objShellOptions.getCommand_Line_options().Value() + " "
						+ objShellOptions.getShell_command_Parameter().Value();
			}
			else {
				command[intCmdIndex++] = objShellOptions.getshell_command().Value();
				command[intCmdIndex++] = objShellOptions.getCommand_Line_options().Value() + " " + objShellOptions.getShell_command_Parameter().Value();
			}
		}
		else {
			strComSpec = System.getenv(pstrComSpec);  //  "SHELL");
			if (strComSpec == null) {
				strComSpec = pstrComSpecDefault; //  "/bin/sh";
			}
			command[intCmdIndex++] = strComSpec;
			command[intCmdIndex++] = strStartShellCommandParameter;
			command[intCmdIndex++] = objShellOptions.getshell_command().Value() + " " + objShellOptions.getCommand_Line_options().Value() + " "
					+ objShellOptions.getShell_command_Parameter().Value();
		}
		logger.debug(SOSVfs_D_230.params(strComSpec));
		return command;
	}
	private String[] createCommandUsingOptions() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::createCommandUsingOptions";

		if (isWindows()) {
			return createCommands("comspec", "cmd.exe", "/C");
		}
		else { // Unix, Linux?
			return createCommands("SHELL", "bin.sh", "-c");
		}

	} // private String[] createCommandUsingOptions

	private String[] createCommand(final String pstrCommand) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::createCommandUsingOptions";
		final String[] command = new String[2 + 1];

		if (isWindows()) {
			String strComSpec = System.getenv("comspec");
			logger.debug(SOSVfs_D_230.params(strComSpec));
			command[0] = strComSpec; // "cmd";
			command[1] = "/C";
			//			command[2] = pstrcommand.replaceAll("/", "\\\\");
			command[2] = pstrCommand;
		}
		else { // Unix, Linux?
			String strComSpec = System.getenv("SHELL");
			if (strComSpec == null) {
				strComSpec = "/bin/sh";
			}
			logger.debug(SOSVfs_D_230.params(strComSpec));
			command[0] = strComSpec; // "/bin/sh";
			command[1] = "-c";
			command[2] = pstrCommand;
		}

		return command;
	} // private String[] createCommand

	public int executeCommandWithoutDebugCommand(final String pstrCommand) throws Exception {
		return executeCommand_(createCommand(pstrCommand), false);
	}

	private void pipein(final InputStream src, final OutputStream dest) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					int ret = -1;
					while ((ret = src.read()) != -1) {
						dest.write(ret);
						dest.flush();
					}
				}
				catch (IOException e) { // just exit
				}
			}
		}).start();

	}

	private void createOutputPipe(final InputStream in, final PrintStream out) {
		new Thread(new OutputPipe(in, out)).start();
	}

	@Override
	public void run() {

		try {
			this.executeCommand(strCommand);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	public static void main(final String[] args) throws Exception {

		String osn = System.getProperty("os.name");
		String fcp = System.getProperty("file.encoding");
		String ccp = System.getProperty("console.encoding");

		logger.info(osn + ", fcp =  " + fcp + ", ccp = " + ccp);
		// if (args.length < 1) {
		// System.err.println("Syntax: BatchFileStarter command [command [...]]");
		// System.exit(1);
		// }

		// command chain
		// final String[] command = new String[2 + args.length];
		cmdShell objShell = new cmdShell();
		int intCC = 0;
		intCC = objShell.executeCommand("C:/Users/KB/Desktop/filezilla_start.bat");
		//		intCC = objShell.executeCommand("dir");
		//		intCC = objShell.executeCommand("dir bin");
		// xcopy bleibt h�ngen. scheint von stdin lesen zu wollen und bekommt nichts?
		// Mit echo 1 | sqlcmd funktioniert es. Ohne geht es nicht bei �lteren Versionen.
		//		intCC = objShell.executeCommand("xcopy conf c:\\temp\\conf /F /Y");
		//		intCC = objShell.executeCommand("xcopy bin c:\\temp\\bin /I /F /Y /J");
		logger.debug(SOSVfs_D_231.params(intCC));
	}
	*/
}
