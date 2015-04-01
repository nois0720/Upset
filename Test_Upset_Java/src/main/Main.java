package main;

import upset.UpSetProgram;

/**
 * 프로젝트 시작 클래스
 * @author hongjun
 * @since 2015.3.31
 */
public class Main {

	public static void main(String[] args) {
		final String inputFile = "ted_data_attr_no"; //CVS파일 이름
		
		
		UpSetProgram program = new UpSetProgram();
		
		program.readCVSFile(inputFile);//CVS파일 read
		
		//program.showDataSet("Europe");
		//program.showDataSet(); //데이터셋을 보여준다.
		
		program.startProgram(program.inputTopics()); //Upset 프로그램 시작
		
	}

}
