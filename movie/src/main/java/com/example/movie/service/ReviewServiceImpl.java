package com.example.movie.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.movie.dto.ReviewDto;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;
import com.example.movie.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewDto> getListOfMovie(Long mno) {
        Movie movie = Movie.builder().mno(mno).build();
        List<Review> reviews = reviewRepository.findByMovie(movie);

        // List<Review> → List<ReviewDto>
        Function<Review, ReviewDto> fn = review -> entityToDto(review);
        return reviews.stream().map(fn).collect(Collectors.toList());

    }

    @Override
    public Long addReview(ReviewDto reviewDto) {

        Review review = DtoToentity(reviewDto);
        return reviewRepository.save(review).getReviewNo();

    }

    @Override
    public void removeReview(Long reviewNo) {
        reviewRepository.deleteById(reviewNo);
    }

    @Override
    public ReviewDto getReview(Long reviewNo) {
        return entityToDto(reviewRepository.findById(reviewNo).get());
    }

    @Override
    public Long updateReview(ReviewDto reviewDto) {
        // save() => 호출하면
        // 시작되는 순서 1.select 2.insert or update

        // 1번방법
        // return reviewRepository.save(DtoToentity(reviewDto)).getReviewNo();

        // 2번방법
        Optional<Review> result = reviewRepository.findById(reviewDto.getReviewNo());

        if (result.isPresent()) {
            Review review = result.get();
            review.setText(reviewDto.getText());
            review.setGrade(reviewDto.getGrade());
            reviewRepository.save(DtoToentity(reviewDto));
        }
        return reviewDto.getReviewNo();
    }

}
