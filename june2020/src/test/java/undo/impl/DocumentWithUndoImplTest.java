package undo.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import undo.ChangeFactory;
import undo.DocumentWithSetUndoManager;
import undo.DocumentWithSetUndoManagerFactory;
import undo.DocumentWithUndo;
import undo.UndoManager;
import undo.UndoManagerFactory;

@RunWith(MockitoJUnitRunner.class)
public class DocumentWithUndoImplTest {

  private static final int BUFFER_SIZE = 1;

  @Mock
  private DocumentWithSetUndoManagerFactory documentWithSetUndoManagerFactory;

  @Mock
  private ChangeFactory changeFactory;

  @Mock
  private UndoManagerFactory undoManagerFactory;

  @Mock
  private DocumentWithSetUndoManager documentWithSetUndoManager;

  @Mock
  private UndoManager undoManager;

  private DocumentWithUndo documentWithUndo;

  @Before
  public void setUp() throws Exception {
    when(documentWithSetUndoManagerFactory.createDocumentWithSetUndoManager(any()))
      .thenReturn(documentWithSetUndoManager);
    when(undoManagerFactory.createUndoManager(documentWithSetUndoManager, BUFFER_SIZE))
      .thenReturn(undoManager);
    this.documentWithUndo = new DocumentWithUndoImpl(documentWithSetUndoManagerFactory,
      changeFactory, undoManagerFactory, BUFFER_SIZE);
  }

  @Test
  public void delete_shouldDelegateToDocumentDelete() {
    //given
    //intentionally left empty

    //when
    documentWithUndo.delete(0, "foo");


    //then
    verify(documentWithSetUndoManager).delete(0, "foo");
  }

  @Test
  public void insert_shouldDelegateToDocumentInsert() {
    //given
    //intentionally left empty

    //when
    documentWithUndo.insert(0, "foo");

    //then
    verify(documentWithSetUndoManager).insert(0, "foo");
  }

  @Test
  public void setDot_shouldDelegateToDocumentSetDot() {
    //given
    //intentionally left empty

    //when
    documentWithUndo.setDot(0);

    //then
    verify(documentWithSetUndoManager).setDot(0);
  }

  @Test
  public void canUndo_shouldDelegateToUndoManagerCanUndo() {
    //given
    //intentionally left empty

    //when
    documentWithUndo.canUndo();


    //then
    verify(undoManager).canUndo();
  }

  @Test
  public void undo_shouldDelegateToUndoManagerUndo() {
    //given
    //intentionally left empty

    //when
    documentWithUndo.undo();

    //then
    verify(undoManager).undo();
  }

  @Test
  public void canRedo_shouldDelegateToUndoManagerCanRedo() {
    //given
    //intentionally left empty

    //when
    documentWithUndo.canRedo();

    //then
    verify(undoManager).canRedo();
  }

  @Test
  public void redo_shouldDelegateToUndoManagerRedo() {
    //given
    //intentionally left empty

    //when
    documentWithUndo.redo();

    //then
    verify(undoManager).redo();
  }
}