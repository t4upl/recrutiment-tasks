package undo.impl;

import undo.Change;
import undo.Document;

public class DeletionChange implements Change {

  private final int pos;
  private final String s;
  private static final String DELETION = "Deletion";

  DeletionChange(int pos, String s) {
    this.pos = pos;
    this.s = s;
  }

  @Override
  public String getType() {
    return DELETION;
  }

  @Override
  public void apply(Document doc) {
    doc.delete(pos, s);
  }

  @Override
  public void revert(Document doc) {
    doc.insert(pos, s);
  }
}
