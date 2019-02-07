package moac.ipfs;

import moac.ipfs.datasources.DynamicDataSourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@Import({DynamicDataSourceConfig.class})
public class MoacIpfsApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MoacIpfsApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MoacIpfsApplication.class);
	}
}
