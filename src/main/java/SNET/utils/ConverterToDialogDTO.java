package SNET.utils;

import java.util.List;

import SNET.domain.dto.message.DialogDTO;
import SNET.domain.entity.User;
import SNET.domain.entity.message.ChatChannel;
import SNET.domain.entity.message.ChatMessage;

public class ConverterToDialogDTO {

	public static List<DialogDTO> getListDialogDTO(List<String> contentsList, List<ChatChannel> channels) {
		return null;
		
	}
	
	/*public static DialogDTO getDialogDTO(ChatChannel channel, String contents) {
		return new DialogDTO(
				ConverterUserDTO.convertUserToUserDTO(channel.getUserOne()),
				contents
				);
	}*/
	
	public static DialogDTO convertUsertoDialogDTO(User user, ChatMessage contents) {
		return new DialogDTO(
				ConverterUserDTO.convertUserToUserDTO(user),
				ConverterUserDTO.convertUserToUserDTO(contents.getAuthorUser()),
				contents.getContents()
				);
	}
}
