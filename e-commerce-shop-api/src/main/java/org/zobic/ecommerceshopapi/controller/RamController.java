package org.zobic.ecommerceshopapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zobic.ecommerceshopapi.service.RamService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RamController {

  private RamService ramService;

  public RamController(RamService ramService) {
    this.ramService = ramService;
  }

  @GetMapping("/api/ram")
  public ResponseEntity<Map<String, Object>> getAllRam() {
    Map<String, Object> response = new HashMap<>();
    response.put("ram", this.ramService.getAllRamValues());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
