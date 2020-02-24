package ch.noseryoung.websockets.sandbox.message;

public class Message  {

    private String from;
    private String content;

    public String getFrom() {
        return from;
    }

    public Message setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Message setContent(String content) {
        this.content = content;
        return this;
    }
}
