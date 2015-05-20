package org.jobscheduler.converter.tws;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.apache.log4j.Logger;
import org.jobscheduler.converter.tws.icalendar.ICalendarLexer;
import org.jobscheduler.converter.tws.icalendar.ICalendarParser;
import org.jobscheduler.converter.tws.job.TWS_JOBSLexer;
import org.jobscheduler.converter.tws.job.TWS_JOBSParser;
import org.jobscheduler.converter.tws.jobstream.TWS_SCHEDULESLexer;
import org.jobscheduler.converter.tws.jobstream.TWS_SCHEDULESParser;
import org.jobscheduler.converter.tws.scheduler.Holiday;
import org.jobscheduler.converter.tws.scheduler.Holidays;
import org.jobscheduler.converter.tws.scheduler.Job;
import org.jobscheduler.converter.tws.scheduler.Job.DelayOrderAfterSetback;
import org.jobscheduler.converter.tws.scheduler.Job.Monitor;
import org.jobscheduler.converter.tws.scheduler.JobChain;
import org.jobscheduler.converter.tws.scheduler.JobChain.FileOrderSource;
import org.jobscheduler.converter.tws.scheduler.JobChain.JobChainNode;
import org.jobscheduler.converter.tws.scheduler.ObjectFactory;
import org.jobscheduler.converter.tws.scheduler.Order;
import org.jobscheduler.converter.tws.scheduler.Param;
import org.jobscheduler.converter.tws.scheduler.Params;
import org.jobscheduler.converter.tws.scheduler.RunTime;
import org.jobscheduler.converter.tws.scheduler.RunTime.Date;
import org.jobscheduler.converter.tws.scheduler.Script;
import org.jobscheduler.converter.tws.scheduler.Weekdays.Day;
import org.jobscheduler.converter.tws.variables.TWS_VARIABLESLexer;
import org.jobscheduler.converter.tws.variables.TWS_VARIABLESParser;


public class Launcher {
	
	 
	public static HashMap<String,TWSJobStream> streams ;
	public static LinkedList<String> submitEndChains ;
	public static  LinkedList<String> submitEventsEvery ;
	
	public static  LinkedList<String> checkEventsEvery ;
	

	
	public static Logger logger = Logger.getLogger(Launcher.class);
	
	public static HashMap<String,Job> jobs ;
	public static HashMap<String,JobChainNode> states ;
	public static HashMap<String,Recovery> recoveries ;
	private static String baseDir  ="/home/fferchic/Bureau/tws/qvi/";
	
	public static String getBaseDir(){
		return baseDir ;
	}
	
	
	public static Job getJobById(String chainId,String jobId){
		String chekedJobId = checkJobId(chainId, jobId) ;
		return jobs.get(chekedJobId) ;
		
	}
	
	
	
	public static void putJobIntoDir(String chainId,String jobId){
		String chekedJobId = checkJobId(chainId, jobId) ;
		Job job = jobs.get(chekedJobId) ;	
		createApplicationDirectory(chekedJobId) ;
		marshallJob(chekedJobId, job) ;
	}
	
	
	public static String checkJobId(String chainId,String jobId){
		String[] jobIdelem = jobId.split("#") ;

		if (jobIdelem.length==1){
			//logger.info(chainId+" "+jobId+chainId.split("#")[0]+" "+"#"+jobId) ;
			return chainId.split("#")[0]+"#"+jobId ;
			
		}else{
			
		return jobId ;
			
		}
	}
	
	
	public static void marshallJob(String id, Job job){
		//at this level, we are sure thar the job id contains a #
		
		try {
			String[] elems = id.split("#") ;
			File folder = new File(baseDir+elems[0]+"/") ;
			folder.mkdirs();
			job.setStopOnError("no") ;
			File jobFile = new File(baseDir+elems[0]+"/"+elems[1]+".job.xml") ;
			JAXBContext jaxbContext;
			jaxbContext = JAXBContext.newInstance(Job.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			 
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	 
			jaxbMarshaller.marshal(job, jobFile);
		} catch (JAXBException e) {
			
			e.printStackTrace();
		}
	
	}
	
	
	
	public static void MarshallJobChain(JobChain jc,String app){
		try {
			//logger.info("Creating Job Chain "+baseDir+app+"/"+jc.getName()+".job_chain.xml");
			File jobFile = new File(baseDir+app+"/"+jc.getName()+".job_chain.xml") ;
			JAXBContext jaxbContext;
			jaxbContext = JAXBContext.newInstance(JobChain.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			 
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	 
			jaxbMarshaller.marshal(jc, jobFile);
		} catch (JAXBException e) {
			
			e.printStackTrace();
		}
	}
	
	
	public static void putChainIntoDirectory(TWSJobStream stream,String app){
		JobChain jc = stream.toJobChain() ;
		MarshallJobChain(jc, app) ;
		//LinkedList<Order> everyOrders = stream.everyToOrders() ;
		LinkedList<Order> orders = stream.onToOrder() ;
		for(Order order : orders){
			marshallOrder(stream.id, order.getId(), order) ;
		}
		
	}
	public static void main(String[] args){
		
		if (args.length <4 ){
			logger.fatal("launch arguments :  jobsFilePath jobSchedulesFilePath calendarFilePath variablesFilePath TargerDirectory" ) ;
		}else{
			baseDir = args[3] ;
		}
		
		
		
		
		
	}
	
	public static int every_orders ;
	public static void marshallOrder(String chainId, String orderId,Order order){

		JAXBContext jaxbContext;
		try {
			every_orders++ ;
		//	logger.info("marshalling order "+chainId+","+orderId+".order.xml") ;
			File file = new File(baseDir+chainId.split("#")[0]+"/"+chainId.split("#")[1]+","+orderId+".order.xml") ;
			jaxbContext = JAXBContext.newInstance(Order.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			 
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ObjectFactory of = new ObjectFactory() ;
			jaxbMarshaller.marshal(of.createOrder(order), file);
			
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
		}
	}
	
	public static void setPriority(Job job, String priority){
		if(!priority.equals("") && priority!= null){
			job.getPriority().add(priority) ;
		}
	}
	
	
	
	public static HashMap<String,Job> getJobs()  {
		return jobs ;
	}
	
	
	public static String getApplicationName(String fullName){
		return fullName.split("#")[0] ;
	}
	
	public static void createApplicationDirectory(String fullName){
		String fullPath = baseDir+getApplicationName(fullName) ;
		File file = new File(fullPath) ;
	if(!file.exists()){
		
		//logger.info("Creating Directory for Application "+getApplicationName(fullName) );
		
		file.mkdir() ;
		}
	}
	
	public static void parseJobs(String file,boolean test) throws IOException, RecognitionException{
		submitEndChains = new LinkedList<String>() ;
		submitEventsEvery = new LinkedList<String>() ;
		checkEventsEvery =new LinkedList<String>() ;
		CharStream charStream = new ANTLRFileStream(file) ;
		TWS_JOBSLexer lexer = new TWS_JOBSLexer(charStream) ;
		TokenStream tokenStream = (TokenStream) new CommonTokenStream(lexer) ;
		TWS_JOBSParser parser = new TWS_JOBSParser((org.antlr.runtime.TokenStream) tokenStream) ;
		//logger.info("Parsing of the jobs contained in "+file) ;
		states = new HashMap<String,JobChainNode>() ;
		recoveries = new HashMap<String,Recovery>() ;
		jobs = parser.jobs(test).jobs ; 
		
	
	}
	
	
	public static HashMap<String,TWSJobStream>  parseSchedule(String file) throws IOException, RecognitionException{
		CharStream charStream = new ANTLRFileStream(file) ;
		TWS_SCHEDULESLexer lexer = new TWS_SCHEDULESLexer(charStream) ;
		TokenStream tokenStream = (TokenStream) new CommonTokenStream(lexer) ;
		TWS_SCHEDULESParser parser = new TWS_SCHEDULESParser((org.antlr.runtime.TokenStream) tokenStream) ;
		
		HashMap<String,TWSJobStream>  streams =parser.schedules().streams ; 
		return streams ;
	
	}
	
	public static void setBseDir(String dir){
		baseDir = dir ;
	}
	
	public static String getString(String string){
		return string.substring(1, string.length()-1) ;
	}
	
	public static String normalizeDate(String date){
		String[] elements = date.split("/") ;
		return elements[2]+"-"+elements[0]+"-"+elements[1] ;
	}
	
	public static String normalizeTime(String time){
		return time.substring(0, 2)+":"+time.substring(2,4)+":00" ;
	}
	
	
	public static int failedChains = 0 ;
	public static int successChains = 0 ;
	
	
	public static void generateJobChain(TWSJobStream stream,String app){
		
	//	if(stream.SameChain()){
			
			putChainIntoDirectory(stream,app) ;
		//	successChains++ ;
		//}else{
			//logger.warn("The Job Chain "+stream.id+" is not centralized , marshalling is impossible for the moment, please contact Michael Koch for further information") ;
		//	failedChains++ ;
		//}
	}


	public static HashMap<String,Params> variables ;
	
	
	public static void getVariables(String file) {
		CharStream charStream;
		try {
			charStream = new ANTLRFileStream(file);
			
			TWS_VARIABLESLexer lexer = new TWS_VARIABLESLexer(charStream) ;
			TokenStream tokenStream = (TokenStream) new CommonTokenStream(lexer) ;
			TWS_VARIABLESParser parser = new TWS_VARIABLESParser((org.antlr.runtime.TokenStream) tokenStream) ;
			//logger.info("Parsing of the variables contained in "+file) ;
			variables = parser.variables() ; 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RecognitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	public static void MarshallVariable(String chainId,String varId){
		
		try {
			Params params = variables.get(varId) ;
			File paramsFile = new File(baseDir+chainId.split("#")[0]+"/"+varId+".xml") ;
			JAXBContext jaxbContext;
			jaxbContext = JAXBContext.newInstance(Params.class);
		
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			 
			
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	 
			jaxbMarshaller.marshal(params, paramsFile);
		
		} catch (JAXBException e) {
			
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public static void recognizeICalendar(String calendar,RunTime runTime,boolean on)
	{
		CharStream charStream;
		try {
			charStream = new ANTLRStringStream(calendar);
			ICalendarLexer lexer = new ICalendarLexer(charStream) ;
			TokenStream tokenStream = (TokenStream) new CommonTokenStream(lexer) ;
			ICalendarParser parser = new ICalendarParser((org.antlr.runtime.TokenStream) tokenStream) ;
			JAXBContext jaxbContext;
			
			parser.calendar(runTime,on) ; 
			
			
		} catch (RecognitionException e) {
			logger.info("parse error") ;
		}
		
	}
	
	
	
	public static  FileOrderSource toFileOrder(String gOpen){
		String open = getString(gOpen) ;
 		FileOrderSource fos = new FileOrderSource() ;
		int pos = open.lastIndexOf("/") ;
		fos.setDirectory(open.substring(0,pos)) ;
		fos.setRegex(open.substring(pos+1,open.length())) ;
		
		return fos ;
	}
	
	public static void createSubMitJobEvent(String id){
		String app = id.split("#")[0] ;
		String chainId = id.split("#")[1] ;
		
		Job job = new Job() ;
		
		Params parameters = new Params() ;
		Param add = new Param() ;
		
		add.setName("scheduler_event_action") ;
		add.setValue("add") ;
		parameters.getParamOrCopyParamsOrInclude().add(add) ;
		
		Param classe = new Param() ;
		classe.setName("scheduler_event_class") ;
		classe.setValue("PROCESS_END") ;
		parameters.getParamOrCopyParamsOrInclude().add(classe) ;
		
		Param idP= new Param() ;
		idP.setName("scheduler_event_id") ;
		idP.setValue(chainId+"_end") ;
		parameters.getParamOrCopyParamsOrInclude().add(idP) ;
		
		Param expires = new Param() ;
		expires.setName("scheduler_event_expires") ;
		
		expires.setValue("never") ;
		parameters.getParamOrCopyParamsOrInclude().add(expires) ;
		
		job.setParams(parameters) ;
		Script script = new Script() ;
		script.setLanguage("java") ;
		script.setJavaClass("sos.scheduler.job.JobSchedulerSubmitEventJob") ;
		
		job.setScript(script) ;
		
		job.setStopOnError("no") ;
		job.setOrder("yes") ;
		
		marshallJob(app+"#"+"submit_event_"+chainId,job) ;
		
	}
	
	public static void createCheckJobEvent(String id){
		String app = id.split("#")[0] ;
		String chainId = id.split("#")[1] ;
		
		Job job = new Job() ;
		
		Params parameters = new Params() ;
		Param add = new Param() ;
		
		add.setName("scheduler_event_spec") ;
		add.setValue("//events[event/@event_id='"+chainId+"_end"+"']" ) ;
		parameters.getParamOrCopyParamsOrInclude().add(add) ;
		job.setParams(parameters) ;
		
		Script script = new Script() ;
		script.setLanguage("java") ;
		script.setJavaClass("sos.scheduler.job.JobSchedulerExistsEventJob") ;
		job.setScript(script) ;
		
		
		Monitor monitor = new Monitor() ;
		monitor.setOrdering(new BigInteger("0")) ;
		monitor.setName("configuration_monitor") ;
		Script monitorScript = new Script() ;
		monitorScript.setLanguage("java") ;
		monitorScript.setJavaClass("sos.scheduler.managed.configuration.ConfigurationOrderMonitor");
		monitor.setScript(monitorScript) ;
		job.getMonitor().add(monitor) ;
		
		DelayOrderAfterSetback d1 = new DelayOrderAfterSetback() ;
		d1.setDelay("10") ;
		d1.setSetbackCount(new BigInteger("1")) ;
		d1.setIsMaximum("no") ;
		job.getDelayOrderAfterSetback().add(d1) ;
		
		DelayOrderAfterSetback d2 = new DelayOrderAfterSetback() ;
		d2.setDelay("0") ;
		d2.setSetbackCount(new BigInteger("999")) ;
		d2.setIsMaximum("yes") ;
		job.getDelayOrderAfterSetback().add(d2) ;
		
		
		
		job.setStopOnError("no") ;
		job.setOrder("yes") ;
		
		marshallJob(app+"#"+"check_event_"+chainId,job) ;
		
	}
	
	
	
	public static void createEveryCheckJobEvent(String id,String end){
		String app = id.split("#")[0] ;
		String event = id.split("#")[1] ;
		
		Job job = new Job() ;
		
		Params parameters = new Params() ;
		Param add = new Param() ;
		
		add.setName("scheduler_event_spec") ;
		add.setValue("//events[event/@event_id='"+event+"']" ) ;
		parameters.getParamOrCopyParamsOrInclude().add(add) ;
		job.setParams(parameters) ;
		
		Script script = new Script() ;
		script.setLanguage("java") ;
		script.setJavaClass("sos.scheduler.job.JobSchedulerExistsEventJob") ;
		job.setScript(script) ;
		
		
		Monitor monitor = new Monitor() ;
		monitor.setOrdering(new BigInteger("0")) ;
		monitor.setName("configuration_monitor") ;
		Script monitorScript = new Script() ;
		monitorScript.setLanguage("java") ;
		monitorScript.setJavaClass("sos.scheduler.managed.configuration.ConfigurationOrderMonitor");
		monitor.setScript(monitorScript) ;
		job.getMonitor().add(monitor) ;
		
		DelayOrderAfterSetback d1 = new DelayOrderAfterSetback() ;
		d1.setDelay("10") ;
		d1.setSetbackCount(new BigInteger("1")) ;
		d1.setIsMaximum("no") ;
		job.getDelayOrderAfterSetback().add(d1) ;
		
		DelayOrderAfterSetback d2 = new DelayOrderAfterSetback() ;
		d2.setDelay("0") ;
		d2.setSetbackCount(new BigInteger("999")) ;
		d2.setIsMaximum("yes") ;
		job.getDelayOrderAfterSetback().add(d2) ;
		
		
		
		job.setStopOnError("no") ;
		job.setOrder("yes") ;
		
		marshallJob(app+"#"+"check_"+event,job) ;
		
	}
	
	public static void createEverySubmitEvent(String id){
		String app = id.split("#")[0] ;
		String event = id.split("#")[1] ;
		System.out.println(event) ;
		
		Job job = new Job() ;
		
		Params parameters = new Params() ;
		Param add = new Param() ;
		
		add.setName("scheduler_event_action") ;
		add.setValue("add") ;
		parameters.getParamOrCopyParamsOrInclude().add(add) ;
		
		Param classe = new Param() ;
		classe.setName("scheduler_event_class") ;
		classe.setValue("PROCESS_END") ;
		parameters.getParamOrCopyParamsOrInclude().add(classe) ;
		
		Param idP= new Param() ;
		idP.setName("scheduler_event_id") ;
		idP.setValue(event) ;
		parameters.getParamOrCopyParamsOrInclude().add(idP) ;
		
		Param expires = new Param() ;
		expires.setName("scheduler_event_expires") ;
		
		expires.setValue("never") ;
		parameters.getParamOrCopyParamsOrInclude().add(expires) ;
		
		job.setParams(parameters) ;
		Script script = new Script() ;
		script.setLanguage("java") ;
		script.setJavaClass("sos.scheduler.job.JobSchedulerSubmitEventJob") ;
		
		job.setScript(script) ;
		
		job.setStopOnError("no") ;
		job.setOrder("yes") ;
		
		marshallJob(app+"#"+"submit_"+event,job) ;
	}
	
	public static void marshalEverySubmitEvents(){
	for(String ev : submitEventsEvery){
		createEverySubmitEvent(ev) ;
	}
	
	}
	
	public static void marshalEveryCheckEvents(){
		for(String ev : checkEventsEvery){
			createEveryCheckJobEvent(ev,null) ;
		}
		
		}
	
	public static void marshalSubmitEvents(){
		for(String ev : submitEndChains){
			createSubMitJobEvent(ev) ;
		}
		
		}
	
	
	public static void marshalCheckEvents(){
		for(String ev : submitEndChains){
			createCheckJobEvent(ev) ;
		}
	}

 
	public static void mergeRunTimes(RunTime runTime, RunTime exceptRt) {
		
		
		
		Holidays hd = new Holidays() ;
		Holidays.Weekdays wds = new Holidays.Weekdays() ;
		
		LinkedList<Holidays.Weekdays.Day> hdays = new LinkedList<Holidays.Weekdays.Day>() ;
		if(exceptRt.getWeekdays() != null)
		{for(Day day :exceptRt.getWeekdays().getDay() ){
			Holidays.Weekdays.Day hday = new Holidays.Weekdays.Day() ;
			hday.getDay().addAll(day.getDay()) ;
			hdays.add(hday) ;
		}
		

		
		wds.getDay().addAll(hdays) ;
		hd.getWeekdaysOrHolidayOrInclude().add(wds)	 ;		
			
		runTime.setHolidays(hd) ;
		}
		
		if(exceptRt.getDate() != null && exceptRt.getDate().size()!=0){
		for(Date date :exceptRt.getDate() ){
			
		Holiday hday = new Holiday() ;
		hday.setDate(date.getDate()) ;
		hd.getWeekdaysOrHolidayOrInclude().add(hday) ;
	}
		runTime.setHolidays(hd) ;
	}
		
	}
	
}
