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
package sos.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * <p>Title: SOSSSLSocketFactory</p>
 * <p>Description: factory for SSL sockets with HTTP Proxy support </p>  
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: SOS GmbH</p>
 * @author <a href="mailto:ghassan.beydoun@sos-berlin.com">Ghassan Beydoun</a>
 * @version $Id: SOSSSLSocketFactory.java 14434 2011-05-23 07:01:48Z sos $
 */

public class SOSSSLSocketFactory extends SSLSocketFactory /* implements org.apache.commons.net.ftp.FTPSocketFactory */  {

	private SSLSocketFactory	sslFactory;

	private String				proxyHost;

	private int					proxyPort			= 3128;
 
	private boolean				done				= false;

	private final String		SECURITY_PROTOCOL	= "TLS";

	private String				securityProtocol	= SECURITY_PROTOCOL;

	/**
	 * @param pproxyHost
	 *            HTTP Proxy
	 * @param pproxyPort
	 * @param psecurityProtocol for example TLS or SSL
	 */
	public SOSSSLSocketFactory(String pproxyHost, int pproxyPort, String psecurityProtocol) {
		this();
		this.proxyHost = pproxyHost;
		this.proxyPort = pproxyPort;
		this.securityProtocol = psecurityProtocol;
	}

	/**
	 * @param pproxyHost
	 *            HTTP Proxy
	 * @param pproxyPort
	 */
	public SOSSSLSocketFactory(String pproxyHost, int pproxyPort) {
		this();
		this.proxyHost = pproxyHost;
		this.proxyPort = pproxyPort;
	}

	public SOSSSLSocketFactory() {

		SSLContext sslcontext = null;
		try {
			sslcontext = SSLContext.getInstance(securityProtocol);
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (sslcontext != null) {
			try {
				sslcontext.init(null, new TrustManager[] { new SOSTrustManager() }, new java.security.SecureRandom());
			}
			catch (KeyManagementException e) {
				e.printStackTrace();
			}
			sslFactory = sslcontext.getSocketFactory();
		}
	}

	public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
		return sslFactory.createSocket(host, port);
	}

	public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort) throws IOException, UnknownHostException {
		return sslFactory.createSocket(host, port, clientHost, clientPort);
	}

	public Socket createSocket(InetAddress host, int port) throws IOException {
		return createSocket(null, host.getHostName(), port, true);
	}

	public Socket createSocket(InetAddress address, int port, InetAddress clientAddress, int clientPort) throws IOException {
		return createSocket(null, address.getHostName(), port, true);
	}

	public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {

		// http proxy is available
		if (proxyHost != null && proxyHost.length() > 0) {

			Socket tunnel = new Socket(proxyHost, proxyPort);

			doTunnelHandshake(tunnel, host, port);

			SSLSocket sslSocket = (SSLSocket) sslFactory.createSocket(tunnel, host, port, autoClose);

			sslSocket.addHandshakeCompletedListener(new HandshakeCompletedListener() {
				public void handshakeCompleted(HandshakeCompletedEvent event) {
					// Handshake finished!"
					done = true;
				}
			});
			if (!done)
				sslSocket.startHandshake();

			return sslSocket;

		}
		else {
			return sslFactory.createSocket(socket, host, port, autoClose);
		}
	}

	private void doTunnelHandshake(Socket tunnel, String host, int port) throws IOException {
		OutputStream out = tunnel.getOutputStream();
		String msg = "CONNECT " + host + ":" + port + " HTTP/1.0\n" + "User-Agent: " + sun.net.www.protocol.http.HttpURLConnection.userAgent + "\r\n\r\n";
		byte b[];
		try {
			/*
			 * We really do want ASCII7 -- the http protocol doesn't change with
			 * locale.
			 */
			b = msg.getBytes("ASCII7");
		}
		catch (UnsupportedEncodingException ignored) {
			/*
			 * If ASCII7 isn't there, something serious is wrong, but Paranoia
			 * Is Good (tm)
			 */
			b = msg.getBytes();
		}
		out.write(b);
		out.flush();

		/*
		 * We need to store the reply so we can create a detailed error message
		 * to the user.
		 */
		byte reply[] = new byte[200];
		int replyLen = 0;
		int newlinesSeen = 0;
		boolean headerDone = false; /* Done on first newline */

		InputStream in = tunnel.getInputStream();

		while (newlinesSeen < 2) {
			int i = in.read();
			if (i < 0) {
				throw new IOException("Unexpected EOF from proxy");
			}
			if (i == '\n') {
				headerDone = true;
				++newlinesSeen;
			}
			else
				if (i != '\r') {
					newlinesSeen = 0;
					if (!headerDone && replyLen < reply.length) {
						reply[replyLen++] = (byte) i;
					}
				}
		}

		/*
		 * Converting the byte array to a string is slightly wasteful in the
		 * case where the connection was successful, but it's insignificant
		 * compared to the network overhead.
		 */
		String replyStr;
		try {
			replyStr = new String(reply, 0, replyLen, "ASCII7");
		}
		catch (UnsupportedEncodingException ignored) {
			replyStr = new String(reply, 0, replyLen);
		}

		/* Look for 200 connection established */
		if (replyStr.toLowerCase().indexOf("200 connection established") == -1) {
			throw new IOException("Unable to tunnel through " + proxyHost + ":" + proxyPort + ".  Proxy returns \"" + replyStr + "\"");
		}

		/* tunneling Handshake was successful! */
	}

	public String[] getDefaultCipherSuites() {
		return sslFactory.getDefaultCipherSuites();
	}

	public String[] getSupportedCipherSuites() {
		return sslFactory.getSupportedCipherSuites();
	}

	public ServerSocket createServerSocket(int arg0) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public ServerSocket createServerSocket(int arg0, int arg1) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public ServerSocket createServerSocket(int arg0, int arg1, InetAddress arg2) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
