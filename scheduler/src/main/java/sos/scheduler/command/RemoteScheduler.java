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
 * RemoteScheduler.java
 * Created on 30.05.2008
 * 
 */
package sos.scheduler.command;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import sos.xml.SOSXMLXPath;

public class RemoteScheduler {
	private String host;
	
	private int tcpPort;
	
	private int udpPort;
	
	public RemoteScheduler(String host, int tcpPort){
		this.host = host;
		this.tcpPort = tcpPort;
	}
	
	public RemoteScheduler(String host, int tcpPort, int udpPort){
		this.host = host;
		this.tcpPort = tcpPort;
		this.udpPort = udpPort;
	}
	
	public String sendCommand(String command)throws Exception{
		SOSSchedulerCommand schedulerCommand = new SOSSchedulerCommand();
        
         schedulerCommand.setHost(getHost());
         schedulerCommand.setPort(getTcpPort());
        
       schedulerCommand.setProtocol("tcp");                        
        try{        	
        	schedulerCommand.connect();
       		schedulerCommand.sendRequest(command);
       		String response = schedulerCommand.getResponse();
        	return response;
        } catch(Exception e){
        	throw new Exception("Error contacting remote Job Scheduler "+this+": "+e,e);
        } finally{
        	try{
        		schedulerCommand.disconnect();
        	}catch (Exception ex){}
        }
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the tcpPort
	 */
	public int getTcpPort() {
		return tcpPort;
	}

	/**
	 * @param tcpPort the tcpPort to set
	 */
	public void setTcpPort(int tcpPort) {
		this.tcpPort = tcpPort;
	}

	/**
	 * @return the udpPort
	 */
	public int getUdpPort() {
		return udpPort;
	}

	/**
	 * @param udpPort the udpPort to set
	 */
	public void setUdpPort(int udpPort) {
		this.udpPort = udpPort;
	}

	public String toString() {
		return this.host+":"+this.tcpPort;
	}
}
