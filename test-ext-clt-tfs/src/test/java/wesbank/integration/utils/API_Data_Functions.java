
package wesbank.integration.utils;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Element;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

//import com.sun.jmx.snmp.Timestamp;

public class API_Data_Functions {
	/** Data accessible variables_________________________ */
	public Connection connect;
	public java.sql.Connection conn = null;
	public Sheet sheet;
	public String sTest, sPageObjectDirectory, sEndpointFile = null;
	static String[] aStorePageObjects = new String[1];
	public Workbook workbook;
	public WebDriver driver;
	int col, Column_Count, Row_Count;
	int colnNum = 0;
	int fillonum = 1;
	String sDatabaseResponse;
	String sOutputPost = null;
	String ssConnection = null, ssUsername = null, ssPassword = null, ssDBQuery = null;
	ArrayList<String> lines = new ArrayList<String>();
	/**
	 * Report variables and Modules______________________
	 */
	private String sUserAPI, sPassAPI, sURLEnvr, sHostName, sEnvironment, sUsername, sDocumentTitle, sReportName,
			sTestExecuted, sProjectReportName, sKeepHistory, sSheetName, sXMLInputData, sqlQuery, endpoint, rUser;

	String contentType = null;
	String status = null;

	public String ENDPOINT() {
		return endpoint;
	}

	public String APIUser() {
		return sUserAPI;
	}

	public String APIPass() {
		return sPassAPI;
	}

	public String sEnvironmentURL() {
		return sURLEnvr;
	}

	public String RHostName() {
		return sHostName;
	}

	public String REnvironment() {
		return sEnvironment;
	}

	public String SheetName() {
		return sSheetName;
	}

	public String InputXMLPath() {
		return sXMLInputData;
	}

	public String RUsername() {
		return sUsername;
	}

	public String RDocumentTitle() {
		return sDocumentTitle;
	}

	public String RReportName() {
		return sReportName;
	}

	public String RKeepHistory() {
		return sKeepHistory;
	}

	public String RTestExecuted() {
		return sTestExecuted;
	}

	public String RProjectReportName() {
		return sProjectReportName;
	}

	public String DBQuery() {
		return sqlQuery;

	}

	public String UserNeo() {
		return rUser;
	}

	/** Test variables and modules__________________________ */
	private String sDataLocation, sPageObjectsPath, sDataType, sDBPassword, sDBUsername, sAppLoc, sOutput, sEmail,
			sEmailFrom, sEmailTo, sDataFileName;
	private String sConnectionString = null, sUserPassDB = null;

	public String getDBPassword() {
		return sDBPassword;
	}

	public String getDBUserName() {
		return sDBUsername;
	}

	public String getWindowsAppLocation() {
		return sAppLoc;
	}

	public String getPageObjectsPath(String XmlFileName) {
		return sPageObjectsPath + XmlFileName;
	}

	public String getDataLocation() {
		return sDataLocation;
	}

	public String getDBConnection() {
		return sConnectionString;
	}

	public String getUserPassLoginDetails() {
		return sUserPassDB;
	}

	public String getDataType() {
		return sDataType;
	}

	public String getSendEmail() {
		return sEmail;
	}

	public String getEmailFrom() {
		return sEmailFrom;
	}

	public String getEmailTo() {
		return sEmailTo;
	}

	public String getDataFileName() {
		return sDataFileName;
	}

	public void SetDataOtput(String sOutput) {
		this.sOutput = sOutput;
	}

	public String PageObjectDirFolder(String sDirFolderName) {
		String[] aDirFolder = { "GUI Applications", "", "Desktop Applications", "Mobile Applications" };
		for (int i = 0; i < aDirFolder.length; i++) {
			if (aDirFolder[i].matches(sDirFolderName)) {
				sPageObjectDirectory = aDirFolder[i];
				aStorePageObjects[0] = sPageObjectDirectory;
				break;
			}
		}
		return sPageObjectDirectory;
	}

	/**
	 * Data
	 * Function___________________________________________________________________________________________________________________
	 */
	/*****************************************************************************
	 * Function Name: GetEnvironmentVariables Description: gets environment
	 * variables from the json file
	 */
	public void ReadJSONFileValues(String FolderDir, String JsonFileName, String UserActive)
			throws IOException, ParseException {
		File file = null;
		FileReader fileread = null;
		JSONParser parser = new JSONParser();
		try {
			file = new File(System.getProperty("user.dir") + "/Config/" + FolderDir + "/" + JsonFileName + ".json");
			fileread = new FileReader(file);
			Object obj = parser.parse(fileread);
			JSONObject jsonObject = (JSONObject) obj;

			/** General Report___________________________________ */
			sHostName = (String) jsonObject.get("HostName");
			sEnvironment = (String) jsonObject.get("Environment");
			sUsername = (String) jsonObject.get("UserName");
			sProjectReportName = (String) jsonObject.get("ReportProjectName");
			sDocumentTitle = (String) jsonObject.get("DocumentTitle");
			sKeepHistory = (String) jsonObject.get("KeepHistory");
			sKeepHistory = (String) jsonObject.get("KeepHistory");
			String[] sUsers = { (String) jsonObject.get("UserAyanda"), (String) jsonObject.get("UserSindi"),
					(String) jsonObject.get("UserNeo"), (String) jsonObject.get("UserThabo"),
					(String) jsonObject.get("UserGlaymond"), (String) jsonObject.get("UserDaniel") };
			for (int i = 0; i < sUsers.length; i++) {
				if (sUsers[i].matches(UserActive)) {

				}

			}

		} finally {
			try {
				fileread.close();

			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	public void GetEnvironmentVariables(String FolderDir, String JsonFileName, String sDBTable, String ServiceName)
			throws IOException, ParseException {
		File file = null;
		FileReader fileread = null;
		JSONParser parser = new JSONParser();
		try {
			file = new File(System.getProperty("user.dir") + "/Config/" + FolderDir + "/" + JsonFileName + ".json");
			fileread = new FileReader(file);
			Object obj = parser.parse(fileread);
			JSONObject jsonObject = (JSONObject) obj;

			/** General Report___________________________________ */
			sHostName = (String) jsonObject.get("HostName");
			sEnvironment = (String) jsonObject.get("Environment");
			sUsername = (String) jsonObject.get("UserName");
			sProjectReportName = (String) jsonObject.get("ReportProjectName");
			sDocumentTitle = (String) jsonObject.get("DocumentTitle");
			sKeepHistory = (String) jsonObject.get("KeepHistory");
			/** ENDPOINT ARRAY */
			// Note: Make sure that the endpoint that you add below is added on both API
			// config file and in the Class/Testcase that you're building
			String[] aDirFolder = { "QA-User Registrations Service", "DEV-User Registrations Service",
					"Local-User Registrations Service", "Verify Employee", "CheckFraudData", "DriversLicenseQuery",
					"Driver Information", "ACAS Scoring - Generic", "ACAS Scoring - Motor Scoring",
					"SendLeadTrackerToTracker", "Customer Account Profile Service (CIS)",
					"Customer Maintenance Commercial (CIS)", "CustomerMaintenanceIndividual (CIS)",
					"Customer Setup (CIS)", "FNBHomeLoanServiceProxy (CIS)", "LocateCustomerByID (CIS)",
					"LocateCustomerByName(CIS)", "FNBOnlineVodsProxy (CIS)", "Account Setup (CIS)",
					"FCRScreeningService", "roadProtect", "CEEDEV", "CEELOCAL", "CEEQA", "OSBQA", "OSBDEV", "OSBDEV2",
					"OSBLocal" };
			for (int i = 0; i < aDirFolder.length; i++) {
				if (aDirFolder[i].matches(ServiceName)) {
					endpoint = (String) jsonObject.get(ServiceName);
					break;
				}
			}

			switch (sDBTable) {
			case "TSPIF1":
				sConnectionString = (String) jsonObject.get("TSPIF1");
				sUserPassDB = (String) jsonObject.get("UPTSPIF1");
			case "DSPIF3":
				sConnectionString = (String) jsonObject.get("DSPIF3");
				sUserPassDB = (String) jsonObject.get("UPDSPIF3");
				break;
			case "TSPIFN":
				sConnectionString = (String) jsonObject.get("TSPIFN");
				sUserPassDB = (String) jsonObject.get("UPTSPIFN");
				break;
			case "TSPIFDC":
				sConnectionString = (String) jsonObject.get("TSPIFDC");
				sUserPassDB = (String) jsonObject.get("UPTSPIFDC");
				break;
			case "TMWU1":
				sConnectionString = (String) jsonObject.get("TMWU1");
				sUserPassDB = (String) jsonObject.get("UPTMWU1");
				break;
			case "tmss1":
				sConnectionString = (String) jsonObject.get("tmss1");
				sUserPassDB = (String) jsonObject.get("UPtmss1");
				break;
			case "pmss1":
				sConnectionString = (String) jsonObject.get("pmss1");
				sUserPassDB = (String) jsonObject.get("UPpmss1");
				break;
			case "T12COSB1":
				sConnectionString = (String) jsonObject.get("T12COSB1");
				sUserPassDB = (String) jsonObject.get("UPT12COSB1");
				break;
			case "dmss1":
				sConnectionString = (String) jsonObject.get("dmss1");
				sUserPassDB = (String) jsonObject.get("UPdmss1");
				break;
			default:
				break;
			}

		} finally {
			try {
				fileread.close();

			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	public void WriteTextFile(String sOutput, String MainFolder, String sSubFolder, String sFileName,
			String fileExtension) {
		try {
			// Whatever the file path is.
			// System.out.println("### File extension: " + fileExtension);
			File statText = new File(System.getProperty("user.dir") + "\\" + MainFolder + "\\" + sSubFolder + "\\"
					+ sFileName + fileExtension);
			if (!statText.exists()) {
				statText.createNewFile();
				System.out.println("New file created with this name:' " + sFileName + "'");
			}
			FileOutputStream is = new FileOutputStream(statText);
			OutputStreamWriter osw = new OutputStreamWriter(is);
			Writer w = new BufferedWriter(osw);
			w.write(sOutput);
			w.close();
		} catch (IOException e) {
			System.err.println("Problem writing to the file");
		}
	}

	public int stringCompare(String str1, String str2) {

		int l1 = str1.length();
		int l2 = str2.length();
		int lmin = Math.min(l1, l2);
		for (int i = 0; i < lmin; i++) {
			int str1_ch = str1.charAt(i);
			int str2_ch = str2.charAt(i);

			if (str1_ch != str2_ch) {
				return str1_ch - str2_ch;
			}
		}

		if (l1 != l2) {
			return l1 - l2;
		} else {
			return 0;
		}
	}

	public boolean StringContained(String search, String sentence) {
		boolean sOutput = false;
		if (sentence.toLowerCase().indexOf(search.toLowerCase()) != -1) {
			System.err.println(search + " Found");
			sOutput = true;
		} else {
			sOutput = false;
			System.err.println(search + " not found");
		}
		return sOutput;
	}

	public Sheet ReadExcel(String FILE_NAME, String SheetName) throws IOException {
		FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
		workbook = new XSSFWorkbook(excelFile);
		sheet = workbook.getSheet(SheetName);
		return sheet;
	}

	public String FormatXML(String xml_input, int indent) {
		try {

			Source xmlInput = new StreamSource(new StringReader(xml_input));
			StringWriter stringWriter = new StringWriter();
			StreamResult xmlOutput = new StreamResult(stringWriter);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			transformerFactory.setAttribute("indent-number", indent);
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(xmlInput, xmlOutput);
			return xmlOutput.getWriter().toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String FormatJSONStr(final String json_str, final int indent_width) {
		final char[] chars = json_str.toCharArray();
		final String newline = System.lineSeparator();
		String jReturnJsonformat = "";
		boolean begin_quotes = false;
		for (int i = 0, indent = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c == '\"') {
				jReturnJsonformat += c;
				begin_quotes = !begin_quotes;
				continue;
			}
			if (!begin_quotes) {
				switch (c) {
				case '{':
				case '[':
					jReturnJsonformat += c + newline + String.format("%" + (indent += indent_width) + "s", "");
					continue;
				case '}':
				case ']':
					jReturnJsonformat += newline
							+ ((indent -= indent_width) > 0 ? String.format("%" + indent + "s", "") : "") + c;
					continue;
				case ':':
					jReturnJsonformat += c + " ";
					continue;
				case ',':
					jReturnJsonformat += c + newline + (indent > 0 ? String.format("%" + indent + "s", "") : "");
					continue;
				default:
					if (Character.isWhitespace(c))
						continue;
				}
			}
			jReturnJsonformat += c + (c == '\\' ? "" + chars[++i] : "");
		}
		return jReturnJsonformat;
	}

	public static String prettyFormat(String input) throws TransformerException, ParserConfigurationException,
			IOException, SAXException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		InputSource src = new InputSource(new StringReader(input));
		Element document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();
		Boolean keepDeclaration = input.startsWith("<?xml");
		DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
		DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
		LSSerializer writer = impl.createLSSerializer();
		writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
		writer.getDomConfig().setParameter("xml-declaration", keepDeclaration);
		return writer.writeToString(document);
	}

	public String GetCellData(String strColumn, int iRow, Sheet sheet, String Type) throws Exception {
		String sValue = null;
		switch (Type) {
		case "Excel":

			Row row = sheet.getRow(0);
			for (int i = 0; i < ColumnCount(sheet); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals("Used_By")) {
					Row raw = sheet.getRow(iRow);
					Cell cell = raw.getCell(i);
					DataFormatter formatter = new DataFormatter();
					sValue = formatter.formatCellValue(cell);
					System.out.println("Debug 3 : strColumn : " + strColumn);
					for (int j = 0; i < ColumnCount(sheet); j++) {
						if (row.getCell(j).getStringCellValue().trim().equals(strColumn)) {
							Cell cell1 = raw.getCell(j);
							DataFormatter formatter1 = new DataFormatter();
							sValue = formatter1.formatCellValue(cell1);
							break;
						}
					}
					// Condition required in the situation whereby the column does not exist
					break;
				}
			}
			break;
		default:
			break;
		}
		return sValue;
	}

	public int RowCount(Sheet sheet) throws Exception {
		int count = 0;
		count = sheet.getPhysicalNumberOfRows();
		return count;
	}

	public int ColumnCount(Sheet sheet) throws Exception {
		return sheet.getRow(0).getLastCellNum();
	}

	public void WriteData(String sColumn, int Row, String SheetName, String filepath, String sValue)
			throws IOException, InvalidFormatException {
		int CoulmnNo = 0;
		FileInputStream file = new FileInputStream(filepath);
		Workbook wb = WorkbookFactory.create(file);
		sheet = wb.getSheet(SheetName);
		org.apache.poi.ss.usermodel.Cell cell = null;
		Row row = sheet.getRow(0);
		for (int i = 0; i < row.getLastCellNum(); i++) {
			if (row.getCell(i).getStringCellValue().trim().equals(sColumn)) {
				CoulmnNo = i;
				Row raw = sheet.getRow(Row);
				cell = raw.createCell(CoulmnNo);
				cell.setCellValue(sValue);
				break;
			}
		}
		FileOutputStream fileOut = new FileOutputStream(filepath);
		wb.write(fileOut);
		fileOut.close();
	}

	public void OnErrorExists(WebDriver driver, String sValue) throws Exception {
		int CoulmnNo = 0;
		FileInputStream file = new FileInputStream(
				System.getProperty("user.dir") + "/Object_Errors/Object_Errors.xlsx");
		Workbook wb = WorkbookFactory.create(file);
		sheet = wb.getSheet("Error");
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		org.apache.poi.ss.usermodel.Cell cell = null;
		Row row = sheet.getRow(0);
		Row newRow = sheet.createRow(rowCount + 1);
		for (int i = 0; i < row.getLastCellNum(); i++) {
			if (row.getCell(i).getStringCellValue().trim().equals("Object_Name")) {
				CoulmnNo = i;
				cell = newRow.createCell(CoulmnNo);
				cell.setCellValue(sValue);
				break;
			}
		}
		FileOutputStream fileOut = new FileOutputStream(
				System.getProperty("user.dir") + "/Object_Errors/Object_Errors.xlsx");
		wb.write(fileOut);
		fileOut.close();
	}

	public ResultSet ConnectAndQuerySQLServer(String sDBURL, String sUserName, String sPassword, String sQuery) {

		ResultSet rs = null;
		try {
			String dbURL = sDBURL;
			String user = sUserName;
			String pass = sPassword;
			conn = DriverManager.getConnection(dbURL, user, pass);
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sQuery);

		} catch (SQLException ex) {
			ex.printStackTrace();

		}
		return rs;
	}

	public Object[][] getJSONdata(String JSON_Path, String JSON_Data, int JSON_attributes)
			throws FileNotFoundException, IOException, ParseException {

		Object obj = new JSONParser().parse(new FileReader(JSON_Path));
		JSONObject jo = (JSONObject) obj;
		JSONArray js = (JSONArray) jo.get(JSON_Data);
		Object[][] arr = new String[js.size()][JSON_attributes];
		for (int i = 0; i < js.size(); i++) {
			JSONObject objGetAllElements = (JSONObject) js.get(i);
			/** Finance Environment_______________________________ */
			arr[i][0] = String.valueOf(objGetAllElements.get("FinanceURL"));
			arr[i][1] = String.valueOf(objGetAllElements.get("FinanceAppDataFileName"));
			arr[i][2] = String.valueOf(objGetAllElements.get("FinanceApplicationDataPath"));
			arr[i][3] = String.valueOf(objGetAllElements.get("PageObjectsPath"));
			arr[i][4] = String.valueOf(objGetAllElements.get("emailfrom"));
			arr[i][5] = String.valueOf(objGetAllElements.get("emailto"));
			arr[i][6] = String.valueOf(objGetAllElements.get("sendemail"));
			/** General Report_______________________________ */
			arr[i][0] = String.valueOf(objGetAllElements.get("FolderDir"));
			arr[i][1] = String.valueOf(objGetAllElements.get("ReportProjectName"));
			arr[i][2] = String.valueOf(objGetAllElements.get("HostName"));
			arr[i][3] = String.valueOf(objGetAllElements.get("Environment"));
			arr[i][4] = String.valueOf(objGetAllElements.get("JsonFileName"));
			arr[i][5] = String.valueOf(objGetAllElements.get("KeepHistory"));
			arr[i][6] = String.valueOf(objGetAllElements.get("DocumentTitle"));
			/** Dealer Applications_______________________________ */
			arr[i][0] = String.valueOf(objGetAllElements.get("Dealer_UserName"));
			arr[i][1] = String.valueOf(objGetAllElements.get("Dealer_TestExecuted"));
			/** Private Applications_______________________________ */
			arr[i][0] = String.valueOf(objGetAllElements.get("Private_UserName"));
			arr[i][1] = String.valueOf(objGetAllElements.get("Private_TestExecuted"));
			/** Employee Asset Applications_______________________________ */
			arr[i][0] = String.valueOf(objGetAllElements.get("Employee_UserName"));
			arr[i][1] = String.valueOf(objGetAllElements.get("Employee_TestExecuted"));
			/** Leisure Applications_______________________________ */
			arr[i][0] = String.valueOf(objGetAllElements.get("Leisure_UserName"));
			arr[i][1] = String.valueOf(objGetAllElements.get("Leisure_TestExecuted"));
			/** Graduate Finance Applications_______________________________ */
			arr[i][0] = String.valueOf(objGetAllElements.get("Graduate_UserName"));
			arr[i][1] = String.valueOf(objGetAllElements.get("Graduate_TestExecuted"));
			/** Business Applications_______________________________ */
			arr[i][0] = String.valueOf(objGetAllElements.get("Business_UserName"));
			arr[i][1] = String.valueOf(objGetAllElements.get("Business_TestExecuted"));
			/** Business Private Applications_______________________________ */
			arr[i][0] = String.valueOf(objGetAllElements.get("BusinessPrivate_UserName"));
			arr[i][1] = String.valueOf(objGetAllElements.get("BusinessPrivate_TestExecuted"));
			/** Balloon Refinance Application_______________________________ */
			arr[i][0] = String.valueOf(objGetAllElements.get("Balloon_UserName"));
			arr[i][1] = String.valueOf(objGetAllElements.get("Balloon_TestExecuted"));
			/** CashPower Personal Loan Application_______________________________ */
			arr[i][0] = String.valueOf(objGetAllElements.get("CashPower_UserName"));
			arr[i][1] = String.valueOf(objGetAllElements.get("CashPower_TestExecuted"));
		}
		return arr;
	}

	public Object[][] getdata(String JSON_Path, String TypeData, int totalDataRow, int totalColumnEntry)
			throws JsonIOException, JsonSyntaxException, IOException {
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObj = jsonParser.parse(new FileReader(JSON_Path)).getAsJsonObject();
		JsonArray array = (JsonArray) jsonObj.get(TypeData);
		return SearchJsonElement(array, totalDataRow, totalColumnEntry);
	}

	public Object[][] toArray(List<List<Object>> list) {
		Object[][] r = new Object[list.size() + 1][];
		int i = 0;
		for (List<Object> next : list) {
			r[i++] = next.toArray(new Object[next.size() + 1]);
		}
		return r;
	}

	public Object[][] SearchJsonElement(JsonArray jsonArray, int totalDataRow, int totalColumnEntry) {
		Object[][] matrix = new Object[totalDataRow][totalColumnEntry];
		int i = 0;
		int j = 0;
		for (JsonElement jsonElement : jsonArray) {
			for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
				matrix[i][j] = entry.getValue().toString().replace("\"", "");
				j++;
			}
			i++;
			j = 0;
		}

		return matrix;
	}

	public long generateRandom() {
		return (long) (Math.random() * 100000 + 3333300000L);
	}

	public String updateData(String[] ColumnValue, String[] SearchValue, String xmlPath) throws IOException {
		String sBody = null;
		FileInputStream inputStream = new FileInputStream(xmlPath);
		try {
			sBody = IOUtils.toString(inputStream);
			for (int i = 0; i < SearchValue.length; i++) {
				if (sBody.contains(SearchValue[i])) {
					sBody = sBody.replace(SearchValue[i], ColumnValue[i]);
				}
			}
		} finally {
			inputStream.close();
		}
		return sBody;
	}

	@SuppressWarnings("unchecked")
	public String ReadDataJson(String xmlPath, String JsonFormat) throws IOException, ParseException {
		File f1 = null;
		FileReader fr = null;

		JSONParser parser = new JSONParser();
		try {
			f1 = new File(xmlPath);
			fr = new FileReader(f1);
			Object obj = parser.parse(fr);
			if (JsonFormat.matches("JSONOBJECT")) {
				@SuppressWarnings("unused")
				JSONObject jsonObject = (JSONObject) obj;
			} else {
				JSONArray array = new JSONArray();
				array.add(obj);
			}

			String sBody = obj.toString();
			return sBody;
		} finally {
			try {
				lines.clear();
				fr.close();
			} catch (IOException ioe)

			{
				ioe.printStackTrace();
			}
		}
	}

	public String WebServiceContentTextSOAP(String RequestPath, String RequestUrl, String SoapAction,
			String ContentType) throws Exception {
		String[] rResponse = null;
		// Create a StringEntity for the SOAP XML.
		String body = RequestPath;
		StringEntity stringEntity = new StringEntity(body, "UTF-8");
		stringEntity.setChunked(true);
		// Request parameters and other properties.
		HttpPost httpPost = new HttpPost(RequestUrl);
		httpPost.setEntity(stringEntity);
		// httpPost.addHeader("Accept", "text/xml");
		httpPost.addHeader("SOAPAction", SoapAction);
		httpPost.addHeader("Content-Type", ContentType);
		// Execute and get the response.
		@SuppressWarnings("deprecation")
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		String strResponse = null;
		if (entity != null) {
			strResponse = EntityUtils.toString(entity);
		}
		return strResponse;
	}

	public String sendSoapRequestNew(String xml, String SoapAction, String soapEndpointUrl) throws Exception {

		byte[] encoded = xml.getBytes();
		InputStream bStream = new ByteArrayInputStream(encoded);
		SOAPMessage request = MessageFactory.newInstance().createMessage(null, bStream);
		MimeHeaders headers = request.getMimeHeaders();
		headers.addHeader("Content-Type", "text/xml; charset=utf-8");
		headers.addHeader("SOAPAction", SoapAction);
		request.saveChanges();
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();
		SOAPMessage soapResponse = soapConnection.call(request, soapEndpointUrl);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		soapResponse.writeTo(out);
		String strMsg = new String(out.toByteArray());
		return strMsg;
	}

	/**
	 * Generate RTS getCurrentTimeStamp____________________ /* public String
	 * getCurrentTimeStamp(String sTimeStamp) { //SimpleDateFormat sdf = new
	 * SimpleDateFormat("yyyyMMddHHmmss"); //Timestamp timestamp = new
	 * Timestamp(System.currentTimeMillis()); LocalDateTime date =
	 * LocalDateTime.now(); DateTimeFormatter dateFormatter =
	 * DateTimeFormatter.ofPattern(sTimeStamp); String stringDate = null;
	 * 
	 * if (date == null) { return null; } try { stringDate =
	 * dateFormatter.format(date); } catch (Exception e) { System.out.println("an
	 * error occured when generating timestamp"); return null; } return stringDate;
	 * }
	 */
	/**
	 * Generate RTS customerKey____________________
	 */
	public int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		return (int) (Math.random() * ((max - min) + 1)) + min;
	}

	/**
	 * Generate RTS String TimeStamp____________________
	 */
	public static String getStringDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return sdf.format(timestamp);
	}

	/**
	 * Generate RTS generateCorrelationId____________________
	 */
	public static String generateCorrelationId(StringBuilder buyerID) {
		StringBuilder builder = new StringBuilder("");
		String stringDate = null;
		LocalDateTime now = null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			now = LocalDateTime.now();
			stringDate = formatter.format(now);
			System.out.println("formatted date: : " + stringDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getCurrentTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return sdf.format(timestamp);
	}

	public String getFormattedStringDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return sdf.format(timestamp);
	}

	/**
	 * Database connection_________________________
	 * 
	 * @throws SQLException
	 * @throws ParseException
	 * @throws IOException
	 */
	public ResultSet DatabaseResponse(String FolderDir, String JsonFileName, String sDBTable, String DBQuery,
			String ServiceName) throws SQLException, IOException, ParseException {
		ResultSet sresult = null;
		try {

			GetEnvironmentVariables(FolderDir, JsonFileName, sDBTable, ServiceName);
			ssConnection = getDBConnection();
			String[] sGetUserPassLogin = getUserPassLoginDetails().split("___");
			ssUsername = sGetUserPassLogin[0];
			ssPassword = sGetUserPassLogin[1];
			Class.forName("oracle.jdbc.driver.OracleDriver"); // Accept the database driver that you want to connect to
																// : e.g.'com.mysql.jdbc.Driver
			Connection sConnect = DriverManager.getConnection(ssConnection, ssUsername, ssPassword); // Accept the
																										// connection
																										// string of the
																										// database
			Statement sQuery = sConnect.createStatement();
			sresult = sQuery.executeQuery(DBQuery); // execute the query
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return sresult; // return the executed query
	}

	public String date_calc(String type, int days) {

		Date dt = new Date();
		Calendar date = Calendar.getInstance();

		date.setTime(dt);
		date.add(Calendar.DATE, days);
		String pattern = null;

		if (type == "LongDate") {
			pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
//                                                                                            2018-08-11T00:00:00.001Z
		}

		if (type == "DaCallBackDate") {
			pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'+02:00'";
//                                                                                            2018-06-08T18:59:21.557+02:00
		}

		if (type == "ShortDate") {
			pattern = "yyyy-MM-dd";
		}

		if (type == "LogDate") {
			pattern = "yyyyMMddHHmmss";
		}

		DateFormat dateFormat = new SimpleDateFormat(pattern);
		String strDate = dateFormat.format(date.getTime());
		return strDate;
	}

	public String generate_alphanumeric(int len) {

		String results = "";
		char[] str = "ABCDEFGHIJKLMNOPQRSTUVXYZabcdefghijklmnopqrstuvxyz1234567890".toCharArray();
		Random rdm = new Random();

		for (int i = 0; i < len; i++) {
			results += str[rdm.nextInt((len - 0) + 1) + 0];
		}

		return results;
	}

	public String generate_numeric(int len) {

		String results = "";
		char[] str = "1234567890".toCharArray();
		Random rdm = new Random();

		for (int i = 0; i < len; i++) {
			results += str[rdm.nextInt((len - 0) + 1) + 0];
		}

		return results;
	}

	public int randInt(int min, int max) {
		Random rand = new Random();
		;
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}
