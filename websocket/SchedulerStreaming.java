package com.bizmobia.bizglams.websocket;

import com.bizmobia.bizglams.models.SlotBook;
import com.bizmobia.bizglams.response.ResponseModel;
import com.bizmobia.bizglams.service.BookingsService;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Vishnu
 */
@ServerEndpoint(value = "/schedulerdata/{schedulerRequest}",
        decoders = {ResponseModelDecoder.class, SlotBookDecoder.class},
        encoders = {ResponseModelEncoder.class, SlotBookEncoder.class})
public class SchedulerStreaming {

    private Session session;
    private static Set<SchedulerStreaming> chatEndpoints = new CopyOnWriteArraySet<>();
    private static HashMap<String, Session> users = new HashMap<>();

    private BookingsService service = SpringContext.getBean(BookingsService.class);

    private static final Gson gson = new Gson();

    @OnOpen
    public void onOpen(@PathParam("schedulerRequest") String schedulerRequest, Session session) throws IOException, EncodeException {
        this.session = session;
        chatEndpoints.add(this);
        users.put(schedulerRequest, session);

        ResponseModel responseModel = service.getSchedulerData(schedulerRequest);

        broadcast(responseModel);

    }

    @OnMessage
    public void onMessage(Session session, SlotBook slot) throws IOException, EncodeException {
        ResponseModel responseModel = service.updateSlotBook(slot);

        broadcast(responseModel);
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        chatEndpoints.remove(this);
        ResponseModel message = new ResponseModel();
        message.setStatusCode(1);
        message.setMessage("Disconnected");
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println(session.getId() + " - Error");
    }

    private static void broadcast(ResponseModel message) throws IOException, EncodeException {
        chatEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote().
                            sendObject(message);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
