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
package com.sos.JSHelper.io.Files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.apache.xalan.xslt.EnvironmentCheck;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.sos.JSHelper.DataElements.JSDataElementDateISO;
import com.sos.JSHelper.Options.SOSOptionFileName;

/* ---------------------------------------------------------------------------
 APL/Software GmbH - Berlin
 ##### generated by ClaviusXPress (http://www.sos-berlin.com) #########
 2007-09-10, kb@sos-berlin.com (sgx2343)
 -------------------------------------------------------------------------------
 <docu type="smcw" version="1.0">
 <project>com.sos.IDocs</project>
 <name>JSXMLFile</name>
 <title>JSXMLFile
 </title>
 <description>
 <para>
 JSXMLFile
 </para>
 </description>
 <params>
 </params>
 <keywords>
 <keyword>IDoc</keyword>
 <keyword>Splitter</keyword>
 </keywords>
 <categories>
 <category>IDoc</category>
 </categories>
 <date>2007-09-10</date>
 <copyright>� 2006 by Klaus D. Buettner</copyright>
 <author>kb@sos-berlin.com</author>
 <changes>
 <change who='sgx2343' when='2007-09-10' id='created'>
 <what>
 <para>
 created
 </para>
 </what>
 </change>
 </changes>
 </docu>
 ---------------------------------------------------------------------------- */

public class JSXMLFile extends JSTextFile /* implements ErrorHandler */{

	public static final String			conORG_APACHE_XERCES_JAXP_DOCUMENT_BUILDER_FACTORY_IMPL	= "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl";
	//	public static final String			conORG_APACHE_XERCES_JAXP_DOCUMENT_BUILDER_FACTORY_IMPL						= "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl";
	private static Logger				logger													= Logger.getLogger(JSXMLFile.class);

	private static final long			serialVersionUID										= 1L;
	private final String				conClassName											= "JSXMLFile";
	protected final String				conMissingDate											= "0000-00-00";
	protected final String				conMissingDate2											= "--";
	private Boolean						flgFileIsOpen											= false;
	private int							intIndent												= 0;

	private Boolean						flgDecrIndent											= false;
	private Boolean						flgIncrIndent											= false;
	private Vector<SOSXMLAttribute>		lstAttributes											= null;
	private int							intNumberOfXMLDeclarations								= 0;

	Document							document												= null;

	protected HashMap<String, String>	hsmParameters											= null;

	public JSXMLFile() {
		super(null);
		// nothing to do
	}

	public JSXMLFile(final SOSOptionFileName pobjOptionElementFileName) throws Exception {
		super(pobjOptionElementFileName.Value());
		strFileName = pobjOptionElementFileName.Value();

	}

	public JSXMLFile(final String pstrFileName) throws Exception {
		super(pstrFileName);
		strFileName = pstrFileName;
	}

	/* ---------------------------------------------------------------------------
	 * <br />
	 * Create XML-File for IDoc
	 *
	 */
	/*
	 <method type="smcw" version="1.0">
	 <name>CreateXMLFile</name>
	 <title>Create XML-File for IDoc</title>
	 <description>
	 <para>
	 Create XML-File for IDoc
	 </para>
	 <para>
	 Die Methode legt den XML-File an.
	 Falls er bereits existiert, wird er zun�chst gel�scht und dann neu angelegt.
	 </para>
	 <para>
	 Die Namespace-Infos werden aus den IDoc-Optionen �bernommen.
	 </para>
	 <para>
	 Der Tag "TechnicalInformations" mit den Untertags wird angelegt.
	 Die notwendigen Infos werden den IDoc-Optionen entnommen.
	 </para>

	 </description>
	 <params>
	 </params>
	 <keywords>
	 <keyword>IDoc</keyword>
	 <keyword>XML-File</keyword>
	 </keywords>
	 <categories>
	 <category>IDoc</category>
	 <category>XML</category>
	 </categories>
	 </method>
	 ---------------------------------------------------------------------------- */
	public void open(final String pstrOutputFileName) throws Exception {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::open";

		// if (FileHandle == null) {
		// FileWriter fleFileWriter = null;
		// strFileName = pstrOutputFileName;
		// fleFile = new File(strFileName);
		//
		// if (fleFile.exists()) {
		// Date dteLastModified = new Date(fleFile.lastModified());
		// message(conMethodName + " - File exist: " + strFileName + ", Size is : " + fleFile.length() + ", last modified : " +
		// dteLastModified);
		// fleFile.delete();
		// fleFileWriter = new FileWriter(fleFile);
		// }
		// else {
		// fleFileWriter = new FileWriter(fleFile);
		// }
		// FileHandle = new BufferedWriter(fleFileWriter);
		// message(conMethodName + " - File opened: " + strFileName);
		// }

		// newTag(pobjIDocOptions.RootTagName() + " " + pobjIDocOptions.xmlns());
		// CreateTechnicalInformations(pobjIDocOptions);
	}

	public JSXMLFile XMLTagV(final String pstrTagName, final String pstrTagValue) throws Exception {
		return this.newTag(pstrTagName, pstrTagValue);
	}

	private String getStartTag(final String strTagName) {
		final String strT = addBraces(adjustTagName(strTagName) + BuildAttributesString());
		if (strTagName.startsWith("/")) {
			flgDecrIndent = true;
		}
		else {
			flgIncrIndent = true;
		}
		return strT;
	}

	private String getEmptyTag(final String strTagName) {
		final String strT = addBraces(adjustTagName(strTagName) + BuildAttributesString() + " /");
		flgDecrIndent = true;
		flgIncrIndent = true;
		return strT;
	}

	private String BuildAttributesString() {
		String strT = "";
		if (lstAttributes != null) {
			for (int i = 0; i < lstAttributes.size(); i++) {
				strT += " " + lstAttributes.elementAt(i).toString();
			}
			lstAttributes = null;
		}
		return strT;
	}

	private String adjustTagName(final String pstrTagName) {
		return pstrTagName;
	}

	private String addBraces(final String pstrParam) {
		return "<" + pstrParam + ">";
	}

	private String getEndTag(final String strTagName) {
		flgDecrIndent = true;
		final String strT = addBraces("/" + adjustTagName(strTagName));
		return strT;
	}

	/**
	 *
	 * \brief tag
	 *
	 * \details
	 *
	 * \return String
	 *
	 * @param pstrTagName
	 * @param pstrTagValue
	 * @return
	 */
	public String tag(final String pstrTagName, final String pstrTagValue) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::tag";

		String strValue = pstrTagValue;
		if (strValue == null) {
			strValue = "";
		}
		else {
			if (strValue.matches(".*[&<>].*") && !pstrTagValue.contains("![CDATA[")) {
				strValue = MakeCData(strValue);
			}
		}
		String strTemp = "";
		if (strValue.length() == 0) {
			strTemp = String.format("%1$s", getEmptyTag(pstrTagName));
		}
		else {
			strTemp = String.format("%1$s%2$s%3$s", getStartTag(pstrTagName), strValue, getEndTag(pstrTagName));
		}
		return strTemp;
	} // public String tag

	public JSXMLFile newTag(final String pstrTagName, final String pstrTagValue) throws Exception {
		String strValue = pstrTagValue;
		if (strValue == null) {
			strValue = "";
		}
		else {
			// TODO �nderung pr�fen
			// if (strValue.matches(".*[&<>].*")) {
			if (strValue.matches(".*[&<>].*") && !pstrTagValue.contains("![CDATA[")) {
				strValue = MakeCData(strValue);
			}
		}
		String strTemp = "";
		if (strValue.length() == 0) {
			strTemp = String.format("%1$s", getEmptyTag(pstrTagName));
		}
		else {
			strTemp = String.format("%1$s%2$s%3$s", getStartTag(pstrTagName), strValue, getEndTag(pstrTagName));
		}
		Write2File(strTemp);
		return this;
	}

	public JSXMLFile Write2File(final String pstrS) throws Exception {
		String strT = "";
		if (!flgFileIsOpen) {
			flgFileIsOpen = true;
			// TODO version und encoding �ber Options steuern.
		}

		if (flgIncrIndent) {
			strT = getIndent();
			intIndent++;
			flgIncrIndent = false;
		}

		if (flgDecrIndent) {
			intIndent--;
			flgDecrIndent = false;
			strT = getIndent();
		}

		super.Write(strT + pstrS);
		return this;
	}

	private String getIndent() {
		String strT = "";
		if (intIndent > 0) {
			strT = "";
			for (int i = 0; i < intIndent; i++) {
				strT += "\t"; // " ";
			}
		}
		return strT;

	}

	public boolean isOpened() {
		return flgFileIsOpen;
	}

	/**
	 *
	 * \brief writeXMLDeclaration
	 *
	 * \details
	 *
	 * \return JSXMLFile
	 *
	 * @param pstrCharset
	 * @return
	 * @throws Exception
	 */
	public JSXMLFile writeXMLDeclaration(final String pstrCharset) throws Exception {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::writeXMLDeclaration";
		String strCharset = pstrCharset;
		if (strCharset == null || strCharset.length() <= 0) {
			strCharset = "ISO-8859-1";
		}
		Write2File(addBraces("?xml version='1.0' encoding='" + strCharset + "' ?"));

		// - <remark who='EQALS' when='Dienstag, 20. Oktober 2009' id='Korrektur' >
		/**
		 * \change Dienstag, 20. Oktober 2009 EQALS Korrektur
		 * Korrektur: Wenn mehrere XML Deklarationen, dann nicht validieren
		 */
		// - <newcode>
		intNumberOfXMLDeclarations++;
		// - </newcode>
		// - </remark> <!-- id=<Korrektur> -->

		return this;
	} // public JSXMLFile writeXMLDeclaration}

	public JSXMLFile writeXMLDeclaration() throws Exception {

		writeXMLDeclaration(null);
		return this;
	}

	public JSXMLFile comment(final String pstrComment) throws Exception {
		Write2File(addBraces("!-- " + pstrComment + " --"));
		return this;
	}

	/**
	 *
	 * \brief tag
	 *
	 * \details
	 *
	 * \return String
	 *
	 * @param pstrTagName
	 * @return
	 */
	public String tag(final String pstrTagName) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::tag";

		return getStartTag(pstrTagName);
	} // public String tag}

	public JSXMLFile newTag(final String pstrTagName) throws Exception {
		Write2File(getStartTag(pstrTagName));
		return this;
	}

	public JSXMLFile endTag(final String pstrTagName) throws Exception {
		Write2File(getEndTag(pstrTagName));
		return this;
	}

	public JSXMLFile newCDataTag(final String pstrTagName) throws Exception {
		Write2File(getStartTag(pstrTagName) + "<![CDATA[");
		return this;
	}

	public JSXMLFile endCDataTag(final String pstrTagName) throws Exception {
		Write2File("]]>" + getEndTag(pstrTagName));
		return this;
	}

	public JSXMLFile newCDataTag(final String pstrTagName, final String pstrTagValue) throws Exception {
		if (pstrTagValue != null && pstrTagName != null) {
			if (pstrTagValue.length() > 0) {
				this.newTag(pstrTagName, MakeCData(pstrTagValue));
			}
		}
		return this;
	}

	/**
	 *
	 * \brief MakeCData
	 *
	 * \details
	 *
	 * \return String
	 *
	 * @param pstrValue
	 * @return
	 */
	public String MakeCData(final String pstrValue) {
		return addBraces("![CDATA[" + pstrValue + "]]");
	}

	public JSXMLFile newDateTag(final String pstrTagName, final String pstrTagValue) throws Exception {
		// if (pstrTagValue != null && pstrTagValue.equals(conMissingDate)) {
		if (pstrTagValue != null && (pstrTagValue.equals(conMissingDate) || pstrTagValue.equals("") || pstrTagValue.trim().equals(conMissingDate2))) {
			// nothing to do; tag omitted
		}
		else {
			this.newTag(pstrTagName, pstrTagValue);
		}
		return this;
	}

	// public void Close() throws IOException {
	//
	// @SuppressWarnings("unused")
	// final String conMethodName = conClassName + "::Close";
	//
	// super.close();
	// }
	public JSXMLFile addAttribute(final String pstrAttributeName, final String pstrAttributeValue) {
		final SOSXMLAttribute objAtt = new SOSXMLAttribute(pstrAttributeName, pstrAttributeValue);
		if (lstAttributes == null) {
			lstAttributes = new Vector<SOSXMLAttribute>();
		}
		lstAttributes.add(objAtt);

		return this;
	} // public

	/**
	 *
	 * \brief Transform
	 *
	 * \details
	 *
	 * \return void
	 *
	 * @param xslFile
	 * @param outputFile
	 * @throws TransformerException
	 * @throws TransformerConfigurationException
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public void Transform(final File xslFile, final File outputFile) throws TransformerException, TransformerConfigurationException, FileNotFoundException,
			Exception {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Transform";

		Transformer transformer = null;
		/**
		 * this is to specifiy which xslt-processor must used by the transformer
		 * saxon is a xslt-2.0 processor and the first choice.
		 *
		 * saxon 8 is needed
		 */
		System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");
		try {
			final TransformerFactory tFactory = TransformerFactory.newInstance();
			transformer = tFactory.newTransformer(new StreamSource(xslFile));
		}
		catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
			throw e;
		}
		catch (TransformerException e) {
			e.printStackTrace();
			int i = 0;
			throw e;
		}
		int i = 0;
		i++;
		DOMSource objDS = new DOMSource(this.getDomDocument());
		// TransformerFactory tf = TransformerFactory.newInstance(conORG_APACHE_XERCES_JAXP_DOCUMENT_BUILDER_FACTORY_IMPL,
		// this.getClass().getClassLoader());
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transf = tf.newTransformer();

		if (hsmParameters != null) {
			for (String strKey : hsmParameters.keySet()) {
				String strValue = hsmParameters.get(strKey);
				logger.debug(String.format("Set XSLT-Parameter '%1$s' to value '%2$s'", strKey, strValue));
				transf.setParameter(strKey, strValue);
				transformer.setParameter(strKey, strValue);
			}
		}

		JSDataElementDateISO objISODate = new JSDataElementDateISO();
		objISODate.Now();
		transf.setParameter("sos.timestamp", objISODate.Now());
		transformer.setParameter("sos.timestamp", objISODate.Now());

		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		transf.transform(objDS, result);
		System.out.println(writer.toString());

		if (outputFile == null) {
			transformer.transform(objDS, // ...
					new StreamResult(new java.io.OutputStreamWriter(System.out)));
		}
		else {
			transformer.transform(objDS, // ...
					new StreamResult(new FileOutputStream(outputFile)));
			// transformer.transform(new StreamSource(fleFile), // ...
			// new StreamResult(new FileOutputStream(outputFile)));
		}
	}

	@Override
	public String getContent() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getContent";

		String strT = "";
		try {
			DOMSource objDS = new DOMSource(this.getDomDocument());
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transf = tf.newTransformer();
			transf.transform(objDS, result);
			strT = writer.toString();
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			// TODO: handle exception
		}

		return strT;
	} // private String getContent

	/**
	 *
	 * \brief Validate
	 *
	 * \details
	 *
	 * \return void
	 *
	 * @throws SAXException
	 * @throws IOException
	 */

	public void Validate() throws SAXException, IOException {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Validate";

		// - <remark who='EQALS' when='Dienstag, 20. Oktober 2009' id='Korrektur' >
		/**
		 * \change Dienstag, 20. Oktober 2009 EQALS Korrektur
		 * Korrektur: Wenn mehrere XML Deklarationen, dann nicht validieren
		 */
		// - <newcode>
		if (intNumberOfXMLDeclarations > 1) {
			return;
		}
		// - </newcode>
		// - </remark> <!-- id=<Korrektur> -->

		// final SAXParser parser = new SAXParser(); // Xcerces

		// parser.setFeature("http://xml.org/sax/features/validation", true);
		// Set Handlers in the parser
		// parser.setContentHandler(sample);
		// parser.setEntityResolver(sample);
		// parser.setDTDHandler(sample);
		// parser.setErrorHandler(this);
		// Convert file to URL and parse

		// spooler_log.info("createURL(xmlFileName): " + createURL(xmlFileName));
		// parser.parse(createURL(xmlFileName).toString());
		// parser.parse(new org.xml.sax.InputSource(super.Reader()));

		// return void;
	} // public void Validate

	/**
	 *
	 * @author eqbfd
	 *
	 */
	class SOSXMLAttribute {

		private String	strAttributeName	= null;
		private String	strAttributeValue	= null;

		public SOSXMLAttribute() {
			super();
			// TODO Auto-generated constructor stub
		}

		public SOSXMLAttribute(final String pstrAttributeName, final String pstrAttributeValue) {
			strAttributeName = pstrAttributeName;
			strAttributeValue = pstrAttributeValue;
		}

		@Override
		public String toString() {
			return strAttributeName + "='" + strAttributeValue + "'";
		}
	} // class SOSXMLAttribute

	class SOSXMLTag {
		private String	Name;
		private String	Value;
		private Boolean	isCdata;
		private int		DataType;

		public SOSXMLTag() {
			//
		}

		@Override
		public String toString() {
			//
			return "not implemented";
		}

		public SOSXMLTag(final String pstrTagName) {
			this(pstrTagName, "");
		}

		public SOSXMLTag(final String pstrTagName, final String pstrTagValue) {
			Name = pstrTagName;
			Value = pstrTagValue;
		}

		/**
		 * @return Returns the dataType.
		 */
		public int getDataType() {
			return DataType;
		}

		/**
		 * @param dataType The dataType to set.
		 */
		public void setDataType(final int dataType) {
			DataType = dataType;
		}

		/**
		 * @return Returns the isCdata.
		 */
		public Boolean getIsCdata() {
			return isCdata;
		}

		/**
		 * @param isCdata1 The isCdata to set.
		 */
		public void setIsCdata(final Boolean isCdata1) {
			isCdata = isCdata1;
		}

		/**
		 * @return Returns the name.
		 */
		public String getName() {
			return Name;
		}

		/**
		 * @param name The name to set.
		 */
		public void setName(final String name) {
			Name = name;
		}

		/**
		 * @return Returns the value.
		 */
		public String getValue() {
			return Value;
		}

		/**
		 * @param value The value to set.
		 */
		public void setValue(final String value) {
			Value = value;
		}

	} // class SOSXMLTag

	public void error(final SAXParseException pobjException) throws SAXException {
		pobjException.printStackTrace();
		throw pobjException;

	}

	public void fatalError(final SAXParseException arg0) throws SAXException {

	}

	public void warning(final SAXParseException arg0) throws SAXException {

	}

	public Document getDomDocument() throws ParserConfigurationException {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getDomDocument";

		if (document == null) {
			// SAXParserFactory spf = SAXParserFactory.newInstance();
			// spf.setNamespaceAware(true);
			// spf.setXIncludeAware(true);

			// && -Dorg.apache.xerces.xni.parser.XMLParserConfiguration=\
			// org.apache.xerces.parsers.XIncludeParserConfiguration \

			System.setProperty("javax.xml.parsers.DocumentBuilderFactory", conORG_APACHE_XERCES_JAXP_DOCUMENT_BUILDER_FACTORY_IMPL);
			//Die folgende Property darf nicht zusammen mit setXIncludeAware(true) gesetzt werden, sonst
			// javax.xml.parsers.ParserConfigurationException: Feature 'http://apache.org/xml/features/xinclude' is not recognized.
			//System.setProperty("org.apache.xerces.xni.parser.XMLParserConfiguration", "org.apache.xerces.parsers.XIncludeParserConfiguration");

			// download : http://mirrors.ibiblio.org/pub/mirrors/maven2/com/sun/org/apache/jaxp-ri/1.4/jaxp-ri-1.4.jar
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance(conORG_APACHE_XERCES_JAXP_DOCUMENT_BUILDER_FACTORY_IMPL, this.getClass()
					.getClassLoader());
			// DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

			// see http://xerces.apache.org/xerces2-j/features.html

			// if NamespaceAware is not set, XInclude will not work :-))

			builderFactory.setNamespaceAware(true);
			builderFactory.setXIncludeAware(true);

			// see http://download.oracle.com/javase/tutorial/essential/environment/sysprop.html
			String strUserDir = System.getProperty("user.dir");
			try {
				String strPath = new File(strFileName).getParent();
				if (strPath != null) {
					System.setProperty("user.dir", strPath);
				}
				// document = builderFactory.newSAXParser(). //parse(new FileInputStream(this.strFileName));
				DocumentBuilder builder = builderFactory.newDocumentBuilder();

				logger.debug("Namespace aware:" + builder.isNamespaceAware());
				logger.debug("XInclude aware:" + builder.isXIncludeAware());
				Reader objR = new InputStreamReader(new FileInputStream(strFileName), "UTF-8");
				InputSource inS = new InputSource(objR);
				document = builder.parse(inS);
			}
			catch (ParserConfigurationException e) {
				e.printStackTrace(System.err);
			}
			catch (FileNotFoundException e) {
				e.printStackTrace(System.err);
			}
			catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				System.setProperty("user.dir", strUserDir);
				System.out.println(document.toString());
			}
		}

		return document;
	} // private Dom getDomDocument

	public void writeDocument(final String pstrFileName) throws Exception {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::writeDocument";

		getDomDocument();
		this.writeXmlFile(document, pstrFileName);

	} // private void writeDocument

	public void writeXmlFile(final Document doc, final String filename) throws Exception {
		try {
			// Prepare the DOM document for writing
			Source source = new DOMSource(doc);

			// Prepare the output file
			File file = new File(filename);
			Result result = new StreamResult(file.toURI().getPath());

			// Write the DOM document to the file
			Transformer xformer = TransformerFactory.newInstance().newTransformer();
			xformer.transform(source, result);
		}
		catch (TransformerConfigurationException e) {
			e.printStackTrace(System.err);
			throw e;
		}
		catch (TransformerException e) {
			e.printStackTrace(System.err);
			throw e;
		}
	}

	public void setParameters(final HashMap<String, String> pobjHshMap) {
		hsmParameters = pobjHshMap;
	}

	// requires ant.main ? This is a know bug. I dont't know on which version of xalan it is solved.
	public void EnvironmentCheck() throws Exception {

		// works if xalan is available only
		EnvironmentCheck ec = new EnvironmentCheck();
		StringWriter sWri = new StringWriter();
		PrintWriter pWri = new PrintWriter(new StringWriter());
		ec.checkEnvironment(pWri);
		pWri.close();
		try {
			logger.debug("Checking Xalan environment...");
			logger.debug(sWri.toString());
		}
		catch (Exception ex) {
			ex.printStackTrace(System.err);
			throw ex;
		}

	}
} // JSXMLFile
