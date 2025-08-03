package dao;

import db.DB;
import model.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public void inserir(Categoria categoria){

        String insertSQL = "INSERT INTO categoria (nome) VALUES (?)";

        try (Connection conexao = DB.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(insertSQL)){

            stmt.setString(1, categoria.getNome());
            stmt.execute();

            System.out.println("Categoria criada com sucesso!");
        }catch (SQLException e){
            throw new RuntimeException("Falha na inserção de dados na tabela Categoria", e);
        }
    }

    public List<Categoria> listarTodas(){

        String selectSQL = "SELECT * FROM categoria";
        List<Categoria> categorias = new ArrayList<>();

        try (Connection conexao = DB.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(selectSQL)){

            try (ResultSet resultado = stmt.executeQuery()){
                while(resultado.next()){
                    categorias.add(new Categoria(resultado.getInt(1), resultado.getString(2)));
                }
            }

            return categorias;
        }catch (SQLException e){
            throw new RuntimeException("Falha ao listar categorias", e);
        }
    }

    public Categoria buscarPorId(int id){

        String selectSQL = "SELECT * FROM categoria WHERE id = ?";

        try (Connection conexao = DB.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(selectSQL)){

            stmt.setInt(1, id);
            try (ResultSet resultado = stmt.executeQuery()){
                if(resultado.next()){
                    return new Categoria(resultado.getInt(1), resultado.getString(2));
                }
            }

            return null;
        }catch (SQLException e){
            throw new RuntimeException("Falha ao buscar categoria por ID", e);
        }
    }

    public void atualizar(Categoria categoria){
        String updateSQL = "UPDATE categoria SET nome = ? WHERE id = ?";

        try (Connection conexao = DB.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(updateSQL)){

            stmt.setString(1, categoria.getNome());
            stmt.setInt(2, categoria.getId());
            stmt.execute();

            System.out.println("Categoria atualizada com sucesso!");
        }catch (SQLException e){
            throw new RuntimeException("Falha ao atualizar categoria", e);
        }
    }

    public void remover(int id){

        String deleteSQL = "DELETE FROM categoria WHERE id = ?";

        try (Connection conexao = DB.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(deleteSQL)){

            Categoria categoria = buscarPorId(id);

            if(categoria != null){
                stmt.setInt(1, id);
                stmt.execute();
                System.out.println("Categoria excluída com sucesso!");
            } else {
                System.out.println("Nenhuma categoria foi encontrada");
            }


        }catch (SQLException e){
            throw new RuntimeException("Falha ao remover categoria", e);
        }
    }

}
