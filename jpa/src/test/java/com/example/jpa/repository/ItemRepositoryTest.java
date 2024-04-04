package com.example.jpa.repository;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Item;
import com.example.jpa.entity.ItemSellStatus;

@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void createTest() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Item item = Item.builder()
                    .itemNm("운동화" + i)
                    .price(95000 * i)
                    .stockNumber(30)
                    .itemSellStatus(ItemSellStatus.SELL)
                    .build();
            itemRepository.save(item);
        });
    }

    @Test
    public void readTest() {
        System.out.println(itemRepository.findById(2L));

        System.out.println("---------------");

        itemRepository.findAll().forEach(item -> System.out.println(item));
    }

    @Test
    public void updateTest() {
        // entity 찾기
        Item item = itemRepository.findById(4L).get();

        // 수정 - 아이템명, 가격
        item.setItemNm("바지");
        item.setPrice(1250000);
        System.out.println(itemRepository.save(item));
    }

    @Test
    public void deleteTest() {
        // entity 찾기
        Item item = itemRepository.findById(4L).get();

        // 삭제
        itemRepository.delete(item);
        System.out.println(itemRepository.findById(5L));
    }
}
