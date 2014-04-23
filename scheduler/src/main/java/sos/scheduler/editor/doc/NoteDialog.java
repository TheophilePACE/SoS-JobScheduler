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
package sos.scheduler.editor.doc;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jdom.Element;

import sos.scheduler.editor.app.Options;
import sos.scheduler.editor.app.ResourceManager;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.doc.forms.NoteForm;

public class NoteDialog extends Dialog {
    private Shell    _shell;

    private Image    _image;

    private int      _shellStyle = SWT.CLOSE | SWT.TITLE | SWT.APPLICATION_MODAL | SWT.RESIZE;

    private Point    _size       = new Point(450, 350);

    private NoteForm _fNote      = null;
    org.eclipse.swt.widgets.Text refreshText = null;

    public NoteDialog(Shell parent, String title) {
        super(parent);
        init(title);
    }


    private void init(String title) {
        Shell parent = getParent();
        _shell = new Shell(parent, _shellStyle);
        _shell.setVisible(false);
        _shell.setText(getText());
        _shell.setSize(_size);
        Options.loadWindow(_shell, "note");

        _shell.addShellListener(new org.eclipse.swt.events.ShellAdapter() {
            public void shellClosed(org.eclipse.swt.events.ShellEvent e) {
                Options.saveWindow(_shell, "note");
                e.doit = Utils.applyFormChanges(_fNote);
            }

        });

        try {
            _image = ResourceManager.getImageFromResource("/sos/scheduler/editor/editor.png");
            _shell.setImage(_image);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        setDialog(title);
    }


    private void setDialog(String title) {
        final FillLayout fillLayout = new FillLayout();
        _shell.setLayout(fillLayout);

        _fNote = new NoteForm(_shell, SWT.NONE);
        _fNote.setTitle(title);
    }


    public void setParams(DocumentationDom dom, Element parent, String name, boolean optional, boolean changeStatus) {
        _fNote.setParams(dom, parent, name, optional, changeStatus);
    }


    public void open() {
        _shell.open();
        Display display = _shell.getDisplay();
        while (!_shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }

        if (_fNote != null)
            _fNote.dispose();
        // if (_image != null)
        // _image.dispose();
    }

    //Textfeld soll beim verlassen diese Dialogs aktualiert werden
    /*public void setUpdateText(org.eclipse.swt.widgets.Text txt) {
       refreshText;
    }*/

    public void setTooltip(String string) {
        _fNote.setToolTipText(string);
    }
}
