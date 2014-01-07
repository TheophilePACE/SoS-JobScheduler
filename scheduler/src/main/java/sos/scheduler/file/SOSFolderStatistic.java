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
package sos.scheduler.file;


/**
 * @author SGLO111
 *
 * 09.12.2003
 * 
 */
 public class SOSFolderStatistic
{
	private long lngNoOfFiles = 0;
	private long lngNoOfFolders = 0;
	private long lngSize = 0;

	private SOSXMLHelper objXML = null;

	public SOSFolderStatistic(SOSXMLHelper pobjXML)
	{
		objXML = pobjXML;
	}
	/**
	 * Returns the lngNoOfFiles.
	 * @return long
	 */
	public long getNoOfFiles()
	{
		return lngNoOfFiles;
	}

	/**
	 * Returns the NoOfFolders.
	 * @return long
	 */
	public long getNoOfFolders()
	{
		return lngNoOfFolders;
	}

	/**
	 * Returns the lngSize.
	 * @return long
	 */
	public long getSize()
	{
		return lngSize;
	}

	/**
	 * Sets the lngNoOfFiles.
	 * @param lngNoOfFiles1 The lngNoOfFiles to set
	 */
	public void setNoOfFiles(long lngNoOfFiles1)
	{
		this.lngNoOfFiles = lngNoOfFiles1;
	}

	/**
	 * Method incrNoOfFiles.
	 */
	public void incrNoOfFiles()
	{
		this.lngNoOfFiles++;
	}

	/**
	 * Sets the lngNoOfFolders.
	 * @param lngNoOfFolders1 The lngNoOfFolders to set
	 */
	public void setNoOfFolders(long lngNoOfFolders1)
	{
		this.lngNoOfFolders = lngNoOfFolders1;
	}

	/**
	 * Method incrNoOfFolders.
	 */
	public void incrNoOfFolders()
	{
		this.lngNoOfFolders++;
	}

	/**
	 * Sets the lngSize.
	 * @param lngSize1 The lngSize to set
	 */
	public void setSize(long lngSize1)
	{
		this.lngSize = lngSize1;
	}

	/**
	 * Method incrSize.
	 * @param lngSize1
	 */
	public void incrSize(long lngSize1)
	{
		this.lngSize += lngSize1;
	}

	/**
	 * Method Cumulate.
	 * @param pobjS
	 */
	public void Cumulate(SOSFolderStatistic pobjS)
	{
		this.lngNoOfFiles += pobjS.getNoOfFiles();
		this.lngNoOfFolders += pobjS.getNoOfFolders();
		this.lngSize += pobjS.getSize();
	}

	/**
	 * Method toXML.
	 * @param strTagName
	 * @throws Exception
	 */
	public void toXML(String strTagName) throws Exception
	{

		if (objXML != null)
		{
			objXML.XMLTag(strTagName);
			objXML.XMLTagV("FileCnt", lngNoOfFiles);
			objXML.XMLTagV("FolderCnt", lngNoOfFolders);
			objXML.XMLTagV("Size", lngSize);
			objXML.XMLTagE(strTagName);
		}

	}
}
