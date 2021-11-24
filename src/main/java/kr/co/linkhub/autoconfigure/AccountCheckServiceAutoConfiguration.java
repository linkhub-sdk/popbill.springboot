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

import com.popbill.api.AccountCheckService;
import com.popbill.api.accountcheck.AccountCheckServiceImp;

import kr.co.linkhub.autoconfigure.properties.AccountCheckServiceProperties;
import kr.co.linkhub.autoconfigure.properties.PopbillServiceProperties;

@Configuration
@ConditionalOnClass(AccountCheckService.class)
@EnableConfigurationProperties({ AccountCheckServiceProperties.class, PopbillServiceProperties.class })
public class AccountCheckServiceAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(AccountCheckServiceAutoConfiguration.class);

    @Autowired
    private PopbillServiceProperties popbillServiceProperties;

    @Autowired
    private AccountCheckServiceProperties accountCheckServiceProperties;

    @Lazy
    @Bean(name = "AccountCheckService")
    @ConditionalOnMissingBean
    public AccountCheckService accountCheckServiceConfig() {

        AccountCheckServiceImp accountCheckServiceImp = new AccountCheckServiceImp();

        accountCheckServiceImp.setLinkID(accountCheckServiceProperties.getLinkId() == null || accountCheckServiceProperties.getLinkId().trim().isEmpty() ?
                popbillServiceProperties.getLinkId() : accountCheckServiceProperties.getLinkId());
        accountCheckServiceImp .setSecretKey(accountCheckServiceProperties.getSecretKey() == null || accountCheckServiceProperties.getSecretKey().trim().isEmpty() ?
                popbillServiceProperties.getSecretKey() : accountCheckServiceProperties.getSecretKey());
        accountCheckServiceImp.setTest(accountCheckServiceProperties.isTest() == null ? 
                popbillServiceProperties.isTest() : accountCheckServiceProperties.isTest());
        accountCheckServiceImp.setUseStaticIP(popbillServiceProperties.isUseStaticIp());
        accountCheckServiceImp.setUseGAIP(popbillServiceProperties.isUseGaIp());
        accountCheckServiceImp.setUseLocalTimeYN(popbillServiceProperties.isUseLocalTimeYn());
        accountCheckServiceImp.setIPRestrictOnOff(popbillServiceProperties.isIpRestrictOnOff());
        accountCheckServiceImp.setAuthURL(popbillServiceProperties.getAuthUrl());
        accountCheckServiceImp.setServiceURL(popbillServiceProperties.getServiceUrl());
        accountCheckServiceImp.setTestServiceURL(popbillServiceProperties.getTestServiceUrl());
        accountCheckServiceImp.setProxyIP(popbillServiceProperties.getProxyIp());
        accountCheckServiceImp.setProxyPort(popbillServiceProperties.getProxyPort());

        logger.debug("POPBiLL Initialized AccountCheckService");
        
        return accountCheckServiceImp;
    }
}
