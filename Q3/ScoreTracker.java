// You are developing a student score tracking system that keeps track of scores from different assignments. The
// ScoreTracker class will be used to calculate the median score from the stream of assignment scores. The class
// should have the following methods:
//  ScoreTracker() initializes a new ScoreTracker object.
//  void addScore(double score) adds a new assignment score to the data stream.
//  double getMedianScore() returns the median of all the assignment scores in the data stream. If the number
// of scores is even, the median should be the average of the two middle scores.
// Input:
// ScoreTracker scoreTracker = new ScoreTracker();
// scoreTracker.addScore(85.5); // Stream: [85.5]
// scoreTracker.addScore(92.3); // Stream: [85.5, 92.3]
// scoreTracker.addScore(77.8); // Stream: [85.5, 92.3, 77.8]
// scoreTracker.addScore(90.1); // Stream: [85.5, 92.3, 77.8, 90.1]
// double median1 = scoreTracker.getMedianScore(); // Output: 88.9 (average of 90.1 and 85.5)
// scoreTracker.addScore(81.2); // Stream: [85.5, 92.3, 77.8, 90.1, 81.2]
// scoreTracker.addScore(88.7); // Stream: [85.5, 92.3, 77.8, 90.1, 81.2, 88.7]
// double median2 = scoreTracker.getMedianScore(); // Output: 86.95 (average of 88.7 and 85.5)

package Q3;

import java.util.PriorityQueue;

public class ScoreTracker {
    private PriorityQueue<Double> lowerHalf; // Max heap
    private PriorityQueue<Double> upperHalf; // Min heap

    public ScoreTracker() {
        lowerHalf = new PriorityQueue<>((a, b) -> Double.compare(b, a)); // Max heap comparator
        upperHalf = new PriorityQueue<>(); // Min heap
    }

    public void addScore(double score) {
        // Add the score to the appropriate heap
        if (lowerHalf.isEmpty() || score <= lowerHalf.peek()) {
            lowerHalf.offer(score);
        } else {
            upperHalf.offer(score);
        }

        // Balance the heaps to maintain median property
        if (lowerHalf.size() > upperHalf.size() + 1) {
            upperHalf.offer(lowerHalf.poll());
        } else if (upperHalf.size() > lowerHalf.size()) {
            lowerHalf.offer(upperHalf.poll());
        }
    }

    public double getMedianScore() {
        if (lowerHalf.size() == upperHalf.size()) {
            // If the number of scores is even, the median is the average of the two middle scores
            return (lowerHalf.peek() + upperHalf.peek()) / 2.0;
        } else {
            // Otherwise, the median is the middle score
            return lowerHalf.peek();
        }
    }

    public static void main(String[] args) {
        ScoreTracker scoreTracker = new ScoreTracker();
        scoreTracker.addScore(85.5);
        scoreTracker.addScore(92.3);
        scoreTracker.addScore(77.8);
        scoreTracker.addScore(90.1);
        double median1 = scoreTracker.getMedianScore(); // Output: 88.9
        System.out.println("Median 1: " + median1);

        scoreTracker.addScore(81.2);
        scoreTracker.addScore(88.7);
        double median2 = scoreTracker.getMedianScore(); // Output: 86.95
        System.out.println("Median 2: " + median2);
    }
}
