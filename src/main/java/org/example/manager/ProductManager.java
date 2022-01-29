package org.example.manager;

import lombok.RequiredArgsConstructor;
import org.example.dto.ProductGetAllResponseDTO;
import org.example.dto.ProductGetByIdResponseDTO;
import org.example.dto.ProductSaveRequestDTO;
import org.example.dto.ProductSaveResponseDTO;
import org.example.exception.ProductNotFoundException;
import org.example.model.ProductBasicModel;
import org.example.model.ProductFullModel;
import org.example.rowmapper.ProductBasicRowMapper;
import org.example.rowmapper.ProductFullRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProductManager {
  private final NamedParameterJdbcTemplate template;
  private final ProductBasicRowMapper productBasicRowMapper;
  private final ProductFullRowMapper productFullRowMapper;
  private final String defaultImage = "noimage.png";

  // усечённая модель
  public ProductGetAllResponseDTO getAll() {
    final List<ProductBasicModel> items = template.query(
        // language=PostgreSQL
        """
            SELECT id, name, price, qty, image FROM products
            WHERE removed = FALSE
            ORDER BY id
            LIMIT 500
            """,
        productBasicRowMapper
    );

    final ProductGetAllResponseDTO responseDTO = new ProductGetAllResponseDTO(new ArrayList<>(items.size()));
    for (ProductBasicModel item : items) {
      responseDTO.getProducts().add(new ProductGetAllResponseDTO.Product(
          item.getId(),
          item.getName(),
          item.getPrice(),
          item.getQty(),
          item.getImage()
      ));
    }

    return responseDTO;
  }

  // полная модель
  public ProductGetByIdResponseDTO getById(long id) {
    try {
      final ProductFullModel item = template.queryForObject(
          // language=PostgreSQL
          """
              SELECT id, name, price, qty, image FROM products
              WHERE id = :id AND removed = FALSE
              """,
          Map.of("id", id),
          productFullRowMapper
      );

      final ProductGetByIdResponseDTO responseDTO = new ProductGetByIdResponseDTO(new ProductGetByIdResponseDTO.Product(
          item.getId(),
          item.getName(),
          item.getPrice(),
          item.getQty(),
          item.getImage()
      ));
      return responseDTO;
    } catch (EmptyResultDataAccessException e) {
      throw new ProductNotFoundException(e);
    }
  }

  public ProductSaveResponseDTO save(ProductSaveRequestDTO requestDTO) {
    return requestDTO.getId() == 0 ? create(requestDTO) : update(requestDTO);
  }

  private ProductSaveResponseDTO create(ProductSaveRequestDTO requestDTO) {
    final ProductFullModel item = template.queryForObject(
        // language=PostgreSQL
        """
            INSERT INTO products (name, price, qty, image) VALUES (:name, :price, :qty, :image)
            RETURNING id, name, price, qty, image
            """,
        Map.of(
            "name", requestDTO.getName(),
            "price", requestDTO.getPrice(),
            "qty", requestDTO.getQty(),
            "image", getImage(requestDTO.getImage())
        ),
        productFullRowMapper
    );

    final ProductSaveResponseDTO responseDTO = new ProductSaveResponseDTO(new ProductSaveResponseDTO.Product(
        item.getId(),
        item.getName(),
        item.getPrice(),
        item.getQty(),
        item.getImage()
    ));

    return responseDTO;
  }

  public void removeById(long id) {
    final int affected = template.update(
        // language=PostgreSQL
        """
            UPDATE products SET removed = TRUE WHERE id = :id
            """,
        Map.of("id", id)
    );
    if (affected == 0) {
      throw new ProductNotFoundException("product with id " + id + " not found");
    }
  }

  private ProductSaveResponseDTO update(ProductSaveRequestDTO requestDTO) {
    try {
      final ProductFullModel item = template.queryForObject(
          // language=PostgreSQL
          """
              UPDATE products SET name = :name, price = :price, qty = :qty, image = :image
              WHERE id = :id AND removed = FALSE
              RETURNING id, name, price, qty, image
              """,
          Map.of(
              "id", requestDTO.getId(),
              "name", requestDTO.getName(),
              "price", requestDTO.getPrice(),
              "qty", requestDTO.getQty(),
              "image", getImage(requestDTO.getImage())
          ),
          productFullRowMapper
      );

      final ProductSaveResponseDTO responseDTO = new ProductSaveResponseDTO(new ProductSaveResponseDTO.Product(
          item.getId(),
          item.getName(),
          item.getPrice(),
          item.getQty(),
          item.getImage()
      ));

      return responseDTO;
    } catch (EmptyResultDataAccessException e) {
      throw new ProductNotFoundException(e);
    }
  }

  private String getImage(String image) {
    return image == null ? defaultImage : image;
  }
}
