package wesbank.integration.utils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import io.cucumber.datatable.dependency.com.fasterxml.jackson.databind.ObjectMapper;

public class ExtentReporter {

	//private static final Config config = ConfigFactory.parseResources("API_Config.properties");
	public API_Utility_Functions apiUtils = new API_Utility_Functions();
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest test;

	public void ExtentReporter(String string) throws Exception {

		LinkedList<String> list = apiUtils.fileReader(string);

		System.out.println("In Writting Report method. ExtentReporter");

		htmlReporter = new ExtentHtmlReporter("./src/test/resources/reports/API_Report.html");

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		extent.setSystemInfo("OS", "Windows");

		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle("test_integration_osb_wesbank_external_bus_vehicle_services");
		htmlReporter.config().setReportName("test_integration_osb_wesbank_external_bus_vehicle_services");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

		ObjectMapper mapper = new ObjectMapper();

		list.forEach(lines -> {

			ExtentClass obj;
			try {
				obj = mapper.readValue(lines, ExtentClass.class);
				System.out.println("In list.forEach :" + lines);
				if (obj.getType().equals("Test")) {

					test = extent.createTest(obj.getFeature() + "  |  " + obj.getScenario());
					test.assignCategory(obj.getFeature());

					if (obj.getStatus().contentEquals("Passed")) {
						test.log(Status.PASS, MarkupHelper.createLabel(" Expected Value : " + obj.getExpectedValue()
								+ " == " + obj.getActualValue() + " : Actual Value", ExtentColor.GREEN));
					} else {
						test.log(Status.FAIL, MarkupHelper.createLabel(" Expected Value : " + obj.getExpectedValue()
								+ " != " + obj.getActualValue() + " : Actual Value", ExtentColor.RED));
					}
				} else {

		test.createNode(obj.getScenario());

					if (obj.getStatus().contentEquals("Passed")) {
						test.log(Status.PASS, MarkupHelper.createLabel(" Expected Value : " + obj.getExpectedValue()
								+ " == " + obj.getActualValue() + " : Actual Value", ExtentColor.GREEN));
					} else {
						test.log(Status.FAIL, MarkupHelper.createLabel(" Expected Value : " + obj.getExpectedValue()
								+ " != " + obj.getActualValue() + " : Actual Value", ExtentColor.RED));
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		extent.flush();

	}
}