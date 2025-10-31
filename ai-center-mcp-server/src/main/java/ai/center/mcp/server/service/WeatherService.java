package ai.center.mcp.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author xiaobaicai
 */
@Slf4j
@Service
public class WeatherService {

    @Tool(description = "根据城市名称获取天气预报")
    public String getWeather(String city) {
        Map<String, String> map = Map.of(
                "北京", "降雨频繁，其中今天和后天雨势较强，部分地区干燥",
                "杭州", "晴转多云，伴有小雨",
                "西安", "晴，但时常有大风"

        );
        return map.get(city);
    }
}
