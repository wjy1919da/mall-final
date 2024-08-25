package com.cmall.authservice.service.Impl;
import com.cmall.authservice.dao.RoleRepository;
import com.cmall.authservice.dao.UserRepository;
import com.cmall.authservice.entity.Role;
import com.cmall.authservice.entity.User;
import com.cmall.authservice.payload.JWTAuthResponse;
import com.cmall.authservice.payload.LoginDto;
import com.cmall.authservice.payload.RegisterDto;
import com.cmall.authservice.service.AuthService;
import com.cmall.authservice.utils.JwtTokenUtil;

import com.cmall.common.exception.ApiException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil tokenProvider;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public JWTAuthResponse registerNewUser(RegisterDto registerDto) throws ApiException {
        String email = registerDto.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new ApiException("Email already exists", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registerDto.getPassword()));
        user.setUsername(registerDto.getUsername());
       // 处理用户角色
        Set<Role> userRoles = new HashSet<>();

        // 获取请求中的 userRole 属性
        Integer userRoleId = registerDto.getUserRole(); // 假设 registerDto 有一个 userRole 字段

        // 根据 userRoleId 设置角色
        if (userRoleId != null) {
            Optional<Role> role = roleRepository.findById(userRoleId);
            if (role.isPresent()) {
                userRoles.add(role.get());
            } else {
                throw new ApiException("User Role not exist", HttpStatus.BAD_REQUEST);
            }
        }

        user.setRoles(userRoles);

        User response = userRepository.save(user);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registerDto.getEmail(), registerDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse(token);
        jwtAuthResponse.setUserId(response.getUserId());
        jwtAuthResponse.setUsername(response.getUsername());
        // 返回适当的响应
        return jwtAuthResponse;
    }


/**
 * 	•	UsernamePasswordAuthenticationToken 被创建，其中包含了用户提交的电子邮件和密码。
 * 	•	此 token 被传递给 AuthenticationManager 的 authenticate 方法。
 * 	•	AuthenticationManager 将找到一个合适的 AuthenticationProvider 来处理这个 token。在标准的 Spring Security 配置中，通常是 DaoAuthenticationProvider。
 * 	•	DaoAuthenticationProvider 使用配置的 UserDetailsService（在您的案例中是 CustomUserDetailsService）来加载与提供的电子邮件匹配的 UserDetails。
 * 	•	加载的 UserDetails（包含从数据库获取的密码）和 UsernamePasswordAuthenticationToken 中的密码将由 DaoAuthenticationProvider 使用配置的 PasswordEncoder（在您的案例中通过构造函数注入的 passwordEncoder）来比较。
 *
 * 3. 密码验证
 *
 * 	•	密码验证是在 DaoAuthenticationProvider 中进行的。这个提供者会自动地使用您通过构造函数传递给 CustomUserDetailsService 的 PasswordEncoder 来比较存储的密码哈希和用户提交的密码。
 * 	•	如果密码匹配，身份验证过程成功，用户被认为是经过验证的，AuthenticationManager 返回一个包含用户权限等信息的已认证 Authentication 对象。
 * 	•	如果密码不匹配，将抛出 BadCredentialsException，表示密码错误。
 *
 * */
    @Override
    public JWTAuthResponse login(LoginDto loginDto) throws ApiException{
        if (!userRepository.existsByEmail(loginDto.getEmail())) {
            throw new ApiException("Email not exists", HttpStatus.BAD_REQUEST);
        }
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // 生成JWT token
            String token = tokenProvider.generateToken(authentication);
            // 构建响应
            JWTAuthResponse jwtAuthResponse = new JWTAuthResponse(token);
            Optional<User> userOptional = userRepository.findByEmail(loginDto.getEmail());
            User user = userOptional.get();
            jwtAuthResponse.setUserId(user.getUserId());
            jwtAuthResponse.setUsername(user.getUsername());
            return jwtAuthResponse;
        }catch(AuthenticationException ex){
            throw new ApiException("Login failed", HttpStatus.BAD_REQUEST);
        }
    }




}
