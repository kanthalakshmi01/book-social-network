package com.kl.book.book;

import com.kl.book.book.exception.OperationNotPermittedException;
import com.kl.book.file.FileStorageService;
import com.kl.book.history.BookTransactionHistory;
import com.kl.book.history.BookTransactionHistoryRepository;
import com.kl.book.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.kl.book.book.BookSpecification.withOwnerId;

@Service
//@RequiredArgsConstructor
public class BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final BookTransactionHistoryRepository bookTransactionHistoryRepository;
    private final FileStorageService fileStorageService;

    public BookService(BookMapper bookMapper, BookRepository bookRepository, BookTransactionHistoryRepository bookTransactionHistoryRepository, FileStorageService fileStorageService) {
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
        this.bookTransactionHistoryRepository = bookTransactionHistoryRepository;
        this.fileStorageService = fileStorageService;
    }


    public Integer save(BookRequest request, Authentication connectedUser) {

        User user = ((User) connectedUser.getPrincipal());
        Book book = bookMapper.toBook(request);
        book.setOwner(user);

        return bookRepository.save(book).getId();

    }

    public BookResponse findBookById(Integer id) {

        return bookRepository.findById(id)
                .map(bookMapper::toBookResponse)
                .orElseThrow(() -> new EntityNotFoundException("No Book found with this id : " + id));

    }


    public PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAllDisplayableBooks(pageable, user.getId());
        List<BookResponse> bookResponse = books.stream()
                .map(bookMapper::toBookResponse)
                .collect(Collectors.toList());
       // System.out.println(bookResponse);
        return new PageResponse<BookResponse>(bookResponse, books.getNumber(), books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast());
    }

    public PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAll(withOwnerId(user.getId()), pageable);

        List<BookResponse> bookResponse = books.stream()
                .map(bookMapper::toBookResponse)
                .collect(Collectors.toList());
        return new PageResponse<BookResponse>(bookResponse, books.getNumber(), books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast());

    }

    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> allBorrowedBooks = bookTransactionHistoryRepository.findAllBorrowedBooks(pageable, user.getId());
        List<BorrowedBookResponse> bookResponse = allBorrowedBooks.stream()
                .map(BookMapper::toBorrowedBookResponse)
                .toList();
        return new PageResponse<BorrowedBookResponse>(bookResponse,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast());

    }

    public PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> allBorrowedBooks = bookTransactionHistoryRepository.findAllReturnedBooks(pageable,user.getId());
        List<BorrowedBookResponse> bookResponse = allBorrowedBooks.stream()
                .map(BookMapper::toBorrowedBookResponse)
                .toList();
        return new PageResponse<BorrowedBookResponse>(bookResponse,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast());

    }

    public Integer updateShareableStatus(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No Book found with this id " + bookId));
        User user = (User) connectedUser.getPrincipal();
        if (!Objects.equals(book.getCreatedBy(),user.getId())) {
            //throw and exception
            throw new OperationNotPermittedException("You cannot update book shareable status");
        }
        book.setShareable(!book.isShareable());
        bookRepository.save(book);
        return bookId;
    }

    public Integer updateArchivedStatus(Integer bookId, Authentication connectedUser) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No Book found with this id " + bookId));
      User user = (User) connectedUser.getPrincipal();
        if (!Objects.equals(book.getCreatedBy(), user.getId())){
            //throw and exception
            throw new OperationNotPermittedException("You cannot update book archived status");
        }
        book.setArchived(!book.isArchived());
        bookRepository.save(book);
        return bookId;
    }

    public Integer borrowBook(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No Book found with this id " + bookId));
        if (book.isArchived() || !book.isShareable()) {
            //throw and exception
            throw new OperationNotPermittedException("The Requested book cannot be borrowed since it is archived or not shareable");
        }
        User user = (User) connectedUser.getPrincipal();
        if (Objects.equals(book.getCreatedBy(), user.getId())){
            //throw and exception
            throw new OperationNotPermittedException("You cannot borrow your own book");
        }
        final boolean isAlreadyBorrowed=bookTransactionHistoryRepository.isAlreadyBorrowedByUser(bookId,user.getId());

        if (isAlreadyBorrowed){
            throw new OperationNotPermittedException("The requested book is already borrowed");
        }
        BookTransactionHistory bookTransactionHistory=new BookTransactionHistory();
        bookTransactionHistory.setUser(user);
        bookTransactionHistory.setBook(book);
        bookTransactionHistory.setReturned(false);
        bookTransactionHistory.setReturnApproved(false);

        return bookTransactionHistoryRepository.save(bookTransactionHistory).getId();
    }

    public Integer returnBorrowedBook(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No Book found with this id " + bookId));
        if (book.isArchived() || !book.isShareable()) {
            //throw and exception
            throw new OperationNotPermittedException("The Requested book cannot be borrowed since it is archived or not shareable");
        }
        User user = (User) connectedUser.getPrincipal();

        //System.out.println(book.getOwner()+"user Id:"+user.getId());
        if (Objects.equals(book.getCreatedBy(), user.getName())){
            //throw and exception
            throw new OperationNotPermittedException("You cannot borrow or return your own book");
        }
        System.out.println("user id"+user.getId());
        System.out.println("book id"+bookId);
      BookTransactionHistory bookTransactionHistory=bookTransactionHistoryRepository.findByBookIdAndUserId(bookId,user.getId())
              .orElseThrow(()->new OperationNotPermittedException("You did not borrow this book"));

bookTransactionHistory.setReturned(true);
return bookTransactionHistoryRepository.save(bookTransactionHistory).getId();
    }

    public Integer returnApproveBorrowedBook(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No Book found with this id " + bookId));
        if (book.isArchived() || !book.isShareable()) {
            //throw and exception
            throw new OperationNotPermittedException("The Requested book cannot be borrowed since it is archived or not shareable");
        }
        User user = (User) connectedUser.getPrincipal();
        if (!Objects.equals(book.getCreatedBy(), user.getId())){
            //throw and exception
            throw new OperationNotPermittedException("You cannot return a book that you do not own");
        }
        BookTransactionHistory bookTransactionHistory=bookTransactionHistoryRepository.findByBookIdAndOwnerId(bookId,user.getId())
                .orElseThrow(()->new OperationNotPermittedException("The Book is not returned yet. So, you cannot approve its return"));

        bookTransactionHistory.setReturnApproved(true);
        return bookTransactionHistoryRepository.save(bookTransactionHistory).getId();
    }

    public void uploadBookCoverPicture(MultipartFile file, Authentication connectedUser, Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No Book found with this id " + bookId));
        User user = (User) connectedUser.getPrincipal();
        var bookCover=fileStorageService.saveFile(file,user.getId());
        book.setBookCover(bookCover);
        bookRepository.save(book);


    }
}
