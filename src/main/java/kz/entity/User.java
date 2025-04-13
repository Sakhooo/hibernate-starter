package kz.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import kz.converter.BirthdayConverter;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = {"company", "profile", "userChats"})
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User implements BaseEntity<Long>{

  @Id
  @GeneratedValue(generator = "user_gen", strategy = GenerationType.SEQUENCE)
//  @SequenceGenerator(name = "user_gen", sequenceName = "users_id_seq", allocationSize = 1)
  private Long id;

//  @EmbeddedId
  @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date"))
  private PersonalInfo personalInfo;

  @Column(unique = true)
  private String username;


  @Type(type = "com.vladmihalcea.hibernate.type.json.JsonBinaryType")
  private String info;


  @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
  @JoinColumn(name = "company_id")
  private Company company;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  private Profile profile;

  @OneToMany(mappedBy = "user")
  private List<UserChat> userChats = new ArrayList<>();






}
