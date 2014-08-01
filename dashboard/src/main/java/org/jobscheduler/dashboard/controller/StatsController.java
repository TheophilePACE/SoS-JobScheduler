package org.jobscheduler.dashboard.controller;

import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import org.json.JSONArray;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.sql.DataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import jaxb.scheduler.generated.Job;
import jaxb.scheduler.generated.RunTime;
import jaxb.scheduler.generated.Script;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.jobscheduler.dashboard.domain.SchedulerHistory;
import org.jobscheduler.dashboard.repository.SchedulerHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StatsController {

	@Autowired
	DataSource datasource;

	@Inject
	SchedulerHistoryRepository schedulerHistoryRepository;

	@RequestMapping(value = "/stats-test/getJobs", method = RequestMethod.GET)
	public @ResponseBody
	String[] getAllJobs() {
		String[] result = schedulerHistoryRepository.getAllJobs();
		return result;
	}
	
	
	
	/**
	 * gets the list of jobs which names match with the filter 
	 * @param jobName
	 * @return
	 */
	@RequestMapping(value = "/stats-test/searchJob", method = RequestMethod.GET)
	public @ResponseBody
	List<String> getJobByName(@RequestParam String jobName) {

		List<String> result = schedulerHistoryRepository.getJobLike(jobName);

		return result.subList(0, Math.min(result.size(), 20));

	}
	
	/**
	 * computes the number of jobs per step between two dates
	 * @param af
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/stats-test/activities", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody
	Data getJobActivitiesBetween2Dates(@RequestBody ActivityFields af,
			Model model) {
		Timestamp startTime = Timestamp.valueOf(af.getStartDate().substring(0,
				10)
				+ " " + af.getStartTime() + ":00");

		Timestamp endTime = Timestamp.valueOf(af.getEndDate().substring(0, 10)
				+ " " + af.getEndTime() + ":00");

		int step = af.getStep() * 60;
		Timestamp stepper = startTime;
		LinkedList<Integer> numbersList = new LinkedList<Integer>();
		LinkedList<String> labelsList = new LinkedList<String>();
		while (stepper.compareTo(endTime) <= 0) {
			int count = (schedulerHistoryRepository.getJobNumberbyDate(stepper));
			numbersList.add(count);

			labelsList.add("" + stepper);
			// incrementing stepper
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(stepper.getTime());
			cal.add(Calendar.SECOND, step);
			stepper = new Timestamp(cal.getTime().getTime());

		}

		double[] numbers = new double[numbersList.size()];
		String[] labels = new String[numbersList.size()];

		for (int i = 0; i < numbersList.size(); i++) {
			labels[i] = labelsList.get(i);
			numbers[i] = numbersList.get(i);
		}
		DataUnit du = new DataUnit();

		du.setData(numbers);
		du.setFillColor("rgba(151,187,205,0.2)");
		du.setStrokeColor("rgba(151,187,205,1)");
		Data data = new Data(new DataUnit[] { du }, labels);

		return data;
	}
	
	
	
	
	/**
	 * computes the execution time of a chosen job between 2 dates 
	 * @param af
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/stats-test/chosenJob2", method = RequestMethod.POST, headers = { "Accept=application/json" })
	public @ResponseBody
	Data getJobActivityBetween2Dates(@RequestBody ActivityFields af, Model model)
			throws ParseException {
		Timestamp start0 = Timestamp.valueOf(af.startDate.substring(0, 10)
				+ " 23:59:59");
		Timestamp end0 = Timestamp.valueOf(af.endDate.substring(0, 10)
				+ " 23:59:59");
		List<Timestamp> startTimes = schedulerHistoryRepository.getStartTimes(
				af.jobName, start0, end0);
		List<Timestamp> endTimes = schedulerHistoryRepository.getEndTimes(
				af.jobName, start0, end0);

		double[] durations = new double[startTimes.size()];
		DataUnit du = new DataUnit();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss.S");
		for (int i = 0; i < startTimes.size(); i++) {
			Timestamp start = startTimes.get(i);

			Timestamp end = endTimes.get(i);
			long s = start.getTime();
			long e = end.getTime();

			durations[i] = (e - s) / 1000;

		}

		String[] labels = new String[startTimes.size()];

		for (int i = 0; i < startTimes.size(); i++)
			labels[i] = startTimes.get(i) + "";
		du.setData(durations);
		du.setFillColor("rgba(151,187,205,0.2)");
		du.setStrokeColor("rgba(151,187,205,1)");
		du.setPointColor("rgba(151,187,205,1)");
		du.setPointHighlightFill("#fff");
		du.setPointStrokeColor("#fff");
		du.setPointHighlightStroke("rgba(151,187,205,1)");

		Data data = new Data(new DataUnit[] { du }, labels);

		return data;

	}

	/**
	 * computes the number of jobs in error between two dates
	 * @param af
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/stats-test/getErrors", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody
	Data getErrorJobsBetween2Dates(@RequestBody ActivityFields af, Model model) {

		Timestamp start = Timestamp.valueOf(af.startDate.substring(0, 10)
				+ " 00:00:00");
		Timestamp end = Timestamp.valueOf(af.endDate.substring(0, 10)
				+ " 00:00:00");
		Timestamp stepper = start;
		LinkedList<String> labels = new LinkedList<String>();
		LinkedList<Double> jobCounts = new LinkedList<Double>();
		LinkedList<Double> jobErrorCounts = new LinkedList<Double>();
		while (stepper.compareTo(end) <= 0) {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(stepper.getTime());
			cal.add(Calendar.HOUR, 24);
			Timestamp endDay = new Timestamp(cal.getTime().getTime());

			int count = schedulerHistoryRepository.getDayJobsCount(stepper,
					endDay);

			jobCounts.add((double) count);
			int errorCount = schedulerHistoryRepository.getErrorDayJobsCount(
					stepper, endDay);

			jobErrorCounts.add((double) errorCount);
			labels.add(stepper + "");
			stepper = endDay;

		}
		double[] ok = new double[labels.size()];
		double[] ko = new double[labels.size()];
		String[] labelsArray = new String[labels.size()];
		for (int i = 0; i < labels.size(); i++) {
			ok[i] = jobCounts.get(i);
			ko[i] = jobErrorCounts.get(i);
			labelsArray[i] = labels.get(i);

		}

		DataUnit du1 = new DataUnit();

		du1.setData(ok);
		du1.setFillColor("rgba(151,187,205,0.2)");
		du1.setStrokeColor("rgba(151,187,205,1)");

		DataUnit du2 = new DataUnit();

		du2.setData(ko);
		du2.setFillColor("rgba(254 ,25, 142,0.2)");
		du2.setStrokeColor("rgba(254 ,25, 142,0.8)");

		return new Data(du1, du2, 1, labelsArray);

	}

	
	/**
	 * gets the number of error jobs and classify them by exit codes 
	 * @param af
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/stats-test/byExitCodes", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody
	Data getErrorsNumberByExitCodes(@RequestBody ActivityFields af, Model model) {

		String[] colors = { "rgba( 0, 52, 154,", "rgba(255 ,50 ,55,",
				"rgba( 0 ,114 ,45,", "rgba(3,3,3,", "rgba(255, 144, 11,",
				"rgba(254, 25, 142,", "rgba( 180 ,255 ,0,", "rgba(255,215,0,",
				"rgba(128,240,240,", "rgba(160,82,45," ,"rgba(255.204.204,","rgba(255.179.102"} ;
		Timestamp start = Timestamp.valueOf(af.startDate.substring(0, 10)
				+ " 00:00:00");
		Timestamp end = Timestamp.valueOf(af.endDate.substring(0, 10)
				+ " 00:00:00");
		BigDecimal[] exitCodes = schedulerHistoryRepository.getExitCodes(start,
				end);

		Data data = new Data(new DataUnit[exitCodes.length], null);
		int inc = 0;
		for (BigDecimal i : exitCodes) {

			Timestamp stepper = start;
			LinkedList<Integer> counts = new LinkedList<Integer>();
			LinkedList<String> labels = new LinkedList<String>();
			Random rn = new Random();

			while (stepper.compareTo(end) <= 0) {
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(stepper.getTime());
				cal.add(Calendar.HOUR, 24);
				labels.add(stepper + "");
				Timestamp endDay = new Timestamp(cal.getTime().getTime());
				int count = schedulerHistoryRepository
						.getErrorDayJobsCountAndExitCode(stepper, endDay, i);
				counts.add(count);
				stepper = endDay;
			}

			double[] countsArray = new double[counts.size()];
			String[] labelsArray = new String[counts.size()];
			for (int j = 0; j < counts.size(); j++) {
				countsArray[j] = counts.get(j);
				labelsArray[j] = labels.get(j);
			}

			DataUnit du = new DataUnit();
			du.setData(countsArray);
			du.setLabel("Exit code " + i);
			du.setFillColor("rgba(255,255,255,0)");
			du.setStrokeColor(colors[inc] + "3)");
			data.getDatasets()[inc] = du;
			data.setLabels(labelsArray);
			inc++;

		}

		return data;
	}

	/**
	 * get the longest jobs between two days
	 * @param tf
	 * @return
	 */
	@RequestMapping(value = "/stats-test/getTops", method = RequestMethod.POST)
	public @ResponseBody
	JobInfo[] getLongestJobs(@RequestBody TopFields tf) {

		JobInfo[] table = new JobInfo[tf.top];
		int incr = 0;
		try {
			Connection conn = datasource.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT \"JOB_NAME\", \"END_TIME\"-\"START_TIME\" AS duration,\"EXIT_CODE\",\"START_TIME\",\"SPOOLER_ID\",\"LOG\" FROM public.scheduler_history "
							+ "WHERE \"START_TIME\">'"
							+ tf.start
							+ "' AND"
							+ "\"END_TIME\" < '"
							+ tf.end
							+ "' ORDER BY duration DESC LIMIT " + tf.top + ";");
			while (rs.next()) {
				Statement st2 = conn.createStatement();
				ResultSet av = st2
						.executeQuery("SELECT AVG(\"END_TIME\"-\"START_TIME\") FROM public.scheduler_history WHERE (\"JOB_NAME\"= '"
								+ rs.getString(1)
								+ "' AND \"END_TIME\" IS NOT NULL AND \"START_TIME\">'"
								+ tf.start
								+ "' AND"
								+ "\"END_TIME\" < '"
								+ tf.end + "') ;");
				
				while (av.next()) {
					table[incr] = new JobInfo(rs.getString(1), av.getString(1)
							.substring(0, 8), rs.getString(3), rs.getString(2),
							rs.getString(4), rs.getString(5));
				}
				av.close();
				st2.close();
				incr++;
			}
			rs.close();
			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	
		return table;
	}
	
	
	
	
	
	
	
	
	//TODO

	@RequestMapping(value = "/newjob/xml", headers = { "Content-type=application/json" }, method = RequestMethod.POST)
	public @ResponseBody
	PersonalString control(@RequestBody Job myJob, Model model) {
		String result = null;

		JAXBContext jaxbContext;
		try {

			myJob.setRunTime(new RunTime());
			jaxbContext = JAXBContext.newInstance(Job.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			OutputStream out = new ByteArrayOutputStream();
			jaxbMarshaller.marshal(myJob, out);
			result = out.toString();

		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return new PersonalString(result);
	}

	@RequestMapping(value = "/newjob/saveJob", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody
	PersonalString control2(@RequestBody Job myJob, Model model) {
		String result = null;

		JAXBContext jaxbContext;
		try {

			myJob.setRunTime(new RunTime());
			jaxbContext = JAXBContext.newInstance(Job.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			File genFile = new File(
					"/home/cloud/git/SoS-JobScheduler/dashboard/generated/"
							+ myJob.getName() + ".job.xml");
			jaxbMarshaller.marshal(myJob, genFile);

		} catch (JAXBException e1) {
			e1.printStackTrace();
		}

		return new PersonalString(result);
	}

	

}
