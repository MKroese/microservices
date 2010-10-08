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

import com.google.common.collect.Lists;

import eu.interedition.collatex2.interfaces.INormalizedToken;
import eu.interedition.collatex2.interfaces.IWitness;

public class NGram {
  private final List<INormalizedToken> tokens;

  public NGram(final List<INormalizedToken> tokens) {
    this.tokens = tokens;
  }

  // TODO rename parameter "remove" to bigram
  public static NGram create(final BiGram remove) {
    final List<INormalizedToken> tokens = Lists.newArrayList(remove.getFirstToken(), remove.getLastToken());
    return new NGram(tokens);
  }

  public static NGram create(final IWitness aa, final int startPosition, final int endPosition) {
    final List<INormalizedToken> tokens2 = aa.createPhrase(startPosition, endPosition).getTokens();
    return new NGram(tokens2);
  }

  // Note: not too pleased with this method! Not immutable!
  public void add(final BiGram nextBiGram) {
    tokens.add(nextBiGram.getLastToken());
  }

  public String getNormalized() {
    StringBuilder replacementString = new StringBuilder();
    String divider = "";
    for (final INormalizedToken token : tokens) {
      replacementString.append(divider).append(token.getNormalized());
      divider = " ";
    }
    return replacementString.toString();

  }

  // TODO add test for defensive behavior!
  public INormalizedToken getFirstToken() {
    if (isEmpty()) {
      throw new RuntimeException("This ngram is empty!");
    }
    return tokens.get(0);
  }

  //TODO make defensive and add test!
  public INormalizedToken getLastToken() {
    return tokens.get(tokens.size() - 1);
  }

  public boolean isEmpty() {
    return tokens.isEmpty();
  }

  public NGram trim() {
    final List<INormalizedToken> subList = tokens.subList(1, tokens.size() - 1);
    return new NGram(subList);
  }

}
