package de.holisticon.servlet4demo.okhttpclient;

import de.holisticon.servlet4demo.okhttpclient.dto.Greeting;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Hello world!
 *
 * @see <a href="https://webtide.com/the-new-jetty-9-http-client/">the-new-jetty-9-http-client</a>
 * @see <a href="http://git.eclipse.org/c/jetty/org.eclipse.jetty.project.git/tree/jetty-http2/http2-client/src/test/java/org/eclipse/jetty/http2/client/Client.java">Jetty HTTP2Client Example</a>
 * @see <a href="https://blogs.oracle.com/brewing-tests/entry/http_2_with_jetty_server">http_2_with_jetty_server</a>
 */
@SpringBootApplication
public class Application {

  private static final Logger LOG = Logger.getLogger(Application.class);

  private RestTemplate okHttpRestTemplate;

  @Value("${server.port}")
  private int httpPort;

  @Autowired
  public Application(@Qualifier("okHttpRestTemplate") RestTemplate okHttpRestTemplate) {
    this.okHttpRestTemplate = okHttpRestTemplate;
  }

  public static void main(String[] args) {
    SpringApplication
        .run(Application.class)
        .close();
  }


  @Bean
  public CommandLineRunner run() throws Exception {
    return args -> {
      String host = "localhost";
      String path = "/greeting?name=JavaLand";
      LOG.info("========================= restTemplate okHttp client => tomcat server ==============");
      Greeting greeting = okHttpRestTemplate.getForObject(
          "https://" + host + ":" + httpPort + path, Greeting.class);
      LOG.info(greeting.toString());
      LOG.info("====================================================================================");

    };
  }


  public void getRespResponse(){
    RestTemplate okHttpRestTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    Greeting greeting = okHttpRestTemplate.getForObject(
        "https://localhost:8443/greeting", Greeting.class);

  }
}
