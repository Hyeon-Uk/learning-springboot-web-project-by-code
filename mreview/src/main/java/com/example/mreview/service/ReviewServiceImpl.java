package com.example.mreview.service;

import com.example.mreview.dto.ReviewDTO;
import com.example.mreview.entity.Movie;
import com.example.mreview.entity.Review;
import com.example.mreview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewDTO> getListOfMovie(Long mno) {
        Movie movie = Movie.builder()
                .mno(mno)
                .build();

        List<Review> result = reviewRepository.findByMovie(movie);

        return result.stream().map(review->entityToDto(review)).collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewDTO dto) {
        Review review = dtoToEntity(dto);

        return reviewRepository.save(review).getReviewnum();
    }

    @Override
    public void modify(ReviewDTO dto) {
        Optional<Review> result =
                reviewRepository.findById(dto.getReviewnum());

        if(result.isPresent()){
            Review review = result.get();
            review.changeGrade(dto.getGrade());
            review.changeText(dto.getText());

            reviewRepository.save(review);
        }
    }

    @Override
    public void remove(Long reviewnum) {
        reviewRepository.deleteById(reviewnum);
    }
}
