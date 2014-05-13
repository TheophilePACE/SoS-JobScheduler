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
 * ftp4j - A pure Java FTP client library
 * 
 * Copyright (C) 2008-2010 Carlo Pelliccia (www.sauronsoftware.it)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version
 * 2.1, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License 2.1 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License version 2.1 along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package com.sos.VirtualFileSystem.FTP;

import java.util.ArrayList;

/**
 * This class represents FTP server replies in a manageable object oriented way.
 * 
 * @author Carlo Pelliccia
 */  
public class SOSFtpServerReply {

	/**
	 * The reply code.
	 */
	private int			code	= 0;

	/**
	 * The reply message(s).
	 */
	private String[]	messages;

	/**
	 * Build the reply.
	 * 
	 * @param code1
	 *            The code of the reply.
	 * @param message
	 *            The textual message(s) in the reply.
	 */
	SOSFtpServerReply(int code1, String[] messages1) {
		this.code = code1;
		this.messages = messages1;
	}

	SOSFtpServerReply(final String messages1) {
		ParseServerReply(messages1);
	}

	public void ParseServerReply(final String pstrReply) {
		if (pstrReply == null) {
			return;
		}
		
		int code1 = 0;
		ArrayList<String> messages1 = new ArrayList<String>();
		String[] strM = pstrReply.split("\r\n");
		for (String statement : strM) {
			if (statement.startsWith("\n")) {
				statement = statement.substring(1);
			}
			int l = statement.length();
			if (code1 == 0 && l < 3) {
				throw new FTPIllegalReplyException();
			}
			int aux;
			try {
				aux = Integer.parseInt(statement.substring(0, 3));
			}
			catch (Exception e) {
				if (code1 == 0) {
					throw new FTPIllegalReplyException();
				}
				else {
					aux = 0;
				}
			}
			if (code1 != 0 && aux != 0 && aux != code1) {
				throw new FTPIllegalReplyException();
			}
			if (code1 == 0) {
				code1 = aux;
			}
			if (aux > 0) {
				if (l > 3) {
					char s = statement.charAt(3);
					String message = statement.substring(4, l);
					messages1.add(message);
					if (s == ' ') {
						break;
					}
					else
						if (s == '-') {
							continue;
						}
						else {
							throw new FTPIllegalReplyException();
						}
				}
				else
					if (l == 3) {
						break;
					}
					else {
						messages1.add(statement);
					}
			}
			else {
				messages1.add(statement);
			}
		}

		int size = messages1.size();

		String[] m = new String[size];
		for (int i = 0; i < size; i++) {
			m[i] = (String) messages1.get(i);
		}

		this.code = code1;
		this.messages = m;
	}

	/**
	 * Returns the code of the reply.
	 * 
	 * @return The code of the reply.
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Returns true if the code of the reply is in the range of success codes
	 * (2**).
	 * 
	 * @return true if the code of the reply is in the range of success codes
	 *         (2**).
	 */
	public boolean isSuccessCode() {
		int aux = code - 200;
		return aux >= 0 && aux < 100;
	}

	/**
	 * Returns the textual message(s) of the reply.
	 * 
	 * @return The textual message(s) of the reply.
	 */
	public String[] getMessages() {
		return messages;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getClass().getName());
		buffer.append(" [code=");
		buffer.append(code);
		buffer.append(", message=");
		for (int i = 0; i < messages.length; i++) {
			if (i > 0) {
				buffer.append(" ");
			}
			buffer.append(messages[i]);
		}
		buffer.append("]");
		return buffer.toString();
	}

}
