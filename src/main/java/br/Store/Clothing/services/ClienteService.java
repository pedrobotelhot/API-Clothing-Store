package br.Store.Clothing.services;

import br.Store.Clothing.controller.ClienteController;
import br.Store.Clothing.models.Cliente;
import br.Store.Clothing.models.ClienteLogin;
import br.Store.Clothing.models.Pedido;
import br.Store.Clothing.repository.ClienteRepository;
import br.Store.Clothing.repository.PedidoRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private static ClienteRepository clienteRepository;

    @Autowired
    private static PedidoRepository pedidoRepository;



    /* CADASTRAR USUARIO NO SISTEMA */
    public static Optional<Cliente> CadastrarCliente(Cliente cliente) {

        if(clienteRepository.findByEmail(cliente.getEmail()).isPresent() && cliente.getId() == 0) {
            return null;

        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String senhaEncoder = encoder.encode(cliente.getSenha());
        cliente.setSenha(senhaEncoder);

        /*GERANDO CARRINHO USUARIO*/
        /* INSTANCIA UM NOVO CARRINHO 'Pedido' */
        Pedido pedido = new Pedido();


        /* REGISTRA O USUARIO NA BASE DE DADOS */
        clienteRepository.save(cliente);

        /* ASSOCIA O USUARIO AO CARRINHO */
        pedido.setCliente(cliente);


        /* REGISTRA O CARRINHO NA BASE DE DADOS */
        pedidoRepository.save(pedido);


        return Optional.of(clienteRepository.save(cliente));

    }

    /* LOGA USUARIO NO SISTEMA */
    public static Optional<ClienteLogin> Logar(Optional<ClienteLogin> clienteLogin) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<Cliente> cliente = clienteRepository.findByEmail(clienteLogin.get().getEmail());

        /* CASO TENHA ALGUM VALOR DIGITADO, IREMOS COMPARAR OS DADOS QUE ESTAO CADASTRADOS NA BASE DE DADOS COM O QUE O USUARIO ACABOU DE DIGITAR */
        if (cliente.isPresent()) {
            /* COMPARA O QUE FOI DIGITADO NO BODY COM O QUE ESTA NO BANCO DE DADOS REFERENTE AQUELE DETERMINADO USUARIO */
            if (encoder.matches(clienteLogin.get().getSenha(), cliente.get().getSenha())) {

                /* CRIA UMA STRING COM O 'NOME_USUARIO:SENHA' */
                String auth = clienteLogin.get().getEmail() + ":" + clienteLogin.get().getSenha();

                /* CRIA UM ARRAY DE BYTE, QUE RECEBE A STRING GERADA ACIMA E FORMATA NO PADRAO 'US-ASCII' */
                byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));

                /* GERA O TOKEN PARA ACESSO DE USUARIO POR MEIO DO ARRAY DE BY GERADO */
                String authHeader = "Basic " + new String(encodedAuth);

                /* INSERE O TOKEN GERADO DENTRO DE NOSSO ATRIBUTO TOKEN */
                clienteLogin.get().setToken(authHeader);
                clienteLogin.get().setUsuario(cliente.get().getUsuario());
                clienteLogin.get().setEmail(cliente.get().getEmail());
                clienteLogin.get().setSenha(cliente.get().getSenha());
                clienteLogin.get().setFoto(cliente.get().getFoto());
                clienteLogin.get().setTipo(cliente.get().getTipo());
                clienteLogin.get().setNome(cliente.get().getNome());
                clienteLogin.get().setBairro(cliente.get().getBairro());
                clienteLogin.get().setCep(cliente.get().getCep());
                clienteLogin.get().setCidade(cliente.get().getCidade());
                clienteLogin.get().setComplemento(cliente.get().getComplemento());
                clienteLogin.get().setCpf(cliente.get().getCpf());
                clienteLogin.get().setEndereco(cliente.get().getEndereco());
                clienteLogin.get().setEstado(cliente.get().getEstado());
                clienteLogin.get().setFone(cliente.get().getFone());
                clienteLogin.get().setId(cliente.get().getId());
                clienteLogin.get().setNumero(cliente.get().getNumero());
                clienteLogin.get().setPais(cliente.get().getPais());
                clienteLogin.get().setPedidos(cliente.get().getPedidos());
				/*clienteLogin.get().getPedidos().setId(cliente.get().getPedidos().getId());
				clienteLogin.get().getListaDeDesejos().setId(cliente.get().getListaDeDesejos().getId());*/

                return clienteLogin;

            }
        }

        return null;
    }



}
