package com.harajuku.messagingApp.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class Config implements WebSocketMessageBrokerConfigurer {

	
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/chatRoom/{chatId}").setAllowedOrigins("*").withSockJS();
		registry.addEndpoint("/test").withSockJS();
		registry.addEndpoint("/chatRoom").setAllowedOrigins("*").withSockJS(); // Allow all origins
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic");
		registry.setApplicationDestinationPrefixes("/app");
	}

//	@Override
//	public void configureClientInboundChannel(ChannelRegistration registration) {
//		registration.interceptors(new ChannelInterceptorAdapter() {
//			@Override
//			public Message<?> preSend(Message<?> message, MessageChannel channel) {
//				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//				if (authentication != null) {
//					StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//					accessor.setUser(authentication);
//				}
//				return message;
//			}
//		});
//	}
	
	// Optionally, configure a message converter for your objects
    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        return messageConverters.add(new MappingJackson2MessageConverter());  // Adds JSON conversion
    }

}