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

import com.popbill.api.BizInfoCheckService;
import com.popbill.api.bizinfocheck.BizInfoCheckServiceImp;
import com.popbill.springboot.autoconfigure.properties.BizInfoCheckServiceProperties;
import com.popbill.springboot.autoconfigure.properties.PopbillServiceProperties;

@Configuration
@ConditionalOnClass(BizInfoCheckService.class)
@EnableConfigurationProperties({ BizInfoCheckServiceProperties.class, PopbillServiceProperties.class })
public class BizInfoCheckServiceAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(BizInfoCheckServiceAutoConfiguration.class);

    @Autowired
    private PopbillServiceProperties popbillServiceProperties;

    @Autowired
    private BizInfoCheckServiceProperties bizInfoCheckServiceProperties;

    @Lazy
    @Bean(name = "BizInfoCheckService")
    @ConditionalOnMissingBean
    public BizInfoCheckService bizInfoCheckServiceConfig() {

        BizInfoCheckServiceImp bizInfoCheckServiceImp = new BizInfoCheckServiceImp();

        bizInfoCheckServiceImp.setLinkID(bizInfoCheckServiceProperties.getLinkId() == null || bizInfoCheckServiceProperties.getLinkId().trim().isEmpty() ?
                popbillServiceProperties.getLinkId() : bizInfoCheckServiceProperties.getLinkId());
        bizInfoCheckServiceImp .setSecretKey(bizInfoCheckServiceProperties.getSecretKey() == null || bizInfoCheckServiceProperties.getSecretKey().trim().isEmpty() ?
                popbillServiceProperties.getSecretKey() : bizInfoCheckServiceProperties.getSecretKey());
        bizInfoCheckServiceImp.setTest(bizInfoCheckServiceProperties.getIsTest() == null ? 
                popbillServiceProperties.getIsTest() : bizInfoCheckServiceProperties.getIsTest());
        bizInfoCheckServiceImp.setUseStaticIP(popbillServiceProperties.isUseStaticIp());
        bizInfoCheckServiceImp.setUseGAIP(popbillServiceProperties.isUseGaIp());
        bizInfoCheckServiceImp.setUseLocalTimeYN(popbillServiceProperties.isUseLocalTimeYn());
        bizInfoCheckServiceImp.setIPRestrictOnOff(popbillServiceProperties.getIsIpRestrictOnOff());
        bizInfoCheckServiceImp.setAuthURL(popbillServiceProperties.getAuthUrl());
        bizInfoCheckServiceImp.setServiceURL(popbillServiceProperties.getServiceUrl());
        bizInfoCheckServiceImp.setTestServiceURL(popbillServiceProperties.getTestServiceUrl());
        bizInfoCheckServiceImp.setProxyIP(popbillServiceProperties.getProxyIp());
        bizInfoCheckServiceImp.setProxyPort(popbillServiceProperties.getProxyPort());
        bizInfoCheckServiceImp.setCustomHeader(popbillServiceProperties.getCustomHeader());

        logger.debug("POPBiLL Initialized BizInfoCheckService");
        
        return bizInfoCheckServiceImp;
    }
}
