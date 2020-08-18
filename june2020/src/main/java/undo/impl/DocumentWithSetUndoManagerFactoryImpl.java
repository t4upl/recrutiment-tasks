package undo.impl;

import undo.ChangeFactory;
import undo.DocumentWithSetUndoManager;
import undo.DocumentWithSetUndoManagerFactory;

public class DocumentWithSetUndoManagerFactoryImpl implements DocumentWithSetUndoManagerFactory {

  @Override
  public DocumentWithSetUndoManager createDocumentWithSetUndoManager(ChangeFactory changeFactory) {
    return new DocumentWithSetUndoManagerImpl(changeFactory);
  }
}
