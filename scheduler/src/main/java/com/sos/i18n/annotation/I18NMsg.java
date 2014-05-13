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
package com.sos.i18n.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a field as an I18N resource bundle message key. You define the locale the message is in and that
 * message's text value translated in the locale. If a locale is not specified, it will be in the default locale
 * specified in its {@link I18NResourceBundle resource bundle}. All fields annotated with this annotation must be
 * within a scope of a {@link I18NResourceBundle resource bundle} - if one is not defined, a default resource bundle
 * definition should be assumed by the annotation processor (e.g. an appropriate default resource bundle definition
 * would be a default base bundle name of "messages" with a default locale being the VM default locale). You can
 * annotate the same field with multiple {@link I18NMsg} annotations via {@link I18NMessages} if you want to
 * define multiple translations for a single message key.
 *
 * <p>The additional "help" attribute can be used when generating help documentation - it is a string that can
 * further describe the message or explain under the conditions by which this message appears and may also include
 * things, as an example, like correction procedures (if the message is an error message).</p>
 *
 * @author <a href="mailto:jmazzitelli@users.sourceforge.net">John Mazzitelli</a>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface I18NMsg {

	/**
	 * The actual message text in the annotation's {@link #locale()}.
	 * This is the value that is stored in the resource bundle properties file (on the right side of the equals sign).
	 *
	 * @return the message text
	 */
	String value() default "";

	/**
	 * The locale of the {@link #value() message text}.  If this is not specified, the {@link I18NResourceBundle#defaultLocale()}
	 * for the resource bundle annotation in scope is used.
	 *
	 * @return the message locale
	 */
	String locale() default "";

	/**
	 * An optional help string that can further describe this message - it can explain under what conditions this message will appear and
	 * (if this message is an error message) can include correction procedures that can be followed to fix the error.  This attribute
	 * is merely for informational purposes only and is generally only used when generating help documentation.
	 *
	 * @return a help description that further explains the meaning of this message
	 */
	String help() default "";

	/**
	 * \fn explanation
	 * \brief explanation
	 * 
	 * \details
	 *
	 * \return String
	 *
	 * @return a short explanation, why this message occured
	 */
	String explanation() default "";

}
