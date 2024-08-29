package com.devsuperior.uri2990.repositories;

import com.devsuperior.uri2990.dto.EmpregadoDeptDTO;
import com.devsuperior.uri2990.projections.EmpregadoDeptProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.uri2990.entities.Empregado;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmpregadoRepository extends JpaRepository<Empregado, Long> {

    //SQL query 'NOT IN'
    @Query(nativeQuery = true, value = "SELECT empregados.cpf, empregados.enome, departamentos.dnome " +
            "FROM empregados " +
            "INNER JOIN departamentos ON empregados.dnumero = departamentos.dnumero " +
            "WHERE empregados.cpf NOT IN ( " +
            " SELECT empregados.cpf " +
            " FROM empregados " +
            " INNER JOIN trabalha ON empregados.cpf = trabalha.cpf_emp " +
            ") " +
            " ORDER BY empregados.cpf  ")
    List<EmpregadoDeptProjection> search1();

    //JPQL query
    //can use 'obj' for sub queries because each query have scoop yours
    @Query("SELECT new com.devsuperior.uri2990.dto.EmpregadoDeptDTO(obj.cpf, obj.enome, obj.departamento.dnome) " +
            "FROM Empregado obj " +
            "WHERE obj.cpf NOT IN ( " +
            " SELECT obj.cpf " +
            " FROM Empregado obj " +
            " INNER JOIN obj.projetosOndeTrabalha " +
            ") " +
            " ORDER BY obj.cpf  ")
    List<EmpregadoDeptDTO> search2();

    //SQL query 'LEFT JOIN'
    @Query(nativeQuery = true, value = "SELECT empregados.cpf, empregados.enome, departamentos.dnome " +
            "FROM empregados " +
            "INNER JOIN departamentos ON empregados.dnumero = departamentos.dnumero " +
            "LEFT JOIN trabalha ON trabalha.cpf_emp = empregados.cpf " +
            "WHERE trabalha.cpf_emp IS NULL " +
            "ORDER BY empregados.cpf")
    List<EmpregadoDeptProjection> search3();

}
