package com.yong.login.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.annotation.Resource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name = "UserDetailsServiceImpl")
    private UserDetailsService userDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private AuthenticationSuccessHandler loginSuccessHandler;
//    @Autowired
//    private AuthenticationFailureHandler loginFailureHandler;
//    @Autowired
//    private AuthenticationEntryPoint authenticationEntryPoint;
//    @Autowired
//    private AccessDeniedHandler accessDeniedHandler;
//    @Autowired
//    private LogoutSuccessHandler logoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                //认证规则
//                .anyRequest().access("@rbacService.hasPermission(request,authentication)")
                .and()
                //开启表单登录
                .formLogin().loginPage("/login").permitAll()
                .and()
                .formLogin().loginPage("/login1").permitAll();
        //登录失败处理
//                .failureHandler(loginFailureHandler)
//                //登录成功处理
//                .successHandler(loginSuccessHandler)
//                .and()
//                //没有权限处理
//                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
//                .and()
//                //未登录处理
//                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
//                .and()
                //退出成功处理
//                .logout().logoutSuccessHandler(logoutSuccessHandler).permitAll();
        //开启跨域访问
        httpSecurity.cors().disable();
        //开启模拟请求，比如API POST测试工具的测试，不开启时，API POST为报403错误
        httpSecurity.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        //对于在header里面增加token等类似情况，放行所有OPTIONS请求。
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
      web.ignoring().antMatchers("/index.html", "/static/**", "/login", "/favicon.ico","/register")
//              // 给 swagger 放行；不需要权限能访问的资源
              .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/images/**", "/webjars/**", "/v2/api-docs", "/configuration/ui", "/configuration/security");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

}
