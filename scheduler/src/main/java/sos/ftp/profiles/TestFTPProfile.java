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
package sos.ftp.profiles;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import sos.util.SOSLogger;
import sos.util.SOSStandardLogger;

/**
 * Test Programm zur Verwendung der Klasse sos.ftp.profiles.FTPProfilePicker.
 * 
 * @resource: swt.jar, sos.util.jar, sos.settings.jar, sos.net.jar, commons-net-1.2.2.jar, trilead-ssh2-build211.jar, jce-jdk13-129.jar
 * 
 * @author mo
 *
 */
public class TestFTPProfile {


	/**
	 * Alle Antworten der FTP Server werden in dem Log Fenster ausgegeben
	 */
	private static Text txtLog = null;

	/**
	 * Test Program zu ftpProfilePicker
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		try {


			Display display = Display.getDefault();
			Shell testFtpProfileShell = new Shell();
			final GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 2;
			testFtpProfileShell.setLayout(gridLayout);
			testFtpProfileShell.setText("Test FTP Profile");
			testFtpProfileShell.setSize(452, 309);
			testFtpProfileShell.setFocus();


			final SOSLogger  logger = new SOSStandardLogger(SOSLogger.DEBUG9);

			
			final FTPProfilePicker ftpProfilePicker = new FTPProfilePicker(testFtpProfileShell,						
					SWT.NONE, 
					new java.io.File("c:/scheduler/config/factory_test.ini")
			);

			ftpProfilePicker.addEmptyItem();
			ftpProfilePicker.setLogger(logger);

			ftpProfilePicker.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));


			//butConnect dient nur zum Testen
			final Button butConnect = new Button(testFtpProfileShell, SWT.NONE);
			//hier: Listener, wenn ein neuer Profile aus dem Combobox ausgew�hlt wird
			ftpProfilePicker.addSelectionListener((new SelectionAdapter() {
				public void widgetSelected(final SelectionEvent e) {
					System.out.println("test 2");
					try {
						butConnect.setEnabled( ftpProfilePicker.getSelectedFTPProfile() != null);
					} catch (Exception ex) {
						//tu nix
					}
				}
			}));
			


			butConnect.setLayoutData(new GridData());
			butConnect.setEnabled( ftpProfilePicker.getSelectedFTPProfile() != null);
			final Label label1 = new Label(testFtpProfileShell, SWT.NONE);
			butConnect.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(final SelectionEvent e) {
					sos.net.SOSFileTransfer sosfileTransfer = null;


					try {
						label1.setText("try to connect..");
						
						//2a. 
						FTPProfile profile1 = ftpProfilePicker.getSelectedFTPProfile();

						if(profile1 == null) {
							logger.warn("Please define first a Profile");
							return;
						}

						//Verbindung zum FTP Server aufbauen
						sosfileTransfer = profile1.connect();
						
						
						
						//M�gliche aufrufen zum holen von Dateien
						
						/*String l = profile1.openFile("/home/test/temp/1.txt", "1");
						
						System.out.println("Datei per FTP holen und in Verzeichnis(=profile1.getRoot() + 1) ablegen: " + l);
						
						//dritte Parameter steht f�r append 
						long j = sosfileTransfer.getFile("/home/test/temp/1.txt", "c:/temp/1/1.txt", false);
						System.out.println("Datei per FTP holen und in Verzeichnis(=c:/temp/1/1.txt) ablegen: Bytes " + j);
						
						long s = profile1.getFile("/home/test/temp/1.txt", "c:/temp/live/2.txt");
						System.out.println("Datei per FTP holen und in Verzeichnis(=c:/temp/1/2.txt) ablegen: " + s);
						
						long k = profile1.getFile("/home/test/temp/1.txt", new java.io.File("c:/temp/1/2212.txt"));
						System.out.println("Datei per FTP holen und in Verzeichnis ablegen: Bytes " + k);
						
						long m = profile1.appendFile("/home/test/temp/1.txt", "c:/temp/1/2.txt");
						System.out.println("Datei per FTP holen und in die Datei c:/temp/1/2.txt hinten anh�ngen: Bytes " + j);
						
						
						long z = profile1.saveAs("c:/temp/config/1.txt", "1.txt");
						System.out.println("Datei (c:/temp/config/1.txt) per FTP Transfer senden und " +
								" in die Datei getRoot() + 2.txt speichern: Bytes " + j);
						
						long x = profile1.saveAs("c:/temp/config/1.txt", "/home/test/temp/test/222222221.txt");
						System.out.println("Datei (c:/temp/config/1.txt) per FTP Transfer senden und " +
								" als /home/test/temp/test/222222221.txt speichern: Bytes " + x);
								*/
						/*if(profile1.isConnect()) {
							//entweder	    							    							    						
							profile1.saveAs("c:/temp/config/1.txt", "1.txt");
							java.util.Vector v0 = profile1.getList();
							for(int i = 0; i < v0.size(); i++) {
								logger.info(i + "\t vector v0 file: " + v0.get(i));
							}
							//oder
							java.util.Vector v1 = profile1.getList("/home/test/temp/test/folder1/live");
							for(int i = 0; i < v1.size(); i++) {
								logger.info(i + "\t vector v1 file: " + v1.get(i));
							}

							//oder
							sosfileTransfer.putFile("c:/temp/config/1.txt", "1.txt");
						} else {
							System.out.println("no connection");
						}
						*/

						/*
	    					if(sosfileTransfer instanceof sos.net.SOSFTP) {
	    						sos.net.SOSFTP  ftp  = (sos.net.SOSFTP)sosfileTransfer;	    						
	    					}else if (sosfileTransfer instanceof sos.net.SOSSFTP) {
	    						sos.net.SOSSFTP sftp = (sos.net.SOSSFTP)sosfileTransfer;	    							    						
	    					}
						 */

						logger.info("------------> is Connected: " + sosfileTransfer.isConnected());
						
						if(profile1.isConnect())
							label1.setText("successfully connected with.." + ftpProfilePicker.getSelectedProfilename());
						else
							label1.setText("connecting failed" );



						//2
						/*
	    					System.out.println();System.out.println();
	    				    FTPProfile profile = ftpProfilePicker.getProfileByName("wilma");
	    				    logger.info("user: " + profile.getUser());
	    				    logger.info("root: " + profile.getRoot());	    				    	    				  
	    				    ftp = ftpProfilePicker.connect();	    				   
	    				    logger.info("2. -->" + ftp.getSystemName());
						 */

						//3
						/*
	    					System.out.println();System.out.println();
	    				    FTPProfile profile2 = ftpProfilePicker.getProfileByName("8of9");
	    				    logger.info("user: " + profile2.getUser());
	    				    logger.info("root: " + profile2.getRoot());	    				    	    				    	    				  
	    				    ftp = ftpProfilePicker.connect();	    				    	    				   
	    				    logger.info("3. -->" + ftp.getSystemName());

	    				    label.setText("successfully connected with.." + ftpProfilePicker.getProfileByName(ftpProfilePicker.getProfilename()).getHost());
						 */
					} catch (Exception ex){
						if (txtLog != null && sosfileTransfer != null) txtLog.append("..ftp server reply error: " + sosfileTransfer.getReplyString());
						label1.setText("connecting failed" );
						System.out.println(ex.toString());	
					}

				}
			});
			butConnect.setText("Test Connect");


			label1.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false, 2, 1));
			txtLog = new Text(testFtpProfileShell, SWT.V_SCROLL | SWT.MULTI | SWT.READ_ONLY | SWT.BORDER | SWT.WRAP);
			txtLog.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true, 2, 1));

			ftpProfilePicker.setLogText(txtLog);



			ftpProfilePicker.setButtonText("Profiles");


			testFtpProfileShell.open();
			testFtpProfileShell.update();


			while (!testFtpProfileShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
			display.dispose();

		} catch (Exception e) {
			System.out.println(e);
		}

	}



}
