package com.example.movie.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.movie.dto.MovieDto;
import com.example.movie.dto.MovieImageDto;
import com.example.movie.dto.PageRequestDto;
import com.example.movie.dto.PageResultDto;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;

public interface MovieService {
    PageResultDto<MovieDto, Object[]> getList(PageRequestDto pageRequestDto);

    MovieDto getRow(Long mno);

    void movieRemove(Long mno);

    Long movieInsert(MovieDto movieDto);

    // [Movie(mno=99, title=Movie99), MovieImage(inum=296,
    // uuid=98fc2592-6d21-4015-a9fe-65a21ec35236, imgName=img0.jpg, path=null), 1,
    // 3.0]
    // entity → dto
    public default MovieDto entityToDto(Movie movie, List<MovieImage> movieImages, Long reviewCnt, Double avg) {

        MovieDto movieDto = MovieDto.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .avg(avg != null ? avg : 0.0d)
                .reviewCnt(reviewCnt)
                .createdDate(movie.getCreatedDate())
                .lastModifiedDate(movie.getLastModifiedDate())
                .build();

        // 영화 상세 조회 → 이미지를 모두 보여주기
        List<MovieImageDto> moviewImageDtos = movieImages.stream().map(movieImage -> {
            return MovieImageDto.builder()
                    .inum(movieImage.getInum())
                    .uuid(movieImage.getUuid())
                    .imgName(movieImage.getImgName())
                    .path(movieImage.getPath())
                    .build();
        }).collect(Collectors.toList());

        movieDto.setMovieImageDtos(moviewImageDtos);

        return movieDto;
    }

    // dto → entity
    public default Map<String, Object> dtoToEntity(MovieDto dto) {

        Map<String, Object> entityMap = new HashMap<>();

        // Movie Entity 생성
        Movie movie = Movie.builder()
                .mno(dto.getMno())
                .title(dto.getTitle())
                .build();

        // 생성된 movie entity 를 Map 에 담기 : put()
        entityMap.put("movie", movie);

        // List<MovieImageDto> movieImageDtos 를
        // List<Moive> 로 변환

        // 1번방법
        // List<MovieImageDto> movieImageDtos = dto.getMovieImageDtos();
        // List<MovieImage> movieImages = new ArrayList<>();
        // if (movieImageDtos != null && movieImageDtos.size() > 0) {
        // for (MovieImageDto mDto : movieImageDtos) {
        // MovieImage movieImage = MovieImage.builder()
        // .imgName(mDto.getImgName())
        // .uuid(mDto.getUuid())
        // .path(mDto.getPath())
        // .build();

        // movieImages.add(movieImage);
        // }
        // }

        // 1번방법 혹은 아래 방법
        List<MovieImageDto> movieImageDtos = dto.getMovieImageDtos();
        if (movieImageDtos != null && movieImageDtos.size() > 0) {
            List<MovieImage> movieImages = movieImageDtos.stream().map(mDto -> {
                MovieImage movieImage = MovieImage.builder()
                        .movie(movie)
                        .imgName(mDto.getImgName())
                        .uuid(mDto.getUuid())
                        .path(mDto.getPath())
                        .build();
                return movieImage;
            }).collect(Collectors.toList());

            entityMap.put("imgList", movieImages);
        }

        // 변환이 끝난 entity list 를 Map 에 담기 : put()

        return entityMap;
    }
}