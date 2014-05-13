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
package com.sos.i18n.logging.jdk;

import com.sos.i18n.LoggerLocale;
import com.sos.i18n.Msg;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Similar in functionality to the {@link JDKLogMsg} utility class, this provides the ability to log messages that
 * are retrieved from a resource bundle with the additional capability of being able to set the base bundle name
 * and locale that this logger will use to log the message. If the locale is not specified or <code>null</code>,
 * the {@link LoggerLocale} will be used to determine what locale will be used.
 *
 * @author  <a href="mailto:jmazzitelli@users.sourceforge.net">John Mazzitelli</a>
 * @version $Revision: 1.1 $
 */
public class JDKLogger
   extends com.sos.i18n.Logger
{
   private Logger m_log;

   /**
    * @see com.sos.i18n.Logger#Logger(String, Msg.BundleBaseName, Locale)
    */
   public JDKLogger( String             name,
                     Msg.BundleBaseName basename,
                     Locale             locale )
   {
      super( name, basename, locale );
   }

   /**
    * @see com.sos.i18n.Logger#Logger(Class, Msg.BundleBaseName, Locale)
    */
   public JDKLogger( Class              clazz,
                     Msg.BundleBaseName basename,
                     Locale             locale )
   {
      super( clazz, basename, locale );
   }

   /**
    * @see com.sos.i18n.Logger#Logger(String, Msg.BundleBaseName)
    */
   public JDKLogger( String             name,
                     Msg.BundleBaseName basename )
   {
      super( name, basename );
   }

   /**
    * @see com.sos.i18n.Logger#Logger(Class, Msg.BundleBaseName)
    */
   public JDKLogger( Class              clazz,
                     Msg.BundleBaseName basename )
   {
      super( clazz, basename );
   }

   /**
    * @see com.sos.i18n.Logger#Logger(String, Locale)
    */
   public JDKLogger( String name,
                     Locale locale )
   {
      super( name, locale );
   }

   /**
    * @see com.sos.i18n.Logger#Logger(Class, Locale)
    */
   public JDKLogger( Class  clazz,
                     Locale locale )
   {
      super( clazz, locale );
   }

   /**
    * @see com.sos.i18n.Logger#Logger(String)
    */
   public JDKLogger( String name )
   {
      super( name );
   }

   /**
    * @see com.sos.i18n.Logger#Logger(Class)
    */
   public JDKLogger( Class clazz )
   {
      super( clazz );
   }

   /**
    * @see com.sos.i18n.Logger#isFatalEnabled()
    */
   public boolean isFatalEnabled()
   {
      return m_log.isLoggable(Level.SEVERE);
   }

   /**
    * @see com.sos.i18n.Logger#isErrorEnabled()
    */
   public boolean isErrorEnabled()
   {
      return m_log.isLoggable(Level.SEVERE);
   }

   /**
    * @see com.sos.i18n.Logger#isWarnEnabled()
    */
   public boolean isWarnEnabled()
   {
      return m_log.isLoggable(Level.WARNING);
   }

   /**
    * @see com.sos.i18n.Logger#isInfoEnabled()
    */
   public boolean isInfoEnabled()
   {
      return m_log.isLoggable(Level.INFO);
   }

   /**
    * @see com.sos.i18n.Logger#isDebugEnabled()
    */
   public boolean isDebugEnabled()
   {
      return m_log.isLoggable(Level.FINE);
   }

   /**
    * @see com.sos.i18n.Logger#isTraceEnabled()
    */
   public boolean isTraceEnabled()
   {
      return m_log.isLoggable(Level.FINEST);
   }

   /**
    * Calls {@link JDKLogMsg#fatal(Logger, Throwable, Msg.BundleBaseName, Locale, String, Object[])} to log the
    * message.
    *
    * @see com.sos.i18n.Logger#fatal(Throwable, String, Object[])
    */
   public Msg fatal( Throwable throwable,
                     String    key,
                     Object... varargs )
   {
      return JDKLogMsg.fatal( m_log, throwable, getBaseBundleName(), getLocale(), key, varargs );
   }

   /**
    * Calls {@link JDKLogMsg#fatal(Logger, Msg.BundleBaseName, Locale, String, Object[])} to log the message.
    *
    * @see com.sos.i18n.Logger#fatal(String, Object[])
    */
   public Msg fatal( String    key,
                     Object... varargs )
   {
      return JDKLogMsg.fatal( m_log, getBaseBundleName(), getLocale(), key, varargs );
   }

   /**
    * Calls {@link JDKLogMsg#error(Logger, Throwable, Msg.BundleBaseName, Locale, String, Object[])} to log the
    * message.
    *
    * @see com.sos.i18n.Logger#error(Throwable, String, Object[])
    */
   public Msg error( Throwable throwable,
                     String    key,
                     Object... varargs )
   {
      return JDKLogMsg.error( m_log, throwable, getBaseBundleName(), getLocale(), key, varargs );
   }

   /**
    * Calls {@link JDKLogMsg#error(Logger, Msg.BundleBaseName, Locale, String, Object[])} to log the message.
    *
    * @see com.sos.i18n.Logger#error(String, Object[])
    */
   public Msg error( String    key,
                     Object... varargs )
   {
      return JDKLogMsg.error( m_log, getBaseBundleName(), getLocale(), key, varargs );
   }

   /**
    * Calls {@link JDKLogMsg#warn(Logger, Throwable, Msg.BundleBaseName, Locale, String, Object[])} to log the
    * message.
    *
    * @see com.sos.i18n.Logger#warn(Throwable, String, Object[])
    */
   public Msg warn( Throwable throwable,
                    String    key,
                    Object... varargs )
   {
      return JDKLogMsg.warn( m_log, throwable, getBaseBundleName(), getLocale(), key, varargs );
   }

   /**
    * Calls {@link JDKLogMsg#warn(Logger, Msg.BundleBaseName, Locale, String, Object[])} to log the message.
    *
    * @see com.sos.i18n.Logger#warn(String, Object[])
    */
   public Msg warn( String    key,
                    Object... varargs )
   {
      return JDKLogMsg.warn( m_log, getBaseBundleName(), getLocale(), key, varargs );
   }

   /**
    * Calls {@link JDKLogMsg#info(Logger, Throwable, Msg.BundleBaseName, Locale, String, Object[])} to log the
    * message.
    *
    * @see com.sos.i18n.Logger#info(Throwable, String, Object[])
    */
   public Msg info( Throwable throwable,
                    String    key,
                    Object... varargs )
   {
      return JDKLogMsg.info( m_log, throwable, getBaseBundleName(), getLocale(), key, varargs );
   }

   /**
    * Calls {@link JDKLogMsg#info(Logger, Msg.BundleBaseName, Locale, String, Object[])} to log the message.
    *
    * @see com.sos.i18n.Logger#info(String, Object[])
    */
   public Msg info( String    key,
                    Object... varargs )
   {
      return JDKLogMsg.info( m_log, getBaseBundleName(), getLocale(), key, varargs );
   }

   /**
    * Calls {@link JDKLogMsg#debug(Logger, Throwable, Msg.BundleBaseName, Locale, String, Object[])} to log the
    * message.
    *
    * @see com.sos.i18n.Logger#debug(Throwable, String, Object[])
    */
   public Msg debug( Throwable throwable,
                     String    key,
                     Object... varargs )
   {
      return JDKLogMsg.debug( m_log, throwable, getBaseBundleName(), getLocale(), key, varargs );
   }

   /**
    * Calls {@link JDKLogMsg#debug(Logger, Msg.BundleBaseName, Locale, String, Object[])} to log the message.
    *
    * @see com.sos.i18n.Logger#debug(String, Object[])
    */
   public Msg debug( String    key,
                     Object... varargs )
   {
      return JDKLogMsg.debug( m_log, getBaseBundleName(), getLocale(), key, varargs );
   }

   /**
    * Calls {@link JDKLogMsg#trace(Logger, Throwable, Msg.BundleBaseName, Locale, String, Object[])} to log the
    * message.
    *
    * @see com.sos.i18n.Logger#trace(Throwable, String, Object[])
    */
   public Msg trace( Throwable throwable,
                     String    key,
                     Object... varargs )
   {
      return JDKLogMsg.trace( m_log, throwable, getBaseBundleName(), getLocale(), key, varargs );
   }

   /**
    * Calls {@link JDKLogMsg#trace(Logger, Msg.BundleBaseName, Locale, String, Object[])} to log the message.
    *
    * @see com.sos.i18n.Logger#trace(String, Object[])
    */
   public Msg trace( String    key,
                     Object... varargs )
   {
      return JDKLogMsg.trace( m_log, getBaseBundleName(), getLocale(), key, varargs );
   }

   /**
    * Creates the JDK-Logging log object.
    *
    * @see com.sos.i18n.Logger#createLogObject(Class)
    */
   protected void createLogObject( Class clazz )
   {
      m_log = Logger.getLogger( clazz.getName() );
   }

   /**
    * Creates the JDK-Logging log object.
    *
    * @see com.sos.i18n.Logger#createLogObject(String)
    */
   protected void createLogObject( String name )
   {
      m_log = Logger.getLogger( name );
   }
}
