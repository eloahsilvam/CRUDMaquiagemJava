package com.template;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MaquiagemDAO {
    private static final Logger logger = Logger.getLogger(MaquiagemDAO.class.getName());
    public void cadastrarMaquiagem(MaquiagemDTO maquiagem) {

        // Comando SQL com ? (parâmetros)
        String sql = "INSERT INTO maquiagem (nome, marca, cor, preco, quantidade) VALUES (?, ?, ?, ?, ?)";

        try (
                Connection conn = new Conexao().conectar();  // conexão com o banco

                // PreparedStatement (stmt) = prepara o comando SQL para execiutar de forma mais segura
                // Ele recebe o SQL e permite substituir os ? com valores
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            // substituicao do ? pelos valores do objeto
            stmt.setString(1, maquiagem.getNome());       // 1º ?
            stmt.setString(2, maquiagem.getMarca());      // 2º ?
            stmt.setString(3, maquiagem.getCor());        // 3º ?
            stmt.setDouble(4, maquiagem.getPreco());      // 4º ?
            stmt.setInt(5, maquiagem.getQuantidade());    // 5º ?

            // insere
            stmt.executeUpdate();

            logger.log(Level.INFO, "Maquiagem cadastrada com sucesso");
        } catch (SQLException e) {
            logger.log(Level.SEVERE,"Erro ao cadastrar maquiagem", e);
        }
    }


    public List<MaquiagemDTO> listar() {

        List<MaquiagemDTO> lista = new ArrayList<>();

        String sql = "SELECT * FROM maquiagem";

        try (
                Connection conn = new Conexao().conectar();
                // Prepara o SELECT
                PreparedStatement stmt = conn.prepareStatement(sql);
                // Executa e retorna os dados
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {
                MaquiagemDTO maquiagem = new MaquiagemDTO();
                // Pega dados do banco e coloca no objeto
                maquiagem.setId(rs.getInt("id"));
                maquiagem.setNome(rs.getString("nome"));
                maquiagem.setMarca(rs.getString("marca"));
                maquiagem.setCor(rs.getString("cor"));
                maquiagem.setPreco(rs.getDouble("preco"));
                maquiagem.setQuantidade(rs.getInt("quantidade"));

                lista.add(maquiagem);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE,"Erro ao listar maquiagem", e);        }
        return lista;
    }

    public void atualizar(MaquiagemDTO maquiagem) {

        // SQL com parâmetros
        String sql = "UPDATE maquiagem SET nome=?, marca=?, cor=?, preco=?, quantidade=? WHERE id=?";

        try (
                Connection conn = new Conexao().conectar();

                // stmt prepara o UPDATE
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            // Substitui os ? pelos valores
            stmt.setString(1, maquiagem.getNome());
            stmt.setString(2, maquiagem.getMarca());
            stmt.setString(3, maquiagem.getCor());
            stmt.setDouble(4, maquiagem.getPreco());
            stmt.setInt(5, maquiagem.getQuantidade());
            stmt.setInt(6, maquiagem.getId());

            // Executa atualização
            stmt.executeUpdate();

            logger.log(Level.INFO, "Maquiagem atualizada com sucesso");
        } catch (SQLException e) {
            logger.log(Level.SEVERE,"Erro ao atualizar maquiagem", e);        }
    }

    public void excluir(int id) {

        String sql = "DELETE FROM maquiagem WHERE id=?";

        try (
                Connection conn = new Conexao().conectar();

                // stmt prepara o DELETE
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

            logger.log(Level.INFO, "Maquiagem excluida com sucesso");
        } catch (SQLException e) {
            logger.log(Level.SEVERE,"Erro ao excluir maquiagem", e);        }
    }
}



