package com.increff.pos.dto;

import java.io.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import com.increff.pos.model.OrderDetail;


public class PDFConvertor
{
	
	 public static void convertToPDF()  throws IOException, FOPException, TransformerException {
	        // the XSL FO file
	        File xsltFile = new File("src\\\\main\\\\resources\\\\com\\\\increff\\\\pos\\\\orderitem.xsl");
	        // the XML file which provides the input
	        StreamSource xmlSource = new StreamSource(new File("src\\\\main\\\\resources\\\\com\\\\increff\\\\pos\\\\orderitem.xml"));
	        // create an instance of fop factory
	        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
	        // a user agent is needed for transformation
	        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
	        // Setup output
	        OutputStream out = new FileOutputStream("src\\\\main\\\\resources\\\\com\\\\increff\\\\pos\\\\Invoice.pdf");
	    
	        try {
	            // Construct fop with desired output format
	            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

	            // Setup XSLT
	            TransformerFactory factory = TransformerFactory.newInstance();
	            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

	            // Resulting SAX events (the generated FO) must be piped through to FOP
	            Result res = new SAXResult(fop.getDefaultHandler());

	            // Start XSLT transformation and FOP processing
	            // That's where the XML is first transformed to XSL-FO and then 
	            // PDF is created
	            transformer.transform(xmlSource, res);
	        } finally {
	            out.close();
	        }
	        
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