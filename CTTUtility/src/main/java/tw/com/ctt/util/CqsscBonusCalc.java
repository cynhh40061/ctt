package tw.com.ctt.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 重庆时时彩
 * 
 * @author Tmser
 * @since 2012.8.29
 */
public class CqsscBonusCalc {
	/** 开奖号位数 --5 */
	public static final int AWARDCODE_SIZE = 5;

	/** 一星彩玩法 */
	public static final int STARS_LOTTERY = 0;
	/** 大小单双玩法 */
	public static final int BIG_SMALL = 1;
	/** 二星组选 */
	public static final int TWO_STARS = 2;
	/** 5星通选 */
	public static final int FIVE_STARS = 3;

	/** 星彩1星奖金 */
	public static final double STARS_LOTTERY_ONE_STARS_BONUS = 10;
	/** 星彩2星奖金 */
	public static final double STARS_LOTTERY_TWO_STARS_BONUS = 100;
	/** 星彩3星奖金 */
	public static final double STARS_LOTTERY_THREE_STARS_BONUS = 1000;
	/** 星彩5星奖金 */
	public static final double STARS_LOTTERY_FIVVE_STARS_BONUS = 100000;

	/** 大小单双奖金 */
	public static final double BIG_SMALL_BONUS = 4;

	/** 二星组选奖金 */
	public static final double TWO_STARS_GROUP_BONUS = 50;
	/** 二星组选个十同号奖金 */
	public static final double TWO_STARS_GROUP_SAME_NUMBER_BONUS = 100;

	/** 5星通选1等奖奖金 */
	public static final double FIVE_STARS_LEVEL1_BONUS = 20000;
	/** 5星通选2等奖奖金 */
	public static final double FIVE_STARS_LEVEL2_BONUS = 200;
	/** 5星通选3等奖奖金 */
	public static final double FIVE_STARS_LEVEL3_BONUS = 20;

	/** 五星通选 */
	/** 5个数字全相同，中一等奖 */
	public static final int FIVE_STARS_LEVEL1 = 1;
	/** 前三个数字或者后三个数字相同，中1个2等奖 */
	public static final int FIVE_STARS_LEVEL2 = 2;
	/** 前两个数字或者后两个数字相同，中1个三等奖 */
	public static final int FIVE_STARS_LEVEL3 = 3;

	/** 星彩玩法 */
	public static final int ONE_STARS_PRIZE = 4;
	public static final int TWO_STARS_PRIZE = 5;
	public static final int THREE_STARS_PRIZE = 6;
	public static final int FIVE_STARS_PRIZE = 7;

	/** 大小单双玩法 */
	public static final int BIG_SMALL_PRIZE = 8;

	/** 二星组选 */
	public static final int TWO_STARTS_GROUP_PRIZE = 9;
	public static final int TWO_STARTS_GROUP_SAME_NUMBER_PRIZE = 10;

	// 2-大，1-小，3-单，4-双
	public static final String BIG = "2";
	public static final String SMALL = "1";
	public static final String SINGULAR = "3";
	public static final String EVEN = "4";
	/** 未中奖 */
	public static final int NO_PRIZE_LEVEL = -1;
	/** 中奖 */
	public static final int IS_BINGO = 0;

	/**
	 * 根据5星通选奖级计算奖金
	 */
	public static BigDecimal getFiveStarBonus(int rank) {
		switch (rank) {
		case FIVE_STARS_LEVEL1:
			return new BigDecimal(FIVE_STARS_LEVEL1_BONUS);
		case FIVE_STARS_LEVEL2:
			return new BigDecimal(FIVE_STARS_LEVEL2_BONUS);
		case FIVE_STARS_LEVEL3:
			return new BigDecimal(FIVE_STARS_LEVEL3_BONUS);
		}

		return new BigDecimal(0);
	}

	/**
	 * 计算五星通选中奖等级
	 * 
	 * @param userCode
	 *            用户投注号码
	 * @param awardCode
	 *            开奖号码
	 */
	public static int haveFiveStarRank(String userCode, String awardCode) {
		if (userCode == null || awardCode == null || userCode.length() != awardCode.length()) {
			return NO_PRIZE_LEVEL;
		}

		String[] userCodes = userCode.split(",");
		String[] awardCodes = awardCode.split(",");
		int equalNum = 0;

		for (int i = 0; i < userCodes.length; i++) {
			if (!userCodes[i].equals(awardCodes[i])) {
				break;
			}

			equalNum++;
		}

		switch (equalNum) {
		case 5:
			return FIVE_STARS_LEVEL1;
		case 4:
		case 3:
			return FIVE_STARS_LEVEL2;

		}

		return NO_PRIZE_LEVEL;
	}

	/**
	 * 判断后两位数字是否相同
	 */
	private static boolean isLastTwoCodesEqual(String[] userCodes, String[] awardCodes) {
		return userCodes[userCodes.length - 1].equals(awardCodes[awardCodes.length - 1])
				&& userCodes[userCodes.length - 2].equals(awardCodes[awardCodes.length - 2]);
	}

	public static int haveRank(int type, String userCode, String awardCode) {
		if (userCode == null || awardCode == null) {
			return NO_PRIZE_LEVEL;
		}
		int equalNum = 0;

		switch (type) {

		case STARS_LOTTERY:
			// TODO *根据type计算星数startsNum
			int startsNum = 0;
			haveStartsLotteryRank(startsNum, userCode, awardCode);
			break;
		case BIG_SMALL:
			haveBigSmallRank(userCode, awardCode);
			break;
		case TWO_STARS:
			haveTwoStarGroupRank(userCode, awardCode);
			break;
		case FIVE_STARS:
			// TODO *五星通选计算不正确
			haveFiveStarRank(userCode, awardCode);
			break;
		}

		switch (equalNum) {
		case 5:
			return FIVE_STARS_LEVEL1;
		case 4:
		case 3:
			return FIVE_STARS_LEVEL2;
		case 1:
			return FIVE_STARS_LEVEL3;
		}

		return NO_PRIZE_LEVEL;
	}

	/**
	 * 星彩玩法
	 * 
	 * @param type
	 *            星数 1,2,3,5
	 * @param userCode
	 *            用户号码
	 * @param awardCode
	 *            开奖号码
	 * @return int
	 */
	public static int haveStartsLotteryRank(int startsNum, String userCode, String awardCode) {
		if (userCode == null || awardCode == null) {
			return NO_PRIZE_LEVEL;
		}
		String[] userCodes = userCode.split(",");
		String[] awardCodes = awardCode.split(",");
		int equalNum = 0;
		//
		if (startsNum == 0) {
			startsNum = userCodes.length;
		}
		for (int i = 0; i < startsNum; i++) {
			int uIndex = startsNum - i - 1;
			int aIndex = awardCodes.length - i - 1;
			if (!userCodes[uIndex].equals(awardCodes[aIndex]))
				break;
			else
				equalNum++;
		}
		if (startsNum == equalNum) {
			switch (startsNum) {
			case 1:
				return ONE_STARS_PRIZE;
			case 2:
				return TWO_STARS_PRIZE;
			case 3:
				return THREE_STARS_PRIZE;
			case 5:
				return FIVE_STARS_PRIZE;
			default:
				return NO_PRIZE_LEVEL;
			}
		}

		else
			return NO_PRIZE_LEVEL;

	}

	public static void main(String[] args) {
		System.out.println(CqsscBonusCalc.haveBigSmallRank("23", "4,8,4,5,1"));

	}

	private static void addBSList(String num, List<String> list) {
		Boolean isbig = isBig(num);
		Boolean issingular = isSingular(num);
		if (isbig == null || issingular == null)
			return;

		if (isbig) {
			list.add(BIG);
		} else {
			list.add(SMALL);
		}
		if (issingular) {
			list.add(SINGULAR);
		} else {
			list.add(EVEN);
		}
	}

	/**
	 * 大小单双玩法
	 * 
	 * @param userCode
	 * @param awardCode
	 * @return
	 */
	public static int haveBigSmallRank(String userCode, String awardCode) {
		if (userCode == null || awardCode == null) {
			return NO_PRIZE_LEVEL;
		}
		String[] userCodes = userCode.trim().split("");
		String[] awardCodes = awardCode.split(",");
		List<String> geweiList = new ArrayList<String>();
		List<String> shiweiList = new ArrayList<String>();
		if (awardCodes.length == AWARDCODE_SIZE) {
			addBSList(awardCodes[AWARDCODE_SIZE - 1], geweiList);
			addBSList(awardCodes[AWARDCODE_SIZE - 2], shiweiList);
			if (geweiList.contains(userCodes[1]) && shiweiList.contains(userCodes[0])) {
				return BIG_SMALL_PRIZE;
			}

		}

		return NO_PRIZE_LEVEL;
	}

	/**
	 * 二星组选
	 * 
	 * @param userCode
	 * @param awardCode
	 * @return
	 */
	public static int haveTwoStarGroupRank(String userCode, String awardCode) {
		if (userCode == null || awardCode == null) {
			return NO_PRIZE_LEVEL;
		}
		String[] userCodes = userCode.split(",");
		String[] awardCodes = awardCode.split(",");

		if ((userCodes[0].equals(awardCodes[3]) && userCodes[1].equals(awardCodes[4]))
				|| (userCodes[0].equals(awardCodes[4]) && userCodes[1].equals(awardCodes[3]))) {
			if (awardCodes[3] == awardCodes[4])
				return TWO_STARTS_GROUP_SAME_NUMBER_PRIZE;
			else
				return TWO_STARTS_GROUP_PRIZE;
		}

		return NO_PRIZE_LEVEL;
	}

	public static Boolean isSingular(String number) {
		int num = -1;
		try {
			num = Integer.parseInt(number);

		} catch (NumberFormatException e) {
			return null;
		}
		if (num < 0 || num > 9)
			return null;
		if (num % 2 == 1) {
			return true;
		} else {
			return false;
		}

	}

	public static Boolean isBig(String number) {
		int num = -1;
		try {
			num = Integer.parseInt(number);

		} catch (NumberFormatException e) {
			return null;
		}
		if (0 <= num && num < 5) {
			return false;
		} else if (num >= 5 && num < 10) {
			return true;
		}

		return null;
	}

}