import java.util.LinkedList;

public class NumberSolver
{
	/*
	 * Attempts to solve countdown number problem by brutce force [:-(]
	 * can be given any number of numbers, negatives aswell
	 * any target
	 * a winning Set is held where the first equation matches
	 * the target, if no match found, the closest is given.
	 *
	 * given input of N numbers
	 * 0, 1, ..., N-2 are the numbers used to try and find a solution to target number N-1
	 * e.g. {1,-2,7,4,50,1056} numbers={1,-2,7,4,50} target=1056
	 * returns: ((((1--2)*7)*50)+4) = 1054 [away by 2]
	 */
	
	private String bestEquation;
	
	public NumberSolver(int[] numbers, int target) throws Exception
	{
		/* calculate all permutations for length 1..N over numbers in
		 * for each permutation put into set over all operations {0,1,2,3}
		 * check against target if == then put Set into winning array
		 */
		
		// bestOffset is the differnce between equation result and the target
		// if a new equation difference < bestOffset then it is a closer result
		int bestOffset = Integer.MAX_VALUE;
		
		int numberOfNumbers = numbers.length - 1;	
		
		// generate all the sets of numbers of size 1 .. N
		// e.g. generateNumberSets(1,2,3) :- {{1}, {2}, {3}, {1,2}, {1, 3}, {2,3} ,{1,2,3}}
		LinkedList<int[]> allSets = generateNumberSets(numbers);
		
		// loop through each set of numbers,
		// permutate, 
		// try against all operators
		// try to beat bestOffset. 
		//   If offset=0 then the equation = target - so we can break out the search
		search:
		for (int setCount=1; setCount<allSets.size(); setCount++)
		{
			int[] setToCompute = allSets.get(setCount);
			numberOfNumbers = setToCompute.length;
			
			// if numbers = 3, there will be 2 operations: a + b + c
			int numberOfOperations = numberOfNumbers - 1;
			
			// total number of different calculations to make, 4^numberOfOPerations
			int totalCalcs = power(4, numberOfOperations);
		
			// puts all different operation combinations into an array 
			//	- should do this on the fly to save space
			int operators[][] = operatorGenerator(numberOfOperations);
		
			// an array to store of all permutations of numbers
			int[] permNumbers = new int[numberOfNumbers];
		
			
			// generate permutations
			int[] indices;
			PermutationGenerator x = new PermutationGenerator (numberOfNumbers);
			while (x.hasMore ())
			{
				indices = x.getNext ();
				for (int i = 0; i < indices.length; i++)
					permNumbers[i] = setToCompute[indices[i]];
			
				// now use each permutation against all operators
				for (int i=0; i<totalCalcs; i++)
				{
					// move operations from big array to small one for processing
					int[] operations = new int[numberOfOperations];
					for (int j=0; j<numberOfOperations; j++)
						operations[j] = operators[j][i];
			
					// make new Set of array of numbers and the current operations
					Set temp = new Set(permNumbers, operations);
					
					if (temp.divisionOk())
					{	
						// check if division didn't return non int answer
						if (temp.getAnswer() < target)
						{
							if (target - temp.getAnswer() < bestOffset)
							{
								// temp set is closer to target than the previous equations
								bestOffset = target - temp.getAnswer();
								bestEquation = temp.toString() + " [away by " + bestOffset + "]";
							}
						}
						else if (temp.getAnswer() > target)
						{
							if (temp.getAnswer() - target < bestOffset)
							{
								// temp set is closer to target than the previous equations
								bestOffset = temp.getAnswer() - target;
								bestEquation = temp.toString() + " [away by " + bestOffset + "]";
							}
						}
						else
						{
							// temp set = target
							bestOffset = 0;
							bestEquation = temp.toString();
							break search;
						} //else	
					} //if tempdivison ok
				}
			}
		}
	}
	
	public String getWinner()
	{  return bestEquation;  }
	
	private static LinkedList<int[]> generateNumberSets(int[] numbers)
	{
		int numOfSets = power(2, numbers.length);
		LinkedList<int[]> result = new LinkedList<int[]>();
		for (int i=1; i<=numOfSets; i++)
		{
			int[] pointers = binaryAdder(i, numbers.length);
			
			// count number of pointers
			int countPtr = 0;
			for (int j=0; j<numbers.length; j++)
				if (pointers[j] == 1)
					countPtr++;
			
			int[] arrayToAdd = new int[countPtr];
			int count=0;
			for (int j=0; j<numbers.length; j++)
			{
				if (pointers[j] == 1)
				{
					arrayToAdd[count] = numbers[j];
					count++;
				}
			}
			result.add(arrayToAdd);
		}
		
		return result;
	}
	
	
	// returns for e.g. 001 010 011 100 101 111
	// used to select numbers where there is a 1 from an array
	// helps when getting permutations over all sizes of N
	private static int[] binaryAdder(int num, int length)
	{
		int[] result = new int[length];
		for (int i=0; i<length; i++)
			result[i] = 0;
		
		int[] units = new int[length];
		for (int i=0; i<length; i++)
			units[i] = power(2, length-1-i);
		
		int remaining = num;
		for (int i=0; i<length; i++)
		{
			if (remaining > units[i])
			{
				result[i] = 1;
				remaining -= units[i];
			}
		}
		return result;
	}
	
	// n is the number of operators needed
	private static int[][] operatorGenerator(int n)
	{
		if (n>0)
		{
			int fourN = power(4, n);
			int count=0;
			int[] oCount = new int[n];
			for (int i=0; i<n; i++)
				oCount[i] = 0;
		
			int[][] returnArr = new int[n][fourN];
	
			while (count < fourN)
			{
				for (int i=0; i<n; i++)
					returnArr[i][count] = oCount[i];
			
				oCount[n-1]++;
			
				for (int i=n-1; i>0; i--)
				{
					if (oCount[i] == 4)
					{
						oCount[i] = 0;
						oCount[i-1]++;
					}
				}
				count++;
			}
			return returnArr;
		}
		
		return null;
	}	
	
	private static int factorial(int n)
	{	return factorial(n, 1);	}
	
	private static int factorial(int n, int ans)
	{
		if (n>1)
			return factorial(n-1, ans*n);
			
		return ans;			
	}
	
	private static int power(int i, int n)
	{
		// computes i^n for n>=0, i>=0
		if (n>1)
			return i*power(i, n-1);
		else if (n==0)
			return 1;
		return i;
	}
		
		
}