package undo.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static undo.impl.TestUtils.getContent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import undo.Change;
import undo.ChangeFactory;
import undo.Document;
import undo.UndoManager;

@RunWith(MockitoJUnitRunner.class)
public class DocumentImplTest {

  private static final int MINUS_ONE = -1;

  @Mock
  private ChangeFactory changeFactory;

  @Mock
  private UndoManager undoManager;

  @Mock
  private Change change;

  private Document document;

  @Before
  public void setUp() throws Exception {
    document = new DocumentWithSetUndoManagerImpl(changeFactory);
    TestUtils.setUndoManager(document, undoManager);
  }

  @Test(expected = IllegalStateException.class)
  public void setDot_withPosNegative_shouldThrowException() {
    //given
    //intentionally left empty

    //when
    document.setDot(-1);

    //then
    //exception expected
  }

  @Test(expected = IllegalStateException.class)
  public void setDot_withPosBiggerThanContentAllows_shouldThrowException() {
    //given
    //intentionally left empty

    //when
    document.setDot(1);

    //then
    //exception expected
  }

  @Test
  public void setDot_withPosInsideContent_shouldSetCursorPosition() {
    //given
    document.insert(0, "foo");

    //when
    document.setDot(1);

    //then
    //intentionally left empty
  }

  @Test(expected = IllegalStateException.class)
  public void insert_withPosNegative_shouldThrowException()
    throws NoSuchFieldException, IllegalAccessException {
    //given
    //intentionally left empty

    //when
    document.insert(-1, "foo");

    //then
    //exception expected
  }

  @Test(expected = IllegalStateException.class)
  public void insert_withPosBiggerThanContentAllows_shouldThrowException() {
    //given
    //intentionally left empty

    //when
    document.insert(1, "foo");

    //then
    //exception expected
  }

  @Test
  public void insert_withPosZero_shouldSetStringAtBeginningOfContent()
    throws NoSuchFieldException, IllegalAccessException {
    //given
    when(changeFactory.createInsertion(0, "foo", MINUS_ONE, MINUS_ONE)).thenReturn(change);

    //when
    document.insert(0, "foo");

    //then
    assertEquals("foo", getContent(document));
    verify(undoManager).registerChange(change);
  }

  @Test
  public void insert_withPosAtTheEndOfContent_shouldAddContentAtTheEnd()
    throws NoSuchFieldException, IllegalAccessException {
    //given
    document.insert(0, "foo");

    //when
    document.insert(3, "bar");

    //then
    String content = getContent(document);
    assertEquals("foobar", content);
  }

  @Test
  public void insert_withPosInTheMiddleOfContent_shouldAddContentInTheMiddle()
    throws NoSuchFieldException, IllegalAccessException {
    //given
    document.insert(0, "bar");

    //when
    document.insert(1, "xyz");

    //then
    String content = getContent(document);
    assertEquals("bxyzar", content);
  }

  @Test(expected = IllegalStateException.class)
  public void delete_withPosNegative_shouldThrowException() {
    //given
    //intentionally left empty

    //when
    document.delete(-1, "");

    //then
    //exception expected
  }

  @Test(expected = IllegalStateException.class)
  public void delete_withPosBiggerThanContentAllows_shouldThrowException() {
    //given
    //intentionally left empty

    //when
    document.delete(1, "");

    //then
    //exception expected
  }

  @Test(expected = IllegalStateException.class)
  public void delete_withContentToBeDeletedTooLong_shouldThrowException() {
    //given
    //intentionally left empty

    //when
    document.delete(0, "f");

    //then
    //exception expected
  }


  @Test(expected = IllegalStateException.class)
  public void delete_withContentToBeDeletedNotMatchingContent_shouldThrowException() {
    //given
    document.insert(0, "bar");

    //when
    document.delete(0, "bax");

    //then
    //exception expected
  }

  @Test
  public void delete_withRemoveStringFromBeginning_shouldRemoveStringFromBeginning()
    throws NoSuchFieldException, IllegalAccessException {
    //given
    when(changeFactory.createDeletion(0, "b", MINUS_ONE, MINUS_ONE)).thenReturn(change);
    document.insert(0, "bar");

    //when
    document.delete(0, "b");

    //then
    assertEquals("ar", getContent(document));
    verify(undoManager).registerChange(change);
  }

  @Test
  public void delete_withRemoveStringFromEnd_shouldRemoveStringFromEnd()
    throws NoSuchFieldException, IllegalAccessException {
    //given
    when(changeFactory.createDeletion(2, "r", MINUS_ONE, MINUS_ONE)).thenReturn(change);

    document.insert(0, "bar");

    //when
    document.delete(2, "r");

    //then
    assertEquals("ba", getContent(document));
  }

  @Test
  public void delete_withRemoveStringFromMiddle_shouldRemoveStringFromMiddle()
    throws NoSuchFieldException, IllegalAccessException {
    //given
    document.insert(0, "barxyz");

    //when
    document.delete(1, "arx");

    //then
    assertEquals("byz", getContent(document));
  }


}