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
package sos.scheduler.db;

import static com.sos.scheduler.messages.JSMessages.JSJ_I_110;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.sos.JSHelper.Basics.JSJobUtilitiesClass;
import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.scheduler.messages.JSMessages;

/**
 * \class 		JobSchedulerPLSQLJob - Workerclass for "Launch Database Statement"
 *
 * \brief AdapterClass of JobSchedulerPLSQLJob for the SOSJobScheduler
 *
 * This Class JobSchedulerPLSQLJob is the worker-class.
 *
 * see: http://asktom.oracle.com/pls/asktom/f?p=100:11:0::::P11_QUESTION_ID:45027262935845
 *
* see also: http://berxblog.blogspot.de/2009/01/pipelined-function-vs-dbmsoutput.html
*
 *
 * see \see R:\backup\sos\java\development\com.sos.scheduler\src\sos\scheduler\jobdoc\JobSchedulerPLSQLJob.xml for (more) details.
 *
 * \verbatim ;
 * mechanicaly created by C:\Users\KB\eclipse\xsl\JSJobDoc2JSWorkerClass.xsl from http://www.sos-berlin.com at 20120905153544
 * \endverbatim
 */
public class JobSchedulerPLSQLJob extends JSJobUtilitiesClass <JobSchedulerPLSQLJobOptions> {
	protected static final String	conSettingDBMS_OUTPUT	= "dbmsOutput";
	private final String					conClassName		= "JobSchedulerPLSQLJob";						//$NON-NLS-1$
	private static Logger					logger				= Logger.getLogger(JobSchedulerPLSQLJob.class);

//	protected JobSchedulerPLSQLJobOptions	objOptions			= null;
//	private final JSJobUtilities			objJSJobUtilities	= this;

	private CallableStatement				cs					= null;
	private Connection						c					= null;
	private DbmsOutput						dbmsOutput			= null;
	private String							strOutput			= "";
	private String							strSqlError			= "";

	/**
	 *
	 * \brief JobSchedulerPLSQLJob
	 *
	 * \details
	 *
	 */
	public JobSchedulerPLSQLJob() {
		super(new JobSchedulerPLSQLJobOptions());
	}

	/**
	 *
	 * \brief Execute - Start the Execution of JobSchedulerPLSQLJob
	 *
	 * \details
	 *
	 * For more details see
	 *
	 * \see JobSchedulerAdapterClass
	 * \see JobSchedulerPLSQLJobMain
	 *
	 * \return JobSchedulerPLSQLJob
	 *
	 * @return
	 */
	public JobSchedulerPLSQLJob Execute() throws Exception {
		final String conMethodName = conClassName + "::Execute"; //$NON-NLS-1$

		JSJ_I_110.toLog(conMethodName);

		objJSJobUtilities.setJSParam(conSettingSQL_ERROR, "");

		try {
			Options().CheckMandatory();
			logger.debug(Options().dirtyString());

			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			c = DriverManager.getConnection(objOptions.db_url.Value(), objOptions.db_user.Value(), objOptions.db_password.Value());

			// pl/sql is expecting \n as newline.
//			String plsql = objOptions.command.Value().replace("\r\n", "\n");
			String plsql = objOptions.command.unescapeXML().replace("\r\n", "\n");
			plsql = objJSJobUtilities.replaceSchedulerVars(false, plsql);

			objOptions.replaceVars(plsql);

			dbmsOutput = new DbmsOutput(c);
			// TODO Option Buffersize
			dbmsOutput.enable(1000000);

			cs = c.prepareCall(plsql);
			cs.execute();

		}
		catch (SQLException e) {
			logger.error(JSMessages.JSJ_F_107.get(conMethodName), e);
			String strT = String.format("SQL Exception raised. Msg='%1$s', Status='%2$s'", e.getLocalizedMessage(), e.getSQLState());
			logger.error(strT);
			strSqlError = strT;
			objJSJobUtilities.setJSParam(conSettingSQL_ERROR, strT);
			throw new JobSchedulerException(strT, e);
		}
		catch (Exception e) {
			throw new JobSchedulerException(JSMessages.JSJ_F_107.get(conMethodName), e);
		}
		finally {
			objJSJobUtilities.setJSParam(conSettingDBMS_OUTPUT, "");
			objJSJobUtilities.setJSParam(conSettingSTD_OUT_OUTPUT, "");
			strOutput = dbmsOutput.getOutput();
			if (strOutput != null) {
				objJSJobUtilities.setJSParam(conSettingDBMS_OUTPUT, strOutput);
				objJSJobUtilities.setJSParam(conSettingSTD_OUT_OUTPUT, strOutput);
				
				int intRegExpFlags = Pattern.CASE_INSENSITIVE + Pattern.MULTILINE + Pattern.DOTALL;
				String strA[] = strOutput.split("\n");
				
				boolean flgAVariableFound = false;
				String strRegExp = objOptions.VariableParserRegExpr.Value();
				if (strRegExp.length() >= 0) {
					// TODO check the number of groups. must be >= 2
					Pattern objRegExprPattern = Pattern.compile(strRegExp, intRegExpFlags);
					for (String string : strA) {
						Matcher objMatch = objRegExprPattern.matcher(string);
						if (objMatch.matches() == true) {
							objJSJobUtilities.setJSParam(objMatch.group(1), objMatch.group(2).trim());
							flgAVariableFound = true;
						}
					}
				}

				dbmsOutput.close();

				if (flgAVariableFound == false) {
					logger.info(String.format("no JS-variable definitions found using reg-exp '%1$s'.", strRegExp));
				}

				ResultSetMetaData csmd = cs.getMetaData();
				if (csmd != null) {
					int nCols;
					nCols = csmd.getColumnCount();
					for (int i = 1; i <= nCols; i++) {
						System.out.print(csmd.getColumnName(i));
						int colSize = csmd.getColumnDisplaySize(i);
						for (int k = 0; k < colSize - csmd.getColumnName(i).length(); k++)
							System.out.print(" ");
					}
					System.out.println("");
				}
			}
			if (cs != null) {
				cs.close();
				cs = null;
			}
			if (c != null) {
				c.close();
				c = null;
			}
		}

		logger.debug(JSMessages.JSJ_I_111.get(conMethodName));
		return this;
	}

	public void init() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::init"; //$NON-NLS-1$
		doInitialize();
	}

	private void doInitialize() {
	} // doInitialize

	public String getSqlError() {
		return strSqlError;
	}

	public String getOutput() {
		return strOutput;
	}
} // class JobSchedulerPLSQLJob
