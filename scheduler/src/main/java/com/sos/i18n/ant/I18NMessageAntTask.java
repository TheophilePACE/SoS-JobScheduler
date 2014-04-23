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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Echo;

import com.sos.i18n.Msg;
import com.sos.i18n.annotation.I18NMessage;
import com.sos.i18n.annotation.I18NResourceBundle;

/**
 * An ANT task to retrieve an i18n message. A typical usage of this ANT task is as follows:
 *
 * <pre><blockquote>
 *    &lt;taskdef name="i18n-msg"
 *             classpathref="i18nlog-jar.classpath"
 *             classname="com.sos.i18n.ant.I18NMessageAntTask" />
 *
 *    &lt;i18n-msg
 *        message="my.bundle.key" &lt;!-- the message key that identifies the message in the bundle -->
 *        bundle="my-messages" &lt;!-- where the messages are found; if not specified, 'messages' is the default -->
 *        locale="de" &lt;!-- explicitly defines what message translation to retrieve; defaults to the VM default locale -->
 *        property="prop.name"> &lt;!-- if defined, the message is stored in this property; otherwise it is echoed -->
 *      &lt;arg value="first arg" /> &lt;!-- replaces {0} in the message -->
 *      &lt;arg value="second arg" /> &lt;!-- replaces {1} in the message -->
 *    &lt;i18n-msg>
 * </blockquote></pre>
 *
 * The <code>classpathref</code> must also point to the resource bundle files that contain the i18n messages. Note
 * that this task extends the <code>echo</code> task, so all attributes for that task are also allowed. The only
 * difference is the <code>message</code> attribute is not the message itself, but rather the resource bundle
 * message key.
 *
 * @author  <a href="mailto:jmazzitelli@users.sourceforge.net">John Mazzitelli</a>
 * @version $Revision: 1.2 $
 */
@I18NResourceBundle(baseName = "i18n-anttask-messages", defaultLocale = "en")
public class I18NMessageAntTask extends Echo {
	/**
	 * This is the base name of this task's own I18N message bundle.
	 */
	static final Msg.BundleBaseName	BUNDLE_BASE_NAME		= new Msg.BundleBaseName("i18n-anttask-messages");

	@I18NMessage("Missing the message key to the i18n message (the 'message' attribute)")
	static final String				I18NMSG_MISSING_MESSAGE	= "I18NMessageAntTask.missing-message-key";

	/**
	 * The base bundle name to identify the resource bundle where the message is found.
	 */
	private Msg.BundleBaseName		m_bundle;

	/**
	 * Explicitly defines the locale of the message to retrieve. This is usually not specified - it will default to
	 * the VM's default locale.
	 */
	private Locale					m_locale;

	/**
	 * If defined, the message will be stored in this property. Otherwise, the message will be echoed.
	 */
	private String					m_property;

	/**
	 * The list of arguments whose values replace the localized message placeholders such as {0} and {1}.
	 */
	private List<Arg>				m_arguments				= new ArrayList<Arg>();

	/**
	 * Sets the base bundle name which identifies the resource bundle where the message can be found.
	 *
	 * @param bundle
	 */
	public void setBundle(Msg.BundleBaseName bundle) {
		m_bundle = bundle;
	}

	/**
	 * Sets the locale of the message to be retrieved. If not defined, this will default to the VM's default locale.
	 *
	 * @param locale
	 */
	public void setLocale(Locale locale) {
		m_locale = locale;
	}

	/**
	 * Sets the name of the property whose value will be set to the localized message string. If this is not
	 * defined, the message is simply echoed.
	 *
	 * @param property
	 */
	public void setProperty(String property) {
		m_property = property;
	}

	/**
	 * Creates a new argument that is used to define the values of the localized message placeholders (such as {0}).
	 *
	 * @return new arg
	 */
	public Arg createArg() {
		Arg arg = new Arg();
		m_arguments.add(arg);
		return arg;
	}

	/**
	 * @see org.apache.tools.ant.Task#execute()
	 */
	public void execute() throws BuildException {
		validateTaskConfiguration();

		// because <echo> task lets you have the message in the XML text node, we need to trim all newlines and whitespace
		message = message.replaceAll("\r\n", "").replaceAll("\n", "").trim();

		// let's get the arguments

		// let's convert the message (which is only a bundle key right now) to the actual localized message
		message = Msg.createMsg(m_bundle, m_locale, message, m_arguments.toArray()).toString();

		// if property is set, put the message text in that property, otherwise, we echo the message
		if (m_property != null) {
			Project proj = getOwningTarget().getProject();
			proj.setNewProperty(m_property, message);
		}
		else {
			super.execute();
		}

		return;
	}

	/**
	 * Ensures that the information passed to the task is valid.
	 *
	 * @throws BuildException if the task configuration was invalid in some way
	 */
	private void validateTaskConfiguration() throws BuildException {
		if (message == null) {
			throw new BuildException(Msg.createMsg(BUNDLE_BASE_NAME, I18NMSG_MISSING_MESSAGE).toString());
		}

		return;
	}

	/**
	 * The inner &lt;arg> element that defines the replacement strings for the localized message placeholders (for
	 * example: {0}, {1}).
	 */
	public class Arg extends Task {
		private String	value	= null;

		/**
		 * Sets the argument's string value
		 *
		 * @param v
		 */
		public void setValue(String v) {
			value = v;
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return value;
		}
	}
}
