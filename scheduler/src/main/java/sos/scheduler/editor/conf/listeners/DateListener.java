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
package sos.scheduler.editor.conf.listeners;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;
import org.jdom.Element;
import sos.scheduler.editor.app.Editor;
import sos.scheduler.editor.app.Options;
import sos.scheduler.editor.app.TreeData;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.conf.SchedulerDom;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class DateListener implements Comparator {

	
    public static final int HOLIDAY      = 0;

    public static final int DATE         = 1;

    private SchedulerDom    _dom         = null;

    /** 0 = holiday 1 = date */
    private int             _type        = -1;

    private static String[] _elementName = { "holiday", "date" };

    private Element         _element     = null;

    private Element         _parent      = null;

    private List            _list        = null;

    private List            _listOfAt    = null;

    
    public DateListener(SchedulerDom dom, Element element, int type) {
        _dom = dom;
        _element = element;
        _type = type;

        if (type == 0 && _element != null)
            _parent = _element.getChild("holidays");
        else
            _parent = _element;
       
        if (_parent != null) {
            _list = _parent.getChildren(_elementName[_type]);
            if(type == 1) {            	
            	_listOfAt = _parent.getChildren("at");           	
            }
            sort();
        }
            
        
    }    
 
    public void fillList(org.eclipse.swt.widgets.List list) {
    	
        list.removeAll();
        if (_list != null) {
            Iterator it = _list.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                if (e.getAttributeValue("date") != null)
                    list.add(e.getAttributeValue("date"));
            }
        }
        if (_listOfAt != null) {
            Iterator it = _listOfAt.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                if (e.getAttributeValue("at") != null) {
                	String date = Utils.getAttributeValue("at", e);
                	date = date.substring(0, 10);                	
                	String[] split = date.split("-");
                	if(!exists(Utils.str2int(split[0]), Utils.str2int(split[1]), Utils.str2int(split[2])) &&
                			!dateExistInList(list, date)) {
                		list.add(date);
                	}
                }
            }
        }                   
        
    }        
    
    private boolean dateExistInList(TreeItem parent, String date) {
    	for(int i = 0; i < parent.getItemCount(); i++) {
    		if(parent.getItem(i).getText().equalsIgnoreCase(date)){
    			return true;
    		}
    	}
    	return false;
    }
    
    private boolean dateExistInList(org.eclipse.swt.widgets.List list, String date) {
    	for(int i = 0; i < list.getItemCount(); i++) {
    		if(list.getItem(i).equalsIgnoreCase(date)){
    			return true;
    		}
    	}
    	return false;
    }

    public Element addDate(int year, int month, int day) {
        if (_parent == null && _type == 0) {
            _parent = new Element("holidays");
            _element.addContent(_parent);
            _list = _parent.getChildren("holiday");
        }

        if (_list == null && _type == 0) {
          _list = _parent.getChildren("holiday");
        }
        
        if(_list == null && _type == 1) {
        	_list = _parent.getChildren(_elementName[_type]);
        }

        Element date = new Element(_elementName[_type]);
        date.setAttribute("date", asStr(year) + "-" + asStr(month) + "-" + asStr(day));
        
    
        _list.add(date);
        
        
        
        sort();
        _dom.setChanged(true);
        if(_dom.isDirectory() && _element.getParentElement() != null)
        	//_dom.setChangedForDirectory("job", Utils.getAttributeValue("name",_element.getParentElement()), SchedulerDom.MODIFY);
        	_dom.setChangedForDirectory(_element, SchedulerDom.MODIFY);
                
        return date;
    }


    public String asStr(int value) {
        if (value < 10)
            return "0" + value;
        else
            return "" + value;
    }


    public void removeDate(int index) {
    	String delDate = "";
    	
    	if (index >= 0 && index < _list.size()) {
    		delDate = Utils.getAttributeValue("date", (Element)(_list.get(index)));
    	}else {
    		
    		int i = _list.size() - (index); 
    		if(i < 0) 
    			i = i*(-1);
    		Element atE = (Element)(_listOfAt.get(i));
    		//System.out.println(atE.getAttributes());
    		delDate = Utils.getAttributeValue("at", atE).substring(0, 10);
    		
    	}
    	
    	if (index >= 0 && index < _list.size()) {
    			
    			_list.remove(index);
    			if (_list.size() == 0 && _type == 0) {
    				if(_parent.getChildren("include") == null || _parent.getChildren("include").isEmpty()) {
    					_element.removeChild("holidays");
    					_parent = null;
    				}
    			}
    			_dom.setChanged(true);
    			if(_dom.isDirectory() && _element.getParentElement() != null)
    	        	//_dom.setChangedForDirectory("job", Utils.getAttributeValue("name",_element.getParentElement()), SchedulerDom.MODIFY);
    				_dom.setChangedForDirectory(_element, SchedulerDom.MODIFY);
    				
    		} else {
    			if(_listOfAt != null && _listOfAt.size()>0) {
        		index = _list.size() - index;
        		if(index > -1)
        			_listOfAt.remove(index);
        	} else 
            System.out.println("Bad index " + index + " for " + _elementName[_type]);
        }
        
        //gibt es auch einen at-Element am gleichen Tag
    	if(_listOfAt != null) {
    		ArrayList remList = new ArrayList();
    		for(int i =0;  i < _listOfAt.size(); i++) {
    			Element e = (Element)_listOfAt.get(i);
    			String at = Utils.getAttributeValue("at", e);
    			String date = at.substring(0, at.indexOf(" "));
    			if(date.equalsIgnoreCase(delDate)) {
    				remList.add(e);        		
    			}
    		}
    		for(int i = 0; i < remList.size(); i++) {
    			_listOfAt.remove(remList.get(i));
    		}
    	}
    }


    public int[] getDate(int index) {
        Element element = (Element) _list.get(index);
        String date = element.getAttributeValue("date");
        String[] str = date.split("-");
        try {
            int[] values = new int[3];
            values[0] = new Integer(str[0]).intValue();
            values[1] = new Integer(str[1]).intValue();
            values[2] = new Integer(str[2]).intValue();
            return values;
        } catch (Exception ex) {
            System.out.println("Invalid " + _elementName[_type] + " date: " + date);
            element.setAttribute("date", "1970-01-01");
            return new int[] { 1970, 1, 1 };
        }
    }


    public int[] getNow() {
        Calendar cal = Calendar.getInstance();
        int[] date = new int[3];
        date[0] = cal.get(Calendar.YEAR);
        date[1] = cal.get(Calendar.MONTH) + 1;
        date[2] = cal.get(Calendar.DAY_OF_MONTH);
        return date;
    }


    public void fillTreeDays(TreeItem parent, boolean expand) {
        if (_list != null) {
            Iterator it = _list.iterator();
            parent.removeAll();

            while (it.hasNext()) {
                Element e = (Element) it.next();
                if (e.getAttributeValue("date") != null) {
                    TreeItem item = new TreeItem(parent, SWT.NONE);
                    item.setText(e.getAttributeValue("date"));
                    item.setData(new TreeData(Editor.PERIODS, e, Options.getHelpURL("periods")));
                    if(!Utils.isElementEnabled("job", _dom, e)) {
                    	item.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
                    }
                }
            }
        }

        ArrayList l = new ArrayList();
        if(_listOfAt != null) {
        	Iterator it = _listOfAt.iterator();
        	 while (it.hasNext()) {
                 Element e = (Element) it.next();                 
                 if (e.getAttributeValue("at") != null) {
                	 String sDate = e.getAttributeValue("at").split(" ")[0];                	 
                	 String[] splitDate = sDate.split("-");
                	 if(!exists(Utils.str2int(splitDate[0]), Utils.str2int(splitDate[1]), Utils.str2int(splitDate[2]))
                	  && !dateExistInList(parent, sDate) ) {
                		 Element a = new Element("date");
                		 Utils.setAttribute("date", Utils.getAttributeValue("at", e).substring(0, 10), a);               		 
                		 l.add(a);
                	 } 
                 }
             }
        }
        
        for(int i =0; i < l.size(); i++) {        	
        	Element e = (Element)l.get(i);        	
        	if(!dateExistInList(parent, Utils.getAttributeValue("date", e))) {
        		_list.add(l.get(i));
        		TreeItem item = new TreeItem(parent, SWT.NONE);
        		item.setText(e.getAttributeValue("date"));    
        		
        		item.setData(new TreeData(Editor.PERIODS, (Element)l.get(i), Options.getHelpURL("periods")));
        		if(!Utils.isElementEnabled("job", _dom, e)) {
                	item.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
                }
        	}
        }
        
    
        parent.setExpanded(expand);
    }


    public boolean exists(int year, int month, int day) {
        if (_list != null) {
            for (int i = 0; i < _list.size(); i++) {
                int[] date = getDate(i);
                if (year == date[0] && month == date[1] && day == date[2]) {
                    return true;
                }
            }
        }
        return false;
    }

  
    private void sort() {
        if (_list != null && _parent != null) {
            List copy = new ArrayList(_list);             
            Collections.sort(copy, this);
            _parent.removeChildren(_elementName[_type]);
            _parent.addContent(copy);
            _list = _parent.getChildren(_elementName[_type]);
        }
    }


    public int compare(Object o1, Object o2) {
        if (o1 instanceof Element && o2 instanceof Element) {
            String date1 = ((Element) o1).getAttributeValue("date");
            String date2 = ((Element) o2).getAttributeValue("date");
            if (date1 == null)
                date1 = "";
            if (date2 == null)
                date2 = "";
            return date1.compareTo(date2);
        }
        return 0;
    }
    
    public String[] getIncludes() {
      /*if (_parent == null && _type == 0) {
        _parent = new Element("holidays");
        _element.addContent(_parent);
      }*/
      
      if (_parent != null) {
          List includeList = _parent.getChildren("include");
          String[] includes = new String[includeList.size()];
          Iterator it = includeList.iterator();
          int i = 0;
          while (it.hasNext()) {
              Element include = (Element) it.next();
              String file = include.getAttributeValue("file");
              includes[i++] = file == null ? "" : file;
          }
          return includes;
      } else
          return new String[0];
  }
    
    public void fillTable(Table table) {
    	table.removeAll();
    	if (_parent != null) {    		
    		List includeList = _parent.getChildren("include");
    		for(int i = 0; i < includeList.size(); i++) {
    			Element include = (Element) includeList.get(i);
    			
    			if(include.getAttributeValue("file") != null) {
    				String filename = Utils.getAttributeValue("file", include);
    				TableItem item = new TableItem(table, SWT.NONE); 
    				item.setText(0, filename);
    				item.setText(1, "file");
     				String fname = new File(filename).getName();
    				/*Unklare Konfiguration. Wird in JOE auch nicht unterst�tzt.
    				 * if(filename.endsWith("holidays.xml") && 
    						Options.getHolidaysDescription().values().contains(Options.getSchedulerNormalizedHotFolder() + fname)) {
    					//Object key = Options.getHolidaysDescription().get("holiday_id_" + filename.substring(0, filename.indexOf(".holidays.xml")));
    					Object key = Options.getHolidaysDescription().get("holiday_id_" + fname.substring(0, fname.indexOf(".holidays.xml")));
    					if(key != null)
    						item.setText(2, key.toString());
    				}*/
    			} else {
    				TableItem item = new TableItem(table, SWT.NONE);
    				
    				String filename = Utils.getAttributeValue("live_file", include);
    				item.setText(0, filename);
    				item.setText(1, "live_file");
    				
    				/*if(filename.endsWith("holidays.xml") && 
    						Options.getHolidaysDescription().values().contains(Options.getSchedulerNormalizedHotFolder() + filename)) {
    					Object key = Options.getHolidaysDescription().get("holiday_id_" + filename.substring(0, filename.indexOf(".holidays.xml")));
    					if(key != null)
    						item.setText(2, key.toString());
    				}
    				*/
    				
    			}    			
    		}
    	}
    }
    
    public void addInclude(Table table, String filename, boolean isLive) {
    	if (_parent == null && _type == 0) {
    		_parent = new Element("holidays");
    		_element.addContent(_parent);
    	}

    	if (_parent != null) {
    		
    		
    		if(Options.getHolidaysDescription().get(filename) != null &&
    				Options.getHolidaysDescription().get("file_" + Options.getHolidaysDescription().get(filename)) != null 
    				&& Options.getHolidaysDescription().get("file_" + Options.getHolidaysDescription().get(filename)).toString().length() > 0) {
    			
    			String home = Options.getSchedulerNormalizedHotFolder();
    			    			  	    		
    			filename = Options.getHolidaysDescription().get("file_" + Options.getHolidaysDescription().get(filename)).toString();
    			
    			if(filename.indexOf(home) > -1)
    				filename = new java.io.File(home).getName() + "/" + filename.substring(home.length());
    			
    			isLive = false;
    		}
    		
    		_parent.addContent(new Element("include").setAttribute((isLive?"live_file":"file"), filename));
    		_dom.setChanged(true);
    		if(_dom.isDirectory() && _element.getParentElement() != null)
    			_dom.setChangedForDirectory(_element, SchedulerDom.MODIFY);
    	} else
    		System.out.println("no script element defined!");
    }   

    /*public void addInclude(String filename) {
    	if (_parent == null && _type == 0) {
    		_parent = new Element("holidays");
    		_element.addContent(_parent);
    	}

    	if (_parent != null) {
    		List includes = _element.getChildren("include");
    		_parent.addContent(includes.size(), new Element("include").setAttribute("file", filename));
    		_dom.setChanged(true);
    		if(_dom.isDirectory() &&_element.getParentElement() != null)
    			//_dom.setChangedForDirectory("job", Utils.getAttributeValue("name",_element.getParentElement()), SchedulerDom.MODIFY);
    			_dom.setChangedForDirectory(_element, SchedulerDom.MODIFY);
    	} else
    		System.out.println("no script element defined!");
    } */   
    
    public void removeInclude(int index) {
    	
      /*if (_parent == null && _type == 0) {
        _parent = new Element("holidays");
        _element.addContent(_parent);
      }*/
      
      if (_parent != null) {
          List includeList = _parent.getChildren("include");
          if (index >= 0 && index < includeList.size()) {
        	  
              includeList.remove(index);
              if (includeList.size() == 0 && _type == 0) {
            	  if(_parent.getChildren() == null || _parent.getChildren().isEmpty()) {
            		  _element.removeChild("holidays");
            		  _parent = null;
            	  }
              }
              _dom.setChanged(true);
              
              if(_dom.isDirectory() &&_element.getParentElement() != null)
              	//_dom.setChangedForDirectory("job", Utils.getAttributeValue("name",_element.getParentElement()), SchedulerDom.MODIFY);
            	  _dom.setChangedForDirectory(_element, SchedulerDom.MODIFY);
          } else
              System.out.println("index " + index + " is out of range for include!");
      } else
          System.out.println("no script element defined!");
      
     
      
  }


	public List get_list() {
		return _list;
	}

	public String[] getHolidayDescription() {
		java.util.Iterator h = Options.getHolidaysDescription().keySet().iterator();
		ArrayList list = new ArrayList();
		
		/*String home = Options.getSchedulerHotFolder();
    	home = home.endsWith("/") || home.endsWith("\\") ? home : home + "/";
    	home = home.replaceAll("\\\\", "/");
    	*/
		while(h.hasNext()) {
			String desc = h.next().toString();
			if(desc != null && desc.length() > 0 && !desc.startsWith("file_") && !desc.startsWith("holiday_id_"))
				//list.add( Options.getHolidaysDescription().get(desc).toString().substring(home.length()));
				//list.add( Options.getHolidaysDescription().get(desc));
				list.add( desc);
		}
		
		String[] ret = new String[list.size()];
		for(int i = 0; i < list.size(); i++) {
			ret[i]= list.get(i).toString();
		}
		
		return ret;
	}
	
}
