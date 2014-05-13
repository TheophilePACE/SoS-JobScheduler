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
package com.sos.jobnet.graph;

import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import com.sos.JSHelper.Basics.JSToolBox;
import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.graphviz.GraphIO;
import com.sos.graphviz.enums.FileType;
import com.sos.graphviz.enums.RankDir;
import com.sos.hibernate.options.HibernateOptions;
import com.sos.jobnet.classes.JobNetConstants;
import com.sos.jobnet.classes.JobNetException;
import com.sos.jobnet.creator.JobNetPlanExecutor;
import com.sos.jobnetmodel.graph.GraphBuilder;
import com.sos.jobnetmodel.graph.GraphOptions;
import com.sos.jobnetmodel.objects.JobnetConverter;
import com.sos.jobnetmodel.objects.JobnetConverterImpl;
import com.sos.localization.Messages;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

public class JobNetGraphBuilder implements Runnable {

	private final static Logger logger = Logger.getLogger(JobNetGraphBuilder.class);
	private static final Messages msg = new JSToolBox(JobNetConstants.strBundleBaseName).getMessageObject();

	private final static String graphSubfolder = "graphs";

	public final static String HTML_EXTENSION = ".html";

	private FileType graphType = FileType.svg;
	private String extension = "." + graphType.name();

	private final static String htmlIndex = "jobnets.html";
	private final static String htmlSingleTemplate = "display_jobnet.html.template";
	private final static String htmlIndexTemplate = htmlIndex + ".template";
	private final static String checkIntervalForHtml = "5";

	private final String graphDir;
	private final String uuid;
	private final HibernateOptions hibernateOptions;
	private final File targetDir;
	private final String size;
	private final String ratio;
	File targetFile;

	private boolean buildStaticFile = false;
	private boolean isJobnetPresentInDb = false;

	public JobNetGraphBuilder(JobNetGraphBuilderOptions options) {
		String conMethodName = JobNetPlanExecutor.class.getSimpleName();
		this.hibernateOptions = options;
		this.uuid = options.uuid_jobnet_identifier.Value();
        this.size = options.graphSize.Value();
        this.ratio = options.graphRatio.Value();
		this.graphDir = graphDir(options);
		this.graphType = graphType(options);
		this.extension = "." + graphType.name();

		this.targetDir = new File(graphDir);
		targetDir.mkdirs();
		logger.debug(msg.getMsg(JobNetConstants.JOBNET_D_0001, conMethodName, "UUID", uuid));
	}

	private String graphDir(JobNetGraphBuilderOptions options) {
		String dir = options.GraphDir.Value().trim();
		if (dir.isEmpty()) {
			dir = options.SCHEDULER_DIRECTORY.Value() + "/" + graphSubfolder;
			logger.info("Parameter 'GraphDir' not specified - using default " + dir);
		}
		logger.info("Graphs will be stored in folder " + dir);
		return dir;
	}

	private FileType graphType(JobNetGraphBuilderOptions options) {
		FileType gType = null;
		try {
			gType = FileType.fromText(options.graphType.Value());
		}

		catch (Exception e) {
			logger.error(String.format("Wrong graphType: %s", options.graphType.Value()));
			e.printStackTrace();
		}
		return gType;

	}

	@Override
	public void run() {

		JobnetConverter converter = convertJobnetFromDB();
		if (converter != null) {
			logger.debug("Number of connectors: " + converter.numberOfConnectores());
			logger.debug("Number of head: " + converter.numberOfHeads());
			logger.debug("Number of normal nodes: " + converter.numberOfNormalNodes());
			logger.debug("Total Number of nodes: " + converter.getNodeList().size());
			logger.debug("Number of containers: " + converter.numberOfContainer());
			createGraph(converter);
		}

	}

	private JobnetConverter convertJobnetFromDB() {
		JobnetConverter converter = null;
		try {
			JobNetPlanAdapter adapter = new JobNetPlanAdapter(hibernateOptions, uuid);
			logger.debug("Number of adapted nodes: " + adapter.numberOfNodes());
			isJobnetPresentInDb = adapter.isJobnetPresentInDB();
			if (isJobnetPresentInDb) {
				converter = new JobnetConverterImpl(adapter);
				converter.run();
			} else {
				logger.warn("A jobnet with uuid " + uuid + " is not present in the DB.");
			}
		} catch (Exception e) {
			String msg = "Error converting jobnet from database.";
			logger.error(msg, e);
			throw new JobNetException(msg, e);
		}
		return converter;
	}

	private void createGraph(JobnetConverter converter) {
		GraphBuilder gb = null;
		try { 
			gb = new GraphBuilder(converter, new GraphOptions(RankDir.TB));
            if (!size.isEmpty()) gb.getGraphProperties().setSize(size);
            if (!ratio.isEmpty()) gb.getGraphProperties().setRatio(ratio);
			gb.execute();
		} catch (Exception e) {
			String msg = "Error creating graph.";
			logger.error(msg, e);
			throw new JobNetException(msg, e);
		}

		try {
			GraphIO io = new GraphIO(gb);
			File tempFile = File.createTempFile("graphviz_", ".tmp");
			targetFile = new File(targetDir, getOutputFilename(converter.getBootstrapNodeName()));
			logger.debug(gb.getContent());
			logger.info("output to file " + targetFile.getAbsolutePath());
			io.writeGraphToFile(graphType, tempFile);
			if (!targetFile.exists() || targetFile.delete())
				tempFile.renameTo(targetFile);
		} catch (IOException e) {
			String msg = "Error creating jobnet graph.";
			logger.error(msg, e);
			throw new JobNetException(msg, e);
		}

	}

	private String getOutputFilename(String baseName) {
		return (buildStaticFile) ? baseName + extension : uuid + extension;
	}

	/**
	 * This flag determines if the file created contains the uuid in its name.
	 * 
	 * @param staticFile
	 */
	public void setBuildStatic(boolean staticFile) {
		this.buildStaticFile = staticFile;
	}

	public boolean isJobnetPresentInDB() {
		return isJobnetPresentInDb;
	}

	public void createHtmlFile() {
		File targetFile = new File(targetDir, uuid + HTML_EXTENSION);
		try {
			InputStream in = this.getClass().getResourceAsStream(htmlSingleTemplate);
			String content = CharStreams.toString(new InputStreamReader(in, Charset.defaultCharset()));
			content = content.replaceAll("\\$\\{uuid_jobnet_identifier\\}", uuid);
			content = content.replaceAll("\\$\\{checkInterval\\}", checkIntervalForHtml);
			Files.write(content, targetFile, Charset.defaultCharset());
		} catch (IOException e) {
			String msg = "Error creating file " + targetFile.getAbsolutePath();
			logger.error(msg, e);
			throw new JobSchedulerException(msg, e);
		}
	}

	public void addToIndexFile(String fileName) {
		removeFromIndexFile(fileName); // to avoid duplicate entries
		int i = fileName.lastIndexOf(".");
		String baseName = (i > 0) ? fileName.substring(0, i) : fileName;
		File targetFile = new File(targetDir, htmlIndex);
		List<String> lines = readIndexContent(targetFile);
		StringBuffer b = new StringBuffer();
		for (String line : lines) {
			if (!line.contains("__cnt")) {
				b.append(line + "\n");
				if (line.contains("${uuid_jobnet_identifier}")) {
					b.append("<p><a id=\"__jobnet\" href=\"" + fileName + "\">" + baseName + "</a></p>\n");
				}
			}
		}
		writeIndexFile(b, targetFile);
	}

	public void removeFromIndexFile(String fileName) {
		File targetFile = new File(targetDir, htmlIndex);
		List<String> lines = readIndexContent(targetFile);
		StringBuffer b = new StringBuffer();
		for (String line : lines) {
			if (!line.contains(fileName)) {
				b.append(line + "\n");
			}
		}
		writeIndexFile(b, targetFile);
	}

	private List<String> readIndexContent(File targetFile) {
		List<String> lines = null;
		try {
			if (targetFile.exists()) {
				lines = Files.readLines(targetFile, Charset.defaultCharset());
			} else {
				InputStream in = this.getClass().getResourceAsStream(htmlIndexTemplate);
				lines = CharStreams.readLines(new InputStreamReader(in, Charset.defaultCharset()));
			}
		} catch (IOException e) {
			String msg = "Error creating file " + targetFile.getAbsolutePath();
			logger.error(msg, e);
			throw new JobSchedulerException(msg, e);
		}
		return lines;
	}

	private void writeIndexFile(StringBuffer content, File targetFile) {
		try {
			Files.write(content, targetFile, Charset.defaultCharset());
		} catch (IOException e) {
			String msg = "Error writing file " + targetFile.getAbsolutePath();
			logger.error(msg, e);
			throw new JobSchedulerException(msg, e);
		}
	}

	public String getExtension() {
		return extension;
	}

	public File getTargetFile() {
		return targetFile;
	}

}
