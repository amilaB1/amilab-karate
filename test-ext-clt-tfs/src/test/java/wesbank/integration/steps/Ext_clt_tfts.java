package wesbank.integration.steps;

import io.cucumber.java.en.Given;
import wesbank.integration.utils.API_Utility_Functions;
import wesbank.integration.utils.ExtentClass;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;

public class Ext_clt_tfts extends Scenario {
	public int excelCount;

	@Given("That wesbank_external_clt_tfs \\( {string} ) service is up and running")
	public void that_exposure_service_is_up_and_running(String serviceName) throws Exception {

		excelCount = apiUtils.readExcelReturnCount(external.getString("root.serviceName." + serviceName + ".testData"),
				external.getString("root.serviceName." + serviceName + ".sheetName"), logger);
		this.serviceName = serviceName;
		for (int testID = 0; excelCount != testID;) {
			if (excelCount == testID) {
				logger.info(" Done....");
				break;
			} else {
				while (excelCount != testID) {
					testID++;
					i_have_testdata_in_excel(external.getString("root.serviceName." + serviceName + ".testData"),
							external.getString("root.serviceName." + serviceName + ".sheetName"), testID, logger);
					if (apiDto.excelRequestMap.get("RunMode").equals("ON")) {
						try {
							i_map_the_raw_test_data_to_a_XML_request();
							i_submit_the_xml_request_to_the_service();
							i_assert_the_xml_service_response();
							logger.debug(
									"\n\n_______________________________________________________________Test Completed_______________________________________________________________\n\n");
						} catch (Exception e) {
							logger.debug(
									"\n\n_______________________________________________________________Test Failed________________________________________________________________\n\n");
							throw new RuntimeException(e);
						}
					} else {
						logger.info("Skipping scenario : " + apiDto.excelRequestMap.get("scenario"));
					}
				}
			}
		}
	}

	public void i_have_testdata_in_excel(String destFile, String serviceName, int testID, Logger logger)
			throws Exception {
		apiDto.excelRequestMap = null;
		apiDto.excelRequestMap = apiUtils.readExcelReturnMap(destFile, serviceName, testID, logger);
		extentClass.setFeature(serviceName);
		extentClass.setScenario(apiDto.excelRequestMap.get("scenario"));
		extentClass.setBddSyntax(apiDto.excelRequestMap.get("BDDSyntax"));
		if (!apiUtils.isBDDComplient(apiDto.excelRequestMap, logger)) {
			logger.error(
					"\t\t ####### EXITING, kindly update (Given When Then) syntax in BDDSyntax field for below scenario####### \n\n\t\t\t\t\t\t\t\t "
							+ serviceName + " : " + apiDto.excelRequestMap.get("scenario") + "\n");
			throw new RuntimeException(
					"Exiting Scenario, kindly update (Given When Then) syntax in BDDSyntax field. :) ");
		}

		logger.debug("\t\t ####### SERVICE   : " + serviceName);
		logger.debug("\t\t ####### SCENARIO  : " + apiDto.excelRequestMap.get("scenario") + "\n");
		logger.debug("\t\t ####### BDDSyntax : " + apiDto.excelRequestMap.get("BDDSyntax" + "") + "\n\n\n");

	}

	private void i_map_the_raw_test_data_to_a_XML_request() throws Exception {
		body = null;

		body = API_Utility_Functions.parseXML(external.getString("root.serviceName." + this.serviceName + ".filename"),
				apiDto.excelRequestMap);

	}

	private void i_submit_the_xml_request_to_the_service() throws IOException {
		response = apiUtils.restAssured(body, apiDto.excelRequestMap, logger);
	}

	private void i_assert_the_xml_service_response() throws Exception {
		List<ExtentClass> results = apiUtils.xmlAssertor(apiDto.excelRequestMap, response, extentClass);

		apiUtils.writeToCsv(results, "./API_ExtentData.json");
	}

}
