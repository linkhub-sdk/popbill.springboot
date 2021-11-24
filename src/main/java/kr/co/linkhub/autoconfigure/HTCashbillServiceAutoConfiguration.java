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

import com.popbill.api.HTCashbillService;
import com.popbill.api.hometax.HTCashbillServiceImp;

import kr.co.linkhub.autoconfigure.properties.HTCashbillServiceProperties;
import kr.co.linkhub.autoconfigure.properties.PopbillServiceProperties;

@Configuration
@ConditionalOnClass(HTCashbillService.class)
@EnableConfigurationProperties({ HTCashbillServiceProperties.class, PopbillServiceProperties.class })
public class HTCashbillServiceAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(HTCashbillServiceAutoConfiguration.class);

    @Autowired
    private PopbillServiceProperties popbillServiceProperties;

    @Autowired
    private HTCashbillServiceProperties htCashbillServiceProperties;

    @Lazy
    @Bean(name = "HTCashbillService")
    @ConditionalOnMissingBean
    public HTCashbillService htCashbillServiceConfig() {

        HTCashbillServiceImp htCashbillServiceImp = new HTCashbillServiceImp();

        htCashbillServiceImp.setLinkID(htCashbillServiceProperties.getLinkId() == null || htCashbillServiceProperties.getLinkId().trim().isEmpty() ?
                popbillServiceProperties.getLinkId() : htCashbillServiceProperties.getLinkId());
        htCashbillServiceImp .setSecretKey(htCashbillServiceProperties.getSecretKey() == null || htCashbillServiceProperties.getSecretKey().trim().isEmpty() ?
                popbillServiceProperties.getSecretKey() : htCashbillServiceProperties.getSecretKey());
        htCashbillServiceImp.setTest(htCashbillServiceProperties.isTest() == null ? 
                popbillServiceProperties.isTest() : htCashbillServiceProperties.isTest());
        htCashbillServiceImp.setUseStaticIP(popbillServiceProperties.isUseStaticIp());
        htCashbillServiceImp.setUseGAIP(popbillServiceProperties.isUseGaIp());
        htCashbillServiceImp.setUseLocalTimeYN(popbillServiceProperties.isUseLocalTimeYn());
        htCashbillServiceImp.setIPRestrictOnOff(popbillServiceProperties.isIpRestrictOnOff());
        htCashbillServiceImp.setAuthURL(popbillServiceProperties.getAuthUrl());
        htCashbillServiceImp.setServiceURL(popbillServiceProperties.getServiceUrl());
        htCashbillServiceImp.setTestServiceURL(popbillServiceProperties.getTestServiceUrl());
        htCashbillServiceImp.setProxyIP(popbillServiceProperties.getProxyIp());
        htCashbillServiceImp.setProxyPort(popbillServiceProperties.getProxyPort());

        logger.debug("POPBiLL Initialized HTCashbillService");

        return htCashbillServiceImp;
    }
}
