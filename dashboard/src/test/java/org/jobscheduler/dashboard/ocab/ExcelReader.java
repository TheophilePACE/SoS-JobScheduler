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
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;

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
	String saveAt;
    JobHelper jobhelp;
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
	LinkedHashMap<String, JobChain> jobchain = new LinkedHashMap<String, JobChain>();
	Hashtable<String, Integer> nbOrderParJobchain = new Hashtable<String, Integer>();// amount
																						// "order"
																						// for
																						// each
																						// jobchain
	Hashtable<String, Job> ljob = new Hashtable<String, Job>();
	LinkedHashMap<String, Order> lorder = new LinkedHashMap<String, Order>();
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
		
		jobhelp=new JobHelper(sheet);
		jobhelp.initialisation();
		
		rowIterator = sheet.iterator();// Iterate through each rows one by one

		cellFirstLigne = sheet.getRow(0).cellIterator();// Get the first line of
														// excel file
		jobchainEnCour = null;
		nbrDeOrder = 0;
		saveAt="00:00";

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
	 * name - treatJobChainLine, initialization of a new jobchain
	 * 
	 * @author Jean-vincent
	 * @date 20/05/2015
	 * @note
	 * 
	 */

	public void treatJobChainLine() {

		int i = 2;// cell and "i"(column title) must be synchronized, title must
					// correspond to cell
		jbc = fabrique.createJobChain();
		jbc.setVisible("yes");

		do {

			cell = coloneExcelSuivant();// first time, we pass to the case 3nd
										// case
										// it's why i=2 (0,1,2)=>3

			if (!cell.toString().isEmpty()) {
				treatJobChainOption(i); // treat a jobchain hut
			}

			i++;

		} while (cellIterator.hasNext());

		jobchain.put(jbc.getName(), jbc);

		if (jobchainEnCour != jbc.getName() && jobchainEnCour!=null) {// reset correspondence between jobchain
										// and order
			
			nbOrderParJobchain.put(jobchainEnCour, nbrDeOrder);
			nbrDeOrder = 0;
			
		}
		jobchainEnCour = jbc.getName();

	}

	/**
	 * name - treatJobChainOption functional correspondence between the excel 
	 *                            file and jobscheduler (jobchain)
	 * 
	 * @param int : correspondence with the title line of the excel file
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */
	
	public void treatJobChainOption(int i) {


		switch (ligneTitre.get(i).toString()) {

		case "jobstream":
			jbc.setName(cell.toString());

			break;

		case "priority":

			break;

		default:
			break;
		}

	}

	/**
	 * name - traiterLesOptionDunJob functional correspondence between the excel
	 * file and jobscheduler (job)
	 * 
	 * @param int : correspondence with the title line of the excel file
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */

	public void treatJobOption(int i) {
		

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
				if(jbcn.getNextState().isEmpty())
				{	
				
					jbcn.setErrorState("!end_ERR");
	
				}
				else
				{
					jbcn.setErrorState(jbcn.getNextState());
				}
			}
			break;

		case "follows":
			
             if(!jobhelp.getNextJob(jb.getTitle()).equals("NogetL1"))
             {	 
			jbcn.setNextState(jobhelp.getNextJob(jb.getTitle()));
             }
             else
             {
            	 jbcn.setNextState("end_SUC_All");
             }
			
			break;

		case "jid":

			jbcn.setState(cell.toString());

			break;

		default:
			break;
		}

	}

	/**
	 * name - traiterUneLigneJob treat a job
	 * 
	 * @see traiterLesOptionDunJob
	 * @author jean-vincent
	 * @date 20/05/2015
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

			
				cell = coloneExcelSuivant();
				i++;
				
				
			if (!cell.toString().isEmpty()||i==11) {
				
				if(i==29){treatJobOption(41);}
				else
				{
				treatJobOption(i); // treat a job hut
				}
			}
                 
				

		} while (cellIterator.hasNext());
		
		
		jbc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(jbcn);// add
																		// the
																		// jobchainnode
																		// in
																		// jobchain
		ljob.put(jb.getTitle(), jb);
		
	}
	
	/**
	 * name - countDay  correspondence between  excel and job scheduler date
	 * 
	 * 
	 * @param String[] : correspondence with the title line of the excel file
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */	
	
public String countDay(String[] days)
{
	String day="";
	
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

public String countDay(String day)
{
	
	
	
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
	 * name - traiterLesOptionDunOrder functional correspondence between the
	 * excel file and jobscheduler (order)
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

				od.setTitle(cell.toString());

				break;
			case "until":

				oRuntime.setBegin("00:00");
				String heureEnd = cell.toString().substring(0, 2) + ":"
						+ cell.toString().substring(2, 4);
				oRuntime.setEnd(heureEnd);
				runtime = true;

				break;
				
			case "timezone":

				oRuntime.setTimeZone(cell.toString());
				runtime=true;

				break;
			case "at":

			
				String heure = cell.toString().substring(0, 2) + ":"
						+ cell.toString().substring(2, 4);
                saveAt=heure;

				break;

			case "ical":
				
				String[] listCommande = cell.toString().split(";");
				
				
				
					if(listCommande[0].indexOf("FREQ=DAILY")!=-1)
					{
						
						Weekdays.Day tmpDay2 = fabrique.createWeekdaysDay();
						Period tmpPeriod2 = fabrique.createPeriod();
						tmpPeriod2.setSingleStart(saveAt);
						tmpDay2.getDay().add("1 2 3 4 5 6 7");
						tmpDay2.getPeriod().add(tmpPeriod2);
						if (oRuntime.getWeekdays() != null) {
							
							
								oRuntime.getWeekdays().getDay().add(tmpDay2);
							

						}
						else
						{
							Weekdays tmpWeek2 = fabrique.createWeekdays();
							
							
							
												
								
								
														
							tmpWeek2.getDay().add(tmpDay2);
							
							oRuntime.setWeekdays(tmpWeek2);
							runtime = true;

							
							
						}
						
					}
					else if (listCommande[0].indexOf("FREQ=WEEKLY")!=-1)
					{
						
						Weekdays.Day tmpDay2 = fabrique.createWeekdaysDay();
						Period tmpPeriod2 = fabrique.createPeriod();
						tmpPeriod2.setSingleStart(saveAt);						
						tmpDay2.getPeriod().add(tmpPeriod2);
						
						if (oRuntime.getWeekdays() != null) {
							
							for(int t=1;t<listCommande.length;t++)
							{
								if(listCommande[t].indexOf("BYDAY")!=-1)
								{
									String substringDay = listCommande[t].substring(6);
									
									String[] days = substringDay.split(",");
									tmpDay2.getDay().add(countDay(days));
									oRuntime.getWeekdays().getDay().add(tmpDay2);
									
								}
								
							}
							
						

					}
					else
					{
						for(int t=1;t<listCommande.length;t++)
						{
							if(listCommande[t].indexOf("BYDAY")!=-1)
							{
								Weekdays tmpWeek2 = fabrique.createWeekdays();
													
								String substringDay = listCommande[t].substring(6);
								String[] days = substringDay.split(",");
								
								tmpDay2.getDay().add(countDay(days));
								tmpWeek2.getDay().add(tmpDay2);
								
								oRuntime.setWeekdays(tmpWeek2);
								runtime = true;
							}
						}	
						

						
						
					}
						
					}
					else if (listCommande[0].indexOf("FREQ=MONTHLY")!=-1)
					{                               
						runtime = true;
						String whichPrecedent="";
                          if (oRuntime.getMonthdays() == null) {
                        	  Monthdays tmpMonthday=fabrique.createMonthdays();
                        	  oRuntime.setMonthdays(tmpMonthday);
                          }  
							
							Monthdays.Weekday tmpWekkday=fabrique.createMonthdaysWeekday();;
							Period tmpPeriod3 = fabrique.createPeriod();
							tmpPeriod3.setSingleStart(saveAt);
							for(int t=1;t<listCommande.length;t++)
							{
								if(listCommande[t].indexOf("BYDAY")!=-1)
								{
									String substringDay = listCommande[t].substring(6);
									String[] days = substringDay.split(",");
									
									for(int f=0;f<days.length;f++)
									{
										String which=days[f].substring(0,1);
										String day=days[f].substring(1);
										
										if(f==0)
										{
											tmpWekkday = fabrique.createMonthdaysWeekday();
											tmpWekkday.setWhich(which);
											whichPrecedent=which;
											tmpWekkday.getPeriod().add(tmpPeriod3);
										}
										else if(!whichPrecedent.equals(which))
										{
											oRuntime.getMonthdays().getDayOrWeekday().add(tmpWekkday);
											tmpWekkday = fabrique.createMonthdaysWeekday();
											tmpWekkday.setWhich(which);
											tmpWekkday.getPeriod().add(tmpPeriod3);
										}
										tmpWekkday.getDay().add(countDay(day));
										
									}
									oRuntime.getMonthdays().getDayOrWeekday().add(tmpWekkday);
									
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
	 * name - traiterUneLigneOrder treat a order
	 *
	 * @see traiterLesOptionDunOrder
	 * @author jean-vincent
	 * @date 20/05/2015
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
	 * name - addEndErrorEndSucsses add jobchainnodestate : EndError, EndSucces
	 *
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */

	public void addEndErrorEndSucsses() {
		Collection c=jobchain.values();
		Iterator ijobchain=c.iterator();
		
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
	 * name - trainterLeFichierExcel() treat a excel file
	 *
	 * @exception IOException
	 * @return boolean
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note don't forget initialize the Excelreader
	 */

	public boolean treatExcelFile() throws IOException {
		copyLineTitle();
		nextExcelLine();

		while (rowIterator.hasNext()) {

			Row row = rowIterator.next();// get a line in the file

			cellIterator = row.cellIterator(); // get huts in the line

			coloneExcelSuivant();
			cell = coloneExcelSuivant(); // go directly to 2nd hut
			chaine = cell.toString();

			// check if SID exist for create a jobchain
			if (!chaine.isEmpty()) {
				treatJobChainLine();

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
		nbOrderParJobchain.put(jobchainEnCour, nbrDeOrder);
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
	 * name - OutputTest test the program, create XML files
	 * 
	 * @param int : 1=> out a job, 2=> out a jobchain, 3=order, 12= job et
	 *        jobchain
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */

	public int OutputTest(int lancement) throws FileNotFoundException,
			JAXBException, IllegalStateException {
		Collection c=jobchain.values();
		Iterator ejobchain=c.iterator();
		
		
		Enumeration eLjob = ljob.elements();
		
		Collection c2=lorder.values();
		Iterator eOrder=c2.iterator();
		
		int i = 0;

		if (lancement == 1 || lancement == 12 || lancement == 123
				|| lancement == 13) {
			Job job;
			while (eLjob.hasMoreElements())

			{
				job = (Job) eLjob.nextElement();
				OutputStream os = new FileOutputStream(outPut + job.getTitle()
						+ ".job.xml");

				marshaller.marshal(job, os);
				i++;
			}

		}
		if (lancement == 2 || lancement == 12 || lancement == 123
				|| lancement == 23) {
			JobChain jobch;
			while (ejobchain.hasNext())

			{
				jobch = (JobChain) ejobchain.next();

				OutputStream os = new FileOutputStream(outPut + jobch.getName()
						+ ".job_chain.xml");
				marshaller.marshal(jobch, os);
				i++;
			}

		}

		if (lancement == 3 || lancement == 13 || lancement == 123
				|| lancement == 23) {
			int tmp = 0;
			JobChain jobch;
			
			ejobchain=c.iterator();
			
			jobch = (JobChain) ejobchain.next();
			
		
			while (eOrder.hasNext()) {
				Order ordTemp = (Order) eOrder.next();

				if (tmp == nbOrderParJobchain.get(jobch.getName())) {
					jobch = (JobChain) ejobchain.next();
					tmp = 0;
				}
				OutputStream os = new FileOutputStream(outPut + jobch.getName()
						+ "," + ordTemp.getTitle() + ".order.xml");
			
				marshaller.marshal(fabrique.createOrder(ordTemp), os);
				
			
				tmp++;
			}
				

		}

		File di = new File("D:/resultat/");
		File fl[] = di.listFiles();

		if (fl.length != 147)
			throw new IllegalStateException(
					"Nombre de fichiers attendus incorrect");
		return fl.length;
	}

	public static void main(String[] args) throws IOException, JAXBException {

		ExcelReader exrd = new ExcelReader(
				"C:/Users/puls/workspace2/SoS-JobScheduler/dashboard/src/test/ressource/OCAB_Test.xlsx",
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