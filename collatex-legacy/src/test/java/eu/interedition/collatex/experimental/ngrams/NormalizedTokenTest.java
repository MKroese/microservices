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

import org.junit.Assert;
import org.junit.Test;

import eu.interedition.collatex.experimental.ngrams.data.NormalizedToken;
import eu.interedition.collatex.experimental.ngrams.data.Token;
import eu.interedition.collatex2.interfaces.INormalizedToken;

public class NormalizedTokenTest {
  @Test
  public void testABC() {
    final Token token = new Token("A", "NotNormalized!...", 1);
    final INormalizedToken result = NormalizedToken.normalize(token);
    Assert.assertEquals("notnormalized", result.getNormalized());
  }

  @Test
  public void testPunctuationToken() {
    final Token token = new Token("P", "#@$!", 1);
    final INormalizedToken result = NormalizedToken.normalize(token);
    Assert.assertEquals("#@$!", result.getNormalized());
  }
}
