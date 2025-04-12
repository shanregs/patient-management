package com.shan.ms.pm.patientservice.grpc;

import com.shan.ms.pm.billing.model.BillingRequest;
import com.shan.ms.pm.billing.model.BillingResponse;
import com.shan.ms.pm.billing.model.BillingServiceGrpc;
import com.shan.ms.pm.patientservice.config.GrpcBillingProperties;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceGrpcClient {
    private static final Logger log = LoggerFactory.getLogger(
            BillingServiceGrpcClient.class);
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    public BillingServiceGrpcClient(GrpcBillingProperties grpcProps) {
        String serverAddress = grpcProps.getServerAddress();
        int serverPort = grpcProps.getPort();
        log.info("Connecting to Billing GRPC at {}:{}", serverAddress, serverPort);

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(serverAddress, serverPort)
                .usePlaintext()
                .build();

        this.blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingResponse createBillingRequest(String patientId, String name,
                                                String email) {
        BillingRequest request = BillingRequest.newBuilder()
                .setPatientId(patientId)
                .setName(name)
                .setEmail(email)
                .build();

        BillingResponse response = blockingStub.createBillingAccount(request);
        log.info("Received response from billing service via GRPC: {}", response);
        return response;
    }
}
