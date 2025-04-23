package br.com.cdb.bancodigitaljpa.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.EnumType;
import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
public class Cliente {
	
	//Declaração de Variáveis
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Valores gerados serão 'autoincrementados'(Ex.: 1, 2, 3 ...)
	private Long id;
	
	private String cpf;
    private String nome;
    private LocalDate dataNascimento;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;
    
    @Enumerated(EnumType.STRING)
    private CategoriaCliente categoria;
    
    @OneToMany(mappedBy = "cliente")
    @JsonBackReference
    private List<Conta> contas;

    @OneToMany(mappedBy = "cliente")
    private List<Cartao> cartoes;
    
    
    // Métodos Getters e Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public CategoriaCliente getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaCliente categoria) {
		this.categoria = categoria;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

	public List<Cartao> getCartoes() {
		return cartoes;
	}

	public void setCartoes(List<Cartao> cartoes) {
		this.cartoes = cartoes;
	}



}
