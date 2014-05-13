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

package sos.scheduler.webservice;


import sos.spooler.Job_impl;
import sos.spooler.Order;
import sos.spooler.Variable_set;
import sos.spooler.Web_service_operation;
import sos.spooler.Web_service_request;
import sos.spooler.Xslt_stylesheet;
import sos.xml.SOSXMLXPath;


public class JobSchedulerWebServiceShellJobRequest extends Job_impl {

    
    public boolean spooler_process() {

        try {
            String xml_document = "";
            Order order = spooler_task.order();
            Web_service_operation operation = order.web_service_operation();

            if (operation == null) throw  new Exception( "no web service operation available" );
            
            Web_service_request request = operation.request();
            if (request == null) throw new Exception( "no web service request available" );
            spooler_log.debug3( "content of web service request:\n" + request.string_content() );

            // should the request be previously transformed ...
            if (spooler_task.params().value("request_stylesheet") != null && spooler_task.params().value("request_stylesheet").length() > 0) {
              Xslt_stylesheet stylesheet = spooler.create_xslt_stylesheet();
              stylesheet.load_file(spooler_task.params().value("request_stylesheet"));
              xml_document = stylesheet.apply_xml(request.string_content());
              spooler_log.debug3( "content of request transformation:\n" + xml_document );
            } else {
              xml_document = request.string_content();
            }
          
            // Javascript has no XML support, so we use a helper class for XPath expressions
            SOSXMLXPath xpath = new sos.xml.SOSXMLXPath(new StringBuffer(xml_document));
            // add order parameters from request xml element /params
            Variable_set params = spooler.create_variable_set();
            params.set_xml( xpath.selectDocumentText("//params") );
            order.set_payload( params );

            // altenatively you could add any xml structure of the request to the xml payload of this order
            // order.set_xml_payload( xpath.selectDocumentText("//xml_payload/*") );
       
            spooler_log.info( "web service request accepted for order \"" + order.id() + "\"" );

            return true;
        } catch (Exception e) {
            spooler_log.warn("error occurred processing web service request: " + e.getMessage());
            return false;
        }
    }

}
