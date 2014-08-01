package org.jobscheduler.dashboard.controller;

import java.io.Serializable;

	
	public class JobInfo implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		protected String jobName ; 
		protected String average ; 
		protected String exit ; 
		protected String duration ;
		protected String startTime ;
		protected String spoolerId ;
		
		
		public JobInfo(String jobName, String average, String exit,
				String duration,String startTime,String spoolerId) {
			super();
			this.jobName = jobName;
			this.average = average;
			this.exit = exit;
			this.duration = duration;
			this.startTime = startTime ;
			this.spoolerId = spoolerId ;
		}


		public String getSpoolerId() {
			return spoolerId;
		}


		public void setSpoolerId(String spoolerId) {
			this.spoolerId = spoolerId;
		}


		public String getStartTime() {
			return startTime;
		}


		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}


		public String getJobName() {
			return jobName;
		}


		public void setJobName(String jobName) {
			this.jobName = jobName;
		}


		public String getAverage() {
			return average;
		}


		public void setAverage(String average) {
			this.average = average;
		}


		public String getExit() {
			return exit;
		}


		public void setExit(String exit) {
			this.exit = exit;
		}


		public String getDuration() {
			return duration;
		}


		public void setDuration(String duration) {
			this.duration = duration;
		} 
		
	}
	
		


