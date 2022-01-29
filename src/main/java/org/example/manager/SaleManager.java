package org.example.manager;

import lombok.RequiredArgsConstructor;
import org.example.dto.SaleRegisterRequestDTO;
import org.example.dto.SaleRegisterResponseDTO;
import org.example.exception.ProductNotFoundException;
import org.example.exception.ProductPriceChangedException;
import org.example.exception.ProductQtyNotEnoughException;
import org.example.exception.SaleRegistrationException;
import org.example.model.SaleModel;
import org.example.model.SaleProductModel;
import org.example.rowmapper.SaleProductRowMapper;
import org.example.rowmapper.SaleRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SaleManager {
  private final NamedParameterJdbcTemplate template;
  private final SaleProductRowMapper saleProductRowMapper;
  private final SaleRowMapper saleRowMapper;

  @Transactional
  public SaleRegisterResponseDTO register(SaleRegisterRequestDTO requestDTO) {
    try {
      final SaleProductModel item = template.queryForObject(
          // language=PostgreSQL
          """
              SELECT id, name, price, qty FROM products
              WHERE id = :id AND removed = FALSE
              """,
          Map.of("id", requestDTO.getProductId()),
          saleProductRowMapper
      );

      if (item.getQty() < requestDTO.getQty()) {
        throw new ProductQtyNotEnoughException("product with id " + item.getId() + " has qty " + item.getQty() + " but requested " + requestDTO.getQty());
      }

      if (item.getPrice() != requestDTO.getPrice()) {
        throw new ProductPriceChangedException("product with id " + item.getId() + " has price " + item.getPrice() + " but requested " + requestDTO.getPrice());
      }

      final int affected = template.update(
          // language=PostgreSQL
          """
              UPDATE products SET qty = qty - :saleQty WHERE id = :productId AND removed = FALSE
              """,
          Map.of(
              "productId", requestDTO.getProductId(),
              "saleQty", requestDTO.getQty()
          )
      );

      if (affected == 0) {
        throw new SaleRegistrationException("can't update qty with value " + requestDTO.getQty() + " for product with id " + requestDTO.getProductId());
      }

      final SaleModel sale = template.queryForObject(
          // language=PostgreSQL
          """
              INSERT INTO sales (product_id, name, price, qty) VALUES (:productId, :name, :price, :qty)
              RETURNING id, product_id, name, price, qty
              """,
          Map.of(
              "productId", requestDTO.getProductId(),
              "name", item.getName(),
              "price", requestDTO.getPrice(),
              "qty", requestDTO.getQty()
          ),
          saleRowMapper
      );

      return new SaleRegisterResponseDTO(new SaleRegisterResponseDTO.Sale(
          sale.getId(),
          sale.getProductId(),
          sale.getName(),
          sale.getPrice(),
          sale.getQty()
      ));

    } catch (EmptyResultDataAccessException e) {
      throw new ProductNotFoundException(e);
    }
  }
}
