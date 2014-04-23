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

import org.apache.log4j.Logger;

import com.sos.jobnet.classes.JobNetException;

public class OrderNameCreator extends ObjectNameCreator {
	
	private static final Logger logger = Logger.getLogger(OrderNameCreator.class);

	private String orderName;
	private final String objectNameExtended;
	
	public OrderNameCreator(String baseFolder, String orderId, String jobChain) {
		super(baseFolder, getOrderAndChain(orderId,jobChain), ObjectNameCreator.Type.ORDER);
//		this.jobChainName = jobChain;
		String name = getOrderAndChain(orderId,jobChain);
		this.objectNameExtended = name + "." + Type.ORDER.getExtension();
		this.orderName = name.split(",")[1];
	}
	
	public static String getOrderAndChain(String orderId, String jobChain) {
		if ( hasPath(jobChain))
			throw new JobNetException("a path specification in jobChain (" + jobChain + ") is not allowed - specify the path with the order.");
		
		ObjectNameCreator creator = null;
		String name = "";
		creator = new ObjectNameCreator("",orderId, ObjectNameCreator.Type.ORDER);
		name = jobChain + "," + creator.getObjectName();
		
		logger.debug("getOrderAndChain: orderId=" + orderId + ", jobChain=" + jobChain + ", chain+order=" + name);
		String result = ObjectNameCreator.concat(creator.getObjectFilePath(), name);
		logger.debug("result=" + result);
		return result;
	}
	
	private static boolean hasPath(String objectName) {
		return objectName.contains("/");
	}

	@Override
	public String getObjectName() {
		return orderName;
	}

	@Override
	public String getObjectFileName() {
		return objectNameExtended;
	}

	@Override
	public String getFullName() {
		return getBasePath() + ensureLeadingSlash(getObjectFileName());
	}
	
}
