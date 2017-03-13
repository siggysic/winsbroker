package controllers;

import formats.LoginFormat;

import play.mvc.*;
import play.data.Form;
import play.data.FormFactory;

import javax.inject.*;

public class Login extends Controller {

  @Inject FormFactory formFactory;

  // @Inject
  // public FormattersProvider(MessagesApi messagesApi) {
  //     this.messagesApi = messagesApi;
  // }

  public Result login() {
    return ok(views.html.login.render("Login", Form.form(LoginFormat.class)));
  }

  public Result doLogin() {
    Form<LoginFormat> loginForm = formFactory.form(LoginFormat.class).bindFromRequest();
    // System.out.println(loginForm.get().getUsername());
    // formFactory.form(LoginFormat.class).bindFromRequest().errors().forEach((k, v) -> System.out.println(v));
    if(loginForm.hasErrors()) {
      System.out.println(loginForm.globalErrors());
      return badRequest(views.html.login.render("Login", loginForm));
    } else {
      return ok("Logged in : " + loginForm.get().getUsername());
    }
  }

}
