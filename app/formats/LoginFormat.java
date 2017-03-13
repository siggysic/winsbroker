package formats;

import play.data.validation.Constraints;

public class LoginFormat {

  @Constraints.Required(message = "Username is required")
  @Constraints.Email(message = "Username is not an email")
  protected String username;
  @Constraints.Required(message = "Password is required")
  protected String password;

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

}
