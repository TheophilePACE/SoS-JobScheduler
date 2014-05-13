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
package sos.scheduler.editor.actions;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;

import org.eclipse.swt.SWT;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.SAXOutputter;
import org.jdom.output.XMLOutputter;

import sos.scheduler.editor.app.DomParser;
import sos.scheduler.editor.app.Editor;
import sos.scheduler.editor.app.MainWindow;
import sos.scheduler.editor.app.Options;

public class ActionsDom extends DomParser {

	public static final String		conTemplate_ACTIONS_TEMPLATE_XML	= "/sos/scheduler/editor/actions-template.xml";

	@SuppressWarnings("unused")
	private final static String		conSVNVersion						= "$Id: ActionsDom.java 14983 2011-08-21 08:47:30Z kb $";

	private static final String[]	_action								= { "events", "commands" };

	public ActionsDom() {
		// super(new String[] {}, _schemas, "");
		super(new String[] { conSchema_SCHEDULER_EDITOR_SCHEMA }, new String[] { Options.getActionSchema() }, "");
		putDomOrder("action", _action);
		initActions();
	}

	/* public void initActions() {
	  	Element actions = new Element("actions");
		setDoc(new Document(actions));		
	  	
	}
	*/

	public void initActions() {
		// open a template
		try {
			Document doc = getBuilder(false).build(getClass().getResource(conTemplate_ACTIONS_TEMPLATE_XML));
			//
			// Element description = doc.getRootElement();
			// Element actions = doc.getRootElement();

			setDoc(doc);
			setChanged(false);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

/*protected SAXBuilder getBuilder(boolean validate) throws IOException {

    	
        SAXBuilder builder = new SAXBuilder(validate);
        if (validate) {
        	
            builder.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                    "http://www.w3.org/2001/XMLSchema");
            builder.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", writeSchemaFile());
            //builder.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", _schemas);

        }
        
        return builder;
        
    }
*/

	public boolean read(String filename) throws JDOMException, IOException {
		return read(filename, Options.isDocValidate());

	}

	public boolean read(String filename, boolean validate) throws JDOMException, IOException {
		// TODO warum false?
		Document doc = getBuilder(true).build(filename);

		/*if (!validate && (!doc.hasRootElement() || !doc.getRootElement().getName().equals("actions")))
		    return false;
		else if (!validate) {
		    // try to avoid the worst
		    Element description = doc.getRootElement();
		    if (description.getChild("job", getNamespace()) == null)
		        description.addContent(new Element("job", getNamespace()));
		    if (description.getChild("releases", getNamespace()) == null)
		        description.addContent(new Element("releases", getNamespace()));
		    if (description.getChild("configuration", getNamespace()) == null)
		        description.addContent(new Element("configuration", getNamespace()));
		}
		*/
		setDoc(doc);

		setChanged(false);
		setFilename(filename);
		return true;
	}

	public boolean readString(String str, boolean validate) throws JDOMException, IOException {

		StringReader sr = new StringReader(str);
		Document doc = getBuilder(true).build(sr);

		// Document doc = getBuilder(validate).build(filename);

		if (!validate && (!doc.hasRootElement() || !doc.getRootElement().getName().equals("description")))
			return false;
		else
			if (!validate) {
				// try to avoid the worst
				Element description = doc.getRootElement();
				if (description.getChild("job", getNamespace()) == null)
					description.addContent(new Element("job", getNamespace()));
				if (description.getChild("releases", getNamespace()) == null)
					description.addContent(new Element("releases", getNamespace()));
				if (description.getChild("configuration", getNamespace()) == null)
					description.addContent(new Element("configuration", getNamespace()));
			}

		setDoc(doc);

		setChanged(false);
		// setFilename(filename);
		return true;
	}

	public void write(String filename) throws IOException, JDOMException {
		writeWithDom(filename);
	}

	public void writeWithDom(String filename) throws IOException, JDOMException {
		String encoding = Editor.SCHEDULER_ENCODING;
		if (encoding.equals(""))
			encoding = DEFAULT_ENCODING;
		reorderDOM(getDoc().getRootElement(), getNamespace());

		StringWriter stream = new StringWriter();
		// FileOutputStream stream = new FileOutputStream(new File(filename));

		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		out.output(getDoc(), stream);
		stream.close();
		String s = stream.toString();

		try {
			s = s.replaceAll("<pre space=\"preserve\">", "<pre>");
			getBuilder(true).build(new StringReader(s));
		}
		catch (JDOMException e) {
			int res = MainWindow.message(Messages.getMsg(conMessage_MAIN_LISTENER_OUTPUT_INVALID, e.getMessage()), SWT.ICON_WARNING | SWT.YES | SWT.NO);
			if (res == SWT.NO)
				return;
		}

		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename), encoding);

		writer.write(s);
		writer.close();

		setFilename(filename);
		setChanged(false);
	}

	public void writeWithHandler(String filename) throws IOException, JDOMException {

		String encoding = Editor.SCHEDULER_ENCODING;
		if (encoding.equals(""))
			encoding = DEFAULT_ENCODING;
		reorderDOM(getDoc().getRootElement(), getNamespace());

		FormatHandler handler = new FormatHandler(this);
		handler.setEnconding(encoding);
		SAXOutputter saxo = new SAXOutputter(handler);
		saxo.output(getDoc());

		try {
			getBuilder(true).build(new StringReader(handler.getXML()));
		}
		catch (JDOMException e) {
			int res = MainWindow.message(Messages.getMsg(conMessage_MAIN_LISTENER_OUTPUT_INVALID, e.getMessage()), SWT.ICON_WARNING | SWT.YES | SWT.NO);
			if (res == SWT.NO)
				return;
		}

		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename), encoding);

		writer.write(handler.getXML());
		writer.close();

		setFilename(filename);
		setChanged(false);
	}

	public String getXML(Element element) throws JDOMException {
		String encoding = Editor.SCHEDULER_ENCODING;
		if (encoding.equals(""))
			encoding = DEFAULT_ENCODING;

		reorderDOM(element, getNamespace());

		FormatHandler handler = new FormatHandler(this);
		handler.setEnconding(encoding);
		SAXOutputter saxo = new SAXOutputter(handler);
		saxo.output(element);

		return handler.getXML();
	}

	public Element noteAsDom(String xml) throws JDOMException, IOException {
		xml = xml.replaceAll("<pre space=\"preserve\">", "<pre>");

		StringReader reader = new StringReader(xml);
		Document doc = getBuilder(true).build(reader);

		Element root = doc.getRootElement();
		doc.removeContent();
		doc.addContent(((Element) getRoot().clone()).addContent(root));

		Element div = doc.getRootElement().getChild("div", getNamespace("xhtml"));
		doc.getRootElement().removeContent();
		return div;
	}

	public String noteAsStr(Element element) {
		StringWriter stream = new StringWriter();
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		try {
			out.output(element.getContent(), stream);
			stream.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		String str = stream.toString().trim();
		if (str.startsWith("<div")) {
			str = str.replaceFirst("\\A<\\s*div\\s*xmlns\\s*=\\s*\"[a-zA-Z0-9/:\\.]*\"\\s*>\\s*", "");
			str = str.replaceFirst("\\s*<\\s*/\\s*div\\s*>\\Z", "");
		}
		str = str.replaceAll("<pre space=\"preserve\">", "<pre>");
		return str;
	}
}
