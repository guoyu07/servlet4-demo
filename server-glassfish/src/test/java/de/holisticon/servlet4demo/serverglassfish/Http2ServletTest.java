package de.holisticon.servlet4demo.serverglassfish;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.PushBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class Http2ServletTest {

  private Http2Servlet sut;

  private HttpServletRequest request;
  private HttpServletResponse response;

  private StringWriter stringWriter = new StringWriter();
  private PushBuilder pushBuilder;

  @Before
  public void setUp() {
    sut = new Http2Servlet();

    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    pushBuilder = mock(PushBuilder.class);
    PrintWriter printWriter = new PrintWriter(stringWriter);

    when(pushBuilder.path(anyString())).thenReturn(pushBuilder);
    when(pushBuilder.addHeader(anyString(), anyString())).thenReturn(pushBuilder);
    when(request.newPushBuilder()).thenReturn(pushBuilder);
    try {
      when(response.getWriter()).thenReturn(printWriter);
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testDoGet() {
    try {
      sut.doGet(request, response);
      verify(response, times(1)).setContentType("text/html;charset=UTF-8");
      verify(response, times(1)).getWriter();
      assertEquals("<html>" +
                       "<img src='images/cat.jpg'>" +
                       "<p>Image by <a href=\"https://flic.kr/p/HPf9R1\">" +
                       "Andy Miccone</a></p>" +
                       "<p>License: <a href=\"https://creativecommons.org/" +
                       "publicdomain/zero/1.0/\">" +
                       "CC0 1.0 Universal (CC0 1.0) \n" +
                       "Public Domain Dedication</a></p>" +
                       "</html>", stringWriter.toString());
      verify(pushBuilder, times(2)).push();
    } catch (ServletException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
  }
}
