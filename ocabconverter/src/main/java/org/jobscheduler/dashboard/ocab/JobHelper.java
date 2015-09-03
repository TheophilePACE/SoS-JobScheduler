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

		cell=coloneExcelSuivant(7);	//we go to the column 7, the job name (of the current job)






		if(!cell.toString().isEmpty()) //if it's not empty we treat a job
		{

			if(isEndComplex(String.valueOf(jid))) //here, we look if this job is the end of a complex case
			{//end of a complex case is always a synchronization 
				nameNextJob.put(cell.toString(),"Sync_"+numbeSyn);




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
						jobChainComplex.put(nameJobChain,true);//For now if a jochain contain complex case,
						//We add for the complex jobchain a job start and a job end 
					}
					complexe=true;

				}else{
					//a normal case, just put the next job in a hashtable
					String temp=getL1File();
					if(temp.equals("NogetL1File"))
					{nameNextJob.put(cell.toString(),getL1(7,1));

					}
					else{
						jobWithFiles.put(cell.toString(), temp);
						nameNextJob.put(cell.toString(),getL1(7,2)); //on s	it deja que le prochain est un fichier
					}
				}

			}

		}


		ligne++;
	}

	if (complexe)
	{
		EndComplexCase("End");
		jobEndComplex="";
	} 


	}
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
				jobChainComplex.put(nameJobChain,true);//For now if a jochain contain complex case,
				//We add for the complex jobchain a job start and a job end 
			}
			complexe=true;

		}
   
	
}
	public String getJobWithFiles(String st) {
		if(jobWithFiles.containsKey(st))
			return jobWithFiles.get(st);

		return "nofiles";
	}


	public String splitPoint(String Job)
	{
		String returnn="";
		boolean boucle,endSplit=false;
		int numLigne=ligne+1;
		boolean happyEnd=false;

		ArrayList<Integer> parallelJob=new ArrayList<Integer> ();

		if(haveNextIndice(numLigne))
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
				if(haveNextIndice(numLigne))
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
		{paramValue="";
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

					paramValue=sheet.getRow(parallelJob.get(j)).getCell(6).toString();

				}
				else
				{
					paramValue=paramValue+";"+sheet.getRow(parallelJob.get(j)).getCell(6).toString();
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

	public String splitBegeningPoint(String Job)
	{
		String returnn="";
		boolean boucle,endSplit=false;
		int numLigne=ligne;
		boolean happyEnd=false;
        int firstJob=-1;
		
		ArrayList<Integer> parallelJob=new ArrayList<Integer> ();

		if(haveNextIndice(numLigne))
		{
			while(haveNextIndice(numLigne)&& sheet.getRow(numLigne).getCell(2).toString().isEmpty())
            {
	            numLigne++;
	            firstJob=numLigne;
	            
            }
			
			boucle=sheet.getRow(numLigne).getCell(1).toString().isEmpty();	

			while(boucle&&!endSplit)
			{

				

				if(sheet.getRow(numLigne).getCell(11).toString().equals(Job))
					{
					parallelJob.add(new Integer(Integer.valueOf(numLigne)));
					
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


				numLigne++;
				if(haveNextIndice(numLigne))
				{
					boucle=sheet.getRow(numLigne).getCell(1).toString().isEmpty();		
				}
				else
				{
					boucle=false;
				}

			}


		}

		if(parallelJob.size()>1&& happyEnd)
		{
			paramValue="";
		splitAtBegening.put(nameJobChain, "Split_"+numbeSplit);
		
		if(firstJob==-1)
		throw new Error("Problème dans le fichier Excel");
		
			nameNextJob.put("Split_"+numbeSplit,sheet.getRow(firstJob).getCell(6).toString());
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

			
			
		
				
				returnn=sheet.getRow(indiceEndSplit).getCell(11).toString();
			
			
			
			for(int j=0;j<parallelJob.size();j++)
			{

				if(paramValue.isEmpty())
				{

					paramValue=sheet.getRow(parallelJob.get(j)).getCell(6).toString();

				}
				else
				{
					paramValue=paramValue+";"+sheet.getRow(parallelJob.get(j)).getCell(6).toString();
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

	public String ResolvLink(int line)
	{
		int i=1;
		boolean boucle;
		
		if(haveNextIndice(line+1))
		{
			if(!sheet.getRow(line+1).getCell(3).toString().isEmpty())
			{
				
				Boolean loop=true;
				
				
				while(loop)
				{i++;
					
					if(haveNextIndice(line+i))
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
		if(sheet.getLastRowNum()>=i)
			return true;
		return false;

	}
	public void EndComplexCase(String nextSynchro)
	{
		//now we treat a simple case or a jobchain but before we treat a complex case then we have some thing to do
		//set  next of the synchro and split 
		nameNextJob.put("Split_"+numbeSplit,"Sync_"+numbeSyn);//next of a split is always a synchronization
		nameNextJob.put("Sync_"+numbeSyn,nextSynchro);
		complexe=false;//reset

		//here, we have treat the complex, we must create a job file
		//if it's the first complex case in the jobchain we create a jobchain with a new order and a new process
		//else we add a new process, look a config file for understand
		//jobchain in the config file it's not a common jobchain 
		




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

			}else if(rowL1.getCell(3).toString().equals("O"))
			{
				return getL1(numeroColone,indice+1);

			}



		}

		return "NogetL1";
	}

	public String getL1File()
	{
		if(sheet.getLastRowNum()>=ligne+1)
		{
			Row rowL1=sheet.getRow(ligne+1);
			if(rowL1.getCell(3).toString().equals("O"))
			{
				int tempNumberFile=numberFile;
				waitFileForExecute(rowL1.getCell(30).toString());
				boolean noEndFileEndChain=false;
				int add=2;
				if(sheet.getLastRowNum()>=ligne+add)
					noEndFileEndChain=true;

				while(noEndFileEndChain)
				{

					if(sheet.getRow(ligne+add).getCell(3).toString().equals("O"))
					{
						waitFileForExecute(sheet.getRow(ligne+add).getCell(30).toString());
						add++;

						if(sheet.getLastRowNum()<ligne+add)
							noEndFileEndChain=false;

					}
					else
					{
						noEndFileEndChain=false;
					}

				}
				//un job peut avoir le nom de (numberfile+_file) meme si cela est peu probable
				//je rajoute Unique pour etre sur qu'il n'y est pas de conflit
				return tempNumberFile+"_file";
			}
		}
		return "NogetL1File";
	}

	public String cdata(String st) {
		return "<![CDATA[" + st + "]]>"; // for add CDATA in Xml file
	}

	public void waitFileForExecute(String contenuFichier)
	{

		createSubMitJobEvent( numberFile , contenuFichier);
		numberFile++;

	}

	/**
	 * name - createSubMitJobEvent create a envent 
	 *                           
	 * 
	 * @param String : the name of event
	 * 
	 * @author fourat
	 * @date 13/08/2015
	 * @note
	 */

	public void createSubMitJobEvent(int id ,String contenuFichier){


		Job job = fabrique.createJob();

		Params parameters = fabrique.createParams() ;
		Param add = fabrique.createParam() ;

		add.setName("scheduler_event_action") ;
		add.setValue("add") ;
		parameters.getParamOrCopyParamsOrInclude().add(add) ;

		Param classe = new Param() ;
		classe.setName("scheduler_event_class") ;
		classe.setValue("file") ;
		parameters.getParamOrCopyParamsOrInclude().add(classe) ;

		Param idP= new Param() ;
		idP.setName("scheduler_event_id") ;
		idP.setValue(id+"_file") ;
		parameters.getParamOrCopyParamsOrInclude().add(idP) ;


		job.setParams(parameters) ;
		Script script =fabrique.createScript();
		script.setLanguage("shell") ;
		script.getContent().add("echo \"EVENT Add/Delete\"");
		job.setScript(script) ;
		job.setStopOnError("no") ;
		job.setOrder("yes") ;

		Monitor moni=fabrique.createJobMonitor();
		moni.setName(id+"_file");

		script =fabrique.createScript();
		script.setLanguage("java");
		script.setJavaClass("com.sos.jitl.eventing.EventMonitorTaskAfter");

		moni.setScript(script);
		job.getMonitor().add(moni);


		JobChain jbc=fabrique.createJobChain();
		JobChain.JobChainNode jbcn=fabrique.createJobChainJobChainNode();
		jbcn.setState(id+"_file");
		jbcn.setJob(id+"_file");
		jbcn.setNextState("S_cleanfile");
		JobChain.FileOrderSource file=fabrique.createJobChainFileOrderSource();

		String[] split=contenuFichier.split("/");
		String directory="";
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
		jbc.getFileOrderSource().add(file);
		jbc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(jbcn);

		JobChain.FileOrderSink deletFile=fabrique.createJobChainFileOrderSink();
		deletFile.setState("S_cleanfile");
		deletFile.setRemove("yes");
		jbc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(deletFile);

		OutputStream os;
		try { 

			os = new FileOutputStream(outPut+id+"_file"+".job.xml");
			marshaller.marshal(job, os);

			List lst=job.getParams().getParamOrCopyParamsOrInclude();

			for(int z=0;z<lst.size();z++)
			{
				Param pr=(Param)lst.get(z);
				if(pr.getValue().equals("add"))
					pr.setValue("remove");
			}




			os = new FileOutputStream(outPut+id+"_file_removeEvent"+".job.xml");
			marshaller.marshal(job, os);



			os = new FileOutputStream(outPut+nameJobChain+"_"+id+"_file"+ ".job_chain.xml");
			marshaller.marshal(jbc, os); 



		} catch (FileNotFoundException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public Cell coloneExcelSuivant(Iterator<Cell> tempCellIteratorL1,int nbreCell) {


		for(int i=0;i<nbreCell-1;i++)
		{
			tempCellIteratorL1.next().getStringCellValue();

		}
		return tempCellIteratorL1.next(); 
	}

	public String getNextJob(String name)
	{
		return nameNextJob.get(name);
	}

	public boolean isJobChainComplex(String name)
	{
		if(jobChainComplex.containsKey(name))
		{return true;}

		return false;
	}

	public String jobChainSuivant(String nameJobchain)
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
