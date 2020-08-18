package undo.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import undo.Change;
import undo.Document;

@RunWith(MockitoJUnitRunner.class)
public class InsertionChangeTest {

  private Change change;
  private static final int POS = 0;
  private static final String S = "";
  private static final String INSERTION = "Insertion";

  @Mock
  private Document document;

  @Before
  public void setUp() throws Exception {
    change = new InsertionChange(POS, S);
  }

  @Test
  public void getType_shouldReturnInsertion() {
    //given
    //intentionally left empty

    //when
    String type = change.getType();

    //then
    assertEquals(INSERTION, type);
  }

  @Test
  public void apply_shouldInsertIntoDocument() {
    //given
    //intentionally left empty

    //when
    change.apply(document);

    //then
    verify(document).insert(POS, S);
  }

  @Test
  public void revert_shouldDeleteFromDocument() {
    //given
    //intentionally left empty

    //when
    change.revert(document);

    //then
    verify(document).delete(POS, S);
  }


}
