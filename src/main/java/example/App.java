package example;

import ratpack.error.ClientErrorHandler;
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
        .bindInstance(ClientErrorHandler.class, (ctx, code) -> ctx.getResponse().status(code).send())
      ))
      .handlers(c -> c
        .get(ctx -> ctx.render(Groovy.groovyMarkupTemplate("page.gtpl")))
      )
    );
  }
}
