import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.JTextComponent;

import java.util.*;

public class MainGUI {
	private int WIDTH = 1000;
	private int HEIGHT = 600;
	
	JFrame frame;
	JComboBox sets;
	JPanel cardandButton;
	JButton start;
	JPanel setsAndChangeSet;
	JButton change;
	
	JPanel pickCType;
	JComboBox cTDrop;
	JLabel text;
	
	JPanel mainPanel;
	JButton flip;
	JButton next;
	JTextField cardName;
	JLabel cName;
	JLabel cNameCorrect;
	JTextField cMana;
	JLabel cMC;
	JLabel cMCorrect;
	
	JTextField power;
	JTextField toughness;
	JLabel pT;
	JLabel back;
	JLabel pTCorrect;

	JTextField cType;
	JLabel cTC;
	JLabel cTCorrect;
	
	JLabel cText;
	JLabel cTextCorrect;
	JComboBox cardText;
	JPanel belowCard;
	JButton subCText;
	String[] allTexts;
	
	JPanel lables;
	JPanel fullCard;
	private boolean started = false;
	private String currset = "Amonkhet";
	ArrayList<ImageIcon> card;
	int index;
	Parser p;
	JLabel image;
	
	boolean nCorrect = false;
	boolean mCorrect = false;
	boolean tCorrect = false;
	boolean cCorrect=false;
	boolean sCorrect=false;
	int numQs = 0;
	int numRight = 0;
	JLabel nC;
	JLabel nQ;
	boolean flipped = false;

	public MainGUI(String[] setnames) throws IOException {
		frame = new JFrame("MTG Quiz");
		
		sets = new JComboBox(setnames);
		sets.addActionListener(new SetListener());
		sets.setBackground(Color.CYAN);
		
		change = new JButton("Change Set");
		change.setBackground(Color.CYAN);
		change.setForeground(Color.CYAN);
		change.addActionListener(new ChangeListener());
		
		setsAndChangeSet = new JPanel();
		setsAndChangeSet.setPreferredSize(new Dimension(700, 50));
		setsAndChangeSet.add(sets);
		setsAndChangeSet.add(change);
		setsAndChangeSet.setBackground(Color.MAGENTA);
		
		String[] arr={"All Types","Creature","Instant","Sorcery","Enchantment","Artifact","Planeswalker","Tribal"};
		cTDrop=new JComboBox(arr);
		cTDrop.addActionListener(new CTListener());
		text=new JLabel("Show cards of type:");
		text.setForeground(Color.GREEN);
		cTDrop.setBackground(Color.CYAN);
		
		pickCType = new JPanel();
		pickCType.setPreferredSize(new Dimension(700, 50));
		pickCType.add(text);
		pickCType.add(cTDrop);
		pickCType.setBackground(Color.YELLOW);
		
		
		cardandButton = new JPanel();
		cardandButton.setPreferredSize(new Dimension(500, 350));
		cardandButton.setBackground(Color.red);
		
		start = new JButton("Start");
		start.addActionListener(new StartListener());
		cardandButton.add(start);
		
		flip = new JButton("Show");
		flip.addActionListener(new FlipListener());
		next = new JButton("Next");
		next.addActionListener(new NextListener());
		
		
		lables = new JPanel();
		lables.setPreferredSize(new Dimension(200, 350));
		lables.setBackground(Color.BLUE);
		
		cName = new JLabel("Card Name:");
		cName.setForeground(Color.yellow);
		cNameCorrect = new JLabel("Correct!",SwingConstants.CENTER);
		cNameCorrect.setPreferredSize(new Dimension(200, 15));
		cardName = new JTextField(15);
		cardName.addActionListener(new NameListener());
		cardName.setBackground(Color.red);
		cNameCorrect.setVisible(false);
		cName.setVisible(false);
		cardName.setVisible(false);
		cardName.setHorizontalAlignment(JTextField.CENTER);
		
		cMana = new JTextField(15);
		cMana.addActionListener(new ManaListener());
		cMana.setVisible(false);
		cMana.setHorizontalAlignment(JTextField.CENTER);
		cMana.setBackground(Color.red);
		cMC = new JLabel("Mana Cost:");
		cMC.setForeground(Color.yellow);
		cMC.setVisible(false);
		cMCorrect = new JLabel("Correct!",SwingConstants.CENTER);
		cMCorrect.setPreferredSize(new Dimension(200, 15));
		cMCorrect.setVisible(false);

		cType = new JTextField(15);
		cType.addActionListener(new TypeListener());
		cType.setVisible(false);
		cType.setHorizontalAlignment(JTextField.CENTER);
		cType.setBackground(Color.red);
		cTC = new JLabel("Card Type:");
		cTC.setForeground(Color.YELLOW);
		cTC.setVisible(false);
		cTCorrect = new JLabel("Correct!",SwingConstants.CENTER);
		cTCorrect.setPreferredSize(new Dimension(200, 15));
		cTCorrect.setVisible(false);
		
		cText=new JLabel("Select Card Text Below");
		cText.setForeground(Color.YELLOW);
		cText.setPreferredSize(new Dimension(700,15));
		cText.setHorizontalAlignment(SwingConstants.CENTER);
		cTextCorrect=new JLabel("Correct!");
		cTextCorrect.setPreferredSize(new Dimension(700,15));
		cTextCorrect.setHorizontalAlignment(SwingConstants.CENTER);
		allTexts=new String[0];
		cardText=new JComboBox(allTexts);
		cardText.setEditable(true);
		cardText.setPreferredSize(new Dimension(400,21));
		cardText.setBackground(Color.MAGENTA);
		subCText=new JButton("Enter");
		subCText.addActionListener(new SubmitListener());
		//cardText.addActionListener(new CardTextListener());
		
		cardText.getEditor().getEditorComponent().addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {    
				if(!p.getAllTexts().isEmpty()){
					String lastText=(String)cardText.getEditor().getItem();
	    			cardText.removeAllItems();
	    			ArrayList<String> texts=p.getCardTexts(lastText);
	    			cardText.addItem(lastText);
	    			for(String s:texts)
	    			{
	    				cardText.addItem(s);
	    			}
	    			}
	    		
			}    });
		
		
		
		cText.setVisible(false);
		cTextCorrect.setVisible(false);
		cardText.setVisible(false);
		subCText.setVisible(false);
		
		belowCard=new JPanel();
		belowCard.setBackground(Color.GREEN);
		belowCard.setPreferredSize(new Dimension(700,150));
		belowCard.add(cText);
		belowCard.add(cardText);
		belowCard.add(subCText);
		belowCard.add(cTextCorrect);
		
		power = new JTextField(2);
		power.addActionListener(new PTListener());
		power.setVisible(false);
		power.setHorizontalAlignment(JTextField.CENTER);
		
		toughness = new JTextField(2);
		toughness.addActionListener(new PTListener());
		toughness.setVisible(false);
		toughness.setHorizontalAlignment(JTextField.CENTER);
		
		power.setBackground(Color.RED);
		toughness.setBackground(Color.RED);
		
		back=new JLabel("/");
		back.setVisible(false);
		
		pT = new JLabel("Power/Toughness:", SwingConstants.CENTER);
		pT.setForeground(Color.YELLOW);
		pT.setPreferredSize(new Dimension(200,15));
		pT.setVisible(false);
		pTCorrect = new JLabel("Correct!",SwingConstants.CENTER);
		pTCorrect.setPreferredSize(new Dimension(200, 15));
		pTCorrect.setVisible(false);
		
		lables.add(cName);
		lables.add(cardName);
		lables.add(cNameCorrect);
		lables.add(cMC);
		lables.add(cMana);
		lables.add(cMCorrect);
		lables.add(cTC);
		lables.add(cType);
		lables.add(cTCorrect);
		lables.add(pT);
		lables.add(power);
		lables.add(back);
		lables.add(toughness);
		lables.add(pTCorrect);
		
		nC = new JLabel("Number Correct: " + numRight + "/" + numQs);
		nC.setForeground(Color.red);
		lables.add(nC);
		nC.setVisible(false);
		
		fullCard = new JPanel();
		fullCard.add(lables);
		fullCard.add(cardandButton);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.add(setsAndChangeSet);
		mainPanel.add(pickCType);
		mainPanel.add(fullCard);
		mainPanel.add(belowCard);
		frame.getContentPane().add(mainPanel);
	}

	public void display() {
		frame.pack();
		frame.setVisible(true);
	}

	public void dispose() {
		frame.dispose();
	}

	private class StartListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			cardandButton.remove(start);
			started = true;
			cName.setVisible(true);
			cardName.setVisible(true);
			cMana.setVisible(true);
			cMC.setVisible(true);
			cType.setVisible(true);
			cTC.setVisible(true);
			nC.setVisible(true);
			cText.setVisible(true);
			cardText.setVisible(true);
			subCText.setVisible(true);
			try {
				p = new Parser(currset);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			index = p.getRestrictedImageInfo((String)cTDrop.getSelectedItem());
			try {
				card = p.getImages(index,(String)cTDrop.getSelectedItem());
			} catch (IOException e1) {
			}
			cardText.removeAllItems();
			ArrayList<String> texts=p.getAllTexts();
			for(String s:texts)
			{
				cardText.addItem(s);
			}
			image = new JLabel("", card.get(1), JLabel.CENTER);
			cardandButton.add(image);
			cardandButton.add(flip);
			cardandButton.repaint();
			if((String)cTDrop.getSelectedItem()!="All Types")
			{
				cType.setVisible(false);
				cTC.setVisible(false);
			}
			if((String)cTDrop.getSelectedItem()=="Creature")
			{
				power.setVisible(true);
				toughness.setVisible(true);
				pT.setVisible(true);
				back.setVisible(true);
			}
		}
	}

	private class SetListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox) e.getSource();
			currset = (String) cb.getSelectedItem();
		}
	}

	private class FlipListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			cardandButton.setVisible(false);
			image.setIcon(card.get(0));
			cardandButton.remove(flip);
			cardandButton.add(next, 0);
			cardandButton.repaint();
			cardandButton.setVisible(true);
			numQs++;
			nC.setText("Number Correct: " + numRight + "/" + numQs);
			flipped = true;
			nCorrect = false;
			mCorrect = false;
			tCorrect=false;
			cCorrect=false;
			sCorrect=false;
		}
	}

	private class NextListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			cardandButton.setVisible(false);
			int oldI = index;
			flipped = false;
			index = p.getRestrictedImageInfo((String)cTDrop.getSelectedItem());
			while (index == oldI) {
				index = p.getRestrictedImageInfo((String)cTDrop.getSelectedItem());
			}
			try {
				card = p.getImages(index,(String)cTDrop.getSelectedItem());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			nCorrect = false;
			mCorrect = false;
			tCorrect=false;
			cCorrect=false;
			sCorrect=false;
			
			if((String)cTDrop.getSelectedItem()!="Creature")
			{
				power.setVisible(false);
				toughness.setVisible(false);
				pT.setVisible(false);
				back.setVisible(false);
			}

			cardandButton.remove(next);
			cardandButton.add(flip);
			image.setIcon(card.get(1));
			cardandButton.repaint();
			cardandButton.setVisible(true);
			
		}
	}

	private class ChangeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (started == true) {
				cardandButton.remove(flip);
				cardandButton.remove(next);
				cardandButton.setVisible(false);
				try {
					p = new Parser(currset);
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				index = p.getRestrictedImageInfo((String)cTDrop.getSelectedItem());
				try {
					card = p.getImages(index,(String)cTDrop.getSelectedItem());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if((String)cTDrop.getSelectedItem()=="Creature")
				{
					power.setVisible(true);
					toughness.setVisible(true);
					pT.setVisible(true);
					back.setVisible(true);
				}
				if((String)cTDrop.getSelectedItem()!="Creature")
				{
					power.setVisible(false);
					toughness.setVisible(false);
					pT.setVisible(false);
					back.setVisible(false);
				}
				image.setIcon(card.get(1));
				cardandButton.add(flip);
				cardandButton.repaint();
				cardandButton.setVisible(true);
				numQs = 0;
				numRight = 0;
				nC.setText("Number Correct: " + numRight + "/" + numQs);
				nCorrect = false;
				mCorrect = false;
				tCorrect = false;
				cCorrect = false;
				sCorrect=false;
				flipped=false;
			}
		}
	}

	private class NameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String name = cardName.getText();
			String compare = p.getCName(index);
			if (name.equalsIgnoreCase(compare)) {
				cNameCorrect.setText("Correct!");
				nCorrect = true;
				try {
					displayCard();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				cNameCorrect.setText("Incorrect");
			}
			cardName.setText("");
			// maybe reveal that part of image
			cNameCorrect.setVisible(true);
			Timer t = new Timer(5000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cNameCorrect.setVisible(false);
				}
			});
			t.setRepeats(false);
			t.start();
		}
	}

	private class ManaListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String name = cMana.getText();
			String compare = p.getCMC(index);
			if (equal(compare, name)) {
				cMCorrect.setText("Correct!");
				mCorrect = true;
				try {
					displayCard();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				cMCorrect.setText("Incorrect");
			}
			cMana.setText("");
			cMCorrect.setVisible(true);
			Timer t = new Timer(5000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cMCorrect.setVisible(false);
				}
			});
			t.setRepeats(false);
			t.start();
		}
	}

	private class TypeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String name = cType.getText();
			String compare = p.getCTC(index);
			if (compare.equalsIgnoreCase(name)) {
				cTCorrect.setText("Correct!");
				tCorrect = true;
				try {
					displayCard();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				cTCorrect.setText("Incorrect");
			}
			cType.setText("");
			cTCorrect.setVisible(true);
			Timer t = new Timer(5000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cTCorrect.setVisible(false);
				}
			});
			t.setRepeats(false);
			t.start();
		}
	}
	private class SubmitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String name = (String)cardText.getSelectedItem();
			String compare = p.getCardText(index);
			if (compare.equalsIgnoreCase(name)) {
				cTextCorrect.setText("Correct!");
				cCorrect = true;
				try {
					displayCard();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				cTextCorrect.setText("Incorrect");
			}
			cardText.removeAllItems();
			ArrayList<String> texts=p.getAllTexts();
			for(String s:texts)
			{
				cardText.addItem(s);
			}
			cTextCorrect.setVisible(true);
			Timer t = new Timer(5000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cTextCorrect.setVisible(false);
				}
			});
			t.setRepeats(false);
			t.start();
		}
	}
	private class CTListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (started == true) {
				cardandButton.remove(flip);
				cardandButton.remove(next);
				cardandButton.setVisible(false);
				
			    index = p.getRestrictedImageInfo((String)cTDrop.getSelectedItem());
				try {
					card = p.getImages(index,(String)cTDrop.getSelectedItem());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				image.setIcon(card.get(1));
				cardandButton.add(flip);
				cardandButton.repaint();
				cardandButton.setVisible(true);
				numQs = 0;
				numRight = 0;
				nC.setText("Number Correct: " + numRight + "/" + numQs);
				nCorrect = false;
				mCorrect = false;
				tCorrect = false;
				cCorrect = false;
				sCorrect=false;
				flipped=false;
				if((String)cTDrop.getSelectedItem()=="Creature")
				{
					power.setVisible(true);
					toughness.setVisible(true);
					pT.setVisible(true);
					back.setVisible(true);
				}
				if((String)cTDrop.getSelectedItem()!="Creature")
				{
					power.setVisible(false);
					toughness.setVisible(false);
					pT.setVisible(false);
					back.setVisible(false);
				}
				if((String)cTDrop.getSelectedItem()!="All Types")
				{
					cType.setVisible(false);
					cTC.setVisible(false);
				}
				else
				{
					cType.setVisible(true);
					cTC.setVisible(true);
				}
			}
		}
	}

	private class PTListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String stats = power.getText()+"/"+toughness.getText();
			String compare = p.getStats(index);
			if (stats.equalsIgnoreCase(compare)) {
				pTCorrect.setText("Correct!");
				sCorrect = true;
				try {
					displayCard();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				pTCorrect.setText("Incorrect");
			}
			power.setText("");
			toughness.setText("");
			// maybe reveal that part of image
			pTCorrect.setVisible(true);
			Timer t = new Timer(5000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pTCorrect.setVisible(false);
				}
			});
			t.setRepeats(false);
			t.start();
		}
	}

	public void displayCard() throws IOException {
		if (flipped == false) {
			if((String)cTDrop.getSelectedItem()!="All Types")
			{
				tCorrect=true;
			}
			if(!p.getCTC(index).contains("Creature"))
			{
				sCorrect=true;
			}
			cardandButton.remove(flip);
			cardandButton.remove(next);
			cardandButton.setVisible(false);

			BufferedImage img = p.getI(index);
			BufferedImage display;

			display = new BufferedImage(223, 311, img.getType());
			Graphics2D gr = display.createGraphics();
			// if everything currently added is true
			if (nCorrect == true && mCorrect == true && tCorrect == true && cCorrect==true&&sCorrect==true) {
				cardandButton.add(next);
				nCorrect = false;
				mCorrect = false;
				tCorrect = false;
				cCorrect = false;
				sCorrect=false;
				flipped = true;
				numQs++;
				numRight++;
				nC.setText("Number Correct: " + numRight + "/" + numQs);

				display = img;

			} else {
				int start=223-16-(p.getCMC(index).length()*12)-2;
				int end=285-(p.getCMC(index).length()*12);
				cardandButton.add(flip);
				if (nCorrect == true) {
					if(p.getLayout(index).equals("aftermath"))
					{
						if(p.getCardNo(index)==0){
						gr.drawImage(img, 0, 0, start, 35, 0, 0, start, 35, null);
						gr.drawImage(img, 0, 35, 17, 126, 0, 35, 17, 126, null);
						}
						else
						{
							gr.drawImage(img, 122, 170, 187, 174, 122, 170, 187, 174, null);
							gr.drawImage(img, 187, 170, 223, end, 187, 170, 223, end, null);
						}
					}
					else
					{
						gr.drawImage(img, 0, 0, start, 35, 0, 0, start, 35, null);
						gr.drawImage(img, 0, 35, 17, 173, 0, 35, 17, 173, null);
					}

				}
				if (mCorrect == true) {
					if(p.getLayout(index).equals("aftermath")&&p.getCardNo(index)!=0)
					{
						gr.drawImage(img, 187, end, 223, 311, 187, end, 223, 311, null);
						gr.drawImage(img, 122, end, 223, 311, 122, end, 223, 311, null);
					}
					else if (p.getLayout(index).equals("aftermath"))
					{
						gr.drawImage(img, start, 0, 223, 35, start, 0, 223, 35, null);
						gr.drawImage(img, 206, 35, 223, 105, 206, 35, 223, 105, null);
					}
					else
					{
					 gr.drawImage(img, start, 0, 223, 35, start, 0, 223, 35, null);
					 gr.drawImage(img, 206, 35, 223, 173, 206, 35, 223, 173, null);
					}
				}
				if (tCorrect==true)
				{
					if(p.getLayout(index).equals("aftermath"))
					{
						if(p.getCardNo(index)==0){
						gr.drawImage(img, 0, 105, 190, 126, 0, 105, 190, 126, null);}
						else
						{
							gr.drawImage(img, 101, 170, 122, 311, 101, 170, 122, 311, null);
						}
					}
					else{
					gr.drawImage(img, 0, 173, 190, 194, 0, 173, 190, 194, null);
					gr.drawImage(img, 0, 35, 17, 173, 0, 35, 17, 173, null);
					}
					if(p.getCTC(index).contains("Creature"))
					{
						power.setVisible(true);
						toughness.setVisible(true);
						pT.setVisible(true);
						back.setVisible(true);
					}
				}
				if(cCorrect==true)
				{
					if(p.getLayout(index).equals("aftermath"))
					{
						if(p.getCardNo(index)==0)
						{
							gr.drawImage(img, 0, 105, 223, 170, 0, 105, 223, 170, null);
							gr.drawImage(img, 0, 35, 17, 126, 0, 35, 17, 126, null);
							gr.drawImage(img, 206, 35, 223, 105, 206, 35, 223, 105, null);
						}
						else
						{
							gr.drawImage(img, 0, 170, 101, 311, 0, 170, 101, 311, null);
							gr.drawImage(img, 122, 170, 187, 174, 122, 170, 187, 174, null);
							gr.drawImage(img, 122, 285, 187, 311, 122, 285, 187, 311, null);
						}
					}
					else{
					gr.drawImage(img, 0, 194, 223, 277, 0, 194, 223, 277, null);
					gr.drawImage(img, 0, 277, 171, 285, 0, 277, 171, 285, null);
					gr.drawImage(img, 0, 35, 17, 173, 0, 35, 17, 173, null);
					gr.drawImage(img, 206, 35, 223, 173, 206, 35, 223, 173, null);
					}
				}
				if(sCorrect==true)
				{
					if(!p.getLayout(index).equals("aftermath")){
					gr.drawImage(img, 171, 277, 223, 311, 171, 277, 223, 311, null);
					gr.drawImage(img, 0, 285, 223, 311, 0, 285, 223, 311, null);
					}
				}
				if(p.getLayout(index).equals("aftermath"))
				{
					if(p.getCardNo(index)==0){
						gr.drawImage(img, 17, 35, 206, 105, 17, 35, 206, 105, null);
					}
					else
					{
						gr.drawImage(img, 122, 174, 187, 285, 122, 174, 187, 285, null);}
					
				}
				else
				{	
				gr.drawImage(img, 17, 35, 206, 173, 17, 35, 206, 173, null);
				}
				

			}
			gr.dispose();
			image.setIcon(new ImageIcon(display));
			cardandButton.repaint();
			cardandButton.setVisible(true);
		
		}
	}

	public boolean equal(String target, String s2) {
		int count = 0;
		ArrayList<String> targ = new ArrayList<String>(target.length());
		ArrayList<String> s = new ArrayList<String>(s2.length());

		for (int i = 0; i < target.length(); i++) {
			targ.add("" + target.charAt(i));
		}
		int t = targ.size();
		for (int i = 0; i < s2.length(); i++) {
			s.add("" + s2.charAt(i));
		}

		for (int i = 0; i < s.size(); i++) {
			if (targ.contains(s.get(i)) || targ.contains(s.get(i).toUpperCase())) {
				count++;
				targ.remove(s.get(i).toUpperCase());
			}
		}
		return (count == t && t == s.size());
	}
}
