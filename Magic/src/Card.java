
public class Card {
	private String cost;
	private String name;
	private int id;
	private String type;
	private String stats;
	private String set;
	private String colour;
	private String abilities;
	private String text;
	private String layout;
	private int index;
	private int cardNo;
	private String matchedCard;
	
	public void setMatch(String m)
	{
		matchedCard=m;
	}
	public String getMatch()
	{
		return matchedCard;
	}
	public void setCardNo(int num)
	{
		cardNo=num;
	}
	public int getCardNo()
	{
		return cardNo;
	}
	public void setLayout(String l)
	{
		layout=l;
	}
	public String getLayout()
	{
		return layout;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public void setIndex(int i)
	{
		index=i;
	}
	
	public int getIndex()
	{
		return index;
	}

	public String getText() {
		return text;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getCost() {
		return cost;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStats() {
		return stats;
	}

	public String getPower() {
		return stats.substring(0, stats.indexOf("/")); // check if char or
														// not!!!!
	}

	public String getToughness() {
		return stats.substring(stats.indexOf("/") + 1); // CHECK THIS TOO!!!!
	}

	public void setStats(String stats) {
		this.stats = stats;
	}

	public String getSet() {
		return set;
	}

	public void setSet(String set) {
		this.set = set;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getAbilities() {
		return abilities;
	}

	public void setAbilities(String abilities) {
		this.abilities = abilities;
	}

}
