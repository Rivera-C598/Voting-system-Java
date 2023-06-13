package tableModels;


public class GetElectionStatistics {
	
	private byte[] candImg;
	
	private String candName;
	private String candPartyList;
	private int votes;
	
	public GetElectionStatistics() {
		
	}
	public GetElectionStatistics(byte[] img, String name, String partyList, int votes) {
		
		this.candImg = img;
		this.candName = name;
		this.candPartyList = partyList;
		this.votes = votes;
		
		
	}
	public byte[] getCandImg() {
		return candImg;
	}
	public void setCandImg(byte[] candImg) {
		this.candImg = candImg;
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
	public int getVotes() {
		return votes;
	}
	public void setVotes(int votes) {
		this.votes = votes;
	}

}
