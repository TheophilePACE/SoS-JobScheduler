
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

package org.jobscheduler.dashboard.ocab;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelCleaner {
	XSSFSheet sheet;
	String outPut;
	String log;
	
	public void ExcelOcabCleaner(XSSFWorkbook wb,XSSFSheet sheet, String log,String outPut, ConvertisseurTwsJbs interfaceGraphique)
	{
		this.sheet=sheet;
		this.outPut=outPut;
		this.log=log;		
		log+="Nettoyage du fichier Excel \n";

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
		String nameOfJobChain="";
		int NumberJobchainsup=10;

		for(int p=2;p<=sheet.getLastRowNum();p++)	 
		{	

			if(!row.getCell(2).toString().isEmpty()) //sort job on dependency
			{ 
				int nextLine=numLigne+1;
				LinkedHashMap<String,Integer> lineAft = new LinkedHashMap<String,Integer>();
				LinkedHashMap<String,Integer> lineBef = new LinkedHashMap<String,Integer>();
				while(nextLine<=sheet.getLastRowNum())
				{
					if(!sheet.getRow(nextLine).getCell(1).toString().isEmpty()){
						nextLine=sheet.getLastRowNum(); 

					}
					else if(sheet.getRow(numLigne).getCell(2).toString().equals(sheet.getRow(nextLine).getCell(11).toString()))
					{

						lineAft.put(sheet.getRow(nextLine).getCell(2).toString(), nextLine);
					}
					nextLine++;
				}

				if(lineAft.size()==1)
				{
					if(lineAft.values().iterator().next()!=numLigne+1)
					{
						copyExcelJob(lineAft.values().iterator().next(),(numLigne+1));

						delete=true;
						log+="La ligne "+lineAft.values().iterator().next() + "a été remonté à la ligne "+ (numLigne+1);
						System.out.println("La ligne "+lineAft.values().iterator().next() + "a été remonté à la ligne "+ (numLigne+1));

					}
				}

				nextLine=numLigne+1;

				while(nextLine<=sheet.getLastRowNum())
				{
					if(!sheet.getRow(nextLine).getCell(1).toString().isEmpty()){
						nextLine=sheet.getLastRowNum(); 

					}
					else if(sheet.getRow(numLigne).getCell(11).toString().equals(sheet.getRow(nextLine).getCell(2).toString()))
					{
						if(!sheet.getRow(numLigne).getCell(11).toString().isEmpty()&&!sheet.getRow(nextLine).getCell(2).toString().isEmpty())
						{

							System.out.println("lline "+ numLigne+" "+nextLine);
							lineBef.put(sheet.getRow(nextLine).getCell(2).toString(), nextLine);
						}
					}

					nextLine++;
				}

				if(lineBef.size()==1)
				{

					copyExcelJob(lineBef.values().iterator().next(),numLigne);				
					rowSuiv=row;
					row = sheet.getRow(p-1);
					delete=true;

				}

			}
			
			//If you want schedule with time, he sorts the time from smallest to largest 
			/*
		 if(!row.getCell(2).toString().isEmpty()&&!row.getCell(16).toString().isEmpty()&&!rowSuiv.getCell(16).toString().isEmpty()&&(!rowPrec.getCell(3).toString().isEmpty()||!rowPrec.getCell(5).toString().isEmpty()))
			{
				String timeRow=row.getCell(16).toString();
				int aComparer=numLigne;
				int nbJob=aComparer+1;
				int ligneEchange=0;
				boolean echange=true;

				while(echange)
				{	 
					while(!sheet.getRow(nbJob).getCell(2).toString().isEmpty()&&!sheet.getRow(nbJob).getCell(16).toString().isEmpty())
					{
						if(Integer.parseInt(timeRow)>Integer.parseInt(sheet.getRow(nbJob).getCell(16).toString()))
						{

							ligneEchange=nbJob;
							timeRow=sheet.getRow(nbJob).getCell(16).toString();

						}
						nbJob++;
					}

					if(ligneEchange!=0)
					{
						log+="Une incohérence dans la liste des jobs a été détectée et corrigée, la ligne "+(ligneEchange+1)+ "a été échangée avec la ligne "+ (aComparer+1)+" à cause de la colonne <<at>> \n";

						switchRow(aComparer, ligneEchange,csCF);
                        /*
						if(rebuildDependency(aComparer,nameOfJobChain+NumberJobchainsup))
	                  						NumberJobchainsup++;
			 */
			/*
						ligneEchange=0;
					}


					if(aComparer==nbJob-1)
					{
						echange=false; 
					}

					aComparer++;
					nbJob=aComparer+1;
					timeRow=sheet.getRow(aComparer).getCell(16).toString();
				}

				if(rowPrec.getCell(3).toString().equals("R"))
				{
					log+="Modification de l'order: "+rowPrec.getCell(14).toString()+ ", mise à jour de la colone <<at>> \n";

					rowPrec.getCell(16).setCellValue(row.getCell(16).toString()); 
					rowPrec.getCell(16).setCellStyle(csCF);
				}
			}

			 */

			if(!row.getCell(1).toString().isEmpty())
			{
				nameOfJobChain=row.getCell(5).toString();
			}


              
			/* décomment if you want add a order when there is not, but carefully! see michael koch C2t 
			//Add order
			 if(!row.getCell(16).toString().isEmpty()&& !row.getCell(1).toString().isEmpty())
			 {

				 if(!rowSuiv.getCell(3).toString().equals("R")&&!rowSuiv.getCell(3).toString().equals("O"))
				 {
					 log+="Ajout d'un order à la ligne :"+(numLigne+1)+" \n";
					 copyRow(sheet,numLigne+1, csCF); 

				 }
			 }
			//End add order
			 */

			if(!row.getCell(1).toString().isEmpty()&&!row.getCell(16).toString().isEmpty()&&!row.getCell(16).toString().isEmpty())
			{
				if(!rowSuiv.getCell(2).toString().isEmpty())
				{
					if(rowSuiv.getCell(16).toString().isEmpty())
						rowSuiv.getCell(16).setCellValue(row.getCell(16).toString());

					if(rowSuiv.getCell(19).toString().isEmpty())
						rowSuiv.getCell(19).setCellValue(row.getCell(19).toString());
				}

			}

			if(rowSuiv.getCell(3).toString().equals("O"))
			{

				rowSuiv.getCell(30).setCellValue(rowSuiv.getCell(30).toString().replace("//", "/"));
				String correction=rowSuiv.getCell(30).toString();


				String[] cheminEtRegex=correction.split("/");

				String regex=cheminEtRegex[cheminEtRegex.length-1];	

				StringTokenizer chaine = new StringTokenizer(regex,"?*",true);
				if(chaine.countTokens()>1)
				{
					rowSuiv.getCell(30).setCellValue("");
					String temps=chaine.nextToken();
					String tempsNext;
					for(int z=0;z<cheminEtRegex.length-1;z++)
					{
						rowSuiv.getCell(30).setCellValue(rowSuiv.getCell(30).toString()+cheminEtRegex[z]+"/");
					}

					while(chaine.hasMoreTokens())
					{
						tempsNext=chaine.nextToken();

						if(!temps.contains(".")&&(tempsNext.equals("*")||tempsNext.equals("?")))
						{

							rowSuiv.getCell(30).setCellStyle(csCF);


							rowSuiv.getCell(30).setCellValue(rowSuiv.getCell(30).toString()+temps+"."+tempsNext);
							log+="Modification du regex à la ligne :"+(numLigne+1)+" \n";

							rowSuiv.getCell(30).setCellStyle(csCF);
						}
						else if(!temps.equals("*")&&!temps.equals("?"))
						{

							rowSuiv.getCell(30).setCellValue(rowSuiv.getCell(30).toString()+temps+tempsNext);
						}
						temps=tempsNext;

					}

				}
			}




			//Cleaning excel order, add time on order
			if(row.getCell(3).toString().equals("R"))
			{

				if(row.getCell(16).toString().isEmpty())
				{

					if(!rowPrec.getCell(16).toString().isEmpty())
					{

						row.getCell(16).setCellValue(rowPrec.getCell(16).toString());
						row.getCell(16).setCellStyle(csCF);
					}

					if(!rowSuiv.getCell(16).toString().isEmpty())
					{
						row.getCell(16).setCellValue(rowSuiv.getCell(16).toString());
						row.getCell(16).setCellStyle(csCF);
					}

					if(!rowPrec.getCell(19).toString().isEmpty()&&!rowPrec.getCell(19).toString().equals("2359"))
					{

						row.getCell(19).setCellValue(rowPrec.getCell(19).toString());
						row.getCell(19).setCellStyle(csCF);
					}


				}


			}
			//End cleaning excel order

			/*
			//Delete order
			if(row.getCell(3).toString().equals("O")&&rowPrec.getCell(3).toString().equals("R"))
			{
				sheet.removeRow(sheet.getRow(p-2));
				sheet.shiftRows(p-1, sheet.getLastRowNum(),-1);
				delete=true;
				log+="Suppression d'un order à la ligne :"+(p-1)+" vérifier que tous les orders ont été supprimés! \n";
			p--;
			}
			else if(row.getCell(3).toString().equals("N")&&rowPrec.getCell(3).toString().equals("R")&&rowSuiv.getCell(3).toString().equals("O"))
			{

                sheet.removeRow(sheet.getRow(p-2));		
				sheet.shiftRows(p-1, sheet.getLastRowNum(),-1);
				delete=true;
				log+="Suppression d'un order à la ligne :"+(p-1)+" vérifier que tous les orders ont été supprimés! \n";
			p--;
			}
			 */

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
				row=rowSuiv;
				if(p+1<=sheet.getLastRowNum())
					rowSuiv = sheet.getRow(p+1);
			}
			//End Delete order


			numLigne++;



		}



		log+="Fin du nettoyage, génération du nouveau fichier Excel \n";

		interfaceGraphique.addValueProgressBar(15);
	}

	public void addExcelJobChain(int line, String name)
	{
		sheet.shiftRows(line, sheet.getLastRowNum(), 1);
		sheet.createRow(line);
		Row row=sheet.getRow(line);
		for (int i=0;i<62;i++)
		{
			row.createCell(i);
			row.getCell(i, Row.CREATE_NULL_AS_BLANK);

			switch(i)
			{
			case 1:
				row.getCell(i).setCellValue("-1");
				break;

			case 5:
				row.getCell(i).setCellValue(name);
				break;


			}
		}
	}

	public void copyExcelJob(int line, int  newLine )
	{
		//si le job est suivis de fichier(s) il faut rajouer le job apres le fichier (ou le lock)

		boolean boucle=!sheet.getRow(newLine).getCell(3).toString().isEmpty();
		System.out.println(line+" "+newLine );

		while(boucle)
		{

			newLine++;

			boucle=!sheet.getRow(newLine+1).getCell(3).toString().isEmpty();
		}


		ArrayList<Integer> ajouter=new ArrayList<Integer>();
		ajouter.add(line);

		boucle=line+1<=sheet.getLastRowNum();
		if(boucle)
			boucle=!sheet.getRow(line+1).getCell(3).toString().isEmpty();

		while(boucle)
		{
			line++;
			ajouter.add(line);

			boucle=line+1<=sheet.getLastRowNum();
			if(boucle)
				boucle=!sheet.getRow(line+1).getCell(3).toString().isEmpty();
		}


		for(int z=0;z<ajouter.size();z++)
		{


			Row row=sheet.getRow(ajouter.get(z)); 
			sheet.shiftRows(newLine, sheet.getLastRowNum(), 1);
			sheet.createRow(newLine);

			Row row2=sheet.getRow(newLine);

			for (int i=0;i<61;i++)
			{
				row2.createCell(i);
				row.getCell(i, Row.CREATE_NULL_AS_BLANK);
				row2.getCell(i, Row.CREATE_NULL_AS_BLANK);
				row2.getCell(i).setCellValue(row.getCell(i).toString());



			}


			System.out.println(sheet.getRow((ajouter.get(z)+1)).getCell(2).toString()+"  "+(ajouter.get(z)) );

			sheet.removeRow(sheet.getRow((ajouter.get(z)+1)));

			if((ajouter.get(z)+1)!=sheet.getLastRowNum()+1)
				sheet.shiftRows((ajouter.get(z)+2), sheet.getLastRowNum(),-1);


			//we have created a line then we need to add 1 to the indice "line" for stay on the right value

			newLine++;
		}
	}
	
	public void switchRow( int ligne1, int ligne2, CellStyle csCF)
	{
		Row row=sheet.getRow(ligne1);
		Row row2=sheet.getRow(ligne2);
		row.setRowStyle(csCF);

		log+="Echange entre la ligne n: "+ligne1+"et la ligne n2: " +ligne2+ "\n";


		for(int cn=0; cn<row.getLastCellNum(); cn++) {

			String stringRow=row.getCell(cn).toString();
			String stringRow2=row2.getCell(cn).toString();

			row.getCell(cn).setCellValue(stringRow2);
			row2.getCell(cn).setCellValue(stringRow);


			if(stringRow.equals("") && (row2.getCell(cn).getCellType()==Cell.CELL_TYPE_STRING)){
				row2.removeCell(row2.getCell(cn));
				row2.getCell(cn, Row.CREATE_NULL_AS_BLANK);

			}

			if(stringRow2.equals("")&& (row.getCell(cn).getCellType()==Cell.CELL_TYPE_STRING)){
				row.removeCell(row.getCell(cn));
				row.getCell(cn, Row.CREATE_NULL_AS_BLANK);

			}





		}
	}

	public void copyRow(int destinationRowNum, CellStyle csCF) {
		// Get the source / new row
		Row newRow = sheet.getRow(destinationRowNum);


		// If the row exist in destination, push down all rows by 1 
		if (newRow != null) {
			sheet.shiftRows(destinationRowNum, sheet.getLastRowNum(), 1);
		} 

		sheet.createRow(destinationRowNum);
		Row row= sheet.getRow(destinationRowNum);



		for(int cn=0; cn<newRow.getLastCellNum(); cn++) {

			row.createCell(cn);
			newRow.getCell(cn, Row.CREATE_NULL_AS_BLANK);//plutot row.getcell(cn,R...);

			switch (cn)

			{
			case 3:
				row.getCell(cn).setCellValue("R");
				break;


			case 28 :
				row.getCell(cn).setCellValue("FREQ=DAILY;INTERVAL=1");
				break;
			}


		}
		row.setRowStyle(csCF);


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
}
