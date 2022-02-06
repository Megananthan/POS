package com.increff.pos.dto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.stereotype.Service;

import com.increff.pos.model.OrderDetail;

@Service
public class ConvertorJavaToXML
{
	
	
	
	
	
	
	protected static void generatePDF() throws IOException{
		
		 File pdfFile = new File("order.pdf");
		 OutputStream out = new FileOutputStream(pdfFile);
         out = new java.io.BufferedOutputStream(out);
        try{
        	
        	
        	
        	
            FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());

            //Setup a buffer to obtain the content length
//            ByteArrayOutputStream out = new ByteArrayOutputStream();

            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource("src\\main\\resources\\com\\increff\\pos\\orderitem.xsl"));
            //Make sure the XSL transformation's result is piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            //Setup input
            Source src = new StreamSource(new File("src\\main\\resources\\com\\increff\\pos\\orderitem.xml"));

            //Start the transformation and rendering process
            transformer.transform(src, res);
           

//            //Prepare response
//            response.setContentType("application/pdf");
//            response.setContentLength(out.size());
//
//            //Send content to Browser
//            response.getOutputStream().write(out.toByteArray());
//            response.getOutputStream().flush();
        }catch(Exception e){
            e.printStackTrace();
        }
        finally { out.close();}
    }
	
    public static void jaxbObjectToXML(OrderDetail d) 
    {
        try
        {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(OrderDetail.class);
             
            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
 
           //Store XML to File
            File file = new File("src\\main\\resources\\com\\increff\\pos\\orderitem.xml");
             
            //Writes XML file to file-system
            jaxbMarshaller.marshal(d, file); 
        } 
        catch (JAXBException e) 
        {
            e.printStackTrace();
        }
    }
}