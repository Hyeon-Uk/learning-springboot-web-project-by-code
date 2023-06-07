package com.example.mreview.service;

import com.example.mreview.dto.MovieDTO;
import com.example.mreview.dto.MovieImageDTO;
import com.example.mreview.dto.PageRequestDTO;
import com.example.mreview.dto.PageResultDTO;
import com.example.mreview.entity.Movie;
import com.example.mreview.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {
    Long register(MovieDTO dto);

    PageResultDTO<MovieDTO,Object[]> getList(PageRequestDTO requestDTO);

    MovieDTO getMovie(Long mno);

    default Map<String,Object> dtoToEntity(MovieDTO dto){
        Map<String,Object> entityMap = new HashMap<>();

        Movie movie = Movie.builder()
                .mno(dto.getMno())
                .title(dto.getTitle())
                .build();

        entityMap.put("movie",movie);

        List<MovieImageDTO> imageDTOList = dto.getImageDTOList();

        if(imageDTOList!=null&&!imageDTOList.isEmpty()){
            List<MovieImage> movieImageList = imageDTOList
                    .stream()
                    .map(movieImageDTO -> {
                        MovieImage movieImage = MovieImage.builder()
                                .path(movieImageDTO.getPath())
                                .imgName(movieImageDTO.getImgName())
                                .uuid(movieImageDTO.getUuid())
                                .movie(movie)
                                .build();

                        return movieImage;
                    }).collect(Collectors.toList());

            entityMap.put("imgList",movieImageList);
        }

        return entityMap;
    }

    default MovieDTO entitiesToDTO(Movie movie,List<MovieImage> movieImages,Double avg,Long reviewCnt){
        MovieDTO movieDTO = MovieDTO.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .regDate(movie.getRegDate())
                .modDate(movie.getModDate())
                .build();

        List<MovieImageDTO> movieImageDTOList = movieImages.stream()
                .map(movieImage->{
                    return MovieImageDTO.builder()
                            .imgName(movieImage.getImgName())
                            .path(movieImage.getPath())
                            .uuid(movieImage.getUuid())
                            .build();
                }).collect(Collectors.toList());

        movieDTO.setImageDTOList(movieImageDTOList);
        movieDTO.setAvg(avg);
        movieDTO.setReviewCnt(reviewCnt.intValue());

        return movieDTO;
    }
}
