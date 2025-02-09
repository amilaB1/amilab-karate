package wesbank.integration.steps;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import wesbank.integration.utils.API_Data_Functions;
import wesbank.integration.utils.ExtentReporter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



@RunWith(Cucumber.class)
@CucumberOptions(stepNotifications = true, strict = true, features = "./src/test/java/wesbank/integration/features/", glue = {
		"wesbank.integration.steps" },plugin = {"json:target/cucumber.json","html:target/site/cucumber-pretty"})

public class TestRunner {
	public static final String logDir = "./src/test/resources/reports/",

			reportName = "API_Report", reportPostFix = ".html", jsonName = "API_ExtentData", jsonPostfix = ".json",
			logName = "API_Logfile", logPostFix = ".log";

	public API_Data_Functions data = new API_Data_Functions();
	public static ExtentReporter extentReportWriter = new ExtentReporter();

	
	
	/*
	 * @BeforeClass public static void beforeScenario() throws Exception {
	 * System.out.println("Before Hook Envoked.......");
	 * 
	 * try { System.out.println("! Deleting File From The Configured Path !");
	 * Files.delete(Paths.get((logDir) + (logName) + (logPostFix)));
	 * Files.delete(Paths.get((logDir) + (jsonName) + (jsonPostfix))); } catch
	 * (Exception e) {
	 * 
	 * }
	 * 
	 * }
	 */

	@AfterClass
	public static void afterScenario() throws Exception {
		System.out.println("After Hook Envoked.......");

		extentReportWriter.ExtentReporter("./API_ExtentData.json");

		Path log = null, json = null, report = null;

		String sys_date = API_Data_Functions.getStringDate();

		// Copy Extent and add date
		try {
			json = Files.copy(Paths.get(("./API_ExtentData.json")),
					Paths.get((logDir) + (jsonName) + sys_date + (jsonPostfix)));

		} catch (Exception e) {
			System.out.println("Exception while copying file: " + e.getMessage());
		}

		if (json != null) {
			System.out.println("Extent data File copied successfully.");
		} else {
			System.out.println("Extent data File copied failed.");
		}

		// Copy logfile and add date
		try {
			log = Files.copy(Paths.get((logDir) + (logName) + (logPostFix)),
					Paths.get((logDir) + (logName) + "_" + sys_date + (logPostFix)));
		} catch (Exception e) {
			System.out.println("Exception while moving file: " + e.getMessage());
		}

		if (log != null) {
			System.out.println("log File moved successfully.");
		} else {

			System.out.println("log File movement failed.");
		}

		// Copy Html and add date
		
		try {

			report = Files.move(Paths.get((logDir) + (reportName) + (reportPostFix)),
					Paths.get( (logDir) + (reportName) + "_" + sys_date + (reportPostFix)));
		} catch (Exception e) {
			System.out.println("Exception while moving file: " + e.getMessage());
		}

		if (report != null) {
			System.out.println("HTML File moved successfully.");
		} else {
			System.out.println("HTML File movement failed.");
		}
	}
}