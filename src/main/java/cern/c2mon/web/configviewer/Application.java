package cern.c2mon.web.configviewer;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Justin Lewis Salmon
 */
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    new SpringApplicationBuilder().bannerMode(Banner.Mode.OFF).sources(Application.class).run(args);
  }
}
