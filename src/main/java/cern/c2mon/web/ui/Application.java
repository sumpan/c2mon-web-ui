package cern.c2mon.web.ui;

import cern.c2mon.client.common.tag.Tag;
import cern.c2mon.client.core.ConfigurationService;
import cern.c2mon.shared.client.configuration.api.tag.DataTag;
import cern.c2mon.shared.common.metadata.Metadata;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

/**
 * @author Justin Lewis Salmon
 */
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    ApplicationContext context = new SpringApplicationBuilder().bannerMode(Banner.Mode.OFF).sources(Application.class).run(args);
    ConfigurationService configurationService = context.getBean(ConfigurationService.class);

    configurationService.updateDataTag(DataTag.update("mem.avail")
        .metadata(Metadata.builder()
            .addMetadata("location", "104").build()).build());

    configurationService.updateDataTag(DataTag.update("mem.swap.used")
        .metadata(Metadata.builder()
            .addMetadata("location", "104").build()).build());

    configurationService.updateDataTag(DataTag.update("cpu.loadavg")
        .metadata(Metadata.builder()
            .addMetadata("location", "874").build()).build());

    configurationService.updateDataTag(DataTag.update("cpu.temp")
        .metadata(Metadata.builder()
            .addMetadata("location", "874").build()).build());

    configurationService.updateDataTag(DataTag.update("cpu.voltage")
        .metadata(Metadata.builder()
            .addMetadata("location", "874").build()).build());

    configurationService.updateDataTag(DataTag.update("os.numprocs")
        .metadata(Metadata.builder()
            .addMetadata("location", "513").build()).build());

    configurationService.updateDataTag(DataTag.update("os.numthreads")
        .metadata(Metadata.builder()
            .addMetadata("location", "513").build()).build());

    configurationService.updateDataTag(DataTag.update("os.fds")
        .metadata(Metadata.builder()
            .addMetadata("location", "513").build()).build());

    ElasticsearchService elasticsearchService = context.getBean(ElasticsearchService.class);

    List<Tag> tags = (List<Tag>) elasticsearchService.findByMetadata("location", "513");
    System.out.println(tags);
  }
}
