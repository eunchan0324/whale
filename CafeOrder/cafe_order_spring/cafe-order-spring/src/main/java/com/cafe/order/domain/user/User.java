package com.cafe.order.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // DB용 PK (자동 생성)

    @Column(nullable = false, unique = true, length = 50)
    private String username; // 로그인 ID

    @Column(nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role;

    @Column(name = "store_id")
    private Integer storeId; // Seller만 사용 (nullable)

    // ADMIN, CUSTOMER용 생성자
    public User(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // SELLER용 생성자
    public User(String username, String password, UserRole role, Integer storeId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.storeId = storeId;
    }


}
