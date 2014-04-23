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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author SGLO111
 *
 */
public class SOSXMLHelper
{

	private int intIndent = 0;
	private String fleXMLFileName = "";
	private String fleXSLTFileName = "";
	private String strXMLDirective =
		"?xml version='1.0' encoding='ISO-8859-1' ?";
	private String strXSLDirective = 
		"?xml-stylesheet href='$FileName$' type='text/sos.scheduler.xsl' ?";
	private PrintWriter fleOut = null;
	private long lngNoOfLinesWritten = 0;

	public SOSXMLHelper()
	{

	}

	public SOSXMLHelper(String pstrXMLFileName)
	{
		this.fleXMLFileName = pstrXMLFileName;
		System.out.println(this.fleXMLFileName);
		this.fleXSLTFileName = "http://be-list-viewer.eu.schering.net/systems/sos.scheduler.xsl/SOSFolderTree.xsl";
	}

	public SOSXMLHelper(String pstrXMLFileName, String pstrXSLTFileName)
	{
		this.fleXMLFileName = pstrXMLFileName;
		System.out.println("XML-File Name: " + this.fleXMLFileName);
		this.fleXSLTFileName = pstrXSLTFileName;
		System.out.println("XSLT-File Name: " + this.fleXSLTFileName);
	}

	public void XMLTagV(String strTagName, String strTagValue) throws Exception
	{
		Out(
			this.getStartTag(strTagName)
				+ strTagValue
				+ this.getEndTag(strTagName));
	}

	public long NoOfLinesWritten()
	{
		return this.lngNoOfLinesWritten;
	}

	public void XMLTagV(String strTagName, int intTagValue) throws Exception
	{
		this.XMLTagV(strTagName, String.valueOf(intTagValue));
	}

	public void XMLTagV(String strTagName, long lngTagValue) throws Exception
	{
		this.XMLTagV(strTagName, String.valueOf(lngTagValue));
	}

	public void XMLTag(String strTagName) throws Exception
	{
		Out(getStartTag(strTagName));
		this.intIndent++;
	}

	public void XMLTagE(String strTagName) throws Exception
	{
		this.intIndent--;
		Out(getEndTag(strTagName));
	}

	public String getStartTag(String strTagName)
	{
		return "<" + strTagName + ">";
	}

	public String getEndTag(String strTagName)
	{
		return "</" + strTagName + ">";
	}

	public void Out(String strBuffer) throws IOException, Exception
	{

		if (fleOut == null)
		{
			if (this.fleXMLFileName.length() <= 0)
			{
				throw new Exception("no output-file-name given");
			}

			File fl = new File(this.fleXMLFileName);
			fl.createNewFile();
			fleOut = new PrintWriter(new FileWriter(fl));
			fleOut.println(getStartTag(strXMLDirective));
			
			if (this.fleXSLTFileName.length() > 0) {
				String strT = strXSLDirective.replaceAll("\\$FileName\\$", this.fleXSLTFileName);
				fleOut.println(getStartTag(strT));
			}
			
		}

		if (fleOut == null)
		{
			throw new Exception("no output-file specfied");
		}
		for (int i = 0; i < this.intIndent; i++)
		{
			fleOut.print(" ");
		}
		fleOut.println(strBuffer);
		lngNoOfLinesWritten++;

	}

	public int getIndent()
	{
		return this.intIndent;
	}

	public void setXMLFileName(String pfleXMLFileName) throws IOException
	{

		this.fleXMLFileName = pfleXMLFileName;

	}

	public void close()
	{
		if (fleOut != null)
		{
			fleOut.close();
			fleOut = null;
		}
	}

	public void finalize()
	{

		this.close();
	}
}
