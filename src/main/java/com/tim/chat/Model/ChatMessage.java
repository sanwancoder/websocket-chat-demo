package com.tim.chat.Model;

import lombok.Data;

/**
 * 响应消息
 */
@Data
public class ChatMessage {

  // 发送者ID
  private String senderId;

  // 发送者名称
  private String senderName;

  // 消息内容
  private String content;

  // 发送时间
  private String sendTime;

  // 消息类型，标注是登录消息（login）、聊天消息(chat)、退出消息(logout)
  private String messageType;

}
