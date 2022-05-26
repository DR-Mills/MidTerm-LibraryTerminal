
	abstract class Media {

	String title;

	enum status { CHECKEDOUT, ONSHELF, LOST };
	int condition;
	// Add date object. Calendar.
	
	
	public abstract void consume();
	// read for book, watch for movie, listen for audiobooks


	public Media(String title, int condition) {
		this.title = title;
		this.condition = condition;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public int getCondition() {
		return condition;
	}


	public void setCondition(int condition) {
		this.condition = condition;
	}
}
