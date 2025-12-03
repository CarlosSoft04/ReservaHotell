package dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;


public class HotelDAO {

    // --- 4.2.1 INSERÇÃO DE DADOS ---
    public void cadastrarHospede(String nome, String cpf, String email, String dataNasc) {
        String sql = "INSERT INTO HOSPEDE (nome_hospede, cpf, email, data_nasc) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, cpf);
            stmt.setString(3, email);
            stmt.setDate(4, Date.valueOf(dataNasc)); // Formato YYYY-MM-DD

            stmt.execute();
            System.out.println(" hospede: '" + nome + "' cadastrado com sucesso no sistema.");

        } catch (SQLException e) {
            System.out.println(" Erro ao cadastra: " + e.getMessage());
        }
    }

    // --- 4.2.3 CONSULTA COM JOIN ---
    public void listarQuartosComTipos() {
        String sql = "SELECT q.numero_quarto, q.andar, t.descricao, t.valor_diaria " +
                "FROM QUARTO q " +
                "INNER JOIN TIPO_QUARTO t ON q.fk_tipo_quarto_id = t.id_tipo";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n===view de quatos e tipos ===");
            while (rs.next()) {
                System.out.printf("  Quarto: %s | Andar: %d | Tipo: %s | Valor Diária: R$ %.2f\n",
                        rs.getString("numero_quarto"),
                        rs.getInt("andar"),
                        rs.getString("descricao"),
                        rs.getDouble("valor_diaria"));
            }


        } catch (SQLException e) {
            System.out.println(" Erro ao gerar view de quartos.");
            e.printStackTrace();
        }
    }

    // --- 4.2.4 ALTERAÇÃO DE DADOS (UPDATE) ---
    public void atualizarEmailHospede(String cpf, String novoEmail) {
        String sql = "UPDATE HOSPEDE SET email = ? WHERE cpf = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoEmail);
            stmt.setString(2, cpf);

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println(" E-mail do hospede (CPF: " + cpf + ") att para: " + novoEmail);
            } else {
                System.out.println("hospede nao encontrado para att.");
            }

        } catch (SQLException e) {
            System.out.println(" Erro ao atualizar e-mail.");
            e.printStackTrace();
        }
    }

    // --- 4.2.2 EXCLUSÃO DE DADOS (DELETE) ---
    public void excluirHospede(String cpf) {
        String sql = "DELETE FROM HOSPEDE WHERE cpf = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println(" hospede (CPF: " + cpf + ") removido!.");
            } else {
                System.out.println(" hospede nao removido.");
            }

        } catch (SQLException e) {
            System.out.println(" Erro ao excluir hospede.");
            System.out.println(". " + e.getMessage());
        }
    }

    // --- 4.2.5 USO DE VIEW ---
    public void consultarViewHospedes() {
        // A VIEW vw_hospede usa exatamente as colunas da tabela HOSPEDE
        String sql = "SELECT * FROM vw_hospede";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\nLISTA DE HÓSPEDES (VIA VIEW)");
            while (rs.next()) {
                // Os nomes das colunas são os mesmos da tabela HOSPEDE, pois a View é um espelho.
                System.out.println("  Nome: " + rs.getString("nome_hospede") + " | CPF: " + rs.getString("cpf"));
            }
            System.out.println("------------------------------------------");

        } catch (SQLException e) {
            System.out.println("erro ao ver view.");
            e.printStackTrace();
        }
    }


    public void testarFunctionContagem(int idHotel) {

        String sql = "SELECT fn_contar_quartos(?) as total";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idHotel);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("\nO Hotel ID " + idHotel + " possui um total de " + rs.getInt("total") + " quartos cadastrados.");
            }

        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }


    public void chamarProcedureTipoQuarto(String desc, double valor, int cap) {

        String sql = "CALL sp_inserir_tipo(?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, desc);
            stmt.setDouble(2, valor);
            stmt.setInt(3, cap);

            stmt.execute();
            System.out.println(" tipo de quarto ('" + desc + "') criado.");

        } catch (SQLException e) {
            System.out.println("erro " + e.getMessage());
        }
    }
}