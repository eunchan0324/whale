package com.cafe.order.domain.store;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    private final JpaStoreRepository storeRepository;

    public StoreService(JpaStoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    // CREATE : 지점 생성
    public Store create(String name) {
        Store store = new Store(name);

        return storeRepository.save(store);
    }

    // READ : 전체 지점 조회
    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    // READ : 지점 id로 Store 반환
    public Store findById(Integer id) {
        return storeRepository.findById(id).orElse(null);
    }

    // UPDATE : 지점 수정
    public Store update(Integer id, String name) {
        Store store = storeRepository.findById(id).orElse(null);

        if (store == null) {
            throw new IllegalArgumentException("지점을 찾을 수 없습니다.");
        }

        store.setName(name);

        return storeRepository.save(store); // JPA
    }

    // DELETE : 지점 삭제
    public void delete(Integer id) {
        storeRepository.deleteById(id);
    }



}
