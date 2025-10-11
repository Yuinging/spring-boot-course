package top.yyyin.boot.websocket.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import top.yyyin.boot.websocket.model.DeviceMonitorData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class SimpleTimeWebSocketHandler implements WebSocketHandler {
    private static final Map<String, WebSocketSession> SESSIONS = new ConcurrentHashMap<>();
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();

    // 随机一言数据库
    private final List<String> sayings = new ArrayList<>() {{
        add("每一个不曾起舞的日子，都是对生命的辜负。——尼采");
        add("要么庸俗，要么孤独。——叔本华");
        add("万物皆有裂痕，那是光照进来的地方。——莱昂纳德·科恩");
        add("真正的自由，是在认清生活真相后依然热爱生活。——罗曼·罗兰");
        add("人生如逆旅，我亦是行人。——苏轼");
        add("且视他人之疑目如盏盏鬼火，大胆地去走你的夜路。——史铁生");
        add("如果你认识从前的我，也许会原谅现在的我。——张爱玲");
        add("重要的不是治愈，而是带着病痛活下去。——加缪");
    }};

    // 模拟天气数据
    private final List<String> weathers = new ArrayList<>() {{
        add("晴转多云，19-28℃，西北风2级，空气质量优（PM2.5: 12）");
        add("小雨，15-18℃，东南风3级，空气质量良（PM2.5: 45）");
        add("雷阵雨，22-26℃，西南风4级，请注意防范短时强降水");
        add("阴，17-21℃，东北风1级，空气质量轻度污染（PM2.5: 110）");
        add("多云转晴，20-30℃，无风，紫外线强度中等，建议涂防晒霜");
        add("小雪，-2-3℃，北风5级，路面易滑，请注意出行安全");
        add("雾，8-12℃，无风，能见度不足1公里，建议减少户外活动");
    }};

    // 模拟设备列表
    private final List<String> deviceIds = new ArrayList<>() {{
        add("server-01");
        add("server-02");
        add("client-000");
        add("client-001");
        add("gateway-01");
    }};

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        SESSIONS.put(session.getId(), session);
        log.info("新的WebSocket连接建立，会话ID: {}, 当前连接数: {}", session.getId(), SESSIONS.size());
        String welcomeMessage = "🎉 欢迎连接信息推送服务！\n" +
                "将收到定时推送的：\n" +
                "- 随机一言\n" +
                "- 模拟天气\n" +
                "- 设备监控数据\n";
        sendMsg(session, welcomeMessage);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = message.getPayload().toString();
        log.info("收到客户端消息: {}, 会话ID: {}", payload, session.getId());

        // 根据客户端指令返回不同类型的数据
        if ("weather".equalsIgnoreCase(payload.trim())) {
            sendWeatherInfo(session);
        } else if ("saying".equalsIgnoreCase(payload.trim())) {
            sendRandomSaying(session);
        } else if ("device".equalsIgnoreCase(payload.trim())) {
            sendDeviceMonitorData(session);
        } else if ("ping".equalsIgnoreCase(payload.trim())) {
            sendMsg(session, "pong");
        } else {
            String response = "收到消息: " + payload + "\n" +
                    "可发送以下指令获取对应信息:\n" +
                    "- weather: 获取天气信息\n" +
                    "- saying: 获取随机一言\n" +
                    "- device: 获取设备监控数据\n" +
                    "- ping: 测试连接";
            sendMsg(session, response);
        }
    }

    // 每10秒推送一次混合信息
    @Scheduled(fixedRate = 10000)
    public void sendPeriodicInfo() {
        if (SESSIONS.isEmpty()) {
            log.debug("当前没有活跃的WebSocket连接");
            return;
        }
        log.info("开始执行定时信息推送任务，当前连接数: {}", SESSIONS.size());

        // 生成三种类型的信息
        String timeInfo = String.format("⏰ %s", LocalDateTime.now().format(timeFormatter));
        String weatherInfo = getWeatherInfo();
        String sayingInfo = getRandomSaying();
        String deviceInfo = getDeviceMonitorData();

        // 组合信息
        String combinedInfo = String.format(
                "%s\n🌤️ 天气: %s\n💬 一言: %s\n📊 设备监控: %s",
                timeInfo, weatherInfo, sayingInfo, deviceInfo
        );

        // 向所有连接的客户端推送消息
        SESSIONS.values().removeIf(session -> {
            try {
                if (session.isOpen()) {
                    sendMsg(session, combinedInfo);
                    return false;
                } else {
                    log.warn("发现已关闭的会话，将其移除: {}", session.getId());
                    return true;
                }
            } catch (Exception e) {
                log.error("发送消息失败，移除会话: {}", session.getId(), e);
                return true;
            }
        });
    }

    // 获取随机天气信息
    private String getWeatherInfo() {
        return weathers.get(random.nextInt(weathers.size()));
    }

    // 发送天气信息到指定会话
    private void sendWeatherInfo(WebSocketSession session) {
        String weatherInfo = String.format("🌤️ 当前天气: %s", getWeatherInfo());
        sendMsg(session, weatherInfo);
    }

    // 获取随机一言
    private String getRandomSaying() {
        return sayings.get(random.nextInt(sayings.size()));
    }

    // 发送随机一言到指定会话
    private void sendRandomSaying(WebSocketSession session) {
        String sayingInfo = String.format("💬 一言: %s", getRandomSaying());
        sendMsg(session, sayingInfo);
    }

    // 获取设备监控数据
    private String getDeviceMonitorData() {
        // 随机选择一个设备生成数据
        String deviceId = deviceIds.get(random.nextInt(deviceIds.size()));
        DeviceMonitorData data = DeviceMonitorData.generateRandomData(deviceId);

        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.error("设备监控数据序列化失败", e);
            return "设备监控数据获取失败";
        }
    }

    // 发送设备监控数据到指定会话
    private void sendDeviceMonitorData(WebSocketSession session) {
        String deviceInfo = String.format("📊 设备监控: %s", getDeviceMonitorData());
        sendMsg(session, deviceInfo);
    }

    private void sendMsg(WebSocketSession session, String message) {
        try {
            if (session.isOpen()) {
                TextMessage textMessage = new TextMessage(message);
                session.sendMessage(textMessage);
                log.debug("消息发送成功，会话ID: {}", session.getId());
            }
        } catch (Exception e) {
            log.error("发送消息失败，会话ID: {}", session.getId(), e);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("WebSocket传输错误，会话ID: {}", session.getId(), exception);
        SESSIONS.remove(session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        SESSIONS.remove(session.getId());
        log.info("WebSocket连接关闭，会话ID: {}, 关闭状态: {}, 当前连接数: {}",
                session.getId(), closeStatus, SESSIONS.size());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}