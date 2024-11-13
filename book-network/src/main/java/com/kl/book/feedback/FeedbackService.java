package com.kl.book.feedback;

import com.kl.book.book.Book;
import com.kl.book.book.BookRepository;
import com.kl.book.book.PageResponse;
import com.kl.book.book.exception.OperationNotPermittedException;
import com.kl.book.user.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;

//@RequiredArgsConstructor
@Service
public class FeedbackService {
    private final BookRepository bookRepository;
    private final FeedbackMapper feedbackMapper;
    private final FeedbackRepository feedbackRepository;

    public FeedbackService(BookRepository bookRepository, FeedbackMapper feedbackMapper, FeedbackRepository feedbackRepository) {
        this.bookRepository = bookRepository;
        this.feedbackMapper = feedbackMapper;
        this.feedbackRepository = feedbackRepository;
    }

    public Integer save(FeedbackRequest request, Authentication connectedUser) {
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new EntityNotFoundException("No Book found with this id " + request.bookId()));

        if (book.isArchived() || !book.isShareable()) {
            //throw and exception
            throw new OperationNotPermittedException("You cannot give a feedback for an archived or not shareable book");
        }
        User user = (User) connectedUser.getPrincipal();
        if (Objects.equals(book.getCreatedBy(),user.getId())){
            //throw and exception
            throw new OperationNotPermittedException("You cannot give feedback to your own book");
        }
        Feedback feedback= feedbackMapper.toFeedback(request);

        return feedbackRepository.save(feedback).getId();

    }

    public PageResponse<FeedbackResponse> findAllFeedbackByBook(Integer bookId,
                                                                int page,
                                                                int size,
                                                                Authentication connectedUser) {

        Pageable pageable= PageRequest.of(page,size);
        User user= (User) connectedUser.getPrincipal();
        Page<Feedback> feedbacks=feedbackRepository.findAllByBookId(bookId,pageable);
        List<FeedbackResponse> feedbackResponses=feedbacks.stream()
                .map(f->feedbackMapper.toFeedbackResponse(f,user.getId()))
                .toList();

        return new PageResponse<>(
                feedbackResponses,
                feedbacks.getNumber(),
                feedbacks.getSize(),
                feedbacks.getTotalElements(),
                feedbacks.getTotalPages(),
                feedbacks.isFirst(),
                feedbacks.isLast()

        );
    }
}
