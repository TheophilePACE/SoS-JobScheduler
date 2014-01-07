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
package sos.scheduler.managed;

import java.util.HashMap;

import sos.scheduler.job.JobSchedulerJobAdapter;
import sos.spooler.Order;
import sos.spooler.Variable_set;

/**
 * Base class for managed jobs. This class can be used to write own mangaged jobs.
 * By calling prepareParams() in spooler_process() task and order parameters
 * will be merged.
 *  
 * @author andreas.pueschel@sos-berlin.com
 * @since 1.0 2005-03-05 
 */
public abstract class JobSchedulerManagedJob extends JobSchedulerJobAdapter {

	/** Attribut: globalSettings: globale Einstellungen des Jobs */
	protected HashMap<String, String>	globalSettings	= null;

	// Payload des Orders oder Parameter des Jobs wenn kein Order
	protected sos.spooler.Variable_set	orderPayload;

	// läuft der Job mit einem order?
	protected boolean					orderJob		= true;

	protected void prepareParams() throws Exception {
		orderJob = !(spooler_task.job().order_queue() == null);
		Order order = null;
		if (orderJob == false) {
			this.getLogger().debug3(spooler_job.name() + " running without order.");
		}
		try {
			orderPayload = clearBlanks(spooler_task.params());
			if (orderJob) {
				order = spooler_task.order();
				Variable_set orderPay = clearBlanks(order.params());
				// getLogger().debug3("Cleared orderPayload: "+orderPay.xml());
				orderPayload.merge(orderPay);
				getLogger().debug6("Merged Payload: " + orderPayload.xml());
			}
		}
		catch (Exception e) {

		}
	}

	private Variable_set clearBlanks(final Variable_set set) throws Exception {
		Variable_set retSet = spooler.create_variable_set();

		String[] keys = set.names().split(";");
		for (String key : keys) {
			String parameterValue = set.var(key);
			if (parameterValue != null && parameterValue.length() > 0) {
				retSet.set_var(key, parameterValue);
			}
		}

		return retSet;
	}

	protected void debugParamter(final Variable_set params, final String name) {
		try {
			getLogger().debug6("Parameter: " + name + " value:\"" + params.var(name) + "\"");
		}
		catch (Exception e) {
		}
	}

	/**
	 * @return Returns the orderJob.
	 */
	public boolean isOrderJob() {
		return orderJob;
	}

	/**
	 * @param orderJob The orderJob to set.
	 */
	public void setOrderJob(final boolean pOrderJob) {
		orderJob = pOrderJob;
	}

	/**
	 * @return Returns the orderPayload.
	 */
	public sos.spooler.Variable_set getOrderPayload() {
		return orderPayload;
	}
}
