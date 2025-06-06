package kz.util;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import kz.converter.BirthdayConverter;
import kz.entity.User;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

  public static SessionFactory buildSessionFactory() {
    Configuration configuration = buildConfiguration();
    configuration.configure();
    return configuration.buildSessionFactory();
  }

  public static Configuration buildConfiguration() {
    Configuration configuration = new Configuration();
    configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
    configuration.addAnnotatedClass(User.class);
    configuration.addAttributeConverter(BirthdayConverter.class, true);
    configuration.registerTypeOverride(new JsonBinaryType());
    return configuration;
  }

}
