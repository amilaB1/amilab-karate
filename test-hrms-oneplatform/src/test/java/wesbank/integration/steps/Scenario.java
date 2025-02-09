package wesbank.integration.steps;

import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigMergeable;

import io.restassured.response.Response;
import wesbank.integration.utils.APIDto;
import wesbank.integration.utils.API_Data_Functions;
import wesbank.integration.utils.API_Utility_Functions;
import wesbank.integration.utils.ExtentClass;
import wesbank.integration.utils.ExtentReporter;

import com.typesafe.config.Config;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Scenario {
	public static final Logger logger = LoggerFactory.getLogger(Scenario.class);
	public static final Config config = ConfigFactory.parseResources("API_Config.properties");
	public static final Config API_Config = ConfigFactory.parseResources("API_Config.properties");
	public static final Config HRMS = ConfigFactory.parseResources("application.conf");
	
	

	public API_Utility_Functions apiUtils = new API_Utility_Functions();
	public API_Data_Functions data = new API_Data_Functions();

	public ExtentReporter extClass = new ExtentReporter();

	public ExtentClass extentClass = new ExtentClass();

	public APIDto apiDto = new APIDto();
	public String serviceName = null;
	public String body = null;

	public Response response;
	public HashMap<String, String> tokenServiceMap = new HashMap<String, String>();
	public LinkedHashMap<String, String> scenarioMap = new LinkedHashMap<String, String>();

	public void Scenario() {
		extentClass.setStatus(null);
		extentClass.setType(null);
	}
}