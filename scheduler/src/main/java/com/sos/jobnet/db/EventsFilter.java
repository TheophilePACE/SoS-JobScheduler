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

import java.util.ArrayList;
import java.util.List;

public class EventsFilter extends SOSHibernateFilter implements com.sos.hibernate.interfaces.ISOSHibernateFilter {

	private List<String> eventList = new ArrayList<String>();
	private String eventClass = null;
	
	@Override
	public String getTitle() {
		return "";
	}

	@Override
	public boolean isFiltered(DbItem arg0) {
		return false;
	}
	
	public List<String> getEventList() {
		return eventList;
	}
	
	public void setEventList(List<String> eventList) {
		this.eventList.clear();
		for(String e : eventList)
			if(!e.isEmpty()) this.eventList.add(e);
	}

	public void addEventId(String eventId) {
		this.eventList.add(eventId);
	}
	
	public boolean hasEvents() {
		return (!eventList.isEmpty());
	}
	
	public void setEventClass(String eventClass) {
		this.eventClass = eventClass;
	}
	
	public String getEventClass() {
		return eventClass;
	}
	
	public boolean hasEventClass() {
		return (eventClass != null && !eventClass.isEmpty());
	}

}
