package com.sptech.school.config_slack;

import org.json.JSONObject;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {

        JSONObject json = new JSONObject();

        json.put("text", "É recomendado olhar a Dashboard. Foram detectadas leituras anormais.");

        Slack.sendMessage(json);
    }
}
