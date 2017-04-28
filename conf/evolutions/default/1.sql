# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table group_product (
  name                      varchar(255) not null,
  constraint pk_group_product primary key (name))
;

create table product (
  id                        varchar(40) not null,
  name                      varchar(255),
  description               varchar(255),
  cost                      double,
  amount                    double,
  constraint pk_product primary key (id))
;

create table user (
  email                     varchar(255) not null,
  is_admin                  boolean,
  password_hash             varchar(255),
  salt                      varchar(255),
  constraint pk_user primary key (email))
;

create sequence group_product_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists group_product;

drop table if exists product;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists group_product_seq;

drop sequence if exists user_seq;

