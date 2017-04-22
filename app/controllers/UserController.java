package controllers;

import models.*;
import helpers.ControllerService;
import formats.ResetPasswordFormat;
import formats.ForgotPasswordFormat;

import java.util.Map;
import java.util.UUID;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import javax.inject.*;

import play.mvc.*;
import play.data.Form;
import play.data.FormFactory;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;

public class UserController extends ControllerService {

  @Inject FormFactory formFactory;
  @Inject MailerClient mailerClient;
  private List<String> errors = new ArrayList<String>(){};
  private Map<String,String> maps = new HashMap(){};

  // @Security.Authenticated(Secured.class)
  public Result login() {
    if(session(sessionUsername) != null) return redirect("/");
    else return ok(views.html.login.render(errors, ""));
  }

  public Result logout() {
    session().clear();
    return redirect("/");
  }

  public Result doLogin() {
    Form<UserModel> loginForm = formFactory.form(UserModel.class).bindFromRequest();
    if(loginForm.hasErrors()) {
      session().clear();
      return badRequest(views.html.login.render(errorsMessage(loginForm.errors()), loginForm.data().get("username")));
    }else {
      UserModel loginResult = loginForm.get().authenticate(loginForm.get().username, encrypt(loginForm.get().password));
      if(loginResult != null) {
        session().put(sessionUsername, loginResult.username);
        session().put(sessionPermission, loginResult.permission);
        flash("loggedin", "Login success.");
        return ok(views.html.index.render());
      }else {
        session().clear();
        errors.add("Username or Password is wrong..");
        return ok(views.html.login.render(errors, loginForm.data().get("username")));
      }
    }
  }

  public Result addUser() {
    Form<UserModel> userForm = formFactory.form(UserModel.class).bindFromRequest();
    if(userForm.hasErrors()) {
      return badRequest(views.html.login.render(errorsMessage(userForm.errors()), ""));
    }else {
      if(userForm.get().findUsername(userForm.get().username) == null) {
        try {
          userForm.get().password = encrypt(userForm.get().password);
          userForm.get().save();
        }catch(Exception e) {
          return badRequest("Cannot create user.");
        }
      }else {
        return badRequest("Username is already in use.");
      }
      return ok();
    }
  }

  public Result updateUser(Long id) {
    Form<UserModel> userForm = formFactory.form(UserModel.class).bindFromRequest();
    if(userForm.hasErrors()) {
      return badRequest(views.html.login.render(errorsMessage(userForm.errors()), ""));
    }else {
      UserModel uniqueUser = userForm.get().findUsername(userForm.get().username);
      UserModel savedUser = userForm.get().findUserById(id);
      if(uniqueUser == null && savedUser != null) {
        try {
          savedUser.username = userForm.get().username;
          savedUser.password = encrypt(userForm.get().password);
          savedUser.permission = userForm.get().permission;
          savedUser.update();
        }catch(Exception e) {
          return badRequest("Cannot update user.");
        }
      }else {
        return badRequest("Username is already in use.");
      }
      return ok();
    }
  }

  public Result deleteUser(Long id) {
    try {
      UserModel.findUserById(id).delete();
    }catch(Exception e){
      return badRequest("Cannot delete user.");
    }
    return ok();
  }

  public Result resetPassword() {
    Form<ResetPasswordFormat> resetForm = formFactory.form(ResetPasswordFormat.class).bindFromRequest();
    if(resetForm.hasErrors()) {
      return badRequest();
    }else {
      UserModel userResult = UserModel.findUsername(resetForm.get().username);
      if(userResult != null && resetForm.get().new_password.equals(resetForm.get().new_match_password)) {
        userResult.password = encrypt(resetForm.get().new_password);
        userResult.update();
        return ok();
      }else if(!resetForm.get().new_password.equals(resetForm.get().new_match_password)) {
        errors.add("Passwords does not match.");
        return badRequest();
      }else {
        errors.add("Username not found.");
        return badRequest();
      }
    }
  }

  public Result forgotPassword() {
    Form<ForgotPasswordFormat> forgotForm = formFactory.form(ForgotPasswordFormat.class).bindFromRequest();
    if(forgotForm.hasErrors()) {
      return badRequest();
    }else {
      UserModel uniqueUser = UserModel.findUsername(forgotForm.get().username);
      if(uniqueUser != null) {
        Form<UrlModel> urlForm = formFactory.form(UrlModel.class);
        String url = UUID.randomUUID().toString();
        Long time = new Date().getTime();
        maps.put("token", url);
        maps.put("time_verify", time.toString());
        UrlModel urls = urlForm.bind(maps).get();
        try {
          urls.save();
          sendEmail(uniqueUser.username, "http://localhost:9000/users/forgot/" + url);
        }catch(Exception e) {
          return badRequest("Cannot create verify url.");
        }
        return ok();
      }else {
        return badRequest();
      }
    }
  }

  public Result verifyPassword(String tokenVerify) {
    UrlModel urlModel = UrlModel.findToken(tokenVerify);
    if(urlModel != null) {
      if(expireTime(Long.parseLong(urlModel.time_verify))) {
        return ok(views.html.login.render(errors, ""));
      }else {
        return badRequest("Token has expired time.");
      }
    }else {
      return badRequest("Token not found.");
    }
  }

  private void sendEmail(String user, String link) {
    Email email = new Email()
    .setSubject("Simple email")
    .setFrom("nattapol.s@dotography.com")
    .addTo("siggysic@gmail.com")
    .setBodyHtml("<html><body><p>Email : " + user + "</p><p>Link to reset your password : <a href=" + link + ">Click here</a></p></body></html>");
    mailerClient.send(email);
  }

  private Boolean expireTime(Long date) {
    return new Date().getTime() <= date + (30 * 60000);
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
