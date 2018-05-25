import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jdk.nashorn.internal.parser.JSONParser;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class WebCapture {
	public static void main(String args[]) throws Exception{
		
		
		Thread1 t1= new Thread1();
		t1.start();
		
	}
}
	class Thread1 extends Thread{
		public void run() {
			Jedis watch = new Jedis("210.114.89.51",6379);
			final Jedis output = new Jedis("210.114.89.51",6379);
			
			String list = new String();
			
			final JedisPubSub jedisPubSub = new JedisPubSub() {
				
				public void onMessage(String channal, String message){
					//System.out.println("C");
					String a = new String(output.rpop("CaptureList"));
					JsonParser parser = new JsonParser();
					JsonObject object = (JsonObject)parser.parse(a);
					JsonArray list = (JsonArray)object.get("Capture");
					//System.out.println("url"+object.get("url"));
					String url = object.get("url").toString();
					String fb_id = object.get("fb_id").toString();
					String ticket = object.get("ticket_id").toString();
					System.out.println(a.toString());
					url = url.substring(1,url.length()-1);
					fb_id = fb_id.substring(1,fb_id.length()-1);
					ticket = ticket.substring(1,ticket.length()-1);
					
					String path = ("/Volumes/lake/capture/img/"+ticket+".jpg");
					
					System.out.println(url+" "+fb_id+" "+ticket);
					
					System.setProperty("webdriver.gecko.driver", "/Users/nightzen/Downloads/web/jar/geckodriver");
					WebDriver driver = new FirefoxDriver();
				
					driver.get(url);
					Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
					
					try{
						ImageIO.write(fpScreenshot.getImage(),"JPG", new File("/Volumes/lake/capture/img/"+ticket+".jpg"));
					}catch(IOException e) {
						e.printStackTrace();
					}
					
					
					try {
						MessageDigest md = MessageDigest.getInstance("SHA-256");
						FileInputStream fis = new FileInputStream(path);
						byte[] dataBytes = new byte[2014];
						int nread = 0;
						while((nread = fis.read(dataBytes))!=-1) {
							md.update(dataBytes,0,nread);
						};
						byte[] mdbytes = md.digest();
						
						StringBuffer sb = new StringBuffer();
						for(int i = 0; i<mdbytes.length;i++) {
							sb.append(Integer.toString((mdbytes[i] &0xff)+0x100,16).substring(1));
						}
						String hst = sb.toString();
						System.out.println(hst);
						char dDot = 34 ;
						
						//String FPList[];
//						String fplist = "[FPList{" +'"'+  "fb_id"+ '"' +":"  + fb_id + ","+'"'+"ticket_id"+'"'+":" +'"'+ ticket +'"'+ ","+'"'+"url"+'"'+":" +'"'+ url +'"'+ ","+'"'+"path"+'"'+":" +'"'+ path +'"'+ ","+'"'+"Hash"+'"'+":" +'"'+ hst +'"'+ "}]";
						String fplist = "[FPList{"+  "fb_id"+"\":\" "+ fb_id + ","+'"'+"ticket_id"+'"'+":" +'"'+ ticket +'"'+ ","+'"'+"url"+'"'+":" +'"'+ url +'"'+ ","+'"'+"path"+'"'+":" +'"'+ path +'"'+ ","+'"'+"Hash"+'"'+":" +'"'+ hst +'"'+ "}]";
						
						String FPList[] = {fplist};
						String FP2 = FPList.toString();
						FP2 = FP2.replace("\\", "");
						
						String FPList2[] = {FP2};
						/*JSONObject obj = new JSONObject();
						LinkedHashMap listMap = new LinkedHashMap();
						LinkedList subList = new LinkedList();
						LinkedList sub = new LinkedList();
						listMap = new LinkedHashMap();
						subList = new LinkedList();
						//obj.put("FPList",sub);
						listMap.put("fb_id:",fb_id);
						listMap.put("ticket_id",ticket);
						listMap.put("url", url);
						listMap.put("path", path);
						listMap.put("hash", hst);
						sub.add(listMap);
						obj.put("FPList",sub);
						Gson gson = new Gson();
						String json = gson.toJson(obj);*/
						
						
						//System.out.println(FPList);
						output.lpush("FPList",FPList2 );
						output.publish("FPList", "input data");
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					try{
						sleep(1000);
						int thumbnail_width = 100;
						int thumbnail_height = 100;
						
						File origin_file_name = new File("/Volumes/lake/capture/img/"+ticket+".jpg");
						File thumb_file_name = new File("/Volumes/lake/capture/thumb/"+ticket+".jpg");
						
						BufferedImage buffer_original_image = ImageIO.read(origin_file_name);
						BufferedImage buffer_thumbnail_image = new BufferedImage(thumbnail_width,thumbnail_height,BufferedImage.TYPE_3BYTE_BGR);
						Graphics2D graphic = buffer_thumbnail_image.createGraphics();
						graphic.drawImage(buffer_original_image, 0, 0, thumbnail_width, thumbnail_height, null);
						ImageIO.write(buffer_thumbnail_image, "JPG", thumb_file_name);
						
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					driver.close();
					
					
				}
					
					
						
					
					
					
				
			};

			//JedisPubSub jedisPubSub = setupSubscriber();
			//JedisListManage jedisPubSub = new JedisListManage();
			String channal[] = {"CaptureList"};
			watch.subscribe(jedisPubSub, channal);
			
			
			/*System.setProperty("webdriver.gecko.driver", "/Users/nightzen/Downloads/web/jar/geckodriver");
			WebDriver driver = new FirefoxDriver();
		
			driver.get(url);
			Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
			try{
				ImageIO.write(fpScreenshot.getImage(),"JPG", new File("/Volumes/lake/capture/img/"+System.currentTimeMillis()+"FullPageScreenshot.jpg"));
			}catch(IOException e) {
				e.printStackTrace();
			}*/
			
		}
	}
	
	/*class JedisListManage {	
		private void setupSubscriber() {
			final Jedis output = new Jedis("210.114.89.51",6379);
			
			final JedisPubSub jedisPubSub = new JedisPubSub() {
				public void onMessage(String channal, String message) {
					System.out.println("C");
					output.rpop("CaptureList");
				}
			};
			//return jedisPubSub;
		}
	}*/


