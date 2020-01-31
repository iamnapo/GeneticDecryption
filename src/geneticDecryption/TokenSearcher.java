//Napoleon Oikonomou

package geneticDecryption;

import java.io.File;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * Epistefei poses fores vrethike sto keimeno i leksi (miden an den vrethike)
 **/

class TokenSearcher {
	int searchIndex(File indexDir, String queryStr) throws Exception {
		Directory index = FSDirectory.open(indexDir.toPath());
		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		QueryParser parser = new QueryParser("contents", new StemmerAnalyzer(new SimpleAnalyzer()));
		Query query = parser.parse(queryStr);
		TopDocs topDocs = searcher.search(query, 100);
		ScoreDoc[] hits = topDocs.scoreDocs;
		return hits.length;
	}
}
