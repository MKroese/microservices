/*
 * NMerge is Copyright 2009-2011 Desmond Schmidt
 *
 * This file is part of NMerge. NMerge is a Java library for merging
 * multiple versions into multi-version documents (MVDs), and for
 * reading, searching and comparing them.
 *
 * NMerge is free software: you can redistribute it and/or modify
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

package au.edu.uq.nmerge.mvd;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Set;

/**
 * Store and create matches for an MVD. Matches are runs within
 * the whole MVD and may have been generated by a search or comparison
 * etc. Matches, like chunks, designate runs that are to be treated
 * specially, e.g. coloured differently. However, unlike chunks, they
 * don't normally cover the whole version.
 */
public class Hit<T> {
  /**
   * the data of the chunk
   */
  protected List<T> realData;

  /**
   * offset within the version where the match starts
   */
  int offset;
  /**
   * length of the match
   */
  int length;
  /**
   * match version
   */
  Witness version;
  /**
   * used in toString for formatting the match
   */
  String shortName;
  /**
   * a flag to assist comparison with other matches
   */
  boolean found;
  /**
   * what the status of the match is, what it represents
   */
  ChunkState state;

  /**
   * @param version   the version in which this match occurs
   * @param offset    the index within the version where the match starts
   * @param length    the length of the match
   * @param shortName short name of the version
   * @param state     the state of the matched text
   */
  Hit(Witness version, int offset, int length, String shortName,
      ChunkState state) {
    super();
    this.offset = offset;
    this.shortName = shortName;
    this.length = length;
    this.version = version;
    this.state = state;
  }

  /**
   * Create a Match. In this version of Match we just store
   * the offset and length in a certain file.
   *
   * @param offset    offset in bytes from the start of the file
   * @param length    length of the match
   * @param version   the version it belongs to
   * @param shortName the short name (siglum) of the version
   */
  public Hit(int offset, int length, Witness version,
             String shortName) {
    super();
    this.length = length;
    this.offset = offset;
    this.version = version;
    this.shortName = shortName;
    this.state = ChunkState.NONE;
  }

  /**
   * Generate a match or a list of Match objects. This is called
   * by search when a match is found that terminates in some pair.
   *
   * @param len      the length of the match
   * @param versions the versions in which to build the match
   * @param endPair  the last Pair in which the match occurs
   * @param endIndex the offset within the endPair in which
   *                 the match occurs.
   * @param multiple true if multiple matches are desired
   * @return a list of Match objects
   */
  static <T> List<Hit<T>> createHits(int len, Set<Witness> versions,
                                     Collation<T> collation, int endPair, int endIndex,
                                     boolean multiple, ChunkState state) throws Exception {
    List<Hit<T>> hits = Lists.newArrayList();
    for (Witness i : versions) {
      // start from one byte after the match
      int offset = endIndex + 1;
      // and add on the length of all relevant
      // pairs up to the first one
      for (int j = endPair - 1; j >= 0; j--) {
        Match<T> p = collation.getMatches().get(j);
        if (p.contains(i)) {
          offset += p.length();
        }
      }
      // now offset is len plus the real offset
      offset -= len;
      String shortName = i.siglum;
      Hit<T> m = new Hit<T>(i, offset, len, shortName, state);
      hits.add(m);
      if (!multiple) {
        break;
      }
    }
    return hits;
  }

  /**
   * Get the start offset of the match in its version
   *
   * @return the offset within its version
   */
  int getStartOffset() {
    return offset;
  }

  /**
   * Get the length of the match in bytes
   *
   * @return the length of the match
   */
  int getLength() {
    return length;
  }

  /**
   * Get the version of this match
   *
   * @return the version id
   */
  public Witness getVersion() {
    return version;
  }

  /**
   * Concatenate two lists of Match objects
   *
   * @param first  the first array
   * @param second the second array
   * @return the concatenated list
   */
  static <T> List<Hit<T>> merge(List<Hit<T>> first, List<Hit<T>> second) {
    return Lists.newArrayList(Iterables.concat(first, second));
  }

  /**
   * Set the found flag. We use this to skip over certain matches
   * that exist in several locations
   *
   * @param found true or false
   */
  public void setFound(boolean found) {
    this.found = found;
  }

  /**
   * Has this match already been found?
   *
   * @return true if it is
   */
  public boolean isFound() {
    return found;
  }

  /**
   * This overrides the superclass method. Tell us if two matches
   * are equivalent.
   *
   * @param other the other match to compare with this one
   * @return true if they are
   */
  public boolean equals(Hit<T> other) {
    if (this.version != other.version
            || this.length != other.length
            || this.offset != other.offset) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Convert to a string in the form that will be parsed by the
   * parsing constructor
   *
   * @return the match expressed as a String
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append('[');
    sb.append(version.siglum + ":");
    sb.append("Offset " + Integer.toString(offset) + ":");
    sb.append("Length " + Integer.toString(length) + ":");
    sb.append(state.toString());
    sb.append("]");
    return sb.toString();
  }
}
