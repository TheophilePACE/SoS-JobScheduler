package org.jobscheduler.dashboard.ocab;

import java.util.Hashtable;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.jobscheduler.dashboard.jobdefinition.xml.Job;

public class JobHelper {
	
	
	Hashtable<String, String> nameNextJob;
	Iterator<Row> rowIterator;
	Iterator<Cell> cellIterator;
	Cell cell;
	
	XSSFSheet sheet;
	int ligne=1;
	
	public JobHelper(XSSFSheet sheet)
	{
		this.sheet=sheet;
		rowIterator=sheet.iterator();
		rowIterator.next();
		nameNextJob = new Hashtable<String, String>();
	}
	
	public void initialisation()
	{
		
		
		while (rowIterator.hasNext()) {
			
		Row row = rowIterator.next();	 
		cellIterator=row.cellIterator();
		cell=coloneExcelSuivant(7);	
		
		if(!cell.toString().isEmpty())
		{
			
			if(estComplex())
			{
				
			}
			else
			{
				nameNextJob.put(cell.toString(),getL1());
				
			}
			
		}
		
		ligne++;
		}
	}
	
	
	public String getL1()
	{
		Row RowL1;
		Iterator<Cell> tempCellIteratorL1;
		Cell cellTemp;
		
		if(sheet.getLastRowNum()>ligne+1)
		{
			RowL1=sheet.getRow(ligne+1);
			
			tempCellIteratorL1=RowL1.iterator();
			cellTemp=coloneExcelSuivant(tempCellIteratorL1,7);
			if (!cellTemp.toString().isEmpty())
			return cellTemp.toString();
		}
		
	return "NogetL1";
	}
	
	public String getL2()
	{
		Row RowL2;
		Iterator<Cell> tempCellIteratorL2;
		Cell cellTemp;
		
		if(sheet.getLastRowNum()>ligne+2)
		{
			RowL2=sheet.getRow(ligne+2);
			
			tempCellIteratorL2=RowL2.iterator();
			cellTemp=coloneExcelSuivant(tempCellIteratorL2,7);
			if (!cellTemp.toString().isEmpty())
			return cellTemp.toString();
		}
		
	return "NogetL2";
	}
	
	public boolean estComplex()
	{
		if (getL1().equals(getL2()))
		{
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
	
	public Cell coloneExcelSuivant(Iterator<Cell> tempCellIteratorL1,int nbreCell) {
		
		
		for(int i=0;i<nbreCell-1;i++)
		{
			tempCellIteratorL1.next();
		}
		return tempCellIteratorL1.next(); 
	}
	
	public String getNextJob(String name)
	{
		return nameNextJob.get(name);
	}
	
}
