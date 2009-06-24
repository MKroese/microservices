package eu.interedition.collatex.visualization;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.sd_editions.collatex.permutations.Matches;
import com.sd_editions.collatex.permutations.TranspositionDetection;
import com.sd_editions.collatex.permutations.Tuple2;
import com.sd_editions.collatex.permutations.collate.Transposition;

import eu.interedition.collatex.collation.GapDetection;
import eu.interedition.collatex.collation.Match;
import eu.interedition.collatex.collation.NonMatch;
import eu.interedition.collatex.collation.sequences.MatchSequence;
import eu.interedition.collatex.matching.Collation;

public class Visualization {

  public static Modifications getModifications(Collation collation) {
    List<Transposition> transpositions = Visualization.determineTranspositions(collation.getMatchSequencesForBase(), collation.getMatchSequencesForWitness());
    List<Modification> modificationList = Visualization.determineModifications(collation.getMatches(), collation.getNonMatches());
    Modifications modifications = new Modifications(modificationList, transpositions, collation.getMatches());
    return modifications;
  }

  public static List<Modification> determineModifications(Set<Match> permutation, List<NonMatch> determineNonMatches) {
    List<Modification> modifications = Lists.newArrayList();
    modifications.addAll(Matches.getWordDistanceMatches(permutation));
    modifications.addAll(GapDetection.analyseVariants(determineNonMatches));
    return modifications;
  }

  public static List<Transposition> determineTranspositions(List<MatchSequence> matchSequencesForBase, List<MatchSequence> matchSequencesForWitness) {
    List<Tuple2<MatchSequence>> matchSequenceTuples = TranspositionDetection.calculateSequenceTuples(matchSequencesForBase, matchSequencesForWitness);
    List<Tuple2<MatchSequence>> possibleTranspositionTuples = TranspositionDetection.filterAwayRealMatches(matchSequenceTuples);
    List<Transposition> transpositions = TranspositionDetection.createTranspositions(possibleTranspositionTuples);
    return transpositions;
  }

}