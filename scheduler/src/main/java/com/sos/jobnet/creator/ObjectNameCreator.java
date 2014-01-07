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
package com.sos.jobnet.creator;

public class ObjectNameCreator {

	protected final static String DELIMITER = "/";
	
	private final String baseFolder;			// the base folder (live folder)
	private final String objectId;
	private final Type type;
	private final String objectName;			// the object name used in JobScheduler 
	private final String objectFileName;		// the filename of the object (based on base folder)
	private final String objectFilePath;		// the file path of the object (based on base folder) 
	private final String fullName;				// the full filename of the object
	private final String fullPath;				// the full path of the folder the object stored in
	
	public ObjectNameCreator(String baseFolder, String objectId, Type type) {
		this.baseFolder = stripTrailingSlash(baseFolder.trim());
		this.objectId = objectId.trim();
		this.type = type;
		String[] arr = this.objectId.split(DELIMITER);
		this.objectName = getObjectName(arr[arr.length-1]);
		this.objectFilePath = normalizeSlashes(stripTrailingSlash( getObjectPath(this.objectId)));
//		this.objectFileName = objectFilePath + DELIMITER + this.objectName + "." + this.type.getExtension();
		this.objectFileName = this.objectName + "." + this.type.getExtension();
		this.fullPath = stripTrailingSlash(this.baseFolder + ensureLeadingSlash(objectFilePath) );
		this.fullName = this.fullPath + ensureLeadingSlash(this.objectFileName);
	}
	
	public static String concat(String... text) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < text.length; i++) {
			sb.append(text[i]);
			sb.append(DELIMITER);
		}
		return stripTrailingSlash(sb.toString());
	}
	
	public static String stripTrailingSlash(String text) {
		return (text.endsWith(DELIMITER)) ? text.substring(0,text.length()-1) : text;
	}
	
	public static String normalizeSlashes(String text) {
		return text.replace("//", "/");
	}
	
	public static String ensureLeadingSlash(String text) {
		return (text.startsWith(DELIMITER)) ? text : DELIMITER + text;
	}
	
	private String getObjectName(String id) {
		return id.replace("." + type.getExtension(), "");
	}
	
	private static String getObjectPath(String id) {
		StringBuffer result = new StringBuffer();
		String[] arr = id.split(DELIMITER);
		for (int i = 0; i < arr.length-1; i++) {
//			if (!arr[i].equals(".")) {
				result.append(arr[i]);
				result.append(DELIMITER);
//			}
		}
		return result.toString();
	}

	public String getObjectId() {
		return objectId;
	}

	public Type getType() {
		return type;
	}

	public String getObjectName() {
		return objectName;
	}

	public String getObjectFileName() {
		return objectFileName;
	}

	public String getObjectFilePath() {
		return objectFilePath;
	}

	public String getFullPath() {
		return fullPath;
	}

	public String getFullName() {
		return fullName;
	}

	public String getBasePath() {
		return baseFolder;
	}

	public enum Type {
		ORDER,
		JOB_CHAIN,
		SCHEDULE,
		JOB,
		LOCK,
		PROCESS_CLASS
		;
		
		public String getExtension() {
			return name().toLowerCase() + ".xml";
		}

	}
}
