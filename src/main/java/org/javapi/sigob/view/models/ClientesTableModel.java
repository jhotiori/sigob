package org.javapi.sigob.view.models;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.javapi.sigob.entity.Cliente;
import org.javapi.sigob.view.base.BaseTableModel;

/**
 * Modelo de tabela de clientes.
 */
public class ClientesTableModel extends BaseTableModel<Cliente> {

    /**
     * Colunas da tabela.
     */
    private static final String[] COLUMNS = {
        "ID",
        "Nome",
        "Documento",
        "Tipo",
        "Data Nascimento"
    };

    /**
     * Formatter de datas.
     */
    private static final DateTimeFormatter FORMATTER
            = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Define clientes da tabela.
     *
     * @param clientes - Lista de clientes
     */
    public void setClientes(
            List<Cliente> clientes
    ) {
        setRows(clientes);
    }

    /**
     * Retorna cliente da linha.
     *
     * @param row - Índice da linha
     * @return Cliente - Cliente encontrado ou null
     */
    public Cliente getCliente(
            int row
    ) {
        return getRow(row);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String[] columns() {
        return COLUMNS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object getValueAt(
            Cliente cliente,
            int columnIndex
    ) {
        return switch (columnIndex) {
            case 0 ->
                cliente.getId();

            case 1 ->
                cliente.getNome();

            case 2 ->
                cliente.getDocumento() != null
                ? cliente.getDocumento().getDocumento()
                : "-";

            case 3 ->
                cliente.getDocumento() != null
                ? cliente.getDocumento().getTipo()
                : "-";

            case 4 ->
                cliente.getDataNascimento() != null
                ? cliente.getDataNascimento().format(FORMATTER)
                : "-";

            default ->
                null;
        };
    }

}
