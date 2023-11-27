package Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {

    private static final String DIRETORIO_LOG = "E:\\CryptoScan - Logs";
    private static final String EXTENSAO_ARQUIVO = ".txt";
    //private static final long TAMANHO_MAXIMO_ARQUIVO = 4 * 1024 * 1024; // 4 MB
    private static final long TAMANHO_MAXIMO_ARQUIVO = 4 * 1024;

    public void info(String mensagem) {
        gerarLog("INFO", mensagem);
    }

    public void error(String mensagem) {
        gerarLog("ERROR", mensagem);
    }

    public void success(String mensagem) {
        gerarLog("SUCCESS", mensagem);
    }

    private void gerarLog(String nivel, String mensagem) {
        LocalDateTime dataAtual = LocalDateTime.now();

        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataFormatada = formatadorData.format(dataAtual);

        DateTimeFormatter formatadorDataNomeArquivo = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dataFormatadaNomeArquivo = formatadorDataNomeArquivo.format(dataAtual);

        String nomeBaseArquivo = DIRETORIO_LOG + "\\" + dataFormatadaNomeArquivo;
        String nomeArquivo = nomeBaseArquivo + EXTENSAO_ARQUIVO;

        int contador = 1;

        while (arquivoExiste(nomeArquivo) && arquivoAtualMenor(nomeArquivo)) {
            nomeArquivo = nomeBaseArquivo + "(" + contador + ")" + EXTENSAO_ARQUIVO;
            contador++;
        }

        String mensagemCompleta = "[" + nivel + "] " + dataFormatada + " - " + mensagem;

        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo, true))) {
            writer.println(mensagemCompleta);
            System.out.println(mensagemCompleta);
        } catch (IOException e) {
            System.err.println("[ERROR] - Erro ao gerar o log: " + e.getMessage());
        }
    }

    private boolean arquivoAtualMenor(String caminhoArquivo) {
        File arquivo = new File(caminhoArquivo);
        return arquivo.length() > TAMANHO_MAXIMO_ARQUIVO;
    }

    private boolean arquivoExiste(String nomeArquivo) {
        File file = new File(nomeArquivo);
        return file.exists();
    }
}
