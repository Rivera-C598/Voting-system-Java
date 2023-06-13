package tableModels;

public class GetVotedCandidates {
	
	private byte[] img;
	
	private String name;
	private String pList;
	private String course;
	private String yrSection;
	private String position;
	
	public GetVotedCandidates() {
		// TODO Auto-generated constructor stub
	}
	public GetVotedCandidates(byte[] img, String name, String course, String yrSec, String position, String partyList) {
		this.img = img;
		this.name = name;
		this.course = course;
		this.yrSection = yrSec;
		this.position = position;
		this.pList = partyList;
	}
	public byte[] getImg() {
		return img;
	}
	public void setImg(byte[] img) {
		this.img = img;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getpList() {
		return pList;
	}
	public void setpList(String pList) {
		this.pList = pList;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getYrSection() {
		return yrSection;
	}
	public void setYrSection(String yrSection) {
		this.yrSection = yrSection;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}

}
