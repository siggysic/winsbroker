package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class TestManyToOne extends Model {

  @Id
  @Constraints.Min(10)
  public Long id;

  public String name;

  @ManyToOne(optional = false)
  public TestOneToMany otm;

}
