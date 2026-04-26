package com.sptech.school;

import com.sptech.school.config_jira.Jira;
import com.sptech.school.config_slack.Slack;
import org.json.JSONObject;

import java.util.Random;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println(getTimestamp() + " Inicializando Geração de alertas");
        Thread.sleep(3000);

        while (true) {

            JSONObject json = new JSONObject();
            JSONObject jsonMsg = new JSONObject();

            String[] servers = {"1", "2", "3","4", "5"};
            String server = servers[gerarRandom(5)];

            String[] componentes = {"CPU", "RAM", "DISCO"};
            String componente = componentes[gerarRandom(3)];

            String data = getTimestamp();

            String[] valores = {"75%","80%","85%","90%","95%","100%"};
            String val = valores[gerarRandom(6)];


            json.put("componente", componente);
            json.put("data", data);
            json.put("valor", val);
            json.put("servidor", server);

            jsonMsg.put("text", ":warning:ALERTA:warning:: \n" +
                    "No servidor " + json.get("servidor") +
                    " o componente " + json.get("componente") +
                    " teve uma leitura de " + json.get("valor") + " na data " +
                    json.get("data"));

            Slack.sendMessage(jsonMsg);


            String baseUrl = env.BASEURL.getValor();
            String email = env.EMAIL.getValor();
            String apiToken = env.APITOKEN.getValor();
            Jira jira = new Jira(baseUrl, email, apiToken);

            String response = jira.createIssue(
                    "KAN",
                    "Alerta: " + json.get("componente") +
                            " com uma leitura de " + json.get("valor") +
                            " no servidor "+ json.get("servidor"),
                    "Task"
            );

            System.out.println(response);
            Thread.sleep(30000);
        }
    }

    // Pegar data e hora formatada [yyyy-MM-dd HH:mm:ss]:
    public static String getTimestamp() {
        LocalDateTime now = LocalDateTime.now();

        String padrao = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatarData = DateTimeFormatter.ofPattern(padrao);

        String timestamp = now.format(formatarData);

        String dataFormatada = "[" + timestamp + "]";
        return dataFormatada;
    }

    public static int gerarRandom(int length){
        Random random = new Random();

        int randomInt = random.nextInt(length);
        return randomInt;
    }

    public static void gerarMonitoramento() {
        Random r = new Random();
        // pegar a data atual
        String data = LocalDateTime.now().toString();

        // sortear um numero de 0 a 100 pra porcentagem de cpu
        int cpuPercent = r.nextInt(101);

        // memoria total com valor mocado
        int memTotal = 16;

        // vai sortear um numero de 0 a 16 (que foi o valor mocado) pra ver o quanto de memoria esta sendo usada
        int memUsed = r.nextInt(17);

        // calcular o quanto de memoria esta livre, subtraindo o numero de memoria total e quanto que esta sendo usada
        int memFree = memTotal - memUsed;

        // calcular o percentual de memoria usada
        double memPercent = ((double) memUsed / memTotal) * 100;

        // valor mocado do total de disco (em gb)
        int discoTotal = 500;

        // sorteio de 0 a 500 do tanto de disco que esta sendo usado
        int discoUsed = r.nextInt(501);

        // calcular o tanto de disco livre, subtraindo o disco total com o disco que ta sendo usado
        int discoFree = discoTotal - discoUsed;

        // chamada dos metodos, o que vai levar os dados mocados para os metodos
        verificarCPU(data, cpuPercent);
        verificarMemoria(data, memTotal, memFree, memUsed, memPercent);
        verificarDisco(data, discoTotal, discoUsed, discoFree);
    }


    // para ficar claro de onde saiu os parametros dos metodos,
    // eles sao os dados que foram mocados na chamada dos metodos no gerarMonitoramento()

    public static void verificarCPU(String data, double cpu) {
        String level;
        String mensagem;

        if (cpu > 90) {
            level = "FATAL";
            mensagem = " - Superaquecimento iminente!";
        } else if (cpu > 75) {
            level = "WARN";
            mensagem = " - Carga elevada detectada.";
        } else {
            level = "INFO";
            mensagem = " - Operação normal.";
        }

        System.out.println("[" + level + "] " + data + " | CPU(%): " + String.format("%.2f", cpu) + mensagem);
    }

    public static void verificarMemoria(String data, double total, double free, double used, double percent) {
        String level;
        String mensagem;

        if (percent > 90) {
            level = "ERROR";
            mensagem = " - Memória insuficiente para novos processos!";
        } else if (percent > 70) {
            level = "WARN";
            mensagem = " - Consumo acima do esperado.";
        } else {
            level = "INFO";
            mensagem = " - Uso estável.";
        }

        System.out.print("[" + level + "] " + data);
        System.out.print(" | Mémoria-total: " + total + "GB | Mémoria-livre: " + String.format("%.2f", free) + "GB");
        System.out.print(" | Mémoria-used: " + String.format("%.2f", used) + "GB | Mémoria(%): " + String.format("%.2f", percent));
        System.out.println(mensagem);
    }

    public static void verificarDisco(String data, double total, double used, double free) {
        double percentUso = ((double) used / total) * 100;
        String level;
        String mensagem;

        if (percentUso > 95) {
            level = "FATAL";
            mensagem = " - Unidade de armazenamento cheia!";
        } else if (percentUso > 80) {
            level = "WARN";
            mensagem = " - Pouco espaço disponível.";
        } else {
            level = "INFO";
            mensagem = " - Espaço suficiente.";
        }

        System.out.print("[" + level + "] " + data);
        System.out.print(" | Disco-total: " + total + "GB | Disco-used: " + String.format("%.2f", used) + "GB");
        System.out.print(" | Disco-free: " + String.format("%.2f", free) + "GB");
        System.out.println(mensagem);
    }
}