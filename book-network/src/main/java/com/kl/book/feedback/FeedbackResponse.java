package com.kl.book.feedback;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class FeedbackResponse {
    private double note;
    private String comment;
    private boolean ownFeedback;

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isOwnFeedback() {
        return ownFeedback;
    }

    public void setOwnFeedback(boolean ownFeedback) {
        this.ownFeedback = ownFeedback;
    }
}
