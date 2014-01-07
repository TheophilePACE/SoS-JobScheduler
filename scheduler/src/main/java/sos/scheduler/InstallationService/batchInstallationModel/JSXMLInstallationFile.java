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
/*
 * Created on 28.02.2011
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package sos.scheduler.InstallationService.batchInstallationModel;

import java.io.File;
 
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Result;
 

import sos.scheduler.InstallationService.batchInstallationModel.installationFile.AutomatedInstallation;
import sos.scheduler.InstallationService.batchInstallationModel.installationFile.ComIzforgeIzpackPanelsFinishPanel;
import sos.scheduler.InstallationService.batchInstallationModel.installationFile.ComIzforgeIzpackPanelsHTMLLicencePanel;
import sos.scheduler.InstallationService.batchInstallationModel.installationFile.ComIzforgeIzpackPanelsInstallPanel;
import sos.scheduler.InstallationService.batchInstallationModel.installationFile.ComIzforgeIzpackPanelsProcessPanel;
import sos.scheduler.InstallationService.batchInstallationModel.installationFile.ComIzforgeIzpackPanelsTargetPanel;
import sos.scheduler.InstallationService.batchInstallationModel.installationFile.ComIzforgeIzpackPanelsUserInputPanel;
import sos.scheduler.InstallationService.batchInstallationModel.installationFile.ComIzforgeIzpackPanelsUserPathPanel;
import sos.scheduler.InstallationService.batchInstallationModel.installationFile.Entry;
import sos.scheduler.InstallationService.batchInstallationModel.installationFile.ObjectFactory;
import sos.scheduler.InstallationService.batchInstallationModel.installationFile.UserInput;
import sos.scheduler.InstallationService.batchInstallationModel.installations.Installation;
 
public class JSXMLInstallationFile {
   private  AutomatedInstallation  automatedInstallation;
 
   public JSXMLInstallationFile()  {
	  super();
    }
   
   public void setValues(Installation installation)  {
	  
	  ObjectFactory o = new ObjectFactory();
	  
	  automatedInstallation = o.createAutomatedInstallation();

  
	  ComIzforgeIzpackPanelsUserInputPanel home = o.createComIzforgeIzpackPanelsUserInputPanel();
	  UserInput userInput_home = o.createUserInput();
	  home.setUserInput(userInput_home);
	  home.setId("home");
  	  automatedInstallation.getComIzforgeIzpackPanelsUserInputPanelOrComIzforgeIzpackPanelsHTMLLicencePanelOrComIzforgeIzpackPanelsTargetPanel().add(home);	  
      
	  ComIzforgeIzpackPanelsUserInputPanel licences = o.createComIzforgeIzpackPanelsUserInputPanel();
	  UserInput userInput_licences = o.createUserInput();
	  licences.setUserInput(userInput_licences);
	  licences.setId("licences");
	  Entry entryLicence = o.createEntry();
	  entryLicence.setKey("licence");
	  entryLicence.setValue(installation.getLicence());
	  licences.getUserInput().getEntry().add(entryLicence);
	  
	  Entry entryLicencOptions = o.createEntry();
	  entryLicencOptions.setKey("licenceOptions");
	  entryLicencOptions.setValue(installation.getLicenceOptions());
	  licences.getUserInput().getEntry().add(entryLicencOptions);

	  automatedInstallation.getComIzforgeIzpackPanelsUserInputPanelOrComIzforgeIzpackPanelsHTMLLicencePanelOrComIzforgeIzpackPanelsTargetPanel().add(licences);
	  
	  ComIzforgeIzpackPanelsHTMLLicencePanel comIzforgeIzpackPanelsHTMLLicencePanelGpl = o.createComIzforgeIzpackPanelsHTMLLicencePanel();
	  comIzforgeIzpackPanelsHTMLLicencePanelGpl.setId("gpl_licence");
	  automatedInstallation.getComIzforgeIzpackPanelsUserInputPanelOrComIzforgeIzpackPanelsHTMLLicencePanelOrComIzforgeIzpackPanelsTargetPanel().add(comIzforgeIzpackPanelsHTMLLicencePanelGpl);
	  
	  ComIzforgeIzpackPanelsHTMLLicencePanel comIzforgeIzpackPanelsHTMLLicencePanelCommercial = o.createComIzforgeIzpackPanelsHTMLLicencePanel();
	  comIzforgeIzpackPanelsHTMLLicencePanelGpl.setId("commercial_licence");
	  automatedInstallation.getComIzforgeIzpackPanelsUserInputPanelOrComIzforgeIzpackPanelsHTMLLicencePanelOrComIzforgeIzpackPanelsTargetPanel().add(comIzforgeIzpackPanelsHTMLLicencePanelCommercial);

	  ComIzforgeIzpackPanelsTargetPanel comIzforgeIzpackPanelsTargetPanel = o.createComIzforgeIzpackPanelsTargetPanel();
	  comIzforgeIzpackPanelsTargetPanel.setId("target");
	   
	  comIzforgeIzpackPanelsTargetPanel.setInstallpath(installation.getInstallPath());
	  automatedInstallation.getComIzforgeIzpackPanelsUserInputPanelOrComIzforgeIzpackPanelsHTMLLicencePanelOrComIzforgeIzpackPanelsTargetPanel().add(comIzforgeIzpackPanelsTargetPanel);
	  
	  ComIzforgeIzpackPanelsUserPathPanel comIzforgeIzpackPanelsUserPathPanel = o.createComIzforgeIzpackPanelsUserPathPanel();
	  comIzforgeIzpackPanelsUserPathPanel.setId("userpath");
	  comIzforgeIzpackPanelsUserPathPanel.setUserPathPanelElement(installation.getUserPathPanelElement());
	  automatedInstallation.getComIzforgeIzpackPanelsUserInputPanelOrComIzforgeIzpackPanelsHTMLLicencePanelOrComIzforgeIzpackPanelsTargetPanel().add(comIzforgeIzpackPanelsUserPathPanel);
	  
	   
	  ComIzforgeIzpackPanelsUserInputPanel network = o.createComIzforgeIzpackPanelsUserInputPanel();
	  UserInput userInput_network = o.createUserInput();
	  network.setUserInput(userInput_network);
	  network.setId("network");

	  Entry entry_schedulerHost = o.createEntry();
	  entry_schedulerHost.setKey("schedulerHost");
	  entry_schedulerHost.setValue(installation.getHost());
	  network.getUserInput().getEntry().add(entry_schedulerHost);

	  Entry entry_schedulerId = o.createEntry();
	  entry_schedulerId.setKey("schedulerId");
	  entry_schedulerId.setValue(installation.getSchedulerId());
	  network.getUserInput().getEntry().add(entry_schedulerId);
	  
	  Entry entry_schedulerAllowedHost = o.createEntry();
	  entry_schedulerAllowedHost.setKey("schedulerAllowedHost");
	  entry_schedulerAllowedHost.setValue(installation.getSchedulerAllowedHost());
	  network.getUserInput().getEntry().add(entry_schedulerAllowedHost);
	  
	  Entry entry_schedulerPort = o.createEntry();
	  entry_schedulerPort.setKey("schedulerPort");
	  entry_schedulerPort.setValue(String.valueOf(installation.getSchedulerPort()));
	  network.getUserInput().getEntry().add(entry_schedulerPort);
	  automatedInstallation.getComIzforgeIzpackPanelsUserInputPanelOrComIzforgeIzpackPanelsHTMLLicencePanelOrComIzforgeIzpackPanelsTargetPanel().add(network);
	  
	  ComIzforgeIzpackPanelsInstallPanel comIzforgeIzpackPanelsInstallPanel = o.createComIzforgeIzpackPanelsInstallPanel();
	  comIzforgeIzpackPanelsInstallPanel.setId("install");
	  automatedInstallation.getComIzforgeIzpackPanelsUserInputPanelOrComIzforgeIzpackPanelsHTMLLicencePanelOrComIzforgeIzpackPanelsTargetPanel().add(comIzforgeIzpackPanelsInstallPanel);

	  ComIzforgeIzpackPanelsProcessPanel comIzforgeIzpackPanelsProcessPanel = o.createComIzforgeIzpackPanelsProcessPanel();
	  comIzforgeIzpackPanelsProcessPanel.setId("process");
	  automatedInstallation.getComIzforgeIzpackPanelsUserInputPanelOrComIzforgeIzpackPanelsHTMLLicencePanelOrComIzforgeIzpackPanelsTargetPanel().add(comIzforgeIzpackPanelsProcessPanel);
	  
	  ComIzforgeIzpackPanelsFinishPanel comIzforgeIzpackPanelsFinishPanel = o.createComIzforgeIzpackPanelsFinishPanel();
	  comIzforgeIzpackPanelsFinishPanel.setId("finish");
	  automatedInstallation.getComIzforgeIzpackPanelsUserInputPanelOrComIzforgeIzpackPanelsHTMLLicencePanelOrComIzforgeIzpackPanelsTargetPanel().add(comIzforgeIzpackPanelsFinishPanel);
	 
   }
   
   public void writeFile(File output) throws JAXBException {
	  JAXBContext context = JAXBContext.newInstance( AutomatedInstallation.class );
 	  Marshaller m = context.createMarshaller();
	  m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
	  m.marshal( automatedInstallation, (Result) output );	  
   }
   
}
