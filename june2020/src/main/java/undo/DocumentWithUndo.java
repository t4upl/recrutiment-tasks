package undo;

public interface DocumentWithUndo extends Document {

  boolean canUndo();

  void undo();

  boolean canRedo();

  void redo();

}
