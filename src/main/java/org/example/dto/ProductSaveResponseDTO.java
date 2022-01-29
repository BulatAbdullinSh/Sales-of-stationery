package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductSaveResponseDTO {
  private Product product;

  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  public static class Product {
    private long id;
    private String name;
    private int price;
    private int qty;
    private String image;
  }
}
