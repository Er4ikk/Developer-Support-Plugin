package com.unimol.connectionManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class ConnectionManager {

    private static ConnectionManager connectionManager=null;
    private final String host="localhost";
    private final String port="5000";
    private JTabbedPane tabbedPane1;

    private ConnectionManager() {

    }

    public static ConnectionManager getInstance(){
        if(connectionManager==null){
            connectionManager=new ConnectionManager();
        }

        return connectionManager;
    }

    public String creatingRequestBody(String codeFragment){
        var values = new HashMap<String, String>() {{
            put("message", codeFragment);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody;
        try {
            requestBody = objectMapper
                    .writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return  requestBody;
    }


    public String makeRequest(String codeTask,String codeFragment){
        String response="";
        String finalUrl = "http://" + host + ":" + port;
        String generateBugFixUrl = finalUrl + "/bug_fix_small";
        String generateRawAssertUrl = finalUrl + "/assertion_raw";
        String generateCommentSummaryUrl = finalUrl + "/comment_summary";
        switch (codeTask){


            case "generate bug fix":
                response=sendMultiTaskModelRequest(codeFragment, generateBugFixUrl);
                break;

            case "generate assertion":
                response=sendMultiTaskModelRequest(codeFragment, generateRawAssertUrl);
                break;

            case "generate comment summary":
                response=sendMultiTaskModelRequest(codeFragment, generateCommentSummaryUrl);
                break;
            default:
                System.out.println("Invalid task");
        }



        return response;
    }



    public String  sendMultiTaskModelRequest(String codeFragment,String url){
        String requestBodyRaw=creatingRequestBody(codeFragment);

        String requestBody= requestBodyRaw
                .replaceAll("\\\\n", "")
                .replaceAll("\\\\t", "").trim();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            JOptionPane.showMessageDialog(
                    tabbedPane1,
                    e,
                    "Exception detected",
                    JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
        return "\n  "+parseMultiTaskModelResponse(response)+"\n";
    }



    public String parseMultiTaskModelResponse(HttpResponse<String> response){
        String[] comment=response.body().split(":");

        return comment[0]
                .replace('}',' ')
                .replace(']',' ')
                .replace('[',' ')
                .replace('"',' ')
                .replace('\'',' ');
    }
    }

