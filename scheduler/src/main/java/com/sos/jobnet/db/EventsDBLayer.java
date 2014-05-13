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

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.joda.time.DateTime;

import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.hibernate.layer.SOSHibernateDBLayer;
import com.sos.jobnet.classes.EventsCollection;

public class EventsDBLayer extends SOSHibernateDBLayer {

	private final static Logger logger = Logger.getLogger(EventsDBLayer.class);

	private final static String EVENT_ID = "eventId";
	private final static String EVENT_CLASS = "eventClass";
	
	public EventsDBLayer(File configurationFile) {
        super();
        this.setConfigurationFile(configurationFile);
	}
	
	public EventsDBLayer(File configurationFile, EventsFilter filter) {
        super();
        this.setConfigurationFile(configurationFile);
	}
	
    private Query setQueryParams(EventsFilter filter, String hql) {
		Query query = null;
		try {
			query = session.createQuery(hql);
			if (filter.hasEvents())
		        query.setParameterList(EVENT_ID, filter.getEventList());
			if (filter.hasEventClass())
		        query.setParameter(EVENT_CLASS, filter.getEventClass());
		} catch(HibernateException e) {
			throw new JobSchedulerException("Error creating Query",e);
		}
		return query;
	}

	private String getWhere(EventsFilter filter) {
		String where = "";
		String and = "";

        if (filter.hasEvents()) {
            where += and + " eventId in ( :eventId )";
            and = " and ";
        }

        if (filter.hasEventClass()) {
            where += and + " eventClass = :eventClass";
            and = " and ";
        }

		return (where.isEmpty()) ? where : "where " + where;

	}

	/**
	 * Checks the events in the eventList if present and returns a list of all events still missing.
	 * @param eventList
	 * @return
	 */
	public EventsCollection checkEvents(List<String> eventList) {
        EventsCollection missingEvents = new EventsCollection();
		EventsFilter filter = new EventsFilter();
		filter.setEventList(eventList);
		if(filter.getEventList().isEmpty()) {
			logger.info("no events to check - eventList is empty.");
		} else {
	    	if (!getSession().isOpen()) initSession();
			logger.info("check events " + filter.getEventList().toString());
			Query query = setQueryParams(filter, "from EventsDBItem table_nodes "
					+ getWhere(filter));
					// + this.filter.getOrderCriteria()
					// + this.filter.getSortMode());
	
			@SuppressWarnings("unchecked")
			List<EventsDBItem> resultList = query.list();
			missingEvents.addAll(eventList);
			for(EventsDBItem item : resultList) {
				missingEvents.remove(item.getEventId());
			}
			logger.info("missing events " + missingEvents.toString());
		}
		return missingEvents;
	}
	
	public boolean isEventThrown(String eventId) {
		List<String> events = Arrays.asList(eventId);
		return (checkEvents(events).size() == 0);
	}
	
	public void createEvent(EventsDBItem record) {
		DateTime now = new DateTime();
		DateTime expired = now.plusDays(60);
		beginTransaction();
        record.setCreated( new DateTime() );
        record.setExpires(expired);
        saveOrUpdate(record);
        commit();
	}
	
	public void deleteEventsForClass(String eventClass) {
		EventsFilter filter = new EventsFilter();
		filter.setEventClass(eventClass);
    	if (!getSession().isOpen()) initSession();
		Query query = setQueryParams(filter, "from EventsDBItem table_nodes "
				+ getWhere(filter));
				// + this.filter.getOrderCriteria()
				// + this.filter.getSortMode());

		@SuppressWarnings("unchecked")
		List<EventsDBItem> resultList = query.list();
		logger.info("Delete " + resultList.size() + " events for eventClass " + eventClass);
		for(EventsDBItem item : resultList) {
			delete(item);
		}
	}

}
