package undo.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import undo.Change;
import undo.Document;
import undo.UndoManager;

@RunWith(MockitoJUnitRunner.class)
public class UndoManagerImplTest {

  @Mock
  private Change change;

  @Mock
  private Change change2;

  @Mock
  private Document document;

  private UndoManager undoManager;

  @Before
  public void setUp() throws Exception {
    undoManager = new UndoManagerImpl(1, document);
  }

  @Test
  public void registerChange_whenNoPlaceInBufferDeleteOldestChange() {
    //given
    undoManager.registerChange(change);

    //when
    undoManager.registerChange(change2);

    //then
    undoManager.undo();
    verify(change, never()).revert(document);
    verify(change2).revert(document);
    assertFalse(undoManager.canUndo());
  }


  @Test
  public void canUndo_withNewUndoManager_ShouldReturnFalse() {
    //given
    //intentionally left empty

    //when
    boolean canUndo = undoManager.canUndo();

    //then
    assertFalse(canUndo);
  }

  @Test
  public void canUndo_whenNoChangesToUndo_ShouldReturnFalse() {
    //given
    undoManager.registerChange(change);

    //when
    boolean canUndo = undoManager.canUndo();

    //then
    assertTrue(canUndo);
  }


  @Test(expected = IllegalStateException.class)
  public void undo_whenNoChangesToUndo_shouldThrowExcpetion() {
    //given
    //intentionally left empty

    //when
    undoManager.undo();

    //then
    //expected exception
  }

  @Test
  public void undo_whenChangesPresentToUndo_shouldApplyChanges() {
    //given
    undoManager.registerChange(change);

    //when
    undoManager.undo();

    //then
    verify(change).revert(document);
  }

  @Test(expected = IllegalStateException.class)
  public void undo_whenIllegalStateExceptionThrownOnUndo_shouldApplyChanges() {
    //given
    undoManager.registerChange(change);
    doThrow(new IllegalStateException()).when(change).revert(document);

    //when
    undoManager.undo();

    //then
    //expected exception
  }


  @Test
  public void canRedo_whenNoChangesToRedo_shouldReturnFalse() {
    //given
    //intentionally left empty

    //when
    boolean canRedo = undoManager.canRedo();

    //then
    assertFalse(canRedo);
  }

  @Test
  public void canRedo_whenChangesToRedoPresent_shouldReturnTrue() {
    //given
    undoManager.registerChange(change);
    undoManager.undo();

    //when
    boolean canRedo = undoManager.canRedo();

    //then
    assertTrue(canRedo);
  }

  @Test(expected = IllegalStateException.class)
  public void redo_whenNoChangesToRedoPresent_shouldThrowException() {
    //given
    //intentionally left empty

    //when
    undoManager.redo();

    //then
    //exception expected
  }

  @Test(expected = IllegalStateException.class)
  public void redo_whenIllegalStateExceptionThrownOnRevertingChange_shouldThrowException() {
    //given
    //intentionally left empty
    undoManager.registerChange(change);
    doThrow(new IllegalStateException()).when(change).apply(document);
    undoManager.undo();

    //when
    undoManager.redo();

    //then
    //exception expected
  }

  @Test
  public void redo_whenChangeToBeRedonePresent_shouldRevertChange() {
    //given
    //intentionally left empty
    undoManager.registerChange(change);
    undoManager.undo();

    //when
    undoManager.redo();

    //then
    verify(change).apply(document);
    verify(change).revert(document);
  }


}