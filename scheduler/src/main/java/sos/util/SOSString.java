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


/**
 * <p>Title: </p>
 * <p>Description: Stringverarbeitung</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: SOS GmbH</p>
 * @author <a href="mailto:ghassan.beydoun@sos-berlin.com">Ghassan Beydoun</a>
 * @version $Id: SOSString.java 581 2005-02-04 09:16:44Z gb $
 */


public class SOSString {


  /**
   * <p>Liefert true falls die eingegebene Zeichenkette leer oder null ist, sonst false</p>
   *
   * <pre>
   * SOSStringisEmpty(null) liefert true
   * SOSStringisEmpty("") liefert true
   * SOSStringisEmpty(" ") liefert false
   * </pre>
   *
   * @param string der geprft werden soll
   * @return <code>true</code> falls string leer oder null, sonst false
   */
  public static boolean  isEmpty( String string ) {
    return ( string == null || string.length() == 0 );
  }
  
  /**
	  * Konvertiert key in Boolean. 
	  * Diese Methode liefert true, wenn der Key  
	  * 	 "1"    oder 
	  * 	 "yes"  oder
	  * 	 "true" ist.
	  * Sonst wird false geliefert 
	  * Dabei werden Gro� und Kleinschreibung nicht beachtet.	
	  *
	  * @author Mrvet �sz
	  *	
	  * @param key 
	  * @return String
	  * @exception Exception
	  */
	  public boolean parseToBoolean(Object key) throws Exception {
		  try {
			  if (key != null) {
				  if (key.toString().equalsIgnoreCase("true")
					  || key.toString().equalsIgnoreCase("yes") 
					  || key.equals("1")) {
					  return true;
				  } else {
					  return false;
				  }
			  } else {
				 return false;
			  }
		  } catch(Exception e) {
			  throw new Exception ("..error in " + SOSClassUtil.getMethodName() + " " + e);
		  }
	  }
	  
	
	/**
	 * Hat die Hashtabelle ein Feld key, so wird der Inhalt dieses Key in String konvertiert
	 * und bergeben. Ist der Hashtabelle leer, dann wird ein Leerstring bergeben.
	 *
	 * @author Mrvet �sz
	 *
	 * @param hash 
	 * @param key 
	 * @return String
	 * @exception Exception
	 */
	public String parseToString(java.util.HashMap hash, String key) throws Exception {
		try {
			if (hash != null && hash.get(key) != null) {
				return hash.get(key).toString();
			}	else {
			  return "";
			}
		} catch(Exception e) {
			throw new Exception ("..error in " + SOSClassUtil.getMethodName() + " " + e);
		}
		 
	}

	/**
	 * Konvertiert key in String, wenn dieser nicht null ist. Sonst wird ein Leerstring 
	 * bergeben. 	  
	 *
	 * @author Mrvet �sz
	 *
	 * @param key 
	 * @return String
	 * @exception Exception
	 */
	public String parseToString(Object key) throws Exception {
		try {
			if (key != null) {
				return key.toString();
			} else {
			  return "";
			}
		} catch(Exception e) {
			throw new Exception ("..error in " + SOSClassUtil.getMethodName() + " " + e);
		}
	}
	
	
	/**
	 * Wenn das Objekt Properties einen key hat, dann wird dieser in String konvertiert
	 * und bergeben.  
	 * Sonst wird ein leerstring bergeben.
	 *
	 * @author Mrvet �sz
	 * 
	 * @param prop
	 * @param key
	 * @exception Exception
	 */
	public String parseToString(java.util.Properties prop, String key) throws Exception{
		try {
			if (prop.get(key) != null) {
				return prop.get(key).toString();
			} else {
				return "";
			}
		} catch(Exception e) {
			throw new Exception ("..error in " + SOSClassUtil.getMethodName() + " " + e);
		}
  }


}
