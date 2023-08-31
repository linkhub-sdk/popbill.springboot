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

import com.popbill.api.TaxinvoiceService;
import com.popbill.api.taxinvoice.TaxinvoiceServiceImp;
import com.popbill.springboot.autoconfigure.properties.PopbillServiceProperties;
import com.popbill.springboot.autoconfigure.properties.TaxinvoiceServiceProperties;

@Configuration
@ConditionalOnClass(TaxinvoiceService.class)
@EnableConfigurationProperties({ TaxinvoiceServiceProperties.class, PopbillServiceProperties.class })
public class TaxinvoiceServiceAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(TaxinvoiceServiceAutoConfiguration.class);

    @Autowired
    private PopbillServiceProperties popbillServiceProperties;
    
    @Autowired
    private TaxinvoiceServiceProperties taxinvoiceServiceProperties;

    @Lazy
    @Bean(name = "TaxinvoiceService")
    @ConditionalOnMissingBean
    public TaxinvoiceService taxinvoiceServiceConfig() {
        
        TaxinvoiceServiceImp taxinvoiceServiceImp = new TaxinvoiceServiceImp();

        taxinvoiceServiceImp.setLinkID(taxinvoiceServiceProperties.getLinkId() == null || taxinvoiceServiceProperties.getLinkId().trim().isEmpty() ?
                popbillServiceProperties.getLinkId() : taxinvoiceServiceProperties.getLinkId());
        taxinvoiceServiceImp .setSecretKey(taxinvoiceServiceProperties.getSecretKey() == null || taxinvoiceServiceProperties.getSecretKey().trim().isEmpty() ?
                popbillServiceProperties.getSecretKey() : taxinvoiceServiceProperties.getSecretKey());
        taxinvoiceServiceImp.setTest(taxinvoiceServiceProperties.getIsTest() == null ? 
                popbillServiceProperties.getIsTest() : taxinvoiceServiceProperties.getIsTest());
        taxinvoiceServiceImp.setUseStaticIP(popbillServiceProperties.isUseStaticIp());
        taxinvoiceServiceImp.setUseGAIP(popbillServiceProperties.isUseGaIp());
        taxinvoiceServiceImp.setUseLocalTimeYN(popbillServiceProperties.isUseLocalTimeYn());
        taxinvoiceServiceImp.setIPRestrictOnOff(popbillServiceProperties.getIsIpRestrictOnOff());
        taxinvoiceServiceImp.setAuthURL(popbillServiceProperties.getAuthUrl());
        taxinvoiceServiceImp.setServiceURL(popbillServiceProperties.getServiceUrl());
        taxinvoiceServiceImp.setTestServiceURL(popbillServiceProperties.getTestServiceUrl());
        taxinvoiceServiceImp.setProxyIP(popbillServiceProperties.getProxyIp());
        taxinvoiceServiceImp.setProxyPort(popbillServiceProperties.getProxyPort());
        taxinvoiceServiceImp.setCustomHeader(popbillServiceProperties.getCustomHeader());

        logger.debug("POPBiLL Initialized TaxinvoiceService");

        return taxinvoiceServiceImp;
    }
}
