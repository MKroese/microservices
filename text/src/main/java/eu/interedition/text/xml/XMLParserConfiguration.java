package eu.interedition.text.xml;

import eu.interedition.text.QName;

public interface XMLParserConfiguration {

  boolean isLineElement(QName name);

  boolean isContainerElement(QName name);

  boolean included(QName name);

  boolean excluded(QName name);

  char getNotableCharacter();

  boolean isNotable(QName name);

  boolean isCompressingWhitespace();

  OffsetDeltaHandler getOffsetDeltaHandler();

  NodePathHandler getNodePathHandler();

}