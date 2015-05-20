package org.jobscheduler.converter.tws.tests;

import java.io.IOException;
import java.util.LinkedList;

import org.antlr.runtime.RecognitionException;
import org.jobscheduler.converter.tws.Launcher;
import org.jobscheduler.converter.tws.TWSJobStream;

public class MigrationTests {
	
	
	public static void main(String[] args){
		try {
			Launcher.parseJobs(args[0],args[args.length-1].equals("test")) ;
			Launcher.getVariables(args[2]) ;
			Launcher.setBseDir(args[3]) ;
				Launcher.streams = Launcher.parseSchedule(args[1]) ;

			Launcher.logger.info(Launcher.every_orders +" every order generated") ;
			for(TWSJobStream stream : Launcher.streams.values()){
				if(stream.prevChains.size() >0)
				stream.setPreviousChains() ;
			}
			
			for(String jobId : Launcher.jobs.keySet()){
				Launcher.marshallJob(jobId, Launcher.jobs.get(jobId)) ;
			}
			
			for(TWSJobStream stream : Launcher.streams.values()){
				Launcher.generateJobChain(stream, stream.id.split("#")[0]) ;
			}
			Launcher.marshalSubmitEvents() ;
			Launcher.marshalCheckEvents() ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RecognitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	

}
