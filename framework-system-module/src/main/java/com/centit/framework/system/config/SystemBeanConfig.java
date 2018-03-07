package com.centit.framework.system.config;

import com.centit.framework.listener.InitialWebRuntimeEnvironment;
import com.centit.framework.model.adapter.PlatformEnvironment;
import com.centit.framework.security.model.CentitPasswordEncoder;
import com.centit.framework.security.model.CentitPasswordEncoderImpl;
import com.centit.framework.security.model.CentitSessionRegistry;
import com.centit.framework.security.model.MemorySessionRegistryImpl;
import com.centit.framework.system.dao.*;
import com.centit.framework.system.service.impl.DBPlatformEnvironment;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

@PropertySource("classpath:system.properties")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableCaching(proxyTargetClass = true)
public class SystemBeanConfig implements EnvironmentAware {

    Environment env;

    @Override
    public void setEnvironment(final Environment environment) {
        this.env = environment;
    }

    /**
     * 配置事务异常封装
     * @return PersistenceExceptionTranslationPostProcessor
     */
    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor() {
        return new AutowiredAnnotationBeanPostProcessor();
    }

    @Bean
    @Lazy(value = false)
    public InitialWebRuntimeEnvironment initialEnvironment() {
        InitialWebRuntimeEnvironment initialWebRuntimeEnvironment = new InitialWebRuntimeEnvironment();
        initialWebRuntimeEnvironment.initialEnvironment();
        return initialWebRuntimeEnvironment;
    }

    @Bean("passwordEncoder")
    public CentitPasswordEncoderImpl passwordEncoder() {
        return  new CentitPasswordEncoderImpl();
    }

    @Bean
    public PlatformEnvironment platformEnvironment(){
        DBPlatformEnvironment platformEnvironment = new DBPlatformEnvironment();
        return platformEnvironment;
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return new HttpSessionCsrfTokenRepository();
    }

    @Bean
    public CentitSessionRegistry centitSessionRegistry(){
        return new MemorySessionRegistryImpl();
    }

    @Bean
    public EhCacheCacheManager cacheManager(CacheManager cacheManager) {
      return new EhCacheCacheManager(cacheManager);
    }

    @Bean
    public EhCacheManagerFactoryBean cacheManagerFactory() {

        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        return ehCacheManagerFactoryBean;

    }

}
