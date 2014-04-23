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
package sos.scheduler.editor.doc.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.jdom.Element;

import sos.scheduler.editor.app.Editor;
import sos.scheduler.editor.app.IUnsaved;
import sos.scheduler.editor.app.IUpdateLanguage;
import sos.scheduler.editor.app.MainWindow;
import sos.scheduler.editor.app.Options;
import sos.scheduler.editor.app.ResourceManager;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.doc.DocumentationDom;
import sos.scheduler.editor.doc.listeners.NoteListener;
import sos.scheduler.editor.doc.listeners.SettingsListener;

public class NoteForm extends SOSJOEMessageCodes implements IUnsaved, IUpdateLanguage {

	private SettingsListener	settingsListener	= null;

	private Group				group				= null;

	@SuppressWarnings("unused")
	private Label				label				= null;

	private Combo				cLang				= null;

	private Text				text				= null;

	private NoteListener		listener			= null; // @jve:decl-index=0:

	private Button				bApply				= null;

	private int					type				= -1;

	private Button				bClear				= null;

	public NoteForm(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	public NoteForm(Composite parent, int style, int type) {
		super(parent, style);
		this.type = type;
		initialize();
	}

	public void setParams(DocumentationDom dom, Element parent, String name, boolean optional) {
		setParams(dom, parent, name, optional, true);
	}

	public void setParams(DocumentationDom dom, Element parent, String name, boolean optional, boolean changeStatus) {
		listener = new NoteListener(dom, parent, name, optional, changeStatus);
		cLang.setItems(listener.getLanguages());
		String strTemplateLang = listener.getLang();
		if (strTemplateLang == null) {
			strTemplateLang = Options.getTemplateLanguage();
		}
		if (strTemplateLang != null) {
			cLang.select(cLang.indexOf(strTemplateLang));
		}
		text.setText(listener.getNote());
		bApply.setEnabled(false);
	}

	public void setTitle(String title) {
		group.setText(title);
	}

	private void initialize() {
		createGroup();
		setSize(new Point(650, 446));
		setLayout(new FillLayout());

		bApply.setEnabled(false);
		setToolTipText();
	}

	/**
	 * This method initializes group
	 */
	private void createGroup() {
		GridData gridData8 = new GridData(GridData.END, GridData.CENTER, true, false);

		GridData gridData11 = new GridData(GridData.END, GridData.CENTER, false, false);
		gridData11.widthHint = 150; // Generated
		gridData11.horizontalIndent = 10; // Generated

		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true, 4, 1);

		GridLayout gridLayout = new GridLayout(4, false);

		group = JOE_G_NoteForm_Documentation.Control(new Group(this, SWT.NONE));
		group.setLayout(gridLayout); // Generated

		label = JOE_L_NoteForm_Language.Control(new Label(group, SWT.NONE));

		createCLang();

		bClear = JOE_B_NoteForm_Clear.Control(new Button(group, SWT.NONE));
		bClear.setLayoutData(gridData8); // Generated
		bClear.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				text.setText("");
			}
		});

		bApply = JOE_B_NoteForm_Apply.Control(new Button(group, SWT.NONE));
		bApply.setLayoutData(gridData11); // Generated
		bApply.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if (isUnsaved())
					apply();
				bApply.setEnabled(false);
				if (!getShell().equals(MainWindow.getSShell()))
					getShell().dispose();
			}
		});

		text = new Text(group, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		text.setFont(ResourceManager.getFont("Courier New", 8, SWT.NONE));
		text.setLayoutData(gridData); // Generated
		text.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
				bClear.setEnabled(text.getText().length() > 0);
				if (listener != null) {
					bApply.setEnabled(true);
				}
			}
		});
	}

	/**
	 * This method initializes cLang
	 */
	private void createCLang() {
		GridData gridData1 = new GridData();
		gridData1.widthHint = 100; // Generated

		cLang = JOE_Cbo_NoteForm_Language.Control(new Combo(group, SWT.BORDER | SWT.READ_ONLY));
		cLang.setLayoutData(gridData1); // Generated
		cLang.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				changeLang();
			}

			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
	}

	public void setToolTipText() {

		switch (type) {
			case Editor.DOC_CONFIGURATION:
				setToolTipText(JOE_M_NoteForm_Config.label());
				break;
			case Editor.DOC_SETTINGS:
				setToolTipText(JOE_M_NoteForm_Settings.label());
				break;
			case Editor.DOC_DOCUMENTATION:
				setToolTipText(JOE_M_NoteForm_Doc.label());
				break;
		}
	}

	public void setToolTipText(String string) {
		super.setToolTipText(string);
		text.setToolTipText(string);
	}

	public void apply() {
		if (listener != null) {
			listener.setNote(text.getText());
			listener.createDefault();

		}
	}

	public boolean isUnsaved() {
		if (listener != null)
			listener.createDefault();

		if (settingsListener != null)
			settingsListener.checkSettings();

		return listener != null ? bApply.getEnabled() : false;
	}

	private void changeLang() {
		if (listener != null) {
			if (Utils.applyFormChanges(this)) {
				listener.setLang(cLang.getText());
				text.setText(listener.getNote());
				bApply.setEnabled(false);
			}
		}
		text.setFocus();
	}

	public boolean setFocus() {
		text.setFocus();
		return super.setFocus();
	}

	public void setSettingsListener(SettingsListener settingsListener1) {
		this.settingsListener = settingsListener1;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
