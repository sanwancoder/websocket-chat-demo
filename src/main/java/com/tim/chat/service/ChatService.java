package com.tim.chat.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tim.chat.Model.BaseMessage;
import com.tim.chat.Model.ChatMessage;
import com.tim.chat.tools.RobotTools;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
public class ChatService {

  private static final String ROBOT = "robot";

  private static final String ALL = "all";

  private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");

  @Autowired
  private SimpMessagingTemplate template;

  /**
   * 发送消息
   */
  public void sendMessage(BaseMessage baseMessage) {
    String receiverId = baseMessage.getReceiverId();
    switch (receiverId) {
      case ROBOT:
        robotReply(baseMessage);
        break;
      case ALL:
        sendToAll(JSON.toJSONString(baseMessage));
        break;
      default:
        sendToUser(JSON.toJSONString(baseMessage));
        break;
    }
  }

  /**
   * 发送给用户，直接将消息转发，客户端需要在/user/receiverId/chat上监听。例如客户端监听了/user/123/chat，这里user=123可以向该用户发送消息
   */
  private void sendToUser(String baseMessage) {
    JSONObject json = JSONObject.parseObject(baseMessage);
    ChatMessage chatMessage = createChatMessage(baseMessage);

    template.convertAndSendToUser(json.getString("receiverId"), "/chat",
        JSON.toJSONString(chatMessage));
  }

  /**
   * 发送给所有人，所有监听了/topic/notice节点的客户端，可以收到消息。
   */
  private void sendToAll(String baseMessage) {
    ChatMessage chatMessage = createChatMessage(baseMessage);

    template.convertAndSend("/topic/notice", JSON.toJSONString(chatMessage));
  }

  /**
   * 机器人回复
   */
  private void robotReply(BaseMessage baseMessage) {
    //从机器人处得到回复消息
    String replyContent = RobotTools.reply(baseMessage.getContent());

    ChatMessage chatMessage = new ChatMessage();
    chatMessage.setContent(replyContent);
    chatMessage.setSendTime(simpleDateFormat.format(new Date()));
    chatMessage.setSenderName(ROBOT);

    template.convertAndSendToUser(baseMessage.getSenderId(), "/chat",
        JSON.toJSONString(chatMessage));
  }

  /**
   * 封装答复信息
   */
  private ChatMessage createChatMessage(String baseMessage) {
    JSONObject json = JSONObject.parseObject(baseMessage);

    String senderId = json.getString("senderId");
    String senderName = json.getString("senderName");
    String message = json.getString("content");
    String messageType = json.getString("messageType");

    ChatMessage chatMessage = new ChatMessage();

    chatMessage.setSenderId(senderId);
    chatMessage.setSenderName(senderName);
    chatMessage.setContent(message);
    chatMessage.setSendTime(simpleDateFormat.format(new Date()));
    chatMessage.setMessageType(messageType);

    return chatMessage;
  }

}
