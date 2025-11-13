package com.cafe.order.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Integer> {

    // role 필터링
    List<User> findByRole(UserRole role);

}
