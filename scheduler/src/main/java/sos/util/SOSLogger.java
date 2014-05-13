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
 * <p>Description: an abstract class for the logger class</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: SOS GmbH</p>
 * @author <a href="mailto:ghassan.beydoun@sos-berlin.com">Ghassan Beydoun</a>
 * @version $Id: SOSLogger.java 961 2005-06-06 09:23:25Z ap $
 */



public abstract class SOSLogger {

  public static final int DEBUG  = 0;
  public static final int DEBUG1 = 1;
  public static final int DEBUG2 = 2;
  public static final int DEBUG3 = 3;
  public static final int DEBUG4 = 4;
  public static final int DEBUG5 = 5;
  public static final int DEBUG6 = 6;
  public static final int DEBUG7 = 7;
  public static final int DEBUG8 = 8;
  public static final int DEBUG9 = 9;

  public static final int INFO  = 10;
  public static final int WARN  = 11;
  public static final int ERROR = 12;


  /** Attribut: hasWarnings: liefert true, falls Warnungen aufgetreten sind */
  private boolean hasWarnings	= false;

  /** Attribut: warningMsg: enth�t die letzte Warnung */
  private String warningMsg      = new String("");

  /** Attribut: hasErrors: liefert true, falls Fehler aufgetreten sind */
  private boolean hasErrors		= false;

  /** Attribut: errorMsg: enth�t die letzte Fehlermeldung */
  private String errorMsg        = new String("");



  /**
   * Schreibt eine info-message ins Protokoll
   *
   * @param str stellt die info message dar
   * @throws java.lang.Exception
   */
  abstract public void info( String str ) throws Exception;


  /**
   * Schreibt eine warning message  ins Protokoll
   * @param str stellt die warning message dar
   * @throws java.lang.Exception
   */
  abstract public void warn( String str ) throws Exception;


  /**
   * Schreibt eine error message  ins Protokoll
   *
   * @param str stellt die error message dar
   * @throws java.lang.Exception
   */
  abstract public void error( String str ) throws Exception;


  /**
   * Schreibt eine debug message  ins Protokoll
   *
   * @param str stellt die debug message dar
   * @throws java.lang.Exception
   */
  abstract public void debug( String str )throws Exception;


  /**
   * Schreibt eine debug message ins Protokoll
   *
   * @param str stellt die debug message dar
   * @throws java.lang.Exception
   */
  abstract public void debug1( String str ) throws Exception;


  /**
   * Schreibt eine debug message ins Protokoll
   *
   * @param str stellt die debug message dar
   * @throws java.lang.Exception
   */
  abstract public void debug2( String str ) throws Exception;


  /**
   * Schreibt eine debug message ins Protokoll
   *
   * @param str stellt die debug message dar
   * @throws java.lang.Exception
   */
  abstract public void debug3( String str ) throws Exception;


  /**
   * Schreibt eine debug message ins Protokoll
   *
   * @param str stellt die debug message dar
   * @throws java.lang.Exception
   */
  abstract public void debug4( String str ) throws Exception;


  /**
   * Schreibt eine debug message ins Protokoll
   *
   * @param str stellt die debug message dar
   * @throws java.lang.Exception
   */
  abstract public void debug5( String str ) throws Exception;


  /**
   * Schreibt eine debug message ins Protokoll
   *
   * @param str stellt die debug message dar
   * @throws java.lang.Exception
   */
  abstract public void debug6( String str ) throws Exception;


  /**
   * Schreibt eine debug message ins Protokoll
   *
   * @param str stellt die debug message dar
   * @throws java.lang.Exception
   */
  abstract public void debug7( String str ) throws Exception;


  /**
   * Schreibt eine debug message ins Protokoll
   *
   * @param str stellt die debug message dar
   * @throws java.lang.Exception
   */
  abstract public void debug8( String str ) throws Exception;


  /**
   * Schreibt eine debug message ins Protokoll
   *
   * @param str stellt die debug message dar
   * @throws java.lang.Exception
   */
  abstract public void debug9( String str ) throws Exception;


  /**
   * Schlie� den Logger
   *
   * @throws java.lang.Exception
   */
  abstract public void close() throws Exception;


  /**
   * setzt den Zustand des Loggers zurck
   */
  public void reset() {
  	
	hasWarnings = false;
	warningMsg  = "";
	hasErrors   = false;
	errorMsg    = "";
  }

  /**
   * setzt die letzte aufgetretene Warnung
   * 
   * @param warningMsg Inhalt der Warnung
   */
  public void setWarning( String warningMsg ) {
  	
	this.hasWarnings = true;
	this.warningMsg  = warningMsg;
  }

  /**
   * lielfert die letzte aufgetretene Warnung
   */
  public String getWarning() {
  	
	return warningMsg;
  }

  /**
   * liefert den Warnungszustand
   */
  public boolean hasWarnings() {
  	
	return hasWarnings;
  }


  /**
   * setzt die letzte aufgetretene Fehlermeldung
   */
  public void setError( String errorMsg ) {
  	
	this.hasErrors = true;
	this.errorMsg  = errorMsg;
  }

  /**
   * liefert die letzte aufgetretene Fehlermeldung
   */
  public String getError() {
  	
	return errorMsg;
  }

  /**
   * liefert true, falls Fehler aufgetreten sind
   */
  public boolean hasErrors() {
  	
	return hasErrors;
  }

  /**
   * @return Returns the logLevel.
   */
  public abstract int getLogLevel();
  
  /**
   * @param logLevel The logLevel to set.
   */
  public abstract void setLogLevel(int logLevel);


  
}
