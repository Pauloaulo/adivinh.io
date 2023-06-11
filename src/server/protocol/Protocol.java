package server.protocol;

public interface Protocol
{
    public final String SUCESSFULL_STRING = "sucessfull";
    public final String ACCOUNT_ALREADY_EXIST_STRING = "error-account-already-exist";
    public final String LOGIN_SUCESSFUL_STRING = "login-sucessfull";
    public final String ALREADY_IN_A_ROOM_STRING = "already-in-a-room";
    public final String ENTER_ROOM_STRING = "enter-room";
    public final String CREATE_ROOM_STRING = "create-room";
    public final String GET_ROOMS_STRING = "get-rooms";
    public final String FORBBIDEN_REQUEST_STRING = "forbbiden-request";
    public final String LOGOUT_STRING = "logout";
    public final String LOGIN_STRING = "login";
    public final String CONNECTION_SUCESSFULL_STRING = "connected";
    public final String EXIT_ROOM_STRING = "exit-room";
    public final String ROOM_NOT_EXISTS_STRING = "room-not-exist";
    public final String TEXT_MESSAGE_STRING = "text-message";
    public final String JOIN_CHAT_STRING = "join-chat";
    public final String CHAT_JOINED_SUCESSFULLY = "chat-joined-sucessfully";
}
