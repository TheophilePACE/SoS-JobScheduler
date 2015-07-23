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

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jobscheduler.dashboard.jobdefinition.xml.*;
import org.jobscheduler.dashboard.jobdefinition.xml.JobChain.FileOrderSink;
import org.jobscheduler.dashboard.jobdefinition.xml.JobChain.FileOrderSource;
import org.jobscheduler.dashboard.jobdefinition.xml.JobChain.JobChainNode;

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
	String outPut;
	String saveAt;
	int metrique;
	boolean fichier=false;
	String contenuFichier="";
	String timeJob="";
	String untilJob="";
	/**
	 * For the conversion , we must know the next steps for each job,
	 * but ExcelReader work sequentially and don't stock anything about the next 
	 * JobHelper contain some operation for now who is the next, 
	 * or if jobchain is complex. 
	 * 
	 */
    JobHelper jobhelp;
    
    /**
     *this variable allows insert in a jobchain, a job split and a job for the synchronization
     *we need those variables because there is no correspondence in the ocab file, we add the job manually. 
     *jbcnSplitBool jbcnSyncBool, just for say if we need add a split/Sync job or not.
     *alreadySync, many job can converge on a job of Synchronization, but there is only one job of Synchronization,
     *when he exist, alreadySync is true  
     */
    boolean jbcnSplitBool=false;
    boolean jbcnSyncBool=false;
    boolean alreadySync=false;
	
    
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

	ArrayList<String> ligneTitre = new ArrayList<String>(); // title line of  Excel															
	Hashtable job = new Hashtable();
	LinkedHashMap<String, JobChain> jobchain = new LinkedHashMap<String, JobChain>();
	// amount "order" for each jobchain
	Hashtable<String, Integer> nbOrderParJobchain = new Hashtable<String, Integer>();
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
	JobChainNode jbcnSplit;
	JobChainNode jbcnSync;
	
	/**
	 * Constructor using Excel and Xml path
	 */

	public ExcelReader(String EmplacementFichierExcel, String output)
			throws JAXBException, IOException {
		super();
		this.outPut = output;
		metrique=0;
		file = new File(EmplacementFichierExcel);
		jc = JAXBContext
				.newInstance("org.jobscheduler.dashboard.jobdefinition.xml");
		marshaller = jc.createMarshaller();
		marshaller.setProperty("jaxb.encoding", "ISO-8859-1");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		//For add CDATA, jaxb don't allow the use of "<!" (special character in xml)
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
		
		for(Row row : sheet) {
			//System.out.println("");
			   for(int cn=0; cn<row.getLastCellNum(); cn++) {
			       // If the cell is missing from the file, generate a blank one
			       // (Works by specifying a MissingCellPolicy)
			       Cell cell = row.getCell(cn, Row.CREATE_NULL_AS_BLANK);
			       // Print the cell for debugging
			       //System.out.print("|CELL: " + cn + " --> " + cell.toString());
			   }
			}
		ExcelCleaner(sheet);
		FileOutputStream fileOut = new FileOutputStream("monfichier.xlsm");
		wb.write(fileOut);

		jobhelp=new JobHelper(sheet,marshaller);
		jobhelp.initialization(output);
		
		rowIterator = sheet.iterator();// Iterate through each rows one by one

		cellFirstLigne = sheet.getRow(0).cellIterator();// Get the first line of
														// excel file
		jobchainEnCour = null;
		nbrDeOrder = 0;
		saveAt="00:00";//Default Time

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
        //for now if we treat a new jobchain
		
		if (jobchainEnCour != jbc.getName() && jobchainEnCour!=null) {// reset correspondence between jobchain
										// and order
			
			nbOrderParJobchain.put(jobchainEnCour, nbrDeOrder);
			nbrDeOrder = 0;
			
			addBeginAndEndJobChain(jobchainEnCour);
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
			
		case "at":
			timeJob= cell.toString().substring(0, 2) + ":"
					+ cell.toString().substring(2, 4);
			
			break;
			
		case "until":
			untilJob= cell.toString().substring(0, 2) + ":"
					+ cell.toString().substring(2, 4);
			
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
			
			//if next line is a split, then we have to create a new jobchainnode (split)  in actual jobchain
			if(jobhelp.getNextJob(cell.toString()).indexOf("Split_")!=-1)
			{
				jbcnSplit=fabrique.createJobChainJobChainNode();
				String temp=jobhelp.getNextJob(cell.toString());//just a temporary variable
				jbcnSplit.setState(temp);
				jbcnSplit.setNextState(jobhelp.getNextJob(temp));
				jbcnSplit.setJob("/sos/jitl/jobChainSplitter");
				jbcnSplitBool=true;
			}
			
			//if next line is a split, then we have to create a new synchro in actual jobchain
			//but every job in a complex case have synch for next or we have only one synch
			//then we notice we need create a job synch but we add it at the end of the complex case,
			//when next of the current job is different of Synch and alreySync(=we have a job synch to add) is true    
			
			if(jobhelp.getNextJob(cell.toString()).indexOf("Sync_")!=-1)
			{
				if(!alreadySync)
				{	
				jbcnSync=fabrique.createJobChainJobChainNode();
				String temp=jobhelp.getNextJob(cell.toString());
				jbcnSync.setState(temp);
				jbcnSync.setNextState(jobhelp.getNextJob(temp));
				jbcnSync.setJob(temp);
				
				alreadySync=true;
				}
			}
			else if(alreadySync)
			{
				jbcnSyncBool=true;
				alreadySync=false;
				
			}
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
            	 if(jobhelp.isJobChainComplex(jobchainEnCour)) //if it's a complex case next of the last jobchainode go to jobchainnode end
             	{
             		jbcn.setNextState("End");	
             	}
            	 else if(fichier)
            	 {
            		 jbcn.setNextState("S_cleanfile"); 
            	 }
            	 else	 
            	 {	 
            	 jbcn.setNextState("end_SUC_All");
            	 }
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
		
		if(!timeJob.isEmpty() && fichier)
	      {
	    	  RunTime rt= new  RunTime();
	    	  rt.setBegin(timeJob);
	    	  
	    	  if(!untilJob.isEmpty() && !untilJob.equals("23:59"))
	    		 rt.setEnd(untilJob); 
	    	  
	    	  jb.setRunTime(rt);
	    	  
	    	  timeJob="";
	    	  untilJob="";
	      }
		
		do {

			
				cell = coloneExcelSuivant();
				i++;
				
				
			if (!cell.toString().isEmpty()||i==11) {
				 
				
				
				treatJobOption(i); // treat a job hut
				
			}
                 
				

		} while (cellIterator.hasNext());
		
		if(jbcnSyncBool)//if a split or a synchro file exist then add in the jobchain
		{
			jbc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(jbcnSync);
			jbcnSyncBool=false;//for reset
			
			
		}
		
		jbc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(jbcn);// add the jobchainnode in jobchain																 
		
		if(jbcnSplitBool)//if a split or a synchro file exist then add in the jobchain
		{
			jbc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(jbcnSplit);
			jbcnSplitBool=false;//for reset
			
			
		}
		
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
							
							Monthdays.Weekday tmpWekkday=fabrique.createMonthdaysWeekday();
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
								else if(listCommande[t].indexOf("BYMONTHDAY")!=-1)
								{
									String[] numberDay = listCommande[t].substring(11).split(",");
									Monthdays.Day day=fabrique.createMonthdaysDay();
									for(int a=0;a<numberDay.length;a++)
									{
										day.getDay().add(Integer.parseInt(numberDay[a]));
									}
									day.getPeriod().add(tmpPeriod3);
									oRuntime.getMonthdays().getDayOrWeekday().add(day);
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
			runtime=false;
		}
		
		String complement="";
		while(lorder.containsKey(od.getTitle()+complement))
			 complement+="bis";
			
	
		lorder.put(od.getTitle()+complement, od);
		
		
		nbrDeOrder++;
		
		if(!saveAt.equals("00:00"))
		saveAt=new String("00:00");
	}

	
	public void treatFileOrderSource() {
		
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
		String chaine;
		int numLigne=1;
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
					else if(cell.toString().equals("O"))
					{
						contenuFichier=sheet.getRow(numLigne).getCell(30).toString();
						fichier=true;
						
					}
					

				}

			}
			
			numLigne++;
		}
		addBeginAndEndJobChain(jobchainEnCour);
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
				
			
				
				if (tmp == nbOrderParJobchain.get(jobch.getName())) {
					jobch = (JobChain) ejobchain.next();
					
					tmp = 0;
				}else
				{	
					Order ordTemp = (Order) eOrder.next();
				OutputStream os = new FileOutputStream(outPut + jobch.getName()
						+ "," + ordTemp.getTitle() + ".order.xml");
			
				marshaller.marshal(fabrique.createOrder(ordTemp), os);
				
			
				tmp++;
				}
			}
				

		}

		File di = new File(outPut);
		File fl[] = di.listFiles();

	
		return fl.length-1;
	}

	public void addBeginAndEndJobChain(String jobChainEnCour)
	{
		if(jobhelp.isJobChainComplex(jobchainEnCour))
		{   
			
			JobChain.JobChainNode jbcnTemp=fabrique.createJobChainJobChainNode();
			jbcnTemp.setState("00_Start");
			jbcnTemp.setJob("/sos/jitl/JobChainStart");
			jbcnTemp.setErrorState("!end_ERR");
			JobChain.JobChainNode jbcnTemp2 =(JobChainNode) jobchain.get(jobchainEnCour).getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().get(0);
			jbcnTemp.setNextState(jbcnTemp2.getState());
			jobchain.get(jobchainEnCour).getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(0, jbcnTemp);
			
			jbcnTemp=fabrique.createJobChainJobChainNode();
			jbcnTemp.setState("End");
			jbcnTemp.setJob("/sos/jitl/JobChainEnd");
			if(fichier)
			{
				jbcnTemp.setErrorState("!end_ERR");
		     jbcnTemp.setNextState("S_cleanfile");
		     jobchain.get(jobchainEnCour).getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(jbcnTemp);
		     
		     AddFileJobChain();
			}
			else
			{
				jbcnTemp.setErrorState("!end_ERR");
				jbcnTemp.setNextState("end_SUC_All");
				jobchain.get(jobchainEnCour).getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(jbcnTemp);	
			}
			
			
		}
		else if(fichier)
		{
			AddFileJobChain();
		}
			
	}
	
	public void AddFileJobChain()
	{
		JobChain.FileOrderSource file=fabrique.createJobChainFileOrderSource();
	     String[] split=contenuFichier.split("/");
	     String directory="t";
	     for(int j=0;j<split.length-1;j++)
	     {
	    	 if(j<split.length-2)
	    		 {
	    		 directory+=split[j]+"/";
	    		 }
	    	 else
	    	 {
	    		 directory+=split[j]; 
	    	 }
	     }
	     file.setDirectory(directory);
	     file.setRegex(split[split.length-1]);
	     jobchain.get(jobchainEnCour).getFileOrderSource().add(file);
	     JobChain.FileOrderSink deletFile=fabrique.createJobChainFileOrderSink();
	     deletFile.setState("S_cleanfile");
	     deletFile.setRemove("yes");
	     jobchain.get(jobchainEnCour).getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(deletFile);
	     fichier=false;
	     contenuFichier="";
	}
	
	public void ExcelCleaner(XSSFSheet sheet)
	{
		//couleur cellule modifier
		XSSFFont font = wb.createFont();
		font.setColor((short)45);
		CellStyle csCF = wb.createCellStyle();
		csCF.setFont(font);
		//Fin couleur cellule modifier
		Iterator<Row> rowIt=sheet.iterator();
		rowIt.next();
		Row row,rowPrec,rowSuiv;
		 row = sheet.getRow(1);
		 rowPrec=row ;
		 rowSuiv=sheet.getRow(2);
		int numLigne=1;
		boolean delete=false;
		// while(rowIt.hasNext())
		for(int p=2;p<=sheet.getLastRowNum();p++)	 
		{	
			
			 
			//Add order
			 if(!row.getCell(16).toString().isEmpty()&& !row.getCell(1).toString().isEmpty())
			 {
				
				 if(!rowSuiv.getCell(3).toString().equals("R")&&!rowSuiv.getCell(3).toString().equals("O"))
				 {
					 
					 copyRow(sheet,numLigne+1); 
					 
				 }
			 }
			//End add order
			 
			
			
			if(row.getCell(3).toString().equals("O"))
			{
				 row.getCell(30).setCellValue(row.getCell(30).toString().replace("//", "/"));
				 String correction=row.getCell(30).toString();
				 
					 
				String[] cheminEtRegex=correction.split("/");
				
				String regex=cheminEtRegex[cheminEtRegex.length-1];	
				StringTokenizer chaine = new StringTokenizer(regex,"?*",true);
				row.getCell(30).setCellValue("");
				String temps=chaine.nextToken();
				String tempsNext;
				for(int z=0;z<cheminEtRegex.length-1;z++)
				{
					row.getCell(30).setCellValue(row.getCell(30).toString()+cheminEtRegex[z]+"/");
				}
				
				while(chaine.hasMoreTokens())
				{
					tempsNext=chaine.nextToken();
					if(!temps.contains(".")&&(tempsNext.equals("*")||tempsNext.equals("?")))
					{
						
						row.getCell(30).setCellStyle(csCF);
						
						
						row.getCell(30).setCellValue(row.getCell(30).toString()+temps+"."+tempsNext);
					}
					else
					{
						row.getCell(30).setCellValue(row.getCell(30).toString()+temps+tempsNext);
					}
					temps=tempsNext;
					
				}
				
				
			}
			
			
			
			
			//Cleaning excel order
			if(row.getCell(3).toString().equals("R"))
			{
				
				if(row.getCell(16).toString().isEmpty())
				{
					
					if(!rowPrec.getCell(16).toString().isEmpty())
					{
						
						row.getCell(16).setCellValue(rowPrec.getCell(16).toString());
					}
					
					if(!rowSuiv.getCell(16).toString().isEmpty())
					{
						row.getCell(16).setCellValue(rowSuiv.getCell(16).toString());
					}
				}
				
				
			}
			//End cleaning excel order
			
			
			//Delete order
			if(row.getCell(3).toString().equals("O")&&rowPrec.getCell(3).toString().equals("R"))
			{
				sheet.removeRow(sheet.getRow(p-2));
				
				sheet.shiftRows(p-1, sheet.getLastRowNum(),-1);
				delete=true;
			}
			//End Delete order
			
			if(delete)
			{
				rowPrec=sheet.getRow(p-1);
					row = sheet.getRow(p);
				if(p+1<=sheet.getLastRowNum())
					rowSuiv = sheet.getRow(p+1);
				delete=false;
			}
			else
			{
				rowPrec=row;
				row=rowSuiv ;
				if(p+1<=sheet.getLastRowNum())
				rowSuiv = sheet.getRow(p+1);
			}
			
			
			
			numLigne++;
			
			
			
		}
	}
	
	public void copyRow(XSSFSheet worksheet, int destinationRowNum) {
		  // Get the source / new row
		  Row newRow = worksheet.getRow(destinationRowNum);
		 

		  // If the row exist in destination, push down all rows by 1 else create a new row
		  if (newRow != null) {
		    worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
		  } 
		   
		  worksheet.createRow(destinationRowNum);
		  Row row= worksheet.getRow(destinationRowNum);
		  
		
		  
				   for(int cn=0; cn<newRow.getLastCellNum(); cn++) {
					   
				        row.createCell(cn);
				        newRow.getCell(cn, Row.CREATE_NULL_AS_BLANK);
				      
				       if(cn==3)
				      {
				    	 row.getCell(cn).setCellValue("R");
				    	
				    	 
				      }
				       if(cn==28)
					      {
					    	 row.getCell(cn).setCellValue("FREQ=DAILY;INTERVAL=1");
					    	 
					      }
				       
				   }
				
		  
		}
	
	
	public void replaceNumber(XSSFSheet worksheet, int numbeToReplace,int replace)	
	{
		Iterator<Row> rowIt=worksheet.iterator();
		rowIt.next();
		Row row;
		while(rowIt.hasNext())
		{
			row=rowIt.next();
			if(row.getCell(2).getStringCellValue().equals(numbeToReplace))
			{
				row.getCell(2).setCellValue(replace);
			}
			
			if(row.getCell(11).getStringCellValue().indexOf(numbeToReplace)!=-1)
			{
			
				if(row.getCell(11).getStringCellValue().equals(numbeToReplace))
					{row.getCell(11).setCellValue(replace);
					}
				else{
					String[] listNum = row.getCell(11).getStringCellValue().split(",");
					row.getCell(11).setCellValue("");
					
					for(String st :listNum)
					{
						
						if(st.equals(String.valueOf(numbeToReplace)))
						{
							st=String.valueOf(replace);
						}
						
						if(row.getCell(11).getStringCellValue().isEmpty())
						{
							row.getCell(11).setCellValue(st);	
						}
						else
						{
							row.getCell(11).setCellValue(row.getCell(11).getStringCellValue()+","+st);	
						}
						
					}
					
					
				}
				
				
			}
		}
	}
	
	
	public static void main(String[] args) throws IOException, JAXBException {

		ExcelReader exrd = new ExcelReader(
				"C:/Users/puls/workspace2/SoS-JobScheduler/dashboard/src/test/ressource/Facile/ARIANE.xlsm",
				"D:/resultat/");
		// 1=job
		// 2=jobchain
		// 3=order
		// 12= job et jobchain
		// etc...
		
		if (exrd.treatExcelFile())
			
			{
			System.out.println("test");
			exrd.OutputTest(123);}
		else{
			System.out.println("nofichir");
		}

	}
}