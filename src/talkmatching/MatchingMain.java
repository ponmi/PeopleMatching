package talkmatching;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

public class MatchingMain {

	public static List<People> peopleList = new ArrayList<People>();


	public static void main(String[] args) {
		run();



	}
	private static void run() {
		ResourceBundle rb = ResourceBundle.getBundle("people");
		Set<String> keySet = rb.keySet();
		try {
			for(String key : keySet) {
				String value = rb.getString(key);
				value = changeEncode(value);
//				value = new String(value.getBytes("8859_1"),"UTF-8");

				// valueがnullか空白だったら何もしない
				if(null == value || value.equals("")) {
					continue;
				}
				System.out.println(key + " " + value);

				// 値をカンマで分割し、配列に格納する。[0]=id, [1]=name, [2]=entryYear
				String[] array = split(value);

				// Peopleのインスタンス生成とリストに格納
				People people = new People(array[0], array[1], array[2]);
				peopleList.add(people);
			}
			List<People> copiedPeopleList = createCopiedList();
			Iterator<People> itr = peopleList.iterator();

			while(itr.hasNext()) {
				People peopleFromItr = itr.next();
				People peopleFromList = null;
				if(!(copiedPeopleList.size() == 0)) {
					peopleFromList = getPeople(copiedPeopleList, peopleFromItr);
				}

				showResult(peopleFromItr, peopleFromList);
				copiedPeopleList = remove(peopleFromList, copiedPeopleList);
				copiedPeopleList = remove(peopleFromItr, copiedPeopleList);
				itr.remove();
			}
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

	}
	private static String changeEncode(String value) throws UnsupportedEncodingException {
		return value = new String(value.getBytes("8859_1"),"UTF-8");
	}

	private static String[] split(String value) {
		return value.split(",");
	}

	private static List<People> createCopiedList() throws CloneNotSupportedException {
		List<People> copiedPeopleList = new ArrayList<People>();
		for(int i=0; i<peopleList.size(); i++ ) {
			copiedPeopleList.add(peopleList.get(i).clone());
		}
		return copiedPeopleList;
	}

	private static People getPeople(List<People> copiedPeopleList, People peopleFromItr) {
		// 乱数生成
		Random random = new Random();
		int index = random.nextInt(copiedPeopleList.size());

		People peopleFromList =  copiedPeopleList.get(index);
		// idまたはentryYearが同じ場合再帰呼び出し
		if(peopleFromItr.getEntryYear().equals(peopleFromList.getEntryYear()) ||
				peopleFromItr.getId().equals(peopleFromList.getId()))
		{
			getPeople(copiedPeopleList, peopleFromItr);
		}
		return peopleFromList;
	}

	private static List<People> remove(People peopleFromList, List<People> copiedPeopleList) {
		copiedPeopleList.remove(peopleFromList);
		return copiedPeopleList;
	}
	private static void showResult(People peopleFromItr, People peopleFromList) {
		System.out.println(peopleFromItr.getName()+
				" と "+
				peopleFromList.getName()+
				" がペアです。");
	}
}
