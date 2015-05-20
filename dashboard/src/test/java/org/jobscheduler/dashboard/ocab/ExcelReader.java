/*
 * Nom de classe : ExcelReader
 *
 * Description   : Module permettant d'ordonnancer des taches sous JobScheduler à partir d'un fichier Excel 
 *
 * Version       : 1.0
 *
 * Date          : 20/05/2015
 * 
 * Copyright     : 
 */

package org.jobscheduler.dashboard.ocab;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jobscheduler.dashboard.jobdefinition.xml.*;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

public class ExcelReader {
	/**
	 * System objects
	 */
	File file;;
	FileInputStream fis;
	JAXBContext jc;
	Marshaller marshaller;
	ObjectFactory fabrique = new ObjectFactory();
	XSSFWorkbook wb;
	XSSFSheet sheet;
	Cell cell;
	String chaine;
	String outPut;

	/**
	 * Name the order based on jobchain
	 */

	String jobchainEnCour;
	int nbrDeOrder;

	/**
	 * Existence or not of a runtime / params ( job scheduler functionality )
	 */
	boolean runtime = false;
	boolean params = false;

	/**
	 * Lists
	 */

	ArrayList<String> ligneTitre = new ArrayList<String>(); // title line of
															// Excel
	Hashtable job = new Hashtable();
	Hashtable<String, JobChain> jobchain = new Hashtable<String, JobChain>();
	Hashtable<String, Integer> nbOrderParJobchain = new Hashtable<String, Integer>();// amount
																						// "order"
																						// for
																						// each
																						// jobchain
	Hashtable<String, Job> ljob = new Hashtable<String, Job>();
	Hashtable<String, Order> lorder = new Hashtable<String, Order>();
	Iterator<Row> rowIterator;
	Iterator<Cell> cellFirstLigne;
	Iterator<Cell> cellIterator;

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

	/**
	 * Constructor using Excel and Xml path
	 */

	public ExcelReader(String EmplacementFichierExcel, String output)
			throws JAXBException, IOException {
		super();
		this.outPut = output;
		file = new File(EmplacementFichierExcel);
		jc = JAXBContext
				.newInstance("org.jobscheduler.dashboard.jobdefinition.xml");
		marshaller = jc.createMarshaller();
		marshaller.setProperty("jaxb.encoding", "ISO-8859-1");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(
				"com.sun.xml.bind.characterEscapeHandler",
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

		
		rowIterator = sheet.iterator();// Iterate through each rows one by one

		
		cellFirstLigne = sheet.getRow(0).cellIterator();// Get the first line of excel file
		jobchainEnCour = null;
		nbrDeOrder = 0;

	}

	public String cdata(String st) {
		return "<![CDATA[" + st + "]]>"; // for add CDATA in Xml file
	}

	/**
	 * name - copieLigneTitre, copy in a arraylist the name of each
	 * column to know on which work
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
	 * name - ligneExcelSuivant, 
	 *               Scroll to the next excel line
	 *   
	 * @return      boolean
	 * @author      Jean-vincent 
	 * @date        20/05/2015
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
	 * name - coloneExcelSuivant, 
	 *               return the next excel column 
	 * 
	 *     
	 * @return      Cell
	 * @author      Jean-vincent 
	 * @date        20/05/2015
	 * @note 
	 */
	
	public Cell coloneExcelSuivant() {
		if (cellIterator.hasNext()) {
			return cellIterator.next();

		}
		return null;
	}

	/** 
	 * name - initialisationJobChain, 
	 *               initialization of a new jobchain 
	 *   
	 * @author      Jean-vincent 
	 * @date        20/05/2015
	 * @note 
	 */
	
	public void initializationJobChain() {

		
		coloneExcelSuivant();
		coloneExcelSuivant();
		coloneExcelSuivant();
		cell = coloneExcelSuivant(); //go directly to the column 4 which contains the name of jobchain

		System.out.println("Affichage du job chaine SID numéro:" + chaine
				+ ", " + cell.toString());
		jbc = fabrique.createJobChain();
		jbc.setVisible("yes");
		jbc.setName(cell.toString());
		jobchain.put(cell.toString(), jbc);
	
		if (jobchainEnCour != null) {//reset correspondence between jobchain and order
			nbOrderParJobchain.put(cell.toString(), nbrDeOrder);
			nbrDeOrder = 0;
		}
		jobchainEnCour = cell.toString();
		
	}
	
	/** 
	 * name -       traiterLesOptionDunJob 
	 *              functional correspondence between the excel file and jobscheduler (job)
	 *              
	 * @param       int : correspondence with the title line of the excel file
	 * @author      jean-vincent 
	 * @date        20/05/2015
	 * @note 
	 */
	

	public void treatJobOption(int i) {
		System.out.print(ligneTitre.get(i) + ":" + cell.toString() + ", ");

		switch (ligneTitre.get(i).toString()) {

		case "job":

			jb.setTitle(cell.toString());
			jbcn.setJob(cell.toString());
			jbcn.setState(cell.toString());
			break;

		case "scriptname":

			scrpt.getContent().add(cdata(cell.toString()));
			jb.setScript(scrpt);
			break;

		case "recovery_option":

			if (cell.toString().equals("stop")) {
				jbcn.setErrorState("!end_ERR");
			} else {
				jbcn.setErrorState(jbcn.getNextState());
			}
			break;

		case "follows":

			jbcn.setNextState(cell.toString());
			break;

		case "jid":

			jbcn.setState(cell.toString());

			break;

		default:
			break;
		}

	}

	/** 
	 * name -       traiterUneLigneJob 
	 *              treat a job
	 *              
	 * @see         traiterLesOptionDunJob              
	 * @author      jean-vincent 
	 * @date        20/05/2015
	 * @note 
	 */
	
	public void treatJobLine() {
		jbcn = fabrique.createJobChainJobChainNode();
		jb = fabrique.createJob();
		jb.setOrder("yes");
		scrpt = fabrique.createScript();
		scrpt.setLanguage("shell");

		int i = 2;// information about job begin in the 2nd column
		do {

			if (i > 2)
				cell = coloneExcelSuivant();

			
			if (!cell.toString().isEmpty()) {
				treatJobOption(i); //treat a job hut 
			}

			i++;

		} while (cellIterator.hasNext());

		jbc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(jbcn);//add the jobchainnode in jobchain
		ljob.put(jb.getTitle(), jb);
		System.out.println("");
	}

	/** 
	 * name -       traiterLesOptionDunOrder 
	 *              functional correspondence between the excel file and jobscheduler (order)
	 *              
	 * @param       int : correspondence with the title line of the excel file
	 * @author      jean-vincent 
	 * @date        20/05/2015
	 * @note 
	 */
	
	public void treatOrderOption(int i) {
		if (i > 3)
			cell = coloneExcelSuivant();

		if (!cell.toString().equals("")) {
			System.out.print(ligneTitre.get(i) + ":" + cell.toString() + ", ");

			switch (ligneTitre.get(i).toString()) {

			case "runcycle":

				od.setTitle(cell.toString());

				break;

			case "at":

				Weekdays TmpWeek = fabrique.createWeekdays();
				Weekdays.Day TmpDay = fabrique.createWeekdaysDay();
				Period TmpPeriod = fabrique.createPeriod();

				TmpPeriod.setSingleStart(cell.toString());
				TmpDay.getPeriod().add(TmpPeriod);
				TmpWeek.getDay().add(TmpDay);
				oRuntime.setWeekdays(TmpWeek);
				runtime = true;

				break;

			case "ical":
				if (oRuntime.getWeekdays() != null) {
					if (cell.toString().equals("FREQ=DAILY;INTERVAL=1")) {
						oRuntime.getWeekdays().getDay().get(0).getDay()
								.add("1 2 3 4 5 6 7");
					}
				} else {
					Weekdays tmpWeek2 = fabrique.createWeekdays();
					Weekdays.Day tmpDay2 = fabrique.createWeekdaysDay();
					Period tmpPeriod2 = fabrique.createPeriod();

					tmpPeriod2.setSingleStart("00:00");
					tmpDay2.getPeriod().add(tmpPeriod2);
					tmpDay2.getDay().add("1 2 3 4 5 6 7");
					tmpWeek2.getDay().add(tmpDay2);
					oRuntime.setWeekdays(tmpWeek2);
					runtime = true;

				}
				break;

			default:
				break;
			}

		}

	}

	/** 
	 * name -       traiterUneLigneOrder 
	 *              treat a order
	 *
	 * @see         traiterLesOptionDunOrder     
	 * @author      jean-vincent 
	 * @date        20/05/2015
	 * @note 
	 */
	public void treatOrderLine() {
		oRuntime = fabrique.createRunTime();
		oParams = fabrique.createParams();
		od = fabrique.createOrder();

		int i = 3;

		do {
			treatOrderOption(i);
			i++;

		} while (cellIterator.hasNext());

		if (runtime == true) {
			od.setRunTime(oRuntime);
		}

		lorder.put(od.getTitle(), od);
		nbrDeOrder++;

	}

	/** 
	 * name -       addEndErrorEndSucsses 
	 *              add jobchainnodestate : EndError, EndSucces
	 *
	 * @author      jean-vincent 
	 * @date        20/05/2015
	 * @note 
	 */
	
	public void addEndErrorEndSucsses() {
		Enumeration ejobchain = jobchain.elements();
		while (ejobchain.hasMoreElements())

		{
			JobChain jtemp = (JobChain) ejobchain.nextElement();
			JobChain.JobChainNode temp = fabrique.createJobChainJobChainNode();
			temp.setState("end_SUC_All");
			jtemp.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(temp);
			temp = fabrique.createJobChainJobChainNode();
			temp.setState("!end_ERR");
			jtemp.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(temp);

		}
	}

	/** 
	 * name -       trainterLeFichierExcel() 
	 *              treat a excel file
	 *
	 * @exception   IOException
	 * @return      boolean
	 * @author      jean-vincent 
	 * @date        20/05/2015
	 * @note        don't forget initialize the Excelreader
	 */
	
	public boolean treatExcelFile() throws IOException {
		copyLineTitle();
		nextExcelLine();

		while (rowIterator.hasNext()) { 
			
			Row row = rowIterator.next();// get a line in the file
			
			cellIterator = row.cellIterator(); //get huts in the line

			
			coloneExcelSuivant();
			cell = coloneExcelSuivant(); //go directly to 2nd hut
			chaine = cell.toString();

			// check if SID exist for create a jobchain
			if (!chaine.isEmpty()) {
				initializationJobChain();

			} else {
				// if SID don't exist we look the JID
				// if JID exist we create a job
				// and complete the jobchain
				cell = coloneExcelSuivant();

				if (!cell.toString().isEmpty()) {
					// we treat the entire line (it's a job)
					
					treatJobLine();
				} else {
					// if JID don't exist we look the DEP
					// if DEP exist and equal "R" we create a order
					cell = coloneExcelSuivant();

					if (cell.toString().equals("R")) {
						treatOrderLine();
					}

				}

			}

		}
		addEndErrorEndSucsses();
		fis.close();
		return true;
	}

	

	        // 1=job
			// 2=jobchain
			// 3=order
			// 12= job et jobchain
			// etc...
	
	/** 
	 * name -       OutputTest 
	 *              test the program, create XML files
	 *              
	 * @param       int : 1=> out a job, 2=> out a jobchain, 3=order, 12= job et jobchain
	 * @author      jean-vincent 
	 * @date        20/05/2015
	 * @note        
	 */

	public void OutputTest(int lancement) throws FileNotFoundException,
			JAXBException {
		Enumeration ejobchain = jobchain.elements();
		Enumeration eLjob = ljob.elements();
		Enumeration eOrder = lorder.elements();
		int i = 0;

		if (lancement == 1 || lancement == 12 || lancement == 123
				|| lancement == 13) {
			Job job;
			while (eLjob.hasMoreElements())

			{
				job = (Job) eLjob.nextElement();
				OutputStream os = new FileOutputStream(outPut + job.getTitle()
						+ ".xml");

				marshaller.marshal(job, os);
				i++;
			}

		}
		if (lancement == 2 || lancement == 12 || lancement == 123
				|| lancement == 23) {
			JobChain jobch;
			while (ejobchain.hasMoreElements())

			{
				jobch = (JobChain) ejobchain.nextElement();

				OutputStream os = new FileOutputStream(outPut + jobch.getName()
						+ ".xml");
				marshaller.marshal(jobch, os);
				i++;
			}

		}
		
		if (lancement == 3 || lancement == 13 || lancement == 123
				|| lancement == 23) {
			int tmp = 0;
			JobChain jobch;
			ejobchain = jobchain.elements();
			jobch = (JobChain) ejobchain.nextElement();
			while (eOrder.hasMoreElements()) {
				Order ordTemp = (Order) eOrder.nextElement();

				if (tmp == nbOrderParJobchain.get(jobch.getName())) {
					jobch = (JobChain) ejobchain.nextElement();
					tmp = 0;
				}
				OutputStream os = new FileOutputStream(outPut + jobch.getName()
						+ ",order.xml");
				marshaller.marshal(fabrique.createOrder(ordTemp), os);
				i++;
				tmp++;
			}

		}
	}

	public static void main(String[] args) throws IOException, JAXBException {

		ExcelReader exrd = new ExcelReader("D:/excel/KARMA_QAL_1.4_FULL.xlsx",
				"D:/resultat/");
		// 1=job
		// 2=jobchain
		// 3=order
		// 12= job et jobchain
		// etc...
		if (exrd.treatExcelFile())
			exrd.OutputTest(123);

	}
}