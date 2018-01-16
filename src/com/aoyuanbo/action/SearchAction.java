package com.aoyuanbo.action;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class SearchAction {
	
	private File indexFile;
	private IndexSearcher searcher;
	
	
	
	public void setIndexFile(File indexFile) {
		this.indexFile = indexFile;
	}



	public IndexSearcher getSearcher() {
		return searcher;
	}



	/**
	 * 
	 * @param searchText ��ѯ������
	 * @throws Exception
	 */
	public TopDocs search(String searchText,String field) throws Exception{

		//����Directory����ȡ����
		Directory directory=FSDirectory.open(indexFile.toPath());
		//����IndexReader����ȡ����
		IndexReader reader=DirectoryReader.open(directory);
		//��������������
		searcher=new IndexSearcher(reader);
		//������ѯ�ִ���
		Analyzer analyzer=new StandardAnalyzer();
		//������ѯ������
		QueryParser queryParser=new QueryParser(field, analyzer);
		Query query=queryParser.parse(searchText);
		TopDocs docs=searcher.search(query, 10);
//		System.out.println(docs.totalHits);
//		for (ScoreDoc scoreDoc : docs.scoreDocs) {
//
//			Document document=searcher.doc(scoreDoc.doc);
//
//			System.out.println(document.get("address"));
//		}
		reader.close();
		directory.close();
		return docs;
	}
}