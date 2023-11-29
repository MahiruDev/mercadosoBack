package com.example.MercadosoBack.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@Controller
@RequiredArgsConstructor
@RequestMapping("/chatroom")
public class ChatController {
    private final ChatMessageService chatMessageService;
private final SimpMessagingTemplate messagingTemplate;

@MessageMapping("/chat")
public void processMessage(@Payload ChatMessage chatMessage){
    ChatMessage savedMsg=chatMessageService.save(chatMessage);
    //user/queue/message
    messagingTemplate.convertAndSendToUser(chatMessage.getRecipientId(),"/queue/messages"
    ,null);
}



    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages
            (@PathVariable String senderId,@PathVariable String recipientId){
        return ResponseEntity.ok(chatMessageService.findChatMessages(senderId,recipientId));
    }
}
