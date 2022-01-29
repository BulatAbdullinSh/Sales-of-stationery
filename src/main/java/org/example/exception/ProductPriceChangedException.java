package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ProductPriceChangedException extends RuntimeException {
  public ProductPriceChangedException() {
  }

  public ProductPriceChangedException(String message) {
    super(message);
  }

  public ProductPriceChangedException(String message, Throwable cause) {
    super(message, cause);
  }

  public ProductPriceChangedException(Throwable cause) {
    super(cause);
  }

  public ProductPriceChangedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
