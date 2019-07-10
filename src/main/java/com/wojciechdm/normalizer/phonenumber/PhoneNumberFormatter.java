package com.wojciechdm.normalizer.phonenumber;

import static com.google.common.base.Preconditions.*;
import static java.util.Objects.*;

import com.google.common.collect.ImmutableSet;

import java.util.*;
import java.util.regex.Pattern;

public class PhoneNumberFormatter {

  private static final Pattern SEPARATE_SYMBOLS_REGEX = Pattern.compile("[- ]+");
  private static final Pattern PHONE_NUMBER_REGEX = Pattern.compile("[1-9]\\d{8}");

  private static final Set<String> PHONE_PREFIXES =
      ImmutableSet.of(
          "12", "13", "14", "15", "16", "17", "18", "22", "23", "24", "25", "29", "32", "33", "34",
          "41", "42", "43", "44", "46", "48", "52", "54", "55", "56", "58", "59", "61", "62", "63",
          "65", "67", "68", "71", "74", "75", "76", "77", "81", "82", "83", "84", "85", "86", "87",
          "89", "91", "94", "95");

  private static final Set<String> SPECIAL_PHONE_PREFIXES =
      ImmutableSet.of(
          "700", "701", "703", "704", "707", "708", "800", "801", "802", "804", "806", "808");

  public PhoneNumberDto identify(final String phoneNumber) {

    checkArgument(nonNull(phoneNumber), "Phone number can't be null");

    String cleanedPhoneNumber = clean(phoneNumber);
    return new PhoneNumberDto(
        generateFormatted(cleanedPhoneNumber), identifyType(cleanedPhoneNumber));
  }

  private String clean(String phoneNumber) {
    return SEPARATE_SYMBOLS_REGEX.matcher(phoneNumber).replaceAll("");
  }

  private PhoneNumberType identifyType(String phoneNumber) {
    return recognizeType(phoneNumber);
  }

  private String generateFormatted(final String phoneNumber) {
    return format(phoneNumber);
  }

  private PhoneNumberType recognizeType(String phoneNumber) {
    return isInvalid(phoneNumber) ? PhoneNumberType.UNKNOWN : (isLandline(phoneNumber) ? PhoneNumberType.LANDLINE : PhoneNumberType.MOBILE);
  }

  private String format(String phoneNumber) {
    return recognizeType(phoneNumber).format(phoneNumber);
  }

  private boolean isInvalid(String phoneNumber) {
    return !PHONE_NUMBER_REGEX.matcher(phoneNumber).matches() || isSpecial(phoneNumber);
  }

  private boolean isLandline(String phoneNumber) {
    return PHONE_PREFIXES.contains(phoneNumber.substring(0, 2));
  }

  private boolean isSpecial(String phoneNumber) {
    return SPECIAL_PHONE_PREFIXES.contains(phoneNumber.substring(0, 3));
  }
}
