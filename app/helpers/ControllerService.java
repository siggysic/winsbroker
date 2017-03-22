package helpers;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import javax.inject.*;

import play.mvc.*;
import play.data.validation.ValidationError;

import java.security.MessageDigest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class ControllerService extends Controller {

  @Inject ConfigService config;
  public String sessionUsername = "username";
  public String sessionPermission = "permission";

  // Login Service
  public Result isLoggedIn() {
    if(session(sessionUsername) != null) {
      return ok();
    }else {
      return redirect("/");
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

  // Security Service

  private SecretKeySpec secretKey;
  private byte[] key;

  public void setKey(String myKey) {
    MessageDigest sha = null;
    try {
      key = myKey.getBytes("UTF-8");
      sha = MessageDigest.getInstance("SHA-1");
      key = sha.digest(key);
      key = Arrays.copyOf(key, 16);
      secretKey = new SecretKeySpec(key, "AES");
    }catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  public String encrypt(String strToEncrypt) {
    try {
      setKey(config.secretKey);
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
      return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
    } catch (Exception e) {
      System.out.println("Error while encrypting: " + e.toString());
    }
    return null;
  }

  public String decrypt(String strToDecrypt) {
    try {
      setKey(config.secretKey);
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
      cipher.init(Cipher.DECRYPT_MODE, secretKey);
      return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    } catch (Exception e) {
      System.out.println("Error while decrypting: " + e.toString());
    }
    return null;
  }

}
