package formats;

import play.data.validation.Constraints;

public class ForgotPasswordFormat {

  @Constraints.Required(message = "Username is required")
  @Constraints.Email(message = "Username is not an email")
  @Constraints.MinLength(value = 5, message = "Username must be size more than 5")
  @Constraints.MaxLength(value = 20, message = "Username must be size less than 20")
  public String username;
}
