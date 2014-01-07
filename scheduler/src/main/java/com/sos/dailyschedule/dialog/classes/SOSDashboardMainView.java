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
import java.util.prefs.Preferences;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.apache.log4j.Logger;

import com.sos.dashboard.globals.DashBoardConstants;
import com.sos.dashboard.globals.SOSDashboardOptions;
import com.sos.dialog.classes.FormBase;
import com.sos.hibernate.classes.DbItem;
import com.sos.scheduler.history.SchedulerHistoryDataProvider;

public class SOSDashboardMainView extends FormBase {
    protected Group                         right;
    protected Group                         left;
    protected CTabFolder                    leftTabFolder                       = null;
    protected CTabFolder                    logTabFolder                        = null;
    protected Preferences                   prefs;
    private static Logger                   logger                              = Logger.getLogger(SOSDashboardMainView.class);
    protected SchedulerHistoryDataProvider  detailHistoryDataProvider           = null;
    protected SosDashboardHeader            sosDashboardHeader                  = null;
    public Composite                        mainViewComposite                       = null;
    protected Table                         tableHistoryDetail                  = null;
    protected SOSDashboardOptions           objOptions                          = null;

    public SOSDashboardMainView(Composite composite_) {
        super(composite_, DashBoardConstants.conPropertiesFileName);
    }
 

    protected void showLog(Table table) { 
        this.showWaitCursor();
        if (table.getSelectionIndex() >= 0 && table.getSelectionIndex() >= 0) {
            SosTabLogItem logItem = (SosTabLogItem) logTabFolder.getSelection();
            if (logItem == null) {
                logTabFolder.setSelection(0);
                logItem = (SosTabLogItem) logTabFolder.getSelection();
            }
            TableItem t = table.getItem(table.getSelectionIndex());
            DbItem d = (DbItem) t.getData();
            logItem.addLog(table, d.getTitle(), detailHistoryDataProvider.getLogAsString(d));
        }
        this.RestoreCursor();
    }

      
    public void setLeftTabFolder(CTabFolder leftTabFolder) {
        this.leftTabFolder = leftTabFolder;
    }

    public void setPrefs(Preferences prefs) {
        this.prefs = prefs;
    }

    public void setDetailHistoryDataProvider(SchedulerHistoryDataProvider detailHistoryDataProvider) {
        this.detailHistoryDataProvider = detailHistoryDataProvider;
    }

    public void setSosDashboardHeaderplanned(SosDashboardHeader sosDashboardHeader) {
        this.sosDashboardHeader = sosDashboardHeader;
    }
 
    public void setLogTabFolder(CTabFolder logTabFolder) {
        this.logTabFolder = logTabFolder;
    }

    public void setTableHistoryDetail(Table tableHistoryDetail) {
        this.tableHistoryDetail = tableHistoryDetail;
    }

    public Composite getTablePlannedComposite() {
        return mainViewComposite;
    }

    public SosDashboardHeader getSosDashboardHeader() {
        return sosDashboardHeader;
    }

    public void setObjOptions(SOSDashboardOptions objOptions) {
        this.objOptions = objOptions;
    }
 
    public void setRight(Group right) {
        this.right = right;
    }

    public void setLeft(Group left) {
        this.left = left;
    }

    public Composite getTableComposite() {
        return mainViewComposite;
    }

    
}
