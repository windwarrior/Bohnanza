package nl.utwente.bpsd.util;

/**
 * A pair that is immutable and has a certain order.
 * 
 * 
 * 
 * @flop This seemed like a good idea at the time, but proved overkill.
 *             I like to keep it because it is a good class in general, but not 
 *             on the place I used it at.
 * @author lennart
 * @param <L> The left value to be stored in this pair, it is also the first
 *            element that is sorted on.
 * @param <R> The right value to be stored in this pair.
 */
public class ImmutableOrderablePair<L extends Comparable, R extends Comparable> 
    implements Comparable<ImmutableOrderablePair<L, R>> {
    private final L left;
    private final R right;
    
    public ImmutableOrderablePair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * @return the left element of this pair
     */
    public L getLeft() {
        return left;
    }

    /**
     * @return the right element of this pair
     */
    public R getRight() {
        return right;
    }

    /**
     * Compares this pair with the given pair, will first check the left 
     * elements for comparability, if that results in equality, the right 
     * elements are compared.
     * @param t
     * @return 
     */
    @Override
    public int compareTo(ImmutableOrderablePair<L, R> t) {
        int result = this.getLeft().compareTo(t.getLeft());
        
        return result == 0 ? this.getRight().compareTo(t.getRight()) : result;
    }


}
