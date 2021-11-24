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

import com.popbill.api.FaxService;
import com.popbill.api.fax.FaxServiceImp;

import kr.co.linkhub.autoconfigure.properties.FaxServiceProperties;
import kr.co.linkhub.autoconfigure.properties.PopbillServiceProperties;

@Configuration
@ConditionalOnClass(FaxService.class)
@EnableConfigurationProperties({ FaxServiceProperties.class, PopbillServiceProperties.class })
public class FaxServiceAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(FaxServiceAutoConfiguration.class);

    @Autowired
    private PopbillServiceProperties popbillServiceProperties;

    @Autowired
    private FaxServiceProperties faxServiceProperties;

    @Lazy
    @Bean(name = "FaxService")
    @ConditionalOnMissingBean
    public FaxService faxServiceBaseConfig() {

        FaxServiceImp faxServiceImp = new FaxServiceImp();

        faxServiceImp.setLinkID(faxServiceProperties.getLinkId() == null || faxServiceProperties.getLinkId().trim().isEmpty() ?
                popbillServiceProperties.getLinkId() : faxServiceProperties.getLinkId());
        faxServiceImp .setSecretKey(faxServiceProperties.getSecretKey() == null || faxServiceProperties.getSecretKey().trim().isEmpty() ?
                popbillServiceProperties.getSecretKey() : faxServiceProperties.getSecretKey());
        faxServiceImp.setTest(faxServiceProperties.isTest() == null ? 
                popbillServiceProperties.isTest() : faxServiceProperties.isTest());
        faxServiceImp.setUseStaticIP(popbillServiceProperties.isUseStaticIp());
        faxServiceImp.setUseGAIP(popbillServiceProperties.isUseGaIp());
        faxServiceImp.setUseLocalTimeYN(popbillServiceProperties.isUseLocalTimeYn());
        faxServiceImp.setIPRestrictOnOff(popbillServiceProperties.isIpRestrictOnOff());
        faxServiceImp.setAuthURL(popbillServiceProperties.getAuthUrl());
        faxServiceImp.setServiceURL(popbillServiceProperties.getServiceUrl());
        faxServiceImp.setTestServiceURL(popbillServiceProperties.getTestServiceUrl());
        faxServiceImp.setProxyIP(popbillServiceProperties.getProxyIp());
        faxServiceImp.setProxyPort(popbillServiceProperties.getProxyPort());

        logger.debug("POPBiLL Initialized FaxService");

        return faxServiceImp;
    }
}
