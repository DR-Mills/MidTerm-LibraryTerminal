import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class Tests {

	@Test
	void dateAdds14Days() {
		Book book = new Book("Blah", 100, Status.ONSHELF, new ArrayList<String>(Arrays.asList("Mike")));
		System.out.println(book.getDueDate());
		book.setDueDate(LocalDate.now().plusDays(14));
		assertEquals (LocalDate.now().plusDays(14), book.getDueDate());
	}

}
