package undo.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import undo.ChangeFactory;
import undo.DocumentWithSetUndoManagerFactory;
import undo.DocumentWithUndo;
import undo.UndoManagerFactory;

public class DocumentWithUndoIT {

  private static final int BUFFER_SIZE = 1;
  private DocumentWithUndo documentWithUndo;

  @Before
  public void setUp() throws Exception {
    DocumentWithSetUndoManagerFactory documentWithSetUndoManagerFactory = new DocumentWithSetUndoManagerFactoryImpl();
    ChangeFactory changeFactory = new ChangeFactoryImpl();
    UndoManagerFactory undoManagerFactory = new UndoManagerFactoryImpl();

    documentWithUndo = new DocumentWithUndoImpl(documentWithSetUndoManagerFactory, changeFactory,
      undoManagerFactory, BUFFER_SIZE);
  }

  //This fails because:
  // 1. insert puts Insertion change in changes
  // 2. undo takes Insertion change and applies a deletion change
  // 3. documents see deletion change and put it in changes

  //Solution: we need insert and delete in document interface without changes tracking

  @Test
  public void whenInsertAndUndo_ContentShouldNotChange()
    throws NoSuchFieldException, IllegalAccessException {
    //given
    documentWithUndo.insert(0, "foo");

    //when
    documentWithUndo.undo();

    //then
    assertFalse(documentWithUndo.canUndo());
  }

  @Test
  public void whenInsertAndUndoAndRedo_StateShouldBeTheSameAsAfterInsert()
    throws NoSuchFieldException, IllegalAccessException {
    //given
    //intentionally left empty

    //when
    documentWithUndo.insert(0, "foo");
    documentWithUndo.undo();
    documentWithUndo.redo();

    //then
    assertTrue(documentWithUndo.canUndo());
    assertFalse(documentWithUndo.canRedo());
  }


}