package data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Set의 정보를 가지고 있는 클래스
 * 
 * @author hongjun
 * @since 2015.4.1
 *
 */
public class Set {
	private HashMap<String, Element> mSet;
	private String mSetId;
	private int mSetCount;
	private int mMaxDuration;
	private int mMinDuration;
	private float mMeanDuration;
	private ArrayList<Integer> mDurations;

	private int mMaxTranslate;
	private int mMinTranslate;
	private float mMeanTranslate;
	private ArrayList<Integer> mTranslates;

	private int mMaxRelease;
	private int mMinRelease;
	private float mMeanRelease;
	private ArrayList<Integer> mReleases;

	public Set(String myId) {
		super();
		this.mSetId = myId;
		String[] names = mSetId.split(", ");
		this.mSetCount = names.length;
		//집합의 개수를 계산
		
		mSet = new HashMap<String, Element>();
		mDurations = new ArrayList<Integer>();
		mTranslates = new ArrayList<Integer>();
		mReleases = new ArrayList<Integer>();
		this.mMaxDuration = 0;
		this.mMinDuration = 0;
		this.mMeanDuration = 0;

		this.mMaxTranslate = 0;
		this.mMinTranslate = 0;
		this.mMeanTranslate = 0;

		this.mMaxRelease = 0;
		this.mMinRelease = 0;
		this.mMeanRelease = 0;
	}

	public void put(String key, Element video) {
		mSet.put(key, video);
	}

	public Element get(String key) {
		return mSet.get(key);
	}

	public int size() {
		return mSet.size();
	}

	public void calculateAttribute() {
		for (String key : mSet.keySet()) {
			Element video = mSet.get(key);
			mDurations.add(video.getDuration());
			mTranslates.add(video.getTranslateCounts());
			mReleases.add(video.getRelease());
		}
		/*
		 * Attribute의 최대, 최소 계산
		 */
		mDurations.sort(null);
		mMinDuration = mDurations.get(0);
		mMaxDuration = mDurations.get(mDurations.size() - 1);

		mTranslates.sort(null);
		mMinTranslate = mTranslates.get(0);
		mMaxTranslate = mTranslates.get(mTranslates.size() - 1);

		mReleases.sort(null);
		mMinRelease = mReleases.get(0);
		mMaxRelease = mReleases.get(mReleases.size() - 1);

		/*
		 * Attribute의 평균 계산
		 */
		for (int i = 0; i < mSet.size(); i++) {
			mMeanDuration += (float) mDurations.get(i);
			mMeanTranslate += (float) mTranslates.get(i);
			mMeanRelease += (float) mReleases.get(i);
		}
		mMeanDuration /= (float) mDurations.size();
		mMeanTranslate /= (float) mTranslates.size();
		mMeanRelease /= (float) mReleases.size();
	}

	public int getMaxDuration() {
		return this.mMaxDuration;
	}

	public int getMinDuration() {
		return this.mMinDuration;
	}

	public float getMeanDuration() {
		return this.mMeanDuration;
	}

	public int getMaxTranslate() {
		return this.mMaxTranslate;
	}

	public int getMinTranslate() {
		return this.mMinTranslate;
	}

	public float getMeanTranslate() {
		return this.mMeanTranslate;
	}

	public int getMaxRelease() {
		return this.mMaxRelease;
	}

	public int getMinRelease() {
		return this.mMinRelease;
	}

	public float getMeanRelease() {
		return this.mMeanRelease;
	}
	
	public int getmSetCount() {
		return mSetCount;
	}


	@Override
	public String toString() {
		String s;
		s = "Set: " + mSetId + "\n";
		Object[] key = mSet.keySet().toArray();
		for (int i = 0; i < key.length; i++) {
			s += Integer.toString(i+1) + ". "+mSet.get((String)key[i]) + "\n";
		}
		s += "Relese -> 최대: "+mMaxRelease+", 최소: "+mMinRelease+", 평균: "+mMeanRelease+"\n";
		s += "Duration -> 최대: "+mMaxDuration+", 최소: "+mMinDuration+", 평균: "+mMeanDuration+"\n";
		s += "TranslateCount -> 최대: "+mMaxTranslate+", 최소: "+mMinTranslate+", 평균: "+mMeanTranslate+"\n";
		return s;
	}

}
