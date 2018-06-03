//package com.example.mongoapp.testDao;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import com.csvreader.CsvWriter;
//import com.mongodb.DBCursor;
//import com.mongodb.DBObject;
//
//public class WriteCsvUtil {
//    public static void Csv(DBCursor cursor,String fileName) throws IOException{
//		 CsvWriter csvWriter;
//		csvWriter = new CsvWriter(("F:/"+fileName));
//		//获取标题
//
//		 DBObject ob = cursor.toArray().get(0);
//
//		 ArrayList<String> title = new ArrayList<String>();
//		 for(String key:ob.keySet()){
//	            title.add(key);
//	        }
//
//		 String[] buffer = new String[100];
//		 String[] buffer1 = new String[10000];
//
//		for(int i = 0;i< (cursor.count() + 1);i++){
//			int k=0;
//			//创建标题行
//			if(i == 0){
//				for(int j=0;j<title.size();j++){
//					buffer[k] = title.get(j);
//					k++;
//				}
//				continue;
//			}
//
//			if(i == 1){
//				csvWriter.writeRecord(buffer);
//			}
//
//
//			//写数据
//			DBObject obj = null;
//			for(int j = 0;j < title.size();j++){
//				int k1=0;
//				obj = cursor.toArray().get(i-1);
//				for(String key : obj.keySet()){
//	                if (key.equals(title.get(j))) {
//	                    buffer1[k1] = obj.get(key).toString();
//	                }
//	                k1++;
//				}
//
//			}
//		csvWriter.writeRecord(buffer1);
//		}
//		try {
//
//			System.out.println("aaa");
//
//		} catch (RuntimeException r) {
//			// TODO Auto-generated catch block
//			new RuntimeException("导出CSV 文件出错");
//		}finally {
//			System.out.println("导出文件成功");
//			//csvWriter.flush();
//			csvWriter.close();
//		}
//	}
//}
//
