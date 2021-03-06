package de.holisticon.servlet4demo.jettyclient.jetty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.Phaser;

import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http2.api.Stream;
import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.http2.frames.PushPromiseFrame;
import org.eclipse.jetty.util.Callback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StreamListenerAdapterTest {

  private StreamListener sut;

  private Phaser phaser;
  private HeadersFrame headersFrame;
  private DataFrame dataFrame;
  private Stream stream;
  private PushPromiseFrame pushPromiseFrame;
  private Callback callback;

  @BeforeEach
  public void setUp() {
    phaser = mock(Phaser.class);
    headersFrame = mock(HeadersFrame.class);
    dataFrame = mock(DataFrame.class);
    stream = mock(Stream.class);
    pushPromiseFrame = mock(PushPromiseFrame.class);
    callback = mock(Callback.class);
    sut = new StreamListener(phaser);

    when(headersFrame.isEndStream()).thenReturn(Boolean.TRUE);
    when(dataFrame.isEndStream()).thenReturn(Boolean.TRUE);
    when(pushPromiseFrame.getMetaData()).thenReturn(new MetaData(HttpVersion.HTTP_2, null));
  }

  @Test
  public void testOnHeaders() {
    sut.onHeaders(stream, headersFrame);
    verify(phaser, times(1)).arrive();
  }

  @Test
  public void testOnHeadersIsNotEndStream() {
    when(headersFrame.isEndStream()).thenReturn(Boolean.FALSE);
    sut.onHeaders(stream, headersFrame);
    verify(phaser, times(0)).arrive();
  }

  @Test
  public void testOnPush() {
    Stream.Listener listener = sut.onPush(stream, pushPromiseFrame);
    assertEquals(sut, listener);
  }

  @Test
  public void testOnData() {
    sut.onData(stream, dataFrame, callback);
    verify(callback, times(1)).succeeded();
    verify(dataFrame, times(1)).isEndStream();
    verify(phaser, times(1)).arrive();
  }

  @Test
  public void testOnDataIsNotEndStream() {
    when(dataFrame.isEndStream()).thenReturn(Boolean.FALSE);
    sut.onData(stream, dataFrame, callback);
    verify(callback, times(1)).succeeded();
    verify(dataFrame, times(1)).isEndStream();
    verify(phaser, times(0)).arrive();
  }
}
