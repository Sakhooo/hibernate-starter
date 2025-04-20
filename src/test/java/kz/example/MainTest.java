package kz.example;

import kz.entity.*;
import kz.example.util.HibernateTestUtil;
import kz.util.HibernateUtil;
import lombok.Cleanup;
import org.hibernate.FlushMode;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.QueryHints;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;

import javax.persistence.QueryHint;
import javax.persistence.Table;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

class MainTest {


  @Test
  void hqlSelect() {
    try(var sessionFactory = HibernateTestUtil.buildSessionFactory();
        var session = sessionFactory.openSession()) {
      session.beginTransaction();


      String name = "Ivan";
      String company = "Google";

      session.createNamedQuery("findUserByName", User.class);


      var user = session.createQuery(
//              "select u from User u where u.personalInfo.firstname = ?1", User.class)
              "select u from User u " +
                      " join u.company c " +
                      "where u.personalInfo.firstname = :firstname and c.name = :companyName", User.class)
//              .setParameter(1, name)
              .setParameter("firstname", name)
              .setParameter("companyName", company)
              .setFlushMode(FlushMode.COMMIT)
              .setHint(QueryHints.HINT_FETCH_SIZE, "50")
              .list();

      int countRows = session.createQuery("update User u set u.profile = 'asd'").executeUpdate();

      session.createNativeQuery("select u from User u where u.personalInfo.firstname = 'Ivan'", User.class);


      session.getTransaction().commit();
    }
  }

  @Test
  void testH2() {
    try(var sessionFactory = HibernateTestUtil.buildSessionFactory();
        var session = sessionFactory.openSession()) {
      session.beginTransaction();

      var google = Company.builder()
              .name("Google")
              .build();

      session.save(google);

//      var programmer = Programmer.builder()
//              .username("ivan@gmail.com")
//              .language(Language.JAVA)
//              .company(google)
//              .build();
//
//      session.save(programmer);
//
//      var manager = Manager.builder()
//              .username("sveta@gmail.com")
//              .projectName("loan")
//              .company(google)
//              .build();

//      session.save(manager);
      session.flush();

      session.clear();

//      var programmer1 = session.get(Programmer.class, 1L);
      var user = session.get(User.class, 1L);


      session.getTransaction().commit();
    }

  }



  @Test
  void localeInfo() {
    try(var sessionFactory = HibernateUtil.buildSessionFactory();
        var session = sessionFactory.openSession()) {
      session.beginTransaction();

      var company = session.get(Company.class, 3);
//      company.getLocales().add(LocaleInfo.of("ru", "Описание на русском"));
//      company.getLocales().add(LocaleInfo.of("en", "English description"));

      company.getUsers().forEach((k, v) -> System.out.println(v));

      session.getTransaction().commit();
    }
  }



  @Test
  void checkManyToMany() {
    try(var sessionFactory = HibernateUtil.buildSessionFactory();
        var session = sessionFactory.openSession()) {
      session.beginTransaction();

      var user = session.get(User.class, 1L);

      var chat = session.get(Chat.class, 1L);

      var userChat = UserChat.builder()
              .createdAt(Instant.now())
              .createdBy(user.getUsername())
              .build();

      userChat.setUser(user);
      userChat.setChat(chat);

      session.save(userChat);

//      var chat = Chat.builder()
//              .name("Sagadat")
//              .build();

//      user.addChat(chat);
      session.save(chat);

      session.getTransaction().commit();
    }
  }




  @Test
  void checkOneToOne() {

    try(var sessionFactory = HibernateUtil.buildSessionFactory();
        var session = sessionFactory.openSession()) {
      session.beginTransaction();

      User user = null;

      Profile profile = Profile.builder()
              .language("ru")
              .street("Abaya")
              .build();

      profile.serUser(user);

      session.save(user);


//      session.save(profile);




      session.getTransaction().commit();
    }


  }




  @Test
  void oneToMany() {
    @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
    @Cleanup var session = sessionFactory.openSession();

    Company company = session.get(Company.class, 1);
    System.out.println(company);


  }




  @Test
  void checkReflectionApi() {


    PersonalInfo personalInfo = PersonalInfo.builder()


            .build();

    User user = null;

    String tableName = Optional.ofNullable(user.getClass().getAnnotation(Table.class))
            .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
            .orElse(user.getClass().getName());


  }



  @Test
  void addUserToNewCompany() {

    @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
    @Cleanup var session = sessionFactory.openSession();

    session.beginTransaction();

    Company facebook = Company.builder()
            .name("Facebook")
            .build();
    User user = null;

//    user.setCompany(facebook);
//    facebook.getUsers().add(user);
    //либо можем так


    Hibernate.initialize(facebook.getUsers());

    facebook.addUser(user);

    session.save(facebook);


    session.getTransaction().commit();


  }




}
