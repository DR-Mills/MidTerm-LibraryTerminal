
import java.util.ArrayList;

public class Book extends Media {
	ArrayList<String> author = new ArrayList<>();	

	public Book(String title, int condition, Status mediaStatus, ArrayList<String> author) {
		super(title, condition, mediaStatus);
		this.author = author;
	}

	public ArrayList<String> getAuthor() {
		return author;
	}

	public void setAuthor(ArrayList<String> author) {
		this.author = author;
	}

	@Override
	protected String getDirector() {
		return "";
	}

}
