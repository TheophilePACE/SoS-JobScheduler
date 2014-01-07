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

 
public final class SOSMalformedBooleanException extends Exception {
 
	private static final long serialVersionUID = 1L;
 
	private List booleanExpressionErrorIndexes;

 
	private String booleanExpression;

 
	private String booleanExpressionErrorMessage;

 
	SOSMalformedBooleanException(final String errorMessage, final int errorIndex,
		final String newBooleanExpression) {
		this(errorMessage, toList(errorIndex), newBooleanExpression);
	}
 
	SOSMalformedBooleanException(final String errorMessage,
		final List errorIndexes, final String newBooleanExpression) {
		super(format(errorMessage, errorIndexes, newBooleanExpression));
		this.booleanExpression = newBooleanExpression;
		this.booleanExpressionErrorIndexes = errorIndexes;
		this.booleanExpressionErrorMessage = errorMessage;
	}

 
	public String getBooleanExpression() {
		return this.booleanExpression;
	}
 
	public List getBooleanExpressionErrorIndexes() {
		return this.booleanExpressionErrorIndexes;
	}

 
	public String getBooleanExpressionErrorMessage() {
		return this.booleanExpressionErrorMessage;
	}

 
	private static List toList(final int errorIndex) {
		List errorIndexes = new ArrayList();
		errorIndexes.add(new Integer(errorIndex));
		return errorIndexes;
	}

	 
	private static String format(final String errorMessage,
		final List errorIndexes, final String newBooleanExpression) {
		if (errorMessage == null || errorMessage.equals("")) {
			throw new IllegalArgumentException("errorMessage is null or void");
		}
		if (errorIndexes == null || errorIndexes.isEmpty()) {
			throw new IllegalArgumentException("errorIndexes is null or void");
		}
		if (newBooleanExpression == null || newBooleanExpression.equals("")) {
			throw new IllegalArgumentException(
				"newBooleanExpression is null or void");
		}
		StringBuffer error = new StringBuffer();
		error.append(errorMessage);
		error.append(" in [ ");
		int size = errorIndexes.size();
		int lastIndex = 0;
		for (int i = 0; i < size; i++) {
			int index = ((Integer) errorIndexes.get(i)).intValue();
			error.append(newBooleanExpression.substring(lastIndex, index));
			error.append("_");
			lastIndex = index;
		}
		error.append(newBooleanExpression.substring(lastIndex,newBooleanExpression.length()));
		error.append(" ]");
		if (size == 1) {
			error.append(" - Index [");
		} else if (size > 1) {
			error.append(" - Indexes [");
		}
		for (int i = 0; i < size; i++) {
			error.append(errorIndexes.get(i));
			if (i != (size - 1)) {
				error.append(", ");
			}
		}
		if (size > 0) {
			error.append("]");
		}
		return error.toString();
	}

}
