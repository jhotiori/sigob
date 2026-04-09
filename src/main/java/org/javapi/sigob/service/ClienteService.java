package org.javapi.sigob.service;

import org.javapi.sigob.entity.Cliente;
import org.javapi.sigob.exception.ClienteException;
import org.javapi.sigob.repository.ClienteRepository;

import java.util.List;

public class ClienteService {
    private ClienteRepository repos;

    public ClienteService() {
    }

    public ClienteService(ClienteRepository repos) {
        this.repos = repos;
    }

    public Cliente save(Cliente cliente){

        //Recuperar os dados
        var idCliente = cliente.getIdCliente();
        var nmCliente = cliente.getNmCliente();
        var nrDocumento = cliente.getNrDocumento();

        //Validar os dados
        if(nmCliente.isBlank()){
            throw new ClienteException("O nome do cliente é obrigatório!");
        }

        if(nrDocumento.isBlank()){
            throw new ClienteException("O documento do cliente é obrigatório!");
        } else if(getByDoc(nrDocumento) != null){
            throw new ClienteException("Cliente com o mesmo documento já cadastrado!");
        }

        //Cria o objeto final
        var resultCliente = new Cliente(idCliente, nmCliente, nrDocumento);

        //Valida se é CREATE ou UPDATE
        if(idCliente > 0){
            repos.update(resultCliente);
        } else {
            repos.create(resultCliente);
        }

        return resultCliente;
    }

    public Cliente getByDoc(String doc){
        if(doc.isBlank()){
            return null;
        }
        return repos.findByDoc(doc);
    }

    public Cliente getById(int id){
        if(id > 0){
            return repos.findById(id);
        }
        throw new ClienteException("Id não informado para busca!");
    }

    public List<Cliente> getAll(){
        return repos.findAll();
    }

    public List<Cliente> getByNome(String nome){
        if(nome.isBlank()){
            throw new ClienteException("Nome não informado para busca!");
        }
        return repos.findByName(nome);
    }

    public boolean exists (Cliente cliente){
        return repos.exists(cliente);
    }

    //basicamente valida se o cliente existe e depois deleta se houver registro igual
    public void delete(Cliente cliente){
        if(exists(cliente)) {
            repos.remove(cliente);
        }
    }
}
