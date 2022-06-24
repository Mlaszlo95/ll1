package ll1;


public class LL1AnalysiserForSolutaion extends Analysis {

	protected LL1AnalysiserForSolutaion(Grammar grammar) {
		super(grammar, true);
	}


	/**
	 *
	 * @param word
	 * @return
	 */
	@Override
	protected Configuration getStartingConfiguration(String word) {
		return new LL1SimpleConfig(grammar.getStart().toString(), word);
	}

	//implementáció
	private class LL1SimpleConfig implements Configuration{

		private String OriginalRem;
		private String deriveredWord;

		private LL1SimpleConfig(String deriveredWord, String originalRem) {
			this.OriginalRem = originalRem;
			this.deriveredWord = deriveredWord;
		}

		/**
		 *
		 * @return
		 */
		@Override
		public boolean isAccepted() {
			return OriginalRem.isEmpty() && deriveredWord.isEmpty();
		}

		/**
		 *
		 * @return
		 */
		@Override
		public boolean hasNext() {
			return hasRuleFor(deriveredWord, OriginalRem);
		}

		/**
		 *
		 * @return
		 */
		@Override
		public Configuration getNext() {
			String nextWord = apply(deriveredWord, OriginalRem);
			return new LL1SimpleConfig(nextWord, OriginalRem);
		}

		/**
		 *
		 * @return
		 */
		@Override
		public boolean isFitting() {
			return true;
		}

		/**
		 *
		 * @return
		 */
		@Override
		public boolean canStep() {
			if(OriginalRem.isEmpty() || deriveredWord.isEmpty()) {
				return false;
			}
			return OriginalRem.charAt(0) == deriveredWord.charAt(0);
		}

		/**
		 *
		 */
		@Override
		public void doStep() {
			OriginalRem = OriginalRem.substring(1);
			deriveredWord = deriveredWord.substring(1);
		}

		/**
		 *
		 * @return
		 */
		@Override
		public String toString() {
			return deriveredWord.charAt(0) + " -> " + getRuleFor(deriveredWord, OriginalRem);
		}
	}

	/**
	 *
	 * @param mainWord
	 * @param remainWord
	 * @return
	 */
	private String apply(String mainWord, String remainWord) {
		if(!hasRuleFor(mainWord, remainWord)) return mainWord;
		
		String left = mainWord.substring(0, 1);
		String right = getRuleFor(mainWord, remainWord);
	
		return mainWord.replaceFirst(left, right);
	}

	/**
	 *
	 * @param mainWord
	 * @param remainWord
	 * @return
	 */
	private boolean hasRuleFor(String mainWord, String remainWord) {
		if(mainWord.isEmpty()) {
			return false;
		}
		
		Character left = mainWord.charAt(0);


		if(!grammar.getProduction().containsKey(left)) return false;
		
		for(String ruleRight : grammar.getProduction().get(left)) {
			if(ruleRight.charAt(0) == remainWord.charAt(0)) return true;
		}
		
		return false;
	}
	
	private String getRuleFor(String mainWord, String remainWord) {
		Character left = mainWord.charAt(0);
		for(String right : grammar.getProduction().get(left)) {
			if(right.charAt(0) == remainWord.charAt(0)) return right;
		}
		
		return null;
	}
}
