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
package com.sos.scheduler.model;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.JSHelper.Options.SOSOptionFolderName;
import com.sos.VirtualFileSystem.Factory.VFSFactory;
import com.sos.VirtualFileSystem.Interfaces.ISOSVFSHandler;
import com.sos.VirtualFileSystem.Interfaces.ISOSVfsFileTransfer;
import com.sos.VirtualFileSystem.Interfaces.ISOSVirtualFile;
import com.sos.scheduler.model.tools.PathResolver;

public class LiveConnector {

	private final static Logger logger = Logger.getLogger(LiveConnector.class);
	private final String liveFolder;
	private final ISOSVfsFileTransfer fileSystemHandler;
	private final ISOSVirtualFile hotFolderHandle;
	
	private String workingDirectory;

	public LiveConnector(SOSOptionFolderName folderName) throws MalformedURLException {
		this( LiveConnector.getUrl(folderName.Value()) );
	}

	public LiveConnector(File folderName) throws MalformedURLException {
		this( LiveConnector.getUrl(folderName.getAbsolutePath()) );
	}

	public LiveConnector(URL url) {
		this.fileSystemHandler = connect( url.toExternalForm() );
		this.liveFolder = getUrl(url.toExternalForm()).getPath();
		setCurrentFolder(liveFolder);
		this.hotFolderHandle = fileSystemHandler.getFileHandle(liveFolder);
	}

	public static URL getUrl(String urlPath) {
		URL result = null;
		String path = PathResolver.normalizePath(urlPath);
		if (path.endsWith("/")) path = path.substring(0,path.length()-1);
		try {
			result = new URL(path);
		} catch (MalformedURLException e) {
			try {
				result = new URL("file://" + path);
			} catch (MalformedURLException e1) {
				throw new JobSchedulerException("the url " + urlPath + " is not valid.",e);
			}
		}
		return result;
	}

	public static String getPath(String urlPath) {
		String result = LiveConnector.getUrl(urlPath).getPath();
		if (result.endsWith("/")) result = result.substring(0,result.length()-1);
		return result;
	}

	// Possible Elements of an URL are:
	//
	// http://hans:geheim@www.example.org:80/demo/example.cgi?land=de&stadt=aa#geschichte
	// | | | | | | | |
	// | | | host | url-path searchpart fragment
	// | | password port
	// | user
	// protocol
	private static ISOSVfsFileTransfer connect(String folder) {
		ISOSVfsFileTransfer result = null;
		try {
			ISOSVFSHandler vfs = VFSFactory.getHandler(folder);
			if (vfs == null)
				throw new JobSchedulerException();
			result = (ISOSVfsFileTransfer) vfs;
		} catch (Exception e) {
			throw new JobSchedulerException("error to connect folder " + folder,e);
		}
		return result;
	}
	
	public ISOSVirtualFile getHotFolderHandle() {
		return hotFolderHandle;
	}

	public String getLiveFolder() {
		return liveFolder;
	}
	
	public String getCurrentFolder() {
		return workingDirectory;
	}
	
	public void setCurrentFolder(String directory) {
		String path = LiveConnector.getUrl(directory).getPath();
		if (!path.startsWith(getLiveFolder())) {
			String msgText = "the working directory " + path + " has to be a subfolder of " + getLiveFolder();
			logger.error(msgText);
			throw new JobSchedulerException(msgText);
		}
		this.workingDirectory = path;
	}
	
	/**
	 * Gets the base folder for an order. An order like /folder/orderId bases on the root directory (e.g. the
	 * live folder). Orders with a relative name such as ../folder/orderId based on the working directory.
	 * @param baseName
	 * @return
	 */
	public String selectBaseFolder(String baseName) {
		return (baseName.startsWith("/")) ? getLiveFolder() : getCurrentFolder();
	}

	public ISOSVfsFileTransfer getFileSystemHandler() {
		return fileSystemHandler;
	}

}
