package undo.impl;

import undo.Change;
import undo.Document;

public class InsertionChange implements Change {

  private final int pos;
  private final String s;
  private static final String INSERTION = "Insertion";

  InsertionChange(int pos, String s) {
    this.pos = pos;
    this.s = s;
  }

  @Override
  public String getType() {
    return INSERTION;
  }

  @Override
  public void apply(Document doc) {
    doc.insert(pos, s);
  }

  @Override
  public void revert(Document doc) {
    doc.delete(pos, s);
  }
}
