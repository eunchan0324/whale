package com.cafe.order.domain.user.service;

import com.cafe.order.domain.store.service.StoreService;
import com.cafe.order.domain.user.JpaUserRepository;
import com.cafe.order.domain.user.dto.SellerDto;
import com.cafe.order.domain.user.dto.User;
import com.cafe.order.domain.user.dto.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final JpaUserRepository userRepository;
    private final StoreService storeService;

//    private final SqlUserRepository userRepository;

//    private final InMemoryUserRepository userRepository;

    public UserService(JpaUserRepository userRepository, StoreService storeService) {
        this.userRepository = userRepository;
        this.storeService = storeService;
    }

    // READ : 전체 판매자 계정 조회
    public List<User> findAllSellers() {
        return userRepository.findByRole(UserRole.SELLER);
    }

    // READ : 판매자 ID로 판매자 조회
    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    // CREATE : 판매자 계정 생성
    public User create(String username, String password, Integer storeId) {
        User user = new User(username, password, UserRole.SELLER, storeId);
        return userRepository.save(user);
    }

    // UPDATE : 판매자 계정 수정
    public User update(Integer id, String password, Integer storeId) {
        User seller = userRepository.findById(id).orElse(null);

        if (seller == null) {
            throw new IllegalArgumentException("판매자를 찾을 수 없습니다.");
        }

        // 비밀번호가 입력된 경우만 변경
        if (password != null && !password.isEmpty()) {
            seller.setPassword(password);
        }

        seller.setStoreId(storeId);

        return userRepository.save(seller); // JPA
//        return userRepository.update(user); // SQL, InMemory
    }

    // DELETE : 판매자 계정 삭제
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }


    // 이미 배정된 지점 ID 목록
    public List<Integer> getAssignedStoreIds() {
        List<User> sellers = userRepository.findByRole(UserRole.SELLER);

        return sellers.stream()
                .map(User::getStoreId)
                .filter(storeId -> storeId != null)
                .distinct()
                .collect(Collectors.toList());
    }

    // 특정 판매자를 제외한 배정된 지점 ID 목록
    public List<Integer> getAssignedStoreIdsExcept(Integer excludeSellerId) {
        List<User> sellers = userRepository.findByRole(UserRole.SELLER);

        return sellers.stream()
                .filter(seller -> !seller.getId().equals(excludeSellerId)) // 현재 판매자 제외
                .map(seller -> seller.getStoreId())
                .filter(storeId -> storeId != null)
                .distinct()
                .collect(Collectors.toList());
    }

    // 판매자 목록 (지점 이름 포함)
    public List<SellerDto> findAllSellerWithStoreName() {
        List<User> sellers = userRepository.findByRole(UserRole.SELLER);

        return sellers.stream()
                .map(seller -> {
                    String storeName = storeService.findById(seller.getStoreId()).getName();
                    return new SellerDto((seller), storeName);
                })
                .collect(Collectors.toList());
    }


}

