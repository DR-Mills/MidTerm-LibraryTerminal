import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Stack;

import org.junit.jupiter.api.Test;

class LibraryAppTest {

	@Test
	void BookTest() {
		ArrayList<String> author = new ArrayList<String>();
		author.add("Jimmy");
		Book book = new Book("Book1", 100, Status.ONSHELF,author);
		String bookTitle = book.getTitle();
		assertEquals("Book1", bookTitle);
	}

}
