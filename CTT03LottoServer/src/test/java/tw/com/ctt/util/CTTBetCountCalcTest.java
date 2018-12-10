package tw.com.ctt.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 彩票對獎機
 * 
 * @author Quanto
 * @since 2018.04.11
 */
public class CTTBetCountCalcTest {
	ArrayList<Double> answer;
	String testCondition;

	@Before
	public void setUp() {

	}

	@After
	public void cleanUp() {
		answer = null;
		testCondition = null;
	}

	public void testCTTBetCountCal_ssc() {
		answer = new ArrayList<Double>();
		double[] ans = { 32, 432, 4, 1, 252, 840, 16, 1, 6, 1, 360, 1, 360, 4, 11, 1, 90, 1, 12, 90, 1, 54, 10000, 9,
				32, 10000, 1, 7, 1, 210, 35, 1, 2, 360, 1, 15, 45, 18, 1, 90, 1, 48, 1000, 8, 126, 426, 1000, 6, 596,
				1000, 1, 4, 10, 1, 20, 120, 2, 20, 90, 5, 91, 210, 1, 2, 3, 1, 12, 1000, 5, 10, 420, 1000, 1, 458, 1000,
				1, 5, 10, 35, 1, 120, 2, 30, 90, 6, 70, 210, 1, 2, 3, 1, 48, 1000, 1, 7, 126, 564, 1000, 36, 453, 1000,
				1, 5, 10, 1, 35, 120, 2, 30, 90, 5, 77, 210, 1, 2, 3, 1, 30, 100, 3, 1, 21, 45, 1, 4, 4, 30, 100, 2, 15,
				45, 1, 25, 100, 1, 6, 1, 15, 45, 1, 4, 7, 37, 100, 1, 24, 45, 1, 15, 50, 1, 3, 10, 1, 3, 10, 1, 5, 10,
				1, 5, 10, 1, 15, 45, 1, 15, 45, 1, 35, 120, 1, 15, 45, 1, 15, 45, 1, 7, 10, 1, 3, 16, 1, 6, 16, 1, 18,
				64, 1, 24, 64, 1, 3, 16, 1, 35, 100, 1, 5, 1, 80, 1000, 1, 4, 2, 30, 90, 1, 10, 120, 1, 4, 10, 1, 4, 10,
				1, 4, 10, 1, 5, 10, 1, 16, 200, 1, 24, 200, 1, 48, 2000, 1, 80, 4000, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2,
				3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3 };
		for (int i = 0; i < ans.length; i++) {
			answer.add(ans[i]);
		}
		testCondition = "{\"calcData\":[{\"rule\":\"ssc5xfs\",\"data\":\"0489,09,16,03,6\"},\r\n"
				+ "{\"rule\":\"ssc5xfs\",\"data\":\"045,046,039,0256,0147\"},\r\n"
				+ "{\"rule\":\"ssc5xds\",\"data\":\"9,8,6,1,2|3,0,9,6,2|3,5,9,6,1|2,3,7,1,3\"},\r\n"
				+ "{\"rule\":\"zx120\",\"data\":\"2,4,5,6,7\"},\r\n"
				+ "{\"rule\":\"zx120\",\"data\":\"0,1,2,3,4,5,6,7,8,9\"},\r\n"
				+ "{\"rule\":\"zx60\",\"data\":\"0123456789,0123456789\"},\r\n"
				+ "{\"rule\":\"zx60\",\"data\":\"2469,1357\"},\r\n" + "{\"rule\":\"zx60\",\"data\":\"0,123\"},\r\n"
				+ "{\"rule\":\"zx30\",\"data\":\"134,25\"},\r\n" + "{\"rule\":\"zx30\",\"data\":\"01,2\"},\r\n"
				+ "{\"rule\":\"zx30\",\"data\":\"0123456789,0123456789\"},\r\n"
				+ "{\"rule\":\"zx20\",\"data\":\"2,34\"},\r\n"
				+ "{\"rule\":\"zx20\",\"data\":\"0123456789,0123456789\"},\r\n"
				+ "{\"rule\":\"zx20\",\"data\":\"35,256\"},\r\n" + "{\"rule\":\"zx10\",\"data\":\"1478,246\"},\r\n"
				+ "{\"rule\":\"zx10\",\"data\":\"0,1\"},\r\n"
				+ "{\"rule\":\"zx10\",\"data\":\"0123456789,0123456789\"},\r\n"
				+ "{\"rule\":\"zx5\",\"data\":\"1,2\"},\r\n" + "{\"rule\":\"zx5\",\"data\":\"1458,237\"},\r\n"
				+ "{\"rule\":\"zx5\",\"data\":\"0123456789,0123456789\"},\r\n"
				+ "{\"rule\":\"sscqh4xfs\",\"data\":\"1,1,1,1\"},\r\n"
				+ "{\"rule\":\"sscqh4xfs\",\"data\":\"01,236,457,249\"},\r\n"
				+ "{\"rule\":\"sscqh4xfs\",\"data\":\"0123456789,0123456789,0123456789,0123456789\"},\r\n"
				+ "{\"rule\":\"qh4ds\",\"data\":\"0,9,1,2|6,3,4,8|7,6,1,9|2,8,3,4|9,1,2,5|3,4,0,8|7,2,1,6|3,5,0,8|0,5,1,4\"},\r\n"
				+ "{\"rule\":\"sscqh4xfs\",\"data\":\"0349,1246,8,17\"},\r\n"
				+ "{\"rule\":\"sscqh4xfs\",\"data\":\"0123456789,0123456789,0123456789,0123456789\"},\r\n"
				+ "{\"rule\":\"sscqh4xfs\",\"data\":\" 0,0,0,0\"},\r\n"
				+ "{\"rule\":\"qh4ds\",\"data\":\"8,1,7,2|3,6,4,9|8,7,1,2|3,9,8,4|5,1,9,2|8,3,5,4|1,6,2,5\"},\r\n"
				+ "{\"rule\":\"zx24\",\"data\":\"1,2,3,4\"},\r\n"
				+ "{\"rule\":\"zx24\",\"data\":\"0,1,2,3,4,5,6,7,8,9\"},\r\n"
				+ "{\"rule\":\"zx24\",\"data\":\"0,1,3,5,6,7,9\"},\r\n" + "{\"rule\":\"zx12\",\"data\":\"0,12\"},\r\n"
				+ "{\"rule\":\"zx12\",\"data\":\"16,37\"},\r\n"
				+ "{\"rule\":\"zx12\",\"data\":\"0123456789,0123456789\"},\r\n"
				+ "{\"rule\":\"zx6\",\"data\":\"0,1\"},\r\n" + "{\"rule\":\"zx6\",\"data\":\"1,3,4,7,8,9\"},\r\n"
				+ "{\"rule\":\"zx6\",\"data\":\"0,1,2,3,4,5,6,7,8,9\"},\r\n"
				+ "{\"rule\":\"zx4\",\"data\":\"01478,1357\"},\r\n" + "{\"rule\":\"zx4\",\"data\":\"0,1\"},\r\n"
				+ "{\"rule\":\"zx4\",\"data\":\"0123456789,0123456789\"},\r\n"
				+ "{\"rule\":\"sscqzh3xfs\",\"data\":\"0,3,1\"},\r\n"
				+ "{\"rule\":\"sscqzh3xfs\",\"data\":\"1356,245,1389\"},\r\n"
				+ "{\"rule\":\"sscqzh3xfs\",\"data\":\"0123456789,0123456789,0123456789\"},\r\n"
				+ "{\"rule\":\"qzh3ds\",\"data\":\"1,0,7|2,6,3|4,9,8|7,1,2|6,3,9|8,4,7|6,1,2|3,4,2\"},\r\n"
				+ "{\"rule\":\"sscq3zhixkd\",\"data\":\"3\"},\r\n"
				+ "{\"rule\":\"sscq3zhixkd\",\"data\":\"2,5,7,9\"},\r\n"
				+ "{\"rule\":\"sscq3zhixkd\",\"data\":\"0,1,2,3,4,5,6,7,8,9\"},\r\n"
				+ "{\"rule\":\"fc3dhzq\",\"data\":\"2\"},\r\n"
				+ "{\"rule\":\"fc3dhzq\",\"data\":\"1,3,5,6,10,12,13,14,15,17,18,20,22\"},\r\n"
				+ "{\"rule\":\"fc3dhzq\",\"data\":\"0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27\"},\r\n"
				+ "{\"rule\":\"r1sscqw\",\"data\":\"2\"},\r\n" + "{\"rule\":\"r1sscqw\",\"data\":\"1 4 5 6\"},\r\n"
				+ "{\"rule\":\"r1sscqw\",\"data\":\"0 1 2 3 4 5 6 7 8 9\"},\r\n"
				+ "{\"rule\":\"rx3z6\",\"data\":\" 345\"},\r\n" + "{\"rule\":\"rx3z6\",\"data\":\"145679\"},\r\n"
				+ "{\"rule\":\"rx3z6\",\"data\":\"0123456789\"},\r\n" + "{\"rule\":\"rx3z3\",\"data\":\"25\"},\r\n"
				+ "{\"rule\":\"rx3z3\",\"data\":\"56789\"},\r\n" + "{\"rule\":\"rx3z3\",\"data\":\" 0123456789\"},\r\n"
				+ "{\"rule\":\"sscq3zxhz\",\"data\":\"5\"},\r\n"
				+ "{\"rule\":\"sscq3zxhz\",\"data\":\"1,3,5,7,9,11,13,17,19,21,23,25\"},\r\n"
				+ "{\"rule\":\"sscq3zxhz\",\"data\":\"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26\"},\r\n"
				+ "{\"rule\":\"sscq3tshm\",\"data\":\"顺子\"},\r\n" + "{\"rule\":\"sscq3tshm\",\"data\":\"顺子,对子\"},\r\n"
				+ "{\"rule\":\"sscq3tshm\",\"data\":\"豹子,顺子,对子\"},\r\n"
				+ "{\"rule\":\"sscqzh3xfs\",\"data\":\"0,0,0\"},\r\n"
				+ "{\"rule\":\"sscqzh3xfs\",\"data\":\"079,1249,4\"},\r\n"
				+ "{\"rule\":\"sscqzh3xfs\",\"data\":\"0123456789,0123456789,0123456789\"},\r\n"
				+ "{\"rule\":\"qzh3ds\",\"data\":\"9,1,7|2,6,3|9,4,8|7,6,1|2,3,4\"},\r\n"
				+ "{\"rule\":\"sscz3zhixkd\",\"data\":\"0\"},\r\n"
				+ "{\"rule\":\"sscz3zhixkd\",\"data\":\"2,4,7,9\"},\r\n"
				+ "{\"rule\":\"sscz3zhixkd\",\"data\":\"0,1,2,3,4,5,6,7,8,9\"},\r\n"
				+ "{\"rule\":\"fc3dhzz\",\"data\":\"0\"},\r\n"
				+ "{\"rule\":\"fc3dhzz\",\"data\":\"2,3,6,9,12,14,16,17,19,22,24,26\"},\r\n"
				+ "{\"rule\":\"fc3dhzz\",\"data\":\"0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27\"},\r\n"
				+ "{\"rule\":\"r1sscqw\",\"data\":\"2\"},\r\n" + "{\"rule\":\"r1sscqw\",\"data\":\"1 3 6 8 9\"},\r\n"
				+ "{\"rule\":\"r1sscqw\",\"data\":\"0 1 2 3 4 5 6 7 8 9\"},\r\n"
				+ "{\"rule\":\"rx3z6\",\"data\":\"0146789\"},\r\n" + "{\"rule\":\"rx3z6\",\"data\":\"345\"},\r\n"
				+ "{\"rule\":\"rx3z6\",\"data\":\"0123456789\"},\r\n" + "{\"rule\":\"rx3z3\",\"data\":\"12\"},\r\n"
				+ "{\"rule\":\"rx3z3\",\"data\":\" 023568\"},\r\n" + "{\"rule\":\"rx3z3\",\"data\":\"0123456789\"},\r\n"
				+ "{\"rule\":\"sscz3zxhz\",\"data\":\"6\"},\r\n"
				+ "{\"rule\":\"sscz3zxhz\",\"data\":\"5,9,12,14,17,20,23\"},\r\n"
				+ "{\"rule\":\"sscz3zxhz\",\"data\":\"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26\"},\r\n"
				+ "{\"rule\":\"sscz3tshm\",\"data\":\"豹子\"},\r\n" + "{\"rule\":\"sscz3tshm\",\"data\":\"顺子,对子\"},\r\n"
				+ "{\"rule\":\"sscz3tshm\",\"data\":\"豹子,顺子,对子\"},\r\n"
				+ "{\"rule\":\"sscqzh3xfs\",\"data\":\"0,0,0\"},\r\n"
				+ "{\"rule\":\"sscqzh3xfs\",\"data\":\"0479,357,1578\"},\r\n"
				+ "{\"rule\":\"sscqzh3xfs\",\"data\":\"0123456789,0123456789,0123456789\"},\r\n"
				+ "{\"rule\":\"qzh3ds\",\"data\":\"0,0,0\"},\r\n"
				+ "{\"rule\":\"qzh3ds\",\"data\":\"0,0,2|0,2,0|0,3,0|2,0,0|3,0,0|3,0,4|0,0,4\"},\r\n"
				+ "{\"rule\":\"ssch3zhixkd\",\"data\":\"3\"},\r\n"
				+ "{\"rule\":\"ssch3zhixkd\",\"data\":\"3,4,6,8,9\"},\r\n"
				+ "{\"rule\":\"ssch3zhixkd\",\"data\":\"0,1,2,3,4,5,6,7,8,9\"},\r\n"
				+ "{\"rule\":\"fc3dhzh\",\"data\":\"20\"},\r\n"
				+ "{\"rule\":\"fc3dhzh\",\"data\":\"3,6,11,14,15,16,17,19,23,25\"},\r\n"
				+ "{\"rule\":\"fc3dhzh\",\"data\":\"0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27\"},\r\n"
				+ "{\"rule\":\"r1sscqw\",\"data\":\"2\"},\r\n" + "{\"rule\":\"r1sscqw\",\"data\":\"2 3 5 7 9\"},\r\n"
				+ "{\"rule\":\"r1sscqw\",\"data\":\"0 1 2 3 4 5 6 7 8 9\"},\r\n"
				+ "{\"rule\":\"rx3z6\",\"data\":\"013\"},\r\n" + "{\"rule\":\"rx3z6\",\"data\":\" 0124679\"},\r\n"
				+ "{\"rule\":\"rx3z6\",\"data\":\"0123456789\"},\r\n" + "{\"rule\":\"rx3z3\",\"data\":\"28\"},\r\n"
				+ "{\"rule\":\"rx3z3\",\"data\":\"135679\"},\r\n" + "{\"rule\":\"rx3z3\",\"data\":\" 0123456789\"},\r\n"
				+ "{\"rule\":\"ssch3zxhz\",\"data\":\"5\"},\r\n"
				+ "{\"rule\":\"ssch3zxhz\",\"data\":\"1,4,5,8,10,14,17,19,21\"},\r\n"
				+ "{\"rule\":\"ssch3zxhz\",\"data\":\"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26\"},\r\n"
				+ "{\"rule\":\"ssch3tshm\",\"data\":\"豹子\"},\r\n" + "{\"rule\":\"ssch3tshm\",\"data\":\"顺子,对子\"},\r\n"
				+ "{\"rule\":\"ssch3tshm\",\"data\":\"豹子,顺子,对子\"},\r\n"
				+ "{\"rule\":\"sscqh2xfs\",\"data\":\"1,3\"},\r\n"
				+ "{\"rule\":\"sscqh2xfs\",\"data\":\"013679,24578\"},\r\n"
				+ "{\"rule\":\"sscqh2xfs\",\"data\":\"0123456789,0123456789\"},\r\n"
				+ "{\"rule\":\"qh2ds\",\"data\":\"6,8|9,6|8,6\"},\r\n" + "{\"rule\":\"z2\",\"data\":\"37\"},\r\n"
				+ "{\"rule\":\"z2\",\"data\":\"1345679\"},\r\n" + "{\"rule\":\"z2\",\"data\":\"0123456789\"},\r\n"
				+ "{\"rule\":\"qh2ds\",\"data\":\"0,2\"},\r\n"
				+ "{\"rule\":\"qh2ds\",\"data\":\"7,5|6,3|9,1|0,2\"},\r\n"
				+ "{\"rule\":\"sscqh2zhixhz\",\"data\":\"3\"},\r\n"
				+ "{\"rule\":\"sscqh2zhixhz\",\"data\":\"0,2,5,8,13,16,17\"},\r\n"
				+ "{\"rule\":\"sscqh2zhixhz\",\"data\":\"0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18\"},\r\n"
				+ "{\"rule\":\"sscqh2zhuxhz\",\"data\":\"3\"},\r\n"
				+ "{\"rule\":\"sscqh2zhuxhz\",\"data\":\"2,4,5,11,13,16,17\"},\r\n"
				+ "{\"rule\":\"sscqh2zhuxhz\",\"data\":\"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17\"},\r\n"
				+ "{\"rule\":\"sscqh2xfs\",\"data\":\"2,3\"},\r\n"
				+ "{\"rule\":\"sscqh2xfs\",\"data\":\"02578,13457\"},\r\n"
				+ "{\"rule\":\"sscqh2xfs\",\"data\":\"0123456789,0123456789\"},\r\n"
				+ "{\"rule\":\"qh2ds\",\"data\":\"1,2\"},\r\n"
				+ "{\"rule\":\"qh2ds\",\"data\":\"6,7|5,8|2,1|7,5|8,1|6,7\"},\r\n"
				+ "{\"rule\":\"z2\",\"data\":\"15\"},\r\n" + "{\"rule\":\"z2\",\"data\":\"013678\"},\r\n"
				+ "{\"rule\":\"z2\",\"data\":\"0123456789\"},\r\n" + "{\"rule\":\"qh2ds\",\"data\":\"6,7\"},\r\n"
				+ "{\"rule\":\"qh2ds\",\"data\":\"9,7|1,2|5,8|7,8\"},\r\n"
				+ "{\"rule\":\"sscqh2zhixhz\",\"data\":\"6\"},\r\n"
				+ "{\"rule\":\"sscqh2zhixhz\",\"data\":\"0,3,9,12,14,15,16,17,18\"},\r\n"
				+ "{\"rule\":\"sscqh2zhixhz\",\"data\":\"0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18\"},\r\n"
				+ "{\"rule\":\"sscqh2zhuxhz\",\"data\":\"1\"},\r\n"
				+ "{\"rule\":\"sscqh2zhuxhz\",\"data\":\"1,2,6,7,8,10,13,15,16,17\"},\r\n"
				+ "{\"rule\":\"sscqh2zhuxhz\",\"data\":\"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17\"},\r\n"
				+ "{\"rule\":\"ssc5xdwd\",\"data\":\"2,-,-,-,-\"},\r\n"
				+ "{\"rule\":\"ssc5xdwd\",\"data\":\"157,027,348,169,259\"},\r\n"
				+ "{\"rule\":\"ssc5xdwd\",\"data\":\"0123456789,0123456789,0123456789,0123456789,0123456789\"},\r\n"
				+ "{\"rule\":\"sscqzhr31m\",\"data\":\"2\"},\r\n" + "{\"rule\":\"sscqzhr31m\",\"data\":\"157\"},\r\n"
				+ "{\"rule\":\"sscqzhr31m\",\"data\":\" 0123456789\"},\r\n"
				+ "{\"rule\":\"sscqzhr31m\",\"data\":\"2\"},\r\n" + "{\"rule\":\"sscqzhr31m\",\"data\":\"158\"},\r\n"
				+ "{\"rule\":\"sscqzhr31m\",\"data\":\"0123456789\"},\r\n"
				+ "{\"rule\":\"sscqzhr31m\",\"data\":\"5\"},\r\n" + "{\"rule\":\"sscqzhr31m\",\"data\":\"14679\"},\r\n"
				+ "{\"rule\":\"sscqzhr31m\",\"data\":\"0123456789\"},\r\n"
				+ "{\"rule\":\"sscqzhr31m\",\"data\":\"3\"},\r\n" + "{\"rule\":\"sscqzhr31m\",\"data\":\"12369\"},\r\n"
				+ "{\"rule\":\"sscqzhr31m\",\"data\":\"0123456789\"},\r\n"
				+ "{\"rule\":\"r2ssc\",\"data\":\"3 6\"},\r\n" + "{\"rule\":\"r2ssc\",\"data\":\"0 2 4 6 7 9\"},\r\n"
				+ "{\"rule\":\"r2ssc\",\"data\":\"0 1 2 3 4 5 6 7 8 9\"},\r\n"
				+ "{\"rule\":\"r2ssc\",\"data\":\"1 6\"},\r\n" + "{\"rule\":\"r2ssc\",\"data\":\"0 1 4 6 8 9\"},\r\n"
				+ "{\"rule\":\"r2ssc\",\"data\":\"0 1 2 3 4 5 6 7 8 9\"},\r\n"
				+ "{\"rule\":\"ssc5x3m\",\"data\":\"0 3 5\"},\r\n"
				+ "{\"rule\":\"ssc5x3m\",\"data\":\"1 3 4 5 6 8 9\"},\r\n"
				+ "{\"rule\":\"ssc5x3m\",\"data\":\"0 1 2 3 4 5 6 7 8 9\"},\r\n"
				+ "{\"rule\":\"r2ssc\",\"data\":\"1 6\"},\r\n" + "{\"rule\":\"r2ssc\",\"data\":\"0 3 5 7 8 9\"},\r\n"
				+ "{\"rule\":\"r2ssc\",\"data\":\"0 1 2 3 4 5 6 7 8 9\"},\r\n"
				+ "{\"rule\":\"r2ssc\",\"data\":\"1 5\"},\r\n" + "{\"rule\":\"r2ssc\",\"data\":\"0 2 4 5 8 9\"},\r\n"
				+ "{\"rule\":\"r2ssc\",\"data\":\"0 1 2 3 4 5 6 7 8 9\"},\r\n"
				+ "{\"rule\":\"ssc4x1m\",\"data\":\"0\"},\r\n"
				+ "{\"rule\":\"ssc4x1m\",\"data\":\" 0 1 3 4 6 7 9\"},\r\n"
				+ "{\"rule\":\"ssc4x1m\",\"data\":\"0 1 2 3 4 5 6 7 8 9\"},\r\n"
				+ "{\"rule\":\"qh2dxds\",\"data\":\"大,大\"},\r\n" + "{\"rule\":\"qh2dxds\",\"data\":\"大单双,小\"},\r\n"
				+ "{\"rule\":\"qh2dxds\",\"data\":\"大小单双,大小单双\"},\r\n" + "{\"rule\":\"qh2dxds\",\"data\":\"小,大\"},\r\n"
				+ "{\"rule\":\"qh2dxds\",\"data\":\"大单双,小双\"},\r\n"
				+ "{\"rule\":\"qh2dxds\",\"data\":\"大小单双,大小单双\"},\r\n"
				+ "{\"rule\":\"qh3dxds\",\"data\":\"大,大,小\"},\r\n"
				+ "{\"rule\":\"qh3dxds\",\"data\":\"大单双,大小,大小双\"},\r\n"
				+ "{\"rule\":\"qh3dxds\",\"data\":\"大小单双,大小单双,大小单双\"},\r\n"
				+ "{\"rule\":\"qh3dxds\",\"data\":\" 大,大,大\"},\r\n"
				+ "{\"rule\":\"qh3dxds\",\"data\":\"大单,大小单双,大单双\"},\r\n"
				+ "{\"rule\":\"qh3dxds\",\"data\":\"大小单双,大小单双,大小单双\"},\r\n"
				+ "{\"rule\":\"rxdxds\",\"data\":\"大,-,单,-,-\"},\r\n"
				+ "{\"rule\":\"rxdxds\",\"data\":\"大小双,大,-,-,-\"},\r\n"
				+ "{\"rule\":\"rxdxds\",\"data\":\"大小单双,-,-,大小单双,-\"},\r\n"
				+ "{\"rule\":\"rx2fs\",\"data\":\"-,-,-,1,3\"},\r\n"
				+ "{\"rule\":\"rx2fs\",\"data\":\"1234789,03469,-,-,-\"},\r\n"
				+ "{\"rule\":\"rx2fs\",\"data\":\"0123456789,0123456789,-,-,-\"},\r\n"
				+ "{\"rule\":\"rx2ds\",\"data\":\"-,1,-,1,-\"},\r\n"
				+ "{\"rule\":\"rx2ds\",\"data\":\"-,-,-,3,9|-,-,-,7,9|-,-,-,8,1|-,-,-,8,7|-,-,-,9,7\"},\r\n"
				+ "{\"rule\":\"rx3fs\",\"data\":\"1,2,5,-,-\"},\r\n"
				+ "{\"rule\":\"rx3fs\",\"data\":\"0368,1459,02469,-,-\"},\r\n"
				+ "{\"rule\":\"rx3fs\",\"data\":\" 0123456789,0123456789,0123456789,-,-\"},\r\n"
				+ "{\"rule\":\"rx3ds\",\"data\":\"1,-,2,-,3\"},\r\n"
				+ "{\"rule\":\"rx3ds\",\"data\":\"3,-,4,-,9|7,-,4,-,2|7,-,8,-,3|9,-,8,-,7\"},\r\n"
				+ "{\"rule\":\"rx3z3\",\"data\":\"24\"},\r\n" + "{\"rule\":\"rx3z3\",\"data\":\"023579\"},\r\n"
				+ "{\"rule\":\"rx3z3\",\"data\":\"0123456789\"},\r\n" + "{\"rule\":\"rx3z6\",\"data\":\"269\"},\r\n"
				+ "{\"rule\":\"rx3z6\",\"data\":\"01479\"},\r\n" + "{\"rule\":\"rx3z6\",\"data\":\"0123456789\"},\r\n"
				+ "{\"rule\":\"r1sscqw\",\"data\":\"1\"},\r\n" + "{\"rule\":\"r1sscqw\",\"data\":\"3 6 8 9\"},\r\n"
				+ "{\"rule\":\"r1sscqw\",\"data\":\"0 1 2 3 4 5 6 7 8 9\"},\r\n"
				+ "{\"rule\":\"r1sscqw\",\"data\":\"6\"},\r\n" + "{\"rule\":\"r1sscqw\",\"data\":\"1 4 6 7\"},\r\n"
				+ "{\"rule\":\"r1sscqw\",\"data\":\"0 1 2 3 4 5 6 7 8 9\"},\r\n"
				+ "{\"rule\":\"r1sscqw\",\"data\":\"2\"},\r\n" + "{\"rule\":\"r1sscqw\",\"data\":\"1 3 6 8\"},\r\n"
				+ "{\"rule\":\"r1sscqw\",\"data\":\"0 1 2 3 4 5 6 7 8 9\"},\r\n"
				+ "{\"rule\":\"r1sscqw\",\"data\":\"9\"},\r\n" + "{\"rule\":\"r1sscqw\",\"data\":\"1 4 6 7 8\"},\r\n"
				+ "{\"rule\":\"r1sscqw\",\"data\":\"0 1 2 3 4 5 6 7 8 9\"},\r\n"
				+ "{\"rule\":\"qh3qw2x\",\"data\":\"小,5,2\"},\r\n"
				+ "{\"rule\":\"qh3qw2x\",\"data\":\"大,2679,2359\"},\r\n"
				+ "{\"rule\":\"qh3qw2x\",\"data\":\"小大,0123456789,0123456789\"},\r\n"
				+ "{\"rule\":\"qh3qw2x\",\"data\":\"大,1,3\"},\r\n"
				+ "{\"rule\":\"qh3qw2x\",\"data\":\"大,135789,0368\"},\r\n"
				+ "{\"rule\":\"qh3qw2x\",\"data\":\"小大,0123456789,0123456789\"},\r\n"
				+ "{\"rule\":\"ssc4mqw3x\",\"data\":\"小,5,2,8\"},\r\n"
				+ "{\"rule\":\"ssc4mqw3x\",\"data\":\"小,158,0259,0367\"},\r\n"
				+ "{\"rule\":\"ssc4mqw3x\",\"data\":\"小大,0123456789,0123456789,0123456789\"},\r\n"
				+ "{\"rule\":\"ssc5mqw3x\",\"data\":\"大,大,5,9,9\"},\r\n"
				+ "{\"rule\":\"ssc5mqw3x\",\"data\":\"大,小,0248,5679,13589\"},\r\n"
				+ "{\"rule\":\"ssc5mqw3x\",\"data\":\"小大,小大,0123456789,0123456789,0123456789\"},\r\n"
				+ "{\"rule\":\"ssclh\",\"data\":\"和\"},\r\n" + "{\"rule\":\"ssclh\",\"data\":\"虎和\"},\r\n"
				+ "{\"rule\":\"ssclh\",\"data\":\"龙虎和\"},\r\n" + "{\"rule\":\"ssclh\",\"data\":\"龙\"},\r\n"
				+ "{\"rule\":\"ssclh\",\"data\":\"龙和\"},\r\n" + "{\"rule\":\"ssclh\",\"data\":\"龙虎和\"},\r\n"
				+ "{\"rule\":\"ssclh\",\"data\":\"和\"},\r\n" + "{\"rule\":\"ssclh\",\"data\":\"虎和\"},\r\n"
				+ "{\"rule\":\"ssclh\",\"data\":\"龙虎和\"},\r\n" + "{\"rule\":\"ssclh\",\"data\":\"虎\"},\r\n"
				+ "{\"rule\":\"ssclh\",\"data\":\"龙和\"},\r\n" + "{\"rule\":\"ssclh\",\"data\":\"龙虎和\"},\r\n"
				+ "{\"rule\":\"ssclh\",\"data\":\"龙\"},\r\n" + "{\"rule\":\"ssclh\",\"data\":\"龙和\"},\r\n"
				+ "{\"rule\":\"ssclh\",\"data\":\"龙虎和\"},\r\n" + "{\"rule\":\"ssclh\",\"data\":\"和\"},\r\n"
				+ "{\"rule\":\"ssclh\",\"data\":\"龙和\"},\r\n" + "{\"rule\":\"ssclh\",\"data\":\"龙虎和\"},\r\n"
				+ "{\"rule\":\"ssclh\",\"data\":\"虎\"},\r\n" + "{\"rule\":\"ssclh\",\"data\":\"龙虎\"},\r\n"
				+ "{\"rule\":\"ssclh\",\"data\":\"龙虎和\"},\r\n" + "{\"rule\":\"ssclh\",\"data\":\"和\"},\r\n"
				+ "{\"rule\":\"ssclh\",\"data\":\"龙虎\"},\r\n" + "{\"rule\":\"ssclh\",\"data\":\"龙虎和\"},\r\n"
				+ "{\"rule\":\"ssclh\",\"data\":\"龙\"},\r\n" + "{\"rule\":\"ssclh\",\"data\":\"龙虎\"},\r\n"
				+ "{\"rule\":\"ssclh\",\"data\":\"龙虎和\"},\r\n" + "{\"rule\":\"ssclh\",\"data\":\"和\"},\r\n"
				+ "{\"rule\":\"ssclh\",\"data\":\"龙虎\"},\r\n" + "{\"rule\":\"ssclh\",\"data\":\"龙虎和\"}]}";

		JSONObject obj = new JSONObject(testCondition);
		JSONArray arr = obj.getJSONArray("calcData");
		if (arr.length() == answer.size()) {
			CTTBetCountCalc calc = new CTTBetCountCalc();
			for (int i = 0; i < arr.length(); i++) {
				String result = CTTBetCountCalc.invokeCalc(calc, ((JSONObject) arr.get(i)).getString("rule"),
						((JSONObject) arr.get(i)).getString("data"));
				if (Double.parseDouble(result + "") != answer.get(i)) {
					System.out.println(i + " # " + ((JSONObject) arr.get(i)).getString("rule") + " # "
							+ ((JSONObject) arr.get(i)).getString("data") + " # Result" + result + " # Ans"
							+ answer.get(i));
				}
				assertTrue(Double.parseDouble(result + "") == answer.get(i));
			}
			System.out.println("testCTTBetCountCal_ssc測試完成");
		} else {
			System.out.println("輸入錯誤");
		}
	}

	@Test
	public void testCTTBetCountCal_115() {
		answer = new ArrayList<Double>();
		double[] ans = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
				9, 45, 15, 7, 339, 35, 28, 9, 9, 9, 6, 6, 21, 8, 8, 36, 35, 35, 21, 28, 8, 9, 8, 4, 3, 3, 3, 4, 3, 3, 6,
				5, 11, 110, 55, 10, 990, 165, 45, 33, 11, 11, 55, 165, 330, 462, 462, 330, 165, 7, 6 };
		for (int i = 0; i < ans.length; i++) {
			answer.add(ans[i]);
		}
		testCondition = "{\"calcData\":[{\"rule\":\"q1zx11x5\", \"data\":\"03\"},\r\n" + 
				"{\"rule\":\"q2zx11x5\", \"data\":\"02,03\"},\r\n" + 
				"{\"rule\":\"r211x5\", \"data\":\"03 05\"},\r\n" + 
				"{\"rule\":\"r211x5\", \"data\":\"(02)06\"},\r\n" + 
				"{\"rule\":\"q3zx11x5\", \"data\":\"02,04,06\"},\r\n" + 
				"{\"rule\":\"r311x5\", \"data\":\"03 07 09\"},\r\n" + 
				"{\"rule\":\"r311x5\", \"data\":\"(03)05 08\"},\r\n" + 
				"{\"rule\":\"rxwfQ2d\", \"data\":\"34,41\"},\r\n" + 
				"{\"rule\":\"qh2ds11x5\", \"data\":\"73,57\"},\r\n" + 
				"{\"rule\":\"qh2ds11x5\", \"data\":\"45,-,34,-,-\"},\r\n" + 
				"{\"rule\":\"qh3ds11x5\", \"data\":\"73,78,96\"},\r\n" + 
				"{\"rule\":\"qh3ds11x5\", \"data\":\"12,35,67\"},\r\n" + 
				"{\"rule\":\"dwd11x5\", \"data\":\"02,-,-\"},\r\n" + 
				"{\"rule\":\"bdw11x5\", \"data\":\"06\"},\r\n" + 
				"{\"rule\":\"r111x5\", \"data\":\"02\"},\r\n" + 
				"{\"rule\":\"r211x5\", \"data\":\"05 07\"},\r\n" + 
				"{\"rule\":\"r311x5\", \"data\":\"03 06 11\"},\r\n" + 
				"{\"rule\":\"r411x5\", \"data\":\"03 04 06 08\"},\r\n" + 
				"{\"rule\":\"r511x5\", \"data\":\"01 03 05 07 09\"},\r\n" + 
				"{\"rule\":\"r611x5\", \"data\":\"01 03 05 07 09 11\"},\r\n" + 
				"{\"rule\":\"r711x5\", \"data\":\"01 03 05 07 09 10 11\"},\r\n" + 
				"{\"rule\":\"r811x5\", \"data\":\"02 03 06 07 08 09 10 11\"},\r\n" + 
				"{\"rule\":\"r1ds11x5\", \"data\":\"01\"},\r\n" + 
				"{\"rule\":\"r2ds11x5\", \"data\":\"02,03\"},\r\n" + 
				"{\"rule\":\"r3ds11x5\", \"data\":\"03,04,05\"},\r\n" + 
				"{\"rule\":\"r4ds11x5\", \"data\":\"02,04,05,06\"},\r\n" + 
				"{\"rule\":\"r5ds11x5\", \"data\":\"02,03,04,05,06\"},\r\n" + 
				"{\"rule\":\"r6ds11x5\", \"data\":\"02,03,04,05,10,11\"},\r\n" + 
				"{\"rule\":\"r7ds11x5\", \"data\":\"01,03,04,05,07,09,11\"},\r\n" + 
				"{\"rule\":\"r8ds11x5\", \"data\":\"01,02,03,05,06,07,10,11\"},\r\n" + 
				"{\"rule\":\"r1\", \"data\":\"04\"},\r\n" + 
				"{\"rule\":\"dds\", \"data\":\"2单3双\"},\r\n" + 
				"{\"rule\":\"q1zx11x5\", \"data\":\"02 04 05 06 07 08 09 10 11\"},\r\n" + 
				"{\"rule\":\"q2zx11x5\", \"data\":\"01 02 06 07 08 10 11,02 03 04 06 09 10 11\"},\r\n" + 
				"{\"rule\":\"r211x5\", \"data\":\"03 05 06 08 10 11\"},\r\n" + 
				"{\"rule\":\"r211x5\", \"data\":\"(03)01 02 05 06 07 08 10\"},\r\n" + 
				"{\"rule\":\"q3zx11x5\", \"data\":\"01 03 05 06 08 09 10 11,01 03 04 05 07 08 11,01 02 04 05 07 09 10 11\"},\r\n" + 
				"{\"rule\":\"r311x5\", \"data\":\"01 03 05 06 07 09 11\"},\r\n" + 
				"{\"rule\":\"r311x5\", \"data\":\"(04)01 03 05 07 08 09 10 11\"},\r\n" + 
				"{\"rule\":\"rxwfQ2d\", \"data\":\"01,02|03,05|06,00|91,01|10,50|60,40|20,30|70,80|40,20\"},\r\n" + 
				"{\"rule\":\"qh2ds11x5\", \"data\":\"01,02|03,05|06,00|91,01|10,50|60,40|20,30|70,80|40,20\"},\r\n" + 
				"{\"rule\":\"qh2ds11x5\", \"data\":\"01,-,02,-,-|03,-,05,-,-|06,-,00,-,-|91,-,01,-,-|10,-,50,-,-|60,-,40,-,-|20,-,30,-,-|70,-,80,-,-|40,-,20,-,-\"},\r\n" + 
				"{\"rule\":\"qh3ds11x5\", \"data\":\"05,04,03|07,08,01|02,06,07|09,01,01|01,10,50|40,11,10\"},\r\n" + 
				"{\"rule\":\"qh3ds11x5\", \"data\":\"05,04,03|07,08,01|02,06,07|09,01,01|01,10,50|40,11,10\"},\r\n" + 
				"{\"rule\":\"dwd11x5\", \"data\":\"01 03 05 06 08 09 10 11,01 03 04 07 08 09,01 02 05 07 08 09 10\"},\r\n" + 
				"{\"rule\":\"bdw11x5\", \"data\":\"01 03 05 06 07 09 10 11\"},\r\n" + 
				"{\"rule\":\"r111x5\", \"data\":\"01 02 03 05 06 09 10 11\"},\r\n" + 
				"{\"rule\":\"r211x5\", \"data\":\"01 02 04 05 06 08 09 10 11\"},\r\n" + 
				"{\"rule\":\"r311x5\", \"data\":\"01 02 03 06 07 09 10\"},\r\n" + 
				"{\"rule\":\"r411x5\", \"data\":\"01 02 04 06 07 09 11\"},\r\n" + 
				"{\"rule\":\"r511x5\", \"data\":\"01 02 04 05 06 07 11\"},\r\n" + 
				"{\"rule\":\"r611x5\", \"data\":\"01 02 04 05 07 09 10 11\"},\r\n" + 
				"{\"rule\":\"r711x5\", \"data\":\"01 02 04 06 07 09 10 11\"},\r\n" + 
				"{\"rule\":\"r811x5\", \"data\":\"01 02 03 04 06 08 09 10 11\"},\r\n" + 
				"{\"rule\":\"r1ds11x5\", \"data\":\"01|03|05|06|07|09|10|11\"},\r\n" + 
				"{\"rule\":\"r2ds11x5\", \"data\":\"01,03|05,06|07,09|10,11\"},\r\n" + 
				"{\"rule\":\"r3ds11x5\", \"data\":\"01,02,03|06,07,09|10,11,06\"},\r\n" + 
				"{\"rule\":\"r4ds11x5\", \"data\":\"01,02,04,06|07,09,11,01|03,04,05,07\"},\r\n" + 
				"{\"rule\":\"r5ds11x5\", \"data\":\"01,02,04,05,06|07,11,06,04,08|03,02,01,05,09\"},\r\n" + 
				"{\"rule\":\"r6ds11x5\", \"data\":\"01,02,04,05,07,09|10,11,07,08,03,02|06,01,04,02,10,11|08,09,03,05,06,07\"},\r\n" + 
				"{\"rule\":\"r7ds11x5\", \"data\":\"01,02,04,06,07,09,10|11,03,05,08,06,01,10|08,07,03,04,02,01,05\"},\r\n" + 
				"{\"rule\":\"r8ds11x5\", \"data\":\"01,02,03,04,06,08,09,10|11,09,07,04,01,05,06,02|03,10,11,05,06,08,09,07\"},\r\n" + 
				"{\"rule\":\"r1\", \"data\":\"03 05 06 07 08 09\"},\r\n" + 
				"{\"rule\":\"dds\", \"data\":\"5单0双4单1双3单2双2单3双0单5双\"},\r\n" + 
				"{\"rule\":\"q1zx11x5\", \"data\":\"01 02 03 04 05 06 07 08 09 10 11\"},\r\n" + 
				"{\"rule\":\"q2zx11x5\", \"data\":\"01 02 03 04 05 06 07 08 09 10 11,01 02 03 04 05 06 07 08 09 10 11\"},\r\n" + 
				"{\"rule\":\"r211x5\", \"data\":\"01 02 03 04 05 06 07 08 09 10 11\"},\r\n" + 
				"{\"rule\":\"r211x5\", \"data\":\"(08)01 02 03 04 05 06 07 09 10 11\"},\r\n" + 
				"{\"rule\":\"q3zx11x5\", \"data\":\"01 02 03 04 05 06 07 08 09 10 11,01 02 03 04 05 06 07 08 09 10 11,01 02 03 04 05 06 07 08 09 10 11\"},\r\n" + 
				"{\"rule\":\"r311x5\", \"data\":\"01 02 03 04 05 06 07 08 09 10 11\"},\r\n" + 
				"{\"rule\":\"r311x5\", \"data\":\"(02)01 03 04 05 06 07 08 09 10 11\"},\r\n" + 
				"{\"rule\":\"dwd11x5\", \"data\":\"01 02 03 04 05 06 07 08 09 10 11,01 02 03 04 05 06 07 08 09 10 11,01 02 03 04 05 06 07 08 09 10 11\"},\r\n" + 
				"{\"rule\":\"bdw11x5\", \"data\":\"01 02 03 04 05 06 07 08 09 10 11\"},\r\n" + 
				"{\"rule\":\"r111x5\", \"data\":\"01 02 03 04 05 06 07 08 09 10 11\"},\r\n" + 
				"{\"rule\":\"r211x5\", \"data\":\"01 02 03 04 05 06 07 08 09 10 11\"},\r\n" + 
				"{\"rule\":\"r311x5\", \"data\":\"01 02 03 04 05 06 07 08 09 10 11\"},\r\n" + 
				"{\"rule\":\"r411x5\", \"data\":\"01 02 03 04 05 06 07 08 09 10 11\"},\r\n" + 
				"{\"rule\":\"r511x5\", \"data\":\"01 02 03 04 05 06 07 08 09 10 11\"},\r\n" + 
				"{\"rule\":\"r611x5\", \"data\":\"01 02 03 04 05 06 07 08 09 10 11\"},\r\n" + 
				"{\"rule\":\"r711x5\", \"data\":\"01 02 03 04 05 06 07 08 09 10 11\"},\r\n" + 
				"{\"rule\":\"r811x5\", \"data\":\"01 02 03 04 05 06 07 08 09 10 11\"},\r\n" + 
				"{\"rule\":\"r1\", \"data\":\"03 04 05 06 07 08 09\"},\r\n" + 
				"{\"rule\":\"dds\", \"data\":\"5单0双4单1双3单2双2单3双1单4双0单5双\"}]}";
		JSONObject obj = new JSONObject(testCondition);
		JSONArray arr = obj.getJSONArray("calcData");
		if (arr.length() == answer.size()) {
			CTTBetCountCalc calc = new CTTBetCountCalc();
			for (int i = 0; i < arr.length(); i++) {
				String result = CTTBetCountCalc.invokeCalc(calc, ((JSONObject) arr.get(i)).getString("rule"),
						((JSONObject) arr.get(i)).getString("data"));
				if (Double.parseDouble(result + "") != answer.get(i)) {
					System.out.println(i + " # " + ((JSONObject) arr.get(i)).getString("rule") + " # "
							+ ((JSONObject) arr.get(i)).getString("data") + " # Result" + result + " # Ans"
							+ answer.get(i));
				}
				assertTrue(Double.parseDouble(result + "") == answer.get(i));
			}
			System.out.println("testCTTBetCountCal_115測試完成");
		} else {
			System.out.println("輸入錯誤");
		}
	}

	public void testCTTBetCountCal_temp() {
		answer = new ArrayList<Double>();
		double[] ans = { 0 };
		for (int i = 0; i < ans.length; i++) {
			answer.add(ans[i]);
		}
		testCondition = "{\"calcData\":[{\"rule\":\"XXX\", \"data\":\"XXXXX\"}]}";
		JSONObject obj = new JSONObject(testCondition);
		JSONArray arr = obj.getJSONArray("calcData");
		if (arr.length() == answer.size()) {
			CTTBetCountCalc calc = new CTTBetCountCalc();
			for (int i = 0; i < arr.length(); i++) {
				String result = CTTBetCountCalc.invokeCalc(calc, ((JSONObject) arr.get(i)).getString("rule"),
						((JSONObject) arr.get(i)).getString("data"));
				if (Double.parseDouble(result + "") != answer.get(i)) {
					System.out.println(i + " # " + ((JSONObject) arr.get(i)).getString("rule") + " # "
							+ ((JSONObject) arr.get(i)).getString("data") + " # "
							+ ((JSONObject) arr.get(i)).getString("kj") + " # " + ((JSONObject) arr.get(i)).getInt("w")
							+ " # " + result + " # " + answer.get(i));
				}
				assertTrue(Double.parseDouble(result + "") == answer.get(i));
			}
			System.out.println("混和測試_測試完成");
		} else {
			System.out.println("輸入錯誤");
		}
	}
}