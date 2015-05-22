package org.jobscheduler.converter.tws;

import java.util.LinkedList;

import org.jobscheduler.converter.tws.scheduler.Job;
import org.jobscheduler.converter.tws.scheduler.JobChain;
import org.jobscheduler.converter.tws.scheduler.JobChainNodeJobChain;
import org.jobscheduler.converter.tws.scheduler.Order;
import org.jobscheduler.converter.tws.scheduler.Period;
import org.jobscheduler.converter.tws.scheduler.RunTime;
import org.jobscheduler.converter.tws.scheduler.JobChain.JobChainNode;

public class TWSJobStatement {
	
	
	protected String id ; 
	//protected boolean isFirst ; 
	protected LinkedList<String> followsList ;
	

	public String every=""  ;
	
	public String localAt ; 
	
	public TWSJobStatement(String id_){
		this.followsList = new LinkedList<String>() ;
		this.id = id_ ;
		
	}
	
	public void addFollow(String follow){
		this.followsList.add(follow) ;
	}
 
	
	
	//cas spécial dans le cas ou un job doit attendre l'exécution de plusieurs jobs
	public boolean hasMultipleFollows() {
		return this.followsList.size()>1 ;
	}
	
	
	public boolean sameChainFollows(){
		boolean retour = true ;
		if(this.followsList.size()==0){
			
		}else{
			for(String follow : this.followsList){
				if(follow.split("#").length>1){
					retour = false ;
					break ;
				}
			}
		}
		return retour ;
	}
	
	
	
	public void setPrevious(String chainId){
		if (this.followsList.size()==0 || this.followsList.size()> 1){
			//Launcher.logger.warn("Multiple Follow, CC Michael Koch") ;
		}else{
			
			String follows = this.followsList.getFirst() ;
			if (follows.contains(".")){
				//Launcher.logger.warn("@ follows, CC Michael Koch") ; 
			}else{
				JobChainNode state = Launcher.states.get(Launcher.checkJobId(chainId, follows)) ;
				if(state!=null){
				
				state.setNextState(id.split("#")[1]) ;
				}else{
					
					}
			}
		}
	}
	
	
	public JobChainNode setRecovery(JobChainNode state){
		Recovery rec = Launcher.recoveries.get(id) ;
		JobChainNode retour = null ;
		if (rec==null){
			Launcher.logger.info("no recovery "+ id +" found") ;
		}else{
			//Launcher.logger.info(id + " "+rec.type) ;
		
		switch (rec.type){
		case CONTINUE : state.setErrorState((String)state.getNextState()) ;
						break ;
		
		case STOP : state.setErrorState("end_error") ;
					break ;
		case STOP_AFTER : 
			
							retour = new JobChainNode() ;
							retour.setErrorState("end_error") ;
							retour.setNextState("end_succ") ;
							retour.setState(rec.after.split("#")[1]) ;
							retour.setJob(rec.after.split("#")[1]) ;
							state.setErrorState(rec.after.split("#")[1]) ;
							//jc.getJobChainNodeJobChainOrJobChainNodeEnd().add(retour) ;
							break ;
		case RERUN : 	//Launcher.logger.info("yiiiihaaaa "+this.id) ;
			
						Job job = Launcher.jobs.get(this.id) ;
					job.getDelayOrderAfterSetback().add(Recovery.createDelay()) ;
					break ;
					
		case CONTINUE_AFTER :	
							retour= new JobChainNode() ;
								retour.setErrorState("end_error") ;
								retour.setNextState(state.getNextState()) ;
								retour.setState(rec.after.split("#")[1]) ;
								retour.setJob(rec.after.split("#")[1]) ;
								state.setErrorState(rec.after.split("#")[1]) ;
								//jc.getJobChainNodeJobChainOrJobChainNodeEnd().add((Object)recState1) ;
								break ;
	
								
		}
		
		}
		return retour ;
	}
	
	
	public String toString(){
		String follows ="" ;
		for(String follow:this.followsList)
			follows += "FOLLOWS "+follow+"\n" ; 
			return "ID "+this.id+"\n"+follows+"multiple follows "+this.hasMultipleFollows()+"\n"+"same chain follows :" +this.sameChainFollows() ;
	}
	
	public void toEvery(String chain) {
		Order order = new Order() ; 
		order.setId(id.split("#")[1]+"_every") ;
		RunTime rt = new RunTime() ;
		Period period = new Period() ;
		period.setAbsoluteRepeat(Launcher.normalizeTime(every)) ;
		rt.getPeriod().add(period) ;
		order.setRunTime(rt) ;
		Launcher.marshallOrder(chain, id.split("#")[1]+"_every", order) ;
		
	}
	

	
	public boolean hasEvery(){
		return  !(this.every.equals("")) ;
	}
	
}
