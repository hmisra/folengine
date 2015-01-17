import java.util.ArrayList;


public class KnowledgeBase {

	ArrayList<ComplexSentence> complexSentences=new ArrayList<ComplexSentence>();
	ArrayList<DefiniteClause> definiteClauses=new ArrayList<DefiniteClause>();
	Query query;

	public KnowledgeBase(ArrayList<ComplexSentence> complexSentences, ArrayList<DefiniteClause> definiteClauses, Query query) {
		this.complexSentences=complexSentences;
		this.definiteClauses=definiteClauses;
		this.query=query;


	}
	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public ArrayList<ComplexSentence> getComplexSentences() {
		return complexSentences;
	}

	public ComplexSentence getComplexSentences(int i) {
		if(this.complexSentences.size()>i+1 && i>=0)
		{
			return complexSentences.get(i);
		}
		return null;
	}

	public void setComplexSentences(ComplexSentence complexSentences) {
		this.complexSentences.add(complexSentences);
	}

	public ArrayList<DefiniteClause> getDefiniteClauses() {
		return definiteClauses;
	}

	public DefiniteClause getDefiniteClauses(int i) {
		if(this.definiteClauses.size()>i+1 && i>=0)
		{
			return definiteClauses.get(i);
		}
		return null;
	}

	public void setDefiniteClauses(DefiniteClause definiteClauses) {
		this.definiteClauses.add(definiteClauses);
	}	

	public void printKB()
	{
		System.out.println("Complex Sentences");

		for(int i=0;i<complexSentences.size();i++)
		{
			System.out.println("Sentence Details for Sentence "+ i);

			System.out.println("\t Premise : ");
			ArrayList<AtomicSentence> premises=complexSentences.get(i).getPremise().getPremiseSentences();
			for(AtomicSentence a:premises)
			{
				a.printAtomicSentence();
			}
			System.out.println("\t Conclusion : ");
			ArrayList<AtomicSentence> conclusions=complexSentences.get(i).getConclusion().getConclusionSentences();
			for(AtomicSentence a:conclusions)
			{
				a.printAtomicSentence();
			}
		}
		
		
		
		
		System.out.println("Definite Clauses");
		for(int i=0;i<definiteClauses.size();i++)
		{
			System.out.println("Sentence Details for Sentence "+ i);
			
			definiteClauses.get(i).getPremise().printAtomicSentence();System.out.println();
		}
		
		
		System.out.println("Query Details");
		query.getQuery().printAtomicSentence();
	}
}
