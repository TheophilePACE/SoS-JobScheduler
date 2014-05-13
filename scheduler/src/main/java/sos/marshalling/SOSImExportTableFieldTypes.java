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
package sos.marshalling;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

  /**
   * <p>Title: FieldTypes</p>
   * <p>Description: Kontainer f&uuml;r Tabellenfelder und deren Typbeschreibung.</p>
   * <p>Copyright: Copyright (c) 2004</p>
   * <p>Company: SOS-Berlin GmbH</p>
   * @author Titus Meyer
   * @version 1.2
   */
  public class SOSImExportTableFieldTypes {
   /** HashMap, &uuml;ber die die Attribute der Felder aufgerufen werden k&ouml;nnen. */
   private HashMap _fieldTypes = new HashMap();
   /** zugeh&ouml;rige Tabellenname */
   private String  _tableName  = null;
   
   /**
    * F&ouml;gt ein Tabellenfeld mit deren Typbeschreibung hinzu.
    * 
    * @param name Name des Tabellenfeldes.
    * @param typeName Name des Feldtyps.
    * @param type ID des Feldtyps (java.sql.Types)
    * @param length L&auml;nge des Feldtyps.
    * @param scale Scale des Feldes.
    */
   public void addField(String name, String typeName, Integer type, BigInteger length, Integer scale) {
     ArrayList field = new ArrayList(5);
     field.add(name);
     field.add(typeName);
     field.add(type);
     field.add(length);
     field.add(scale);
     _fieldTypes.put(name, field);
   }

   /**
    * Liefert den Feldtyp als String f&uuml;r ein Feld.
    * 
    * @param field Name des Tabellenfeldes.
    * @return String des Feldtyps.
    */
   public String getTypeName(String field) {
     ArrayList fieldType = (ArrayList)_fieldTypes.get(field);
     if(fieldType != null) return (String)fieldType.get(1);
     else return null;
   }
   
   /**
    * Liefert die ID des Feldtyps f&uuml;r ein Feld.
    * 
    * @param field Name des Tabellenfeldes.
    * @return Typ-ID (java.sql.Types)
    */
   public int getTypeId(String field) {
     ArrayList fieldType = (ArrayList)_fieldTypes.get(field);
     if(fieldType != null) return ((Integer)fieldType.get(2)).intValue();
     else return -1;
   }
    
   /**
    * Liefert die Feldtypl&auml;nge eines Feldes.
    * 
    * @param field Name des Tabellenfeldes.
    * @return L&auml;nge des Typs.
    */
   public BigInteger getLength(String field) {
     ArrayList fieldType = (ArrayList)_fieldTypes.get(field);
     if(fieldType != null) return ((BigInteger)fieldType.get(3));
     else return new BigInteger("-1");
   }

   /**
    * Liefert den Scale eines Tabellenfeldes.
    * 
    * @param field Name des Tabellenfeldes.
    * @return Scale des Feldes.
    */
   public int getScale(String field) {
     ArrayList fieldType = (ArrayList)_fieldTypes.get(field);
     if(fieldType != null) return ((Integer)fieldType.get(4)).intValue();
     else return -1;
   }
   
   /**
    * Speichert den Namen der Tabelle.
    * 
    * @param table Tabellenname
    */
   public void setTable(String table) {
     _tableName = table;
   }
   
   /**
    * Liefert den Namen der Tabelle.
    * 
    * @return Tabellenname
    */
   public String getTable() {
     return _tableName;
   }
   
   /**
    * Liefert einen durch Kommatas getrennten String der Schl&uuml;sselfelder
    * 
    * @return Keystringliste
    */
   public String getKeyString() {
     StringBuffer str = new StringBuffer();
     for(Iterator it = _fieldTypes.keySet().iterator(); it.hasNext(); ) {
     	 str.append("\"" + it.next().toString() + "\"");
       if(it.hasNext()) str.append(", ");
     }
     return str.toString();
   }
   
   /**
    * Liefert einen Iterator &uuml;ber die Schl&uuml;sselfelder
    * 
    * @return Iterator
    */
   public Iterator getIterator() {
     return _fieldTypes.keySet().iterator();
   }
   
   /**
    * Liefert die Anzahl der Felder.
    * 
    * @return Felderanzahl
    */
   public int getSize() {
     return _fieldTypes.size();
   }
  }
