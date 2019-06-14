package com.tim.chat.controller;

import com.alibaba.fastjson.JSON;
import com.tim.chat.Model.BaseMessage;
import com.tim.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;


@Controller
public class WsController {

  @Autowired
  private ChatService chatService;

  @MessageMapping("/chat")//通过 @MessageMapping 注解接收浏览器端发送的消息
  public void chat(String message) {
    BaseMessage baseMessage = JSON.parseObject(message, BaseMessage.class);

    chatService.sendMessage(baseMessage);
  }

}
