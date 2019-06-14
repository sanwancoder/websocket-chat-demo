package com.tim.chat.Model;

import java.util.Date;
import lombok.Data;

/**
 * 基本消息类型，用于接收消息
 */
@Data
public class BaseMessage {

  // 发送者ID
  private String senderId;

  //发送者名称
  private String senderName;

  // 接收者ID
  private String receiverId;

  //接收者名称
  private String receiverName;

  // 消息类型，标注是登录消息（login）、聊天消息(chat)、退出消息(logout)
  private String messageType;

  // 消息内容
  private String content;

  // 发送时间
  private Date sendTime;

}
