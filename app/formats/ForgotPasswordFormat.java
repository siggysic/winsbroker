package formats;

import play.data.validation.Constraints;

public class ForgotPasswordFormat {

  @Constraints.Required(message = "Username is required")
  @Constraints.Email(message = "Username is not an email")
  public String username;
}
