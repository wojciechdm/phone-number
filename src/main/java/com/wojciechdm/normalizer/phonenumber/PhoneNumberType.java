package com.wojciechdm.normalizer.phonenumber;

import java.util.function.UnaryOperator;

enum PhoneNumberType {
  MOBILE(phoneNumber -> new StringBuilder(phoneNumber).insert(3, "-").insert(7, "-").toString()),
  LANDLINE(
      phoneNumber ->
          new StringBuilder(phoneNumber).insert(2, " ").insert(6, "-").insert(9, "-").toString()),
  UNKNOWN(phoneNumber -> "Invalid phone number");

  private UnaryOperator<String> formatUnaryOperator;

  PhoneNumberType(UnaryOperator<String> formatUnaryOperator) {
    this.formatUnaryOperator = formatUnaryOperator;
  }

  String format(String phoneNumber) {
    return formatUnaryOperator.apply(phoneNumber);
  }
}
