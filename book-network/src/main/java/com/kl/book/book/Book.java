package com.kl.book.book;

import com.kl.book.common.BaseEntity;
import com.kl.book.feedback.Feedback;
import com.kl.book.history.BookTransactionHistory;
import com.kl.book.user.User;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
//@EntityListeners(AuditingEntityListener.class)
public class Book extends BaseEntity {

    private String title;
    private String authorName;
    private String isbn;
    private String synopsis;
    @Column(columnDefinition = "TEXT")
    private String bookCover;
    private boolean archived;
    private boolean shareable;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "book")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> histories;

    @Transient
    public double getRate() {
        if (feedbacks == null || feedbacks.isEmpty()) {
            return 0.0;
        }
        var rate = this.feedbacks.stream().mapToDouble(Feedback::getNote)
                .average().orElse(0.0);
        double roundedRate = Math.round(rate * 10.0) / 10.0;
        return roundedRate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getBookCover() {
        return bookCover;
    }

    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isShareable() {
        return shareable;
    }

    public void setShareable(boolean shareable) {
        this.shareable = shareable;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public List<BookTransactionHistory> getHistories() {
        return histories;
    }

    public void setHistories(List<BookTransactionHistory> histories) {
        this.histories = histories;
    }
}
