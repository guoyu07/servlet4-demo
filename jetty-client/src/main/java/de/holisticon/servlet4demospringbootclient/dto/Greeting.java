package de.holisticon.servlet4demospringbootclient.dto;

public class Greeting {
  private long id;
  private String content;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String toString() {
    return String.format("Greetings, %s (%d)", content, id);
  }
}
