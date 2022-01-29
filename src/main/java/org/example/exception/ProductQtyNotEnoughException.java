package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ProductQtyNotEnoughException extends RuntimeException {
  public ProductQtyNotEnoughException() {
  }

  public ProductQtyNotEnoughException(String message) {
    super(message);
  }

  public ProductQtyNotEnoughException(String message, Throwable cause) {
    super(message, cause);
  }

  public ProductQtyNotEnoughException(Throwable cause) {
    super(cause);
  }

  public ProductQtyNotEnoughException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
