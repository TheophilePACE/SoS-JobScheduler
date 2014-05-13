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


/**
 * <p>Title: </p>
 * <p>Description: Logger-Klasse on using the SOS-Scheduler</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: SOS GmbH</p>
 * @author <a href="mailto:ghassan.beydoun@sos-berlin.com">Ghassan Beydoun</a>
 * @version $Id: SOSSchedulerLogger.java 2497 2007-01-16 15:52:13Z al $
 */


public class SOSSchedulerLogger extends SOSLogger{

  private sos.spooler.Log spoolerLog;


  /**
   * construktor
   *
   * @param spoolerLog the log-object of the scheduler
   * @throws Exception
   */
  public SOSSchedulerLogger( sos.spooler.Log spoolerLog ) throws Exception {

    if ( spoolerLog == null )
      throw new Exception("spooler_log is null.");
      this.spoolerLog = spoolerLog;
  }


  /**
   * write the warning message
   *
   * @param str the specified message
   * @throws java.lang.Exception
   */
  public void warn( String str ) throws Exception {
    log(WARN, str);
    setWarning(str);
  }


  /**
   * write the error message
   *
   * @param str the specified message
   * @throws java.lang.Exception
   */
  public void error( String str ) throws Exception {
    log(ERROR, str);
	setError(str);
  }


  /**
   * write the info message
   *
   * @param str the specified message
   * @throws java.lang.Exception
   */
  public void info( String str ) throws Exception {
    log(INFO, str);
  }


  /**
  * write the debug message
  *
  * @param str the specified message
  * @throws java.lang.Exception
  */
  public void debug( String str ) throws Exception {
    log(DEBUG, str);
  }


  /**
   * write the debug message
   *
   * @param str the specified message
   * @throws java.lang.Exception
   */
  public void debug1( String str ) throws Exception {
    log(DEBUG1, str);
  }


  /**
   * write the debug message
   *
   * @param str the specified message
   * @throws java.lang.Exception
   */
  public void debug2( String str ) throws Exception {
    log(DEBUG2, str);
  }


  /**
   * write the debug message
   *
   * @param str the specified message
   * @throws java.lang.Exception
   */
  public void debug3( String str ) throws Exception {
    log(DEBUG3, str);
  }


  /**
   * write the debug message
   *
   * @param str the specified message
   * @throws java.lang.Exception
   */
  public void debug4( String str ) throws Exception {
    log(DEBUG4, str);
  }



  /**
   * write the debug message
   *
   * @param str the specified message
   * @throws java.lang.Exception
   */
  public void debug5( String str ) throws Exception {
    log(DEBUG5, str);
  }


  /**
   * write the debug message
   *
   * @param str the specified message
   * @throws java.lang.Exception
   */
  public void debug6( String str ) throws Exception {
    log(DEBUG6, str);
  }


  /**
   * write the debug message
   *
   * @param str the specified message
   * @throws java.lang.Exception
   */
  public void debug7( String str ) throws Exception {
    log(DEBUG7, str);
  }


  /**
   * write the debug message
   *
   * @param str the specified message
   * @throws java.lang.Exception
   */
  public void debug8( String str ) throws Exception {
    log(DEBUG8, str);
  }


  /**
   * write the debug message
   *
   * @param str the specified message
   * @throws java.lang.Exception
   */
  public void debug9( String str ) throws Exception {
    log(DEBUG9, str);
  }


  /**
   * write the specified message
   *
   * @param level the log level
   * @param str the specified message
   * @throws java.lang.Exception
   */
  public void log( int level, String str) throws Exception {
    int logLevel;

    switch ( level ) {
      case DEBUG:
      case DEBUG1:
        logLevel = -1;
        break;
      case DEBUG2:
      case DEBUG3:
      case DEBUG4:
      case DEBUG5:
      case DEBUG6:
      case DEBUG7:
      case DEBUG8:
      case DEBUG9:
      logLevel = -level;
        break;
      case INFO:
        logLevel = 0;
        break;
      case WARN:
        logLevel = 1;
		setWarning(str);
        break;
      case ERROR:
        logLevel = 2;     
        setError(str);
        break;
      default:
        logLevel = 0;
    }
    spoolerLog.log(logLevel,str);
  }


  /**
   * close the logger
   *
   * @throws java.lang.Exception
   */
  public void close() throws Exception{
    if ( spoolerLog != null ) spoolerLog = null;
  }

  /**
   * @return Returns the logLevel.
   */
  public int getLogLevel() {
	  int spoolerLogLevel = spoolerLog.level();
	  int logLevel = INFO;
	  switch (spoolerLogLevel){
	  case -1:
		  logLevel = DEBUG1;
		  break;
	  case 0:
		  logLevel = INFO;
		  break;
	  case 1:
		  logLevel = WARN;
		  break;
	  case 2:
		  logLevel = ERROR;
		  break;
	  default:
		  logLevel = -spoolerLogLevel;
			  
	  }
      return logLevel;
  }
  /**
   * @param logLevel The logLevel to set.
   */
  public void setLogLevel(int logLevel) {
	  int level = logLevel;
	  switch ( level ) {
      case DEBUG:
      case DEBUG1:
        logLevel = -1;
        break;
      case DEBUG2:
      case DEBUG3:
      case DEBUG4:
      case DEBUG5:
      case DEBUG6:
      case DEBUG7:
      case DEBUG8:
      case DEBUG9:
      logLevel = -level;
        break;
      case INFO:
        logLevel = 0;
        break;
      case WARN:
        logLevel = 1;
        break;
      case ERROR:
        logLevel = 2;
        break;
      default:
        logLevel = 0;
    }
      spoolerLog.set_level(logLevel);
  }

  /**
   * @return String Name der Logdatei
   */
  public String getFileName() {
    return spoolerLog.filename();
  }

}
