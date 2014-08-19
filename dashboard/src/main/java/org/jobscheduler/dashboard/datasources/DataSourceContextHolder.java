package org.jobscheduler.dashboard.datasources;






import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class DataSourceContextHolder  {
	
	
	
//	private static final ThreadLocal<DataSource> contextHolder = new ThreadLocal<DataSource>();
//
//	   
//	   @Inject
//		@Qualifier("toulouseDataSource")
//		static DataSource datasourceToulouse;
//		
//		
//		@Inject
//		@Qualifier("valbonneDataSource")
//		static DataSource datasourceValbonne;
//	   
//	   public static void setDataSource(DataSource dataSource) {
//	          if(dataSource == null){
//	        	  //TODO
//	                 System.out.println("it is null") ;private static final ThreadLocal<DataSource> contextHolder = new ThreadLocal<DataSource>();
	//
//	   
//	   @Inject
//		@Qualifier("toulouseDataSource")
//		static DataSource datasourceToulouse;
//		
//		
//		@Inject
//		@Qualifier("valbonneDataSource")
//		static DataSource datasourceValbonne;
//	   
//	   public static void setDataSource(DataSource dataSource) {
//	          if(dataSource == null){
//	        	  //TODO
//	                 System.out.println("it is null") ;
//	        	  contextHolder.set(datasourceToulouse);
//	          }else{
//	                 contextHolder.set(dataSource);
//	          }
//	         
//	   }
//
//	   public static DataSource getDataSource() {
//		   System.out.println("Context data source " +contextHolder.get()) ;
//	          return (DataSource) contextHolder.get();
//
//	   }
//
//	   public static void clearDataSource() {
//	          contextHolder.remove();
//	   }
//	   
//	   
//	   public static void switchDataSource(String datasource){
//		   clearDataSource();
//		   if(datasource.equals("Toulouse")){
//			   System.out.println("switeched to Toulouse") ;
//			   if(datasourceToulouse==null){
//				   System.out.println("MDR") ;
//			   }
//			   contextHolder.set(datasourceToulouse);
//			   }else{
//			   System.out.println("switched to Valbonne") ;
//			   if(datasourceValbonne==null){
//				   System.out.println("MDR") ;
//			   }
//			   contextHolder.set(datasourceValbonne);
//		   }
//	   }

//	        	  contextHolder.set(datasourceToulouse);
//	          }else{
//	                 contextHolder.set(dataSource);
//	          }
//	         
//	   }
//
//	   public static DataSource getDataSource() {
//		   System.out.println("Context data source " +contextHolder.get()) ;
//	          return (DataSource) contextHolder.get();
//
//	   }
//
//	   public static void clearDataSource() {
//	          contextHolder.remove();
//	   }
//	   
//	   


}
