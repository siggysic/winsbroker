package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name = "login")
public class LoginModel extends Model {

  @Id
  @Constraints.Min(10)
  public Long id;

  @Constraints.Required(message = "Username is required")
  @Constraints.Email(message = "Username is not an email")
  public String username;

  @Constraints.Required(message = "Password is required")
  public String password;

  public String permission;

  public Finder<Long, LoginModel> find = new Finder<Long, LoginModel>(LoginModel.class);

  public List<LoginModel> findAll() {
    return find.all();
  }

  public LoginModel authenticate(String user, String pass) {
    return find.where()
      .eq("username", user)
      .eq("password", pass)
      .findUnique();
  }

}
