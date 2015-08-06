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
import java.nio.channels.FileChannel;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.format.CellFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.tools.ant.types.LogLevel;
import org.h2.tools.Script;
import org.jobscheduler.dashboard.jobdefinition.xml.Job;
import org.jobscheduler.dashboard.jobdefinition.xml.JobChain;
import org.jobscheduler.dashboard.jobdefinition.xml.JobSettings;
import org.jobscheduler.dashboard.jobdefinition.xml.ObjectFactory;
import org.jobscheduler.dashboard.jobdefinition.xml.Order;
import org.jobscheduler.dashboard.jobdefinition.xml.Param;
import org.jobscheduler.dashboard.jobdefinition.xml.Params;
import org.jobscheduler.dashboard.jobdefinition.xml.RunTime;
import org.jobscheduler.dashboard.jobdefinition.xml.Settings;

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
	String nameJobChain;
	Job jb;
	ObjectFactory fabrique ;
	boolean beginJobchain;
	Settings st;
	JobChain jbc;
	Order ord;
	boolean configFile=false;
	boolean complexe;


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
	{
		Integer jid = 0;

		while (rowIterator.hasNext()) {

			Row row = rowIterator.next();//we start at the line number 2, the first line is for title in the excel file


			if(!row.getCell(5).getStringCellValue().isEmpty()) //for check jobchain name 
			{  
				if (complexe)
				{
					EndComplexCase("End");
					
				} 
				
				if(!row.getCell(11).toString().isEmpty())
				{
					
					String followJobChain =getJobChaine(row.getCell(11).toString().substring(1));
					
					this.nextJobChain.put(followJobChain,row.getCell(5).getStringCellValue());
				}

				if(!beginJobchain&&configFile) // here, we have generate a config file for the current jobchain, then we create a output file
				{

					OutputStream os = new FileOutputStream(outPut+nameJobChain+".config.xml");
					marshaller.marshal(st, os);
					configFile=false;


				}
				nameJobChain=row.getCell(5).getStringCellValue(); //for check jobchain name
				beginJobchain=true; //initialization for a new jobchain


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
			
			
			cellIterator=row.cellIterator();

			cell=coloneExcelSuivant(7);	//we go to the column 7, the job name (of the current job)
			
			
			
			

			
			if(!cell.toString().isEmpty()) //if it's not empty we treat a job
			{

				if(isComplex()) //we treat a complicated case because the jid in the ocab >999999 look ocab for understand
				{
					nameNextJob.put(cell.toString(),"Sync_"+numbeSyn);//next of all complicated case is a synchro 
					//if we treat a complex case for first time, complex==false
					//we build a String like the example : mot1;mots2;mots3;
					//it's for the config file
					if(complexe==false)
					{

						paramValue=cell.toString(); //for build all

					}
					else
					{
						paramValue=paramValue+";"+cell.toString();
					}


					complexe=true;//for notice we have treat a complex case


					if (!jobChainComplex.containsKey(nameJobChain))
					{
						jobChainComplex.put(nameJobChain,true);//For now if a jochain contain complex case,
						//We add for the complex jobchain a job start and a job end 
					}
				}
				else
				{
					if (complexe)
					{
						EndComplexCase(cell.toString());
					} 

					//if the 2 next row have the same previous Jid then the next line is a complicated cases
					//and the next of the current line is a split

					if(getL1(12).equals(getL2(12)) && getL1(12).equals(String.valueOf(jid)) && getL2(12).equals(String.valueOf(jid)))
					{

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
						OutputStream os = new FileOutputStream(outPut+"Sync_"+numbeSyn+".job.xml");
						marshaller.marshal(jb, os);




					}else{
						//a normal case, just put the next job in a hashtable
						nameNextJob.put(cell.toString(),getL1(7));
					}

				}

			}
			

			ligne++;
		}

		//for generate the last config file
		if(!beginJobchain)
		{
			OutputStream os = new FileOutputStream(outPut+nameJobChain+".config.xml");
			marshaller.marshal(st, os);
		}
		
		
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
		createConfigFile();




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

	public String getL1(int numeroColone)
	{
		Row rowL1;
		Iterator<Cell> tempCellIteratorL1;
		Cell cellTemp;
		
		
		if(sheet.getLastRowNum()>=ligne+1)
		{
			
			rowL1=sheet.getRow(ligne+1);

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

			}


		}

		return "NogetL1";
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
	public boolean isComplex()
	{
		Row rowC;
		Iterator<Cell> tempCellC;
		Cell cellTemp;
		rowC=sheet.getRow(ligne);
		String valeurCourante=rowC.getCell(11).toString();
		String ValeurAcomparer=getL1(12);
		
		if(!valeurCourante.isEmpty()&& valeurCourante.indexOf(";")==-1 && valeurCourante.indexOf(",")==-1)
		{	
			
			
			
			
				if(ValeurAcomparer.indexOf(";")!=-1 || ValeurAcomparer.indexOf(",")!=-1||ValeurAcomparer.equals("NogetL1"))
				{
					if(!sheet.getRow(ligne-1).getCell(11).toString().isEmpty())
					{
					ValeurAcomparer=sheet.getRow(ligne-1).getCell(11).toString();
					
					}
					else
					{
						return false;
					}
				}

				if(valeurCourante.indexOf(".")!=-1)
				{
					String[] tmp=valeurCourante.split("\\.");
					valeurCourante=tmp[0];

				}

				if(ValeurAcomparer.indexOf(".")!=-1)
				{
					String[] tmp2=ValeurAcomparer.split("\\.");

					ValeurAcomparer=tmp2[0];

				}

				if(Integer.parseInt(valeurCourante)==Integer.parseInt(ValeurAcomparer))
					return true;
			

		}

		
		
		


		return false;
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
