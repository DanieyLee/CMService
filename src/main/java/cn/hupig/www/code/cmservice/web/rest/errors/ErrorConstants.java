package cn.hupig.www.code.cmservice.web.rest.errors;

import java.net.URI;

public final class ErrorConstants {

    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    public static final String ERR_VALIDATION = "error.validation";
    public static final String PROBLEM_BASE_URL = "https://www.yuoai.com/problem";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");
    public static final URI INVALID_PASSWORD_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-password");
    public static final URI EMAIL_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/email-already-used");
    public static final URI PHONE_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/phone-already-used");
    public static final URI FILE_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/file-already-used");
    public static final URI KEY_BOX_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/key-box-already-used");
    public static final URI IMAGE_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/image-already-used");
    public static final URI LOGIN_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/login-already-used");

    private ErrorConstants() {
    }
}
