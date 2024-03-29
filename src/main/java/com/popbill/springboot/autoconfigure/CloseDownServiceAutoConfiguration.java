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

import com.popbill.api.CloseDownService;
import com.popbill.api.closedown.CloseDownServiceImp;
import com.popbill.springboot.autoconfigure.properties.CloseDownServiceProperties;
import com.popbill.springboot.autoconfigure.properties.PopbillServiceProperties;

@Configuration
@ConditionalOnClass(CloseDownService.class)
@EnableConfigurationProperties({ CloseDownServiceProperties.class, PopbillServiceProperties.class })
public class CloseDownServiceAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(CloseDownServiceAutoConfiguration.class);

    @Autowired
    private PopbillServiceProperties popbillServiceProperties;

    @Autowired
    private CloseDownServiceProperties closeDownServiceProperties;

    @Lazy
    @Bean(name = "CloseDownService")
    @ConditionalOnMissingBean
    public CloseDownService taxinvoiceServiceConfig() {

        CloseDownServiceImp closeDownServiceImp = new CloseDownServiceImp();

        closeDownServiceImp.setLinkID(closeDownServiceProperties.getLinkId() == null || closeDownServiceProperties.getLinkId().trim().isEmpty() ?
                popbillServiceProperties.getLinkId() : closeDownServiceProperties.getLinkId());
        closeDownServiceImp .setSecretKey(closeDownServiceProperties.getSecretKey() == null || closeDownServiceProperties.getSecretKey().trim().isEmpty() ?
                popbillServiceProperties.getSecretKey() : closeDownServiceProperties.getSecretKey());
        closeDownServiceImp.setTest(closeDownServiceProperties.getIsTest() == null ?
                popbillServiceProperties.getIsTest() : closeDownServiceProperties.getIsTest());
        closeDownServiceImp.setUseStaticIP(popbillServiceProperties.isUseStaticIp());
        closeDownServiceImp.setUseGAIP(popbillServiceProperties.isUseGaIp());
        closeDownServiceImp.setUseLocalTimeYN(popbillServiceProperties.isUseLocalTimeYn());
        closeDownServiceImp.setIPRestrictOnOff(popbillServiceProperties.getIsIpRestrictOnOff());
        closeDownServiceImp.setAuthURL(popbillServiceProperties.getAuthUrl());
        closeDownServiceImp.setServiceURL(popbillServiceProperties.getServiceUrl());
        closeDownServiceImp.setTestServiceURL(popbillServiceProperties.getTestServiceUrl());
        closeDownServiceImp.setProxyIP(popbillServiceProperties.getProxyIp());
        closeDownServiceImp.setProxyPort(popbillServiceProperties.getProxyPort());
        closeDownServiceImp.setCustomHeader(popbillServiceProperties.getCustomHeader());
        closeDownServiceImp.setMleKeyID(popbillServiceProperties.getMleKeyID());
        closeDownServiceImp.setMleKeyName(popbillServiceProperties.getMleKeyName());
        closeDownServiceImp.setMlePublicKey(popbillServiceProperties.getMlePublicKey());

        logger.debug("POPBiLL Initialized CloseDownService");

        return closeDownServiceImp;
    }
}
