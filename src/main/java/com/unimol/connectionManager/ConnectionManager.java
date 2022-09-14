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

    /**
     * This method accept as
     * @param  codeFragment selected by the user
     * and it @return the request body as String

     */


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

    /**
     * this method takes
     *@param codeTask the string that rapresent the codeTask (Eg: "/bug_fix_small")
     * @param codeFragment that contains the selected code fragment
     * and
     * @return the suggestion from the multitask model
     */
    public String makeRequest(String codeTask,String codeFragment){
        String response="";
        String parsedResponse="";
        String finalUrl = "http://" + host + ":" + port;
        String generateBugFixUrl = finalUrl + "/bug_fix_small";
        String generateRawAssertUrl = finalUrl + "/assertion_raw";
        String generateCommentSummaryUrl = finalUrl + "/comment_summary";
        switch (codeTask){


            case "generate bug fix":
                    response=sendMultiTaskModelRequest(codeFragment, generateBugFixUrl);
                    parsedResponse= parseBugFix(response);
                break;

            case "generate assertion":
                    response=sendMultiTaskModelRequest(codeFragment, generateRawAssertUrl);
                    parsedResponse= parseAssertion(response);
                break;

            case "generate comment summary":
                    response=sendMultiTaskModelRequest(codeFragment, generateCommentSummaryUrl);
                    parsedResponse= parseComment(response);
                break;
            default:
                System.out.println("Invalid task");
        }

        return parsedResponse;
    }

    /**
     * this method takes
     * @param url the string that rapresent the full url of the codeTask
     * @param codeFragment that contains the selected code fragment and
     * @return the suggestion from the multitask model
     */

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
        return parseMultiTaskModelResponse(response) ;
    }


    /**
     * this method takes
     * @param response that is the HttpResponse
     * and replaces raw characters from the model

     */
    public String parseMultiTaskModelResponse(HttpResponse<String> response){
        String[] comment=response.body().split(":");
        return comment[0];

    }

    public String parseBugFix(String comment){
        return comment
                .substring(4,comment.length()-4)
                .replace("{","{\n")
                .replaceAll(";",";\n")
                .replaceAll("}","}\n");
    }

    public String parseAssertion(String comment){
        return comment
                .replace('[',' ')
                .replace(']',' ')
                .replace("\"","");
    }

    public String parseComment(String comment){
        return comment
                .substring(1,comment.length()-2)
                .replace('"',' ');
    }
    }

