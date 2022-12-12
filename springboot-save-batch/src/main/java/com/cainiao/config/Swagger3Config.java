package com.cainiao.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@EnableOpenApi
@EnableWebMvc
@Configuration
public class Swagger3Config {
//http://localhost:8089/swagger-ui/index.html
// 这个需要在配置文件中配置是否开启Swagger

    @Value("${swagger.enable}")
    private Boolean SwaggerEnable;
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)// 添加文档信息
                .apiInfo(apiInfo())
                .select()
// 选择要显示的包 如果是下面的写法则会将所有的接口都会展示出来 无论你有没有写注解
                .apis(RequestHandlerSelectors.any())
// 而这种写法则是将所有的有注解的接口展示出来
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
// 是否启用Swagger
                .enable(SwaggerEnable);

    }

    /**

     * 文档信息

     * @return ApiInfo

     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("这是私有化部署的接口文档")
                .description("更多请咨询服务开发者peishunwu@360.cn")
                .contact(new Contact("极库云", "xxxx", "peishunwu@360.cn"))
                .version("1.0")
                .build();

    }

}
