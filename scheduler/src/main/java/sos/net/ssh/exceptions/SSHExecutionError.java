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
package sos.net.ssh.exceptions;

import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.localization.SOSMsg;

/**
 * @author KB
 *
 */
public class SSHExecutionError extends JobSchedulerException {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5148373372299244495L;

	/**
	 * 
	 */
	public SSHExecutionError() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param pstrMessage
	 */
	public SSHExecutionError(final String pstrMessage) {
		super(pstrMessage);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param pobjMsg
	 */
	public SSHExecutionError(final SOSMsg pobjMsg) {
		super(pobjMsg);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param pstrMessage
	 * @param e
	 */
	public SSHExecutionError(final String pstrMessage, final Exception e) {
		super(pstrMessage, e);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param pobjMsg
	 * @param e
	 */
	public SSHExecutionError(final SOSMsg pobjMsg, final Exception e) {
		super(pobjMsg, e);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param e
	 */
	public SSHExecutionError(final Exception e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

}
