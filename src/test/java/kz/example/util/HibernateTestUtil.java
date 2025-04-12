package kz.example.util;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;

import static kz.util.HibernateUtil.buildConfiguration;

@UtilityClass
public class HibernateTestUtil {

  private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13");

  static {
    postgres.start();
  }

  public static SessionFactory buildSessionFactory() {
    Configuration configuration = buildConfiguration();
    configuration.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
    configuration.setProperty("hibernate.connection.username", postgres.getUsername());
    configuration.setProperty("hibernate.connection.password", postgres.getPassword());


    configuration.configure();
    return configuration.buildSessionFactory();
  }


}
