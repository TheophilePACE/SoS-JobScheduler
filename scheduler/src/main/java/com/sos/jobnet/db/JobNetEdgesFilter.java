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
import com.sos.hibernate.classes.SOSHibernateFilter;
import com.sos.jobnet.classes.EdgeType;

//import com.sos.jobnet.interfaces.ISOSJobNetPlanItem;
 
/**
* \class JobNetEdgesFilter 
* 
* \brief JobNetEdgesFilter - 
* 
* \details
*
* \section JobNetEdgesFilter.java_intro_sec Introduction
*
* \section JobNetEdgesFilter.java_samples Some Samples
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
* \version 14.12.2011
* \see reference
*
* Created on 14.12.2011 13:53:37
 */

//public class JobNetEdgesFilter extends SOSHibernateFilter implements com.sos.hibernate.interfaces.ISOSHibernateFilter, ISOSJobNetPlanItem {
public class JobNetEdgesFilter extends SOSHibernateFilter implements com.sos.hibernate.interfaces.ISOSHibernateFilter {

	@SuppressWarnings("unused")
	private final String	conClassName	= "JobNetEdgesFilter";
    public  final String	conSVNVersion	= "$Id: JobNetEdgesFilter.java 20189 2013-05-17 14:48:44Z ss $";
    private String node;
    private EdgeType edgeType = new EdgeType();
	private String jobChain;
	private String schedulerId="";
	private String searchField;	 
	private Long parentNodeId;
	private Long childNodeId;
	
	

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getJobChain() {
		return jobChain;
	}

	public void setJobChain(String jobChain) {
		this.jobChain = jobChain;
	}


	public String getSchedulerId() {
		return schedulerId;
	}

	public void setSchedulerId(String schedulerId) {
		this.schedulerId = schedulerId;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}



	public String getSearchField() {
		return searchField;
	}

	public void setSearchFiled(String searchField) {
		this.searchField = searchField;
	}
	

	public EdgeType getEdgeType() {
		return edgeType;
	}


	public void setEdgeType(EdgeType edgeType) {
		this.edgeType = edgeType;
	}

	public void setEdgeType(String edgeType_)    {
 		try {
			edgeType.setEdgeType(edgeType_);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setEdgeTypeSuccessors(){
		edgeType.setEdgeTypeSuccessors();
	}

	public void setEdgeTypePredecessors(){
 		edgeType.setEdgeTypePredecessors();
	}	
	
	@Override
	public String getTitle() {
 		return "";
	}

	@Override
	public boolean isFiltered(DbItem h) {
		return false;
 	}

	public Long getParentNodeId() {
		return parentNodeId;
	}


	public void setParentNodeId(Long parentId) {
		this.parentNodeId = parentId;
	}

	public Long getChildNodeId() {
		return childNodeId;
	}


	public void setChildNodeId(Long childNodeId) {
		this.childNodeId = childNodeId;
	}
}
