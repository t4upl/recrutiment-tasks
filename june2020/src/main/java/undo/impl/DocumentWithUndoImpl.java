package undo.impl;

import undo.ChangeFactory;
import undo.DocumentWithSetUndoManager;
import undo.DocumentWithSetUndoManagerFactory;
import undo.DocumentWithUndo;
import undo.UndoManager;
import undo.UndoManagerFactory;

public class DocumentWithUndoImpl implements DocumentWithUndo {

  private final DocumentWithSetUndoManager documentWithSetUndoManager;
  private final UndoManager undoManager;

  DocumentWithUndoImpl(DocumentWithSetUndoManagerFactory documentWithSetUndoManagerFactory,
    ChangeFactory changeFactory, UndoManagerFactory undoManagerFactory,
    int bufferSize) {
    this.documentWithSetUndoManager = documentWithSetUndoManagerFactory
      .createDocumentWithSetUndoManager(changeFactory);
    this.undoManager = undoManagerFactory.createUndoManager(documentWithSetUndoManager, bufferSize);
    documentWithSetUndoManager.setUndoManager(undoManager);
  }

  @Override
  public void delete(int pos, String s) {
    documentWithSetUndoManager.delete(pos, s);
  }

  @Override
  public void insert(int pos, String s) {
    documentWithSetUndoManager.insert(pos, s);
  }

  @Override
  public void setDot(int pos) {
    documentWithSetUndoManager.setDot(pos);
  }

  @Override
  public boolean canUndo() {
    return undoManager.canUndo();
  }

  @Override
  public void undo() {
    undoManager.undo();
  }

  @Override
  public boolean canRedo() {
    return undoManager.canRedo();
  }

  @Override
  public void redo() {
    undoManager.redo();
  }
}
