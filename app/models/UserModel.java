package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name = "users")
public class UserModel extends Model {

  @Id
  @Constraints.Min(10)
  public Long id;

  @Constraints.Required(message = "Username is required")
  @Constraints.Email(message = "Username is not an email")
  public String username;

  @Constraints.Required(message = "Password is required")
  public String password;

  public String permission;

  public static Finder<Long, UserModel> find = new Finder<Long, UserModel>(UserModel.class);

  public static List<UserModel> findAll() {
    return find.all();
  }

  public static UserModel findUsername(String user) {
    return find.where()
      .eq("username", user)
      .findUnique();
  }

  public static UserModel findUserById(Long id) {
    return find.byId(id);
  }

  public static UserModel authenticate(String user, String pass) {
    return find.where()
      .eq("username", user)
      .eq("password", pass)
      .findUnique();
  }

}
