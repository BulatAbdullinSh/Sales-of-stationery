package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductSaveRequestDTO {
  private long id;
  private String name;
  private int price;
  private int qty;
  private String image;
}
