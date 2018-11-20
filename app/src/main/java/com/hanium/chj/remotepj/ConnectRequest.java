package com.hanium.chj.remotepj;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class ConnectRequest extends StringRequest {
    final static private String URL = "http://14.63.161.31/Connect.php";
    private Map<String,String> parameters;

    public ConnectRequest(String userID,String parentID, String parentNum, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("parentID", parentID);
        parameters.put("parentNum", parentNum);
    }

    public Map<String,String> getParams() {
        return parameters;
    }
}
