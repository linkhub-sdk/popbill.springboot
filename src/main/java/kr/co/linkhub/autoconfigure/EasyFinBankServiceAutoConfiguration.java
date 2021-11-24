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

import com.popbill.api.EasyFinBankService;
import com.popbill.api.easyfin.EasyFinBankServiceImp;

import kr.co.linkhub.autoconfigure.properties.EasyFinBankServiceProperties;
import kr.co.linkhub.autoconfigure.properties.PopbillServiceProperties;

@Configuration
@ConditionalOnClass(EasyFinBankService.class)
@EnableConfigurationProperties({ EasyFinBankServiceProperties.class, PopbillServiceProperties.class })
public class EasyFinBankServiceAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(EasyFinBankServiceAutoConfiguration.class);

    @Autowired
    private PopbillServiceProperties popbillServiceProperties;

    @Autowired
    private EasyFinBankServiceProperties easyFinBankServiceProperties;

    @Lazy
    @Bean(name = "EasyFinBankService")
    @ConditionalOnMissingBean
    public EasyFinBankService easyFinBankServiceConfig() {

        EasyFinBankServiceImp easyFinBankServiceImp = new EasyFinBankServiceImp();

        easyFinBankServiceImp.setLinkID(easyFinBankServiceProperties.getLinkId() == null || easyFinBankServiceProperties.getLinkId().trim().isEmpty() ?
                popbillServiceProperties.getLinkId() : easyFinBankServiceProperties.getLinkId());
        easyFinBankServiceImp .setSecretKey(easyFinBankServiceProperties.getSecretKey() == null || easyFinBankServiceProperties.getSecretKey().trim().isEmpty() ?
                popbillServiceProperties.getSecretKey() : easyFinBankServiceProperties.getSecretKey());
        easyFinBankServiceImp.setTest(easyFinBankServiceProperties.isTest() == null ? 
                popbillServiceProperties.isTest() : easyFinBankServiceProperties.isTest());
        easyFinBankServiceImp.setUseStaticIP(popbillServiceProperties.isUseStaticIp());
        easyFinBankServiceImp.setUseGAIP(popbillServiceProperties.isUseGaIp());
        easyFinBankServiceImp.setUseLocalTimeYN(popbillServiceProperties.isUseLocalTimeYn());
        easyFinBankServiceImp.setIPRestrictOnOff(popbillServiceProperties.isIpRestrictOnOff());
        easyFinBankServiceImp.setAuthURL(popbillServiceProperties.getAuthUrl());
        easyFinBankServiceImp.setServiceURL(popbillServiceProperties.getServiceUrl());
        easyFinBankServiceImp.setTestServiceURL(popbillServiceProperties.getTestServiceUrl());
        easyFinBankServiceImp.setProxyIP(popbillServiceProperties.getProxyIp());
        easyFinBankServiceImp.setProxyPort(popbillServiceProperties.getProxyPort());

        logger.debug("POPBiLL Initialized EasyFinBankService");

        return easyFinBankServiceImp;
    }
}
