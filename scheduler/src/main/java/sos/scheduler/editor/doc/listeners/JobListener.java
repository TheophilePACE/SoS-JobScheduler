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
package sos.scheduler.editor.doc.listeners;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.program.Program;
import org.jdom.Element;
import org.jdom.JDOMException;

import sos.scheduler.editor.app.Options;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.doc.DocumentationDom;

public class JobListener {
    private DocumentationDom      _dom;

    private Element               _job;

    private static final String[] _orderValues = { "yes", "no", "both" };

    private static final String[] _tasksValues = { "", "1", "unbounded" };


    public JobListener(DocumentationDom dom, Element job) {
        _dom = dom;
        _job = job;
    }

    
    public File writeToFile() throws IOException, JDOMException {
    	File tmp = File.createTempFile(Options.getXSLTFilePrefix(), Options.getXSLTFileSuffix());
		tmp.deleteOnExit();
       _dom.writeFileWithDom(tmp);
       return tmp;
    }
    public void preview() {

      Element element = _dom.getRoot();
      if (element != null) {
          try {
        	   
              String filename = _dom.transform(element);

              Program prog = Program.findProgram("html");
              if (prog != null)
                  prog.execute(filename);
              else {
                  Runtime.getRuntime().exec(Options.getBrowserExec(filename, null));
              }
          } catch (Exception ex) {
              ex.printStackTrace();
              //message("Error: " + ex.getMessage(), SWT.ICON_ERROR | SWT.OK);
          }
       }
    }
      
    public String getName() {
        return Utils.getAttributeValue("name", _job);
    }


    public void setName(String name) {
        Utils.setAttribute("name", name, _job, _dom);
    }


    public String getTitle() {
        return Utils.getAttributeValue("title", _job);
    }


    public void setTitle(String title) {
        Utils.setAttribute("title", title, _job, _dom);
    }


    public String[] getOrderValues() {
        return _orderValues;
    }


    public String getOrder() {
        String order = Utils.getAttributeValue("order", _job);
        if (order.length() == 0)
            Utils.setAttribute("order", _orderValues[0], _job);
        return order.length() > 0 ? order : _orderValues[0];
    }


    public void setOrder(String order) {
        Utils.setAttribute("order", order, _job, _dom);
    }


    public String[] getTasksValues() {
        return _tasksValues;
    }


    public String getTasks() {
      return Utils.getAttributeValue("tasks", _job);
  }


    public void setTasks(String tasks) {
        Utils.setAttribute("tasks", tasks, _job, _dom);
    }

}
