package kz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Birthday {
  private LocalDate localDate;

    public long getAge() {
      return ChronoUnit.YEARS.between(localDate, LocalDate.now());
    }
}
