package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name = "news")
public class NewsModel extends Model {

  @Id
  @Constraints.Min(10)
  public Long id;

  @Constraints.Required(message = "Topic is required")
  public String topic;

  @Constraints.Required(message = "Content is required")
  public String content;

  public Finder<Long, NewsModel> find = new Finder<Long, NewsModel>(NewsModel.class);

}
