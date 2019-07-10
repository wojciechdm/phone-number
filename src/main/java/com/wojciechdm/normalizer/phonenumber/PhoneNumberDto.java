package com.wojciechdm.normalizer.phonenumber;

public class PhoneNumberDto {

  private String phoneNumber;
  private PhoneNumberType phoneNumberType;

  public PhoneNumberDto(String phoneNumber, PhoneNumberType phoneNumberType) {
    this.phoneNumber = phoneNumber;
    this.phoneNumberType = phoneNumberType;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public PhoneNumberType getPhoneNumberType() {
    return phoneNumberType;
  }
}
