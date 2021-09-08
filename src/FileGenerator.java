import java.io.BufferedWriter;
import java.io.FileWriter;


/* 
***********************************************
   				 test.hf 파일 
***********************************************

   (echo "Hello world!")
   (list_dir)
   (echo "**********sample.txt**********")
   (show "sample.txt")
   (mov list_dir "current_directory_list.txt")
   (echo "delete sample.txt")
   (del "sample.txt")
   
***********************************************
   				test.c 결과 파일 
***********************************************

   #inlcude <stdio.h>
   #include <stdlib.h>
   
   int main() {
   printf("Hello world!");
   system("ls -al");
   printf("**********sample.txt**********");
   system("cat sample.txt");
   system("ls > current_directory_list.txt");
   printf("delete sample.txt");
   system("rm sample.txt");
   }
   
 */


public class FileGenerator {
	
	String[] fileArr = new String[50]; // C언어로 작성된 코드들을 담을 배열, 크기는 50으로 임의 지정
	int idx = 0;
	
	public void addToFileArr(String str) {
		// hf 파일 내 코드를 C언어로 변환한 결과를 배열에 추가한다 
		fileArr[idx] = str; // 메소드 호출마다 파라미터로 받은 str 을 배열에 저장한다
		idx++;
	}
	
	public void writeFile() {
		// Output 인 C파일에 write 하는 메소드 
		
		String fileName = "/Users/isubin/eclipse-workspace/CP01_HfToC/src/test.c"; // Output 인 C 파일 
			
		try {
			// 파일 객체를 생성한다
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			
			for(int i = 0; i < idx; i++) {
				// C언어로 변환하여 담긴 배열의 원소들을 Output 파일에 write 한다 
				// fileArr.length 는 임의로 50으로 주었기 때문에 for loop는 저장된 원소 개수인 index만큼 반복한다 
				
				bw.write(fileArr[i]);
			}
			// 버퍼 비운다 
			bw.flush();
				
			// 객체를 닫는다
			bw.close();
			
			// 성공을 알리는 출력 메세지 
			System.out.println("***************************");
			System.out.println("test.hf -> test.c");
			System.out.println("변환에 성공하였습니다.");
			System.out.println("***************************");
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
