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
package com.sos.JSHelper.Options;


/**
* \class JSOptionMailOptions 
* 
* \brief JSOptionMailOptions - 
* 
* \details
*
* \section JSOptionMailOptions.java_intro_sec Introduction
*
* \section JSOptionMailOptions.java_samples Some Samples
*
* \code
*   .... code goes here ...
* \endcode
*
* <p style="text-align:center">
* <br />---------------------------------------------------------------------------
* <br /> APL/Software GmbH - Berlin
* <br />##### generated by ClaviusXPress (http://www.sos-berlin.com) #########
* <br />---------------------------------------------------------------------------
* </p>
* \author eqbfd
* @version $Id: JSOptionMailOptions.java 14729 2011-07-05 20:47:16Z sos $23.01.2009
* \see reference
*
* Created on 23.01.2009 17:12:46
 */

/**
 * @author eqbfd
 *
 */
public class JSOptionMailOptions extends SOSOptionElement {

	private final String	conClassName	= "JSOptionMailOptions";

	private JSMailOptions objMailOptions = null;
	
	/**
	 * \brief JSOptionMailOptions
	 *
	 * \details
	 *
	 * @param pPobjParent
	 * @param pPstrKey
	 * @param pPstrDescription
	 * @param pPstrValue
	 * @param pPstrDefaultValue
	 * @param pPflgIsMandatory
	 * @throws Exception
	 */
	public JSOptionMailOptions(JSOptionsClass pPobjParent, String pPstrKey, 
			String pPstrDescription, String pPstrValue, String pPstrDefaultValue,
			boolean pPflgIsMandatory) {
		super(pPobjParent, pPstrKey, pPstrDescription, pPstrValue, pPstrDefaultValue, pPflgIsMandatory);

		super.intOptionType = isOptionTypeOptions;
		objMailOptions = new JSMailOptions();
		
	}
	
	public void Value(JSMailOptions pobjOptionsClass) {
		
		@SuppressWarnings("unused")
		final String	conMethodName	= conClassName + "::Value";
		
		objMailOptions = pobjOptionsClass;
		
	} // public void Value
	
	public JSMailOptions value() {
		
		@SuppressWarnings("unused")
		final String	conMethodName	= conClassName + "::value";

		if (objMailOptions == null) {
			objMailOptions = new JSMailOptions();
		}
		
		return objMailOptions;
		
	} // public JSOptionsClass value}
}
