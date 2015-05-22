package org.jobscheduler.converter.tws;

import java.util.LinkedList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.jobscheduler.converter.tws.scheduler.ObjectFactory;
import org.jobscheduler.converter.tws.scheduler.Order;
import org.jobscheduler.converter.tws.scheduler.Period;
import org.jobscheduler.converter.tws.scheduler.RunTime;
import org.jobscheduler.converter.tws.scheduler.RunTime.Date;

public class TWSOnStatement {
	
	
	public TWSOnStatement(){
		this.dates = new LinkedList<String>() ;
		this.runTime = new RunTime() ;
		
	}
	
	public String id ; 
	public String onAt ;
	public String vars ;
	
	
	
	public String onUntil ;
	
	public RunTime runTime ;
	
	
	public LinkedList<String> dates ;
	public String timeZone ;
	
	public Order toOrder(){ //ajouter un paramètre runtime qui est créé lors du parsing du calendrier
		Order order = new Order() ; 
		order.setId(this.id) ;
		
		;
		Period period = new Period() ;
		if(this.onAt != null){
			
			
			period.setSingleStart(onAt) ;
			
		}else{
			period.setSingleStart("00:10:00") ;
		}
	
		this.runTime.getPeriod().add(period) ;
		if (this.onUntil != null){
			this.runTime.setEnd(onUntil) ;
		}
		if(this.dates != null && this.dates.size()>0){
			Launcher.logger.info("adding dates in "+this.id+" "+this.dates.getFirst()) ;
			
		this.runTime.getDate().addAll(this.toJSDates()) ;
		}
		
//		
//		try {
//			JAXBContext jaxbContext = JAXBContext.newInstance(RunTime.class);
//			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//			 
//			// output pretty printed
//			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//		
//			
//				ObjectFactory of = new ObjectFactory() ;
//			jaxbMarshaller.marshal(of.createRunTime(runTime), System.out);
//			System.out.println("***************************************");
//		
//		} catch (JAXBException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		if (!this.timeZone.equals("")){
			runTime.setTimeZone(timeZone) ;
		}
		order.setRunTime(this.runTime);
		return order ;
	}
	
	
	public LinkedList<Date>  toJSDates() {
		LinkedList<Date> retour = new LinkedList<Date>() ;
		for(String d : this.dates){
			Date date = new Date() ; 
			date.setDate(d) ;
			retour.add(date) ;
 		}
		return retour ;
		
		
		
	}
	
	
	public void setCalendar(Object object){
		if(object instanceof LinkedList ){
			this.dates = (LinkedList<String>) object ;
			
		}
	}

}
