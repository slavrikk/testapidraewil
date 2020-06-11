package requests;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class RegistrarPost {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private JSONObject jsonObjectRequest;
    private JSONObject jsonObjectRespond;
    private int responseNumber;
    private String responseStringJSON;

    public RegistrarPost(String firstName, String lastName, String email, String password, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public JSONObject postRegisterUser(){
    HttpPost post = new HttpPost("https://preprod-business.draewil.net/v1/enterprise-web/user");
    post.addHeader("content-type", "application/json");

        jsonObjectRequest = new JSONObject();
        jsonObjectRequest.put("firstName", firstName);
        jsonObjectRequest.put("lastName", lastName);
        jsonObjectRequest.put("email", email);
        jsonObjectRequest.put("password", password);
        jsonObjectRequest.put("phoneNumber", phoneNumber);

        try {
            post.setEntity(new StringEntity(jsonObjectRequest.toString()));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
            responseNumber  =  response.getStatusLine().getStatusCode();
            responseStringJSON = EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        jsonObjectRespond = new JSONObject(responseStringJSON);

        return jsonObjectRespond;
    }

    public int getResponseNumber() {
        return responseNumber;
    }

}
