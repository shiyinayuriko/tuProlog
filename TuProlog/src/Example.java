import java.util.Iterator;

import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Theory;
import alice.tuprolog.Var;

public class Example {
	public static void main( String[] args ) {
		try {
			Prolog engine = new Prolog();
			Theory theory = new Theory( Example.class.getClassLoader().getResourceAsStream( "database_example.txt" ) );
			engine.setTheory(theory);
			SolveInfo solution = engine.solve("me(1569691,X).");

			while( solution.isSuccess() ) {
				if( solution.getBindingVars().size() < 1 ) {
					System.out.println(solution.toString());
				} else {
					@SuppressWarnings( "rawtypes" )
					Iterator it = solution.getBindingVars().iterator();
					while( it.hasNext() ) {
						Var type = (Var) it.next();
						System.out.println(type.getName() + ": " + solution.getTerm(type.getName()));
					}
				}
				if( engine.hasOpenAlternatives() ) {
					solution = engine.solveNext();
				} else {
					break;
				}
			}
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}
}
