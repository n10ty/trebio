package com.trebio.spring;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.Loader;
import com.mitchellbosecke.pebble.loader.ServletLoader;

import com.mitchellbosecke.pebble.spring.PebbleViewResolver;
import com.mitchellbosecke.pebble.spring.extension.SpringExtension;
import com.trebio.context.bot.model.KotloBot;
import com.trebio.web.template.CustomTemplateExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpEncodingProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.ServletContext;

@Configuration
@ComponentScan(basePackages = {"com.trebio.web"})
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    public MvcConfig() {
        super();
    }

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private HttpEncodingProperties httpEncodingProperties;

    @Autowired
    private KotloBot bot;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("user");
        registry.addViewController("channel/{channelId}/post/text").setViewName("channel/post_text");
        registry.addViewController("channel/{channelId}/post/photo").setViewName("channel/post_photo");
        registry.addViewController("/").setViewName("index");
    }

    @Bean
    public Loader templateLoader(){
        return new ServletLoader(servletContext);
    }

    @Bean
    public PebbleEngine pebbleEngine() {
        return new PebbleEngine.Builder()
                .loader(this.templateLoader())
                .extension(new SpringExtension(), new CustomTemplateExtension(bot))
                .build();
    }

    @Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    @Bean
    public ViewResolver viewResolver() {
        PebbleViewResolver viewResolver = new PebbleViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");
        viewResolver.setPebbleEngine(this.pebbleEngine());
        return viewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/").setCachePeriod(31556926);
        registry.addResourceHandler("/img/**").addResourceLocations("/resources/img/").setCachePeriod(31556926);
        registry.addResourceHandler("/js/**").addResourceLocations("/resources/js/").setCachePeriod(31556926);
        registry.addResourceHandler("/image/**").addResourceLocations("/resources/image/photo/").setCachePeriod(31556926);
    }
}
