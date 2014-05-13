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
package sos.scheduler.editor.conf.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import sos.scheduler.editor.app.Editor;
import sos.scheduler.editor.app.ResourceManager;
import sos.scheduler.editor.app.SOSJOEMessageCodes;

public class DetailDialogForm {

	private String		jobChainname	= "";

	private String		state			= null;

	private Shell		shell			= null;

	// private String[] listOfOrderIds = null;

	private DetailForm	dialogForm		= null;

	private boolean		isLifeElement	= false;

	private String		path			= null;

	private String		orderId			= null;

	/**
	 * Wenn es eine JobDokumentation gibt, dann wird beim Starten der Wizzard die Parameter Fenster ge�ffnet
	 */
	// private String descriptionname = null;

	// public DetailDialogForm(String jobChainname_, String[] listOfOrderIds_, boolean isLifeElement_, String path_) {
	public DetailDialogForm(String jobChainname_, boolean isLifeElement_, String path_) {
		jobChainname = jobChainname_;
		// listOfOrderIds = listOfOrderIds_;
		isLifeElement = isLifeElement_;
		path = path_;
	}

	// public DetailDialogForm(String jobChainname_, String state_, String[] listOfOrderIds_, boolean isLifeElement_, String path_) {
	public DetailDialogForm(String jobChainname_, String state_, String orderId_, boolean isLifeElement_, String path_) {
		jobChainname = jobChainname_;
		state = state_;
		// listOfOrderIds = listOfOrderIds_;
		this.orderId = orderId_;
		isLifeElement = isLifeElement_;
		path = path_;
	}

	/**
	 * Aufruf dieser Methode, wenn es eine Jobdokumentation gibt
	 * @param descriptionname_
	 */
	/*public void showDetails(String descriptionname_) {

		descriptionname = descriptionname_;
		showDetails();
	}*/

	public void showDetails() {

		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		shell = new Shell(sos.scheduler.editor.app.MainWindow.getSShell(), SWT.CLOSE | SWT.TITLE | SWT.APPLICATION_MODAL | SWT.BORDER | SWT.RESIZE);
		shell.setLayout(new FillLayout());

		shell.setImage(ResourceManager.getImageFromResource("/sos/scheduler/editor/editor.png"));
//		 shell.setText("Details for JobChain: " + jobChainname +
//		 (state != null && state.length()> 0 ? "  State: " + state : "") +
//		 (orderId != null && orderId.length() > 0 ? "  Order Id: " + orderId : ""));
		shell.setText(SOSJOEMessageCodes.JOE_M_0017.params(jobChainname, (state != null && state.length()> 0 ? SOSJOEMessageCodes.JOE_M_0018.params(state) : ""), (orderId != null && orderId.length() > 0 ? SOSJOEMessageCodes.JOE_M_0019.params(orderId) : "")));
		
		final Composite composite = SOSJOEMessageCodes.JOE_Composite1.Control(new Composite(shell, SWT.NONE));
		
		composite.setLayout(new FillLayout());
		final GridData gridData_6 = new GridData(GridData.FILL, GridData.CENTER, false, false, 3, 1);
		gridData_6.widthHint = 500;
		gridData_6.heightHint = 500;

		// dialogForm =new DetailForm(composite, SWT.NONE, jobChainname, state, listOfOrderIds, Editor.JOB_CHAINS, null, null,
		// isLifeElement, path);
		dialogForm = new DetailForm(composite, SWT.NONE, jobChainname, state, orderId, Editor.JOB_CHAINS, null, null, isLifeElement, path);

		if (!dialogForm.hasErrors())// im fehlerfall
			dialogForm.setLayout(new FillLayout());
	}

	public DetailForm getDialogForm() {
		return dialogForm;
	}

}
