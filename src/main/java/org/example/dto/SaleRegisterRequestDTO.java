package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaleRegisterRequestDTO {
  private long productId;
  private int price;
  private int qty;
}
