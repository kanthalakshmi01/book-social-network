package com.kl.book.feedback;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.*;
import lombok.NonNull;

public record FeedbackRequest(

        @Positive(message = "200")
        @Min(value = 0, message = "201")
        @Max(value = 5, message = "202")
        Double note,
        @NotNull(message = "203")
        @NotEmpty(message = "203")
        @NotBlank(message = "203")
        String comment,
        @NotNull(message = "204")
        Integer bookId
) {
       /* @Override
        public @Positive(message = "200") @Min(value = 0, message = "201") @Max(value = 5, message = "202") Double note() {
                return note;
        }

        @Override
        public @NotNull(message = "203") @NotEmpty(message = "203") @NotBlank(message = "203") String comment() {
                return comment;
        }

        @Override
        public @NotNull(message = "204") Integer bookId() {
                return bookId;
        }*/
}
