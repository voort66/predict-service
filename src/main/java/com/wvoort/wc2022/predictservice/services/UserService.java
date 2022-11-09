package com.wvoort.wc2022.predictservice.services;

import com.wvoort.wc2022.predictservice.PredictionConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class UserService {

    public UserDetails getUserDetails(Authentication authentication) {
        return new CustomUserDetails(authentication);
    }


    class CustomUserDetails implements UserDetails {

        private static final String ROLE = "ROLE_";

        Authentication authentication;

        CustomUserDetails(Authentication authentication) {
            this.authentication = authentication;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            if(authentication.getName().equalsIgnoreCase(PredictionConstants.SCORE_SRV)) {
                return Collections.singleton(ROLE + PredictionConstants.READ_ALL_USERS_AUTHORITY).stream().
                        map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
            } else {
                return Collections.singleton(ROLE + PredictionConstants.RW).stream().
                        map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
            }
        }

        @Override
        public String getPassword() {
            return (String) authentication.getCredentials();
        }

        @Override
        public String getUsername() {
            return authentication.getName();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }

}
