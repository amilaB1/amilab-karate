package wesbank.integration.utils;

import java.util.HashMap;
import java.util.Map;

public class ExtentClass {

	private String type;
	private String feature;
	private String scenario;
	private String TestIDX;
	private String actualValue;
	private String expectedValue;
	private String status;
	private String comment;
	private String description;
	private String bddSyntax;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getScenario() {
		return scenario;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	public String getTestID() {
		return TestIDX;
	}

	public void setTestID(String TestIDX) {
		this.TestIDX = TestIDX;
	}

	public String getActualValue() {
		return actualValue;
	}

	public void setActualValue(String actualValue) {
		this.actualValue = actualValue;
	}

	public String getExpectedValue() {
		return expectedValue;
	}

	public void setExpectedValue(String expectedValue) {
		this.expectedValue = expectedValue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBddSyntax() {
		return bddSyntax;
	}

	public void setBddSyntax(String bddSyntax) {
		this.bddSyntax = bddSyntax;
	}

	/*
	 * public String getValue2() { return value2; }
	 * 
	 * public void setValue2(String value2) { this.value2 = value2; }
	 * 
	 * public String getValue3() { return value3; }
	 * 
	 * public void setValue3(String value3) { this.value3 = value3; }
	 * 
	 * public String getValue4() { return value4; }
	 * 
	 * public void setValue4(String value4) { this.value4 = value4; }
	 * 
	 * public String getValue5() { return value5; }
	 * 
	 * public void setValue5(String value5) { this.value5 = value5; }
	 * 
	 * public String getValue6() { return value6; }
	 * 
	 * public void setValue6(String value6) { this.value6 = value6; }
	 * 
	 * public String getValue7() { return value7; }
	 * 
	 * public void setValue7(String value7) { this.value7 = value7; }
	 */
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}