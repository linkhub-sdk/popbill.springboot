package com.popbill.springboot.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.popbill.api.HTTaxinvoiceService;
import com.popbill.api.hometax.HTTaxinvoiceServiceImp;
import com.popbill.springboot.autoconfigure.properties.HTTaxinvoiceServiceProperties;
import com.popbill.springboot.autoconfigure.properties.PopbillServiceProperties;

@Configuration
@ConditionalOnClass(HTTaxinvoiceService.class)
@EnableConfigurationProperties({ HTTaxinvoiceServiceProperties.class, PopbillServiceProperties.class })
public class HTTaxinvoiceServiceAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(HTTaxinvoiceServiceAutoConfiguration.class);

    @Autowired
    private PopbillServiceProperties popbillServiceProperties;

    @Autowired
    private HTTaxinvoiceServiceProperties htTaxinvoiceServiceProperties;

    @Lazy
    @Bean(name = "HTTaxinvoiceService")
    @ConditionalOnMissingBean
    public HTTaxinvoiceService htTaxinvoiceServiceConfig() {

        HTTaxinvoiceServiceImp htTaxinvoiceServiceImp = new HTTaxinvoiceServiceImp();

        htTaxinvoiceServiceImp.setLinkID(htTaxinvoiceServiceProperties.getLinkId() == null || htTaxinvoiceServiceProperties.getLinkId().trim().isEmpty() ?
                popbillServiceProperties.getLinkId() : htTaxinvoiceServiceProperties.getLinkId());
        htTaxinvoiceServiceImp .setSecretKey(htTaxinvoiceServiceProperties.getSecretKey() == null || htTaxinvoiceServiceProperties.getSecretKey().trim().isEmpty() ?
                popbillServiceProperties.getSecretKey() : htTaxinvoiceServiceProperties.getSecretKey());
        htTaxinvoiceServiceImp.setTest(htTaxinvoiceServiceProperties.getIsTest() == null ?
                popbillServiceProperties.getIsTest() : htTaxinvoiceServiceProperties.getIsTest());
        htTaxinvoiceServiceImp.setUseStaticIP(popbillServiceProperties.isUseStaticIp());
        htTaxinvoiceServiceImp.setUseGAIP(popbillServiceProperties.isUseGaIp());
        htTaxinvoiceServiceImp.setUseLocalTimeYN(popbillServiceProperties.isUseLocalTimeYn());
        htTaxinvoiceServiceImp.setIPRestrictOnOff(popbillServiceProperties.getIsIpRestrictOnOff());
        htTaxinvoiceServiceImp.setAuthURL(popbillServiceProperties.getAuthUrl());
        htTaxinvoiceServiceImp.setServiceURL(popbillServiceProperties.getServiceUrl());
        htTaxinvoiceServiceImp.setTestServiceURL(popbillServiceProperties.getTestServiceUrl());
        htTaxinvoiceServiceImp.setProxyIP(popbillServiceProperties.getProxyIp());
        htTaxinvoiceServiceImp.setProxyPort(popbillServiceProperties.getProxyPort());
        htTaxinvoiceServiceImp.setCustomHeader(popbillServiceProperties.getCustomHeader());
        htTaxinvoiceServiceImp.setMleKeyID(popbillServiceProperties.getMleKeyID());
        htTaxinvoiceServiceImp.setMleKeyName(popbillServiceProperties.getMleKeyName());
        htTaxinvoiceServiceImp.setMlePublicKey(popbillServiceProperties.getMlePublicKey());

        logger.debug("POPBiLL Initialized HTTaxinvoiceService");

        return htTaxinvoiceServiceImp;
    }
}
