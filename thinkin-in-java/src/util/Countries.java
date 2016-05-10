package util;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * A Map of the countries in the world and their capitals.
 * <br>
 * "Flyweight" {@link Map} and {@link List} of sample data.
 * 
 * @author pirent
 *
 */
public class Countries {

	public static final String[][] DATA = {
		// Africa
		{"ALGERIA","Algiers"}, {"ANGOLA","Luanda"},
		{"BENIN","Porto-Novo"}, {"BOTSWANA","Gaberone"},
		{"BURKINA FASO","Ouagadougou"},
		{"BURUNDI","Bujumbura"},
		{"CAMEROON","Yaounde"}, {"CAPE VERDE","Praia"},
		{"CENTRAL AFRICAN REPUBLIC","Bangui"},
		{"CHAD","N’djamena"}, {"COMOROS","Moroni"},
		{"CONGO","Brazzaville"}, {"DJIBOUTI","Dijibouti"},
		{"EGYPT","Cairo"}, {"EQUATORIAL GUINEA","Malabo"},
		{"ERITREA","Asmara"}, {"ETHIOPIA","Addis Ababa"},
		{"GABON","Libreville"}, {"THE GAMBIA","Banjul"},
		{"GHANA","Accra"}, {"GUINEA","Conakry"},
		{"BISSAU","Bissau"},
		{"COTE D’IVOIR (IVORY COAST)","Yamoussoukro"},
		{"KENYA","Nairobi"}, {"LESOTHO","Maseru"},
		{"LIBERIA","Monrovia"}, {"LIBYA","Tripoli"},
		{"MADAGASCAR","Antananarivo"}, {"MALAWI","Lilongwe"},
		{"MALI","Bamako"}, {"MAURITANIA","Nouakchott"},
		{"MAURITIUS","Port Louis"}, {"MOROCCO","Rabat"},
		{"MOZAMBIQUE","Maputo"}, {"NAMIBIA","Windhoek"},
		{"NIGER","Niamey"}, {"NIGERIA","Abuja"},
		{"RWANDA","Kigali"},
		{"SAO TOME E PRINCIPE","Sao Tome"},
		{"SENEGAL","Dakar"}, {"SEYCHELLES","Victoria"},
		{"SIERRA LEONE","Freetown"}, {"SOMALIA","Mogadishu"},
		{"SOUTH AFRICA","Pretoria/Cape Town"},
		{"SUDAN","Khartoum"},
		{"SWAZILAND","Mbabane"}, {"TANZANIA","Dodoma"},
		{"TOGO","Lome"}, {"TUNISIA","Tunis"},
		{"UGANDA","Kampala"},
		{"DEMOCRATIC REPUBLIC OF THE CONGO (ZAIRE)",
		"Kinshasa"},
		{"ZAMBIA","Lusaka"}, {"ZIMBABWE","Harare"},
		
		// Asia
		{"AFGHANISTAN","Kabul"}, {"BAHRAIN","Manama"},
		{"BANGLADESH","Dhaka"}, {"BHUTAN","Thimphu"},
		{"BRUNEI","Bandar Seri Begawan"},
		{"CAMBODIA","Phnom Penh"},
		{"CHINA","Beijing"}, {"CYPRUS","Nicosia"},
		{"INDIA","New Delhi"}, {"INDONESIA","Jakarta"},
		{"IRAN","Tehran"}, {"IRAQ","Baghdad"},
		{"ISRAEL","Jerusalem"}, {"JAPAN","Tokyo"},
		{"JORDAN","Amman"}, {"KUWAIT","Kuwait City"},
		{"LAOS","Vientiane"}, {"LEBANON","Beirut"},
		{"MALAYSIA","Kuala Lumpur"}, {"THE MALDIVES","Male"},
		{"MONGOLIA","Ulan Bator"},
		{"MYANMAR (BURMA)","Rangoon"},
		{"NEPAL","Katmandu"}, {"NORTH KOREA","P’yongyang"},
		{"OMAN","Muscat"}, {"PAKISTAN","Islamabad"},
		{"PHILIPPINES","Manila"}, {"QATAR","Doha"},
		{"SAUDI ARABIA","Riyadh"}, {"SINGAPORE","Singapore"},
		{"SOUTH KOREA","Seoul"}, {"SRI LANKA","Colombo"},
		{"SYRIA","Damascus"},
		{"TAIWAN (REPUBLIC OF CHINA)","Taipei"},
		{"THAILAND","Bangkok"}, {"TURKEY","Ankara"},
		{"UNITED ARAB EMIRATES","Abu Dhabi"},
		{"VIETNAM","Hanoi"}, {"YEMEN","Sana’a"},

		// Australia and Oceania
		{"AUSTRALIA","Canberra"}, {"FIJI","Suva"},
		{"KIRIBATI","Bairiki"},
		{"MARSHALL ISLANDS","Dalap-Uliga-Darrit"},
		{"MICRONESIA","Palikir"}, {"NAURU","Yaren"},
		{"NEW ZEALAND","Wellington"}, {"PALAU","Koror"},
		{"PAPUA NEW GUINEA","Port Moresby"},
		{"SOLOMON ISLANDS","Honaira"}, {"TONGA","Nuku’alofa"},
		{"TUVALU","Fongafale"}, {"VANUATU","< Port-Vila"},
		{"WESTERN SAMOA","Apia"},
		
		// Eastern Europe and former USSR
		{"ARMENIA","Yerevan"}, {"AZERBAIJAN","Baku"},
		{"BELARUS (BYELORUSSIA)","Minsk"},
		{"BULGARIA","Sofia"}, {"GEORGIA","Tbilisi"},
		{"KAZAKSTAN","Almaty"}, {"KYRGYZSTAN","Alma-Ata"},
		{"MOLDOVA","Chisinau"}, {"RUSSIA","Moscow"},
		{"TAJIKISTAN","Dushanbe"}, {"TURKMENISTAN","Ashkabad"},
		{"UKRAINE","Kyiv"}, {"UZBEKISTAN","Tashkent"},

		// Europe
		{"ALBANIA","Tirana"}, {"ANDORRA","Andorra la Vella"},
		{"AUSTRIA","Vienna"}, {"BELGIUM","Brussels"},
		{"BOSNIA","-"}, {"HERZEGOVINA","Sarajevo"},
		{"CROATIA","Zagreb"}, {"CZECH REPUBLIC","Prague"},
		{"DENMARK","Copenhagen"}, {"ESTONIA","Tallinn"},
		{"FINLAND","Helsinki"}, {"FRANCE","Paris"},
		{"GERMANY","Berlin"}, {"GREECE","Athens"},
		{"HUNGARY","Budapest"}, {"ICELAND","Reykjavik"},
		{"IRELAND","Dublin"}, {"ITALY","Rome"},
		{"LATVIA","Riga"}, {"LIECHTENSTEIN","Vaduz"},
		{"LITHUANIA","Vilnius"}, {"LUXEMBOURG","Luxembourg"},
		{"MACEDONIA","Skopje"}, {"MALTA","Valletta"},
		{"MONACO","Monaco"}, {"MONTENEGRO","Podgorica"},
		{"THE NETHERLANDS","Amsterdam"}, {"NORWAY","Oslo"},
		{"POLAND","Warsaw"}, {"PORTUGAL","Lisbon"},
		{"ROMANIA","Bucharest"}, {"SAN MARINO","San Marino"},
		{"SERBIA","Belgrade"}, {"SLOVAKIA","Bratislava"},
		{"SLOVENIA","Ljuijana"}, {"SPAIN","Madrid"},
		{"SWEDEN","Stockholm"}, {"SWITZERLAND","Berne"},
		{"UNITED KINGDOM","London"}, {"VATICAN CITY","---"},

		// North and Central America
		{"ANTIGUA AND BARBUDA","Saint John’s"},
		{"BAHAMAS","Nassau"},
		{"BARBADOS","Bridgetown"}, {"BELIZE","Belmopan"},
		{"CANADA","Ottawa"}, {"COSTA RICA","San Jose"},
		{"CUBA","Havana"}, {"DOMINICA","Roseau"},
		{"DOMINICAN REPUBLIC","Santo Domingo"},
		{"EL SALVADOR","San Salvador"},
		{"GRENADA","Saint George’s"},
		{"GUATEMALA","Guatemala City"},
		{"HAITI","Port-au-Prince"},
		{"HONDURAS","Tegucigalpa"}, {"JAMAICA","Kingston"},
		{"MEXICO","Mexico City"}, {"NICARAGUA","Managua"},
		{"PANAMA","Panama City"}, {"ST. KITTS","-"},
		{"NEVIS","Basseterre"}, {"ST. LUCIA","Castries"},
		{"ST. VINCENT AND THE GRENADINES","Kingstown"},
		{"UNITED STATES OF AMERICA","Washington, D.C."},

		// South America
		{"ARGENTINA","Buenos Aires"},
		{"BOLIVIA","Sucre (legal)/La Paz(administrative)"},
		{"BRAZIL","Brasilia"}, {"CHILE","Santiago"},
		{"COLOMBIA","Bogota"}, {"ECUADOR","Quito"},
		{"GUYANA","Georgetown"}, {"PARAGUAY","Asuncion"},
		{"PERU","Lima"}, {"SURINAME","Paramaribo"},
		{"TRINIDAD AND TOBAGO","Port of Spain"},
		{"URUGUAY","Montevideo"}, {"VENEZUELA","Caracas"},
	};
	
	/*
	 * ==================================================================================
	 * This fly-weight map must implements the entrySet() method which requires:
	 *  + a custom Set implementation
	 *  + a custom Map.Entry class
	 *  ==================================================================================
	 */
	private static class FlyweightMap extends AbstractMap<String, String> {

		/**
		 * Custom implementation of {@link Map.Entry}.
		 * <br>
		 * Each Map.Entry object simple stores its index, rather than the actual key and value.
		 * When you call {@link #getKey()} or {@link #getValue()}, it uses the index to return
		 * the appropriate DATA element.
		 * <br>
		 * Mostly all methods in this implementation relies on the key (name of the country)
		 */
		private static class Entry implements java.util.Map.Entry<String, String> {

			private int index;
			
			Entry(int index) {
				this.index = index;
			}
			
			@Override
			public boolean equals(Object other) {
				// Use the key (name of the country) to compare
				return DATA[index][0].equals(other);
			}
			
			@Override
			public int hashCode() {
				return DATA[index][0].hashCode();
			}
			
			@Override
			public String getKey() {
				return DATA[index][0];
			}

			@Override
			public String getValue() {
				return DATA[index][1];
			}

			@Override
			public String setValue(String value) {
				throw new UnsupportedOperationException();
			}
			
		}
		
		/**
		 * Custom implementation for a {@link Map}'s entry set.
		 */
		static class EntrySet extends AbstractSet<java.util.Map.Entry<String, String>> {

			private int size;
			
			/*
			 * This ensure that size of set of entries is no bigger than DATA.
			 */
			EntrySet(int size) {
				if (size < 0) {
					this.size = 0;
				}
				else if (size > DATA.length) {
					// Cannot be bigger than the array
					this.size = DATA.length; 
				}
				else {
					this.size = size;
				}
			}
			
			@Override
			public Iterator<java.util.Map.Entry<String, String>> iterator() {
				return new Iter();
			}

			@Override
			public int size() {
				return size;
			}
			
			private class Iter implements Iterator<java.util.Map.Entry<String, String>> {
				
				/*
				 * Instead of creating a Map.Entry object for each data pair in DATA,
				 * there's only one Map.Entry object per iterator.
				 */
				
				/**
				 * This {@link Entry} object is used as a window into the data;
				 * it only contains an idex into the static array of strings (i.e., DATA)
				 */
				private Entry entry = new Entry(-1);
				
				@Override
				public boolean hasNext() {
					return entry.index < size - 1;
				}

				/**
				 * Every time this method is call for the iterator, the index
				 * in the Entry is incremented so that it points the next elements pair,
				 * and the that Iterator's single Entry object is returned from this.
				 */
				@Override
				public java.util.Map.Entry<String, String> next() {
					entry.index++;
					return entry;
				}
				
				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}
				
			}
		}
		
		private static Set<java.util.Map.Entry<String, String>> entries = new EntrySet(DATA.length);
		
		@Override
		public Set<java.util.Map.Entry<String, String>> entrySet() {
			return entries;
		}
		
	}
	
	// ========================================================== ||
	
	/**
	 * Produces a {@link FlyweightMap} containing an {@link EntrySet}
	 * of the desired size.
	 * 
	 * @param size
	 * @return
	 */
	static Map<String, String> select(final int size) {
		return new FlyweightMap() {

			@Override
			public Set<java.util.Map.Entry<String, String>> entrySet() {
				return new EntrySet(size);
			}
			
		};
	}
	
	static Map<String, String> map = new FlyweightMap();
	
	/**
	 * Produces a {@link Map} of countries and capitals.
	 * 
	 * @return
	 */
	public static Map<String, String> capitals() {
		return map;	// entire map
	}
	
	/**
	 * Works like {@link #capitals()} but with a number of capitals.
	 * 
	 * @param size number of country's capitals to be displayed
	 * @return
	 */
	public static Map<String, String> capitals(int size) {
		return select(size);
	}
	
	// ========================================================== ||
	
	static List<String> names = new ArrayList<String>(map.keySet());
	
	/**
	 * Produces a {@link List} of the country names.
	 * 
	 * @return
	 */
	public static List<String> names() {
		return names;	// All country names
	}
	
	/**
	 * Produces a partial {@link List} of the country names.
	 * 
	 * @param size
	 * @return
	 */
	public static List<String> names(int size) {
		// A partial list
		return new ArrayList<String>(select(size).keySet());
	}
	
	// ========================================================== ||
	public static void main(String[] args) {
		System.out.println(capitals(10));
		System.out.println(names(10));
		System.out.println(new HashMap<String, String>(capitals(3)));
		System.out.println(new LinkedHashMap<String, String>(capitals(3)));
		System.out.println(new TreeMap<String, String>(capitals(3)));
		System.out.println(new Hashtable<String, String>(capitals(3)));
		System.out.println(new HashSet<String>(names(6)));
		System.out.println(new LinkedHashSet<String>(names(6)));
		System.out.println(new TreeSet<String>(names(6)));
		System.out.println(new ArrayList<String>(names(6)));
		System.out.println(new LinkedList<String>(names(6)));
		System.out.println(capitals().get("BRAZIL"));
	}
}
