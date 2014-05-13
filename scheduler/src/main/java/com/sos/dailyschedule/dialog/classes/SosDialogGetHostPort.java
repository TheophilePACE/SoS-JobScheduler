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
package com.sos.dailyschedule.dialog.classes;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.sos.dialog.components.IntegerField;

public class SosDialogGetHostPort {
	final int				conDefaultPort	= 4444;
	private GridLayout		gridLayout;
	private Logger			logger			= Logger.getLogger(SosDialogGetHostPort.class);
	private IntegerField	edPort;
	private Text			edHost;
	private Button			btnOk;
	private String			hostValue;
	private int				portValue;

	public SosDialogGetHostPort(Shell parentShell) {
		execute(parentShell);
	}

	private void execute(Shell parentShell) {
		Display display = Display.getDefault();
		Shell shell = showForm(display, parentShell);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private Shell showForm(final Display display, Shell parentShell) {
		final Shell dialogShell = new Shell(parentShell, SWT.PRIMARY_MODAL | SWT.SHEET);
		dialogShell.setSize(450, 300);
		dialogShell.setLayout(new GridLayout(3, false));
		new Label(dialogShell, SWT.NONE);
		new Label(dialogShell, SWT.NONE);
		new Label(dialogShell, SWT.NONE);
		new Label(dialogShell, SWT.NONE);
		Label lbHost = new Label(dialogShell, SWT.NONE);
		lbHost.setText("Host");
		edHost = new Text(dialogShell, SWT.BORDER);
		GridData gd_edHost = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_edHost.widthHint = 150;
		edHost.setLayoutData(gd_edHost);
		new Label(dialogShell, SWT.NONE);
		Label lbPort = new Label(dialogShell, SWT.NONE);
		lbPort.setText("Port");
		edPort = new IntegerField(dialogShell, SWT.BORDER);
		GridData gd_edPort = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_edPort.widthHint = 150;
		edPort.setLayoutData(gd_edPort);
		new Label(dialogShell, SWT.NONE);
		btnOk = new Button(dialogShell, SWT.NONE);
		GridData gd_btnOk = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_btnOk.widthHint = 64;
		btnOk.setLayoutData(gd_btnOk);
		btnOk.setText("Ok");
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				hostValue = edHost.getText();
				try {
					portValue = edPort.getIntegerValue(conDefaultPort);
				}
				catch (NumberFormatException ee) {
					// returning default port.
				}
				dialogShell.dispose();
			}
		});
		dialogShell.setDefaultButton(btnOk);
		dialogShell.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
			}
		});
		dialogShell.pack();
		dialogShell.open();
		return dialogShell;
	}

	public String getHost() {
		if (hostValue == null) {
			return hostValue;
		} else {
			if (hostValue.contains(":")) {
				return hostValue.split(":")[0];
			} else {
				return hostValue;
			}
		}
	}

	private int getPortValue(String port) {
		int p = conDefaultPort;
		try {
			p = Integer.parseInt(port);
		}
		catch (NumberFormatException e) {
		}
		return p;
	}

	public int getPort() {
		if (hostValue == null) {
			return portValue;
		} else {
			if (hostValue.contains(":")) {
				return getPortValue(hostValue.split(":")[1]);
			} else {
				return portValue;
			}
		}
	}
}
