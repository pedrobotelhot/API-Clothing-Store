package br.Store.Clothing.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_produto")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(max = 50)
	private String nome;
	
	@NotNull
	@Size(max = 1000)
	private String descricao;
	
	@NotNull
	@Size(max = 50)
	private String marca;
	
	@NotNull
	@URL
	private String img;
	
	@NotNull
	private double preco;
	
	@NotNull
	private int estoque;
	
	private int qtdPedidoProduto;
	
	@ManyToOne
	@JsonIgnoreProperties("produtos")
	private Categoria categoria;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
	  name = "produto_pedido",
	  joinColumns = @JoinColumn(name = "produto_id"),
	  inverseJoinColumns = @JoinColumn(name = "pedido_id")
	)
	@JsonIgnoreProperties({"data", "valorTotal", "produtos", "cliente", "qtdProduto"})
	private List<Pedido> pedidos = new ArrayList<>();
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(
	  name = "produto_lista",
	  joinColumns = @JoinColumn(name = "produto_id"),
	  inverseJoinColumns = @JoinColumn(name = "lista_id")
	)
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public int getQtdPedidoProduto() {
		return qtdPedidoProduto;
	}

	public void setQtdPedidoProduto(int qtdPedidoProduto) {
		this.qtdPedidoProduto = qtdPedidoProduto;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

}


