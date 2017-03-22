package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;

import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name = "urls")
public class UrlModel extends Model {

  @Id
  @Constraints.Min(10)
  public Long id;

  @Constraints.Required(message = "Token is required")
  public String token;

  @Constraints.Required(message = "Timestamp is required")
  public String time_verify;

  public static Finder<Long, UrlModel> find = new Finder<Long, UrlModel>(UrlModel.class);

  public static UrlModel findToken(String token) {
    return find.where()
      .eq("token", token)
      .findUnique();
  }

}
