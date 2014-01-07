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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

 
final class SOSBooleanUtil {

	 
	static String validAndformat(final String booleanExpression)
		throws SOSMalformedBooleanException {
	    if (booleanExpression == null || booleanExpression.equals("")) {
			throw new IllegalArgumentException(
				"Argument: booleanExpression is null or void");
		}		validRegexp(booleanExpression);
		validParenthesis(booleanExpression);
		
		String s = booleanExpression.toUpperCase().replaceAll("TRUE", "T").replaceAll("FALSE", "F").replaceAll(			"\\|\\|", "|").replaceAll("&&", "&");
		return s;
		 
	}

	   	 
	private static void validRegexp(final String booleanExpression)
		throws SOSMalformedBooleanException {
		String regexp = "(\\(|\\)|\\|{2}|\\&{2}|!|(false)|(true)|\\s)+";
		if (!booleanExpression.matches("^" + regexp + "$")) {
			Matcher matcher = Pattern.compile(regexp)
				.matcher(booleanExpression);
			List errorIndexes = new ArrayList();
			while (matcher.find()) {
				int start = matcher.start();
				if (start != 0) {
					errorIndexes.add(new Integer(start));
				}
				int end = matcher.end();
				if (end != booleanExpression.length()) {
					errorIndexes.add(new Integer(end));
				}
			}
			if (errorIndexes.isEmpty()) {
				errorIndexes.add(new Integer(0));
			}
			throw new SOSMalformedBooleanException(
				"Expected [ ' ' ( ) || && ! true false ]", errorIndexes,
				booleanExpression);
		}
	}

	
	private static void validParenthesis(final String booleanExpression)
		throws SOSMalformedBooleanException {
		int length = booleanExpression.length();
		int openParenthesis = 0;
		int closeParenthesis = 0;
		int lastOpenParenthesisIndex = 0;
		for (int i = 0; i < length; i++) {
			char charAt = booleanExpression.charAt(i);
			switch (charAt) {
				case '(':
					lastOpenParenthesisIndex = i;
					openParenthesis++;
					break;
				case ')':
					closeParenthesis++;
					if (openParenthesis < closeParenthesis) {
						throw new SOSMalformedBooleanException(
							"Have a close parenthesis without an open parenthesis",
							i, booleanExpression);
					}
					break;
				default:
					break;
			}
		}
		if (closeParenthesis < openParenthesis) {
			throw new SOSMalformedBooleanException(
				"Have an open parenthesis without a close parenthesis",
				lastOpenParenthesisIndex, booleanExpression);
		}
	}

	 
 

}
