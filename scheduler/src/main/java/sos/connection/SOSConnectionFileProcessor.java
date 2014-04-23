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
package sos.connection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Vector;

import sos.connection.SOSConnection;
import sos.util.SOSFile;
import sos.util.SOSLogger;
import sos.util.SOSStandardLogger;

public class SOSConnectionFileProcessor {

	SOSConnection connection = null;

	SOSLogger logger = null;

	String settingsFilename = null;

	String fileSpec = "^(.*)";
	
	boolean hasDirectory	= false;
    
    boolean commitAtEnd = false;
    

	public SOSConnectionFileProcessor(SOSConnection sosConnection)
			throws Exception {

		this.setConnection(sosConnection);
		this.init();
	}

	public SOSConnectionFileProcessor(SOSConnection sosConnection,
			SOSLogger sosLogger) throws Exception {

		this.setConnection(sosConnection);
		this.setLogger(sosLogger);
		this.init();
	}

	public SOSConnectionFileProcessor(String settingsFilename) throws Exception {

		this.setSettingsFilename(settingsFilename);
		this.init();
	}

	public SOSConnectionFileProcessor(String settingsFilename,
			SOSLogger sosLogger) throws Exception {

		this.setSettingsFilename(settingsFilename);
		this.setLogger(sosLogger);
		this.init();
	}

	public void init() throws Exception {

		try {
			if (this.getConnection() == null) {
				if (this.getSettingsFilename() == null
						|| this.getSettingsFilename().trim().length() == 0)
					throw new Exception(
							"no connection and no settings filename were given for connection");

				// DB Initialisierung
				if (this.getLogger() != null)
					this.getLogger().debug3("DB Connecting.. .");
				this.setConnection(SOSConnection.createInstance(this
						.getSettingsFilename(), this.getLogger()));
				this.getConnection().connect();
				if (this.getLogger() != null)
					this.getLogger().debug3("DB Connected");

			}
		} catch (Exception e) {
			throw (new Exception("connect to database failed: "
					+ e.getMessage()));
		}
	}

	public void process(File inputFile, String fileSpec) throws Exception {

		this.setFileSpec(fileSpec);
		this.process(inputFile);
	}

	
	public void process(File inputFile) throws Exception {

		boolean isEnd = false;
		try {
			if (inputFile.isDirectory()) {
				
				this.hasDirectory = true;
				
				Vector filelist = SOSFile.getFilelist(inputFile
						.getAbsolutePath(), this.getFileSpec(), 0);
				
				Iterator iterator = filelist.iterator();
				
				while (iterator.hasNext()) {
					this.process((File) iterator.next());
				}
				
				isEnd = true;
				
			} 
			else {
				BufferedReader br = new BufferedReader(new FileReader(inputFile
						.getAbsolutePath()));
				String nextLine = "";
				StringBuffer sb = new StringBuffer();
				while ((nextLine = br.readLine()) != null) {
					sb.append(nextLine);
					sb.append("\n");
				}

				this.getConnection().executeStatements(sb.toString());

				
				if(!this.hasDirectory){
					isEnd = true;
				}
				
				if (this.getLogger() != null)
					this.getLogger().info(
							"file successfully processed: " + inputFile);
			}
		} catch (Exception e) {
			if (this.getLogger() != null)
				this.getLogger().warn(
						"an error occurred processing file ["
								+ inputFile.getAbsolutePath() + "]: "
								+ e.getMessage());
		} finally {
			// SQL Server 2005 compatibility issue: bei connection.rollback()
			// wird der Transaktionszähler falsch erhöht, daher explizites
			// "rollback"
			// try { if (this.getConnection() != null)
			// this.getConnection().rollback(); } catch (Exception ex) {}
			
			try {
				if (this.getConnection() != null && isEnd) {
                    if (this.isCommitAtEnd()) {
                        this.getConnection().commit();
                    } else {
                        this.getConnection().executeUpdate("ROLLBACK");
                    }
				}
			} catch (Exception ex) {
			}

		}
	}

	/**
	 * @return Returns the connection.
	 */
	public SOSConnection getConnection() {
		return connection;
	}

	/**
	 * @param connection
	 *            The connection to set.
	 */
	public void setConnection(SOSConnection connection) {
		this.connection = connection;
	}

	/**
	 * @return Returns the logger.
	 */
	public SOSLogger getLogger() {
		return logger;
	}

	/**
	 * @param logger
	 *            The logger to set.
	 */
	public void setLogger(SOSLogger logger) {
		this.logger = logger;
	}

	/**
	 * @return Returns the settingsFilename.
	 */
	public String getSettingsFilename() {
		return settingsFilename;
	}

	/**
	 * @param settingsFilename
	 *            The settingsFilename to set.
	 */
	public void setSettingsFilename(String settingsFilename) {
		this.settingsFilename = settingsFilename;
	}

	/**
	 * @return Returns the fileSpec.
	 */
	public String getFileSpec() {
		return fileSpec;
	}

	/**
	 * @param fileSpec
	 *            The fileSpec to set.
	 */
	public void setFileSpec(String fileSpec) {
		this.fileSpec = fileSpec;
	}

    /**
     * @return the commitAtEnd
     */
    public boolean isCommitAtEnd() {
        return commitAtEnd;
    }

    /**
     * @param commitAtEnd the commitAtEnd to set
     */
    public void setCommitAtEnd(boolean commitAtEnd) {
        this.commitAtEnd = commitAtEnd;
    }

    
    /** process directory or files */
	public static void main(String args[]) throws Exception {

		int logLevel = 0;

		if (args.length < 2) {
			System.out
					.println("Usage: SOSConnectionFileProcessor configuration-file  path  [file-specification]  [log-level] [-commit-at-end|-auto-commit] ");
		}

		if (args.length > 3)
			logLevel = Integer.parseInt(args[3]);

		SOSConnectionFileProcessor processor = new SOSConnectionFileProcessor(
				args[0], (SOSLogger) new SOSStandardLogger(logLevel));

		File inputFile = new File(args[1]);

		if (args.length > 2)
			processor.setFileSpec(args[2]);

        if (args.length > 4) {
            if (args[4].equalsIgnoreCase("-commit-at-end")) processor.setCommitAtEnd(true);
            if (args[4].equalsIgnoreCase("-auto-commit")) processor.getConnection().setAutoCommit(true);
        }
        
		processor.process(inputFile);
	}

}
