package requests;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class LoginUserPost {

    private String email;
    private String password;
    private JSONObject jsonObjectRequest;
    private JSONObject jsonObjectRespond;
    private int responseNumber;
    private String responseStringJSON;

    public LoginUserPost(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getResponseNumber() {
        return responseNumber;
    }

    public String getResponseStringJSON() {
        return responseStringJSON;
    }

    public JSONObject loginUser(){
        HttpPost post = new HttpPost("https://preprod-business.draewil.net/v1/auth/basic");
        post.addHeader("content-type", "application/json");

        jsonObjectRequest = new JSONObject();
        jsonObjectRequest.put("email", email);
        jsonObjectRequest.put("password", password);

        try {
            post.setEntity(new StringEntity(jsonObjectRequest.toString()));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
            responseNumber  =  response.getStatusLine().getStatusCode();
            responseStringJSON = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }

        jsonObjectRespond = new JSONObject(responseStringJSON);

        return jsonObjectRespond;
    }

}
