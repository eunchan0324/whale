package com.cafe.order.domain.user.repo;

import com.cafe.order.domain.user.dto.User;
import com.cafe.order.domain.user.dto.UserRole;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class SqlUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public SqlUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // CREATE : seller 계정 DB 추가
    public User save(User user) {
        String sql = "INSERT INTO users (username, password, role, store_id) VALUES (?, ? ,? ,?)";

        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getRole().name(), user.getStoreId());

        return user;

    }

    // READ : user role로 해당 role User List 반환
    public List<User> findByRole(UserRole role) {
        String sql = "SELECT id, username, password, role, store_id FROM users WHERE role = ?";

        return jdbcTemplate.query(sql, userRowMapper(), role.name()); // Enum -> 문자열로 전달
    }

    // READ : User id로 해당 id User 반환
    public Optional<User> findById(Integer id) {
        String sql = "SELECT id, username, password, role, store_id FROM users WHERE id = ?";

        try {
            User user = jdbcTemplate.queryForObject(sql, userRowMapper(), id);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    // UPDATE : seller 계정 DB 수정
    public User update(User seller) {
        String sql = "UPDATE users SET password = ?, store_id = ? WHERE id = ?";

        jdbcTemplate.update(sql, seller.getPassword(), seller.getStoreId(), seller.getId());

        return seller;
    }

    // DELETE : seller id로 해당 계정 DB 삭제
    public void deleteById(Integer id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // ResultSet -> User 객체로 반환하는 RowMapper 메서드 (람다식)
    private RowMapper<User> userRowMapper() {

        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setRole(UserRole.valueOf(rs.getString("role")));

            // store_id가 NULL이면 null로 설정, 아니면 값 설정 (ADMIN, CUSTOMER 고려)
            Integer storeId = (Integer) rs.getObject("store_id");
            user.setStoreId(storeId);

            return user;
        };
    }

//     ResultSet -> User 객체로 반환하는 RowMapper 메서드 (익명 클래스로 구현한 원시 코드)
//    private RowMapper<User> userRowMapper() {
//        return new RowMapper<User>() {
//            @Override
//            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
//                User user = new User();
//                user.setId(rs.getInt("id"));
//                user.setUsername(rs.getString("username"));
//                user.setPassword(rs.getString("password"));
//                user.setRole(UserRole.valueOf(rs.getString("role")));
//                user.setStoreId(rs.getInt("store_id"));
//
//                return user;
//            }
//        };
//    }

    // ResultSet -> User 객체로 반환하는 RowMapper 메서드 (내부 클래스로 구현한 원시 코드)
//    private RowMapper<User> userRowMapper() {
//
//        // 메서드 내부에 클래스 선언
//        class UserRowMapperImpl implements RowMapper<User> {
//            @Override
//            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
//                User user = new User();
//                user.setId(rs.getInt("id"));
//                user.setUsername(rs.getString("username"));
//                user.setPassword(rs.getString("password"));
//                user.setRole(UserRole.valueOf(rs.getString("role")));
//                user.setStoreId(rs.getInt("store_id"));
//
//                return user;
//            }
//        }
//        // 내부 클래스 인스턴스 생성
//        RowMapper<User> userRowMapper = new UserRowMapperImpl();
//
//        // 반환
//        return userRowMapper;
//    }


}
