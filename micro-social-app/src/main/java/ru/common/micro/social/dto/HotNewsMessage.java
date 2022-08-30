package ru.common.micro.social.dto;

public class HotNewsMessage {
    private final String title;
    private final String text;
    private final String timestamp;

    public HotNewsMessage(String title, String text, String timestamp) {
        this.title = title;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getTimestamp() { return timestamp; }
}
