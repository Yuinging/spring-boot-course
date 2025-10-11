package top.yyyin.boot.websocket.model;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@Data
public class DeviceMonitorData {
    private String deviceId;
    private double cpuUsage; // CPU使用率(%)
    private double memoryUsage; // 内存使用率(%)
    private double diskUsage; // 磁盘使用率(%)
    private String status; // 设备状态
    private long timestamp; // 时间戳

    // 生成随机监控数据
    public static DeviceMonitorData generateRandomData(String deviceId) {
        DeviceMonitorData data = new DeviceMonitorData();
        Random random = new Random();

        data.setDeviceId(deviceId);
        // 生成1-80之间的随机CPU使用率，保留1位小数
        data.setCpuUsage(new BigDecimal(random.nextDouble() * 80 + 1)
                .setScale(1, RoundingMode.HALF_UP).doubleValue());
        // 生成10-90之间的随机内存使用率
        data.setMemoryUsage(new BigDecimal(random.nextDouble() * 80 + 10)
                .setScale(1, RoundingMode.HALF_UP).doubleValue());
        // 生成5-70之间的随机磁盘使用率
        data.setDiskUsage(new BigDecimal(random.nextDouble() * 65 + 5)
                .setScale(1, RoundingMode.HALF_UP).doubleValue());

        // 根据CPU使用率设置状态
        if (data.getCpuUsage() > 70) {
            data.setStatus("警告");
        } else if (data.getCpuUsage() > 50) {
            data.setStatus("正常");
        } else {
            data.setStatus("良好");
        }

        data.setTimestamp(System.currentTimeMillis());
        return data;
    }
}
