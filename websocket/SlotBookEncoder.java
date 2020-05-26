package com.bizmobia.bizglams.websocket;

import com.bizmobia.bizglams.models.SlotBook;
import com.google.gson.Gson;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Vishnu
 */
public class SlotBookEncoder implements Encoder.Text<SlotBook> {

    private static final Gson gson = new Gson();

    @Override
    public String encode(SlotBook slotBook) throws EncodeException {
        return gson.toJson(slotBook);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }

}
