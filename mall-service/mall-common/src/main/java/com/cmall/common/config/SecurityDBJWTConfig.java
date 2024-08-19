package com.cmall.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityDBJWTConfig{
//
////    private final CustomUserDetailsService userDetailsService;
//    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public SecurityDBJWTConfig(CustomUserDetailsService userDetailsService, JwtAuthenticationEntryPoint authenticationEntryPoint, PasswordEncoder passwordEncoder) {
////        this.userDetailsService = userDetailsService;
//        this.authenticationEntryPoint = authenticationEntryPoint;
//        this.passwordEncoder = passwordEncoder;
//    }
////
//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        return new JwtAuthenticationFilter();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // 禁用 CSRF 保护
//                .exceptionHandling(exception -> exception
//                        .authenticationEntryPoint(authenticationEntryPoint)) // 自定义认证入口点
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 设置会话为无状态
//                .authorizeRequests(authorize -> authorize
//                        .antMatchers("/api/auth/signup").permitAll() // 允许所有用户访问 `/api/auth/signup`
//                        .antMatchers("/api/auth/signin").permitAll()
//                        .anyRequest().authenticated()) // 所有其他请求都需要认证
//                .formLogin(form -> form.disable()) // 禁用表单登录，适用于JWT认证模式
//                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // 添加自定义JWT过滤器
//
//        return http.build();
//    }
//
////    @Bean
////    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
////        http.authenticationProvider(daoAuthenticationProvider());
////        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
////    }
////
////    @Bean
////    public DaoAuthenticationProvider daoAuthenticationProvider() {
////        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
////        authProvider.setUserDetailsService(userDetailsService);
////        authProvider.setPasswordEncoder(passwordEncoder);
////        return authProvider;
////    }
//}