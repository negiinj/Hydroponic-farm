package com.example.hydroponic_farm.ui.notifications;

public class Alarm {
    public void setId(String id) {
        this.id = id;
    }

    public void setAck(boolean ack) {
        isAck = ack;
    }

    public void setCleared(boolean cleared) {
        isCleared = cleared;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    private String id;
    private boolean isAck;
    private boolean isCleared;
    private String type;
    private String severity;

    public String getId() {
        return id;
    }

    public boolean isAck() {
        return isAck;
    }

    public boolean isCleared() {
        return isCleared;
    }

    public String getType() {
        return type;
    }

    public String getSeverity() {
        return severity;
    }

    public String getReason() {
        return reason;
    }

    private String reason;

    public Alarm() {
    }

}
