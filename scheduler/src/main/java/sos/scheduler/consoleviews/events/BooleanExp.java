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
 * Created on 20.10.2008
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package sos.scheduler.consoleviews.events;

import java.util.HashMap;
import java.util.StringTokenizer;

import sos.util.string2bool.SOSBooleanExpression;
import sos.util.string2bool.SOSMalformedBooleanException;

public class BooleanExp {

	private String	boolExp;

	public BooleanExp(final String boolExp_) {
		super();
		boolExp = boolExp_;
		boolExp = boolExp.replaceAll(" and ", " && ");
		boolExp = boolExp.replaceAll("not ", "! ");
		boolExp = boolExp.replaceAll(" or ", " || ");
		boolExp = boolExp.replaceAll("[ ]{2,}", " "); // Viele Leerzeichen in 1 Leerzeichen
		boolExp = boolExp.trim();
	}

	public void replace(String s1, String s2) {
		boolExp = " " + boolExp + " ";
		boolExp = boolExp.replaceAll("\\(", " ( ");
		boolExp = boolExp.replaceAll("\\)", " ) ");

		s1 = " " + s1 + " ";
		s2 = " " + s2 + " ";
		boolExp = boolExp.replaceAll(s1, s2);
		boolExp = boolExp.replaceAll(" \\( ", "(");
		boolExp = boolExp.replaceAll(" \\) ", ")");
		boolExp = boolExp.trim();
	}

	public boolean evaluateExpression() {
		SOSBooleanExpression boolExpr = null;
		HashMap<String, String> allowedToken = new HashMap<String, String>();
		allowedToken.put("(", "");
		allowedToken.put(")", "");
		allowedToken.put("||", "");
		allowedToken.put("&&", "");
		allowedToken.put("!", "");
		allowedToken.put("!(", "");
		allowedToken.put(")!", "");
		allowedToken.put("(!)", "");
		allowedToken.put("true", "");
		allowedToken.put("false", "");
		allowedToken.put("(true", "");
		allowedToken.put("(false", "");
		allowedToken.put("(true)", "");
		allowedToken.put("(false)", "");
		allowedToken.put("true)", "");
		allowedToken.put("false)", "");

		try {
			String normalizedBoolExpr = "";
			StringTokenizer t = new StringTokenizer(boolExp, " ");
			while (t.hasMoreTokens()) {
				String s = t.nextToken();
				if (allowedToken.get(s) != null) {
					normalizedBoolExpr = normalizedBoolExpr + " " + s;
				}
				else {
					normalizedBoolExpr = normalizedBoolExpr + " false ";
				}
			}
			if (normalizedBoolExpr.length() == 0) {
				return true;
			}
			else {
				boolExpr = SOSBooleanExpression.readLeftToRight(normalizedBoolExpr);
				return boolExpr.booleanValue();
			}
		}
		catch (SOSMalformedBooleanException e) {
			System.out.println("--->" + boolExp);
			e.printStackTrace();
			return false;
		}
	}

	public String trueFalse(final boolean b) {
		if (b) {
			return " true ";
		}
		else {
			return " false ";
		}
	}

}
