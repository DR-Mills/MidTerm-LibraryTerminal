import java.time.LocalDate;


abstract class Media {

	private String title;
	private Status mediaStatus = Status.ONSHELF;
	private int condition;
	private LocalDate dueDate;

	
	public Media(String title, int condition, Status mediaStatus) {
		this.title = title;
		this.condition = condition;
		this.mediaStatus = mediaStatus;
	}

	
	public LocalDate getDueDate() {
		return dueDate;
	}


	public void setDueDate(LocalDate date) {
		this.dueDate = date;
	}


	public Status getMediaStatus() {
		return mediaStatus;
	}


	public void setMediaStatus(Status mediaStatus) {
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
	
	
	@Override
	public String toString() {
		return "Media [title=" + title + ", mediaStatus=" + mediaStatus + ", condition=" + condition + "]";
	}


	protected abstract Object getAuthor();


	protected abstract Object getDirector();


	
	
}
