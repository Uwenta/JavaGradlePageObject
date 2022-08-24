package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Random;

public class DataHelper {
  private DataHelper() {}

  @Value
  public static class AuthInfo {
    private String login;
    private String password;
  }

  public static AuthInfo getAuthInfo() {
    return new AuthInfo("vasya", "qwerty123");
  }

  public static AuthInfo getOtherAuthInfo(AuthInfo original) {
    return new AuthInfo("petya", "123qwerty");
  }

  @Value
  public static class VerificationCode {
    private String code;
  }

  public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
    return new VerificationCode("12345");
  }

  public static String getNumberCard (int id) {
    if (id == 1) {
      return "5559 0000 0000 0001";
    } if (id == 2)  {
      return "5559 0000 0000 0002";
    }
    return null;
  }




}
