package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SaleRegistrationException extends RuntimeException {
  public SaleRegistrationException() {
  }

  public SaleRegistrationException(String message) {
    super(message);
  }

  public SaleRegistrationException(String message, Throwable cause) {
    super(message, cause);
  }

  public SaleRegistrationException(Throwable cause) {
    super(cause);
  }

  public SaleRegistrationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
