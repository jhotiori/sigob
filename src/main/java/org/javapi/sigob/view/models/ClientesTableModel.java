package org.javapi.sigob.view.models;

import org.javapi.sigob.entity.Cliente;

import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * TableModel responsável pela tabela de clientes.
 */
public class ClientesTableModel extends AbstractTableModel {

    private static final String[] COLUMNS = {
        "ID",
        "Nome",
        "Documento",
        "Tipo",
        "Data Nascimento"
    };

    private static final DateTimeFormatter FORMATTER
            = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private List<Cliente> clientes;

    /**
     * Cria um novo ClientesTableModel.
     */
    public ClientesTableModel() {
        this.clientes = new ArrayList<>();
    }

    /**
     * Atualiza os clientes da tabela.
     *
     * @param clientes Os clientes
     */
    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
        fireTableDataChanged();
    }

    /**
     * Retorna um cliente pelo índice.
     *
     * @param row O índice
     * @return Cliente - O cliente
     */
    public Cliente getCliente(int row) {
        return clientes.get(row);
    }

    @Override
    public int getRowCount() {
        return clientes.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente cliente = clientes.get(rowIndex);

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
                "";
        };
    }
}
