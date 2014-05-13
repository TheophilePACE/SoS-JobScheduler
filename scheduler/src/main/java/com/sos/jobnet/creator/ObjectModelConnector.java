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
package com.sos.jobnet.creator;

import com.sos.JSHelper.Options.SOSOptionFolderName;
import com.sos.JSHelper.Options.SOSOptionHostName;
import com.sos.JSHelper.Options.SOSOptionPortNumber;
import com.sos.VirtualFileSystem.Interfaces.ISOSVfsFileTransfer;
import com.sos.jobnet.classes.JobNetException;
import com.sos.scheduler.model.LiveConnector;
import com.sos.scheduler.model.SchedulerObjectFactory;
import com.sos.scheduler.model.objects.Spooler;
import com.sos.scheduler.model.tools.PathResolver;
import org.apache.log4j.Logger;

public class ObjectModelConnector {

	private final static Logger logger = Logger.getLogger(ObjectModelConnector.class);

	private final SchedulerObjectFactory objectFactory;
	private final LiveConnector connector;
	
	private static ObjectModelConnector instance = null;

	private ObjectModelConnector(SOSOptionHostName host, SOSOptionPortNumber port, SOSOptionFolderName folder) {
        try {
            objectFactory = new SchedulerObjectFactory();
            objectFactory.initMarshaller(Spooler.class);
            objectFactory.Options().ServerName = host;
            objectFactory.Options().PortNumber = port;
            logger.info("Object factory connected with " + getHostAndPort() + ".");
        } catch(Exception e) {
            throw new JobNetException("Unable to connect with SchedulerObjectFactory for server " + getHostAndPort());
        }

        String path = PathResolver.resolvePath(folder.Value());
        try {
            connector = new LiveConnector( LiveConnector.getUrl(path) );
            logger.info("The configuration directory is " + getRootDirectory() + ".");
        } catch(Exception e) {
            throw new JobNetException("Unable to connect with live folder" + path);
        }
	}

    private String getHostAndPort() {
        String host = (objectFactory.Options().ServerName.Value().trim().isEmpty()) ? "???" : objectFactory.Options().ServerName.Value();
        String port = (objectFactory.Options().PortNumber.Value().equals("0")) ? "???" : objectFactory.Options().PortNumber.Value();
        return host + ":" + port;
    }

	public static ObjectModelConnector getInstance(SOSOptionHostName host, SOSOptionPortNumber port, SOSOptionFolderName folder) {
		if (instance == null) 
			instance = new ObjectModelConnector(host, port, folder);
		return instance;
	}
	
	public SchedulerObjectFactory getFactory() {
		return objectFactory;
	}

	public ISOSVfsFileTransfer getFileHandler() {
		return connector.getFileSystemHandler();
	}
	
	public String getWorkingDirectory() {
		return connector.getCurrentFolder();
	}
	
	public String getRootDirectory() {
		return connector.getLiveFolder();
	}
	
	public void setWorkingDirectory(String directory) {
		connector.setCurrentFolder(directory);
	}
	
	/**
	 * Gets the base folder for an order. An order like /folder/orderId bases on the root directory (e.g. the
	 * live folder. Order with a relative name such as ../folder/orderId based on the working directory.
	 * @param baseName
	 * @return
	 */
	public String selectBaseFolder(String baseName) {
		return connector.selectBaseFolder(baseName);
	}

}
