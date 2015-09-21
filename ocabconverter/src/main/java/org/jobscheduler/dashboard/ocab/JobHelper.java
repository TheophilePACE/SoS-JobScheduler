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
 * that file help ExcelReader,
 * he check the next job for each job
 * he detect if it's a complicated case
 * if you want understand this code, you must have concept of jobScheduler
 * and take a look in ocab file (Exel file)
 */
package org.jobscheduler.dashboard.ocab;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.jobscheduler.dashboard.jobdefinition.xml.Job;
import org.jobscheduler.dashboard.jobdefinition.xml.Job.Monitor;
import org.jobscheduler.dashboard.jobdefinition.xml.JobChain;
import org.jobscheduler.dashboard.jobdefinition.xml.JobSettings;
import org.jobscheduler.dashboard.jobdefinition.xml.Lock;
import org.jobscheduler.dashboard.jobdefinition.xml.ObjectFactory;
import org.jobscheduler.dashboard.jobdefinition.xml.Order;
import org.jobscheduler.dashboard.jobdefinition.xml.Param;
import org.jobscheduler.dashboard.jobdefinition.xml.Params;
import org.jobscheduler.dashboard.jobdefinition.xml.RunTime;
import org.jobscheduler.dashboard.jobdefinition.xml.Script;
import org.jobscheduler.dashboard.jobdefinition.xml.Settings;
import org.w3c.dom.CDATASection;

public class JobHelper {


	Hashtable<String, String> nameNextJob;
	Iterator<Row> rowIterator;
	Iterator<Cell> cellIterator;
	Cell cell;
	/*
	 * count number split and synch
	 */
	int numbeSplit;
	int numbeSyn;
	XSSFSheet sheet;
	int ligne=1;
	Marshaller marshaller;
	String paramValue;
	LinkedHashMap<String,Boolean> jobChainComplex;
	Hashtable  <String , String > nextJobChain;
	Hashtable  <String , String > jobWithFiles=new Hashtable<String, String>();
	LinkedHashMap<String,String> splitAtBegening;
	String nameJobChain;
	Job jb;
	ObjectFactory fabrique ;
	boolean beginJobchain;
	Settings st;
	JobChain jbc;
	Order ord;
	boolean configFile=false;
	boolean complexe;
	int numberFile=10;
	String outPut;
	LinkedHashMap<String, Integer>  EndCmplex= new  LinkedHashMap<String, Integer>();
	String jobEndComplex="";
	Hashtable <String, String> addSynch=new Hashtable<String, String>();
	int indiceEndSplit=-1;

	/**
	 * name - AddSynch notify on job need synchronization before process
	 * 
	 * 
	 * @param String : name of the job
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */

	public boolean AddSynch(String st) {
		return addSynch.containsKey(st);
	}


	public JobHelper(XSSFSheet sheet,Marshaller marshaller)
	{
		this.sheet=sheet;
		rowIterator=sheet.iterator();
		rowIterator.next();
		nameNextJob = new Hashtable<String, String>();
		nextJobChain= new Hashtable<String, String>();
		numbeSplit=1;
		numbeSyn=1;
		this. marshaller= marshaller;
		fabrique = new ObjectFactory();
		beginJobchain=true;
		complexe=false;
		jobChainComplex=new LinkedHashMap<String,Boolean>();
		splitAtBegening=new LinkedHashMap<String,String>();
		paramValue="";
	}

	/**
	 * name - initialization, it's the main work
	 *        For each job we look the next values and save it,
	 *        we look if a job is a part of a chain simple or complicate                  
	 * 
	 * @param String : the output file path
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */
	public void initialization(String outPut) throws JAXBException, FileNotFoundException
	{this.outPut=outPut;
	Integer jid = 0;

	while (rowIterator.hasNext()) {

		Row row = rowIterator.next();//we start at the line number 2, the first line is for title in the excel file


		if(!row.getCell(5).getStringCellValue().isEmpty()) //for check jobchain name 
		{  
			if (complexe)
			{
				EndComplexCase("End");
				jobEndComplex="";
			} 
			EndCmplex=new LinkedHashMap<String, Integer>();

			if(!row.getCell(11).toString().isEmpty())
			{

				String followJobChain =getJobChaine(row.getCell(11).toString().substring(1));

				this.nextJobChain.put(followJobChain,row.getCell(5).getStringCellValue());
			}


			configFile=false;
			nameJobChain=row.getCell(5).getStringCellValue(); //for check jobchain name
			beginJobchain=true; //initialization for a new jobchain
			CheckSplitAtTheBegening();

		}


		if(!row.getCell(2).toString().isEmpty())	//for check the jid of the line
		{


			if(row.getCell(2).getCellType()==0)
			{
				jid=(int)row.getCell(2).getNumericCellValue();
			}
			else
			{
				jid=Integer.parseInt(row.getCell(2).getStringCellValue());
			}
		}


		if(row.getCell(3).toString().equals("N"))	//for check if it's a lock line
		{


			Lock lc=fabrique.createLock();
			lc.setMaxNonExclusive(new BigInteger(row.getCell(33).toString()));

			OutputStream os = new FileOutputStream(outPut+row.getCell(32).toString()+".lock.xml");
			marshaller.marshal(fabrique.createLock(lc), os);

		}

		cellIterator=row.cellIterator();

		cell=coloneExcelSuivant(7);	//we go to the column 7, the job name (if it's a job)






		if(!cell.toString().isEmpty()) //if it's not empty we treat a job
		{
			String temp=getL1File();

			if(isEndComplex(String.valueOf(jid))) //here, we look if this job is the end of a complex case
			{//end of a complex case is always a synchronization 
				nameNextJob.put(cell.toString(),"Sync_"+numbeSyn);

				if(!temp.equals("NogetL1File"))
				{
					jobWithFiles.put(cell.toString(), temp);
				}


			}
			else
			{
				//the last job in parallel execution, we can up the number of synchronization and split
				if (jobEndComplex.equals(String.valueOf(jid)))
				{
					EndComplexCase(cell.toString());
					jobEndComplex="";
				} 

				//here we find a split point, this is the beginning of a complex case 
				String endSplit=splitPoint(String.valueOf(jid));
				if(!endSplit.equals("-1")&&indiceEndSplit!=-1)
				{


					String[] value=endSplit.split(",");
					if(indiceEndSplit!=-2)
						jobEndComplex=sheet.getRow(indiceEndSplit).getCell(2).toString();

					indiceEndSplit=-1;

					addSynch.put(value[value.length-1],"Sync_"+numbeSyn);

					for(int z=0;z<value.length;z++)
					{
						EndCmplex.put(value[z],numbeSyn);
					}

					if (!jobChainComplex.containsKey(nameJobChain))
					{
						jobChainComplex.put(nameJobChain,true);// for know if a jochain contain complex case,
						//then ExcelReader Will add for the complex jobchain a job start and a job end 
					}
					complexe=true;

				}else{
					//a normal case, just put the next job in a hashtable


					if(temp.equals("NogetL1File"))// if there is no file on a job
					{
						if(!nameNextJob.containsKey(cell.toString()))
							nameNextJob.put(cell.toString(),getL1(7,1));

					}
					else{

						jobWithFiles.put(cell.toString(), temp);
						nameNextJob.put(cell.toString(),getL1(7,2)); //we already know the next line is a file
					}
				}

			}

		}


		ligne++;
	}

	if (complexe)// for end complece case
	{
		EndComplexCase("End");
		jobEndComplex="";
	} 


	}
	
	/**
	 * name - CheckSplitAtTheBegening : specially for check if there is parallel job at the beginning                  
	 * 
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */
	
	public void CheckSplitAtTheBegening()
	{


		String jid="";
		//here we find a split point, this is the beginning of a complex case 
		String endSplit=splitBegeningPoint(jid);
		if(!endSplit.equals("-1")&&indiceEndSplit!=-1)
		{


			String[] value=endSplit.split(",");
			if(indiceEndSplit!=-2)
				jobEndComplex=sheet.getRow(indiceEndSplit).getCell(2).toString();

			indiceEndSplit=-1;

			addSynch.put(value[value.length-1],"Sync_"+numbeSyn);

			for(int z=0;z<value.length;z++)
			{
				EndCmplex.put(value[z],numbeSyn);
			}

			if (!jobChainComplex.containsKey(nameJobChain))
			{
				jobChainComplex.put(nameJobChain,true);// now if a jochain contain complex case,
				//We add for the complex jobchain a job start and a job end 
			}
			complexe=true;

		}


	}
	
	/**
	 * name - CheckSplitAtTheBegening : specially for check if there is parallel job at the beginning                  
	 * @param String : name of the job
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */
	
	public String getJobWithFiles(String st) {
		if(jobWithFiles.containsKey(st))
			return jobWithFiles.get(st);

		return "nofiles";
	}

	/**
	 * name - splitPoint :  for check if the job is the beginning of a parallel execution              
	 * @param String : name of the job
	 * @see   splitBegeningPoint (for the comments)
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */
	
	public String splitPoint(String Job)
	{
		String returnn="";
		boolean boucle,endSplit=false;
		int numLigne=ligne+1;
		boolean happyEnd=false;

		ArrayList<Integer> parallelJob=new ArrayList<Integer> ();

		if(valableIndice(numLigne))
		{
			boucle=sheet.getRow(numLigne).getCell(1).toString().isEmpty();	

			while(boucle&&!endSplit)
			{



				if(sheet.getRow(numLigne).getCell(11).toString().equals(Job))
					parallelJob.add(new Integer(Integer.valueOf(numLigne)));

				if(!endSplit&&parallelJob.size()>1)
				{
					if(sheet.getRow(numLigne).getCell(11).toString().contains(","))
					{
						endSplit=true;
						happyEnd=true;
						indiceEndSplit=numLigne;
					}
				}


				numLigne++;
				if(valableIndice(numLigne))
				{
					boucle=sheet.getRow(numLigne).getCell(1).toString().isEmpty();		
				}
				else
				{
					boucle=false;
				}

			}


		}

		if(parallelJob.size()>1)
		{
			paramValue="";
			nameNextJob.put(cell.toString(),"Split_"+numbeSplit);
			Job jb=fabrique.createJob();
			jb.setOrder("yes");
			jb.setStopOnError("no");
			jb.setTitle("EndSpleet"+numbeSplit);
			JobSettings jbs=fabrique.createJobSettings();
			jb.setSettings(jbs);
			org.jobscheduler.dashboard.jobdefinition.xml.Script src=fabrique.createScript();
			src.setJavaClass("com.sos.jitl.sync.JobSchedulerSynchronizeJobChainsJSAdapterClass");
			src.setJavaClassPath("");
			src.setLanguage("java");
			jb.setScript(src);
			RunTime rt=fabrique.createRunTime();
			jb.setRunTime(rt);
			OutputStream os;



			if(happyEnd){

				returnn=sheet.getRow(indiceEndSplit).getCell(11).toString();
			}
			else
			{
				for(int j=0;j<parallelJob.size();j++)
				{

					if(returnn.isEmpty())
						returnn=ResolvLink(parallelJob.get(j));
					else
						returnn+=","+ResolvLink(parallelJob.get(j));	

				}
				indiceEndSplit=-2;
			}

			for(int j=0;j<parallelJob.size();j++)
			{

				if(paramValue.isEmpty())
				{
					if(haveNextIndice(parallelJob.get(j)))
					{
						if(sheet.getRow(parallelJob.get(j)+1).getCell(3).toString().equals("O"))
							paramValue=sheet.getRow(parallelJob.get(j)).getCell(6).toString()+"_WaitFiles"; 
						else
							paramValue=sheet.getRow(parallelJob.get(j)).getCell(6).toString();

					}
					else
					{
						paramValue=sheet.getRow(parallelJob.get(j)).getCell(6).toString();
					}

				}
				else
				{
					if(haveNextIndice(parallelJob.get(j)))
					{
						if(sheet.getRow(parallelJob.get(j)+1).getCell(3).toString().equals("O"))
							paramValue=paramValue+";"+sheet.getRow(parallelJob.get(j)).getCell(6).toString()+"_WaitFiles"; 
						else
							paramValue=paramValue+";"+sheet.getRow(parallelJob.get(j)).getCell(6).toString();
					}
					else
					{
						paramValue=paramValue+";"+sheet.getRow(parallelJob.get(j)).getCell(6).toString();	
					}

				}


			}


			createConfigFile();





			try {
				os = new FileOutputStream(outPut+"Sync_"+numbeSyn+".job.xml");
				marshaller.marshal(jb, os);

				os = new FileOutputStream(outPut+nameJobChain+".config.xml");
				marshaller.marshal(st, os);
			} catch (FileNotFoundException | JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return returnn;
		}




		return "-1";

	}

	/**
	 * name - splitBegeningPoint :  for check if the job is the beginning of a parallel execution 
	 * Specially for the top of a jobchain, very similar to function "splitPoint", 
	 * i think we can merge the two function (but there is some work!)
	 *          
	 * @param String : name of the job
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */
	
	public String splitBegeningPoint(String Job)
	{
		String returnn="";
		boolean boucle,endSplit=false;
		int numLigne=ligne;
		boolean happyEnd=false; // if the complex case end with a split in the excel then yes, else we need to determine where is the end 
		int firstJob=-1;
		boolean noTime=true;

		ArrayList<Integer> parallelJob=new ArrayList<Integer> ();

		if(haveNextIndice(numLigne))
		{
			while(haveNextIndice(numLigne)&& sheet.getRow(numLigne).getCell(2).toString().isEmpty()) // go on the first job (line)
			{
				numLigne++;
				firstJob=numLigne;

			}

			boucle=sheet.getRow(numLigne).getCell(1).toString().isEmpty(); //is a jobchain ? if empty, no  

			while(boucle&&!endSplit)
			{

				if(!sheet.getRow(numLigne).getCell(16).toString().isEmpty())
					noTime=false;


				if(sheet.getRow(numLigne).getCell(11).toString().equals(Job) && !sheet.getRow(numLigne).getCell(2).toString().isEmpty() )
				{
					parallelJob.add(new Integer(Integer.valueOf(numLigne))); // add a job in parallel 

				}

				if(!endSplit&&parallelJob.size()>1)
				{
					if(sheet.getRow(numLigne).getCell(11).toString().contains(","))
					{
						endSplit=true;
						happyEnd=true;
						indiceEndSplit=numLigne;
					}
				}



				if(haveNextIndice(numLigne))
				{
					boucle=sheet.getRow(numLigne+1).getCell(1).toString().isEmpty();
					if(!boucle)
						indiceEndSplit=numLigne;
				}
				else
				{
					boucle=false;
					indiceEndSplit=numLigne;
				}
				numLigne++;
			}


		}

		if(parallelJob.size()>1) // if there is more than on job in parallel
		{
			paramValue="";

			String tempo=";";
			for(int h=0;h<parallelJob.size();h++)
			{
				
				tempo+=parallelJob.get(h)+";";
			}

			//tempo is the line of the job in parallel, it's a information for excelReader
			//see ExcelReader :treatJobChainOption : line 453 (may have changed) 
			splitAtBegening.put(nameJobChain, "Split_"+numbeSplit+tempo);

			if(firstJob==-1)
				throw new Error("Problème dans le fichier Excel, pas de job dans une chaine");

			Job jb=fabrique.createJob();
			jb.setOrder("yes");
			jb.setStopOnError("no");
			jb.setTitle("EndSpleet"+numbeSplit);
			JobSettings jbs=fabrique.createJobSettings();
			jb.setSettings(jbs);
			org.jobscheduler.dashboard.jobdefinition.xml.Script src=fabrique.createScript();
			src.setJavaClass("com.sos.jitl.sync.JobSchedulerSynchronizeJobChainsJSAdapterClass");
			src.setJavaClassPath("");
			src.setLanguage("java");
			jb.setScript(src);
			RunTime rt=fabrique.createRunTime();
			jb.setRunTime(rt);
			OutputStream os;



			if(happyEnd)// there is a end for the split
			{
				returnn=sheet.getRow(indiceEndSplit).getCell(11).toString();
			}



			for(int j=0;j<parallelJob.size();j++)
			{





				if(paramValue.isEmpty())
				{
					if(!happyEnd)
					{
						returnn=ResolvLink(parallelJob.get(j));
					}

					if(haveNextIndice(parallelJob.get(j)))
					{
						if(sheet.getRow(parallelJob.get(j)+1).getCell(3).toString().equals("O"))
							paramValue=sheet.getRow(parallelJob.get(j)).getCell(6).toString()+"_WaitFiles"; 
						else
							paramValue=sheet.getRow(parallelJob.get(j)).getCell(6).toString();

					}
					else
					{
						paramValue=sheet.getRow(parallelJob.get(j)).getCell(6).toString();
					}


				}
				else
				{
					if(!happyEnd)
					{
						// there is no end for the split, we need resolve the dependence for know where is the end
						returnn=returnn+","+ResolvLink(parallelJob.get(j));
					}

					if(haveNextIndice(parallelJob.get(j)))
					{   // if the job have a file, then execute in parallel the job wait file
						if(sheet.getRow(parallelJob.get(j)+1).getCell(3).toString().equals("O")) 
							paramValue=paramValue+";"+sheet.getRow(parallelJob.get(j)).getCell(6).toString()+"_WaitFiles"; 
						else
							paramValue=paramValue+";"+sheet.getRow(parallelJob.get(j)).getCell(6).toString();
					}
					else
					{
						paramValue=paramValue+";"+sheet.getRow(parallelJob.get(j)).getCell(6).toString();	
					}
				}


			}

            //create the config file
			createConfigFile();





			try {
				os = new FileOutputStream(outPut+"Sync_"+numbeSyn+".job.xml");
				marshaller.marshal(jb, os);

				os = new FileOutputStream(outPut+nameJobChain+".config.xml");
				marshaller.marshal(st, os);
			} catch (FileNotFoundException | JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return returnn;
		}




		return "-1";

	}

	
	/**
	 * name - splitBegeningPoint :  resolve dependence of a job (the last son after him 3002->3003->3004[end] return 3004)
	 *          
	 * @param int : line of the job to treat 
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */
	
	public String ResolvLink(int line)
	{
		int i=1;
		boolean boucle;

		if(valableIndice(line+1))
		{
			if(!sheet.getRow(line+1).getCell(3).toString().isEmpty()) //les fichiers sur job 
			{

				Boolean loop=true;


				while(loop)
				{i++;

				if(valableIndice(line+i))
				{

					loop=!sheet.getRow(line+i).getCell(3).toString().isEmpty();

				}
				else
				{
					return sheet.getRow(line).getCell(2).toString();
				}



				}

				if(sheet.getRow(line).getCell(2).toString().equals(sheet.getRow(line+i).getCell(11).toString()))
					return  ResolvLink(line+i);

				return sheet.getRow(line).getCell(2).toString();

			}
			else if(sheet.getRow(line).getCell(2).toString().equals(sheet.getRow(line+1).getCell(11).toString()))
				return  ResolvLink(line+1);

			return sheet.getRow(line).getCell(2).toString();
		}

		return sheet.getRow(line).getCell(2).toString();
	}

	public boolean haveNextIndice(int i)
	{
		if(sheet.getLastRowNum()>=i+1)
			return true;
		return false;

	}

	public boolean valableIndice(int i)
	{
		if(sheet.getLastRowNum()>=i)
			return true;
		return false;

	}
	
	
	public void EndComplexCase(String nextSynchro)
	{

		nameNextJob.put("Split_"+numbeSplit,"Sync_"+numbeSyn);//next of a split is always a synchronization
		nameNextJob.put("Sync_"+numbeSyn,nextSynchro);// next of the synchronization ( a job)
		complexe=false;//reset

		numbeSyn++;//for the next synchro
		numbeSplit++;//for the next split
	}

	/**
	 * name - getL1 get in next line, the column enter in parameter
	 *                           
	 * 
	 * @param int : column number
	 * @return String :  case specified in parameter of the next line
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */

	public String getL1(int numeroColone,int indice)
	{
		Row rowL1;
		Iterator<Cell> tempCellIteratorL1;
		Cell cellTemp;


		if(sheet.getLastRowNum()>=ligne+indice)
		{

			rowL1=sheet.getRow(ligne+indice);

			tempCellIteratorL1=rowL1.iterator();
			cellTemp=coloneExcelSuivant(tempCellIteratorL1,numeroColone);

			if (!cellTemp.toString().isEmpty()&&cellTemp.toString().indexOf("s")==-1)
			{

				if(cellTemp.getCellType()==0)
				{
					return String.valueOf((int)cellTemp.getNumericCellValue());
				}
				else
				{


					return cellTemp.toString();
				}

			}else if(rowL1.getCell(3).toString().equals("O")||rowL1.getCell(3).toString().equals("N"))
			{
				return getL1(numeroColone,indice+1);

			}



		}

		return "NogetL1";
	}
	
	/**
	 * name - getL1File get in next line, if the job have a file, or not
	 *                           
	 * 
	 * @param int : column number
	 * @return String :  case specified in parameter of the next line
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */
	public String getL1File()
	{
		if(haveNextIndice(ligne))
		{
			Row rowL1=sheet.getRow(ligne+1);
			if(rowL1.getCell(3).toString().equals("O"))
			{
				String lineFile=String.valueOf(ligne+1);
				boolean noEndFileEndChain=false;
				int add=2;
				if(sheet.getLastRowNum()>=ligne+add)
					noEndFileEndChain=true;

				while(noEndFileEndChain)
				{

					if(sheet.getRow(ligne+add).getCell(3).toString().equals("O"))
					{
						lineFile+=";"+(ligne+add);
						add++;

						if(sheet.getLastRowNum()<ligne+add)
							noEndFileEndChain=false;

					}
					else
					{
						noEndFileEndChain=false;
					}

				}
			

				return lineFile;
			}
		}
		
		return "NogetL1File";
	}

	public String cdata(String st) {
		return "<![CDATA[" + st + "]]>"; // for add CDATA in Xml file
	}

	/**
	 * name - getL2 get in two next line, the column enter in parameter
	 *                           
	 * 
	 * @param int : column numbe
	 * @return String :  case specified in parameter of the two next line
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */

	public String getL2(int numeroColone)
	{
		Row rowL2;
		Iterator<Cell> tempCellIteratorL2;
		Cell cellTemp;

		if(sheet.getLastRowNum()>=ligne+2)
		{
			rowL2=sheet.getRow(ligne+2);

			tempCellIteratorL2=rowL2.iterator();
			cellTemp=coloneExcelSuivant(tempCellIteratorL2,numeroColone);
			if (!cellTemp.toString().isEmpty())
			{
				if(cellTemp.getCellType()==0)
				{

					return String.valueOf((int)cellTemp.getNumericCellValue());
				}
				else
				{


					return cellTemp.toString();
				}
			}

		}

		return "NogetL2";
	}

	/**
	 * name - isComplex the current line is complex or not (complex = two or more branch)
	 *                           
	 * @return boolean :  if complex then true else false 
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */
	public boolean isEndComplex(String leJob)
	{
		return EndCmplex.containsKey(leJob);
	}



	public Cell coloneExcelSuivant(int nbColSuivant) {

		for(int i=0;i<nbColSuivant-1;i++)
		{	
			if (cellIterator.hasNext()) {
				cellIterator.next();

			}
		}
		return cellIterator.next();
	}

	public boolean nextExcelLine() {
		if (rowIterator.hasNext()) {
			rowIterator.next();
			return true;
		}
		return false;
	}

	/*//for copy a file
 
	public static boolean copyFile(String source, String dest){
		FileChannel in = null; // canal d'entrée
		FileChannel out = null; // canal de sortie

		try {
			// Init
			in = new FileInputStream(source).getChannel();
			out = new FileOutputStream(dest).getChannel();

			// Copie depuis le in vers le out
			in.transferTo(0, in.size(), out);
		} catch (Exception e) {

			e.printStackTrace(); // n'importe quelle exception


		} finally { // finalement on ferme
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {}
			}
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {}
			}
		}
		return true;
	}
*/
	
	public Cell coloneExcelSuivant(Iterator<Cell> tempCellIteratorL1,int nbreCell) {


		for(int i=0;i<nbreCell-1;i++)
		{
			tempCellIteratorL1.next().getStringCellValue();

		}
		return tempCellIteratorL1.next(); 
	}

	/**
	 * name - getNextJob : use by Excelreader for know who is the next job and add it in the jobchain 
	 *   
	 * @param String : name of the job                          
	 * @return String :  name of the jobchain
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */
	public String getNextJob(String job)
	{
		return nameNextJob.get(job);
	}

	/**
	 * name - isJobChainComplex : use by Excelreader for know if a jobchain have parallel job
	 *   
	 * @param String : name of the jobchain                          
	 * @return boolean 
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */
	
	public boolean isJobChainComplex(String jobchain)
	{
		if(jobChainComplex.containsKey(jobchain))
		{return true;}

		return false;
	}

	/**
	 * name - jobChainNext : use by Excelreader for know if a jobchain execute a other jobchain 
	 *   
	 * @param String : name of the jobchain                          
	 * @return String : name of the next jobchain
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */
	public String jobChainNext(String nameJobchain)
	{

		if(nextJobChain.containsKey(nameJobchain))
		{
			return nextJobChain.get(nameJobchain);
		}
		return "noJobchainNext";
	}

	public String splitAtBegening(String chaine)
	{
		if(!splitAtBegening.containsKey(chaine))
			return "No";

		return splitAtBegening.get(chaine);
	}
	
	/**
	 * name -  getJobChaine : return the jobchain of a job
	 *   
	 * @param String : jid of the job                          
	 * @return String : name of the jobchain 
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */
	public String getJobChaine(String JID)
	{

		for(int t=1;t<sheet.getLastRowNum();t++)	 
		{	
			if(sheet.getRow(t).getCell(1).toString().equals(JID)&&!sheet.getRow(t).getCell(1).toString().isEmpty())
				return sheet.getRow(t).getCell(5).toString();
		}

		return "NoJobchain";
	}
	
	/**
	 * name - createConfigFile create a config job for a split
	 * 
	 * @author jean-vincent
	 * @date 20/05/2015
	 * @note
	 */
	public void createConfigFile()
	{
		configFile=true;
		if(beginJobchain)
		{

			jb=fabrique.createJob();
			st=fabrique.createSettings();
			jbc=fabrique.createJobChain();
			jbc.setName(nameJobChain);
			ord=fabrique.createOrder();
			Order.Process proc=fabrique.createOrderProcess();
			Params params=fabrique.createParams();
			Param param=fabrique.createParam();
			param.setName("state_names");
			param.setValue(paramValue);
			params.getParamOrCopyParamsOrInclude().add(param);
			param=fabrique.createParam();
			param.setName("sync_state_name");
			param.setValue("Sync_"+numbeSyn);
			params.getParamOrCopyParamsOrInclude().add(param);
			proc.setState("Split_"+numbeSplit);
			proc.setParams(params);
			ord.getProcess().add(proc);
			jbc.setOrder(ord);
			st.setJobChain(jbc);
			beginJobchain=false;
		}
		else //the job already exist we add a process
		{
			Order.Process proc=fabrique.createOrderProcess();
			Params params=fabrique.createParams();
			Param param=fabrique.createParam();
			param.setName("state_names");
			param.setValue(paramValue);
			params.getParamOrCopyParamsOrInclude().add(param);
			param=fabrique.createParam();
			param.setName("sync_state_name");
			param.setValue("Sync_"+numbeSyn);
			params.getParamOrCopyParamsOrInclude().add(param);
			proc.setState("Split_"+numbeSplit);
			proc.setParams(params);
			ord.getProcess().add(proc);



		}
	}
}
