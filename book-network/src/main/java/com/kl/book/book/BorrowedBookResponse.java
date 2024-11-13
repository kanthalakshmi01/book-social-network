package com.kl.book.book;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
@Builder
public class BorrowedBookResponse {
    private Integer id;
    private String title;
    private String authorName;
    private String isbn;

    public BorrowedBookResponse() {
    }

    public BorrowedBookResponse(String authorName, double rate, boolean returned, boolean returnApproved, String isbn, String title, Integer id) {
        this.authorName = authorName;
        this.rate = rate;
        this.returned = returned;
        this.returnApproved = returnApproved;
        this.isbn = isbn;
        this.title = title;
        this.id = id;
    }

    private double rate;
    private boolean returned;
    private boolean returnApproved;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public boolean isReturnApproved() {
        return returnApproved;
    }

    public void setReturnApproved(boolean returnApproved) {
        this.returnApproved = returnApproved;
    }
}
