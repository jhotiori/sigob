package org.javapi.sigob.model.service;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.model.entity.Funcionario;

public interface FuncionarioService extends JpaCrudService<Funcionario, Integer> {

    /**
     * Busca funcionários pelo nome.
     *
     * @param nome - Nome utilizado na busca.
     * @return List<Funcionario> - Lista de funcionários encontrados.
     */
    List<Funcionario> findByNome(String nome);

    /**
     * Busca um funcionário pelo código.
     *
     * @param codigo - Código utilizado na busca.
     * @return Optional<Funcionario> - Funcionário encontrado, se existir.
     */
    Optional<Funcionario> findByCodigo(String codigo);

    /**
     * Busca funcionários pelo documento.
     *
     * @param documento - Documento utilizado na busca.
     * @return List<Funcionario> - Lista de funcionários encontrados.
     */
    List<Funcionario> findByDocumento(String documento);

    /**
     * Busca funcionários pelo acesso.
     *
     * @param acesso - Acesso utilizado na busca.
     * @return List<Funcionario> - Lista de funcionários encontrados.
     */
    List<Funcionario> findByAcesso(String acesso);
}
