package controllers;

import models.*;
import helpers.ControllerService;

import java.util.List;
import java.util.ArrayList;

import javax.inject.*;

import play.mvc.*;
import play.data.Form;
import play.data.FormFactory;

public class UserController extends ControllerService {

  @Inject FormFactory formFactory;
  private String sessionUsername = "username";
  private String sessionPermission = "permission";
  private List<String> errors = new ArrayList<String>(){};

  public Result login() {
    if(isLoggedIn(sessionUsername)) {
      return redirect("/");
    }else {
      return ok(views.html.login.render(errors));
    }
  }

  public Result logout() {
    session().clear();
    return redirect("/");
  }

  public Result doLogin() {
    Form<UserModel> loginForm = formFactory.form(UserModel.class).bindFromRequest();
    if(loginForm.hasErrors()) {
      return badRequest(views.html.login.render(errorsMessage(loginForm.errors())));
    }else {
      UserModel loginResult = loginForm.get().authenticate(loginForm.get().username, encrypt(loginForm.get().password));
      if(loginResult != null) {
        session().put(sessionUsername, loginResult.username);
        session().put(sessionPermission, loginResult.permission);
        flash("loggedin", "Login success.");
        return ok(views.html.index.render());
      }else {
        // List<String> errors = new ArrayList<String>();
        errors.add("Username or Password is wrong..");
        return ok(views.html.login.render(errors));
      }
    }
  }

  public Result addUser() {
    Form<UserModel> userForm = formFactory.form(UserModel.class).bindFromRequest();
    if(userForm.hasErrors()) {
      return badRequest(views.html.login.render(errorsMessage(userForm.errors())));
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
      return badRequest(views.html.login.render(errorsMessage(userForm.errors())));
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

  public Result test(Long id) {
    TestOneToMany qwe = TestOneToMany.findById(id);
    Form<TestManyToOne> form2 = formFactory.form(TestManyToOne.class).bindFromRequest();
    form2.get().otm = qwe;
    form2.get().save();
    System.out.println(TestOneToMany.findTest(id).mto);
    return ok();
  }

}
