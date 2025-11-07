package com.cafe.order.domain.menu;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class SqlMenuRepository {

  private final JdbcTemplate jdbcTemplate;


  public SqlMenuRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Menu> findAll() {
    // 1. SQL 직접 작성
    String sql = "SELECT id, name, price, category, description, recommend FROM menus";

    // 2. 쿼리 실행 + RowMapper로 변환
    return jdbcTemplate.query(sql, menuRowMapper());
  }

  // ResultSet을 Menu 객체로 변환하는 메서드
  private RowMapper<Menu> menuRowMapper() {
    return (rs, rowNum) -> {
      Menu menu = new Menu();

      // UUID 변환 (H2의 BINARY(16))
      byte[] idBytes = rs.getBytes("id");
      menu.setId(convertBytesToUUID(idBytes));

      // 나머지 필드
      menu.setName(rs.getString("name"));
      menu.setPrice(rs.getInt("price"));
      menu.setCategory(Category.valueOf(rs.getString("category")));
      menu.setDescription(rs.getString("description"));
      menu.setRecommend(rs.getString("recommend"));

      return menu;
    };
  }

  // byte[] -> UUID 변환 헬퍼
  public UUID convertBytesToUUID(byte[] bytes) {
    if (bytes == null || bytes.length != 16) {
      return null;
    }

    long mostSigBits = 0;
    long leastSigBits = 0;

    for (int i = 0; i < 8; i++) {
      mostSigBits = (mostSigBits << 8) | (bytes[i] & 0xff);
    }

    for (int i = 8; i < 16; i++) {
      leastSigBits = (leastSigBits << 8) | (bytes[i] & 0xff);
    }

    return new UUID(mostSigBits, leastSigBits);
  }


}
