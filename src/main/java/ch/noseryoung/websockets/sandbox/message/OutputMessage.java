package ch.noseryoung.websockets.sandbox.message;

public class OutputMessage {

    private String from;
    private String content;
    private String time;

    public OutputMessage(String from, String content, String time) {
        this.from = from;
        this.content = content;
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public OutputMessage setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getContent() {
        return content;
    }

    public OutputMessage setContent(String content) {
        this.content = content;
        return this;
    }

    public String getTime() {
        return time;
    }

    public OutputMessage setTime(String time) {
        this.time = time;
        return this;
    }
}
