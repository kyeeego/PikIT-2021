package ru.kyeeego.pikit.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kyeeego.pikit.modules.requisition.filter.ModifyReqAccessFilter;
import ru.kyeeego.pikit.modules.requisition.port.RequisitionRepository;

@Configuration
public class BeanRegistration {

    @Autowired
    private RequisitionRepository repository;

    @Bean
    public FilterRegistrationBean<ModifyReqAccessFilter> modifyReqAccessFilter() {
        FilterRegistrationBean<ModifyReqAccessFilter> filter
                = new FilterRegistrationBean<>();

        filter.setFilter(new ModifyReqAccessFilter(repository));
        filter.addUrlPatterns("/api/v1/req/new/update/*");

        return filter;
    }

}
