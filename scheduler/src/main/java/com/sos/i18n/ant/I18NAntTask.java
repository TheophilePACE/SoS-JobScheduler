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
 * Copyright (C) 2006-2010 John J. Mazzitelli
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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

import com.sos.i18n.Msg;
import com.sos.i18n.annotation.I18NMessage;
import com.sos.i18n.annotation.I18NMessages;
import com.sos.i18n.annotation.I18NResourceBundle;

/**
 * An ANT task to generate resource bundle files containing I18N messages defined by the I18N annotations. A
 * typical usage of this ANT task is as follows:
 *
 * <pre><blockquote>
 *    &lt;taskdef name="i18n" classpathref="i18nlog-jar.classpath" classname="com.sos.i18n.ant.I18NAntTask" />
 *
 *    &lt;i18n outputdir="${classes.dir}"
 *          defaultlocale="en"
 *          verbose="true"
 *          append="false"
 *          verify="true">
 *       &lt;classpath refid="my.classpath" />
 *       &lt;classfileset dir="${classes.dir}"/>
 *    &lt;/i18n>
 * </blockquote></pre>
 *
 * <p>Where this says to {@link #setOutputDir(File) output} all resource bundles in the classes directory, assume a
 * {@link #setDefaultLocale(String) default locale} of our application to be English, turn on
 * {@link #setVerbose(boolean) verbose mode} so this task can log alot of information about its progress, do not
 * {@link #setAppend(boolean) append} to any existing resource bundles (we want to overwrite them) and
 * {@link #setVerify(boolean) verify} that our code has not introduced duplicate or invalid messages. The classpath
 * defines where all the classes can be found and the classfileset defines those classes that this task should
 * process (it is these classes that will be examined for I18N message and resource bundle annotations - these
 * classes must be found on the given classpath).</p>
 *
 * <p>This ANT task can also output help documentation via the &lt;helpdoc> inner tag that allows you to develop
 * reference material for your users that further describe the meaning of the I18N messages (e.g. provide an
 * explanation of specific error messages and how users can perform corrective action). See {@link Helpdoc} for
 * more information.</p>
 *
 * @author  <a href="mailto:jmazzitelli@users.sourceforge.net">John Mazzitelli</a>
 * @version $Revision: 1.13 $
 * @see     Helpdoc
 */

@I18NResourceBundle(baseName = "i18n-anttask-messages", defaultLocale = "en")
public class I18NAntTask extends Task {
	/*!
	 * \var OUTPUT_CHARSET_NAME
	 * \brief We use ISO-8859-1, rather than the JVM's default encoding, as the encoding when writing the resource bundle
	 * properties files, since ISO-8859-1 is the encoding Java uses to read resource bundle properties files unless you
	 * jump through some major hoops, which we currently do not jump through in
	 * {@link com.sos.i18n.Msg#getResourceBundle()}.
	 * <p/>
	 * Note, for compatibility with older Java runtimes, we use the "historical Java name" for the encoding -
	 * "ISO8859_1".
	 */
	private static final String								OUTPUT_CHARSET_NAME								= "ISO-8859-1";

	/*!
	 * \var BUNDLE_BASE_NAME
	 * \brief This is the base name of this task's own I18N message bundle.
	 */
	static final Msg.BundleBaseName							BUNDLE_BASE_NAME								= new Msg.BundleBaseName("i18n-anttask-messages");

	@I18NMessages(value = { @I18NMessage("Hello, pussycat ...."), //
			@I18NMessage(value = "Hello, pussycat ....", locale = "en_UK", //
			explanation = "Hello, pussycat ...." //
			), //
			@I18NMessage(value = "Hallo, Mietzekatze ....", locale = "de", //
			explanation = "Wir begrüssen hier die Mietzekatze ...." //
			) //
	}, msgnum = "SOS-I-0010", msgurl = "")
	/*!
	 * \var HELLO_PUSSYCAT
	 * \brief Hello, pussycat ....
	 */
	public static final String								HELLO_PUSSYCAT									= "I18NAntTask.HELLO_PUSSYCAT";

	@I18NMessage("Processing I18N messages...")
	@I18NMessages({ @I18NMessage("Processing I18N messages..."), //
			@I18NMessage(value = "Processing I18N messages...", locale = "en_UK"), //
			@I18NMessage(value = "Suche nach I18N Meldungen ...", locale = "de") //
	})
	static final String										I18N_START_PROCESSING							= "I18NAntTask.start-processing";

	@I18NMessages({ @I18NMessage("Default file encoding is [{0}]. Output encoding is [{1}]."), //
			@I18NMessage(value = "Default file encoding is [{0}]. Output encoding is [{1}].", locale = "en_UK"), //
			@I18NMessage(value = "Standard-Zeichensatz ist [{0}], Ausgabe-Zeichensatz ist [{1}].", locale = "de") //
	})
	/*!
	 * \var I18N_ENCODING_NAMES
	 * \brief Message Key 'I18NAntTask.encoding-names'
	 */
	static final String										I18N_ENCODING_NAMES								= "I18NAntTask.encoding-names";

	@I18NMessages({ @I18NMessage("Processing I18N class file [{0}]..."), //
			@I18NMessage(value = "Processing I18N class file [{0}]...", locale = "en_UK"), //
			@I18NMessage(value = "Bearbeite Klassen-Datei [{0}] ...", locale = "de") //
	})
	/*!
	 * \var I18N_PROCESSING_CLASS
	 * \brief Processing I18N class file [{0}]...
	 */
	static final String										I18N_PROCESSING_CLASS							= "I18NAntTask.processing-class";

	@I18NMessages(value = { @I18NMessage("Loaded I18N class from classloader: [{0}]"), //
			@I18NMessage(value = "Loaded I18N class from classloader: [{0}]", locale = "en_UK"), //
			@I18NMessage(value = "Klassen-Datei [{0}] geladen ...", locale = "de") //
	}, msgnum = "", msgurl = "")
	/*!
	 * \var I18N_LOADED_CLASS
	 * \brief Loaded I18N class from classloader: [{0}]
	 */
	static final String										I18N_LOADED_CLASS								= "I18NAntTask.loaded-class";

	@I18NMessages(value = { @I18NMessage("The class [{0}] does not appear to be annotated for I18N and was not processed"), //
			@I18NMessage(value = "The class [{0}] does not appear to be annotated for I18N and was not processed", locale = "en_UK"), //
			@I18NMessage(value = "Die Klassendatei [{0}] wird nicht verarbeitet", //
			explanation = "Die Klassendatei [{0}] hat keine @I18ResourceBundle-Annotation und wird deshalb nicht verarbeitet", locale = "de") //
	})
	/*!
	 * \var I18N_CLASS_IGNORED
	 * \brief The class [{0}] does not appear to be annotated for I18N and was not processed
	 */
	static final String										I18N_CLASS_IGNORED								= "I18NAntTask.class-ignored";

	@I18NMessages(value = {
			@I18NMessage("Cannot find class file [{0}] on the I18N task''s classpath. Cause: {1}"), //
			@I18NMessage(value = "Cannot find class file [{0}] on the I18N task''s classpath. Cause: {1}", locale = "en_UK"), //
			@I18NMessage(value = "Klasse '{0}' im Klassenpfad '{1}' nicht gefunden", locale = "de", explanation = "Ein Verweis auf die Klasse '{0}' wurde in der aktuell bearbeiteten Klasse gefunden, aber diese\n"
					+ "Klasse wurde im Klassenpfad '{1}' nicht gefunden.\n"
					+ "\nErgänzen Sie gegebenenfalls den Klassenpfad, falls diese Klasse ebenfalls bearbeitet werden soll.") //
	}, msgnum = "I18N-W-0009", msgurl = "")
	/*!
	 * \var I18N_CANNOT_FIND_CLASS
	 * \brief Cannot find class file [{0}] on the I18N task''s classpath. Cause: {1}
	 */
	static final String										I18N_CANNOT_FIND_CLASS							= "I18NAntTask.cannot-find-class";

	@I18NMessages(value = { @I18NMessage("An error occurred when attempting to put the template footer in the help documents. Cause: {0}"), //
			@I18NMessage(value = "An error occurred when attempting to put the template footer in the help documents. Cause: {0}", locale = "en_UK"), //
			@I18NMessage(value = "Fusszeilen des Hilfedokumentes verursachen einen Fehler. Grund: {0}", locale = "de") //
	}, msgnum = "I18N-E-0010", msgurl = "msgurl")
	/*!
	 * \var I18N_CANNOT_APPEND_HELPDOC_TEMPLATE_FOOTER
	 * \brief An error occurred when attempting to put the template footer in the help documents. Cause: {0}
	 */
	static final String										I18N_CANNOT_APPEND_HELPDOC_TEMPLATE_FOOTER		= "I18NAntTask.cannot-append-helpdoc-footer";

	@I18NMessages(value = { @I18NMessage("The following I18N resource bundles were placed in {0}"), //
			@I18NMessage(value = "The following I18N resource bundles were placed in {0}", locale = "en_UK", //
			explanation = "The following I18N resource bundles were placed in {0}" //
			), //
			@I18NMessage(value = "The following I18N resource bundles were placed in {0}", locale = "de", //
			explanation = "The following I18N resource bundles were placed in {0}" //
			) //
	}, msgnum = "I18N-I-0011", msgurl = "")
	/*!
	 * \var I18N_BUNDLES_PLACED_IN_DIR
	 * \brief The following I18N resource bundles were placed in {0}
	 */
	static final String										I18N_BUNDLES_PLACED_IN_DIR						= "I18NAntTask.bundles-placed-in";

	@I18NMessages(value = { @I18NMessage("-> {0}: {1} Messages"), //
			@I18NMessage(value = "-> {0}: {1} Messages", locale = "en_UK", //
			explanation = "-> {0}: {1} Messages" //
			), //
			@I18NMessage(value = "-> {0}: {1} Meldungen", locale = "de", //
			explanation = "Die Anzahl der gefundenen und verarbeiteten Meldungen wird hier angezeigt." //
			) //
	}, msgnum = "I18N-I-0012", msgurl = "")
	/*!
	 * \var I18N_BUNDLE_MESSAGE_COUNTER
	 * \brief -> {0}: {1} Meldungen
	 */
	static final String										I18N_BUNDLE_MESSAGE_COUNTER						= "I18NAntTask.bundle-message-counter";

	@I18NMessages(value = { @I18NMessage("The following help documents were placed in {0}"), //
			@I18NMessage(value = "The following help documents were placed in {0}", locale = "en_UK", //
			explanation = "The following help documents were placed in {0}" //
			), //
			@I18NMessage(value = "The following help documents were placed in {0}", locale = "de", //
			explanation = "The following help documents were placed in {0}" //
			) //
	}, msgnum = "msgnum", msgurl = "msgurl")
	/*!
	 * \var HELPDOCS_PLACED_IN_DIR
	 * \brief The following help documents were placed in {0}
	 */
	static final String										HELPDOCS_PLACED_IN_DIR							= "I18NAntTask.helpdocs-placed-in";

	@I18NMessages(value = { @I18NMessage("-> {0}{1}: {2} help doc items"), //
			@I18NMessage(value = "-> {0}{1}: {2} help doc items", locale = "en_UK", //
			explanation = "-> {0}{1}: {2} help doc items" //
			), //
			@I18NMessage(value = "-> {0}{1}: {2} help doc items", locale = "de", //
			explanation = "-> {0}{1}: {2} help doc items" //
			) //
	}, msgnum = "msgnum", msgurl = "")
	/*!
	 * \var HELPDOCS_MESSAGE_COUNTER
	 * \brief -> {0}{1}: {2} help doc items
	 */
	static final String										HELPDOCS_MESSAGE_COUNTER						= "I18NAntTask.helpdocs-message-counter";

	@I18NMessages(value = { @I18NMessage("[{0}] I18N resource bundles have been created with a total of [{1}] messages"), //
			@I18NMessage(value = "[{0}] I18N resource bundles have been created with a total of [{1}] messages", locale = "en_UK", //
			explanation = "[{0}] I18N resource bundles have been created with a total of [{1}] messages" //
			), //
			@I18NMessage(value = "[{0}] I18N resource bundles have been created with a total of [{1}] messages", locale = "de", //
			explanation = "[{0}] I18N resource bundles have been created with a total of [{1}] messages" //
			) //
	}, msgnum = "msgnum", msgurl = "")
	/*!
	 * \var I18N_NUM_BUNDLES_MESSAGES_CREATED
	 * \brief [{0}] I18N resource bundles have been created with a total of [{1}] messages
	 */
	static final String										I18N_NUM_BUNDLES_MESSAGES_CREATED				= "I18NAntTask.num-bundles-messages-created";

	@I18NMessages(value = { @I18NMessage("[{0}] help documents have been created with a total of [{1}] items"), //
			@I18NMessage(value = "[{0}] help documents have been created with a total of [{1}] items", locale = "en_UK", //
			explanation = "[{0}] help documents have been created with a total of [{1}] items" //
			), //
			@I18NMessage(value = "{0} Hilfe-Dokumente mit {1} Einträgen wurden erzeugt.", locale = "de", //
			explanation = "{0} Hilfe-Dokumente mit {1} Einträgen wurden erzeugt." //
			) //
	}, msgnum = "msgnum", msgurl = "")
	/*!
	 * \var NUM_HELPDOCS_ITEMS_CREATED
	 * \brief [{0}] help documents have been created with a total of [{1}] items
	 */
	static final String										NUM_HELPDOCS_ITEMS_CREATED						= "I18NAntTask.num-helpdocs-items-created";

	@I18NMessages(value = { @I18NMessage("Did not find any I18N messages so no I18N resource bundles were created"), //
			@I18NMessage(value = "Did not find any I18N messages so no I18N resource bundles were created", locale = "en_UK", //
			explanation = "Did not find any I18N messages so no I18N resource bundles were created" //
			), //
			@I18NMessage(value = "Keine Meldungen in der Klasse gefunden, deshalb keine Resource erzeugt", locale = "de", //
			explanation = "Keine Meldungen in der Klasse gefunden, deshalb keine Resource erzeugt" //
			) //
	}, msgnum = "msgnum", msgurl = "")
	/*!
	 * \var I18N_NO_BUNDLES_CREATED
	 * \brief Did not find any I18N messages so no I18N resource bundles were created
	 */
	static final String										I18N_NO_BUNDLES_CREATED							= "I18NAntTask.no-bundles-created";

	@I18NMessages(value = { @I18NMessage("[{0}] is annotated with resource bundle definition [{1}]"), //
			@I18NMessage(value = "[{0}] is annotated with resource bundle definition [{1}]", locale = "en_UK", //
			explanation = "[{0}] is annotated with resource bundle definition [{1}]" //
			), //
			@I18NMessage(value = "{0} ist dem resource bundle {1} zugeordnet", locale = "de", //
			explanation = "[{0}] is annotated with resource bundle definition [{1}]" //
			) //
	}, msgnum = "I18N-I-0000", msgurl = "")
	/*!
	 * \var I18N_CLASS_ANNOTATED_WITH_BUNDLE
	 * \brief [{0}] is annotated with resource bundle definition [{1}]
	 */
	static final String										I18N_CLASS_ANNOTATED_WITH_BUNDLE				= "I18NAntTask.class-annotated-with-bundle";

	@I18NMessages(value = { @I18NMessage("[{0}] does not define a resource bundle definition; will default to [{1}]"), //
			@I18NMessage(value = "[{0}] does not define a resource bundle definition; will default to [{1}]", locale = "en_UK", //
			explanation = "[{0}] does not define a resource bundle definition; will default to [{1}]" //
			), //
			@I18NMessage(value = "[{0}] does not define a resource bundle definition; will default to [{1}]", locale = "de", //
			explanation = "[{0}] does not define a resource bundle definition; will default to [{1}]" //
			) //
	}, msgnum = "I18N-I-0000", msgurl = "")
	/*!
	 * \var I18N_CLASS_NOT_ANNOTATED_WITH_BUNDLE
	 * \brief [{0}] does not define a resource bundle definition; will default to [{1}]
	 */
	static final String										I18N_CLASS_NOT_ANNOTATED_WITH_BUNDLE			= "I18NAntTask.class-not-annotated-with-bundle";

	@I18NMessages(value = { @I18NMessage("Field [{0}.{1}] is annotated with its own resource bundle definition [{2}]"), //
			@I18NMessage(value = "Field [{0}.{1}] is annotated with its own resource bundle definition [{2}]", locale = "en_UK", //
			explanation = "Field [{0}.{1}] is annotated with its own resource bundle definition [{2}]" //
			), //
			@I18NMessage(value = "Field [{0}.{1}] is annotated with its own resource bundle definition [{2}]", locale = "de", //
			explanation = "Field [{0}.{1}] is annotated with its own resource bundle definition [{2}]" //
			) //
	}, msgnum = "I18N-I-0000", msgurl = "")
	/*!
	 * \var I18N_FIELD_ANNOTATED_WITH_BUNDLE
	 * \brief Field [{0}.{1}] is annotated with its own resource bundle definition [{2}]
	 */
	static final String										I18N_FIELD_ANNOTATED_WITH_BUNDLE				= "I18NAntTask.field-annotated-with-bundle";

	@I18NMessages(value = { @I18NMessage("Field [{0}.{1}] has [{2}] I18N messages"), //
			@I18NMessage(value = "Field [{0}.{1}] has [{2}] I18N messages", locale = "en_UK", //
			explanation = "Field [{0}.{1}] has [{2}] I18N messages" //
			), //
			@I18NMessage(value = "Dem Feld [{0}.{1}] sind [{2}] Meldungen zugeordnet", locale = "de", //
			explanation = "Dem Feld [{0}.{1}] sind [{2}] Meldungen zugeordnet" //
			) //
	}, msgnum = "I18N-I-0000", msgurl = "msgurl")
	/*!
	 * \var I18N_FIELD_MESSAGE_COUNT
	 * \brief Field [{0}.{1}] has [{2}] I18N messages
	 */
	static final String										I18N_FIELD_MESSAGE_COUNT						= "I18NAntTask.field-message-count";

	@I18NMessages(value = { @I18NMessage("Field [{0}.{1}] is not of type String; cannot use I18N annotations on non-String fields."), //
			@I18NMessage(value = "Field [{0}.{1}] is not of type String; cannot use I18N annotations on non-String fields.", locale = "en_UK", //
			explanation = "Field [{0}.{1}] is not of type String; cannot use I18N annotations on non-String fields." //
			), //
			@I18NMessage(value = "Field [{0}.{1}] is not of type String; cannot use I18N annotations on non-String fields.", locale = "de", //
			explanation = "Field [{0}.{1}] is not of type String; cannot use I18N annotations on non-String fields." //
			) //
	}, msgnum = "I18N-E-0000", msgurl = "")
	/*!
	 * \var I18N_FIELD_NOT_STRING
	 * \brief Field [{0}.{1}] is not of type String; cannot use I18N annotations on non-String fields.
	 */
	static final String										I18N_FIELD_NOT_STRING							= "I18NAntTask.field-not-string";

	@I18NMessages(value = { @I18NMessage("Field [{0}.{1}] is not static final; cannot use I18N annotations on non-constant fields."), //
			@I18NMessage(value = "Field [{0}.{1}] is not static final; cannot use I18N annotations on non-constant fields.", locale = "en_UK", //
			explanation = "Field [{0}.{1}] is not static final; cannot use I18N annotations on non-constant fields." //
			), //
			@I18NMessage(value = "Das Feld [{0}.{1}] muß als 'static final' deklariert sein.", locale = "de", //
			explanation = "Das Feld [{0}.{1}] muß als 'static final' deklariert sein.\n" + "I18N-Annotationen sind nur bei konstanten Feldern möglich." //
			) //
	}, msgnum = "I18N-E-0000", msgurl = "")
	/*!
	 * \var I18N_FIELD_NOT_STATIC_FINAL
	 * \brief Field [{0}.{1}] is not static final; cannot use I18N annotations on non-constant fields.
	 */
	static final String										I18N_FIELD_NOT_STATIC_FINAL						= "I18NAntTask.field-not-static-final";

	@I18NMessages(value = { @I18NMessage("Resource bundle keys cannot be null or an empty string - must be at least 1 character long"), //
			@I18NMessage(value = "Resource bundle keys cannot be null or an empty string - must be at least 1 character long", locale = "en_UK", //
			explanation = "Resource bundle keys cannot be null or an empty string - must be at least 1 character long" //
			), //
			@I18NMessage(value = "Resource bundle keys cannot be null or an empty string - must be at least 1 character long", locale = "de", //
			explanation = "Resource bundle keys cannot be null or an empty string - must be at least 1 character long" //
			) //
	}, msgnum = "I18N-E-0000", msgurl = "")
	/*!
	 * \var I18N_FIELD_VALUE_CANNOT_BE_EMPTY
	 * \brief Resource bundle keys cannot be null or an empty string - must be at least 1 character long
	 */
	static final String										I18N_FIELD_VALUE_CANNOT_BE_EMPTY				= "I18NAntTask.field-cannot-be-empty";

	@I18NMessages(value = { @I18NMessage("Resource bundle keys cannot contain any ''='' characters [{0}]"), //
			@I18NMessage(value = "Resource bundle keys cannot contain any ''='' characters [{0}]", locale = "en_UK", //
			explanation = "Resource bundle keys cannot contain any ''='' characters [{0}]" //
			), //
			@I18NMessage(value = "Resource bundle keys cannot contain any ''='' characters [{0}]", locale = "de", //
			explanation = "Resource bundle keys cannot contain any ''='' characters [{0}]" //
			) //
	}, msgnum = "I18N-E-0000", msgurl = "")
	/*!
	 * \var I18N_NO_EQUALS_ALLOWED
	 * \brief Resource bundle keys cannot contain any ''='' characters [{0}]
	 */
	static final String										I18N_NO_EQUALS_ALLOWED							= "I18NAntTask.no-equals-allowed";

	@I18NMessages(value = { @I18NMessage("Failed to get the constant value of field [{0}.{1}] - Cause: {2}"), //
			@I18NMessage(value = "Failed to get the constant value of field [{0}.{1}] - Cause: {2}", locale = "en_UK", //
			explanation = "Failed to get the constant value of field [{0}.{1}] - Cause: {2}" //
			), //
			@I18NMessage(value = "Failed to get the constant value of field [{0}.{1}] - Cause: {2}", locale = "de", //
			explanation = "Failed to get the constant value of field [{0}.{1}] - Cause: {2}" //
			) //
	}, msgnum = "I18N-E-0000", msgurl = "")
	/*!
	 * \var I18N_FAILED_TO_GET_FIELD_VALUE
	 * \brief Failed to get the constant value of field [{0}.{1}] - Cause: {2}
	 */
	static final String										I18N_FAILED_TO_GET_FIELD_VALUE					= "I18NAntTask.failed-to-get-field-value";

	@I18NMessages(value = { @I18NMessage("Failed to write I18N message to resource bundle for field [{0}.{1}] - Cause: {2}"), //
			@I18NMessage(value = "Failed to write I18N message to resource bundle for field [{0}.{1}] - Cause: {2}", locale = "en_UK", //
			explanation = "Failed to write I18N message to resource bundle for field [{0}.{1}] - Cause: {2}" //
			), //
			@I18NMessage(value = "Failed to write I18N message to resource bundle for field [{0}.{1}] - Cause: {2}", locale = "de", //
			explanation = "Failed to write I18N message to resource bundle for field [{0}.{1}] - Cause: {2}" //
			) //
	}, msgnum = "I18N-E-0000", msgurl = "")
	/*!
	 * \var I18N_FAILED_TO_WRITE_TO_BUNDLE
	 * \brief Failed to write I18N message to resource bundle for field [{0}.{1}] - Cause: {2}
	 */
	static final String										I18N_FAILED_TO_WRITE_TO_BUNDLE					= "I18NAntTask.failed-to-write-to-bundle";

	@I18NMessages(value = { @I18NMessage("Failed to write help doc item for I18N message for field [{0}.{1}] - Cause: {2}"), //
			@I18NMessage(value = "Failed to write help doc item for I18N message for field [{0}.{1}] - Cause: {2}", locale = "en_UK", //
			explanation = "Failed to write help doc item for I18N message for field [{0}.{1}] - Cause: {2}" //
			), //
			@I18NMessage(value = "Failed to write help doc item for I18N message for field [{0}.{1}] - Cause: {2}", locale = "de", //
			explanation = "Failed to write help doc item for I18N message for field [{0}.{1}] - Cause: {2}" //
			) //
	}, msgnum = "I18N-E-0000", msgurl = "")
	/*!
	 * \var I18N_FAILED_TO_WRITE_HELPDOC_ITEM
	 * \brief Failed to write help doc item for I18N message for field [{0}.{1}] - Cause: {2}
	 */
	static final String										I18N_FAILED_TO_WRITE_HELPDOC_ITEM				= "I18NAntTask.failed-to-write-helpdoc-item";

	@I18NMessages(value = { @I18NMessage("VERIFY WARNING: The text for I18N message key [{0}] for field [{1}.{2}] is empty and will be ignored"), //
			@I18NMessage(value = "VERIFY WARNING: The text for I18N message key [{0}] for field [{1}.{2}] is empty and will be ignored", locale = "en_UK", //
			explanation = "VERIFY WARNING: The text for I18N message key [{0}] for field [{1}.{2}] is empty and will be ignored" //
			), //
			@I18NMessage(value = "VERIFY WARNING: The text for I18N message key [{0}] for field [{1}.{2}] is empty and will be ignored", locale = "de", //
			explanation = "VERIFY WARNING: The text for I18N message key [{0}] for field [{1}.{2}] is empty and will be ignored" //
			) //
	}, msgnum = "I18N-W-0000", msgurl = "")
	/*!
	 * \var I18N_MESSAGE_TEXT_IS_EMPTY
	 * \brief VERIFY WARNING: The text for I18N message key [{0}] for field [{1}.{2}] is empty and will be ignored
	 */
	static final String										I18N_MESSAGE_TEXT_IS_EMPTY						= "I18NAntTask.message-text-is-empty";

	@I18NMessages(value = { @I18NMessage("Writing to bundle file [{0}] the key=message of: {1}"), //
			@I18NMessage(value = "Writing to bundle file [{0}] the key=message of: {1}", locale = "en_UK", //
			explanation = "Writing to bundle file [{0}] the key=message of: {1}" //
			), //
			@I18NMessage(value = "Writing to bundle file [{0}] the key=message of: {1}", locale = "de", //
			explanation = "Writing to bundle file [{0}] the key=message of: {1}" //
			) //
	}, msgnum = "I18N-I-0000", msgurl = "")
	/*!
	 * \var I18N_WRITING_MESSAGE_TO_BUNDLE
	 * \brief Writing to bundle file [{0}] the key=message of: {1}
	 */
	static final String										I18N_WRITING_MESSAGE_TO_BUNDLE					= "I18NAntTask.writing-message-to-bundle";

	@I18NMessage("VERIFY WARNING: The I18N message [{0}] found on field [{1}.{2}] has a duplicate key. "
			+ "The message associated with this duplicate key is:\\n\\\n{3}")
	@I18NMessages(value = {
			@I18NMessage("VERIFY WARNING: The I18N message [{0}] found on field [{1}.{2}] has a duplicate key. "
					+ "The message associated with this duplicate key is:\\n\\\n{3}"), //
			@I18NMessage(value = "VERIFY WARNING: The I18N message [{0}] found on field [{1}.{2}] has a duplicate key. "
					+ "The message associated with this duplicate key is:\\n\\\n{3}", locale = "en_UK", //
			explanation = "VERIFY WARNING: The I18N message [{0}] found on field [{1}.{2}] has a duplicate key. "
					+ "The message associated with this duplicate key is:\\n\\\n{3}" //
			), //
			@I18NMessage(value = "VERIFY WARNING: The I18N message [{0}] found on field [{1}.{2}] has a duplicate key. "
					+ "The message associated with this duplicate key is:\\n\\\n{3}", locale = "de", //
			explanation = "VERIFY WARNING: The I18N message [{0}] found on field [{1}.{2}] has a duplicate key. "
					+ "The message associated with this duplicate key is:\\n\\\n{3}" //
			) //
	}, msgnum = "I18N-E-0000", msgurl = "")
	/*!
	 * \var I18N_VERIFY_WARNING_DUPLICATE
	 * \brief VERIFY WARNING: The I18N message [{0}] found on field [{1}.{2}] has a duplicate key. "
	+ "The message associated with this duplicate key is:\\n\\\n{3}
	 */
	static final String										I18N_VERIFY_WARNING_DUPLICATE					= "I18NAntTask.warning-duplicate";

	@I18NMessages(value = {
			@I18NMessage("VERIFY WARNING: The I18N message [{0}] found on field [{1}.{2}] has a text value "
					+ "that contains a newline break at character #[{3}] but is not preceded by " + "an escape backslash (\\\\):\\n\\\n" + "\"...{4}\"\\n\\\n"
					+ "Please make sure this is the value you really want:\\n\\\n{5}"), //
			@I18NMessage(value = "VERIFY WARNING: The I18N message [{0}] found on field [{1}.{2}] has a text value "
					+ "that contains a newline break at character #[{3}] but is not preceded by " + "an escape backslash (\\\\):\\n\\\n" + "\"...{4}\"\\n\\\n"
					+ "Please make sure this is the value you really want:\\n\\\n{5}", locale = "en_UK", //
			explanation = "VERIFY WARNING: The I18N message [{0}] found on field [{1}.{2}] has a text value "
					+ "that contains a newline break at character #[{3}] but is not preceded by " + "an escape backslash (\\\\):\\n\\\n" + "\"...{4}\"\\n\\\n"
					+ "Please make sure this is the value you really want:\\n\\\n{5}" //
			), //
			@I18NMessage(value = "VERIFY WARNING: The I18N message [{0}] found on field [{1}.{2}] has a text value "
					+ "that contains a newline break at character #[{3}] but is not preceded by " + "an escape backslash (\\\\):\\n\\\n" + "\"...{4}\"\\n\\\n"
					+ "Please make sure this is the value you really want:\\n\\\n{5}", locale = "de", //
			explanation = "VERIFY WARNING: The I18N message [{0}] found on field [{1}.{2}] has a text value "
					+ "that contains a newline break at character #[{3}] but is not preceded by " + "an escape backslash (\\\\):\\n\\\n" + "\"...{4}\"\\n\\\n"
					+ "Please make sure this is the value you really want:\\n\\\n{5}" //
			) //
	}, msgnum = "I18N-W-0000", msgurl = "")
	/*!
	 * \var I18N_VERIFY_WARNING_UNESCAPED_NEWLINE
	 * \brief VERIFY WARNING: The I18N message [{0}] found on field [{1}.{2}] has a text value "
				+ "that contains a newline break at character #[{3}] but is not preceded by " + "an escape backslash (\\\\):\\n\\\n" + "\"...{4}\"\\n\\\n"
				+ "Please make sure this is the value you really want:\\n\\\n{5}
	 */
	static final String										I18N_VERIFY_WARNING_UNESCAPED_NEWLINE			= "I18NAntTask.warning-unescaped-newline";

	@I18NMessage("VERIFY WARNING: The I18N message [{0}] found on field [{1}.{2}] has a text value "
			+ "that contains a quoted placeholder.  Check your use of single quotes and make " + "sure you are using them the way you want:\\n\\\n{3}")
	static final String										I18N_VERIFY_WARNING_QUOTED_PLACEHOLDER			= "I18NAntTask.warning-quoted-placeholder";

	@I18NMessages(value = { @I18NMessage("Cannot create output directory [{0}]"), //
			@I18NMessage(value = "Cannot create output directory [{0}]", locale = "en_UK"), //
			@I18NMessage(value = "Ausgabe-Verzeichnis kann nicht erstellt werden: [{0}]", locale = "de") //
	}, msgnum = "I18N-E-0000", msgurl = "msgurl")
	static final String										I18N_CANNOT_CREATE_OUTPUT_DIR					= "I18NAntTask.cannot-create-output-dir";

	@I18NMessages(value = { @I18NMessage("Output directory is not a true directory [{0}]"), //
			@I18NMessage(value = "Output directory is not a true directory [{0}]", locale = "en_UK", //
			explanation = "Output directory is not a true directory [{0}]" //
			), //
			@I18NMessage(value = "Das Ausgabe-Verzeichnis ist kein (gültiges) Verzeichnis", locale = "de", //
			explanation = "Output directory is not a true directory [{0}]" //
			) //
	}, msgnum = "I18N-E-0000", msgurl = "msgurl")
	/*!
	 * \var I18N_OUTPUT_DIR_NOT_A_DIR
	 * \brief Output directory is not a true directory [{0}]
	 */
	static final String										I18N_OUTPUT_DIR_NOT_A_DIR						= "I18NAntTask.output-dir-not-a-dir";

	@I18NMessages(value = { @I18NMessage("There are no class filesets defined"), //
			@I18NMessage(value = "There are no class filesets defined", locale = "en_UK", //
			explanation = "There are no class filesets defined" //
			), //
			@I18NMessage(value = "There are no class filesets defined", locale = "de", //
			explanation = "There are no class filesets defined" //
			) //
	}, msgnum = "I18N-E-0000", msgurl = "msgurl")
	/*!
	 * \var I18N_NO_CLASS_FILESETS
	 * \brief There are no class filesets defined
	 */
	static final String										I18N_NO_CLASS_FILESETS							= "I18NAntTask.no-class-filesets";

	@I18NMessages(value = { @I18NMessage("There is no classpath defined or the classpath is empty"), //
			@I18NMessage(value = "There is no classpath defined or the classpath is empty", locale = "en_UK", //
			explanation = "There is no classpath defined or the classpath is empty" //
			), //
			@I18NMessage(value = "There is no classpath defined or the classpath is empty", locale = "de", //
			explanation = "There is no classpath defined or the classpath is empty" //
			) //
	}, msgnum = "I18N-E-0000", msgurl = "msgurl")
	/*!
	 * \var I18N_EMPTY_CLASSPATH
	 * \brief There is no classpath defined or the classpath is empty
	 */
	static final String										I18N_EMPTY_CLASSPATH							= "I18NAntTask.empty-classpath";

	@I18NMessages(value = { @I18NMessage("Verbose mode is ON"), //
			@I18NMessage(value = "Verbose mode is ON", locale = "en_UK", //
			explanation = "Verbose mode is ON" //
			), //
			@I18NMessage(value = "Verbose mode is ON", locale = "de", //
			explanation = "Verbose mode is ON" //
			) //
	}, msgnum = "I18N-I-0000", msgurl = "")
	/*!
	 * \var I18N_VERBOSE_ON
	 * \brief Verbose mode is ON
	 */
	static final String										I18N_VERBOSE_ON									= "I18NAntTask.verbose-on";

	@I18NMessages(value = { @I18NMessage("Default locale is [{0}]"), //
			@I18NMessage(value = "Default locale is [{0}]", locale = "en_UK", //
			explanation = "Default locale is [{0}]" //
			), //
			@I18NMessage(value = "Das Standard-Gebietsschema (locale) ist [{0}]", locale = "de", //
			explanation = "Default locale is [{0}]" //
			) //
	}, msgnum = "I18N-I-0000", msgurl = "")
	/*!
	 * \var I18N_DEFAULT_LOCALE
	 * \brief Default locale is [{0}]
	 */
	static final String										I18N_DEFAULT_LOCALE								= "I18NAntTask.default-locale";

	@I18NMessages(value = { @I18NMessage("Verify mode is {0}"), //
			@I18NMessage(value = "Verify mode is {0}", locale = "en_UK", //
			explanation = "Verify mode is {0}" //
			), //
			@I18NMessage(value = "Prüf-Modus ist {0}", locale = "de", //
			explanation = "Verify mode is {0}" //
			) //
	}, msgnum = "I18N-I-0000", msgurl = "")
	/*!
	 * \var I18N_VERIFY_MODE
	 * \brief Verify mode is {0}
	 */
	static final String										I18N_VERIFY_MODE								= "I18NAntTask.verify-mode";

	@I18NMessages(value = {
			@I18NMessage("Append mode is {0}"), //
			@I18NMessage(value = "Append mode is {0}", locale = "en_UK", //
			explanation = "Append mode is {0}" //
			), //
			@I18NMessage(value = "Anfügemodus ist {0}", locale = "de", //
			explanation = "Wenn der Anfügemodus auf ON gesetzt ist, wird die Resource-Datei erweitert.\n"
					+ "andernfalls wird sie neu erstellt, der bisherige Inhalt wird überschrieben."//
			) //
	}, msgnum = "I18N-I-0000", msgurl = "")
	/*!
	 * \var I18N_APPEND_MODE
	 * \brief Append mode is {0}
	 */
	static final String										I18N_APPEND_MODE								= "I18NAntTask.append-mode";

	@I18NMessages(value = { @I18NMessage("Output directory is: [{0}]"), //
			@I18NMessage(value = "Output directory is: [{0}]", locale = "en_UK", //
			explanation = "Output directory is: [{0}]" //
			), //
			@I18NMessage(value = "Output directory is: [{0}]", locale = "de", //
			explanation = "Output directory is: [{0}]" //
			) //
	}, msgnum = "I18N-I-0000", msgurl = "")
	/*!
	 * \var I18N_OUTPUT_DIR
	 * \brief Output directory is: [{0}]
	 */
	static final String										I18N_OUTPUT_DIR									= "I18NAntTask.output-dir";

	@I18NMessages(value = { @I18NMessage("Java-Classpath: [{0}]"), //
			@I18NMessage(value = "Classpath: [{0}]", locale = "en_UK", //
			explanation = "Classpath: [{0}]" //
			), //
			@I18NMessage(value = "Java-Klassenpfad-Name: [{0}]", locale = "de", //
			explanation = "Classpath: [{0}]" //
			) //
	}, msgnum = "I18N-I-0000", msgurl = "")
	/*!
	 * \var I18N_CLASSPATH
	 * \brief Classpath: [{0}]
	 */
	static final String										I18N_CLASSPATH									= "I18NAntTask.classpath";

	@I18NMessages(value = { @I18NMessage("Help Doc: [{0}]"), //
			@I18NMessage(value = "Help Doc: [{0}]", locale = "en_UK", //
			explanation = "Help Doc: [{0}]" //
			), //
			@I18NMessage(value = "Help Doc: [{0}]", locale = "de", //
			explanation = "Help Doc: [{0}]" //
			) //
	}, msgnum = "I18N-I-0000", msgurl = "")
	/*!
	 * \var I18N_HELPDOC
	 * \brief Help Doc: [{0}]
	 */
	static final String										I18N_HELPDOC									= "I18NAntTask.helpdoc";

	@I18NMessages(value = { @I18NMessage("The <helpdoc> element has already been specified.  Cannot specify it more than once."), //
			@I18NMessage(value = "The <helpdoc> element has already been specified.  Cannot specify it more than once.", locale = "en_UK", //
			explanation = "The <helpdoc> element has already been specified.  Cannot specify it more than once." //
			), //
			@I18NMessage(value = "The <helpdoc> element has already been specified.  Cannot specify it more than once.", locale = "de", //
			explanation = "The <helpdoc> element has already been specified.  Cannot specify it more than once." //
			) //
	}, msgnum = "I18N-E-0000", msgurl = "")
	/*!
	 * \var I18N_HELPDOC_ALREADY_SPECIFIED
	 * \brief The <helpdoc> element has already been specified.  Cannot specify it more than once.
	 */
	static final String										I18N_HELPDOC_ALREADY_SPECIFIED					= "I18NAntTask.helpdoc-already-exists";

	@I18NMessages(value = { @I18NMessage("The <helpdoc> output directory [{0}] exists but is not actually a directory."), //
			@I18NMessage(value = "The <helpdoc> output directory [{0}] exists but is not actually a directory.", locale = "en_UK", //
			explanation = "The <helpdoc> output directory [{0}] exists but is not actually a directory." //
			), //
			@I18NMessage(value = "The <helpdoc> output directory [{0}] exists but is not actually a directory.", locale = "de", //
			explanation = "The <helpdoc> output directory [{0}] exists but is not actually a directory." //
			) //
	}, msgnum = "I18N-E-0000", msgurl = "msgurl")
	/*!
	 * \var I18N_HELPDOC_OUTPUT_DIR_NOT_A_DIR
	 * \brief The <helpdoc> output directory [{0}] exists but is not actually a directory.
	 */
	static final String										I18N_HELPDOC_OUTPUT_DIR_NOT_A_DIR				= "I18NAntTask.helpdoc-outputdir-not-dir";

	@I18NMessages(value = { @I18NMessage("The <helpdoc> output directory [{0}] cannot be created"), //
			@I18NMessage(value = "The <helpdoc> output directory [{0}] cannot be created", locale = "en_UK", //
			explanation = "The <helpdoc> output directory [{0}] cannot be created" //
			), //
			@I18NMessage(value = "The <helpdoc> output directory [{0}] cannot be created", locale = "de", //
			explanation = "The <helpdoc> output directory [{0}] cannot be created" //
			) //
	}, msgnum = "I18N-E-0000", msgurl = "")
	/*!
	 * \var I18N_HELPDOC_CANNOT_CREATE_OUTPUT_DIR
	 * \brief The <helpdoc> output directory [{0}] cannot be created
	 */
	static final String										I18N_HELPDOC_CANNOT_CREATE_OUTPUT_DIR			= "I18NAntTask.cannot-create-helpdoc-outputdir";

	@I18NMessages(value = { @I18NMessage("The <helpdoc> output directory is required but was not specified."), //
			@I18NMessage(value = "The <helpdoc> output directory is required but was not specified.", locale = "en_UK", //
			explanation = "The <helpdoc> output directory is required but was not specified." //
			), //
			@I18NMessage(value = "The <helpdoc> output directory is required but was not specified.", locale = "de", //
			explanation = "The <helpdoc> output directory is required but was not specified." //
			) //
	}, msgnum = "I18N-E-0000", msgurl = "")
	/*!
	 * \var I18N_HELPDOC_OUTPUT_DIR_IS_NULL
	 * \brief The <helpdoc> output directory is required but was not specified.
	 */
	static final String										I18N_HELPDOC_OUTPUT_DIR_IS_NULL					= "I18NAntTask.helpdoc-outputdir-missing";

	@I18NMessages(value = { @I18NMessage("The <helpdoc> template item file [{0}] exists but is actually a directory."), //
			@I18NMessage(value = "The <helpdoc> template item file [{0}] exists but is actually a directory.", locale = "en_UK", //
			explanation = "The <helpdoc> template item file [{0}] exists but is actually a directory." //
			), //
			@I18NMessage(value = "The <helpdoc> template item file [{0}] exists but is actually a directory.", locale = "de", //
			explanation = "The <helpdoc> template item file [{0}] exists but is actually a directory." //
			) //
	}, msgnum = "I18N-W-0000", msgurl = "")
	/*!
	 * \var I18N_HELPDOC_TEMPLATE_ITEM_FILE_NOT_A_FILE
	 * \brief The <helpdoc> template item file [{0}] exists but is actually a directory.
	 */
	static final String										I18N_HELPDOC_TEMPLATE_ITEM_FILE_NOT_A_FILE		= "I18NAntTask.helpdoc-templateitem-not-file";

	@I18NMessages(value = { @I18NMessage("The <helpdoc> template header file [{0}] exists but is actually a directory."), //
			@I18NMessage(value = "The <helpdoc> template header file [{0}] exists but is actually a directory.", locale = "en_UK", //
			explanation = "The <helpdoc> template header file [{0}] exists but is actually a directory." //
			), //
			@I18NMessage(value = "The <helpdoc> template header file [{0}] exists but is actually a directory.", locale = "de", //
			explanation = "The <helpdoc> template header file [{0}] exists but is actually a directory." //
			) //
	}, msgnum = "I18N-W_0000", msgurl = "")
	/*!
	 * \var I18N_HELPDOC_TEMPLATE_HEADER_FILE_NOT_A_FILE
	 * \brief The <helpdoc> template header file [{0}] exists but is actually a directory.
	 */
	static final String										I18N_HELPDOC_TEMPLATE_HEADER_FILE_NOT_A_FILE	= "I18NAntTask.helpdoc-templateheader-not-file";

	@I18NMessages(value = { @I18NMessage("The <helpdoc> template footer file [{0}] exists but is actually a directory."), //
			@I18NMessage(value = "The <helpdoc> template footer file [{0}] exists but is actually a directory.", locale = "en_UK", //
			explanation = "The <helpdoc> template footer file [{0}] exists but is actually a directory." //
			), //
			@I18NMessage(value = "The <helpdoc> template footer file [{0}] exists but is actually a directory.", locale = "de", //
			explanation = "The <helpdoc> template footer file [{0}] exists but is actually a directory." //
			) //
	}, msgnum = "I18N-W-0000", msgurl = "")
	/*!
	 * \var I18N_HELPDOC_TEMPLATE_FOOTER_FILE_NOT_A_FILE
	 * \brief The <helpdoc> template footer file [{0}] exists but is actually a directory.
	 */
	static final String										I18N_HELPDOC_TEMPLATE_FOOTER_FILE_NOT_A_FILE	= "I18NAntTask.helpdoc-templatefooter-not-file";

	@I18NMessages(value = { @I18NMessage("Writing helpdoc item [{0}] for bundle [{1}]"), //
			@I18NMessage(value = "Writing helpdoc item [{0}] for bundle [{1}]", locale = "en_UK", //
			explanation = "Writing helpdoc item [{0}] for bundle [{1}]" //
			), //
			@I18NMessage(value = "Writing helpdoc item [{0}] for bundle [{1}]", locale = "de", //
			explanation = "Writing helpdoc item [{0}] for bundle [{1}]" //
			) //
	}, msgnum = "I18N-I-0000", msgurl = "")
	/*!
	 * \var I18N_WRITING_HELPDOC_ITEM
	 * \brief Writing helpdoc item [{0}] for bundle [{1}]
	 */
	static final String										I18N_WRITING_HELPDOC_ITEM						= "I18NAntTask.writing-helpdoc-item";

	@I18NMessages(value = { @I18NMessage("Appending template footer to the helpdoc [{0}]"), //
			@I18NMessage(value = "Appending template footer to the helpdoc [{0}]", locale = "en_UK", //
			explanation = "Appending template footer to the helpdoc [{0}]" //
			), //
			@I18NMessage(value = "Appending template footer to the helpdoc [{0}]", locale = "de", //
			explanation = "Appending template footer to the helpdoc [{0}]" //
			) //
	}, msgnum = "I18N-I-0000", msgurl = "msgurl")
	/*!
	 * \var I18N_WRITING_HELPDOC_FOOTER
	 * \brief Appending template footer to the helpdoc [{0}]
	 */
	static final String										I18N_WRITING_HELPDOC_FOOTER						= "I18NAntTask.writing-helpdoc-footer";

	@I18NMessages(value = { @I18NMessage("The template footer contents is empty - not writing anything to the helpdoc."), //
			@I18NMessage(value = "The template footer contents is empty - not writing anything to the helpdoc.", locale = "en_UK", //
			explanation = "The template footer contents is empty - not writing anything to the helpdoc." //
			), //
			@I18NMessage(value = "The template footer contents is empty - not writing anything to the helpdoc.", locale = "de", //
			explanation = "The template footer contents is empty - not writing anything to the helpdoc." //
			) //
	}, msgnum = "I18N-W-0000", msgurl = "")
	/*!
	 * \var I18N_NOT_WRITING_EMPTY_HELPDOC_FOOTER
	 * \brief The template footer contents is empty - not writing anything to the helpdoc.
	 */
	static final String										I18N_NOT_WRITING_EMPTY_HELPDOC_FOOTER			= "I18NAntTask.not-writing-empty-helpdoc-footer";

	@I18NMessages(value = { @I18NMessage("Ignoring the message [{0}] because it does not have a help description."), //
			@I18NMessage(value = "Ignoring the message [{0}] because it does not have a help description.", locale = "en_UK", //
			explanation = "Ignoring the message [{0}] because it does not have a help description." //
			), //
			@I18NMessage(value = "Ignoring the message [{0}] because it does not have a help description.", locale = "de", //
			explanation = "Ignoring the message [{0}] because it does not have a help description." //
			) //
	}, msgnum = "I18N-I-0000", msgurl = "")
	/*!
	 * \var I18N_IGNORE_NON_HELP_MESSAGE
	 * \brief Ignoring the message [{0}] because it does not have a help description.
	 */
	static final String										I18N_IGNORE_NON_HELP_MESSAGE					= "I18NAntTask.ignore-non-help-message";

	@I18NMessages(value = { @I18NMessage("A default locale has been defined [{0}]"), //
			@I18NMessage(value = "A default locale has been defined [{0}]", locale = "en_UK", //
			explanation = "A default locale has been defined [{0}]" //
			), //
			@I18NMessage(value = "A default locale has been defined [{0}]", locale = "de", //
			explanation = "A default locale has been defined [{0}]" //
			) //
	}, msgnum = "I18N-I-0000", msgurl = "")
	/*!
	 * \var DEFAULT_LOCALE_DEFINED
	 * \brief A default locale has been defined [{0}]
	 */
	static final String										DEFAULT_LOCALE_DEFINED							= "I18NAntTask.default-locale-defined";

	@I18NMessages(value = {
			@I18NMessage("VERIFY WARNING: A default locale has not been defined.\n" + "If your users are in a locale which is not supported,\n"
					+ "they will not see any messages."), //
			@I18NMessage(value = "VERIFY WARNING: A default locale has not been defined.\n" + "If your users are in a locale which is not supported,\n"
					+ "they will not see any messages.", locale = "en_UK", //
			explanation = "VERIFY WARNING: A default locale has not been defined.\\n\\\n" + "If your users are in a locale which is not supported,\\n\\\n"
					+ "they will not see any messages." //
			), //
			@I18NMessage(value = "Eine Standard-locale ist nicht angegeben.\n" + "If your users are in a locale which is not supported,\n"
					+ "they will not see any messages.", locale = "de", //
			explanation = "VERIFY WARNING: A default locale has not been defined.\n" + "If your users are in a locale which is not supported,\n"
					+ "they will not see any messages." //
			) //
	}, msgnum = "I18N-W-0000", msgurl = "")
	/*!
	 * \var I18N_VERIFY_WARNING_DEFAULT_LOCALE_NOT_DEFINED
	 * \brief VERIFY WARNING: A default locale has not been defined.\\n\\\n" + "If your users are in a locale which is not supported,\\n\\\n"
				+ "they will not see any messages.
	 */
	static final String										I18N_VERIFY_WARNING_DEFAULT_LOCALE_NOT_DEFINED	= "I18NAntTask.warning-default-locale-not-defined";

	@I18NMessages(value = { @I18NMessage("Failed to copy resource bundle [{0}] to [{1}]. Cause: {2}"), //
			@I18NMessage(value = "Failed to copy resource bundle [{0}] to [{1}]. Cause: {2}", locale = "en_UK", //
			explanation = "Failed to copy resource bundle [{0}] to [{1}]. Cause: {2}" //
			), //
			@I18NMessage(value = "Failed to copy resource bundle [{0}] to [{1}]. Cause: {2}", locale = "de", //
			explanation = "Failed to copy resource bundle [{0}] to [{1}]. Cause: {2}" //
			) //
	}, msgnum = "I18N-E-0000", msgurl = "")
	/*!
	 * \var FAILED_TO_COPY_BUNDLE
	 * \brief Failed to copy resource bundle [{0}] to [{1}]. Cause: {2}
	 */
	static final String										FAILED_TO_COPY_BUNDLE							= "I18NAntTask.failed-to-copy-bundle";

	@I18NMessages(value = { @I18NMessage("[{0}] resource bundles have been created to support the default locale of [{1}]"), //
			@I18NMessage(value = "[{0}] resource bundles have been created to support the default locale of [{1}]", locale = "en_UK", //
			explanation = "[{0}] resource bundles have been created to support the default locale of [{1}]" //
			), //
			@I18NMessage(value = "[{0}] resource bundles have been created to support the default locale of [{1}]", locale = "de", //
			explanation = "[{0}] resource bundles have been created to support the default locale of [{1}]" //
			) //
	}, msgnum = "I18N-I-0000", msgurl = "")
	/*!
	 * \var DEFAULT_BUNDLES_CREATED
	 * \brief [{0}] resource bundles have been created to support the default locale of [{1}]
	 */
	static final String										DEFAULT_BUNDLES_CREATED							= "I18NAntTask.default-bundles-created";

	/**
	 * The file set containing the class files that will be examined for I18N annotations.
	 */
	protected List<FileSet>									m_classFilesets									= new ArrayList<FileSet>();

	/**
	 * Flag to indicate that this task should log some debug messages during processing.
	 */
	protected boolean										m_verbose										= false;

	/**
	 * Flag to indicate that this task should verify the correctness of the bundles and messages to be generated.
	 */
	protected boolean										m_verify										= true;

	/**
	 * If <code>true</code>, the messages will be appended to the end of the resource bundle files if they already
	 * exist. If <code>false</code>, the resource bundle files that do exist will be overwritten.
	 */
	private boolean											m_append										= false;

	/**
	 * If not <code>null</code>, this is the locale that will be used as the default locale for the application. If
	 * a resource bundle file is generated with this locale, that resource bundle file will be copied under a
	 * filename which is just the base bundle name, excluding a language/country/variant.
	 */
	private Locale											m_defaultLocale									= null;

	/**
	 * The base directory where the generate files will be written.
	 */
	protected File											m_outputDir;

	/**
	 * The classpath that is to be used to load in the classes in the class filesets.
	 */
	private Path											m_classpath;

	/**
	 * Used for verifying - this will contain all the keys generated in all the bundles so we can detect duplicates.
	 */
	private Map<ResourceBundleDefinition, List<String>>		m_generatedBundleMessageKeys;

	/**
	 * Map that contains counters of the number of messages in each generated bundle for reporting purposes at the
	 * end of the task's execution. This is also useful for determine what resource bundles were generated.
	 */
	private Map<ResourceBundleDefinition, MutableInteger>	m_bundleCounters;

	/**
	 * Map that contains counters of the number of help doc items in each generated help document for reporting
	 * purposes at the end of the task's execution.
	 */
	private Map<ResourceBundleDefinition, MutableInteger>	m_helpdocCounters;

	/**
	 * The optional help documentation information which indicates how to generate the help docs.
	 */
	private Helpdoc											m_helpdoc										= null;

	/**
	 * Adds a file set that contains class files this task will process.
	 *
	 * @param fs the new fileset
	 */
	public void addClassfileset(FileSet fs) {
		m_classFilesets.add(fs);
	}

	/**
	 * Adds a class fileset to the task given a reference to the fileset.
	 *
	 * @param reference the fileset reference
	 */
	public void setClassfilesetRef(Reference reference) {
		addClassfileset(createFileSet(reference));
	}

	/**
	 * Sets the verbose flag which, when <code>true</code>, will allow this task to emit debug messages.
	 *
	 * @param verbose
	 */
	public void setVerbose(boolean verbose) {
		m_verbose = verbose;
	}

	/**
	 * Sets the verify flag which, when <code>true</code>, will force this task to ensure the correctness of the
	 * bundles and messages being generated (such as checking to make sure duplicate resource keys do not exist in a
	 * bundle).
	 *
	 * @param verify
	 */
	public void setVerify(boolean verify) {
		m_verify = verify;
	}

	/**
	 * Sets the base output directory where the generated files will go.
	 *
	 * @param directory
	 */
	public void setOutputDir(File directory) {
		m_outputDir = directory;
	}

	/**
	 * If <code>true</code>, this task will append the messages it generates to resource bundle files that already
	 * exist. If <code>false</code>, any existing resource bundle will be overwritten.
	 *
	 * @param append
	 */
	public void setAppend(boolean append) {
		m_append = append;
	}

	/**
	 * Sets the locale that is to be considered the application's default locale. If an i18n message does not have a
	 * declared locale (in either its {@link I18NMessage} or its enclosing {@link I18NResourceBundle} annotation),
	 * this defines its locale.
	 *
	 * <p>In addition, if a resource bundle is generated with the given locale, that resource bundle properties file
	 * will be copied and the new file will be named with the base bundle file excluding the locale string. This
	 * allows the running application to fallback to this given default locale, if the user's locale is not
	 * supported (that is, the application does not have a localized resource bundle for the user's locale).</p>
	 *
	 * <p>Example: assume the given <code>default_locale</code> is "en". Assume also that this ANT task has generated
	 * a resource bundle with a base name of "my-messages" with the "en" locale (i.e. a resource bundle properties
	 * file named "my-messages_en.properties" was generated). Since this ANT task was told to assume the default
	 * locale is "en", it will copy "my-messages_en.properties" and name that copy "my-messages.properties". If a
	 * user runs the application and that user's locale does not have a resource bundle specific to it, this
	 * "my-messages.properties" file will be used to locate the i18n messages - which means the user will see English
	 * localized messages. If a default locale was not specified, the user would not see any localized messages for
	 * any language.</p>
	 *
	 * @param default_locale
	 */
	public void setDefaultLocale(String default_locale) {
		m_defaultLocale = parseLocaleString(default_locale);
	}

	/**
	* Set the classpath to be used when running the Java class
	*
	* @param s an Ant Path object containing the classpath.
	*/
	public void setClasspath(Path s) {
		createClasspath().append(s);
	}

	/**
	 * Classpath to use, by reference.
	 *
	 * @param r a reference to an existing classpath
	 */
	public void setClasspathRef(Reference r) {
		createClasspath().setRefid(r);
	}

	/**
	 * If it does not yet exist, this will create an empty classpath. If it was already created, it will be
	 * returned. This public method is required to support the nested classpath element.
	 *
	 * @return the classpath
	 */
	public Path createClasspath() {
		if (m_classpath == null) {
			m_classpath = new Path(getProject());
		}

		return m_classpath;
	}

	/**
	 * Adds the helpdoc element which indicates how to generate the help document. This is optional but only one can
	 * be specified.
	 *
	 * @param  helpdoc the configured helpdoc element
	 *
	 * @throws BuildException if the helpdoc element was specified more than once
	 */
	public void addConfiguredHelpdoc(Helpdoc helpdoc) throws BuildException {
		if (m_helpdoc != null) {
			throw new BuildException(getMsg(I18N_HELPDOC_ALREADY_SPECIFIED));
		}

		m_helpdoc = helpdoc;

		return;
	}

	/**
	 * @see org.apache.tools.ant.Task#execute()
	 */
	@SuppressWarnings("rawtypes")
	public void execute() throws BuildException {
		logMsg(I18N_START_PROCESSING);
		logMsgIfVerbose(I18N_ENCODING_NAMES, Charset.defaultCharset().name(), OUTPUT_CHARSET_NAME);

		validateTaskConfiguration();

		if (m_verify) {
			m_generatedBundleMessageKeys = new HashMap<ResourceBundleDefinition, List<String>>();
		}

		m_bundleCounters = new HashMap<ResourceBundleDefinition, MutableInteger>();
		m_helpdocCounters = new HashMap<ResourceBundleDefinition, MutableInteger>();

		AntClassLoader classloader = new AntClassLoader(this.getClass().getClassLoader(), getProject(), m_classpath, true);
		classloader.setIsolated(false);

		List<String> class_files = getClassesFromFileSets(m_classFilesets);
		for (String class_file : class_files) {
			try {
				System.out.println(class_file);
				class_file = getClassNameFromClassFile(class_file);
				if (class_file.endsWith(".xml") || class_file.endsWith(".xsl")) {
					
				}
				else {
					logMsgIfVerbose(I18N_PROCESSING_CLASS, class_file);
					Class clazz = classloader.loadClass(class_file);
					logMsgIfVerbose(I18N_LOADED_CLASS, clazz);

					if (processI18NClass(clazz) == false) {
						logMsgIfVerbose(I18N_CLASS_IGNORED, clazz);
					}
//				}
//				else {
//					logMsgIfVerbose(I18N_CLASS_IGNORED, class_file);					
				}
			}
			catch (Exception e) {
//				e.printStackTrace();
				logMsgIfVerbose(I18N_CANNOT_FIND_CLASS, class_file, e.getMessage());
				// throw new BuildException(getMsg(I18N_CANNOT_FIND_CLASS, class_file, e.getMessage()));
			}
		}

		// let's finish the help documents by appending the footer
		if (m_helpdoc != null) {
			try {
				for (Map.Entry<ResourceBundleDefinition, MutableInteger> entry : m_helpdocCounters.entrySet()) {
					writeHelpDocFooter(entry.getKey());
				}
			}
			catch (Exception e) {
				throw new BuildException(getMsg(I18N_CANNOT_APPEND_HELPDOC_TEMPLATE_FOOTER, e.getMessage()), e);
			}
		}

		// now make copies of the resource bundles so the default locale is used in non-supported locales
		if (m_defaultLocale != null) {
			logMsgIfVerbose(DEFAULT_LOCALE_DEFINED, m_defaultLocale);

			// to avoid concurrent modifications to m_bundleCounters, store in here the new, default bundles we create
			Map<ResourceBundleDefinition, MutableInteger> default_bundles = new HashMap<ResourceBundleDefinition, MutableInteger>();

			for (Map.Entry<ResourceBundleDefinition, MutableInteger> bundle_counter : m_bundleCounters.entrySet()) {
				ResourceBundleDefinition generated_bundle = bundle_counter.getKey();

				if (m_defaultLocale.equals(generated_bundle.locale)) {
					// generated bundle is one that contains messages in the same locale as the default locale.
					// copy that bundle ("baseName_locale.properties") to a default bundle ("baseName.properties")
					ResourceBundleDefinition default_bundle = new ResourceBundleDefinition();
					default_bundle.baseName = generated_bundle.baseName;
					default_bundle.locale = null;

					copyResourceBundle(generated_bundle, default_bundle);

					MutableInteger count = new MutableInteger();
					count.value = bundle_counter.getValue().value;

					default_bundles.put(default_bundle, count);
				}
			}

			// since we've created more bundles, let's add them to our running counter
			m_bundleCounters.putAll(default_bundles);

			logMsgIfVerbose(DEFAULT_BUNDLES_CREATED, default_bundles.size(), m_defaultLocale);
		}
		else {
			if (m_verify) {
				logMsg(I18N_VERIFY_WARNING_DEFAULT_LOCALE_NOT_DEFINED);
			}
		}

		// we are done generating the files - let's log a report of what we did
		if (m_bundleCounters.size() > 0) {
			if (m_verbose) {
				logMsg(I18N_BUNDLES_PLACED_IN_DIR, m_outputDir);
				for (Map.Entry<ResourceBundleDefinition, MutableInteger> entry : m_bundleCounters.entrySet()) {
					logMsg(I18N_BUNDLE_MESSAGE_COUNTER, entry.getKey(), entry.getValue().value);
				}

				if (m_helpdoc != null) {
					logMsg(HELPDOCS_PLACED_IN_DIR, m_helpdoc.getOutputdir());
					for (Map.Entry<ResourceBundleDefinition, MutableInteger> entry : m_helpdocCounters.entrySet()) {
						logMsg(HELPDOCS_MESSAGE_COUNTER, entry.getKey(), m_helpdoc.getOutputfileext(), entry.getValue().value);
					}
				}
			}
			else {
				int num_bundles = 0;
				int num_messages = 0;

				for (MutableInteger num_messages_in_bundle : m_bundleCounters.values()) {
					num_bundles++;
					num_messages += num_messages_in_bundle.value;
				}

				logMsg(I18N_NUM_BUNDLES_MESSAGES_CREATED, num_bundles, num_messages);

				if (m_helpdoc != null) {
					int num_helpdocs = 0;
					int num_helpdocs_items = 0;

					for (MutableInteger num_items_in_helpdoc : m_bundleCounters.values()) {
						num_helpdocs++;
						num_helpdocs_items += num_items_in_helpdoc.value;
					}

					logMsg(NUM_HELPDOCS_ITEMS_CREATED, num_helpdocs, num_helpdocs_items);
				}
			}
		}
		else {
			logMsg(I18N_NO_BUNDLES_CREATED);
		}

		return;
	}

	/**
	 * Processes the I18N annotations found on the given class and its fields. Returns <code>true</code> if the
	 * given class was annotated in such a way as to indicate it is internationalized and was processed by this
	 * task. <code>false</code> is returned if the class did not have any I18N annotations.
	 *
	 * @param  clazz the clazz to process
	 *
	 * @return <code>true</code> if this class was processed by this task, <code>false</code> if this class did not
	 *         have any I18N information annotated on it
	 *
	 * @throws BuildException if the class was inappropriately annotated with the I18N annotations
	 */

	private boolean processI18NClass(Class<?> clazz) throws BuildException {
		boolean was_internationalized = false;

		// This defines the default resource bundle - the no-arg constructor call here sets the defaults
		// to reasonable values; these can be used if a resource bundle annotation does not exist at the top class-level.
		ResourceBundleDefinition default_bundle_def = new ResourceBundleDefinition();

		// Process the top class-level resource bundle annotation, if one exists.
		// This will be the default resource bundle definition for all messages defined on the class's fields.
		if (clazz.isAnnotationPresent(I18NResourceBundle.class) == true) {
			I18NResourceBundle bundle_ann = clazz.getAnnotation(I18NResourceBundle.class);
			processI18NResourceBundleAnnotation(default_bundle_def, bundle_ann);
			was_internationalized = true;

			logMsgIfVerbose(I18N_CLASS_ANNOTATED_WITH_BUNDLE, clazz.getName(), default_bundle_def);
		}
		else {
			logMsgIfVerbose(I18N_CLASS_NOT_ANNOTATED_WITH_BUNDLE, clazz.getName(), default_bundle_def);
		}

		// now go through each field in the class and build our resource bundle based on the I18N messages we find
		Field[] fields = clazz.getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			List<I18NMessage> i18n_messages_list = new ArrayList<I18NMessage>();

			// see if this field overrides the default bundle definition
			ResourceBundleDefinition field_bundle;

			if (field.isAnnotationPresent(I18NResourceBundle.class) == true) {
				field_bundle = new ResourceBundleDefinition();
				processI18NResourceBundleAnnotation(field_bundle, field.getAnnotation(I18NResourceBundle.class));
				logMsgIfVerbose(I18N_FIELD_ANNOTATED_WITH_BUNDLE, clazz.getName(), field.getName(), field_bundle);
			}
			else {
				field_bundle = default_bundle_def;
			}

			// get all the I18N messages that are associated with this field (if there are any at all)
			if (field.isAnnotationPresent(I18NMessages.class)) {
				i18n_messages_list.addAll(Arrays.asList(field.getAnnotation(I18NMessages.class).value()));
			}

			if (field.isAnnotationPresent(I18NMessage.class)) {
				i18n_messages_list.add(field.getAnnotation(I18NMessage.class));
			}

			// if there are messages/bundles to be generated for this field, generate them now
			if (i18n_messages_list.size() > 0) {
				generateResourceBundle(clazz, field, field_bundle, i18n_messages_list);
				was_internationalized = true;
			}

			i18n_messages_list = null;
		}

		return was_internationalized;
	}

	/**
	 * This will generate the given I18N messages in the resource bundle defined by <code>bundle</code>.
	 *
	 * @param  clazz              the class that contains the given I18N-annotated field
	 * @param  field              the field that was I18N-annotated, whose value is the bundle key and must be a
	 *                            static final
	 * @param  bundle             the resource bundle where the messages should be written
	 * @param  i18n_messages_list the list of I18N messages that are associated with the field
	 *
	 * @throws BuildException if the bundles or its messages could not be generated for some reason
	 */
	private void generateResourceBundle(Class<?> clazz, Field field, ResourceBundleDefinition bundle, List<I18NMessage> i18n_messages_list)
			throws BuildException {
		logMsgIfVerbose(I18N_FIELD_MESSAGE_COUNT, clazz.getName(), field.getName(), i18n_messages_list.size());

		// make sure only static final String constants are being annotated
		if (!field.getType().equals(String.class)) {
			throw new BuildException(getMsg(I18N_FIELD_NOT_STRING, clazz.getName(), field.getName()));
		}

		if (!Modifier.isStatic(field.getModifiers()) || !Modifier.isFinal(field.getModifiers())) {
			throw new BuildException(getMsg(I18N_FIELD_NOT_STATIC_FINAL, clazz.getName(), field.getName()));
		}

		// get field's value - this is the resource bundle message key (the left side of the equals sign in the properties file)
		String bundle_key;

		try {
			bundle_key = (String) field.get(null);

			if ((bundle_key == null) || (bundle_key.length() == 0)) {
				throw new Exception(getMsg(I18N_FIELD_VALUE_CANNOT_BE_EMPTY));
			}

			if (bundle_key.indexOf('=') > -1) {
				throw new Exception(getMsg(I18N_NO_EQUALS_ALLOWED, bundle_key));
			}
		}
		catch (Exception e) {
			throw new BuildException(getMsg(I18N_FAILED_TO_GET_FIELD_VALUE, clazz.getName(), field.getName(), e.getMessage()));
		}

		// at this point we have the resource bundle key (bundle_key), the resource bundle file information
		// and the resource bundle messages themselves. Now we need to generate the resource bundle files.
		for (I18NMessage i18n_message : i18n_messages_list) {
			try {
				writeI18NMessage(clazz, field, bundle, bundle_key, i18n_message);
			}
			catch (BuildException be) {
				throw be;
			}
			catch (Exception e) {
				throw new BuildException(getMsg(I18N_FAILED_TO_WRITE_TO_BUNDLE, clazz.getName(), field.getName(), e.getMessage()), e);
			}
		}

		return;
	}

	/**
	 * Writes the I18N message to the resource bundle file. It will also generate the helpdoc item for the message,
	 * if help documentation has been enabled.
	 *
	 * <p>If the <code>i18n_message</code>'s text is <code>null</code> or an empty string, it will be ignored and
	 * this method will not write anything.</p>
	 *
	 * @param  clazz        the class that contains the given I18N-annotated field
	 * @param  field        the field that was annotated with the given I18N message
	 * @param  bundle       describes the resource bundle - use this to determine the filename of the bundle (note
	 *                      that the given I18N message may override the locale of this bundle with its own locale)
	 * @param  bundle_key   the resource bundle key of the message
	 * @param  i18n_message the actual message
	 *
	 * @throws BuildException if failed to write the message to the resource bundle file
	 * @throws IOException    if cannot open the bundle file for writing
	 */
	private void writeI18NMessage(Class<?> clazz, Field field, ResourceBundleDefinition bundle, String bundle_key, I18NMessage i18n_message)
			throws BuildException, IOException {
		String msg_text = i18n_message.value();
		String msg_locale = i18n_message.locale();

		// the I18N message may define what locale it is translated in; if it does not, assume the bundle locale
		ResourceBundleDefinition bundle_to_write_to = bundle;

		if ((msg_locale != null) && (msg_locale.length() > 0)) {
			bundle_to_write_to = new ResourceBundleDefinition();
			bundle_to_write_to.baseName = bundle.baseName;
			bundle_to_write_to.locale = parseLocaleString(msg_locale);
		}

		// make sure the message text is defined; if not, its probably a language translation placeholder - ignore it
		if ((msg_text == null) || (msg_text.trim().length() == 0)) {
			logMsg(I18N_MESSAGE_TEXT_IS_EMPTY, bundle_key, clazz.getName(), field.getName());
			return;
		}

		// if verifying, make sure a newline in text is followed by an escape \
		// since most times, a newline in a bundle message should be followed by a \ character to indicate the
		// message continues on the next line
		if (m_verify) {
			checkNewlines(clazz, field, bundle_key, msg_text);
			checkSingleQuotes(clazz, field, bundle_key, msg_text);
			checkForDuplicate(clazz, field, bundle_to_write_to, bundle_key, msg_text);
		}

		// update our counter to indicate we are adding one more message to the bundle
		// if this is the first time we've seen this bundle file, determine if we should append to it or not
		boolean append_bundle = true;
		MutableInteger old_bundle_count = m_bundleCounters.get(bundle_to_write_to);

		if (old_bundle_count == null) {
			old_bundle_count = new MutableInteger();
			m_bundleCounters.put(bundle_to_write_to, old_bundle_count);
			append_bundle = m_append;
		}

		old_bundle_count.value++;

		// now write the message to the bundle file
		File bundle_file = new File(m_outputDir, bundle_to_write_to + ".properties");
		String resource_key_and_value = bundle_key + "=" + msg_text;

		logMsgIfVerbose(I18N_WRITING_MESSAGE_TO_BUNDLE, bundle_file, resource_key_and_value);

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(bundle_file, append_bundle), OUTPUT_CHARSET_NAME));
		pw.println(resource_key_and_value);
		pw.close();

		// now write the help doc item for this message
		if (m_helpdoc != null) {
			// ignore this message if only those messages with help descriptions are to be
			// processed but this message does not have a help description
			if (!m_helpdoc.getHelpmessagesonly() || ((i18n_message.help() != null) && (i18n_message.help().length() > 0))) {
				boolean append_helpdoc = true;
				boolean include_header = false;
				MutableInteger old_helpdoc_count = m_helpdocCounters.get(bundle_to_write_to);

				if (old_helpdoc_count == null) {
					old_helpdoc_count = new MutableInteger();
					m_helpdocCounters.put(bundle_to_write_to, old_helpdoc_count);
					append_helpdoc = m_helpdoc.getAppend();
					include_header = true;
				}

				old_helpdoc_count.value++;

				try {
					writeHelpDocItem(clazz, field, bundle_to_write_to, bundle_key, i18n_message, append_helpdoc, include_header);
				}
				catch (Exception e) {
					throw new BuildException(getMsg(I18N_FAILED_TO_WRITE_HELPDOC_ITEM, clazz.getName(), field.getName(), e.getMessage()), e);
				}
			}
			else {
				logMsgIfVerbose(I18N_IGNORE_NON_HELP_MESSAGE, bundle_key);
			}
		}

		return;
	}

	/**
	 * Writes the help doc item for a I18N message.
	 *
	 * @param  clazz          the class that contains the given I18N-annotated field
	 * @param  field          the field that was annotated with the given I18N message
	 * @param  bundle         describes the resource bundle that is associated with the message
	 * @param  bundle_key     the resource bundle key of the message
	 * @param  i18n_message   the actual message (message text must not be <code>null</code>)
	 * @param  append         indicates if the help doc item should be appended to the help doc output file
	 * @param  include_header if <code>true</code>, the template header file contents should be output first
	 *
	 * @throws Exception if failed to write the help doc item to the help doc output file
	 */
	private void writeHelpDocItem(Class<?> clazz, Field field, ResourceBundleDefinition bundle, String bundle_key, I18NMessage i18n_message, boolean append,
			boolean include_header) throws Exception {
		logMsgIfVerbose(I18N_WRITING_HELPDOC_ITEM, bundle_key, bundle);

		String template_contents = m_helpdoc.getTemplateitemContents();
		template_contents = replaceAll(template_contents, Helpdoc.TEMPLATE_BUNDLE, bundle.toString());
		template_contents = replaceAll(template_contents, Helpdoc.TEMPLATE_KEY, bundle_key);
		template_contents = replaceAll(template_contents, Helpdoc.TEMPLATE_MESSAGE, i18n_message.value());

		if (i18n_message.help() != null) {
			template_contents = replaceAll(template_contents, Helpdoc.TEMPLATE_HELP, i18n_message.help());
		}

		File helpdoc_file = new File(m_helpdoc.getOutputdir(), bundle + m_helpdoc.getOutputfileext());
		PrintWriter pw = new PrintWriter(new FileWriter(helpdoc_file, append));

		if (include_header) {
			String header_contents = m_helpdoc.getTemplateheaderContents();
			header_contents = replaceAll(header_contents, Helpdoc.TEMPLATE_BUNDLE, bundle.toString());
			pw.print(header_contents);
		}

		pw.print(template_contents);
		pw.close();

		return;
	}

	/**
	 * Writes footer to the help doc associated with the given bundle.
	 *
	 * @param  bundle describes the resource bundle that is associated help doc
	 *
	 * @throws Exception if failed to write the footer to the help doc
	 */
	private void writeHelpDocFooter(ResourceBundleDefinition bundle) throws Exception {
		File helpdoc_file = new File(m_helpdoc.getOutputdir(), bundle + m_helpdoc.getOutputfileext());
		String footer_contents = m_helpdoc.getTemplatefooterContents();

		logMsgIfVerbose(I18N_WRITING_HELPDOC_FOOTER, helpdoc_file);

		if ((footer_contents != null) && (footer_contents.length() > 0)) {
			footer_contents = replaceAll(footer_contents, Helpdoc.TEMPLATE_BUNDLE, bundle.toString());
			PrintWriter pw = new PrintWriter(new FileWriter(helpdoc_file, true));
			pw.print(footer_contents);
			pw.close();
		}
		else {
			logMsgIfVerbose(I18N_NOT_WRITING_EMPTY_HELPDOC_FOOTER);
		}

		return;
	}

	/**
	 * This checks to see if the given <code>bundle_key</code> has been generated before in the same <code>
	 * bundle</code>. If so, a simple warning is logged but nothing else is done - the task should continue
	 * normally.
	 *
	 * @param clazz      the class that has the annotated field
	 * @param field      the annotated field where the I18N message being verified was found
	 * @param bundle     the resource bundle where the bundle key/text is to be stored
	 * @param bundle_key the resource bundle key that is being checked if its a duplicate
	 * @param text       the text message associated with the bundle key
	 */
	private void checkForDuplicate(Class<?> clazz, Field field, ResourceBundleDefinition bundle, String bundle_key, String text) {
		List<String> bundleKeyList = m_generatedBundleMessageKeys.get(bundle);
		if (bundleKeyList != null) {
			if (bundleKeyList.contains(bundle_key)) {
				logMsg(I18N_VERIFY_WARNING_DUPLICATE, bundle_key, clazz.getName(), field.getName(), text);
			}
		}
		else {
			bundleKeyList = new ArrayList<String>();
			m_generatedBundleMessageKeys.put(bundle, bundleKeyList);
		}

		bundleKeyList.add(bundle_key);
	}

	/**
	 * This verifies that, if the given text string contains a single quote character, that no placeholders appear
	 * within the quoted portion of the string. While it is valid to have a message look something like <code>"this
	 * is quoted '{0}'</code>", it is usually not going to be what the developer wants it to be. Usually, the only
	 * time "{#}" strings exist in the message is because they are meant to be placeholders that are to be replaced
	 * with values during runtime. However, having them within single quotes effectively creates them as literal
	 * "{#}" strings and they will not get replaced (see the Javadoc on <code>java.text.MessageFormat</code> for the
	 * syntax rules). This type of error usually occurs when an English-speaking developer uses apostrophes in the
	 * message, not realizing that the single apostrophe has this effect (e.g. <code>"you don't want to do this
	 * because the placeholder {0} is inside a single-quoted string and will not be replaced - so use the words DO
	 * NOT instead"</code>). If such a condition exists in the given text string, a simple warning is logged but
	 * nothing else is done - the task should continue normally.
	 *
	 * @param clazz      the class that has the annotated field
	 * @param field      the annotated field where the I18N message being verified was found
	 * @param bundle_key the resource bundle key whose text message is being verified
	 * @param text       the text that may have newlines that need to be verified
	 */
	private void checkSingleQuotes(Class<?> clazz, Field field, String bundle_key, String text) {
		int first_quote = text.indexOf('\'');
		int second_quote;
		boolean quoted_placeholder = false;

		while ((first_quote > -1) && !quoted_placeholder) {
			second_quote = text.indexOf('\'', first_quote + 1);

			// if the second quote is right after the first (''), this is a literal quote and will be skipped
			if ((first_quote + 1) == second_quote) {
				// go to the next single quote in the text
				first_quote = text.indexOf('\'', second_quote + 1);
			}
			else {
				// if there is a second quote and it isn't at the last character,
				// look for a placeholder between first and second
				if (second_quote > -1) {
					quoted_placeholder = text.substring(first_quote, second_quote).matches("(?s).*\\{[0-9].*");
					first_quote = text.indexOf('\'', second_quote + 1);
				}
				else {
					// there is no second quote or its at the last position, so just
					// look for a placeholder after the first quote
					quoted_placeholder = text.substring(first_quote).matches("(?s).*\\{[0-9].*");
					first_quote = -1;
				}
			}
		}

		if (quoted_placeholder) {
			logMsg(I18N_VERIFY_WARNING_QUOTED_PLACEHOLDER, bundle_key, clazz.getName(), field.getName(), text);
		}

		return;
	}

	/**
	 * This simply verifies that if the given text string has newline characters, that they are all preceded by an
	 * escape backslash. Most of the time, your resource bundle strings will want to escape newline characters so
	 * the bundle string is continued onto the next line. If an escape backslash is not found, a simple warning is
	 * logged but nothing else is done - the task should continue normally.
	 *
	 * @param clazz      the class that has the annotated field
	 * @param field      the annotated field where the I18N message being verified was found
	 * @param bundle_key the resource bundle key whose text message is being verified
	 * @param text       the text that may have newlines that need to be verified
	 */
	private void checkNewlines(Class<?> clazz, Field field, String bundle_key, String text) {
		int newline_index = -1;
		do {
			int next_index_increment = 1; // assumes we'll find a \n and not a \r\n
			int start_search_index = newline_index;

			newline_index = text.indexOf("\r\n", start_search_index);
			if (newline_index == -1) {
				newline_index = text.indexOf("\n", start_search_index);
			}
			else {
				next_index_increment = 2; // need to skip across the \r\n
			}

			if (newline_index > -1) {
				// the message text has a newline
				// let's verify that it has an end-of-line escape character, since that's usually what you want
				if ((newline_index == 0) || (text.charAt(newline_index - 1) != '\\')) {
					logMsg(I18N_VERIFY_WARNING_UNESCAPED_NEWLINE, bundle_key, clazz.getName(), field.getName(), newline_index,
							text.substring(((newline_index - 30) < 0) ? 0 : (newline_index - 30), newline_index), text);
					break;
				}

				newline_index += next_index_increment;
			}
		}
		while (newline_index > -1);
	}

	/**
	 * Given a resource bundle definition object and a resource bundle annotation, this will put the bundle
	 * annotation settings into the definition. If a annotation attribute is not specified (<code>null</code> or an
	 * empty string), the corresponding value in the given definition object is left as-is.
	 *
	 * @param definition
	 * @param annotation
	 */
	private void processI18NResourceBundleAnnotation(ResourceBundleDefinition definition, I18NResourceBundle annotation) {
		String ann_default_locale = annotation.defaultLocale();
		String ann_base_name = annotation.baseName();

		if ((ann_default_locale != null) && (ann_default_locale.length() > 0)) {
			definition.locale = parseLocaleString(ann_default_locale);
		}

		if ((ann_base_name != null) && (ann_base_name.length() > 0)) {
			
			definition.baseName = ann_base_name.replaceAll("\\.", "_");
		}

		return;
	}

	/**
	 * Parses the locale string, which must be in the form of "language_country_variant" where country and variant
	 * are optional, and returns that locale.
	 *
	 * @param  locale_str the locale string in the form of "language_country_variant"
	 *
	 * @return the locale
	 */
	private Locale parseLocaleString(String locale_str) {
		String[] locale_specs = locale_str.split("_");
		String language = (locale_specs.length > 0) ? locale_specs[0] : "";
		String country = (locale_specs.length > 1) ? locale_specs[1] : "";
		String variant = (locale_specs.length > 2) ? locale_specs[2] : "";

		return new Locale(language, country, variant);
	}

	/**
	 * Ensures that the information passed to the task is valid.
	 *
	 * @throws BuildException if the task configuration was invalid in some way
	 */
	private void validateTaskConfiguration() throws BuildException {
		if (m_outputDir == null) {
			m_outputDir = new File(System.getProperty("java.io.tmpdir"));
		}

		if (!m_outputDir.exists()) {
			if (!m_outputDir.mkdirs()) {
				throw new BuildException(getMsg(I18N_CANNOT_CREATE_OUTPUT_DIR, m_outputDir));
			}
		}

		if (!m_outputDir.isDirectory()) {
			throw new BuildException(getMsg(I18N_OUTPUT_DIR_NOT_A_DIR, m_outputDir));
		}

		if ((m_classFilesets == null) || (m_classFilesets.size() == 0)) {
			throw new BuildException(getMsg(I18N_NO_CLASS_FILESETS));
		}

		if ((m_classpath == null) || (m_classpath.size() == 0)) {
			throw new BuildException(getMsg(I18N_EMPTY_CLASSPATH));
		}

		if (m_helpdoc != null) {
			validateHelpdoc(m_helpdoc);
		}

		if (m_verbose) {
			logMsg(I18N_VERBOSE_ON);
			logMsg(I18N_DEFAULT_LOCALE, new ResourceBundleDefinition().locale);
			logMsg(I18N_VERIFY_MODE, (m_verify) ? "ON" : "OFF");
			logMsg(I18N_APPEND_MODE, (m_append) ? "ON" : "OFF");
			logMsg(I18N_OUTPUT_DIR, m_outputDir);
			logMsg(I18N_CLASSPATH, m_classpath);
			if (m_helpdoc != null) {
			logMsg(I18N_HELPDOC, m_helpdoc);
			}
		}

		return;
	}

	/**
	 * Ensure that the given helpdoc element has been configured properly.
	 *
	 * @param  helpdoc the helpdoc element to validate
	 *
	 * @throws BuildException if the helpdoc element is missing some required information or has invalid information
	 */
	private void validateHelpdoc(Helpdoc helpdoc) throws BuildException {
		if (helpdoc.getTemplateitem() != null) {
			File template = new File(helpdoc.getTemplateitem());

			if (template.exists() && template.isDirectory()) {
				throw new BuildException(getMsg(I18N_HELPDOC_TEMPLATE_ITEM_FILE_NOT_A_FILE, helpdoc.getTemplateitem()));
			}
		}

		if (helpdoc.getTemplateheader() != null) {
			File template = new File(helpdoc.getTemplateheader());

			if (template.exists() && template.isDirectory()) {
				throw new BuildException(getMsg(I18N_HELPDOC_TEMPLATE_HEADER_FILE_NOT_A_FILE, helpdoc.getTemplateheader()));
			}
		}

		if (helpdoc.getTemplatefooter() != null) {
			File template = new File(helpdoc.getTemplatefooter());

			if (template.exists() && template.isDirectory()) {
				throw new BuildException(getMsg(I18N_HELPDOC_TEMPLATE_FOOTER_FILE_NOT_A_FILE, helpdoc.getTemplatefooter()));
			}
		}

		if (helpdoc.getOutputdir() == null) {
			throw new BuildException(getMsg(I18N_HELPDOC_OUTPUT_DIR_IS_NULL));
		}

		File output = new File(helpdoc.getOutputdir());

		if (output.exists()) {
			if (!output.isDirectory()) {
				throw new BuildException(getMsg(I18N_HELPDOC_OUTPUT_DIR_NOT_A_DIR, helpdoc.getOutputdir()));
			}
		}
		else {
			output.mkdirs();

			if (!output.isDirectory()) {
				throw new BuildException(getMsg(I18N_HELPDOC_CANNOT_CREATE_OUTPUT_DIR, helpdoc.getOutputdir()));
			}
		}

		return;
	}

	/**
	 * Returns a list of class file string that are the included class files in the given filesets
	 *
	 * @param  filesets the filesets containing the class files
	 *
	 * @return list of path names to all the classes included in the given filesets
	 */
	private List<String> getClassesFromFileSets(List<FileSet> filesets) {
		List<String> class_files = new ArrayList<String>();

		for (Iterator<FileSet> iterator = filesets.iterator(); iterator.hasNext();) {
			FileSet fileset = iterator.next();
			DirectoryScanner ds = fileset.getDirectoryScanner(getProject());

			for (String file : ds.getIncludedFiles()) {
				class_files.add(file);
			}
		}

		return class_files;
	}

	/**
	 * Creates a fileset from a file set reference.
	 *
	 * @param  reference the reference to convert
	 *
	 * @return the reference's fileset
	 */
	private FileSet createFileSet(Reference reference) {
		FileSet fs = new FileSet();
		fs.setRefid(reference);
		fs.setProject(getProject());

		return fs;
	}

	/**
	 * Logs the message given a resource bundle key and placeholder replacement values.
	 *
	 * @param key    the bundle key that is associated with the message to log
	 * @param params values to replace the message's placeholders with
	 *
	 * @see   Msg
	 */
	private void logMsg(String key, Object... params) {
		log(getMsg(key, params));
	}

	/**
	 * Logs the given message but only if {@link #setVerbose(boolean) verbose mode} is enabled.
	 *
	 * @param key     the bundle key that is associated with the message to log if verbose is enabled
	 * @param varargs values to replace the message's placeholders with
	 */
	private void logMsgIfVerbose(String key, Object... varargs) {
		if (m_verbose) {
			log(getMsg(key, varargs));
		}
	}

	/**
	 * Gets an I18N message from this task's own resource bundle.
	 *
	 * @param  key     the bundle key that is associated with the message to log
	 * @param  varargs values to replace the message's placeholders with
	 *
	 * @return the message in the VM's default locale
	 */
	private String getMsg(String key, Object... varargs) {
		return Msg.createMsg(BUNDLE_BASE_NAME, key, varargs).toString();
	}

	/**
	 * Returns the fully qualified name (in other words, dot notation) of the class that is specified by the given
	 * class file name.
	 *
	 * @param  class_file the class file name (includes path information with file separators and .class extension)
	 *
	 * @return the fully qualified class name
	 */
	private String getClassNameFromClassFile(String class_file) {
		class_file = class_file.replace(File.separatorChar, '.');
		class_file = class_file.replaceAll("\\.class$", "");
		return class_file;
	}

	/**
	 * Replaces all substrings found in the target string with the replacement string.
	 *
	 * @param  target_str           the string that has one or more substrings that are to be replaced with the
	 *                              replacement string
	 * @param  substring_to_replace the substring to find in the target - all instances of this substring in the
	 *                              target will be replaced
	 * @param  replacement          the string that replaces all the substrings found in the target string
	 *
	 * @return the target string with all its substrings replaced
	 */
	private String replaceAll(String target_str, String substring_to_replace, String replacement) {
		return target_str.replaceAll(Pattern.quote(substring_to_replace), Matcher.quoteReplacement(replacement));
	}

	/**
	 * Copies a resource bundle properties file.
	 *
	 * @param  from identifies the resource bundle that is to be copied
	 * @param  to   identifies the new resource bundle whose contents will be the same as the <code>from</code>
	 *              bundle
	 *
	 * @throws BuildException if failed to copy the resource bundle properties file
	 */
	private void copyResourceBundle(ResourceBundleDefinition from, ResourceBundleDefinition to) throws BuildException {
		File from_file = new File(m_outputDir, from + ".properties");
		File to_file = new File(m_outputDir, to + ".properties");
		InputStream from_stream = null;
		OutputStream to_stream = null;

		try {
			int buffer_size = 32768;

			from_stream = new BufferedInputStream(new FileInputStream(from_file), buffer_size);
			to_stream = new BufferedOutputStream(new FileOutputStream(to_file), buffer_size);

			byte[] buffer = new byte[buffer_size];

			for (int bytes = from_stream.read(buffer); bytes != -1; bytes = from_stream.read(buffer)) {
				to_stream.write(buffer, 0, bytes);
			}

			to_stream.flush();
		}
		catch (Exception e) {
			throw new BuildException(getMsg(FAILED_TO_COPY_BUNDLE, from_file, to_file, e.getMessage()), e);
		}
		finally {
			try {
				if (to_stream != null) {
					to_stream.close();
				}
			}
			catch (Exception e) {
			}

			try {
				if (from_stream != null) {
					from_stream.close();
				}
			}
			catch (Exception e) {
			}
		}

		return;
	}

	/**
	 * This is a simple object to encapsulate a resource bundle's base name and locale.
	 */
	private class ResourceBundleDefinition {
		String	baseName	= "messages";
		Locale	locale		= (m_defaultLocale != null) ? m_defaultLocale : Locale.getDefault();

		/**
		 * @see Object#toString()
		 */
		public String toString() {
			return baseName + ((locale != null) ? ("_" + locale) : (""));
		}

		/**
		 * @see Object#equals(Object)
		 */
		public boolean equals(Object obj) {
			return this.toString().equals(obj.toString());
		}

		/**
		 * @see Object#hashCode()
		 */
		public int hashCode() {
			return this.toString().hashCode();
		}
	}

	/**
	 * A simple mutable integer that we use as values in our counter map - this helps us avoid doing alot of
	 * creating of temporary or short-lived integer objects.
	 */
	private class MutableInteger {
		int	value	= 0;
	}
}
