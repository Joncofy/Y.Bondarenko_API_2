package project2.step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import project2.pojo.Response_Body;
import static io.restassured.RestAssured.*;
import java.util.*;

public class Proj2_StepDefs {

     static String baseURL;
        static int count;

    @Given("the API endpoint {string}")
    public void the_api_endpoint(String strLink)  {

        baseURL=strLink;
    }

    @When("I retrieve all APIs with category {string} and link containing {string}")
    public void i_retrieve_all_ap_is_with_category_and_link_containing(String category, String link){
        Response response = given().accept(ContentType.JSON)
                .queryParam("Category", category)
                .get(baseURL)
                .then().statusCode(200)
                .contentType("application/json")
                .extract().response();

        JsonPath jsonPath = response.jsonPath();

        Response_Body responseBody =jsonPath.getObject("", Response_Body.class);

       count=responseBody.getCount();

        for(Map<String,Object> eachMap:responseBody.getEntries()){
            if(eachMap.get("Link").toString().contains(link))
                System.out.println("eachMap = " + eachMap);
        }
    }

    @Then("the list should contain distinct {string} values")
  public void the_list_should_contain_distinct_values(String cors) {
        Response response = given().accept(ContentType.JSON)
                .get(baseURL)
                .then().statusCode(200)
                .contentType("application/json")
                .extract().response();

        JsonPath jsonPath = response.jsonPath();

        Response_Body responseBody =jsonPath.getObject("", Response_Body.class);

        Set<String> distinctValues = new HashSet<>();
        for(Map<String,Object> eachMap:responseBody.getEntries()){
            distinctValues.add(eachMap.get(cors).toString());
        }
        System.out.println("distinctValues = " + distinctValues);
    }

   @Then("I should see the count of APIs belonging to the {string} category")
   public void i_should_see_the_count_of_ap_is_belonging_to_the_category(String string) {
       System.out.println("count = " + count);
   }

}
