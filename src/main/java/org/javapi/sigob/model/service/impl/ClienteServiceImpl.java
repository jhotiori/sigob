package org.javapi.sigob.model.service.impl;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.exception.SigobException;
import org.javapi.sigob.model.entity.Cliente;
import org.javapi.sigob.model.persistence.Database;
import org.javapi.sigob.model.repository.impl.ClienteRepositoryImpl;
import org.javapi.sigob.model.repository.impl.DocumentoRepositoryImpl;
import org.javapi.sigob.model.repository.impl.VendaRepositoryImpl;
import org.javapi.sigob.model.service.ClienteService;
import org.javapi.sigob.model.validation.entity.ClienteValidator;

public class ClienteServiceImpl implements ClienteService {

    /**
     * Salva um novo cliente.
     *
     * @param cliente - Cliente a ser salvo.
     */
    @Override
    public void save(Cliente cliente) {
        ClienteValidator.validate(cliente);
        Database.write(
                ClienteRepositoryImpl::new,
                repo -> repo.save(cliente)
        );
    }

    /**
     * Atualiza um cliente existente.
     *
     * @param cliente - Cliente a ser atualizado.
     */
    @Override
    public void update(Cliente cliente) {
        ClienteValidator.validate(cliente);
        Database.write(
                ClienteRepositoryImpl::new,
                repo -> repo.update(cliente)
        );
    }

    /**
     * Verifica se existe um cliente com o ID informado.
     *
     * @param id - ID do cliente.
     * @return boolean - True caso exista, false caso contrário.
     */
    @Override
    public boolean existsById(Integer id) {
        return Database.read(
                ClienteRepositoryImpl::new,
                repo -> repo.existsById(id)
        );
    }

    /**
     * Remove um cliente.
     *
     * @param cliente - Cliente a ser removido.
     */
    @Override
    public void delete(Cliente cliente) {
        if (validateDeleteCliente(cliente)) {
            int documentoId = cliente.getDocumento().getId();

            Database.write(
                    ClienteRepositoryImpl::new,
                    repo -> repo.deleteById(cliente.getId())
            );

            /*
             * Após deletar o cliente, remove também o documento associado.
             */
            if (documentoId > 0) {
                Database.write(
                        DocumentoRepositoryImpl::new,
                        repo -> repo.deleteById(documentoId)
                );
            }
        } else {
            throw new SigobException(
                    "O Cliente possuí vínculo com Vendas, não podendo ser removido!"
            );
        }
    }

    /**
     * Busca todos os clientes cadastrados.
     *
     * @return List<Cliente> - Lista de clientes encontrados.
     */
    @Override
    public List<Cliente> findAll() {
        return Database.read(
                ClienteRepositoryImpl::new,
                ClienteRepositoryImpl::findAll
        );
    }

    /**
     * Busca um cliente pelo ID.
     *
     * @param id - ID do cliente.
     * @return Optional<Cliente> - Cliente encontrado, se existir.
     */
    @Override
    public Optional<Cliente> findById(Integer id) {
        return Database.read(
                ClienteRepositoryImpl::new,
                repo -> repo.findById(id)
        );
    }

    /**
     * Busca clientes pelo nome.
     *
     * @param nome - Nome utilizado na busca.
     * @return List<Cliente> - Lista de clientes encontrados.
     */
    @Override
    public List<Cliente> findByNome(String nome) {
        ClienteValidator.validateNome(nome);
        return Database.read(
                ClienteRepositoryImpl::new,
                repo -> repo.findByNome(nome)
        );
    }

    /**
     * Busca clientes pelo documento.
     *
     * @param documento - Documento utilizado na busca.
     * @return List<Cliente> - Lista de clientes encontrados.
     */
    @Override
    public List<Cliente> findByDocumento(String documento) {
        ClienteValidator.validateDocumento(documento);
        return Database.read(
                ClienteRepositoryImpl::new,
                repo -> repo.findByDocumento(documento)
        );
    }

    /**
     * Valida se um cliente está vinculado a uma venda antes de deletar.
     *
     * @param cliente - Cliente a ser validado.
     * @return boolean - True se é possível deletar o registro de forma segura.
     */
    private boolean validateDeleteCliente(Cliente cliente) {
        return Database.read(
                VendaRepositoryImpl::new,
                repo -> repo.findByClienteId(cliente.getId()).isEmpty()
        );
    }
}
