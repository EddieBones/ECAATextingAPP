package textapp;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Pattern;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public  class ExcelClass extends JFrame{

	private String _excelLocation;
	private ArrayList<String> NumberList;
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private Date dt;
	private FileHandler fh; 
	public  ExcelClass()  {
		
		
		
		
	}
	
	public ArrayList<String> GetExcelData(String _excelLocation) 
	{
		ArrayList<String> outputNumbers = new ArrayList<String>();

		try {
			String loc =_excelLocation.replace("\\","\\\\" );
			System.out.println("Excel Location is: " + loc);
			FileInputStream fileinput = new FileInputStream(loc);
			//System.out.println("fileinput created");
			StringBuilder sb = new StringBuilder();
			dt = new Date();
			//System.out.println("date object created");
			if(loc.contains("\\\\"))
			{
			String[] arr=	loc.split("\\\\");
			//System.out.println(arr.toString());
			for(int i=0;i<arr.length-1;i++) 
			{
				sb.append(arr[i]+"\\");
				
			}
			//System.out.println(sb);
			sb.append("LogFile.txt");
			String str1 = sb + "";
			//System.out.println(str1);
			fh = new FileHandler(str1);
			//System.out.println("filehandler created");
	        logger.addHandler(fh);
	        //System.out.println("logger add handler");
	        SimpleFormatter formatter = new SimpleFormatter(); 
	        //System.out.println("created formatter");
	        fh.setFormatter(formatter);
	        //System.out.println("set formatter");
			}
			else if(loc.contains("/"))
			{
			String[] arr=	loc.split("/");
			for(int i=0;i<arr.length-1;i++) 
			{
				sb.append(arr[i]+"/");
				
			}	
				
			sb.append("LogFile_" + dt.toString() +".log");	
			String str2 = sb +"";
			fh = new FileHandler(str2);  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  
	    
			}
			
			
	
		    if(loc.endsWith(".xlsx"))
			{
		    	
					System.out.println("Excel file is xlsx");
					XSSFWorkbook wb = new XSSFWorkbook(fileinput);
				    XSSFSheet sheet = wb.getSheetAt(0);
				    int rows = sheet.getPhysicalNumberOfRows();
				    
				    for (int i = 0; i<rows;i++) {
				    	
				    	XSSFRow row = sheet.getRow(i);
				    	XSSFCell cell =row.getCell(7);
				    	String cellData =cell.getStringCellValue();
				    	//System.out.println(cellData);
				    	if(cellData.startsWith("(")) {
				    	outputNumbers.add(cellData);
				    	}
				    }
				    wb.close();
		
				
				
			}
		    else if(loc.endsWith(".xls"))
			{	
			System.out.println("Excel file is xls");	
			HSSFWorkbook wb = new HSSFWorkbook(fileinput);
		    HSSFSheet sheet = wb.getSheetAt(0);
		    int rows = sheet.getPhysicalNumberOfRows();
		    
		    for (int i = 0; i<rows;i++) {
		    	
		    	HSSFRow row = sheet.getRow(i);
		    	HSSFCell cell =row.getCell(7);
		    	String cellData =cell.getStringCellValue();
		    	//System.out.println(cellData);
		    	if(cellData.startsWith("(")) {
		    	outputNumbers.add(cellData);
		    	}
		    }
		    wb.close();
			}
		  
	    
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//JOptionPane JOPane = new JOptionPane();
			//int result = JOPane.showConfirmDialog(this, "Error: Please check Excel file path", "Error",JOptionPane.ERROR_MESSAGE);
			JOptionPane optionPane = new JOptionPane("Error: Please verify Excel file Path", JOptionPane.ERROR_MESSAGE);    
			JDialog dialog = optionPane.createDialog("Error Message");
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);
			System.out.println("Excel sheet location is invalid");
			e.printStackTrace();
		}
		 ArrayList<String> list = NumberFormat(outputNumbers);
		 logger.info("Total Numbers in queue " + list.size());
		 System.out.println("Total Numbers in queue " + list.size());
		    return list;
		
	}
	
	private ArrayList<String> NumberFormat(ArrayList<String> Numbers){
		
		ArrayList<String> list = new ArrayList<String>();
		
		for(int i = 0; i< Numbers.size();i++) {
			String num =Numbers.get(i);
			String formattedNum =num.replaceAll("[(]*", "").replaceAll("[)]*", "").replaceAll("[-]*", "").replaceAll("[\\s]*","");
            String finalString = "+1" + formattedNum;
			list.add(finalString);
		}

		return list;
	}
	
	
	
	
	
	
}
