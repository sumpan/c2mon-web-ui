package cern.c2mon.web.ui;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author Justin Lewis Salmon
 */
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    new SpringApplicationBuilder().bannerMode(Banner.Mode.OFF).sources(Application.class).run(args);
  }
}
