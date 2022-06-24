package ll1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public abstract class Analysis {

	protected Grammar grammar;
	private boolean reverseDerivationDuringCreation;
	
	protected Analysis(Grammar grammar, boolean reverseDerivationDuringCreation) {
		this.grammar = grammar;
		this.reverseDerivationDuringCreation = reverseDerivationDuringCreation;
	}
	
	public Result analyse(String word) {
		Stack<Configuration> configurationStack = new Stack<>();
		configurationStack.push(getStartingConfiguration(word));
		
		while(!configurationStack.isEmpty()) {
			Configuration currentConfiguration = configurationStack.peek();
			if(currentConfiguration.isAccepted()) {
				break;
			}
			
			if(currentConfiguration.hasNext()) {
				Configuration nextConfiguration = currentConfiguration.getNext();
				if(nextConfiguration.isFitting()) {
					configurationStack.push(nextConfiguration);
				}
			}
			else if(currentConfiguration.canStep()) {
				currentConfiguration.doStep();
			}
			else {
				return new Result(new Stack<>());
			}
		}
		
		return new Result(configurationStack);
	}
	
	public class Result{
		private boolean wordAccepted;
		private List<String> derivation;
		
		private Result(Stack<Configuration> configurations) {
			this.wordAccepted = !configurations.isEmpty();
			this.derivation = new ArrayList<>();
			
			while(!configurations.isEmpty()) {
				Configuration configuration = configurations.pop();
				if(!configuration.isAccepted()) {
					derivation.add(configuration.toString());
				}
			}
			if(reverseDerivationDuringCreation) {
				Collections.reverse(derivation);
			}
		}

		public boolean isWordAccepted() {
			return wordAccepted;
		}

		public List<String> getDerivation() {
			return derivation;
		}
	}
	
	protected abstract Configuration getStartingConfiguration(String word);
}
