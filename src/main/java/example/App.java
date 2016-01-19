package example;

import ratpack.groovy.Groovy;
import ratpack.groovy.template.MarkupTemplateModule;
import ratpack.guice.Guice;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

public class App {
  public static void main(String[] args) throws Exception {
    RatpackServer.start(a -> a
      .serverConfig(s -> s.baseDir(BaseDir.find()))
      .registry(Guice.registry(r -> r
        .module(MarkupTemplateModule.class)
      ))
      .handlers(c -> c
        .all(ctx -> ctx.render(Groovy.groovyMarkupTemplate("page.gtpl")))
      )
    );
  }
}
