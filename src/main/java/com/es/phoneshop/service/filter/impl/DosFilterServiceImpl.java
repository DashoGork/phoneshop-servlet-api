package com.es.phoneshop.service.filter.impl;

import com.es.phoneshop.service.filter.DosFilterService;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class DosFilterServiceImpl implements DosFilterService {

    private static volatile DosFilterServiceImpl ServiceImplementation;

    private static final Long THRESHOLD = 20l;
    private Map<String, Long> countMap = new ConcurrentHashMap<>();

    private DosFilterServiceImpl() {
        cleaningMap();
    }

    public static DosFilterServiceImpl getDefaultDosFilterService() {
        DosFilterServiceImpl result = ServiceImplementation;
        if (result != null) {
            return result;
        }
        synchronized (DosFilterServiceImpl.class) {
            if (ServiceImplementation == null) {
                ServiceImplementation = new DosFilterServiceImpl();
            }
            return ServiceImplementation;
        }
    }

    @Override
    public boolean isAllowed(String ip) {
        Long count = countMap.get(ip);
        if (count == null) {
            count = 1l;
        } else {
            if (count > THRESHOLD) {
                return false;
            }
            count++;
        }
        countMap.put(ip, count);
        return true;
    }

    private synchronized void cleaningMap() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                countMap.clear();
            }
        }, 0, 60000);
    }
}
