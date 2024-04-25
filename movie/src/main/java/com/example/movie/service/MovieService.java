package com.example.movie.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.example.movie.dto.MovieDto;
import com.example.movie.dto.MovieImageDto;
import com.example.movie.dto.PageRequestDto;
import com.example.movie.dto.PageResultDto;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;

public interface MovieService {

    PageResultDto<MovieDto, Object[]> getList(PageRequestDto pageRequestDto);

    // [Movie(mno=99, title=Movie99), MovieImage(inum=296,
    // uuid=98fc2592-6d21-4015-a9fe-65a21ec35236, imgName=img0.jpg, path=null), 1,
    // 3.0]
    public default MovieDto entityToDto(Movie movie, List<MovieImage> movieImages, Long reviewCnt, Double avg) {

        MovieDto movieDto = MovieDto.builder()
                .mno(movie.getMno())
                .createdDate(movie.getCreatedDate())
                .lastModifiedDate(movie.getLastModifiedDate())
                .title(movie.getTitle())
                .avg(avg)
                .reviewCnt(reviewCnt)
                .build();
        // 영화 상세 조회 => 이미지를 모두 보여주기
        List<MovieImageDto> movieImageDtos = movieImages.stream().map(movieImage -> {
            return MovieImageDto.builder()
                    .inum(movieImage.getInum())
                    .uuid(movieImage.getUuid())
                    .imgName(movieImage.getImgName())
                    .path(movieImage.getImgName())
                    .build();
        }).collect(Collectors.toList());

        movieDto.setMovieImageDtos(movieImageDtos);
        return movieDto;
    }

    // dto => entity
    public default Map<String, Object> dtoToEntity(MovieDto dto) {
        return null;
    }
}
