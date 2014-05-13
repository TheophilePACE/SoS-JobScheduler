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
 * Created on 06.04.2011
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.sos.dialog.comparators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.sos.hibernate.classes.SosSortTableItem;


/**
 * 
 * @author Administrator
 * Eigener Comparator der das Vergleichen der einzelnen Tabellenzeilen vornimmt... 
 */

public class DateComperator extends SortBaseComparator implements Comparable {

	/**
	 * Konstruktor ...
	 * @param textBuffer : Der Text der Zeile 
	 * @param rowNum : Die Zeilennr der Tabellenzeile 
	 * @param sortFlag : Aufsteigend false, Absteigend true
	 * @param colPos : Die Spalte nach der Sortiert werden soll
	 */

	public DateComperator(SosSortTableItem tableItem, int rowNum, int colPos) {
		super(tableItem,rowNum,colPos);
	}

	public final int compareTo(Object arg0) {
		
		if (sosSortTableItem.getTextBuffer()[_colPos] == null) {
			sosSortTableItem.getTextBuffer()[_colPos] = "2000-01-01 00:00:00";
		}
		SosSortTableItem compareItem = null;
		if (((DateComperator) arg0) == null) {
			compareItem = new SosSortTableItem();
			compareItem.setTextBuffer(sosSortTableItem.getTextBuffer());
		}else {
			compareItem = ((DateComperator) arg0).sosSortTableItem;
		}
		
		if  ( compareItem.getTextBuffer()[_colPos] == null){
			 compareItem.getTextBuffer()[_colPos] = "2000-01-01 00:00:00";
		}
		
		String s1 = sosSortTableItem.getTextBuffer()[_colPos];
		if (s1.equals("")){
			s1 = "2001-01-01 00:00:00";
		}
		String s2 = compareItem.getTextBuffer()[_colPos];
		if (s2.equals("")){
			s2 = "2001-01-01 00:00:00";
		}
		String isoDateTimeFormat = "yyyy-MM-dd HH:mm:ss";
        String isoDateFormat = "yyyy-MM-dd";

        SimpleDateFormat formatter = new SimpleDateFormat(isoDateTimeFormat);
        SimpleDateFormat formatterDate = new SimpleDateFormat(isoDateFormat);
 		
 		Calendar now = Calendar.getInstance();
        String today = formatterDate.format(now.getTime());

        Date d1 = new Date();
		try {
		   d1 = formatter.parse(s1);
		}
		catch (ParseException e) {
   		   try {
			d1 = formatter.parse(today + " " + s1);
		}
		catch (ParseException e1) {
			try {
				d1 = formatter.parse("2000:01:01 00:00:00");
			}
			catch (ParseException e2) {
 				e2.printStackTrace();
			}
		}
		}
		
		Date d2 = new Date();
		try {
		   d2 = formatter.parse(s2);
		}
		catch (ParseException e) {
   		   try {
			d2 = formatter.parse(today + " " + s2);
		}
		catch (ParseException e1) {
			try {
				d2 = formatter.parse("2000:01:01 00:00:00" );
			}
			catch (ParseException e2) {
				e2.printStackTrace();
			}

		}
		}
		
		
		int ret = 0;
		if (d1.before(d2)){
			ret = -1;
		}
		if (d2.before(d1)){
			ret = 1;
		}

		return _sortFlag ? ret : ret * -1;
	}

}
