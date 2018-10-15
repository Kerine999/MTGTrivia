
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class Parser {
	ArrayList<Card> a1;
	String s;
	ArrayList<Card> creat;
	ArrayList<Card> inst;
	ArrayList<Card> sorc;
	ArrayList<Card> ench;
	ArrayList<Card> art;
	ArrayList<Card> plane;
	ArrayList<Card> tri;
	ArrayList<String> allTexts;

	@SuppressWarnings("resource")
	public Parser(String set) throws FileNotFoundException {

		String fName;
		s = set;
		if (set == "Kaladesh")
			fName = "KLD";
		else if (set=="Aether Revolt")
			fName = "AER";
		else if (set=="Eldritch Moon")
			fName="EMN.json";
		else if(set=="Shadows Over Innistrad")
			fName="SOI.json";
		else if (set=="Conspiracy: Take the Crown")
			fName="CN2.json";
		else if (set=="Eternal Masters")
			fName="EMA.json";
		else if(set=="Modern Masters 2017")
			fName="MM3.json";
		else if(set=="Oath of the Gatewatch")
			fName="OGW.json";
		else
			fName="AKH.json";

		String s = "";

		Scanner input = new Scanner(System.in);

		File file = new File(fName);

		input = new Scanner(file);

		while(input.hasNextLine())
		{
		s += input.nextLine();
		}

		input.close();
		a1 = new ArrayList<Card>();
		int count=0;
		int aCount=1;
		allTexts=new ArrayList<String>(200);
		allTexts.add("No Card Text");
		int fuck=0;
		if(set=="Amonkhet")
		{
			fuck=1;
		}
		while (s.indexOf("manaCost") != -1) {

			Card a = new Card();
			
			int start = s.indexOf("layout")+8+fuck;
			String q=s.substring(start, start+20);
			String[]arr=q.split("\"");
			String layout=arr[1];
			a.setLayout(layout);
			if(layout.equals("aftermath"))
			{
				//if the card is the first one, it's card number is 0. If it's the aftermath card,
				//it's card number is 1.
				aCount=(aCount+1)%2;
				a.setCardNo(aCount);
			}
			
			start = s.indexOf("manaCost") + 11+fuck;
			
			q = s.substring(start, start + 20);
			arr = q.split("}");
			String cost = "";
			for (int i = 0; i < arr.length - 1; i++) {
				cost += arr[i].substring(1);
			}
			a.setCost(cost);
			s = s.substring(s.indexOf("manaCost") + 1);

			start = s.indexOf("\"multiverseid\"") + 15+fuck;
			q = s.substring(start, start + 7);
			arr = q.split(",");
			String id = "";
			id += arr[0];
			a.setId(Integer.parseInt(id));
			s = s.substring(s.indexOf("\"multiverseid\"") + 1);

			start = s.indexOf("\"name\"") + 7+fuck;
			q = s.substring(start, start + 30);
			arr = q.split("\"");
			String name = "";
			name += arr[1];
			a.setName(name);
			s = s.substring(s.indexOf("\"name\"") + 1);
			
			if(layout=="aftermath"){
				start = s.indexOf("\"names\"")+8+fuck;
				q=s.substring(start,start+90);
				arr=q.split("\"");
				String match="";
				if(aCount==1)
				{
					match+=arr[1];
				}
				if(aCount==0)
				{
					match+=arr[3];
				}
				a.setMatch(match);
			}

			String power = "";
			if(s.indexOf("\"power\"")<s.indexOf("\"rarity\""))
			{
			start = s.indexOf("\"power\"") + 9+fuck;
			q = s.substring(start, start + 5);
			arr = q.split("\"");
			power += arr[0]+"/";
			s = s.substring(s.indexOf("\"power\"") + 9+fuck);
			}
			//subtypes is after rarity
			String text="";
			start = s.indexOf("\"rarity\"");
			int end=s.indexOf("\"type\"")+9+fuck;
			q = s.substring(start, end);
			if(q.contains("\"text\""))
			{
				start=q.indexOf("\"text\"")+8+fuck;
				q=q.substring(start, q.indexOf("\"type\""));
				arr = q.split("\",");
				text = "";
				text += arr[0];
				while(text.contains("\\n"))
				{
					text=text.substring(0, text.indexOf("\\n"))+" "+text.substring(text.indexOf("\\n")+2);
				}
				while(text.contains("("))
				{
					text=text.substring(0,text.indexOf("("))+" "+text.substring(text.indexOf(")")+1);
				}
				
			}
			else
			{
				text="No Card Text";
			}
			a.setText(text);
			if(!allTexts.contains(text))
			{
			allTexts.add(text);
			}
			s = s.substring(s.indexOf("\"rarity\"") + 1);
			
			if(s.indexOf("\"toughness\"")<s.indexOf("\"types\"")){
			start = s.indexOf("\"toughness\"") + 13+fuck;
			q = s.substring(start, start + 5);
			arr = q.split("\"");
			power += arr[0];
			a.setStats(power);
			s = s.substring(s.indexOf("\"toughness\"") + 13+fuck);
			}
			
			start = s.indexOf("\"types\"") + 9+fuck;
			String temp = s.substring(start);
			q = temp.substring(0, temp.indexOf("}"));
			arr = q.split("\"");
			String type = "";
			for (int i = 0; i < arr.length; i++) {
				if (arr[i].length() > 3&&!arr[i].contains("  "))
					type += arr[i] + " ";
			}
			type = type.substring(0, type.length() - 1);
			a.setType(type);
			s = s.substring(s.indexOf("\"types\"") + 8+fuck);
			a.setIndex(count);
			count++;
			a1.add(a);
			
			}
		creat=new ArrayList<Card>();
		inst=new ArrayList<Card>();
		sorc=new ArrayList<Card>();
		ench=new ArrayList<Card>();
		art=new ArrayList<Card>();
		plane=new ArrayList<Card>();
		tri=new ArrayList<Card>();
		for(Card c:a1)
		{
			if(c.getType().contains("Creature"))
			{
				creat.add(c);
			}
			if(c.getType().contains("Instant"))
			{
				inst.add(c);
			}
			if(c.getType().contains("Sorcery"))
			{
				sorc.add(c);
			}
			if(c.getType().contains("Enchantment"))
			{
				ench.add(c);
			}
			if(c.getType().contains("Artifact"))
			{
				art.add(c);
			}
			if(c.getType().contains("Planeswalker"))
			{
				plane.add(c);
			}
			if(c.getType().contains("Tribal"))
			{
				tri.add(c);
			}
		}
	}

	public int getImageInfo() {
		return (int) (Math.random() * a1.size());
	}
	
	public ArrayList<String> getAllTexts()
	{
		return allTexts;
	}
	
	public String getCardText(int index)
	{
		return a1.get(index).getText();
	}
	
	public ArrayList<String> getCardTexts(String cont)
	{
		ArrayList<String> ret=new ArrayList<String>(allTexts.size());
		if(cont!=null){
		for(String s:allTexts)
		{
			if(s.toUpperCase().contains(cont.toUpperCase()))
			{
				ret.add(s);
			}
		}
		}
		else
		{
			return allTexts;
		}
		return ret;
	}

	public int getRestrictedImageInfo(String type) {
		if (type.equalsIgnoreCase("all types")) {
			return getImageInfo();
		}
		ArrayList<Card> arr=new ArrayList<Card>();
		switch(type)
		{
		case "Creature":
			arr=creat;
			break;
		case "Instant":
			arr=inst;
			break;
		case "Sorcery":
			arr=sorc;
			break;
		case "Enchantment":
			arr=ench;
			break;
		case "Artifact":
			arr=art;
			break;
		case "Planeswalker":
			arr=plane;
			break;
		case "Tribal":
			arr=tri;	
			break;
		}
		int i=(int) (Math.random() * arr.size());
		return arr.get(i).getIndex();
	}
	public ArrayList<ImageIcon> getImages(int index, String type) throws IOException {
		ArrayList<Card> arr=a1;
		int aftermath=-1;
		if(getLayout(index).equals("aftermath"))
		{
			aftermath=0;
			if(getCardNo(index)==1)
			{
				aftermath=1;
			}
		}
		return ParseImage.imageParce(s, arr.get(index).getId(),aftermath);
	}

	public String getCName(int index) {
		return a1.get(index).getName();
	}

	public String getCMC(int index) {
		return a1.get(index).getCost();
	}

	public int getMID(int index) {
		return a1.get(index).getId();
	}
	
	public String getCTC(int index)
	{
		return a1.get(index).getType();
	}
	
	public String getLayout(int index)
	{
		return a1.get(index).getLayout();
	}
	
	public int getCardNo(int index)
	{
		return a1.get(index).getCardNo();
	}
	
	public String getStats(int index)
	{
		return a1.get(index).getStats();
	}

	public BufferedImage getI(int index) throws IOException {
		return ParseImage.image(a1.get(index).getId());
	}

}
