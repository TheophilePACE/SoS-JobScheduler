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
package sos.net.sosftp;

import java.io.File;
import java.util.Iterator;
import java.util.Properties;

import sos.net.SOSFTPCommand;
import sos.util.SOSLogger;
import sos.configuration.SOSConfiguration;

public class SOSFTPCommandSSH extends SOSFTPCommand {
	
	@SuppressWarnings("unused")
	String	conSVNVersion					= "$Id: SOSFTPOptions.java 17481 2012-06-29 15:40:36Z kb $";

	public SOSFTPCommandSSH(SOSLogger logger, Properties arguments_)
			throws Exception {
		super(logger, arguments_);
			
	}

	public SOSFTPCommandSSH(SOSConfiguration sosConfiguration_, SOSLogger logger)
			throws Exception {
		super(sosConfiguration_, logger);
		
	}
	
    
	/**
	 * 
	 * overwrite Method from super class.
	 * 
	 * Es darf keinen Banner für das SSH Job (=operation=execute) ausgegeben werden, wenn 
	 * banner_header bzw. banner_footer nicht angegeben sind.
	 * 
	 * This program logs output to stdout or to a file that has been specified by the parameter log_filename. 
	 * A template can be used in order to organize the output that is created. The output is grouped into header, file list and footer.
	 * This specifies a template file for header and footer output.
	 * Templates can use internal variables and parameters as placeholders in the form %{placeholder}. 
	 *   
	 * @param header
	 * @return String
	 * @throws Exception
	 */
	public String getBanner(boolean header) throws Exception {
		this.bannerHeader = "";
		this.bannerFooter = "";
		return super.getBanner(header);	
	}
	

}
