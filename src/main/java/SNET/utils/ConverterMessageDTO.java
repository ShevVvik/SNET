package SNET.utils;

import java.util.ArrayList;
import java.util.List;

import SNET.domain.dto.message.ChatMessageDTO;
import SNET.domain.entity.User;
import SNET.domain.entity.message.ChatMessage;

public class ConverterMessageDTO {
	
	public static List<ChatMessageDTO> mapMessagesToChatDTOs(List<ChatMessage> chatMessages) {
	    List<ChatMessageDTO> dtos = new ArrayList<ChatMessageDTO>();

	    for(ChatMessage chatMessage : chatMessages) { 
	      dtos.add(
	        new ChatMessageDTO(
	          chatMessage.getContents(),
	          chatMessage.getAuthorUser().getId(),
	          chatMessage.getRecipientUser().getId()
	        )
	      );
	    }

	    return dtos;
	}
	
	public static ChatMessage mapChatDTOtoMessage(ChatMessageDTO dto) {
	    return new ChatMessage(
	    	// only need the id for mapping
	    	new User(dto.getFromUserId()),
	    	new User(dto.getToUserId()),
	    	dto.getContents()
	    );
	}
	
	public static ChatMessageDTO mapChatMessagetoMessageDTO(ChatMessage dto) {
	    return new ChatMessageDTO(
	    	dto.getContents(),
	    	dto.getAuthorUser().getId(),
	    	dto.getRecipientUser().getId()	    	
	    );
	}
}
