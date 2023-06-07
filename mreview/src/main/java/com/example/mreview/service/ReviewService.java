package com.example.mreview.service;

import com.example.mreview.dto.ReviewDTO;
import com.example.mreview.entity.Member;
import com.example.mreview.entity.Movie;
import com.example.mreview.entity.Review;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getListOfMovie(Long mno);

    Long register(ReviewDTO dto);

    void modify(ReviewDTO dto);

    void remove(Long reviewnum);

    default Review dtoToEntity(ReviewDTO dto){
        Review review = Review.builder()
                .reviewnum(dto.getReviewnum())
                .movie(Movie.builder()
                        .mno(dto.getMno())
                        .build()
                )
                .member(Member.builder()
                        .mid(dto.getMid())
                        .build()
                )
                .grade(dto.getGrade())
                .text(dto.getText())
                .build();

        return review;
    }

    default ReviewDTO entityToDto(Review review){
        ReviewDTO dto = ReviewDTO.builder()
                .reviewnum(review.getReviewnum())
                .mno(review.getMovie().getMno())
                .mid(review.getMember().getMid())
                .nickname(review.getMember().getNickname())
                .email(review.getMember().getEmail())
                .grade(review.getGrade())
                .text(review.getText())
                .regDate(review.getRegDate())
                .modDate(review.getModDate())
                .build();

        return dto;
    }
}
