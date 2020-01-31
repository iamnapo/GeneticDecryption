//Napoleon Oikonomou

package geneticDecryption;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.AnalyzerWrapper;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.PorterStemFilter;

public class StemmerAnalyzer extends AnalyzerWrapper {

	private Analyzer baseAnalyzer;

	StemmerAnalyzer(Analyzer baseAnalyzer) {
		super(baseAnalyzer.getReuseStrategy());
		this.baseAnalyzer = baseAnalyzer;
	}

	@Override
	public void close() {
		baseAnalyzer.close();
		super.close();
	}

	@Override
	protected Analyzer getWrappedAnalyzer(String fieldName) {
		return baseAnalyzer;
	}

	@Override
	protected TokenStreamComponents wrapComponents(String fieldName, TokenStreamComponents components) {
		TokenStream tS = components.getTokenStream();
		PorterStemFilter porterStem = new PorterStemFilter(tS);
		return new TokenStreamComponents(components.getTokenizer(), porterStem);
	}
}
