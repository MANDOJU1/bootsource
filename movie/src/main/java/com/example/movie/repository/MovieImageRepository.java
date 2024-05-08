package com.example.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.repository.total.MovieImageReviewRepository;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long>, MovieImageReviewRepository {
    // DELETE FROM MOVIE_IMAGE mi WHERE movie_mno=1; → 메소드 생성 필요
    // delete(), deleteById() → MovieImage 의 inum 기준
    @Modifying
    @Query("delete from MovieImage mi where mi.movie = :movie")
    void deleteByMovie(Movie movie);

    @Query(value = "SELECT * FROM MOVIE_IMAGE mi WHERE mi.PATH = TO_CHAR(SYSDATE - 1, 'yyyy\\mm\\dd')", nativeQuery = true)
    List<MovieImage> getOldMovieImages();
}