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
package sos.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Vector;

import sos.util.SOSLogger;

/**
 * @author Andreas P�schel <andreas.pueschel@sos-berlin.com>
 * @since 2009-02-20
 * @version 1.0
 *
 * Command line processing
 */

public class SOSCommandline {


	/**
	 * Parse and split command line arguments
	 */
	public static String[] splitArguments(String arguments) throws Exception {

		String[] resultArguments = null;
		Vector resultVector = new Vector();
		int resultIndex = 0;
		String resultString = "";		
		boolean inQuote = false;
		try {


			for (int i=0; i<arguments.length(); i++) {                        		        		      		
				
				if(arguments.substring(i).startsWith("\\\"")){        		
					int pos1 = i;        			
					int pos2 = arguments.indexOf("\\\"", pos1+1);
					if(pos2 > -1) {
						//resultString = arguments.substring(pos1, pos2+5);
						resultString = arguments.substring(pos1, pos2+1);
						resultVector.add(resultIndex++, resultString);
						resultString = "";
						i = pos2+1;
						continue;
					}
				}

				if(arguments.substring(i).startsWith("\'")){        		
					int pos1 = i;        			
					int pos2 = arguments.indexOf("\'", pos1+1);
					if(pos2 > -1) {
						//resultString = arguments.substring(pos1, pos2+5);
						resultString = arguments.substring(pos1, pos2+1);
						resultVector.add(resultIndex++, resultString);
						resultString = "";
						i = pos2+1;
						continue;
					}
				} 

				if (inQuote) { 
					resultString += arguments.charAt(i); 
					continue;
				}

				if (arguments.charAt(i) != ' ') {
					resultString += arguments.charAt(i);
				} else {
					resultVector.add(resultIndex++, resultString);
					resultString = "";
				}
			}

			if (resultString.trim().length() > 0) {
				resultVector.add(resultIndex++, resultString);
			}           
			resultArguments = new String[resultIndex];
			resultVector.copyInto(resultArguments);
			return resultArguments;        


			/*//original
            for (int i=0; i<arguments.length(); i++) {
                if (arguments.charAt(i) == '\'' ) {
                   inQuote = !inQuote;
                }



                if (inQuote) { 
                    resultString += arguments.charAt(i); 
                    continue;
                }

                if (arguments.charAt(i) != ' ') {
                    resultString += arguments.charAt(i);
                } else {
                    resultVector.add(resultIndex++, resultString);
                    resultString = "";
                }
            }

            if (resultString.trim().length() > 0) {
                resultVector.add(resultIndex++, resultString);
            }

            resultArguments = new String[resultIndex];
            resultVector.copyInto(resultArguments);
            return resultArguments;
			 */} catch (Exception e) {
				 throw new Exception("error occurred splitting arguments: " + e.getMessage());
			 }
	}


	/**
	 * executes a command
	 */
	public static Vector execute(String command) {

		return SOSCommandline.execute(command, null);
	}   


	/**
	 * executes a command
	 */
	public static Vector execute(String command, SOSLogger logger) {

		Vector returnValues = new Vector();

		try {
			try { // to execute command
				Process p = Runtime.getRuntime().exec(SOSCommandline.splitArguments(command));

				final BufferedReader stdInput = new BufferedReader(new 
						InputStreamReader(p.getInputStream()));
				final BufferedReader stdError = new BufferedReader(new 
						InputStreamReader(p.getErrorStream()));

				p.waitFor();

				if (logger != null) {
					try {
						logger.debug3("command returned exit code: " + p.exitValue());
					} catch (Exception exc) {}
				}
				returnValues.add(0, new Integer(p.exitValue()));

				// logger.debug8("number of characters available from stdout: " + p.getInputStream().available());
				// logger.debug8("number of characters available from stderr: " + p.getErrorStream().available());

				try { // to process output to stdout
					String line = "";
					String stdout = "";
					while (line != null) {
						line = stdInput.readLine();
						if (line != null) { 
							stdout += line; 
						}
					}

					try {
						if (logger != null) {
							if (stdout != null && stdout.trim().length() > 0){
								logger.debug8("Command returned output to stdout ...");                        
							} else {
								logger.debug8("Command did not return any output to stdout.");
							}
						}
					} catch(Exception exc) {}

					returnValues.add(1, stdout);

				} catch (Exception ex) {
					returnValues.add(1, "");
					returnValues.add(2, ex.getMessage());
					if (logger != null) {
						try { 
							logger.debug("error occurred processing stdout: " + ex.getMessage()); 
						} catch (Exception exc) {}; 
					}
				}

				try { // to process output to stderr
					String line = "";
					String stderr = "";
					while (line != null) {
						line = stdError.readLine();
						if (line != null) { 
							stderr += line; 
						}
					} 

					try {
						if (logger != null) {
							if (stderr != null && stderr.trim().length() > 0){
								logger.debug8("Command returned output to stderr ...");                        
							} else {
								logger.debug8("Command did not return any output to stderr.");
							}
						}
					} catch(Exception exc) {}

					returnValues.add(2, stderr);

				} catch (Exception ex) {
					returnValues.add(2, ex.getMessage());
					if (logger != null) {
						try { 
							logger.debug("error occurred processing stderr: " + ex.getMessage()); 
						} catch (Exception exc) {}; 
					}

				}

				if (stdInput != null) stdInput.close();
				if (stdError != null) stdError.close();

			} catch (Exception ex) {
				returnValues.add(0, new Integer(1));
				returnValues.add(1, "");
				returnValues.add(2, ex.getMessage());
				if (logger != null) {
					try { 
						logger.debug("error occurred executing command: " + ex.getMessage()); 
					} catch (Exception exc) {}; 
				}
			}

		} catch(Exception e){
			try {
				if (logger != null) logger.debug("Command could not be executed successfully: " + e.getMessage());
			} catch (Exception ex) {}

			returnValues.add(0, new Integer(1));
			returnValues.add(1, "");
			returnValues.add(2, e.getMessage());
		}

		return returnValues;
	}


	/**
	 * Checks if a command needs to be executed to get the password
	 */
	public static String getExternalPassword(String password) {

		return SOSCommandline.getExternalPassword(password, null);
	}


	/**
	 * Checks if an external command needs to be executed to get the password
	 */
	public static String getExternalPassword(String password, SOSLogger logger) {

		String returnPassword = password;
		String firstChar = null;
		try {

			if (password != null && password.startsWith("`") && password.endsWith("`")){
				String command = password.substring(1, password.length()-1);
				try{
					if (logger != null) logger.debug3("Trying to get password by executing command in backticks: " + command);                       
				} catch(Exception ex){ex.printStackTrace();}

				Vector returnValues = SOSCommandline.execute(command, logger);
				Integer exitValue = (Integer) returnValues.elementAt(0);
				if (exitValue.compareTo(new Integer(0)) == 0) {
					if ((String) returnValues.elementAt(1) != null) {
						returnPassword = (String) returnValues.elementAt(1);
					}
				}
			}
		} catch(Exception e) {
			if (logger != null) {
				try {
					logger.debug3("Using password string as password. Command could not be executed: "+e);
				} catch(Exception ex) {ex.printStackTrace();}
			}
		}

		return returnPassword;
	}


	public static void main(String[] args) {
		try {
			SOSStandardLogger logger = new SOSStandardLogger(9);
			//String password = "hallo";
			//String password = "`c:/sosftp/getpassword.cmd`";    		
			//String password = "`c:/sosftp/getpassword.cmd \"ggg\"aa `";   		    		
			String password = "`c:/sosftp/getpassword.cmd \"a\" \"b\" `";

			//String password = "`c:/sosftp/getpassword.cmd \"a\" \"b\" `"; //resultvector hat drei eintr�ge[c:/sosftp/getpassword.cmd, "a", "b"]
			//String password = "`c:/sosftp/getpassword.cmd \"a\" b `"; //resultvector hat drei eintr�ge[c:/sosftp/getpassword.cmd, "a", b]
			//String password = "`c:/sosftp/getpassword.cmd a\'b `"; //resultvector hat zwei eintr�ge [c:/sosftp/getpassword.cmd, a'b ]
			//String password = "`c:/sosftp/getpassword.cmd 'a' 'b' `"; //resultvector hat drei eintr�ge[c:/sosftp/getpassword.cmd, 'a', 'b' ]

			//String password = "`a b`"; //resultvector hat zwei eintr�ge [a, b]
			//String password = "`a\"b`"; //resultvector hat ein eintrag  [a"b]


			//ab hier test ok

			//String password = "`c:/sosftp/getpassword.cmd 'a b' `";  //resultvector hat zwei eintr�ge  [c:/sosftp/getpassword.cmd, 'a b']
			//String password = "`cmd \"a b\"`"; //resultvector hat drei eintr�ge  [c:/sosftp/getpassword.cmd, "a, b"] 
			//String password = "`cmd a b`"; //resultvector hat 3 eintr�ge  [cmd, a, b]
			//String password = "`c:/sosftp/getpassword.cmd ggg\"aa `";//resultvector hat zwei eintr�ge  [c:/sosftp/getpassword.cmd, ggg"aa]
			//String password = "`c:/sosftp/getpassword.cmd 'ggg\"aa' `";//resultvector hat zwei eintr�ge  [c:/sosftp/getpassword.cmd, 'ggg"aa']
			//String password = "`c:/sosftp/getpassword.cmd \"a\" \"b\" xyz `"; //resultvector hat 4 eintr�ge [c:/sosftp/getpassword.cmd, "a", "b", xyz]		
			//String password = "`c:/sosftp/getpassword.cmd '\"a\" \"b\"' xyz `"; //resultvector hat 3 eintr�ge [c:/sosftp/getpassword.cmd, '"a" "b"', xyz]		

			//String password = "`c:/sosftp/getpassword.cmd \"'a b'\"`";  //resultvector hat 2 eintr�ge [c:/sosftp/getpassword.cmd,  'a b']  

            password = "`\\tmp\\lGetPasswd.cmd ssh wilma.sos sos`";
            System.out.println(getExternalPassword(password, logger));
		} catch (Exception e){
			System.out.println("error:" + e.toString() );
		}

	}
}
