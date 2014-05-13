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
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Combo;
import sos.util.SOSLogger;
import org.eclipse.swt.widgets.Text;
import java.io.File;

/**
 * 
 * In dieser Klasse k�nnen FTP Zug�nge verwaltet werden.
 * 
 * <br/><br/>Diese Klasse besteht aus einer Auswahlbox und einem Button.
 * 
 * <br/><br/>Beim Klicken auf den Button wird Dialog zur Erstellen, Bearbeiten und L�schen 
 * von FTP Zug�nge ge�ffnet.
 * 
 * <br/><br/>In der Auswahlbox stehen alle Zug�nge die im Dialog erfasst wurden.
 * 
 * <br/>Mit den Zugangsdaten k�nnen FTP Verbindungen aufgebaut und Dateien transferiert werden.
 * 
 * <br/><br/>Beispielaufruf:
 * 
 * <br/><br/> Folgende Bibliotheken werden verwendet: 
 *       swt.jar, sos.util.jar, sos.settings.jar, sos.net.jar, commons-net-1.2.2.jar, trilead-ssh2-build211.jar, jce-jdk13-129.jar
 *   
 *  <br/><br/> //�bergabe einer Logger Object. @see sos.util.SOSLogger
 *  <br/> //Wenn kein Logger Object �bergeben wurde, dann wird alles in Standardausgabe geschrieben.
 *  <br/> final SOSLogger  logger = new SOSStandardLogger(SOSLogger.DEBUG9);
 *   
 *   <br/><br/>//FTPProfilePicker Object instanzieren
 *   <br/>FTPProfilePicker ftpProfilePicker = new FTPProfilePicker(shell, SWT.NONE, "c:/temp/factory_test.ini");
 *   
 *   <br/><br/>//der Profile Auswahlbox soll auch einen leeren item haben
 *   <br/>ftpProfilePicker.addEmptyItem();
 *   
 *   <br/><br/>//ggf. Logger Object �bergeben
 *   <br/>ftpProfilePicker.setLogger(logger);
 *   
 *   <br/><br/>//hier: Listener, wenn ein neuer Profile aus dem Combobox ausgew�hlt wird
 *   <br/>ftpProfilePicker.addSelectionListener((new SelectionAdapter() {
 *   <br/>public void widgetSelected(final SelectionEvent e) {
 *   <br/>		System.out.println("e ist das abgefangene event");
 *   <br/>		try {
 *   <br/>			butConnect.setEnabled( ftpProfilePicker.getSelectedFTPProfile() != null);
 *   <br/>		} catch (Exception ex) {
 *   <br/>  		//tu nix
 *   <br/>  		  }
 *   <br/>  		}
 *   <br/>  	}));
 *     
 *    <br/><br/> //FTPProfile Object   
 *    <br/>FTPProfile profile1 = ftpProfilePicker.getSelectedFTPProfile();   
 *    <br/> if(profile1 == null) {
 *    <br/>    logger.warn("Please define first a Profile");
 *    <br/>    return;
 *    <br/> }
 *     
 *     <br/><br/>//Verbindung zum FTP Server aufbauen
 *     <br/>sosfileTransfer = profile1.connect();
 *     <br/>long j = sosfileTransfer.getFile("/home/test/temp/1.txt", "c:/temp/1/1.txt", false);
 *     <br/>System.out.println("Datei per FTP holen und in Verzeichnis(=c:/temp/1/1.txt) ablegen: Bytes " + j);
 *     						
 * 
 * @author Mueruevet Oeksuez
 *
 */
public class FTPProfilePicker extends Composite {



	/** Button: �ffnet einen Dialog zur Erstellen, Bearbeiten und l�schen von FTP Zug�nge und */
	private        Button               button       = null;

	/** Auswahl der konfigurierten Zugangsdaten */
	private        Combo                cboProfile   = null;

	/** Konfigurationsdatei. Im Dialog konfigurierten Profile werden hier gespeichert.*/
	private        File                 configFile   = null;

	/**  @see sos.ftp.profiles.FTPDialogListener*/
	private        FTPDialogListener    listener     = null;

	/** Dilaog zum anlegen, bearbeiten und l�schen von FTP Zug�ngen*/
	private        FTPProfileDialog     profileDialog= null;


	/**
	 * Konstruktor
	 * @param parent 
	 * @param style
	 * @param configFile_ -> Konfigurationsdatei, indem die FTP Zug�nge gespeichert sind
	 */
	public FTPProfilePicker(Composite parent, int style, String configFile_) {
		super(parent, style);
		try {
			if(configFile_ == null)
				throw new Exception("Config File is null");

			configFile = new File(configFile_);						

			initialize();
			init();
		} catch (Exception e){
			FTPProfile.log("error in FTPProfilePicker.init()" + ", cause: " + e.toString(), 1);    		
		}

	}

	/**
	 * Konstruktor
	 * 
	 * configFile_ -> Konfigurationsdatei, indem die FTP Zug�nge gespeichert sind.
	 * Wenn die Konfigurationsdatei nicht existiert, dann wird dieser neu angelegt.
	 * 
	 * @param parent 
	 * @param style
	 * @param configFile_ 
	 */
	public FTPProfilePicker(Composite parent, int style, File configFile_) {
		super(parent, style);
		try {

			configFile = configFile_;
			initialize();
			init();
		} catch (Exception e){
			FTPProfile.log("error in FTPProfilePicker.init()" + ", cause: " + e.toString(), 1);    		
		}

	}

	/**
	 * Initialisierung
	 * @throws Exception
	 */
	private  void init() throws Exception {
		try {
			FTPProfile.log("calling " + sos.util.SOSClassUtil.getMethodName(), SOSLogger.DEBUG9);
			FTPProfile.log("Configuration File: " + (configFile != null ? configFile.getCanonicalPath() : ""), SOSLogger.DEBUG9);
			if(!configFile.exists()) {
				if(!configFile.createNewFile()) {    			
					FTPProfileDialog.message("Could not create config file: " + configFile, SWT.ICON_WARNING);
					throw new Exception("Could not create config file: " + configFile);
				}
			}

			profileDialog = new FTPProfileDialog(configFile);

			profileDialog.fillCombo(cboProfile);
		} catch (Exception e) {
			throw new Exception("error in " + sos.util.SOSClassUtil.getClassName() + ", cause: " + e.toString());
		}
	}


	/**
	 * 
	 * @throws Exception
	 */
	private void initialize() throws Exception  {
		try {
			FTPProfile.log("calling " + sos.util.SOSClassUtil.getMethodName(), SOSLogger.DEBUG9);
			GridData gridData2 = new GridData();
			gridData2.horizontalAlignment = GridData.BEGINNING; // Generated
			gridData2.verticalAlignment = GridData.FILL; // Generated
			GridData gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL; // Generated
			gridData.grabExcessHorizontalSpace = true; // Generated
			gridData.verticalAlignment = GridData.FILL; // Generated
			GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 2; // Generated
			gridLayout.verticalSpacing = 0; // Generated
			gridLayout.marginWidth = 0; // Generated
			gridLayout.marginHeight = 0; // Generated
			gridLayout.horizontalSpacing = 0; // Generated

			cboProfile = new Combo(this, SWT.READ_ONLY | SWT.BORDER);
			cboProfile.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(final SelectionEvent e) {

					if(cboProfile.getText().length() > 0) {
						listener = (FTPDialogListener)cboProfile.getData();
						listener.setCurrProfileName(cboProfile.getText());
					}
					try {
						listener.getCurrProfile().disconnect();
					} catch (Exception ee) {
						
					}

				}
			});

			cboProfile.setLayoutData(gridData);
			cboProfile.setText(""); 

			button = new Button(this, SWT.NONE);
			button.setText("Profile..."); 
			button.setLayoutData(gridData2);
			this.setLayout(gridLayout); 
			button.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

					profileDialog.showModal(cboProfile);
				}
			});
			setSize(new Point(242, 45));
		} catch (Exception e) {
			throw new Exception("error in " + sos.util.SOSClassUtil.getClassName() + ", cause: " + e.toString());
		}
	}


	/**
	 * 
	 */
	public void dispose() {
		FTPProfile.log("calling dispose", SOSLogger.DEBUG9);
		super.dispose();
	}

	/**
	 * Deaktivieren der FTPProfilePicker
	 */
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		cboProfile.setEnabled(enabled);
		button.setEnabled(enabled);
	}


	/**
	 * FTPProfilePicker unsichtbar setzen
	 */
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		cboProfile.setVisible(visible);
		button.setVisible(visible);
	}

	public void removeModifyListener(ModifyListener listener) {
		cboProfile.removeModifyListener(listener);
	}

	public void setToolTipText(String string) {
		super.setToolTipText(string);
		cboProfile.setToolTipText(string);

	}

	/**
	 * �ndern der Button Text
	 * @param txt
	 */
	public void setButtonText(String txt){
		if(txt != null)
			button.setText(txt);
		else
			button.setText("");
	}

	/**
	 * Holt einen FTP Zugangsdaten mit der Profilename "name" .
	 * 
	 * Existieren zu der name keine Zugangsdaten, dann wird null geliefert.
	 * 
	 * @param name
	 * @return FTPProfile Objeckt, wenn ein Profile mit der name existiert. Sonst wird ein null geliefert.
	 * @throws Exception
	 */
	public FTPProfile getProfileByName(String name) throws Exception{
		try {
			FTPProfile.log("calling " + sos.util.SOSClassUtil.getMethodName(), SOSLogger.DEBUG9);
			if(listener == null) {
				listener = (FTPDialogListener)cboProfile.getData();
			}

			if(listener.getProfiles() != null && listener.getProfiles().get(name) != null) {
				listener.setCurrProfileName(name);
				cboProfile.setText(name);
				return (FTPProfile)listener.getProfiles().get(name);
			}
			return null;
		} catch (Exception e) {
			throw new Exception("error in " +  sos.util.SOSClassUtil.getMethodName() + ", cause: " + e.toString());
		}
	}

	/**
	 * Holt den aktuelle Profile und liefert die Zugangsdaten (als sos.ftp.profiles.FTPProfile Objekt).
	 * 
	 * Existieren keine Zugangsdaten dann wird eine null geliefert
	 * 
	 * @return FTPProfile
	 * @see sos.ftp.profiles.FTPProfile
	 * @throws Exception
	 */
	public FTPProfile getSelectedFTPProfile() throws Exception{
		try {
			FTPProfile.log("calling " + sos.util.SOSClassUtil.getMethodName(), SOSLogger.DEBUG9);
			if(listener == null) {
				listener = (FTPDialogListener)cboProfile.getData();
			}

			if(getSelectedProfilename() != null) {
				return (FTPProfile)listener.getProfiles().get(getSelectedProfilename());
			}
			return null;
		} catch (Exception e) {
			throw new Exception("error in " +  sos.util.SOSClassUtil.getMethodName() + ", cause: " + e.toString());
		}
	}

	/**
	 * Setzt die sos.util.SOSLogger Objekt
	 * @param sos.util.SOSLogger the logger to set
	 */
	public void setLogger(SOSLogger logger_) {
		FTPProfile.logger = logger_;		
	}

	/**
	 * Wurde ein Text als Parameter �bergeben dann werden alle FTP Server antworten 
	 * hier geschrieben.
	 * 
	 * @param Text text_
	 */
	public void setLogText(Text text_) {
		//FTPDialogListener.logtext = text_;
		FTPProfile.logtext = text_;
	}

	/**
	 * Liefert den aktuellen Zugangsname, der in der Auswahlbox steht
	 * @return
	 */
	public String getSelectedProfilename() {
		return cboProfile.getText();
	}

	/**
	 * Setzt einen neuen Profilename
	 * 
	 * @param profilename
	 */
	public void setProfilename(String profilename) {
		cboProfile.setText(profilename);
	}

	public FTPDialogListener getListener() {	
		if(listener == null)
			listener = (FTPDialogListener)cboProfile.getData();
		return this.listener;
	}

	/**
	 * Events beim �nderen der Auswahlbox  
	 * @param listener
	 */
	public void addModifyListener(ModifyListener listener) {
		cboProfile.addModifyListener(listener);
	}

	/**
	 * Event, beim Ausw�hlen einer neuen Profile
	 * @param listener
	 */
	public void addSelectionListener(SelectionAdapter listener){

		cboProfile.addSelectionListener(listener);
	}

	/**
	 * Erlaubt das hinzuf�gen einer leeren Eintrag in den Auswahlbox
	 * @throws Exception
	 */
	public void addEmptyItem() throws Exception{
		FTPProfile.log("calling " + sos.util.SOSClassUtil.getMethodName(), SOSLogger.DEBUG9);
		profileDialog.fillCombo(cboProfile, true);
	}


} // @jve:decl-index=0:visual-constraint="10,10"
