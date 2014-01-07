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


public class JSDataElementInteger extends JSDataElementNumeric {

	@SuppressWarnings("unused")
	private final int intMinSize = 0;  // minimum Size
	@SuppressWarnings("unused")
	private final int intMaxSize = 9999;  // maximum Size

	private int intValue = 0;

	public JSDataElementInteger() {
		super.TrimValue(true);		// hier immer die blanks abschneiden, falls nicht anders eingestellt.
	}

	public JSDataElementInteger(final String pstrValue) {
		super.Value(pstrValue);
		super.TrimValue(true);		// hier immer die blanks abschneiden, falls nicht anders eingestellt.
		getInt();
	}

	public JSDataElementInteger (final int pintValue) {
		intValue = pintValue;
		super.TrimValue(true);		// hier immer die blanks abschneiden, falls nicht anders eingestellt.
		this.Value(pintValue);
	}

	public JSDataElementInteger(final String pPstrValue, final String pPstrDescription, final int pPintSize, final int pPintPos, final String pPstrFormatString, final String pPstrColumnHeader,
			final String pPstrXMLTagName) {
		super(pPstrValue, pPstrDescription, pPintSize, pPintPos, pPstrFormatString, pPstrColumnHeader, pPstrXMLTagName);
	}


	@Override
	public void Value (final String pstrValue) {
		super.Value(pstrValue);
		getInt();
	}
	/**
	 *
	 * \brief Value
	 *
	 * \details

	 * \return void
	 *
	 * @param pintValue
	 */
	public void Value (final int pintValue) {
		intValue = pintValue;
		super.Value(new Integer(pintValue).toString());
	}
	/**
	 *
	 * \brief getInt
	 *
	 * \details

	 * \return int
	 *
	 */
	public int getInt () {
		// EQCPN-2009-03-06 Direkt aus dem Eigenschaftwert ermitteln, weil intValue nicht gesetzt
		// wird, wenn die Wertezuweisung mit Value(String pstrValue) erfolgt.
		try {
			intValue = 0;
			String strT = super.Value().trim();
			final int intLen = strT.length();
			if (intLen > 0) {
				if (strT.endsWith("-")) {
					strT = "-" + strT.substring(0, intLen-1);
				}
				else {
					if (strT.startsWith("+")) {
						strT = strT.substring(1, intLen);
					}
					else {
						if (strT.endsWith("+")) {
							strT = strT.substring(0, intLen-1);
						}
					}
				}

			intValue = Integer.parseInt(strT);
			}
		} catch(final NumberFormatException e) {
			intValue = 0;
		}
		return intValue;
	}
	/**
	 *
	 * \brief FormattedValue - Liefert den Wert des Elements formatiert
	 *
	 * \details
	 * das Format (die Edit-Maske) wird über die Eigenschaft FormatString
	 * definiert.
	 *
	 * Wenn kein Format-String definiert ist, so wird der Wert als String
	 * zurückgegeben.
	 *
	 * \return String
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	public String FormattedValue ()  {
		final String strFormat = super.FormatString();

		if (isNotEmpty(strFormat.trim())) {
			String strFormatted = String.format("%1$" + strFormat, getInt());
			strFormatted = strFormatted.trim();
			if (super.MaxSize()!=0) {		// EQCPN-2009-04-22: führt bei 0 sonst zur leerer Ausgabe
				if (strFormatted.length() > super.MaxSize()) {
					strFormatted = "";
					for (int i = 0; i < super.MaxSize(); i++) {
						strFormatted += "*";
					}
				}
			}
			return strFormatted;
		}
		else {
			return super.Value();
		}
	}

	@Override
	public void doInit() {

		super.MaxSize(15);
		super.FormatString("-,15d");
		super.Description("Integer");
		super.ColumnHeader("Integer");
		super.XMLTagName("Integer");

	}
}
