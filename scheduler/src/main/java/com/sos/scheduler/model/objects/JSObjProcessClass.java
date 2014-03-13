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
package com.sos.scheduler.model.objects;

import java.io.File;

import javax.xml.bind.JAXBElement;

import org.apache.log4j.Logger;

import com.sos.VirtualFileSystem.Interfaces.ISOSVirtualFile;
import com.sos.scheduler.model.SchedulerObjectFactory;

/**
* \class JSObjProcessClass
*
* \brief JSObjProcessClass -
*
* \details
*
* \section JSObjProcessClass.java_intro_sec Introduction
*
* \section JSObjProcessClass.java_samples Some Samples
*
* \code
*   .... code goes here ...
* \endcode
*
* <p style="text-align:center">
* <br />---------------------------------------------------------------------------
* <br /> APL/Software GmbH - Berlin
* <br />##### generated by ClaviusXPress (http://www.sos-berlin.com) #########
* <br />---------------------------------------------------------------------------
* </p>
* \author oh
* @version $Id: JSObjProcessClass.java 20718 2013-07-18 18:16:10Z kb $
* \see reference
*
* Created on 09.02.2011 15:09:33
 */

/**
 * @author oh
 *
 */
public class JSObjProcessClass extends ProcessClass {

	@SuppressWarnings("unused")
	private final String		conClassName	= "JSObjProcessClass";
	@SuppressWarnings("unused")
	private static final Logger	logger			= Logger.getLogger(JSObjProcessClass.class);

	public final static String fileNameExtension = ".process_class.xml";

	public JSObjProcessClass (final SchedulerObjectFactory schedulerObjectFactory) {
		super();
		objFactory = schedulerObjectFactory;
		super.strFileNameExtension = fileNameExtension;
	}

	public JSObjProcessClass (final SchedulerObjectFactory schedulerObjectFactory, final ProcessClass origOrder) {
		super();
		super.strFileNameExtension = fileNameExtension;
		objFactory = schedulerObjectFactory;
		setObjectFieldsFrom(origOrder);
	}

	@SuppressWarnings("unchecked")
	public JSObjProcessClass(final SchedulerObjectFactory schedulerObjectFactory, final ISOSVirtualFile pobjVirtualFile) {
		objFactory = schedulerObjectFactory;
		objJAXBElement = (JAXBElement<JSObjBase>) unMarshal(pobjVirtualFile);
		setObjectFieldsFrom(objJAXBElement.getValue());
		setHotFolderSrc(pobjVirtualFile);
	}

	@Override
	public String getObjectName() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getJobName";

		String name = this.getHotFolderSrc().getName();
		int i = name.indexOf(fileNameExtension);
		if (i != -1) {
			name = name.substring(0, name.indexOf(fileNameExtension));
		}
		name = new File(name).getName();

		return name;

	} // private String getJobName

    public void setMaxProcesses(final int value) {
        maxProcesses = Int2BigInteger(value);
    }

}