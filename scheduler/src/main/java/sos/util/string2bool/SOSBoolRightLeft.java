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
package  sos.util.string2bool;

final class SOSBoolRightLeft extends SOSBooleanExpression {

	 
	private String booleanExpression;

	 
	private SOSIBoolean iBoolean;

	 
	SOSBoolRightLeft(final String newBooleanExpression)
		throws SOSMalformedBooleanException {
		this.booleanExpression = newBooleanExpression;
		this.iBoolean = toIBoolean(SOSBooleanUtil
			.validAndformat(newBooleanExpression), 0);
	}

	 
	public boolean booleanValue() {
		return this.iBoolean.booleanValue();
	}

	 
	private SOSIBoolean toIBoolean(final String formatedBooleanExpression,
		final int index) throws SOSMalformedBooleanException {
		char firstChar = getFirstChar(formatedBooleanExpression);
		if (new Character(firstChar).toString().matches("\\s")) {
			firstChar = ' ';
		}
		String substring = getSubstringWithoutFirstChar(formatedBooleanExpression);
		switch (firstChar) {
			case ' ':
				SOSIBoolean boolWhitespace = toIBoolean(substring, index + 1); 
				return boolWhitespace;
			case '(':
				String beginToClose = getFromBeginToCloseParenthesis(substring,
					index + 1);
				String closeToEnd = getFromCloseParenthesisToEnd(substring,
					index + 1);
				SOSIBoolean boolBeginToClose= toIBoolean(
					beginToClose, index + 1);
				SOSIBoolean boolOpen = toIBoolean(boolBeginToClose, closeToEnd, index + 1); 
				return boolOpen;
			case 'T':
				SOSIBoolean boolTrue = toIBoolean(new SOSBoolean(true), substring, index + 4); 
				return boolTrue;
			case 'F':
				SOSIBoolean boolFalse = toIBoolean(new SOSBoolean(false), substring, index + 5); 
				return boolFalse;
			case '!':
				SOSIBoolean boolAll = toIBoolean(substring, index + 1);
				SOSIBoolean boolNot = new SOSNot(boolAll); 
				return boolNot;
			default:
				throw new SOSMalformedBooleanException(
					"Expected [ (, true, flase, ! ]", index,
					this.booleanExpression);
		}
	}

	 
	private SOSIBoolean toIBoolean(final SOSIBoolean lastIBoolean,
		final String formatedBooleanExpression, final int index)
		throws SOSMalformedBooleanException {
		char firstChar = getFirstChar(formatedBooleanExpression);
		if (new Character(firstChar).toString().matches("\\s")) {
			firstChar = ' ';
		}
		String substring = getSubstringWithoutFirstChar(formatedBooleanExpression);
		switch (firstChar) {
			case ' ':
				return toIBoolean(lastIBoolean, substring, index + 1);
			case '.':
				return lastIBoolean;
			case ')':
				return toIBoolean(lastIBoolean, substring, index + 1);
			case '|':
				return new SOSOrOperation(lastIBoolean, toIBoolean(
					substring, index + 2));
			case '&':
				return new SOSAnd(lastIBoolean, toIBoolean(
					substring, index + 2));
			default:
				throw new SOSMalformedBooleanException(
					"Expected [ ' ', ), ||, && ]", index,
					this.booleanExpression);
		}
	}

 
	private char getFirstChar(final String formatedBooleanExpression) {
		if (formatedBooleanExpression.length() == 0) {
			return '.';
		}
		return formatedBooleanExpression.charAt(0);
	}

 
	private String getSubstringWithoutFirstChar(
		final String formatedBooleanExpression) {
		if (formatedBooleanExpression == null
			|| formatedBooleanExpression.length() == 0) {
			return "";
		}
		return formatedBooleanExpression.substring(1, formatedBooleanExpression
			.length());
	}

	 
	private String getFromBeginToCloseParenthesis(
		final String formatedBooleanExpression, final int index)
		throws SOSMalformedBooleanException {
		if (formatedBooleanExpression == null
			|| formatedBooleanExpression.length() == 0) {
			return "";
		}
		int fromIndex = 0;
		int toIndex = getIndexOfCloseParenthesis(formatedBooleanExpression,
			index);
		return formatedBooleanExpression.substring(fromIndex, toIndex);
	}

	 
	private String getFromCloseParenthesisToEnd(
		final String formatedBooleanExpression, final int index)
		throws SOSMalformedBooleanException {
		if (formatedBooleanExpression == null
			|| formatedBooleanExpression.length() == 0) {
			return "";
		}
		int fromIndex = getIndexOfCloseParenthesis(formatedBooleanExpression,
			index);
		int toIndex = formatedBooleanExpression.length();
		return formatedBooleanExpression.substring(fromIndex, toIndex);
	}

	 
	private int getIndexOfCloseParenthesis(
		final String formatedBooleanExpression, final int index)
		throws SOSMalformedBooleanException {
		int lastIndexOfOpenParenthesis = getIndexOf(formatedBooleanExpression,
			"(", -1);
		int lastIndexOfCloseParenthesis = getIndexOf(formatedBooleanExpression,
			")", -1);
		while (lastIndexOfOpenParenthesis != -1
			&& lastIndexOfOpenParenthesis < lastIndexOfCloseParenthesis) {
			lastIndexOfOpenParenthesis = getIndexOf(formatedBooleanExpression,
				"(", lastIndexOfOpenParenthesis);
			lastIndexOfCloseParenthesis = getIndexOf(formatedBooleanExpression,
				")", lastIndexOfCloseParenthesis);
		}
		if (lastIndexOfCloseParenthesis == -1) {
			int parenthesisIndex = index + lastIndexOfOpenParenthesis;
			throw new SOSMalformedBooleanException(
				"Have a open parenthesis without a close parenthesis",
				parenthesisIndex, this.booleanExpression);
		}
		return lastIndexOfCloseParenthesis;
	}

 
	private int getIndexOf(final String formatedBooleanExpression,
		final String searchedString, final int fromIndex) {
		int newFromIndex = fromIndex;
		if (newFromIndex == -1) {
			return formatedBooleanExpression.indexOf(searchedString);
		}
		newFromIndex++;
		int length = formatedBooleanExpression.length();
		if (newFromIndex > length) {
			return -1;
		}
		return formatedBooleanExpression.indexOf(searchedString, newFromIndex);
	}

	 
	public String toString() {
		return this.iBoolean.toString();
	}

}
