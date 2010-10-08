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

package eu.interedition.collatex.experimental.ngrams;

import java.util.List;

import eu.interedition.collatex.experimental.interfaces.WitnessF;
import eu.interedition.collatex.input.Phrase;
import eu.interedition.collatex2.interfaces.IWitness;

public class BiGrams {

  public static List<Subsegment2> getOverlappingBiGrams(final IWitness a, final IWitness b) {
    final BiGramIndexGroup group = BiGramIndexGroup.create(a, b);
    return group.getOverlap();
  }

  public static List<BiGram> getOverlappingBiGramsForWitnessA(final IWitness a, final IWitness b) {
    final BiGramIndexGroup group = BiGramIndexGroup.create(a, b);
    return group.getOverlappingBiGramsForWitnessA();
  }

  // TODO move this to the BiGramIndexGroup!
  public static List<NGram> getUniqueBiGramsForWitnessA(final IWitness a, final IWitness b) {
    final BiGramIndexGroup group = BiGramIndexGroup.create(a, b);
    return group.getUniqueNGramsForWitnessA();
  }

  // TODO this method is not finished!
  // TODO does not work right for multiple groups of bigrams
  public static List<Phrase> getLongestUniquePiecesForWitnessA(final WitnessF a, final WitnessF b) {
    throw new UnsupportedOperationException("NOT YET IMPLEMENTED!");

  }

  // TODO this method is not finished!
  public static List<Phrase> getLongestUniquePiecesForWitnessB(final WitnessF a, final WitnessF b) {
    throw new UnsupportedOperationException("NOT YET IMPLEMENTED!");
    //    final List<Phrase> uniqueBiGramsForWitnessB = getUniqueBiGramsForWitnessB(a, b);
    //    return uniqueBiGramsForWitnessB;
  }

}
