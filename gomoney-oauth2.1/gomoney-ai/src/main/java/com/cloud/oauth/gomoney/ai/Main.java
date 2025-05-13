package com.cloud.oauth.gomoney.ai;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
public class Main {
    public static void main(String[] args) {
        String token = "2fed56b5d2f0de81c6d5f4f007f242abfb5b789894f4eb609af3a153";

        // TuShare API 接口地址
        String url = "https://api.tushare.pro";

        // 请求的参数
        String query = "{\n" +
                "  \"api_name\": \"daily\",\n" +
                "  \"params\": {\n" +
                "    \"ts_code\": \"000001.SZ\",\n" +
                "    \"start_date\": \"20240101\",\n" +
                "    \"end_date\": \"20240221\"\n" +
                "  },\n" +
                "  \"fields\": \"ts_code,trade_date,open,high,low,close\",\n" +
                "  \"token\": \"" + token + "\"\n" +
                "}";

        // 调用 TuShare API
        try {
            // 创建 HttpClient 对象
            HttpClient client = HttpClient.newHttpClient();

            // 创建 HttpRequest 对象，POST 请求
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(java.net.http.HttpRequest.BodyPublishers.ofString(query))
                    .build();

            // 发送请求并获取响应
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 打印响应
            String responseBody = response.body();
            System.out.println("Response: " + responseBody);

            // 解析 JSON 响应
            JSONObject jsonResponse = new JSONObject(responseBody);
            System.out.println("Parsed JSON: " + jsonResponse.toString(4));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
