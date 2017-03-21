# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table news (
  id                            bigint auto_increment not null,
  topic                         varchar(255),
  content                       varchar(255),
  image                         varchar(255),
  constraint pk_news primary key (id)
);

create table test_many_to_one (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  otm_id                        bigint not null,
  constraint pk_test_many_to_one primary key (id)
);

create table test_one_to_many (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  constraint pk_test_one_to_many primary key (id)
);

create table users (
  id                            bigint auto_increment not null,
  username                      varchar(255),
  password                      varchar(255),
  permission                    varchar(255),
  constraint pk_users primary key (id)
);

alter table test_many_to_one add constraint fk_test_many_to_one_otm_id foreign key (otm_id) references test_one_to_many (id) on delete restrict on update restrict;
create index ix_test_many_to_one_otm_id on test_many_to_one (otm_id);


# --- !Downs

alter table test_many_to_one drop foreign key fk_test_many_to_one_otm_id;
drop index ix_test_many_to_one_otm_id on test_many_to_one;

drop table if exists news;

drop table if exists test_many_to_one;

drop table if exists test_one_to_many;

drop table if exists users;

