package org.zobic.ecommerceshopapi.service;

import org.springframework.web.multipart.MultipartFile;
import org.zobic.ecommerceshopapi.model.Panel;

import java.util.List;

public interface PanelService {
  void refreshState();
  List<Panel> getPanels();
  boolean isPanelSupported(String tag);
}
