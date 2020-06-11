package requests;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import java.io.IOException;

public class ObtainUserGet {

    private HttpGet request;
    private String responseJsonString;
    private JSONObject responseJSON;
    private String token;

    public ObtainUserGet(String token) {
        this.token = token;
    }

    public JSONObject obtainUser() throws IOException {

        request = new HttpGet("https://preprod-business.draewil.net/v1/user/");
        request.addHeader("x-authorization",token);
    try (CloseableHttpClient httpClient = HttpClients.createDefault();
         CloseableHttpResponse response = httpClient.execute(request)) {

        HttpEntity entity = response.getEntity();
        if (entity != null) {
            responseJsonString = EntityUtils.toString(entity);
        }

    }
    responseJSON = new JSONObject(responseJsonString);

    return responseJSON;
   }

}
