package tableModels;

public class GetPartialCandidates {
	
	private int candId;
	private byte[] candImage;
	private String candName;
	private String candCourse;
	private String candYearAndSection;
	private String candPosition;
	private String candPartyList;
	private String currentUserId;
	
	public GetPartialCandidates() {
	}
	
	public GetPartialCandidates(int candId, byte[] candImage, String candName, String candCourse, String candYearAndSection, String candPosition, String candPartyList, String currentUserId)
	{
		this.candId = candId;
		this.candImage = candImage;
		this.candName = candName;
		this.candCourse = candCourse;
		this.candYearAndSection = candYearAndSection;
		this.candPosition = candPosition;
		this.candPartyList = candPartyList;
		this.currentUserId = currentUserId;
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
	
	public String getCandPosition() {
		return candPosition;
	}

	public void setCandPosition(String candPosition) {
		this.candPosition = candPosition;
	}
	public String getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}
}
