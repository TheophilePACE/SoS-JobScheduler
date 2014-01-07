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
package sos.connection.util;

import sos.connection.SOSConnection;
import sos.util.SOSBufferedLogger;

/**
 * @author Andreas Püschel <andreas.pueschel@sos-berlin.com>
 * @since 2004-04-06
 * @version 1.0
 * 
 * basic logger implementation for connections
 */
public class SOSConnectionLogger extends SOSBufferedLogger {

    private SOSConnection sosConnection = null;
    
    private String tableLogs = "LOGS";
   
    private int objectId = 0;

    /**
     * Konstruktor logging nach stdout
     * 
     * @param logLevel
     *            ein Wert von 1 bis 9
     * @throws Exception
     */
    public SOSConnectionLogger(int logLevel) throws Exception {
    	super(logLevel);
    }

    /**
     * logging in Datei
     * 
     * @param sosConnection
     *            Connection-Objekt
     * @param logLevel
     *            von 1 bis 9
     * @throws Exception
     */
    public SOSConnectionLogger(SOSConnection sosConnection, int logLevel)
            throws Exception {
    	super(logLevel);
        if (sosConnection == null) { throw new Exception(
                "illegal SOSConnection object"); }
        
        this.sosConnection = sosConnection;
        
    }

    

    /**
     * Speichert die Log-Informationen in der Datenbank
     * 
     * @throws java.lang.Exception
     */ 
    public void store(long objectType, long objectId) throws Exception {

        if (sosConnection == null) {
            throw new Exception("illegal SOSConnection object");
        }
        
        try {
            String query = "DELETE FROM " + getTableLogs() 
            	+ " WHERE \"OBJECT\"=" + objectType
            	+ " AND \"ID\"=" + objectId;
            
            sosConnection.execute(query);
            
            query = "INSERT INTO " + getTableLogs()
            	+ " (\"OBJECT\", \"ID\") VALUES (" + objectType + ", " + objectId + ")";
            
            sosConnection.execute(query);
            
            sosConnection.updateBlob(getTableLogs(), "LOG", this.logBuffer.toString().getBytes(), 
                    "\"OBJECT\"=" + objectType + " AND \"ID\"=" + objectId );

        } catch (Exception e) {
            throw new Exception("error occurred while logging: " + e.getMessage());
        }
        
        this.logBuffer = new StringBuffer();
    }

    /**
     * @return Returns the log contents for the rule that was processed.
     */
    public byte[] getLog(long objectType, long objectId) throws Exception {
        
        if (sosConnection == null)
            throw new Exception("invalid SOSConnection object");
        
        return sosConnection.getBlob("SELECT \"LOG\" FROM " + this.getTableLogs() 
                + " WHERE \"OBJECT\"=" + objectType 
                + " AND \"ID\"=" + objectId);

    }
    
    
    /**
     * @return Returns the tableLogs.
     */
    public String getTableLogs() {
        
        return tableLogs;
    }

    /**
     * @param tableLogs
     *            The tableLogs to set.
     */
    public void setTableLogs(String tableLogs) {
        
        this.tableLogs = tableLogs;
    }    
    
}
