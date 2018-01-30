/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.umsl;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.security.Signature;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.text.Document;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureOptions;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.tools.TextToPDF;

/**
 *
 * @author njc3n3
 */
public class runPDF 
{
    File pdfFile;
    File txtFile;
    String content;
    Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) throws IOException
    {
        runPDF myPDF = new runPDF();
        myPDF.readTXT();
        //myPDF.writeText();
        
        //basic();
        //merge();
        //split();
    }
    
    public void readPDF() throws IOException
    {
//        System.out.println("Please enter PDF file location, omit extension: ");
//        String input = sc.next(); 
        pdfFile = new File("E:\\Past Semesters\\FALL2016\\Java 2\\PDFBOX\\sheet2.txt");
        PDDocument pdDocument = PDDocument.load(pdfFile);
        
        
        
        PDFTextStripper strip = new PDFTextStripper();
//        strip.setStartPage(1);
//        strip.setEndPage(1);
        content = strip.getText(pdDocument);
        System.out.println("PDF Read");
//        System.out.println(content);
//        FileOutputStream outStream;
//        strip.writeText(txtFile, outStream);
        
    }
    public void readTXT() throws IOException
    {
     
    pdfFile = new File("E:\\Past Semesters\\FALL2016\\Java 2\\PDFBOX\\sheet2.txt");
    //FileReader reader = new FileReader(pdfFile);
    TextToPDF txpd = new TextToPDF();
    PDDocument doc = new PDDocument();
    PDPage bp = new PDPage();
    doc.addPage(bp);
    
    BufferedReader reader = new BufferedReader(new FileReader(pdfFile));
    txpd.createPDFFromText(doc,reader);
    
    doc.save("E:\\Past Semesters\\FALL2016\\Java 2\\PDFBOX\\freedom.pdf");  

    }
    
    public void writeText() throws FileNotFoundException, IOException
    {
//        System.out.println("Please enter TXT file location, omit extension: ");
//        String input = sc.next();
        txtFile = new File("E:\\Past Semesters\\FALL2016\\Java 2\\PDFBOX\\sheet3.pdf");
        FileOutputStream FOS = new FileOutputStream(txtFile);
        ObjectOutputStream OOS = new ObjectOutputStream(FOS);
        OOS.writeObject(content);
        OOS.flush();
        OOS.close();
        FOS.flush();
        FOS.close();
        System.out.println("Text File Written");
//        PrintWriter pw = new PrintWriter(txtFile);
//        pw.write(content);
    }
    public static void basic()
    {
    
    String fileName = "Pdfwithtext.pdf";
        try{
            //CREATE PDF
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();
            PDPageContentStream content = new PDPageContentStream(doc, page);
            
            //ADD PAGE
            doc.addPage(page);
            
            //ADD PICTURE
            InputStream in = new FileInputStream("E:\\FALL2016\\Java 2\\PDFBOX\\PDFB\\code-for-your-life.jpg");
            BufferedImage bimg = ImageIO.read(in);           
            PDImageXObject pdi = LosslessFactory.createFromImage(doc, bimg);
            content.drawImage(pdi, 100, 200);
            
            //ADD LINE
            content.setLineWidth(1);
            content.moveTo(200, 740);
            content.lineTo(400, 740);
            content.closeAndStroke();
            
            //ADD TEXT
            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.setNonStrokingColor(Color.yellow);
            content.moveTextPositionByAmount(250, 750);
            content.drawString("Registration Form");          
            content.endText();
            
            content.beginText();
            content.setFont(PDType1Font.COURIER_BOLD, 13);
            content.setNonStrokingColor(Color.red);
            content.moveTextPositionByAmount(100, 600);
            content.drawString("Name");
            content.endText();
            
            content.setLineWidth(3);
            content.moveTo(140,600);
            content.lineTo(240, 600);
            content.closeAndStroke();
            content.close();    
            
            //SAVE FILE
            doc.save(fileName);
            doc.close();
            System.out.println("your file created in : " + System.getProperty("user.dir"));
            }
        catch(IOException e)
            {
            System.out.println(e.getMessage());
            }
    }
//    public static void img()
//    {
//   
//        try {
//            PDDocument document = PDDocument.load(new File("Pdfwithtext.pdf"));
//            List<PDPage> pages = (List<PDPage>) document.getDocumentCatalog().getPages();
//            
//          
//            PDFRenderer pdfRenderer = new PDFRenderer(document);
//            
//
//            
//         
//            } 
//        
//        catch (IOException ex) 
//        {
//            Logger.getLogger(PDFB.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public static void merge()
    {
        try {
            PDFMergerUtility ut = new PDFMergerUtility();
            ut.addSource("E:\\FALL2016\\Java 2\\PDFBOX\\Pdfwithtext.pdf");
            ut.addSource("E:\\FALL2016\\Java 2\\PDFBOX\\sample.pdf");
            ut.setDestinationFileName("merged.pdf");
            ut.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        } catch (IOException ex) {
            Logger.getLogger(runPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void split()
    {
    File file = new File("E:\\FALL2016\\Java 2\\PDFBOX\\merged.pdf"); 
        try 
        {
            PDDocument document = PDDocument.load(file);
            Splitter splitter = new Splitter();
            List<PDDocument> Pages = splitter.split(document);
            Iterator<PDDocument> iterator = Pages.listIterator();
                int i = 1;
                while(iterator.hasNext()) 
                {
                PDDocument pd = iterator.next();
                pd.save("E:\\FALL2016\\Java 2\\PDFBOX\\split"+ i++ +".pdf");
                }
        } catch (IOException ex) 
        {
            Logger.getLogger(runPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
