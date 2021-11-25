package kr.co.linkhub.autoconfigure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "popbill.httaxinvoice-service")
public class HTTaxinvoiceServiceProperties {
    private String LinkID = null;
    private String SecretKey = null;
    private Boolean isTest = null;
    
    public String getLinkId() {
        return this.LinkID;
    }
    public void setLinkId(String linkID) {
        this.LinkID = linkID;
    }

    public String getSecretKey() {
        return this.SecretKey;
    }
    public void setSecretKey(String secretKey) {
        this.SecretKey = secretKey;
    }

    public Boolean getIsTest() {
        return this.isTest;
    }
    public void setIsTest(Boolean isTest) {
        this.isTest = isTest;
    }
}
