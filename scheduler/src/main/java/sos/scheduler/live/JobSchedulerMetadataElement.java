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
package sos.scheduler.live;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;


public class JobSchedulerMetadataElement {
   
   public String pkid                = "";
   public String parent_id           = "";
   public String element_path        = "";
   public String element_name        = "";
   public String table_name          = "";
   public HashMap attributes         ;
   public String attribute           = "";
   public int nesting                = 0;
   public ArrayList elements	        = null;
   
   public JobSchedulerMetadataElement(HashMap rec) {
	  super();
 	  pkid          = getValue(rec,"pk_id");
	  parent_id     = getValue(rec,"parent_id");
	  try {
	   nesting      = Integer.parseInt(getValue(rec,"nesting")); 
	  }catch (NumberFormatException n) {nesting = 0;}
	  element_name  = getValue(rec,"element_name");
	  table_name    = getValue(rec,"table_name");
	  element_path    = getValue(rec,"element_path");
	  StringTokenizer t = new StringTokenizer(element_path,"/");
	  elements = new ArrayList();
	  while (t.hasMoreTokens()) {
		 String token = t.nextToken().trim();
		 elements.add(token);
	  }			
    }
   
   public String fieldnames() {
	  String erg = "";
   
	  Iterator i = attributes.keySet().iterator();
	  while (i.hasNext()){
		 erg += "\""+i.next().toString().toUpperCase() + "\",";
	  }

	  if (element_name.toLowerCase().equals("script")) {
			erg += "\"CDATA\",";
	  }
	  
	  
	  if (erg.length() > 0) erg = "," + erg.substring(0,erg.length()-1);
	  
	  return erg;
   }
   
   public String fieldvalues() {
	  String erg = "";
	  Iterator i = attributes.values().iterator();
	  while (i.hasNext()){
		 erg += "'"+i.next().toString() + "',";
	  }

	  if (element_name.toLowerCase().equals("script")) {
			erg += "'',";
	  }

	  if (erg.length() > 0) erg = "," + erg.substring(0,erg.length()-1);
	  return erg;
   }
   
   
   private String getValue(HashMap h, String k) {
      String erg = "";
      try {
      if (h.containsKey(k) && h.get(k) == null) {
   	  erg = "";
      }else {
         erg = h.get(k).toString();
      }
      return erg;
      }catch (Exception e) {return "";}
   }
   
}
