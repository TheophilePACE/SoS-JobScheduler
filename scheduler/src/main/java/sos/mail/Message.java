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
// $Id: Message.java 18725 2013-01-04 10:43:34Z kb $

package sos.mail;

// JavaMail 1.3: http://java.sun.com/products/javamail/
// JavaBeans Activation Framework (JAF): http://java.sun.com/products/javabeans/glasgow/jaf.html

// Classpath zum �bersetzen:
//   mail.jar

// Classpath zum Ablauf:
//   mail.jar
//   imap.jar
//   mailapi.jar
//   pop3.jar
//   smtp.jar
//   activation.jar

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileTypeMap;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;

public class Message {
	static final int	current_version	= 2;

	//-------------------------------------------------------------------------------My_data_source

	abstract class My_data_source implements DataSource {
		final String	name;
		final String	content_type;

		public My_data_source(final File new_filename, final String content_type) {
			name = new_filename.getName();
			this.content_type = content_type;
		}

		@Override
		public String getContentType() {
			return content_type;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public OutputStream getOutputStream() {
			throw new RuntimeException(getClass().getName() + " hat keinen OutputStream");
		}
	}

	//-----------------------------------------------------------------------Byte_array_data_source

	class Byte_array_data_source extends My_data_source {
		final byte[]	byte_array;

		public Byte_array_data_source(final byte[] byte_array, final File new_filename, final String content_type) {
			super(new_filename, content_type);
			this.byte_array = byte_array;
		}

		@Override
		public InputStream getInputStream() {
			return new ByteArrayInputStream(byte_array);
		}
	}

	//-----------------------------------------------------------------------------File_data_source
	// M�glicherweise kann FileDataSource verwendet werden.
	// Aber schlie�t die Klasse die Datei? Es gibt keinen close()

	class File_data_source extends My_data_source {
		final File	file;

		public File_data_source(final File file, final File new_filename, final String content_type) {
			super(new_filename, content_type);
			this.file = file;
		}

		@Override
		public InputStream getInputStream() throws IOException {
			FileInputStream f = new FileInputStream(file);
			file_input_streams.add(f); // wird von Message.close() geschlossen
			return f;
		}
	}

	//-----------------------------------------------------------------------------My_authenticator

	public class My_authenticator extends Authenticator {
		@Override
		public PasswordAuthentication getPasswordAuthentication() {
			//System.err.print( "getPasswordAuthentication " + _smtp_user_name + ", " + _smtp_password + "\n" );
			return new PasswordAuthentication(_smtp_user_name, _smtp_password);
		}
	}

	//---------------------------------------------------------------------------------------------

	private MimeMessage			_msg;
	private final Properties	_properties			= System.getProperties();
	private Session				_session			= null;
	private byte[]				_body;
	private final List			_attachments		= new LinkedList();
	private final ArrayList		file_input_streams	= new ArrayList();			// Alle offenen Attachments, werden von close() geschlossen
	private String				_smtp_user_name		= "";
	private String				_smtp_password		= "";
	private String				_encoding;
	private String				_content_type;
	private boolean				_built				= false;

	//--------------------------------------------------------------------------------------Message

	public Message() {
		_msg = new MimeMessage(get_session()); // s.a. set( "rfc822_text" )
	}

	//---------------------------------------------------------------------------------------------

	public void close() throws Exception {
		Exception exception = null;

		for (int i = 0; i < file_input_streams.size(); i++) {
			try {
				//System.err.print( getClass().getName() + ".close()\n" );
				((FileInputStream) file_input_streams.get(i)).close();
			}
			catch (Exception x) {
				if (exception == null)
					exception = x;
			}
		}

		if (exception != null)
			throw exception;
	}

	//------------------------------------------------------------------------------------------set

	public void need_version(final int version) throws Exception {
		if (version > current_version)
			throw new Exception("Class sos.mail.Message (sos.mail.jar) is not up to date");
	}

	//----------------------------------------------------------------------------------get_session

	private Session get_session() {
		if (_session == null) {
			_session = Session.getInstance(_properties, new My_authenticator());
		}

		return _session;
	}

	//------------------------------------------------------------------------------------------set

	public void set(final String what, final byte[] value) throws AddressException, MessagingException, UnsupportedEncodingException {
		if (what.equals("smtp"))
			_properties.put("mail.smtp.host", new String(value, "iso8859-1"));
		else
			//if( what.equals( "smtp.user"     ) )  _smtp_user_name = new String( value, "iso8859-1" );
			//else
			//if( what.equals( "smtp.password" ) )  _smtp_password = new String( value, "iso8859-1" );
			//else
			if (what.equals("from")) {
				InternetAddress[] addr = InternetAddress.parse(new String(value, "iso8859-1"));
				if (addr.length != 0)
					_msg.setFrom(addr[0]);
			}
			else
				if (what.equals("reply-to"))
					_msg.setReplyTo(InternetAddress.parse(new String(value, "iso8859-1")));
				else
					if (what.equals("to"))
						_msg.setRecipients(RecipientType.TO, InternetAddress.parse(new String(value, "iso8859-1")));
					else
						if (what.equals("cc"))
							_msg.setRecipients(RecipientType.CC, InternetAddress.parse(new String(value, "iso8859-1")));
						else
							if (what.equals("bcc"))
								_msg.setRecipients(RecipientType.BCC, InternetAddress.parse(new String(value, "iso8859-1")));
							else
								if (what.equals("subject"))
									_msg.setSubject(new String(value, "iso8859-1"));
								else
									if (what.equals("body")) {
										//_body = new MimeBodyPart();
										//_body.setText( new String(value,"iso8859-1") );
										_body = value;
									}
									else
										if (what.equals("content_type"))
											_content_type = new String(value, "iso8859-1");
										else
											if (what.equals("encoding"))
												_encoding = new String(value, "iso8859-1");
											else
												if (what.equals("send_rfc822")) {
													_msg = new MimeMessage(get_session(), new ByteArrayInputStream(value));
													send2();
												}
												else
													if (what.equals("debug"))
														get_session().setDebug(new String(value, "iso8859-1").equals("1"));
													else
														throw new RuntimeException("sos.mail.Message.set: what");
	}

	//---------------------------------------------------------------------------------set_property

	public void set_property(final String name, final String value) {
		if (name.equals("mail.smtp.user"))
			_smtp_user_name = value; // Keine Java-Property, Jira JS-136
		else
			if (name.equals("mail.smtp.password"))
				_smtp_password = value; // Keine Java-Property, Jira JS-136
			else
				_properties.put(name, value);
	}

	//------------------------------------------------------------------------string_from_addresses

	private String string_from_addresses(final Address[] addresses) {
		if (addresses == null)
			return "";

		String result = "";

		for (Address addresse : addresses) {
			if (!result.equals(""))
				result = new String(result + ", ");
			result = new String(result + addresse);
		}

		return result;
	}

	//------------------------------------------------------------------------------------------get

	public String get(final String what) throws Exception {
		if (what.equals("smtp"))
			return (String) _properties.get("mail.smtp.host");
		else
			if (what.equals("from"))
				return string_from_addresses(_msg.getFrom());
			else
				if (what.equals("reply-to"))
					return string_from_addresses(_msg.getReplyTo());
				else
					if (what.equals("to"))
						return string_from_addresses(_msg.getRecipients(RecipientType.TO));
					else
						if (what.equals("cc"))
							return string_from_addresses(_msg.getRecipients(RecipientType.CC));
						else
							if (what.equals("bcc"))
								return string_from_addresses(_msg.getRecipients(RecipientType.BCC));
							else
								if (what.equals("subject"))
									return _msg.getSubject();
								else
									if (what.equals("body"))
										return _body == null ? "" : new String(_body, "iso8859-1");
									else
										if (what.equals("rfc822_text")) {
											build();
											ByteArrayOutputStream os = new ByteArrayOutputStream();
											_msg.writeTo(os);
											return os.toString();
										}
										else
											throw new RuntimeException("sos.mail.Message.get: what=\"" + what + "\" ist unbekannt");
	}

	//-----------------------------------------------------------------------------add_header_field

	public void add_header_field(final String name, final String value) throws MessagingException {
		_msg.setHeader(name, value);
	}

	//-------------------------------------------------------------------------------------add_file

	public void add_file(final String real_filename, String new_filename, String content_type, final String encoding) throws Exception {
		if (new_filename == null || new_filename.length() == 0)
			new_filename = real_filename;
		if (content_type == null || content_type.length() == 0)
			content_type = FileTypeMap.getDefaultFileTypeMap().getContentType(new_filename);

		MimeBodyPart attachment = new MimeBodyPart();

		DataSource data_source = new File_data_source(new File(real_filename), new File(new_filename), content_type);
		DataHandler data_handler = new DataHandler(data_source);

		attachment.setDataHandler(data_handler);
		attachment.setFileName(data_handler.getName());

		_attachments.add(attachment);
	}

	//-------------------------------------------------------------------------------add_attachment

	public void add_attachment(final byte[] data, final String filename, String content_type, final String encoding) throws MessagingException {
		if (content_type.length() == 0)
			content_type = FileTypeMap.getDefaultFileTypeMap().getContentType(filename);

		MimeBodyPart attachment = new MimeBodyPart();

		DataSource data_source = new Byte_array_data_source(data, new File(filename), content_type);
		DataHandler data_handler = new DataHandler(data_source);

		attachment.setDataHandler(data_handler);
		attachment.setFileName(data_handler.getName());

		_attachments.add(attachment);
	}

	//-----------------------------------------------------------------------------------------send

	public void send() throws Exception {
		build();
		send2();
	}

	//----------------------------------------------------------------------------------------build

	public void build() throws Exception {
		if (_built)
			return;

		_msg.setSentDate(new Date()); // Damit rfc822_text das Datum liefert. Jira JS-81

		if (_content_type == null || _content_type.equals(""))
			_content_type = "text/plain";

		if (_attachments.size() == 0) {
			set_body_in(_msg);
		}
		else {
			MimeMultipart multipart = new MimeMultipart();

			MimeBodyPart b = new MimeBodyPart();
			set_body_in(b);
			multipart.addBodyPart(b);

			ListIterator i = _attachments.listIterator();
			while (i.hasNext())
				multipart.addBodyPart((BodyPart) i.next());

			_msg.setContent(multipart);
		}

		_built = true;
	}

	//----------------------------------------------------------------------------------set_body_in

	private void set_body_in(final MimePart body_part) throws Exception {
		body_part.setContent(new String(_body, "iso8859-1"), _content_type);
	}

	//----------------------------------------------------------------------------------------send2

	protected void send2() throws MessagingException, NoSuchProviderException {
		if (_smtp_user_name.length() > 0)
			_properties.put("mail.smtp.auth", "true");

		Transport.send(_msg);
	}

	//---------------------------------------------------------------------------------------------
/*
    static void main( String[] args )
    {
        new Message();
    }
*/
}
