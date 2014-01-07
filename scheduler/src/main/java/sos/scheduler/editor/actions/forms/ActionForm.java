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
package sos.scheduler.editor.actions.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.jdom.Element;

import sos.scheduler.editor.actions.ActionsDom;
import sos.scheduler.editor.actions.listeners.ActionListener;
import sos.scheduler.editor.app.IUpdateLanguage;
import sos.scheduler.editor.app.SOSJOEMessageCodes;

public class ActionForm extends SOSJOEMessageCodes implements IUpdateLanguage {

	private ActionListener	listener		= null;
	private Group			actionsGroup	= null;
	private Text			txtName			= null;

	public ActionForm(final Composite parent, final int style, final ActionsDom dom, final Element action, final ActionsForm _gui) {
		super(parent, style);
		//gui = _gui;
		listener = new ActionListener(dom, action, _gui);
		initialize();
		setToolTipText();

	}

	private void initialize() {
		createGroup();
		setSize(new Point(696, 462));
		setLayout(new FillLayout());
		txtName.setText(listener.getName());
		txtName.setFocus();
	}

	/**
	 * This method initializes group
	 */
	private void createGroup() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		actionsGroup = JOE_G_ActionForm_Action.Control(new Group(this, SWT.NONE));
		actionsGroup.setLayout(gridLayout); // Generated

		@SuppressWarnings("unused")
		final Label nameLabel = JOE_L_Name.Control(new Label(actionsGroup, SWT.NONE));

		txtName = JOE_T_ActionForm_Name.Control(new Text(actionsGroup, SWT.BORDER));
		txtName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				listener.setName(txtName.getText());
			}
		});
		txtName.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
	}

	@Override
	public void setToolTipText() {
		//
	}

} // @jve:decl-index=0:visual-constraint="10,10"

