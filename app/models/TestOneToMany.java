package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class TestOneToMany extends Model {

  @Id
  @Constraints.Min(10)
  public Long id;

  public String name;

  @OneToMany(mappedBy = "otm")
  public List<TestManyToOne> mto;

  public static Finder<Long, TestOneToMany> find = new Finder<Long, TestOneToMany>(TestOneToMany.class);

  public static TestOneToMany findById(Long id) {
    return find.byId(id);
  }

  public static TestOneToMany findTest(Long id) {
    return find.fetch("mto").where().idEq(id).findUnique();
  }

}
