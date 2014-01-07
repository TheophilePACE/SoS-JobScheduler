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
package com.sos.jobnet.classes;

import java.io.IOException;
import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.sos.JSHelper.Options.SOSOptionString;
import com.sos.JSHelper.Options.SOSOptionUUID;

public class JobNetProtocolBase {
	
	private final static Logger logger = Logger.getLogger(JobNetProtocolBase.class);
	
    private final Logger jobnetProtocol;
//	private final ChainExtractor livePath;
    private final String log4jConfigFileName;
    private final String orderId;
	
//	private class ChainExtractor {
//		protected final String chain;
//		protected ChainExtractor(String orderWithPath) {
//			int i = orderWithPath.lastIndexOf("/");
//			chain = (i > 0) ? orderWithPath.substring(i+1) : orderWithPath;
//		}
//	}
	
	public JobNetProtocolBase(String orderId, SOSOptionString orderWithPath, SOSOptionUUID uuid, Logger logger, String appenderName) {
		
		boolean log4jDebug = (System.getProperty("log4j.debug") == null) ? false : Boolean.getBoolean(System.getProperty("log4j.debug")) ;
		this.log4jConfigFileName = System.getProperty("log4j.configuration");
		if (log4jDebug) logger.info("Log4j-Debug mode is configured");
		logger.info("Log4j-Configuration file is " + log4jConfigFileName);
		
//		this.livePath = new ChainExtractor(orderWithPath.Value());
		this.jobnetProtocol = logger;
		this.orderId = orderId;
		modifyAppender(appenderName, uuid);
	
	}
	
	private void modifyAppender(String appenderName, SOSOptionUUID uuid) {
		Appender a = jobnetProtocol.getAppender(appenderName);
		if (a instanceof FileAppender) {
			FileAppender fa = (FileAppender)a;
			logger.debug("the current file for jobnet protocol is " + fa.getFile());
			if (!fa.getFile().contains(uuid.Value())) {
				String id = uuid.Value();
				String newName = getProtocolFilename(fa, id);
				try {
					fa.setFile(newName,true,false,0);
				} catch (IOException e) {
					logger.warn("Error to set log file " + newName);
				}
				fa.activateOptions();
				logger.info("Writing jobnet protocol to " + fa.getFile());
			}
		} else {
			logger.warn("Appender '" + appenderName + "' not found - could not write jobnet protocol.");
		}
	}
	
	private String getProtocolFilename(FileAppender currentAppender, String jobnetId) {
		int i = currentAppender.getFile().lastIndexOf(".");
		String filename = currentAppender.getFile().substring(0,i) + "." + jobnetId;
		if (i > 0) filename += currentAppender.getFile().substring(i);
		return filename;
	}
	
	public void log(Level level, String message, Throwable error) {
		jobnetProtocol.log(level, formatMessage(message), error);
	}
	
	public void log(Level level, String message) {
		jobnetProtocol.log(level, formatMessage(message));
	}
	
	private String formatMessage(String message) {
		return String.format("[%-80s] %s", orderId, message);
	}
	
	public void info(String message) {
		log(Level.INFO,message);
	}

}
