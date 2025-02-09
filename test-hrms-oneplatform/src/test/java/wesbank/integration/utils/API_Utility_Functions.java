package wesbank.integration.utils;

import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.jcabi.xml.XMLDocument;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.cucumber.datatable.dependency.com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.XmlConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static io.restassured.config.SSLConfig.sslConfig;

public class API_Utility_Functions {

//	public ConfigFileReader configFileReader = new ConfigFileReader();
	public static JSONObject jsonObject = new JSONObject();
	public static JSONArray jsonArray = new JSONArray();
	public JSONObject targetJSONObject = new JSONObject();
	public static JSONArray targetJSONArray = new JSONArray();
	public static int debug_int = 0;
	public static Map<String, String> target = new LinkedHashMap<String, String>();
	public static Map<String, Integer> targetArrayIndex = new LinkedHashMap<String, Integer>();
//	private static final Config config = ConfigFactory.parseResources("config.conf");
	public static Map<String, String> targetLivesIn = new LinkedHashMap<String, String>();
	private String[] assertionList = null;
	private String[] tmp = null;
	private String operation = null;
	private Map<String, String> results_map;
	private List<ExtentClass> list_results = new LinkedList<ExtentClass>();
	private String assertionResults;
	private int x = 0;
	private static String results = null;
	public int testID;
	public static String base, xlsEnvironment = null;
	int code = 0;

	public String GenerateNumbericNumbers(int iLength) {
		String outValues = "";
		String nNumbers = "01234567890123456789";
		for (int i = 0; i <= iLength; i++) {
			outValues = nNumbers.substring(0, iLength);
		}
		return outValues;
	}

	public String GenerateRandomString(int iLength) {
		String outValues = "";
		String nNumbers = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 0; i <= iLength; i++) {
			outValues = nNumbers.substring(0, iLength);
		}
		return outValues;
	}

	public String ReturnValuesFromAlphanumeric(String nNumbers) {
		String outValues = "", sHoldValues = "";
		String sDigits = "0123456789";
		for (int i = 0; i < nNumbers.length(); i++) {
			outValues = nNumbers.substring(i, i + 1);
			if (sDigits.contains(outValues)) {
				sHoldValues += outValues;
			}
		}
		return sHoldValues;
	}

	public String GetRandomPassword(int itotalNumber) {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz"
				+ "!@#$%&*()_+-=[]|,./?><";
		StringBuilder sb = new StringBuilder(itotalNumber);
		for (int i = 0; i < itotalNumber; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}
		return sb.toString();
	}

	public LinkedHashMap<String, String> readExcelReturnMap(String destinationFile, String sheetName, String column_id)
			throws Exception {

		String scenario = column_id;

		Fillo fillo = new Fillo();
		LinkedHashMap<String, String> columnValueTestDataMap = null;
		com.codoid.products.fillo.Connection connection = fillo.getConnection(destinationFile);

		String strQuery = "Select * from " + sheetName + " where TestID =   '" + scenario.valueOf(testID) + "'";
		System.out.println(strQuery);

		Recordset recordset = connection.executeQuery(strQuery);
		columnValueTestDataMap = new LinkedHashMap<String, String>();

		while (recordset.next()) {
			for (String columnName : recordset.getFieldNames()) {
				columnValueTestDataMap.put(columnName, recordset.getField(columnName));
			}
		}

		recordset.close();
		connection.close();

		return columnValueTestDataMap;
	}

	public Integer readExcelReturnCount(String destinationFile, String sheetName, org.slf4j.Logger logger)
			throws Exception {
		int count = 0;
		Fillo fillo = new Fillo();
		com.codoid.products.fillo.Connection connection = fillo.getConnection(destinationFile);
		String strQuery = "Select * from " + sheetName;
		Recordset recordset = connection.executeQuery(strQuery);
		count = recordset.getCount();
		recordset.close();
		connection.close();
		return count;
	}

	public LinkedHashMap<String, String> readExcelReturnMap(String destinationFile, String sheetName, Integer testID,
			org.slf4j.Logger logger) throws Exception {

		Fillo fillo = new Fillo();
		LinkedHashMap<String, String> columnValueTestDataMap = null;
		com.codoid.products.fillo.Connection connection = fillo.getConnection(destinationFile);

		String strQuery = "Select * from " + sheetName + " where TestID = " + testID;

		// logger.debug("\n\n\n\n" + "####### EXCEL QUERY : " + strQuery +"\n\n");

		Recordset recordset = connection.executeQuery(strQuery);
		columnValueTestDataMap = new LinkedHashMap<String, String>();

		while (recordset.next()) {
			for (String columnName : recordset.getFieldNames()) {
				columnValueTestDataMap.put(columnName, recordset.getField(columnName));
			}
		}

		recordset.close();
		connection.close();

		return columnValueTestDataMap;
	}

	public enum TARGET {
		ARRAY, OBJECT
	}

	public boolean isBDDComplient(LinkedHashMap<String, String> exceKeyVal, org.slf4j.Logger logger) {
		boolean complient = false;

		try {
			if ("".equals(exceKeyVal.get("BDDSyntax")))
				complient = false;

			if (exceKeyVal.get("BDDSyntax").toLowerCase().contains("given"))
				complient = true;

			if (exceKeyVal.get("BDDSyntax").toLowerCase().contains("when"))
				complient = true;

			if (exceKeyVal.get("BDDSyntax").toLowerCase().contains("then"))
				complient = true;
		} catch (Exception e) {
			return false;
		}

		return complient;

	}

	public JSONObject MapToJson(final LinkedHashMap<String, String> excelRequestMap) {

		API_Utility_Functions thisClassObject = new API_Utility_Functions();

		Set<String> toIterateMap = excelRequestMap.keySet();
		Object[] toIterateSet = toIterateMap.toArray();

		for (int v = 0; v < toIterateSet.length + 1; v++) {

			thisClassObject.targetJSONArray = jsonArray;
			thisClassObject.targetJSONObject = jsonObject;

			if (v == toIterateSet.length)
				continue;

			String column = (String) toIterateSet[v];
			String value = excelRequestMap.get(column);

			if (!column.startsWith("$")) {
				continue;
			}

			String[] keys = column.split("\\.");
			Boolean isArray = true;
			TARGET recentTarget = null;
			recentTarget = recentTarget.OBJECT;

			for (int j = 0; j < keys.length - 1; j++) {
				if (!target.keySet().contains(keys[j])) {
					if (!isArray(keys[j])) {
						if (recentTarget == recentTarget.OBJECT) {
							thisClassObject.targetJSONObject.put(keys[j], new JSONObject());
							target.put(keys[j], "Object{}");
							targetLivesIn.put(keys[j], "Object");
							recentTarget = recentTarget.OBJECT;
						} else {
							thisClassObject.targetJSONArray.put(new JSONObject());
							target.put(keys[j], "Object{}");
							targetLivesIn.put(keys[j], "Array");
							targetArrayIndex.put(keys[j], (thisClassObject.targetJSONArray.length() - 1));
							recentTarget = recentTarget.OBJECT;
						}
					} else {
						if (recentTarget == recentTarget.OBJECT) {
							thisClassObject.targetJSONObject.put(keys[j], new JSONArray());
							target.put(keys[j], "Array[]");
							targetLivesIn.put(keys[j], "Object");
							targetArrayIndex.put(keys[j], (thisClassObject.targetJSONObject.length() - 1));
							recentTarget = recentTarget.ARRAY;
						} else {
							thisClassObject.targetJSONArray.put(new JSONArray());
							target.put(keys[j], "Array[]");
							targetLivesIn.put(keys[j], "Array");
							targetArrayIndex.put(keys[j], (thisClassObject.targetJSONArray.length() - 1));
							recentTarget = recentTarget.ARRAY;
						}
					}
				} else {
					if (isArray(target.get(keys[j]))) {
						recentTarget = recentTarget.ARRAY;
					} else {
						recentTarget = recentTarget.OBJECT;
					}
				}

				if (recentTarget == recentTarget.ARRAY) {
					if (targetLivesIn.get(keys[j]) == "Array") {
						thisClassObject.targetJSONArray = (JSONArray) thisClassObject.targetJSONArray
								.get(targetArrayIndex.get(keys[j]));
					} else {
						thisClassObject.targetJSONArray = (JSONArray) thisClassObject.targetJSONObject
								.getJSONArray(keys[j]);
					}
				} else {
					if (targetLivesIn.get(keys[j]) == "Array") {
						thisClassObject.targetJSONObject = (JSONObject) thisClassObject.targetJSONArray
								.getJSONObject(targetArrayIndex.get(keys[j]));
					} else {
						thisClassObject.targetJSONObject = (JSONObject) thisClassObject.targetJSONObject.get(keys[j]);
					}
				}
			}

			if (recentTarget.equals("ARRAY")) {
			} else {
				thisClassObject.targetJSONObject.put(keys[keys.length - 1], value);
			}
		}
		return thisClassObject.targetJSONObject;
	}

	private boolean isArray(String value) {
		return value.contains("[");
	}

	public Response restAssured(String body, Map excelKeysAndValues, org.slf4j.Logger logger) throws IOException {

		Map<String, String> headers = new HashMap<String, String>();
		String[] headers_list = excelKeysAndValues.get("Headers").toString().split(",");
		for (String header : headers_list) {
			String[] tmp = header.split(":");
			headers.put(tmp[0], tmp[1]);
			logger.debug("\t\t ####### HEADER    : " + tmp[0] + " = " + tmp[1] + " \n\t\t\t\t\t" + "\n ");
		}
		RestAssured.reset();
		xlsEnvironment = excelKeysAndValues.get("Environment").toString();
		switch (xlsEnvironment) {
    	case "QA":
    	base="https://osbsqa.wesbank.co.za";
    	
        break;

    	case "WLS":
    	base="https://wlsqa.wesbank.co.za";
        break; 
       
    	default:
        System.out.println("Environment Not Set In Excel");
		}
		RestAssured.baseURI = base;

		logger.debug("\t\t ####### baseURI   : " + base + "\n");

		RestAssured.basePath = excelKeysAndValues.get("basePath").toString();

		logger.debug("\t\t ####### basePath  : " + excelKeysAndValues.get("basePath").toString() + "\n\n");

		logger.debug("\n\n\n" + "####### REQUEST BODY ####### \n\t\t\t\t" + "\n" + body + "\n");
		Response jsonResponse = null;
		JSONObject obj = null;

		////////// Execution method
		try {
			RestAssured.config = RestAssured.config()
					.sslConfig(sslConfig().with().trustStore(KeyStoreLocation.JKSFILE, KeyStoreLocation.JKSPASSWORD)
							.trustStoreType(FileType.JKS.name())
							.keyStore(KeyStoreLocation.P12FILE, KeyStoreLocation.P12PASSWORD)
							.keystoreType(FileType.PKCS12.name()));

			jsonResponse = RestAssured.given().headers(headers).body(body).when().post();
			logger.debug("\n\n\n" + "####### RESPONSE BODY ####### " + "\n\n" + jsonResponse.asString() + "\n\n\n"
					+ "####### RESPONSE CODE  : " + jsonResponse.getStatusCode() + "\n\n" + "####### RESPONSE TIME  : " 
					+ jsonResponse.getTimeIn(TimeUnit.SECONDS)+ " Second(s)" +  "\n\n");

		} catch (Exception e) {
			logger.debug("\n\n\n\t\t #######CAUGHT EXCEPTION IN  METHOD ####### :\n\n\n\t\t\t\t\t\t\t" + e.toString()
					+ "\n");
			throw new RuntimeException(e);
		}
		return jsonResponse;
	}

	public Response postXML(String body, Map excelKeysAndValues) throws IOException {

		Map<String, String> headers = new HashMap<String, String>();
		String[] headers_list = excelKeysAndValues.get("Headers").toString().split(",");

		for (String header : headers_list) {
			String[] tmp = header.split(":");
			headers.put(tmp[0], tmp[1]);
			System.out.println("Headers [key ,  val] : [" + tmp[0] + " , " + tmp[1] + "]");
			
		}

		RestAssured.baseURI = base;

		/*
		 * if (!(excelKeysAndValues.get("port") == "null" ||
		 * excelKeysAndValues.get("port") == "")) { RestAssured.port =
		 * Integer.valueOf(excelKeysAndValues.get("port").toString()); }
		 */

		RestAssured.basePath = excelKeysAndValues.get("basePath").toString();

		Response xmlResponse = RestAssured.given().config(RestAssuredConfig.config()
				.xmlConfig(XmlConfig.xmlConfig().with()
						.declareNamespace("soapenv", "http://schemas.xmlsoap.org/soap/envelope/")
						.declareNamespace("ns0", "http://www.wesbank.co.za/common/Header/v1_1/WBHeader")
						.declareNamespace("v1_", "http://www.wesbank.co.za/conn/core/cutomerScoringDetails/v1_0")
						.namespaceAware(true)))
				.headers(headers).body(body).when().relaxedHTTPSValidation().post();
		return xmlResponse;
	}

	public List<ExtentClass> xmlAssertor(LinkedHashMap<String, String> excelRequestMap, Response res,
			ExtentClass extentClass) {

		AssertionDto assertiondto = new AssertionDto();
		Map<String, String> pathOperationMap = new HashMap<String, String>();
		Map<String, String> pathExpectedvalueMap = new HashMap<String, String>();
		String scenario = extentClass.getScenario();
		String feature = extentClass.getFeature();
		String type = extentClass.getType();

		excelRequestMap.forEach((field, value) -> {
			if (field.startsWith("assert")) {

				if (value == null || value.isEmpty())
					return;

				assertionList = value.split(",");
				for (int a = 0; a < assertionList.length; a++) {
					if (assertionList[a].contains("!=")) {
						operation = "!=";
						tmp = assertionList[a].split("!=");
						pathOperationMap.put(tmp[0], "!=");
						pathExpectedvalueMap.put(tmp[0], tmp[1]);
					} else if (assertionList[a].contains("=")) {
						operation = "=";
						tmp = assertionList[a].split("=");
						pathOperationMap.put(tmp[0], "=");
						pathExpectedvalueMap.put(tmp[0], tmp[1]);
					} else if (assertionList[a].contains(">")) {
						operation = ">";
						tmp = assertionList[a].split(">");
						pathOperationMap.put(tmp[0], ">");
						pathExpectedvalueMap.put(tmp[0], tmp[1]);
					} else if (assertionList[a].contains("<")) {
						operation = "<";
						tmp = assertionList[a].split("<");
						pathOperationMap.put(tmp[0], "<");
						pathExpectedvalueMap.put(tmp[0], tmp[1]);
					}

					String expectedVal = tmp[1];

					XmlPath xpath = new XmlPath(res.asString().replace(":", "_"));

					String actualVal = xpath.getString(tmp[0]);
					System.out
							.println("XML Path : " + tmp[0] + "\n" + " actualVal.toString() : " + actualVal.toString());

					try {
						switch (operation) {
						case "!=":

							Assert.assertEquals(true, !actualVal.equals(expectedVal), "!=");
							assertionResults = "Passed";
							break;
						case "=":
							Assert.assertEquals(true, actualVal.equals(expectedVal), "=");
							assertionResults = "Passed";
							break;
						case ">":
							Assert.assertEquals(true, actualVal.equals(expectedVal), ">");
							assertionResults = "Passed";
							break;
						case "<":
							Assert.assertEquals(true, actualVal.equals(expectedVal), "<");
							assertionResults = "Passed";
							break;
						case "|contains|":
							Assert.assertEquals(true, actualVal.contains(expectedVal), "|contains|");
							assertionResults = "Passed";
							break;
						default:
							Assert.assertEquals(true, expectedVal.equals(expectedVal), "!=");
							assertionResults = "Passed";
						}
					} catch (AssertionError e) {
						assertionResults = "Failed";
					} catch (NullPointerException nullexception) {
						assertionResults = "Failed";
						actualVal = "Null / Not found in response";
					}

					ExtentClass resultClass = new ExtentClass();
					resultClass.setExpectedValue(expectedVal);
					resultClass.setActualValue(actualVal);

					if (assertionResults.equals("Passed")) {
						resultClass.setStatus("Passed");
					} else {
						resultClass.setStatus("Failed");
					}

					if (x == 0) {
						resultClass.setType("Test");
						resultClass.setFeature(feature);
						resultClass.setScenario(scenario);
					} else {
						resultClass.setType("step");
						resultClass.setFeature(feature);
						resultClass.setScenario(scenario);
					}

					list_results.clear();
					list_results.add(resultClass);
					// x += 1;

				}
			}
		});

		return list_results;
	}

		private static Document convertStringToXMLDocument(String xmlString) {
		// Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// API to obtain DOM Document instance
		DocumentBuilder builder = null;
		try {
			// Create DocumentBuilder with default configuration
			builder = factory.newDocumentBuilder();

			Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
			return doc;
		} 	catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<ExtentClass> jsonAssertor(LinkedHashMap<String, String> excelRequestMap, Response res,
			ExtentClass extentClass) {
		ExtentClass resultClass = new ExtentClass();
		AssertionDto assertiondto = new AssertionDto();
		Map<String, String> pathOperationMap = new HashMap<String, String>();
		Map<String, String> pathExpectedvalueMap = new HashMap<String, String>();
		String scenario = extentClass.getScenario();
		String feature = extentClass.getFeature();
		String type = extentClass.getType();

		excelRequestMap.forEach((field, value) -> {
			if (field.startsWith("assert")) {
				assertionList = value.split(",");
				for (int a = 0; a < assertionList.length; a++) {
					if (assertionList[a].contains("!=")) {
						operation = "!=";
						tmp = assertionList[a].split("!=");
						pathOperationMap.put(tmp[0], "!=");
						pathExpectedvalueMap.put(tmp[0], tmp[1]);
					} else if (assertionList[a].contains("=")) {
						operation = "=";

						tmp = assertionList[a].split("=");

						pathOperationMap.put(tmp[0], "=");

						pathExpectedvalueMap.put(tmp[0], tmp[1]);

					} else if (assertionList[a].contains(">")) {
						operation = ">";
						tmp = assertionList[a].split(">");
						pathOperationMap.put(tmp[0], ">");
						pathExpectedvalueMap.put(tmp[0], tmp[1]);
					} else if (assertionList[a].contains("<")) {
						operation = "<";
						tmp = assertionList[a].split("<");
						pathOperationMap.put(tmp[0], "<");
						pathExpectedvalueMap.put(tmp[0], tmp[1]);
					}

					String expectedVal = tmp[1];

					String actualVal = (String) this.getJSONObjectFromPath(tmp[0], res);

					try {
						switch (operation) {
						case "!=":

//                                                                                                    System.out.println("\t\t jsonPath \t\t : \t\t"+tmp[0]);
//                                                                                                    System.out.println("\t\t expectedVal \t\t : \t\t"+expectedVal);
//                                                                                                    System.out.println("\t\t actualVal \t\t : \t\t"+actualVal);

							Assert.assertEquals(true, !actualVal.equals(expectedVal), "!=");
							assertionResults = "Passed";
							break;
						case "=":
							Assert.assertEquals(true, actualVal.equals(expectedVal), "=");
							assertionResults = "Passed";
							break;
						case ">":
							Assert.assertEquals(true, actualVal.equals(expectedVal), ">");
							assertionResults = "Passed";
							break;
						case "<":
							Assert.assertEquals(true, actualVal.equals(expectedVal), "<");
							assertionResults = "Passed";
							break;
						case "|contains|":
							Assert.assertEquals(true, actualVal.contains(expectedVal), "|contains|");
							assertionResults = "Passed";
							break;
						default:
							Assert.assertEquals(true, expectedVal.equals(expectedVal), "!=");
							assertionResults = "Passed";
						}
					} catch (AssertionError e) {
						assertionResults = "Failed";
					} catch (NullPointerException nullexception) {
						assertionResults = "Failed";
						actualVal = "Null / Not found in response";
					}

					resultClass.setExpectedValue(expectedVal);
					resultClass.setActualValue(actualVal);
					resultClass.setBddSyntax(extentClass.getBddSyntax());

					if (assertionResults.equals("Passed")) {
						resultClass.setStatus("Passed");
					} else {
						resultClass.setStatus("Failed");
					}
					if (x == 0) {
						resultClass.setType("Test");
						resultClass.setFeature(feature);
						resultClass.setScenario(scenario);
					} else {
						resultClass.setType("step");
						resultClass.setFeature(feature);
						resultClass.setScenario(scenario);
					}

					list_results.clear();
					list_results.add(resultClass);
					// x += 1;
				}
			}
		});

		return list_results;
	}

	public Object getJSONObjectFromPath(String jsonPathToField, Response res) {

		JsonPath jsonPathEvaluator = res.jsonPath();

		if (jsonPathEvaluator.get(jsonPathToField) == null) {
			return null;

		} else {
			return jsonPathEvaluator.get(jsonPathToField).toString();
		}
	}

	public LinkedList<String> fileReader(String string) throws Exception {
		List<String> list = new LinkedList<String>();
		FileReader fr = new FileReader(string);
		BufferedReader br = new BufferedReader(fr);
		String s;

		while ((s = br.readLine()) != null) {
			list.add(s);
			System.out.println(s);
		}

		fr.close();

		return (LinkedList<String>) list;
	}

	public void writeToCsv(List<ExtentClass> extentList, String filename) {

		FileWriter fileWriter = null;

		try {

			fileWriter = new FileWriter(filename, true);

			for (ExtentClass step_or_test : extentList) {

				ObjectMapper mapper = new ObjectMapper();
				fileWriter.append(mapper.writeValueAsString(step_or_test));
				fileWriter.append('\n');

			}

			System.out.println("Write JSON successfully!");
		} catch (Exception e) {
			System.out.println("Writing JSON error!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Flushing/closing error!");
				e.printStackTrace();
			}
		}
	}

	public static Logger getLogger() throws Exception, Exception {

		boolean append = true;
		int days = 0;

		String logFileName = "Logger_" + new API_Data_Functions().date_calc("LogDate", days) + ".log";
		System.out.println("| logFileName : " + logFileName);
		FileHandler handler = new FileHandler(
				System.getProperty("user.dir") + "./src/test/resources/reports/" + logFileName, append);

		SimpleFormatter formatter = new SimpleFormatter();

		handler.setFormatter(formatter);
		Logger logger = Logger.getLogger("Utilities.iLogger");
		logger.addHandler(handler);

		return logger;
	}

	private static String readAllBytesJava(String filePath) throws Exception {
		String contentOfFile = "";
		Path path = Paths.get(filePath);

		try {
			contentOfFile = new String(Files.readAllBytes(Paths.get(filePath)), Charset.forName("Cp1252"));
			System.out.println(" \n\n contentOfFile : " + contentOfFile + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return contentOfFile;
	}

	public static String parseJSON(String filePath, HashMap<String, String> column_value) throws Exception {

		String content = new String(Files.readAllBytes(Paths.get(filePath)));
		results = content.toString();

		column_value.forEach((key, val) -> {

			String intemediate_string = "";
			intemediate_string = results.replace(key, val);
			results = intemediate_string;

		});

		return results.toString();

	}

	public static String parseXML(String filePath, HashMap<String, String> column_value) throws Exception {

		XMLDocument xml = new XMLDocument(new File(filePath));
		results = xml.toString();

		column_value.forEach((key, val) -> {

			String intemediate_string = "";
			intemediate_string = results.replace(key, val);
			results = intemediate_string;

		});

		return results.toString();

	}

	public Object MapToXML(LinkedHashMap<String, String> excelRequestMap) {

		return null;
	}
}