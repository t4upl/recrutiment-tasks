package undo.impl;

import java.lang.reflect.Field;
import undo.Document;
import undo.UndoManager;

public class TestUtils {

  private static final String CONTENT_FIELD = "content";
  private static final String DOCUMENT_FIELD = "document";
  private static final String UNDO_MANAGER = "undoManager";

  //TODO to be replaced by getContent() once it will be introduced to Document interface
  static String getContent(Object object) throws NoSuchFieldException, IllegalAccessException {
    return (String)getField(object, CONTENT_FIELD);
  }

  static Object getField(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
    Field field = object.getClass().getDeclaredField(fieldName);
    field.setAccessible(true);
    return field.get(object);
  }

  static void setUndoManager(Document document, UndoManager undoManager)
    throws NoSuchFieldException, IllegalAccessException {
    Field field = document.getClass().getDeclaredField(UNDO_MANAGER);
    field.setAccessible(true);
    field.set(document, undoManager);
  }

}
