package acfb.org;

public class Manga {
	private int mangaId;
	private int userId;
	private String mangaName;
	private String mangaSynopsis;
	private boolean mangaStatus;
	private String mangaCreation;
	private String genero;
	
	public Manga() {}
	
	
	//-----------------------------------------------------------GETTERS AND SETTERS
	public int getMangaId() {
		return mangaId;
	}
	public void setMangaId(int mangaId) {
		this.mangaId = mangaId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getMangaName() {
		return mangaName;
	}
	public void setMangaName(String mangaName) {
		this.mangaName = mangaName;
	}
	public String getMangaSynopsis() {
		return mangaSynopsis;
	}
	public void setMangaSynopsis(String mangaSynopsis) {
		this.mangaSynopsis = mangaSynopsis;
	}
	public boolean isMangaStatus() {
		return mangaStatus;
	}
	public void setMangaStatus(boolean mangaStatus) {
		this.mangaStatus = mangaStatus;
	}
	public String getMangaCreation() {
		return mangaCreation;
	}
	public void setMangaCreation(String mangaCreation) {
		this.mangaCreation = mangaCreation;
	}


	public String getGenero() {
		return genero;
	}


	public void setGenero(String genero) {
		this.genero = genero;
	}
	
}
