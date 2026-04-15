package util;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportCall {
	
	//static class with object
	private static ExtentReports extent;
	
	//method with static having ExtentReports class return type 
	public static ExtentReports getInstance() {
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
		if(extent == null) {
			String path = System.getProperty("user.dir") + "\\extentreport\\extentrpt_" +timestamp+".html";
			ExtentSparkReporter spark = new ExtentSparkReporter(path);
			spark.config().setReportName("Automation Testing");
			spark.config().setDocumentTitle("Test");
			extent = new ExtentReports();
			extent.attachReporter(spark);
			extent.setSystemInfo("Windows", "QA");
			
			
			
		}
		
		return extent;
	}
	


}
