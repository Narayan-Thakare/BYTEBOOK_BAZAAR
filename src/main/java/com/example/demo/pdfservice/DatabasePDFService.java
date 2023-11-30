package com.example.demo.pdfservice;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.example.demo.model.Sellbooks;

public class DatabasePDFService {

    public static ByteArrayInputStream employeePDFReport(List<Sellbooks> sellbooks) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (Document document = new Document()) {
            PdfWriter.getInstance(document, out);
            document.open();

            addHeader(document);
            addTable(document, sellbooks);

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private static void addHeader(Document document) throws DocumentException {
        Font fontHeader = FontFactory.getFont(FontFactory.TIMES_BOLD, 22);
        document.add(createCenteredParagraph("Books Bill", fontHeader));
        document.add(createEmptyLine());
    }

    private static void addTable(Document document, List<Sellbooks> sellbooks) throws DocumentException {
        PdfPTable table = createPdfPTable(9);

        Stream.of("Title", "Author", "Language", "Number of Pages", "Genre", "Quantity", "Rate", "Date", "Total")
                .forEach(headerTitle -> addHeaderCell(table, headerTitle));
        
        double grandTotal = 0.0;

        for (Sellbooks sellbook : sellbooks) {
            addCell(table, sellbook.getTitle());
            addCell(table, sellbook.getAuthor());
            addCell(table, sellbook.getLanguage());
            addCell(table, sellbook.getNumber_of_pages());
            addCell(table, sellbook.getGenre());
            addCell(table, String.valueOf(sellbook.getNewQuantity()));
            addCell(table, String.valueOf(sellbook.getRate()));
            addCell(table, sellbook.getDate().toString());

            String total = calculateTotal(sellbook);
            addCell(table, total);

            grandTotal += Double.parseDouble(total);
        }

        PdfPCell grandTotalCell = new PdfPCell(new Phrase("Grand Total"));
        grandTotalCell.setColspan(8);  // Span the first 8 columns
        setCommonCellProperties(grandTotalCell);
        table.addCell(grandTotalCell);

        PdfPCell grandTotalValueCell = new PdfPCell(new Phrase(String.valueOf(grandTotal)));
        setCommonCellProperties(grandTotalValueCell);
        table.addCell(grandTotalValueCell);
        
        document.add(table);
    }

    
    private static String calculateTotal(Sellbooks sellbook) {
        int newQuantity = sellbook.getNewQuantity();
        int rate = sellbook.getRate();
        return String.valueOf(newQuantity * rate);
    }
    
    
    private static void addHeaderCell(PdfPTable table, String headerTitle) {
        PdfPCell header = new PdfPCell();
        Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD);
        header.setBackgroundColor(java.awt.Color.CYAN);
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.setBorderWidth(2);
        header.setPhrase(new Phrase(headerTitle, headFont));
        table.addCell(header);
    }

    private static void addCell(PdfPTable table, String content) {
        PdfPCell cell = new PdfPCell(new Phrase(content));
        setCommonCellProperties(cell);
        table.addCell(cell);
    }

    private static void setCommonCellProperties(PdfPCell cell) {
        cell.setPaddingLeft(4);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingRight(4);
    }

    private static PdfPTable createPdfPTable(int columns) {
        return new PdfPTable(columns);
    }

    private static Paragraph createCenteredParagraph(String content, Font font) {
        Paragraph paragraph = new Paragraph(content, font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        return paragraph;
    }

    private static Paragraph createEmptyLine() {
        return new Paragraph(Chunk.NEWLINE);
    }
}
