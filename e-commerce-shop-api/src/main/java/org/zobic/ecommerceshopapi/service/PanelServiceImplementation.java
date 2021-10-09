package org.zobic.ecommerceshopapi.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.zobic.ecommerceshopapi.model.Panel;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PanelServiceImplementation implements PanelService {

  private List<Panel> panelList = new ArrayList<>();

  private ObjectMapper mapper = new ObjectMapper();

  @PostConstruct
  void postConstruct() {
    log.info("Loading panels from file");
    this.loadFile();
  }

  private void loadFile() {
    log.info("Parsing panels file");
    ClassPathResource classPathResource = new ClassPathResource("entities/Panels.json");
    try {
      File file = classPathResource.getFile();

      panelList = mapper.readValue(file, new TypeReference<List<Panel>>(){});

      for (Panel panel : panelList) {
        log.info("Panel Name: {} ::: Panel TAG: {}", panel.getName(), panel.getTag());
      }

      log.info("Parsing panels finished successfully");
    } catch (IOException e) {
      log.warn("Parsing panels file failed");
    }
  }

  @Override
  public void refreshState() {
    log.info("Refreshing panels state");
    this.loadFile();
  }

  @Override
  public List<Panel> getPanels() {
    return this.panelList;
  }

  @Override
  public boolean isPanelSupported(String tag) {
    return this.panelList.stream().anyMatch(p -> p.getTag().equals(tag));
  }

}
