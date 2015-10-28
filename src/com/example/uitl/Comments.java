package com.example.uitl;

public class Comments {
	private int id;
	private String agentid;
	private int veryGood;
	private int good;
	private int general;
	private int bad;
	public Comments() {
	}
	
	public Comments(int id ,String agentid, int veryGood, int good,
			int general, int bad) {
		super();
		this.id = id;
		this.agentid = agentid;
		this.veryGood = veryGood;
		this.good = good;
		this.general = general;
		this.bad = bad;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAgentid() {
		return agentid;
	}
	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}
	public int getVeryGood() {
		return veryGood;
	}
	public void setVeryGood(int veryGood) {
		this.veryGood = veryGood;
	}
	public int getGood() {
		return good;
	}
	public void setGood(int good) {
		this.good = good;
	}
	public int getGeneral() {
		return general;
	}
	public void setGeneral(int general) {
		this.general = general;
	}
	public int getBad() {
		return bad;
	}
	public void setBad(int bad) {
		this.bad = bad;
	}

	@Override
	public String toString() {
		return "Comments [id=" + id + ", agentid=" + agentid + ", veryGood="
				+ veryGood + ", good=" + good + ", general=" + general
				+ ", bad=" + bad + "]";
	}
}
