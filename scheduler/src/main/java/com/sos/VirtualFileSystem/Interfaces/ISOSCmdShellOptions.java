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
package com.sos.VirtualFileSystem.Interfaces;

import com.sos.JSHelper.Options.SOSOptionCommandString;
import com.sos.JSHelper.Options.SOSOptionString;

public interface ISOSCmdShellOptions {

	/**
	 * \brief getcommand_script_file : Script file name to Execute The va
	 *
	 * \details
	 * The value of this parameter contains the file-name (and path-name, if needed) of a local (script-)file, which will be transferred to the remote host and will then be executed there. The script can access job- and order-parameters by environment variables. The names of the environment variables are in upper case and have the string "SCHEDULER_PARAM_" as a prefix. Order parameters with the same name overwrite task parameters. This parameter can be used as an alternative to command , command_delimiter and command_script .
	 *
	 * \return Script file name to Execute The va
	 *
	 */
	public  SOSOptionCommandString getcommand_script_file();

	/**
	 * \brief setcommand_script_file : Script file name to Execute The va
	 *
	 * \details
	 * The value of this parameter contains the file-name (and path-name, if needed) of a local (script-)file, which will be transferred to the remote host and will then be executed there. The script can access job- and order-parameters by environment variables. The names of the environment variables are in upper case and have the string "SCHEDULER_PARAM_" as a prefix. Order parameters with the same name overwrite task parameters. This parameter can be used as an alternative to command , command_delimiter and command_script .
	 *
	 * @param command_script_file : Script file name to Execute The va
	 */
	public  void setcommand_script_file(SOSOptionCommandString p_command_script_file);

	/**
	 * \brief getCommand_Line_options : Command_Line_options
	 *
	 * \details
	 *
	 *
	 * \return Command_Line_options
	 *
	 */
	public  SOSOptionString getCommand_Line_options();

	/**
	 * \brief setCommand_Line_options : Command_Line_options
	 *
	 * \details
	 *
	 *
	 * @param CommandLineOptions : Command_Line_options
	 */
	public  void setCommand_Line_options(SOSOptionString p_Command_Line_options);

	/**
	 * \brief getshell_command :
	 *
	 * \details
	 *
	 *
	 * \return
	 *
	 */
	public  SOSOptionString getshell_command();

	/**
	 * \brief setshell_command :
	 *
	 * \details
	 *
	 *
	 * @param shell_command :
	 */
	public  void setshell_command(SOSOptionString p_shell_command);

	public SOSOptionString getStart_Shell_command();

	public SOSOptionString getShell_command_Parameter();

	public void setShell_command_Parameter(final SOSOptionString pstrValue);

	public void setStart_Shell_command(final SOSOptionString pstrValue);

	public SOSOptionString getOS_Name();

	public void setOS_Name(final SOSOptionString pstrValue);

	public SOSOptionString getStart_Shell_command_Parameter();

	public void setStart_Shell_command_Parameter(final SOSOptionString pstrValue);

}
