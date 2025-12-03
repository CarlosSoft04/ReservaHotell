package main;
import dao.HotelDAO;
public class SistemaHotel {

    public static void main(String[] args) {
        System.out.println("=== INICIANDO SISTEMA ===\n");

        HotelDAO dao = new HotelDAO();


        dao.cadastrarHospede("Carlos Alberto", "111.222.333-44", "carlos@teste.com", "1985-01-01");


        dao.listarQuartosComTipos();


        dao.atualizarEmailHospede("111.222.333-44", "joao.novo@email.com");


        dao.consultarViewHospedes();


        dao.testarFunctionContagem(1);

        dao.chamarProcedureTipoQuarto("Premium", 300, 3);


        dao.excluirHospede("111.222.333-44");


    }
}