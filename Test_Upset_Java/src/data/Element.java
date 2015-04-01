package data;

import java.util.ArrayList;

/**
 * Element의 정보를 가지고 있는 클래스
 * 
 * @author hongjun
 * @since 2015.3.31
 *
 */
public class Element {
	private String mId;
	private String mName;
	private int mRelease;
	private int mDuration;
	private int mTranslateCounts;
	private ArrayList<String> mTopics;
	private String mParents;

	
	public Element(String id, String name, int duration, int release,
			int translateCounts) {
		super();
		this.mId = id;
		this.mName = name;
		this.mDuration = duration;
		this.mRelease = release;
		this.mTranslateCounts = translateCounts;
		this.mTopics = new ArrayList<String>();
	}

	public void addTopic(String topic) {
		this.mTopics.add(topic);
	}
	public void addAllTopic(ArrayList<String> topics) {
		this.mTopics.addAll(topics);
	}
	public String getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}

	public int getRelease() {
		return mRelease;
	}

	public int getDuration() {
		return mDuration;
	}

	public int getTranslateCounts() {
		return mTranslateCounts;
	}

	public ArrayList<String> getTopics() {
		return mTopics;
	}
	
	public String getParents() {
		return mParents;
	}

	public void setParents(String mParents) {
		this.mParents = mParents;
	}

	@Override
	public String toString(){
		String s;
		s = "id: "+mId+", 이름: "+mName+", 재생: "+mDuration+", 날짜: "+mRelease+", 번역: "+mTranslateCounts+", topics-> ";
		for(int i = 0; i < mTopics.size(); i++){
			s += mTopics.get(i);
			if(i != mTopics.size()-1){
				s += ", ";
			}
		}
		return s;
	}
}
