import java.util.ArrayList;
import java.util.Arrays;

public class MediaDatabase {

	public ArrayList<Book> getBookList() {

		ArrayList<Book> bookArrayList = new ArrayList<>();

		for (int i = 1; i <= 11; i++) {
			bookArrayList.add(getBook(i));
		}
		return bookArrayList;
	}

	
	public ArrayList<Movie> getMovieList() {

		ArrayList<Movie> movieArrayList = new ArrayList<>();
		for (int i = 1; i <= 7; i++) {
			movieArrayList.add(getMovie(i));
		}
		return movieArrayList;
	}

	
	public Book getBook(int i) {

		int condition = 100;
		Status MediaStatus = Status.ONSHELF;

		switch (i) {
		case 1:
			String title001 = "Harry Potter and the Half-Blood Prince";
			ArrayList<String> creator001 = new ArrayList<String>(Arrays.asList("J.K. Rowling"));
			return new Book(title001, condition, MediaStatus, creator001);

		case 2:
			String title002 = "The Outsider";
			ArrayList<String> creator002 = new ArrayList<String>(Arrays.asList("Stephen King"));
			return new Book(title002, condition, MediaStatus, creator002);

		case 3:
			String title003 = "Pet Sematary";
			ArrayList<String> creator003 = new ArrayList<String>(Arrays.asList("Stephen King"));
			return new Book(title003, condition, MediaStatus, creator003);

		case 4:
			String title004 = "Harry Potter and the Deathly Hallows";
			ArrayList<String> creator004 = new ArrayList<String>(Arrays.asList("J.K. Rowling"));
			return new Book(title004, condition, MediaStatus, creator004);

		case 5:
			String title005 = "Harry Potter and the Order of the Phoenix";
			ArrayList<String> creator005 = new ArrayList<String>(Arrays.asList("J.K. Rowling"));
			return new Book(title005, condition, MediaStatus, creator005);

		case 6:
			String title006 = "It";
			ArrayList<String> creator006 = new ArrayList<String>(Arrays.asList("Stephen King"));
			return new Book(title006, condition, MediaStatus, creator006);

		case 7:
			String title007 = "Where the Crawdads Sing";
			ArrayList<String> creator007 = new ArrayList<String>(Arrays.asList("Delia Owens"));
			return new Book(title007, condition, MediaStatus, creator007);

		case 8:
			String title008 = "Oh, the Places You'll Go";
			ArrayList<String> creator008 = new ArrayList<String>(Arrays.asList("Dr. Seuss"));
			return new Book(title008, condition, MediaStatus, creator008);

		case 9:
			String title009 = "Zen and the Art of Motorcycle Maintenance";
			ArrayList<String> creator009 = new ArrayList<String>(Arrays.asList("Robert Pirsig"));
			return new Book(title009, condition, MediaStatus, creator009);

		case 10:
			String title010 = "Rich Dad Poor Dad";
			ArrayList<String> creator010 = new ArrayList<String>(Arrays.asList("Robert T. Kiyosaki"));
			return new Book(title010, condition, MediaStatus, creator010);

		case 11:
			String title011 = "Head First Java, 2nd Edition";
			ArrayList<String> creator011 = new ArrayList<String>(Arrays.asList("Kathy Sierra", "Bert Bates"));
			return new Book(title011, condition, MediaStatus, creator011);
		}
		return null;
	}

	public Movie getMovie(int i) {

		int condition = 100;
		Status MediaStatus = Status.ONSHELF;

		switch (i) {
		case 1:
			String title001 = "The Shawshank Redemption";
			String director001 = "Frank Darabont";
			int runtime001 = 144;
			return new Movie(title001, condition, MediaStatus, director001, runtime001);

		case 2:
			String title002 = "The Dark Knight";
			String director002 = "Christopher Nolan";
			int runtime002 = 152;
			return new Movie(title002, condition, MediaStatus, director002, runtime002);

		case 3:
			String title003 = "Pulp Fiction";
			String director003 = "Quentin Tarantino";
			int runtime003 = 154;
			return new Movie(title003, condition, MediaStatus, director003, runtime003);

		case 4:
			String title004 = "Inception";
			String director004 = "Christopher Nolan";
			int runtime004 = 148;
			return new Movie(title004, condition, MediaStatus, director004, runtime004);

		case 5:
			String title005 = "The Matrix";
			String director005 = "Lana & Lilly Wachowski";
			int runtime005 = 136;
			return new Movie(title005, condition, MediaStatus, director005, runtime005);

		case 6:
			String title006 = "The Silence of the Lambs";
			String director006 = "Jonathan Demme";
			int runtime006 = 118;
			return new Movie(title006, condition, MediaStatus, director006, runtime006);

		case 7:
			String title007 = "The Terminator 2";
			String director007 = "James Cameron";
			int runtime007 = 137;
			return new Movie(title007, condition, MediaStatus, director007, runtime007);
		}
		return null;
	}
}
