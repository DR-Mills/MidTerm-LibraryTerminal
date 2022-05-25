
public interface Media {

	String title = "";
	String status = "";		// Checked-Out, On-Shelf, Lost (not reordered), Damaged
	
	public void consume();
	// read for book, watch for movie, listen for audiobooks
}
