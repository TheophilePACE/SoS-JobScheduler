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
/*
 * JobSchedulerCriticalSection.java
 * Created on 01.06.2006
 * 
 */
package sos.scheduler.job;

import sos.spooler.Job_impl;
import sos.spooler.Spooler;

public class JobSchedulerCriticalSection {
	
	
	// no real mutexes are used in this implementation
	// a delay ensures the critical section to work	
	private long delay = 200;
	private long dDelay = 2000;
	
	// name of the mutex
	private String mutex = "section";
	
	// current task id
	private String taskId ="";
	
	private Spooler spooler;
	private Job_impl job;
	
	private static final String GREEN = "GREEN";	
	private static final String RED = "RED";	
	private static final String ORANGE = "ORANGE";
	
	public JobSchedulerCriticalSection(Job_impl job, String mutex){
		this.spooler = job.spooler;
		this.job = job;
		this.mutex = mutex+"_MUTEX";
		this.taskId = job.spooler_task.id()+"";
		job.spooler_log.debug9("Critical section \""+mutex+"\" initialized.");
	}
	
	public JobSchedulerCriticalSection(Job_impl job, String mutex, long delay){		
		this(job, mutex);
		this.delay = delay;
		this.dDelay = delay*10;		
	}
	
	/**
	 * Enters a critical section.<br/>
	 * Method waits until it is allowed to enter
	 * @param timeNeeded time in milliseconds needed until caller
	 * is ready to exit the critical section again (call exit()).
	 * If timeNeeded has passed without calling exit, other task
	 * may enter the critical section. 
	 */
	public void enter(long timeNeeded){
		//job.spooler_log.info("entered critical section");
		long now = System.currentTimeMillis();
		String status = spooler.var(mutex);
		try{
		while (!(status==null || status.equalsIgnoreCase(GREEN) || status.length()==0 ) ){
			//job.spooler_log.info("Im while, Status: "+status);
			String[] split = status.split(";");
			// if not green, status has the form "color;taskid;time" e.g.
			// ORANGE;3421;879879872
			long timeout = Long.parseLong(split[2]);
			// timeout eingetreten
			if(timeout<now) status = GREEN;
			else{ // weiter warten
				try{
					Thread.sleep(5);
				} catch(Exception ex){}
				now = System.currentTimeMillis();
				status = spooler.var(mutex);
			}			
		}
		//job.spooler_log.info("go Orange!");
		goOrange(now+dDelay);		
		// jetzt nochmal �berpr�fen, ob kein anderer Orange gesetzt hat:
		status = spooler.var(mutex);
		//job.spooler_log.info("go Orange: "+status);
		
		if (status.startsWith(GREEN)){
			// sollte nicht passieren, passiert aber bei zu engem delay
			// nochmal probieren:
			enter(timeNeeded);
			return;
		}
		String[] split = status.split(";");
		
		if (split[1].equalsIgnoreCase(taskId)){			
			// immer noch der gleiche Thread also auf Rot gehen:
			goRed(timeNeeded);
		}else{
			// ein anderer konkurriert und hat als letzter seine ID
			// gesetzt. Also nochmal probieren
			enter(timeNeeded);
		}
		} catch(ArrayIndexOutOfBoundsException e){
			job.spooler_log.info("Status: "+status);
			throw e;
		}
	}
	
	private final void goOrange(long until){
		spooler.set_var(mutex,ORANGE+";"+taskId+";"+until);
		try{
			Thread.sleep(delay);
		}catch (Exception e){}
	}
	
	private final void goRed(long timeNeeded){
		long now = System.currentTimeMillis();
		spooler.set_var(mutex, RED+";"+taskId+";"+(now+timeNeeded));
	}
	
	public void exit(){		
		String status = spooler.var(mutex);
		if (status!=null){
			if (status.startsWith(GREEN)){
				job.spooler_log.info("Trying to exit critical section \""+mutex+"\", but"
						+" it is already GREEN");
				return;
			}
					
			String[] split = status.split(";");
			if (split[1].equalsIgnoreCase(taskId)){
				spooler.set_var(mutex,GREEN);
				job.spooler_log.debug3("Exited critical section \""+mutex+"\".");
			} else {
				job.spooler_log.info("Trying to exit critical section \""+mutex+"\", but"
						+" it already belongs to task "+ split[1]);
			}
		}
		
	}
}
