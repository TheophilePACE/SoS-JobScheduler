/**
 * Copyright (C) 2015 Jean-Vincent http://bigloupe.github.io/SoS-JobScheduler/
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

/*
 * Nom de classe : ExcelReader
 *
 * Description   : Module permettant d'ordonnancer des taches sous JobScheduler à partir d'un fichier Excel 
 *
 * Version       : 1.0
 *
 * Date          : 20/05/2015
 * 
 * Copyright     :  Licensed under the Apache License
 */

package org.jobscheduler.dashboard.ocab;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jobscheduler.dashboard.jobdefinition.xml.Commands;
import org.jobscheduler.dashboard.jobdefinition.xml.Holiday;
import org.jobscheduler.dashboard.jobdefinition.xml.Holidays;
import org.jobscheduler.dashboard.jobdefinition.xml.Include;
import org.jobscheduler.dashboard.jobdefinition.xml.Job;
import org.jobscheduler.dashboard.jobdefinition.xml.Job.DelayOrderAfterSetback;
import org.jobscheduler.dashboard.jobdefinition.xml.Job.Description;
import org.jobscheduler.dashboard.jobdefinition.xml.Job.LockUse;
import org.jobscheduler.dashboard.jobdefinition.xml.JobChain;
import org.jobscheduler.dashboard.jobdefinition.xml.JobChain.JobChainNode;
import org.jobscheduler.dashboard.jobdefinition.xml.Lock;
import org.jobscheduler.dashboard.jobdefinition.xml.Monthdays;
import org.jobscheduler.dashboard.jobdefinition.xml.ObjectFactory;
import org.jobscheduler.dashboard.jobdefinition.xml.Order;
import org.jobscheduler.dashboard.jobdefinition.xml.Param;
import org.jobscheduler.dashboard.jobdefinition.xml.Params;
import org.jobscheduler.dashboard.jobdefinition.xml.Period;
import org.jobscheduler.dashboard.jobdefinition.xml.RunTime;
import org.jobscheduler.dashboard.jobdefinition.xml.RunTime.Date;
import org.jobscheduler.dashboard.jobdefinition.xml.Script;
import org.jobscheduler.dashboard.jobdefinition.xml.StartJob;
import org.jobscheduler.dashboard.jobdefinition.xml.Weekdays;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

public class ExcelReader {
	/**
	 * System objects
	 */
	File file;
	FileInputStream fis;
	JAXBContext jc;
	Marshaller marshaller;
	ObjectFactory fabrique = new ObjectFactory();
	XSSFWorkbook wb;
	XSSFSheet sheet;
	Cell cell;
	String outPut;
	String saveAt;
	int metrique;
	boolean fichier = false;
	String timeJob = "";
	String untilJob = "";
	StringBuilder log=new StringBuilder("");
	ConvertisseurTwsJbs interfaceGraphique;
	boolean modeTest;
	boolean userMode;
	int numeroOrder = 1;
	int numLigne = 1;
	String lockInUse = "";
	String jobchainRunOtherJobChain;
	String orderfileName;
	String jobFileName;
	boolean orderAuthorization;
	boolean boucleExterne;
	String[] lineSpleat;
	boolean alertUtilisateur;
	String userJob="";

	/**
	 * For the conversion , we must know the next steps for each job, but
	 * ExcelReader work sequentially and don't stock anything about the next
	 * JobHelper contain some operation for know who is the next, or if jobchain
	 * is complex.
	 * 
	 */
	JobHelper jobhelp;

	/**
	 * this variable allows insert a job split and a job for the synchronization
	 * in a jobchain, we need those variables because there is no correspondence
	 * in the ocab file (no split no synchro), we add the job manually.
	 * jbcnSplitBool jbcnSyncBool, just for say if we need add a split/Sync job
	 * or not. alreadySync, many job can converge on a Synchronization, but
	 * there is only one job of Synchronization, when he already on the chain,
	 * alreadySync is true
	 */
	boolean jbcnSplitBool = false;
	boolean jbcnSyncBool = false;
	boolean alreadySync = false;

	/**
	 * for the name of order based on jobchain and know how many order have a jobchain
	 */

	String jobchainEnCour;
	int nbrOrder;

	/**
	 * Existence or not of a runtime / params ( job scheduler functionality )
	 */
	boolean runtime = false;
	boolean params = false;
	boolean haveRunTimeFiles;

	/**
	 * Lists
	 */

	ArrayList<String> ligneTitre = new ArrayList<String>(); // title line of
															// Excel
	LinkedHashMap<String, JobChain> jobchain = new LinkedHashMap<String, JobChain>();
	// amount "order" for each jobchain
	Hashtable<String, Integer> nbOrderParJobchain = new Hashtable<String, Integer>();
	LinkedHashMap<String, Job> lJob = new LinkedHashMap<String, Job>();
	LinkedHashMap<String, Order> lOrder = new LinkedHashMap<String, Order>();
	Iterator<Row> rowIterator;
	Iterator<Cell> cellFirstLigne;
	Iterator<Cell> cellIterator;
	ArrayList<String> contenuFichier = new ArrayList<String>();

	/**
	 * Objects generated from the schema
	 */

	JobChain jbc = null;
	JobChain.JobChainNode jbcn;
	Job jb;
	Script scrpt;
	Order od;
	Params oParams;
	RunTime oRuntime;
	JobChainNode jbcnSplit;
	JobChainNode jbcnSync;
	RunTime runTimeFiles;

	/**
	 * variable Initialization
	 */

	public void InitVariable() {
		metrique = 0;
		orderfileName = "";
		alertUtilisateur = true;
		jobFileName = "";
		orderAuthorization = true;
		boucleExterne = false;
		contenuFichier = new ArrayList<String>();
		jobchainEnCour = null;
		nbrOrder = 0;
		saveAt = "00:00";// Default Time
		haveRunTimeFiles = false;
	}

	/**
	 * Constructor using Excel and Xml path
	 */

	public ExcelReader(String EmplacementFichierExcel, String output,
			ConvertisseurTwsJbs ctj, boolean modTest,boolean usermode) throws JAXBException,
			IOException {
		super();
		modeTest = modTest;
		userMode=usermode;
		interfaceGraphique = ctj;
		this.outPut = output;
		InitVariable();

		file = new File(EmplacementFichierExcel);
		File dir = new File(outPut + "/" + file.getName().split("\\.")[0]);
		dir.mkdirs();
		outPut = dir.getAbsolutePath() + "/";

		log.append("**********************************************************\n");
		log.append("Traitement du fichier Excel : " + file.getName()
				+ " destination: " + outPut + "\n");

		jc = JAXBContext
				.newInstance("org.jobscheduler.dashboard.jobdefinition.xml");
		marshaller = jc.createMarshaller();
		marshaller.setProperty("jaxb.encoding", "ISO-8859-1");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		// For add CDATA, jaxb don't allow the use of "<!" (special character in
		// xml)
		marshaller.setProperty("com.sun.xml.bind.characterEscapeHandler",
				new CharacterEscapeHandler() {
					@Override
					public void escape(char[] ch, int start, int length,
							boolean isAttVal, Writer writer) throws IOException {
						writer.write(ch, start, length);
					}
				});

		fabrique = new ObjectFactory();
		fis = new FileInputStream(file);
		wb = new XSSFWorkbook(fis);
		sheet = wb.getSheetAt(3);

		// first clean of excel file, create blank for null case
		for (Row row : sheet) {

			for (int cn = 0; cn < row.getLastCellNum(); cn++) {

				row.getCell(cn, Row.CREATE_NULL_AS_BLANK);

			}
		}

		interfaceGraphique.addValueProgressBar(5);

		ExcelCleaner excl = new ExcelCleaner();
		excl.ExcelOcabCleaner(wb, sheet, log, outPut, ctj);
		FileOutputStream fileOut = new FileOutputStream(outPut + file.getName());
		wb.write(fileOut);

		jobhelp = new JobHelper(sheet, marshaller);

		log.append( "Exécution du JobHelper \n");

		// jobhelp help the main work, he resolve the dependence, exp (next 1002
		// is 1003)
		// detect the split case (parallel), etc.

		jobhelp.initialization(outPut);

		interfaceGraphique.addValueProgressBar(20);// update graphic

		rowIterator = sheet.iterator();// Iterate through each rows one by one

		cellFirstLigne = sheet.getRow(0).cellIterator();// Get the first line of

		// application Lock (for stop a application put "setmaxNonExclusive=0")
		Lock lc = fabrique.createLock();
		lc.setMaxNonExclusive(new BigInteger("50"));
		OutputStream os = new FileOutputStream(outPut
				+ file.getName().split("\\.")[0] + ".lock.xml");
		marshaller.marshal(fabrique.createLock(lc), os);
	}

	public String cdata(String st) {
		return "<![CDATA[" + st + "]]>"; // for add CDATA in Xml file
	}

	/**
	 * name - copieLigneTitre, copy in a arraylist the name of each column to
	 * know on which work
	 * 
	 * @author Jean-vincent
	 * @date 20/05/2015
	 * @note
	 */

	public void copyLineTitle() {
		while (cellFirstLigne.hasNext()) {
			ligneTitre.add(cellFirstLigne.next().toString());
		}
	}

	/**
	 * name - ligneExcelSuivant, Scroll to the next excel line
	 * 
	 * @return boolean
	 * @author Jean-vincent
	 * @date 20/05/2015
	 * @note
	 */

	public boolean nextExcelLine() {
		if (rowIterator.hasNext()) {
			rowIterator.next();
			return true;
		}
		return false;
	}

	/**
	 * name - coloneExcelSuivant, return the next excel column
	 * 
	 * 
	 * @return Cell
	 * @author Jean-vincent
	 * @date 20/05/2015
	 * @note
	 */

	public Cell coloneExcelSuivant() {
		if (cellIterator.hasNext()) {
			return cellIterator.next();

		}
		return null;
	}

	/**
	 * variable JobChain Initialization
	 */
	public void InitVariableOfJobChain() {// initialization
		lockInUse = "";
		boucleExterne = true;// for the repetition in another chain
		lineSpleat = null; // line for add parallel job
		timeJob = "";
		numeroOrder = 1;
		haveRunTimeFiles = false;
	}

	/**
	 * name - treatJobChainLine, initialization of a new jobchain
	 * 
	 * @author Jean-vincent
	 * @date 20/05/2015
	 * @note
	 * 
	 */

	public void treatJobChainLine(boolean MAJ) {

		int i = 2;// cell and "i"(column title) must be synchronized, title must
		// correspond to cell

		InitVariableOfJobChain();
		// if the past chain don't synchronize his split (parallel job) we add
		// it here
		// The last job was a split with no end, the only way for add it it's in
		// the next jobchain because,
		// next of the last job is always a jobchain or nothing (end of the
		// file)
		if (jbcnSyncBool) {

			jbcnSyncBool = false;
			jbc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(jbcnSync);
		}
		// if the jobchain wait a file for execute, he can't have order
		orderAuthorization = Orderauthorization(numLigne + 1);

		// new jobchain
		jbc = fabrique.createJobChain();
		jbc.setVisible("yes");
		jbc.setOrdersRecoverable("yes");
		runTimeFiles = fabrique.createRunTime();

		do {

			cell = coloneExcelSuivant();
			// first time, we pass to the case 3nd
			// case
			// it's why i=2 (0,1,2)=>3

			if (!cell.toString().isEmpty()) {
				treatJobChainOption(i); // treat a jobchain hut
			}

			i++;

		} while (cellIterator.hasNext());

		// add the new jobchain in the list of jobchain (list of jobchain
		// application)
		jobchain.put(jbc.getName(), jbc);

		if (jobchainEnCour != jbc.getName() && jobchainEnCour != null) {
			// now if we treat a new jobchain
			// reset correspondence between jobchain
			// and order because jobchain can have many order

			nbOrderParJobchain.put(jobchainEnCour, nbrOrder);
			nbrOrder = 0;

			// here we finish the past jobchain if he easy we add step "success"
			// and "error"
			// else one more, "END"
			addBeginAndEndJobChain(jobchainEnCour);
		}

		// now the present jobchain is the new chain
		jobchainEnCour = jbc.getName();

		// in the new version this step is not use
		// if MAJ=true it's a jobchain i have add with the prog
		if (MAJ)
			jobchainRunOtherJobChain = jobchainEnCour;

	}

	/**
	 * name - treatJobChainOption functional correspondence between the excel
	 * file and jobscheduler (jobchain) look the Ocab file
	 * 
	 * @param int : correspondence with the title line of the excel file
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */

	public void treatJobChainOption(int i) {

		switch (ligneTitre.get(i).toString()) {

		// option in the excel ocab
		case "jobstream":
			jbc.setName(cell.toString());

			if (!jobhelp.splitAtBegening(cell.toString()).equals("No")) {
				jbcnSplit = fabrique.createJobChainJobChainNode();
				String temp = jobhelp.splitAtBegening(cell.toString()).split(
						";")[0];// just a temporary variable
				lineSpleat = jobhelp.splitAtBegening(cell.toString())
						.split(";");

				jbcnSplit.setState(temp);
				jbcnSplit.setNextState(jobhelp.getNextJob(temp));
				jbcnSplit.setJob("/sos/jitl/JobChainSplitter");
				jbc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(
						jbcnSplit);
			}

			break;

		case "priority":

			break;

		case "at":
			timeJob = cell.toString().substring(0, 2) + ":"
					+ cell.toString().substring(2, 4);

			break;

		case "until":
			untilJob = cell.toString().substring(0, 2) + ":"
					+ cell.toString().substring(2, 4);
			int tmp = Integer.parseInt(untilJob.split(":")[0]
					+ untilJob.split(":")[1]);

			if (!timeJob.isEmpty()) {

				int tmp2 = Integer.parseInt(timeJob.split(":")[0]
						+ timeJob.split(":")[1]);
				if (tmp <= tmp2 || untilJob.equals("00:15")
						|| untilJob.equals("00:05")) {
					untilJob = "";
				}
			}

			break;

		default:
			break;
		}

	}

	/**
	 * name - treatJobOption functional correspondence between the excel file
	 * and jobscheduler (job)
	 * 
	 * see treatJobLine()
	 * 
	 * @param int : correspondence with the title line of the excel file
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */

	public void treatJobOption(int i) {

		switch (ligneTitre.get(i).toString()) {

		case "job":
			jobFileName = cell.toString();
			jbcn.setJob(cell.toString());
			jbcn.setState(cell.toString());

			// if next line is a split, then we have to create a new
			// jobchainnode (split) in actual jobchain
			// see split at begening of jobchain in treatJobchainoption

			if (jobhelp.getNextJob(cell.toString()).indexOf("Split_") != -1) {
				jbcnSplit = fabrique.createJobChainJobChainNode();
				String temp = jobhelp.getNextJob(cell.toString());// just a
																	// temporary
																	// variable
				jbcnSplit.setState(temp);
				jbcnSplit.setNextState(jobhelp.getNextJob(temp));
				jbcnSplit.setJob("/sos/jitl/JobChainSplitter");
				jbcnSplitBool = true;
			}

			// if next line is a split, then we have to create a new synchro in
			// actual jobchain
			// but every job in a complex case have synch for next or we have
			// only one synch
			// then we notice we need create a job synch but we add it at the
			// end of the complex case,
			// when next of the current job is different of Synch and
			// alreySync(=we have a job synch to add) is true

			if (jobhelp.AddSynch(sheet.getRow(numLigne).getCell(2).toString())) {

				jbcnSync = fabrique.createJobChainJobChainNode();
				String temp = jobhelp.getNextJob(cell.toString());

				jbcnSync.setState(temp);
				jbcnSync.setNextState(jobhelp.getNextJob(temp));
				jbcnSync.setJob(temp);
				jbcnSyncBool = true;

			}

			break;
		case "user":
			// If there is more than one users, notify in log
			if(userJob.isEmpty())
				userJob=cell.toString();
			
			if (!cell.toString().equals(userJob)
					&& alertUtilisateur) {

				log.append( "ATTENTION les jobs dans le fichier ont plusieurs utilisateurs! 1-"+userJob+" 2-"+cell.toString()+"\n");
				alertUtilisateur = false;

			}

			if(!userJob.equals(cell.toString()))
				userJob=cell.toString();
			break;

		case "description":

			if (cell.toString().indexOf("by composer") == -1)
				jb.setTitle(cell.toString());

			break;

		case "scriptname":

			if (modeTest) {//test mode with sleep 12 and the script in comment 
				if(userMode) //userMode use the Excel ocab user, otherwise, it's the default user (jobscheduler user) 
				{
					scrpt.getContent().add(
							cdata("sleep 12 " + "#sudo -iHu "+userJob+" "+ cell.toString()));

					jb.setScript(scrpt);
				}
				else
				{	
				scrpt.getContent().add(
						cdata("sleep 12 " + "#" + cell.toString()));

				jb.setScript(scrpt);
				}
			} else {
				
				if(userMode) //userMode use the Excel ocab user, otherwise, it's the default user (jobscheduler user) 
				{
					scrpt.getContent().add(
							cdata("sudo -iHu "+userJob+" "+ cell.toString()));

					jb.setScript(scrpt);
				}
				else
				{	
				scrpt.getContent().add(cdata(cell.toString()));
				jb.setScript(scrpt);
				}
			}

			break;

		case "recovery_option":

			if (cell.toString().equals("stop") || cell.toString().isEmpty()) {
				jbcn.setErrorState("!end_ERR");

			} else if (cell.toString().equals("RERUN")) {
				jbcn.setOnError("setback");
				DelayOrderAfterSetback dl = fabrique
						.createJobDelayOrderAfterSetback();
				dl.setIsMaximum("yes");
				dl.setDelay("0");
				dl.setSetbackCount(new BigInteger("1"));
				jb.getDelayOrderAfterSetback().add(dl);
				jbcn.setErrorState("!end_ERR");
			} else {

				if (jbcn.getNextState().toString().isEmpty()) {

					jbcn.setErrorState("!end_ERR");

				} else {
					jbcn.setErrorState(jbcn.getNextState());
				}
			}
			break;

		case "follows":

			// if a job wait a file
			if (!jobhelp.getJobWithFiles(jobFileName).equals("nofiles")) {
				// if the job wait a file(s) to be execute, we create job with
				// the same
				// name with "wait_file" at end. this job wait the file(s), then
				// execute the job
				// See :
				// http://www.sos-berlin.com/doc/JITL/JobSchedulerExistsFile.xml

				String[] Line = jobhelp.getJobWithFiles(jobFileName).split(";");

				JobChainNode tempBoucle = fabrique.createJobChainJobChainNode();

				Job jobSchedulerExistsFile = fabrique.createJob();
				Description dsc = fabrique.createJobDescription();

				Include inc = fabrique.createInclude();
				inc.setFile("jobs/JobSchedulerExistsFile.xml");
				dsc.getContent().add(inc);

				Script scr = fabrique.createScript();
				scr.setJavaClass("sos.scheduler.file.JobSchedulerExistsFile");
				scr.setLanguage("java");

				Params prms = fabrique.createParams();

				Param file1 = fabrique.createParam();
				file1.setName("file");

				Param regex = fabrique.createParam();
				regex.setName("file_spec");

				Param skip_first_files = fabrique.createParam();
				skip_first_files.setName("skip_first_files");

				String directory = "";
				// par défaut
				initialiseParamsJobBoucle(prms, jobSchedulerExistsFile);
				// End défaut

				String[] splitdirectory = null;

				if (Line.length > 1) {
					for (int a = 0; a < Line.length; a++) {
						splitdirectory = sheet
								.getRow(Integer.parseInt(Line[a])).getCell(30)
								.toString().split("/");

						if (a == 0) {
							for (int j = 0; j < splitdirectory.length - 1; j++) {
								if (j < splitdirectory.length - 2) {
									directory += splitdirectory[j] + "/";
								} else {
									directory += splitdirectory[j];
								}
							}
							file1.setValue(directory);
							regex.setValue(splitdirectory[splitdirectory.length - 1]);

						} else {
							regex.setValue(regex.getValue() + " || "
									+ splitdirectory[splitdirectory.length - 1]);
						}
					}
					skip_first_files.setValue(String.valueOf(Line.length));
				} else {
					splitdirectory = sheet.getRow(Integer.parseInt(Line[0]))
							.getCell(30).toString().split("/");
					skip_first_files.setValue("0");
					for (int j = 0; j < splitdirectory.length - 1; j++) {
						if (j < splitdirectory.length - 2) {
							directory += splitdirectory[j] + "/";
						} else {
							directory += splitdirectory[j];
						}
					}

					file1.setValue(directory);
					regex.setValue(splitdirectory[splitdirectory.length - 1]);

				}

				tempBoucle.setState(jobFileName + "_WaitFiles");
				tempBoucle.setJob(jobFileName + "_WaitFiles");
				tempBoucle.setNextState(jbcn.getState());
				jbc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(
						tempBoucle);
				prms.getParamOrCopyParamsOrInclude().add(file1);
				prms.getParamOrCopyParamsOrInclude().add(regex);
				prms.getParamOrCopyParamsOrInclude().add(skip_first_files);
				jobSchedulerExistsFile.setParams(prms);
				outJob(jobSchedulerExistsFile, jobFileName + "_WaitFiles");

			}

			if (!jobhelp.getNextJob(jobFileName).equals("NogetL1")) {
				// if the next job have a file the current job have for next a
				// WaitFiles
				// see the top of "follow"---> in the switch--> line 608 if he
				// don't change
				if (!jobhelp.getJobWithFiles(jobhelp.getNextJob(jobFileName))
						.equals("nofiles")) {

					jbcn.setNextState(jobhelp.getNextJob(jobFileName)
							+ "_WaitFiles");

				} else {
					jbcn.setNextState(jobhelp.getNextJob(jobFileName));
				}
			} else {
				// add a dependence on a other jobchain
				if (!jobhelp.jobChainNext(jobchainEnCour).equals(
						"noJobchainNext") && !jobhelp.jobChainNext(jobchainEnCour).equals(
								jobchainEnCour)) {
					
					Commands cmd = fabrique.createCommands();
					cmd.getOnExitCode().add("success");
					Order tmp = fabrique.createOrder();
					tmp.setJobChain("/" + file.getName().split("\\.")[0] + "/"
							+ jobhelp.jobChainNext(jobchainEnCour));
					cmd.getAddJobsOrAddOrderOrCheckFolders().add(
							fabrique.createOrder(tmp));
					jb.getCommands().add(cmd);

				}

				if (jobhelp.isJobChainComplex(jobchainEnCour)) // if it's a
																// complex case
																// next of the
																// last jobchain
																// node go to
																// jobchainnode
																// end
				{
					jbcn.setNextState("End");
				} else if (fichier) // if there is a file we delete it
				{
					jbcn.setNextState("S_cleanfile");
				} else {
					jbcn.setNextState("end_SUC_All");

				}
			}

			break;

		case "jid":

			jbcn.setState(cell.toString());

			break;

		case "at":

			// see Ocab
			if (sheet.getRow(numLigne - 1).getCell(3).toString().isEmpty()) {
				RunTime rt = new RunTime();
				rt.setBegin(cell.toString().substring(0, 2) + ":"
						+ cell.toString().substring(2, 4));
				if (jb.getRunTime() == null)
					jb.setRunTime(rt);
				else
					jb.getRunTime().setBegin(
							cell.toString().substring(0, 2) + ":"
									+ cell.toString().substring(2, 4));
			}

			break;

		case "every":

			// boucle interne = repeat in a Order
			// boucle externe= reapeat in a other jobchain

			if (boucleExterne) {

				Commands cmds = fabrique.createCommands();
				Order tmp = fabrique.createOrder();
				RunTime rt = new RunTime();
				JobChain tmpJobchain = fabrique.createJobChain();
				tmpJobchain.setName(jobchainEnCour + "_" + jobFileName
						+ "repeat");
				tmpJobchain.setVisible("yes");

				JobChain.JobChainNode temp = fabrique
						.createJobChainJobChainNode();
				temp.setState(jbcn.getState() + "_R");
				temp.setJob(jbcn.getJob() + "_R");
				temp.setNextState("end_SUC_All");
				temp.setErrorState("!end_ERR");
				tmpJobchain.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd()
						.add(temp);

				temp = fabrique.createJobChainJobChainNode();
				temp.setState("end_SUC_All");
				tmpJobchain.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd()
						.add(temp);

				temp = fabrique.createJobChainJobChainNode();
				temp.setState("!end_ERR");
				tmpJobchain.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd()
						.add(temp);

				rt.setRepeat(cell.toString().substring(0, 2) + ":"
						+ cell.toString().substring(2, 4));

				if (!sheet.getRow(numLigne).getCell(16).toString().isEmpty()
						&& !sheet.getRow(numLigne).getCell(19).toString()
								.isEmpty()) {

					rt.setBegin(sheet.getRow(numLigne).getCell(16).toString()
							.substring(0, 2)
							+ ":"
							+ sheet.getRow(numLigne).getCell(16).toString()
									.substring(2, 4));

					rt.setEnd(sheet.getRow(numLigne).getCell(19).toString()
							.substring(0, 2)
							+ ":"
							+ sheet.getRow(numLigne).getCell(19).toString()
									.substring(2, 4));

				} else {

					int n = numLigne - 1;
					while (n != 1) {
						if (!sheet.getRow(n).getCell(16).toString().isEmpty()
								&& !sheet.getRow(n).getCell(19).toString()
										.isEmpty()
								&& sheet.getRow(n).getCell(3).toString()
										.equals("N")) {
							rt.setBegin(sheet.getRow(n).getCell(16).toString()
									.substring(0, 2)
									+ ":"
									+ sheet.getRow(n).getCell(16).toString()
											.substring(2, 4));

							rt.setEnd(sheet.getRow(n).getCell(19).toString()
									.substring(0, 2)
									+ ":"
									+ sheet.getRow(n).getCell(19).toString()
											.substring(2, 4));
							n = 2;
						}

						if (!sheet.getRow(n).getCell(16).toString().isEmpty()
								&& !sheet.getRow(n).getCell(19).toString()
										.isEmpty()
								&& !sheet.getRow(n).getCell(1).toString()
										.isEmpty()) {
							rt.setBegin(sheet.getRow(n).getCell(16).toString()
									.substring(0, 2)
									+ ":"
									+ sheet.getRow(n).getCell(16).toString()
											.substring(2, 4));

							rt.setEnd(sheet.getRow(n).getCell(19).toString()
									.substring(0, 2)
									+ ":"
									+ sheet.getRow(n).getCell(19).toString()
											.substring(2, 4));
							n = 2;

						}

						if (!sheet.getRow(n).getCell(1).toString().isEmpty()
								&& (sheet.getRow(n).getCell(16).toString()
										.isEmpty() || sheet.getRow(n)
										.getCell(19).toString().isEmpty())) {
							n = 2;
							rt.setBegin("00:00");
							rt.setEnd("24:00");
							log.append("Attention! un job répétitif sans plage de répétition a été détecté! plage par defaut : 24h \n");

						}

						n--;
					}
				}

				tmp.setRunTime(rt);
				tmp.setJobChain(file.getName().split("\\.")[0] + "/"
						+ jobchainEnCour + "_" + jobFileName + "repeat");

				OutputStream os;

				try {
					os = new FileOutputStream(outPut + jobFileName + "_R"
							+ ".job.xml");
					marshaller.marshal(jb, os);
				} catch (FileNotFoundException | JAXBException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (jb.getCommands().isEmpty()) {
					cmds.getOnExitCode().add("success");

					cmds.getAddJobsOrAddOrderOrCheckFolders().add(
							fabrique.createOrder(tmp));
					jb.getCommands().add(cmds);
				} else {
					cmds = jb.getCommands().get(0);
					cmds.getAddJobsOrAddOrderOrCheckFolders().add(
							fabrique.createOrder(tmp));
				}

				try {
					os = new FileOutputStream(outPut + tmpJobchain.getName()
							+ ".job_chain.xml");
					marshaller.marshal(tmpJobchain, os);
				} catch (FileNotFoundException | JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			break;
		default:
			break;
		}

	}

	public void outJob(Job jb, String name) {
		OutputStream os;
		try {
			os = new FileOutputStream(outPut + name + ".job.xml");
			marshaller.marshal(jb, os);
		} catch (FileNotFoundException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * name - treatJobLine() treat a job line
	 * 
	 * @see treatJobOption
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */

	public void treatJobLine() {
		jbcn = fabrique.createJobChainJobChainNode();
		jb = fabrique.createJob();
		jb.setOrder("yes");
		jb.setStopOnError("no");
		scrpt = fabrique.createScript();
		scrpt.setLanguage("shell");
		jobFileName = "";
		if (!lockInUse.isEmpty()) {
			LockUse temp = fabrique.createJobLockUse();
			temp.setLock(lockInUse);
			temp.setExclusive("no");
			jb.getLockUse().add(temp);
		}

		LockUse lck = fabrique.createJobLockUse();
		lck.setLock(file.getName().split("\\.")[0]);
		lck.setExclusive("no");
		jb.getLockUse().add(lck);

		int i = 2;// information about job begin in the 2nd column

		// if the jobchain wait a file and have a time for execute
		// the only way for convert in jobscheduler is the runtime in a job
		if (haveRunTimeFiles && fichier) {

			if (lineSpleat != null) {
				for (int u = 1; u < lineSpleat.length; u++) {
					if (numLigne == Integer.parseInt(lineSpleat[u]))
						jb.setRunTime(runTimeFiles);
				}
                //if true, it's the last parallel job at the jobchain beginning 
				if (Integer.parseInt(lineSpleat[lineSpleat.length - 1]) < numLigne) {
					haveRunTimeFiles = false;
					runTimeFiles = fabrique.createRunTime();
				}
			} else {
				jb.setRunTime(runTimeFiles);
				haveRunTimeFiles = false;
				runTimeFiles = fabrique.createRunTime();
			}

		}

		do {

			cell = coloneExcelSuivant();
			i++;

			if (!cell.toString().isEmpty() || i == 11 || i == 41) {

				treatJobOption(i); // treat a job hut

			}

		} while (cellIterator.hasNext());

		jbc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(jbcn);// add
																		// the
																		// jobchainnode
																		// in
																		// jobchain

		if (jbcnSyncBool)// if a split or a synchro file exist then add in the
							// jobchain
		{
			jbc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(jbcnSync);
			jbcnSyncBool = false;// for reset

		}

		if (jbcnSplitBool)// if a split or a synchro file exist then add in the
							// jobchain
		{
			jbc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd()
					.add(jbcnSplit);
			jbcnSplitBool = false;// for reset

		}
		// add the job in the list of job
		lJob.put(jobFileName, jb);

	}

	/**
	 * name - countDay correspondence between excel and job scheduler date
	 * 
	 * 
	 * @param String
	 *            [] : Days to convert
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */

	public String countDay(String[] days) {
		String day = "";

		for (int k = 0; k < days.length; k++) {
			switch (days[k]) {

			case "MO":

				day = day + "1 ";

				break;
			case "TU":

				day = day + "2 ";

				break;
			case "WE":

				day = day + "3 ";

				break;

			case "TH":

				day = day + "4 ";

				break;
			case "FR":

				day = day + "5 ";

				break;
			case "SA":

				day = day + "6 ";

				break;

			case "SU":

				day = day + "7";

				break;

			default:
				break;

			}
		}

		return day;

	}

	/**
	 * name - countDay correspondence between excel and job scheduler date
	 * 
	 * 
	 * @param String
	 *            : Day to convert
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */

	public String countDay(String day) {

		switch (day) {

		case "MO":

			return "1";

		case "TU":

			return "2";
		case "WE":

			return "3";

		case "TH":

			return "4";

		case "FR":

			return "5";

		case "SA":

			return "6";

		case "SU":

			return "7";

		default:
			break;

		}

		return null;

	}

	/**
	 * name - treatOrderOption functional correspondence between the excel file
	 * and jobscheduler (order)
	 * 
	 * see treatOrderLine
	 * 
	 * @param int : correspondence with the title line of the excel file
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */

	public void treatOrderOption(int i) {
		if (i > 3)
			cell = coloneExcelSuivant();

		if (!cell.toString().equals("")) {

			switch (ligneTitre.get(i).toString()) {

			case "runcycle":

				// Formatting name of order

				String zero = "";
				if (numeroOrder < 10) {
					zero = "00" + numeroOrder;
				} else if (numeroOrder < 100) {
					zero = "0" + numeroOrder;
				} else {
					zero = String.valueOf(numeroOrder);
				}

				orderfileName = "O" + zero + "_" + jobchainEnCour;

				numeroOrder++;
				break;

			case "description":
				od.setTitle(cell.toString());

				break;
			case "until":

				String heureEnd = cell.toString().substring(0, 2) + ":"
						+ cell.toString().substring(2, 4);

				int tmp = Integer.parseInt(cell.toString());

				int tmp2 = Integer.parseInt(heureEnd.split(":")[0]
						+ heureEnd.split(":")[1]);

				if (tmp <= tmp2 || heureEnd.equals("00:15")
						|| heureEnd.equals("00:05")) {
					heureEnd = "";
				}

				if (!heureEnd.toString().equals("23:59") && !heureEnd.isEmpty()) {
					oRuntime.setEnd(heureEnd);
					runtime = true;
					runTimeFiles.setEnd(heureEnd);
					haveRunTimeFiles = true;
				}
				break;

			case "timezone":

				oRuntime.setTimeZone(cell.toString());
				runtime = true;
				runTimeFiles.setTimeZone(cell.toString());
				haveRunTimeFiles = true;
				break;
			case "at":

				String heure = cell.toString().substring(0, 2) + ":"
						+ cell.toString().substring(2, 4);
				saveAt = heure;

				break;

			case "dates":

				Date dt = fabrique.createRunTimeDate();
				dt.setDate(cell.toString());
				Period prd = fabrique.createPeriod();
				prd.setSingleStart(saveAt);
				dt.getPeriod().add(prd);
				oRuntime.getDate().add(dt);

				runTimeFiles.getDate().add(dt);
				haveRunTimeFiles = true;

				runtime = true;
				break;

			case "ical":

				String[] listCommande = cell.toString().split(";");

				if (listCommande[0].indexOf("FREQ=DAILY") != -1) {

					Weekdays.Day tmpDay2 = fabrique.createWeekdaysDay();
					Period tmpPeriod2 = fabrique.createPeriod();

					Weekdays.Day tmpDayfile = fabrique.createWeekdaysDay();
					Period tmpPeriodfile = fabrique.createPeriod();

					tmpPeriod2.setSingleStart(saveAt);
					// if there is a file we add the period in the first(s) job
					tmpPeriodfile.setBegin(saveAt);

					String day = "1 2 3 4 5 6 7";
					for (int j = 1; j < listCommande.length; j++) {

						if (listCommande[j].equals("BYWORKDAY"))
							day = "1 2 3 4 5";
					}

					tmpDay2.getDay().add(day);
					tmpDayfile.getDay().add(day);
					detectBoucle(tmpPeriod2);

					tmpDay2.getPeriod().add(tmpPeriod2);
					tmpDayfile.getPeriod().add(tmpPeriodfile);

					if (oRuntime.getWeekdays() != null) {

						oRuntime.getWeekdays().getDay().add(tmpDay2);
						runTimeFiles.getWeekdays().getDay().add(tmpDayfile);

					} else {
						Weekdays tmpWeek2 = fabrique.createWeekdays();
						Weekdays tmpWeekFile = fabrique.createWeekdays();

						tmpWeek2.getDay().add(tmpDay2);
						tmpWeekFile.getDay().add(tmpDayfile);

						oRuntime.setWeekdays(tmpWeek2);

						if (runTimeFiles.getWeekdays() == null)
							runTimeFiles.setWeekdays(tmpWeekFile);
						else
							runTimeFiles.getWeekdays().getDay().add(tmpDayfile);

						runtime = true;

					}
					haveRunTimeFiles = true;
				} else if (listCommande[0].indexOf("FREQ=WEEKLY") != -1) {

					Weekdays.Day tmpDay2 = fabrique.createWeekdaysDay();
					Weekdays.Day tmpDayFile = fabrique.createWeekdaysDay();

					Period tmpPeriod2 = fabrique.createPeriod();
					Period tmpPeriodFile = fabrique.createPeriod();

					tmpPeriod2.setSingleStart(saveAt);
					tmpPeriodFile.setBegin(saveAt);

					detectBoucle(tmpPeriod2);// une boucle(repeat)  est ajouté si les
												// conditions sont ok

					tmpDay2.getPeriod().add(tmpPeriod2);
					tmpDayFile.getPeriod().add(tmpPeriodFile);

					if (oRuntime.getWeekdays() != null) {

						for (int t = 1; t < listCommande.length; t++) {
							if (listCommande[t].indexOf("BYDAY") != -1) {
								String substringDay = listCommande[t]
										.substring(6);

								String[] days = substringDay.split(",");

								tmpDay2.getDay().add(countDay(days));
								tmpDayFile.getDay().add(countDay(days));

								oRuntime.getWeekdays().getDay().add(tmpDay2);
								runTimeFiles.getWeekdays().getDay()
										.add(tmpDayFile);

							}

						}

					} else {
						for (int t = 1; t < listCommande.length; t++) {
							if (listCommande[t].indexOf("BYDAY") != -1) {
								Weekdays tmpWeek2 = fabrique.createWeekdays();
								Weekdays tmpWeekFile = fabrique
										.createWeekdays();

								String substringDay = listCommande[t]
										.substring(6);
								String[] days = substringDay.split(",");

								tmpDay2.getDay().add(countDay(days));
								tmpDayFile.getDay().add(countDay(days));

								tmpWeek2.getDay().add(tmpDay2);
								tmpWeekFile.getDay().add(tmpDayFile);

								oRuntime.setWeekdays(tmpWeek2);
								if (runTimeFiles.getWeekdays() == null)
									runTimeFiles.setWeekdays(tmpWeekFile);
								else
									runTimeFiles.getWeekdays().getDay()
											.add(tmpDayFile);

								runtime = true;
							}
						}

						haveRunTimeFiles = true;

					}

				} else if (listCommande[0].indexOf("FREQ=MONTHLY") != -1) {
					haveRunTimeFiles = true;
					runtime = true;
					String whichPrecedent = "";
					if (oRuntime.getMonthdays() == null) {
						Monthdays tmpMonthday = fabrique.createMonthdays();
						Monthdays tmpMonthdayFile = fabrique.createMonthdays();

						oRuntime.setMonthdays(tmpMonthday);
						runTimeFiles.setMonthdays(tmpMonthdayFile);

					}

					Monthdays.Weekday tmpWekkday = fabrique
							.createMonthdaysWeekday();
					Monthdays.Weekday tmpWekkdayFile = fabrique
							.createMonthdaysWeekday();

					Period tmpPeriod3 = fabrique.createPeriod();
					Period tmpPeriodFile = fabrique.createPeriod();

					tmpPeriod3.setSingleStart(saveAt);
					tmpPeriodFile.setBegin(saveAt);

					detectBoucle(tmpPeriod3);
					for (int t = 1; t < listCommande.length; t++) {
						if (listCommande[t].indexOf("BYDAY") != -1) {
							String substringDay = listCommande[t].substring(6);
							String[] days = substringDay.split(",");

							for (int f = 0; f < days.length; f++) {
								String which = days[f].substring(0, 1);
								String day = days[f].substring(1);

								if (f == 0) {
									tmpWekkday = fabrique
											.createMonthdaysWeekday();
									tmpWekkdayFile = fabrique
											.createMonthdaysWeekday();

									tmpWekkday.setWhich(which);
									tmpWekkdayFile.setWhich(which);

									whichPrecedent = which;
									tmpWekkday.getPeriod().add(tmpPeriod3);
									tmpWekkdayFile.getPeriod().add(
											tmpPeriodFile);
								} else if (!whichPrecedent.equals(which)) {
									oRuntime.getMonthdays().getDayOrWeekday()
											.add(tmpWekkday);
									runTimeFiles.getMonthdays()
											.getDayOrWeekday()
											.add(tmpWekkdayFile);

									tmpWekkday = fabrique
											.createMonthdaysWeekday();
									tmpWekkdayFile = fabrique
											.createMonthdaysWeekday();

									tmpWekkday.setWhich(which);
									tmpWekkdayFile.setWhich(which);

									tmpWekkday.getPeriod().add(tmpPeriod3);
									tmpWekkdayFile.getPeriod().add(
											tmpPeriodFile);
								}

								tmpWekkday.getDay().add(countDay(day));
								tmpWekkdayFile.getDay().add(countDay(day));
							}
							oRuntime.getMonthdays().getDayOrWeekday()
									.add(tmpWekkday);
							runTimeFiles.getMonthdays().getDayOrWeekday()
									.add(tmpWekkdayFile);
						} else if (listCommande[t].indexOf("BYMONTHDAY") != -1) {
							String[] numberDay = listCommande[t].substring(11)
									.split(",");
							Monthdays.Day day = fabrique.createMonthdaysDay();
							Monthdays.Day dayFile = fabrique
									.createMonthdaysDay();

							for (int a = 0; a < numberDay.length; a++) {
								day.getDay()
										.add(Integer.parseInt(numberDay[a]));
								dayFile.getDay().add(
										Integer.parseInt(numberDay[a]));
							}

							day.getPeriod().add(tmpPeriod3);
							dayFile.getPeriod().add(tmpPeriodFile);

							oRuntime.getMonthdays().getDayOrWeekday().add(day);
							runTimeFiles.getMonthdays().getDayOrWeekday()
									.add(dayFile);
						}

					}

				}

				break;

			default:
				break;
			}

		}

	}

	/**
	 * name - Orderauthorization : if a job have a file, no order
	 *@param int : line
	 * 
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */

	public boolean Orderauthorization(int line) {
		for (int i = line; i <= sheet.getLastRowNum(); i++) {

			if (!sheet.getRow(i).getCell(2).toString().isEmpty())
				return true;
			else if (sheet.getRow(i).getCell(3).toString().equals("O"))
				return false;
			else if (!sheet.getRow(i).getCell(1).toString().isEmpty())
				return false;
		}

		return false;
	}

	/**
	 * name - detectBoucle : if a job have a boucle, extern (in another
	 * jobchain) or intern(in order)
	 *@param Period : period to update 
	 * 
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */
	public void detectBoucle(Period period) {
		int ligne = numLigne + 1;
		// je me déplace pour arriver sur le premier job
		while (sheet.getRow(ligne).getCell(2).toString().isEmpty()) {
			ligne++;
		}

		boolean canBoucleInOrder = true;
		String timeBoucle = sheet.getRow(ligne).getCell(47).toString();
		canBoucleInOrder = !timeBoucle.isEmpty();
		boolean run = true;

		// traite deux fois la meme ligne pas très propre
		while (!sheet.getRow(ligne).getCell(2).toString().isEmpty() && run
				&& canBoucleInOrder) {
			if (!timeBoucle.equals(sheet.getRow(ligne).getCell(47).toString()))
				canBoucleInOrder = false;

			if (ligne + 1 <= sheet.getLastRowNum())
				ligne++;
			else
				run = false;
		}

		if (canBoucleInOrder) {
			boucleExterne = false;

			period.setBegin(period.getSingleStart());
			period.setRepeat(timeBoucle.substring(0, 2) + ":"
					+ timeBoucle.substring(2, 4));

			if (runtime)
				period.setEnd(oRuntime.getEnd());

			if (!sheet.getRow(ligne - 1).getCell(16).toString().isEmpty()
					&& !sheet.getRow(ligne - 1).getCell(19).toString()
							.isEmpty()) {

				period.setEnd(sheet.getRow(ligne - 1).getCell(19).toString()
						.substring(0, 2)
						+ ":"
						+ sheet.getRow(ligne - 1).getCell(19).toString()
								.substring(2, 4));
			}

			period.setSingleStart(null);
		}
	}

	/**
	 * name -treatOrderLine treat a order
	 *
	 * @see treatOrderOption
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */

	public void TreatOrderLine() {

		oRuntime = fabrique.createRunTime();
		oParams = fabrique.createParams();
		od = fabrique.createOrder();
		orderfileName = "";

		int i = 3;

		do {
			treatOrderOption(i);
			i++;

		} while (cellIterator.hasNext());

		if (runtime == true) {
			od.setRunTime(oRuntime);
			runtime = false;
		}

		if (orderAuthorization) {
			lOrder.put(orderfileName, od);
			nbrOrder++;
		}
		if (!saveAt.equals("00:00"))
			saveAt = new String("00:00");
	}

	/**
	 * name - addEndErrorEndSucsses add jobchainnodestate : EndError, EndSucces
	 *
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */

	public void addEndErrorEndSucsses() {
		Collection c = jobchain.values();
		Iterator ijobchain = c.iterator();

		while (ijobchain.hasNext())

		{
			JobChain jtemp = (JobChain) ijobchain.next();
			JobChain.JobChainNode temp = fabrique.createJobChainJobChainNode();
			temp.setState("end_SUC_All");
			jtemp.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(temp);
			temp = fabrique.createJobChainJobChainNode();
			temp.setState("!end_ERR");
			jtemp.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(temp);

		}
	}

	/**
	 * name - treatExcelFile() treat a excel file
	 *
	 * @exception IOException
	 * @return boolean
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note don't forget initialize the Excelreader
	 */

	public boolean treatExcelFile() throws IOException {

		log.append("Traitement du fichier Excel \n");

		copyLineTitle();
		nextExcelLine();
		String chaine;
		double size = 40 / sheet.getLastRowNum();
		double valeur = 0;
		boolean ActivateMultiFileOrder = false;
		while (rowIterator.hasNext()) {
			valeur += size;
			if (valeur >= 1) {
				interfaceGraphique.addValueProgressBar((int) valeur);
				valeur = 0;
			}
			Row row = rowIterator.next();// get a line in the file

			cellIterator = row.cellIterator(); // get huts in the line

			coloneExcelSuivant();
			cell = coloneExcelSuivant(); // go directly to 2nd hut
			chaine = cell.toString();

			// check if SID exist for create a jobchain
			if (!chaine.isEmpty()) {
				ActivateMultiFileOrder = true;

				// normaly useless
				if (chaine.equals("-1")) {
					Commands cmd = fabrique.createCommands();
					cmd.getOnExitCode().add("success");
					Order tmp = fabrique.createOrder();
					tmp.setJobChain("/" + file.getName().split("\\.")[0] + "/"
							+ sheet.getRow(numLigne).getCell(5).toString());
					cmd.getAddJobsOrAddOrderOrCheckFolders().add(
							fabrique.createOrder(tmp));

					lJob.get(
							sheet.getRow(numLigne)
									.getCell(5)
									.toString()
									.substring(
											jobchainRunOtherJobChain.length() + 3))
							.getCommands().add(cmd);
					// +3 le chiffre rajouter pour distinger le jobchain + le
					// "_" cela fait trois caracteres à sauter

					treatJobChainLine(false);

				} else {
					treatJobChainLine(true);
				}
			} else {
				// if SID don't exist we look the JID
				// if JID exist we create a job
				// and complete the jobchain
				cell = coloneExcelSuivant();

				if (!cell.toString().isEmpty()) {
					// we treat the entire line (it's a job)

					// now we can't add file in the chain again,
					// now if there is a file, it's on a job
					ActivateMultiFileOrder = false;
					treatJobLine();

				} else {

					// if JID don't exist we look the DEP
					// if DEP exist and equal "R" we create a order
					cell = coloneExcelSuivant();

					if (cell.toString().equals("R")) {

						if (sheet.getRow(numLigne).getCell(15).toString()
								.isEmpty()) {
							TreatOrderLine();
						} else if (sheet.getRow(numLigne - 1).getCell(3)
								.toString().equals("R")) {
							Holidays hlds = fabrique.createHolidays();
							Holiday hld = fabrique.createHoliday();
							String[] vacs = sheet.getRow(numLigne).getCell(25)
									.toString().split(",");

							for (int w = 0; w < vacs.length; w++) {
								hld.setDate(vacs[w]);
								hlds.getWeekdaysOrHolidayOrInclude().add(hld);
								hld = fabrique.createHoliday();
							}
							od.getRunTime().setHolidays(hlds);
						}

					} else if (cell.toString().equals("O")) {
						if (ActivateMultiFileOrder) // use for add files only on
													// a jobchain
						{

							contenuFichier.add(sheet.getRow(numLigne)
									.getCell(30).toString());
							fichier = true;
						}
					} else if (cell.toString().equals("N")) {
						lockInUse = sheet.getRow(numLigne).getCell(32)
								.toString();
					}

				}

			}

			numLigne++;
		}
		addBeginAndEndJobChain(jobchainEnCour);
		nbOrderParJobchain.put(jobchainEnCour, nbrOrder);
		addEndErrorEndSucsses();
		fis.close();
		log.append( "Fin du traitement, les fichiers ont été chargés en mémoire \n");

		return true;
	}

	/**
	 * name - OutputTest test the program, create XML files
	 * 
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */

	public int OutputTest() throws FileNotFoundException, JAXBException,
			IllegalStateException {
		Collection c = jobchain.values();
		Iterator ejobchain = c.iterator();

		Collection cjob = lJob.values();
		Iterator ejob = cjob.iterator();
		Set keyset2 = lJob.keySet();
		Iterator nameFileJob = keyset2.iterator();

		Collection c2 = lOrder.values();
		Iterator eOrder = c2.iterator();
		Set keyset = lOrder.keySet();
		Iterator nameFileOrder = keyset.iterator();

		Job job;
		log.append("génération des jobs \n");

		while (ejob.hasNext())

		{
			job = (Job) ejob.next();
			OutputStream os = new FileOutputStream(outPut + nameFileJob.next()
					+ ".job.xml");

			marshaller.marshal(job, os);

		}

		JobChain jobch;
		log.append("génération des jobchains \n");

		while (ejobchain.hasNext())

		{

			jobch = (JobChain) ejobchain.next();

			OutputStream os = new FileOutputStream(outPut + jobch.getName()
					+ ".job_chain.xml");
			marshaller.marshal(jobch, os);

		}

		int tmp = 0;
		JobChain jobch3;

		ejobchain = c.iterator();

		jobch3 = (JobChain) ejobchain.next();

		log.append( "génération des orders \n");

		while (eOrder.hasNext()) {

			if (tmp == nbOrderParJobchain.get(jobch3.getName())) {
				jobch3 = (JobChain) ejobchain.next();

				tmp = 0;
			} else {
				Order ordTemp = (Order) eOrder.next();
				OutputStream os = new FileOutputStream(outPut
						+ jobch3.getName() + ","
						+ nameFileOrder.next().toString() + ".order.xml");

				marshaller.marshal(fabrique.createOrder(ordTemp), os);

				tmp++;
			}
		}

		log.append( "Fin du traitement pour le fichier: " + file.getName() + " \n");
		log.append( "**********************************************************\n");

		interfaceGraphique.notification(log.toString());
		File f = new File(outPut + "LOG.txt");
		PrintWriter pw;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(f)));
			pw.println(log);
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File di = new File(outPut);
		File fl[] = di.listFiles();

		return fl.length;
	}

	/**
	 * name - addBeginAndEndJobChain add some elements in jobchain (top and end) for formalism,
	 * "00_Start" =>at the beginning of the jobchain
	 * "!end_ERR" "end_SUC_All" at the end
	 * if complexe jobchain (with split) "End"
	 * if jobchain have a file "S_cleanfile"
	 * 
	 * @param String : the current jobChain 
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */
	public void addBeginAndEndJobChain(String jobChainEnCour) {
		if (jobhelp.isJobChainComplex(jobchainEnCour)) {

			JobChain.JobChainNode jbcnTemp = fabrique
					.createJobChainJobChainNode();
			jbcnTemp.setState("00_Start");
			jbcnTemp.setJob("/sos/jitl/JobChainStart");
			jbcnTemp.setErrorState("!end_ERR");
			JobChain.JobChainNode jbcnTemp2 = (JobChainNode) jobchain
					.get(jobchainEnCour)
					.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().get(0);
			jbcnTemp.setNextState(jbcnTemp2.getState());
			jobchain.get(jobchainEnCour)
					.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd()
					.add(0, jbcnTemp);

			jbcnTemp = fabrique.createJobChainJobChainNode();
			jbcnTemp.setState("End");
			jbcnTemp.setJob("/sos/jitl/JobChainEnd");

			if (fichier) {
				jbcnTemp.setErrorState("!end_ERR");
				jbcnTemp.setNextState("S_cleanfile");
				jobchain.get(jobchainEnCour)
						.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd()
						.add(jbcnTemp);

				AddFileJobChain(true);
			} else {
				jbcnTemp.setErrorState("!end_ERR");
				jbcnTemp.setNextState("end_SUC_All");
				jobchain.get(jobchainEnCour)
						.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd()
						.add(jbcnTemp);
			}

		} else if (fichier) {
			AddFileJobChain(false);
		}

	}

	/**
	 * name - AddFileJobChain : add job for wait file in jobchain
	 * 
	 * @param boolean : for know if the jobchain is complex or not 
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */
	public void AddFileJobChain(boolean complexe) {
		JobChain.FileOrderSource file = fabrique
				.createJobChainFileOrderSource();
		Job jobSchedulerExistsFile = fabrique.createJob();

		Params prms = fabrique.createParams();

		Param file1 = fabrique.createParam();
		file1.setName("file");

		Param regex = fabrique.createParam();
		regex.setName("file_spec");

		Param skip_first_files = fabrique.createParam();
		skip_first_files.setName("skip_first_files");

		// défaut
		initialiseParamsJobBoucle(prms, jobSchedulerExistsFile);
		// End défaut

		for (int i = 0; i < contenuFichier.size(); i++) {
			String[] split = contenuFichier.get(i).split("/");
			String directory = "";
			for (int j = 0; j < split.length - 1; j++) {
				if (j < split.length - 2) {
					directory += split[j] + "/";
				} else {
					directory += split[j];
				}
			}

			if (i == 0) {
				file.setDirectory(directory);
				file.setRegex(split[split.length - 1]);
				jobchain.get(jobchainEnCour).getFileOrderSource().add(file);
			} else if (i == 1) {

				file1.setValue(directory);
				regex.setValue(split[split.length - 1]);

			} else {
				regex.setValue(regex.getValue() + " || "
						+ split[split.length - 1]);
			}

		}

		if (contenuFichier.size() == 2)
			skip_first_files.setValue("0");
		else if (contenuFichier.size() > 2)
			skip_first_files.setValue(String.valueOf(contenuFichier.size()));

		if (contenuFichier.size() > 1) {
			prms.getParamOrCopyParamsOrInclude().add(file1);
			prms.getParamOrCopyParamsOrInclude().add(regex);
			prms.getParamOrCopyParamsOrInclude().add(skip_first_files);

			jobSchedulerExistsFile.setParams(prms);

			outJob(jobSchedulerExistsFile, jobchainEnCour + "Wait_Files");

			JobChainNode temp = fabrique.createJobChainJobChainNode();
			temp.setState(jobchainEnCour + "Wait_Files");
			temp.setJob(jobchainEnCour + "Wait_Files");
			JobChain jbctemp = jobchain.get(jobchainEnCour);
			JobChain.JobChainNode jbcnTemp2 = (JobChainNode) jobchain
					.get(jobchainEnCour)
					.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().get(0);
			temp.setNextState(jbcnTemp2.getState());
			jobchain.get(jobchainEnCour)
					.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd()
					.add(0, temp);

		}
		JobChain.FileOrderSink deletFile = fabrique
				.createJobChainFileOrderSink();
		deletFile.setState("S_cleanfile");
		deletFile.setRemove("yes");
		jobchain.get(jobchainEnCour)
				.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd()
				.add(deletFile);
		fichier = false;
		contenuFichier = new ArrayList<String>();
	}

	/**
	 * name - AddFileJobChain : add job for wait file in jobchain
	 * 
	 * @param Params : params to update
	 * @param Job : Job waitfile to update
	 * 
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */
	
	public void initialiseParamsJobBoucle(Params prms, Job jb) {
		
		Param on_empty_result_set = fabrique.createParam();
		on_empty_result_set.setName("on_empty_result_set");
		on_empty_result_set.setValue(jobFileName + "_WaitFiles");
		prms.getParamOrCopyParamsOrInclude().add(on_empty_result_set);

		Param max_file_age = fabrique.createParam();
		max_file_age.setName("max_file_age");
		max_file_age.setValue("1000");
		prms.getParamOrCopyParamsOrInclude().add(max_file_age);

		Param gracious = fabrique.createParam();
		gracious.setName("gracious");
		gracious.setValue("true");
		prms.getParamOrCopyParamsOrInclude().add(gracious);

		Param min_file_age = fabrique.createParam();
		min_file_age.setName("min_file_age");
		min_file_age.setValue("0");
		prms.getParamOrCopyParamsOrInclude().add(min_file_age);

		Param max_file_size = fabrique.createParam();
		max_file_size.setName("max_file_size");
		max_file_size.setValue("1000");
		prms.getParamOrCopyParamsOrInclude().add(max_file_size);

		Param min_file_size = fabrique.createParam();
		min_file_size.setName("min_file_size");
		min_file_size.setValue("0");
		prms.getParamOrCopyParamsOrInclude().add(min_file_size);

		Param skip_last_files = fabrique.createParam();
		skip_last_files.setName("skip_last_files");
		skip_last_files.setValue("0");
		prms.getParamOrCopyParamsOrInclude().add(skip_last_files);

		Param count_files = fabrique.createParam();
		count_files.setName("count_files");
		count_files.setValue("false");
		prms.getParamOrCopyParamsOrInclude().add(count_files);

		Param create_order = fabrique.createParam();
		create_order.setName("create_order");
		create_order.setValue("");
		prms.getParamOrCopyParamsOrInclude().add(create_order);

		Param create_order_for_all_files = fabrique.createParam();
		create_order_for_all_files.setName("create_order_for_all_files");
		create_order_for_all_files.setValue("");
		prms.getParamOrCopyParamsOrInclude().add(create_order_for_all_files);

		Param order_jobchain_name = fabrique.createParam();
		order_jobchain_name.setName("order_jobchain_name");
		order_jobchain_name.setValue("");
		prms.getParamOrCopyParamsOrInclude().add(order_jobchain_name);

		Param next_state = fabrique.createParam();
		next_state.setName("next_state");
		next_state.setValue("");
		prms.getParamOrCopyParamsOrInclude().add(next_state);

		Param merge_order_parameter = fabrique.createParam();
		merge_order_parameter.setName("merge_order_parameter");
		merge_order_parameter.setValue("false");
		prms.getParamOrCopyParamsOrInclude().add(merge_order_parameter);

		Param expected_size_of_result_set = fabrique.createParam();
		expected_size_of_result_set.setName("expected_size_of_result_set");
		expected_size_of_result_set.setValue("");
		prms.getParamOrCopyParamsOrInclude().add(expected_size_of_result_set);

		Param raise_error_if_result_set_is = fabrique.createParam();
		raise_error_if_result_set_is.setName("raise_error_if_result_set_is");
		raise_error_if_result_set_is.setValue("");
		prms.getParamOrCopyParamsOrInclude().add(raise_error_if_result_set_is);

		Param check_steady_state_of_files = fabrique.createParam();
		check_steady_state_of_files.setName("check_steady_state_of_files");
		check_steady_state_of_files.setValue("false");
		prms.getParamOrCopyParamsOrInclude().add(check_steady_state_of_files);

		Param steady_state_count = fabrique.createParam();
		steady_state_count.setName("steady_state_count");
		steady_state_count.setValue("30");
		prms.getParamOrCopyParamsOrInclude().add(steady_state_count);

		Param check_steady_state_interval = fabrique.createParam();
		check_steady_state_interval.setName("check_steady_state_interval");
		check_steady_state_interval.setValue("1");
		prms.getParamOrCopyParamsOrInclude().add(check_steady_state_interval);

		Description dsc = fabrique.createJobDescription();

		Include inc = fabrique.createInclude();
		inc.setFile("jobs/JobSchedulerExistsFile.xml");
		dsc.getContent().add(inc);
		jb.setDescription(dsc);

		Script scr = fabrique.createScript();
		scr.setJavaClass("sos.scheduler.file.JobSchedulerExistsFile");
		scr.setLanguage("java");
		jb.setScript(scr);

		jb.setOrder("yes");
		jb.setStopOnError("no");

	}

	// for test the programme
	public static void main(String[] args) throws IOException, JAXBException {

		ExcelReader exrd = new ExcelReader(
				"C:/Users/j-vincent/Documents/OCAB-Extraction/QVI/PROD/Moyen/CFM.xlsm",
				"C:/Users/j-vincent/Documents/résultat",
				new ConvertisseurTwsJbs(), true,false);

		if (exrd.treatExcelFile())

		{
			System.out.println("Fichier Excel traité");
			exrd.OutputTest();
		} else {
			System.out.println("prob");
		}

		System.exit(0);
	}
}