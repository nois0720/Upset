package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

/**
 * CVS파일의 입출력을 관리한다.
 * 
 * @author hongjun
 * @since 2015.3.31
 */
public class CSVManager {
	private CSVReader mReader = null;
	private CSVWriter mWriter = null;
	private String mFileInputPath = null;
	private String mFileOutputPath = null;
	private boolean isTag = true;
	/*
	 * CVS파일을 이중 Hash구조로 저장한다.
	 */
	private HashMap<String, HashMap<String, Element>> mRow = new HashMap<String, HashMap<String, Element>>();

	/*
	 * 토픽의 위치를 가진다.
	 */
	private ArrayList<String> mTopics = new ArrayList<String>();
	

	public CSVManager() {
		super();
	}

	/**
	 * CVS파일을 읽는다.
	 * 
	 * @param fileName
	 *            데이터를 읽을 CVS파일 이름.
	 */
	public void readData(String fileName) {
		mFileInputPath = fileName;
		mReader = null;
		try {
			mReader = new CSVReader(new FileReader("data/" + mFileInputPath
					+ ".csv"));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		System.out.print(mFileInputPath + " 파일 열기 성공\n\n");
		String[] nextLine = null;

		String id = null;
		String title = null;
		int duration = 0;
		int release = 0;
		int translate = 0;
		int number = 1;
		try {
			while ((nextLine = mReader.readNext()) != null) {
				if (isTag) {
					for (int i = 0; i < nextLine.length; i++) {
						mTopics.add(nextLine[i]);
						if (i >= 4) {
							mRow.put(nextLine[i], new HashMap<String, Element>());
						}

					}
					isTag = false;

				} else {
					id = "v" + number;
					title = nextLine[0];
					duration = Integer.parseInt(nextLine[1]);
					release = Integer.parseInt(nextLine[2]);
					translate = Integer.parseInt(nextLine[3]);

					Element parentVideo = new Element(id, title, duration, release,
							translate);
					// 비디오 정보저장
					for (int i = 4; i < nextLine.length; i++) {
						if (Integer.parseInt(nextLine[i]) == 1) {
							parentVideo.addTopic(mTopics.get(i));
						}
					}
					/*
					 * 토빅별로 영상 분배
					 */
					ArrayList<String> topics = parentVideo.getTopics();
					for (int i = 0; i < topics.size(); i++) {
						HashMap<String, Element> column = mRow.get(topics.get(i));
						Element childVideo = new Element(parentVideo.getId(),
								parentVideo.getName(),
								parentVideo.getDuration(),
								parentVideo.getRelease(),
								parentVideo.getTranslateCounts());
						childVideo.setParents(topics.get(i));
						childVideo.addAllTopic(parentVideo.getTopics());
						column.put(childVideo.getId(), childVideo);
					}
					number++;
				}

			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		System.out.print("파일 읽기 완료.\n\n");
	}

	/**
	 * CVS파일을 쓴다.
	 * 
	 * @param fileName
	 *            데이터를 기록할 CVS파일 이름.
	 */
	public void writeData(String fileName) {
		mFileOutputPath = fileName;
		mWriter = null;
		String[] record;
		try {
			mWriter = new CSVWriter(new FileWriter("src\\" + mFileOutputPath));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		// Create record
		record = new String[7];

		mWriter.writeNext(record);

		try {
			mWriter.close();
			// close the writer
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public HashMap<String, HashMap<String, Element>> getDataSet() {
		return this.mRow;
	}
}
