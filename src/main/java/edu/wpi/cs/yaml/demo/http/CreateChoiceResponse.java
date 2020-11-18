package edu.wpi.cs.yaml.demo.http;

/*The response contains the name of the created choice in the usual case and a 200 HTTP code
 * In the case of an error, the result contains the name of the error, and a non 200 HTTP code
 * */
public class CreateChoiceResponse extends GenericResponse {
	public CreateChoiceResponse(String response) {
		super(response);
	}
	public CreateChoiceResponse(String response, int code) {
		super(response, code);
	}
	
}
