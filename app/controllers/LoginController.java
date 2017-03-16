package controllers;

import models.*;
import helpers.ControllerService;

import java.util.List;
import java.util.ArrayList;

import javax.inject.*;

import play.mvc.*;
import play.data.Form;
import play.data.FormFactory;

public class LoginController extends ControllerService {

  @Inject FormFactory formFactory;
  private String sessionUsername = "username";
  private String sessionPermission = "permission";
  private List<String> errors = new ArrayList<String>(){};

  public Result login() {
    if(isLoggedIn(sessionUsername)) {
      return redirect("/");
    } else {
      return ok(views.html.login.render(errors));
    }
  }

  public Result doLogin() {
    Form<LoginModel> loginForm = formFactory.form(LoginModel.class).bindFromRequest();
    if(loginForm.hasErrors()) {
      return badRequest(views.html.login.render(errorsMessage(loginForm.errors())));
    } else {
      LoginModel loginResult = loginForm.get().authenticate(loginForm.get().username, encrypt(loginForm.get().password));
      if(loginResult != null) {
        session().put(sessionUsername, loginResult.username);
        session().put(sessionPermission, loginResult.permission);
        flash("loggedin", "Login success");
        return ok(views.html.index.render());
      } else {
        List<String> errors = new ArrayList<String>();
        errors.add("Username or Password is wrong..");
        return ok(views.html.login.render(errors));
      }
    }
  }

  public Result addUser() {
    Form<LoginModel> loginForm = formFactory.form(LoginModel.class).bindFromRequest();
    if(loginForm.hasErrors()) {
      return badRequest(views.html.login.render(errorsMessage(loginForm.errors())));
    } else {
      try {
        loginForm.get().save();
      }catch(Exception e) {
        return badRequest("Cannot create user.");
      }
      return ok();
    }
  }

  public Result test(Long id) {
    TestOneToMany qwe = TestOneToMany.findById(id);
    Form<TestManyToOne> form2 = formFactory.form(TestManyToOne.class).bindFromRequest();
    form2.get().otm = qwe;
    form2.get().save();
    System.out.println(TestOneToMany.findTest(id).mto);
    return ok();
  }

}
