package dao;

import db.DB;
import model.Categoria;
import model.Despesa;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DespesaDAO {

    public void inserir(Despesa despesa){
        String insertSQL = "INSERT INTO despesa (descricao, valor, data, categoria_id)" +
                "VALUES (?, ?, ?, ?)";

        try (Connection conexao = DB.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(insertSQL)){

            stmt.setString(1, despesa.getDescricao());
            stmt.setBigDecimal(2, despesa.getValor());
            stmt.setDate(3, java.sql.Date.valueOf(despesa.getData()));
            stmt.setInt(4, despesa.getCategoria().getId());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("\n✅ Despesa criada com sucesso!");
            } else {
                System.out.println("\n❌ Nenhuma despesa criada.");
            }

        }catch (SQLException e){
            throw new RuntimeException("\n❌ Falha na inserção de dados na tabela Despesa", e);
        }
    }

    public List<Despesa> listarTodas(){
        String selectSQL = "SELECT d.id AS despesa_id, d.descricao, d.valor, d.data, " +
                "c.id AS categoria_id, c.nome AS categoria_nome " +
                "FROM despesa d INNER JOIN categoria c ON d.categoria_id = c.id ORDER BY data ASC";

        List<Despesa> despesas = new ArrayList<>();

        try (Connection conexao = DB.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(selectSQL)){

            try (ResultSet resultado = stmt.executeQuery()){
                while(resultado.next()){
                    int despesaID = resultado.getInt("despesa_id");
                    String despesaDescricao = resultado.getString("descricao");
                    BigDecimal despesaValor = resultado.getBigDecimal("valor");
                    LocalDate despesaData = resultado.getDate("data").toLocalDate();
                    int despesaCategoriaID = resultado.getInt("categoria_id");
                    String categoriaNome = resultado.getString("categoria_nome");

                    Categoria categoria = new Categoria(despesaCategoriaID, categoriaNome);
                    Despesa despesa = new Despesa(despesaID, despesaDescricao,
                            despesaValor, despesaData, categoria);

                    despesas.add(despesa);
                }
            }
            return despesas;
        }catch (SQLException e){
            throw new RuntimeException("\n❌ Falha ao listar despesas", e);
        }
    }

    public List<Despesa> listarPorCategoria(int id){
        String selectSQL = "SELECT * FROM despesa WHERE categoria_id = ?";
        List<Despesa> despesas = new ArrayList<>();

        try (Connection conexao = DB.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(selectSQL)){

            stmt.setInt(1, id);
            try (ResultSet resultado = stmt.executeQuery()){
                while(resultado.next()){
                    despesas.add(buscarPorId(resultado.getInt("id")));
                }
            }
        }catch (SQLException e){
            throw new RuntimeException("❌ Erro ao buscar despesas por categoria", e);
        }

        return despesas;
    }

    public Despesa buscarPorId(int id){
        String selectSQL = "SELECT * FROM despesa WHERE id = ?";
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        try (Connection conexao = DB.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(selectSQL)){

            stmt.setInt(1, id);
            try (ResultSet resultado = stmt.executeQuery()){
                if(resultado.next()){
                    Categoria categoria = categoriaDAO.buscarPorId(resultado.getInt("categoria_id"));
                    Despesa despesa = new Despesa(resultado.getInt("id"), resultado.getString("descricao"), resultado.getBigDecimal("valor"), resultado.getDate("data").toLocalDate(), categoria);
                    return despesa;
                }
            }
            return null;
        }catch (SQLException e){
            throw new RuntimeException("\n❌ Falha ao buscar despesa por ID", e);
        }
    }

    public void atualizar(Despesa despesa, int id){
        String updateSQL = "UPDATE despesa SET descricao = ?, valor = ?, data = ?, categoria_id = ? WHERE id = ?";

        try (Connection conexao = DB.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(updateSQL)){

            stmt.setString(1, despesa.getDescricao());
            stmt.setBigDecimal(2, despesa.getValor());
            stmt.setDate(3, java.sql.Date.valueOf(despesa.getData()));
            stmt.setInt(4, despesa.getCategoria().getId());
            stmt.setInt(5, id);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("\n✅ Despesa atualizada com sucesso!");
            } else {
                System.out.println("\n❌ Nenhuma despesa atualizada.");
            }

        }catch (SQLException e){
            throw new RuntimeException("\n❌ Falha ao atualizar despesa", e);
        }
    }

    public void remover(int id){
        String removeSQL = "DELETE FROM despesa WHERE id = ?";

        try (Connection conexao = DB.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(removeSQL)){

            stmt.setInt(1, id);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("\n✅ Despesa removida com sucesso!");
            } else {
                System.out.println("\n❌ Nenhuma despesa removida.");
            }

        }catch (SQLException e){
            throw new RuntimeException("\n❌ Falha ao remover despesa", e);
        }
    }

    public List<Despesa> filtrarPorPeriodo(LocalDate inicio, LocalDate fim){
        String selectSQL = "SELECT * FROM despesa WHERE data >= ? AND data <= ? ORDER BY data ASC";
        List<Despesa> despesas = new ArrayList<>();
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        try (Connection conexao = DB.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(selectSQL)){

            stmt.setDate(1, java.sql.Date.valueOf(inicio));
            stmt.setDate(2, java.sql.Date.valueOf(fim));

            try (ResultSet resultado = stmt.executeQuery()){
                while(resultado.next()){
                    int despesaID = resultado.getInt("id");
                    String despesaDescricao = resultado.getString("descricao");
                    BigDecimal despesaValor = resultado.getBigDecimal("valor");
                    LocalDate despesaData = resultado.getDate("data").toLocalDate();
                    int despesaCategoriaID = resultado.getInt("categoria_id");

                    Categoria categoria = categoriaDAO.buscarPorId(despesaCategoriaID);
                    Despesa despesa = new Despesa(despesaID, despesaDescricao, despesaValor, despesaData, categoria);
                    despesas.add(despesa);
                }
            }
            return despesas;
        }catch (SQLException e){
            throw new RuntimeException("\n❌ Falha ao filtrar despesas por período", e);
        }
    }

    public BigDecimal totalMensal(int mes, int ano){
        String sumSQL = "SELECT SUM(valor) FROM despesa " +
                "WHERE MONTH(data) = ? AND YEAR(data) = ?";

        BigDecimal total = new BigDecimal(0);

        try (Connection conexao = DB.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sumSQL)){

            stmt.setInt(1, mes);
            stmt.setInt(2, ano);

            try (ResultSet resultado = stmt.executeQuery()){
                if(resultado.next()){
                    BigDecimal soma = resultado.getBigDecimal(1);
                    if(soma != null){
                        total = soma;
                    }
                }
            }
            return total;
        }catch (SQLException e){
            throw new RuntimeException("\n❌ Falha ao calcular despesa", e);
        }
    }

}
