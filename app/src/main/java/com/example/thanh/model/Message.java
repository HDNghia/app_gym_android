package com.example.thanh.model;

public class Message {
    private int _id;
    private int conversationId;
    private int senderId;
    private String attachmentId;
    private String messageContent;
    private long sentDate;
    private int status;
    private int __v;

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public void setSentDate(long sentDate) {
        this.sentDate = sentDate;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public int get_id() {
        return _id;
    }

    public int getConversationId() {
        return conversationId;
    }

    public int getSenderId() {
        return senderId;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public long getSentDate() {
        return sentDate;
    }

    public int getStatus() {
        return status;
    }

    public int get__v() {
        return __v;
    }
    @Override
    public String toString() {
        return "_id: " + _id +
                ", conversationId: " + conversationId +
                ", senderId: " + senderId +
                ", attachmentId: " + attachmentId +
                ", messageContent: " + messageContent +
                ", sentDate: " + sentDate +
                ", status: " + status +
                ", __v: " + __v;
    }
}
