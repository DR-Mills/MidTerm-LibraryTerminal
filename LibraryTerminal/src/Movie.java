
public class Movie extends Media{
	String director;
	int runTimeMin;
	public String getDirector() {
		return director;
	}
	
	public void setDirector(String director) {
		this.director = director;
	}
	
	public int getRunTimeMin() {
		return runTimeMin;
	}
	
	public void setRunTimeMin(int runTimeMin) {
		this.runTimeMin = runTimeMin;
	}

	public Movie(String title, int condition, Status mediaStatus, String director, int runTimeMin) {
		super(title, condition, mediaStatus);
		this.director = director;
		this.runTimeMin = runTimeMin;
	}

	@Override
	protected String getAuthor() {
		// returns director
		return director;
	}
}
