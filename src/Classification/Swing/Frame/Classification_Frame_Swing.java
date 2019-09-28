package Swing.Frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class Classification_Frame_Swing extends javax.swing.JFrame implements ActionListener{

	/**
	* Auto-generated main method to display this JFrame
	*/
	
	private JMenu file;
	private JMenuBar jMenuBar1;
	private JMenuItem jMenuItem1,jMenuItem2;
	private JLabel jLabel6;
	private JTextField jTextField2;
	private JLabel jLabel5;
	private JComboBox jComboBox7;
	private JPanel jPanel4;
	private JPanel jPanel3;
	private JComboBox jComboBox6;
	private JComboBox jComboBox5;
	private JComboBox jComboBox4;
	private JComboBox jComboBox3;
	private JComboBox jComboBox2;
	private JComboBox jComboBox1;
	private JPanel jPanel2;
	private JButton jButton1;
	private String fileName;	//File name of opening file
	private String fileParent;	//File path of opening file
	private JLabel jLabel4;
	private JTextField jTextField1;
	private JLabel jLabel3;
	private JPanel jPanel1;
	private JLabel jLabel2;
	private JLabel jLabel1;

	// SVM
	private int classification_type=1;
	private int multi_class_type=2;
	private int estimate_type=0;
	private int preprocess_type=1;
	private double gamma;
	private double cost;
	private double gammaList[];
	private double costList[];
	private int[] chooseMethod;	// �m�J�U�Ӥ�k
	// KNN
	private int K_ori=1;
	private int K;
	// Random
	private int seed_num = 20;
	// Fitness Choice
	private int F_choice=1;		// KNN
	//	Set BorderLine  (�ؽu)
	Border blackLine = BorderFactory.createLineBorder(Color.black);
	
	private static String[] files_name_array;
	
	public static void main(String[] args) {
		Classification_Frame_Swing inst = new Classification_Frame_Swing();
		inst.setVisible(true);
	}
	
	public Classification_Frame_Swing() {
		super("Classification");
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			{
				jButton1 = new JButton();
				getContentPane().add(jButton1);
				jButton1.setText("Start");
				jButton1.setBounds(119, 266, 63, 28);
				jButton1.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e)
					{					
							chooseMethod = new int[5];
							chooseMethod[0] = classification_type;
							chooseMethod[1] = multi_class_type;
							chooseMethod[2] = estimate_type;
							chooseMethod[3] = preprocess_type;
							chooseMethod[4] = F_choice;
			
							seed_num = Integer.valueOf(jTextField1.getText()).intValue();
							K = Integer.valueOf(jTextField2.getText()).intValue();
							
							gammaListSet(gamma);
							costListSet(cost);
	
							
							Intermediary II = new Intermediary();
							try {
								II.get_paramater(fileParent, fileName, chooseMethod, gammaList, costList, K, seed_num);
							} catch (IOException e1) {
								// TODO �۰ʲ��� catch �϶�
								e1.printStackTrace();
							}
							
							
					}
				});
			}
			{
				jPanel2 = new JPanel();
				getContentPane().add(jPanel2);
				jPanel2.setBounds(0, 70, 392, 112);
				jPanel2.setLayout(null);
				TitledBorder titledLine2 = BorderFactory.createTitledBorder(blackLine, "Support Vector Machine", TitledBorder.LEFT, TitledBorder.CENTER);
				jPanel2.setBorder(titledLine2);
				{
					ComboBoxModel jComboBox1Model = new DefaultComboBoxModel(
						new String[] { "SVMs Method", "SVM", "FSVM" });
					jComboBox1 = new JComboBox();
					jPanel2.add(jComboBox1);
					jComboBox1.setModel(jComboBox1Model);
					jComboBox1.setSelectedIndex(classification_type);
					jComboBox1.setBounds(14, 28, 133, 21);
					jComboBox1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e)
						{
							if (jComboBox1.getSelectedIndex() == 0){
								classification_type=1;
							}else if (jComboBox1.getSelectedIndex() == 1){
								classification_type=1;
							}else if (jComboBox1.getSelectedIndex() == 2){
								classification_type=2;
							}
						}
					});
				}
				{
					ComboBoxModel jComboBox2Model = new DefaultComboBoxModel(
						new String[] { "MultiClass", "One-Against-One", "One-Against-All" });
					jComboBox2 = new JComboBox();
					jPanel2.add(jComboBox2);
					jComboBox2.setModel(jComboBox2Model);
					jComboBox2.setSelectedIndex(multi_class_type);
					jComboBox2.setBounds(14, 56, 133, 21);
					jComboBox2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e)
						{
							if (jComboBox2.getSelectedIndex() == 0){
								multi_class_type=0;			// No Choice
							}else if (jComboBox2.getSelectedIndex() == 1){
								multi_class_type=1;			// OVO
							}else if (jComboBox2.getSelectedIndex() == 2){
								multi_class_type=2;			// OVA
							}
						}
					});
				}
				{
					ComboBoxModel jComboBox3Model = new DefaultComboBoxModel(
						new String[] { "Cross Validation"});
					jComboBox3 = new JComboBox();
					jPanel2.add(jComboBox3);
					jComboBox3.setModel(jComboBox3Model);
					jComboBox3.setSelectedIndex(estimate_type);
					jComboBox3.setBounds(14, 84, 133, 21);
					jComboBox3.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e)
						{
							if (jComboBox3.getSelectedIndex() == 0){
								estimate_type=0;
							}else if (jComboBox3.getSelectedIndex() == 1){
								estimate_type=1;
							}
						}
					});
				}
				{
					ComboBoxModel jComboBox5Model = new DefaultComboBoxModel(
						new String[] { "Gamma", "2^4", "2^3", "2^2", "2^1", "2^0", "2^-1", "2^-2", 
								"2^-3", "2^-4", "2^-5", "2^-6", "2^-7", "2^-8", "2^-9", "2^-10" });
					jComboBox5 = new JComboBox();
					jPanel2.add(jComboBox5);
					jComboBox5.setModel(jComboBox5Model);
					jComboBox5.setSelectedIndex(15);		gamma = Math.pow(2, -10);
					jComboBox5.setBounds(259, 56, 77, 21);
					jComboBox5.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e)
						{
							if (jComboBox5.getSelectedIndex() == 0){
								gamma = 0.0;
							}else if(jComboBox5.getSelectedIndex() == 1){
								gamma = Math.pow(2, 4);
							}else if(jComboBox5.getSelectedIndex() == 2){
								gamma = Math.pow(2, 3);
							}else if(jComboBox5.getSelectedIndex() == 3){
								gamma = Math.pow(2, 2);
							}else if(jComboBox5.getSelectedIndex() == 4){
								gamma = Math.pow(2, 1);
							}else if(jComboBox5.getSelectedIndex() == 5){
								gamma = Math.pow(2, 0);
							}else if(jComboBox5.getSelectedIndex() == 6){
								gamma = Math.pow(2, -1);
							}else if(jComboBox5.getSelectedIndex() == 7){
								gamma = Math.pow(2, -2);
							}else if(jComboBox5.getSelectedIndex() == 8){
								gamma = Math.pow(2, -3);
							}else if(jComboBox5.getSelectedIndex() == 9){
								gamma = Math.pow(2, -4);
							}else if(jComboBox5.getSelectedIndex() == 10){
								gamma = Math.pow(2, -5);
							}else if(jComboBox5.getSelectedIndex() == 11){
								gamma = Math.pow(2, -6);
							}else if(jComboBox5.getSelectedIndex() == 12){
								gamma = Math.pow(2, -7);
							}else if(jComboBox5.getSelectedIndex() == 13){
								gamma = Math.pow(2, -8);
							}else if(jComboBox5.getSelectedIndex() == 14){
								gamma = Math.pow(2, -9);
							}else if(jComboBox5.getSelectedIndex() == 15){
								gamma = Math.pow(2, -10);
							}
						}
					});
				}
				{
					ComboBoxModel jComboBox6Model = new DefaultComboBoxModel(
						new String[] { "COST", "2^12", "2^11", "2^10", "2^9", "2^8", "2^7", "2^6", 
								"2^5", "2^4", "2^3", "2^2", "2^1", "2^0", "2^-1", "2^-2" });
					jComboBox6 = new JComboBox();
					jPanel2.add(jComboBox6);
					jComboBox6.setModel(jComboBox6Model);
					jComboBox6.setSelectedIndex(1);				cost = Math.pow(2, 12);
					jComboBox6.setBounds(259, 84, 77, 21);
					jComboBox6.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e)
						{
							if (jComboBox6.getSelectedIndex() == 0){
								cost = 0.0;
							}else if (jComboBox6.getSelectedIndex() == 1){
								cost = Math.pow(2, 12);
							}else if (jComboBox6.getSelectedIndex() == 2){
								cost = Math.pow(2, 11);
							}else if (jComboBox6.getSelectedIndex() == 3){
								cost = Math.pow(2, 10);
							}else if (jComboBox6.getSelectedIndex() == 4){
								cost = Math.pow(2, 9);
							}else if (jComboBox6.getSelectedIndex() == 5){
								cost = Math.pow(2, 8);
							}else if (jComboBox6.getSelectedIndex() == 6){
								cost = Math.pow(2, 7);
							}else if (jComboBox6.getSelectedIndex() == 7){
								cost = Math.pow(2, 6);
							}else if (jComboBox6.getSelectedIndex() == 8){
								cost = Math.pow(2, 5);
							}else if (jComboBox6.getSelectedIndex() == 9){
								cost = Math.pow(2, 4);
							}else if (jComboBox6.getSelectedIndex() == 10){
								cost = Math.pow(2, 3);
							}else if (jComboBox6.getSelectedIndex() == 11){
								cost = Math.pow(2, 2);
							}else if (jComboBox6.getSelectedIndex() == 12){
								cost = Math.pow(2, 1);
							}else if (jComboBox6.getSelectedIndex() == 13){
								cost = Math.pow(2, 0);
							}else if (jComboBox6.getSelectedIndex() == 14){
								cost = Math.pow(2, -1);
							}else if (jComboBox6.getSelectedIndex() == 15){
								cost = Math.pow(2, -2);
							}
						}
					});
				}
				{
					jLabel1 = new JLabel();
					jPanel2.add(jLabel1);
					jLabel1.setText("gammar");
					jLabel1.setBounds(210, 49, 63, 28);
				}
				{
					jLabel2 = new JLabel();
					jPanel2.add(jLabel2);
					jLabel2.setText("cost");
					jLabel2.setBounds(210, 77, 63, 28);
				}
				{
					jLabel4 = new JLabel();
					jPanel2.add(jLabel4);
					jLabel4.setText("Kernel : Radial Basis Function");
					jLabel4.setBounds(210, 14, 168, 28);
				}
			}
			{
				jPanel3 = new JPanel();
				getContentPane().add(jPanel3);
				jPanel3.setBounds(0, 182, 175, 56);
				TitledBorder titledLine3 = BorderFactory.createTitledBorder(blackLine, "K-Nearest Neighbor", TitledBorder.LEFT, TitledBorder.CENTER);
				jPanel3.setBorder(titledLine3);
				jPanel3.setLayout(null);
				{
					jLabel5 = new JLabel();
					jPanel3.add(jLabel5);
					jLabel5.setText("K = ");
					jLabel5.setBounds(14, 28, 28, 21);
				}
				{
					jTextField2 = new JTextField();
					jPanel3.add(jTextField2);
					jTextField2.setText(String.valueOf(K_ori));
					jTextField2.setBounds(35, 21, 63, 28);
				}
			}
			{
				jPanel4 = new JPanel();
				getContentPane().add(jPanel4);
				jPanel4.setBounds(0, 0, 392, 70);
				TitledBorder titledLine4 = BorderFactory.createTitledBorder(blackLine, "Fitness Function Choice", TitledBorder.LEFT, TitledBorder.CENTER);
				jPanel4.setBorder(titledLine4);
				jPanel4.setLayout(null);
				{
					ComboBoxModel jComboBox7Model = new DefaultComboBoxModel(
						new String[] { "Choice", "SVM" , "KNN" });
					jComboBox7 = new JComboBox();
					jPanel4.add(jComboBox7);
					jComboBox7.setModel(jComboBox7Model);
					jComboBox7.setSelectedIndex(2);
					jComboBox7.setBounds(21, 28, 84, 28);//F_choice
					jComboBox7.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e)
						{
							if (jComboBox7.getSelectedIndex() == 0){
								F_choice=0;
							}else if (jComboBox7.getSelectedIndex() == 1){
								F_choice=0;
							}else if (jComboBox7.getSelectedIndex() == 2){
								F_choice=1;
							}
						}
					});
				}
				{
					ComboBoxModel jComboBox4Model = new DefaultComboBoxModel(
						new String[] { "Preprocess", "Normalize" });
					jComboBox4 = new JComboBox();
					jPanel4.add(jComboBox4);
					jComboBox4.setModel(jComboBox4Model);
					jComboBox4.setSelectedIndex(1);
					jComboBox4.setBounds(189, 28, 91, 28);
					jComboBox4.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (jComboBox4.getSelectedIndex() == 0) {
								preprocess_type = 0;
							} else if (jComboBox4.getSelectedIndex() == 1) {
								preprocess_type = 1;
							}
						}
					});
				}
				{
					jLabel6 = new JLabel();
					jPanel4.add(jLabel6);
					jLabel6.setText("Data : ");
					jLabel6.setBounds(147, 28, 35, 28);
				}
			}
			{
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1);
				jPanel1.setBounds(175, 182, 217, 56);
				jPanel1.setLayout(null);
				TitledBorder titledLine4 = BorderFactory.createTitledBorder(blackLine, "Java SEED", TitledBorder.LEFT, TitledBorder.CENTER);
				jPanel1.setBorder(titledLine4);
				{
					jLabel3 = new JLabel();
					jPanel1.add(jLabel3);
					jLabel3.setText("SEED =");
					jLabel3.setBounds(7, 21, 63, 28);
				}
				{
					jTextField1 = new JTextField();
					jPanel1.add(jTextField1);
					jTextField1.setText(String.valueOf(seed_num));
					jTextField1.setBounds(49, 21, 63, 28);
				}
			}
			{
				jMenuBar1 = new JMenuBar();
				setJMenuBar(menubar(jMenuBar1));
			}
			pack();
			setSize(400, 370);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JMenuBar menubar(JMenuBar jMenuBar1)
	{
		file = new JMenu("Files");
		
		
//		File(F)->Open File(*.arff & *.txt)
		jMenuItem1 = new JMenuItem("Open File");
		jMenuItem1.addActionListener(this);
//		File(F)->Exit
		jMenuItem2 = new JMenuItem("Exit");
		jMenuItem2.addActionListener(this);
		file.add(jMenuItem1);
		file.add(jMenuItem2);
		jMenuBar1.add(file);
		
		return jMenuBar1;
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		if(evt.getSource() == jMenuItem1)
		{
			openfile();
		}else if((evt.getSource() == jMenuItem2))
		{
			System.exit(0);
		}
	}
	
	private void openfile()
	{
		JFileChooser jfc = new JFileChooser();	//Declare FileChooser component
		int n = jfc.showOpenDialog(this);
		if(n == JFileChooser.APPROVE_OPTION)
		{
			File file = jfc.getSelectedFile();
			fileParent = file.getParent() + "/";
			fileName = file.getName();
		}
		
		System.out.println("�ɮצW��:"+fileName);
	}
	
	private void gammaListSet(double gamma)
	{
		int setExponent = 4;
		//int setExponent = -5;
		
		if(gamma == 0.0)
		{
			gammaList = new double[15];
		
			for(int i=0;i<15;i++)
			{
				gammaList[i] = Math.pow(2,setExponent--);
			}
		}
		else
		{
			gammaList = new double[1];
			gammaList[0] = gamma;
		}
	}
	
	private void costListSet(double cost)
	{
		int setExponent = 12;
		
		if(cost == 0.0)
		{
			costList = new double[15];
		
			for(int i=0;i<15;i++)
			{
				costList[i] = Math.pow(2,setExponent--);
			}
		}
		else
		{
			costList = new double[1];
			costList[0] = cost;
		}
	}
	
}
