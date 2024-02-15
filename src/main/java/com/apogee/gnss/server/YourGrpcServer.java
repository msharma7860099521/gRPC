package com.apogee.gnss.server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;

@Configuration
public class YourGrpcServer {
	@Bean
    void startServer() throws Exception {
		
    	Server2 yourService = new Server2();
    	
        io.grpc.Server server = ServerBuilder.forPort(6565)
            .addService(yourService)
            .addService(ProtoReflectionService.newInstance()) // Enable server reflection
            .build();
        server.start();
        server.awaitTermination();
    }
    
}