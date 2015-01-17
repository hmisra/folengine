import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Stack;


/**
 * @author hmisra@usc.edu
 * 
 */
public class Agent {

	public static void main(String[] args) throws IOException {
		String kbFile="input.txt";
		KnowledgeBase kb=read(kbFile);
		boolean result=backwardChaining(kb);
		BufferedWriter w = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(new File("output.txt"))));
		if(result==true)
		{
			w.write("TRUE");
		}
		else
		{
			w.write("FALSE");
		}
		w.close();
		//		kb.printKB();


	}

	public static KnowledgeBase read(String path) throws IOException
	{
		File f=new File(path);
		BufferedReader br=new BufferedReader(new FileReader(f));
		int count=0;
		int numberOfSentences=0;
		String query="";
		ArrayList<Sentence> sentences=new ArrayList<Sentence> ();

		String str="";
		while(( str=br.readLine())!=null)
		{
			if(count==0)
			{
				query=str.replaceAll("\\s+","");
			}
			else if(count==1)
			{
				numberOfSentences=Integer.parseInt(str.replaceAll("\\s+",""));
			}
			else
			{
				sentences.add(new Sentence(str.replaceAll("\\s+",""),count));

			}
			count++;

		}
		br.close();
		KnowledgeBase kb=CreateKB(query, sentences, numberOfSentences);

		return kb;
	}

	private static KnowledgeBase CreateKB(String query,
			ArrayList<Sentence> sentences, int numberOfSentences) {
		Query queryObject=extractQuery(query);
		ArrayList<ComplexSentence> complexSentences=extractComplexSentences(sentences, numberOfSentences);
		ArrayList<DefiniteClause> definiteClauses=extractDefiniteClauses(sentences, numberOfSentences);
		KnowledgeBase kb=new KnowledgeBase(complexSentences, definiteClauses, queryObject);
		return kb;
	}

	private static ArrayList<ComplexSentence> extractComplexSentences(
			ArrayList<Sentence> sentences, int numberOfSentences) {

		ArrayList<ComplexSentence> complexSentences=new ArrayList<ComplexSentence>();
		for(Sentence query: sentences)
		{				


			if(query.getSentence().indexOf('=')!=-1)

			{
				Premise premise=new Premise();
				Conclusion conclusion=new Conclusion();

				String [] parts=query.getSentence().split("=");
				String[] premiseSentences=parts[0].split("&");
				String conclusionSentence=parts[1].substring(1);
				for(String sentence : premiseSentences)
				{
					int open=sentence.indexOf('(');
					int close=sentence.indexOf(')');
					int comma=sentence.indexOf(',');
					AtomicSentence atomic;
					if(comma==-1)
					{
						ArrayList<String> values=new ArrayList<String>();
						values.add(sentence.substring(open+1, close));
						atomic=new AtomicSentence(sentence, query.getNumber(),sentence.substring(0,open), 1, values);

					}
					else
					{
						ArrayList<String> values=new ArrayList<String>();
						values.add(sentence.substring(open+1,comma));
						values.add(sentence.substring(comma+1, close));
						atomic=new AtomicSentence(sentence, query.getNumber(),sentence.substring(0,open), 2, values);
					}

					premise.setPremiseSentences(atomic);
				}

				int open=conclusionSentence.indexOf('(');
				int close=conclusionSentence.indexOf(')');
				int comma=conclusionSentence.indexOf(',');
				AtomicSentence atomic;
				if(comma==-1)
				{
					ArrayList<String> values=new ArrayList<String>();
					values.add(conclusionSentence.substring(open+1, close));
					atomic=new AtomicSentence(conclusionSentence, query.getNumber(),conclusionSentence.substring(0,open), 1, values);

				}
				else
				{
					ArrayList<String> values=new ArrayList<String>();
					values.add(conclusionSentence.substring(open+1,comma));
					values.add(conclusionSentence.substring(comma+1, close));
					atomic=new AtomicSentence(conclusionSentence, query.getNumber(),conclusionSentence.substring(0,open), 2, values);
				}

				conclusion.setConclusionSentences(atomic);
				complexSentences.add(new ComplexSentence(premise,conclusion));

			}
		}
		return complexSentences;
	}

	private static ArrayList<DefiniteClause> extractDefiniteClauses(
			ArrayList<Sentence> sentences, int numberOfSentences) {


		ArrayList<DefiniteClause> definiteClauses=new ArrayList<DefiniteClause>();
		for(Sentence query: sentences)
		{				
			if(query.getSentence().indexOf('=')==-1&& query.getSentence().indexOf('&')==-1)

			{

				int open=query.getSentence().indexOf('(');
				int close=query.getSentence().indexOf(')');
				int comma=query.getSentence().indexOf(',');
				AtomicSentence atomic;
				if(comma==-1)
				{
					ArrayList<String> values=new ArrayList<String>();
					values.add(query.getSentence().substring(open+1, close));
					atomic=new AtomicSentence(query.getSentence(), query.getNumber(),query.getSentence().substring(0,open), 1, values);

				}
				else
				{
					ArrayList<String> values=new ArrayList<String>();
					values.add(query.getSentence().substring(open+1,comma));
					values.add(query.getSentence().substring(comma+1, close));
					atomic=new AtomicSentence(query.getSentence(), query.getNumber(),query.getSentence().substring(0,open), 2, values);
				}

				DefiniteClause d=new DefiniteClause();
				d.setPremise(atomic);
				definiteClauses.add(d);
			}
		}

		return definiteClauses;
	}

	private static Query extractQuery(String query) {

		int open=query.indexOf('(');
		int close=query.indexOf(')');
		int comma=query.indexOf(',');
		AtomicSentence atomic;
		if(comma==-1)
		{
			ArrayList<String> values=new ArrayList<String>();
			values.add(query.substring(open+1, close));
			atomic=new AtomicSentence(query, -1,query.substring(0,open), 1, values);

		}
		else
		{
			ArrayList<String> values=new ArrayList<String>();
			values.add(query.substring(open+1,comma));
			values.add(query.substring(comma+1, close));
			atomic=new AtomicSentence(query, -1,query.substring(0,open), 2, values);
		}

		Query q=new Query();
		q.setQuery(atomic);
		return q;


	}

	private static Substitution unification(AtomicSentence x, AtomicSentence y)
	{
		if(x.getNumberOfArguments()==y.getNumberOfArguments() && x.getPredicateName().compareTo(y.getPredicateName())==0)
		{
			if(x.getNumberOfArguments()==1)
			{
				if(x.getValues().get(0).compareTo("x")==0 && y.getValues().get(0).compareTo("x")!=0)
				{
					return new Substitution("x", y.getValues().get(0));
				}
				else if(x.getValues().get(0).compareTo("x")!=0 && y.getValues().get(0).compareTo("x")==0)
				{
					return new Substitution("x", x.getValues().get(0));
				}
				else if(x.getValues().get(0).compareTo(y.getValues().get(0))==0)
				{
					return new Substitution(x.getValues().get(0), x.getValues().get(0));
				}
				else
				{
					return null;
				}
			}
			else if(x.getNumberOfArguments()==2)
			{
				if(x.getValues().get(0).compareTo("x")==0 && y.getValues().get(0).compareTo("x")!=0 && x.getValues().get(1).compareTo(y.getValues().get(1))==0)
				{
					return new Substitution("x", y.getValues().get(0));
				}
				else if(y.getValues().get(0).compareTo("x")==0 && x.getValues().get(0).compareTo("x")!=0 && x.getValues().get(1).compareTo(y.getValues().get(1))==0)
				{
					return new Substitution("x", x.getValues().get(0));
				}
				else if(y.getValues().get(1).compareTo("x")==0 && x.getValues().get(1).compareTo("x")!=0 && x.getValues().get(0).compareTo(y.getValues().get(0))==0)
				{
					return new Substitution("x", x.getValues().get(1));
				}
				else if(x.getValues().get(1).compareTo("x")==0 && y.getValues().get(1).compareTo("x")!=0 && x.getValues().get(0).compareTo(y.getValues().get(0))==0)
				{
					return new Substitution("x", y.getValues().get(1));
				}
				else if(x.getValues().get(0).compareTo(y.getValues().get(0))==0&& x.getValues().get(1).compareTo(y.getValues().get(1))==0)
				{
					return new Substitution(x.getValues().get(0), x.getValues().get(0),x.getValues().get(1), x.getValues().get(1));
				}
				//code to handle case for (x, value) and (Value, x)
				else
				{
					return null;
				}
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}


	}

	/**
	 * Function to return the atomic sentence with the substituted value
	 * @param var
	 * @param value
	 * @param s
	 * @return
	 */
	private static AtomicSentence substitute(String var, String value, AtomicSentence s)
	{

		ArrayList<String> values=s.getValues();
		for(int i=0;i<s.getValues().size();i++)
		{

			if(s.getValues().get(i).compareTo(var)==0)
			{
				values.set(i, value);
				return new AtomicSentence(s.getSentence(), s.getSentenceNumber(), s.getPredicateName(), s.getNumberOfArguments(), values);
			}
		}
		return s;
	}

	//a function to extract the atomic sentences that we need to look upon (conclusion + atomic sentences) from the KB
	private static ArrayList<AtomicSentence> getDomain(KnowledgeBase kb)
	{
		ArrayList<AtomicSentence> domain =new ArrayList<AtomicSentence>();
		for(int i=0;i<kb.getComplexSentences().size();i++)
		{
			ArrayList<AtomicSentence> conclusions=kb.getComplexSentences().get(i).getConclusion().getConclusionSentences();
			for(AtomicSentence con:conclusions)
			{
				domain.add(con);

			}
		}
		for(int i=0;i<kb.getDefiniteClauses().size();i++)
		{		
			domain.add(kb.getDefiniteClauses().get(i).getPremise());
		}
		return domain;
	}

	// a function to return the premise of a given atomic sentence (conclusion) from the KB

	private static ArrayList<AtomicSentence> getPremise(KnowledgeBase kb, int sentenceNumber, Substitution s)
	{
		//chances of optimization in KB data structure to find the premise optimally, finding the sentence number quickly by adding the sentence number up in the hierarchy
		ArrayList<AtomicSentence> premises=new ArrayList<AtomicSentence>();
		for(int i=0;i<kb.getComplexSentences().size();i++)
		{
			ArrayList<AtomicSentence> premise=kb.getComplexSentences().get(i).getPremise().getPremiseSentences();
			ArrayList<AtomicSentence> conclusion=kb.getComplexSentences().get(i).getConclusion().getConclusionSentences();
			if(premise.get(0).getSentenceNumber()==sentenceNumber)
			{
				
				for(int k=0;k<premise.size();k++)
				{
					AtomicSentence atomic=premise.get(k);
					boolean flag=false;
					if(conclusion.get(0).getNumberOfArguments()==2)
						flag=true;
					if(conclusion.get(0).getSentenceNumber()==sentenceNumber && (conclusion.get(0).getValues().get(0).compareTo("x")==0)||(flag && conclusion.get(0).getValues().get(0).compareTo("x")==0))
						premises.add(substitute(s.getVariable(), s.getValue(), atomic));
					else
						premises.add(atomic);
				}

			}
		}
		return premises;
	}


	private static boolean backwardChaining(KnowledgeBase kb)
	{
		Stack s=new Stack();
		ArrayList<AtomicSentence> domain=getDomain(kb);
		s.push(kb.getQuery().getQuery());
		Substitution substitution=null;
		for(int i=0;i<domain.size();i++)
		{
			if(unification((AtomicSentence) s.peek(), domain.get(i))!=null)
			{
				substitution=unification((AtomicSentence) s.peek(), domain.get(i));
				ArrayList<AtomicSentence> premises=getPremise(kb, domain.get(i).getSentenceNumber(),substitution);
				s.pop();
				for(AtomicSentence as:premises)
				{
					s.push(as);
				}
				break;
			}
		}
		while(substitution!=null && s.size()!=0)
		{
			int counter=0;
			for(int i=0;i<domain.size();i++)
			{
				if(unification((AtomicSentence) s.peek(), domain.get(i))!=null)
				{
					counter=1;
					substitution=unification((AtomicSentence) s.peek(), domain.get(i));
					ArrayList<AtomicSentence> premises=getPremise(kb, domain.get(i).getSentenceNumber(),substitution);
					s.pop();
					for(AtomicSentence as:premises)
					{
						as.getPredicateName();
						s.push(as);
					}
					break;
				}
			}
			if(counter==0)
			{
				substitution=null;
			}
		}
		if (s.size()==0)
		{
			return true;
		}
		else 
		{
			return false;
		}

	}



}
