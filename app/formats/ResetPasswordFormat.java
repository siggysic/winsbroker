package formats;

import play.data.validation.Constraints;

public class ResetPasswordFormat {

  @Constraints.Required(message = "Username is required")
  @Constraints.Email(message = "Username is not an email")
  public String username;

  @Constraints.Required(message = "Password is required")
  public String password;

  @Constraints.Required(message = "New password is required")
  public String new_password;

  @Constraints.Required(message = "New password 2 is required")
  public String new_match_password;

  // public void setUsername(String username) {
  //   this.username = username;
  // }
  //
  // public void setPassword(String password) {
  //   this.password = password;
  // }
  //
  // public String getUsername() {
  //   return username;
  // }
  //
  // public String getPassword() {
  //   return password;
  // }
}
