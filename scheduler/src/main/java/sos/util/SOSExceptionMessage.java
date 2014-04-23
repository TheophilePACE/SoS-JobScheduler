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
package sos.util;


import java.lang.Throwable;
import java.sql.SQLException;

/**
 * SOSExceptionHandler.java
 * 
 * Diese Klasse faßt eine Fehlermeldung/Exception in String um. 
 * Berücksichtigt werden insbesondere die Verschachtelte Exceptions, wie
 * z.B. org.apache.commons.dbcp.SQLNestedException. Die org.apache.commons.dbcp.SQLNestedException
 * werden iteriert und zu einem String zusammengefaßt. 
 * 
 * @author Mürüvet Öksüz
 *
 * @version 1.0
 * 
 */
public class SOSExceptionMessage extends Throwable {
	
	
	/**
	 * 
	 * 
	 * @param exception
	 * @return
	 */
	public static String getExceptionMessage(Exception exception) {
		String msg = "";
		
		try {
			
			if (exception instanceof SQLException) { 
					//|| exception instanceof javax.mail.MessagingException) {
				
				
				SQLException sqlExcep = (SQLException)exception ;				
				while( sqlExcep !=null){
					
					if (sqlExcep.equals(sqlExcep.getNextException())) {
						break;	
					} 					
					
					msg = sqlExcep.toString();
					if (sqlExcep.getCause() != null) {
						msg = msg +  "\n" + sqlExcep.getCause();					
					}  	       		 
					sqlExcep = sqlExcep.getNextException();
					
				} 				
				
			} else {
				msg = exception.toString();
				if (exception.getCause() != null) {
					msg = exception.toString() + " " + exception.getCause();				
				} 
				
			}
		} catch (Exception e) {
			System.out.print(e);
		}
		return msg;
	}	
	
	/**
	 * Testprogramm
	 * 
	 * Zum Testen der Klasse SOSExceptionMessage werden
	 * Fehlermeldungen generiert. 
	 * 
	 * test = 0 -> NullPointerException
	 * test = 1 -> division by 0 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		String s = null;
		int i = 0;		
		int test = 0;
		try {
			
			if (test == 0) {
				boolean w= s.equals("a");
			} else if (test == 1) {
				i = 1 / 0;
			}
			
		} catch (Exception e) {			
			String a = getExceptionMessage(e);
			System.out.print("..error: " + a);
		}
	}
}
