package com.rongdu.loans.enums;

/**
 * @author zcb
 *	借款期限
 */
public enum LoanTermEnum {
	M1("1", "1个月"), 
	M2("2", "2个月"),
	M3("3", "3个月"),
	M4("4", "4个月"),
	M5("5", "5个月"),
	M6("6", "6个月"),
	M7("7", "7个月"),
	M8("8", "8个月"),
	M9("9", "9个月"),
	M10("10", "10个月"),
	
	M11("11", "11个月"), 
	M12("12", "12个月"),
	M13("13", "13个月"),
	M14("14", "14个月"),
	M15("15", "15个月"),
	M16("16", "16个月"),
	M17("17", "17个月"),
	M18("18", "18个月"),
	M19("19", "19个月"),
	M20("20", "20个月"),
	
	M21("21", "21个月"), 
	M22("22", "22个月"),
	M23("23", "23个月"),
	M24("24", "24个月"),
	M25("25", "25个月"),
	M26("26", "26个月"),
	M27("27", "27个月"),
	M28("28", "28个月"),
	M29("29", "29个月"),
	M30("30", "30个月"),
	
	M31("31", "31个月"), 
	M32("32", "32个月"),
	M33("33", "33个月"),
	M34("34", "34个月"),
	M35("35", "35个月"),
	M36("36", "36个月");
	
	private String id;
	private String name;

	LoanTermEnum(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public static String getDesc(Integer id) {
		for (LoanTermEnum loanTermEnum : LoanTermEnum.values()) {
			if (loanTermEnum.getId().equals(id.toString())) {
				return loanTermEnum.getName();
			}
		}
		return null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
