package com.example.certifinderexamen.filter;

import com.example.certifinderexamen.model.Company;
import com.example.certifinderexamen.repository.CertuserRepository;
import com.example.certifinderexamen.repository.CompanyRepository;
import com.example.certifinderexamen.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {


    @Autowired
    private CertuserRepository certuserRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException{

            if (request.getCookies() == null) {
                chain.doFilter(request, response);
                return;
            }
            // Get authorization header and validate
            Optional<Cookie> jwtOpt = Arrays.stream(request.getCookies())
                    .filter(cookie -> "jwt".equals(cookie.getName()))
                    .findAny();

            if (jwtOpt.isEmpty()) {
                chain.doFilter(request, response);
                return;
            }

            String token = jwtOpt.get().getValue();
                        UserDetails userDetails = companyRepository
                    .findByUsername(jwtUtil.getUsernameFromToken(token))
                    .orElse(null);

            // Get jwt token and validate
            if (!jwtUtil.validateToken(token, userDetails)) {
                chain.doFilter(request, response);
                return;
            }

            UsernamePasswordAuthenticationToken
                    authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null,
                    userDetails == null ?
                            List.of() : userDetails.getAuthorities()
            );

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            // this is where the authentication magic happens and the user is now valid!
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);

        }



//        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
//            if(!StringUtils.hasText(header) || (StringUtils.hasText(header) && !header.startsWith("Bearer "))){
//                chain.doFilter(request, response);
//                return;
//            }
//
//            final String token = header.split(" ")[1].trim();
//
//            UserDetails userDetails = companyRepository
//                    .findByUsername(jwtUtil.getUsernameFromToken(token))
//                    .orElse(null);
//
//
//            if(!jwtUtil.validateToken(token, userDetails)){
//                chain.doFilter(request, response);
//                return;
//            }
//        UsernamePasswordAuthenticationToken
//            authentication = new UsernamePasswordAuthenticationToken(
//                    userDetails, null,
//                    userDetails == null?
//                            List.of() : userDetails.getAuthorities()
//            );
//
//            authentication.setDetails(
//                    new WebAuthenticationDetailsSource().buildDetails(request)
//            );
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            chain.doFilter(request,response);
//
//    }
}