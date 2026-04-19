package com.sptech.school.config_jira;

public class App {
    public static void main(String[] args) throws Exception {
        String baseUrl = "INSERIRurl";
        String email = "INSERIRemail";
        String apiToken = "INSERIRapiToken";
        Jira jira = new Jira(baseUrl, email, apiToken);

        String response = jira.createIssue(
                "KEY", // Substitua pela key do seu projeto que estará presente na URL do seu site
                // ex: "https://java-integration.atlassian.net/jira/software/projects/SCRUM/boards/1/backlog" -> a parte "SCRUM" é a key desse projeto
                "Alerta", // Nome da issue
                "Task" // Tipo da issue, pode ser "Task", "Bug", "Story", etc. Verifique os tipos disponíveis no seu projeto para usar o correto
        );

        System.out.println(response);
        // Se a requisição for bem-sucedida confira o backlog do seu projeto para ver a nova issue que foi criada

    }
}
