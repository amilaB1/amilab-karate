package cucumberJava;

//import Repository.API_Data_Functions;
//import dtos.APIDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class cucumberJava {
	
	public static API_Utility_Functions apiUtils= new API_Utility_Functions();
	public static API_Data_Functions data=new API_Data_Functions();
	
   @Given("I have Scheem data to upload")
   public void i_have_Scheem_data_to_upload() throws Exception {

	   APIDto apiDto = new APIDto();
	   apiDto.excelRequestMap = apiUtils.readExcelReturnMap("c:\\things\\Ja.xlsx", "Shua", "Shua");
	   
	   System.out.println( " Printing the JSON : \n\n \t\t\t" + apiUtils.MapToJson(apiDto.excelRequestMap).toString());
   }

   @When("I upload the data")
   public void i_upload_the_data() {
       // Write code here that turns the phrase above into concrete actions
	   System.out.println("IN : I upload the data");
   }

   @Then("Data should reflect in DB")
   public void data_should_reflect_in_DB() {
       // Write code here that turns the phrase above into concrete actions
	   System.out.println("IN : Data should reflect in DB");
   }   
}
