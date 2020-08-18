package undo.impl;

import undo.Change;
import undo.ChangeFactory;

public class ChangeFactoryImpl implements ChangeFactory {

  @Override
  public Change createDeletion(int pos, String s, int oldDot, int newDot) {
    return new DeletionChange(pos, s);
  }

  @Override
  public Change createInsertion(int pos, String s, int oldDot, int newDot) {
    return new InsertionChange(pos, s);
  }

}
