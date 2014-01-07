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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;

import sos.scheduler.InstallationService.batchInstallationModel.installations.Globals;
import sos.scheduler.InstallationService.batchInstallationModel.installations.Installation;
import sos.scheduler.InstallationService.batchInstallationModel.installations.Installations;
 
public class JSInstallations {
   
   private ArrayList<Installation> listOfInstallations;
   private File installationsDefinitionFile;
   private Iterator <Installation> jsInstallationsIterator;
   private  Installations installations;
 
   
   public JSInstallations(File installationsDefinitionFile_) throws Exception {
	  super();
	  this.installationsDefinitionFile = installationsDefinitionFile_;
	  readInstallationDefinitionFile();
   }
   
  
  public void reset() {
 	  jsInstallationsIterator = listOfInstallations.iterator();
   }
   
   public JSinstallation next() {
	  Installation i = jsInstallationsIterator.next();
	  JSinstallation j = new JSinstallation();
	  j.globals = installations.getGlobals();
	  j.setValues(i);
  	  return j;
   }
   
  
   public Installations getInstallations() {
	return installations;
   }


public Installation nextInstallation() {
	  Installation i = jsInstallationsIterator.next();
  	  return i;
   }  
   public boolean eof() {
	  return !jsInstallationsIterator.hasNext();
   }

  public void readInstallationDefinitionFile() throws JAXBException  {
	 JAXBContext context = JAXBContext.newInstance( Installations.class );
	 Unmarshaller unmarshaller = context.createUnmarshaller();
 
	 installations = (Installations) unmarshaller.unmarshal( installationsDefinitionFile );
	 listOfInstallations = (ArrayList)installations.getInstallation();
	 reset();
   }
   
   public void writeFile(File output) throws JAXBException, ParseException {
	  JAXBContext context = JAXBContext.newInstance( Installations.class );

 	  Marshaller m = context.createMarshaller();
 	  m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
	  m.marshal( installations, (Result) output );	  
	  
   }
   
   
   
}
