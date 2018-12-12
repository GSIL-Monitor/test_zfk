package math;

import java.util.Arrays;
import java.util.Random;

public class RandomTest {

	public static void main(String[] args) {
		Random ran = new Random();
		
		for(int i=0 ;i< 100; i++){
			System.out.println(ran.nextInt(10));
		}
		
		String[] answerS = "我喜欢你|#|a".split("\\|#\\|");
		System.err.println(Arrays.toString(answerS));
		System.err.println(answerS.length);
		
	}

}
