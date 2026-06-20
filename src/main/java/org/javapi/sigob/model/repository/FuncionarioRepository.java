package org.javapi.sigob.model.repository;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.model.entity.Funcionario;

public interface FuncionarioRepository extends JpaCrudRepository<Funcionario, Integer> {

    /**
     * Busca Funcionários pelo nome.
     *
     * @param nome - Nome para busca
     * @return List<Funcionario> - Funcionários encontrados
     */
    List<Funcionario> findByNome(String nome);

    /**
     * Busca um Funcionário pelo código.
     *
     * @param codigo - Código para busca
     * @return Optional<Funcionario> - Funcionário encontrado
     */
    Optional<Funcionario> findByCodigo(String codigo);

    /**
     * Busca Funcionários pelo documento.
     *
     * @param documento - Documento para busca
     * @return List<Funcionario> - Funcionários encontrados
     */
    List<Funcionario> findByDocumento(String documento);

    /**
     * Busca Funcionários pelo acesso.
     *
     * @param acesso - Acesso para busca
     * @return List<Funcionario> - Funcionários encontrados
     */
    List<Funcionario> findByAcesso(String acesso);
}
