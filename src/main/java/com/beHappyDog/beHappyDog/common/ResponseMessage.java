package com.beHappyDog.beHappyDog.common;

public interface ResponseMessage {

    String SUCCESS = "Success.";

    String VALIDATION_FAIL = "Validation filed.";
    String DUPLICATE_EMAIL = "Duplicate EMAIL.";

    String SIGN_IN_FAIL = "Login information mismatch.";
    String CERTIFICATION_FAIL = "Certification failed.";

    String MAIL_FAIL = "Mail send failed. ";
    String DATABASE_ERROR = "Databases error";
}
