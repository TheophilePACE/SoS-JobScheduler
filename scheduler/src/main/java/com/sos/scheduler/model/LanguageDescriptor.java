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
/**
 *
 */
package com.sos.scheduler.model;

import org.apache.log4j.Logger;

import com.sos.JSHelper.interfaces.ISOSComboItem;

/**
 * @author KB
 *
 */
public class LanguageDescriptor implements ISOSComboItem  {
	@SuppressWarnings("unused")
	private final String conClassName = this.getClass().getSimpleName();
	@SuppressWarnings("unused")
	private static final String conSVNVersion = "$Id$";
	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(this.getClass());
	/**
	 *
	 */
	private String			strLanguageName		= "";
	private int				intLanguageNumber	= -1;
	private boolean			flgIsHiddenL		= false;
	private String			strClassName		= "";
	private String			strDocuFileName		= "";
	private String			strExternalLanguage	= "";
	private boolean IsAPIL = true;
	private boolean IsMonitorL = false;

	public LanguageDescriptor(final String pstrLanguageName, final int pintNumber, final boolean pflgIsHiddenL, final String pstrExternalLanguage,
			final String pstrClassName, final String pstrDocuFileName, final boolean pflgIsAPIL, final boolean pflgIsMonitorL) {

		strLanguageName = pstrLanguageName;
		intLanguageNumber = pintNumber;
		flgIsHiddenL = pflgIsHiddenL;
		strClassName = pstrClassName;
		strDocuFileName = pstrDocuFileName;
		strExternalLanguage = pstrExternalLanguage;  // e.g. SSH is internal, Java is external for SSH
		IsAPIL = pflgIsAPIL;
		IsMonitorL = pflgIsMonitorL;
	}

	public String getExternalLanguage() {
		return strExternalLanguage;
	}

	public void setExternalLanguage(final String externalLanguage) {
		strExternalLanguage = externalLanguage;
	}

	public String getLanguageName() {
		return strLanguageName;
	}

	public void setLanguageName(final String languageName) {
		strLanguageName = languageName;
	}

	public int getLanguageNumber() {
		return intLanguageNumber;
	}

	public void setLanguageNumber(final int languageNumber) {
		intLanguageNumber = languageNumber;
	}

	public boolean isHiddenL() {
		return flgIsHiddenL;
	}

	public void setHiddenL(final boolean isHiddenL) {
		flgIsHiddenL = isHiddenL;
	}

	public String getClassName() {
		return strClassName;
	}

	public void setClassName(final String className) {
		strClassName = className;
	}

	public String getDocuFileName() {
		return strDocuFileName;
	}

	public void setDocuFileName(final String docuFileName) {
		strDocuFileName = docuFileName;
	}

	public boolean isIsAPIL() {
		return IsAPIL;
	}

	public void setIsAPIL(final boolean isAPIL) {
		IsAPIL = isAPIL;
	}

	public boolean isIsMonitorL() {
		return IsMonitorL;
	}

	public void setIsMonitorL(final boolean isMonitorL) {
		IsMonitorL = isMonitorL;
	}

	@Override
	public String getText() {
		return getLanguageName();
	}

	@Override
	public int getIndex() {
		return getLanguageNumber();
	}



}
