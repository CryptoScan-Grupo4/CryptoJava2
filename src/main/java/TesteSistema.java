import Computador.Computador;
import Computador.Setup;
import Conexao.Conexao;
import Medida.Medida;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.sistema.Sistema;
import login.Funcionario;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class TesteSistema {
    public static void main(String[] args) {
        Timer timer = new Timer();
        Looca looca = new Looca();
        Conexao conexao = new Conexao();
        Sistema sistema = looca.getSistema();
        JdbcTemplate sql = conexao.getConexaoDoBanco();
        Scanner leitor = new Scanner(System.in);
        Scanner leitorLogin = new Scanner(System.in);
        Integer opcaoEscolhida;
        String emailFuncionario;
        String senha;
        List<Computador> listaLoginFuncionario;

        System.out.println("""
                |-------------------------------|
                |    Bem vindo ao CryptoScan    |
                |-------------------------------|
                """);
        do {
            System.out.println("""
                    |-------------------------------|
                    |1 - Realizar login no sistema  |
                    |0 - Sair                       |
                    |-------------------------------|
                    """);
            opcaoEscolhida = leitor.nextInt();

            switch (opcaoEscolhida){
                case 1:
                    System.out.println("""
                    |-------------------------------|
                    |Informe seu email:             |
                    |-------------------------------|
                    """);
                    emailFuncionario = leitorLogin.nextLine();

                    System.out.println("""
                    |-------------------------------|
                    |Informe sua senha:             |
                    |-------------------------------|
                    """);
                    senha = leitorLogin.nextLine();

                     listaLoginFuncionario = sql.query("SELECT * FROM computador WHERE (SELECT idFuncionario FROM Funcionario WHERE emailFuncionario = ? AND senha = ?)",
                             new BeanPropertyRowMapper<>(Computador.class),emailFuncionario, senha);

                     if (listaLoginFuncionario.size() == 0){
                         System.out.println("""
                                 |------------------------------------------------------|
                                 |               Nenhuma conta encontrada               |
                                 |------------------------------------------------------|
                                 |Rever os dados informados ou Fazer cadastro no site   |
                                 |------------------------------------------------------|
                                 """);
                     }else{

                                 Scanner leitorSerial = new Scanner(System.in);
                                 Integer serialMaquina;
                                 System.out.println("""
                                 |-------------------------------------|
                                 |Informe o serial da máquina:         |
                                 |-------------------------------------|
                                 """);
                                 serialMaquina = leitorSerial.nextInt();

                                 List<Setup> codigoComputadores = sql.query("SELECT idSetup, fkComputador FROM Setup WHERE(SELECT serialComputador FROM Computador WHERE serialComputador = ?)" ,
                                    new BeanPropertyRowMapper<>(Setup.class), serialMaquina);

                                 if (codigoComputadores.size() == 0){
                                     System.out.println("Computador não existe");
                                 }else {
                                     Scanner leitorOpcaoSetup = new Scanner(System.in);
                                     Integer idSetup;
                                     System.out.println(""" 
                                     |-------------------------------------|
                                     |          Máquina acessada           |
                                     |-------------------------------------|
                                     |Informe o id do setup:               |
                                     |-------------------------------------|
                                     """);
                                     idSetup = leitorOpcaoSetup.nextInt();

                                     List<Medida> setupsDoBanco = sql.query("SELECT * FROM Setup WHERE idSetup = ? ",
                                             new BeanPropertyRowMapper<>(Medida.class), idSetup);


                                     if (setupsDoBanco.size() == 0){
                                         System.out.println("Setup não existe");
                                     }else {
                                         Scanner leitorOpcaoDados = new Scanner(System.in);
                                         Integer opcaoDados;
                                         System.out.println("""
                                            |---------------------------------------------------|
                                            |                  Setup acessado                   |
                                            |---------------------------------------------------|
                                            """);

                                         do {
                                             System.out.println("""
                                            |---------------------------------------------------|
                                            |1 - Iniciar leitura em tempo real                  |
                                            |2 - Visualização do histórico                      |
                                            |3 - Sair                                           |
                                            |0 - Parar monitoramento                            |
                                            |---------------------------------------------------|
                                            """);
                                             opcaoDados = leitorOpcaoDados.nextInt();

                                             switch (opcaoDados){
                                                 case 1:

                                                     timer.schedule(new TimerTask() {
                                                         @Override
                                                         public void run() {
                                                             Integer usoProcessador = (looca.getProcessador().getUso()).intValue();
                                                             Long usoMemoria = (looca.getMemoria().getEmUso());
                                                             Long limiteMemoria = (looca.getMemoria().getTotal());

                                                             Double porcentagemMemoria = Double.valueOf ((usoMemoria * 100) / limiteMemoria);

                                                             Double valorDisponivelDisco = Double.valueOf(looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel() / 8e+9);
                                                             Double valorTotalDisco = Double.valueOf(looca.getGrupoDeDiscos().getVolumes().get(0).getTotal() / 8e+9);

                                                             BigDecimal valorDisponivelBigDecimal = new BigDecimal(valorDisponivelDisco);
                                                             valorDisponivelBigDecimal = valorDisponivelBigDecimal.setScale(2 , RoundingMode.HALF_UP);
                                                             Double valorDisponivelArrendondado = valorDisponivelBigDecimal.doubleValue();

                                                             BigDecimal valorTotalBigDecimal = new BigDecimal(valorTotalDisco);
                                                             valorTotalBigDecimal = valorTotalBigDecimal.setScale(2 , RoundingMode.HALF_UP);
                                                             Double valorTotalArrendondado = valorTotalBigDecimal.doubleValue();

                                                             Double discoOcupado = (valorTotalArrendondado - valorDisponivelArrendondado);
                                                             Double usoDisco = ((discoOcupado * 100) / valorTotalArrendondado);

                                                             sql.update("INSERT INTO Medida (medida, fkComponente, fkSetup) VALUES (?, ?, ?)", usoProcessador, 1 , idSetup);
                                                             sql.update("INSERT INTO Medida (medida, fkComponente, fkSetup) VALUES (?, ?, ?)", porcentagemMemoria, 2, idSetup);
                                                             sql.update("INSERT INTO Medida (medida, fkComponente, fkSetup) VALUES (?, ?, ?)", porcentagemMemoria, 3, idSetup);
                                                             sql.update("INSERT INTO Medida (medida, fkComponente, fkSetup) VALUES (?, ?, ?)", usoDisco, 4, idSetup);
                                                         }
                                                     }, 5000 , 2000);

                                                     break;

                                                 case 2:
                                                     List<Medida> medidasInseridas = sql.query("SELECT tipoComponente, medida, idSetup AS fkSetup, DATE_FORMAT(dataHoraMedida, '%d %c %Y %T') AS 'dataHoraMedida' FROM Medida join setup on idSetup = fkSetup join Componente on idComponente = fkComponente where idSetup = ?;" ,
                                                             new BeanPropertyRowMapper<>(Medida.class), idSetup);

                                                     for (Medida medida : medidasInseridas) {
                                                         System.out.println(medidasInseridas);
                                                     }
                                                     break;
                                             }

                                         }while (opcaoDados != 3);


                                     }

                                 }

                     }
                break;

                case 0: {
                    System.out.println("Parando o sistema");

                    System.exit(0);

                    break;
                }
            }

        }while (opcaoEscolhida != 0);

    }

}
