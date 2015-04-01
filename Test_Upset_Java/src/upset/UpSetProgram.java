package upset;

import java.util.HashMap;
import java.util.Scanner;

import data.CSVManager;
import data.Element;

/**
 * 
 * @author hongjun
 * @since 2015.3.31
 *
 */
public class UpSetProgram {
	private HashMap<String, HashMap<String, Element>> mDataSet;
	CSVManager mCSVManager = null;

	public UpSetProgram() {
		super();
		System.out.print("UpSet 프로그램 시작\n\n");
	}

	/**
	 * CVS파일을 읽는다.
	 * 
	 * @param fileName
	 *            데이터를 읽을 CVS파일 이름
	 */
	public void readCVSFile(String fileName) {
		System.out.print("CVSManager 생성\n\n");
		mCSVManager = new CSVManager();
		mCSVManager.readData(fileName);
		mDataSet = mCSVManager.getDataSet();
	}

	/**
	 * 사용자에게 입력을 받는다.
	 * 
	 * @return 입력한 topic 리스트
	 */
	public String[] inputTopics() {
		showAllTopic();
		String input;
		String[] inputData = null;
		System.out.println("Topic을 입력 해주세요 : (fish submarine oceans)");
		Scanner s = new Scanner(System.in);

		input = s.nextLine();
		inputData = input.split(" ");
		return inputData;
	}

	/**
	 * UpSet연산을 시작한다.
	 * 
	 * @param input
	 *            사용자가 입력한 topic 리스트
	 */
	public void startProgram(String[] input) {
		UpSetAlgorithms algorithms = new UpSetAlgorithms(mDataSet, input);
		algorithms.runAlogrithms();
	}

	/**
	 * 선택 가능한 토픽을 보여준다.
	 */
	public void showAllTopic() {
		System.out.println("선택할 수 있는 Topic");
		int n = 1;
		try {
			for (String key : mDataSet.keySet()) {
				System.out.print(key + "  ");
				if (n % 10 == 0) {
					System.out.println();
				}
				n++;
			}
			System.out.println("\n topic 개수: " + mDataSet.size());
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 특정 토픽의 데이터셋을 확인해 볼 수 있다.
	 * 
	 * @param topics
	 *            확인해보고 싶은 토픽
	 */
	public void showDataSet(String topics) {
		try {
			for (String key : mDataSet.get(topics).keySet()) {
				System.out
						.println(key + " -> " + mDataSet.get(topics).get(key));
			}
			System.out.println("개수: " + mDataSet.get(topics).size());
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * 전체 데이터셋을 확인해 볼 수 있다.
	 */
	public void showDataSet() {
		try {
			for (String key : mDataSet.keySet()) {
				System.out.println(key + " -> " + mDataSet.get(key));
			}
			System.out.println("개수: " + mDataSet.size());
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}
}
