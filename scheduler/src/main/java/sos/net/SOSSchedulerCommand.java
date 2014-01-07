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


/**
 * <p>Title: </p>
 * <p>Description: Client für den Scheduler</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: SOS GmbH</p>
 * @author <a href="mailto:ghassan.beydoun@sos-berlin.com">Ghassan Beydoun</a>
 * @version $Id: SOSSchedulerCommand.java,v 1.1.1.1 2003/09/23 11:48:15 gb Exp $
 */


import java.io.PrintWriter;
import java.io.IOException;
import java.io.DataInputStream;

import java.net.*;


public class SOSSchedulerCommand {

  /** host */
  private String host = "localhost";

  /** port: default ist 44444 */
  private int port    = 4444;

  /** protocl: default ist tcp */
 

  private Socket socket = null;

  /** timeout für getResponse in sekunden: default ist 5 sek. */
  private int timeout = 5;


  private DataInputStream in = null;

  private PrintWriter out = null;


  /**
   *
   * @param host
   */
  public void setHost( String host ) {
    this.host = host;
  }


  /**
   *
   * @param port
   */
  public void setPort( int port ) {
    this.port = port;
  }


  /**
   *
   *
   * @param timeout
   */
  public void setTimeout( int timeout ) {
    this.timeout = timeout;
  }


  /**
   * Liefert die aktuelle Port-Nummer des Servers
   *
   * @return Port-Nummer des Servers
   */

  public int getPort() {
    return socket.getPort();
  }


  /**
   * baut die Verbindung zum Server auf
   *
   * @param host
   * @param port
   * @throws java.lang.Exception
   */
  public void connect( String host, int port ) throws Exception {
    if( host == null || host.length() == 0 )
      throw (new Exception("hostname missing."));

    if( port == 0 )
      throw (new Exception("port missing."));

    socket = new Socket(host, port);

    in     = new DataInputStream(socket.getInputStream());
    out    = new PrintWriter(socket.getOutputStream(), true);
  }


  /**
   * baut die Verbindung zum Server auf
   *
   * @throws java.lang.Exception
   */
  public void connect() throws Exception {
    this.connect(this.host,this.port);
  }



  /**
   * sendet eine Anfrage an den Scheduler
   *
   * @param command
   * @throws java.lang.Exception
   */
  public void sendRequest( String command ) throws Exception {

	if ( command.indexOf("<?xml") == 0 ) {
    	out.print(command + "\r\n");
	} else {
		out.print( "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>" + command + "\r\n" );
	}
    out.flush();
  }


  /**
   * Liefert die Antwort auf die an den Scheduler zuletzt gesendete Anfrage
   *
   * @return String Antwort vom Scheduler
   * @throws IOException
   */
  public String getResponse() throws IOException,RuntimeException {
    int sec = 0;
    byte[] buffer = {};
    while(in.available() == 0) {
      try {
        Thread.sleep(1000);
      } catch(InterruptedException e) {}
      if(sec++ == timeout)
        throw new RuntimeException("timeout reached");
    }
    buffer = new byte[in.available()];
    int bytesRead = in.read(buffer);
    return new String(buffer,"ISO-8859-1");
  }


  /**
   * schliesst die Verbindung
   *
   * @throws java.lang.Exception
   */
  public void disconnect() throws Exception {
    if (socket != null) socket.close();
    if (in != null) in.close();
    if (out != null) out.close();
  }


  public static void main(String[] args) throws Exception {

    int argc=0, i=0;
    final String CLASS_NAME = "sos.net.SOSSchedulerCommand";

    final String USAGE = "\nUSAGE: java " + CLASS_NAME +
        " [ -host <host> -port <port ] <xml-command>";

    boolean params = false;
    String host    = null;
    String command = null;
    int port       = 0;

    argc = args.length;

    if ( argc == 5 ) {
      if ( args[0].equals("-host") ) {
        i++;
        host = args[i++];
        if( args[i++].equals("-port") ) {
          port = Integer.parseInt(args[i++]);
        } else {
          System.out.println(USAGE);
          System.exit(0);
        }
        command = args[i];
      } else {
        System.out.println(USAGE);
        System.exit(0);
      }
    } else if (argc == 1) { // default parameter verwenden
      params = true;
    } else {
      System.out.println(USAGE);
      System.exit(0);
    }

    SOSSchedulerCommand socket = null;
    try {
      socket = new SOSSchedulerCommand();
      if (params)
        socket.connect();
      else
        socket.connect(host, port); // sag,4371sag
        //c.sendRequest("<show_state what=\"all\"/>");
      socket.sendRequest(command);
      System.out.println(socket.getResponse());

    } catch(Exception e) {
      System.out.println(e.getMessage());
      System.out.println(USAGE);
    } finally {
      if( socket != null) socket.disconnect();
      System.exit(0);
    }

  }


}
