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
 * JobSchedulerCreateMD5Job.java
 * Created on 18.06.2008
 * 
 */
package sos.scheduler.file;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import sos.scheduler.job.JobSchedulerJob;
import sos.spooler.Variable_set;
import sos.util.SOSCrypt;

import com.sos.JSHelper.Basics.JSVersionInfo;

public class JobSchedulerMD5File extends JobSchedulerJob {
	@SuppressWarnings("unused")
	private final String conSVNVersion = "$Id: JobSchedulerMD5File.java 15153 2011-09-14 11:59:34Z kb $";
	private static final String	conFilenameExtensionMD5	= ".md5";
	private static final String	conJobParameterFILE		= "file";
	private static final String	conJobParamMD5_SUFFIX	= "md5_suffix";
	private static final String	conModeCREATE			= "create";
	private static final String	conJobParamMODE			= "mode";

	public boolean spooler_process() throws Exception {
		String strParamName = "";
		String strMD5FilenameExtension = conFilenameExtensionMD5;
		String fileName = "";
		String mode = conModeCREATE;
		try {
			getLogger().debug(JSVersionInfo.getVersionString());
			getLogger().debug(conSVNVersion);

			// Job oder Order
			Variable_set params = spooler.create_variable_set();
			if (spooler_task.params() != null)
				params.merge(spooler_task.params());
			if (spooler_job.order_queue() != null && spooler_task.order().params() != null)
				params.merge(spooler_task.order().params());
			// mandatory parameters
			strParamName = conJobParameterFILE;
			if (params.var(strParamName) != null && params.var(strParamName).length() > 0) {
				fileName = params.var(strParamName);
				// To make orderparams available for substitution in orderparam value
				while (fileName.matches("^.*%[^%]+%.*$")) {
					String p = fileName.replaceFirst("^.*%([^%]+)%.*$", "$1");
					String s = params.var(p);
					s = s.replace('\\', '/');
					fileName = fileName.replaceAll("%" + p + "%", s);
					getLogger().debug("processing job parameter [" + strParamName + "]: substitute %" + p + "% with " + s);
				}
			}
			else
				throw new Exception("job parameter is missing: [" + strParamName + "]");
			getLogger().info(".. job parameter [" + strParamName + "]: " + fileName);
			strParamName = conJobParamMD5_SUFFIX;
			if (params.var(strParamName) != null && params.var(strParamName).length() > 0) {
				strMD5FilenameExtension = params.var(strParamName);
				getLogger().info(".. job parameter [" + strParamName + "]: " + strMD5FilenameExtension);
			}
			strParamName = conJobParamMODE;
			if (params.var(strParamName) != null && params.var(strParamName).length() > 0) {
				mode = params.var(strParamName);
				getLogger().info(".. job parameter [" + strParamName + "]: " + mode);
			}
			File file = new File(fileName);
			if (!file.canRead()) {
				getLogger().warn(String.format("Failed to read file: '%1$s'", file.getAbsolutePath()));
				return false;
			}
			File md5File = new File(file.getAbsolutePath() + strMD5FilenameExtension);
			String strFileMD5 = SOSCrypt.MD5encrypt(file);
			getLogger().info("md5 of " + file.getAbsolutePath() + ": " + strFileMD5);
			if (mode.equalsIgnoreCase(conModeCREATE)) {
				getLogger().debug1("creating md5 file: " + md5File.getAbsolutePath());
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(md5File)));
				out.write(strFileMD5);
				out.close();
			}
			else {
				getLogger().debug1("checking md5 file: " + md5File.getAbsolutePath());
				BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(md5File)));
				String strMD5fromFile = in.readLine();
				in.close();
				if (strMD5fromFile != null) {
					// get only 1st part in case of md5sum format
					strMD5fromFile = strMD5fromFile.split("\\s+")[0];
				}
				else
					strMD5fromFile = "";
				getLogger().debug3("md5 from " + md5File.getAbsolutePath() + ": " + strMD5fromFile);
				if (strMD5fromFile.equalsIgnoreCase(strFileMD5)) {
					getLogger().info("md5 checksums are equal.");
				}
				else {
					getLogger().warn("md5 checksums are different.");
					return false;
				}
			}
			return (spooler_job.order_queue() != null);
		}
		catch (Exception e) {
			try {
				e.printStackTrace(System.err);
				getLogger().error("error occurred in JobSchedulerCreateMD5File: " + e.getMessage());
			}
			catch (Exception x) {
			}
			return false;
		}
	}
}
