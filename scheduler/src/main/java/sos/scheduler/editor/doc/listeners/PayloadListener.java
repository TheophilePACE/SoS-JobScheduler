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

import org.jdom.Element;

import sos.scheduler.editor.doc.DocumentationDom;
import sos.scheduler.editor.doc.forms.ParamsForm;

public class PayloadListener {
    private DocumentationDom _dom;

    private Element          _parent;

    private Element          _payload;

    private Element          _document;

    private ParamsForm       _paramsForm;


    public PayloadListener(DocumentationDom dom, Element parent, ParamsForm paramsForm) {
        _dom = dom;
        _parent = parent;
        _paramsForm = paramsForm;
        _payload = _parent.getChild("payload", _dom.getNamespace());
        if (_payload != null)
            _document = _payload.getChild("document", _dom.getNamespace());
    }


    public void setPayload() {
        if (_payload == null) {
            _payload = new Element("payload", _dom.getNamespace());
            _parent.addContent(_payload);
        }
    }


    public void checkPayload() {
        if (_payload != null) {
            _paramsForm.checkParams();
            checkDocumentation();

            boolean remove = true;
            if (_payload.getChild("note", _dom.getNamespace()) != null)
                remove = false;
            if (_payload.getChild("params", _dom.getNamespace()) != null)
                remove = false;
            if (_document != null)
                remove = false;

            if (remove) {
                _payload.detach();
                _payload = null;
            }
        }
    }


    private void checkDocumentation() {
        if (_document != null && _document.getChildren("note", _dom.getNamespace()).size() == 0) {
            _document.detach();
            _document = null;
        }
    }


    public Element getPayloadElement() {
        setPayload();
        return _payload;
    }


    private void setDocumentation() {
        if (_document == null) {
            if (_payload == null)
                setPayload();
            _document = new Element("document", _dom.getNamespace());
            _payload.addContent(_document);
        }
    }


    public Element getDocumentationElement() {
        setDocumentation();
        return _document;
    }
}
