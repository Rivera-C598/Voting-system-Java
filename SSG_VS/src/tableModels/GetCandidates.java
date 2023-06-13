package tableModels;

public class GetCandidates {
	private int candId;
	private String candName;
	private String candPartyList;
	private String candCourse;
	private String candYearAndSection;
	private byte[] candImage;
	
	public GetCandidates() {
		
	}
	
	public GetCandidates(int candId, byte[] candImage, String candName, String candCourse, String candYearAndSection, String candPartyList) {
		this.candId = candId;
		this.candImage = candImage;
		this.candName = candName;
		this.candCourse = candCourse;
		this.candYearAndSection = candYearAndSection;
		this.candPartyList = candPartyList;
	}
	
	public int getCandId() {
		return candId;
	}

	public void setCandId(int candId) {
		this.candId = candId;
	}

	public String getCandName() {
		return candName;
	}

	public void setCandName(String candName) {
		this.candName = candName;
	}

	public String getCandPartyList() {
		return candPartyList;
	}

	public void setCandPartyList(String candPartyList) {
		this.candPartyList = candPartyList;
	}

	public String getCandCourse() {
		return candCourse;
	}

	public void setCandCourse(String candCourse) {
		this.candCourse = candCourse;
	}

	public String getCandYearAndSection() {
		return candYearAndSection;
	}

	public void setCandYearAndSection(String candYearAndSection) {
		this.candYearAndSection = candYearAndSection;
	}

	public byte[] getCandImage() {
		return candImage;
	}

	public void setCandImage(byte[] candImage) {
		this.candImage = candImage;
	}
	
	
}
