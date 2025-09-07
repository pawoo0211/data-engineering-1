package com.example.demo.entity;

import lombok.Data;

@Data
public class WebLog {
    private String userId;
    private String url;
    private String ipAddress;
    private String timeStamp;
    private String sessionId;
}
