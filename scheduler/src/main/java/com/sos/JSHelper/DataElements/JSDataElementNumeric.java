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
package com.sos.JSHelper.DataElements;

/**
 * \class JSDataElementNumeric
 *
 * \brief
 * Basisklasse f�r ein numerisches Datenelement
 *
 * @author eqbfd
 *
 */
public class JSDataElementNumeric extends JSDataElement {

	private final String	conClassName		= "JSDataElementNumeric";

	@SuppressWarnings("unused")
	private final String	strMinValue	= "";
	@SuppressWarnings("unused")
	private final String	strMaxValue	= "";

	@SuppressWarnings("unused")
	private int intDecimal = 0;


	public JSDataElementNumeric() {
	}

	public JSDataElementNumeric(final String pstrValue) {
		super.Value(pstrValue);
	}

	public JSDataElementNumeric(final String pPstrValue, final String pPstrDescription, final int pPintSize, final int pPintPos, final String pPstrFormatString, final String pPstrColumnHeader,
			final String pPstrXMLTagName) {
		super(pPstrValue, pPstrDescription, pPintSize, pPintPos, pPstrFormatString, pPstrColumnHeader, pPstrXMLTagName);
	}

	public int MinSize() {
		return 0;
	}

	public void MinSize(@SuppressWarnings("unused") final
			int pintMinSize) {

	}

	public int MaxSize() {
		return 0;
	}

	public void MaxSize(@SuppressWarnings("unused") final
			int pintMaxSize) {

	}

	/**
	 *
	 * \brief FormattedValue - Liefert den Wert des Elements formatiert
	 *
	 * \details
	 * das Format (die Edit-Maske) wird �ber die Eigenschaft FormatString
	 * definiert.
	 * Die Ausrichtung ist linksb�ndig.
	 *
	 * Wenn kein Format-String definiert ist, so wird der Wert als String
	 * zur�ckgegeben.
	 *
	 * \return String
	 * @throws Exception
	 *
	 */
	@Override
	public String FormattedValue()  {
		final String strFormat = super.FormatString();

		//so geht es ohne Format-String:
		//		NumberFormat fmt = NumberFormat.getInstance(Locale.US);
		//		fmt.setGroupingUsed(false);
		//		fmt.setMaximumFractionDigits(3);
		//
		//		try {
		//			RetVal = fmt.format(dblQuantity);
		//			//
		//		}

		if (isNotEmpty(strFormat)) {
			try {
				final String strFormatted = String.format("%1$" + strFormat, new Integer(this.Value()));
				return strFormatted.trim();

			} // try
			catch (final Exception numberFormException) {
				return super.Value();
			}
		}
		else {
			return super.Value();
		}
	}

	/**
	 *
	 * \brief Decimal
	 *
	 * \details
	 * Setzt die max. Anzahl Dezimalstellen. Wird f�r die Darstellung im XML verwendet
	 *
	 * \return void
	 *
	 * @param pintDecimal
	 */
	@SuppressWarnings("unused")
	public void Decimal(final int pintDecimal) {
		System.out.println("JSDataElementNumeric.Decimal()" + pintDecimal);
		intDecimal = pintDecimal;
	}

	/**
	 *
	 * \brief Decimal
	 * Liefert die max. Anzahl Dezimalstellen
	 *
	 * \details
	 *
	 * \return int
	 *
	 * @return
	 */
	@SuppressWarnings("unused")
	public int Decimal() {
		return intDecimal;
	}

	public int IntValue () {
		return new Integer( super.Value()).intValue();
	}

	public JSDataElementNumeric Subtract (final JSDataElementNumeric pobjDataElementNumeric) {
		final Integer intValue = new Integer( IntValue() - pobjDataElementNumeric.IntValue());
		this.Value( intValue.toString());
		return this;
	}

	public JSDataElementNumeric Add (final JSDataElementNumeric pobjDataElementNumeric) {
		final Integer intValue = new Integer( IntValue() + pobjDataElementNumeric.IntValue());
		this.Value( intValue.toString());
		return this;
	}

	@Override
	public String SQLValue() {

		@SuppressWarnings("unused")
		final String	conMethodName	= conClassName + "::SQLValue";

		if (isEmpty( this.Value()) ) {
			return "null";
		}

		return this.Value();
	} // public String SQLValue}
}
