package kz.example;

import kz.entity.Birthday;
import kz.entity.Company;
import kz.entity.PersonalInfo;
import kz.entity.User;
import kz.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class Main {

  private static final Logger log = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {

    System.out.println("Hello world!");

    try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        Session session = sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();

      Company company = Company.builder()
              .name("google")
              .build();



      User user = User.builder()
              .username("qwer3")
              .personalInfo(PersonalInfo.builder()
                      .birthDate(new Birthday(LocalDate.of(2000, 1, 19)))
              .firstname("Sagadat1")
              .lastname("asdf")

                      .build())
//              .info("""
//                      {
//                      "name": "Sagadat",
//                      "id": "25"
//                      }
//                      """)
              .company(company)
//
              .build();

      log.info("User entity is in transient state, object: {} ", user);

      session.save(company);

      Object unproxy = Hibernate.unproxy(company);

      session.save(user);

      log.trace("User is in persistent state, session {}", transaction);

      session.getTransaction().commit();

      System.out.println("OK!");
    }
  }
}
