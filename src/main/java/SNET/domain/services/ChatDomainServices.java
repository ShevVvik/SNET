package SNET.domain.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import SNET.dao.ChatChannelRepository;
import SNET.dao.ChatMessageRepository;
import SNET.domain.dto.message.ChatConnectionInitializeDTO;
import SNET.domain.dto.message.ChatMessageDTO;
import SNET.domain.dto.message.DialogDTO;
import SNET.domain.entity.User;
import SNET.domain.entity.message.ChatChannel;
import SNET.domain.entity.message.ChatMessage;
import SNET.utils.ConverterMessageDTO;
import SNET.utils.ConverterToDialogDTO;

@Service
public class ChatDomainServices {
	@Autowired
	private ChatChannelRepository chatChannelRepository;
	
	@Autowired
	private ChatMessageRepository chatMessageRepository;

	@Autowired
	private UserDomainServices userService;
	  
	private final int MAX_PAGABLE_CHAT_MESSAGES = 100;

	private String getExistingChannel(ChatConnectionInitializeDTO chatChannelInitializationDTO) {
		String channel = chatChannelRepository
	      .getChannelUuid(
	        chatChannelInitializationDTO.getUserIdOne(),
	        chatChannelInitializationDTO.getUserIdTwo()
	      );
	    
	    return (channel != null && !channel.isEmpty()) ? channel : null;
	  }

	  private String newChatSession(ChatConnectionInitializeDTO chatChannelInitializationDTO) throws BeansException {
	    ChatChannel channel = new ChatChannel(
	      userService.getById(chatChannelInitializationDTO.getUserIdOne()),
	      userService.getById(chatChannelInitializationDTO.getUserIdTwo())
	    );
	    
	    chatChannelRepository.save(channel);

	    return channel.getUuid();
	  }

	  public String establishChatSession(ChatConnectionInitializeDTO chatChannelInitializationDTO) {
	    /*if (chatChannelInitializationDTO.getUserIdOne() == chatChannelInitializationDTO.getUserIdTwo()) {
	      return null;
	    }*/

	    String uuid = getExistingChannel(chatChannelInitializationDTO);
	    System.out.println(uuid);
	    // If channel doesn't already exist, create a new one
	    return (uuid != null) ? uuid : newChatSession(chatChannelInitializationDTO);
	  }
	  
	  public void submitMessage(ChatMessageDTO chatMessageDTO) throws BeansException {
	    ChatMessage chatMessage = ConverterMessageDTO.mapChatDTOtoMessage(chatMessageDTO);
	    System.out.println(chatMessage.getContents());
	    chatMessageRepository.save(chatMessage);
	    /*
	    User fromUser = userService.getById(chatMessage.getAuthorUser().getId());
	    User recipientUser = userService.getById(chatMessage.getRecipientUser().getId());
	      
	    userService.notifyUser(recipientUser,
	      new NotificationDTO(
	        "ChatMessageNotification",
	        fromUser.getFullName() + " has sent you a message",
	        chatMessage.getAuthorUser().getId()
	      )
	    );*/
	  }
	  
	  public List<DialogDTO> getAllDialogByUser(User user) {
		  List<ChatChannel> channelsList = chatChannelRepository.findByUserOneOrUserTwo(user, user);
		  if (channelsList != null) {
			  List<DialogDTO> dialogsList = new ArrayList<DialogDTO>();
			  for (ChatChannel ch : channelsList) {
				  List<ChatMessage> messages = chatMessageRepository.getLastMessage(
						  ch.getUserOne().getId(),
						  ch.getUserTwo().getId(),
						  PageRequest.of(0, 1));
				  if (messages.size() != 0) {
					  ChatMessage mes = messages.get(0);
					  if (user.getId().equals(ch.getUserOne().getId())) {
						  dialogsList.add(ConverterToDialogDTO.convertUsertoDialogDTO(ch.getUserTwo(), mes));  
					  } else {
						  dialogsList.add(ConverterToDialogDTO.convertUsertoDialogDTO(ch.getUserOne(), mes));
					  }
				  }
			  }
			  return dialogsList;
		  }
		  
		  return new ArrayList<DialogDTO>();
	  }
	  
	  public List<ChatMessageDTO> getExistingChatMessages(String channelUuid) {
	    ChatChannel channel = chatChannelRepository.findByUuid(channelUuid);
	    System.out.println(channelUuid + " " + channel);
	    List<ChatMessage> chatMessages = 
	      chatMessageRepository.getExistingChatMessages(
	        channel.getUserOne().getId(),
	        channel.getUserTwo().getId(),
	        PageRequest.of(0, MAX_PAGABLE_CHAT_MESSAGES)
	      );
	    Collections.reverse(chatMessages);
	    return ConverterMessageDTO.mapMessagesToChatDTOs(chatMessages);
	}
}
