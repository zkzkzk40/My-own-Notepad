import java.io.*;

public class TXT {
  public static String readTXT(String path){
    String result="";
    try {
      FileInputStream fileInputStream=new FileInputStream(path);
      InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream,"UTF-8");
      BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
      while(bufferedReader.ready()){
        result=result+bufferedReader.readLine()+"\n";
      }
      fileInputStream.close();
      inputStreamReader.close();
      bufferedReader.close();
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }
  public static void writeTXT(String path,String text){
    try {
      FileWriter fileWriter=new FileWriter(path);
      BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
      bufferedWriter.write(text);
      bufferedWriter.flush();
      bufferedWriter.close();
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public static void main(String[] args) {
    writeTXT("D:\\desktop\\1.txt","我爱你中国");
    System.out.println(readTXT("D:\\desktop\\1.txt"));
  }
}

