package Swing.API;

public class readfile
{
	private String filePath;
	public String stringData[][];
	public int dataNum;					//	������Ƽƶq(�X��?)
	public int dimNum;					//  ��ƯS�x��
	public int classNum;				//  ������O��
	public int dataClass[];				//  �C����ƪ����O
	
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
