package com.example.interview.utils;

import javax.persistence.*;


public class SnowflakeId {


    // 起始的时间戳 (自定义纪元时间，通常为某个固定日期)
    private final long epoch = 1728571440L; // 这个时间戳是Twitter的纪元时间戳，可以自己定义

    // 每个部分占用的位数
    private final long workerIdBits = 5L; // 机器ID所占的位数
    private final long datacenterIdBits = 5L; // 数据中心ID所占的位数
    private final long sequenceBits = 12L; // 序列号所占的位数

    // 最大值计算
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits); // 机器ID最大值
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits); // 数据中心ID最大值
    private final long maxSequence = -1L ^ (-1L << sequenceBits); // 序列号最大值

    // 各部分左移的位数
    private final long workerIdShift = sequenceBits;
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    private final long timestampLeftShift = datacenterIdShift + datacenterIdBits;

    private final long sequenceMask = -1L ^ (-1L << sequenceBits); // 序列号掩码，用于序列号部分

    private long workerId; // 机器ID
    private long datacenterId; // 数据中心ID
    private long sequence = 0L; // 序列号
    private long lastTimestamp = -1L; // 上次生成ID的时间戳

    // 构造函数，传入机器ID和数据中心ID
    public SnowflakeId(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    // 生成下一个ID
    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        // 如果当前时间戳小于上次生成ID的时间戳，说明发生了时钟回拨
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id for " + (lastTimestamp - timestamp) + " milliseconds");
        }

        // 如果在同一毫秒内生成ID，序列号自增
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 如果序列号溢出，等待下一毫秒
                timestamp = waitForNextMillis(lastTimestamp);
            }
        } else {
            // 如果时间戳不等于上次时间戳，说明进入了新的一毫秒，重置序列号
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        // 组合各部分，生成最终的ID
        return ((timestamp - epoch) << timestampLeftShift) // 时间戳部分
                | (datacenterId << datacenterIdShift) // 数据中心ID部分
                | (workerId << workerIdShift) // 机器ID部分
                | sequence; // 序列号部分
    }

    // 等待直到下一毫秒
    private long waitForNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    // 获取当前时间戳
    public long currentTimestamp() {
        return System.currentTimeMillis();
    }

}


