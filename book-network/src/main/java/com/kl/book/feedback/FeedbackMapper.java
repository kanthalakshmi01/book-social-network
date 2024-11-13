package com.kl.book.feedback;

import com.kl.book.book.Book;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
public class FeedbackMapper {


    public Feedback toFeedback(FeedbackRequest request) {

        Feedback feedback = new Feedback();
        Book book = new Book();
        book.setId(request.bookId());
        book.setArchived(false);//not required or has no impact
        book.setShareable(false);//not required or has no impact
        feedback.setNote(request.note());
        feedback.setComment(request.comment());
        feedback.setBook(book);

        return feedback;
    }

    public FeedbackResponse toFeedbackResponse(Feedback feedback, Integer id) {
        FeedbackResponse response = new FeedbackResponse();
        response.setNote(feedback.getNote());
        response.setComment(feedback.getComment());
        response.setOwnFeedback(Objects.equals(feedback.getCreatedBy(), id));
        return response;
    }
}
