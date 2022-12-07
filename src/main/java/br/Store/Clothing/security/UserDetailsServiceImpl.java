package br.Store.Clothing.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.Store.Clothing.models.Cliente;
import br.Store.Clothing.repository.ClienteRepository;

public class UserDetailsServiceImpl  implements UserDetailsService {

    private ClienteRepository clienteRepository;

    public UserDetailsServiceImpl(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente u = clienteRepository.findUserByLogin(username);
        if(u == null){
            throw new UsernameNotFoundException(username);
        }
        UserDetails user = org.springframework.security.core.userdetails.User.withUsername(u.getUsername()).password(u.getPassword()).authorities("USER").build();
        return user;
    }
}