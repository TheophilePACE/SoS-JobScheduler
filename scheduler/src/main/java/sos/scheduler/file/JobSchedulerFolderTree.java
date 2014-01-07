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
import java.io.IOException;

import org.apache.log4j.Logger;

import com.sos.JSHelper.Basics.JSJobUtilities;
import com.sos.JSHelper.Basics.JSToolBox;
import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.i18n.annotation.I18NResourceBundle;

/**
 * \class 		JobSchedulerFolderTree - Workerclass for "check wether a file exist"
 *
 * \brief AdapterClass of JobSchedulerFolderTree for the SOSJobScheduler
 *
 * This Class JobSchedulerFolderTree is the worker-class.
 *

 *
 * see \see J:\E\java\development\com.sos.scheduler\src\sos\scheduler\jobdoc\JobSchedulerFolderTree.xml for (more) details.
 *
 * \verbatim ;
 * mechanicaly created by C:\Users\KB\eclipse\sos.scheduler.xsl\JSJobDoc2JSWorkerClass.xsl from http://www.sos-berlin.com at 20110805104813 
 * \endverbatim
 */
@I18NResourceBundle(baseName = "com_sos_scheduler_messages", defaultLocale = "en")
public class JobSchedulerFolderTree extends JSToolBox implements JSJobUtilities {
	private final String					conClassName		= "JobSchedulerFolderTree";											//$NON-NLS-1$
	private static Logger					logger				= Logger.getLogger(JobSchedulerFolderTree.class);
	private final String					conSVNVersion		= "$Id: JobSchedulerFolderTree.java 19333 2013-03-02 09:49:40Z kb $";

	protected JobSchedulerFolderTreeOptions	objOptions			= null;
	private JSJobUtilities					objJSJobUtilities	= this;

	private SOSXMLHelper objXML = null;
	
	String strDefaultFileName = "c:/temp/SOSFolderTree.xml";
	String strXSLTFileName = "http://www.sos-berlin.com/schema/SOSFolderTree.xsl";
	String strXMLFileName = "c:/kb/SOSFolderTreeTest.xml";
	String strOutFileName = "";

	/**
	 * 
	 * \brief JobSchedulerFolderTree
	 *
	 * \details
	 *
	 */
	public JobSchedulerFolderTree() {
		super("com_sos_scheduler_messages");
	}

	/**
	 * 
	 * \brief Options - returns the JobSchedulerFolderTreeOptionClass
	 * 
	 * \details
	 * The JobSchedulerFolderTreeOptionClass is used as a Container for all Options (Settings) which are
	 * needed.
	 *  
	 * \return JobSchedulerFolderTreeOptions
	 *
	 */
	public JobSchedulerFolderTreeOptions Options() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Options"; //$NON-NLS-1$

		if (objOptions == null) {
			objOptions = new JobSchedulerFolderTreeOptions();
		}
		return objOptions;
	}

	/**
	 * 
	 * \brief Options - set the JobSchedulerFolderTreeOptionClass
	 * 
	 * \details
	 * The JobSchedulerFolderTreeOptionClass is used as a Container for all Options (Settings) which are
	 * needed.
	 *  
	 * \return JobSchedulerFolderTreeOptions
	 *
	 */
	public JobSchedulerFolderTreeOptions Options(final JobSchedulerFolderTreeOptions pobjOptions) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Options"; //$NON-NLS-1$

		objOptions = pobjOptions;
		return objOptions;
	}

	/**
	 * 
	 * \brief Execute - Start the Execution of JobSchedulerFolderTree
	 * 
	 * \details
	 * 
	 * For more details see
	 * 
	 * \see JobSchedulerAdapterClass 
	 * \see JobSchedulerFolderTreeMain
	 * 
	 * \return JobSchedulerFolderTree
	 *
	 * @return
	 */
	public JobSchedulerFolderTree Execute() throws Exception {
		final String conMethodName = conClassName + "::Execute"; //$NON-NLS-1$

		logger.debug(Messages.getMsg("JSJ-I-110", conMethodName));

		try {
			Options().CheckMandatory();
			logger.debug(Options().toString());
			
			try
			{
				objXML = new SOSXMLHelper(strXMLFileName, strXSLTFileName);
				
				SOSFolderStatistic objStat = new SOSFolderStatistic(objXML);
				objXML.XMLTag("FolderTree");
				RecurseFolder(objOptions.file_path.Value(), objStat);
				objXML.XMLTagE("FolderTree");

			} catch (IOException e)
			{
				e.printStackTrace();
			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				objXML.close();
			}

			System.out.println("***lines written: " + String.valueOf(objXML.NoOfLinesWritten()));
			System.out.println("SOSFolderTree - normal end");

		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			String strM = Messages.getMsg("JSJ-I-107");
			logger.error(Messages.getMsg(strM, conMethodName), e);
			throw new JobSchedulerException(strM);

		}
		finally {
		}

		logger.debug(Messages.getMsg("JSJ-I-111", conMethodName));
		return this;
	}

	public void init() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::init"; //$NON-NLS-1$
		doInitialize();
	}

	/**
	 * Method RecurseFolder.
	 * @param args
	 * @param pobjS
	 */
	private void RecurseFolder(final String pstrFolderName, final SOSFolderStatistic pobjS)
	{

		SOSFolderStatistic lobjS = new SOSFolderStatistic(objXML);

		try
		{
			File flePathName = new File(pstrFolderName);
			if (! flePathName.exists()) {
				throw new Exception ("Path does not exist (may be not valid): " + pstrFolderName);
			}
			String[] strFileNames = flePathName.list();

			objXML.XMLTag("Folder");
			objXML.XMLTagV("Name", flePathName.getCanonicalPath());

			for (String strFileName : strFileNames) {
				File fleFile = new File(flePathName.getPath(), strFileName);

				if (fleFile.isDirectory())
				{
					if (lobjS.getNoOfFolders() == 0)
					{
						objXML.XMLTag("Folders");
					}
					lobjS.incrNoOfFolders();
					SOSFolderStatistic lobjSt = new SOSFolderStatistic(objXML);
					RecurseFolder(fleFile.getPath(), lobjSt);
					pobjS.Cumulate(lobjSt);
				} else
				{
					lobjS.incrNoOfFiles();
					lobjS.incrSize(fleFile.length());
				}
			}

			if (lobjS.getNoOfFolders() > 0)
			{
				objXML.XMLTagE("Folders");
			}

			lobjS.toXML("FolderStatistic");

			pobjS.Cumulate(lobjS);
			pobjS.toXML("TreeStatistic");

			objXML.XMLTagE("Folder");

		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}			

	private void doInitialize() {
	} // doInitialize

	@Override
	public String myReplaceAll(final String pstrSourceString, final String pstrReplaceWhat, final String pstrReplaceWith) {

		String newReplacement = pstrReplaceWith.replaceAll("\\$", "\\\\\\$");
		return pstrSourceString.replaceAll("(?m)" + pstrReplaceWhat, newReplacement);
	}

	/**
	 * 
	 * \brief replaceSchedulerVars
	 * 
	 * \details
	 * Dummy-Method to make sure, that there is always a valid Instance for the JSJobUtilities.
	 * \return 
	 *
	 * @param isWindows
	 * @param pstrString2Modify
	 * @return
	 */
	@Override
	public String replaceSchedulerVars(final boolean isWindows, final String pstrString2Modify) {
		logger.debug("replaceSchedulerVars as Dummy-call executed. No Instance of JobUtilites specified.");
		return pstrString2Modify;
	}

	/**
	 * 
	 * \brief setJSParam
	 * 
	 * \details
	 * Dummy-Method to make shure, that there is always a valid Instance for the JSJobUtilities.
	 * \return 
	 *
	 * @param pstrKey
	 * @param pstrValue
	 */
	@Override
	public void setJSParam(final String pstrKey, final String pstrValue) {

	}

	@Override
	public void setJSParam(final String pstrKey, final StringBuffer pstrValue) {

	}

	/**
	 * 
	 * \brief setJSJobUtilites
	 * 
	 * \details
	 * The JobUtilities are a set of methods used by the SSH-Job or can be used be other, similar, job-
	 * implementations.
	 * 
	 * \return void
	 *
	 * @param pobjJSJobUtilities
	 */
	@Override
	public void setJSJobUtilites(final JSJobUtilities pobjJSJobUtilities) {

		if (pobjJSJobUtilities == null) {
			objJSJobUtilities = this;
		}
		else {
			objJSJobUtilities = pobjJSJobUtilities;
		}
		logger.debug("objJSJobUtilities = " + objJSJobUtilities.getClass().getName());
	}

	@Override
	public String getCurrentNodeName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStateText(final String pstrStateText) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCC(final int pintCC) {
		// TODO Auto-generated method stub
		
	}

} // class JobSchedulerFolderTree
