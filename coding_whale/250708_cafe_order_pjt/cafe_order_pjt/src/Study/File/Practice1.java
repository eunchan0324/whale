package Study.File;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// 1단계 : 문자열 하나 저장/읽기
public class Practice1 {
    public static void main(String[] args) throws IOException {

        // 저장
        FileWriter writer = new FileWriter("C:\\Users\\eunchan1\\Desktop\\filePrac\\file.txt");
        writer.write("안녕하세요");
        writer.close();

        // 읽기
        FileReader reader = new FileReader("C:\\Users\\eunchan1\\Desktop\\filePrac\\file.txt");
        int ch;
        while ((ch = reader.read()) != -1) {
            System.out.print((char) ch);
        }
        reader.close();

    }
}
