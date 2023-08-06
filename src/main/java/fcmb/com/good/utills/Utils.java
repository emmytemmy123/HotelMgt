package fcmb.com.good.utills;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Utils {

    public static StreamingResponseBody getStreamingResponseBody(ByteArrayInputStream bis) {
        return outputStream -> {
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = bis.read(data, 0, data.length)) != -1) {
                outputStream.write(data, 0, nRead);
            }
            outputStream.flush();
            outputStream.close();
        };
    }

    public static Workbook getWorkbook(String excelFilePath) throws IOException {
        Workbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not an Excel file");
        }

        return workbook;
    }

    public static String postToSever(Object load, RestTemplate restTemplate, String slackWebhook) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity<>(load, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(slackWebhook, HttpMethod.POST, httpEntity,
                String.class);
        String result = responseEntity.getBody();
        return result;

    }

    public static String formatString(Integer result){
       return String.format("%02d", result);
    }

    public static Gson getGson() {
        return new Gson();
    }

    public static ObjectMapper getMapper(){
        return new ObjectMapper();
    }

    public static String getApiKey(){
        return UUID.randomUUID().toString();
    }

    public static String processString(String str){
        return str.replaceAll("[^a-zA-Z0-9]","");
    }

    public static Integer getInt(String data){
        if(data.contains("."))
            data=data.substring(0,data.lastIndexOf("."));
        return Integer.parseInt(data);
    }

    public static boolean isValid(String data) {
        return data != null && !data.isEmpty();
    }

    public static boolean isValidPhone(String data) {
        return data != null && !data.isEmpty() && data.length() != 11;
    }

    public static LocalDateTime parseDate(String date) {
        if (date != null)
            return LocalDateTime.parse(date);
        else
            return null;
    }

    public static String getStringResult(String data, String placeHolder) {
        return String.format(data, placeHolder);
    }

    public static FileSystemResource baseDir(String path) {
        FileSystemResource fl = new FileSystemResource(path);
        return fl;
    }

    public static String getDate(Date date){
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        ft.setTimeZone(TimeZone.getTimeZone("GMT+01"));
        return ft.format(date);
    }

    public static String getNewFileName(String path, String name) {
        if (new File(path, name).exists()) {

            String newFile;
            do {
                newFile = "flix" + RandomStringUtils.randomAlphanumeric(8).toLowerCase();
            } while (new File(path, newFile).exists());
            return newFile + name.substring(name.lastIndexOf("."));
        } else {
            return name;
        }
    }

    public static Integer getDefaultId(){
        return 5555555;
    }

    public static boolean isDoubleNumber(String str){
        Pattern DOUBLE_PATTERN = Pattern.compile(
                "[\\x00-\\x20]*[+-]?(NaN|Infinity|((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)" +
                        "([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|" +
                        "(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))" +
                        "[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*");
       return DOUBLE_PATTERN.matcher(processString(str)).matches();
    }

    public static Double getDouble(String str){
        return Double.parseDouble(processString(str));
    }

    public static Double getDouble(Float rs){
        return rs==null?0.0:rs.doubleValue();
    }

    public static LocalDateTime extractDate(String str){
        if(str==null || str.isBlank())
            return null;
        try{
            return  LocalDateTime.parse(str);

        }catch(Exception e){

            return null;
        }
    }

    public static boolean extractValue(String str){
        return str != null && !str.isBlank() && !str.equals("No");
    }

    public static String getResult(String result){
        return result==null?"NA":result;
    }

    public static void createContent(Row row, CellStyle style, Object text, int i) {
        Cell cell = row.createCell(i);
        cell.setCellStyle(style);
        cell.setCellValue(text.toString());
    }

    public static Object getData(Row row, String key, Map<String, Integer> map) {
        int cellIndex = 0;
        if (map.containsKey(key)) {
            cellIndex = map.get(key);
            if (row.getCell(cellIndex) == null || row.getCell(cellIndex).getCellType().equals(CellType.BLANK)
                    || row.getCell(cellIndex).getCellType().equals(CellType._NONE)
                    || row.getCell(cellIndex).getCellType().equals(CellType.ERROR)) {
                return "";
            }  else if (row.getCell(cellIndex).getCellType().equals(CellType.BOOLEAN)) {
                return row.getCell(cellIndex).getBooleanCellValue();
            }
            else if (row.getCell(cellIndex).getCellType().equals(CellType.NUMERIC)) {
               // BigDecimal b = new BigDecimal();
                return row.getCell(cellIndex).getNumericCellValue();
            }
            else if (row.getCell(cellIndex).getCellType().equals(CellType.STRING)) {
                return row.getCell(cellIndex).getStringCellValue();
            }else if(DateUtil.isCellDateFormatted(row.getCell(cellIndex))){
                return row.getCell(cellIndex).getDateCellValue();
            }else{
                String link =row.getCell(cellIndex).getHyperlink().getAddress();
                System.out.println(link);
                return link;
            }
        } else {
            System.out.println("Key........................." + key + " ..............Not Found");
            return "";
        }

    }


    public static Date getDateData(Row row, String key, Map<String, Integer> map) {
        int cell = 0;
        return row.getCell(cell).getDateCellValue();
    }


    public static Workbook getWorkbook(FileInputStream inputStream, String excelFilePath) throws IOException {
        Workbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException(
                    "The specified file is not Excel file");
        }

        return workbook;
    }

    public static Map<String, Integer> getColumnName(Sheet sheet) {
        Map<String, Integer> maps = new HashMap<>();
        Row row = sheet.getRow(0); //Get first row
        short minColIx = row.getFirstCellNum(); //get the first column index for a row
        short maxColIx = row.getLastCellNum(); //get the last column index for a row
        for (short colIx = minColIx; colIx < maxColIx; colIx++) { //loop from first to last index
            Cell cell = row.getCell(colIx); //get the cell
            maps.put(cell.getStringCellValue().replaceAll("\\s+", "").toUpperCase(), cell.getColumnIndex()); //add the cell contents (name of column) and cell index to the map

        }

        return maps;
    }


    public static Double getValue(BigDecimal result){
        String data =String.valueOf(result.doubleValue());
        Double outcome= result==null?0.0:data.contains("E")?Double.valueOf(result.toBigInteger().toString()):format(result.doubleValue());
        System.out.println("Data###################### "+outcome +" ########### Main Data: "+ data);
        return outcome;
    }

    public static Double format(Double data){
        return   Double.valueOf(getFormat(data));
    }

    public static String getFormat(Double data){
        return new DecimalFormat("0.00").format(data);
    }

    public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }






}