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

 
final class SOSOrOperation implements SOSIBoolean {
 
	private SOSIBoolean iBoolean1;

	 
	private SOSIBoolean iBoolean2;

	 
	SOSOrOperation(final SOSIBoolean newIBoolean1, final SOSIBoolean newIBoolean2) {
		if (newIBoolean1 == null) {
			throw new IllegalArgumentException("Argument: newIBoolean1 is null");
		}
		this.iBoolean1 = newIBoolean1;
		if (newIBoolean2 == null) {
			throw new IllegalArgumentException("Argument: newIBoolean2 is null");
		}
		this.iBoolean2 = newIBoolean2;

	}

	 
	public boolean booleanValue() {
		return (this.iBoolean1.booleanValue() || this.iBoolean2.booleanValue());
	}

	 
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("(");
		buffer.append(this.iBoolean1);
		buffer.append("||");
		buffer.append(this.iBoolean2);
		buffer.append(")");
		return buffer.toString();
	}

}
