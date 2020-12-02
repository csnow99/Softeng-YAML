package edu.wpi.cs.yaml.demo;

import edu.wpi.cs.yaml.demo.http.ChoiceInfoListResponse;
import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.yaml.demo.http.ChoiceInfoListResponse;
import edu.wpi.cs.yaml.demo.model.ChoiceInfo;

public class CreateReportHandlerTest extends LambdaTest{

	@Test 
	public void testCreateReport() {
		try {
			/*Check by eye if everything is okay*/
			CreateReportsHandler handler = new CreateReportsHandler();
			ChoiceInfoListResponse list = handler.handleRequest(new Object(), createContext("Create Reports"));

			for (ChoiceInfo i : list.infos) {
				System.out.println(i.toString());
			}
		} catch (Exception e) {
			Assert.fail();
		}

	}
}
