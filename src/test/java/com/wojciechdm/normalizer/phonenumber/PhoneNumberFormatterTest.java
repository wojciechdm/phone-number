package com.wojciechdm.normalizer.phonenumber;

import static com.wojciechdm.normalizer.phonenumber.PhoneNumberType.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

class PhoneNumberFormatterTest {

  private PhoneNumberFormatter phoneNumberFormatter;

  @BeforeEach
  void setUp() {
    phoneNumberFormatter = new PhoneNumberFormatter();
  }

  @ParameterizedTest
  @MethodSource("correctPhoneNumbersAndIdentifiedPhoneNumbers")
  void shouldReturnCorrectFormattedPhoneNumberAndTypeWhenNumberIsCorrect(
      String phoneNumber, String expectedPhoneNumber, PhoneNumberType expectedPhoneNumberType) {
    // when
    PhoneNumberDto actual = phoneNumberFormatter.identify(phoneNumber);
    String actualPhoneNumber = actual.getPhoneNumber();
    PhoneNumberType actualPhoneNumberType = actual.getPhoneNumberType();
    // then
    assertEquals(expectedPhoneNumber, actualPhoneNumber);
    assertEquals(expectedPhoneNumberType, actualPhoneNumberType);
  }

  @ParameterizedTest
  @MethodSource("incorrectPhoneNumbersAndIdentifiedPhoneNumbers")
  void shouldReturnUnknownPhoneNumberAndTypeWhenNumberIsIncorrect(
      String phoneNumber, String expectedPhoneNumber, PhoneNumberType expectedPhoneNumberType) {
    // when
    PhoneNumberDto actual = phoneNumberFormatter.identify(phoneNumber);
    String actualPhoneNumber = actual.getPhoneNumber();
    PhoneNumberType actualPhoneNumberType = actual.getPhoneNumberType();
    // then
    assertEquals(expectedPhoneNumber, actualPhoneNumber);
    assertEquals(expectedPhoneNumberType, actualPhoneNumberType);
  }

  static Stream<Arguments> correctPhoneNumbersAndIdentifiedPhoneNumbers() {
    return Stream.of(
        Arguments.of("817777777", "81 777-77-77", LANDLINE),
        Arguments.of("81 777-77-77", "81 777-77-77", LANDLINE),
        Arguments.of("81 7777777", "81 777-77-77", LANDLINE),
        Arguments.of("81 77777- 77", "81 777-77-77", LANDLINE),
        Arguments.of("600999999", "600-999-999", MOBILE));
  }

  static Stream<Arguments> incorrectPhoneNumbersAndIdentifiedPhoneNumbers() {
    return Stream.of(
        Arguments.of("81999992", "Invalid phone number", UNKNOWN),
        Arguments.of("81999999999", "Invalid phone number", UNKNOWN),
        Arguments.of("800999999", "Invalid phone number", UNKNOWN),
        Arguments.of("099999999", "Invalid phone number", UNKNOWN),
        Arguments.of("999.888000", "Invalid phone number", UNKNOWN),
        Arguments.of("67880cd89", "Invalid phone number", UNKNOWN),
        Arguments.of("", "Invalid phone number", UNKNOWN),
        Arguments.of("      ", "Invalid phone number", UNKNOWN),
        Arguments.of("$#%&&()&^", "Invalid phone number", UNKNOWN),
        Arguments.of("ghhjkklkk", "Invalid phone number", UNKNOWN));
  }
}
