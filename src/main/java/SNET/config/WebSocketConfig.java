package SNET.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import SNET.config.interceptor.HttpHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	@Autowired
    private HttpHandshakeInterceptor handshakeInterceptor;
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic/", "/queue/");
	    config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
	    registry.addEndpoint("/ws").withSockJS().setInterceptors(handshakeInterceptor);
	}
	/*
	@Bean
	public UserPresenceService presenceChannelInterceptor() {
	    return new UserPresenceService();
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
	    registration.setInterceptors(presenceChannelInterceptor());
	}

	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {
	    registration.taskExecutor().corePoolSize(OUTBOUND_CHANNEL_CORE_POOL_SIZE);
	    registration.setInterceptors(presenceChannelInterceptor());
	}

	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
	    messages.simpDestMatchers("/*").authenticated();
	}
	*/
}
