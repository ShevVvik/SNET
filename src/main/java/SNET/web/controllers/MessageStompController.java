package SNET.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import SNET.config.UserDetailsImpl;
import SNET.domain.dto.message.ChatConnectionInitializeDTO;
import SNET.domain.dto.message.ChatMessageDTO;
import SNET.domain.dto.message.EstablishedChatChannelDTO;
import SNET.domain.entity.User;
import SNET.domain.services.ChatDomainServices;
import SNET.domain.services.UserDomainServices;
import SNET.utils.ConverterUserDTO;
import SNET.utils.JSONResponseHelper;

@Controller
public class MessageStompController {
	
	@Autowired
	private ChatDomainServices chatService;

	@Autowired
	private UserDomainServices userService;
	
	@MessageMapping("/message.{channelId}")
    @SendTo("/topic/message.{channelId}")
    public ChatMessageDTO chatMessage(@DestinationVariable String channelId, ChatMessageDTO message) {
      chatService.submitMessage(message);
      return message;
	}
	
	@RequestMapping(value="/message", method=RequestMethod.PUT, produces="application/json", consumes="application/json")
    public ResponseEntity<String> establishChatChannel(@RequestBody ChatConnectionInitializeDTO chatChannelInitialization, Authentication auth) { 
	  UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
      String channelUuid = chatService.establishChatSession(chatChannelInitialization);
      User userOne = userDetails.getUser();
      User userTwo = userService.getById(chatChannelInitialization.getUserIdTwo());

      EstablishedChatChannelDTO establishedChatChannel = new EstablishedChatChannelDTO(
        channelUuid,
        ConverterUserDTO.convertUserToUserDTO(userOne),
        ConverterUserDTO.convertUserToUserDTO(userTwo)
      );
    
      return JSONResponseHelper.createResponse(establishedChatChannel, HttpStatus.OK);
    }
	
	@RequestMapping(value="/message", method=RequestMethod.GET, produces="application/json")
    public String getChatMessagesPage(Model model, Authentication auth) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		model.addAttribute("user", userDetails.getUser());
		model.addAttribute("dialogs", chatService.getAllDialogByUser(userDetails.getUser()));
		return "/user/mes";
    }
    
    @RequestMapping(value="/message/{channelUuid}", method=RequestMethod.GET, produces="application/json")
    public ResponseEntity<String> getExistingChatMessages(@PathVariable("channelUuid") String channelUuid) {
    	List<ChatMessageDTO> messages = chatService.getExistingChatMessages(channelUuid);

    	return JSONResponseHelper.createResponse(messages, HttpStatus.OK);
    }
}
