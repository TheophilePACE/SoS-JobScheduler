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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
 
import com.sos.hibernate.classes.DbItem;

@Entity
@Table(name="SCHEDULER_JOB_NET_HISTORY")
public class JobNetHistoryDBItem extends DbItem {
	
	private long historyId;
	private String schedulerId;
    private long netId;
    private String uuid;
    private Date startTime;
    private Date endTime;
    private Date runTime;
    private Integer status;
    private Integer exitCode;
    private String exitMessage;

    private String jobNetId;
    private String jobNetIdParameterName;
    
    private Date created;
    private String createdBy;
    private Date modified;
    private String modifiedBy;
    
    private JobNetDBItem jobNetDBItem;

	public JobNetHistoryDBItem() {
		super();
	}

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="HISTORY_ID")
    public Long getHistoryId() {
		return historyId;
	}

	@Column(name="HISTORY_ID")
    public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}
      
    @ManyToOne (optional=true)
    @NotFound( action = NotFoundAction.IGNORE )
    @JoinColumn (name="`NET_ID`",  insertable = false, updatable = false)
    public JobNetDBItem getJobNetDBItem() {
        return jobNetDBItem;
    }
    public void setJobNetDBItem(JobNetDBItem jobNetDBItem)  {
       this.jobNetDBItem = jobNetDBItem; 
    }

    @Column(name="NET_ID",nullable=true)
    public void setNetId(long netId) {
        this.netId = netId;
    }
 
    @Column(name="NET_ID",nullable=true)
    public long getNetId() {
        return netId;
    } 

    
	
    @Column(name="SCHEDULER_ID",nullable=false)
	public void setSchedulerId(String schedulerId) {
		this.schedulerId = schedulerId;
	}
    
    @Column(name="SCHEDULER_ID",nullable=false)
	public String getSchedulerId() {
		return schedulerId;
	}
    
    
    @Column(name="UUID")
    public String getUuid() {
		return uuid;
	}

	@Column(name="UUID")
    public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	    
    @Column(name="START_TIME",nullable=true)
    @Temporal (TemporalType.TIMESTAMP)
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
 
    @Column(name="START_TIME",nullable=true)
    @Temporal (TemporalType.TIMESTAMP)
    public Date getStartTime() {
        return startTime;
    } 
    
    @Column(name="END_TIME",nullable=true)
    @Temporal (TemporalType.TIMESTAMP)
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
 

    @Column(name="END_TIME",nullable=true)
    @Temporal (TemporalType.TIMESTAMP)
    public Date getEndTime() {
        return endTime;
    } 
  
  
        
    @Column(name="RUN_TIME",nullable=true)
    @Temporal (TemporalType.TIMESTAMP)
    public void setRunTime(Date runTime) {
        this.runTime = runTime;
    }
 
    @Column(name="RUN_TIME",nullable=true)
    @Temporal (TemporalType.TIMESTAMP)
    public Date getRunTime() {
        return runTime;
    } 
  
  
    @Column(name="EXIT_MESSAGE",nullable=true)
	public void setExitMessage(String exitMessage) {
		this.exitMessage = exitMessage;
	}
 
    @Column(name="EXIT_MESSAGE",nullable=true)
	public String getExitMessage() {
		return exitMessage;
	} 
    
    @Column(name="JobNet_ID",nullable=true)
	public void setJobNetId(String JobNetId) {
		this.jobNetId = JobNetId;
	}
 
    @Column(name="JobNet_ID",nullable=true)
	public String getJobNetId() {
		return jobNetId;
	} 
    
    @Column(name="JobNet_ID_PARAMETER_NAME",nullable=true)
	public void setJobNetIdParameterName(String JobNetIdParameterName) {
		this.jobNetIdParameterName = JobNetIdParameterName;
	}
 
    @Column(name="JobNet_ID_PARAMETER_NAME",nullable=true)
	public String getJobNetIdParameterName() {
		return jobNetIdParameterName;
	} 
     
    @Column(name="EXIT_CODE",nullable=true)
	public void setExitCode(Integer exitCode) {
		this.exitCode = exitCode;
	}
 
    @Column(name="STATUS",nullable=true)
	public Integer getStatus() {
		return status;
	} 
    
      @Column(name="EXIT_CODE",nullable=true)
    public void setStatus(Integer status) {
        this.status = status;
    }
 
    @Column(name="EXIT_CODE",nullable=true)
    public Integer getExitCode() {
        return exitCode;
    } 
    
    
    @Temporal (TemporalType.TIMESTAMP)
    @Column(name="CREATED",nullable=true)
    public void setCreated(Date created) {
        this.created = created;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED", nullable = true)
    public Date getCreated() {
        return created;
    }
    
    @Temporal (TemporalType.TIMESTAMP)
    @Column(name="MODIFIED",nullable=true)
    public void setModified(Date modified) {
        this.modified = modified;
    }
    
    @Column(name="CREATED_BY",nullable=true)
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
 
    @Column(name="CREATED_BY",nullable=true)
    public String getCreatedBy() {
        return createdBy;
    }
    
    @Column(name="MODIFIED_BY",nullable=true)
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
 
    @Column(name="MODIFIED_BY",nullable=true)
    public String getModifiedBy() {
        return modifiedBy;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFIED", nullable = true)
    public Date getModified() {
        return modified;
    }
    @Transient
    @Override
    public String getIdentifier() {
        return uuid;
    }
    
    @Transient
    @Override
    public String getTitle() {
        return uuid;
    }
     
    
    @Transient
    @Override    public boolean isJobNetNode(){
        return true;
    }       
    
    @Transient 
    public String getRunTimeFormatted(){
        return getDateFormatted(this.getRunTime());
    }

    @Transient 
    public String getStartTimeFormatted(){
        return getDateFormatted(this.getStartTime());
    }    
    
    @Transient 
    public String getEndTimeFormatted(){
        return getDateFormatted(this.getEndTime());
    }
    
    @Transient 
    public String getNetIdValue(){
        return String.valueOf(netId);
    }

    @Transient 
    public String getStatusValue(){
        return String.valueOf(status);
    }
    

    @Transient 
    public String getExitCodeValue(){
        return String.valueOf(exitCode);
    } 
    
}
