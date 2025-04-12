package kz.entity;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(of = "language")
public class Profile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "user_id")
//  @PrimaryKeyJoinColumn
  private User user;

  private String street;

  private String language;

  public void serUser(User user) {
    user.setProfile(this);
    this.user = user;
  }

}

