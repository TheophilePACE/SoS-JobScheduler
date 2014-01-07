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
package sos.scheduler.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.sos.scheduler.model.ISOSSchedulerSocket;

/**
 * <p>Title: </p>
 * <p>Description: this class represents a simple client for the scheduler</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: SOS GmbH</p>
 * @author <a href="mailto:ghassan.beydoun@sos-berlin.com">Ghassan Beydoun</a>
 * @version $Id: SOSSchedulerCommand.java 20965 2013-08-28 12:11:38Z kb $
 */

public class SOSSchedulerCommand {

	@SuppressWarnings("unused")
	private final String conClassName = this.getClass().getSimpleName();
	@SuppressWarnings("unused")
	private static final String conSVNVersion = "$Id: SOSSchedulerCommand.java 20965 2013-08-28 12:11:38Z kb $";
	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(this.getClass());
	/** host */
	private String			host		= "localhost";

	/** port: default is 44444 */
	private int				port		= 4444;

	/** protocl: default is tcp */
	private String			protocol	= "tcp";

	private Socket			socket		= null;

	private DatagramSocket	udpSocket	= null;

	/** timeout für getResponse in sekunden */
	private int				timeout		= 60;

	private BufferedReader	in			= null;

	private PrintWriter		out			= null;

	protected ISOSSchedulerSocket objO = null;

	public SOSSchedulerCommand() {
	}
	public SOSSchedulerCommand(final ISOSSchedulerSocket pobjOptions) {
		objO = pobjOptions;
		this.setHost(objO.getServerName());
		this.setPort(objO.getPortNumber());
		this.setProtocol(protocol);
		this.setTimeout(objO.getTCPTimeoutValue());
	}

	public SOSSchedulerCommand(final String host) {

		this.setHost(host);
	}

	public SOSSchedulerCommand(final String host, final int port) {

		this.setHost(host);
		this.setPort(port);
	}

	/**
	* @param host the hostname (or ip address) of the Job Scheduler
	* @param port the port of the Job Scheduler
	* @param protocol the connection protocol ("tcp" or "udp")
	*/
	public SOSSchedulerCommand(final String host, final int port, final String protocol) {

		this.setHost(host);
		this.setPort(port);
		this.setProtocol(protocol);
	}

	/**
	 * Sets the hostname (or ip address) of the Job Scheduler
	 * @param host
	 */
	public void setHost(final String host) {
		this.host = host;
	}

	public String getHost() {
		return host;
	}

	/**
	 * Sets the port of the Job Scheduler
	 * @param port
	 */
	public void setPort(final int port) {
		this.port = port;
	}

	public int getPort() {
		return port;
	}

	/**
	 * @param timeout
	 */
	public void setTimeout(final int timeout) {
		this.timeout = timeout;
	}

	/**
	 *
	 * @return Returns the protocol.
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * @param protocol sets the protocol to "tcp" or "udp"
	 */
	public void setProtocol(final String protocol) {
		this.protocol = protocol;
	}

	/**
	 * connects to the scheduler
	 *
	 * @param host Job Scheduler host
	 * @param port Job Scheduler port
	 * @throws java.lang.Exception
	 */
	public void connect(final String host, final int port) throws Exception {

		if (host == null || host.length() == 0)
			throw new Exception("hostname missing.");

		if (port == 0)
			throw new Exception("port missing.");

		if (protocol.equalsIgnoreCase("udp")) {

			udpSocket = new DatagramSocket();
			udpSocket.connect(InetAddress.getByName(this.host), this.port);

		}
		else {

			socket = new Socket(host, port);
			if (this.getTimeout() > 0)
				socket.setSoTimeout(this.getTimeout() * 1000);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		}
	}

	/**
	 * connects to the scheduler
	 *
	 * @throws java.lang.Exception
	 */
	public void connect() throws Exception {
		this.connect(host, port);
	}

	/**
	 * sends a command to the scheduler
	 *
	 * @param command XML String containing the command
	 * @throws java.lang.Exception
	 */
	public void sendRequest(String command) throws Exception {

		if (protocol.equalsIgnoreCase("udp")) {
			if (command.indexOf("<?xml") == -1) {
				command = "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>" + command + "\r\n";
			}
			byte[] commandBytes = command.getBytes();
			udpSocket.send(new DatagramPacket(commandBytes, commandBytes.length, InetAddress.getByName(host), port));

		}
		else {
			if (command.indexOf("<?xml") == 0) {
				out.print(command + "\r\n");
			}
			else {
				out.print("<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>" + command + "\r\n");
			}
			out.flush();
		}
	}

	/**
	 * returns the scheduler reply.
	 *
	 * @return String represents the reply of the scheduler on success,
	 * otherwise empty string
	 * @throws IOException
	 * @throws RuntimeException
	 */
	public String getResponse() throws IOException, RuntimeException {

		int b;
		StringBuffer response = new StringBuffer();

		if (in != null) {
			while ((b = in.read()) != -1) {
				if (b == 0)
					break;
				response.append((char) b);

			} // while
		}
		return response.toString();
	}

	/**
	* @param host Job Scheduler host
	* @param port Job Scheduler port
	* @param xmlCommand XML String containing the command
	* @throws Exception
	*/
	public static void sendCommand(final String host, final int port, final String xmlCommand) throws Exception {

		SOSSchedulerCommand command = null;

		try {
			command = new SOSSchedulerCommand();

			command.setHost(host);
			command.setPort(port);
			command.setProtocol("udp");

			command.connect();
			command.sendRequest(xmlCommand);

		}
		catch (Exception e) {
			throw new Exception("startJob: could not start job: " + e.getMessage());
		}
	}

	public static void addOrder(final String host, final int port, final int status, final String jobChain) throws Exception {
		sendCommand(host, port, "<add_order job_chain=\"" + jobChain + "\" state=\"" + status + "\">" + "<params></params></add_order>");
	}

	public static void startJob(final String host, final int port, final String job) throws Exception {
		sendCommand(host, port, "<job job=\"" + job + "\">");
	}

	/**
	 * close the connection
	 *
	 * @throws java.lang.Exception
	 */
	public void disconnect() throws Exception {
		if (socket != null)
			socket.close();
		if (in != null)
			in.close();
		if (out != null)
			out.close();
	}

	public static void main(final String[] args) throws Exception {

		final String USAGE = "\nUsage: java -cp com.sos.scheduler-xxx.jar SOSSchedulerCommand"
				+ "  -host <host> -port <port> [-timeout <timeout>]  <xml-command>";
		String host = null;
		String command = null;
		int port = 0;
		int timeout = 5;
		int rc = 0;
		int argc = args.length;
		boolean help = false;

		for (int i = 0; i < argc; i++) {
			if (args[i].equals("-help") || args[i].equals("--help") || args[i].equals("-h")) {
				help = true;
				break;
			}
			if (args[i].equals("-host") && i + 1 < argc) {
				host = args[i + 1];
			}
			if (args[i].equals("-port") && i + 1 < argc) {
				port = Integer.parseInt(args[i + 1]);
			}
			if (args[i].equals("-timeout") && i + 1 < argc) {
				timeout = Integer.parseInt(args[i + 1]);
			}
			if (i == argc - 1) {
				command = args[i];
			}
		}

		if (help == true || argc == 0) {
			System.out.println(USAGE);
			System.exit(0);
		}

		if (argc < 5 || argc == 6 || argc > 7 || host == null || port == 0 || command == null) {
			System.err.println("wrong parameter");
			System.err.println(USAGE);
			System.exit(3);
		}

		SOSSchedulerCommand socket = null;
		try {
			socket = new SOSSchedulerCommand();
			socket.setTimeout(timeout);

			socket.connect(host, port);
			socket.sendRequest(command);
			String response = socket.getResponse();
			System.out.println(response);

			try {
				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				XPath xPath = XPathFactory.newInstance().newXPath();
				Document doc = builder.parse(new InputSource(new StringReader(response)));
				String errorText = (String) xPath.evaluate("/spooler/answer/ERROR/@text", doc, XPathConstants.STRING);

				if (errorText != null && errorText.length() > 0) {
					System.err.println(errorText);
					rc = 1;
				}
			}
			catch (Exception e) {
				//
			}
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			rc = 2;
		}
		finally {
			if (socket != null)
				socket.disconnect();
		}

		System.exit(rc);
	}

	/**
	 * @return the timeout
	 */
	public int getTimeout() {
		return timeout;
	}

}
