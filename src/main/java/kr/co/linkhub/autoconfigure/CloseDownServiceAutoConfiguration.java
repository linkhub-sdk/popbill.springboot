package kr.co.linkhub.autoconfigure;

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

import kr.co.linkhub.autoconfigure.properties.CloseDownServiceProperties;
import kr.co.linkhub.autoconfigure.properties.PopbillServiceProperties;

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
        closeDownServiceImp.setTest(closeDownServiceProperties.isTest() == null ? 
                popbillServiceProperties.isTest() : closeDownServiceProperties.isTest());
        closeDownServiceImp.setUseStaticIP(popbillServiceProperties.isUseStaticIp());
        closeDownServiceImp.setUseGAIP(popbillServiceProperties.isUseGaIp());
        closeDownServiceImp.setUseLocalTimeYN(popbillServiceProperties.isUseLocalTimeYn());
        closeDownServiceImp.setIPRestrictOnOff(popbillServiceProperties.isIpRestrictOnOff());
        closeDownServiceImp.setAuthURL(popbillServiceProperties.getAuthUrl());
        closeDownServiceImp.setServiceURL(popbillServiceProperties.getServiceUrl());
        closeDownServiceImp.setTestServiceURL(popbillServiceProperties.getTestServiceUrl());
        closeDownServiceImp.setProxyIP(popbillServiceProperties.getProxyIp());
        closeDownServiceImp.setProxyPort(popbillServiceProperties.getProxyPort());
        
        logger.debug("POPBiLL Initialized CloseDownService");

        return closeDownServiceImp;
    }
}
