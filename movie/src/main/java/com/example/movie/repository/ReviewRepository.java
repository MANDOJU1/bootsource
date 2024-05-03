package com.example.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // DELETE FROM REVIEW r WHERE movie_mno=1; → 메소드 생성 필요
    // delete(), deleteById() → Review 의 review_no 기준
    @Modifying
    @Query("delete from Review r where r.movie = :movie")
    void deleteByMovie(Movie movie);

    // movie 번호를 이용해 리뷰 가져오기
    // 이 메소드 실행 시 join 구문으로 처리해줘
    // join 구문으로 처리 한 이유 : 빠르게 응답하기 위해
    @EntityGraph(attributePaths = { "member" }, type = EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);

    // 멤버를 기준으로 삭제
    // r.member : ReviewEntity에서 Member을 member로 받았기 때문임
    // @Query("delete from Review r where r.member =:member", nativeQuery=true)
    // 대소문자 중요
    @Modifying
    @Query("delete from Review r where r.member =:member")
    void deleteByMember(Member member); // review_no 를 기준으로 동작함(리뷰 작성이 많은 사람일 수록 delete 구문 여러번 실행됨)

}