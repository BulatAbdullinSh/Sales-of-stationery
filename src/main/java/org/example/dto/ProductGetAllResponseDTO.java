package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductGetAllResponseDTO {
  private List<Product> products;

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
