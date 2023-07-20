package au.com.telstra.simcardactivator.status;

public class ActivationRequest {

	private String iccid;
	private String email;

	public ActivationRequest(String iccid, String email) {
		this.iccid = iccid;
		this.email = email;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
