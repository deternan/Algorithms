package Weka_KNN;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;

/*
 * Jar
 * pentaho.jar
 * weka-dev-3.7.6.jar
 * 
 * source:
 * https://www.programcreek.com/2013/01/use-k-nearest-neighbors-knn-classifier-in-java/
 * 
 */

public class weka_KNN 
{

	public static BufferedReader readDataFile(String filename) 
	{
		BufferedReader inputReader = null;
 
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}
 
		return inputReader;
	}
 
	public static void main(String[] args) throws Exception 
	{
		//BufferedReader datafile = readDataFile("C:\\Users\\Barry.Ke\\Desktop\\ads.txt");
		BufferedReader datafile = readDataFile("C:\\Users\\Barry.Ke\\Desktop\\eTube sub\\word2vec\\subtitle_word2vec.vec - 複製.arff");
		
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
		
		//System.out.println(data.instance(0));
		//System.out.println(data.instance(1));
		
		// Class information
		System.out.println(data.classAttribute().name());
		
		//do not use first and second
		Instance test_A = data.instance(0);
		Instance test_B = data.instance(4);
		Instance test_C = data.instance(9);
		data.delete(0);
		data.delete(4);
		data.delete(9);		
		
		Classifier ibk = new IBk();		
		ibk.buildClassifier(data);
 
		double classA = ibk.classifyInstance(test_A);
		double classB = ibk.classifyInstance(test_B);
		double classC = ibk.classifyInstance(test_C);
		
		System.out.println("Test first: " + classA);
		System.out.println("Test second: " + classB);
		System.out.println("Test second: " + classC);
	}
}
