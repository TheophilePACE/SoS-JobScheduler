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
 * Created on 13.10.2008
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package sos.scheduler.consoleviews.events;


import java.util.LinkedHashSet;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

 
public class SOSEventCommand {
   private Node command=null;
   private NamedNodeMap attr=null;
   private LinkedHashSet listOfCommandElements=null;

   
   private String getText( Node n) {
	  if (n != null) {
		 return n.getNodeValue();
	  }else {
		 return "";
      }
   }
   
   private String addCommandElements() {
	 String erg="";
	 listOfCommandElements = new LinkedHashSet();
   	 NodeList c =  command.getChildNodes();
	   	
	 for (int i=0; i<c.getLength(); i++) {
	    Node node = c.item(i);
	    if (node == null || node.getNodeType() != Node.ELEMENT_NODE) continue;
	    SOSEventCommandElement ece = new SOSEventCommandElement();
	    ece.command = node;
	    listOfCommandElements.add(ece);
	  }
	  return erg;   
   }
	      
   
   public String getAttribute(String a) {
	  if (command.getNodeName().equals("command")){
 		 if (attr == null) attr = command.getAttributes();
   		 return getText(attr.getNamedItem(a));
   	 }
	  return "";
   }

   public Node getCommand() {
      return command;
   }
   
   public void setCommand(Node n) {
     command = n;
     addCommandElements();
   }

   public LinkedHashSet getListOfCommandElements() {
      return listOfCommandElements;
   }
}
