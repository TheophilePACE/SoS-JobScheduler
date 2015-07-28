package org.jobscheduler.dashboard.ocab;

import junit.framework.TestCase;

public class TestExcelReader extends TestCase{
	
	public void testNumberFile() throws Exception {
	String cheminExcel="C:/Users/puls/workspace2/SoS-JobScheduler/dashboard/src/test/ressource/KARMA_QAL_1.4_FULL.xlsx";
	String cheminSortie="D:/resultat/";
		ExcelReader exrd = new ExcelReader(cheminExcel,cheminSortie,null);
		if (exrd.treatExcelFile())
			assertEquals(exrd.OutputTest(123),147);
		
		
		} 
	

}
