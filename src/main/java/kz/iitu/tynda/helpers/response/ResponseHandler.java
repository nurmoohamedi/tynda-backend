package kz.iitu.tynda.helpers.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(Object message, HttpStatus status, Integer resultCode, Object responseObj){
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObj);
        map.put("resultCode", resultCode);

        return new ResponseEntity<Object>(map, status);
    }
}
