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
package com.sos.scheduler.converter.graphviz;

import static com.sos.scheduler.messages.JSMessages.JSJ_F_107;
import static com.sos.scheduler.messages.JSMessages.JSJ_I_110;
import static com.sos.scheduler.messages.JSMessages.JSJ_I_111;

import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.sos.JSHelper.Basics.JSJobUtilitiesClass;
import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.JSHelper.io.Files.JSTextFile;
import com.sos.VirtualFileSystem.Factory.VFSFactory;
import com.sos.VirtualFileSystem.Interfaces.ISOSVFSHandler;
import com.sos.VirtualFileSystem.Interfaces.ISOSVfsFileTransfer;
import com.sos.VirtualFileSystem.Interfaces.ISOSVirtualFile;
import com.sos.VirtualFileSystem.shell.cmdShell;
import com.sos.scheduler.model.SchedulerHotFolder;
import com.sos.scheduler.model.SchedulerHotFolderFileList;
import com.sos.scheduler.model.SchedulerObjectFactory;
import com.sos.scheduler.model.objects.JSObjBase;
import com.sos.scheduler.model.objects.JSObjJobChain;
import com.sos.scheduler.model.objects.JSObjOrder;
import com.sos.scheduler.model.objects.JobChain.JobChainNode;
import com.sos.scheduler.model.objects.JobChainNodeEnd;
import com.sos.scheduler.model.objects.Spooler;

/**
 * \class 		JSObjects2Graphviz - Workerclass for "JSObjects2Graphviz"
 *
 * \brief AdapterClass of JSObjects2Graphviz for the SOSJobScheduler
 *
 * This Class JSObjects2Graphviz is the worker-class.
 *

 *
 * see \see C:\Users\KB\AppData\Local\Temp\scheduler_editor-2781494595910967227.html for (more) details.
 *
 * \verbatim ;
 * mechanicaly created by C:\Users\KB\sos-berlin.com\jobscheduler\scheduler\config\JOETemplates\java\xsl\JSJobDoc2JSWorkerClass.xsl from http://www.sos-berlin.com at 20121108150924
 * \endverbatim
 */
public class JSObjects2Graphviz extends JSJobUtilitiesClass<JSObjects2GraphvizOptions> {
	private static final String	conPseudoNodeSTART	= "_start_";
	private final String				conClassName					= "JSObjects2Graphviz";											//$NON-NLS-1$
	private static Logger				logger							= Logger.getLogger(JSObjects2Graphviz.class);
	@SuppressWarnings("unused")
	private final String				conSVNVersion					= "$Id: JSObjects2Graphviz.java 20718 2013-07-18 18:16:10Z kb $";

	private ISOSVFSHandler				objVFS							= null;
	private ISOSVfsFileTransfer			objFileSystemHandler			= null;
	private SchedulerObjectFactory		objFactory						= null;
	private SchedulerHotFolderFileList	objSchedulerHotFolderFileList	= null;
	private String						strOutputFolderName				= "";
	private boolean						flgCreateCluster				= false;

	/**
	 *
	 * \brief JSObjects2Graphviz
	 *
	 * \details
	 *
	 */
	public JSObjects2Graphviz() {
		super(new JSObjects2GraphvizOptions());
	}

	public JSObjects2Graphviz initialize() throws Exception {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::initialize";

		Options().CheckMandatory();
		logger.debug(Options().dirtyString());

		String strTestHotFolder = objOptions.live_folder_name.Value();
		objVFS = VFSFactory.getHandler("local");
		//			objVFS = VFSFactory.getHandler(strTestHotFolder);
		objFileSystemHandler = (ISOSVfsFileTransfer) objVFS;

		objFactory = new SchedulerObjectFactory();
		objFactory.initMarshaller(Spooler.class);

		ISOSVirtualFile objHotFolder = objFileSystemHandler.getFileHandle(strTestHotFolder);
		SchedulerHotFolder objSchedulerHotFolder = objFactory.createSchedulerHotFolder(objHotFolder);
		logger.info(String.format("... load %1$s", strTestHotFolder));
		objSchedulerHotFolderFileList = objSchedulerHotFolder.loadRecursive();
		strOutputFolderName = objOptions.output_folder_name.Value();

		return this;
	} // private JSObjects2Graphviz initialize

	/**
	 *
	 * \brief Execute - Start the Execution of JSObjects2Graphviz
	 *
	 * \details
	 *
	 * For more details see
	 *
	 * \see JobSchedulerAdapterClass
	 * \see JSObjects2GraphvizMain
	 *
	 * \return JSObjects2Graphviz
	 *
	 * @return
	 */
	public JSObjects2Graphviz Execute() throws Exception {
		final String conMethodName = conClassName + "::Execute"; //$NON-NLS-1$

		JSJ_I_110.toLog(conMethodName);

		try {
			initialize();
			// TODO in die Options damit
			flgCreateCluster = false;

			for (JSObjBase obj : objSchedulerHotFolderFileList.getSortedFileList()) {
				if (obj instanceof JSObjJobChain) {
					String strOutFile = createGraphvizFile(obj);
					cmdShell objShell = new cmdShell();
					objShell.executeCommand("dot.exe -x -Tpdf " + strOutFile + " > " + strOutFile + ".pdf");
				}
			}

		}
		catch (Exception e) {
			throw new JobSchedulerException(JSJ_F_107.get(conMethodName) + ":" + e.getMessage(), e);
		}
		finally {
		}

		JSJ_I_111.toLog(conMethodName);

		return this;
	}

	public String createGraphvizFile(final JSObjBase obj) throws Exception {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::createGraphvizFile";
		String strOutputFileName = null;

		if (obj instanceof JSObjJobChain) {

			// TODO as a method in JSObjChain

			JSObjJobChain objChain = (JSObjJobChain) obj;
			String strName = objChain.getName();
			if (strName == null) {
				strName = objChain.getObjectName();
			}

			strOutputFileName = strOutputFolderName + strName + ".dot";
			JSTextFile objDotFile = new JSTextFile(strOutputFileName);

			// Get list of orders related to this JobChain

			Hashtable<String, JSObjOrder> tblOrders = new Hashtable<String, JSObjOrder>();
			for (JSObjBase objO : objSchedulerHotFolderFileList.getSortedFileList()) {
				if (objO instanceof JSObjOrder) {
					JSObjOrder objOrder = (JSObjOrder) objO;
					String strOrderName = objOrder.getJobChainName();
					if (strName.equalsIgnoreCase(strOrderName)) {
						tblOrders.put(strOrderName, objOrder);
					}
				}
			}

			String strSource = objChain.toXMLString();

			objDotFile.WriteLine("/* \n" + strSource + "\n*/\n");

			objDotFile.WriteLine("digraph " + AddQuotes(strName) + " {");
			objDotFile.WriteLine("rankdir = TB; size = \"8.27,11.69\";");

			objDotFile.WriteLine("graph [");
			objDotFile.WriteLine("label = " + AddQuotes(objChain.getTitle()));
			objDotFile.WriteLine("fontsize = 10");
			objDotFile.WriteLine("];");
			objDotFile.WriteLine("node [");
			objDotFile.WriteLine("fontsize = 8");
			objDotFile.WriteLine("shape = " + AddQuotes("box"));
			objDotFile.WriteLine("style = " + AddQuotes("rounded"));
			objDotFile.WriteLine("fontname = " + AddQuotes("Arial"));
			objDotFile.WriteLine("];");

			Hashtable<String, JobChainNode> tblNodes = new Hashtable<String, JobChainNode>();
			objDotFile.WriteLine(AddQuotes(conPseudoNodeSTART) + " [label = " + AddQuotes(conPseudoNodeSTART + ": " + strName) + ", shape = " + AddQuotes("box") + ", style = "
					+ AddQuotes("solid") + "];");
			objDotFile.WriteLine(AddQuotes("end") + " [label = " + AddQuotes("end" + ": " + strName) + ", shape = " + AddQuotes("box") + ", style = "
					+ AddQuotes("solid") + ", fontsize=8];");

			for (JSObjOrder objOrder : tblOrders.values()) {
				objDotFile.WriteLine(AddQuotes(objOrder.getObjectName()) + " [label = " + AddQuotes("Order - " + objOrder.getObjectName()) + "];");
			}

			for (Object objO : objChain.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd()) {
				String strState = "";
				String strErrorState = "";
				String strNextState = "";
				String strJobName = "";
				JobChainNode objNode = null;

				if (objO instanceof JobChainNode) {
					objNode = (JobChainNode) objO;
					strState = objNode.getState();
					strErrorState = objNode.getErrorState();
					strNextState = objNode.getNextState();
					strJobName = objNode.getJob();
				}
				if (objO instanceof JobChainNodeEnd) {
					JobChainNodeEnd objEndNode = (JobChainNodeEnd) objO;
					strState = objEndNode.getState();
					strJobName = "endNode";
				}

				if (strState.length() > 0 && tblNodes.get(strState) == null) {
					tblNodes.put(strState, objNode);
					// "0" [label = "0: virtual start "];
					if (strJobName == null) {
						strJobName = "endNode";
					}
					String strT = strState;
					if (strState.equalsIgnoreCase(strJobName) == false) {
						strT = strState + ": " + strJobName;
					}

					objDotFile.WriteLine(AddQuotes(strState) + " [label = " + AddQuotes(strT) + "];");
					if (strErrorState != null) {
						if (tblNodes.get(strErrorState) == null) {
							if (strErrorState.equalsIgnoreCase(strNextState) == false) {
								tblNodes.put(strErrorState, objNode);
								objDotFile.WriteLine(AddQuotes(strErrorState) + " [label = " + AddQuotes(strErrorState)
										+ ", color=\"red\", fillcolor=\"yellow\", style=\"filled\", fontcolor=\"blue\", fontsize=8];");
							}
						}
					}
				}
			}
			// TODO implement Method: getNodes()
			// TODO implement Method: getAllNodeNames()

			for (JSObjOrder objOrder : tblOrders.values()) {
				objDotFile.WriteLine(AddQuotes(objOrder.getObjectName()) + " -> " + AddQuotes(conPseudoNodeSTART));
			}

			boolean flgStart = true;
			String strState = null;
			String strNextState = null;
			String strLastNextState = null;
			for (Object objO : objChain.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd()) {
				if (objO instanceof JobChainNode) {
					JobChainNode objNode = (JobChainNode) objO;

					strState = objNode.getState();

					if (flgStart == true) {
						flgStart = false;
						objDotFile.WriteLine(AddQuotes(conPseudoNodeSTART) + " -> " + AddQuotes(strState));
						if (flgCreateCluster) {
							objDotFile.WriteLine("subgraph cluster_0 {");
							objDotFile.WriteLine("    style=filled;");
							objDotFile.WriteLine("color=lightgrey;");
							objDotFile.WriteLine("node [style=filled,color=white];");
						}
					}
					strNextState = objNode.getNextState();
					if (strNextState != null) {
						objDotFile.WriteLine(AddQuotes(strState) + " -> " + AddQuotes(strNextState)  + " [label=\" cc=0\", fontcolor=\"grey\", fontsize=7]");
						strLastNextState = strNextState;
					}
					//						else {
					//							objDotFile.WriteLine(AddQuotes(strState) + " -> " + AddQuotes("end"));
					//						}
				}
			}

			if (strLastNextState != null) {
				objDotFile.WriteLine(AddQuotes(strLastNextState) + " -> " + "end");
			}

			if (flgCreateCluster) {
				objDotFile.WriteLine("label = \"Process\";");
				objDotFile.WriteLine("}");
			}

			/**
			 * create the links to the error-states.
			 */
			Hashtable<String, JobChainNode> tblErrNodes = new Hashtable<String, JobChainNode>();
			for (Object objO : objChain.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd()) {
				if (objO instanceof JobChainNode) {
					JobChainNode objNode = (JobChainNode) objO;

					strState = objNode.getState();
					strNextState = objNode.getNextState();
					String strErrorState = objNode.getErrorState();
					if (strErrorState != null && strErrorState.equalsIgnoreCase(strNextState) == false) {
						objDotFile.WriteLine(AddQuotes(strState) + " -> " + AddQuotes(strErrorState) + " [label=\" error\", fontcolor=\"grey\", fontsize=7, style=\"dotted\", constraint=false]");
						tblErrNodes.put(strErrorState, objNode);
					}
					else {
					}
				}
			}

			if (flgCreateCluster) {
				objDotFile.WriteLine("subgraph cluster_1 {");
				objDotFile.WriteLine("    style=filled;");
				objDotFile.WriteLine("color=lightgrey;");
				objDotFile.WriteLine("node [style=filled,color=white];");
			}

			String strLastErrNode = "";
			for (JobChainNode objErrNode : tblErrNodes.values()) {
				String strErrNodeName = objErrNode.getErrorState();
				if (flgCreateCluster) {
					if (strLastErrNode.length() <= 0) {
						strLastErrNode = strErrNodeName;
					}
					else {
						objDotFile.WriteLine(AddQuotes(strLastErrNode) + " -> " + AddQuotes(strErrNodeName) + " [style=invis]");
						strLastErrNode = strErrNodeName;
					}
				}
				objDotFile.WriteLine(AddQuotes(strErrNodeName) + " -> " + AddQuotes("end"));
			}

			if (flgCreateCluster) {
				objDotFile.WriteLine("label = \"Error\";");
				objDotFile.WriteLine("}");
			}

			objDotFile.WriteLine("}");
			objDotFile.close();
			logger.info(String.format("file '%1$s' created", strOutputFileName));
		}
		else {
			logger.info(String.format("no JobChain", strOutputFileName));
		}

		return strOutputFileName;
	} // private void createGraphvizFile
} // class JSObjects2Graphviz
