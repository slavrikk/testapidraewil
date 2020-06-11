package testclasses;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import requests.ChangeUserPersonalDataPut;
import requests.LoginUserPost;
import requests.ObtainUserGet;
import requests.RegistrarPost;
import java.io.IOException;

public class TestScenario {

    private RegistrarPost registrarPost;
    private LoginUserPost loginUserPost;
    private ObtainUserGet obtainUserGet;
    private ObtainUserGet obtainUserFromChanging;
    private ChangeUserPersonalDataPut changeUserPersonalDataPut;
    private String token;
    private JSONObject responseFromRegistration;
    private JSONObject responseFromLogin;
    private JSONObject responseFromGetUser;
    private JSONObject responseJSONFromChangingUser;

    @Test(priority = 1)
    @Parameters({"firstName", "lastName", "email", "password", "phoneNumber"})
    public void testRegistrarUser(String firstName, String lastName, String email, String password, String phoneNumber){
        registrarPost = new RegistrarPost(firstName, lastName, email, password, phoneNumber);
        responseFromRegistration = registrarPost.postRegisterUser();
        int actualResponse = registrarPost.getResponseNumber();
        Assert.assertEquals(actualResponse,200);
    }

    @Test(priority = 2)
    public void testLoginUser(){
        String responseEmail = (String)responseFromRegistration.get("email");
        String responsePassword = registrarPost.getPassword();
        loginUserPost = new LoginUserPost(responseEmail, responsePassword);
        responseFromLogin = loginUserPost.loginUser();
        int actualResponse = loginUserPost.getResponseNumber();
       Assert.assertEquals(actualResponse,200);
    }

    @Test(priority = 3)
    public void testCorrectName() throws IOException {
        token = (String)responseFromLogin.get("token");
         obtainUserGet = new ObtainUserGet(token);
        responseFromGetUser = obtainUserGet.obtainUser();
        String responseFullName = (String)responseFromGetUser.get("fullName");
        Assert.assertEquals(responseFullName,registrarPost.getFirstName()+" "+registrarPost.getLastName());
    }

    @Test(priority = 4)
    public void testCorrectEmail(){
        String responseEmail = (String)responseFromGetUser.get("email");
        Assert.assertEquals(responseEmail, registrarPost.getEmail());
    }

    @Test(priority = 5)
    public void testCorrectNumber(){
    String responseNumber = (String)responseFromGetUser.get("phoneNumber");
    Assert.assertEquals(responseNumber, registrarPost.getPhoneNumber());
    }

    @Test(priority = 6)
    @Parameters({"changingFirstName", "changingLastName"})
    public void testChangePersonalData(String changingFirstName, String changingLastName){
        changeUserPersonalDataPut = new ChangeUserPersonalDataPut(changingFirstName, changingLastName, token);
        int responseNumber = changeUserPersonalDataPut.changePersonalData();
        Assert.assertEquals(responseNumber,200);
    }

    @Test(priority = 7)
    public void testChangingFullName() throws IOException {
        obtainUserFromChanging = new ObtainUserGet(token);
        responseJSONFromChangingUser = obtainUserFromChanging.obtainUser();
        String changingFullName = (String)responseJSONFromChangingUser.get("fullName");
        Assert.assertEquals(changingFullName, changeUserPersonalDataPut.getChangingFirstName()+" "+changeUserPersonalDataPut.getChangingLastName());
    }
}
