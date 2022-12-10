package com.softaloy.softchaat.Model;

public class MessageModel {

    String uId, mwssage, messageId, imageUrl;
    Long timestamp;

    public MessageModel(String uId, String mwssage, Long timestamp) {
        this.uId = uId;
        this.mwssage = mwssage;
        this.timestamp = timestamp;
    }

    public MessageModel(String uId, String mwssage) {
        this.uId = uId;
        this.mwssage = mwssage;
    }

    public MessageModel() {
    }

    public String getMessageId() {
        return messageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMwssage() {
        return mwssage;
    }

    public void setMwssage(String mwssage) {
        this.mwssage = mwssage;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
