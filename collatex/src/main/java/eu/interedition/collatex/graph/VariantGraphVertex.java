package eu.interedition.collatex.graph;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import eu.interedition.collatex.Token;
import eu.interedition.collatex.Witness;
import eu.interedition.collatex.input.SimpleToken;
import org.neo4j.graphdb.Node;

import java.util.Iterator;
import java.util.Set;

import static com.google.common.collect.Iterables.getFirst;
import static com.google.common.collect.Iterables.transform;
import static java.util.Collections.singleton;
import static org.neo4j.graphdb.Direction.INCOMING;
import static org.neo4j.graphdb.Direction.OUTGOING;

/**
 * @author <a href="http://gregor.middell.net/" title="Homepage">Gregor Middell</a>
 */
public class VariantGraphVertex extends GraphVertex<VariantGraph> {
  private static final String TOKEN_REFERENCE_KEY = "tokenReferences";
  private static final String RANK_KEY = "rank";

  public VariantGraphVertex(VariantGraph graph, Node node) {
    super(graph, node);
  }

  public VariantGraphVertex(VariantGraph graph, Set<Token> tokens) {
    this(graph, graph.getDatabase().createNode());
    setTokens(tokens);
  }

  public Iterable<VariantGraphEdge> incoming() {
    return incoming(null);
  }

  public Iterable<VariantGraphEdge> incoming(Set<Witness> witnesses) {
    return Iterables.filter(transform(node.getRelationships(GraphRelationshipType.PATH, INCOMING), graph.getEdgeWrapper()), VariantGraphEdge.createTraversableFilter(witnesses));
  }

  public Iterable<VariantGraphEdge> outgoing() {
    return outgoing(null);
  }

  public Iterable<VariantGraphEdge> outgoing(Set<Witness> witnesses) {
    return Iterables.filter(transform(node.getRelationships(GraphRelationshipType.PATH, OUTGOING), graph.getEdgeWrapper()), VariantGraphEdge.createTraversableFilter(witnesses));
  }

  public Iterable<VariantGraphTransposition> transpositions() {
    return transform(node.getRelationships(GraphRelationshipType.TRANSPOSITION), graph.getTranspositionWrapper());
  }

  public Set<Token> tokens() {
    return tokens(null);
  }

  public Set<Token> tokens(Set<Witness> witnesses) {
    final Set<Token> tokens = graph.getTokenMapper().map(getTokenReferences());
    if (witnesses != null && !witnesses.isEmpty()) {
      for (Iterator<Token> tokenIt = tokens.iterator(); tokenIt.hasNext(); ) {
        final Token token = tokenIt.next();
        if (!witnesses.contains(token.getWitness())) {
          tokenIt.remove();
        }
      }
    }
    return tokens;
  }

  public Set<Witness> witnesses() {
    final Set<Witness> witnesses = Sets.newHashSet();
    for (Token token : tokens()) {
      witnesses.add(token.getWitness());
    }
    return witnesses;
  }

  public void add(Iterable<Token> tokens) {
    final Set<Token> tokenSet = Sets.newHashSet(tokens());
    Iterables.addAll(tokenSet, tokens);
    setTokens(tokenSet);
  }

  public void setTokens(Set<Token> tokens) {
    setTokenReferences(graph.getTokenMapper().map(tokens));
  }

  public int getRank() {
    return (Integer) node.getProperty(RANK_KEY);
  }

  public void setRank(int rank) {
    node.setProperty(RANK_KEY, rank);
  }

  public int[] getTokenReferences() {
    return (int[]) node.getProperty(TOKEN_REFERENCE_KEY);
  }

  public void setTokenReferences(int... references) {
    node.setProperty(TOKEN_REFERENCE_KEY, references);
  }

  @Override
  public String toString() {
    return Iterables.toString(tokens());
  }

  public static Function<Node, VariantGraphVertex> createWrapper(final VariantGraph in) {
    return new Function<Node, VariantGraphVertex>() {
      @Override
      public VariantGraphVertex apply(Node input) {
        return new VariantGraphVertex(in, input);
      }
    };
  }

  public static final Function<VariantGraphVertex, String> TO_CONTENTS = new Function<VariantGraphVertex, String>() {
    @Override
    public String apply(VariantGraphVertex input) {
      final Set<Witness> witnesses = input.witnesses();
      if (witnesses.isEmpty()) {
        return "";
      }
      final StringBuilder contents = new StringBuilder();
      for (SimpleToken token : Ordering.natural().sortedCopy(Iterables.filter(input.tokens(singleton(getFirst(witnesses, null))), SimpleToken.class))) {
        contents.append(token.getContent()).append(" ");
      }
      return contents.toString().trim();
    }
  };

  public static final Function<VariantGraphVertex,Integer> TO_RANK = new Function<VariantGraphVertex, Integer>() {
    @Override
    public Integer apply(VariantGraphVertex input) {
      return input.getRank();
    }
  };
}
