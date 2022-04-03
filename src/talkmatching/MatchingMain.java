package talkmatching;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

public class MatchingMain {
	
	public static List<People> peopleList = new ArrayList<People>();
	
	
	public static void main(String[] args) {
		ResourceBundle rb = ResourceBundle.getBundle("people");
		Set<String> keySet = rb.keySet();
		
		for(String key : keySet) {
			String value = rb.getString(key);
			// valueがnullか空白だったら何もしない
			if(null == value || value.equals("")) {
				continue;
			}
			System.out.println(key + " " + value);
			
			// 値をカンマで分割し、配列に格納する。[0]=name, [1]=entryYear
			String[] array = split(value);
			
			// Peopleのインスタンス生成とリストに格納
			People people = new People(array[0], array[1]);
			peopleList.add(people);
		}
		Iterator<People> itr = peopleList.iterator();
		
		while(itr.hasNext()) {
			People peopleFromItr = itr.next();
			
			People peopleFromList = getPeople(peopleList, peopleFromItr);
			
			showResult(peopleFromItr, peopleFromList);
//			remove(peopleFromList);
			itr.remove();
		}
	}
	
	private static String[] split(String value) {
		return value.split(",");
	}
	
	private static People getPeople(List<People> peopleList, People peopleFromItr) {
		// 乱数生成
		Random random = new Random();
		int index = random.nextInt(peopleList.size()) + 1;
		
		People peopleFromList =  peopleList.get(index);
		// entryYearが同じ場合再帰呼び出し
		if(peopleFromItr.getEntryYear().equals(peopleFromList.getEntryYear())){
			getPeople(peopleList, peopleFromItr);
		}
		return peopleFromList;
	}
	
	private static void remove(People peopleFromList) {
		peopleList.remove(peopleFromList);
	}
	private static void showResult(People peopleFromItr, People peopleFromList) {
		System.out.println(peopleFromItr.getName()+
				" と "+
				peopleFromList.getName()+
				" がペアです。");
	}
}
