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
package sos.util;

import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Title: SOSResourceBundle <br>
 * Description: <br>
 * Copyright: Copyright (c) 2005 <br>
 * Company: SOS Berlin GmbH <br>
 * 
 * @author <a href="mailto:robert.ehrlich@sos-berlin.com">Robert Ehrlich </a>
 * @version $Id$
 */
public class SOSResourceBundle {

    /** ResourceBundle's die schon einmal angefordert wurden */
    private final static Hashtable bundles = new Hashtable();

    /** aktuelle ResourceBundle mit Nachrichten */
    private ResourceBundle bundle = null;

    /** aktuelle Locale */
    private Locale locale = Locale.UK;

    
    /**
     * Konstruktor
     * 
     */
    public SOSResourceBundle(){
        this(null);
    }
    
    /**
     * 
     * @param locale
     */
    public SOSResourceBundle(Locale locale){
        
        if(locale != null){
            this.locale = locale;
        }
    
    }
    
    /**
     * ResourceBundle f�r die Nachrichten definieren.
     * 
     * @param name
     *            Name der Nachrichtendatei Der Name soll wie folgt aussehen :
     *            <package name> <datei name ohne .properties und Sprache> z.B.
     *            "sos.messages.Message"
     * 
     * @throws Exception
     *  
     */
    public void setBundle(String name) throws Exception {
        this.setBundle(name, null);
    }

    /**
     * ResourceBundle f�r die Nachrichten definieren.
     * 
     * @param name
     *            Name der Nachrichtendatei Der Name soll wie folgt aussehen :
     *            <package name> <datei name ohne .properties und Sprache> z.B.
     *            "sos.util.Messages"
     * @param locale
     *            Locale
     * 
     * @throws Exception
     *  
     */
    public void setBundle(String name, Locale locale) throws Exception {

        if (locale != null) {
            this.locale = locale;
        }
        
        if (this.locale == null) {
            this.locale = Locale.getDefault();
        }
        

        
        String bundleKey = name + "_" + this.locale.toString();

        if (bundles.containsKey(bundleKey)) {
            this.bundle = (ResourceBundle) bundles.get(bundleKey);
        } 
        else {
            try {
                this.bundle = ResourceBundle.getBundle(name, this.locale);
                bundles.put(bundleKey, bundle);
            } catch (MissingResourceException exc) {
                throw new Exception(
                        "Error while setting ResourceBundle. Can not find : "
                                + name);
            }
        }
    }

    /**
     * Liefert aktuelle ResourceBundle mit Nachrichten
     * 
     * @return Returns the bundle.
     */
    public ResourceBundle getBundle() {
        return this.bundle;
    }

    /**
     * Liefert aktuelle Locale
     * 
     * @return Returns the locale.
     */
    public Locale getLocale() {
        return this.locale;
    }
    
    /**
     * Nachricht lesen
     * 
     * @param name
     *            Schl�sselname in der .properties Datei
     * @return message oder message key(wenn nicht gefunden)
     * @throws Exception
     */
    public String getMessage(String name) throws Exception {
        try {
            return bundle.getString(name);
        } catch (MissingResourceException exc) {
            //throw new Exception("Cannot find message named : "+name);
            return name;
        }

    }

    /**
     * Nachricht lesen und den Platzhalter {0} in der .properties Datei durch
     * arg ersetzen
     * 
     * @param name
     *            Schl�sselname in der .properties Datei
     * @param arg
     *            Wert f�r den Platzhalter
     * @return message oder message key(wenn nicht gefunden)
     * @throws Exception
     */
    public String getMessage(String name, String arg) throws Exception {
        return this.getMessage(name, new String[] { arg});
    }

    /**
     * Nachricht lesen und den Platzhalter {0} und {1} in der .properties Datei
     * durch arg0 und arg1 ersetzen
     * 
     * @param name
     *            Schl�sselname in der .properties Datei
     * @param arg0
     *            Wert f�r den Platzhalter {0}
     * @param arg1
     *            Wert f�r den Platzhalter {1}
     * @return message oder message key(wenn nicht gefunden)
     * @throws Exception
     */
    public String getMessage(String name, String arg0, String arg1)
            throws Exception {
        return this.getMessage(name, new String[] { arg0, arg1});
    }

    /**
     * Nachricht lesen und die Platzhalter {0},{1} .... in der .properties Datei
     * durch args ersetzen
     * 
     * @param name
     *            Schl�sselname in der .properties Datei
     * @param args
     *            Werte f�r die Platzhalter
     * @return message oder message key(wenn nicht gefunden)
     * @throws Exception
     */
    public String getMessage(String name, String[] args) throws Exception {
        try {
            String pattern = bundle.getString(name);
            return MessageFormat.format(pattern, args);
        } catch (MissingResourceException exc) {
            //throw new Exception("Can not find message named : "+name);
            return name;
        }
    }

}
