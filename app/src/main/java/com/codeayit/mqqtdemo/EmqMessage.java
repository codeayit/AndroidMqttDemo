package com.codeayit.mqqtdemo;

public class EmqMessage {

    /**
     * topic : check_online_server
     * sn : 设备唯一标示
     * time : 111111111
     * data :
     */

    private String topic;
    private String sn;
    private long time;
    private String data;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
