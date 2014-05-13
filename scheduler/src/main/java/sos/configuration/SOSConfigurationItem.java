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
package sos.configuration;

/**
 * Diese Klasse repr�sentiert eine Parameter/Einstellung mit folgenden Eigenschaften:
 * 
 *  - identifier
 *  - Name
 *  - Wert
 *  - Defaultwert
 *  - Ist password?
 *  
 * @author mo
 *
 */
public class SOSConfigurationItem {

	private String    name                 = "";
	private String    value                = "";
	private String    defaults             = "";		
	private String    itemId               = "";
	private boolean   isPassword           = false;
	
	
	/**
	 * Konstruktor
	 * 
	 */
	public SOSConfigurationItem() { 
		
	}

	/**
	 * Konstruktur
	 * @param name
	 * @param value
	 */
	public SOSConfigurationItem(String name, String value) {
		setName(name);
		setValue(value);
	}
	
	/**
	 * Konstruktor
	 * @param name
	 * @param value
	 * @param defaultvalue
	 */
	public SOSConfigurationItem(String name, String value, String defaultvalue) {
		setName(name);
		setValue(value);
		setDefaults(defaultvalue);
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the value
	 */
	public String getValue() {
		if(value != null && value.length() > 0)
			return value;
		else if(defaults != null && defaults.length() > 0)
			return defaults;
		else
			return "";
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}


	/**
	 * @return the defaults
	 */
	public String getDefaults() {
		return defaults;
	}


	/**
	 * @param defaults the defaults to set
	 */
	public void setDefaults(String defaults) {		
		this.defaults = defaults;
	}

	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the isPassword
	 */
	public boolean isPassword() {
		return isPassword;
	}

	/**
	 * @param isPassword the isPassword to set
	 */
	public void setPassword(boolean isPassword) {
		this.isPassword = isPassword;
	}

}
