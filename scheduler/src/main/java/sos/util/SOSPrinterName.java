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
/*
 * Created on 28.04.2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package sos.util;
import javax.print.PrintService;
import java.awt.print.PrinterJob;
/**
 * @author mo
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SOSPrinterName {   	
	
	/**
	 * Schreibt alle Namen der Drucker auf standard output. Manche Namen
   * differenzieren sich im Gegensatz zu dem, was im Druckerdialog steht. 
	 *
	 */
	public static void getPrinterName() {
		try {
			System.out.println("");
			System.out.println("Default Printername is: ");
			System.out.println(java.awt.print.PrinterJob.getPrinterJob().getPrintService().getName());
			System.out.println("");
			System.out.println("List of All Printername: ");				    
			PrintService[] printService = PrinterJob.lookupPrintServices();
			for (int i = 0; i < printService.length; i++) {
				System.out.println(i + " Printer name: " + printService[i].getName());
			}
		} catch (Exception e) {
		  System.err.print(e);
		}
	}
	
	/**
		* Liefert alle Namen der Default Drucker auf den Rechner. 
		* @return String
		*/
		public static String getDefaultPrinterName() {
			try {								
				return java.awt.print.PrinterJob.getPrinterJob().getPrintService().getName();				
			} catch (Exception e) {
			  return "Error: " + e.toString();
			}
		}
		/**
		 * Liefert alle Namen der Drucker in einer ArrayListe
		 * @return String
		 */
		public static java.util.ArrayList getAllPrinterName() throws Exception{
				java.util.ArrayList retVal = new java.util.ArrayList();
				try {
					PrintService[] printService = PrinterJob.lookupPrintServices();								
					for (int i = 0; i < printService.length; i++) {
						retVal.add(printService[i].getName());
					}	
					return retVal;			
				} catch (Exception e) {
				  throw new Exception (e); 
				}
			}
		
}
