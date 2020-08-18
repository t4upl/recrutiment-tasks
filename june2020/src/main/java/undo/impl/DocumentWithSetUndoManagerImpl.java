package undo.impl;

import java.util.Optional;
import undo.ChangeFactory;
import undo.DocumentWithSetUndoManager;
import undo.UndoManager;

public class DocumentWithSetUndoManagerImpl implements DocumentWithSetUndoManager {

  private static final int MINUS_ONE = -1;
  private UndoManager undoManager;
  private final ChangeFactory changeFactory;
  private String content = "";

  DocumentWithSetUndoManagerImpl(ChangeFactory changeFactory) {
    this.changeFactory = changeFactory;
  }

  @Override
  public void delete(int pos, String s) {
    throwExceptionIfPositionOutsideOfContent(pos);
    throwExceptionIfPositionOutsideOfContent(pos + s.length());
    checkIfSequenceToBeDeletedAppearInContent(pos, s);

    StringBuilder text = new StringBuilder(content);
    text.replace(pos, pos + s.length(), "");
    content = text.toString();
    getUndoManager().ifPresent((undoManager) ->
      undoManager.registerChange(changeFactory.createDeletion(pos, s, MINUS_ONE, MINUS_ONE)));
  }

  @Override
  public void insert(int pos, String s) {
    throwExceptionIfPositionOutsideOfContent(pos);
    StringBuilder newString = new StringBuilder(content);
    newString.insert(pos, s);
    content = newString.toString();
    getUndoManager().ifPresent((undoManager) ->
      undoManager.registerChange(changeFactory.createInsertion(pos, s, MINUS_ONE, MINUS_ONE)));
  }

  @Override
  public void setDot(int pos) throws IllegalStateException {
    throwExceptionIfPositionOutsideOfContent(pos);
  }

  @Override
  public void setUndoManager(UndoManager undoManager) {
    this.undoManager = undoManager;
  }

  private Optional<UndoManager> getUndoManager() {
    return Optional.ofNullable(undoManager);
  }

  private void throwExceptionIfPositionOutsideOfContent(int pos) {
    if (!isPositionWithinContentRange(pos)) {
      throwIllegalStateException(pos);
    }
  }

  private void throwIllegalStateException(int pos) {
    //TODO add LOGGER
    throw new IllegalStateException(
      "Cannot set dot positon: " + pos + ", content: " + content);
  }

  private boolean isPositionWithinContentRange(int pos) {
    return pos > -1 && pos < content.length() + 1;
  }

  private void checkIfSequenceToBeDeletedAppearInContent(int pos, String s) {
    String contentSubstring = content.substring(pos, pos + s.length());
    if (!contentSubstring.equals(s)) {
      throw new IllegalStateException("String: " + s + " does not appear in content: " + content //
        + " with starting position: " + pos);
    }

  }

}
