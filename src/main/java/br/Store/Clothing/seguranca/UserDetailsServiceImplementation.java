package br.Store.Clothing.seguranca;

import br.Store.Clothing.models.Cliente;
import br.Store.Clothing.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        Optional<Cliente> cliente = clienteRepository.findByEmail(username);

        cliente.orElseThrow(() -> new UsernameNotFoundException(username + " not found."));

        return cliente.map(UserDetailsImplementation::new).get();
    }

}
