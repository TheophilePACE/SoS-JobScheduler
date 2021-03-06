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
package com.sos.VirtualFileSystem.Interfaces;

import com.sos.JSHelper.Options.SOSOptionHostName;
import com.sos.JSHelper.Options.SOSOptionPassword;
import com.sos.JSHelper.Options.SOSOptionPortNumber;
import com.sos.JSHelper.Options.SOSOptionString;
import com.sos.JSHelper.Options.SOSOptionUserName;

/**
* \class ISOSConnectionOptions 
* 
* \brief ISOSConnectionOptions - 
* 
* \details
*
* \section ISOSConnectionOptions.java_intro_sec Introduction
*
* \section ISOSConnectionOptions.java_samples Some Samples
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
* \author KB
* @version $Id: ISOSConnectionOptions.java 17949 2012-08-31 18:27:00Z kb $21.05.2010
* \see reference
*
* Created on 21.05.2010 11:39:42
 */

public interface ISOSConnectionOptions {

	/**
	 * \brief gethost
	 *
	 * \details
	 * getter 
	 *
	 * @return the host
	 */
	public SOSOptionHostName getHost() ;
	public void setalternative_host(SOSOptionHostName p_alternative_host) ;
	public SOSOptionHostName getalternative_host();

	public void setalternative_password(SOSOptionPassword p_alternative_password);
	public SOSOptionPassword getalternative_password();
	
	public SOSOptionString getalternative_account();
	public SOSOptionString getalternative_passive_mode();
	public SOSOptionPortNumber getalternative_port() ;
	
//	public SOSConnection2Options getConnectionOptions();
	/**
	 * \brief sethost - 
	 *
	 * \details
	 * setter 
	 *
	 * @param host the value for host to set
	 */
	public abstract void setHost(SOSOptionHostName host);

	/**
	 * \brief getport
	 *
	 * \details
	 * getter 
	 *
	 * @return the port
	 */
	public abstract SOSOptionPortNumber getPort();

	/**
	 * \brief setport - 
	 *
	 * \details
	 * setter 
	 *
	 * @param port the value for port to set
	 */
	public abstract void setPort(SOSOptionPortNumber port);

	/**
	 * \brief getproxy_host
	 *
	 * \details
	 * getter 
	 *
	 * @return the proxy_host
	 */
	public abstract SOSOptionString getProxy_host();

	/**
	 * \brief setproxy_host - 
	 *
	 * \details
	 * setter 
	 *
	 * @param proxyHost the value for proxy_host to set
	 */
	public abstract void setProxy_host(SOSOptionString proxyHost);

	/**
	 * \brief getproxy_port
	 *
	 * \details
	 * getter 
	 *
	 * @return the proxy_port
	 */
	public abstract SOSOptionPortNumber getProxy_port();

	/**
	 * \brief setproxy_port - 
	 *
	 * \details
	 * setter 
	 *
	 * @param proxyPort the value for proxy_port to set
	 */
	public abstract void setProxy_port(SOSOptionPortNumber proxyPort);

	
	public abstract void setProxy_user(SOSOptionUserName proxyUser);
	public abstract SOSOptionUserName getProxy_user();

	public abstract void setProxy_password(SOSOptionPassword proxyPassword);
	public abstract SOSOptionPassword getProxy_password();


}
