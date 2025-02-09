package wesbank.integration.steps;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.github.mkolisnyk.cucumber.runner.AfterSuite;
import com.github.mkolisnyk.cucumber.runner.BeforeSuite;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import wesbank.integration.utils.API_Data_Functions;
import wesbank.integration.utils.ExtentReporter;

public class Hooks {

	public API_Data_Functions data = new API_Data_Functions();
	private static final Config API_Config = ConfigFactory.parseResources("./src/test/resources/API_Config.properties");
	
	public static ExtentReporter extentReportWriter = new ExtentReporter();

	@BeforeSuite
	public void beforeScenario() throws Exception {

		System.out.println("@@@@@@@@@@@@@@@@Before Hook running.......");
		Path result = null;

		try {
			result = Files.move(
					Paths.get(API_Config.getString("logDir") + API_Config.getString("csvName")
							+ API_Config.getString("csvPostfix")),
					Paths.get(API_Config.getString("logDir") + API_Config.getString("csvName") + "_"
							+ API_Data_Functions.getStringDate() + API_Config.getString("csvPostfix")));
		} catch (IOException e) {
			System.out.println("Exception while moving file: " + e.getMessage());
		}

		if (result != null) {
			System.out.println("File moved successfully.");
		} else {
			System.out.println("File movement failed.");
		}

		try {
			result = Files.move(
					Paths.get(API_Config.getString("logDir") + API_Config.getString("logName")
							+ API_Config.getString("logPostfix")),
					Paths.get(API_Config.getString("logDir") + API_Config.getString("logName") + "_"
							+ API_Data_Functions.getStringDate() + API_Config.getString("logPostfix")));
		} catch (IOException e) {
			System.out.println("Exception while moving file: " + e.getMessage());
		}

		if (result != null) {
			System.out.println("File moved successfully.");
		} else {
			System.out.println("File movement failed.");
		}
		
		try {
			String sys_date = API_Data_Functions.getStringDate();
			result = Files.move(
					Paths.get(API_Config.getString("logDir") + API_Config.getString("reportName")
							+ API_Config.getString("reportPostFix")),
					Paths.get(API_Config.getString("logDir") + API_Config.getString("reportName") + "_" + sys_date
							+ API_Config.getString("reportPostFix")));
		} catch (Exception e) {
			System.out.println("Exception while moving file: " + e.getMessage());
		}

	}

	@AfterSuite
	public void afterScenario() throws Exception {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@After Hook running.......");
		extentReportWriter.ExtentReporter(API_Config.getString("logDir") + API_Config.getString("csvName")
				+ API_Config.getString("csvPostfix"));
	}
}