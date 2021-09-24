package stepDefinitions;

import java.util.HashMap;
import java.util.List;

import org.testng.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utilities.Base;
import utilities.Keywords;

public class StepDefinitions {
	Keywords keywords = new Keywords(Base.getWebDriver());
	String strRequestURL;
	HashMap<String, String> requestHeader = new HashMap<String, String>();
	String strRequestBody;
	Response response;

	// ==================================================|Given|==================================================
	@Given("I will test batch disbursement api")
	public void I_will_test_batch_disbursement_api() throws Throwable {
		try {
			strRequestURL = "https://api.xendit.co/batch_disbursements";

			Assert.assertEquals(strRequestURL, "https://api.xendit.co/batch_disbursements");
		} catch (AssertionError assertionError) {
			throw new Exception(assertionError.getMessage());
		} catch (Exception exception) {
			throw new Exception(exception.getMessage());
		}
	}

	// ==================================================|When|==================================================
	@When("I create a batch disbursement with the following details$")
	public void I_create_a_batch_disbursement_with_the_following_details(DataTable batchDisbursementDetails)
			throws Throwable {
		try {
			List<List<String>> dtValues = batchDisbursementDetails.asLists(String.class);
						
			String strReferenceId = dtValues.get(0).get(1);
			String strAmount = dtValues.get(1).get(1);
			String strBankCode = dtValues.get(2).get(1);
			String strBankAccountNumber = dtValues.get(3).get(1);
			String strBankAccountName = dtValues.get(4).get(1);
			String strDescription = dtValues.get(5).get(1);
			String strExternalId = dtValues.get(6).get(1);
			
			requestHeader.put("Authorization", Base.configurationVariables("secretAPIKey"));
			requestHeader.put("Content-Type", "application/json");

			strRequestBody = constructBatchDisbursementRequestBody(strReferenceId, strAmount, strBankCode, strBankAccountName, strBankAccountNumber, strDescription, strExternalId);

			response = keywords.restPost(strRequestURL, requestHeader, strRequestBody);
		} catch (Exception exception) {
			throw new Exception(exception.getMessage());
		}
	}

	@When("I send a request to the batch disbursement api with more than 10000 transactions")
	public void I_send_a_request_to_the_batch_disbursement_api_with_more_than_10000_transactions() throws Throwable {
		try {
			requestHeader.put("Authorization", Base.configurationVariables("secretAPIKey"));
			requestHeader.put("Content-Type", "application/json");

			strRequestBody = constructBatchDisbursementRequestBodyWith10001Transactions();

			response = keywords.restPost(strRequestURL, requestHeader, strRequestBody);
		} catch (Exception exception) {
			throw new Exception(exception.getMessage());
		}
	}

	@When("I send a request to the batch disbursement api with an invalid API key")
	public void I_send_a_request_to_the_batch_disbursement_api_with_an_invalid_API_key() throws Throwable {
		try {
			requestHeader.put("Authorization", "INVALID");
			requestHeader.put("Content-Type", "application/json");

			strRequestBody = constructBatchDisbursementRequestBody("Sample", "123.45", "BCA", "Fadlan", "1234567890", "Batch Disbursement", "SampDisb1");

			response = keywords.restPost(strRequestURL, requestHeader, strRequestBody);
		} catch (Exception exception) {
			throw new Exception(exception.getMessage());
		}
	}

	// ==================================================|Then|==================================================
	@Then("I will expect a response that the api (.*)$")
	public void I_will_expect_an_error_response_saying_message(String strExpectedOutput) throws Throwable {
		try {
			Integer result = 0;
			Integer actual = response.getStatusCode();
			
			if (strExpectedOutput.contains("Passed")) {
				result = 200;
			}
			else if (strExpectedOutput.contains("Failed")) {
				result = 400;
			}
			else if (strExpectedOutput.contains("Cannot be called due to Unauthorized API key")) {
				result = 401;
			}
			
			Assert.assertEquals(actual, result);
		} catch (AssertionError assertionError) {
			throw new Exception(assertionError.getMessage());
		} catch (Exception exception) {
			throw new Exception(exception.getMessage());
		}
	}

	// ==================================================|Reusable_Functions|==================================================
	private String constructBatchDisbursementRequestBody(String strReference, String strAmount, String strBankCode,
			String strBankAccountName, String strBankAccountNumber, String strDescription, String strExternalID)
			throws Throwable {
		String strResult = "";
		
		if (strReference == null) {
			strReference = "";
		}
		
		if (strAmount == null) {
			strAmount = "";
		}
		
		if (strBankCode == null) {
			strBankCode = "";
		}
		
		if (strBankAccountName == null) {
			strBankAccountName = "";
		}
		
		if (strBankAccountNumber == null) {
			strBankAccountNumber = "";
		}
		
		if (strDescription == null) {
			strDescription = "";
		}
		
		if (strExternalID == null) {
			strExternalID = "";
		}

		strResult = "{\"reference\": \"" + strReference + "\", \"disbursements\": [{\"amount\": " + strAmount
				+ ", \"bank_code\": \"" + strBankCode + "\", \"bank_account_name\": \"" + strBankAccountName
				+ "\", \"bank_account_number\": \"" + strBankAccountNumber + "\", \"description\": \"" + strDescription
				+ "\", \"external_id\": \"" + strExternalID + "\"}]}";

		return strResult;
	}

	private String constructBatchDisbursementRequestBodyWith10001Transactions() throws Throwable {
		String strResult = "";
		String transactions = "{\"amount\": 1, \"bank_code\": \"BCA\", \"bank_account_name\": \"Fadlan\", \"bank_account_number\": \"1234567890\", \"description\": \"Batch Disbursement\", \"external_id\": \"disbursement-0\"}";

		for (int i = 1; i <= 10001; i++) {
			transactions += "{\"amount\": 1, \"bank_code\": \"BCA\", \"bank_account_name\": \"Fadlan\", \"bank_account_number\": \"1234567890\", \"description\": \"Batch Disbursement\", \"external_id\": \"disbursement-"
					+ i + "\"}";
		}

		strResult = "{\"reference\": \"disb_batch-{{$timestamp}}\", \"disbursements\": [" + transactions + "]}";

		return strResult;
	}
}