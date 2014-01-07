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

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;

public class Test  extends Composite {
    private Text text;

    /**
     * Create the composite.
     * @param parent
     * @param style
     */
    public Test (Composite parent, int style) {
        super(parent,style);
        Composite mainViewComposite = parent;
        GridLayout layout = new GridLayout(9, false);
        mainViewComposite.setLayout(layout);
        
        Label lblNewLabel = new Label(mainViewComposite, SWT.NONE);
        lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblNewLabel.setText("New Label");
        
        Combo combo = new Combo(mainViewComposite, SWT.NONE);
        combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        new Label(mainViewComposite, SWT.NONE);
        
        Label lblNewLabel_1 = new Label(mainViewComposite, SWT.NONE);
        lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblNewLabel_1.setText("New Label");
        
        Text text = new Text(mainViewComposite, SWT.BORDER);
        text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        new Label(mainViewComposite, SWT.NONE);
        new Label(mainViewComposite, SWT.NONE);
        new Label(mainViewComposite, SWT.NONE);
        new Label(mainViewComposite, SWT.NONE);
         
        SashForm sashForm = new SashForm(mainViewComposite,  SWT.SMOOTH | SWT.HORIZONTAL);
        GridData gd_sashForm = new GridData(GridData.FILL_BOTH);
        gd_sashForm.horizontalSpan = 9;
        sashForm.setLayoutData(gd_sashForm);
        
        Group grpJobnet = new Group(sashForm, SWT.NONE);
        grpJobnet.setText("Jobnet");
        
        Group grpNodes = new Group(sashForm, SWT.NONE);
        grpNodes.setText("Nodes");
        
        Group grpPlans = new Group(sashForm, SWT.NONE);
        grpPlans.setText("Plans");
        sashForm.setWeights(new int[] {1, 1, 1});
        
    }

    
    
    public void createTable  () {

       

         
    }
}
