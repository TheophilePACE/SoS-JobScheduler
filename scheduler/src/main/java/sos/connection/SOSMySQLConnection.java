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
package sos.connection;

import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.sql.Driver;

import java.sql.Connection;
import java.sql.Statement;

import sos.util.SOSClassUtil;
import sos.util.SOSLogger;
import sos.util.SOSString;

/**
 * Title: SOSMySQLConnection.java
 * <p>
 * Description: Implementation of SOSConnection class for MySQL
 * </p>
 * Copyright: Copyright (c) 2003 Company: SOS GmbH
 * 
 * @author <a href="mailto:ghassan.beydoun@sos-berlin.com">Ghassan Beydoun </a>
 * @version $Id: SOSMySQLConnection.java 4176 2008-09-04 12:50:05Z re $
 */

public class SOSMySQLConnection extends sos.connection.SOSConnection {

    /** Replacements for %lcase, %ucase, %now, %updlock */
    private static final String replacement[] = { "LCASE", "UCASE", "NOW()",
            "FOR UPDATE"};
    
    private static final SOSConnectionVersionLimiter versionLimiter;
 
    // init des versionLimiter
    static {
    	versionLimiter = new SOSConnectionVersionLimiter();
    	versionLimiter.setExcludedThroughVersion(3,999);
    	versionLimiter.setMinSupportedVersion(4,0);
    	versionLimiter.setMaxSupportedVersion(5,1);
    }

    public SOSMySQLConnection(Connection connection, SOSLogger logger)
            throws Exception {
        super(connection, logger);
    }

   public SOSMySQLConnection(Connection connection) throws Exception {
        super(connection);
    }

   public SOSMySQLConnection(String configFileName, SOSLogger logger)
            throws Exception {
        super(configFileName, logger);
    }

   public SOSMySQLConnection(String configFileName) throws Exception {
        super(configFileName);
    }

    public SOSMySQLConnection(String driver, String url, String dbuser,
            String dbpassword, SOSLogger logger) throws Exception {

        super(driver, url, dbuser, dbpassword, logger);
    }

    public SOSMySQLConnection(String driver, String url, String dbuser,
            String dbpassword) throws Exception {

        super(driver, url, dbuser, dbpassword);
    }

    
    public void connect() throws Exception {

        Properties properties = new Properties();

        if (SOSString.isEmpty(url))
                throw new Exception(SOSClassUtil.getMethodName()
                        + ": missing database url.");
        if (SOSString.isEmpty(driver))
                throw new Exception(SOSClassUtil.getMethodName()
                        + ": missing database driver.");
        if (SOSString.isEmpty(dbuser))
                throw new Exception(SOSClassUtil.getMethodName()
                        + ": missing database user.");
        if (SOSString.isEmpty(dbpassword)) dbpassword = "";

        //  String currentCharacterSet = "set CHARACTER SET latin1_german1_ci";
        //  String timestamp = "";

        properties.setProperty("user", dbuser);
        properties.setProperty("password", dbpassword);

        try {
            logger.debug6(SOSClassUtil.getMethodName());
            Driver driver = (Driver) Class.forName(this.driver).newInstance();

            connection = driver.connect(url, properties);

            if (connection == null)
                    throw new Exception(SOSClassUtil.getMethodName()
                            + ": can't connect to database");
            logger.debug6(".. successfully connected to " + url);
            
            versionLimiter.check(this, logger);
            prepare(connection);

            //  stmt.execute(currentCharacterSet);
            //  logger.debug9(".. " + currentCharacterSet + " successfully
            // set.");
            //  logger.debug9(".. " + timestamp + " successfully set.");

        } catch (Exception e) {
            throw new Exception(SOSClassUtil.getMethodName() + ": "
                    + e.toString(), e);
        }
    }

    
     public void prepare(Connection connection) throws Exception {

        Statement stmt = null;

        logger.debug6("calling " + SOSClassUtil.getMethodName());

        try {
            if (connection == null)
                    throw new Exception("can't connect to database");

            connection
                    .setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            connection.rollback();

            try {
                stmt = connection.createStatement();
                stmt.execute("SET SESSION SQL_MODE='ANSI_QUOTES'");
            } catch (Exception ex) { // ignore any errors
            } finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }

        } catch (Exception e) {
            throw new Exception(SOSClassUtil.getMethodName() + ":"
                    + e.toString(), e);
        }
    }

    
    /**
     * returns MySQL timestamp function
     * 
     * @param dateString
     * @return MySQL timestamp function
     * @throws java.lang.Exception
     */
    public String toDate(String dateString) throws Exception {
        if (SOSString.isEmpty(dateString))
                throw new Exception(SOSClassUtil.getMethodName()
                        + ": dateString has no value.");
        return "'" + dateString + "'";
    }

    
    protected GregorianCalendar getDateTime(String format) throws Exception {
        GregorianCalendar gc = new GregorianCalendar();

        String timestamp = this.getSingleValue("select CURRENT_TIMESTAMP");

        if (timestamp.length() > 19) {
            timestamp = timestamp.substring(0, 19);
        }

        java.util.Date date = sos.util.SOSDate.getDate(timestamp, format);

        gc.setTime(date);

        return gc;
    } // getDateTime
    
    
    protected String replaceCasts( String inputString) throws Exception {
        
        logger.debug6("Calling " + SOSClassUtil.getMethodName());
        
        Pattern pattern = Pattern.compile(CAST_PATTERN);
        Matcher matcher = pattern.matcher(inputString);
        StringBuffer buffer = new StringBuffer();
        String replaceString;
        String token;
       
        while ((matcher.find())) {

            replaceString = matcher.group().toLowerCase();
            
            if ( matcher.group(1) != null &&  matcher.group(6) != null) {
                token = matcher.group(6).replaceFirst("\\)","").trim();

                if ( token.matches(".*varchar.*")) {
                  replaceString = replaceString.replaceAll("varchar"," as char)");
                  replaceString = replaceString.replaceFirst("%cast","trim(cast");
                } else if ( token.matches(".*character.*")) {
                      replaceString = replaceString.replaceAll("character"," as char");
                      replaceString = replaceString.replaceFirst("%cast","cast");
                } else if (token.matches(".*integer.*")) {
                  replaceString = replaceString.replaceAll("integer"," as signed");
                  replaceString = replaceString.replaceFirst("%cast","cast");
                }
                else if (token.matches(".*timestamp.*")) {
                  replaceString = replaceString.replaceAll("timestamp"," as datetime");
                  replaceString = replaceString.replaceFirst("%cast","cast");
                }
                else if (token.matches(".*datetime.*")) {
                  replaceString = replaceString.replaceAll("datetime"," as datetime");
                  replaceString = replaceString.replaceFirst("%cast","cast");
                }
            } // if
            if ( matcher.group(3) != null && matcher.group(4) != null) { // group 4 "VALUE <data_type>"
               token = matcher.group(4).replaceFirst("\\(","").trim();
               
               if ( token.matches(".*varchar.*")) {
                   replaceString = replaceString.replaceAll("varchar"," as char)");
                   replaceString = replaceString.replaceAll("%cast","trim(cast");
               } else if ( token.matches(".*character.*")) {
                   replaceString = replaceString.replaceAll("character"," as char");
                   replaceString = replaceString.replaceAll("%cast","cast");
               } else if (token.matches(".*integer.*")) {
                   replaceString = replaceString.replaceAll("integer"," as signed");
                   replaceString = replaceString.replaceAll("%cast","cast");
               }
               else if (token.matches(".*timestamp.*")) {
                  replaceString = replaceString.replaceAll("timestamp"," as datetime");
                  replaceString = replaceString.replaceFirst("%cast","cast");
                }
                else if (token.matches(".*datetime.*")) {
                  replaceString = replaceString.replaceAll("datetime"," as datetime");
                  replaceString = replaceString.replaceFirst("%cast","cast");
                }
            }
            
            replaceString = replaceString.toUpperCase();
            matcher.appendReplacement(buffer, replaceString);
        } // while
        matcher.appendTail(buffer);
        logger.debug6(".. result [" + buffer.toString() + "]");
        return buffer.toString();
    } // pseudoFunctions

	
	protected String getLastSequenceQuery(String sequence) {
		return "SELECT LAST_INSERT_ID();";
	}

	
    protected boolean prepareGetStatements(StringBuffer contentSB,StringBuffer splitSB,StringBuffer endSB) throws Exception{
    	if(contentSB == null){ throw new Exception("contentSB is null");}
    	if(splitSB == null){ throw new Exception("splitSB is null");}
    	if(endSB == null){ throw new Exception("endSB is null");}
    	
    	splitSB.append("\n\\\\g\n");
        // endSB.append("\n\\g\n");
        endSB.append("");
    return true;
    }

	public String[] getReplacement() {
		return replacement;
	}
    
}
