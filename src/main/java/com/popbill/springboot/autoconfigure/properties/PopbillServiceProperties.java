package com.popbill.springboot.autoconfigure.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "popbill")
public class PopbillServiceProperties {
    private String LinkID = null;
    private String SecretKey = null;
    private Boolean isTest = true;
    private Boolean useStaticIP = false;
    private Boolean useGAIP = false;
    private Boolean useLocalTimeYN = true;
    private Boolean isIPRestrictOnOff = true;

    private String AuthURL = null;
    private String ServiceURL = null;
    private String TestServiceURL = null;

    private String ProxyIP = null;
    private Integer ProxyPort =null;
    private Map<String, String> customHeader = null;
    private String mleKeyID;
    private String mleKeyName;
    private String mlePublicKey;

    public Map<String, String> getCustomHeader() {
    	return this.customHeader;
    }
    public void setCustomHeader(Map<String, String> customHeader) {
    	this.customHeader = customHeader;
    }
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

    public Boolean isUseStaticIp() {
        return this.useStaticIP;
    }
    public void setUseStaticIp(Boolean useStaticIP) {
        this.useStaticIP = useStaticIP;
    }

    public Boolean isUseGaIp() {
        return useGAIP;
    }
    public void setUseGaIp(Boolean useGAIP) {
        this.useGAIP = useGAIP;
    }

    public Boolean isUseLocalTimeYn() {
        return useLocalTimeYN;
    }
    public void setUseLocalTimeYn(Boolean useLocalTimeYN) {
        this.useLocalTimeYN = useLocalTimeYN;
    }

    public Boolean getIsIpRestrictOnOff() {
        return isIPRestrictOnOff;
    }
    public void setIsIpRestrictOnOff(Boolean isIPRestrictOnOff) {
        this.isIPRestrictOnOff = isIPRestrictOnOff;
    }

    public String getAuthUrl() {
        return this.AuthURL;
    }
    public void setAuthUrl(String authURL) {
        this.AuthURL = authURL;
    }

    public String getServiceUrl() {
        return this.ServiceURL;
    }
    public void setServiceUrl(String serviceURL) {
        this.ServiceURL = serviceURL;
    }

    public String getTestServiceUrl() {
        return this.TestServiceURL;
    }
    public void setTestServiceUrl(String testServiceURL) {
        this.TestServiceURL = testServiceURL;
    }

    public String getProxyIp() {
        return this.ProxyIP;
    }
    public void setProxyIp(String proxyIP) {
        this.ProxyIP = proxyIP;
    }

    public Integer getProxyPort() {
        return this.ProxyPort;
    }
    public void setProxyPort(Integer proxyPort) {
        this.ProxyPort = proxyPort;
    }

    public void setMleKeyID(String mleKeyID) {
  		this.mleKeyID = mleKeyID;
  	}

  	public void setMleKeyName(String mleKeyName) {
  		this.mleKeyName = mleKeyName;
  	}

  	public void setMlePublicKey(String mlePublicKey) {
  		this.mlePublicKey = mlePublicKey;
  	}

  	public String getMleKeyID() {
  		return mleKeyID;
  	}

  	public String getMleKeyName() {
  		return mleKeyName;
  	}

  	public String getMlePublicKey() {
  		return mlePublicKey;
  	}



}
