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

import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.jdom.Element;

import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.doc.DocumentationDom;

public class ReleaseAuthorsListener {

	private DocumentationDom _dom;    

	private Element          _release;


	public ReleaseAuthorsListener(DocumentationDom dom, Element release) {
		_dom = dom;
		_release = release;
	}

	public void removeAuthor(Element elem) {
		if (_release != null) {        	
			_release.getContent().remove(elem);
			_dom.setChanged(true);
		}
	} 

	public void fillAuthors(Table table) {

		table.removeAll();

		if (_release != null) {
			for (Iterator it = _release.getChildren("author", _dom.getNamespace()).iterator(); it.hasNext();) {
				Element author = (Element) it.next();
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(0, Utils.getAttributeValue("name", author));
				item.setText(1, Utils.getAttributeValue("email", author));
				item.setData(author);
			}
		}
		Utils.setBackground(table, true);
	}


	public void applyRelease(Table table) {
		TableItem[] authors = table.getItems();
		_release.removeChildren("author", _dom.getNamespace());
		for (int i = 0; i < authors.length; i++) {
			Element author = new Element("author", _dom.getNamespace());
			Utils.setAttribute("name", authors[i].getText(0), author);
			Utils.setAttribute("email", authors[i].getText(1), author);
			_release.addContent(author);
		}

		fillAuthors(table);
		_dom.setChanged(true);
	}

}
