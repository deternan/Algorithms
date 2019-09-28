package Swing.API;

public class readfile
{
	private String filePath;
	public String stringData[][];
	public int dataNum;					//	全部資料數量(幾筆?)
	public int dimNum;					//  資料特徵數
	public int classNum;				//  資料類別數
	public int dataClass[];				//  每筆資料的類別
	
	public readfile(String filePath)
	{
		this.filePath = filePath;
		ReadFileArff rfa = new ReadFileArff(this.filePath);
		stringData = rfa.returnStringData();
		dataClass = rfa.returnDataClass();
		dataNum = stringData.length;
		dimNum = stringData[0].length;
		classNum = rfa.returnClassNum();
	}
	
}
