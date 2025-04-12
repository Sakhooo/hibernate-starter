package kz.entity;

import lombok.*;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "users")
@EqualsAndHashCode(exclude = "users")
@Builder
@Entity
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  @Builder.Default
  @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
//  @OrderBy("username Desc, personalInfo.lastname asc")
//  @SortNatural
  @MapKey(name = "username")
  private Map<String , User> users = new HashMap<>();

  @Builder.Default
  @ElementCollection
  @CollectionTable(name = "company_local")
  private List<LocaleInfo> locales = new ArrayList<>();


  public void addUser(User user) {
    users.put(user.getUsername(), user);
    user.setCompany(this);
  }


}
