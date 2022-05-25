
	abstract class Media {

	String title = "";
	String status = "";		// Checked-Out, On-Shelf, Lost (not reordered), Damaged
	public enum condition { CHECKEDOUT, ONSHELF, LOST };
	
	public abstract void consume();
	// read for book, watch for movie, listen for audiobooks
}
