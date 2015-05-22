package org.jobscheduler.converter.tws;

import java.math.BigInteger;

import org.jobscheduler.converter.tws.scheduler.Job.DelayOrderAfterSetback;
import org.jobscheduler.converter.tws.scheduler.JobChain.JobChainNode;

public class Recovery {
	
	
	
	public String option ; 
	public String after ;
	public RecovryType type ;
	public String state ;
	
	
	
	
	
	public void setRecoveryType(){
		
		switch (this.option){
		case  "rerun" : 
						this.type =  RecovryType.RERUN ;
						break ;
		case "stop"   : if(this.after != null){
							this.type = RecovryType.STOP_AFTER ;
						}else{
							this.type = RecovryType.STOP ;
						}
						break ;
		case "continue" :if(this.after != null){
							this.type = RecovryType.CONTINUE_AFTER ;
						}else{
							this.type = RecovryType.CONTINUE ;
						}
						break ;
		
		}
		
	}
	
	
	public static DelayOrderAfterSetback  createDelay(){
		DelayOrderAfterSetback delay = new DelayOrderAfterSetback() ;
		delay.setSetbackCount(new BigInteger("1"));
		delay.setIsMaximum("yes") ;
		delay.setDelay("0") ;
		return delay ;
		
	}

	
	
	

}
