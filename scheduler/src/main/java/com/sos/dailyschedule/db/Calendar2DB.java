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
package com.sos.dailyschedule.db;


import java.io.File;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import com.sos.JSHelper.Logging.Log4JHelper;
import com.sos.dailyschedule.job.CreateDailyScheduleOptions;
import com.sos.scheduler.model.SchedulerObjectFactory;
import com.sos.scheduler.model.SchedulerObjectFactory.enu4What;
import com.sos.scheduler.model.answers.At;
import com.sos.scheduler.model.answers.Calendar;
import com.sos.scheduler.model.answers.Order;
import com.sos.scheduler.model.answers.Period;
import com.sos.scheduler.model.answers.State;
import com.sos.scheduler.model.commands.JSCmdShowCalendar;
import com.sos.scheduler.model.commands.JSCmdShowOrder;
import com.sos.scheduler.model.commands.JSCmdShowState;
import com.sos.scheduler.model.objects.Spooler;


/**
* \class Calendar2DBItem 
* 
* \brief Calendar2DBItem - 
* 
* \details
*
* \section Calendar2DBItem.java_intro_sec Introduction
*
* \section Calendar2DBItem.java_samples Some Samples
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
* \version 23.09.2011
* \see reference
*
* Created on 23.09.2011 14:19:47
 */

public class Calendar2DB {

	@SuppressWarnings("unused")
	private final String	conClassName	= "Calender2DB";
	private static Logger					logger			= Logger.getLogger(Calendar2DB.class);
	@SuppressWarnings("unused")
	private static Log4JHelper				objLogger		= null;
	private static SchedulerObjectFactory	objFactory		= null;
	 
	private Date from;
	private Date to;
	private int dayOffset;
	private String schedulerId="";
	
	private String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";
	private DailyScheduleDBLayer dailySchedulerDBLayer;;
	private CreateDailyScheduleOptions options=null;

	public Calendar2DB(File configurationFile) {
		dailySchedulerDBLayer = new DailyScheduleDBLayer(configurationFile);
	}
	
	private void initSchedulerConnection()   {
		if (schedulerId.equals("")){
     		objLogger = new Log4JHelper("./log4j.properties");
	    	logger = Logger.getRootLogger();
		    logger.debug("Calender2DB");
		    objFactory = new SchedulerObjectFactory(options.getSchedulerHostName().Value(), options.getscheduler_port().value());
		    objFactory.initMarshaller(Spooler.class);
		    dayOffset = options.getdayOffset().value();
            schedulerId = this.getSchedulerId();
		}

	 
	}
	
	

	private Calendar getCalender()  {
		initSchedulerConnection();

		JSCmdShowCalendar objSC = objFactory.createShowCalendar(); 
		objSC.setWhat("orders");
		objSC.setLimit(9999);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
		objSC.setFrom(sdf.format(from));
		sdf = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
		objSC.setBefore(sdf.format(to));
		objSC.run();
		return  objSC.getCalendar();
	}
	
	public void delete() {
		initSchedulerConnection();

  		dailySchedulerDBLayer.setWhereFrom(from);
		dailySchedulerDBLayer.setWhereTo(to);
		dailySchedulerDBLayer.setWhereSchedulerId(schedulerId);
		dailySchedulerDBLayer.delete();
  	}	

	private String getSchedulerId() {
		//<show_state path="/notexisst_sos" what="folders no_subfolders" subsystems="folder"/>
		JSCmdShowState objCmdShowState = objFactory.createShowState(new enu4What[] { enu4What.folders, enu4What.no_subfolders }); // http://localhost:4444/doc/en/xml/show_state.xml
		objCmdShowState.setPath("notexist_sos");
		objCmdShowState.setSubsystems("folder");
		objCmdShowState.setMaxTaskHistory(BigInteger.valueOf(1));
 		objCmdShowState.run();
		State objState = objCmdShowState.getState();
		return objState.getSpoolerId();
	}
	
	private boolean isSetback(String jobChain, String orderId) {
		JSCmdShowOrder objSO = objFactory.createShowOrder();
		objSO.setJobChain(jobChain);
		objSO.setOrder(orderId);
		objSO.run();
		Order order = objSO.getAnswer().getOrder();
		return (order.getSetback() != null);
	}
	
    public void store() throws ParseException {
        dailySchedulerDBLayer.beginTransaction();

        this.delete();

        Calendar objCalendar = getCalender();

        for (Object objCalendarObject : objCalendar.getAtOrPeriod()) {
            DailyScheduleDBItem dailySchedulerDBItem = new DailyScheduleDBItem(this.dateFormat);
            dailySchedulerDBItem.setSchedulerId(schedulerId);
            if (objCalendarObject instanceof At) {

                At objAt = (At) objCalendarObject;
                objAt.setParent(objFactory);

                String orderId = objAt.getOrder();
                String jobChain = objAt.getJobChain();
                String job = objAt.getJob();

                dailySchedulerDBItem.setJob(job);
                dailySchedulerDBItem.setJobChain(jobChain);
                dailySchedulerDBItem.setOrderId(orderId);

                if (orderId == null || !isSetback(jobChain, orderId)) {
                    dailySchedulerDBItem.setSchedulePlanned(objAt.getAt());

                    logger.debug("Start at :" + objAt.getAt());
                    logger.debug("Job Name :" + job);
                    logger.debug("Job-Chain Name :" + jobChain);
                    logger.debug("Order Name :" + orderId);

                }
                else {
                    logger.debug("Job-Chain Name :" + jobChain + "/" + orderId + " ignored because order is in setback state");
                }

            }
            else {
                if (objCalendarObject instanceof Period) {
                    Period objPeriod = (Period) objCalendarObject;
                    objPeriod.setParent(objFactory);
                    dailySchedulerDBItem.setJob(objPeriod.getJob());
                    dailySchedulerDBItem.setJobChain(objPeriod.getJobChain());
                    dailySchedulerDBItem.setOrderId(objPeriod.getOrder());

                    dailySchedulerDBItem.setPeriodBegin(objPeriod.getBegin());
                    dailySchedulerDBItem.setPeriodEnd(objPeriod.getEnd());

                    dailySchedulerDBItem.setRepeat(objPeriod.getAbsoluteRepeat(), objPeriod.getRepeat());
                    // logger.debug(objPeriod.toXMLString());
                    logger.debug("Absolute Repeat Interval :" + objPeriod.getAbsoluteRepeat());
                    logger.debug("Timerange start :" + objPeriod.getBegin());
                    logger.debug("Timerange end :" + objPeriod.getEnd());
                    logger.debug("Job-Name :" + objPeriod.getJob());
                }
            }
            dailySchedulerDBItem.setResult(0);
            dailySchedulerDBItem.setStatus(0);
            dailySchedulerDBItem.setModified(new Date());
            dailySchedulerDBItem.setCreated(new Date());
            if (dailySchedulerDBItem.getSchedulePlanned() != null) {
                if (dailySchedulerDBItem.getJob() == null || !dailySchedulerDBItem.getJob().equals("(Spooler)")) {
                   dailySchedulerDBLayer.save(dailySchedulerDBItem);
                }
            }

        }
        dailySchedulerDBLayer.commit();
    }

	/*public void setFrom(String from) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date d = formatter.parse(from);
		String froms = formatter.format(d);
		froms = froms + "T00:00:00";
		formatter = new SimpleDateFormat(dateFormat);
		this.from = formatter.parse(froms);
	}
	
	public void setTo(String to) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date d = formatter.parse(to);
 		String tos = formatter.format(d);
		tos = tos + "T23:59:59";
		formatter = new SimpleDateFormat(dateFormat);
		this.to = formatter.parse(tos);
	}
	*/
	private void setFrom() throws ParseException {
		Date now = new Date();
		if (dayOffset < 0) {
		   GregorianCalendar calendar = new GregorianCalendar();
		   calendar.setTime(now);
		   calendar.add(GregorianCalendar.DAY_OF_MONTH, dayOffset);
		   now = calendar.getTime();
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String froms = formatter.format(now);
    	froms = froms + "T00:00:00";
		formatter = new SimpleDateFormat(dateFormat);
		this.from = formatter.parse(froms);	
	}
	
	private void setTo() throws ParseException {
		Date now = new Date();
		if (dayOffset > 0) {
		   GregorianCalendar calendar = new GregorianCalendar();
		   calendar.setTime(now);
		   calendar.add(GregorianCalendar.DAY_OF_MONTH, dayOffset);
		   now = calendar.getTime();
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String tos = formatter.format(now);
    	tos = tos + "T23:59:59";
		formatter = new SimpleDateFormat(dateFormat);
		this.to = formatter.parse(tos);
	}
	
	public Date getFrom() {
		return from;
	}

	public Date getTo() {
		return to;
	}

	public void setOptions(CreateDailyScheduleOptions options) throws ParseException {
		this.options = options;
		dayOffset = options.getdayOffset().value();
		setFrom();
		setTo();
	}
}
