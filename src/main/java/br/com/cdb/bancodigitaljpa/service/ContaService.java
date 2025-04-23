package br.com.cdb.bancodigitaljpa.service;

import br.com.cdb.bancodigitaljpa.entity.Conta;
import br.com.cdb.bancodigitaljpa.repository.ContaRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {
	
	@Autowired
    private ContaRepository contaRepository;

    public Conta salvarConta(Conta conta) {
        // Adicionar validações e regras de negócios
        return contaRepository.save(conta);
    }

    public List<Conta> listarContas() {
        return contaRepository.findAll();
    }

    public Conta buscarContaPorId(Long id) {
        Optional<Conta> contaOptional = contaRepository.findById(id);
        return contaOptional.orElse(null);
    }
    public BigDecimal consultarSaldo(Long id) {
        Conta conta = buscarContaPorId(id);
        if (conta != null) {
            return conta.getSaldo();
        }
        return null;

    }
    @Transactional // Garante que a operação seja atômica
    public boolean transferir(Long contaOrigemId, Long contaDestinoId, BigDecimal valor) {
        // Buscar as contas de origem e destino
        Conta contaOrigem = buscarContaPorId(contaOrigemId);
        Conta contaDestino = buscarContaPorId(contaDestinoId);

        // Validar se as contas existem
        if (contaOrigem == null || contaDestino == null) {
            System.out.println("Conta de origem ou destino não encontrada."); // Para debug
            return false;
        }

        // Validar se a conta de origem tem saldo suficiente
        if (contaOrigem.getSaldo().compareTo(valor) < 0) {
            System.out.println("Saldo insuficiente na conta de origem."); // Para debug
            return false;
        }

        // Realizar a transferência
        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));
        contaDestino.setSaldo(contaDestino.getSaldo().add(valor));

        // Salvar as alterações no banco de dados
        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);

        return true;
    }
    @Transactional
    public boolean depositar(Long contaId, BigDecimal valor) {
        Conta conta = buscarContaPorId(contaId);

        if (conta == null) {
            System.out.println("Erro: Conta não encontrada para depósito.");
            return false;
        }

        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Erro: Valor do depósito deve ser positivo.");
            return false;
        }

        conta.setSaldo(conta.getSaldo().add(valor));
        contaRepository.save(conta);
        return true;
    }
    @Transactional
    public boolean sacar(Long contaId, BigDecimal valor) {
        Conta conta = buscarContaPorId(contaId);

        if (conta == null) {
            System.out.println("Erro: Conta não encontrada para saque.");
            return false;
        }

        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Erro: Valor do saque deve ser positivo.");
            return false;
        }

        if (conta.getSaldo() == null || conta.getSaldo().compareTo(valor) < 0) {
            System.out.println("Erro: Saldo insuficiente para saque na conta " + contaId + ". Saldo atual: " + conta.getSaldo() + ", Valor solicitado: " + valor);
            return false;
        }

        conta.setSaldo(conta.getSaldo().subtract(valor));
        contaRepository.save(conta);
        return true;
    }
    
   
}

  

 
