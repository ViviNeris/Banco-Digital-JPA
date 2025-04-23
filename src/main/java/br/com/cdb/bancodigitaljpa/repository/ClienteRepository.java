package br.com.cdb.bancodigitaljpa.repository;

import br.com.cdb.bancodigitaljpa.entity.Cliente;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
