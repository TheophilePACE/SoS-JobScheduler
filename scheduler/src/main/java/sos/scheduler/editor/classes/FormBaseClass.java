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
package sos.scheduler.editor.classes;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import sos.scheduler.editor.app.Editor;
import sos.scheduler.editor.app.IContainer;
import sos.scheduler.editor.app.MainWindow;
import sos.scheduler.editor.conf.listeners.JOEListener;

/**
* \class FormBaseClass 
* 
* \brief FormBaseClass - 
* 
* \details
*
*
* \code
*   .... code goes here ...
* \endcode
*
* <p style="text-align:center">
* <br />---------------------------------------------------------------------------
* <br /> APL/Software GmbH - Berlin
* <br />##### generated by ClaviusXPress (http://www.sos-berlin.com) #########
* <br />---------------------------------------------------------------------------
* </p>
* \author KB
* \version $Id: FormBaseClass.java 17417 2012-06-21 17:01:24Z ur $
* \see reference
*
* Created on 06.02.2012 16:23:36
 */

/**
 * @author KB
 *
 */
public class FormBaseClass {

	@SuppressWarnings("unused")
	private final String		conClassName	= "FormBaseClass";
	@SuppressWarnings("unused") private static final String	conSVNVersion	= "$Id: FormBaseClass.java 17417 2012-06-21 17:01:24Z ur $";
	private static final Logger	logger			= Logger.getLogger(FormBaseClass.class);

	protected JOEListener		objJobDataProvider	= null;
	protected Composite			objParent		= null;
	protected Shell				shell			= null;
	protected Cursor			objLastCursor	= null;
	protected FormBaseClass		objParentForm	= this;

	protected final int						intComboBoxStyle	= SWT.NONE;

	protected FormBaseClass(Composite parent, int style) {
        shell = parent.getShell();
	}

	public FormBaseClass(Composite pParentComposite, JOEListener pobjDataProvider) {
        super();
		objParent = pParentComposite;
		shell = pParentComposite.getShell();
		objJobDataProvider = pobjDataProvider;
		GridLayout grdL = new GridLayout();
		pParentComposite.setLayout(new GridLayout( ));
		setResizableV(pParentComposite);
		// createGroup();
		// objParent.setSize(new org.eclipse.swt.graphics.Point(723, 566));
	}

	protected void setResizableV(Control objControl) {
		boolean flgGrapVerticalspace = true;
		objControl.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, flgGrapVerticalspace));
	}

	protected void setStatusLine(final String pstrText) {
		// Editor.objMainWindow.setStatusLine(pstrText);
		final int delay = 2000;
		final Display display = shell.getDisplay();
		display.asyncExec(new Runnable() {
			public void run() {
				Editor.objMainWindow.setStatusLine(pstrText);
				try {
					Thread.sleep(delay);
				}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Editor.objMainWindow.setStatusLine("");
			}
		});

	}

	protected void showWaitCursor() {
		if (!shell.isDisposed()) {
			objLastCursor = shell.getCursor();
		}
		shell.setCursor(new Cursor(shell.getDisplay(), SWT.CURSOR_WAIT));
	}

	protected void restoreCursor() {
		if (!shell.isDisposed())
			if (objLastCursor == null) {
				shell.setCursor(new Cursor(shell.getDisplay(), SWT.CURSOR_ARROW));
			}
			else {
				shell.setCursor(objLastCursor);
			}
	}

	protected IContainer getContainer() {
		return MainWindow.getContainer();
	}


	protected void MsgWarning(final String pstrMsgText) {
		MainWindow.message(pstrMsgText, SWT.ICON_WARNING);
		this.setStatusLine(pstrMsgText);
	}

	protected void Enable(Control objC, boolean flgStatus) {
		if (objC != null) {
			objC.setEnabled(flgStatus);
		}
	}

    protected Shell getShell() {
        return shell;
    }

}
