package com.devsu.utilities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Util {

  public static boolean validatePassword(String password, String regex) {
    Pattern pattern = Pattern.compile(regex);
    return pattern.matcher(password).matches();
  }

}
