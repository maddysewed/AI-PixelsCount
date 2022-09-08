import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Row;

public class ExcelWorker {

    // заполнение строки (rowNum) определенного листа (sheet)
    // данными  из dataModel созданного в памяти Excel файла
    public static void createSheetHeader(HSSFSheet sheet, int rowNum, DataModel dataModel) {
        Row row = sheet.createRow(rowNum);

        row.createCell(0).setCellValue(dataModel.getStrand());
        row.createCell(1).setCellValue(dataModel.getT0());
        row.createCell(2).setCellValue(dataModel.getA0());
        row.createCell(3).setCellValue(dataModel.getH0());
        row.createCell(4).setCellValue(dataModel.getR0());
        row.createCell(5).setCellValue(dataModel.getRy());
        row.createCell(6).setCellValue(dataModel.getDy());
        row.createCell(7).setCellValue(dataModel.getPo());
        row.createCell(8).setCellValue(dataModel.getPy());
        row.createCell(9).setCellValue(dataModel.getLyf());
        row.createCell(10).setCellValue(dataModel.getKny());
        row.createCell(11).setCellValue(dataModel.getG());
    }
}
