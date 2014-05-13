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
package com.sos.hibernate.layer;

import java.io.File;
import java.util.Date;
import java.util.GregorianCalendar;
 
import org.apache.log4j.Logger;
import org.hibernate.CacheMode;
 
import org.hibernate.Query;
import org.hibernate.Session;
 
import org.hibernate.Transaction;

 
import com.sos.hibernate.classes.SOSHibernateFilter;
import com.sos.hibernate.classes.SosHibernateSession;


/**
* \class SOSHibernateDBLayer 
* 
* \brief SOSHibernateDBLayer - 
* 
* \details
*
* \section SOSHibernateDBLayer.java_intro_sec Introduction
*
* \section SOSHibernateDBLayer.java_samples Some Samples
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
* \author Uwe Risse
* \version 06.10.2011
* \see reference
*
* Created on 06.10.2011 14:23:43
 */

public class SOSHibernateDBLayer {
 
	@SuppressWarnings("unused")
	private final String	conClassName	= "SOSHibernateDBLayer";
    private static Logger                        logger             = Logger.getLogger(SOSHibernateDBLayer.class);

 	//private static   ServiceRegistry serviceRegistry=null;
    
	protected Session			session=null;
	protected Transaction		transaction=null;


	public SOSHibernateDBLayer() {
		//
	}
	
 
	private void initSessionEx() throws Exception   {
		session = SosHibernateSession.getInstance(SosHibernateSession.configurationFile);
		if ( session == null){
		   String s = String.format("Could not initiate session for database using file %s",SosHibernateSession.configurationFile);
           throw new Exception (s);
		}else{
		   session.setCacheMode(CacheMode.IGNORE);
		}
	
 	}
	
    public void initSession()    {
      try {
        initSessionEx();
    } catch (Exception e) {
        String s = String.format("Could not initiate session for database using file %s",SosHibernateSession.configurationFile);
        logger.error(s);
        e.printStackTrace();
     }
   }
	
 
	
	public File getConfigurationFile() {
		return SosHibernateSession.configurationFile;
	}

	 

	public void save(Object dBItem) {
			session.save(dBItem);
			session.flush();
	}
	
	public void update(Object dBItem) {
			session.update(dBItem);
			session.flush();
	}
	
 
	
	public void saveOrUpdate(Object dBItem) {
		session.saveOrUpdate(dBItem);
		session.flush();
	}
	
	public Session getSession() {
	    if (session == null){
            initSession();
        }
		return session;
	}

	public void delete(Object dBItem) {
		session.delete(dBItem);
		session.flush();
	}

 
	
	public Query createQuery(String hQuery) {
		return this.session.createQuery(hQuery);
	}
	
	public void beginTransaction() {
		initSession();
		transaction = session.beginTransaction();
	}

	public void commit() {
		if (transaction != null){
			session.flush();
			transaction.commit();
			//SosHibernateSession.close();
		}
 
	}

	public void closeSession() {
		if (session != null && session.isOpen()) {
		  SosHibernateSession.close();
		  session = null;
		}
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public void setSession(SOSHibernateDBLayer layer){
		this.session = layer.getSession();
	}

	public void setConfigurationFile(File configurationFile) {
		SosHibernateSession.configurationFile = configurationFile;
	}
	 

}
