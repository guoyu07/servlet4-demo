module de.holisticon.servlet4demo.okhttpclient {

  requires de.holisticon.servlet4demo.util;

  requires spring.core;
  requires spring.expression;
  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires spring.beans;
  requires spring.context;
  requires spring.web;

  requires okhttp;
  requires jackson.annotations;
  requires slf4j.api;


  exports de.holisticon.servlet4demo.okhttpclient;
}