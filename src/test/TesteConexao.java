package test;


import connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {

    public static void main(String[] args) {
        System.out.println("------------------------------------------------");
        System.out.println("teste...");
        System.out.println("------------------------------------------------");

        try {
            // Tenta obter a conex coma classe Factory
            Connection connection = ConnectionFactory.getConnection();

            if (connection != null) {
                System.out.println("✅ SUCESSO! Conexão realizada.");
                System.out.println("Banco de Dados: RESERVA_HOTEL");

                // Fecha a conexão para não deixar aberta
                connection.close();
            } else {
                System.out.println("❌ FALHA: A conexão retornou nula.");
            }

        } catch (RuntimeException e) {
            System.out.println("erro:");
            System.out.println("banco nao encontradp.");
            System.out.println("Motivo: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
