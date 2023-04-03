package com.softarc.eternal.remote.printing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "printing", url = "http://localhost:8081")
public interface PrintingClient {
  @PostMapping(value = "/api/order")
  boolean addPrintingJob(AddPrintingJobRequest addPrintingJobRequest);
}
