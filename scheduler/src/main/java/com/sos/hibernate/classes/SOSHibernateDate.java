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
package com.sos.hibernate.classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
* \class SOSHibernateDate 
* 
* \brief SOSHibernateDate - 
* 
* \details
*
* \section SOSHibernateDate.java_intro_sec Introduction
*
* \section SOSHibernateDate.java_samples Some Samples
*
* \code
*   .... code goes here ...
* \endcode
*
* <p style="text-align:center">
* <br />---------------------------------------------------------------------------
* <br /> SOS GmbH - Berlin
* <br />---------------------------------------------------------------------------
* </p>
* \author Uwe Risse
* \version 26.09.2011
* \see reference
*
* Created on 26.09.2011 11:36:37
 */

public class SOSHibernateDate {

	@SuppressWarnings("unused")
	private final String	conClassName	= "SOSHibernateDate";
	private String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";
	private Date   date;
	private String isoDate;
	
		

	public SOSHibernateDate(String dateFormat_) {
		this.dateFormat = dateFormat_;
		//
	}

	
	private void setIsoDate() throws ParseException {
		String isoDateFormat = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formatter = new SimpleDateFormat(isoDateFormat);
		this.isoDate = formatter.format(date);
	}
	
	
	public void setDate(String date) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		if (date.equals("now")){
				this.date = new Date();
		}else {
				this.date = formatter.parse(date);
		}
		this.setIsoDate();
	}

	public Date getDate() {
		return date;
	}

	public String getIsoDate() {
		return isoDate;
	}

	public void setDate(Date date) {
		this.date = date;
		try {
		   this.setIsoDate();
		}catch (ParseException e) {
			System.out.println(conClassName+".setDate: Could not set Iso-Date");
		}
	}
	
	
}
