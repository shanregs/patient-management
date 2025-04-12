package com.shan.ms.pm.billingservice.grpc;


import com.shan.ms.pm.billing.model.BillingRequest;
import com.shan.ms.pm.billing.model.BillingResponse;
import com.shan.ms.pm.billing.model.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;


@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {
    private static Logger log = LoggerFactory.getLogger(BillingGrpcService.class);
    @Override
    public void createBillingAccount(BillingRequest request, StreamObserver<BillingResponse> responseObserver) {
        log.info("createBillingAccount request received - {} ", request.toString());
        String randomId = "BILL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        BillingResponse billingResponse = BillingResponse.newBuilder()
                .setAccountId(randomId)
                .setStatus("ACTIVE")
                .build();


        responseObserver.onNext(billingResponse);
        responseObserver.onCompleted();
    }
}
