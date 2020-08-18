package undo.impl;

import com.google.common.base.Preconditions;
import java.util.Stack;
import undo.Change;
import undo.Document;
import undo.UndoManager;

class UndoManagerImpl implements UndoManager {

  private final int bufferSize;
  private final Document document;
  private final StackWithBuffer changes = new StackWithBuffer();
  private final StackWithBuffer undoneChanges = new StackWithBuffer();

  UndoManagerImpl(int bufferSize, Document document) {
    Preconditions.checkNotNull(document, "UndoManagerImpl must have document declared");
    this.bufferSize = bufferSize;
    this.document = document;
  }

  @Override
  public void registerChange(Change change) {
    changes.push(change);
    undoneChanges.clear();
  }

  @Override
  public boolean canUndo() {
    return changes.size() > 0;
  }

  @Override
  public void undo() {
    if (!canUndo()) {
      throw new IllegalStateException("Cannot perform undo, check canUndo() method");
    }
    Change change = changes.pop();

    try {
      revertChangesAndPushToUndoneChanges(change);
    } catch (IllegalStateException e) {
      throw new IllegalStateException("Could not perform undo.", e);
    }
  }

  @Override
  public boolean canRedo() {
    return undoneChanges.size() > 0;
  }

  @Override
  public void redo() {
    if (!canRedo()) {
      throw new IllegalStateException("Cannot perform redo, check canRedo() method");
    }


    Change change = undoneChanges.pop();
    try {
      change.apply(document);
    } catch (IllegalStateException e) {
      throw new IllegalStateException("Could not perform redo.", e);
    }
  }

  private void revertChangesAndPushToUndoneChanges(Change change) {
    change.revert(document);
    undoneChanges.push(change);
  }

  private class StackWithBuffer extends Stack<Change> {
    @Override
    public Change push(Change elt) {
      super.push(elt);
      while (this.size() > bufferSize) {
        this.removeElementAt(0);
      }
      return elt;
    }
  }

}
