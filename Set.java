public class Set
{
	/* the set of numbers and operations that are performed on them.
	 * e.g.
	 * numbers = {a,b,c,d}
	 * operations = {0, 1, 2} [0=addition, 1=subtract, 2=product, 3=division]
	 * result: ((a+b)-c)*d
	 *  operations.length must be 1 less than numbers
	 * ! NB if a division operation returns a non int then a boolean is set
	 * to disallow return of result as it is not counted in countdown
	 * must check for divisionOk
	 */
	 
	int result;
	boolean division_ok = true;
	int[] numbers;
	int[] operations;
	
	public Set(int[] numbersIn, int[] operationsIn) throws Exception
	{
		if (numbersIn.length-1 != operationsIn.length)
			throw new Exception("Operations must be 1 less than numbers given.");
		else
		{
			numbers = numbersIn;
			operations = operationsIn;	
		
			// perform operation calculation on first number			
			result = numbers[0];
			
			for (int i=0; i<operations.length && division_ok; i++)
			{
				// calculate answer
				switch (operations[i])
				{
					case 0: result = result + numbers[i+1]; break;
					case 1: result = result - numbers[i+1]; break;
					case 2: result = result * numbers[i+1]; break;
					case 3: 
						setDivisionBool(result, numbers[i+1]);
						if (division_ok)
							result = result / numbers[i+1];
						break;
					default: System.out.println("Error: operation was not of set {0,1,2,3}"); break;
				}	
			}			
		}		// else
	}
		public int getAnswer() throws Exception
		{
			if (division_ok)
				return result;
			else
				throw new Exception("Division on eqwuation failed: use divisonOK");
		}
		
		// sets boolean, stops divide by 0, non integer results, and fractionResults
		private void setDivisionBool(int number1, int number2)
		{
			if ((number2 > 0) && (number1 >= number2) && (number1 % number2 == 0))
				division_ok = true;
			else		
				division_ok = false;
		}
		public boolean divisionOk()
		{  return division_ok;  }
		
		public String toString()
		{
			String output = "" + numbers[0];
			for (int i=0; i<operations.length; i++)
			{
				switch (operations[i])
				{
					case 0: output += "+"; break;
					case 1: output += "-"; break;
					case 2: output += "*"; break;
					case 3: output += "/"; break;
				}
				
				output = "(" + output + numbers[i+1] + ")";
			}
			output += " = " + result;
			return output;
		}
}