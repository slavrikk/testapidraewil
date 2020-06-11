package requests;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ChangeUserPersonalDataPut {

    private HttpPut httpPut;
    private String changingFirstName;
    private String changingLastName;
    private JSONObject requestJSON;
    private int responseNumber;
    private String responseStringJSON;
    private String token;

    public ChangeUserPersonalDataPut(String changingFirstName, String changingLastName, String token) {
        this.changingFirstName = changingFirstName;
        this.changingLastName = changingLastName;
        this.token = token;
    }

    public String getChangingFirstName() {
        return changingFirstName;
    }

    public String getChangingLastName() {
        return changingLastName;
    }

    public String getResponseStringJSON() {
        return responseStringJSON;
    }

    public int changePersonalData(){
        httpPut = new HttpPut("https://preprod-business.draewil.net/v1/user/");
        httpPut.addHeader("content-type", "application/json");
        httpPut.addHeader("x-authorization",token);

        requestJSON = new JSONObject();
        requestJSON.put("firstName", changingFirstName);
        requestJSON.put("lastName", changingLastName);

        try {
            httpPut.setEntity(new StringEntity(requestJSON.toString()));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPut)) {
            responseNumber  =  response.getStatusLine().getStatusCode();
            responseStringJSON = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }

     return responseNumber;
    }

}
