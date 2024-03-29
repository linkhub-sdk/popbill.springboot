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

import com.popbill.api.CashbillService;
import com.popbill.api.cashbill.CashbillServiceImp;
import com.popbill.springboot.autoconfigure.properties.CashbillServiceProperties;
import com.popbill.springboot.autoconfigure.properties.PopbillServiceProperties;

@Configuration
@ConditionalOnClass(CashbillService.class)
@EnableConfigurationProperties({ CashbillServiceProperties.class, PopbillServiceProperties.class })
public class CashbillServiceAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(CashbillServiceAutoConfiguration.class);

    @Autowired
    private PopbillServiceProperties popbillServiceProperties;

    @Autowired
    private CashbillServiceProperties cashbillServiceProperties;

    @Lazy
    @Bean(name = "CashbillService")
    @ConditionalOnMissingBean
    public CashbillService cashbillServiceConfig() {

        CashbillServiceImp cashbillServiceImp = new CashbillServiceImp();

        cashbillServiceImp.setLinkID(cashbillServiceProperties.getLinkId() == null || cashbillServiceProperties.getLinkId().trim().isEmpty() ?
                popbillServiceProperties.getLinkId() : cashbillServiceProperties.getLinkId());
        cashbillServiceImp .setSecretKey(cashbillServiceProperties.getSecretKey() == null || cashbillServiceProperties.getSecretKey().trim().isEmpty() ?
                popbillServiceProperties.getSecretKey() : cashbillServiceProperties.getSecretKey());
        cashbillServiceImp.setTest(cashbillServiceProperties.getIsTest() == null ?
                popbillServiceProperties.getIsTest() : cashbillServiceProperties.getIsTest());
        cashbillServiceImp.setUseStaticIP(popbillServiceProperties.isUseStaticIp());
        cashbillServiceImp.setUseGAIP(popbillServiceProperties.isUseGaIp());
        cashbillServiceImp.setUseLocalTimeYN(popbillServiceProperties.isUseLocalTimeYn());
        cashbillServiceImp.setIPRestrictOnOff(popbillServiceProperties.getIsIpRestrictOnOff());
        cashbillServiceImp.setAuthURL(popbillServiceProperties.getAuthUrl());
        cashbillServiceImp.setServiceURL(popbillServiceProperties.getServiceUrl());
        cashbillServiceImp.setTestServiceURL(popbillServiceProperties.getTestServiceUrl());
        cashbillServiceImp.setProxyIP(popbillServiceProperties.getProxyIp());
        cashbillServiceImp.setProxyPort(popbillServiceProperties.getProxyPort());
        cashbillServiceImp.setCustomHeader(popbillServiceProperties.getCustomHeader());
        cashbillServiceImp.setMleKeyID(popbillServiceProperties.getMleKeyID());
        cashbillServiceImp.setMleKeyName(popbillServiceProperties.getMleKeyName());
        cashbillServiceImp.setMlePublicKey(popbillServiceProperties.getMlePublicKey());

        logger.debug("POPBiLL Initialized CashbillService");

        return cashbillServiceImp;
    }
}
