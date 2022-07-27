/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oscarblancarte.ipd.templetemethod.impl;

import java.io.File;
import java.io.FileOutputStream;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element; 
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import oscarblancarte.ipd.templetemethod.util.OnMemoryDataBase;




/**
 *
 * @author ABUESTAN
 */
public class EmpresaXYZFileProcess extends AbstractFileProcessTemplete {
    private String log = "";


    public EmpresaXYZFileProcess(File file, String logPath, String movePath) {
        super(file, logPath, movePath);
    }
    
    
    @Override
    protected void validateName() throws Exception {
        String fileName = file.getName();
        if (!fileName.endsWith(".xyz")) {
            throw new Exception("Invalid file name, must end with .xyz");
        }
    }

    @Override
    protected void processFile() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            String fileName = this.file.getName();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            doc.getDocumentElement().normalize();
            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
            System.out.println("------");

            NodeList list = doc.getElementsByTagName("Documento");

            for (int temp = 0; temp < list.getLength(); temp++) {
              Node node = list.item(temp);

              if (node.getNodeType() == Node.ELEMENT_NODE) {
                  Element element = (Element) node;
                  
                  String codigo = element.getElementsByTagName("Codigo").item(0).getTextContent();
                  String cliente = element.getElementsByTagName("Cliente").item(0).getTextContent();
                  String fecha = element.getElementsByTagName("Fecha").item(0).getTextContent();
                  String producto = element.getElementsByTagName("Producto").item(0).getTextContent();
                  String cantidad = element.getElementsByTagName("Cantidad").item(0).getTextContent();
                  String precio = element.getElementsByTagName("Precio").item(0).getTextContent();
                  String total = element.getElementsByTagName("Total").item(0).getTextContent();
                  String observacion = element.getElementsByTagName("Observacion").item(0).getTextContent();
                  
                double totalAmount = Double.parseDouble(total);
                  
                boolean exist = OnMemoryDataBase.customerExist(Integer.parseInt(codigo));
                if (!exist) {
                    log += codigo + " E" + cliente + "\t\t" + fecha + " Customer not exist\n";
                } else if (totalAmount > 200) {
                    log += codigo + " E" + cliente + "\t\t" + fecha + " The amount exceeds the maximum\n";
                } else {
                    //TODO Aplicar el pago en algún lugar.
                    log += codigo + " E" + cliente + "\t\t" + fecha + " Successfully applied\n";
                }
              }
            }

        } finally {
        }
    }

    @Override
    protected void createLog() throws Exception {
        FileOutputStream out = null;
        try {
            File outFile = new File(logPath + "/" + file.getName());
            if (!outFile.exists()) { outFile.createNewFile(); }
            out = new FileOutputStream(outFile, false);
            out.write(log.getBytes());
            out.flush();

        } finally {
            out.close();
        }
    }
    
}
