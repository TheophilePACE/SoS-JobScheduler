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
package com.sos.jobnet.db;

import com.sos.hibernate.classes.DbItem;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Table(name="SCHEDULER_JOB_NET")
public class    JobNetDBItem extends DbItem {
	
	private long netId;
	private String schedulerId;
    private String jobNet;
    private long startNodeId;
    private long nodeId;
    private JobNetNodeDBItem jobNetNodeDBItem;
    private JobNetNodeDBItem jobNetStartNodeDBItem;

	public JobNetDBItem() {
		super();
	}

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="NET_ID")
    public Long getNetId() {
		return netId;
	}

	@Column(name="NET_ID")
    public void setNetId(Long netId) {
		this.netId = netId;
	}
	
	   @ManyToOne (optional=true)
	    @NotFound( action = NotFoundAction.IGNORE )
	    @JoinColumn (name="`START_NODE_ID`", insertable = false, updatable = false)
	    public JobNetNodeDBItem getJobNetStartNodeDBItem() {
	        return jobNetStartNodeDBItem;
	    }
	    
	    public void setJobNetStartNodeDBItem(JobNetNodeDBItem jobNetStartNodeDBItem)  {
	       this.jobNetStartNodeDBItem = jobNetStartNodeDBItem; 
	    }
	    
	  
 	    @Column(name="START_NODE_ID")
	    public Long getStartNodeId() {
	        return startNodeId;
	    }

	    @Column(name="START_NODE_ID")
	    public void setStartNodeId(Long startNodeId) {
	        this.startNodeId = startNodeId;
	    }
	      
    @Column(name="SCHEDULER_ID",nullable=false)
	public void setSchedulerId(String schedulerId) {
		this.schedulerId = schedulerId;
	}
    
    @Column(name="SCHEDULER_ID",nullable=false)
	public String getSchedulerId() {
		return schedulerId;
	}
     
	 
    @Column(name="JOB_NET",nullable=true)
	public void setJobNet(String i) {
		this.jobNet = i;
	}
 
    @Column(name="JOB_NET",nullable=true)
	public String getJobNet() {
		return jobNet;
	} 
   
    
    @Transient
    @Override
    public String getIdentifier() {
        return jobNet;
    }
     
    
    @Transient
    @Override
    public String getTitle() {
        return jobNet;
    }
     
    
   
}
