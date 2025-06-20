package com.gexingw.mall.infrastucture.core.util;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * mall-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/2 11:37
 */
@SuppressWarnings("unused")
public class SnowflakeUtil {

    /**
     * 机器id所占的位数
     */
    private final long workerIdBits = 5L;

    /**
     * 数据标识id所占的位数
     */
    private final long dataCenterIdBits = 5L;

    /**
     * 工作机器ID(0~31)
     */
    private final long workerId;

    /**
     * 数据中心ID(0~31)
     */
    private final long dataCenterId;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    private static final SnowflakeUtil ID_WORKER;

    static {
        ID_WORKER = new SnowflakeUtil(getWorkId(), getDataCenterId());
    }

    /**
     * 构造函数
     *
     * @param workerId     工作ID (0~31)
     * @param dataCenterId 数据中心ID (0~31)
     */
    private SnowflakeUtil(long workerId, long dataCenterId) {
        // 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
        long maxWorkerId = ~(-1L << workerIdBits);
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("workerId can't be greater than %d or less than 0", maxWorkerId));
        }

        // 支持的最大数据标识id，结果是31
        long maxDataCenterId = ~(-1L << dataCenterIdBits);
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("dataCenterId can't be greater than %d or less than 0", maxDataCenterId));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    private synchronized long nextId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        // 序列在id中占的位数
        long sequenceBits = 12L;
        if (lastTimestamp == timestamp) {
            // 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
            long sequenceMask = ~(-1L << sequenceBits);
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        // 数据标识id向左移17位(12+5)
        long dataCenterIdShift = sequenceBits + workerIdBits;
        // 时间截向左移22位(5+5+12)
        long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;
        //开始时间截 (2015-01-01)
        long twepoch = 1288834974657L;
        return ((timestamp - twepoch) << timestampLeftShift)
                | (dataCenterId << dataCenterIdShift)
                | (workerId << sequenceBits)
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    private static Long getWorkId() {
        try {
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            int[] ints = StringUtils.toCodePoints(hostAddress);
            int sums = 0;
            for (int b : ints) {
                sums += b;
            }
            return (long) (sums % 32);
        } catch (UnknownHostException e) {
            // 如果获取失败，则使用随机数备用
            return RandomUtils.nextLong(0, 31);
        }
    }

    private static Long getDataCenterId() {
        int[] ints = StringUtils.toCodePoints(getHostName());
        int sums = 0;
        for (int i : ints) {
            sums += i;
        }
        return (long) (sums % 32);
    }

    /**
     * 获取 hostName
     * SystemUtils.getHostName() 在mac系统为空处理
     *
     * @return hostname
     */
    public static String getHostName() {
        //获取当前操作系统名称,例如:windows xp,linux 等;
        String osName = System.getProperty("os.name").toLowerCase();
        String hostName;
        //noinspection AlibabaUndefineMagicConstant
        if (osName.startsWith("mac")) {
            try {
                hostName = InetAddress.getLocalHost().getHostName().toUpperCase();
            } catch (UnknownHostException e) {
                e.printStackTrace();
                hostName = "N/A";
            }
        } else {
            hostName = SystemUtils.getHostName();
        }
        return hostName;
    }

    /**
     * 获取雪花算法Id
     *
     * @return id
     */
    public static Long getId() {
        return ID_WORKER.nextId();
    }

}
