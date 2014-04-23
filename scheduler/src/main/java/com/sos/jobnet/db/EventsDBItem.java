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
package com.sos.jobnet.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import com.sos.hibernate.classes.DbItem;

@Entity
@Table(name="SCHEDULER_EVENTS")
public class EventsDBItem extends DbItem {
	
	private long id;
	private String schedulerId;
	private String host;
	private long port;
	private String jobChain;
	private String orderId;
	private String jobName;
	private String eventClass;
	private String eventId;
	private String exitCode;
	private String parameters;
	private DateTime created;
	private DateTime expires;
	
	public EventsDBItem() {
		super();
	}

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    public Long getId() {
		return id;
	}

	@Column(name="ID")
    public void setId(Long id) {
		this.id = id;
	}
      
    @Column(name="SPOOLER_ID",nullable=false)
	public void setSchedulerId(String schedulerId) {
		this.schedulerId = schedulerId;
	}
    
    @Column(name="SPOOLER_ID",nullable=false)
	public String getSchedulerId() {
		return schedulerId;
	}
    
    @Column(name="REMOTE_SCHEDULER_HOST",nullable=true)
	public void setHost(String host) {
		this.host = host;
	}
 
    @Column(name="REMOTE_SCHEDULER_HOST",nullable=true)
	public String getHost() {
		return host;
	} 
    
    @Column(name="REMOTE_SCHEDULER_PORT")
    public Long getPort() {
		return port;
	}

	@Column(name="REMOTE_SCHEDULER_PORT")
    public void setPort(Long port) {
		this.port = port;
	}
    
    @Column(name="JOB_CHAIN",nullable=true)
	public void setJobChain(String chain) {
		this.jobChain = chain;
	}
 
    @Column(name="JOB_CHAIN",nullable=true)
	public String getJobChain() {
		return jobChain;
	} 
    
    @Column(name="ORDER_ID",nullable=true)
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
 
    @Column(name="ORDER_ID",nullable=true)
	public String getOrderId() {
		return orderId;
	} 
    
    @Column(name="JOB_NAME",nullable=true)
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
 
    @Column(name="JOB_NAME",nullable=true)
	public String getJobName() {
		return jobName;
	} 
    
    @Column(name="EVENT_CLASS",nullable=true)
	public void setEventClass(String eventClass) {
		this.eventClass = eventClass;
	}
 
    @Column(name="EVENT_CLASS",nullable=true)
	public String getEventClass() {
		return eventClass;
	} 
    
    @Column(name="EVENT_ID",nullable=true)
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
 
    @Column(name="EVENT_ID",nullable=true)
	public String getEventId() {
		return eventId;
	} 
    
    @Column(name="EXIT_CODE",nullable=true)
	public void setExitCode(String exitCode) {
		this.exitCode = exitCode;
	}
 
    @Column(name="EXIT_CODE",nullable=true)
	public String getExitCode() {
		return exitCode;
	} 
    
    @Column(name="PARAMETERS",nullable=true)
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
 
    @Column(name="PARAMETERS",nullable=true)
	public String getParameters() {
		return parameters;
	} 
    
    @Column(name="CREATED",nullable=false)
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	public void setCreated(DateTime created) {
		this.created = created;
	}
 
    @Column(name="CREATED",nullable=false)
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	public DateTime getCreated() {
		return created;
	} 
    
    @Column(name="EXPIRES",nullable=true)
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	public void setExpires(DateTime expires) {
		this.expires = expires;
	}
 
    @Column(name="EXPIRES",nullable=true)
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	public DateTime getExpires() {
		return expires;
	} 

}
