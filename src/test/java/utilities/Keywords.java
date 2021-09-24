package utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Keywords {
	WebDriver webDriver;

	public Keywords(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	/**
	 * Submit a Rest API Post Request and will return a response output. If
	 * requestBody is not needed just leave the parameter as blank a string.
	 * 
	 * @param strRequestURL  - Set Rest request URL.
	 * @param strRequestBody - Set Json body as string.
	 * @return Response type as output.
	 */
	public Response restPost(String strRequestURL, HashMap<String, String> requestHeader, String strRequestBody) {
		RequestSpecification request = RestAssured.given();

		if (!requestHeader.isEmpty()) {
			for (HashMap.Entry<String, String> entry : requestHeader.entrySet()) {
				request.header(entry.getKey(), entry.getValue());
			}
		}

		request.body(strRequestBody);

		Response response = request.post(strRequestURL);

		return response;
	}

	/**
	 * Gets the current date and time and returns it depending on the given format.
	 * 
	 * @param strFormat - the format of the date and time string. Example is
	 *                  "_ddMMyy_HHmm".
	 * @return Returns the formatted current date and time.
	 */
	public String getStringDateTimeNow(String strFormat) {
		String strFormattedDateTimeNow = "";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strFormat);
		strFormattedDateTimeNow = simpleDateFormat.format(calendar.getTime());

		return strFormattedDateTimeNow;
	}

}