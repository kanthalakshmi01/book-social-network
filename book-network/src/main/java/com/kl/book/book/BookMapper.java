package com.kl.book.book;

import com.kl.book.file.FileUtils;
import com.kl.book.history.BookTransactionHistory;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {


    public Book toBook(BookRequest request) {
        Book book = new Book();
        book.setId(request.id());
        book.setTitle(request.title());
        book.setTitle(request.title());
        book.setAuthorName(request.authorName());
        book.setSynopsis(request.synopsis());
        book.setArchived(false);
        book.setShareable(request.shareable());
        return book;
    }

    public BookResponse toBookResponse(Book book) {

        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthorName(book.getAuthorName());
        response.setIsbn(book.getIsbn());
        response.setSynopsis(book.getSynopsis());
        response.setRate(book.getRate());
        response.setArchived(book.isArchived());
        response.setShareable(book.isShareable());
        response.setOwner(book.getOwner().getFullName());
        //implement later response.setCover();
        response.setCover(FileUtils.readFileFromLocation(book.getBookCover()));
       // System.out.println(response.getCover());
        return response;
    }

    public static BorrowedBookResponse toBorrowedBookResponse(BookTransactionHistory history) {
        BorrowedBookResponse response = new BorrowedBookResponse();

        response.setId(history.getBook().getId());
        response.setTitle(history.getBook().getTitle());
        response.setAuthorName(history.getBook().getAuthorName());
        response.setIsbn(history.getBook().getIsbn());
        response.setRate(history.getBook().getRate());
        response.setReturned(history.isReturned());
        response.setReturnApproved(history.isReturnApproved());
        return response;
    }
}
