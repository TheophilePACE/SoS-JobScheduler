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

import java.util.Iterator;
import java.util.LinkedHashSet;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SOSActions {

	protected String		name				= "";
	protected String		logic				= "";
	protected LinkedHashSet	listOfEventGroups	= null;
	protected LinkedHashSet	listOfCommands		= null;
	protected NodeList		commandNodes		= null;
	protected Node			commands			= null;

	public SOSActions(final String name) {
		super();
		this.name = name;
		listOfEventGroups = new LinkedHashSet();
		listOfCommands = new LinkedHashSet();
	}

	public boolean isActive(final LinkedHashSet listOfActiveEvents) {
		String tmp = logic;
		if (logic.length() == 0 || logic.equalsIgnoreCase("or")) {
			logic = "";
			Iterator i = listOfEventGroups.iterator();
			while (i.hasNext()) {
				SOSEventGroups evg = (SOSEventGroups) i.next();
				logic += evg.group + " or ";
			}
			logic += " false";
		}

		if (logic.equalsIgnoreCase("and")) {
			logic = "";
			Iterator i = listOfEventGroups.iterator();
			while (i.hasNext()) {
				SOSEventGroups evg = (SOSEventGroups) i.next();
				logic += evg.group + " and ";
			}
			logic += " true";
		}

		BooleanExp exp = new BooleanExp(logic);
		Iterator i = listOfEventGroups.iterator();
		while (i.hasNext()) {
			SOSEventGroups evg = (SOSEventGroups) i.next();
			exp.replace(evg.group, exp.trueFalse(evg.isActiv(listOfActiveEvents)));
		}
		logic = tmp;
		return exp.evaluateExpression();

	}

	public LinkedHashSet getListOfCommands() {
		return listOfCommands;
	}

	public String getName() {
		return name;
	}

	public String getLogic() {
		return logic;
	}

	public LinkedHashSet getListOfEventGroups() {
		return listOfEventGroups;
	}

	public Node getCommands() {
		return commands;
	}

	public NodeList getCommandNodes() {
		return commandNodes;
	}

}
