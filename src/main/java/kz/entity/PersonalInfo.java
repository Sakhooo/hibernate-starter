package kz.entity;

import kz.converter.BirthdayConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PersonalInfo implements Serializable {
  @Serial
  private static final long serialVersionUID = -7065247582099888100L;
  private String firstname;
  private String lastname;
//  @Column(name = "birth_date")
//  @Convert(converter = BirthdayConverter.class)
  private LocalDate birthDate;


}
