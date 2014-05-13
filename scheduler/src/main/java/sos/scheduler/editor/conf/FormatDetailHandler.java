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
package sos.scheduler.editor.conf;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import sos.scheduler.editor.app.Options;

public class FormatDetailHandler extends DefaultHandler implements ContentHandler {
	
	
    private    StringBuffer    _sb          = new StringBuffer();
    private    String          _encoding    = "ISO-8859-1";
    private    String          _indentStr   = "    ";
    private    String          _indent      = "    ";
    private    StringBuffer    _text        = new StringBuffer();
    private    int             _level       = 0;
    private    boolean         _isOpen      = false;

    public FormatDetailHandler(DetailDom dom) {
        //_dom = dom;
    }


    public void setEnconding(String encoding) {
        _encoding = encoding;
    }

    public String getXML() {
        return _sb.toString();
    }


    public void startDocument() {
        _sb.append("<?xml version=\"1.0\" encoding=\"" + _encoding + "\"?>\n\n");
        if(Options.getDetailXSLT() != null && Options.getDetailXSLT().length() > 0) {
        	_sb.append("<?xml-stylesheet type=\"text/xsl\" href=\""+ Options.getDetailXSLT() + "\"?> ");
		}
    }


    public void characters(char[] ch, int start, int length) throws SAXException {
    	//System.out.println("ch:" + new String(ch, start, length));
        _text.append(new String(ch, start, length));
    }


    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
    	//System.out.println("endelement:" + qName);
        _level--;
        _indent = strRepeat(_indentStr, _level);

        String text = _text.toString().trim();
        _text = new StringBuffer();
                
    
        boolean hasText = text.length() > 0;

        if (_isOpen && !hasText)
            _sb.append("/>\n");
        else if (_isOpen)
            _sb.append(">\n");

        /*if (hasText) {
            _sb.append(text + "\n");
        }*/
        if (hasText) {
            _sb.append(_indent + _indentStr + "<![CDATA[\n");
            _sb.append(text + "\n");
            _sb.append(_indent + _indentStr + "]]>\n");
        }

        if (!_isOpen || hasText)
            _sb.append(_indent + "</" + qName + ">\n");

        _isOpen = false;
    }


    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
    	//System.out.println("startelement:" + qName);
        if (_isOpen)
            _sb.append(">\n");        
        else
            _sb.append("\n");

        _indent = strRepeat(_indentStr, _level);

        StringBuffer attributes = new StringBuffer();
        String sep = " ";
        String sepStr = "\n" + _indent + strRepeat(" ", new String("<" + qName).length() + 1);

        //String attrName = "";

        // iterate atributes
        for (int i = 0; i < atts.getLength(); i++) {
            String name = atts.getQName(i);
            String value = atts.getValue(i);
            String uri = atts.getURI(i);

            if (name.equals("__comment__")) { // build element comment
                _sb.append(_indent + "<!-- " + value + " -->\n");
            } else { // add attribute
                if (!uri.equals("")) {
                    attributes.append(sep);
                    attributes.append("xmlns:xsi" + "=\"" + uri + "\"");
                }
                attributes.append(sep);
                attributes.append(name + "=\"" + value + "\"");
                sep = sepStr;

                //if (name.equals("name"))
                 //   attrName = value;
            }
        }
       
        String text = _text.toString().trim();
        if(text != null && text.length() > 0) {        	
        	_sb.append(text + " \n");
        	_text = new StringBuffer();
        }
        String ns = "";
        if( namespaceURI != null && namespaceURI.length() > 0) {
        	ns = " xmlns=\"" + namespaceURI + "\"";        	
        }
        
        _sb.append(_indent + "<" + qName +  ns + attributes.toString());
        _isOpen = true;
        _level++;

    }


    private String strRepeat(String str, int cnt) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < cnt; i++)
            sb.append(str);
        return sb.toString();
    }
}
