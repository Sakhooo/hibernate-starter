package kz.dao;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QPredicatie {
  private final List<Predicate> predicates = new ArrayList<>();

  public static QPredicatie builder() {
    return new QPredicatie();
  }

  public <T>  QPredicatie add (T object, Function<T, Predicate> function) {
    if (object != null) {
      predicates.add(function.apply(object));
    }
    return this;
  }


  public Predicate buildAnd() {
    return ExpressionUtils.allOf(predicates);
  }

  public Predicate buildOr() {
    return ExpressionUtils.anyOf(predicates);
  }



}
