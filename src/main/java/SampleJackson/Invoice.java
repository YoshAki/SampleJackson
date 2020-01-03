package SampleJackson;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Invoice {
	@JsonProperty("time")
	private String createDate;

	@JsonProperty("DomainName")
	private String domainName;

	@JsonProperty("EgressTotal")
    private int egressTotal;

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public int getEgressTotal() {
		return egressTotal;
	}

	public void setEgressTotal(int egressTotal) {
		this.egressTotal = egressTotal;
	}

}
