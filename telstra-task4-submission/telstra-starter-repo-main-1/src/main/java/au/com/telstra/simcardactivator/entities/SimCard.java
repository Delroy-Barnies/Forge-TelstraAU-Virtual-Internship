package au.com.telstra.simcardactivator.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SimCard {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String iccid;
	private String customerEmail;
	private boolean active;

	public SimCard() {
	}

	public SimCard(String iccid, String customerEmail, boolean active) {
		this.iccid = iccid;
		this.customerEmail = customerEmail;
		this.active = active;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "SimCard [id=" + id + ", iccid=" + iccid + ", customerEmail=" + customerEmail + ", active=" + active
				+ "]";
	}

}
