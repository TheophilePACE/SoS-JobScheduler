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
package sos.net.mail.options;

import java.util.HashMap;

import com.sos.JSHelper.Exceptions.JSExceptionMandatoryOptionMissing;
import com.sos.JSHelper.Options.SOSOptionHostName;
import com.sos.JSHelper.Options.SOSOptionMailAdress;
import com.sos.JSHelper.Options.SOSOptionMailSubject;
import com.sos.JSHelper.Options.SOSOptionPassword;
import com.sos.JSHelper.Options.SOSOptionPortNumber;
import com.sos.JSHelper.Options.SOSOptionString;

/**
* \class ISOSSmtpMailOptions 
* 
* \brief ISOSSmtpMailOptions - 
* 
* \details
*
*
* \code
*   .... code goes here ...
* \endcode
*
* <p style="text-align:center">
* <br />---------------------------------------------------------------------------
* <br /> APL/Software GmbH - Berlin
* <br />##### generated by ClaviusXPress (http://www.sos-berlin.com) #########
* <br />---------------------------------------------------------------------------
* </p>
* \author KB
* \version $Id$
* \see reference
*
* evtl beachten: https://blogs.oracle.com/apanicker/entry/java_code_for_smtp_server
* 
* Created on 24.11.2011 20:17:28
 */

public interface ISOSSmtpMailOptions {

	/**
	 * \brief getattachment : title Filename and pat
	 * 
	 * \details
	 * Filename and path of the attachment(s) (multiple attachments separated by ";")
	 *
	 * \return title Filename and pat
	 *
	 */
	public abstract SOSOptionString getattachment();

	/**
	 * \brief setattachment : title Filename and pat
	 * 
	 * \details
	 * Filename and path of the attachment(s) (multiple attachments separated by ";")
	 *
	 * @param attachment : title Filename and pat
	 */
	public abstract void setattachment(SOSOptionString p_attachment);

	/**
	 * \brief getattachment_charset : title charset of attac
	 * 
	 * \details
	 * charset of attachments
	 *
	 * \return title charset of attac
	 *
	 */
	public abstract SOSOptionString getattachment_charset();

	/**
	 * \brief setattachment_charset : title charset of attac
	 * 
	 * \details
	 * charset of attachments
	 *
	 * @param attachment_charset : title charset of attac
	 */
	public abstract void setattachment_charset(SOSOptionString p_attachment_charset);

	/**
	 * \brief getattachment_content_type : title content_type of
	 * 
	 * \details
	 * content_type of attachments (application/octet-stream, application/pdf...)
	 *
	 * \return title content_type of
	 *
	 */
	public abstract SOSOptionString getattachment_content_type();

	/**
	 * \brief setattachment_content_type : title content_type of
	 * 
	 * \details
	 * content_type of attachments (application/octet-stream, application/pdf...)
	 *
	 * @param attachment_content_type : title content_type of
	 */
	public abstract void setattachment_content_type(SOSOptionString p_attachment_content_type);

	/**
	 * \brief getattachment_encoding : title encoding of atta
	 * 
	 * \details
	 * encoding of attachments (7bit, Quoted-Printable, Base64)
	 *
	 * \return title encoding of atta
	 *
	 */
	public abstract SOSOptionString getattachment_encoding();

	/**
	 * \brief setattachment_encoding : title encoding of atta
	 * 
	 * \details
	 * encoding of attachments (7bit, Quoted-Printable, Base64)
	 *
	 * @param attachment_encoding : title encoding of atta
	 */
	public abstract void setattachment_encoding(SOSOptionString p_attachment_encoding);

	/**
	 * \brief getbcc : title bcc recipient(s)
	 * 
	 * \details
	 * bcc recipient(s)
	 *
	 * \return title bcc recipient(s)
	 *
	 */
	public abstract SOSOptionMailAdress getbcc();

	/**
	 * \brief setbcc : title bcc recipient(s)
	 * 
	 * \details
	 * bcc recipient(s)
	 *
	 * @param bcc : title bcc recipient(s)
	 */
	public abstract void setbcc(SOSOptionMailAdress p_bcc);

	/**
	 * \brief getbody : title Mail body
	 * 
	 * \details
	 * Mail body
	 *
	 * \return title Mail body
	 *
	 */
	public abstract SOSOptionString getbody();

	/**
	 * \brief setbody : title Mail body
	 * 
	 * \details
	 * Mail body
	 *
	 * @param body : title Mail body
	 */
	public abstract void setbody(SOSOptionString p_body);

	/**
	 * \brief getcc : title cc recipient(s)
	 * 
	 * \details
	 * cc recipient(s)
	 *
	 * \return title cc recipient(s)
	 *
	 */
	public abstract SOSOptionMailAdress getcc();

	/**
	 * \brief setcc : title cc recipient(s)
	 * 
	 * \details
	 * cc recipient(s)
	 *
	 * @param cc : title cc recipient(s)
	 */
	public abstract void setcc(SOSOptionMailAdress p_cc);

	/**
	 * \brief getcharset : title charset of the m
	 * 
	 * \details
	 * charset of the mail
	 *
	 * \return title charset of the m
	 *
	 */
	public abstract SOSOptionString getcharset();

	/**
	 * \brief setcharset : title charset of the m
	 * 
	 * \details
	 * charset of the mail
	 *
	 * @param charset : title charset of the m
	 */
	public abstract void setcharset(SOSOptionString p_charset);

	/**
	 * \brief getcontent_type : title content_type of
	 * 
	 * \details
	 * content_type of the mail (text/plain, text/html...)
	 *
	 * \return title content_type of
	 *
	 */
	public abstract SOSOptionString getcontent_type();

	/**
	 * \brief setcontent_type : title content_type of
	 * 
	 * \details
	 * content_type of the mail (text/plain, text/html...)
	 *
	 * @param content_type : title content_type of
	 */
	public abstract void setcontent_type(SOSOptionString p_content_type);

	/**
	 * \brief getencoding : title encoding of the
	 * 
	 * \details
	 * encoding of the mail (7bit, Quoted-Printable, Base64)
	 *
	 * \return title encoding of the
	 *
	 */
	public abstract SOSOptionString getencoding();

	/**
	 * \brief setencoding : title encoding of the
	 * 
	 * \details
	 * encoding of the mail (7bit, Quoted-Printable, Base64)
	 *
	 * @param encoding : title encoding of the
	 */
	public abstract void setencoding(SOSOptionString p_encoding);

	/**
	 * \brief getfrom : title mail sender
	 * 
	 * \details
	 * mail sender
	 *
	 * \return title mail sender
	 *
	 */
	public abstract SOSOptionMailAdress getfrom();

	/**
	 * \brief setfrom : title mail sender
	 * 
	 * \details
	 * mail sender
	 *
	 * @param from : title mail sender
	 */
	public abstract void setfrom(SOSOptionMailAdress p_from);

	/**
	 * \brief getfrom_name : title name of the send
	 * 
	 * \details
	 * name of the sender
	 *
	 * \return title name of the send
	 *
	 */
	public abstract SOSOptionString getfrom_name();

	/**
	 * \brief setfrom_name : title name of the send
	 * 
	 * \details
	 * name of the sender
	 *
	 * @param from_name : title name of the send
	 */
	public abstract void setfrom_name(SOSOptionString p_from_name);

	/**
	 * \brief gethost : title mail server host
	 * 
	 * \details
	 * mail server host
	 *
	 * \return title mail server host
	 *
	 */
	public abstract SOSOptionHostName gethost();

	/**
	 * \brief sethost : title mail server host
	 * 
	 * \details
	 * mail server host
	 *
	 * @param host : title mail server host
	 */
	public abstract void sethost(SOSOptionHostName p_host);

	/**
	 * \brief getport : title mail server port
	 * 
	 * \details
	 * mail server port
	 *
	 * \return title mail server port
	 *
	 */
	public abstract SOSOptionPortNumber getport();

	/**
	 * \brief setport : title mail server port
	 * 
	 * \details
	 * mail server port
	 *
	 * @param port : title mail server port
	 */
	public abstract void setport(SOSOptionPortNumber p_port);

	/**
	 * \brief getqueue_directory : title Mail queue direc
	 * 
	 * \details
	 * Mail queue directory. Mails which cannot be transferred will be put here. The Job Scheduler will later retry to send these mails.
	 *
	 * \return title Mail queue direc
	 *
	 */
	public abstract SOSOptionString getqueue_directory();

	/**
	 * \brief setqueue_directory : title Mail queue direc
	 * 
	 * \details
	 * Mail queue directory. Mails which cannot be transferred will be put here. The Job Scheduler will later retry to send these mails.
	 *
	 * @param queue_directory : title Mail queue direc
	 */
	public abstract void setqueue_directory(SOSOptionString p_queue_directory);

	/**
	 * \brief getreply_to : title reply address
	 * 
	 * \details
	 * reply address
	 *
	 * \return title reply address
	 *
	 */
	public abstract SOSOptionMailAdress getreply_to();

	/**
	 * \brief setreply_to : title reply address
	 * 
	 * \details
	 * reply address
	 *
	 * @param reply_to : title reply address
	 */
	public abstract void setreply_to(SOSOptionMailAdress p_reply_to);

	/**
	 * \brief getsmtp_password : title smtp user passwo
	 * 
	 * \details
	 * smtp user password
	 *
	 * \return title smtp user passwo
	 *
	 */
	public abstract SOSOptionPassword getsmtp_password();

	/**
	 * \brief setsmtp_password : title smtp user passwo
	 * 
	 * \details
	 * smtp user password
	 *
	 * @param smtp_password : title smtp user passwo
	 */
	public abstract void setsmtp_password(SOSOptionPassword p_smtp_password);

	/**
	 * \brief getsmtp_user : title smtp username
	 * 
	 * \details
	 * smtp username
	 *
	 * \return title smtp username
	 *
	 */
	public abstract SOSOptionString getsmtp_user();

	/**
	 * \brief setsmtp_user : title smtp username
	 * 
	 * \details
	 * smtp username
	 *
	 * @param smtp_user : title smtp username
	 */
	public abstract void setsmtp_user(SOSOptionString p_smtp_user);

	/**
	 * \brief getsubject : title Mail Subject
	 * 
	 * \details
	 * Mail Subject
	 *
	 * \return title Mail Subject
	 *
	 */
	public abstract SOSOptionMailSubject getsubject();

	/**
	 * \brief setsubject : title Mail Subject
	 * 
	 * \details
	 * Mail Subject
	 *
	 * @param subject : title Mail Subject
	 */
	public abstract void setsubject(SOSOptionMailSubject p_subject);

	/**
	 * \brief getto : title mail recipient(s
	 * 
	 * \details
	 * mail recipient(s)
	 *
	 * \return title mail recipient(s
	 *
	 */
	public abstract SOSOptionMailAdress getto();

	/**
	 * \brief setto : title mail recipient(s
	 * 
	 * \details
	 * mail recipient(s)
	 *
	 * @param to : title mail recipient(s
	 */
	public abstract void setto(SOSOptionMailAdress p_to);

	/**
	 * \brief setAllOptions - �bernimmt die OptionenWerte aus der HashMap
	 *
	 * \details In der als Parameter anzugebenden HashMap sind Schl�ssel (Name)
	 * und Wert der jeweiligen Option als Paar angegeben. Ein Beispiel f�r den
	 * Aufbau einer solchen HashMap findet sich in der Beschreibung dieser
	 * Klasse (\ref TestData "setJobSchedulerSSHJobOptions"). In dieser Routine
	 * werden die Schl�ssel analysiert und, falls gefunden, werden die
	 * dazugeh�rigen Werte den Properties dieser Klasse zugewiesen.
	 *
	 * Nicht bekannte Schl�ssel werden ignoriert.
	 *
	 * \see JSOptionsClass::getItem
	 *
	 * @param pobjJSSettings
	 * @throws Exception
	 */
	public abstract void setAllOptions(HashMap<String, String> pobjJSSettings) throws Exception; // public void setAllOptions (HashMap <String, String> JSSettings)

	/**
	 * \brief CheckMandatory - pr�ft alle Muss-Optionen auf Werte
	 *
	 * \details
	 * @throws Exception
	 *
	 * @throws Exception
	 * - wird ausgel�st, wenn eine mandatory-Option keinen Wert hat
	 */
	public abstract void CheckMandatory() throws JSExceptionMandatoryOptionMissing //
			, Exception; // public void CheckMandatory ()

	/**
	 *
	 * \brief CommandLineArgs - �bernehmen der Options/Settings aus der
	 * Kommandozeile
	 *
	 * \details Die in der Kommandozeile beim Starten der Applikation
	 * angegebenen Parameter werden hier in die HashMap �bertragen und danach
	 * den Optionen als Wert zugewiesen.
	 *
	 * \return void
	 *
	 * @param pstrArgs
	 * @throws Exception
	 */
	public abstract void CommandLineArgs(String[] pstrArgs) throws Exception;

}
