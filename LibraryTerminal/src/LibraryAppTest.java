import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class LibraryAppTest {

	@Test
	void bookTitleTest() {
		ArrayList<String> author = new ArrayList<String>();
		author.add("Jimmy jazz");
		Book book = new Book("Book1", 100, Status.ONSHELF,author);
		String bookTitle = book.getTitle();
		assertEquals("Book1", bookTitle);
	}
	
	@Test
	void bookStatusTest() {
		ArrayList<String> author = new ArrayList<String>();
		author.add("Jimmy john");
		Book book = new Book("Book1", 100, Status.INCART,author);
		Status bookStatus = book.getMediaStatus();
		assertEquals(Status.INCART, bookStatus);
	}
	
	@Test
	void bookConditionTest() {
		ArrayList<String> author = new ArrayList<String>();
		author.add("Jimmy john");
		Book book = new Book("Book1", 100, Status.ONSHELF,author);
		int bookCondition = book.getCondition();
		assertEquals("Book1", bookCondition);
	}
	
	@Test
	void getBookReturnsBook() {
		MediaDatabase db = new MediaDatabase();
		Media actualMedia = db.getBook(1);
		assertEquals(Book.class, actualMedia.getClass());
	}
	
	@Test
	void getMovieReturnsMovie() {
		MediaDatabase db = new MediaDatabase();
		Media actualMedia = db.getMovie(1);
		assertEquals(Movie.class, actualMedia.getClass());
	}
	
	@Test
	void getMovieTitleReturnsTitle() {
		MediaDatabase db = new MediaDatabase();
		Media actualMedia = db.getMovie(1);
		assertEquals("The Shawshank Redemption", actualMedia.getTitle());
	}
	
	@Test
	void getBookListsWorks() {
		MediaDatabase db = new MediaDatabase();
		ArrayList<Book> testBookArray = db.getBookList();
		assertTrue(testBookArray.size() > 0);
	}
	
	@Test
	void getMovieListsWorks() {
		MediaDatabase db = new MediaDatabase();
		ArrayList<Movie> testBookArray = db.getMovieList();
		assertTrue(testBookArray.size() > 0);
	}
	
	@Test
	void dateAdds14Days() {
		Book book = new Book("Blah", 100, Status.ONSHELF, new ArrayList<String>(Arrays.asList("Mike")));
		System.out.println(book.getDueDate());
		book.setDueDate(LocalDate.now().plusDays(14));
		assertEquals (LocalDate.now().plusDays(14), book.getDueDate());
	}

}
