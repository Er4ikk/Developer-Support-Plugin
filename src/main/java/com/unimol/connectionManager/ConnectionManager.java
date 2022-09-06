package com.unimol.connectionManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class ConnectionManager {

    private static ConnectionManager connectionManager=null;
    private String host="localhost";
    private String port="5000";
    private String finalUrl="http://"+host+":"+port;
    private String generateCodeCommentUrl=finalUrl+"/code";
    private String generateBugFixUrl=finalUrl+"/bug_fix_small";


    private ConnectionManager() {

    }

    public static ConnectionManager getInstance(){
        if(connectionManager==null){
            connectionManager=new ConnectionManager();
        }

        return connectionManager;
    }

    public String creatingCodeCommentRequest(String codeFragment){
        var values = new HashMap<String, String>() {{
            put("message", codeFragment);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = null;
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
        switch (codeTask){
            case "generate comment":
                response=sendRequest(codeFragment,generateCodeCommentUrl);
                break;

            case "generate bug fix":
                response=sendModelRequest(codeFragment,generateBugFixUrl);
                break;

            case "generate assertion":
                response=sendModelRequest(codeFragment,generateCodeCommentUrl);
                break;
        }



        return response;
    }

    public String  sendRequest(String codeFragment,String url){
        String requestBodyRaw=creatingCodeCommentRequest(codeFragment);

        String requestBody= requestBodyRaw
                .replaceAll("\\\\n", "")
                .replaceAll("\\\\t", "").trim();
      ;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "\n /* "+parseResponse(response)+" */ \n";
    }

    public String  sendModelRequest(String codeFragment,String url){
        String requestBodyRaw=creatingCodeCommentRequest(codeFragment);

        String requestBody= requestBodyRaw
                .replaceAll("\\\\n", "")
                .replaceAll("\\\\t", "").trim();
        ;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "\n  "+parseModelResponse(response)+"\n";
    }

    public String parseResponse(HttpResponse<String> response){
        String[] comment=response.body().split(":");

        return comment[1]
                .replace('}',' ')
                .replace(']',' ')
                .replace('"',' ');
    }

    public String parseModelResponse(HttpResponse<String> response){
        String[] comment=response.body().split(":");

        return comment[0]
                .replace('}',' ')
                .replace(']',' ')
                .replace('"',' ')
                .replace('\'',' ');
    }
    }

