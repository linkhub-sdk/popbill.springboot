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

import com.popbill.api.MessageService;
import com.popbill.api.message.MessageServiceImp;
import com.popbill.springboot.autoconfigure.properties.MessageServiceProperties;
import com.popbill.springboot.autoconfigure.properties.PopbillServiceProperties;

@Configuration
@ConditionalOnClass(MessageService.class)
@EnableConfigurationProperties({ MessageServiceProperties.class, PopbillServiceProperties.class })
public class MessageServiceAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceAutoConfiguration.class);

    @Autowired
    private PopbillServiceProperties popbillServiceProperties;

    @Autowired
    private MessageServiceProperties messageServiceProperties;

    @Lazy
    @Bean(name = "MessageService")
    @ConditionalOnMissingBean
    public MessageService messageServiceConfig() {

        MessageServiceImp messageServiceImp = new MessageServiceImp();

        messageServiceImp.setLinkID(messageServiceProperties.getLinkId() == null || messageServiceProperties.getLinkId().trim().isEmpty() ?
                popbillServiceProperties.getLinkId() : messageServiceProperties.getLinkId());
        messageServiceImp .setSecretKey(messageServiceProperties.getSecretKey() == null || messageServiceProperties.getSecretKey().trim().isEmpty() ?
                popbillServiceProperties.getSecretKey() : messageServiceProperties.getSecretKey());
        messageServiceImp.setTest(messageServiceProperties.getIsTest() == null ?
                popbillServiceProperties.getIsTest() : messageServiceProperties.getIsTest());
        messageServiceImp.setUseStaticIP(popbillServiceProperties.isUseStaticIp());
        messageServiceImp.setUseGAIP(popbillServiceProperties.isUseGaIp());
        messageServiceImp.setUseLocalTimeYN(popbillServiceProperties.isUseLocalTimeYn());
        messageServiceImp.setIPRestrictOnOff(popbillServiceProperties.getIsIpRestrictOnOff());
        messageServiceImp.setAuthURL(popbillServiceProperties.getAuthUrl());
        messageServiceImp.setServiceURL(popbillServiceProperties.getServiceUrl());
        messageServiceImp.setTestServiceURL(popbillServiceProperties.getTestServiceUrl());
        messageServiceImp.setProxyIP(popbillServiceProperties.getProxyIp());
        messageServiceImp.setProxyPort(popbillServiceProperties.getProxyPort());
        messageServiceImp.setCustomHeader(popbillServiceProperties.getCustomHeader());
        messageServiceImp.setMleKeyID(popbillServiceProperties.getMleKeyID());
        messageServiceImp.setMleKeyName(popbillServiceProperties.getMleKeyName());
        messageServiceImp.setMlePublicKey(popbillServiceProperties.getMlePublicKey());


        logger.debug("POPBiLL Initialized MessageService");

        return messageServiceImp;
    }
}
