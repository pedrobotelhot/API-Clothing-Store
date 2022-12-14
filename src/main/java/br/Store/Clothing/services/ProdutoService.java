package br.Store.Clothing.services;

import br.Store.Clothing.models.Pedido;
import br.Store.Clothing.models.Produto;
import br.Store.Clothing.repository.PedidoRepository;
import br.Store.Clothing.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ProdutoService {

    @Autowired
    private static PedidoRepository pedidoRepository;

    @Autowired
    private static ProdutoRepository produtoRepository;



    double a = 0;
    int posicao = 0; // aramazena a posicao do item dentro do array de lista de desejos

    public static Produto compraProduto(long idProduto, long idPedido) {

        Optional<Produto> produtoExistente = produtoRepository.findById(idProduto);
        Optional<Pedido> pedidoExistente = pedidoRepository.findById(idPedido);

        if(produtoExistente.isPresent() && pedidoExistente.isPresent() && produtoExistente.get().getEstoque() == 0) {
            /* ACRESCENTA MAIS 50 PRODUTOS AO ESTOQUE */
            produtoExistente.get().setEstoque(50);

        }

        /* SE O 'PRODUTO' E 'PEDIDO' EXISTIREM, E SE O 'ESTOQUE' CONTEM PRODUTOS DISPONIVEIS ENTRA NA CONDICAO */
        if(produtoExistente.isPresent() && pedidoExistente.isPresent() && produtoExistente.get().getEstoque() >= 0 && !(pedidoExistente.get().getProdutos().isEmpty())) {

            /* ADICIONA O PRODUTO AO CARRINHO DO USUARIO */
            produtoExistente.get().getPedidos().add(pedidoExistente.get());

            System.out.println("Retorno: "+ pedidoExistente.get().getProdutos().contains(produtoExistente.get()));

            System.out.println("QTD produtos "+ pedidoExistente.get().getProdutos().size());

            /* ARMAZENA A QTD DE PRODUTOS */
            int contador = 0;

            /* ARMAZENA OS IDs DOS PRODUTOS LISTADOS DENTRO DO CARRINHO DO USUARIO */
            long[] vetor = new long[pedidoExistente.get().getProdutos().size()];

            for(int i = 0; i < pedidoExistente.get().getProdutos().size(); i++) {

                vetor[i] = pedidoExistente.get().getProdutos().get(i).getId();

                System.out.println("Posicao do vetor ["+ i +"] = "+ vetor[i]);
                System.out.println("Produto ID: "+ produtoExistente.get().getId());

                if(vetor[i] == produtoExistente.get().getId()) {
                    contador++;

                }

            }

            System.out.println("Valor Total a Pagar ATUAL "+ pedidoExistente.get().getValorTotal());

            /* RETIRA O VALOR EXISTENTE DO CARRINHO PARA PODER SER RECALCULADO */
            pedidoExistente.get().setValorTotal(pedidoExistente.get().getValorTotal() - (produtoExistente.get().getPreco() * contador));

            /* COMPENSA ACRESCENTANDO O NOVO PRODUTO AO CARRINHO ==> O ID INFORMADO */
            contador++;
            System.out.println("QTD de produto: "+ contador);

            /* INSERE O VALOR DO CONTADOR == QTD DE PRODUTOS POR ID */
            produtoExistente.get().setQtdPedidoProduto(contador);
            /* DEBITA O ESTOQUE SEMPRE QUE E INSERIDO UM PRODUTO A UM CARRINHO */
            produtoExistente.get().setEstoque(produtoExistente.get().getEstoque() - 1);

            System.out.println("Valor Total a Pagar ZERADO "+ pedidoExistente.get().getValorTotal());

            /* AJUSTA O VALOR DO CARRINHO DE UM USUARIO ESPECIFICO */
            pedidoExistente.get().setValorTotal(pedidoExistente.get().getValorTotal() + (produtoExistente.get().getPreco() * produtoExistente.get().getQtdPedidoProduto()));

            System.out.println("Valor Total a Pagar ATUALIZADO "+ pedidoExistente.get().getValorTotal());

            System.out.println("Contador: "+ contador);
            System.out.println("QTD Produtos Comprados: "+ produtoExistente.get().getQtdPedidoProduto());
            System.out.println("Valor Produto: "+ produtoExistente.get().getPreco());
            System.out.println("Valor Carrinho: "+ pedidoExistente.get().getValorTotal());

            System.out.println("IF");
            System.out.println("ID: "+ pedidoExistente.get().getId() +" | Valor a pagar: "+ pedidoExistente.get().getValorTotal());

            produtoRepository.save(produtoExistente.get());
            pedidoRepository.save(pedidoExistente.get());
            pedidoRepository.save(pedidoExistente.get()).getValorTotal();

            return produtoRepository.save(produtoExistente.get());

        }else if(produtoExistente.isPresent() && pedidoExistente.isPresent()) {
            /* ADICIONA O PRODUTO AO CARRINHO DO USUARIO */
            produtoExistente.get().getPedidos().add(pedidoExistente.get());

            /* ADICIONA UM PRODUTO AO QTD PRODUTOS DENTRO DE PRODUTO */
            produtoExistente.get().setQtdPedidoProduto(1);
            /* GERENCIA O ESTOQUE DEBITNADO UM PRODUTO DO ESTOQUE */
            produtoExistente.get().setEstoque(produtoExistente.get().getEstoque() - 1);

            /* ATUALIZA O VALOR DO CARRINHO DO USUARIO */
            pedidoExistente.get().setValorTotal(pedidoExistente.get().getValorTotal() + (produtoExistente.get().getPreco() * produtoExistente.get().getQtdPedidoProduto()));

            System.out.println("ELSE");
            System.out.println("ID: "+ pedidoExistente.get().getId() +" | Valor a pagar: "+ pedidoExistente.get().getValorTotal());

            produtoRepository.save(produtoExistente.get());
            pedidoRepository.save(pedidoExistente.get());
            pedidoRepository.save(pedidoExistente.get()).getValorTotal();

            return produtoRepository.save(produtoExistente.get());

        }

        return null;
    }

    /* DELETAR OBJETOS DO PRODUTO */
    public void deletarProduto(long idProduto, long idPedido) {

        Optional<Produto> produtoExistente = produtoRepository.findById(idProduto);
        Optional<Pedido> pedidoExistente = pedidoRepository.findById(idPedido);

        if(pedidoExistente.get().getProdutos().contains(produtoExistente.get())) {
            /* REMOVE O CARRINHO DO PRODUTO */
            produtoExistente.get().getPedidos().remove(pedidoExistente.get());

            /* GERENCIA O ESTOQUE DEBITNADO UM PRODUTO DO ESTOQUE */
            produtoExistente.get().setEstoque(produtoExistente.get().getEstoque() + 1);

            int contador = 0;

            /* ARMAZENA OS IDs DOS PRODUTOS LISTADOS DENTRO DO CARRINHO DO USUARIO */
            long[] vetor = new long[pedidoExistente.get().getProdutos().size()];

            for(int i = 0; i < pedidoExistente.get().getProdutos().size(); i++) {

                vetor[i] = pedidoExistente.get().getProdutos().get(i).getId();

                System.out.println("Posicao do vetor ["+ i +"] = "+ vetor[i]);
                System.out.println("Produto ID: "+ produtoExistente.get().getId());

                if(vetor[i] == produtoExistente.get().getId()) {
                    contador++;

                }

            }

            produtoExistente.get().setQtdPedidoProduto(contador - 1);

            /* AJUSTA O VALOR DO CARRINHO DE UM USUARIO ESPECIFICO */
            pedidoExistente.get().setValorTotal(pedidoExistente.get().getValorTotal() - produtoExistente.get().getPreco());

            a = pedidoExistente.get().getValorTotal();
            a = Math.floor(a * 100) / 100;
            pedidoExistente.get().setValorTotal(a);

            if(pedidoExistente.get().getValorTotal() < 0) {
                pedidoExistente.get().setValorTotal(0);
            }

            produtoRepository.save(produtoExistente.get());
            pedidoRepository.save(pedidoExistente.get());
            pedidoRepository.save(pedidoExistente.get()).getValorTotal();

        }

    }

}
