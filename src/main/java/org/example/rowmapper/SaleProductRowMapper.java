package org.example.rowmapper;

import org.example.model.SaleProductModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SaleProductRowMapper implements RowMapper<SaleProductModel> {
  @Override
  public SaleProductModel mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new SaleProductModel(
        rs.getLong("id"),
        rs.getString("name"),
        rs.getInt("price"),
        rs.getInt("qty")
    );
  }
}
