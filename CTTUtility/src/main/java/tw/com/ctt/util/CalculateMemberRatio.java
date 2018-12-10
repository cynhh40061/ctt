package tw.com.ctt.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculateMemberRatio {
	
	public static void main(String[] argvs){
		List<Map<String, Integer>> managerRatio =  new ArrayList<Map<String, Integer>>();
		Map<String, Integer> layer1 = new HashMap<String, Integer>();
		Map<String, Integer> layer2 = new HashMap<String, Integer>();
		Map<String, Integer> layer3 = new HashMap<String, Integer>();
		Map<String, Integer> layer4 = new HashMap<String, Integer>();
		Map<String, Integer> layer5 = new HashMap<String, Integer>();
		Map<String, Integer> layer6 = new HashMap<String, Integer>();
		Map<String, Integer> layer7 = new HashMap<String, Integer>();
		Map<String, Integer> layer8 = new HashMap<String, Integer>();
		Map<String, Integer> layer9 = new HashMap<String, Integer>();
		Map<String, Integer> layer10 = new HashMap<String, Integer>();
		Map<String, Integer> layer11 = new HashMap<String, Integer>();
		Map<String, Integer> layer12 = new HashMap<String, Integer>();
		Map<String, Integer> layer13 = new HashMap<String, Integer>();
		Map<String, Integer> layer14 = new HashMap<String, Integer>();
		Map<String, Integer> layer15 = new HashMap<String, Integer>();
		Map<String, Integer> layer16 = new HashMap<String, Integer>();
		
		Map<String, Integer> playLayer = new HashMap<String, Integer>();

		
//		layer1.put("upperMin", 20);				// [5, 35, 10, 5, 15, 30]
//		layer1.put("upperMax", 80);
//		layer1.put("currentMax", 60);
//		
//		layer2.put("upperMin", 10);
//		layer2.put("upperMax", 60);
//		layer2.put("currentMax", 40);
//		
//		layer3.put("upperMin", 10);
//		layer3.put("upperMax", 40);
//		layer3.put("currentMax", 30);
//		
//		layer4.put("upperMin", 10);
//		layer4.put("upperMax", 30);
//		layer4.put("currentMax", 20);
//		
//		layer5.put("upperMin", 10);
//		layer5.put("upperMax", 20);
//		layer5.put("currentMax", 5);
//		
//		playLayer.put("upperMin", 5);		
//		playLayer.put("upperMax", 5);	
//		
//		managerRatio.add(layer1);
//		managerRatio.add(layer2);
//		managerRatio.add(layer3);
//		managerRatio.add(layer4);
//		managerRatio.add(layer5);
//		managerRatio.add(playLayer);
		
//		layer1.put("upperMin", 10);			//[90, 0, 0, 10]
//		layer1.put("upperMax", 100);
//		layer1.put("currentMax", 80);
//		
//		layer2.put("upperMin", 0);
//		layer2.put("upperMax", 0);
//		layer2.put("currentMax", 20);
//		
//		layer3.put("upperMin", 0);
//		layer3.put("upperMax", 0);
//		layer3.put("currentMax", 10);
//		
//		playLayer.put("upperMin", 10);		
//		playLayer.put("upperMax", 10);	
//		
//		managerRatio.add(layer1);
//		managerRatio.add(layer2);
//		managerRatio.add(layer3);
//		managerRatio.add(playLayer);
		
		
//		layer1.put("upperMin", 0);				// [10, 10, 10, 5, 10, 10, 10, 35]
//		layer1.put("upperMax", 100);
//		layer1.put("currentMax", 90);
//		
//		layer2.put("upperMin", 5);
//		layer2.put("upperMax", 80);
//		layer2.put("currentMax", 80);
//		
//		layer3.put("upperMin", 10);
//		layer3.put("upperMax", 40);
//		layer3.put("currentMax", 70);
//		
//		layer4.put("upperMin", 5);
//		layer4.put("upperMax", 40);
//		layer4.put("currentMax", 65);
//		
//		layer5.put("upperMin", 5);
//		layer5.put("upperMax", 40);
//		layer5.put("currentMax", 55);
//		
//		layer6.put("upperMin", 5);
//		layer6.put("upperMax", 40);
//		layer6.put("currentMax", 45);
//		
//		layer7.put("upperMin", 5);
//		layer7.put("upperMax", 40);
//		layer7.put("currentMax", 40);
//		
//		playLayer.put("upperMin", 35);		
//		playLayer.put("upperMax", 35);	
//		
//		managerRatio.add(layer1);
//		managerRatio.add(layer2);
//		managerRatio.add(layer3);
//		managerRatio.add(layer4);
//		managerRatio.add(layer5);
//		managerRatio.add(layer6);
//		managerRatio.add(layer7);
//		managerRatio.add(playLayer);
		
//		layer1.put("upperMin", 0);				// [10, 10, 10, 10, 10, 5, 10, 35]
//		layer1.put("upperMax", 100);
//		layer1.put("currentMax", 90);
//		
//		layer2.put("upperMin", 0);
//		layer2.put("upperMax", 90);
//		layer2.put("currentMax", 80);
//		
//		layer3.put("upperMin", 10);
//		layer3.put("upperMax", 60);
//		layer3.put("currentMax", 70);
//		
//		layer4.put("upperMin", 10);
//		layer4.put("upperMax", 60);
//		layer4.put("currentMax", 60);
//		
//		layer5.put("upperMin", 10);
//		layer5.put("upperMax", 60);
//		layer5.put("currentMax", 50);
//		
//		layer6.put("upperMin", 0);
//		layer6.put("upperMax", 50);
//		layer6.put("currentMax", 45);
//		
//		layer7.put("upperMin", 5);
//		layer7.put("upperMax", 45);
//		layer7.put("currentMax", 40);
//		
//		playLayer.put("upperMin", 35);		
//		playLayer.put("upperMax", 35);	
//		
//		managerRatio.add(layer1);
//		managerRatio.add(layer2);
//		managerRatio.add(layer3);
//		managerRatio.add(layer4);
//		managerRatio.add(layer5);
//		managerRatio.add(layer6);
//		managerRatio.add(layer7);
//		managerRatio.add(playLayer);
		
		
//		layer1.put("upperMin", 20);				
//		layer1.put("upperMax", 80);
//		layer1.put("currentMax", 70);
//		
//		layer2.put("upperMin", 10);
//		layer2.put("upperMax", 70);
//		layer2.put("currentMax", 55);
//		
//		layer3.put("upperMin", 5);
//		layer3.put("upperMax", 40);
//		layer3.put("currentMax", 50);
//		
//		layer4.put("upperMin", 0);
//		layer4.put("upperMax", 15);
//		layer4.put("currentMax", 45);
//		
//		layer5.put("upperMin", 30);
//		layer5.put("upperMax", 35);
//		layer5.put("currentMax", 15);
//		
//		playLayer.put("upperMin", 5);		
//		playLayer.put("upperMax", 5);	
//		
//		managerRatio.add(layer1);
//		managerRatio.add(layer2);
//		managerRatio.add(layer3);
//		managerRatio.add(layer4);
//		managerRatio.add(layer5);
//		managerRatio.add(playLayer);
	
		
//		layer1.put("upperMin", 20);				//[5, 15, 20, 20, 15, 25]
//		layer1.put("upperMax", 80);
//		layer1.put("currentMax", 75);
//		
//		layer2.put("upperMin", 5);
//		layer2.put("upperMax", 30);
//		layer2.put("currentMax", 60);
//		
//		layer3.put("upperMin", 15);
//		layer3.put("upperMax", 60);
//		layer3.put("currentMax", 40);
//		
//		layer4.put("upperMin", 15);
//		layer4.put("upperMax", 20);
//		layer4.put("currentMax", 20);
//		
//		layer5.put("upperMin", 10);
//		layer5.put("upperMax", 20);
//		layer5.put("currentMax", 5);
//		
//		playLayer.put("upperMin", 5);		
//		playLayer.put("upperMax", 5);		
		
//		managerRatio.add(layer1);
//		managerRatio.add(layer2);
//		managerRatio.add(layer3);
//		managerRatio.add(layer4);
//		managerRatio.add(layer5);
//		managerRatio.add(playLayer);
//	

//		layer1.put("upperMin", 0);				//[40, 0, 0, 0, 0, 60]
//		layer1.put("upperMax", 100);
//		layer1.put("currentMax", 70);
//		
//		layer2.put("upperMin", 0);
//		layer2.put("upperMax", 0);
//		layer2.put("currentMax", 60);
//		
//		layer3.put("upperMin", 0);
//		layer3.put("upperMax", 0);
//		layer3.put("currentMax", 60);
//		
//		layer4.put("upperMin", 0);
//		layer4.put("upperMax", 0);
//		layer4.put("currentMax", 60);
//		
//		layer5.put("upperMin", 0);
//		layer5.put("upperMax", 0);
//		layer5.put("currentMax", 60);
//		
//		playLayer.put("upperMin", 60);		
//		playLayer.put("upperMax", 60);		
//		
//		managerRatio.add(layer1);
//		managerRatio.add(layer2);
//		managerRatio.add(layer3);
//		managerRatio.add(layer4);
//		managerRatio.add(layer5);
//		managerRatio.add(playLayer);
		
		
//		layer1.put("upperMin", 20);			//[10, 10, 20, 30, 30] 
//		layer1.put("upperMax", 80);
//		layer1.put("currentMax", 70);
//		
//		layer2.put("upperMin", 10);
//		layer2.put("upperMax", 40);
//		layer2.put("currentMax", 50);
//		
//		layer3.put("upperMin", 10);
//		layer3.put("upperMax", 20);
//		layer3.put("currentMax", 30);
//		
//		layer4.put("upperMin", 10);
//		layer4.put("upperMax", 10);
//		layer4.put("currentMax", 20);
//		
//		playLayer.put("upperMin", 10);		
//		playLayer.put("upperMax", 10);	
//		
//		managerRatio.add(layer1);
//		managerRatio.add(layer2);
//		managerRatio.add(layer3);
//		managerRatio.add(layer4);
//		managerRatio.add(playLayer);
		

		
		layer1.put("upperMin", 0);			//[10, 10, 20, 30, 30] 
		layer1.put("upperMax", 100);
		layer1.put("currentMax", 90);
		
		layer2.put("upperMin", 30);
		layer2.put("upperMax", 75);
		layer2.put("currentMax", 50);
		
		layer3.put("upperMin", 25);
		layer3.put("upperMax", 25);
		layer3.put("currentMax", 25);
		
		layer4.put("upperMin", 5);
		layer4.put("upperMax", 25);
		layer4.put("currentMax", 20);
		
		layer5.put("upperMin", 5);
		layer5.put("upperMax", 20);
		layer5.put("currentMax", 15);

		layer6.put("upperMin", 5);
		layer6.put("upperMax", 15);
		layer6.put("currentMax", 10);
		
		layer7.put("upperMin", 5);
		layer7.put("upperMax", 10);
		layer7.put("currentMax", 5);
		
		layer8.put("upperMin", 5);
		layer8.put("upperMax", 5);
		layer8.put("currentMax", 0);
		
		layer9.put("upperMin", 0);
		layer9.put("upperMax", 0);
		layer9.put("currentMax", 0);
		
		layer10.put("upperMin", 0);
		layer10.put("upperMax", 0);
		layer10.put("currentMax", 0);
		
		layer11.put("upperMin", 0);
		layer11.put("upperMax", 0);
		layer11.put("currentMax", 0);
		
		layer12.put("upperMin", 0);
		layer12.put("upperMax", 0);
		layer12.put("currentMax", 0);
		
		layer13.put("upperMin", 0);
		layer13.put("upperMax", 0);
		layer13.put("currentMax", 0);
		
		layer14.put("upperMin", 0);
		layer14.put("upperMax", 0);
		layer14.put("currentMax", 0);
		
		layer15.put("upperMin", 0);
		layer15.put("upperMax", 0);
		layer15.put("currentMax", 0);
		
		layer16.put("upperMin", 0);
		layer16.put("upperMax", 0);
		layer16.put("currentMax", 0);
		
		playLayer.put("upperMin", 0);		
		playLayer.put("upperMax", 0);	
		
		managerRatio.add(layer1);
		managerRatio.add(layer2);
		managerRatio.add(layer3);
		managerRatio.add(layer4);
		managerRatio.add(layer5);
		managerRatio.add(layer6);
		managerRatio.add(layer7);
		managerRatio.add(layer8);
		managerRatio.add(layer9);
		managerRatio.add(layer10);
		managerRatio.add(layer11);
		managerRatio.add(layer12);
		managerRatio.add(layer13);
		managerRatio.add(layer14);
		managerRatio.add(layer15);
		managerRatio.add(layer16);
		
		managerRatio.add(playLayer);
		
		List<Integer> result = getRatio(managerRatio);		
//		System.out.println(result);
	}
	
	public static List<Integer> getRatio(List<Map<String, Integer>> managerRatioInfo){
		List<Integer> result = new ArrayList<Integer>();
		int totalBalance = 0;
		for(int i = 0;i < managerRatioInfo.size();i++){
			Map<String, Integer> tmpLayer = managerRatioInfo.get(i);
			if(i == 0){
//				tmpLayer.put("realMax", 80);
				tmpLayer.put("realMax", 100);
			}
			else{
				tmpLayer.put("realMax", managerRatioInfo.get(i-1).get("currentMax"));
			}
		}		
		for(int i = managerRatioInfo.size() -1;i >=0 ;i--){					
			int eachLayerRatio;				
			if((managerRatioInfo.get(i).get("realMax") - totalBalance) < managerRatioInfo.get(i).get("upperMax")){
				eachLayerRatio = (managerRatioInfo.get(i).get("realMax") - totalBalance);
			}else{
				eachLayerRatio = managerRatioInfo.get(i).get("upperMax");
			}			
			totalBalance = totalBalance + eachLayerRatio;		
			result.add(eachLayerRatio);
		}	
		//result.set(result.size()-1, result.get(result.size()-1) + 20);
//		while(result.size() < 6){
////		while(result.size() < 8){
//			result.add(0, -1);
//		}		
		Collections.reverse(result);
		return result;
		
	}
}
