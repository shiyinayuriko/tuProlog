import java.util.Iterator;

import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Theory;
import alice.tuprolog.Var;

public class Solution
{
	public static void main( String[] args )
	{
		try
		{
			// load prolog data
			Theory theory = new Theory( Solution.class.getClassLoader().getResourceAsStream( "database_solution.txt" ) );
			
			// combine prolog with our data
			Prolog engine = new Prolog();
			engine.setTheory(theory);
			
			// some example prolog terms
			String queryPassedModules = "passed(3483045,Y).";
			String queryPassedModule = "passed(3483045,1569691).";
			
			String queryFailedModules = "failed(3483045,Y).";
			String queryFailedModule = "failed(3483045,1569691).";
			
			// maybe final solution
			String passedModule = "passedModule(3483045,1569691).";
			
			SolveInfo solution = engine.solve( passedModule );

			// iterate over all found solutions
			while( solution.isSuccess() )
			{
				if( solution.getBindingVars().size() < 1 )
				{
					// found no binding
					System.out.println( solution.toString() );
				}
				else 
				{
					// found bindings and now print there values
					@SuppressWarnings( "rawtypes" )
					Iterator it = solution.getBindingVars().iterator();
					while( it.hasNext() )
					{
						// print solution with variable and his binding value
						Var type = (Var) it.next();
						System.out.println( type.getName() + ": " + solution.getTerm( type.getName() ) );
					}
				}
				
				if( engine.hasOpenAlternatives() )
				{
					// solve next alternative
					solution = engine.solveNext();
				}
				else
				{
					// stop program
					break;
				}
			}
		} catch( Exception e )
		{
			System.out.println("tuProlog exception:" + e.getMessage());
		}
	}

}
