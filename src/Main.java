import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/* 
 * 과목명: 컴파일러개론 1주차 과제 
 * 작성자: 201801604 이수빈 
 * 
 * 
 * OS: MacOS Big Sur 11.5.1
 * Complier: javac 16.0.2
 * IDE: Eclipse 
 * 
 * 
 * Java Terminal Command
 * Compile : javac FileGenerator.java Main.java
 * Run : java Main.java 
 * 
 * 
 * C file Run Command Using gcc
 * Compiler: gcc 4.2.1
 * gcc test.c // a.out 실행파일 생성
 * ./a.out    // c 파일 실행 
 * 
 */

public class Main {

	public static void main(String[] args) throws IOException {
		
		// Input 인 test.hf 파일을 읽기 위한 객체를 만든다 
		BufferedReader br = new BufferedReader(new FileReader("/Users/isubin/eclipse-workspace/CP01_HfToC/src/test.hf"));
		

		FileGenerator fileGenerator = new FileGenerator();

		/* 어느 output 이나 미리 존재하는 구문을 추가한다 
		 *  #inlcude <stdio.h>
		 *  #include <stdlib.h>
		 *  
		 *  int main() {
		 */
		
		fileGenerator.addToFileArr("#include <stdio.h>\n" +
								   "#include <stdlib.h>\n\n" +
								   "int main(){\n");
		
		
		while(true) {
			String line = br.readLine(); // 줄 하나를 리턴한다 
			if(line == null) break; 	 // input 파일이 비어있을 경우 while loop break
			
			//System.out.println(line);  // 파일 내용을 한 줄씩 출력한다 
			
			char openBracket = line.charAt(0); // hf 파일 명령어의 첫 문자는 열린괄호로 시작한다 
			
			
			if(openBracket == '(') { 
			// 입력파일의 형식이 일치할 경우 (열린괄호로 시작할 경우) 정상작동 
				
				String[] splitedToken = line.split(" ");  // 공백을 기준으로 split하여 명령어 Token 을 가져온다 (echo / "Hello / World") 
				String cmdToken = splitedToken[0];	  	  // 명령어 Token 은 배열의 0번째 원소에 있다 ex. (echo
				cmdToken = cmdToken.substring(1); 		  // 열린괄호를 제거하기 위해 문자열을 추출한다 ex. echo
				
				//System.out.println(cmdToken);
				
				
				switch (cmdToken) { 
				// cmdToken 에 따라 hf 파일의 내용을 c 파일로 바꾼다 
				
					case "echo" :
						// (echo "Hello world!")                  ->  printf("Hello wolrd!");
						// (echo "**********test.txt**********")  ->  printf("**********test.txt**********");
						// (echo "delete test.txt")               ->  printf("delete test.txt");
						// 명령어 다음으로 오는 부분을 화면에 출력한다 
						
						fileGenerator.addToFileArr("printf(");
						
						for(int i = 1; i < splitedToken.length; i++) {
							// echo 다음 원소부터 배열 하나씩 넣는다 
							fileGenerator.addToFileArr(splitedToken[i]);
							
							if(i != splitedToken.length-1) {
								fileGenerator.addToFileArr(" ");
							}
						}
						
						fileGenerator.addToFileArr(";\n");
						
						break;
							
						
					case "list_dir)" :
						// (list_dir) -> system("ls -al");
						// 현재 디렉토리의 모든 파일을 보여준다 
						
						fileGenerator.addToFileArr("system(\"ls -al\");\n");
						
						break;
						
					 
					case "show":
						// (show "sample.txt") -> system("cat sample.txt");
						// 파일 내용 전체를 화면에 보여준다 
						
						fileGenerator.addToFileArr("system(\"cat " + splitedToken[1].substring(1) + ";\n");
						
						break;
						
						
					case "mov":
						// (mov list_dir "test_list.txt") -> system("ls > test_list.txt")
						// 현재 디렉토리의 리스트를 해당 파일에 저장한다 
						
						fileGenerator.addToFileArr("system(\"ls > " + splitedToken[2].substring(1) + ";\n");
									
						break;
	
					
					case "del":
						// (del "test.txt") -> system(rm test.txt");
						// 현재 디렉토리의 해당 파일을 제거한다 
						
						fileGenerator.addToFileArr("system(\"rm " + splitedToken[1].substring(1) + ";\n"); 
						
						break;						
						
						
					default:
						// case 에 해당하는 명령어가 없을 경우이다 
						System.out.println("해당하는 명령어가 없습니다.");
						break;
					
				} // End of switch
			} // End of if
			
			
			// 입력파일의 명령어 형식이 올바르지 않으면 (열린괄호로 시작하지 않으면)  
			else {
				System.out.println("입력파일의 명령어 형식이 올바르지 않습니다.");
			}
	
		} // End of while loop
		
		fileGenerator.addToFileArr("}"); // c파일 main() 괄호 닫기
		
		br.close();
		
		fileGenerator.writeFile();
		
	} // End of method

} // End of class
