package org.example.rowmapper;

import org.example.model.ProductFullModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductFullRowMapper implements RowMapper<ProductFullModel> {
  @Override
  public ProductFullModel mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new ProductFullModel(
        rs.getLong("id"),
        rs.getString("name"),
        rs.getInt("price"),
        rs.getInt("qty"),
        rs.getString("image")
    );
  }
}
