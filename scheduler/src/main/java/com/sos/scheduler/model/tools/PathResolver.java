/**
 * Copyright (C) 2014 BigLoupe http://bigloupe.github.io/SoS-JobScheduler/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
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
package com.sos.scheduler.model.tools;

import java.io.File;
import java.io.FileNotFoundException;
import org.apache.log4j.Logger;
import com.sos.JSHelper.Exceptions.JobSchedulerException;

public class PathResolver {
	
	private final static Logger logger = Logger.getLogger(PathResolver.class);

	private PathResolver() {}
	
	public static String getRelativePath(String rootDir, String baseDir, String relativeName) {
//		logger.debug("getRelativePath: rootDir = " + rootDir);
//		logger.debug("getRelativePath: baseDir = " + baseDir);
//		logger.debug("getRelativePath: relativePath = " + relativePath);
		String result = normalizePath(relativeName);
		if (!result.startsWith("/")) {
			result = getPath(baseDir, relativeName);
			String rootPath = resolvePath(rootDir);
			if (!result.startsWith(rootPath))
				throw new JobSchedulerException("the path " + result + " point outside the root " + rootPath);
			result = result.replace(normalizePath(rootDir), "");
		}
		return result;
	}
	
	public static String getAbsolutePath(String rootDir, String baseDir, String relativeName) {
		return normalizePath(rootDir) + getRelativePath(rootDir, baseDir, relativeName);
	}
	
	public static boolean isAbsolutePath(String path) {
		return (isAbsoluteWindowsPath(path) || isAbsoluteUnixPath(path));
	}
	
	public static boolean isAbsoluteWindowsPath(String path) {
		String normalizedPath = normalizePath(path);
		return (normalizedPath.substring(1,3).equals(":/"));
	}
	
	public static boolean isAbsoluteUnixPath(String path) {
		String normalizedPath = normalizePath(path);
		return (normalizedPath.substring(0,1).equals("/"));
	}
	
	private static String getPath(String basePath, String relativeName) {
		String result = "";
		try {
			result = getPath(basePath, relativeName, false);
		} catch (FileNotFoundException e) {} 
		return result;
	}
	
	private static String getPath(String basePath, String relativePath, boolean throwException) throws FileNotFoundException {
		basePath = normalizePath(basePath);
		testPath(basePath,throwException);
		relativePath = normalizePath(relativePath);
		if (relativePath.startsWith("/"))
			return relativePath;			
//		if (!relativePath.startsWith("/"))
		basePath += "/";
		String result = resolvePath(basePath + relativePath);
		testPath(result,throwException);
		return result;
	}
	
	private static String stripTrailingSlash(String text) {
		return (text.endsWith("/")) ? text.substring(0,text.length()-1) : text;
	}
	
	private static String normalizeSlashes(String text) {
		return text.replace("://", ":::").replace("//", "/").replace(":::", "://");
	}
	
	private static void testPath(String path, boolean throwException) throws FileNotFoundException {
		File f = new File(path);
		if (!f.exists()) {
			String message = "the directory " + path + " does not exist.";
			if (throwException) throw new FileNotFoundException(message);
			logger.warn(message);
		}
	}

	public static String normalizePath(String path) {
		return normalizeSlashes(stripTrailingSlash(path.replace("\\", "/")));
	}

	public static String resolvePath(String path) {
		String result = normalizePath(path);
		if (result.startsWith("./")) result = result.substring(2);
		if (result.endsWith("/.")) result = result.substring(0,result.length()-2);
		String workingCopy = result.replace("/./","/");
		do {
			result = workingCopy;
			workingCopy = parseRelative(result);
		} while(!workingCopy.equals(result));
		return result;
	}
		
	private static String parseRelative(String path) {
		String result = path;
		String[] arr = path.split("/");
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals("..")) {
				result = removePart(arr,i-1,i);
				break;
			}
		}
		return result;
	}
	
	private static String removePart(String[] arr, int start, int end) {
		StringBuffer result = new StringBuffer();
		for (int j = 0; j < arr.length; j++) {
			if (j < start || j > end ) {
				result.append(arr[j]);
				result.append("/");
			}
		}
		return stripTrailingSlash(result.toString());
	}
	
}
