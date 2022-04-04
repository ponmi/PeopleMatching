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
			System.out.println("========以下のメンバーをマッチさせます========");
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
			System.out.println("");
			List<People> copiedPeopleList = createCopiedList();
			Iterator<People> itr = peopleList.iterator();

			System.out.println("========マッチ開始========");
			System.out.println("");
			while(itr.hasNext()) {
				People peopleFromItr = itr.next();
				People peopleFromList = null;

				if((copiedPeopleList.size() == 0)) {
					break;
				}else {
					System.out.println(peopleFromItr.getName()+" のマッチ相手をさがします。");
					peopleFromList = getPeopleAtRandom(copiedPeopleList, peopleFromItr);
					showResult(peopleFromItr, peopleFromList);
					copiedPeopleList = remove(peopleFromList, copiedPeopleList);
					copiedPeopleList = remove(peopleFromItr, copiedPeopleList);
					itr.remove();
				}
			}
			System.out.println("========マッチ終了========");
			System.out.println("");
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 文字のエンコードを変更し、返却する。<BR>
	 * @param value 文字列
	 * @return 文字コードを変更した文字列
	 * @throws UnsupportedEncodingException
	 */
	private static String changeEncode(String value) throws UnsupportedEncodingException {
		return value = new String(value.getBytes("8859_1"),"UTF-8");
	}

	/**
	 * 文字列をカンマで分割し配列に格納し返却する。<BR>
	 * @param value 文字列
	 * @return カンマで分割された文字列が入った配列
	 */
	private static String[] split(String value) {
		return value.split(",");
	}

	/**
	 * peopleListをディープコピーして、返却する。<BR>
	 * @return peopleListをディープコピーしたリスト
	 * @throws CloneNotSupportedException
	 */
	private static List<People> createCopiedList() throws CloneNotSupportedException {
		List<People> copiedPeopleList = new ArrayList<People>();
		for(int i=0; i<peopleList.size(); i++ ) {
			copiedPeopleList.add(peopleList.get(i).clone());
		}
		return copiedPeopleList;
	}

	/**
	 *
	 * @param copiedPeopleList
	 * @param peopleFromItr
	 * @return
	 */
	private static People getPeopleAtRandom(List<People> copiedPeopleList, People peopleFromItr) {
		People peopleFromList = null;
		try {
			// 乱数生成
			int size = copiedPeopleList.size();
			int index = getRandomInt(size);
			if(size == 1) {
				return copiedPeopleList.get(0);
			}
			peopleFromList =  copiedPeopleList.get(index);
			// idまたはentryYearが同じ場合再帰呼び出し
			if(isSameEntryYear(peopleFromItr, peopleFromList) ||
					isSamePeople(peopleFromItr, peopleFromList))
			{
				peopleFromList = getPeopleAtRandom(copiedPeopleList, peopleFromItr);
			}
		}catch (StackOverflowError e) {
			e.printStackTrace();
		}
		return peopleFromList;
	}

	/**
	 * 引数の範囲までの乱数を生成する。
	 * @param source
	 * @return 乱数
	 */
	private static int getRandomInt(int source) {
		Random random = new Random();
		int index = random.nextInt(source);
		return index;
	}

	/**
	 * 第二引数のリストから要素を取り出し、第一引数の要素と比較し
	 * 同じである場合は、第一引数のPeopleを第二引数のリストから削除する。<BR>
	 * @param people
	 * @param peopleList
	 * @return リスト
	 */
	private static List<People> remove(People people, List<People> peopleList) {
		for(People copiedPeople : peopleList) {
			if(isSamePeople(copiedPeople, people)) {
				peopleList.remove(copiedPeople);
				break;
			}
		}
		return peopleList;
	}
	/**
	 * マッチしたペアの結果をコンソールに表示する。<BR>
	 * @param peopleFromItr
	 * @param peopleFromList
	 */
	private static void showResult(People peopleFromItr, People peopleFromList) {
		String sameYearSign = "";
		String samePeopleSign = "";
		if(isSameEntryYear(peopleFromItr, peopleFromList)){
			sameYearSign = "★";
		}
		if(isSamePeople(peopleFromItr, peopleFromList)){
			samePeopleSign = "＝";
		}
		System.out.println(
				peopleFromItr.getName()+"("+peopleFromItr.getEntryYear()+")"+
				" と "+
				peopleFromList.getName()+"("+peopleFromList.getEntryYear()+")"+
				" がペアです。"+
				sameYearSign +
				samePeopleSign
				);
		System.out.println("");
	}

	private static boolean isSameEntryYear(People p1, People p2) {
		if(p1.getEntryYear().equals(p2.getEntryYear())){
			return true;
		}
		return false;
	}

	private static boolean isSamePeople(People p1, People p2) {
		if(p1.getId().equals(p2.getId())){
			return true;
		}
		return false;
	}
}
