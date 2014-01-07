import java.util.Iterator;

import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Theory;
import alice.tuprolog.Var;

public class Example
{
	public static void main( String[] args )
	{
		try
		{
			// load prolog data
			Theory theory = new Theory( Example.class.getClassLoader().getResourceAsStream( "database_example.txt" ) );
			
			// combine prolog with our data
			Prolog engine = new Prolog();
			engine.setTheory(theory);
			
			// search X in our me's (module element) with student id 1569691
			SolveInfo solution = engine.solve( "me(1569691,X)." );

			// iterate over all found solutions
			while( solution.isSuccess() )
			{
				if( solution.getBindingVars().size() < 1 )
				{
					// found no binding for result variable X
					System.out.println( solution.toString() );
				}
				else 
				{
					// found binding for X
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
