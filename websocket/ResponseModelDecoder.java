package com.bizmobia.bizglams.websocket;

import com.bizmobia.bizglams.response.ResponseModel;
import com.google.gson.Gson;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Vishnu
 */
public class ResponseModelDecoder implements Decoder.Text<ResponseModel> {

    private static final Gson gson = new Gson();

    @Override
    public ResponseModel decode(String s) throws DecodeException {
        return gson.fromJson(s, ResponseModel.class);
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }
}
