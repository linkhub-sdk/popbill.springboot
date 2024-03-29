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

import com.popbill.api.KakaoService;
import com.popbill.api.kakao.KakaoServiceImp;
import com.popbill.springboot.autoconfigure.properties.KakaoServiceProperties;
import com.popbill.springboot.autoconfigure.properties.PopbillServiceProperties;

@Configuration
@ConditionalOnClass(KakaoService.class)
@EnableConfigurationProperties({ KakaoServiceProperties.class, PopbillServiceProperties.class })
public class KakaoServiceAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(KakaoServiceAutoConfiguration.class);

    @Autowired
    private PopbillServiceProperties popbillServiceProperties;

    @Autowired
    private KakaoServiceProperties kakaoServiceProperties;

    @Lazy
    @Bean(name = "KakaoService")
    @ConditionalOnMissingBean
    public KakaoService kakaoServiceConfig() {

        KakaoServiceImp kakaoServiceImp = new KakaoServiceImp();

        kakaoServiceImp.setLinkID(kakaoServiceProperties.getLinkId() == null || kakaoServiceProperties.getLinkId().trim().isEmpty() ?
                popbillServiceProperties.getLinkId() : kakaoServiceProperties.getLinkId());
        kakaoServiceImp .setSecretKey(kakaoServiceProperties.getSecretKey() == null || kakaoServiceProperties.getSecretKey().trim().isEmpty() ?
                popbillServiceProperties.getSecretKey() : kakaoServiceProperties.getSecretKey());
        kakaoServiceImp.setTest(kakaoServiceProperties.getIsTest() == null ?
                popbillServiceProperties.getIsTest() : kakaoServiceProperties.getIsTest());
        kakaoServiceImp.setUseStaticIP(popbillServiceProperties.isUseStaticIp());
        kakaoServiceImp.setUseGAIP(popbillServiceProperties.isUseGaIp());
        kakaoServiceImp.setUseLocalTimeYN(popbillServiceProperties.isUseLocalTimeYn());
        kakaoServiceImp.setIPRestrictOnOff(popbillServiceProperties.getIsIpRestrictOnOff());
        kakaoServiceImp.setAuthURL(popbillServiceProperties.getAuthUrl());
        kakaoServiceImp.setServiceURL(popbillServiceProperties.getServiceUrl());
        kakaoServiceImp.setTestServiceURL(popbillServiceProperties.getTestServiceUrl());
        kakaoServiceImp.setProxyIP(popbillServiceProperties.getProxyIp());
        kakaoServiceImp.setProxyPort(popbillServiceProperties.getProxyPort());
        kakaoServiceImp.setCustomHeader(popbillServiceProperties.getCustomHeader());
        kakaoServiceImp.setMleKeyID(popbillServiceProperties.getMleKeyID());
        kakaoServiceImp.setMleKeyName(popbillServiceProperties.getMleKeyName());
        kakaoServiceImp.setMlePublicKey(popbillServiceProperties.getMlePublicKey());

        logger.debug("POPBiLL Initialized KakaoService");

        return kakaoServiceImp;
    }
}
