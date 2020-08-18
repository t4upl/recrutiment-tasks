package undo;

public interface DocumentWithSetUndoManager extends Document {

  void setUndoManager(UndoManager undoManager);
}
