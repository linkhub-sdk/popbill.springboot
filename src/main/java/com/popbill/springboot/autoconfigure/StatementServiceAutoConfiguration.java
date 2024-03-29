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

import com.popbill.api.StatementService;
import com.popbill.api.statement.StatementServiceImp;
import com.popbill.springboot.autoconfigure.properties.PopbillServiceProperties;
import com.popbill.springboot.autoconfigure.properties.StatementServiceProperties;

@Configuration
@ConditionalOnClass(StatementService.class)
@EnableConfigurationProperties({ StatementServiceProperties.class, PopbillServiceProperties.class })
public class StatementServiceAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(StatementServiceAutoConfiguration.class);

    @Autowired
    private PopbillServiceProperties popbillServiceProperties;

    @Autowired
    private StatementServiceProperties statementServiceProperties;

    @Lazy
    @Bean(name = "StatementService")
    @ConditionalOnMissingBean
    public StatementService statementServiceConfig() {

        StatementServiceImp statementServiceImp = new StatementServiceImp();

        statementServiceImp.setLinkID(statementServiceProperties.getLinkId() == null || statementServiceProperties.getLinkId().trim().isEmpty() ?
                popbillServiceProperties.getLinkId() : statementServiceProperties.getLinkId());
        statementServiceImp .setSecretKey(statementServiceProperties.getSecretKey() == null || statementServiceProperties.getSecretKey().trim().isEmpty() ?
                popbillServiceProperties.getSecretKey() : statementServiceProperties.getSecretKey());
        statementServiceImp.setTest(statementServiceProperties.getIsTest() == null ?
                popbillServiceProperties.getIsTest() : statementServiceProperties.getIsTest());
        statementServiceImp.setUseStaticIP(popbillServiceProperties.isUseStaticIp());
        statementServiceImp.setUseGAIP(popbillServiceProperties.isUseGaIp());
        statementServiceImp.setUseLocalTimeYN(popbillServiceProperties.isUseLocalTimeYn());
        statementServiceImp.setIPRestrictOnOff(popbillServiceProperties.getIsIpRestrictOnOff());
        statementServiceImp.setAuthURL(popbillServiceProperties.getAuthUrl());
        statementServiceImp.setServiceURL(popbillServiceProperties.getServiceUrl());
        statementServiceImp.setTestServiceURL(popbillServiceProperties.getTestServiceUrl());
        statementServiceImp.setProxyIP(popbillServiceProperties.getProxyIp());
        statementServiceImp.setProxyPort(popbillServiceProperties.getProxyPort());
        statementServiceImp.setCustomHeader(popbillServiceProperties.getCustomHeader());
        statementServiceImp.setMleKeyID(popbillServiceProperties.getMleKeyID());
        statementServiceImp.setMleKeyName(popbillServiceProperties.getMleKeyName());
        statementServiceImp.setMlePublicKey(popbillServiceProperties.getMlePublicKey());

        logger.debug("POPBiLL Initialized StatementService");

        return statementServiceImp;
    }
}
