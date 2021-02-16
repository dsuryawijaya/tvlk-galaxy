package com.galaxy;

import com.galaxy.ancillaries.Ancillary;
import com.galaxy.ancillaries.SalesData;
import com.galaxy.currency.CurrencyAccessor;
import com.galaxy.flight.FlightGroup;
import com.galaxy.flight.FlightGroups;
import com.galaxy.flight.RevenueComponent;
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

public class AncillaryMain {

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
            "Net Margin Tag",
            "Status"
    );

    private static List<String> groupHeaders = Arrays.asList(
            "Period",
            "Product",
            "Revenue Components",
            "Entity",
            "Transaction Currency",
            "Tag : Positive / Negative Margin",
            "Tag : Status",
            "Amount"
    );

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private static CurrencyAccessor currencyAccessor = new CurrencyAccessor();

    public static void main(String[] args){
        String folder = "/Users/danielsuryawijaya/traveloka/galaxy/ancillaries/";
        //2018 are using month names
//        List<String> fileNames2018 = getFilesFor2018();
//        for(String fileName : fileNames2018){
//            System.out.println(fileName);
//            SalesData salesData = loadSalesData(folder + "2018/" + fileName);
//            writeAncillariesToFile(folder + "margin/2018/bid-" + fileName, salesData.getAncillaries());
//            writeGroupsToFile(folder + "margin/2018/group-" + fileName, salesData.getGroups());
//        }
        Map<Integer, List<String>> fileNames2019 = getFilesFor2019(folder + "2019/");
        for(Integer month : fileNames2019.keySet()){
            if(month < 8){
                continue;
            }
            SalesData salesData = new SalesData();
            for(String fileName : fileNames2019.get(month)){
                salesData.add(loadSalesData(folder + "2019/" + fileName));
            }
            String marginFileName = "ancillary_bid_" + month + "2019.xlsx";
            writeAncillariesToFile(folder + "margin/2019/" + marginFileName, salesData.getAncillaries());
            String groupFileName = "ancillary_group_" + month + "2019.xlsx";
            writeGroupsToFile(folder + "margin/2018/group-" + groupFileName, salesData.getGroups());
        }

        //TODO process 2020

    }

    private static Map<Integer, List<String>> getFilesFor2019(String folder){
        int i = 1;
        Map<Integer, List<String>> monthFiles = new HashMap<>();
        File dir = new File(folder);
        String[] crawledFiles = dir.list();

        for(String crawledFileName : crawledFiles){
            Integer month = retrieveMonth(crawledFileName);
            if(month != null){
                List<String> fileNames = monthFiles.get(month);
                if(fileNames == null){
                    fileNames = new ArrayList<>();
                }
                fileNames.add(crawledFileName);
                monthFiles.put(month, fileNames);
            }
        }

        return monthFiles;
    }

    private static Integer retrieveMonth(String fileName){
        try {
            return Integer.valueOf(fileName.substring(41,43));
        } catch (StringIndexOutOfBoundsException e){
            if(!fileName.equals(".DS_Store")){
                e.printStackTrace();
                throw new RuntimeException("invalid file " + fileName);
            }
            return null;
        }
    }

    private static void writeGroupsToFile(String fileAddress, FlightGroups flightGroups){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Groups");

        int rowNum = 0;
        System.out.println("Creating excel groups : " + fileAddress);

        Row headerRow = sheet.createRow(rowNum++);

        int i = 0;
        for(String headerCell : groupHeaders){
            Cell cell = headerRow.createCell(i ++);
            cell.setCellValue(headerCell);
        }

        for (String groupKey : flightGroups.getGroups().keySet()) {
            FlightGroup flightGroup = flightGroups.getGroups().get(groupKey);
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;

            Cell period = row.createCell(colNum++);
            period.setCellValue(flightGroup.getPeriod());

            Cell product = row.createCell(colNum++);
            product.setCellValue(flightGroup.getProduct());

            Cell revenue = row.createCell(colNum++);
            revenue.setCellValue(flightGroup.getRevenueComponent().getName());

            Cell entity = row.createCell(colNum++);
            entity.setCellValue(flightGroup.getContractEntity());

            Cell curr = row.createCell(colNum++);
            curr.setCellValue(flightGroup.getTrxCurrency());

            Cell tagMargin = row.createCell(colNum++);
            tagMargin.setCellValue(flightGroup.getMarginTag());

            Cell tagStatus = row.createCell(colNum++);
            tagStatus.setCellValue(flightGroup.getStatus());

            Cell amount = row.createCell(colNum++);
            amount.setCellValue(flightGroup.getAmount().doubleValue());
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

        System.out.println("Done Groups");
    }

    private static void writeAncillariesToFile(String fileAddress, List<Ancillary> ancillaries){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Net Margin");

        int rowNum = 0;
        System.out.println("Creating excel ancillaries : " + fileAddress);

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
            String marginTag = getMarginTag(ancillary.getNetMargin());
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

        System.out.println("Done Ancillaries");
    }

    private static String getMarginTag(BigDecimal netMargin){
        String marginTag = "POSITIVE";
        if(netMargin.compareTo(BigDecimal.ZERO) < 0){
            marginTag = "NEGATIVE";
        }
        return marginTag;
    }

    private static List<String> getFilesFor2018(){
        return Arrays.asList(
                "Flight Ancillaries September 2018.xlsx",
                "Flight Ancillaries October 2018.xlsx",
                "Flight Ancillaries November 2018.xlsx",
                "Flight Ancillaries December 2018.xlsx");
    }

    private static SalesData loadSalesData(String fileName){
        SalesData salesData = new SalesData();
        System.out.println("processing : " + fileName);
        try {
            File file = new File(fileName);
            OPCPackage opcPackage = OPCPackage.open(file);
            XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            Map<Integer, String> columnMapping = getColumnMapping(iterator.next());
            Long i = 0L;
            while (iterator.hasNext()) {
                System.out.println(i++);
                Row currentRow = iterator.next();
                try {
                    Ancillary ancillary = parseAncillaryRow(currentRow, columnMapping);
                    FlightGroups flightGroups = parseGroup(ancillary);
                    salesData.getGroups().upsert(flightGroups);
                    salesData.getAncillaries().add(ancillary);
                } catch (Exception e){
                    e.printStackTrace();
                    System.out.println("fileName = " + fileName + " | rownum = " + currentRow.getRowNum());
                    //TODO continue
                }
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
        return salesData;
    }

    private static FlightGroups parseGroup(Ancillary ancillary){
        FlightGroups groups = new FlightGroups();
        String marginTag = getMarginTag(ancillary.getNetMargin());
        String issuedPeriod = ancillary.getIssuedDate().substring(3);

        //trx fee
        FlightGroup trxFee = new FlightGroup();
        trxFee.setPeriod(issuedPeriod);
        trxFee.setContractEntity(ancillary.getContractEntity());
        trxFee.setMarginTag(marginTag);
        trxFee.setRevenueComponent(RevenueComponent.TRANSACTION_FEE);
        trxFee.setProduct("Ancillaries");
        trxFee.setStatus("Issued");
        trxFee.setTrxCurrency(ancillary.getCollectingCurrency());
        trxFee.setAmount(ancillary.getTransactionFeeCollectingCurrency());
        if(trxFee.getAmount().compareTo(BigDecimal.ZERO) != 0){
            groups.upsert(trxFee);
        }

        boolean premium = true;
        if(ancillary.getPremiumDiscountCollectingCurrency().compareTo(BigDecimal.ZERO) < 0){
            premium = false;
        }

        //premium/discount
        FlightGroup discountPremium = new FlightGroup();
        discountPremium.setPeriod(issuedPeriod);
        discountPremium.setContractEntity(ancillary.getContractEntity());
        discountPremium.setMarginTag(marginTag);
        discountPremium.setRevenueComponent(premium ? RevenueComponent.PREMIUM : RevenueComponent.DISCOUNT);
        discountPremium.setProduct("Ancillaries");
        discountPremium.setStatus("Issued");
        discountPremium.setTrxCurrency(ancillary.getCollectingCurrency());
        discountPremium.setAmount(ancillary.getPremiumDiscountCollectingCurrency());
        if(discountPremium.getAmount().compareTo(BigDecimal.ZERO) != 0){
            groups.upsert(discountPremium);
        }

        //coupon
        FlightGroup coupon = new FlightGroup();
        coupon.setPeriod(issuedPeriod);
        coupon.setContractEntity(ancillary.getContractEntity());
        coupon.setMarginTag(marginTag);
        coupon.setRevenueComponent(RevenueComponent.COUPON);
        coupon.setProduct("Ancillaries");
        coupon.setStatus("Issued");
        coupon.setTrxCurrency(ancillary.getCollectingCurrency());
        coupon.setAmount(ancillary.getCouponCollectingCurrency());
        if(coupon.getAmount().compareTo(BigDecimal.ZERO) != 0){
            groups.upsert(coupon);
        }

        //points
        FlightGroup points = new FlightGroup();
        points.setPeriod(issuedPeriod);
        points.setContractEntity(ancillary.getContractEntity());
        points.setMarginTag(marginTag);
        points.setRevenueComponent(RevenueComponent.REDEEMED_POINTS);
        points.setProduct("Ancillaries");
        points.setStatus("Issued");
        points.setTrxCurrency(ancillary.getCollectingCurrency());
        points.setAmount(ancillary.getPointsCollectingCurrency());
        if(points.getAmount().compareTo(BigDecimal.ZERO) != 0){
            groups.upsert(points);
        }

        //uniq code
        FlightGroup uniqueCode = new FlightGroup();
        uniqueCode.setPeriod(issuedPeriod);
        uniqueCode.setContractEntity(ancillary.getContractEntity());
        uniqueCode.setMarginTag(marginTag);
        uniqueCode.setRevenueComponent(RevenueComponent.UNIQUE_CODE);
        uniqueCode.setProduct("Ancillaries");
        uniqueCode.setStatus("Issued");
        uniqueCode.setTrxCurrency(ancillary.getCollectingCurrency());
        uniqueCode.setAmount(ancillary.getUniqueCodeCollectingCurrency());
        if(uniqueCode.getAmount().compareTo(BigDecimal.ZERO) != 0){
            groups.upsert(uniqueCode);
        }

        //rebook
        FlightGroup rebook = new FlightGroup();
        rebook.setPeriod(issuedPeriod);
        rebook.setContractEntity(ancillary.getContractEntity());
        rebook.setMarginTag(marginTag);
        rebook.setRevenueComponent(RevenueComponent.REBOOK_COST);
        rebook.setProduct("Ancillaries");
        rebook.setStatus("Issued");
        rebook.setTrxCurrency(ancillary.getCollectingCurrency());
        rebook.setAmount(ancillary.getRebookCostCollectingCurrency());
        if(rebook.getAmount().compareTo(BigDecimal.ZERO) != 0){
            groups.upsert(rebook);
        }

        //rebook
        FlightGroup commission = new FlightGroup();
        commission.setPeriod(issuedPeriod);
        commission.setContractEntity(ancillary.getContractEntity());
        commission.setMarginTag(marginTag);
        commission.setRevenueComponent(RevenueComponent.COMMISSION_REVENUE);
        commission.setProduct("Ancillaries");
        commission.setStatus("Issued");
        commission.setTrxCurrency(ancillary.getCollectingCurrency());
        commission.setAmount(ancillary.getCommissionContractingCurrency());
        if(commission.getAmount().compareTo(BigDecimal.ZERO) != 0){
            groups.upsert(commission);
        }

        return groups;
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
            Double conversionRateInDouble = currencyAccessor.getRates(ancillary.getCollectingCurrency(), ancillary.getContractCurrency(), bookingIssueDate);
            if(conversionRateInDouble != null){
                conversionRate = new BigDecimal(conversionRateInDouble);
            } else {
                throw new RuntimeException("conversion rate not found : " + ancillary.getCollectingCurrency() + " | " + ancillary.getContractCurrency() + " | " + bookingIssueDate);
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
