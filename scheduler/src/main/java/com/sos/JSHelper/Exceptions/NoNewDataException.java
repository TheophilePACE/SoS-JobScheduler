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
package com.sos.JSHelper.Exceptions;

/*
* <p>
* Title: Exception class representing the error during processing
* </p>
* <p>
* Description:
* </p>
* <p> 
* Copyright: Copyright (c) 2007
* </p>
* <p>
* Company: APL Software GmbH
* </p>
* 
* @author <a href="mailto:rainer.buhl@schering.de">Rainer Buhl</a>
* @version $Id: NoNewDataException.java 14731 2011-07-05 20:50:42Z sos $
*/
public class NoNewDataException extends JobSchedulerException {

    /**
	 * 
	 */
	private static final long	serialVersionUID	= -8636032062881811019L;

	/**
     * Construtor with message. 
     *
     * @param pstrMessage the message of the exception
     */
    public NoNewDataException(String pstrMessage) {
        super(pstrMessage);
//        this.Status(DataswitchException.PENDING);
        this.Status(JobSchedulerException.WARNING);
//        this.Category(CategoryJobStart);
//        this.Typ(CategoryJobStart);
        
    }

    /**
     * Construtor without message.
     *
     */
    public NoNewDataException() {
        this("No new Data available");
    }
    
}
