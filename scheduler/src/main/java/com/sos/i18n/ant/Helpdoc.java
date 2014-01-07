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
/**
 * I18N Messages and Logging
 * Copyright (C) 2006 John J. Mazzitelli
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA 
 */
package com.sos.i18n.ant;

import java.io.File;
import java.io.FileInputStream;

import org.apache.tools.ant.ProjectComponent;

/**
 * Inner element of the {@link I18NAntTask} ant task that defines how to generate the help documentation for all
 * the I18N messages. Each I18N message will correspond to a single help document item (the item's text is defined
 * by a template containing replacement strings). For each resource bundle that gets generated, a corresponding
 * help document will be created in the output directory. Example usage:
 *
 * <pre>
 * &lt;helpdoc
 *     outputdir="${build.dir}/help"
 *     append="false"
 *     outputfileext=".html"
 *     templateitem="${template.dir}/my-help-template.html"
 *     templateheader="${template.dir}/my-help-header.html"
 *     templatefooter="${template.dir}/my-help-footer.html"
 *     templatecharset="UTF-8" />
 * </pre>
 *
 * <p>The only required attribute is "outputdir". See the setter methods in this class for a description of these
 * attributes.</p>
 *
 * @author  <a href="mailto:jmazzitelli@users.sourceforge.net">John Mazzitelli</a>
 * @version $Revision$
 */
public class Helpdoc extends ProjectComponent {
	/**
	 * The template replacement string for the resource bundle name. When this replacement string is found in the
	 * template contents, it will be replaced with the resource bundle base name.
	 */
	public static final String	TEMPLATE_BUNDLE		= "@@@I18NBUNDLE@@@";

	/**
	 * The template replacement string for the resource bundle key. When this replacement string is found in the
	 * template contents, it will be replaced with the resource bundle key.
	 */
	public static final String	TEMPLATE_KEY		= "@@@I18NKEY@@@";

	/**
	 * The template replacement string for the resource bundle value. When this replacement string is found in the
	 * template contents, it will be replaced with the resource bundle value string (i.e. the actual message text).
	 */
	public static final String	TEMPLATE_MESSAGE	= "@@@I18NMESSAGE@@@";

	/**
	 * The template replacement string for the message help text. When this replacement string is found in the
	 * template contents, it will be replaced with the help description for the message (if one exists).
	 */
	public static final String	TEMPLATE_HELP		= "@@@I18NHELP@@@";

	/**
	 * The output directory where the help documentation will be written.
	 */
	private String				m_outputDir;

	/**
	 * If <code>true</code>, the help doc items will be appended to the end of its help document if it already
	 * exists. If <code>false</code>, the output help document file will be overwritten if it exists.
	 */
	private boolean				m_append;

	/**
	 * The template file that defines what each item in the help documentation will look like.
	 */
	private String				m_templateItemFile;

	/**
	 * The template header file that defines the contents of the beginning of each help document. There is one help
	 * document per resource bundle generated.
	 */
	private String				m_templateHeaderFile;

	/**
	 * The template footer file that defines the contents of the end of each help document. There is one help
	 * document per resource bundle generated.
	 */
	private String				m_templateFooterFile;

	/**
	 * The contents of the template item file - this is used to cached the contents so we only have to read the file
	 * once.
	 */
	private String				m_templateItemFileContents;

	/**
	 * The contents of the template header file - this is used to cached the contents so we only have to read the
	 * file once.
	 */
	private String				m_templateHeaderFileContents;

	/**
	 * The contents of the template footer file - this is used to cached the contents so we only have to read the
	 * file once.
	 */
	private String				m_templateFooterFileContents;

	/**
	 * The charset of all the template files' contents - if <code>null</code>, the default charset should be
	 * assumed.
	 */
	private String				m_templateCharset;

	/**
	 * The file extension on all output help document files.
	 */
	private String				m_outputFileExtension;

	/**
	 * If <code>true</code>, only those I18N Messages that have help descriptions associated with them will have
	 * their help doc items output; others will be ignored. If <code>false</code>, all messages will have help doc
	 * items output for them.
	 */
	private boolean				m_helpMessagesOnly;

	/**
	 * No-arg constructor for {@link Helpdoc} required by the ANT framework.
	 */
	public Helpdoc() {
		m_outputDir = null;
		m_append = false;
		m_templateItemFile = null;
		m_templateItemFileContents = null;
		m_templateHeaderFile = null;
		m_templateHeaderFileContents = null;
		m_templateFooterFile = null;
		m_templateFooterFileContents = null;
		m_templateCharset = null;
		m_outputFileExtension = ".html";
		m_helpMessagesOnly = false;
	}

	/**
	 * Returns the output directory where the contents of the help documents will be written.
	 *
	 * @return output directory path
	 */
	public String getOutputdir() {
		return m_outputDir;
	}

	/**
	 * The output directory where the contents of the help documents will be written.
	 *
	 * @param out output directory path
	 */
	public void setOutputdir(String out) {
		m_outputDir = out;
	}

	/**
	 * If <code>true</code>, the help documentation will be appended to the help document files located in the
	 * output directory. If <code>false</code>, and an output file already exists, it will be overwritten. The
	 * default is <code>false</code>.
	 *
	 * <p>Be careful if you use <code>true</code> here; if you do, you probably will want to define empty header and
	 * footer template files since appending to a help document will append to the very end of the file, which is
	 * after the header and footer contents have been output (which means this may end up generating a badly
	 * formatted help document).</p>
	 *
	 * @return append
	 */
	public boolean getAppend() {
		return m_append;
	}

	/**
	 * If <code>true</code>, the help documentation will be appended to the help document files located in the
	 * output directory. If <code>false</code>, and an output file already exists, it will be overwritten.
	 *
	 * <p>Be careful if you use <code>true</code> here; if you do, you probably will want to define empty header and
	 * footer template files since appending to a help document will append to the very end of the file, which is
	 * after the header and footer contents have been output (which means this may end up generating a badly
	 * formatted help document).</p>
	 *
	 * @param append
	 */
	public void setAppend(boolean append) {
		m_append = append;
	}

	/**
	 * Returns the template item file whose contents define what each item of a help document will look like.
	 *
	 * @return path to the file containing the help doc item template
	 */
	public String getTemplateitem() {
		return m_templateItemFile;
	}

	/**
	 * The template item file whose contents define what each item of a help document will look like.
	 *
	 * @param template the path to the file containing the help doc item template
	 */
	public void setTemplateitem(String template) {
		m_templateItemFile = template;
	}

	/**
	 * Returns the template header file whose contents define what the beginning of each help document will look
	 * like. There is one help document for each resource bundle generated.
	 *
	 * @return path to the file containing the help doc header template
	 */
	public String getTemplateheader() {
		return m_templateHeaderFile;
	}

	/**
	 * Sets the template header file whose contents define what the beginning of each help document will look like.
	 * There is one help document for each resource bundle generated.
	 *
	 * @param template the path to the file containing the help doc header template
	 */
	public void setTemplateheader(String template) {
		m_templateHeaderFile = template;
	}

	/**
	 * Returns the template footer file whose contents define what the end of each help document will look like.
	 * There is one help document for each resource bundle generated.
	 *
	 * @return path to the file containing the help doc footer template
	 */
	public String getTemplatefooter() {
		return m_templateFooterFile;
	}

	/**
	 * Sets the template footer file whose contents define what the end of each help document will look like. There
	 * is one help document for each resource bundle generated.
	 *
	 * @param template the path to the file containing the help doc footer template
	 */
	public void setTemplatefooter(String template) {
		m_templateFooterFile = template;
	}

	/**
	 * Returns the charset that all the template files' contents can be decoded as. If <code>null</code>, the
	 * default charset should be used.
	 *
	 * @return the template file charset (item, header and footer)
	 */
	public String getTemplatecharset() {
		return m_templateCharset;
	}

	/**
	 * Sets the charset that all the template files' contents can be decoded as. If <code>null</code>, the default
	 * charset is assumed.
	 *
	 * @param charset the charset of the template files (item, header and footer)
	 */
	public void setTemplatecharset(String charset) {
		m_templateCharset = charset;
	}

	/**
	 * Returns the file extension that will be appended to the end of all help doc output files. An example would be
	 * ".txt" or ".html". If this was never set, the default is ".html".
	 *
	 * @return the output file extension
	 */
	public String getOutputfileext() {
		return m_outputFileExtension;
	}

	/**
	 * Sets the file extension that will be appended to the end of all help doc output files. An example would be
	 * ".txt" or ".html". The default is ".html".
	 *
	 * @param ext the file extension for all output files
	 */
	public void setOutputfileext(String ext) {
		m_outputFileExtension = ext;
	}

	/**
	 * If <code>true</code>, only those I18N Messages that have help descriptions associated with them will have
	 * their help doc items output; others will be ignored. If <code>false</code>, all messages will have help doc
	 * items output for them.
	 *
	 * @return flag
	 */
	public boolean getHelpmessagesonly() {
		return m_helpMessagesOnly;
	}

	/**
	 * If <code>true</code>, only those I18N Messages that have help descriptions associated with them will have
	 * their help doc items output; others will be ignored. If <code>false</code>, all messages will have help doc
	 * items output for them.
	 *
	 * @param helpMessagesOnly
	 */
	public void setHelpmessagesonly(boolean helpMessagesOnly) {
		m_helpMessagesOnly = helpMessagesOnly;
	}

	/**
	 * Returns the contents of the template item file. If the template item file was not specified, a default
	 * template will be returned.
	 *
	 * @return contents of the template item file
	 *
	 * @throws Exception if failed to read the file
	 */
	public String getTemplateitemContents() throws Exception {
		if (m_templateItemFileContents == null) {
			if (m_templateItemFile == null) {
				m_templateItemFileContents = "<tr>" + "<td>" + TEMPLATE_KEY + "</td>" + "<td>" + TEMPLATE_MESSAGE + "</td>" + "<td>" + TEMPLATE_HELP + "</td>"
						+ "</tr>\n";
			}
			else {
				File template = new File(m_templateItemFile);
				byte[] bytes = new byte[(int) template.length()];

				FileInputStream fis = new FileInputStream(template);
				fis.read(bytes);
				fis.close();

				if (m_templateCharset == null) {
					m_templateItemFileContents = new String(bytes);
				}
				else {
					m_templateItemFileContents = new String(bytes, m_templateCharset);
				}
			}
		}

		return m_templateItemFileContents;
	}

	/**
	 * Returns the contents of the template header file. If the template header file was not specified, a default
	 * template will be returned.
	 *
	 * @return contents of the template header file
	 *
	 * @throws Exception if failed to read the file
	 */
	public String getTemplateheaderContents() throws Exception {
		if (m_templateHeaderFileContents == null) {
			if (m_templateHeaderFile == null) {
				m_templateHeaderFileContents = "<html>\n" + "<head><title>" + TEMPLATE_BUNDLE + "</title></head>\n" + "<body><h2>" + TEMPLATE_BUNDLE
						+ "</h2>\n" + "<table border=\"1\">\n" + "<tr>" + "<th>Message Code</th>" + "<th>Actual Message Text</th>"
						+ "<th>Message Description</th>" + "</tr>\n";
			}
			else {
				File template = new File(m_templateHeaderFile);
				byte[] bytes = new byte[(int) template.length()];

				FileInputStream fis = new FileInputStream(template);
				fis.read(bytes);
				fis.close();

				if (m_templateCharset == null) {
					m_templateHeaderFileContents = new String(bytes);
				}
				else {
					m_templateHeaderFileContents = new String(bytes, m_templateCharset);
				}
			}
		}

		return m_templateHeaderFileContents;
	}

	/**
	 * Returns the contents of the template footer file. If the template footer file was not specified, a default
	 * template will be returned.
	 *
	 * @return contents of the template footer file
	 *
	 * @throws Exception if failed to read the file
	 */
	public String getTemplatefooterContents() throws Exception {
		if (m_templateFooterFileContents == null) {
			if (m_templateFooterFile == null) {
				m_templateFooterFileContents = "</table>\n</body>\n</html>\n";
			}
			else {
				File template = new File(m_templateFooterFile);
				byte[] bytes = new byte[(int) template.length()];

				FileInputStream fis = new FileInputStream(template);
				fis.read(bytes);
				fis.close();

				if (m_templateCharset == null) {
					m_templateFooterFileContents = new String(bytes);
				}
				else {
					m_templateFooterFileContents = new String(bytes, m_templateCharset);
				}
			}
		}

		return m_templateFooterFileContents;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("outputdir=" + m_outputDir);
		sb.append("; outputfileext=" + m_outputFileExtension);
		sb.append("; append=" + m_append);
		sb.append("; helpmessagesonly=" + m_helpMessagesOnly);
		sb.append("; templateitem=" + m_templateItemFile);
		sb.append("; templateheader=" + m_templateHeaderFile);
		sb.append("; templatefooter=" + m_templateFooterFile);
		sb.append("; templatecharset=" + ((m_templateCharset == null) ? "<default>" : m_templateCharset));

		return sb.toString();
	}
}
