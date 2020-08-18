package undo;

public interface DocumentWithSetUndoManagerFactory {

  DocumentWithSetUndoManager createDocumentWithSetUndoManager(ChangeFactory changeFactory);
}
