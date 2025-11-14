package com.cafe.order.domain.store;

import com.cafe.order.domain.store.dto.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaStoreRepository extends JpaRepository<Store, Integer> {

}
