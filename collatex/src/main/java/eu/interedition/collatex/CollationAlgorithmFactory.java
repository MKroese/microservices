package eu.interedition.collatex;

import eu.interedition.collatex.dekker.DekkerAlgorithm;
import eu.interedition.collatex.dekker.EditGraphTokenLinker;
import eu.interedition.collatex.dekker.TokenLinker;
import eu.interedition.collatex.graph.GraphFactory;
import eu.interedition.collatex.needlemanwunsch.NeedlemanWunschAlgorithm;

import java.util.Comparator;

/**
 * @author <a href="http://gregor.middell.net/" title="Homepage">Gregor Middell</a>
 */
public class CollationAlgorithmFactory {
  
  public static CollationAlgorithm dekker(Comparator<Token> comparator) {
    return new DekkerAlgorithm(comparator);
  }

  public static CollationAlgorithm dekkerExperimental(Comparator<Token> comparator, GraphFactory graphFactory) {
    return new DekkerAlgorithm(comparator, new EditGraphTokenLinker(graphFactory));
  }

  public static CollationAlgorithm needlemanWunsch(Comparator<Token> comparator) {
    return new NeedlemanWunschAlgorithm(comparator);
  }
}
