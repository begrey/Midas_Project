package com.midas.midas_project.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String midasUserId) throws UsernameNotFoundException {
        User user = userRepository.findByMidasUserId(midasUserId);
        if(user == null){
            throw new UsernameNotFoundException(midasUserId);
        }
        //userDetail을 default로 사용할때
//        return new User(person.get(0).getId(), person.get(0).getPassword(), "ROLE_USER");

        return new CustomUserDetail(user);
    }
}