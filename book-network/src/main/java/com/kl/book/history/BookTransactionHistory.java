package com.kl.book.history;

import com.kl.book.book.Book;
import com.kl.book.common.BaseEntity;
import com.kl.book.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
public class BookTransactionHistory extends BaseEntity {

    //user relationship
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
   // @Column(name = "user_id")
    private User user;
    //book relationship
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "book_id")
    private Book book;

    private boolean returned;
    private boolean returnApproved;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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
