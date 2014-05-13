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

import javax.naming.InitialContext;
import javax.sql.DataSource;


/**
 * Title:
 * <p>
 * Description: Helper Class for DB Connection Pool
 * </p>
 * Copyright: Copyright (c) 2003 Company: SOS GmbH
 * 
 * @author <a href="mailto:ghassan.beydoun@sos-berlin.com">Ghassan Beydoun </a>
 * @version $Id: SOSConnectionPool.java 2887 2007-08-07 15:53:12Z al $
 */

public class SOSConnectionPool {

    private InitialContext initCtx = null;

    /** datasource for connection pooling */
    private DataSource dataSource = null;

    /** the datasource name */
    private String dataSourceName = null;

    /** the SOSConnection class name */
    private String sosConnectionClassName = null;
    
    /** lookup method already called*/
    private boolean lookupCalled = false;
    

    /**
     * SOSConnectionPool constructor
     * 
     * @param dataSourceName
     *            datasource name
     * @throws Exception
     *  
     */
    public SOSConnectionPool(String dataSourceName) throws Exception {
        if (dataSourceName == null || dataSourceName.length() == 0)
                throw (new Exception("missing datasource name!!"));
        this.dataSourceName = dataSourceName;
        lookup();

        this.lookupCalled = true;
    }
    
 
    /**
     * SOSConnectionPool constructor
     * 
     * @param dataSourceName
     *            datasource name
     * @param sosConnectionClassName
     *            sosConnection class name
     * @throws Exception
     *  
     */
    public SOSConnectionPool(String dataSourceName,
            String sosConnectionClassName) throws Exception {
        this(dataSourceName);
        if (sosConnectionClassName == null
                || sosConnectionClassName.length() == 0)
                throw (new Exception("missing sosconnection class name!!"));

        this.sosConnectionClassName = sosConnectionClassName;

        this.lookupCalled = true;
    }

    /**
     * SOSConnectionPool constructor
     *
     */
    public SOSConnectionPool() {
        this.lookupCalled = false;
    }
 
    
    
    /**
     * @throws Exception
     */
    private void lookup() throws Exception {

        try { // to connect to the pool
            initCtx = new InitialContext();
            dataSource = (DataSource) initCtx.lookup("java:comp/env/"
                    + dataSourceName);

        } catch (Exception e) {
            throw new Exception("Could not find datasource for ["
                    + dataSourceName + "], " + e);
        }
    }

    /**
     * @return the sosconnection
     * @throws Exception
     */
    public SOSConnection getConnection() throws Exception {
        
    return this.getConnection(null);	
    }
    
    /**
     * 
     * @param logger
     * @return
     * @throws Exception
     */
    public SOSConnection getConnection(sos.util.SOSLogger logger) throws Exception {
        
        if(!this.lookupCalled){
	        
	        if ( this.dataSourceName == null || this.dataSourceName.length() == 0){
				throw (new Exception ("missing datasource name!!"));
	        }
	        if ( this.sosConnectionClassName == null || this.sosConnectionClassName.length() == 0){
				throw (new Exception ("missing sosconnection class name!!"));
	        }
	        lookup();
	    }
       
        // try to get a sosConnection
        synchronized (this.getDataSource()) {
        	SOSConnection retConnection=null;
        	if(logger == null){
        		retConnection = SOSConnection.createInstance(this.getSosConnectionClassName(), this.getDataSource().getConnection());
        	}
        	else{
        		retConnection = SOSConnection.createInstance(this.getSosConnectionClassName(), this.getDataSource().getConnection(),logger);
        	}
        	retConnection.prepare(retConnection.getConnection());
        	return retConnection;
        } // synchronized
    }
    
    /**
     * @return the datasource object
     */
    public DataSource getDataSource() {
        return this.dataSource;
    }


    /**
     * close the Initial Context
     *  
     */
    public void close() {
        try {
            if (initCtx != null) initCtx.close();
        } catch (Exception e) {
        }
    }

    /**
     * @return the name of the sosconnection class
     */
    public String getSosConnectionClassName() {
        return sosConnectionClassName;
    }

    /**
     * @param sosConnectionClassName
     *            the sosconnection class
     */
    public void setSosConnectionClassName(String sosConnectionClassName) {
        this.sosConnectionClassName = sosConnectionClassName;
    }

    /**
     * @return Returns the dataSourceName.
     */
    public String getDataSourceName() {
        return dataSourceName;
    }

    /**
     * @param dataSourceName
     *            The dataSourceName to set.
     */
    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }
}
