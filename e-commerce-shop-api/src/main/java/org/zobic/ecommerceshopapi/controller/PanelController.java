package org.zobic.ecommerceshopapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zobic.ecommerceshopapi.service.PanelService;

import java.util.HashMap;

@RestController
public class PanelController {

  private final PanelService panelService;

  public PanelController(PanelService panelService) {
    this.panelService = panelService;
  }

  @GetMapping("/api/panels")
  @PreAuthorize(value = "hasAuthority('ROLE_USER')")
  public HashMap<Object, Object> getAllPanels() {
    HashMap<Object, Object> response = new HashMap<>();
    response.put("panels", this.panelService.getPanels());
    return response;
  }

  @PostMapping("/api/panels/upload")
  @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
  public HashMap<Object, Object> publishNewPanels(@RequestParam("file") MultipartFile jsonFile) {
    return new HashMap<>();
  }

  @GetMapping("/api/panels/refresh")
  @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<?> refresh() {
    this.panelService.refreshState();
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
