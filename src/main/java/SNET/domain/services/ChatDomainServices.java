package SNET.domain.services;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import SNET.dao.ChatChannelRepository;
import SNET.dao.ChatMessageRepository;
import SNET.domain.dto.message.ChatConnectionInitializeDTO;
import SNET.domain.dto.message.ChatMessageDTO;
import SNET.domain.entity.User;
import SNET.domain.entity.message.ChatChannel;
import SNET.domain.entity.message.ChatMessage;
import SNET.utils.ConverterMessageDTO;

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
		List<ChatChannel> channel = chatChannelRepository
	      .findByUserOneAndUserTwo(
	        userService.getById(chatChannelInitializationDTO.getUserIdOne()),
	        userService.getById(chatChannelInitializationDTO.getUserIdTwo())
	      );
	    
	    return (channel != null && !channel.isEmpty()) ? channel.get(0).getUuid() : null;
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
	    if (chatChannelInitializationDTO.getUserIdOne() == chatChannelInitializationDTO.getUserIdTwo()) {
	      return null;
	    }

	    String uuid = getExistingChannel(chatChannelInitializationDTO);

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
	 
	  public List<ChatMessageDTO> getExistingChatMessages(String channelUuid) {
	    ChatChannel channel = chatChannelRepository.findByUuid(channelUuid);

	    List<ChatMessage> chatMessages = 
	      chatMessageRepository.getExistingChatMessages(
	        channel.getUserOne().getId(),
	        channel.getUserTwo().getId(),
	        PageRequest.of(0, MAX_PAGABLE_CHAT_MESSAGES)
	      );

	    return ConverterMessageDTO.mapMessagesToChatDTOs(chatMessages);
	}
}
