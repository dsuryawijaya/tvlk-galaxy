package com.galaxy;

import com.galaxy.ancillaries.Ancillary;
import com.galaxy.currency.CurrencyMaster;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    private static List<String> parsedColumn = Arrays.asList("Booking Issue Date",
            "Booking ID",
            "Locale",
            "Contract Entity",
            "Collecting Entity",
            "Collecting Currency",
            "Transaction Fee",
            "Discount/Premium",
            "Coupon Value",
            "Point Redemption",
            "Unique Code",
            "Customer Invoice",
            "Rebook Cost",
            "Contract Currency",
            "Commission",
            "Total Fare - NTA");

    private static List<String> headers = Arrays.asList(
            "Issued Date",
            "BID",
            "Locale",
            "Contract Entity",
            "Collecting Entity",
            "Transaction Currency (Collecting Currency)",
            "Transaction Fee (Collecting Currency)",
            "Premium (Collecting Currency)",
            "Discount (Collecting Currency)",
            "Coupon (Collecting Currency)",
            "Redeemed Points (Collecting Currency)",
            "Unique Code (Collecting Currency)",
            "Invoice Amount (Collecting Currency)",
            "Rebook Cost (Collecting Currency)",
            "Reschedule Fee (Collecting Currency)",
            "Refund Fee (Collecting Currency)",
            "Transaction Currency (Contract Currency)",
            "Commission Revenue (Contract Currency)",
            "Transaction Fee (Contract Currency)",
            "Premium (Contract Currency)",
            "Discount (Contract Currency)",
            "Coupon (Contract Currency)",
            "Redeemed Points (Contract Currency)",
            "Unique Code (Contract Currency)",
            "Rebook Cost (Contract Currency)",
            "Reschedule Fee (Contract Currency)",
            "Refund Fee (Contract Currency)",
            "Invoice Amount (Contract Currency)",
            "Net Margin (Contract Currency)",
            "Status"
    );
    private static CurrencyMaster currencyMaster = new CurrencyMaster();
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args){
        String folder = "/Users/danielsuryawijaya/traveloka/galaxy/ancillaries/";
        //2018 are using month names
        List<String> fileNames = getFilesFor2018();
        for(String fileName : fileNames){
            System.out.println(fileName);
            List<Ancillary> ancillaries = loadSalesData(folder + "2018/" + fileName);
            writeToFile(folder + "margin/2018/" + fileName, ancillaries);
//            group(ancillaries);
        }
        //TODO process 2019 2020
    }

    private static void writeToFile(String fileAddress, List<Ancillary> ancillaries){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Net Margin");

        int rowNum = 0;
        System.out.println("Creating excel");

        Row headerRow = sheet.createRow(rowNum++);

        int i = 0;
        for(String headerCell : headers){
            Cell cell = headerRow.createCell(i ++);
            cell.setCellValue(headerCell);
        }

        for (Ancillary ancillary : ancillaries) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            //issuedDate
            Cell issuedDate = row.createCell(colNum++);
            issuedDate.setCellValue(ancillary.getIssuedDate());
            //BID
            Cell bid = row.createCell(colNum++);
            bid.setCellValue(ancillary.getBid().doubleValue());
            //Locale
            Cell locale = row.createCell(colNum++);
            locale.setCellValue(ancillary.getLocale());
            //Contract Entity
            Cell contractEntity = row.createCell(colNum++);
            contractEntity.setCellValue(ancillary.getContractEntity());
            //Collecting Entity
            Cell collectingEntity = row.createCell(colNum++);
            collectingEntity.setCellValue(ancillary.getCollectingEntity());
            //Transaction Currency (Collecting Currency)
            Cell trxCurr = row.createCell(colNum++);
            trxCurr.setCellValue(ancillary.getCollectingCurrency());
            //Transaction Fee (Collecting Currency)
            Cell trxFeeColl = row.createCell(colNum++);
            trxFeeColl.setCellValue(ancillary.getTransactionFeeCollectingCurrency().doubleValue());
            //Premium (Collecting Currency)
            Cell premiumColl = row.createCell(colNum++);
            double premium = 0;
            if(ancillary.getPremiumDiscountCollectingCurrency().compareTo(BigDecimal.ZERO) > 0){
                premium = ancillary.getPremiumDiscountCollectingCurrency().doubleValue();
            }
            premiumColl.setCellValue(premium);
            //discount
            Cell discountColl = row.createCell(colNum++);
            double discount = 0;
            if(ancillary.getPremiumDiscountCollectingCurrency().compareTo(BigDecimal.ZERO) < 0){
                discount = ancillary.getPremiumDiscountCollectingCurrency().doubleValue();
            }
            discountColl.setCellValue(discount);
            //coupon
            Cell couponColl = row.createCell(colNum++);
            couponColl.setCellValue(ancillary.getCouponCollectingCurrency().doubleValue());
            //Redeemed Points (Collecting Currency)
            Cell pointsColl = row.createCell(colNum++);
            pointsColl.setCellValue(ancillary.getPointsCollectingCurrency().doubleValue());
            //Unique Code (Collecting Currency)
            Cell uniqCodeColl = row.createCell(colNum++);
            uniqCodeColl.setCellValue(ancillary.getUniqueCodeCollectingCurrency().doubleValue());
            //Invoice Amount (Collecting Currency)
            Cell invoiceColl = row.createCell(colNum++);
            invoiceColl.setCellValue(ancillary.getInvoiceAmountCollectingCurrency().doubleValue());
            //Rebook Cost (Collecting Currency)
            Cell rebookColl = row.createCell(colNum++);
            rebookColl.setCellValue(ancillary.getRebookCostCollectingCurrency().doubleValue());
            //Reschedule Fee (Collecting Currency)
            Cell reschedColl = row.createCell(colNum++);
            reschedColl.setCellValue("N/A");
            //Refund Fee (Collecting Currency)
            Cell refundColl = row.createCell(colNum++);
            refundColl.setCellValue("N/A");
            //Transaction Currency (Contract Currency)
            Cell contractCurrency = row.createCell(colNum++);
            contractCurrency.setCellValue(ancillary.getContractCurrency());
            //commissionRevenue
            Cell commissionRevenue = row.createCell(colNum++);
            BigDecimal totalCommission = ancillary.getCommissionContractingCurrency().add(ancillary.getTotalFareNTAContractingCurrency());
            commissionRevenue.setCellValue(totalCommission.doubleValue());
            //Transaction Fee (Contract Currency)
            Cell trxFeeCont = row.createCell(colNum++);
            trxFeeCont.setCellValue(ancillary.getTransactionFeeContractingCurrency().doubleValue());
            //Premium (Contract Currency)
            Cell premiumCont = row.createCell(colNum++);
            double premiumContValue = 0;
            if(ancillary.getPremiumDiscountContractingCurrency().compareTo(BigDecimal.ZERO) > 0){
                premiumContValue = ancillary.getPremiumDiscountContractingCurrency().doubleValue();
            }
            premiumCont.setCellValue(premiumContValue);
            //Discount (Contract Currency)
            Cell discountCont = row.createCell(colNum++);
            double discountContValue = 0;
            if(ancillary.getPremiumDiscountContractingCurrency().compareTo(BigDecimal.ZERO) < 0){
                discountContValue = ancillary.getPremiumDiscountContractingCurrency().doubleValue();
            }
            discountCont.setCellValue(discountContValue);
            //Coupon (Contract Currency)
            Cell couponCont = row.createCell(colNum++);
            couponCont.setCellValue(ancillary.getCouponContractingCurrency().doubleValue());
            //Redeemed Points (Contract Currency)
            Cell pointsCont = row.createCell(colNum++);
            pointsCont.setCellValue(ancillary.getPointsContractingCurrency().doubleValue());
            //Unique Code (Contract Currency)
            Cell uniqCodeCont = row.createCell(colNum++);
            uniqCodeCont.setCellValue(ancillary.getUniqueCodeContractingCurrency().doubleValue());
            //Rebook Cost (Contract Currency)
            Cell rebookCont = row.createCell(colNum++);
            rebookCont.setCellValue(ancillary.getRebookCostContractingCurrency().doubleValue());
            //Reschedule Fee (Contract Currency)
            Cell reschedCont = row.createCell(colNum++);
            reschedCont.setCellValue("N/A");
            //Refund Fee (Contract Currency)
            Cell refundCont = row.createCell(colNum++);
            refundCont.setCellValue("N/A");
            //Invoice Amount (Contract Currency)
            Cell invoiceAmountCont = row.createCell(colNum++);
            invoiceAmountCont.setCellValue(ancillary.getInvoiceAmountContractingCurrency().doubleValue());
            //Net Margin
            Cell marginCont = row.createCell(colNum++);
            marginCont.setCellValue(ancillary.getNetMargin().doubleValue());
            //Net Margin Tag
            String marginTag = "POSITIVE";
            if(ancillary.getNetMargin().compareTo(BigDecimal.ZERO) < 0){
                marginTag = "NEGATIVE";
            } else if(ancillary.getNetMargin().compareTo(BigDecimal.ZERO) == 0){
                marginTag = "BALANCED";
            }
            Cell marginCell = row.createCell(colNum++);
            marginCell.setCellValue(marginTag);
            //Status
            Cell status = row.createCell(colNum++);
            status.setCellValue("ISSUED");
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(fileAddress);
            workbook.write(outputStream);
            outputStream.close();
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");
    }

    private static List<String> getFilesFor2018(){
        return Arrays.asList("Flight Ancillaries Report 1-30 September 2018.xlsx",
                "Flight Ancillaries 01-31 October 2018.xlsx",
                "Flight Ancil 01-30 November 2018.xlsx",
                "Flight Ancillaries Report Dec 18.xlsx");
    }

//    private static List<String> getFilesForMonth(String folder, Integer monthNumber){
//        File dir = new File(folder);
//        String[] fileNames = dir.list();
//        List<String> fileToProcess = new ArrayList<>();
//        for(String fileName : fileNames){
//            if(fileName.contains("ERP Flight Ancillary Report between")){
//                //retrieve for specific month
//                fileName
//                fileToProcess.add(fileName);
//            }
//        }
//
//        return fileToProcess;
//    }
//
    private static List<Ancillary> loadSalesData(String fileName){
        List<Ancillary> ancillaries = new ArrayList<>();
        try {
            File file = new File(fileName);
            OPCPackage opcPackage = OPCPackage.open(file);
            XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            Map<Integer, String> columnMapping = getColumnMapping(iterator.next());
            Long i = 0L;
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                ancillaries.add(parseAncillaryRow(currentRow, columnMapping));
                System.out.println(i++);
            }
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } catch (InvalidFormatException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        return ancillaries;
    }

    private static Ancillary parseAncillaryRow(Row currentRow, Map<Integer, String> columnMapping) {
        Ancillary ancillary = new Ancillary();
        for(Integer columnIndex : columnMapping.keySet()){
            String columnName = columnMapping.get(columnIndex);
            if("Booking Issue Date".equals(columnName)){
                Cell cell = currentRow.getCell(columnIndex);
                if(cell.getCellType().equals(CellType.NUMERIC)){
                    Date date = cell.getDateCellValue();
                    ancillary.setIssuedDate(sdf.format(date));
                } else {
                    ancillary.setIssuedDate(currentRow.getCell(columnIndex).getStringCellValue());
                }
            } else if("Booking ID".equals(columnName)){
                double cellValue =  currentRow.getCell(columnIndex).getNumericCellValue();
                ancillary.setBid(BigDecimal.valueOf(cellValue));
            } else if("Locale".equals(columnName)){
                ancillary.setLocale(currentRow.getCell(columnIndex).getStringCellValue());
            } else if("Contract Entity".equals(columnName)){
                ancillary.setContractEntity(currentRow.getCell(columnIndex).getStringCellValue());
            } else if("Collecting Entity".equals(columnName)){
                ancillary.setCollectingEntity(currentRow.getCell(columnIndex).getStringCellValue());
            } else if("Collecting Currency".equals(columnName)){
                ancillary.setCollectingCurrency(currentRow.getCell(columnIndex).getStringCellValue());
            } else if("Transaction Fee".equals(columnName)){
                double cellValue =  currentRow.getCell(columnIndex).getNumericCellValue();
                ancillary.setTransactionFeeCollectingCurrency(BigDecimal.valueOf(cellValue));
            } else if("Discount/Premium".equals(columnName)){
                double cellValue =  currentRow.getCell(columnIndex).getNumericCellValue();
                ancillary.setPremiumDiscountCollectingCurrency(BigDecimal.valueOf(cellValue));
            } else if("Coupon Value".equals(columnName)){
                double cellValue =  currentRow.getCell(columnIndex).getNumericCellValue();
                ancillary.setCouponCollectingCurrency(BigDecimal.valueOf(cellValue));
            } else if("Point Redemption".equals(columnName)){
                double cellValue =  currentRow.getCell(columnIndex).getNumericCellValue();
                ancillary.setPointsCollectingCurrency(BigDecimal.valueOf(cellValue));
            } else if("Unique Code".equals(columnName)){
                double cellValue =  currentRow.getCell(columnIndex).getNumericCellValue();
                ancillary.setUniqueCodeCollectingCurrency(BigDecimal.valueOf(cellValue));
            } else if("Customer Invoice".equals(columnName)){
                double cellValue =  currentRow.getCell(columnIndex).getNumericCellValue();
                ancillary.setInvoiceAmountCollectingCurrency(BigDecimal.valueOf(cellValue));
            } else if("Rebook Cost".equals(columnName)){
                double cellValue =  currentRow.getCell(columnIndex).getNumericCellValue();
                ancillary.setRebookCostCollectingCurrency(BigDecimal.valueOf(cellValue));
            } else if("Contract Currency".equals(columnName)){
                ancillary.setContractCurrency(currentRow.getCell(columnIndex).getStringCellValue());
            } else if("Commission".equals(columnName)){
                double cellValue =  currentRow.getCell(columnIndex).getNumericCellValue();
                ancillary.setCommissionContractingCurrency(BigDecimal.valueOf(cellValue));
            } else if("Total Fare - NTA".equals(columnName)){
                double cellValue =  currentRow.getCell(columnIndex).getNumericCellValue();
                ancillary.setTotalFareNTAContractingCurrency(BigDecimal.valueOf(cellValue));
            }
        }

        Date bookingIssueDate;
        try {
            bookingIssueDate = sdf.parse(ancillary.getIssuedDate());
        } catch (ParseException e){
            throw new RuntimeException(e);
        }
        BigDecimal conversionRate = null;
        if(ancillary.getCollectingCurrency().equals(ancillary.getContractCurrency())){
            conversionRate = BigDecimal.ONE;
        } else {
            Double conversionRateInDouble =  currencyMaster.find(
                    ancillary.getCollectingCurrency(),
                    ancillary.getContractCurrency(),
                    bookingIssueDate
            );
            if(conversionRateInDouble != null){
                conversionRate = new BigDecimal(conversionRateInDouble);
            } else {
                conversionRate = BigDecimal.ZERO;
            }
        }
        if(conversionRate == null){
            throw new RuntimeException("unable to retrieve the currency : "
                    + ancillary.getCollectingCurrency() + " | "
                    + ancillary.getContractCurrency() + " | "
                    + bookingIssueDate);
        }
        ancillary.setPremiumDiscountContractingCurrency(ancillary.getPremiumDiscountCollectingCurrency().multiply(conversionRate));
        ancillary.setCouponContractingCurrency(ancillary.getCouponCollectingCurrency().multiply(conversionRate));
        ancillary.setPointsContractingCurrency(ancillary.getPointsCollectingCurrency().multiply(conversionRate));
        ancillary.setUniqueCodeContractingCurrency(ancillary.getUniqueCodeCollectingCurrency().multiply(conversionRate));
        ancillary.setRebookCostContractingCurrency(ancillary.getRebookCostCollectingCurrency().multiply(conversionRate));
        ancillary.setInvoiceAmountContractingCurrency(ancillary.getInvoiceAmountCollectingCurrency().multiply(conversionRate));

        BigDecimal commissionRevenue = ancillary.getCommissionContractingCurrency().add(ancillary.getTotalFareNTAContractingCurrency());
        ancillary.setCommissionRevenue(commissionRevenue);
        BigDecimal margin = ancillary.getCommissionRevenue()
                .add(ancillary.getTransactionFeeContractingCurrency())
                .add(ancillary.getPremiumDiscountContractingCurrency())
                .add(ancillary.getCouponContractingCurrency())
                .add(ancillary.getPointsContractingCurrency())
                .add(ancillary.getUniqueCodeContractingCurrency())
                .add(ancillary.getRebookCostContractingCurrency());
        ancillary.setNetMargin(margin);
        return ancillary;
    }

    private static Map<Integer, String> getColumnMapping(Row headerRow){
        Map<Integer, String> columnMapping = new HashMap<>();
        Iterator<Cell> cellIterator = headerRow.cellIterator();
        while (cellIterator.hasNext()) {
            Cell currentCell = cellIterator.next();
            String cellValue = currentCell.getStringCellValue();
            if(parsedColumn.contains(cellValue)){
                columnMapping.put(currentCell.getColumnIndex(), cellValue);
                continue;
            }
        }
        return columnMapping;
    }

}
