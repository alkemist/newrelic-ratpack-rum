package example;

import ratpack.error.ClientErrorHandler;
import ratpack.exec.Blocking;
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
        .get("work", ctx -> ctx.render(Groovy.groovyMarkupTemplate("page.gtpl")))
        .get("no-work", ctx ->
          Blocking.get(() -> 1) // This causes execution to jump off the thread and “escape” the New Relic transaction
            .then(val -> ctx.render(Groovy.groovyMarkupTemplate("page.gtpl")))
        )
      )
    );
  }
}
