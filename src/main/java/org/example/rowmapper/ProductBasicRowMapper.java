package org.example.rowmapper;

import org.example.model.ProductBasicModel;
import org.example.model.ProductFullModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductBasicRowMapper implements RowMapper<ProductBasicModel> {
  @Override
  public ProductBasicModel mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new ProductBasicModel(
        rs.getLong("id"),
        rs.getString("name"),
        rs.getInt("price"),
        rs.getInt("qty"),
        rs.getString("image")
    );
  }
}
