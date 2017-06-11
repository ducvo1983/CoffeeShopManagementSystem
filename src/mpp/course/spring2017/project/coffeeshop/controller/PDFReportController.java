package mpp.course.spring2017.project.coffeeshop.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import mpp.course.spring2017.project.coffeeshop.model.CustomerOrder;
import mpp.course.spring2017.project.coffeeshop.model.OrderLine;
import mpp.course.spring2017.project.coffeeshop.view.CoffeeShopUtils;

public class PDFReportController {
	@FXML private ImageView imgViewPDF;
	@FXML private void initialize() {
		imgViewPDF.setFitHeight(600);
        imgViewPDF.setFitWidth(400);
        imgViewPDF.setPreserveRatio(true);
	}
	public void showPDF(CustomerOrder co) {
	        try {
	        			String pdfFile = "reports/" + co.getCustomerName() + ".pdf";
	                    Document order_report = new Document();
	                    PdfWriter.getInstance(order_report, new FileOutputStream(pdfFile));
	                    order_report.open();    
	                    PdfPTable blankTable = new PdfPTable(1);
	                    PdfPTable page_table = new PdfPTable(1);
	                    PdfPTable order_table = new PdfPTable(2);
	                    PdfPTable order_line_table = new PdfPTable(4);
	                    PdfPTable price_tax_table = new PdfPTable(4);
	                    
	                    PdfPCell table_cell;
	                    
	                    table_cell=new PdfPCell(new Phrase(""));
	                    blankTable.addCell(table_cell);
                        
	                    table_cell=new PdfPCell(new Phrase("CoffeeShop"));
	                    page_table.addCell(table_cell);
                        
                        table_cell=new PdfPCell(new Phrase("Order No:"));
                        order_table.addCell(table_cell);
                        table_cell=new PdfPCell(new Phrase(co.getOrderNo()));
                        order_table.addCell(table_cell);
                        table_cell=new PdfPCell(new Phrase("Date:"));
                        order_table.addCell(table_cell);
                        table_cell=new PdfPCell(new Phrase(co.getOrderDate().toString()));
                        order_table.addCell(table_cell);
                        
                        table_cell=new PdfPCell(new Phrase("Product"));
                        order_line_table.addCell(table_cell);
                        table_cell=new PdfPCell(new Phrase("Size"));
                        order_line_table.addCell(table_cell);
                        table_cell=new PdfPCell(new Phrase("Quantity"));
                        order_line_table.addCell(table_cell);
                        table_cell=new PdfPCell(new Phrase("Price"));
                        order_line_table.addCell(table_cell);
                        
                        float total_before_tax = 0;
                        for (OrderLine odl : co.getListOrderLine()) {
                        	table_cell=new PdfPCell(new Phrase(odl.getProduct().toString()));
                            order_line_table.addCell(table_cell);
                            table_cell=new PdfPCell(new Phrase(odl.getBeverageSize() == null ? "" : odl.getBeverageSize()));
                            order_line_table.addCell(table_cell);
                            table_cell=new PdfPCell(new Phrase(String.valueOf(odl.getQuantity())));
                            order_line_table.addCell(table_cell);
                            total_before_tax += (odl.getPrice()); 
                            table_cell=new PdfPCell(new Phrase(String.valueOf(odl.getPrice())));
                            order_line_table.addCell(table_cell);
                        }
                        table_cell=new PdfPCell(new Phrase("Tax:"));
                        price_tax_table.addCell(table_cell);
                        table_cell=new PdfPCell(new Phrase(""));
                        price_tax_table.addCell(table_cell);
                        table_cell=new PdfPCell(new Phrase((co.getTax() * 100) + "%"));
                        price_tax_table.addCell(table_cell);
                        table_cell=new PdfPCell(new Phrase(String.valueOf(total_before_tax * co.getTax())));
                        price_tax_table.addCell(table_cell);
                        table_cell=new PdfPCell(new Phrase("Total:"));
                        price_tax_table.addCell(table_cell);
                        table_cell=new PdfPCell(new Phrase(""));
                        price_tax_table.addCell(table_cell);
                        table_cell=new PdfPCell(new Phrase(""));
                        price_tax_table.addCell(table_cell);
                        table_cell=new PdfPCell(new Phrase(String.valueOf(co.getAmount())));
                        price_tax_table.addCell(table_cell);
                        order_report.add(page_table);
                        order_report.add(order_table);
	                    order_report.add(order_line_table);
                        order_report.add(price_tax_table);
	                    order_report.close();
	                    imgViewPDF.setImage(CoffeeShopUtils.convertPDFtoImage(pdfFile));
	    } catch (FileNotFoundException e) {
	    	e.printStackTrace();
	    } catch (DocumentException e) {
	    	e.printStackTrace();
	    }
	}
}
