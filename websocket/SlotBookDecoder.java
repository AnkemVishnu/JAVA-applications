package com.bizmobia.bizglams.websocket;

import com.bizmobia.bizglams.models.SlotBook;
import com.google.gson.Gson;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Vishnu
 */
public class SlotBookDecoder implements Decoder.Text<SlotBook> {

    private static final Gson gson = new Gson();

    @Override
    public SlotBook decode(String s) throws DecodeException {
        return gson.fromJson(s, SlotBook.class);
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
