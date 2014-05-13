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
package com.sos.dialog.classes;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.sos.dialog.interfaces.ICompositeBaseAbstract;
import com.sos.dialog.interfaces.IDialogActionHandler;


/**
 *
 * \Example:
 *
 * @author KB
 *
 */
public class DialogAdapter extends Dialog implements IDialogActionHandler {

	@SuppressWarnings("unused")
	private final String		conClassName	= this.getClass().getSimpleName();
	@SuppressWarnings("unused")
	private static final String	conSVNVersion	= "$Id$";
	@SuppressWarnings("unused")
	private final Logger		logger			= Logger.getLogger(this.getClass());

	protected Object			result;
	protected Shell				shell;
	private WindowsSaver		objFormHelper;
	private IDialogActionHandler objDialogActionHandler = this;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public DialogAdapter(final Shell parent, final int style) {
		super(parent, style);
		shell = new Shell(parent, SWT.ON_TOP | SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);

		objFormHelper = new WindowsSaver(this.getClass(), parent, 643, 600);
		setText("SWT Dialog");
	}

	public Object open (final ICompositeBaseAbstract objChildComposite) {
		shell.setRedraw(false);
		shell.open();
		shell.setLayout(new FillLayout());
		Composite objC = createContents();
		objChildComposite.createComposite(objC);
		((Group) objC).setText(objChildComposite.getWindowTitle());
		shell.setText(objChildComposite.getWindowTitle());
		objC.layout(true, true);
		shell.layout(true, true);
		shell.setRedraw(true);
//		shell.forceActive();
//		Object objO = open();
		Object objO = null;
		return objO;
	}

	public Object open (final IDialogActionHandler pobjDialogActionHandler) {
		objDialogActionHandler = pobjDialogActionHandler;
		objDialogActionHandler.setDialogActionHandler(this);
		return open();
	}

	private void saveWindowPosAndSize() {
		objFormHelper.saveWindow();
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	public Composite createContents() {

		objFormHelper = new WindowsSaver(this.getClass(), shell, 400, 200);
		objFormHelper.restoreWindow();

		shell.addListener(SWT.Traverse, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				switch (event.detail) {
					case SWT.TRAVERSE_ESCAPE:
						shell.close();
						event.detail = SWT.TRAVERSE_NONE;
						event.doit = false;
						break;
				}
			}
		});

		shell.addControlListener(new ControlAdapter() {
			@Override
			public void controlMoved(final ControlEvent e) {
				saveWindowPosAndSize();
			}

			@Override
			public void controlResized(final ControlEvent e) {
				saveWindowPosAndSize();
			}
		});

		Composite composite = new Group(shell, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(final DisposeEvent e) {
				//					if (butApply.isEnabled()) {
				//						save();
				//					}
			}
		});
		composite.layout(true, true);
		shell.layout(true, true);
		return composite;
	}

	public Shell getShell() {
		return shell;
	}

	@Override
	public void doCancel() {
		closeShell ();
		objDialogActionHandler.doClose();
	}

	@Override
	public void doEdit() {
		closeShell ();
		objDialogActionHandler.doEdit();
	}

	@Override
	public void doNew() {
		closeShell ();
		objDialogActionHandler.doNew();
	}

	@Override
	public void doDelete() {
		closeShell ();
		objDialogActionHandler.doDelete();
	}

	@Override
	public void doClose() {
		closeShell ();
		objDialogActionHandler.doClose();
	}

	private void closeShell () {
		shell.close();
	}

	@Override
	public void setDialogActionHandler(final IDialogActionHandler pobjDialogActionHandler) {
		objDialogActionHandler = pobjDialogActionHandler;
	}
}
