package ll1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grammar {

	private List<Character> termSymbols;
	private List<Character> nontermSymbols;
	private Character start;
	private Map<Character, List<String>> production;
	private int count;
	
	public Grammar() {
		nontermSymbols = new ArrayList<>();
		termSymbols = new ArrayList<>();

		start = null;

		count = 0;
		production = new HashMap<>();
	}
	
	public void addTermSymbol(Character symbol) throws ExceptionOfGrammar {
		if(termSymbols.contains(symbol)) throw new ExceptionOfGrammar(symbol + " mar resze Terminal symbolumnak!");
		if(nontermSymbols.contains(symbol)) throw new ExceptionOfGrammar(symbol + " mar resze nem Terminal symbolumnak");
		
		termSymbols.add(symbol);
	}
	
	public void addProductionRule(Character leftSide, String rightSide) throws ExceptionOfGrammar {
		if(production.containsKey(leftSide)) {
			for(String right : production.get(leftSide)) {
				if(right.startsWith(rightSide.substring(0, 1))) throw new ExceptionOfGrammar("Ez a resz mar  production rule allá tartozik, " + leftSide + " kezdodik " + rightSide.charAt(0) + "!");
			}
		}
		else {
			production.put(leftSide, new ArrayList<>());
		}
		
		production.get(leftSide).add(rightSide);
		++count;
	}

	public void addNonTermSymbol(Character symbol) throws ExceptionOfGrammar {
		if(termSymbols.contains(symbol)) {
			throw new ExceptionOfGrammar(symbol + " mar resze Terminal symbolumnak!");
		}
		if(nontermSymbols.contains(symbol)) {
			throw new ExceptionOfGrammar(symbol + " mar resze nem Terminal symbolumnak!");
		}

		nontermSymbols.add(symbol);
	}

	public void setStart(Character symbol) throws ExceptionOfGrammar {
		if(!nontermSymbols.contains(symbol)) throw new ExceptionOfGrammar(symbol + " nem nonterminal szimbolum!");
		start = symbol;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Terminals: ");
		if(!termSymbols.isEmpty()) {
			for(Character symbol : termSymbols) {
				builder.append(symbol);
				builder.append(", ");
			}
			builder.delete(builder.lastIndexOf(","), builder.length());
		}
		builder.append("\n");
		
		builder.append("Nonterminals: ");
		if(!nontermSymbols.isEmpty()) {
			for(Character symbol : nontermSymbols) {
				builder.append(symbol);
				builder.append(", ");
			}
			builder.delete(builder.lastIndexOf(","), builder.length());
		}

		builder.append("\n");
		builder.append("Kezdo: ");
		builder.append(start);
		builder.append("\n");
		
		builder.append("Rules:\n");
		for(Character leftSide : production.keySet()) {
			for(String rightSide : production.get(leftSide)) {
				builder.append(leftSide);
				builder.append(" -> ");
				builder.append(rightSide);
				builder.append("\n");
			}
		}
		
		return builder.toString();
	}


	//Set-GET-Implement methods
	public Character getStart() {
		return start;
	}

	public int getCount() {
		return count;
	}

	public boolean isTerminalSymbol(Character symbol) {
		return termSymbols.contains(symbol);
	}

	public boolean isNonterminalSymbol(Character symbol) {
		return nontermSymbols.contains(symbol);
	}

	public Map<Character, List<String>> getProduction() {
		return production;
	}
	
	
}
