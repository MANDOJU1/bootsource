package com.example.movie.repository.total;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.entity.QMovie;
import com.example.movie.entity.QMovieImage;
import com.example.movie.entity.QReview;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class MovieImageReviewRepositoryImpl extends QuerydslRepositorySupport implements MovieImageReviewRepository {

    public MovieImageReviewRepositoryImpl() {
        super(MovieImage.class);

    }

    @Override
    public Page<Object[]> getTotalList(String type, String keyword, Pageable pageable) {
        log.info("==== querydsl getTotalList ====");

        // Q 클래스 가져오기
        QMovie movie = QMovie.movie;
        QReview review = QReview.review;
        QMovieImage movieImage = QMovieImage.movieImage;

        // 기준이 되는 클래스 from
        JPQLQuery<MovieImage> query = from(movieImage);
        query.leftJoin(movie).on(movieImage.movie.eq(movie));

        // 서브 쿼리는 from 절 사용을 못함
        // subquery select 구문 → JPAExpressions
        JPQLQuery<Tuple> tuple = query.select(movie, movieImage,
                JPAExpressions.select(review.countDistinct()).from(review).where(review.movie.eq(movieImage.movie)),
                JPAExpressions.select(review.grade.avg().round()).from(review)
                        .where(review.movie.eq(movieImage.movie)))
                .where(movieImage.inum
                        .in(JPAExpressions.select(movieImage.inum.min()).from(movieImage).groupBy(movieImage.movie)));
        // .orderBy(movie.mno.desc());

        // 검색
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(movie.mno.gt(0L));

        // 검색 조건
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")) {
            // OR content LIKE '%content%'
            conditionBuilder.or(movie.title.contains(keyword));
        }
        builder.and(conditionBuilder);
        tuple.where(builder);

        // 페이지 나누기
        // sort 지정 - sort 기능이 바뀌거나 할 때를 대비하기 위한 정렬 방법(유연성)
        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            // com.querydsl.types
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder<Movie> orderByExpression = new PathBuilder<>(Movie.class, "movie");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        // 페이지 처리
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        // fetch() 실행 결과 가져오기
        // 실행결과 List 튜플(결과가 담긴 레코드)로 담아오기
        List<Tuple> result = tuple.fetch();
        long count = tuple.fetchCount();

        return new PageImpl<>(result.stream().map(t -> t.toArray()).collect(Collectors.toList()), pageable, count);
    }

    // 특정 영화 조회 (이미지 전체 조회)
    @Override
    public List<Object[]> getMovieRow(Long mno) {
        // Q 클래스 가져오기
        QMovie movie = QMovie.movie;
        QReview review = QReview.review;
        QMovieImage movieImage = QMovieImage.movieImage;

        // 기준이 되는 클래스 from
        JPQLQuery<MovieImage> query = from(movieImage);
        query.leftJoin(movie).on(movieImage.movie.eq(movie));

        // 서브 쿼리는 from 절 사용을 못함
        // subquery select 구문 → JPAExpressions
        JPQLQuery<Tuple> tuple = query.select(movie, movieImage,
                JPAExpressions.select(review.countDistinct()).from(review).where(review.movie.eq(movieImage.movie)),
                JPAExpressions.select(review.grade.avg().round()).from(review)
                        .where(review.movie.eq(movieImage.movie)))
                .where(movieImage.movie.mno.eq(mno))
                .orderBy(movieImage.inum.desc());

        List<Tuple> result = tuple.fetch();
        return result.stream().map(t -> t.toArray()).collect(Collectors.toList());
    }

}