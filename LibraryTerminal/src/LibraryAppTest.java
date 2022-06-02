import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Stack;

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

}
