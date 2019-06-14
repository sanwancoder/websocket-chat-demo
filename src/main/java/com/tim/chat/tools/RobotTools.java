package com.tim.chat.tools;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 茉莉机器人 API 调用工具类
 */
public class RobotTools {

  private final static String KEY = "be3cd4020dfc234e89e45a68a785815f";

  private final static String SECRET = "18nu0028r9lk";

  private final static String API = "http://i.itpk.cn/api.php";

  /**
   * 调用机器人的智能回复
   */
  public static String reply(String content) {
    try {
      Document document = Jsoup.connect(API).data("question", content)
          .data("api_key", KEY)
          .data("api_secret", SECRET)
          .ignoreContentType(true).get();
      String result = document.text();
      if (result.startsWith("{")) {
        result = JSONObject.parseObject(result).getString("content");
      }

      return result;
    } catch (IOException e) {
      e.printStackTrace();
    }

    return "";
  }

}
