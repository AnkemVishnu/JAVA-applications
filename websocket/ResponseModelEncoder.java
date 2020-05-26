package com.bizmobia.bizglams.websocket;

import com.bizmobia.bizglams.response.ResponseModel;
import com.google.gson.Gson;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Vishnu
 */
public class ResponseModelEncoder implements Encoder.Text<ResponseModel> {

    private static final Gson gson = new Gson();

    @Override
    public String encode(ResponseModel response) throws EncodeException {
        return gson.toJson(response);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }
}
