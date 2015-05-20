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

import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;


public class ExcelReader {
	//***************objets système****************//
	File file;;
	FileInputStream fis;
	boolean JobChaine=false;
	JAXBContext jc ;
	Marshaller marshaller ;
	ObjectFactory fabrique = new ObjectFactory();
	XSSFWorkbook wb;
	XSSFSheet sheet;
	Cell cell;
	String chaine;
	String outPut;
	//**********Pour nommer les order en fonction des jobchain*************// 
	
	String jobchainEnCour;
	int nbrDeOrder;
	
	//Existance ou non d'un runtime/params (fonctionnalité job scheduler)
	boolean runtime=false;
	boolean params=false;

	//***************les listes****************//
	
	//ligne titre du excel  qui est parsé
	ArrayList<String> LigneTitre = new ArrayList<String>(); 
	Hashtable job = new Hashtable();	
	Hashtable<String,JobChain>jobchain = new Hashtable<String,JobChain>();
	Hashtable<String,Integer>NbOrderParJobchain = new Hashtable<String,Integer>();//à commenter...
	Hashtable<String,Job>Ljob = new Hashtable<String,Job>();
	Hashtable<String,Order>Lorder = new Hashtable<String,Order>();
	Iterator<Row> rowIterator;
	Iterator<Cell> cellFirstLigne;
	Iterator<Cell> cellIterator;


	//***************Les objets générés à partir du schéma****************//
	JobChain jbc = null;
	JobChain.JobChainNode jbcn;
	Job jb;
	Script scrpt;
	Order od;
	Params oParams;
	RunTime oRuntime;

	//***************Constructeur avec chemin du fichier excel, et chemin de sortie des fichier xml  en paramètre)****************//
	public ExcelReader(String EmplacementFichierExcel,String output) throws JAXBException, IOException {
		super();
		this.outPut=output;
		file = new File("D:/excel/KARMA_QAL_1.4_FULL.xlsx");
		jc = JAXBContext.newInstance("org.jobscheduler.dashboard.jobdefinition.xml");
		marshaller = jc.createMarshaller();
		marshaller.setProperty("jaxb.encoding", "ISO-8859-1");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty("com.sun.xml.internal.bind.characterEscapeHandler",
				new CharacterEscapeHandler() {
			@Override
			public void escape(char[] ch, int start, int length,
					boolean isAttVal, Writer writer)
							throws IOException {
				writer.write(ch, start, length);
			}
		});


		fabrique = new ObjectFactory();
		fis = new FileInputStream(file);
		wb = new XSSFWorkbook(fis);
		sheet = wb.getSheetAt(3);
		
		//Iterate through each rows one by one
		rowIterator = sheet.iterator();
		
		//Pour récupérer  la premiere ligne du fichier excel
		//et connaitre le titre de chaque colonne
		cellFirstLigne =sheet.getRow(0).cellIterator();
		jobchainEnCour=null;
		nbrDeOrder=0;

	}



	public String CDATA(String st)
	{
		return "<![CDATA["+st+"]]>";
	}



	//Copier dans une Arraylist le nom de chaque colonne
	//pour savoir sur quel colonne on travail
	public void CopieLigneTitre()
	{
		while(cellFirstLigne.hasNext())
		{
			LigneTitre.add(cellFirstLigne.next().toString());
		}
	}

	//Passer à la ligne exel suivante
	public boolean LigneExcelSuivant()
	{
		if(rowIterator.hasNext())
		{
			rowIterator.next();
			return true;
		}
		return false;
	}

	//Passer à la colone exel suivante
	public Cell ColoneExcelSuivant()
	{
		if(cellIterator.hasNext())
		{
			return cellIterator.next();

		}
		return null;
	}

	
	public void InitialisationJobChain()
	{
		
		//on va directement  à la colone 4
		ColoneExcelSuivant();   
		ColoneExcelSuivant();
		ColoneExcelSuivant();
		cell=ColoneExcelSuivant();

		System.out.println("Affichage du job chaine SID numéro:"+chaine+", "+cell.toString());		
		jbc=fabrique.createJobChain();
		jbc.setVisible("yes");
		jbc.setName(cell.toString());
		jobchain.put(cell.toString(),jbc);
		//à commenter..
		if(jobchainEnCour!=null)
		{
			NbOrderParJobchain.put(cell.toString(), nbrDeOrder);
			nbrDeOrder=0;
		}
		jobchainEnCour=cell.toString();
		//boucle à ajouter pour exploiter les autres options 
	}

	//Ajout des fonctionnalités
	public void TraiterLesOptionDunJob(int i)
	{
		System.out.print(LigneTitre.get(i)+":"+cell.toString() + ", ");

		switch (LigneTitre.get(i).toString()) {
		
		case "job":

			jb.setTitle(cell.toString());
			jbcn.setJob(cell.toString());
			jbcn.setState(cell.toString());
			break;

		case "scriptname": 


			scrpt.getContent().add(CDATA(cell.toString()));
			jb.setScript(scrpt);
			break;

		case "recovery_option":

			if(cell.toString().equals("stop"))
			{
				jbcn.setErrorState("!end_ERR");
			}
			else
			{
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


	public void TraiterUneLigneJob()
	{
		jbcn=fabrique.createJobChainJobChainNode();
		jb=fabrique.createJob();
		jb.setOrder("yes");
		scrpt=fabrique.createScript();
		scrpt.setLanguage("shell");

		int i=2;
		do     
		{

			if(i>2)cell=ColoneExcelSuivant();

			// On traite une colone de la case excel
			//il sagit d'un job	
			if(!cell.toString().isEmpty())
			{
				TraiterLesOptionDunJob(i);
			}

			i++;

		}while (cellIterator.hasNext());

		jbc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(jbcn);
		Ljob.put(jb.getTitle(),jb);
		System.out.println("");
	}

	public void TraiterLesOptionDunOrder(int i)
	{
		if(i>3)cell=ColoneExcelSuivant();


		if(!cell.toString().equals(""))
		{
			System.out.print(LigneTitre.get(i)+":"+cell.toString() + ", ");

			switch (LigneTitre.get(i).toString()) {

			case "runcycle":

				od.setTitle(cell.toString());

				break;

			case "at": 

				Weekdays TmpWeek=fabrique.createWeekdays();
				Weekdays.Day TmpDay=fabrique.createWeekdaysDay();
				Period TmpPeriod =fabrique.createPeriod();

				TmpPeriod.setSingleStart(cell.toString());
				TmpDay.getPeriod().add(TmpPeriod);
				TmpWeek.getDay().add(TmpDay);
				oRuntime.setWeekdays(TmpWeek);
				runtime=true;





				break;


			case "ical":
				if(oRuntime.getWeekdays()!=null)
				{     
					if (cell.toString().equals("FREQ=DAILY;INTERVAL=1"))
					{
						oRuntime.getWeekdays().getDay().get(0).getDay().add("1 2 3 4 5 6 7");
					}
				}
				else
				{
					Weekdays TmpWeek2=fabrique.createWeekdays();
					Weekdays.Day TmpDay2=fabrique.createWeekdaysDay();
					Period TmpPeriod2 =fabrique.createPeriod();

					TmpPeriod2.setSingleStart("00:00");
					TmpDay2.getPeriod().add(TmpPeriod2);
					TmpDay2.getDay().add("1 2 3 4 5 6 7");
					TmpWeek2.getDay().add(TmpDay2);
					oRuntime.setWeekdays(TmpWeek2);
					runtime=true;


				}
				break;

			default: 
				break;
			}

		}

	}

	public void TraiterUneLigneOrder()
	{
		oRuntime=fabrique.createRunTime();
		oParams=fabrique.createParams();
		od=fabrique.createOrder();




		int i=3;

		do     
		{
			TraiterLesOptionDunOrder(i);
			i++;

		}while (cellIterator.hasNext());

		if(runtime==true)
		{
			od.setRunTime(oRuntime);
		}

		Lorder.put(od.getTitle(),od);
		nbrDeOrder++;

	}

public void AddEndErrorEndSucsses()
{
	Enumeration ejobchain=jobchain.elements();
	while(ejobchain.hasMoreElements())

	{
		JobChain jtemp=(JobChain) ejobchain.nextElement();
		JobChain.JobChainNode temp=fabrique.createJobChainJobChainNode();
		temp.setState("end_SUC_All");
		jtemp.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(temp);
		temp=fabrique.createJobChainJobChainNode();
		temp.setState("!end_ERR");
		jtemp.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(temp);
		
			
	} 
}
	public boolean TrainterLeFichierExcel() throws IOException
	{
		CopieLigneTitre();
		LigneExcelSuivant();

		while (rowIterator.hasNext())
		{
			//Récupération d'une ligne dans la liste des lignes
			Row row = rowIterator.next();
			//récupération de la liste des colones
			cellIterator = row.cellIterator();

			//Je vais directement à la deuxieme colone 
			ColoneExcelSuivant();
			cell = ColoneExcelSuivant(); 
			chaine=cell.toString();

			//On vérifie à la deuxieme case (le SID)
			//si SID existant il faut créer un jobchain
			if (!chaine.isEmpty())
			{
				InitialisationJobChain();

			}
			else    
			{
				//si SID iniexistant on regarde le JID
				//si JID existant il faut créer un job
				//et compléter le jobchain
				cell = ColoneExcelSuivant(); 

				if(!cell.toString().isEmpty())
				{	
					//on va traiter une ligne entiere
					// qui contiendra la description d'un job
					TraiterUneLigneJob();
				}
				else
				{
					//si JID iniexistant on regarde le DEP
					//si DEP existant et égale à "R" il faut créer un order
					cell = ColoneExcelSuivant();

					if(cell.toString().equals("R"))
					{
						TraiterUneLigneOrder();
					}

				}


			}	

		}
		AddEndErrorEndSucsses();
		fis.close();
		return true;
	}

	
	//************FonctionPourTesterLeProgramme*****************//
	
	//lancement permet de sélectionner le type de fichier que l'on veux générer
	//un job, un jobchain, un order, les trois en meme temps etc..
	
	public void OutputTest(int lancement) throws FileNotFoundException, JAXBException
	{
		Enumeration ejobchain=jobchain.elements();
		Enumeration eLjob=Ljob.elements();
		Enumeration eOrder=Lorder.elements();
		int i=0;

		if(lancement==1||lancement==12||lancement==123||lancement==13)
		{Job job;
			while(eLjob.hasMoreElements())

			{
				job=(Job) eLjob.nextElement();
				OutputStream os = new FileOutputStream(outPut+job.getTitle()+".xml");

			marshaller.marshal(job,os);
			i++;
			}

		}
		if(lancement==2||lancement==12||lancement==123||lancement==23)
		{ JobChain jobch;
			while(ejobchain.hasMoreElements())

			{
				jobch=(JobChain) ejobchain.nextElement();
				
				OutputStream os = new FileOutputStream(outPut+jobch.getName()+".xml" );
				marshaller.marshal(jobch,os);
				i++;	
			} 

		}
//à commenter d'urgence...
		if(lancement==3||lancement==13||lancement==123||lancement==23)
		{ 
			int tmp=0;
			JobChain jobch;
			ejobchain=jobchain.elements();
			jobch=(JobChain) ejobchain.nextElement();
			while(eOrder.hasMoreElements())
			{
				Order ordTemp=(Order) eOrder.nextElement();
				
				
				if(tmp==NbOrderParJobchain.get(jobch.getName()))
				{
					jobch=(JobChain) ejobchain.nextElement();
					tmp=0;
				}
				OutputStream os = new FileOutputStream(outPut +jobch.getName()+",order.xml" );
				marshaller.marshal(fabrique.createOrder(ordTemp),os);
				i++;	
				tmp++;
			}

		}
	}
	public static void main(String[] args) throws IOException, JAXBException {

		ExcelReader exrd= new ExcelReader("D:/excel/KARMA_QAL_1.4_FULL.xlsx","D:/resultat/");
		//1=job
		//2=jobchain
		//3=order
		//12= job et jobchain
		//etc...
		if(exrd.TrainterLeFichierExcel()) exrd.OutputTest(123);


	}
}