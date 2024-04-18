package com.example.board.repository.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.board.entity.Board;
import com.example.board.entity.QBoard;
import com.example.board.entity.QMember;
import com.example.board.entity.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Page<Object[]> list(String type, String keyword, Pageable pageable) {
        log.info("Board + Reply + Member join");

        // Q 클래스 사용
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        // @Query("select b, m from Board b left join b.writer m")
        JPQLQuery<Board> query = from(board);
        query.leftJoin(board.writer, member);

        // subquery → JPAExpressions
        JPQLQuery<Long> replyCount = JPAExpressions.select(reply.rno.count().as("replycnt"))
                .from(reply)
                .where(reply.board.eq(board))
                .groupBy(reply.board);

        JPQLQuery<Tuple> tuple = query.select(board, member, replyCount);

        // where b1_0.bno>?
        // and (b1_0.title like ? escape '!'
        // or b1_0.content like ? escape '!'
        // or w1_0.email like ? escape '!')
        // 검색
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(board.bno.gt(0L));

        // 검색 타입이 있는 경우
        // tc, tcw 때문에 contains 사용
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")) {
            // OR content LIKE '%content%'
            conditionBuilder.or(board.title.contains(keyword));
        }
        if (type.contains("c")) {
            // OR content LIKE '%content%'
            conditionBuilder.or(board.content.contains(keyword));
        }
        if (type.contains("w")) {
            // OR content LIKE '%content%'
            conditionBuilder.or(member.email.contains(keyword));
        }
        builder.and(conditionBuilder);
        tuple.where(builder);

        // 페이지 나누기
        // sort 지정
        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            // com.querydsl.types
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder<Board> orderByExpression = new PathBuilder<>(Board.class, "board");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });
        // 페이지 처리
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();
        // 전체 개수
        long count = tuple.fetchCount();
        List<Object[]> list = result.stream().map(t -> t.toArray()).collect(Collectors.toList());

        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Object[] getRow(Long bno) {
        log.info("상세조회 요청");

        // Q 클래스 사용
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        // @Query("select b, m from Board b left join b.writer m")
        JPQLQuery<Board> query = from(board);
        query.leftJoin(board.writer, member);
        query.where(board.bno.eq(bno));

        // subquery → JPAExpressions
        JPQLQuery<Long> replyCount = JPAExpressions.select(reply.rno.count().as("replycnt"))
                .from(reply)
                .where(reply.board.eq(board))
                .groupBy(reply.board);

        JPQLQuery<Tuple> tuple = query.select(board, member, replyCount);

        Tuple result = tuple.fetch().get(0);

        return result.toArray();
    }

}