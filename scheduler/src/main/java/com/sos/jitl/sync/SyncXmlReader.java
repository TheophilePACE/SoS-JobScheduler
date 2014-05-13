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
package com.sos.jitl.sync;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import sos.xml.SOSXMLXPath;

public class SyncXmlReader {

	private final String		xml;
	private final String		xPathExpression;
	private NodeList	nodes;
	private int			index;
	private SOSXMLXPath	xPath;
	private Node		lastNode;

	public SyncXmlReader(final String xml, final String xPathExpression) throws Exception {
		super();
		this.xml = xml;
		this.xPathExpression = xPathExpression;
		getNodes();
	}

	private void getNodes() throws Exception {
		xPath = new SOSXMLXPath(new StringBuffer(xml));
		nodes = xPath.selectNodeList(xPathExpression);
		index = 0;
	}

	public boolean eof() {
		return index >= nodes.getLength();
	}

	public void getNext() {
		lastNode = nodes.item(index);
		index++;
	}

	public String getAttributeValue(final String attribute) {
		String strR = "";
		if (lastNode != null) {
			Node objN = lastNode.getAttributes().getNamedItem(attribute);
			if (objN != null) {
				strR = objN.getNodeValue();
			}
			return strR;
		}
		else {
			return strR;
		}
	}

	public String getAttributeValueFromParent(final String attribute) {
		if (lastNode != null) {
			return lastNode.getParentNode().getAttributes().getNamedItem(attribute).getNodeValue();
		}
		else {
			return null;
		}

	}

	public Node getNode(final String xpression) throws Exception {
		return xPath.selectSingleNode(xpression);
	}

	public String getAttributeValueFromXpath(final String xPath, final String attribute) throws Exception {
		Node n = getNode(xPath);
		if (n == null) {
			return "";
		}
		else {
			return n.getAttributes().getNamedItem(attribute).getNodeValue();
		}

	}

}
