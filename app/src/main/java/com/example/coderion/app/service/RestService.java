package com.example.coderion.app.service;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.example.coderion.app.model.Board;
import com.example.coderion.app.model.ExtendedBoard;
import com.example.coderion.app.model.Values;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class RestService extends AsyncTask<String, Void, ResponseEntity<?>> {

    Board board;
    byte[] imageArray;
    String operation;
    List<Long> ids;

    public RestService(String operation) {
        this.operation = operation;
    }

    public RestService(List<Long> ids, String operation) {
        this.operation = operation;
        this.ids = ids;
    }

    public RestService(Board board, byte[] imageArray, String operation) {
        this.board = board;
        this.imageArray = imageArray;
        this.operation = operation;
    }

    @Override
    protected ResponseEntity<?> doInBackground(String... strings) {

        final String url = strings[0];
        RestTemplate restTemplate = new RestTemplate();

        try {
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            HttpHeaders headers = new HttpHeaders();

            String auth = "user:user";
            String encodedAuth = Base64.encodeToString(auth.getBytes(), Base64.DEFAULT);
            String authHeader = "Basic " + new String(encodedAuth);
            headers.set("authorization", authHeader);
            headers.setContentType(MediaType.APPLICATION_JSON);

            ResponseEntity<?> response = null;

            switch (operation) {
                case Values.FIND_ALL: {
                    HttpEntity entity = new HttpEntity<>(headers);
                    response = getAllSlabs(restTemplate, url, HttpMethod.GET, entity);
                    break;
                }
                case Values.FIND_BY_ID: {
                    HttpEntity entity = new HttpEntity<>(headers);
                    response = getSlabById(restTemplate, url, HttpMethod.GET, entity);
                    break;
                }
                case Values.ADD_NEW_BOARD: {
                    ExtendedBoard extendedBoard = new ExtendedBoard(board, imageArray);
                    HttpEntity<ExtendedBoard> entity = new HttpEntity<>(extendedBoard, headers);
                    response = addPlyta(restTemplate, url, HttpMethod.POST, entity);
                    break;
                }
                case Values.DELETE_BY_ID: {
                    HttpEntity<List<Long>> entity = new HttpEntity<>(ids, headers);
                    response = deleteById(restTemplate, url, HttpMethod.POST, entity);
                    break;
                }
            }

            return response;

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            return null;
        }
    }

    public ResponseEntity<List<Board>> getAllSlabs(RestTemplate restTemplate, String url, HttpMethod method, HttpEntity entity) {
        ResponseEntity<Board[]> responseEntity = restTemplate.exchange(url, method, entity, Board[].class);
        return new ResponseEntity<>(Arrays.asList(responseEntity.getBody()), HttpStatus.OK);
    }

    public ResponseEntity<ExtendedBoard> getSlabById(RestTemplate restTemplate, String url, HttpMethod method, HttpEntity entity) {
        return restTemplate.exchange(url, method, entity, ExtendedBoard.class);
    }

    public ResponseEntity<Board> addPlyta(RestTemplate restTemplate, String url, HttpMethod method, HttpEntity entity) {
        return restTemplate.exchange(url, method, entity, Board.class);
    }

    public ResponseEntity<Board> deleteById(RestTemplate restTemplate, String url, HttpMethod method, HttpEntity entity) {
        return restTemplate.exchange(url, method, entity, Board.class);
    }
}
