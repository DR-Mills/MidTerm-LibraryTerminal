
	abstract class Media {

	String title;

	enum status { CHECKEDOUT, ONSHELF, LOST, InCart};
	//enum variable for media status
	status mediaStatus = status.ONSHELF;
	public status getMediaStatus() {
		return mediaStatus;
	}


	public void setMediaStatus(status mediaStatus) {
		this.mediaStatus = mediaStatus;
	}


	int condition;
	// Add date object. Calendar.
	
	
	public abstract void consume();
	// read for book, watch for movie, listen for audiobooks


	public Media(String title, int condition, status mediaStatus) {
		this.title = title;
		this.condition = condition;
		this.mediaStatus = mediaStatus;
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
