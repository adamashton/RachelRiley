public class NumberSolverTest
{
	public static void main(String [] args)
	{
		try
		{
			// put numbers into array
			int[] numbers = new int[args.length-1];
			for (int i=0; i<args.length-1; i++)
				numbers[i] = Integer.parseInt(args[i]);
			int target = Integer.parseInt(args[args.length-1]);
			
			NumberSolver solver = new NumberSolver(numbers, target);
			System.out.println(solver.getWinner());
		}
		catch (Exception e)
		{ System.out.println(e); }
	}
}
