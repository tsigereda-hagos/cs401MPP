package com.team2.mppproject.dataaccess;

import com.team2.mppproject.business.Author;
import com.team2.mppproject.business.Book;
import com.team2.mppproject.business.BookCopy;
import com.team2.mppproject.business.LibraryMember;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class DataAccessFacade implements DataAccess {

    @Override
    public void saveNewBook(Book book) {
        HashMap<String, Book> books = readBooksMap();
        String bookId = book.getIsbn();
        books.put(bookId, book);
        saveToStorage(StorageType.BOOKS, books);
    }

    @Override
    public void updateMember(LibraryMember member) {
        HashMap<String, LibraryMember> members = readMemberMap();
        members.put(member.getMemberId(), member);
        saveToStorage(StorageType.MEMBERS, members);
    }
    
    enum StorageType {
        BOOKS, MEMBERS, USERS, CHECKOUTRECORD, AUTHORS
    }

    public static final String OUTPUT_DIR = System.getProperty("user.dir")
            + "/src/main/java/com/team2/mppproject/dataaccess/storage"; // for Unix file system
    // + "\\src\\dataaccess\\storage"; //for Windows file system
    public static final String DATE_PATTERN = "MM/dd/yyyy";

    // implement: other save operations
    public void saveNewMember(LibraryMember member) {
        HashMap<String, LibraryMember> mems = readMemberMap();
        String memberId = member.getMemberId();
        mems.put(memberId, member);
        saveToStorage(StorageType.MEMBERS, mems);
    }

    @Override
    public void updateBookHM(HashMap<String, Book> hmBooks) {
        saveToStorage(StorageType.BOOKS, hmBooks);
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, Book> readBooksMap() {
        // Returns a Map with name/value pairs being
        // isbn -> Book
        return (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
    }
    
    @SuppressWarnings("unchecked")
    public HashMap<String, Author> readAuthorsMap() {
        // Returns a Map with name/value pairs being
        // isbn -> Book
        return (HashMap<String, Author>) readFromStorage(StorageType.AUTHORS);
    }


    @SuppressWarnings("unchecked")
    public HashMap<String, LibraryMember> readMemberMap() {
        // Returns a Map with name/value pairs being
        // memberId -> LibraryMember
        return (HashMap<String, LibraryMember>) readFromStorage(
                StorageType.MEMBERS);
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, User> readUserMap() {
        // Returns a Map with name/value pairs being
        // userId -> User
        return (HashMap<String, User>) readFromStorage(StorageType.USERS);
    }
    
    @Override
    public HashMap<String, Author> readAuthorMap() {
        return (HashMap<String, Author>) readFromStorage(StorageType.AUTHORS);
    }


    ///// load methods - these place test data into the storage area
    ///// - used just once at startup
    static void loadBookMap(List<Book> bookList) {
        HashMap<String, Book> books = new HashMap<String, Book>();
        for (Book book : bookList) {
            books.put(book.getIsbn(), book);
        }
        // bookList.forEach(book -> books.put(book.getIsbn(), book));
        saveToStorage(StorageType.BOOKS, books);
    }

    static void loadUserMap(List<User> userList) {
        HashMap<String, User> users = new HashMap<String, User>();
        userList.forEach(user -> users.put(user.getId(), user));
        saveToStorage(StorageType.USERS, users);
    }

    static void loadMemberMap(List<LibraryMember> memberList) {
        HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
        memberList.forEach(member -> members.put(member.getMemberId(), member));
        saveToStorage(StorageType.MEMBERS, members);
    }
    
    static void loadAuthors(List<Author> authorList) {
        HashMap<String, Author> authors = new HashMap<String, Author>();
        authorList.forEach(author -> authors.put(author.getTelephone(), author));
        saveToStorage(StorageType.AUTHORS, authors);
    }

    static void saveToStorage(StorageType type, Object ob) {
        ObjectOutputStream out = null;
        try {
            Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
            out = new ObjectOutputStream(Files.newOutputStream(path));
            out.writeObject(ob);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        }
    }

    static Object readFromStorage(StorageType type) {
        ObjectInputStream in = null;
        Object retVal = null;
        try {
            Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
            in = new ObjectInputStream(Files.newInputStream(path));
            retVal = in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }
        return retVal;
    }

    final static class Pair<S, T> implements Serializable {

        S first;
        T second;

        Pair(S s, T t) {
            first = s;
            second = t;
        }

        @Override
        public boolean equals(Object ob) {
            if (ob == null) {
                return false;
            }
            if (this == ob) {
                return true;
            }
            if (ob.getClass() != getClass()) {
                return false;
            }
            @SuppressWarnings("unchecked")
            Pair<S, T> p = (Pair<S, T>) ob;
            return p.first.equals(first) && p.second.equals(second);
        }

        @Override
        public int hashCode() {
            return first.hashCode() + 5 * second.hashCode();
        }

        @Override
        public String toString() {
            return "(" + first.toString() + ", " + second.toString() + ")";
        }

        private static final long serialVersionUID = 5399827794066637059L;
    }

    @Override
    public Book searchBook(String isbn) {
        HashMap<String, Book> books = this.readBooksMap();
        return books.get(isbn);
    }

    @Override
    public BookCopy addBookCopy(Book book) {
        HashMap<String, Book> books = this.readBooksMap();
        books.put(book.getIsbn(), book);
        saveToStorage(StorageType.BOOKS, books);

        return book.getCopies()[book.getCopies().length - 1];
    }

    @Override
    public BookCopy[] getBookCopies(String isbn) {
        Book book = searchBook(isbn);
        return book.getCopies();
    }
}
