public class PermutationGenerator {

  private int[] a;
  private Long numLeft;
  private Long total;

  //-----------------------------------------------------------
  // Constructor. WARNING: Don't make n too large.
  // Recall that the number of permutations is n!
  // which can be very large, even when n is as small as 20 --
  // 20! = 2,432,902,008,176,640,000 and
  // 21! is too big to fit into a Java long, which is
  // why we use BigInteger instead.
  //----------------------------------------------------------

  public PermutationGenerator (int n) {
    if (n < 1) {
      throw new IllegalArgumentException ("Min 1");
    }
    a = new int[n];
    total = getFactorial (n);
    reset ();
  }

  //------
  // Reset
  //------

  public void reset () {
    for (int i = 0; i < a.length; i++) {
      a[i] = i;
    }
    numLeft = new Long(total.toString ());
  }

  //------------------------------------------------
  // Return number of permutations not yet generated
  //------------------------------------------------

  public long getNumLeft () {
    return numLeft;
  }

  //------------------------------------
  // Return total number of permutations
  //------------------------------------

  public long getTotal () {
    return total;
  }

  //-----------------------------
  // Are there more permutations?
  //-----------------------------

  public boolean hasMore () {
    return numLeft.compareTo ((long) 0) == 1;
  }

  //------------------
  // Compute factorial
  //------------------

  private static long getFactorial(int n)
	{	return (long) factorial(n, 1);	}
	
	private static int factorial(int n, int ans)
	{
		if (n>1)
			return factorial(n-1, ans*n);
			
		return ans;			
	}

  //--------------------------------------------------------
  // Generate next permutation (algorithm from Rosen p. 284)
  //--------------------------------------------------------

  public int[] getNext () {

    if (numLeft.equals (total)) {
      numLeft--;
      return a;
    }

    int temp;

    // Find largest index j with a[j] < a[j+1]

    int j = a.length - 2;
    while (a[j] > a[j+1]) {
      j--;
    }

    // Find index k such that a[k] is smallest integer
    // greater than a[j] to the right of a[j]

    int k = a.length - 1;
    while (a[j] > a[k]) {
      k--;
    }

    // Interchange a[j] and a[k]

    temp = a[k];
    a[k] = a[j];
    a[j] = temp;

    // Put tail end of permutation after jth position in increasing order

    int r = a.length - 1;
    int s = j + 1;

    while (r > s) {
      temp = a[s];
      a[s] = a[r];
      a[r] = temp;
      r--;
      s++;
    }

    numLeft--;
    return a;

  }

}