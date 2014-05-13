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
package sos.scheduler.editor.app;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.sos.JSHelper.Basics.JSToolBox;
import com.sos.i18n.annotation.I18NResourceBundle;

@I18NResourceBundle(baseName = "JOEMessages", defaultLocale = "en") public class Messages {
    //    private static final String   BUNDLE_NAME = "sos.scheduler.editor.messages"; //$NON-NLS-1$
    private static final String BUNDLE_NAME = "JOEMessages"; //$NON-NLS-1$
    public static String strLastMsgKey = "";
    private static ResourceBundle RESOURCE_BUNDLE;
    private static JSToolBox objToolBox;

    private Messages() {
        objToolBox = new JSToolBox("JOEMessages");
    }

    public static boolean setResource(Locale pobjLocale) {
        getMsgObj().setLocale(pobjLocale);
        // try {
        // RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, locale);
        // } catch (MissingResourceException e) {
        // return false;
        // }
        return true;
    }

    public static void clearMsgObj() {
        objToolBox = null;
    }

    private static com.sos.localization.Messages getMsgObj() {
        if (objToolBox == null) {
            objToolBox = new JSToolBox("JOEMessages", Options.getLanguage());
        }
        return objToolBox.getMessageObject();
    }

    public static String getBundle() {
        return BUNDLE_NAME;
    }

    public static String getString(String key) {
        return getLabel(key);
    }

    public static String getString(String key, String variable) {
        strLastMsgKey = key;
        return getMsgObj().getMsg(key, variable);
    }

    public static String getMsg(String key, Object... pstrArgs) {
        strLastMsgKey = key;
        return getMsgObj().getMsg(key, pstrArgs);
    }

    public static String getString(String key, Object[] values) {
        strLastMsgKey = key;
        return getMsgObj().getMsg(key, values);

        // try {
        // return MessageFormat.format(RESOURCE_BUNDLE.getString(key), values);
        // } catch (MissingResourceException e) {
        // return '!' + key + '!';
        // }
    }

    public static boolean hasMessage(String key) {
        try {
            // String msg = RESOURCE_BUNDLE.getString(key);
            String msg = getMsgObj().getMsg(key);
            return msg != null && !msg.equals("");
        }
        catch (MissingResourceException e) {
            return false;
        }
    }

    public static String getTooltip(String key) {
        try {
            strLastMsgKey = key;
            String msg = getMsgObj().getTooltip("tooltip." + key.toLowerCase());
            if (msg == null) {
                msg = getMsgObj().getLabel(key.toLowerCase() + ".tooltip");
            }

            return msg != null && !msg.equals("") ? msg : key;
        }
        catch (Exception e) {
            return key;
        }
    }

    public static String getLabel(String pstrKey) {
        String key = pstrKey.trim().replaceAll(" ", "");
        try {
            strLastMsgKey = key;
            String msg = getMsgObj().getLabel(key.toLowerCase() + ".label");
            if (msg == null) {
                msg = getMsgObj().getLabel(key + ".label");
                if (msg == null) {
                    msg = getMsgObj().getLabel(key);
                }
            }
            String strR = "";
            if (msg != null && msg.equals("") == false) {
                strR = msg;
            }
            else {
                strR = key;
            }
            return strR;
        }
        catch (Exception e) {
            return pstrKey;
        }
    }

    public static String getF1(String pstrKey) {
        String key = pstrKey.trim().replaceAll(" ", "");
        try {
            strLastMsgKey = key;
            String msg = getMsgObj().getLabel(key.toLowerCase() + ".f1");
            if (msg == null) {
                msg = getMsgObj().getLabel(key.toLowerCase() + ".f1");
                if (msg == null) {
                    msg = getMsgObj().getLabel(key);
                }
            }
            return msg != null && !msg.equals("") ? msg : key;
        }
        catch (Exception e) {
            return pstrKey;
        }
    }

}
