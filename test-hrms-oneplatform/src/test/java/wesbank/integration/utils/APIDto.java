package wesbank.integration.utils;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Getter
@Setter
public class APIDto {

	public LinkedHashMap<String, String> excelRequestMap = new LinkedHashMap<String, String>();
	public Map<String, String> requestMap = new LinkedHashMap<String, String>();
	public Map<String, String> ResponseMap = new LinkedHashMap<String, String>();
	public Map<String, String> reqResMap = new LinkedHashMap<String, String>();
	public Map<String, String> restAssured = new LinkedHashMap<String, String>();

}
