package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaleModel {
  private long id;
  private long productId;
  private String name;
  private int price;
  private int qty;
}
