package com.aguillen.supermarketshoppingmobile.export;

import android.os.Environment;
import android.util.Base64;
import com.aguillen.supermarketshoppingmobile.util.Images;

import com.aguillen.supermarketshoppingmobile.model.Shopping;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

public class TemplatePDF {

    private static final String PATH_NAME = Environment.getExternalStorageDirectory().toString() + "/Download/Supermarket Shopping/Lista de compras.pdf";

    public static void exportPDF(List<Shopping> shoppingList) throws Exception {
        createFile();
        addContentFile(shoppingList);
    }

    private static void createFile() throws Exception {
        File file = new File(PATH_NAME);
        if(!file.exists()) file.getParentFile().mkdirs();
    }

    private static void addContentFile(List<Shopping> shoppingList) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(PATH_NAME));
        Document doc = new Document(pdfDoc);

        doc.add(createTitleImage());
        doc.add(createSubtitleParagraph());
        doc.add(createTable(shoppingList));

        doc.close();
    }

    private static Table createTable(List<Shopping> shoppingList) throws MalformedURLException {
        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2}));
        for(Shopping shopping : shoppingList) {
            if(shopping.getCantidad() > 0) {
                byte[] articleImageByte = Base64.decode(shopping.getArticle().getImage(), Base64.DEFAULT);
                table.addCell(createImageCell(articleImageByte));
                table.addCell(createTextCell(
                        shopping.getArticle().getName() + "\n" +
                                shopping.getArticle().getDescription() + "\n" +
                                "Cantidad: " + shopping.getCantidad()
                ));
            }
        }
        return table;
    }

    private static Paragraph createSubtitleParagraph() {
        return new Paragraph("Lista de compras")
                .setFontSize(18)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(30);
    }

    private static Image createTitleImage() {
        byte[] titleImageByte = Base64.decode(Images.TITLE_IMAGE, Base64.DEFAULT);
        ImageData titleImageData = ImageDataFactory.create(titleImageByte);
        return new Image(titleImageData)
                .setMarginBottom(30);
    }

    private static Cell createImageCell(byte[] titleImageByte) throws MalformedURLException {
        return new Cell()
                .setBorder(Border.NO_BORDER)
                .setMarginBottom(20)
                .add(new Image(ImageDataFactory.create(titleImageByte))
                    .setWidth(60)
                    .setHeight(60));
    }

    private static Cell createTextCell(String text) {
        Paragraph p = new Paragraph(text);
        p.setTextAlignment(TextAlignment.LEFT);
        return new Cell()
                .add(p)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setBorder(Border.NO_BORDER)
                .setMarginLeft(20);
    }
}
