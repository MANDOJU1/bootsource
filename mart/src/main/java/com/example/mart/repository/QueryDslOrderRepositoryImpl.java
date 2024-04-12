package com.example.mart.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.mart.entity.Item;
import com.example.mart.entity.Member;
import com.example.mart.entity.Order;
import com.example.mart.entity.QItem;
import com.example.mart.entity.QMember;
import com.example.mart.entity.QOrder;
import com.example.mart.entity.QOrderItem;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;

public class QueryDslOrderRepositoryImpl extends QuerydslRepositorySupport implements QueryDslOrderRepository {

    public QueryDslOrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    public List<Object[]> joinList() {

        // Q객체 가져오기
        QOrder order = QOrder.order;
        QMember member = QMember.member;
        QOrderItem orderItem = QOrderItem.orderItem;

        // 첫번째, 쿼리문 생성
        // 1. JPAQuery 2. JPQLQuery
        // 2번 방법 사용 JPQLQuery
        // select * from order o join member m on o.member_id= m.member_id
        JPQLQuery<Order> query = from(order);
        // join : innerJoin, leftJoin, rightJoin, fullJoin 지원
        // join(조인대상, 별칭으로 사용할 쿼리 타입)
        // query.innerJoin(order.member, member);

        // 내부조인 : join(), innerJoin()
        // query.join(order.member, member);

        // 외부조인
        query.join(order.member, member)
                .leftJoin(order.orderItems, orderItem);

        // 두번째, 결과 받기
        // select m, t
        JPQLQuery<Tuple> tuple = query.select(order, member, orderItem);
        List<Tuple> result = tuple.fetch();
        System.out.println("결과");
        // [[Order(id=1, member=Member(id=1, name=user1, zipcode=15102, city=서울시,
        // street=중구), orderDate=2024-04-12T16:00:18.317705, orderStatus=ORDER),
        // Member(id=1, name=user1, zipcode=15102, city=서울시, street=중구)]]
        // => 이런식으로 결과가 나옴
        System.out.println(result);

        // List<Tuple> ==> List<Object[]>

        List<Object[]> list = result.stream().map(t -> t.toArray()).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<Member> members() {

        QMember member = QMember.member;

        // select * from member where name = 'user1' order by name desc;
        // 첫 번째, 쿼리문 생성 => 1. JPAQuery 2. JPQLQuery
        JPQLQuery<Member> query = from(member);
        query.where(member.name.eq("user1")).orderBy(member.name.desc());
        // 두 번째, 결과받기
        JPQLQuery<Member> tuple = query.select(member);
        List<Member> list = tuple.fetch();
        return list;
    }

    @Override
    public List<Item> items() {
        QItem item = QItem.item;

        // select * from item where name='바지' and price > 20000
        // 쿼리문 생성 1. JPAQuery 2. JPQLQuery
        JPQLQuery<Item> query = from(item);
        query.where(item.name.eq("바지").and(item.price.gt(20000)));
        JPQLQuery<Item> tuple = query.select(item);
        List<Item> list = tuple.fetch();
        return list;
    }

}