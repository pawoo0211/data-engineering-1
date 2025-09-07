package com.example.demo.rw;


import com.example.demo.entity.WebLog;
import net.datafaker.Faker;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class WebLogItemReader implements ItemReader<WebLog> {
    private final Faker faker = new Faker();
    private int getCount = 100;
    private int currentCount = 0;

    @Override
    public WebLog read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (currentCount <= getCount) {
            currentCount++;
            return getNewWebLog();
        }

        return null;
    }

    public WebLog getNewWebLog() {
        WebLog webLog = new WebLog();
        webLog.setIpAddress(faker.internet().ipV4Address());
        webLog.setUrl(faker.internet().url());
        webLog.setUserId(faker.idNumber().singaporeanFinBefore2000());
        webLog.setSessionId(UUID.randomUUID().toString());
        webLog.setTimeStamp(faker.date().past(7, TimeUnit.DAYS).toString());
        return webLog;
    }
}
