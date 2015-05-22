package org.jobscheduler.converter.tws.tests;

import java.io.IOException;
import java.util.LinkedList;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.apache.log4j.Logger;
import org.jobscheduler.converter.tws.TWSJobStatement;
import org.jobscheduler.converter.tws.TWSJobStream;
import org.jobscheduler.converter.tws.jobstream.TWS_SCHEDULESLexer;
import org.jobscheduler.converter.tws.jobstream.TWS_SCHEDULESParser;

public class JobStatementTEST {
	
	
	protected static  Logger logger = Logger.getLogger(JobStatementTEST.class) ;
	
	public static void main(String[] args){
		CharStream charStream;
		try {
			charStream = new ANTLRFileStream(args[0]);
		
		TWS_SCHEDULESLexer lexer = new TWS_SCHEDULESLexer(charStream) ;
		TokenStream tokenStream = (TokenStream) new CommonTokenStream(lexer) ;
		TWS_SCHEDULESParser parser = new TWS_SCHEDULESParser((org.antlr.runtime.TokenStream) tokenStream) ;
		//LinkedList<TWSJobStream> streams = parser.schedules().streams ;
//		for(TWSJobStream st: streams){
//			//logger.info(st.toString()) ;
//		}
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();}
//		} catch (RecognitionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		}

}
