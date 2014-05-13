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
 package  sos.util.string2bool;

final class SOSBoolLeftRight extends SOSBooleanExpression {

	
	private String booleanExpression;

	private SOSIBoolean iBoolean;

	
	SOSBoolLeftRight(final String newBooleanExpression)
		throws SOSMalformedBooleanException {
		this.booleanExpression = newBooleanExpression;
		this.iBoolean = toIBoolean(SOSBooleanUtil
			.validAndformat(newBooleanExpression), newBooleanExpression
			.length());
	}

	
	public boolean booleanValue() {
		return this.iBoolean.booleanValue();
	}

	private SOSIBoolean toIBoolean(final String formatedBooleanExpression,
		final int index) throws SOSMalformedBooleanException {
		char lastChar = getLastChar(formatedBooleanExpression);
		if (new Character(lastChar).toString().matches("\\s")) {
			lastChar = ' ';
		}
		String substring = getSubstringWithoutLastChar(formatedBooleanExpression);
		switch (lastChar) {
			case ' ':
				SOSIBoolean boolWhitespace = toIBoolean(substring, index - 1);
				return boolWhitespace;
			case ')':
				String openToEnd = getFromOpenParenthesisToEnd(substring,
					index - 1);
				String beginToOpen = getFromBeginToOpenParenthesis(substring,
					index - 1);
				SOSIBoolean boolOpenToEnd = toIBoolean(openToEnd, index - 1);
				SOSIBoolean boolToClose = toIBoolean(boolOpenToEnd, beginToOpen,
					index - 1);
				return boolToClose;
			case 'T':
				SOSIBoolean boolTrue = toIBoolean(new SOSBoolean(true), substring,
					index - 4);
				return boolTrue;
			case 'F':
				SOSIBoolean boolFalse = toIBoolean(new SOSBoolean(false), substring,
					index - 5);
				return boolFalse;
			default:
				throw new SOSMalformedBooleanException(
					"Expected [ ' ', ), true, false ]", index,
					this.booleanExpression);
		}
	}

	
	private SOSIBoolean toIBoolean(final SOSIBoolean lastIBoolean,
		final String formatedBooleanExpression, final int index) throws SOSMalformedBooleanException {
		char lastChar = getLastChar(formatedBooleanExpression);
		if (new Character(lastChar).toString().matches("\\s")) {
			lastChar = ' ';
		}
		String substring = getSubstringWithoutLastChar(formatedBooleanExpression);
		switch (lastChar) {
			case ' ':
				SOSIBoolean boolWhitespace = toIBoolean(lastIBoolean, substring,
					index - 1);
				return boolWhitespace;
			case '.':
				return lastIBoolean;
			case '(':
			   SOSIBoolean boolToOpen = toIBoolean(lastIBoolean, substring,
					index - 1);
				return boolToOpen;
			case '|':
			   SOSIBoolean boolFirstOr = toIBoolean(substring, index - 2);
			   SOSIBoolean boolOr = new SOSOrOperation(boolFirstOr,
					lastIBoolean);
				return boolOr;
			case '&':
			   SOSIBoolean boolFirstAnd = toIBoolean(substring, index - 2);
			   SOSIBoolean boolAnd = new SOSAnd(boolFirstAnd,
					lastIBoolean);
				return boolAnd;
			case '!':
			   SOSIBoolean boolNot = new SOSNot(lastIBoolean);
			   SOSIBoolean boolAll = toIBoolean(boolNot, substring, index - 1);
				return boolAll;
			default:
				throw new SOSMalformedBooleanException(
					"Expected [ ' ', ), ||, &&, ! ]", index,
					this.booleanExpression);
		}
	}

	
	private char getLastChar(final String formatedBooleanExpression) {
		if (formatedBooleanExpression.length() == 0) {
			return '.';
		}
		return formatedBooleanExpression.charAt(formatedBooleanExpression.length() - 1);
	}

	
	private String getSubstringWithoutLastChar(
		final String formatedBooleanExpression) {
		if (formatedBooleanExpression == null || formatedBooleanExpression.length() == 0) {
			return "";
		}
		return formatedBooleanExpression.substring(0, formatedBooleanExpression.length() - 1);
	}

	
	private String getFromBeginToOpenParenthesis(
		final String formatedBooleanExpression, final int index)
		throws SOSMalformedBooleanException {
		if (formatedBooleanExpression == null
			|| formatedBooleanExpression.length() == 0) {
			return "";
		}
		int fromIndex = 0;
		int toIndex = getIndexOfOpenParenthesis(formatedBooleanExpression,
			index);
		return formatedBooleanExpression.substring(fromIndex, toIndex);
	}

	
	private String getFromOpenParenthesisToEnd(
		final String formatedBooleanExpression, final int index)
		throws SOSMalformedBooleanException {
		if (formatedBooleanExpression == null
			|| formatedBooleanExpression.length() == 0) {
			return "";
		}
		int fromIndex = getIndexOfOpenParenthesis(formatedBooleanExpression,
			index) + 1;
		int toIndex = formatedBooleanExpression.length();
		return formatedBooleanExpression.substring(fromIndex, toIndex);
	}

	
	private int getIndexOfOpenParenthesis(
		final String formatedBooleanExpression, final int index)
		throws SOSMalformedBooleanException {
		int lastIndexOfOpenParenthesis = getLastIndexOf(
			formatedBooleanExpression, "(", formatedBooleanExpression.length());
		int lastIndexOfCloseParenthesis = getLastIndexOf(
			formatedBooleanExpression, ")", formatedBooleanExpression.length());
		while (lastIndexOfCloseParenthesis != -1
			&& lastIndexOfOpenParenthesis < lastIndexOfCloseParenthesis) {
			lastIndexOfOpenParenthesis = getLastIndexOf(
				formatedBooleanExpression, "(", lastIndexOfOpenParenthesis);
			lastIndexOfCloseParenthesis = getLastIndexOf(
				formatedBooleanExpression, ")", lastIndexOfCloseParenthesis);
		}
		if (lastIndexOfOpenParenthesis == -1) {
			int parenthesisIndex = index
				- (formatedBooleanExpression.length() - lastIndexOfCloseParenthesis);
			throw new SOSMalformedBooleanException(
				"Have a close parenthesis without an open parenthesis",
				parenthesisIndex, this.booleanExpression);
		}
		return lastIndexOfOpenParenthesis;
	}

	private int getLastIndexOf(final String formatedBooleanExpression,
		final String searchedString, final int toIndex) {
		if (toIndex < 0) {
			return -1;
		} else if (toIndex >= formatedBooleanExpression.length()) {
			return formatedBooleanExpression.lastIndexOf(searchedString);
		} else {
			String newFormatedBooleanExpression = formatedBooleanExpression
				.substring(0, toIndex);
			return newFormatedBooleanExpression.lastIndexOf(searchedString);
		}
	}

	
	public String toString() {
		return this.iBoolean.toString();
	}

}
