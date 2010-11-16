/**
 * CollateX - a Java library for collating textual sources,
 * for example, to produce an apparatus.
 *
 * Copyright (C) 2010 ESF COST Action "Interedition".
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.interedition.collatex2.output.alignmenttable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.interedition.collatex2.implementation.CollateXEngine;
import eu.interedition.collatex2.interfaces.ColumnState;
import eu.interedition.collatex2.interfaces.IAlignmentTable;
import eu.interedition.collatex2.interfaces.IColumn;
import eu.interedition.collatex2.interfaces.IInternalColumn;
import eu.interedition.collatex2.interfaces.INormalizedToken;
import eu.interedition.collatex2.interfaces.IWitness;
import eu.interedition.collatex2.legacy.alignmenttable.Column3;

//NOTE: this is an implementation test!
//NOTE: constructors are called directly!
//NOTE: Not only read only methods are called!
public class ColumnTest {
  private static CollateXEngine factory;

  @BeforeClass
  public static void setup() {
    factory = new CollateXEngine();
  }

  @Test
  public void testFirstToken() {
    final IWitness witness = factory.createWitness("A", "a test string");
    final IAlignmentTable table = factory.align(witness);
    List<IColumn> columns = table.getColumns();
    IColumn column1 = columns.get(0);
    assertTrue(column1.containsWitness("A"));
    assertFalse(column1.containsWitness("B"));
    assertEquals(ColumnState.MATCH, column1.getState());
  }

  @Test
  public void testAddVariant() {
    final IWitness witness = factory.createWitness("A", "first");
    final IWitness witnessB = factory.createWitness("B", "second");
    final IWitness witnessC = factory.createWitness("C", "third");
    final IAlignmentTable table = factory.align(witness, witnessB, witnessC);
    List<IColumn> columns = table.getColumns();
    IColumn column1 = columns.get(0);
    //NOTE: containsWitness method is only used in tests!
    assertTrue(column1.containsWitness("A"));
    assertTrue(column1.containsWitness("B"));
    assertTrue(column1.containsWitness("C"));
    assertFalse(column1.containsWitness("D"));
    assertEquals(ColumnState.VARIANT, column1.getState());
    //NOTE: getVariants method was only used in this test!
//  final List<INormalizedToken> variants = column1.getVariants();
//  assertEquals(3, variants.size());
//  assertEquals("first", variants.get(0).getNormalized());
//  assertEquals("second", variants.get(1).getNormalized());
//  assertEquals("third", variants.get(2).getNormalized());

  }

  @Test
  public void testAddMatch() {
    final IWitness a = factory.createWitness("A", "match");
    final IWitness b = factory.createWitness("B", "match");
    IAlignmentTable table = factory.align(a, b);
    IColumn column = table.getColumns().get(0);
    Assert.assertTrue(column.containsWitness("A"));
    Assert.assertTrue(column.containsWitness("B"));
    Assert.assertFalse(column.containsWitness("C"));
    Assert.assertEquals(ColumnState.MATCH, column.getState());
//  final List<INormalizedToken> variants = column.getVariants();
//  Assert.assertEquals(1, variants.size());
//  Assert.assertEquals("match", variants.get(0).getNormalized());
  }
  
  //TODO: Remove Column3 class!
  
  @Test(expected = NoSuchElementException.class)
  public void testGetWordNonExistingGivesException() {
    final IWitness witness = factory.createWitness("A", "a test string");
    final INormalizedToken word = witness.getTokens().get(0);
    final IInternalColumn column = new Column3(word, 1);
    column.getToken("B");
  }



  @Test
  public void testMixedColumn() {
    final IWitness witness = factory.createWitness("A", "match");
    final IWitness witnessB = factory.createWitness("B", "match");
    final IWitness witnessC = factory.createWitness("C", "variant");
    final INormalizedToken word = witness.getTokens().get(0);
    final INormalizedToken wordB = witnessB.getTokens().get(0);
    final INormalizedToken wordC = witnessC.getTokens().get(0);
    final IInternalColumn column = new Column3(word, 1);
    column.addMatch(wordB);
    column.addVariant(wordC);
    final List<INormalizedToken> variants = column.getVariants();
    Assert.assertEquals(2, variants.size());
    Assert.assertEquals("match", variants.get(0).getNormalized());
    Assert.assertEquals("variant", variants.get(1).getNormalized());
    Assert.assertTrue(column.containsWitness("A"));
    Assert.assertTrue(column.containsWitness("B"));
    Assert.assertTrue(column.containsWitness("C"));
    Assert.assertFalse(column.containsWitness("D"));
    Assert.assertEquals(ColumnState.VARIANT, column.getState());
  }
}
