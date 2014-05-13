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
package com.sos.scheduler.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import org.apache.log4j.Logger;

public class SchedulerSocket extends Socket {

	@SuppressWarnings("unused")
	private final String					conClassName	= "SchedulerSocket";

	private static final Logger logger = Logger.getLogger(SchedulerSocket.class);

	private BufferedReader					in				= null;
	private PrintWriter						out				= null;
	private SchedulerObjectFactoryOptions	objOptions		= null;

	private int								timeout			= 60;

	public SchedulerSocket() {
		super();
	}

	public SchedulerSocket(final SchedulerObjectFactoryOptions pobjOptions) throws Exception {
		super(pobjOptions.ServerName.Value(), pobjOptions.PortNumber.value());
		logger.debug("super() is executed ...");
		objOptions = pobjOptions;
		logger.debug("try to connect ...");
//		this.connect();
	}

	public void connect(final SchedulerObjectFactoryOptions pobjOptions) throws IOException {

		objOptions = pobjOptions;
		this.connect();
	}

	public void connect() {
		InetAddress addr;
		try {
			String strHost = objOptions.ServerName.Value();
			addr = InetAddress.getByName(strHost);
			int intPortnumber = objOptions.PortNumber.value();
			if (objOptions.TransferMethod.isTcp() == false) {
				intPortnumber = objOptions.UDPPortNumber.value();
			}
			SocketAddress sockaddr = new InetSocketAddress(addr, intPortnumber);
			timeout = objOptions.TCPTimeoutValue.value();
			super.connect(sockaddr, timeout);
		}
		catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Returns a stream of type PrintWriter
	 */
	public PrintWriter getPrintWriter() throws IOException {
		if (out == null) {
			out = new PrintWriter(super.getOutputStream(), true);
		}
		return out;
	}

	/*
	 * Returns a stream of type BufferedReader
	 */
	public BufferedReader getBufferedReader() throws IOException {
		if (in == null) {
			in = new BufferedReader(new InputStreamReader(super.getInputStream()));
		}
		return in;
	}

	/*
	 * Flush the SchedulerOutputStream before closing the socket.
	 */
	public synchronized void doClose() throws IOException {
		if (getOutputStream() != null)
			getOutputStream().flush();
		super.close();
	}

	/**
	 * sends a query to the scheduler
	 *
	 * @param request
	 * @throws java.lang.Exception
	 */
	public void sendRequest(final String request) throws Exception {
		out = getPrintWriter();
		if (out != null) {
			if (request.indexOf("<?xml") == 0) {
				out.print(request + "\r\n");
			}
			else {
				out.print("<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>" + request + "\r\n");
			}
			out.flush();
		}
	}

	/**
	 * returns the last response from the scheduler
	 *
	 * @return String the scheduler response, else empty string
	 * @throws IOException, RuntimeException
	 */
	public String getResponse() throws IOException, RuntimeException {
		int b;
		StringBuffer response = new StringBuffer();
		in = getBufferedReader();
		if (in != null) {
			while ((b = in.read()) != -1) {
				if (b == 0)
					break;
				response.append((char) b);

			} // while
		}

		return response.toString();
	}

}
