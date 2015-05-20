package org.jobscheduler.converter.tws;

import java.math.BigInteger;
import java.util.LinkedList;

import org.jobscheduler.converter.tws.scheduler.Job;
import org.jobscheduler.converter.tws.scheduler.JobChain;
import org.jobscheduler.converter.tws.scheduler.JobChainNodeJobChain;
import org.jobscheduler.converter.tws.scheduler.ObjectFactory;
import org.jobscheduler.converter.tws.scheduler.Order;
import org.jobscheduler.converter.tws.scheduler.Param;
import org.jobscheduler.converter.tws.scheduler.Params;
import org.jobscheduler.converter.tws.scheduler.Script;
import org.jobscheduler.converter.tws.scheduler.Job.DelayOrderAfterSetback;
import org.jobscheduler.converter.tws.scheduler.Job.Monitor;
import org.jobscheduler.converter.tws.scheduler.JobChain.JobChainNode;

public class TWSJobStream {
	
	
	
	
	public LinkedList<TWSJobStatement> jobStream ;
	public String id ;
	public String globalAt ;
	public String  globalUntil ;
	public LinkedList<TWSOnStatement> onStatements ;	
	public LinkedList<String> opens ;
	public LinkedList<String> prevChains;
	public boolean isFollwed ;
	
	
	
	public String getChainId(){
		return this.id.split("#")[0] ;
	}
	
	public TWSJobStream (String id_){
		this.id= id_ ;
		this.jobStream= new LinkedList<TWSJobStatement>() ;
		this.onStatements = new LinkedList<TWSOnStatement>() ;
		this.opens = new LinkedList<String>() ;
		this.prevChains = new LinkedList<String>() ;
		this.isFollwed = false ;
	}
	
	public void addJobStatement(TWSJobStatement statement){
		this.jobStream.add(statement) ;
	}
	
	
	public boolean SameChain(){
		boolean retour = true  ;
		String chainId = this.getChainId() ;
		for (TWSJobStatement st : this.jobStream){
			retour = (st.id.split("#").length==1) || (st.id.split("#")[0].equals(chainId));
					if (!retour){
						break ;
					}
		}
		return retour ;
		
	}
	
	
	public void setPreviousChains(){
		for(String prev : this.prevChains){
			
			if(prev.contains(".")){
				Launcher.logger.info( prev +" "+prev.split("\\.").length) ;
		 String streamId = prev.split("\\.")[0] ;
		 TWSJobStream prevStream = Launcher.streams.get(streamId);
		 prevStream.isFollwed = true ;
			}
		}
		
	}
	
	
	
	public JobChain toJobChain(){
		JobChain jc = new JobChain() ;
		for(int i = 0 ; i< this.prevChains.size();i++){
			String prev = this.prevChains.get(i).split("\\.")[0].split("#")[1] ;
			JobChainNode previous = new JobChainNode() ;
			previous.setState(prev) ;
			previous.setJob("check_event_"+prev) ;
			if(i== this.prevChains.size()-1){
				if(this.opens.size()>0){
					String next = "checkOpenEvent_"+0 ;
					previous.setNextState(next) ;
				}else{
				String next = this.jobStream.getFirst().id.split("#")[1] ;
				previous.setNextState(next) ;
				}
			}else{
				String next = this.prevChains.get(i+1).split("\\.")[0].split("#")[1] ;
				previous.setNextState(next) ;
			}
			previous.setOnError("setback") ;
			jc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(previous);
		}
		
		
		for(int i = 0 ; i<this.opens.size() ;i++){
			opensToJobChain(i,this.opens.get(i)) ; 
			JobChainNode node = new JobChainNode() ;
			node.setState( "checkOpenEvent_"+i) ;
			node.setErrorState("end_error") ;
			node.setOnError("setback") ;
			node.setJob("check_open_event_"+i+"_"+this.id.split("#")[1]) ;
			if(i== this.opens.size()-1){
				
				String next = this.jobStream.getFirst().id.split("#")[1] ;
				node.setNextState(next) ;
				
			}else{
				String next = "checkOpenEvent"+(i+1) ;
				node.setNextState(next) ;
			}
			
			jc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(node);
				
			this.createOpenSubmitEvent(i) ;
			this.openToCheckEvent(i) ;
		
		}
		
		jc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd() ;
		jc.setName(id.split("#")[1]) ;
		for(TWSJobStatement st : this.jobStream){
			st.setPrevious(id) ;
			JobChainNode rec ;
			JobChainNode state = Launcher.states.get(st.id) ;
			if(!st.id.split("#")[0].equals(this.id.split("#")[0])){
				state.setJob("./../"+st.id.split("#")[0]+"/"+state.getJob()) ;
			}
			 rec = st.setRecovery(state) ;
			if (rec!=null){
				jc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(rec);
			}
			
			
		if(st.equals(this.jobStream.getLast())){
			if (!isFollwed){
				state.setNextState("end_succ") ;
			}else{
				state.setNextState("submit_event") ;
				JobChainNode submitState = new JobChainNode() ;
				submitState.setState("submit_event") ;
				submitState.setJob("submit_event_"+id.split("#")[1]) ;
				submitState.setNextState("end_succ") ;
				submitState.setErrorState("end_error") ;
				jc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(submitState) ;
				Launcher.submitEndChains.add(id) ;
			}
			
		}
		if(!st.hasEvery()){
		jc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(state);
		}else{
			JobChainNode submitEvent = new JobChainNode() ;
			submitEvent.setState(state.getState()) ;
			submitEvent.setErrorState(state.getErrorState()) ;
			submitEvent.setJob("submit_every_event_"+state.getJob()) ;
			submitEvent.setNextState(state.getNextState()) ;
			Launcher.submitEventsEvery.add(id.split("#")[0]+"#"+"every_event_"+state.getJob() ) ;
			jc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(submitEvent);
			st.toEvery(this.id.split("#")[0]+"#"+state.getJob()+"_every") ;
			if (st.localAt != null && this.globalAt==null){
			
			toJobChainEvery(state, rec,st.localAt) ;
			}
			
			if (st.localAt == null && this.globalAt!=null){
				
				toJobChainEvery(state, rec,this.globalAt) ;
				}
		}
		}
		
		JobChainNode err = new JobChainNode() ; 
		err.setState("end_error") ;
		JobChainNode succ = new JobChainNode() ; 
		succ.setState("end_succ") ;
		jc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(err) ;
		jc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(succ) ;
		
		return  jc;
	}
	
	public String toString(){
		
		return "ID :"+id+"\n Centralized chain : "+SameChain() ;
	}
	
	
	public  void normalizeNames(){
		LinkedList<String> jobNames = new LinkedList<String>() ; 
		for (TWSJobStatement st : this.jobStream  ){
			jobNames.add(st.id) ;
		}
		
		for (TWSJobStatement st : this.jobStream  ){
			LinkedList<String>  follows = st.followsList ;
			
			for(int i=0;i<follows.size();i++){
				if (!(follows.get(i).split("#").length>1)){
					follows.set(i,this.getWorktation(jobNames, follows.get(i))+"#"+follows.get(i) ) ;
				}
			}
			//Launcher.logger.info(st.id +" "+ st.followsList) ;
			
		}
		

	}
	
	
	public String getWorktation( LinkedList<String> jobs, String jobId){
		String retour ="" ; 
		
		for(String s : jobs ){
			if (s.split("#")[1].equals(jobId)){
				retour = s.split("#")[0] ; 
				break ;
			}
		}
		
		if(retour.equals("")){
			return this.id.split("#")[0] ;
		}
		
		return retour ;
		
	}
	
	

	
	public LinkedList<Order> onToOrder(){
		LinkedList<Order> retour = new LinkedList<Order>() ; 
	if(this.onStatements.size() > 0)
		for(TWSOnStatement on : this.onStatements){
			
			if(this.globalAt != null && on.onAt == null){
				on.onAt = this.globalAt ;
			}
			
			if(this.globalUntil != null && on.onUntil == null){
				on.onUntil = this.globalUntil ;
			}
			
			
			
			retour.add(on.toOrder()) ;
			
		}else{
			Order order = new Order() ; 
			
		}
	
		return retour ;
		
	}
	
	
	public  void toJobChainEvery(JobChainNode state,JobChainNode rec,String until){
		
		JobChain jc = new JobChain() ;
		jc.setName(state.getJob()+"_every") ;
		JobChainNode checkEvent = new JobChainNode() ;
		checkEvent.setState("checkEvent") ;
		checkEvent.setNextState(state.getState()) ;
		checkEvent.setOnError("setback") ;
		checkEvent.setErrorState("end_error") ;
		checkEvent.setJob("check_every_event_"+state.getJob()) ;
		Launcher.checkEventsEvery.add(id.split("#")[0]+"#"+ "every_event_"+state.getJob()) ;
		jc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(checkEvent) ;
		
		state.setErrorState(state.getErrorState()) ;
		state.setNextState("end_succ") ;
		jc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(state) ;
		jc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(rec) ;
		JobChainNode err = new JobChainNode() ;
		err.setState("end_error") ;
		JobChainNode succ = new JobChainNode() ;
		succ.setState("end_succ") ;
		jc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(err) ;
		jc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(succ) ;
		
		
		Launcher.MarshallJobChain(jc,this.id.split("#")[0] ) ;
	}
	
	
	public void opensToJobChain(int i,String jobId){
		JobChain jc = new JobChain() ;
		jc.setName(this.id.split("#")[1]+"_opens") ;
		jc.getFileOrderSource().add(Launcher.toFileOrder(jobId)) ;
		JobChainNode node = new JobChainNode() ;
		node.setState("submitOpenEvent_"+i) ;
		node.setErrorState("end_error") ;
		node.setNextState("end_succ") ;
		node.setJob("submit_open_event_"+i+"_"+this.id.split("#")[1]) ;
		JobChainNode error = new JobChainNode() ;
		error.setState("end_error") ;
		JobChainNode end = new JobChainNode() ;
		end.setState("end_succ") ;

		jc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(node) ;
		jc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(error) ;

		jc.getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(end) ;

		Launcher.MarshallJobChain(jc, this.id.split("#")[0]) ;
		this.createOpenSubmitEvent(i) ;
		
	}
	
	public void createOpenSubmitEvent(int open){
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
		idP.setValue("open_event_"+open+"_"+this.id.split("#")[1]) ;
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
		
		Launcher.marshallJob(this.id.split("#")[0]+"#"+"submit_open_event_"+open+"_"+this.id.split("#")[1],job) ;
	}
	
	
	public void openToCheckEvent(int open){
Job job = new Job() ;
		
		Params parameters = new Params() ;
		Param add = new Param() ;
		
		add.setName("scheduler_event_spec") ;
		add.setValue("//events[event/@event_id='"+"open_event_"+open+"_"+this.id.split("#")[1]+"']" ) ;
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
		
		Launcher.marshallJob(this.id.split("#")[0]+"#"+"check_open_event_"+open+"_"+this.id.split("#")[1],job) ;
	}

}
