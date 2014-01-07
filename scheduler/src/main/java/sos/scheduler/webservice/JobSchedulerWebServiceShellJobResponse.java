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
import sos.spooler.Web_service_operation;
import sos.spooler.Web_service_request;
import sos.spooler.Web_service_response;
import sos.spooler.Xslt_stylesheet;


public class JobSchedulerWebServiceShellJobResponse extends Job_impl {

    
    public boolean spooler_process() {

        try {
            String xml_document = "";
            Order order = spooler_task.order();
            Web_service_operation operation = order.web_service_operation();
            
            if (operation == null) throw  new Exception( "no web service operation available" );
            
            Web_service_request request = operation.request();
            if (request == null) throw new Exception( "no web service request available" );
            Web_service_response response = operation.response();
            if (response == null) throw new Exception( "no web service response available" );

            if (spooler_task.params().value("response_stylesheet") != null && spooler_task.params().value("response_stylesheet").length() > 0) {
                // .. either transform the response from order parameters and payload
                Xslt_stylesheet stylesheet = spooler.create_xslt_stylesheet();
                stylesheet.load_file( spooler_task.params().value("response_stylesheet") );
                xml_document = stylesheet.apply_xml( order.xml() );
                spooler_log.debug3( "content of response transformation:\n" + xml_document );
                response.set_string_content( xml_document );
            } else {
                // .. or send an individual response (use order.params().xml() or order.xml_payload() to access order data)
                response.set_string_content( "<response state=\"success\">" + order.params().xml() + "</response>" );
            }

            response.send();
            spooler_log.info( "web service response successfully processed for order \"" + order.id() + "\"" ); 

            return true;
        } catch (Exception e) {
            spooler_log.warn( "error occurred processing web service response: " + e.getMessage() );
            return false;
        }
    }

}
