package upset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import data.Set;
import data.Element;

/**
 * 집합 연산을 하는 클래스
 * 
 * @author hongjun
 *
 */
public class UpSetAlgorithms {
	private HashMap<String, HashMap<String, Element>> mDataSet; //처음 데이터셋이다.
	private HashMap<String, HashMap<String, Element>> mSelectedTopicSet; //유저가 선택한 Set의 정보를 가지고 있다.
	private HashMap<String, Set> mSetResultItem; //Set연산의 결과로 item 정보가 있다.
	
	private ArrayList<String> mSetNames; //교집합되는 Set의 이름들을 가지고 있다.
	private HashMap<String, Element> mSelectedVideo; //유저가 선택한 Set에 대한 item을 중복없이 가지고 있다.
	private String[] inputData; //유저가 선택한 Set의 이름이 있다.
	
	public UpSetAlgorithms(HashMap<String, HashMap<String, Element>> myData,
			String[] inputs) {
		this.mDataSet = myData;
		this.inputData = inputs;
	}

	/**
	 * 교집합 찾는 알고리즘을 수행한다.
	 */
	public void runAlogrithms() {
		System.out.println("알고리즘 수행중...\n");
		selectTopics();
		calculateIntersection();
		calculateSetAttribution();
		System.out.println("알고리즘 종료!\n");
		showResult();
	}

	/**
	 * 사용자가 선택한 topic들을 새로운 HashMap에서 관리한다.
	 */
	private void selectTopics() {
		mSelectedTopicSet = new HashMap<String, HashMap<String, Element>>();
		for (int i = 0; i < inputData.length; i++) {
			mSelectedTopicSet.put(inputData[i], mDataSet.get(inputData[i]));
		}
	}

	/**
	 *집합 연산을 찾는다.
	 */
	private void calculateIntersection() {
		System.out.println("집합연산중...");
		mSelectedVideo = new HashMap<String, Element>();
		mSetResultItem = new HashMap<String, Set>();
		try {
			for (String outterKey : mSelectedTopicSet.keySet()) {// 토빅별로 검색
				for (String innerKey : mSelectedTopicSet.get(outterKey)
						.keySet()) { // 영상별로
										// 검색

					/*
					 * 선택된 Set들의 강연들을 중복안되게 따로 저장한다.
					 */
					Element v = mSelectedTopicSet.get(outterKey).get(innerKey); // 강연
																				// 선택
					if (mSelectedVideo.get(v.getId()) == null) {
						mSelectedVideo.put(v.getId(), v);
					}

				}
			}

			for (String hashKey : mSelectedVideo.keySet()) {
				Element selectedVdieo = mSelectedVideo.get(hashKey);
				// System.out.println(selectedVdieo.getId());
				mSetNames = new ArrayList<String>();
				/*
				 * 영상의 임시 저장소로 연산후 여러 강연이 쌓이면 교집합 HashMap에 저장하고,한개만 있을 경우 따로 저장을
				 * 안한다.
				 */
				for (String searchKey : mSelectedTopicSet.keySet()) {
					if (mSelectedTopicSet.get(searchKey).get(
							selectedVdieo.getId()) != null) {
						Element video = mSelectedTopicSet.get(searchKey).get(
								selectedVdieo.getId());
						mSetNames.add(video.getParents());
					}
				}
				if (mSetNames.size() > 1) {// 교집합이 생길때
					/*
					 * 집합 명칭만들기
					 */
					mSetNames.sort(null);
					String setName = "";
					for (int i = 0; i < mSetNames.size(); i++) {
						setName += mSetNames.get(i);
						if (mSetNames.size() - 1 != i) {
							setName += ", ";
						}
					}
					if (mSetResultItem.get(setName) == null) {// 처음 집합데이터 만들 때,
						Set set = new Set(setName);
						//HashMap<String, Video> set = new HashMap<String, Video>();
						set.put(selectedVdieo.getId(), selectedVdieo);
						mSetResultItem.put(setName, set);
					} else {
						Set set = mSetResultItem.get(setName);
						//HashMap<String, Video> set = mSetResultItem.get(setName);
						set.put(selectedVdieo.getId(), selectedVdieo);
					}
				} else {// 교집합이 아닐때
					String setName = mSetNames.get(0);
					if (mSetResultItem.get(setName) == null) {// 처음 집합데이터 만들 때,
						//HashMap<String, Video> set = new HashMap<String, Video>();
						Set set = new Set(setName);
						set.put(selectedVdieo.getId(), selectedVdieo);
						mSetResultItem.put(setName, set);
					} else {
						Set set = mSetResultItem.get(setName);
						//HashMap<String, Video> set = mSetResultItem.get(setName);
						set.put(selectedVdieo.getId(), selectedVdieo);
					}
				}
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * Set의 Attribute를 계산한다.
	 */
	public void calculateSetAttribution(){
		System.out.println("Attribute 연산중...\n");
		for(String key: mSetResultItem.keySet()){
			Set set = mSetResultItem.get(key);
			set.calculateAttribute();
		}
	}
	
	/**
	 * Set를 개수에 따라 sort한 후 conselo창에 보여준다. 
	 */
	public void showResult() {
		/*
		 * Set을 개수에 따라 정렬
		 */
		List<Set> setByCount = new ArrayList<Set>(mSetResultItem.values());
	    Collections.sort(setByCount, new Comparator<Set>() {

	        public int compare(Set o1, Set o2) {
	            return o1.getmSetCount() - o2.getmSetCount();
	        }
	    });
	    System.out.println("집합 연산 결과.\n");
	    for (Set set : setByCount) {
	        System.out.print(set);
			System.out.println("Item의 개수: "+set.size()+"\n");
	    }System.out.println("Set의 개수: "+setByCount.size());
	}
}
