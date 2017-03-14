package helpers;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import play.mvc.*;
import play.data.validation.ValidationError;


public class ControllerService extends Controller {
  // Login Service
  public Boolean isLoggedIn(String head) {
    if(session(head) != null) {
      return true;
    }else {
      return false;
    }
  }

  // Validate Service
  public List<String> errorsMessage(Map<String, List<ValidationError>> errors) {
    List<String> bundleErrors = new ArrayList<String>();
    errors.forEach((k, v) -> {
      v.forEach((error) -> bundleErrors.add(error.message()));
    });
    return bundleErrors;
  }

}
