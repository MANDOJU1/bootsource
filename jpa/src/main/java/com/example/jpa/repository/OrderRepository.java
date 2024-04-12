package com.example.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.jpa.entity.Address;
import com.example.jpa.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // 주소 조회
    @Query("SELECT o.homeAddress FROM Order o")
    List<Address> findByHomeAddress();

    @Query("SELECT o.member2, o.product, o.id FROM Order o")
    List<Object[]> findByOrders();
}