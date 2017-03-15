package controllers;

import models.LoginModel;
import helpers.ControllerService;

import java.util.List;
import java.util.ArrayList;

import javax.inject.*;

import play.mvc.*;
import play.data.Form;
import play.data.FormFactory;

public class LoginController extends ControllerService {

  @Inject FormFactory formFactory;
  private String sessionTopic = "loggedin";
  private List<String> errors = new ArrayList<String>(){};

  public Result login() {
    if(isLoggedIn(sessionTopic)) {
      return redirect("/");
    }else {
      return ok(views.html.login.render(errors));
    }
  }

  public Result doLogin() {
    Form<LoginModel> loginForm = formFactory.form(LoginModel.class).bindFromRequest();
    if(loginForm.hasErrors()) {
      return badRequest(views.html.login.render(errorsMessage(loginForm.errors())));
    } else {
      LoginModel loginResult = loginForm.get().authenticate(loginForm.get().username, loginForm.get().password);
      if(loginResult != null) {
        session(sessionTopic, loginForm.get().username);
        return ok("Logged in : " + loginForm.get());
      }else {
        List<String> errors = new ArrayList<String>();
        errors.add("Username or Password is wrong..");
        return ok(views.html.login.render(errors));
      }
    }
  }

}
