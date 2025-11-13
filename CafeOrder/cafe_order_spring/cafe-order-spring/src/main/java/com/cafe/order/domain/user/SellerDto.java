package com.cafe.order.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerDto {
    private Integer id;
    private String username;
    private String password;
    private Integer storeId;
    private String storeName;

    public SellerDto(User user, String storeName) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.storeId = user.getStoreId();
        this.storeName = storeName;
    }
}
