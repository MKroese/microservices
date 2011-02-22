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

package eu.interedition.collatex2.implementation.vg_alignment;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import eu.interedition.collatex2.implementation.CollateXEngine;
import eu.interedition.collatex2.interfaces.ITokenIndex;
import eu.interedition.collatex2.interfaces.IWitness;
import eu.interedition.collatex2.todo.alternativeindexing.AlternativeWitnessIndex;

public class AlternativeWitnessIndexTest {
  private static final Logger LOG = LoggerFactory.getLogger(AlternativeWitnessIndexTest.class);
  private CollateXEngine factory;

  @Before
  public void setup() {
    factory = new CollateXEngine();
  }
  
  @Test
  public void testBigrams1() {
    final IWitness witnessA = factory.createWitness("A", "the big black cat and the big black rat");
    final ITokenIndex index = new AlternativeWitnessIndex(witnessA, witnessA.getRepeatedTokens());
    // The index should contain all unique n-grams with 
    //   0 or more tokens         occurring multiple times in the witness, and
    //   exactly 1 token (or '#') occurring only once      in the witness
    assertTrue(index.contains("# the"));
    assertFalse(index.contains("big black")); 
    assertTrue(index.contains("black cat"));
    assertTrue(index.contains("cat and"));
    assertTrue(index.contains("and the"));
    assertTrue(index.contains("black rat"));
    assertTrue(index.contains("rat #"));
    // test trigrams
    assertTrue(index.contains("# the big"));
    assertFalse(index.contains("the big black"));
    assertTrue(index.contains("big black cat"));
    assertTrue(index.contains("black cat and"));
    assertTrue(index.contains("cat and the"));
    assertTrue(index.contains("and the big"));
    assertTrue(index.contains("big black rat"));
    assertTrue(index.contains("black rat #"));
  }

  
  //TODO: update expectations!
  @Ignore
  @Test
  public void test1() {
    final IWitness a = factory.createWitness("A", "tobe or not tobe");
    final ITokenIndex index = new AlternativeWitnessIndex(a, a.getRepeatedTokens());
    LOG.debug(index.keys().toString());
    assertEquals(6, index.size());
    assertTrue(index.contains("# tobe"));
    assertTrue(index.contains("tobe or"));
    assertTrue(index.contains("or"));
    assertTrue(!index.contains("or not"));
    assertTrue(index.contains("not"));
    assertTrue(!index.contains("or tobe"));
    assertTrue(index.contains("not tobe"));
    assertTrue(index.contains("tobe #"));
  }

  @Test
  public void test2() {
    final IWitness witnessA = factory.createWitness("A", "the big black");
    final ITokenIndex index = new AlternativeWitnessIndex(witnessA, Lists.newArrayList("the", "big", "black"));
    LOG.debug(index.keys().toString());
    assertEquals(10, index.size());
    assertTrue(index.contains("the"));
    assertTrue(index.contains("big"));
    assertTrue(index.contains("black"));
    assertTrue(index.contains("# the"));
    assertTrue(index.contains("the big"));
    assertTrue(index.contains("big black"));
    assertTrue(index.contains("black #"));
    assertTrue(index.contains("# the big"));
    assertTrue(index.contains("the big black"));
    assertTrue(index.contains("big black #"));
  }


}