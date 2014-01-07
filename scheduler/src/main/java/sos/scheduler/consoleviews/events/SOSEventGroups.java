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

public class SOSEventGroups {

	protected String		logic			= "";
	protected String		group			= "";
	protected String		event_class		= "";
	protected LinkedHashSet	listOfEvents	= null;

	public SOSEventGroups(final String group) {
		super();
		this.group = group;
		listOfEvents = new LinkedHashSet();
	}

	public boolean isActiv(final LinkedHashSet listOfActiveEvents) {
		boolean erg = false;
		if (logic.length() == 0) {
			logic = "or";
		}
		Iterator i = listOfEvents.iterator();
		if (logic.equalsIgnoreCase("or")) {
			while (i.hasNext() && !erg) {
				SchedulerEvent e = (SchedulerEvent) i.next();
				erg = e.isIn(listOfActiveEvents);
			}
		}
		else {
			if (logic.equalsIgnoreCase("and")) {
				erg = true;
				while (i.hasNext() && erg) {
					SchedulerEvent e = (SchedulerEvent) i.next();
					erg = erg && e.isIn(listOfActiveEvents);
				}
			}
			else {
				BooleanExp exp = new BooleanExp(logic);
				while (i.hasNext()) {
					SchedulerEvent e = (SchedulerEvent) i.next();

					exp.replace(e.getEvent_name(), exp.trueFalse(e.isIn(listOfActiveEvents)));
				}
				erg = exp.evaluateExpression();
			}
		}

		return erg;
	}

	public String getGroup() {
		return group;
	}

	public LinkedHashSet getListOfEvents() {
		return listOfEvents;
	}

	public String getLogic() {
		return logic;
	}
}
