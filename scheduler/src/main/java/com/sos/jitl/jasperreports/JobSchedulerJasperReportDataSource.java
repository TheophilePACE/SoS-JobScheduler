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
package com.sos.jitl.jasperreports;

import java.util.HashMap;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import sos.connection.SOSConnection;
import sos.util.SOSLogger;


public class JobSchedulerJasperReportDataSource implements JRDataSource
{

	private SOSConnection connection = null;
    
    private SOSLogger logger = null;
	
    private HashMap record = null;

    
    /**
     * constructor
     */
    public JobSchedulerJasperReportDataSource() {}
        

	/**
	 * constructor
	 */
	public JobSchedulerJasperReportDataSource(SOSConnection connection) {
        
        this.setConnection(connection);
	}


    /**
     * constructor
     */
    public JobSchedulerJasperReportDataSource(SOSConnection connection, SOSLogger logger) {
        
        this.setConnection(connection);
        this.setLogger(logger);
    }


	/**
	 *
	 */
	public boolean next() throws JRException
	{
        try {
            this.record = this.getConnection().get();
            return (record != null && !record.isEmpty());
        } catch (Exception e) {
            throw new JRException(e);
        }
	}


	/**
	 *
	 */
	public Object getFieldValue(JRField field) throws JRException {

        try {
            String fieldName = field.getName().toLowerCase();
            if (record != null && !record.isEmpty() && record.containsKey(fieldName)) {
                return record.get(fieldName);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new JRException(e);
        }
	}


    /**
     * @return Returns the connection.
     */
    public SOSConnection getConnection() {
        return connection;
    }


    /**
     * @param connection The connection to set.
     */
    public void setConnection(SOSConnection connection) {
        this.connection = connection;
    }


    /**
     * @return Returns the logger.
     */
    public SOSLogger getLogger() {
        return logger;
    }


    /**
     * @param logger The logger to set.
     */
    public void setLogger(SOSLogger logger) {
        this.logger = logger;
    }


}
